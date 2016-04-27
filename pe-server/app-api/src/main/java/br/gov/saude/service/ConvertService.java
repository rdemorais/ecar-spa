package br.gov.saude.service;

import java.util.List;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.OE;
import br.gov.saude.web.dto.OeDto;

public interface ConvertService {
	public List<OeDto> convertListaOE(List<OE> oesDd) throws AkulaRuntimeException;
	
	public OeDto convertOE(OE oe) throws AkulaRuntimeException;
}