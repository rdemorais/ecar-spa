package br.gov.saude.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.report.ItemExcel;
import br.gov.saude.report.SecretariaExcel;
import br.gov.saude.service.dto.FiltroDto;
import br.gov.saude.spi.file.EcarFileSystem;
import br.gov.saude.spi.service.RelatorioExcelService;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

public class RelatorioExcelServiceImpl implements RelatorioExcelService {
	
	@Autowired
	private EcarFileSystem ecarFileSystem;
	
	public HSSFWorkbook exportarRelatorioExcel(FiltroDto filtro) throws AkulaRuntimeException {
		try {
			Map<String, Object> beans = new HashMap<String, Object>();
			List<String> sheetNames = new ArrayList<String>();
			List<SecretariaExcel> secretarias = new ArrayList<SecretariaExcel>();
			List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
			InputStream is = ecarFileSystem.findFileInputStream("relatorio-excel-template.xls");
			
			SecretariaExcel sec = new SecretariaExcel("SE");
			SecretariaExcel sec2 = new SecretariaExcel("SAS");

			ItemExcel mi = new ItemExcel("OE 01", "M 01", "Meta para teste", "03Z4", "Em andamento", "Rafael de Morais",
					"rdemorais.freitas@gmail.com", "SE");
			ItemExcel pi = new ItemExcel("OE 01", "01", "Produto interno para teste", "", "Em andamento",
					"Rafael de Morais", "rdemorais.freitas@gmail.com", "SE");
			ItemExcel atv = new ItemExcel("OE 01", "01", "Atividade para teste", "", "Em andamento", "Rafael de Morais",
					"rdemorais.freitas@gmail.com", "SE");

			mi.addItem(pi);
			pi.addItem(atv);

			sec.addItem(mi);
			sec2.addItem(mi);
			
			secretarias.add(sec);
			secretarias.add(sec2);
			Map<String, Object> map;
			for (SecretariaExcel secretariaExcel : secretarias) {
				sheetNames.add(secretariaExcel.getNome());
				map = new HashMap<String, Object>();
				map.put("mis", secretariaExcel.getItens());
				
				maps.add(map);
			}
			XLSTransformer transformer = new XLSTransformer();
			return (HSSFWorkbook) transformer.transformMultipleSheetsList(is, maps, sheetNames, "map", beans, 0);
			
		} catch (IOException e) {
			throw new AkulaRuntimeException(e.getMessage(), e);
		} catch (ParsePropertyException e) {
			throw new AkulaRuntimeException(e.getMessage(), e);
		} catch (InvalidFormatException e) {
			throw new AkulaRuntimeException(e.getMessage(), e);
		}
	}
}