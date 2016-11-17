package br.gov.saude.model.ecar;

import java.util.Date;

public interface IettAnexo {
	public Long getId();
	public void setId(Long id);
	public Long getCodIett();
	public void setCodIett(Long codIett);
	public String getNomeOriginal();
	public void setNomeOriginal(String nomeOriginal);
	public byte[] getArquivo();
	public void setArquivo(byte[] arquivo);
	public Date getDataInclusao();
	public void setDataInclusao(Date dataInclusao);
	public String getIndAtivo();
	public void setIndAtivo(String indAtivo);
	public Long getTamanho();
	public void setTamanho(Long tamanho);
	public Long getCodUta();
	public void setCodUta(Long codUta);
	public Long getCodArel();
	public void setCodArel(Long codArel);
	public Long getCodUsu();
	public void setCodUsu(Long codUsu);
}