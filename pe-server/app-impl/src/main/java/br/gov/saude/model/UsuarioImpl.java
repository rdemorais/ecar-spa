package br.gov.saude.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Usuario")
@Table(name="tb_usuario_usu", schema="dbsitedemas")
public class UsuarioImpl implements Usuario{
	@Id
	@Column(name="co_usuario")
	private Long id;
	
	@Column(name="co_usuario_sentinela")
	private Long coUsuarioSentinela;
	
	@Column(name="ds_nome")
	private String nome;
	
	@Column(name="ds_email")
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCoUsuarioSentinela() {
		return coUsuarioSentinela;
	}

	public void setCoUsuarioSentinela(Long coUsuarioSentinela) {
		this.coUsuarioSentinela = coUsuarioSentinela;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}