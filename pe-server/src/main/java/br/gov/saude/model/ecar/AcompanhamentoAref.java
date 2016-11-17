package br.gov.saude.model.ecar;

import java.util.Date;

public interface AcompanhamentoAref {
	public Long getId();
	public void setId(Long id);
	public Date getDataInicioAref();
	public void setDataInicioAref(Date dataInicioAref);
	public String getMes();
	public void setMes(String mes);
	public String getAno();
	public void setAno(String ano);
}