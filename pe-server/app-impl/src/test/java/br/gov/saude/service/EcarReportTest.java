package br.gov.saude.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.gov.saude.model.Estrutura;
import br.gov.saude.report.EcarReport;
import br.gov.saude.report.PEGerencial;
import br.gov.saude.web.dto.FiltroDto;
import br.gov.saude.web.dto.ItemDto;

@ContextConfiguration("/META-INF/ecarTest-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EcarReportTest {
	
	private static final Logger logger = LoggerFactory.getLogger(EcarReportTest.class);
	
	@Autowired
	public EcarSiteService ecarSiteService;
	
	@Autowired
	public EcarReport ecarReport;
	
	@Test
	public void gerarReportGerencialTest() throws IOException {
		logger.debug("Gerando relatorio...");
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		List<Object> conteudo = new ArrayList<Object>();
		PEGerencial peGerencial = new PEGerencial();
		
		BufferedImage image = ecarReport.getImageFromContext("logo_small.gif");
		
		FiltroDto filtro = new FiltroDto();
		filtro.setCodExe(1L);
		
		List<ItemDto> listaItens = ecarSiteService.loadListaItens(filtro, Estrutura.META);
		peGerencial.setListaItens(listaItens);
		
		conteudo.add(peGerencial);
		parametros.put("logo", image);
		
		byte[] bytes = ecarReport.generateReportPDF("pe-gerencial.jasper",  parametros, conteudo);

		File reportFile = new File("/Users/rafaeldemorais/ecarReport.pdf");
		reportFile.createNewFile();
		
		FileOutputStream stream = new FileOutputStream(reportFile);
		stream.write(bytes);
		stream.flush();
        stream.close();
	}
}