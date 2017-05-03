package br.gov.saude.model.ecar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Exercicio")
@Table(name="tb_exercicio_exe", schema="dbecardemas")
public class Exercicio {
	
	@Id
	@Column(name="cod_exe")
	private Long id;
	
	@Column(name="descricao_exe")
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