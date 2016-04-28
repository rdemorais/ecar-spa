package br.gov.saude.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import br.gov.saude.dao.EcarSiteDao;
import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.OE;
import br.gov.saude.web.dto.OeDto;

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
}