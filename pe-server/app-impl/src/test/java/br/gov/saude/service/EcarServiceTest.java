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

import br.gov.saude.web.dto.EtiquetaDto;
import br.gov.saude.web.dto.ItemDto;
import br.gov.saude.web.dto.OeDto;
import br.gov.saude.web.dto.StatusDto;

@ContextConfiguration("/META-INF/ecarTest-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EcarServiceTest {
	
	private static final Logger logger = LoggerFactory.getLogger(EcarServiceTest.class);
	
	@Autowired
	private EcarSiteService ecarSiteService;
	
	@Test
	public void testListaOEs() {
		List<OeDto> oes = ecarSiteService.listaOes();
		logger.debug("Tamanho da listagem: " + oes.size());
		Assert.assertEquals(24, oes.size());
	}
	
	@Test
	public void testListaEtiquetas() {
		List<EtiquetaDto> etiquetas = ecarSiteService.listaEtiquetas();
		logger.debug("Tamanho da listagem de etiquetas: " + etiquetas.size());
		Assert.assertTrue(etiquetas.size() >= 0);
	}
	
	@Test
	public void testLoadStatusCount() {
		List<StatusDto> statusList = ecarSiteService.loadStatusCount(1L);
		logger.debug("Tamanho da listagem de status: " + statusList.size());
		
		for (StatusDto statusDto : statusList) {
			logger.debug(statusDto.toString());
		}
		Assert.assertTrue(statusList.size() >= 0);
	}
	
	@Test
	public void testLoadListaItens() {
		List<ItemDto> listaItens = ecarSiteService.loadListaItens();
		
		logger.debug("Tamanho da listagem de itens: " + listaItens.size());
		
		for (ItemDto itemDto : listaItens) {
			logger.debug(itemDto.toString());
		}
	}
}