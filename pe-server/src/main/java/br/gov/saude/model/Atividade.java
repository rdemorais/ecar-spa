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

@Entity(name="Atividade")
@Table(name="tb_atividade", schema="dbsitedemas")
@PrimaryKeyJoinColumn(name="cod_iett")
public class Atividade extends Iett{
	
	@Column(name="sigla_atv")
	private String siglaAtv;
	
	@Column(name="nome_atv")
	private String nomeAtv;
	
	@Column(name="data_inicio")
	private Date dataInicio;
	
	@Column(name="data_termino")
	private Date dataTermino;
	
	@Column(name="cod_org")
	private Long codOrg;
	
	@Column(name="sigla_org")
	private String siglaOrgAtv;
	
	@Column(name="ind_ativo")
	private String ativoAtv;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=Usuario.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "cod_usu", referencedColumnName = "co_usuario")
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=ProdutoIntermediario.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "cod_pi", referencedColumnName = "cod_iett")
	private ProdutoIntermediario produtoIntermediario;

	public String getSiglaAtv() {
		return siglaAtv;
	}

	public void setSiglaAtv(String siglaAtv) {
		this.siglaAtv = siglaAtv;
	}

	public String getNomeAtv() {
		return nomeAtv;
	}

	public void setNomeAtv(String nomeAtv) {
		this.nomeAtv = nomeAtv;
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
	
	public String getSiglaOrgAtv() {
		return siglaOrgAtv;
	}

	public void setSiglaOrgAtv(String siglaOrgAtv) {
		this.siglaOrgAtv = siglaOrgAtv;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public ProdutoIntermediario getProdutoIntermediario() {
		return produtoIntermediario;
	}

	public void setProdutoIntermediario(ProdutoIntermediario produtoIntermediario) {
		this.produtoIntermediario = produtoIntermediario;
	}

	public String getAtivoAtv() {
		return ativoAtv;
	}

	public void setAtivoAtv(String ativoAtv) {
		this.ativoAtv = ativoAtv;
	}

}