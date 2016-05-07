package br.gov.saude.model;

public interface IettAnexo {
	public Long getId();
	public void setId(Long id);
	public Long getCodIett();
	public void setCodIett(Long codIett);
	public String getNomeOriginal();
	public void setNomeOriginal(String nomeOriginal);
	public byte[] getArquivo();
	public void setArquivo(byte[] arquivo);
}