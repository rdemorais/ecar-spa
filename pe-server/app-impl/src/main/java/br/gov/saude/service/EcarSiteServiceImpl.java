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

import org.springframework.beans.factory.annotation.Autowired;

import br.gov.saude.dao.EcarSiteDao;
import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.exc.AkulaServiceRuntimeException;
import br.gov.saude.file.EcarFileSystem;
import br.gov.saude.model.Estrutura;
import br.gov.saude.model.Etiqueta;
import br.gov.saude.model.OE;
import br.gov.saude.report.EcarReport;
import br.gov.saude.web.dto.EtiquetaDto;
import br.gov.saude.web.dto.FiltroDto;
import br.gov.saude.web.dto.ItemDto;
import br.gov.saude.web.dto.OeDto;
import br.gov.saude.web.dto.StatusBarDto;

public class EcarSiteServiceImpl implements EcarSiteService{
	
	@Autowired
	private EcarSiteDao ecarSiteDao;
	
	@Autowired
	private ConvertService convertService;
	
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private EcarReport ecarReport;
	
	@Autowired
	public EcarFileSystem ecarFileSystem;
	
	public byte[] gerarRelatorioGerencial(FiltroDto filtro) throws AkulaRuntimeException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		List<Object> conteudo = new ArrayList<Object>();
		SimpleDateFormat sdfDataH = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		try {
			BufferedImage image = ecarFileSystem.getImageFromContext("logo_small.gif");
			BufferedImage logoEcar = ecarFileSystem.getImageFromContext("ecarLogo.png");
			BufferedImage logoHeader = ecarFileSystem.getImageFromContext("header_logos.gif");
			
			List<ItemDto> listaItens = loadListaItens(filtro, Estrutura.META);
			
			conteudo.add(convertService.createPEGerencial(listaItens));
			parametros.put("logo", image);
			parametros.put("logoEcar", logoEcar);
			parametros.put("logoHeader", logoHeader);
			parametros.put("dataAtual", sdfDataH.format(new Date()));
			
			byte[] bytes = ecarReport.generateReportPDF("pe-gerencial.jasper",  parametros, conteudo);
			
			return bytes;
		} catch (IOException e) {
			throw new AkulaServiceRuntimeException(e.getMessage(), e);
		}
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
	public StatusBarDto loadStatusBar(Long codExe) throws AkulaRuntimeException {
		
		return statusService.loadStatusBar(codExe);
	}
	
	public ItemDto loadItem(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException {
		return ecarSiteDao.loadItem(filtro, estrutura);
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
}