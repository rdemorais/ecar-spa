package br.gov.saude.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="Monitoramento")
@Table(name="tb_monitoramento", schema="dbsitedemas")
public class MonitoramentoImpl implements Monitoramento {
	@Id
	@Column(name="cod_monitoramento")
	private Long id;
	
	@Column(name="mes")
	private String mes;
	
	@Column(name="ano")
	private String ano;
	
	@Column(name="cod_exe")
	private Long exercicio;
	
	@Column(name="data_parecer")
	private Date dataParecer;
	
	@Column(name="parecer")
	private String parecer;
	
	@Column(name="ultimo_parecer")
	private String ultimoParecer;
	
	@Column(name="nao_monitorado")
	private String naoMonitorado;
	
	@Column(name="data_limite_parecer")
	private Date dataLimite;
	
	@Column(name="cod_arel")
	private Long codArel;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=IettImpl.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "cod_iett", referencedColumnName = "cod_iett")
	private Iett iett;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=UsuarioImpl.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "cod_usu", referencedColumnName = "co_usuario")
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=CorImpl.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "co_cor", referencedColumnName = "co_cor")
	private Cor cor;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=SituacaoImpl.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "co_situacao", referencedColumnName = "co_situacao")
	private Situacao situacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getExercicio() {
		return exercicio;
	}

	public void setExercicio(Long exercicio) {
		this.exercicio = exercicio;
	}

	public Date getDataParecer() {
		return dataParecer;
	}

	public void setDataParecer(Date dataParecer) {
		this.dataParecer = dataParecer;
	}

	public String getParecer() {
		return parecer;
	}

	public void setParecer(String parecer) {
		this.parecer = parecer;
	}

	public String getUltimoParecer() {
		return ultimoParecer;
	}

	public void setUltimoParecer(String ultimoParecer) {
		this.ultimoParecer = ultimoParecer;
	}

	public String getNaoMonitorado() {
		return naoMonitorado;
	}

	public void setNaoMonitorado(String naoMonitorado) {
		this.naoMonitorado = naoMonitorado;
	}

	public Iett getIett() {
		return iett;
	}

	public void setIett(Iett iett) {
		this.iett = iett;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataLimite() {
		return dataLimite;
	}

	public void setDataLimite(Date dataLimite) {
		this.dataLimite = dataLimite;
	}

	public Long getCodArel() {
		return codArel;
	}

	public void setCodArel(Long codArel) {
		this.codArel = codArel;
	}

	public Cor getCor() {
		return cor;
	}

	public void setCor(Cor cor) {
		this.cor = cor;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	
}