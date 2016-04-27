package br.gov.saude.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="OE")
@Table(name="tb_oe", schema="dbsitedemas2016")
public class OEImpl implements OE{
	@Id
	@Column(name="cod_oe")
	private Long id;
	
	@Column(name="sigla_oe")
	private String sigla;
	
	@Column(name="nome_oe")
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}