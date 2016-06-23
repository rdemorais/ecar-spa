package br.gov.saude.model.ecar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.gov.saude.model.UsuarioPermissaoMonitoramento;

@Entity(name="UsuarioPermissaoMonitoramento")
@Table(name="tb_item_estrut_usuario_iettus", schema="dbecardemas")
public class UsuarioPermissaoMonitoramentoImpl implements UsuarioPermissaoMonitoramento{
	@Id
	@Column(name="cod_iettus")
	private Long id;
	
	@Column(name="cod_tp_perm_iettus")
	private String tpPerm;
	
	@Column(name="cod_iett")
	private Long codIett;
	
	@Column(name="cod_usu")
	private Long codUsu;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTpPerm() {
		return tpPerm;
	}

	public void setTpPerm(String tpPerm) {
		this.tpPerm = tpPerm;
	}

	public Long getCodIett() {
		return codIett;
	}

	public void setCodIett(Long codIett) {
		this.codIett = codIett;
	}

	public Long getCodUsu() {
		return codUsu;
	}

	public void setCodUsu(Long codUsu) {
		this.codUsu = codUsu;
	}
}