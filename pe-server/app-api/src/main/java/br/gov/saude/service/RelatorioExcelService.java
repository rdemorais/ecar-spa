package br.gov.saude.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.web.dto.FiltroDto;

public interface RelatorioExcelService {
	public HSSFWorkbook exportarRelatorioExcel(FiltroDto filtro) throws AkulaRuntimeException ;
}