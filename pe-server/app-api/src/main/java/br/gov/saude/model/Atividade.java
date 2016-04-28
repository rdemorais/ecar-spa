package br.gov.saude.model;

import java.util.Date;

public interface Atividade extends Iett{
	public String getSigla();
	public void setSigla(String sigla);
	public String getNome();
	public void setNome(String nome);
	public Date getDataInicio();
	public void setDataInicio(Date dataInicio);
	public Date getDataTermino();
	public void setDataTermino(Date dataTermino);
	public Long getCodOrg();
	public void setCodOrg(Long codOrg);
	public String getSiglaOrg();
	public void setSiglaOrg(String siglaOrg);
	public Usuario getUsuario();
	public void setUsuario(Usuario usuario);
	public ProdutoIntermediario getProdutoIntermediario();
	public void setProdutoIntermediario(ProdutoIntermediario produtoIntermediario);
}