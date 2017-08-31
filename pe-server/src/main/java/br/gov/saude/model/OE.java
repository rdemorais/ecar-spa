package br.gov.saude.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity(name="OE")
@Table(name="tb_oe", schema="dbsitedemas")
@PrimaryKeyJoinColumn(name="cod_iett")
public class OE extends Iett {
	
	@Column(name="sigla_oe")
	private String sigla;
	
	@Column(name="nome_oe")
	private String descricao;
	
	@Column(name="ind_ativo")
	private String ativoOe;

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

	public String getAtivoOe() {
		return ativoOe;
	}

	public void setAtivoOe(String ativoOe) {
		this.ativoOe = ativoOe;
	}
	
	
}