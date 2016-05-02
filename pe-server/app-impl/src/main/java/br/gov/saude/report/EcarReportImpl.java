package br.gov.saude.report;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

import br.gov.saude.exc.AkulaServiceRuntimeException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public class EcarReportImpl implements EcarReport, ResourceLoaderAware{
	private static final Logger logger = LoggerFactory.getLogger(EcarReportImpl.class);
	
	private ResourceLoader resourceLoader;
	
	private static String ECAR_REPORT_DIR = "classpath:reports/eCarPEReports/";
	
	public byte[] generateReportPDF(String reportName, List<Object> conteudo)
			throws AkulaServiceRuntimeException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		return generateReportPDF(reportName, parametros, conteudo);
	}
	
	public byte[] generateReportPDF(String reportName, Map<String, Object> parametros, List<Object> conteudo)
			throws AkulaServiceRuntimeException {
		try {
			logger.debug("Gerando relatorio: " + reportName);
			JasperReport report;
			JasperPrint reportPrint;
			JRBeanCollectionDataSource jrBean;
			
			report = (JasperReport) JRLoader.loadObject(findFileInputStream(ECAR_REPORT_DIR, reportName));
			
			jrBean = new JRBeanCollectionDataSource(conteudo);
			
			reportPrint = JasperFillManager.fillReport(report, parametros,jrBean);
			
			return JasperExportManager.exportReportToPdf(reportPrint);
			
		} catch (JRException e) {
			throw new AkulaServiceRuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new AkulaServiceRuntimeException(e.getMessage(), e);
		}
	}
	
	private InputStream findFileInputStream(String local, String fileName) throws IOException {
		return resourceLoader.getResource(local+fileName).getInputStream();
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
}