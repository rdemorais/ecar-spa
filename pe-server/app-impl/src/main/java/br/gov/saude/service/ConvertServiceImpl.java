package br.gov.saude.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.file.EcarFileSystem;
import br.gov.saude.model.Etiqueta;
import br.gov.saude.model.OE;
import br.gov.saude.report.ItemReport;
import br.gov.saude.report.PEGerencial;
import br.gov.saude.web.dto.EtiquetaDto;
import br.gov.saude.web.dto.ItemDto;
import br.gov.saude.web.dto.OeDto;

public class ConvertServiceImpl implements ConvertService{
	
	@Autowired
	private EcarFileSystem ecarFileSystem;
	
	public PEGerencial createPEGerencial(List<ItemDto> listaItens) throws IOException {
		PEGerencial peGerencial = new PEGerencial();
		peGerencial.setExercicio("2016");
		peGerencial.setFiltros("OE 01");
		peGerencial.setListaItens(convertItem(listaItens));
		
		return peGerencial;
	}
	
	public List<ItemReport> convertItem(List<ItemDto> listaItens) throws IOException {
		List<ItemReport> itensRep = new ArrayList<ItemReport>();
		for (ItemDto itemDto : listaItens) {
			itensRep.add(convertItem(itemDto));
		}
		
		return itensRep;
	}
	
	public ItemReport convertItem(ItemDto itemDto) throws IOException {
		ItemReport itemReport = new ItemReport();
		itemReport.setImgStatus(ecarFileSystem.getImageFromContext(itemDto.getNomeCor() + ".gif"));
		itemReport.setOe(itemDto.getOe());
		itemReport.setSigla(itemDto.getSiglaMi());
		itemReport.setDesc(itemDto.getDesc());
		itemReport.setCiclo(itemDto.getMes() + "/" + itemDto.getAno());
		itemReport.setResponsavel(itemDto.getResponsavel());
		itemReport.setOrgaoResp(itemDto.getOrgaoResp());
		itemReport.setSituacao(itemDto.getSituacao());
		itemReport.setNivel(itemDto.getNivel());
		
		return itemReport;
	}
	
	public List<OeDto> convertListaOE(List<OE> oesDd) throws AkulaRuntimeException {
		List<OeDto> oes = new ArrayList<OeDto>();
		for (OE oe : oesDd) {
			oes.add(convertOE(oe));
		}
		return oes;
	}
	
	public OeDto convertOE(OE oe) throws AkulaRuntimeException {
		OeDto dto = new OeDto();
		
		dto.setId(oe.getId());
		dto.setSigla(oe.getSigla());
		dto.setDesc(oe.getDescricao());
		
		return dto;
	}
	
	public List<EtiquetaDto> convertListaEtiquetas(List<Etiqueta> etiquetasDb) throws AkulaRuntimeException {
		List<EtiquetaDto> etiquetas = new ArrayList<EtiquetaDto>();
		for (Etiqueta et : etiquetasDb) {
			etiquetas.add(convertEtiqueta(et));
		}
		return etiquetas;
	}
	
	public EtiquetaDto convertEtiqueta(Etiqueta et) throws AkulaRuntimeException {
		EtiquetaDto dto = new EtiquetaDto();
		
		dto.setId(et.getId());
		dto.setNome(et.getNome());
		
		return dto;
	}
}