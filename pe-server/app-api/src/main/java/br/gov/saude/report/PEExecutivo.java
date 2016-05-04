package br.gov.saude.report;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PEExecutivo {
	
	private String exercicio;
	private String sigla;
	private String desc;
	private BufferedImage imgStatus;
	private String situacao;
	private String responsavel;
	private String orgaoResp;
	private String oe;
	private String ciclo;
	private String nivel;
	private String parecer;
	
	private List<ItemReport> listaItens = new ArrayList<ItemReport>();
	
	public String getExercicio() {
		return exercicio;
	}

	public void setExercicio(String exercicio) {
		this.exercicio = exercicio;
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

	public List<ItemReport> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<ItemReport> listaItens) {
		this.listaItens = listaItens;
	}

	public String getParecer() {
		return parecer;
	}

	public void setParecer(String parecer) {
		this.parecer = parecer;
	}
	
}