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

@Entity(name="ProdutoIntermediario")
@Table(name="tb_prod_inter", schema="dbsitedemas")
@PrimaryKeyJoinColumn(name="cod_iett")
public class ProdutoIntermediarioImpl extends IettImpl implements ProdutoIntermediario{
	
	@Column(name="sigla_pi")
	private String siglaPi;
	
	@Column(name="nome_pi")
	private String nomePi;
	
	@Column(name="data_inicio")
	private Date dataInicio;
	
	@Column(name="data_termino")
	private Date dataTermino;
	
	@Column(name="cod_org")
	private Long codOrg;
	
	@Column(name="sigla_org")
	private String siglaOrgPi;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=UsuarioImpl.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "cod_usu", referencedColumnName = "co_usuario")
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=MetaIniciativaImpl.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "cod_mi", referencedColumnName = "cod_iett")
	private MetaIniciativa metaIniciativa;

	public String getSiglaPi() {
		return siglaPi;
	}

	public void setSiglaPi(String siglaPi) {
		this.siglaPi = siglaPi;
	}
	
	public String getNomePi() {
		return nomePi;
	}

	public void setNomePi(String nomePi) {
		this.nomePi = nomePi;
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
	
	public String getSiglaOrgPi() {
		return siglaOrgPi;
	}

	public void setSiglaOrgPi(String siglaOrgPi) {
		this.siglaOrgPi = siglaOrgPi;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public MetaIniciativa getMetaIniciativa() {
		return metaIniciativa;
	}

	public void setMetaIniciativa(MetaIniciativa metaIniciativa) {
		this.metaIniciativa = metaIniciativa;
	}
}