package br.gov.saude.report;

import java.util.ArrayList;
import java.util.List;

public class ItemExcel {
	private String oe;
	private String sigla;
	private String desc;
	private String ppa;
	private String situacao;
	private String responsavel;
	private String email;
	private String siglaOrg;
	private List<ItemExcel> itens = new ArrayList<ItemExcel>();
	
	public ItemExcel() {
	
	}
	
	public ItemExcel(String oe, String sigla, String desc, String ppa, String situacao, String responsavel,
			String email, String siglaOrg) {
		this.oe = oe;
		this.sigla = sigla;
		this.desc = desc;
		this.ppa = ppa;
		this.situacao = situacao;
		this.responsavel = responsavel;
		this.email = email;
		this.siglaOrg = siglaOrg;
	}

	public String getOe() {
		return oe;
	}
	public void setOe(String oe) {
		this.oe = oe;
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
	public String getPpa() {
		return ppa;
	}
	public void setPpa(String ppa) {
		this.ppa = ppa;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSiglaOrg() {
		return siglaOrg;
	}
	public void setSiglaOrg(String siglaOrg) {
		this.siglaOrg = siglaOrg;
	}
	public List<ItemExcel> getItens() {
		return itens;
	}
	public void setItens(List<ItemExcel> itens) {
		this.itens = itens;
	}
	public void addItem(ItemExcel item) {
		this.itens.add(item);
	}
}