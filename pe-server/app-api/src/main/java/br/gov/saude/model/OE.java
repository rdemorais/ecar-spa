package br.gov.saude.model;

public interface OE extends Iett{
	public Long getId();
	public void setId(Long id);
	public String getSigla();
	public void setSigla(String sigla);
	public String getDescricao();
	public void setDescricao(String descricao);
}