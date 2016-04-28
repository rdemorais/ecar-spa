package br.gov.saude.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.gov.saude.web.dto.OeDto;

@ContextConfiguration("/META-INF/ecarTest-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EcarServiceTest {
	
	private static final Logger logger = LoggerFactory.getLogger(EcarServiceTest.class);
	
	@Autowired
	private EcarSiteService ecarSiteService;
	
	@Test
	public void testListOEs() {
		List<OeDto> oes = ecarSiteService.listaOes();
		logger.debug("Tamanho da listagem: " + oes.size());
		Assert.assertEquals(24, oes.size());
	}
}