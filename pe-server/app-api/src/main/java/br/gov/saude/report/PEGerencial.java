package br.gov.saude.report;

import java.util.ArrayList;
import java.util.List;

public class PEGerencial {
	private String exercicio;
	private String filtros;
	private List<ItemReport> listaItens = new ArrayList<ItemReport>();

	public List<ItemReport> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<ItemReport> listaItens) {
		this.listaItens = listaItens;
	}
	
	public void addItem(ItemReport itemReport) {
		this.listaItens.add(itemReport);
	}

	public String getExercicio() {
		return exercicio;
	}

	public void setExercicio(String exercicio) {
		this.exercicio = exercicio;
	}

	public String getFiltros() {
		return filtros;
	}

	public void setFiltros(String filtros) {
		this.filtros = filtros;
	}
}