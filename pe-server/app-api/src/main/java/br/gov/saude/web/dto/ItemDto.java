package br.gov.saude.web.dto;

import br.gov.saude.model.Estrutura;

public class ItemDto {
	private Long id;
	private String nomeCor;
	private String desc;
	private String situacao;
	private String nivel;
	private String orgaoResp;
	private String ciclo;
	private String oe;
	private String oeShortName;
	
	public ItemDto() {
		
	}
	
	public ItemDto(Long id, String nomeCor, String desc, String situacao, Estrutura estrutura, String orgaoResp,
			String mes, String ano, String oe) {
		super();
		this.id = id;
		this.nomeCor = nomeCor;
		this.desc = desc;
		this.situacao = situacao;
		this.nivel = estrutura.name().toLowerCase();
		this.orgaoResp = orgaoResp;
		this.ciclo = mes + "/" + ano;
		this.oe = oe;
		this.oeShortName = oe.replaceAll("\\s+","").toLowerCase();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeCor() {
		return nomeCor;
	}
	public void setNomeCor(String nomeCor) {
		this.nomeCor = nomeCor;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public String getOrgaoResp() {
		return orgaoResp;
	}
	public void setOrgaoResp(String orgaoResp) {
		this.orgaoResp = orgaoResp;
	}
	public String getCiclo() {
		return ciclo;
	}
	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}
	public String getOe() {
		return oe;
	}
	public void setOe(String oe) {
		this.oe = oe;
	}
	public String getOeShortName() {
		return oeShortName;
	}
	public void setOeShortName(String oeShortName) {
		this.oeShortName = oeShortName;
	}
	@Override
	public String toString() {
		return "ItemDto [id=" + id + ", nomeCor=" + nomeCor + ", desc=" + desc + ", situacao=" + situacao
				+ ", nivel=" + nivel + ", orgaoResp=" + orgaoResp + ", ciclo=" + ciclo + ", oe=" + oe
				+ ", oeShortName=" + oeShortName + "]";
	}
	
}