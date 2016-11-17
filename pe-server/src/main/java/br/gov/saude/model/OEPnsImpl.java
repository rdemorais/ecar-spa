package br.gov.saude.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="OEPns")
@Table(name="tb_sis_atributo_satb", schema="dbecardemas")
public class OEPnsImpl implements OEPns {
	
	@Id
	@Column(name="cod_satb")
	private Long id;
	
	@Column(name="cod_sga")
	private Long codSga;
	
	@Column(name="descricao_satb")
	private String nome;
	
	@Column(name="atrib_inf_comp_satb")
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCodSga() {
		return codSga;
	}

	public void setCodSga(Long codSga) {
		this.codSga = codSga;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}