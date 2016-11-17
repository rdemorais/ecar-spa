package br.gov.saude.model.ecar;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="AcompanhamentoAref")
@Table(name="tb_acomp_referencia_aref", schema="dbecardemas")
public class AcompanhamentoAref {
	@Id
	@Column(name="cod_aref")
	private Long id;
	
	@Column(name="data_inicio_aref")
	private Date dataInicioAref;
	
	@Column(name="mes_aref")
	private String mes;
	
	@Column(name="ano_aref")
	private String ano;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataInicioAref() {
		return dataInicioAref;
	}

	public void setDataInicioAref(Date dataInicioAref) {
		this.dataInicioAref = dataInicioAref;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}
}