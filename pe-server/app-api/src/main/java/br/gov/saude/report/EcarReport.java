package br.gov.saude.report;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import br.gov.saude.exc.AkulaServiceRuntimeException;

public interface EcarReport {
	public byte[] generateReportPDF(String reportName, List<Object> conteudo) throws AkulaServiceRuntimeException;
	public byte[] generateReportPDF(String reportName, Map<String, Object> parametros, List<Object> conteudo) throws AkulaServiceRuntimeException;
}