package br.gov.saude.service;

import java.util.List;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.web.dto.EtiquetaDto;
import br.gov.saude.web.dto.OeDto;
import br.gov.saude.web.dto.StatusDto;

public interface EcarSiteService {
	public List<OeDto> listaOes() throws AkulaRuntimeException;
	public List<EtiquetaDto> listaEtiquetas() throws AkulaRuntimeException;
	public List<StatusDto> loadStatusCount() throws AkulaRuntimeException;
}