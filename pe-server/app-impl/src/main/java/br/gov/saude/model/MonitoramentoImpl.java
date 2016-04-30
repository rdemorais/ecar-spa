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
@Table(name="tb_monitoramento", schema="dbsitedemas2016")
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
	
	@Column(name="cod_cor")
	private Long codCor;
	
	@Column(name="nome_cor")
	private String nomeCor;
	
	@Column(name="significado_cor")
	private String significadoCor;
	
	@Column(name="data_parecer")
	private Date dataParecer;
	
	@Column(name="cod_sit")
	private Long codSit;
	
	@Column(name="descricao_sit")
	private String descricaoSit;

	@Column(name="parecer")
	private String parecer;
	
	@Column(name="ultimo_parecer")
	private String ultimoParecer;
	
	@Column(name="nao_monitorado")
	private String naoMonitorado;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=IettImpl.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "cod_iett", referencedColumnName = "cod_iett")
	private Iett iett;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=UsuarioImpl.class, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "cod_usu", referencedColumnName = "co_usuario")
	private Usuario usuario;

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

	public Long getCodCor() {
		return codCor;
	}

	public void setCodCor(Long codCor) {
		this.codCor = codCor;
	}

	public String getNomeCor() {
		return nomeCor;
	}

	public void setNomeCor(String nomeCor) {
		this.nomeCor = nomeCor;
	}

	public String getSignificadoCor() {
		return significadoCor;
	}

	public void setSignificadoCor(String significadoCor) {
		this.significadoCor = significadoCor;
	}

	public Date getDataParecer() {
		return dataParecer;
	}

	public void setDataParecer(Date dataParecer) {
		this.dataParecer = dataParecer;
	}

	public Long getCodSit() {
		return codSit;
	}

	public void setCodSit(Long codSit) {
		this.codSit = codSit;
	}

	public String getDescricaoSit() {
		return descricaoSit;
	}

	public void setDescricaoSit(String descricaoSit) {
		this.descricaoSit = descricaoSit;
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
	
}