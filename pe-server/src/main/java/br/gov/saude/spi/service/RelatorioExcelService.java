package br.gov.saude.spi.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.service.dto.FiltroDto;

public interface RelatorioExcelService {
	public HSSFWorkbook exportarRelatorioExcel(FiltroDto filtro) throws AkulaRuntimeException ;
}