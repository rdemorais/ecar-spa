package br.gov.saude.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.gov.saude.service.EcarSiteService;
import br.gov.saude.web.dto.EtiquetaDto;
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
}