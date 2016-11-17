package br.gov.saude.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import br.gov.saude.service.EcarSiteService;

@Controller
@CrossOrigin(value="*", maxAge=3600)
@RequestMapping(value="/ecar/api")
public class FileUploadController {
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@Autowired
	private EcarSiteService ecarSiteService;
	
	@RequestMapping(value="/upload", 
			method=RequestMethod.POST,
			produces="application/json")
	@ResponseBody
    public EcarResponse handleFileUpload(@RequestParam("file") MultipartFile file,
    		@RequestParam("nomeFile") String nomeFile,
    		@RequestParam("codIett") Long codIett,
    		@RequestParam("codArel") Long codArel,
    		HttpServletRequest request) {
		try {
			logger.debug(file.getSize() + " :: " + codIett);
			ecarSiteService.gravarUpload(file, nomeFile, codIett, codArel);
			return EcarResponse.ok();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return EcarResponse.error(e.getMessage());
		}
	}
}