package br.gov.saude.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.gov.saude.service.dto.TrocaSenhaDto;
import br.gov.saude.spi.service.EcarSiteService;

@Controller
@CrossOrigin(value="*", maxAge=3600)
@RequestMapping(value="/troca-senha/ecar/api")
public class TrocaSenhaController {
	
	private static final Logger logger = LoggerFactory.getLogger(TrocaSenhaController.class);
	
	@Autowired
	private EcarSiteService ecarSiteService;
	
	@RequestMapping(value="/troca-senha", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse trocarSenha(@RequestBody TrocaSenhaDto trocaDto) {
		logger.debug("Trocando senha do usuario");
		
		try {
			ecarSiteService.trocarSenha(trocaDto);
			return EcarResponse.ok();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return EcarResponse.error(e.getMessage());
		}
	}
	
	@RequestMapping(value="/verificar-usuario", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse verificarUsuario(@RequestBody TrocaSenhaDto trocaDto) {
		logger.debug("Verificando usuario para troca de senha: " + trocaDto.toString());
		
		try {
			TrocaSenhaDto ret = ecarSiteService.verificarUsuario(trocaDto);
			return EcarResponse.ok(ret);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return EcarResponse.error(e.getMessage());
		}
	}
}