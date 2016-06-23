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
import br.gov.saude.report.PEExecutivo;
import br.gov.saude.report.PEGerencial;
import br.gov.saude.web.dto.EtiquetaDto;
import br.gov.saude.web.dto.FiltroDto;
import br.gov.saude.web.dto.ItemDto;
import br.gov.saude.web.dto.OeDto;

public class ConvertServiceImpl implements ConvertService{
	
	@Autowired
	private EcarFileSystem ecarFileSystem;
	
	public PEExecutivo createPEExecutivo(ItemDto itemDto, List<ItemDto> listaItens) throws IOException {
		PEExecutivo peExecutivo = new PEExecutivo();
		
		peExecutivo.setExercicio("2016");
		peExecutivo.setOe(itemDto.getOe());
		peExecutivo.setSigla(itemDto.getSiglaMi());
		peExecutivo.setDesc(itemDto.getDesc());
		peExecutivo.setCiclo(itemDto.getMes() + "/" + itemDto.getAno());
		peExecutivo.setResponsavel(itemDto.getResponsavel());
		peExecutivo.setOrgaoResp(itemDto.getOrgaoResp());
		peExecutivo.setSituacao(itemDto.getSituacao());
		peExecutivo.setNivel(itemDto.getNivel());
		peExecutivo.setParecer(itemDto.getParecer());
		peExecutivo.setSiglaMi(itemDto.getSiglaMi());
		peExecutivo.setSiglaPi(itemDto.getSiglaPi());
		peExecutivo.setSiglaAtv(itemDto.getSiglaAtv());
		peExecutivo.setEstruturaProduto(itemDto.getEstruturaProduto());
		peExecutivo.setEstruturaSuperior(itemDto.getEstruturaSuperior());
		peExecutivo.setImgStatus(ecarFileSystem.getImageFromContext(itemDto.getNomeCor() + ".gif"));
		peExecutivo.setPpa(itemDto.getPpa());
		peExecutivo.setPns(itemDto.getOePns());
		
		peExecutivo.setListaItens(convertItem(listaItens));
		
		return peExecutivo;
	}
	
	public PEGerencial createPEGerencial(List<ItemDto> listaItens, FiltroDto filtro) throws IOException {
		PEGerencial peGerencial = new PEGerencial();
		peGerencial.setExercicio("2016");
		peGerencial.setFiltros(convertFiltroToString(filtro));
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
		if(itemDto.getNivel().equals("meta") || itemDto.getNivel().equals("iniciativa")) {
			itemReport.setSigla(itemDto.getSiglaMi());
		}else if(itemDto.getNivel().equals("produto_intermediario")) {
			itemReport.setSigla(itemDto.getSiglaPi());
		}else if(itemDto.getNivel().equals("atividade")) {
			itemReport.setSigla(itemDto.getSiglaAtv());
		}
		itemReport.setDesc(itemDto.getDesc());
		itemReport.setCiclo(itemDto.getMes() + "/" + itemDto.getAno());
		itemReport.setResponsavel(itemDto.getResponsavel());
		itemReport.setOrgaoResp(itemDto.getOrgaoResp());
		itemReport.setSituacao(itemDto.getSituacao());
		itemReport.setNivel(itemDto.getNivel());
		itemReport.setPpa(itemDto.getPpa());
		itemReport.setPns(itemDto.getOePns());
		
		return itemReport;
	}
	
	public String convertFiltroToString(FiltroDto filtro) {
		StringBuffer filtroStr = new StringBuffer();
		if(filtro.isPpa()) {
			filtroStr.append("Apenas PPA");
			filtroStr.append(", ");
		}
		if(filtro.isMeta() && filtro.isIniciativa()) {
			filtroStr.append("Apenas Metas e Iniciativas");
			filtroStr.append(", ");
		}
		if(filtro.isMeta()) {
			filtroStr.append("Apenas Metas");
			filtroStr.append(", ");
		}
		
		return "";
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