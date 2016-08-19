package br.gov.saude.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.Estrutura;
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

public interface EcarSiteService {
	public void excluirAnexo(AnexoDto anexo) throws AkulaRuntimeException;
	public void trocarSenha(TrocaSenhaDto dto) throws AkulaRuntimeException;
	public TrocaSenhaDto verificarUsuario(TrocaSenhaDto dto) throws AkulaRuntimeException;
	public void gravarUpload(MultipartFile file, String nomeFile, Long codIett, Long codArel) throws AkulaRuntimeException;
	public void gravarParecer(ParecerDto dto) throws AkulaRuntimeException;
	public List<SecretariaDto> loadSecretarias() throws AkulaRuntimeException;
	public byte[] gerarRelatorioExcel(FiltroDto filtro) throws AkulaRuntimeException;
	public AnexoDto loadAnexo(Long codAnexo) throws AkulaRuntimeException;
	public List<AnexoDto> listaAnexos(FiltroDto filtro) throws AkulaRuntimeException;
	public byte[] gerarRelatorioExecutivo(FiltroDto filtro) throws AkulaRuntimeException;
	public byte[] gerarRelatorioGerencial(FiltroDto filtro) throws AkulaRuntimeException;
	public List<OeDto> listaOesPns() throws AkulaRuntimeException;
	public List<OeDto> listaOes() throws AkulaRuntimeException;
	public List<EtiquetaDto> listaEtiquetas() throws AkulaRuntimeException;
	public StatusBarDto loadStatusBar(FiltroDto filtro) throws AkulaRuntimeException;
	public ItemDto loadItem(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException;
	public List<ItemDto> loadListaItens(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException;
	public List<SituacaoDto> listSituacao() throws AkulaRuntimeException;
	public List<CorDto> listCor() throws AkulaRuntimeException;
}