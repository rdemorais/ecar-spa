package br.gov.saude.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.gov.saude.model.Estrutura;
import br.gov.saude.web.dto.EtiquetaDto;
import br.gov.saude.web.dto.FiltroDto;
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
		logger.debug("Tamanho da listagem OES: " + oes.size());
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
		
		Assert.assertTrue(statusList.size() >= 0);
	}
	
	@Test
	public void testFiltroEstruturaMetaIniListaItens() {
		FiltroDto filtro = new FiltroDto();
		filtro.setCodExe(1L);
		
		List<ItemDto> listaItens = ecarSiteService.loadListaItens(filtro, Estrutura.META);
		
		logger.debug("Tamanho da listagem de metas/iniciativas: " + listaItens.size());
	}
	
	@Test
	public void testFiltroEstruturaProdutoListaItens() {
		FiltroDto filtro = new FiltroDto();
		filtro.setCodExe(1L);
		
		List<ItemDto> listaItens = ecarSiteService.loadListaItens(filtro, Estrutura.PRODUTO_INTERMEDIARIO);
		
		logger.debug("Tamanho da listagem de produtos: " + listaItens.size());
	}
	
	@Test
	public void testFiltroEstruturaAtividadeListaItens() {
		FiltroDto filtro = new FiltroDto();
		filtro.setCodExe(1L);
		
		List<ItemDto> listaItens = ecarSiteService.loadListaItens(filtro, Estrutura.ATIVIDADE);
		
		logger.debug("Tamanho da listagem de atividades: " + listaItens.size());
	}
	
	@Test
	public void testFiltroEstruturaApenasMetaListaItens() {
		FiltroDto filtro = new FiltroDto();
		filtro.setCodExe(1L);
		filtro.setMeta(true);
		
		List<ItemDto> listaItens = ecarSiteService.loadListaItens(filtro, Estrutura.META);
		
		logger.debug("Tamanho da listagem - Apenas Metas: " + listaItens.size());
	}
	
	@Test
	public void testFiltroEstruturaApenasIniciativaListaItens() {
		FiltroDto filtro = new FiltroDto();
		filtro.setCodExe(1L);
		filtro.setIniciativa(true);
		
		List<ItemDto> listaItens = ecarSiteService.loadListaItens(filtro, Estrutura.META);
		
		logger.debug("Tamanho da listagem - Apenas Iniciativas: " + listaItens.size());
	}
	
	@Test
	public void testFiltroEstruturaApenasMetasIniciativaListaItens() {
		FiltroDto filtro = new FiltroDto();
		filtro.setCodExe(1L);
		filtro.setIniciativa(true);
		filtro.setMeta(true);
		
		List<ItemDto> listaItens = ecarSiteService.loadListaItens(filtro, Estrutura.META);
		
		logger.debug("Tamanho da listagem - Apenas Metas/Iniciativas: " + listaItens.size());
	}
	
	@Test
	public void testFiltroOEsListaItens() {
		FiltroDto filtro = new FiltroDto();
		filtro.setCodExe(1L);
		filtro.setOes(Arrays.asList(new Long(17), new Long(28))); //OE 01 e OE 12
		
		List<ItemDto> listaItens = ecarSiteService.loadListaItens(filtro, Estrutura.META);
		/*
		for (ItemDto itemDto : listaItens) {
			logger.debug(itemDto.toString());
		}
		*/
		logger.debug("Tamanho da listagem - Filtro OE 01: " + listaItens.size());
	}
	
}