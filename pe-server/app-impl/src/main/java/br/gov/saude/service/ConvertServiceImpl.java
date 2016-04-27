package br.gov.saude.service;

import java.util.ArrayList;
import java.util.List;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.OE;
import br.gov.saude.web.dto.OeDto;

public class ConvertServiceImpl implements ConvertService{
	
	public List<OeDto> convertListaOE(List<OE> oesDd) throws AkulaRuntimeException {
		List<OeDto> oes = new ArrayList<OeDto>();
		for (OE oe : oesDd) {
			oes.add(convertOE(oe));
		}
		return oes;
	}
	
	public OeDto convertOE(OE oe) throws AkulaRuntimeException {
		OeDto dto = new OeDto();
		
		dto.setId(oe.getId());
		dto.setSigla(oe.getSigla());
		dto.setDesc(oe.getDescricao());
		
		return dto;
	}
}