package br.gov.saude.service.dto;

public class OeDto {
	private Long id;
	private String sigla;
	private String desc;
	
	public OeDto() {
		
	}
	
	public OeDto(Long id, String sigla, String desc) {
		super();
		this.id = id;
		this.sigla = sigla;
		this.desc = desc;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
}