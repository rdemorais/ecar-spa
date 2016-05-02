package br.gov.saude.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.gov.saude.report.EcarReport;

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
		byte[] bytes = ecarReport.generateReportPDF("pe-gerencial.jasper", new ArrayList<Object>());

		File reportFile = new File("/Users/rafaeldemorais/ecarReport.pdf");
		reportFile.createNewFile();
		
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(reportFile));
		stream.write(bytes);
		stream.flush();
        stream.close();
	}
}