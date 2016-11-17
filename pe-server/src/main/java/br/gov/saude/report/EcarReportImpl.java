package br.gov.saude.report;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.saude.exc.AkulaServiceRuntimeException;
import br.gov.saude.spi.file.EcarFileSystem;
import br.gov.saude.spi.report.EcarReport;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public class EcarReportImpl implements EcarReport{
	private static final Logger logger = LoggerFactory.getLogger(EcarReportImpl.class);
	
	@Autowired
	public EcarFileSystem ecarFileSystem;
	
	public BufferedImage getImageFromContext(String name) throws IOException {
		return ImageIO.read(ecarFileSystem.findFileInputStream(name));
	}
	
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
			
			report = (JasperReport) JRLoader.loadObject(ecarFileSystem.findFileInputStream(reportName));
			
			jrBean = new JRBeanCollectionDataSource(conteudo);
			
			reportPrint = JasperFillManager.fillReport(report, parametros,jrBean);
			
			return JasperExportManager.exportReportToPdf(reportPrint);
			
		} catch (JRException e) {
			throw new AkulaServiceRuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new AkulaServiceRuntimeException(e.getMessage(), e);
		}
	}
}