package br.gov.saude.report;

import java.util.ArrayList;
import java.util.List;

public class SecretariaExcel {
	private String nome;
	private List<ItemExcel> itens = new ArrayList<ItemExcel>();
	
	public SecretariaExcel() {
	
	}
	
	public SecretariaExcel(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<ItemExcel> getItens() {
		return itens;
	}

	public void setItens(List<ItemExcel> itens) {
		this.itens = itens;
	}
	
	public void addItem(ItemExcel item) {
		this.itens.add(item);
	}

	@Override
	public String toString() {
		return "SecretariaExcel [nome=" + nome + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SecretariaExcel other = (SecretariaExcel) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}