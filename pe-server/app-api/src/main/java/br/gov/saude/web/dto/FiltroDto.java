package br.gov.saude.web.dto;

import java.util.ArrayList;
import java.util.List;

public class FiltroDto {
	private boolean ppa = false;
	private boolean meta = false;
	private boolean iniciativa = false;
	private List<OeDto> oes = new ArrayList<OeDto>();
	private List<EtiquetaDto> etiquetas = new ArrayList<EtiquetaDto>();
	private Long codExe;
	
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
	public List<OeDto> getOes() {
		return oes;
	}
	public void setOes(List<OeDto> oes) {
		this.oes = oes;
	}
	public List<EtiquetaDto> getEtiquetas() {
		return etiquetas;
	}
	public void setEtiquetas(List<EtiquetaDto> etiquetas) {
		this.etiquetas = etiquetas;
	}
	public Long getCodExe() {
		return codExe;
	}
	public void setCodExe(Long codExe) {
		this.codExe = codExe;
	}
}