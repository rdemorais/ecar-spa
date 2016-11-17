package br.gov.saude.service.dto;

import java.util.ArrayList;
import java.util.List;

public class StatusEstruturaDto {
	private Long total;
	private List<StatusDto> status = new ArrayList<StatusDto>();
	
	public StatusEstruturaDto() {
	
	}
	
	public StatusEstruturaDto(List<StatusDto> status, Long total) {
		this.total = total;
		this.status = status;
	}

	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<StatusDto> getStatus() {
		return status;
	}
	public void setStatus(List<StatusDto> status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "StatusEstruturaDto [total=" + total + ", status=" + status + "]";
	}
	
}