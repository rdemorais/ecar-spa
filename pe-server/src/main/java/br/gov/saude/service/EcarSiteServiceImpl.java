package br.gov.saude.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import br.gov.saude.auth.SentinelaPasswordHash;
import br.gov.saude.dao.EcarDao;
import br.gov.saude.dao.EcarSentinelaDao;
import br.gov.saude.dao.EcarSiteDao;
import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.exc.AkulaServiceRuntimeException;
import br.gov.saude.file.EcarFileSystem;
import br.gov.saude.model.Cor;
import br.gov.saude.model.Estrutura;
import br.gov.saude.model.Etiqueta;
import br.gov.saude.model.Monitoramento;
import br.gov.saude.model.OE;
import br.gov.saude.model.Situacao;
import br.gov.saude.model.Usuario;
import br.gov.saude.model.UsuarioSentinela;
import br.gov.saude.model.ecar.AcompanhamentoAref;
import br.gov.saude.model.ecar.AcompanhamentoArel;
import br.gov.saude.model.ecar.IettAnexo;
import br.gov.saude.model.ecar.IettAnexoImpl;
import br.gov.saude.model.ecar.UsuarioPermissaoMonitoramento;
import br.gov.saude.report.EcarReport;
import br.gov.saude.web.dto.AnexoDto;
import br.gov.saude.web.dto.CorDto;
import br.gov.saude.web.dto.EtiquetaDto;
import br.gov.saude.web.dto.FiltroDto;
import br.gov.saude.web.dto.ItemDto;
import br.gov.saude.web.dto.OeDto;
import br.gov.saude.web.dto.ParecerDto;
import br.gov.saude.web.dto.SecretariaDto;
import br.gov.saude.web.dto.SituacaoDto;
import br.gov.saude.web.dto.StatusBarDto;
import br.gov.saude.web.dto.TrocaSenhaDto;

public class EcarSiteServiceImpl implements EcarSiteService{
	
	@Autowired
	private EcarSiteDao ecarSiteDao;
	
	@Autowired
	private EcarSentinelaDao ecarSentinelaDao;
	
	@Autowired
	private EcarDao ecarDao;
	
	@Autowired
	private ConvertService convertService;
	
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private EcarReport ecarReport;
	
	@Autowired
	private EcarFileSystem ecarFileSystem;
	
	@Autowired
	private ControleAcessoService controleAcessoService;
	
	@Autowired
	public RelatorioExcelService relatorioExcelService;
	
	@Autowired
	private SentinelaPasswordHash sentinelaPasswordHash;
	
	@Transactional
	public void trocarSenha(TrocaSenhaDto dto) throws AkulaRuntimeException {
		UsuarioSentinela us = ecarSentinelaDao.verificarUsuario(dto.getEmail(), dto.getCpf());
		String cryptPass = sentinelaPasswordHash.criptografar(us.getLoginusuario() + dto.getNovaSenha());
		
		us.setSenhausuario(cryptPass);
		
		ecarDao.merge(us);
	}
	
	@Transactional
	public TrocaSenhaDto verificarUsuario(TrocaSenhaDto dto) throws AkulaRuntimeException {
		UsuarioSentinela us = ecarSentinelaDao.verificarUsuario(dto.getEmail(), dto.getCpf());
		TrocaSenhaDto ret = null;
		if(us != null) {
			ret = new TrocaSenhaDto();
			ret.setNomeUsuario(us.getNomeUsuario());
		}
		return ret;
	}
	
	@Transactional
	public void gravarUpload(MultipartFile file, String nomeFile, Long codIett, Long codArel) throws AkulaRuntimeException {
		try {
			Long userId = (Long) controleAcessoService.usuarioLogadoId();
			IettAnexo anexo = new IettAnexoImpl();
			
			anexo.setArquivo(file.getBytes());
			anexo.setCodIett(codIett);
			anexo.setCodArel(codArel);
			anexo.setCodUta(5L);
			anexo.setDataInclusao(new Date());
			anexo.setIndAtivo("S");
			anexo.setNomeOriginal(nomeFile);
			anexo.setTamanho(file.getSize());
			anexo.setCodUsu(userId);
			
			ecarDao.create(anexo);
		} catch (IOException e) {
			throw new AkulaServiceRuntimeException(e.getMessage(), e);
		}
	}
	
