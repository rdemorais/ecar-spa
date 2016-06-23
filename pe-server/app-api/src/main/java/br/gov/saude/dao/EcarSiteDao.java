package br.gov.saude.dao;

import java.util.List;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.Estrutura;
import br.gov.saude.model.Etiqueta;
import br.gov.saude.model.OE;
import br.gov.saude.model.UsuarioPermissaoMonitoramento;
import br.gov.saude.web.dto.FiltroDto;
import br.gov.saude.web.dto.ItemDto;
import br.gov.saude.web.dto.OeDto;
import br.gov.saude.web.dto.SecretariaDto;
import br.gov.saude.web.dto.StatusDto;

public interface EcarSiteDao extends Dao{
	public UsuarioPermissaoMonitoramento loadUsuarioPermissaoMonitoramento(Long codUsu, Long codIett) throws AkulaRuntimeException;
	public List<OeDto> listOEPns() throws AkulaRuntimeException;
	public List<OE> loadOes() throws AkulaRuntimeException;
	public List<Etiqueta> loadEtiquetas() throws AkulaRuntimeException;
	public List<StatusDto> loadStatusCount(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException;
	public List<SecretariaDto> loadSecretarias() throws AkulaRuntimeException;
	public StatusDto loadStatusCountNaoMonitorado(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException;
	public ItemDto loadItem(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException;
	public List<ItemDto> loadListaItens(FiltroDto filtro, Estrutura estrutura, boolean nMonitorado) throws AkulaRuntimeException;
}