package br.gov.saude.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="Etiqueta")
@Table(name="tb_etiqueta", schema="dbsitedemas2016")
public class EtiquetaImpl {
	@Id
	@Column(name="cod_etiqueta")
	private Long id;
	
	
}