	@Transactional
	public void gravarParecer(ParecerDto dto) throws AkulaRuntimeException {
		Long codUser = (Long) controleAcessoService.usuarioLogadoId();
		Monitoramento mon = ecarSiteDao.loadMonitoramento(dto.getCodArel());
		Cor cor = ecarSiteDao.find(Cor.class, dto.getCor().getId());
		Situacao situacao = ecarSiteDao.find(Situacao.class, dto.getSituacao().getId());
		Usuario user = ecarSiteDao.find(Usuario.class, codUser);
		
		mon.setParecer(dto.getTexto());
		mon.setCor(cor);
		mon.setSituacao(situacao);
		mon.setDataParecer(new Date());
		mon.setUsuario(user);
		mon.setUltimoParecer("Y");
		mon.setNaoMonitorado("N");
		
		//Gravar parecer no eCar
		AcompanhamentoArel arel = ecarDao.find(AcompanhamentoArel.class, dto.getCodArel());
		arel.setCodCor(dto.getCor().getId());
		arel.setCodSit(dto.getSituacao().getId());
		arel.setCodUsuarioUltimaManut(codUser);
		arel.setDataUltimaManutencao(new Date());
		arel.setDescricaoArel(dto.getTexto());
		arel.setIndLiberado("S");
		arel.setCodTpfaUsuario(arel.getCodTpfa());
		
		ecarSiteDao.updateUltimoParecerENaoMonitorado(mon.getIett().getId(), mon.getCodArel());
		
		ecarDao.merge(arel);
		ecarSiteDao.merge(mon);
	}
	
	public List<SecretariaDto> loadSecretarias() throws AkulaRuntimeException {
		return ecarSiteDao.loadSecretarias();
	}
	
	public byte[] gerarRelatorioExcel(FiltroDto filtro) throws AkulaRuntimeException {
		
		return relatorioExcelService.exportarRelatorioExcel(filtro).getBytes();
	}
	
	public AnexoDto loadAnexo(Long codAnexo) throws AkulaRuntimeException {
		return ecarDao.loadAnexo(codAnexo);
	}
	
	public List<AnexoDto> listaAnexos(FiltroDto filtro) throws AkulaRuntimeException {
		Long idUser = (Long) controleAcessoService.usuarioLogadoId();
		UsuarioPermissaoMonitoramento upm = ecarSiteDao.loadUsuarioPermissaoMonitoramento(idUser, filtro.getCodIett());
		List<AnexoDto> anexos = ecarDao.listaAnexos(filtro.getCodIett());
		
		if(upm != null) {
			for (AnexoDto anexoDto : anexos) {
				anexoDto.setPermissaoExclusao(true);
			}
		}
		
		return anexos;
	}
	
	@Transactional
	public void excluirAnexo(AnexoDto anexo) throws AkulaRuntimeException {
		IettAnexo iettAnexo = ecarSiteDao.find(IettAnexo.class, anexo.getId());
		
		ecarSiteDao.remove(iettAnexo);
	}
	
	private Map<String, Object> gerarParametros() throws IOException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		SimpleDateFormat sdfDataH = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		BufferedImage image = ecarFileSystem.getImageFromContext("logo_small.gif");
		BufferedImage logoHeader = ecarFileSystem.getImageFromContext("bg-gov.png");
		
		parametros.put("logo", image);
		parametros.put("logoHeader", logoHeader);
		parametros.put("dataAtual", sdfDataH.format(new Date()));
		
