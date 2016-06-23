package br.gov.saude.report;

import java.awt.image.BufferedImage;

public class ItemReport {
	private String sigla;
	private String desc;
	private BufferedImage imgStatus;
	private String situacao;
	private String responsavel;
	private String orgaoResp;
	private String oe;
	private String ciclo;
	private String nivel;
	private String ppa;
	private String pns;
	
	public String getPpa() {
		return ppa;
	}
	public void setPpa(String ppa) {
		this.ppa = ppa;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public BufferedImage getImgStatus() {
		return imgStatus;
	}
	public void setImgStatus(BufferedImage imgStatus) {
		this.imgStatus = imgStatus;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	public String getOrgaoResp() {
		return orgaoResp;
	}
	public void setOrgaoResp(String orgaoResp) {
		this.orgaoResp = orgaoResp;
	}
	public String getOe() {
		return oe;
	}
	public void setOe(String oe) {
		this.oe = oe;
	}
	public String getCiclo() {
		return ciclo;
	}
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public String getPns() {
		return pns;
	}
	public void setPns(String pns) {
		this.pns = pns;
	}
}