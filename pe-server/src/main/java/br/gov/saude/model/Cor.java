package br.gov.saude.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Cor")
@Table(name="tb_cor", schema="dbsitedemas")
public class Cor {
	
	@Id
	@Column(name="co_cor")
	private Long id;
	
	@Column(name="no_cor")
	private String nome;
	
	@Column(name="ds_significado_cor")
	private String significado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSignificado() {
		return significado;
	}

	public void setSignificado(String significado) {
		this.significado = significado;
	}
}