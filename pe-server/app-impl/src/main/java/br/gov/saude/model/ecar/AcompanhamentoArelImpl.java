package br.gov.saude.model.ecar;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="AcompanhamentoArel")
@Table(name="tb_acomp_relatorio_arel", schema="dbecardemas")
public class AcompanhamentoArelImpl implements AcompanhamentoArel{
	
	@Id
	@Column(name="cod_arel")
	private Long id;
	
	@Column(name="data_ult_manut_arel")
	private Date dataUltimaManutencao;
	
	@Column(name="ind_liberado_arel")
	private String indLiberado;
	
	@Column(name="descricao_arel")
	private String descricaoArel;
	
	@Column(name="cod_cor")
	private Long codCor;
	
	@Column(name="cod_sit")
	private Long codSit;
	
	@Column(name="cod_usuultmanut_arel")
	private Long codUsuarioUltimaManut;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataUltimaManutencao() {
		return dataUltimaManutencao;
	}

	public void setDataUltimaManutencao(Date dataUltimaManutencao) {
		this.dataUltimaManutencao = dataUltimaManutencao;
	}

	public String getIndLiberado() {
		return indLiberado;
	}

	public void setIndLiberado(String indLiberado) {
		this.indLiberado = indLiberado;
	}

	public String getDescricaoArel() {
		return descricaoArel;
	}

	public void setDescricaoArel(String descricaoArel) {
		this.descricaoArel = descricaoArel;
	}

	public Long getCodCor() {
		return codCor;
	}

	public void setCodCor(Long codCor) {
		this.codCor = codCor;
	}

	public Long getCodSit() {
		return codSit;
	}

	public void setCodSit(Long codSit) {
		this.codSit = codSit;
	}

	public Long getCodUsuarioUltimaManut() {
		return codUsuarioUltimaManut;
	}

	public void setCodUsuarioUltimaManut(Long codUsuarioUltimaManut) {
		this.codUsuarioUltimaManut = codUsuarioUltimaManut;
	}
}