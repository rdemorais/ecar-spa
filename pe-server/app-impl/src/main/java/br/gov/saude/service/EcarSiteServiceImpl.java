package br.gov.saude.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import br.gov.saude.dao.EcarSiteDao;
import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.Estrutura;
import br.gov.saude.model.Etiqueta;
import br.gov.saude.model.OE;
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