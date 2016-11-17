package br.gov.saude.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Situacao")
@Table(name="tb_situacao", schema="dbsitedemas")
public class Situacao {
	@Id
	@Column(name="co_situacao")
	private Long id;
	
	@Column(name="ds_situacao")
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}