		return parametros;
	}
	
	public byte[] gerarRelatorioExecutivo(FiltroDto filtro) throws AkulaRuntimeException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		List<Object> conteudo = new ArrayList<Object>();
		
		try {
			parametros = gerarParametros();
			Estrutura nivelPai = Estrutura.valueOf(filtro.getNivel().toUpperCase());
			ItemDto item = null;
			List<ItemDto> listaItens = null;
			
			switch (nivelPai) {
			case META:
				item = loadItem(filtro, Estrutura.META);
				listaItens = loadListaItens(filtro, Estrutura.PRODUTO_INTERMEDIARIO);
				break;
			case INICIATIVA:
				item = loadItem(filtro, Estrutura.INICIATIVA);
				listaItens = loadListaItens(filtro, Estrutura.PRODUTO_INTERMEDIARIO);
				break;
			case PRODUTO_INTERMEDIARIO:
				item = loadItem(filtro, Estrutura.PRODUTO_INTERMEDIARIO);
				listaItens = loadListaItens(filtro, Estrutura.ATIVIDADE);
				break;
			case ATIVIDADE:
				item = loadItem(filtro, Estrutura.ATIVIDADE);
				listaItens = new ArrayList<ItemDto>();
				break;
			default:
				break;
			}
			
			
			conteudo.add(convertService.createPEExecutivo(item, listaItens));
			
			byte[] bytes = ecarReport.generateReportPDF("pe-executivo.jasper",  parametros, conteudo);
			
			return bytes;
		} catch (IOException e) {
			throw new AkulaServiceRuntimeException(e.getMessage(), e);
		}
	}
	
	public byte[] gerarRelatorioGerencial(FiltroDto filtro) throws AkulaRuntimeException {
		Map<String, Object> parametros = new HashMap<String, Object>();
		List<Object> conteudo = new ArrayList<Object>();
		
		try {
			parametros = gerarParametros();
			List<ItemDto> listaItens = loadListaItens(filtro, Estrutura.META);
			
			conteudo.add(convertService.createPEGerencial(listaItens, filtro));
			
			byte[] bytes = ecarReport.generateReportPDF("pe-gerencial.jasper",  parametros, conteudo);
			
			return bytes;
		} catch (IOException e) {
			throw new AkulaServiceRuntimeException(e.getMessage(), e);
		}
	}
	
	@Transactional
	public List<OeDto> listaOesPns() throws AkulaRuntimeException {
		return ecarSiteDao.listOEPns();
	}
	
	@Transactional
	public List<OeDto> listaOes() throws AkulaRuntimeException {
		List<OE> oesDb = ecarSiteDao.loadOes();
		
		return convertService.convertListaOE(oesDb);
	}
	
	@Transactional
	public List<EtiquetaDto> listaEtiquetas() throws AkulaRuntimeException {
		List<Etiqueta> etiquetas = ecarSiteDao.loadEtiquetas();
		
		return convertService.convertListaEtiquetas(etiquetas);
	}
	
	@Transactional
	public StatusBarDto loadStatusBar(FiltroDto filtro) throws AkulaRuntimeException {
		
		return statusService.loadStatusBar(filtro);
	}
	
	public ItemDto loadItem(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException {
		ItemDto dto = ecarSiteDao.loadItem(filtro, estrutura);
		Long idUser = (Long) controleAcessoService.usuarioLogadoId();
		UsuarioPermissaoMonitoramento upm = ecarSiteDao.loadUsuarioPermissaoMonitoramento(idUser, filtro.getCodIett());
		DateTime hoje = new DateTime();
		
		if(upm != null) {
			
			AcompanhamentoAref aref = ecarDao.loadUltimoCiclo();
			Monitoramento mon = ecarSiteDao.loadMonitoramento(dto.getId(), aref.getMes(), aref.getAno());
			DateTime dataLimite = new DateTime(mon.getDataLimite());
			dataLimite = dataLimite.plusDays(1);
			
			if(dataLimite.isAfter(hoje)) {
				dto.setParecerAutorizado(true);
				dto.setDataLimite(mon.getDataLimite());
				dto.setCodArel(mon.getCodArel());
				dto.setMesCicloParecer(aref.getMes());
				dto.setAnoCicloParecer(aref.getAno());
			}
		}else {
			dto.setParecerAutorizado(false);
		}
		
		return dto;
	}
	
	@Transactional
	public List<ItemDto> loadListaItens(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException {
		Long idUser = (Long) controleAcessoService.usuarioLogadoId();
		filtro.setCodUsu(idUser);
		
		List<ItemDto> monitorados = ecarSiteDao.loadListaItens(filtro, estrutura, false);
		List<ItemDto> nMonitorados = ecarSiteDao.loadListaItens(filtro, estrutura, true);
		
		List<ItemDto> itens = new ArrayList<ItemDto>(monitorados);
		
		itens.addAll(nMonitorados);
		
		Collections.sort(itens);
		
		return itens;
	}
	
	@Transactional
	public List<SituacaoDto> listSituacao() throws AkulaRuntimeException {
		return ecarSiteDao.listSituacao();
	}
	
	@Transactional
	public List<CorDto> listCor() throws AkulaRuntimeException {
		return ecarSiteDao.listCor();
	}
}