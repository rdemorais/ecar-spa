package br.gov.saude.web.dto;

import br.gov.saude.model.Estrutura;

public class ItemDto implements Comparable<ItemDto>{
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
	private String responsavel;
	private String estruturaSuperior;
	
	private String siglaMi;
	private String siglaPi;
	private String siglaAtv;
	private String parecer;
	
	public ItemDto() {
		
	}
	
	public ItemDto(Long ind, Long id, String nomeCor, String situacao, Estrutura estrutura,
			String mes, String ano, String oe, String responsavel, String parecer, String siglaMi, String desc, String orgaoResp, Estrutura estruturaSuperior) {
		this(id, nomeCor, situacao, estrutura, mes, ano, oe, responsavel, siglaMi, desc, orgaoResp, estruturaSuperior);
		this.parecer = parecer;
	}
	
	public ItemDto(Long ind, Long id, String nomeCor, String situacao, Estrutura estrutura, String mes, String ano,
			 String oe, String responsavel, String parecer, String siglaMi, String siglaPi, String desc, String orgaoResp, Estrutura estruturaSuperior) {
		this(id, nomeCor, situacao, estrutura, mes, ano, oe, responsavel, siglaMi, siglaPi, desc, orgaoResp, estruturaSuperior);
		this.parecer = parecer;
	}
	
	public ItemDto(Long ind, Long id, String nomeCor, String situacao, Estrutura estrutura, String mes, String ano, String oe,
			 String responsavel, String parecer, String siglaMi, String siglaPi, String siglaAtv, String desc, String orgaoResp, Estrutura estruturaSuperior) {
		this(id, nomeCor, situacao, estrutura, mes, ano, oe, responsavel, siglaMi, siglaPi, siglaAtv, desc, orgaoResp, estruturaSuperior);
		this.parecer = parecer;
	}
	
	public ItemDto(Long id, String nomeCor, String situacao, Estrutura estrutura,
			String mes, String ano, String oe, String responsavel, String siglaMi, String desc, String orgaoResp, Estrutura estruturaSuperior) {
		super();
		this.id = id;
		this.nomeCor = nomeCor;
		this.siglaMi = siglaMi;
		this.desc = desc;
		this.situacao = situacao;
		this.nivel = estrutura.name().toLowerCase();
		this.orgaoResp = orgaoResp;
		this.mes = mes;
		this.ano = ano;
		this.oe = oe;
		this.oeShortName = oe.replaceAll("\\s+","").toLowerCase();
		this.responsavel = responsavel;
		this.estruturaSuperior = estruturaSuperior.name().toLowerCase();
	}
	
	public ItemDto(Long id, String nomeCor, String situacao, Estrutura estrutura,
			String mes, String ano, String oe, String responsavel, String siglaMi, String siglaPi, String desc, String orgaoResp, Estrutura estruturaSuperior) {
		super();
		this.id = id;
		this.nomeCor = nomeCor;
		this.siglaMi = siglaMi;
		this.siglaPi = siglaPi;
		this.desc = desc;
		this.situacao = situacao;
		this.nivel = estrutura.name().toLowerCase();
		this.orgaoResp = orgaoResp;
		this.mes = mes;
		this.ano = ano;
		this.oe = oe;
		this.oeShortName = oe.replaceAll("\\s+","").toLowerCase();
		this.responsavel = responsavel;
		this.estruturaSuperior = estruturaSuperior.name().toLowerCase();
	}
	
	public ItemDto(Long id, String nomeCor, String situacao, Estrutura estrutura, String mes, String ano,
			 String oe, String responsavel, String siglaMi, String siglaPi, String siglaAtv, String desc, String orgaoResp, Estrutura estruturaSuperior) {
		super();
		this.id = id;
		this.nomeCor = nomeCor;
		this.siglaMi = siglaMi;
		this.siglaPi = siglaPi;
		this.siglaAtv = siglaAtv;
		this.desc = desc;
		this.situacao = situacao;
		this.nivel = estrutura.name().toLowerCase();
		this.orgaoResp = orgaoResp;
		this.mes = mes;
		this.ano = ano;
		this.oe = oe;
		this.oeShortName = oe.replaceAll("\\s+","").toLowerCase();
		this.responsavel = responsavel;
		this.estruturaSuperior = estruturaSuperior.name().toLowerCase();
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
	
	public String getParecer() {
		return parecer;
	}
	public void setParecer(String parecer) {
		this.parecer = parecer;
	}
	
	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
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
	
	public String getEstruturaSuperior() {
		return estruturaSuperior;
	}

	public void setEstruturaSuperior(String estruturaSuperior) {
		this.estruturaSuperior = estruturaSuperior;
	}

	@Override
	public String toString() {
		return "ItemDto [oe=" + oe + ", siglaMi=" + siglaMi + ", siglaPi=" + siglaPi + ", siglaAtv=" + siglaAtv
				+ ", estruturaSuperior=" + estruturaSuperior + ", nivel=" + nivel + ", id=" + id + ", nomeCor="
				+ nomeCor + ", desc=" + desc + ", situacao=" + situacao + ", orgaoResp=" + orgaoResp + ", mes=" + mes
				+ ", ano=" + ano + ", oeShortName=" + oeShortName + ", responsavel=" + responsavel + ", parecer="
				+ parecer + "]";
	}

	@Override
	public int compareTo(ItemDto o) {
		int vOe = this.oe.compareTo(o.oe);
		if(vOe == 0) {
			if(this.siglaMi != null) {
				int vMi = this.siglaMi.compareTo(o.siglaMi);
				if(vMi == 0) {
					if(this.siglaPi != null) {
						int vPi = this.siglaPi.compareTo(o.siglaPi);
						if(vPi == 0) {
							if(this.siglaAtv != null) {
								return this.siglaAtv.compareTo(o.siglaAtv);								
							}
						}
						return vPi;	
					}
				}
				return vMi;
			}
			
		}
		return vOe;
	}
	
}