package br.gov.saude.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity(name="OE")
@Table(name="tb_oe", schema="dbsitedemas2016")
@PrimaryKeyJoinColumn(name="cod_iett")
public class OEImpl extends IettImpl implements OE{
	
	@Column(name="sigla_oe")
	private String sigla;
	
	@Column(name="nome_oe")
	private String descricao;

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