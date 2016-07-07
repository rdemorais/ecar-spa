package br.gov.saude.web.dto;

import java.util.Date;

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
	private String estruturaProduto;
	private String ppa;
	private Long coOePnsMi;
	private String oePns;
	
	private Long codMi;
	private Long codPi;
	private Long codAtv;
	private String siglaMi;
	private String siglaPi;
	private String siglaAtv;
	private String parecer;
	
	private Date dataLimite;
	
	private boolean parecerAutorizado = false;
	
	public ItemDto() {
		
	}
	
	public ItemDto(
			Long id, 
			String nomeCor, 
			String situacao, 
			Estrutura estrutura, 
			String mes, 
			String ano,
			String oe, 
			String responsavel,
			Estrutura estruturaProduto,
			String desc, 
			String orgaoResp,
			Long codMi, 
			Long codPi, 
			Long codAtv,
			String siglaMi, 
			String siglaPi, 
			String siglaAtv, 
			Estrutura estruturaSuperior,
			String ppa,
			Long coOePnsMi,
			String oePns,
			Date dataLimite,
			String parecer) {
		super();
		this.id = id;
		if(nomeCor == null) {
			this.nomeCor = "branco";
		}else {
			this.nomeCor = nomeCor;
		}
		this.codMi = codMi;
		this.codPi = codPi;
		this.codAtv = codAtv;
		this.siglaMi = siglaMi;
		this.siglaPi = siglaPi;
		this.siglaAtv = siglaAtv;
		this.desc = desc;
		this.situacao = situacao;
		this.estruturaProduto = estruturaProduto.name().toLowerCase();
		this.nivel = estrutura.name().toLowerCase();
		this.orgaoResp = orgaoResp;
		this.mes = mes;
		this.ano = ano;
		this.oe = oe;
		this.oeShortName = oe.replaceAll("\\s+","").toLowerCase();
		this.responsavel = responsavel;
		this.estruturaSuperior = estruturaSuperior.name().toLowerCase();
		this.ppa = ppa;
		this.coOePnsMi = coOePnsMi;
		this.oePns = oePns;
		this.parecer = parecer;
		this.dataLimite = dataLimite;
	}
	
	public Date getDataLimite() {
		return dataLimite;
	}

	public void setDataLimite(Date dataLimite) {
		this.dataLimite = dataLimite;
	}

	public Long getCoOePnsMi() {
		return coOePnsMi;
	}

	public void setCoOePnsMi(Long coOePnsMi) {
		this.coOePnsMi = coOePnsMi;
	}

	public String getOePns() {
		return oePns;
	}

	public void setOePns(String oePns) {
		this.oePns = oePns;
	}

	public String getPpa() {
		return ppa;
	}

	public void setPpa(String ppa) {
		this.ppa = ppa;
	}

	public String getEstruturaProduto() {
		return estruturaProduto;
	}

	public void setEstruturaProduto(String estruturaProduto) {
		this.estruturaProduto = estruturaProduto;
	}

	public Long getCodMi() {
		return codMi;
	}

	public void setCodMi(Long codMi) {
		this.codMi = codMi;
	}

	public Long getCodPi() {
		return codPi;
	}

	public void setCodPi(Long codPi) {
		this.codPi = codPi;
	}

	public Long getCodAtv() {
		return codAtv;
	}

	public void setCodAtv(Long codAtv) {
		this.codAtv = codAtv;
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

	public boolean isParecerAutorizado() {
		return parecerAutorizado;
	}

	public void setParecerAutorizado(boolean parecerAutorizado) {
		this.parecerAutorizado = parecerAutorizado;
	}

	@Override
	public String toString() {
		return "ItemDto [oe=" + oe + ", siglaMi=" + siglaMi + ", siglaPi=" + siglaPi + ", siglaAtv=" + siglaAtv
				+ ", codMi=" + codMi + ", codPi=" + codPi + ", codAtv=" + codAtv + ", estruturaSuperior="
				+ estruturaSuperior + ", estruturaProduto=" + estruturaProduto + ", id=" + id + ", nomeCor=" + nomeCor
				+ ", desc=" + desc + ", situacao=" + situacao + ", nivel=" + nivel + ", orgaoResp=" + orgaoResp
				+ ", mes=" + mes + ", ano=" + ano + ", oeShortName=" + oeShortName + ", responsavel=" + responsavel
				+ ", parecer=-]";
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