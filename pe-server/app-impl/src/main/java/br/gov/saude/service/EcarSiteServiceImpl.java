package br.gov.saude.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.saude.dao.EcarDao;
import br.gov.saude.dao.EcarSiteDao;
import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.exc.AkulaServiceRuntimeException;
import br.gov.saude.file.EcarFileSystem;
import br.gov.saude.model.Estrutura;
import br.gov.saude.model.Etiqueta;
import br.gov.saude.model.OE;
import br.gov.saude.model.UsuarioPermissaoMonitoramento;
import br.gov.saude.report.EcarReport;
import br.gov.saude.web.dto.AnexoDto;
import br.gov.saude.web.dto.CorDto;
import br.gov.saude.web.dto.EtiquetaDto;
import br.gov.saude.web.dto.FiltroDto;
import br.gov.saude.web.dto.ItemDto;
import br.gov.saude.web.dto.OeDto;
import br.gov.saude.web.dto.SecretariaDto;import br.gov.saude.web.dto.SituacaoDto;
import br.gov.saude.web.dto.StatusBarDto;

public class EcarSiteServiceImpl implements EcarSiteService{
	
	@Autowired
	private EcarSiteDao ecarSiteDao;
	
	@Autowired
	private EcarDao ecarDao;
	
	@Autowired
	private ConvertService convertService;
	
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private EcarReport ecarReport;
	
	@Autowired
	private EcarFileSystem ecarFileSystem;
	
	@Autowired
	private ControleAcessoService controleAcessoService;
	
	@Autowired
	public RelatorioExcelService relatorioExcelService;
	
	public List<SecretariaDto> loadSecretarias() throws AkulaRuntimeException {
		return ecarSiteDao.loadSecretarias();
	}
	
	public byte[] gerarRelatorioExcel(FiltroDto filtro) throws AkulaRuntimeException {
		
		return relatorioExcelService.exportarRelatorioExcel(filtro).getBytes();
	}
	
	public AnexoDto loadAnexo(Long codAnexo) throws AkulaRuntimeException {
		return ecarDao.loadAnexo(codAnexo);
	}
	
	public List<AnexoDto> listaAnexos(FiltroDto filtro) throws AkulaRuntimeException {
		return ecarDao.listaAnexos(filtro.getCodIett());
	}
	
	private Map<String, Object> gerarParametros() throws IOException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		SimpleDateFormat sdfDataH = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		BufferedImage image = ecarFileSystem.getImageFromContext("logo_small.gif");
		BufferedImage logoHeader = ecarFileSystem.getImageFromContext("bg-gov.png");
		
		parametros.put("logo", image);
		parametros.put("logoHeader", logoHeader);
		parametros.put("dataAtual", sdfDataH.format(new Date()));
		
		return parametros;
	}
	
	public byte[] gerarRelatorioExecutivo(FiltroDto filtro) throws AkulaRuntimeException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		List<Object> conteudo = new ArrayList<Object>();
		
		try {
			parametros = gerarParametros();
			Estrutura nivelPai = Estrutura.valueOf(filtro.getNivel().toUpperCase());
			ItemDto item = null;
			List<ItemDto> listaItens = null;
			
			switch (nivelPai) {
			case META:
				item = loadItem(filtro, Estrutura.META);
				listaItens = loadListaItens(filtro, Estrutura.PRODUTO_INTERMEDIARIO);
				break;
			case INICIATIVA:
				item = loadItem(filtro, Estrutura.INICIATIVA);
				listaItens = loadListaItens(filtro, Estrutura.PRODUTO_INTERMEDIARIO);
				break;
			case PRODUTO_INTERMEDIARIO:
				item = loadItem(filtro, Estrutura.PRODUTO_INTERMEDIARIO);
				listaItens = loadListaItens(filtro, Estrutura.ATIVIDADE);
				break;
			case ATIVIDADE:
				item = loadItem(filtro, Estrutura.ATIVIDADE);
				listaItens = new ArrayList<ItemDto>();
				break;
			default:
				break;
			}
			
			
			conteudo.add(convertService.createPEExecutivo(item, listaItens));
			
			byte[] bytes = ecarReport.generateReportPDF("pe-executivo.jasper",  parametros, conteudo);
			
			return bytes;
		} catch (IOException e) {
			throw new AkulaServiceRuntimeException(e.getMessage(), e);
		}
	}
	
	public byte[] gerarRelatorioGerencial(FiltroDto filtro) throws AkulaRuntimeException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		List<Object> conteudo = new ArrayList<Object>();
		
		try {
			parametros = gerarParametros();
			List<ItemDto> listaItens = loadListaItens(filtro, Estrutura.META);
			
			conteudo.add(convertService.createPEGerencial(listaItens, filtro));
			
			byte[] bytes = ecarReport.generateReportPDF("pe-gerencial.jasper",  parametros, conteudo);
			
			return bytes;
		} catch (IOException e) {
			throw new AkulaServiceRuntimeException(e.getMessage(), e);
		}
	}
	
	@Transactional
	public List<OeDto> listaOesPns() throws AkulaRuntimeException {
		return ecarSiteDao.listOEPns();
	}
	
	@Transactional
	public List<OeDto> listaOes() throws AkulaRuntimeException {
		List<OE> oesDb = ecarSiteDao.loadOes();
		
		return convertService.convertListaOE(oesDb);
	}
	
	@Transactional
	public List<EtiquetaDto> listaEtiquetas() throws AkulaRuntimeException {
		List<Etiqueta> etiquetas = ecarSiteDao.loadEtiquetas();
		
		return convertService.convertListaEtiquetas(etiquetas);
	}
	
	@Transactional
	public StatusBarDto loadStatusBar(FiltroDto filtro) throws AkulaRuntimeException {
		
		return statusService.loadStatusBar(filtro);
	}
	
	public ItemDto loadItem(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException {
		ItemDto dto = ecarSiteDao.loadItem(filtro, estrutura);
		Long idUser = (Long) controleAcessoService.usuarioLogadoId();
		UsuarioPermissaoMonitoramento upm = ecarSiteDao.loadUsuarioPermissaoMonitoramento(idUser, filtro.getCodIett());
		DateTime hoje = new DateTime();
		DateTime dataLimite = new DateTime(dto.getDataLimite());
		dataLimite = dataLimite.plusDays(1);
		
		if(upm != null && dataLimite.isAfter(hoje)) {
			dto.setParecerAutorizado(true);
		}else {
			dto.setParecerAutorizado(false);
		}
		
		return dto;
	}
	
	@Transactional
	public List<ItemDto> loadListaItens(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException {
		List<ItemDto> monitorados = ecarSiteDao.loadListaItens(filtro, estrutura, false);
		List<ItemDto> nMonitorados = ecarSiteDao.loadListaItens(filtro, estrutura, true);
		
		List<ItemDto> itens = new ArrayList<ItemDto>(monitorados);
		
		itens.addAll(nMonitorados);
		
		Collections.sort(itens);
		
		return itens;
	}
	
	@Transactional
	public List<SituacaoDto> listSituacao() throws AkulaRuntimeException {
		return ecarSiteDao.listSituacao();
	}
	
	@Transactional
	public List<CorDto> listCor() throws AkulaRuntimeException {
		return ecarSiteDao.listCor();
	}
}