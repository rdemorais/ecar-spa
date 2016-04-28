package br.gov.saude.model;

public interface Etiqueta {
	public Long getId();
	public void setId(Long id);
	public String getNome();
	public void setNome(String nome);
	public String getNomeFonetico();
	public void setNomeFonetico(String nomeFonetico);
	public Iett getIett();
	public void setIett(Iett iett);
}