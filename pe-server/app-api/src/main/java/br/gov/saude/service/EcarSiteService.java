package br.gov.saude.service;

import java.util.List;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.web.dto.OeDto;

public interface EcarSiteService {
	public List<OeDto> listaOes() throws AkulaRuntimeException;
}