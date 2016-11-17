package br.gov.saude.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.gov.saude.service.dto.FiltroDto;
import br.gov.saude.spi.service.EcarSiteService;

@ContextConfiguration("/META-INF/ecarTest-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class EcarReportTest {
	
	private static final Logger logger = LoggerFactory.getLogger(EcarReportTest.class);
	
	@Autowired
	public EcarSiteService ecarSiteService;
	
	@Test
	public void gerarReportGerencialTest() throws IOException {
		logger.debug("Gerando relatorio...");
		
		FiltroDto filtro = new FiltroDto();
		filtro.setCodExe(1L);
		byte[] bytes = ecarSiteService.gerarRelatorioGerencial(filtro);
		
		File reportFile = new File("/Users/rafaeldemorais/ecarReport.pdf");
		reportFile.createNewFile();
		
		FileOutputStream stream = new FileOutputStream(reportFile);
		stream.write(bytes);
		stream.flush();
        stream.close();
	}
	
	@Test
	public void gerarReportExecutivoTest() throws IOException {
		logger.debug("Gerando relatorio executivo...");
		
		FiltroDto filtro = new FiltroDto();
		filtro.setCodExe(1L);
		filtro.setCodIett(44L);
		//filtro.setCodIett(321L);
		//filtro.setCodIett(319L);
		//filtro.setCodIett(322L);
		filtro.setNivel("meta");
		
		byte[] bytes = ecarSiteService.gerarRelatorioExecutivo(filtro);
		
		File reportFile = new File("/Users/rafaeldemorais/ecarReportExecutivo.pdf");
		reportFile.createNewFile();
		
		FileOutputStream stream = new FileOutputStream(reportFile);
		stream.write(bytes);
		stream.flush();
        stream.close();
	}
	
	@Test
	public void testRelatorioExcel() throws IOException {
		FiltroDto filtro = new FiltroDto();
		byte[] bytes = ecarSiteService.gerarRelatorioExcel(filtro);
		
		File reportFile = new File("/Users/rafaeldemorais/ecarReportExcel.xls");
		reportFile.createNewFile();
		
		FileOutputStream stream = new FileOutputStream(reportFile);
		stream.write(bytes);
		stream.flush();
        stream.close();
	}
}