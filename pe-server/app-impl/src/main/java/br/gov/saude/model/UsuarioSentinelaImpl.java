package br.gov.saude.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="UsuarioSentinela")
@Table(name="usuario", schema="dbsentdemas")
public class UsuarioSentinelaImpl implements UsuarioSentinela {
	@Id
	@Column(name="codusuario")
	private Long id;
	
	@Column(name="loginusuario")
	private String loginusuario;
	
	@Column(name="senhausuario")
	private String senhausuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginusuario() {
		return loginusuario;
	}

	public void setLoginusuario(String loginusuario) {
		this.loginusuario = loginusuario;
	}

	public String getSenhausuario() {
		return senhausuario;
	}

	public void setSenhausuario(String senhausuario) {
		this.senhausuario = senhausuario;
	}
}