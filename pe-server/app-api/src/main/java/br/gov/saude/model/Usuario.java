package br.gov.saude.model;

public interface Usuario {
	public Long getId();
	public void setId(Long id);
	public Long getCoUsuarioSentinela();
	public void setCoUsuarioSentinela(Long coUsuarioSentinela);
	public String getNome();
	public void setNome(String nome);
	public String getEmail();
	public void setEmail(String email);
}