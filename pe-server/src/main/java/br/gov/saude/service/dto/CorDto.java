package br.gov.saude.service.dto;

public class CorDto {
	private Long id;
	private String nome;
	private String significado;
	
	public CorDto() {
	
	}
	
	public CorDto(Long id, String nome, String significado) {
		this.id = id;
		this.nome = nome;
		this.significado = significado;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSignificado() {
		return significado;
	}
	public void setSignificado(String significado) {
		this.significado = significado;
	}
}