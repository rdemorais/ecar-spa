package br.gov.saude.model.ecar;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="IettAnexo")
@Table(name="tb_item_estrut_upload_iettup", schema="dbecardemas")
public class IettAnexo  {
	
	@Id
	@Column(name="cod_iettup")
	@SequenceGenerator(name="pems_item_anexo_seq", sequenceName="dbecardemas.item_anexo_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="pems_item_anexo_seq")
	private Long id;
	
	@Column(name="cod_iett")
	private Long codIett;
	
	@Column(name="nome_original_iettup")
	private String nomeOriginal;
	
	@Column(name="up_arquivo")
	private byte[] arquivo;
	
	@Column(name="data_inclusao_iettup")
	private Date dataInclusao;
	
	@Column(name="ind_ativo_iettup")
	private String indAtivo;
	
	@Column(name="tamanho_iettup")
	private Long tamanho;
	
	@Column(name="cod_uta")
	private Long codUta;
	
	@Column(name="cod_arel")
	private Long codArel;
	
	@Column(name="cod_usu")
	private Long codUsu;
	
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

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public String getIndAtivo() {
		return indAtivo;
	}

	public void setIndAtivo(String indAtivo) {
		this.indAtivo = indAtivo;
	}

	public Long getTamanho() {
		return tamanho;
	}

	public void setTamanho(Long tamanho) {
		this.tamanho = tamanho;
	}

	public Long getCodUta() {
		return codUta;
	}

	public void setCodUta(Long codUta) {
		this.codUta = codUta;
	}

	public Long getCodArel() {
		return codArel;
	}

	public void setCodArel(Long codArel) {
		this.codArel = codArel;
	}

	public Long getCodUsu() {
		return codUsu;
	}

	public void setCodUsu(Long codUsu) {
		this.codUsu = codUsu;
	}
}