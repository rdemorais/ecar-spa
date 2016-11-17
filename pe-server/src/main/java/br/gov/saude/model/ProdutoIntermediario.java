package br.gov.saude.model;

import java.util.Date;

public interface ProdutoIntermediario extends Iett{
	public String getSiglaPi();
	public void setSiglaPi(String siglaPi);
	public String getNomePi();
	public void setNomePi(String nomePi);
	public Date getDataInicio();
	public void setDataInicio(Date dataInicio);
	public Date getDataTermino();
	public void setDataTermino(Date dataTermino);
	public Long getCodOrg();
	public void setCodOrg(Long codOrg);
	public String getSiglaOrgPi();
	public void setSiglaOrgPi(String siglaOrgPi);
	public Usuario getUsuario();
	public void setUsuario(Usuario usuario);
	public MetaIniciativa getMetaIniciativa();
	public void setMetaIniciativa(MetaIniciativa metaIniciativa);
}