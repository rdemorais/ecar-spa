package br.gov.saude.service.dto;

public class StatusBarDto {
	private StatusEstruturaDto iniciativa;
	private StatusEstruturaDto meta;
	private StatusEstruturaDto produto;
	private StatusEstruturaDto atividade;
	
	public StatusEstruturaDto getIniciativa() {
		return iniciativa;
	}
	public void setIniciativa(StatusEstruturaDto iniciativa) {
		this.iniciativa = iniciativa;
	}
	public StatusEstruturaDto getMeta() {
		return meta;
	}
	public void setMeta(StatusEstruturaDto meta) {
		this.meta = meta;
	}
	public StatusEstruturaDto getProduto() {
		return produto;
	}
	public void setProduto(StatusEstruturaDto produto) {
		this.produto = produto;
	}
	public StatusEstruturaDto getAtividade() {
		return atividade;
	}
	public void setAtividade(StatusEstruturaDto atividade) {
		this.atividade = atividade;
	}
	@Override
	public String toString() {
		return "StatusBarDto [iniciativa=" + iniciativa + ", meta=" + meta + ", produto=" + produto + ", atividade="
				+ atividade + "]";
	}
	
}