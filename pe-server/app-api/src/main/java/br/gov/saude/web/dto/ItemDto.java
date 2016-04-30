package br.gov.saude.web.dto;

import br.gov.saude.model.Estrutura;

public class ItemDto {
	private Long id;
	private String nomeCor;
	private String desc;
	private String situacao;
	private String nivel;
	private String orgaoResp;
	private String mes;
	private String ano;
	private String oe;
	private String oeShortName;
	
	private String sigla;
	private String parecer;
	
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
		this.mes = mes;
		this.ano = ano;
		this.oe = oe;
		this.oeShortName = oe.replaceAll("\\s+","").toLowerCase();
	}
	
	public ItemDto(Long id, String nomeCor, String desc, String situacao, Estrutura estrutura, String orgaoResp,
			String mes, String ano, String oe, String sigla, String parecer) {
		this(id, nomeCor, desc, situacao, estrutura, orgaoResp, mes, ano, oe);
		this.sigla = sigla;
		this.parecer = parecer;
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
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getParecer() {
		return parecer;
	}
	public void setParecer(String parecer) {
		this.parecer = parecer;
	}

	@Override
	public String toString() {
		return "ItemDto [id=" + id + ", nomeCor=" + nomeCor + ", desc=" + desc + ", situacao=" + situacao + ", nivel="
				+ nivel + ", orgaoResp=" + orgaoResp + ", mes=" + mes + ", ano=" + ano + ", oe=" + oe + ", oeShortName="
				+ oeShortName + ", sigla=" + sigla + ", parecer=" + parecer + "]";
	}
}