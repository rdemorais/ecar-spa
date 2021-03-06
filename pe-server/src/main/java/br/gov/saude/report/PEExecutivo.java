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
	private String estruturaProduto;
	private String estruturaSuperior;
	private String ppa;
	private String pns;
	
	private String siglaMi;
	private String siglaPi;
	private String siglaAtv;
	
	private List<ItemReport> listaItens = new ArrayList<ItemReport>();
	
	public String getPpa() {
		return ppa;
	}

	public void setPpa(String ppa) {
		this.ppa = ppa;
	}

	public String getEstruturaSuperior() {
		return estruturaSuperior;
	}

	public void setEstruturaSuperior(String estruturaSuperior) {
		this.estruturaSuperior = estruturaSuperior;
	}

	public String getEstruturaProduto() {
		return estruturaProduto;
	}

	public void setEstruturaProduto(String estruturaProduto) {
		this.estruturaProduto = estruturaProduto;
	}

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

	public String getSiglaMi() {
		return siglaMi;
	}

	public void setSiglaMi(String siglaMi) {
		this.siglaMi = siglaMi;
	}

	public String getSiglaPi() {
		return siglaPi;
	}

	public void setSiglaPi(String siglaPi) {
		this.siglaPi = siglaPi;
	}

	public String getSiglaAtv() {
		return siglaAtv;
	}

	public void setSiglaAtv(String siglaAtv) {
		this.siglaAtv = siglaAtv;
	}

	public String getPns() {
		return pns;
	}

	public void setPns(String pns) {
		this.pns = pns;
	}
}