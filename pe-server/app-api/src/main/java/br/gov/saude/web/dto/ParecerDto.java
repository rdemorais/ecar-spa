package br.gov.saude.web.dto;

public class ParecerDto {
	private String texto;
	private CorDto cor;
	private SituacaoDto situacao;
	private Long codIett;
	
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public CorDto getCor() {
		return cor;
	}
	public void setCor(CorDto cor) {
		this.cor = cor;
	}
	public SituacaoDto getSituacao() {
		return situacao;
	}
	public void setSituacao(SituacaoDto situacao) {
		this.situacao = situacao;
	}
	public Long getCodIett() {
		return codIett;
	}
	public void setCodIett(Long codIett) {
		this.codIett = codIett;
	}
	@Override
	public String toString() {
		return "ParecerDto [cor=" + cor + ", situacao=" + situacao + ", codIett=" + codIett + "]";
	}
	
}