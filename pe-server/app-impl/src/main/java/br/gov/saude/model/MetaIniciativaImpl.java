package br.gov.saude.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity(name="MetaIniciativa")
@Table(name="tb_meta_iniciativa", schema="dbsitedemas")
@PrimaryKeyJoinColumn(name="cod_iett")
public class MetaIniciativaImpl extends IettImpl implements MetaIniciativa{
	
	@Column(name="sigla_mi")
	private String siglaMi;
	
	@Column(name="nome_mi")
	private String nomeMi;
	
	@Column(name="data_inicio")
	private Date dataInicio;
	
	@Column(name="data_termino")
	private Date dataTermino;
	
	@Column(name="cod_org")
	private Long codOrg;
	
	@Column(name="sigla_org")
	private String siglaOrgMi;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=UsuarioImpl.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "cod_usu", referencedColumnName = "co_usuario")
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=OEImpl.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "cod_oe", referencedColumnName = "cod_iett")
	private OE oe;

	public String getSiglaMi() {
		return siglaMi;
	}

	public void setSiglaMi(String siglaMi) {
		this.siglaMi = siglaMi;
	}
	
	public String getNomeMi() {
		return nomeMi;
	}

	public void setNomeMi(String nomeMi) {
		this.nomeMi = nomeMi;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public Long getCodOrg() {
		return codOrg;
	}

	public void setCodOrg(Long codOrg) {
		this.codOrg = codOrg;
	}

	public String getSiglaOrgMi() {
		return siglaOrgMi;
	}

	public void setSiglaOrgMi(String siglaOrgMi) {
		this.siglaOrgMi = siglaOrgMi;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public OE getOe() {
		return oe;
	}

	public void setOe(OE oe) {
		this.oe = oe;
	}
}