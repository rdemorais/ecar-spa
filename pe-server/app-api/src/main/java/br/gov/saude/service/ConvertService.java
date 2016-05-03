package br.gov.saude.service;

import java.io.IOException;
import java.util.List;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.Etiqueta;
import br.gov.saude.model.OE;
import br.gov.saude.report.ItemReport;
import br.gov.saude.report.PEGerencial;
import br.gov.saude.web.dto.EtiquetaDto;
import br.gov.saude.web.dto.ItemDto;
import br.gov.saude.web.dto.OeDto;

public interface ConvertService {
	public PEGerencial createPEGerencial(List<ItemDto> listaItens) throws IOException;
	public List<ItemReport> convertItem(List<ItemDto> listaItens) throws IOException;
	public ItemReport convertItem(ItemDto itemDto) throws IOException;
	public List<OeDto> convertListaOE(List<OE> oesDd) throws AkulaRuntimeException;
	public OeDto convertOE(OE oe) throws AkulaRuntimeException;
	public List<EtiquetaDto> convertListaEtiquetas(List<Etiqueta> etiquetasDb) throws AkulaRuntimeException;
	public EtiquetaDto convertEtiqueta(Etiqueta et) throws AkulaRuntimeException;
}