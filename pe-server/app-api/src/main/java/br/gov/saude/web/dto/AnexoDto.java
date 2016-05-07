package br.gov.saude.web.dto;

public class AnexoDto {
	private Long id;
	private Long codIett;
	private String nomeOriginal;
	private byte[] arquivo;
	
	public AnexoDto() {

	}
	
	public AnexoDto(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public AnexoDto(Long id, Long codIett, String nomeOriginal) {
		this.id = id;
		this.codIett = codIett;
		this.nomeOriginal = nomeOriginal;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCodIett() {
		return codIett;
	}
	public void setCodIett(Long codIett) {
		this.codIett = codIett;
	}
	public String getNomeOriginal() {
		return nomeOriginal;
	}
	public void setNomeOriginal(String nomeOriginal) {
		this.nomeOriginal = nomeOriginal;
	}
	public byte[] getArquivo() {
		return arquivo;
	}
	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	@Override
	public String toString() {
		return "AnexoDto [id=" + id + ", codIett=" + codIett + ", nomeOriginal=" + nomeOriginal + "]";
	}
	
}