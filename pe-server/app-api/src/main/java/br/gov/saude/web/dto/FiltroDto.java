package br.gov.saude.web.dto;

import java.util.ArrayList;
import java.util.List;

public class FiltroDto {
	private boolean ppa = false;
	private boolean meta = false;
	private boolean iniciativa = false;
	private List<Long> oes = new ArrayList<Long>();
	private List<Long> status = new ArrayList<Long>();
	private List<Long> etiquetas = new ArrayList<Long>();
	private Long codExe;
	private Long codIett;
	private String mes;
	private String ano;
	
	public boolean isPpa() {
		return ppa;
	}
	public void setPpa(boolean ppa) {
		this.ppa = ppa;
	}
	public boolean isMeta() {
		return meta;
	}
	public void setMeta(boolean meta) {
		this.meta = meta;
	}
	public boolean isIniciativa() {
		return iniciativa;
	}
	public void setIniciativa(boolean iniciativa) {
		this.iniciativa = iniciativa;
	}
	public List<Long> getOes() {
		return oes;
	}
	public void setOes(List<Long> oes) {
		this.oes = oes;
	}
	public List<Long> getEtiquetas() {
		return etiquetas;
	}
	public void setEtiquetas(List<Long> etiquetas) {
		this.etiquetas = etiquetas;
	}
	public Long getCodExe() {
		return codExe;
	}
	public void setCodExe(Long codExe) {
		this.codExe = codExe;
	}
	public List<Long> getStatus() {
		return status;
	}
	public void setStatus(List<Long> status) {
		this.status = status;
	}
	public Long getCodIett() {
		return codIett;
	}
	public void setCodIett(Long codIett) {
		this.codIett = codIett;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	@Override
	public String toString() {
		return "FiltroDto [ppa=" + ppa + ", meta=" + meta + ", iniciativa=" + iniciativa + ", oes=" + oes + ", status="
				+ status + ", etiquetas=" + etiquetas + ", codExe=" + codExe + ", codIett=" + codIett + ", mes=" + mes
				+ ", ano=" + ano + "]";
	}
	
	
}