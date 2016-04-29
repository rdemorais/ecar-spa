package br.gov.saude.dao;

import java.util.List;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.Estrutura;
import br.gov.saude.model.Etiqueta;
import br.gov.saude.model.OE;
import br.gov.saude.web.dto.FiltroDto;
import br.gov.saude.web.dto.ItemDto;
import br.gov.saude.web.dto.StatusDto;

public interface EcarSiteDao extends Dao{
	public List<OE> loadOes() throws AkulaRuntimeException;
	public List<Etiqueta> loadEtiquetas() throws AkulaRuntimeException;
	public List<StatusDto> loadStatusCount(Long codExe) throws AkulaRuntimeException;
	public List<StatusDto> loadStatusCountNaoMonitorado(Long codExe) throws AkulaRuntimeException;
	public List<ItemDto> loadListaItens(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException;
}