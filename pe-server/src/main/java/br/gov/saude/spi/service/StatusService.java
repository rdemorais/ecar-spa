package br.gov.saude.spi.service;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.service.dto.FiltroDto;
import br.gov.saude.service.dto.StatusBarDto;

public interface StatusService {
	public StatusBarDto loadStatusBar(FiltroDto filtro) throws AkulaRuntimeException;
}