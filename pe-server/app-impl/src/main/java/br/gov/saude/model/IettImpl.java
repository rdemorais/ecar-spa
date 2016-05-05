package br.gov.saude.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name="Iett")
@Table(name="tb_iett", schema="dbsitedemas")
@Inheritance(strategy = InheritanceType.JOINED)
public class IettImpl implements Iett{
	@Id
	@Column(name="cod_iett")
	private Long id;
	
	@Column(name="ds_estrutura")
	@Enumerated(EnumType.STRING)
	private Estrutura estrutura;
	
	@OneToMany(targetEntity=EtiquetaImpl.class, cascade=CascadeType.REFRESH, fetch=FetchType.LAZY, mappedBy="iett")
	private Collection<Etiqueta> etiquetas = new ArrayList<Etiqueta>();

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

	public Collection<Etiqueta> getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(Collection<Etiqueta> etiquetas) {
		this.etiquetas = etiquetas;
	}
}