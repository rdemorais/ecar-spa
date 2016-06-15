package br.gov.saude.service;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.web.dto.FiltroDto;
import br.gov.saude.web.dto.StatusBarDto;

public interface StatusService {
	public StatusBarDto loadStatusBar(FiltroDto filtro) throws AkulaRuntimeException;
}