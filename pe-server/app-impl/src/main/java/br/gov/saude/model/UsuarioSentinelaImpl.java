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
	
	@Column(name="emailusuario")
	private String emailUsuario;
	
	@Column(name="nomeusuario")
	private String nomeUsuario;
	
	@Column(name="cpfusuario")
	private String cpf;

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

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}