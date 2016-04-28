package br.gov.saude.web.dto;

public class StatusDto {
	private String desc;
	private String nomeCor;
	private Long count;
	
	public StatusDto() {
	}
	
	public StatusDto(String desc, String nomeCor, Long count) {
		super();
		this.desc = desc;
		this.nomeCor = nomeCor;
		this.count = count;
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
		return "StatusDto [desc=" + desc + ", nomeCor=" + nomeCor + ", count=" + count + "]";
	}
}