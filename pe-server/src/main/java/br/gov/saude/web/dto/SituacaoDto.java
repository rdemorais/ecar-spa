package br.gov.saude.web.dto;

public class SituacaoDto {
	private Long id;
	private String descricao;
	
	public SituacaoDto() {
	
	}
	
	public SituacaoDto(Long id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}