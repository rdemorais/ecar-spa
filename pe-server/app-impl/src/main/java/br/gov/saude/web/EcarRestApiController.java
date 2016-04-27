package br.gov.saude.web;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.gov.saude.web.dto.OeDto;

@Controller
@RequestMapping(value="/oauth/api")
public class EcarRestApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(EcarRestApiController.class);
	
	@RequestMapping(value="/lista-oes", 
			method=RequestMethod.POST)
	@ResponseBody
	public EcarResponse loadOes() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		logger.debug(auth.toString());
		
		return EcarResponse.ok(Arrays.asList(
					new OeDto(1L, "1", "OE 1"), 
					new OeDto(2L, "2", "OE 2"),
					new OeDto(3L, "3", "OE 3"),
					new OeDto(4L, "4", "OE 4"))
				);
	}
}