package br.gov.saude.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity(name="MetaIniciativa")
@Table(name="tb_meta_iniciativa", schema="dbsitedemas")
@PrimaryKeyJoinColumn(name="cod_iett")
public class MetaIniciativa extends Iett {
	
	@Column(name="sigla_mi")
	private String siglaMi;
	
	@Column(name="nome_mi")
	private String nomeMi;
	
	@Column(name="data_inicio")
	private Date dataInicio;
	
	@Column(name="data_termino")
	private Date dataTermino;
	
	@Column(name="cod_org")
	private Long codOrgMi;
	
	@Column(name="sigla_org")
	private String siglaOrgMi;
	
	@Column(name="ds_ppa_mi")
	private String codPpa;
	
	@Column(name="co_oe_pns")
	private Long coOePns;
	
	@Column(name="ds_oe_pns")
	private String oePns;
	
	@Column(name="ind_ativo")
	private String ativoMi;
	
	//Novos campos - Demanda SPO
	@Column(name="ds_produto")
	private String descProduto;
	
	@Column(name="ds_especificacao_produto")
	private String descEspecificacaoProduto;
	
	//Unidade de medida
	
	@Column(name="nu_linha_base")
	private Double linhaBase;
	
	@Column(name="nu_ano_linha_base")
	private Integer anoLinhaBase;
	
	@Column(name="dt_data_apuracao")
	private Date dataApuracao;
	
	@Column(name="ds_metodo_apuracao")
	private String metodoApuracao;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ds_polaridade_indicador")
	private Polaridade polaridadeIndicador;
	
	@Column(name="ds_periodicidade")
	private Periodicidade periodicidade;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=Usuario.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "cod_usu", referencedColumnName = "co_usuario")
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=OE.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "cod_oe", referencedColumnName = "cod_iett")
	private OE oe;
	
	public Long getCoOePns() {
		return coOePns;
	}

	public void setCoOePns(Long coOePns) {
		this.coOePns = coOePns;
	}

	public String getOePns() {
		return oePns;
	}

	public void setOePns(String oePns) {
		this.oePns = oePns;
	}

	public String getCodPpa() {
		return codPpa;
	}

	public void setCodPpa(String codPpa) {
		this.codPpa = codPpa;
	}

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

	public Long getCodOrgMi() {
		return codOrgMi;
	}

	public void setCodOrgMi(Long codOrgMi) {
		this.codOrgMi = codOrgMi;
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

	public String getAtivoMi() {
		return ativoMi;
	}

	public void setAtivoMi(String ativoMi) {
		this.ativoMi = ativoMi;
	}

	public String getDescProduto() {
		return descProduto;
	}

	public void setDescProduto(String descProduto) {
		this.descProduto = descProduto;
	}

	public String getDescEspecificacaoProduto() {
		return descEspecificacaoProduto;
	}

	public void setDescEspecificacaoProduto(String descEspecificacaoProduto) {
		this.descEspecificacaoProduto = descEspecificacaoProduto;
	}

	public Double getLinhaBase() {
		return linhaBase;
	}

	public void setLinhaBase(Double linhaBase) {
		this.linhaBase = linhaBase;
	}

	public Integer getAnoLinhaBase() {
		return anoLinhaBase;
	}

	public void setAnoLinhaBase(Integer anoLinhaBase) {
		this.anoLinhaBase = anoLinhaBase;
	}

	public Date getDataApuracao() {
		return dataApuracao;
	}

	public void setDataApuracao(Date dataApuracao) {
		this.dataApuracao = dataApuracao;
	}

	public String getMetodoApuracao() {
		return metodoApuracao;
	}

	public void setMetodoApuracao(String metodoApuracao) {
		this.metodoApuracao = metodoApuracao;
	}

	public Polaridade getPolaridadeIndicador() {
		return polaridadeIndicador;
	}

	public void setPolaridadeIndicador(Polaridade polaridadeIndicador) {
		this.polaridadeIndicador = polaridadeIndicador;
	}

	public Periodicidade getPeriodicidade() {
		return periodicidade;
	}

	public void setPeriodicidade(Periodicidade periodicidade) {
		this.periodicidade = periodicidade;
	}

	
}