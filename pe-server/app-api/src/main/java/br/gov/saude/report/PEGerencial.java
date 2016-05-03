package br.gov.saude.report;

import java.util.ArrayList;
import java.util.List;

import br.gov.saude.web.dto.ItemDto;

public class PEGerencial {
	private List<ItemDto> listaItens = new ArrayList<ItemDto>();

	public List<ItemDto> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<ItemDto> listaItens) {
		this.listaItens = listaItens;
	}
}