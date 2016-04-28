package br.gov.saude.web.dto;

import br.gov.saude.model.Estrutura;

public class StatusDto {
	private Estrutura estrutura;
	private String desc;
	private String nomeCor;
	private Long count;
	
	public StatusDto() {
	}
	
	public StatusDto(Estrutura estrutura, String desc, String nomeCor, Long count) {
		super();
		this.estrutura = estrutura;
		this.desc = desc;
		this.nomeCor = nomeCor;
		this.count = count;
	}
	
	public Estrutura getEstrutura() {
		return estrutura;
	}

	public void setEstrutura(Estrutura estrutura) {
		this.estrutura = estrutura;
	}

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getNomeCor() {
		return nomeCor;
	}
	public void setNomeCor(String nomeCor) {
		this.nomeCor = nomeCor;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "StatusDto [estrutura=" + estrutura + ", desc=" + desc + ", nomeCor=" + nomeCor + ", count=" + count
				+ "]";
	}
}