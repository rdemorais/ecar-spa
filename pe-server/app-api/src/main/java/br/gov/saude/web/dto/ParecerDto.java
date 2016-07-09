package br.gov.saude.web.dto;

public class ParecerDto {
	private String texto;
	private CorDto cor;
	private SituacaoDto situacao;
	private Long codArel;
	
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
	public Long getCodArel() {
		return codArel;
	}
	public void setCodArel(Long codArel) {
		this.codArel = codArel;
	}
	@Override
	public String toString() {
		return "ParecerDto [texto=" + texto + ", cor=" + cor + ", situacao=" + situacao + ", codArel=" + codArel + "]";
	}
}