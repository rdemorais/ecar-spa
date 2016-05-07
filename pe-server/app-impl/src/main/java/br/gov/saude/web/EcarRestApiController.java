package br.gov.saude.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.Estrutura;
import br.gov.saude.service.EcarSiteService;
import br.gov.saude.web.dto.AnexoDto;
import br.gov.saude.web.dto.EtiquetaDto;
import br.gov.saude.web.dto.FiltroDto;
import br.gov.saude.web.dto.ItemDto;
import br.gov.saude.web.dto.OeDto;

@Controller
@CrossOrigin(value="*", maxAge=3600)
@RequestMapping(value="/ecar/api")
public class EcarRestApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(EcarRestApiController.class);
	
	@Autowired
	private EcarSiteService ecarSiteService;
	
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
		List<ItemDto> itens = ecarSiteService.loadListaItens(filtro, Estrutura.INICIATIVA);
		logger.debug("retornando lista de itens - MI: " + itens.size());
		
		return EcarResponse.ok(itens);
	}
	
	@RequestMapping(value="/lista-itens-pi", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse loadListaItensPi(@RequestBody FiltroDto filtro) {
		List<ItemDto> itens = ecarSiteService.loadListaItens(filtro, Estrutura.PRODUTO_INTERMEDIARIO);
		logger.debug("retornando lista de itens - PI: " + itens.size());
		
		return EcarResponse.ok(itens);
	}
	
	@RequestMapping(value="/lista-itens-atv", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse loadListaItensAtv(@RequestBody FiltroDto filtro) {
		List<ItemDto> itens = ecarSiteService.loadListaItens(filtro, Estrutura.ATIVIDADE);
		logger.debug("retornando lista de itens - ATV: " + itens.size());
		
		return EcarResponse.ok(itens);
	}
	
	@RequestMapping(value="/status", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse loadStatus() {
		
		logger.debug("retornando statusBar");
		
		return EcarResponse.ok(ecarSiteService.loadStatusBar(1L));
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
			e.printStackTrace();
		} catch (AkulaRuntimeException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		} catch (AkulaRuntimeException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		} catch (AkulaRuntimeException e) {
			e.printStackTrace();
		}
        
	}
	
	
}