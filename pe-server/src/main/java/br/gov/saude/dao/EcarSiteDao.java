package br.gov.saude.dao;

import java.util.List;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.Estrutura;
import br.gov.saude.model.Etiqueta;
import br.gov.saude.model.Monitoramento;
import br.gov.saude.model.OE;
import br.gov.saude.model.Usuario;
import br.gov.saude.model.ecar.UsuarioPermissaoMonitoramento;
import br.gov.saude.service.dto.CorDto;
import br.gov.saude.service.dto.FiltroDto;
import br.gov.saude.service.dto.ItemDto;
import br.gov.saude.service.dto.OeDto;
import br.gov.saude.service.dto.SecretariaDto;
import br.gov.saude.service.dto.SituacaoDto;
import br.gov.saude.service.dto.StatusDto;

public interface EcarSiteDao extends Dao{
	public void updateUltimoParecerENaoMonitorado(Long codIett, Long codArel) throws AkulaRuntimeException;
	public Monitoramento loadMonitoramento(Long codIett, String mes, String ano) throws AkulaRuntimeException;
	public Monitoramento loadMonitoramento(Long codArel) throws AkulaRuntimeException;
	public List<CorDto> listCor() throws AkulaRuntimeException;
	public List<SituacaoDto> listSituacao() throws AkulaRuntimeException;
	public UsuarioPermissaoMonitoramento loadUsuarioPermissaoMonitoramento(Long codUsu, Long codIett) throws AkulaRuntimeException;
	public Usuario loadUsuario(Long id) throws AkulaRuntimeException;
	public Usuario loadUsuario(String email) throws AkulaRuntimeException;
	public List<OeDto> listOEPns() throws AkulaRuntimeException;
	public List<OE> loadOes() throws AkulaRuntimeException;
	public List<Etiqueta> loadEtiquetas() throws AkulaRuntimeException;
	public List<StatusDto> loadStatusCount(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException;
	public List<SecretariaDto> loadSecretarias() throws AkulaRuntimeException;
	public StatusDto loadStatusCountNaoMonitorado(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException;
	public ItemDto loadItem(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException;
	public List<ItemDto> loadListaItens(FiltroDto filtro, Estrutura estrutura, boolean nMonitorado, boolean comParecer) throws AkulaRuntimeException;
}