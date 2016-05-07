package br.gov.saude.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="IettAnexo")
@Table(name="tb_item_estrut_upload_iettup", schema="dbecardemas")
public class IettAnexoImpl implements IettAnexo {
	
	@Id
	@Column(name="cod_iettup")
	private Long id;
	
	@Column(name="cod_iett")
	private Long codIett;
	
	@Column(name="nome_original_iettup")
	private String nomeOriginal;
	
	@Column(name="up_arquivo")
	private byte[] arquivo;
	
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
}