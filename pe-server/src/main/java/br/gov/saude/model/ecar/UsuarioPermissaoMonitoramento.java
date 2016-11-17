package br.gov.saude.model.ecar;

public interface UsuarioPermissaoMonitoramento {
	public Long getId();
	public void setId(Long id);
	public String getTpPerm();
	public void setTpPerm(String tpPerm);
	public Long getCodIett();
	public void setCodIett(Long codIett);
	public Long getCodUsu();
	public void setCodUsu(Long codUsu);
}