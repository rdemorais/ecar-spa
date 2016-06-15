package br.gov.saude.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.gov.saude.dao.EcarSiteDao;
import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.Estrutura;
import br.gov.saude.web.dto.FiltroDto;
import br.gov.saude.web.dto.StatusBarDto;
import br.gov.saude.web.dto.StatusDto;
import br.gov.saude.web.dto.StatusEstruturaDto;

public class StatusServiceImpl implements StatusService{
	@Autowired
	public EcarSiteDao ecarSiteDao;
	
	public StatusBarDto loadStatusBar(FiltroDto filtro) throws AkulaRuntimeException {
		StatusBarDto statusBar = new StatusBarDto();
		
		statusBar.setMeta(produzStatusEstrutura(filtro, Estrutura.META));
		statusBar.setIniciativa(produzStatusEstrutura(filtro, Estrutura.INICIATIVA));
		statusBar.setProduto(produzStatusEstrutura(filtro, Estrutura.PRODUTO_INTERMEDIARIO));
		statusBar.setAtividade(produzStatusEstrutura(filtro, Estrutura.ATIVIDADE));
		
		return statusBar;
	}
	
	private StatusEstruturaDto produzStatusEstrutura(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException {
		List<StatusDto> statusListDto = ecarSiteDao.loadStatusCount(filtro, estrutura);
		StatusDto statusNMDto = ecarSiteDao.loadStatusCountNaoMonitorado(filtro, estrutura);
		List<StatusDto> statusPadrao = listaStatusPadrao();
		
		Long count = 0L;
		for (StatusDto statusP : statusPadrao) {
			for (StatusDto statusDto : statusListDto) {
				if(statusP.getNomeCor().equals(statusDto.getNomeCor())) {
					statusP.setCount(statusDto.getCount());
					count += statusDto.getCount();
				}
				
				if(statusP.getNomeCor().equals(statusNMDto.getNomeCor())) {
					statusP.setCount(statusNMDto.getCount());
					count += statusNMDto.getCount();
					break;
				}
			}
		}
		return new StatusEstruturaDto(statusPadrao, count);
	}
	
	private List<StatusDto> listaStatusPadrao() {
		StatusDto satisfatorio = new StatusDto("Satisfatório", "Verde", 0L);
		StatusDto alerta = new StatusDto("Alerta", "Amarelo", 0L);
		StatusDto critico = new StatusDto("Crítico", "Vermelho", 0L);
		StatusDto alcancado = new StatusDto("Alcançado", "Azul", 0L);
		StatusDto cancelado = new StatusDto("Cancelado", "Cinza", 0L);
		StatusDto nmonitorado = new StatusDto("Não Monitorado", "Branco", 0L);
		return Arrays.asList(satisfatorio, alerta, critico, alcancado, cancelado, nmonitorado);
	}
}