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

import br.gov.saude.model.Estrutura;import br.gov.saude.web.dto.AnexoDto;
import br.gov.saude.web.dto.EtiquetaDto;
import br.gov.saude.web.dto.FiltroDto;
import br.gov.saude.web.dto.ItemDto;
import br.gov.saude.web.dto.OeDto;
import br.gov.saude.web.dto.SecretariaDto;
import br.gov.saude.web.dto.StatusBarDto;

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
	public void testLoadStatusBar() {
		StatusBarDto statusBar = ecarSiteService.loadStatusBar(1L);
		logger.debug("Carregando StatusBar: " + statusBar.toString());
	}
	
	@Test
	public void testFiltroEstruturaMetaIniListaItens() {
		FiltroDto filtro = new FiltroDto();
		filtro.setCodExe(1L);
		List<ItemDto> listaItens = ecarSiteService.loadListaItens(filtro, Estrutura.META);
		/*
		for (ItemDto itemDto : listaItens) {
			logger.debug(itemDto.toString());
		}*/
		
		logger.debug("Tamanho da listagem de metas/iniciativas: " + listaItens.size());
	}
	
	@Test
	public void testFiltroEstruturaProdutoListaItens() {
		FiltroDto filtro = new FiltroDto();
		filtro.setCodExe(1L);
		filtro.setCodIett(44L);
		List<ItemDto> listaItens = ecarSiteService.loadListaItens(filtro, Estrutura.PRODUTO_INTERMEDIARIO);
		
		logger.debug("Tamanho da listagem de produtos: " + listaItens.size());
	}
	
	@Test
	public void testFiltroEstruturaAtividadeListaItens() {
		FiltroDto filtro = new FiltroDto();
		filtro.setCodExe(1L);
		filtro.setCodIett(321L);
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
		filtro.setOes(Arrays.asList(new Long(17))); //OE 01 
		
		List<ItemDto> listaItens = ecarSiteService.loadListaItens(filtro, Estrutura.META);
		logger.debug("Tamanho da listagem - Filtro OE 01: " + listaItens.size());
	}
	
	@Test
	public void testFiltroStatusListaItens() {
		FiltroDto filtro = new FiltroDto();
		filtro.setCodExe(1L);
		filtro.setStatus(Arrays.asList(new Long(1))); //Verde 
		
		List<ItemDto> listaItens = ecarSiteService.loadListaItens(filtro, Estrutura.META);

		logger.debug("Tamanho da listagem - Filtro Status: " + listaItens.size());
	}
	
	@Test
	public void testFiltroEtiquetasListaItens() {
		FiltroDto filtro = new FiltroDto();
		filtro.setCodExe(1L);
		filtro.setEtiquetas(Arrays.asList(new Long(1144))); 
		
		List<ItemDto> listaItens = ecarSiteService.loadListaItens(filtro, Estrutura.META);

		logger.debug("Tamanho da listagem - Filtro Etiquetas: " + listaItens.size());
	}
	
	@Test
	public void testFiltroPPAListaItens() {
		FiltroDto filtro = new FiltroDto();
		filtro.setCodExe(1L);
		filtro.setPpa(true);
		List<ItemDto> listaItens = ecarSiteService.loadListaItens(filtro, Estrutura.META);

		logger.debug("Tamanho da listagem - Filtro PPA: " + listaItens.size());
	}
	
	@Test
	public void testLoadItem() {
		FiltroDto filtro = new FiltroDto();
		filtro.setCodExe(1L);
		filtro.setCodIett(321L);
		
		ItemDto item = ecarSiteService.loadItem(filtro, Estrutura.PRODUTO_INTERMEDIARIO);
		
		Assert.assertNotNull(item);
		
		logger.debug("Item carregado com sucesso: " + item.toString());
	}
	
	@Test
	public void testLoadAnexos() {
		FiltroDto filtro = new FiltroDto();
		filtro.setCodIett(41L);
		
		List<AnexoDto> anexos = ecarSiteService.listaAnexos(filtro);
		
		logger.debug("Lista de anexos: " + anexos.size());
		
		for (AnexoDto anexoDto : anexos) {
			logger.debug(anexoDto.toString());
		}
	}
	
	@Test
	public void testLoadSecretarias() {
		List<SecretariaDto> secretarias = ecarSiteService.loadSecretarias();
		
		for (SecretariaDto secretariaDto : secretarias) {
			logger.debug(secretariaDto.toString());
		}
	}
	
	@Test
	public void testFiltroPorSecretaria() {
		FiltroDto filtro = new FiltroDto();
		filtro.setCodExe(1L);
		filtro.setSecretarias(Arrays.asList(new Long(12))); //SGETS
		
		List<ItemDto> listaItens = ecarSiteService.loadListaItens(filtro, Estrutura.INICIATIVA);
		
		for (ItemDto itemDto : listaItens) {
			logger.debug(itemDto.toString());
		}
	}
}