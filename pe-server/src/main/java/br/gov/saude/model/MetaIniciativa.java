package br.gov.saude.model;

import java.util.Date;

public interface MetaIniciativa {
	public String getSiglaMi();
	public void setSiglaMi(String siglaMi);
	public String getNomeMi();
	public void setNomeMi(String nomeMi);
	public Date getDataInicio();
	public void setDataInicio(Date dataInicio);
	public Date getDataTermino();
	public void setDataTermino(Date dataTermino);
	public Long getCodOrg();
	public void setCodOrg(Long codOrg);
	public String getSiglaOrgMi();
	public void setSiglaOrgMi(String siglaOrgMi);
	public Usuario getUsuario();
	public void setUsuario(Usuario usuario);
	public OE getOe();
	public void setOe(OE oe);
	public Long getCoOePns();
	public void setCoOePns(Long coOePns);
	public String getOePns();
	public void setOePns(String oePns);
	public String getCodPpa();
	public void setCodPpa(String codPpa);
}