package br.gov.saude.service;

import java.util.List;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.Estrutura;
import br.gov.saude.web.dto.AnexoDto;
import br.gov.saude.web.dto.EtiquetaDto;
import br.gov.saude.web.dto.FiltroDto;
import br.gov.saude.web.dto.ItemDto;
import br.gov.saude.web.dto.OeDto;
import br.gov.saude.web.dto.StatusBarDto;

public interface EcarSiteService {
	public byte[] gerarRelatorioExcel(FiltroDto filtro) throws AkulaRuntimeException;
	public AnexoDto loadAnexo(Long codAnexo) throws AkulaRuntimeException;
	public List<AnexoDto> listaAnexos(FiltroDto filtro) throws AkulaRuntimeException;
	public byte[] gerarRelatorioExecutivo(FiltroDto filtro) throws AkulaRuntimeException;
	public byte[] gerarRelatorioGerencial(FiltroDto filtro) throws AkulaRuntimeException;
	public List<OeDto> listaOes() throws AkulaRuntimeException;
	public List<EtiquetaDto> listaEtiquetas() throws AkulaRuntimeException;
	public StatusBarDto loadStatusBar(Long codExe) throws AkulaRuntimeException;
	public ItemDto loadItem(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException;
	public List<ItemDto> loadListaItens(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException;
}