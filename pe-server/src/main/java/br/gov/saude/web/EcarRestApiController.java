package br.gov.saude.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.Estrutura;
import br.gov.saude.service.dto.AnexoDto;
import br.gov.saude.service.dto.CorDto;
import br.gov.saude.service.dto.EtiquetaDto;
import br.gov.saude.service.dto.FiltroDto;
import br.gov.saude.service.dto.ItemDto;
import br.gov.saude.service.dto.OeDto;
import br.gov.saude.service.dto.ParecerDto;
import br.gov.saude.service.dto.SecretariaDto;
import br.gov.saude.service.dto.SituacaoDto;
import br.gov.saude.spi.service.ControleAcessoService;
import br.gov.saude.spi.service.EcarSiteService;

@Controller
@CrossOrigin(value="*", maxAge=3600)
@RequestMapping(value="/ecar/api")
public class EcarRestApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(EcarRestApiController.class);
	
	@Autowired
	private EcarSiteService ecarSiteService;
	
	@Autowired
    private TokenStore tokenStore;
	
	@Autowired
	private ControleAcessoService controleAcessoService;
	
	@RequestMapping(value="/nome-usuario", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse getNomeUsuario() {
		String nomeUsuario = controleAcessoService.getNomeUsuario();
		return EcarResponse.ok(nomeUsuario);
	}
	
	
	@RequestMapping(value="/logout", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse logout(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
            logger.debug("Logout do usuario realizado com sucesso");
        }
		return EcarResponse.ok();
	}
	
	@RequestMapping(value="/gravar-parecer", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse gravarParecer(@RequestBody ParecerDto parecer) {
		logger.debug("Gravando parecer: " + parecer.toString());
		
		try {
			ecarSiteService.gravarParecer(parecer);
			
			return EcarResponse.ok();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return EcarResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping(value="/lista-cores", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse listCores() {
		List<CorDto> cores = ecarSiteService.listCor();
		
		logger.debug("retornando lista de Cores: " + cores.size());
		
		return EcarResponse.ok(cores);
	}
	
	@RequestMapping(value="/lista-situacoes", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse listSituacoes() {
		List<SituacaoDto> situacoes = ecarSiteService.listSituacao();
		
		logger.debug("retornando lista de Situacoes: " + situacoes.size());
		
		return EcarResponse.ok(situacoes);
	}
	
	@RequestMapping(value="/lista-secretarias", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse loadSecretarias() {
		List<SecretariaDto> secretarias = ecarSiteService.loadSecretarias();
		
		logger.debug("retornando lista de Secretarias: " + secretarias.size());
		
		return EcarResponse.ok(secretarias);
	}
	
	@RequestMapping(value="/lista-oes-pns", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse loadOesPns() {
		
		List<OeDto> oes = ecarSiteService.listaOesPns();
		
		logger.debug("retornando lista de OEs do PNS: " + oes.size());
		
		return EcarResponse.ok(oes);
	}
	
	@RequestMapping(value="/lista-oes", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse loadOes() {
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		//logger.debug(auth.toString());
		
		List<OeDto> oes = ecarSiteService.listaOes();
		
		logger.debug("retornando lista de OEs: " + oes.size());
		
		return EcarResponse.ok(oes);
	}
	
	@RequestMapping(value="/load-item", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse loadItem(@RequestBody FiltroDto filtro) {
		logger.debug(filtro.toString());
		Estrutura estrutura = Estrutura.INICIATIVA;
		
		if(filtro.getNivel() != null) {
			estrutura = Estrutura.valueOf(filtro.getNivel());
		}
		ItemDto item = ecarSiteService.loadItem(filtro, estrutura);
		if(item != null) {
			logger.debug("retornando item: " + item.toString());
		}
		
		return EcarResponse.ok(item);
	}
	
	@RequestMapping(value="/lista-itens", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse loadListaItens(@RequestBody FiltroDto filtro) {
		List<ItemDto> itens = ecarSiteService.loadListaItens(filtro, Estrutura.INICIATIVA, false, false);
		logger.debug("retornando lista de itens - MI: " + itens.size());
		
		return EcarResponse.ok(itens);
	}
	
	@RequestMapping(value="/lista-itens-pi", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse loadListaItensPi(@RequestBody FiltroDto filtro) {
		List<ItemDto> itens = ecarSiteService.loadListaItens(filtro, Estrutura.PRODUTO_INTERMEDIARIO, false, false);
		logger.debug("retornando lista de itens - PI: " + itens.size());
		
		return EcarResponse.ok(itens);
	}
	
	@RequestMapping(value="/lista-itens-atv", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse loadListaItensAtv(@RequestBody FiltroDto filtro) {
		List<ItemDto> itens = ecarSiteService.loadListaItens(filtro, Estrutura.ATIVIDADE, false, false);
		logger.debug("retornando lista de itens - ATV: " + itens.size());
		
		return EcarResponse.ok(itens);
	}
	
	@RequestMapping(value="/status", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse loadStatus(@RequestBody FiltroDto filtro) {
		
		logger.debug("retornando statusBar");
		
		return EcarResponse.ok(ecarSiteService.loadStatusBar(filtro));
	}
	
	@RequestMapping(value="/etiquetas", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse loadEtiquetas() {
		List<EtiquetaDto> etiquetas = ecarSiteService.listaEtiquetas();
		
		logger.debug("retornando lista de Etiquetas: " + etiquetas.size());
		
		return EcarResponse.ok(etiquetas);
	}
	
	@RequestMapping(value="/anexos", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse loadAnexos(@RequestBody FiltroDto filtro) {
		List<AnexoDto> anexos = ecarSiteService.listaAnexos(filtro);
		
		logger.debug("retornando lista de anexos: " + anexos.size());
		
		return EcarResponse.ok(anexos);
	}
	
	@RequestMapping(value="/excluir-anexo", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse excluirAnexo(@RequestBody AnexoDto anexo) {
		try {
			logger.debug("Excluindo anexo: " + anexo.toString());
			
			ecarSiteService.excluirAnexo(anexo);
			
			return EcarResponse.ok();
		} catch (AkulaRuntimeException e) {
			logger.error(e.getMessage(), e);
			
			return EcarResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping(value="/download-anexo", 
			method=RequestMethod.POST)
	public void downloadRelatorioExecutivo(HttpServletResponse response, @RequestBody Long codAnexo) {
		
	    try {
	    	AnexoDto anexo = ecarSiteService.loadAnexo(codAnexo);
	    	
	    	byte[] data = anexo.getArquivo();
			
			//response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
	    	response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=" + anexo.getNomeOriginal());
		    response.setContentLength(data.length);
		    
			response.getOutputStream().write(data);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (AkulaRuntimeException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@RequestMapping(value="/download-rel-executivo", 
			method=RequestMethod.POST)
	public void downloadRelatorioExecutivo(HttpServletResponse response, @RequestBody FiltroDto filtro) {
		
	    try {
	    	byte[] data = ecarSiteService.gerarRelatorioExecutivo(filtro);
			
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=relatorioExecutivo.pdf");
		    response.setContentLength(data.length);
		    
			response.getOutputStream().write(data);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (AkulaRuntimeException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@RequestMapping(value="/download-rel-gerencial", 
			method=RequestMethod.POST)
	public void downloadRelatorio(HttpServletResponse response, @RequestBody FiltroDto filtro) {
		
	    try {
	    	byte[] data = ecarSiteService.gerarRelatorioGerencial(filtro);
			
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=relatorioGerencial.pdf");
		    response.setContentLength(data.length);
		    
			response.getOutputStream().write(data);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (AkulaRuntimeException e) {
			logger.error(e.getMessage(), e);
		}
        
	}
	
	@RequestMapping(value="/download-rel-executivo-pareceres", 
			method=RequestMethod.POST)
	public void downloadRelatorioExecutivoPareceres(HttpServletResponse response, @RequestBody FiltroDto filtro) {
		
	    try {
	    	byte[] data = ecarSiteService.gerarRelatorioExecutivoPareceres(filtro);
			
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=relatorio-executivo-pareceres.pdf");
		    response.setContentLength(data.length);
		    
			response.getOutputStream().write(data);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (AkulaRuntimeException e) {
			logger.error(e.getMessage(), e);
		}
        
	}
	
	@RequestMapping(value="/download-rel-executivo-pareceres-anteriores", 
			method=RequestMethod.POST)
	public void downloadRelatorioExecutivoPareceresAnteriores(HttpServletResponse response, @RequestBody FiltroDto filtro) {
		
	    try {
	    	byte[] data = ecarSiteService.gerarRelatorioExecutivoPareceresAnteriores(filtro);
			
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=relatorio-executivo-pareceres-anteriores.pdf");
		    response.setContentLength(data.length);
		    
			response.getOutputStream().write(data);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (AkulaRuntimeException e) {
			logger.error(e.getMessage(), e);
		}
        
	}
}