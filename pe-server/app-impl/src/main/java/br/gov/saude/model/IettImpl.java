package br.gov.saude.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity(name="Iett")
@Table(name="tb_iett", schema="dbsitedemas2016")
@Inheritance(strategy = InheritanceType.JOINED)
public class IettImpl {
	@Id
	@Column(name="cod_iett")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}