package br.gov.saude.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import br.gov.saude.dao.EcarSiteDao;
import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.Etiqueta;
import br.gov.saude.model.OE;
import br.gov.saude.web.dto.EtiquetaDto;
import br.gov.saude.web.dto.OeDto;
import br.gov.saude.web.dto.StatusDto;

public class EcarSiteServiceImpl implements EcarSiteService{
	
	@Autowired
	public EcarSiteDao ecarSiteDao;
	
	@Autowired
	public ConvertService convertService;
	
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
	
	public List<StatusDto> loadStatusCount() throws AkulaRuntimeException {
		List<StatusDto> statusCores = ecarSiteDao.loadStatusCount();
		
		List<StatusDto> naoMonitorado = ecarSiteDao.loadStatusCountNaoMonitorado();
		
		List<StatusDto> statusList = new ArrayList<StatusDto>(statusCores);
		statusList.addAll(naoMonitorado);
		
		return statusList;
	}
}