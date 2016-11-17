package br.gov.saude.model;

import java.util.Date;

public interface Atividade extends Iett{
	public String getSiglaAtv();
	public void setSiglaAtv(String siglaAtv);
	public String getNomeAtv();
	public void setNomeAtv(String nomeAtv);
	public Date getDataInicio();
	public void setDataInicio(Date dataInicio);
	public Date getDataTermino();
	public void setDataTermino(Date dataTermino);
	public Long getCodOrg();
	public void setCodOrg(Long codOrg);
	public String getSiglaOrgAtv();
	public void setSiglaOrgAtv(String siglaOrgAtv);
	public Usuario getUsuario();
	public void setUsuario(Usuario usuario);
	public ProdutoIntermediario getProdutoIntermediario();
	public void setProdutoIntermediario(ProdutoIntermediario produtoIntermediario);
}