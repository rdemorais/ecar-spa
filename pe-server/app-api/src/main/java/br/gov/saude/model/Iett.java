package br.gov.saude.model;

import java.util.Collection;

public interface Iett {
	public Long getId();
	public void setId(Long id);
	public Estrutura getEstrutura();
	public void setEstrutura(Estrutura estrutura);
	public Collection<Etiqueta> getEtiquetas();
	public void setEtiquetas(Collection<Etiqueta> etiquetas);
}