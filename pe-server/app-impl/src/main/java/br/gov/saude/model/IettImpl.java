package br.gov.saude.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity(name="Iett")
@Table(name="tb_iett", schema="dbsitedemas2016")
@Inheritance(strategy = InheritanceType.JOINED)
public class IettImpl implements Iett{
	@Id
	@Column(name="cod_iett")
	private Long id;
	
	@Column(name="ds_estrutura")
	@Enumerated(EnumType.STRING)
	private Estrutura estrutura;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Estrutura getEstrutura() {
		return estrutura;
	}

	public void setEstrutura(Estrutura estrutura) {
		this.estrutura = estrutura;
	}
}