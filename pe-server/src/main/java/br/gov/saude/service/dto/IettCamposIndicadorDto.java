package br.gov.saude.service.dto;

import java.util.Date;

import br.gov.saude.model.Periodicidade;
import br.gov.saude.model.Polaridade;

public class IettCamposIndicadorDto {
	private String descProduto;
	private String descEspecificacaoProduto;
	private Double linhaBase;
	private Integer anoLinhaBase;
	private Date dataApuracao;
	private String metodoApuracao;
	private Polaridade polaridadeIndicador;
	private Periodicidade periodicidade;
	
	public IettCamposIndicadorDto(String descProduto, String descEspecificacaoProduto, Double linhaBase,
			Integer anoLinhaBase, Date dataApuracao, String metodoApuracao, Polaridade polaridadeIndicador,
			Periodicidade periodicidade) {
		super();
		this.descProduto = descProduto;
		this.descEspecificacaoProduto = descEspecificacaoProduto;
		this.linhaBase = linhaBase;
		this.anoLinhaBase = anoLinhaBase;
		this.dataApuracao = dataApuracao;
		this.metodoApuracao = metodoApuracao;
		this.polaridadeIndicador = polaridadeIndicador;
		this.periodicidade = periodicidade;
	}
	
	public IettCamposIndicadorDto() {

	}
	
	public String getDescProduto() {
		return descProduto;
	}
	public void setDescProduto(String descProduto) {
		this.descProduto = descProduto;
	}
	public String getDescEspecificacaoProduto() {
		return descEspecificacaoProduto;
	}
	public void setDescEspecificacaoProduto(String descEspecificacaoProduto) {
		this.descEspecificacaoProduto = descEspecificacaoProduto;
	}
	public Double getLinhaBase() {
		return linhaBase;
	}
	public void setLinhaBase(Double linhaBase) {
		this.linhaBase = linhaBase;
	}
	public Integer getAnoLinhaBase() {
		return anoLinhaBase;
	}
	public void setAnoLinhaBase(Integer anoLinhaBase) {
		this.anoLinhaBase = anoLinhaBase;
	}
	public Date getDataApuracao() {
		return dataApuracao;
	}
	public void setDataApuracao(Date dataApuracao) {
		this.dataApuracao = dataApuracao;
	}
	public String getMetodoApuracao() {
		return metodoApuracao;
	}
	public void setMetodoApuracao(String metodoApuracao) {
		this.metodoApuracao = metodoApuracao;
	}
	public Polaridade getPolaridadeIndicador() {
		return polaridadeIndicador;
	}
	public void setPolaridadeIndicador(Polaridade polaridadeIndicador) {
		this.polaridadeIndicador = polaridadeIndicador;
	}
	public Periodicidade getPeriodicidade() {
		return periodicidade;
	}
	public void setPeriodicidade(Periodicidade periodicidade) {
		this.periodicidade = periodicidade;
	}
}
