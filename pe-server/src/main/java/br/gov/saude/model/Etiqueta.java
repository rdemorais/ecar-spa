package br.gov.saude.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="Etiqueta")
@Table(name="tb_etiqueta", schema="dbsitedemas")
public class Etiqueta {
	@Id
	@Column(name="cod_etiqueta")
	private Long id;
	
	@Column(name="nome_etiqueta")
	private String nome;
	
	@Column(name="nome_etiqueta_fonetico")
	private String nomeFonetico;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=Iett.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "cod_iett", referencedColumnName = "cod_iett")
	private Iett iett;
	
	public Etiqueta() {
	
	}

	public Etiqueta(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeFonetico() {
		return nomeFonetico;
	}

	public void setNomeFonetico(String nomeFonetico) {
		this.nomeFonetico = nomeFonetico;
	}

	public Iett getIett() {
		return iett;
	}

	public void setIett(Iett iett) {
		this.iett = iett;
	}
}