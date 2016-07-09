package br.gov.saude.model;

import java.util.Date;

public interface Monitoramento {
	public Long getId();
	public void setId(Long id);
	public String getMes();
	public void setMes(String mes);
	public String getAno();
	public void setAno(String ano);
	public Long getExercicio();
	public void setExercicio(Long exercicio);
	public Date getDataParecer();
	public void setDataParecer(Date dataParecer);
	public String getParecer();
	public void setParecer(String parecer);
	public String getUltimoParecer();
	public void setUltimoParecer(String ultimoParecer);
	public String getNaoMonitorado();
	public void setNaoMonitorado(String naoMonitorado);
	public Iett getIett();
	public void setIett(Iett iett);
	public Usuario getUsuario();
	public void setUsuario(Usuario usuario);
	public Date getDataLimite();
	public void setDataLimite(Date dataLimite);
	public Long getCodArel();
	public void setCodArel(Long codArel);
	public Cor getCor();
	public void setCor(Cor cor);
	public Situacao getSituacao();
	public void setSituacao(Situacao situacao);
}