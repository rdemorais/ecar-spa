package br.gov.saude.spi.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.Estrutura;
import br.gov.saude.service.dto.AnexoDto;
import br.gov.saude.service.dto.CorDto;
import br.gov.saude.service.dto.EtiquetaDto;
import br.gov.saude.service.dto.FiltroDto;
import br.gov.saude.service.dto.IettCamposIndicadorDto;
import br.gov.saude.service.dto.ItemDto;
import br.gov.saude.service.dto.OeDto;
import br.gov.saude.service.dto.ParecerDto;
import br.gov.saude.service.dto.SecretariaDto;
import br.gov.saude.service.dto.SituacaoDto;
import br.gov.saude.service.dto.StatusBarDto;
import br.gov.saude.service.dto.TrocaSenhaDto;

public interface EcarSiteService {
	public void updateIettCamposIndicador(Long codIett, IettCamposIndicadorDto camposDto);
	public byte[] gerarRelatorioExecutivoPareceresAnteriores(FiltroDto filtro) throws AkulaRuntimeException;
	public byte[] gerarRelatorioExecutivoPareceres(FiltroDto filtro) throws AkulaRuntimeException;
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
	public List<ItemDto> loadListaItens(FiltroDto filtro, Estrutura estrutura, boolean comParecer, boolean anteriores) throws AkulaRuntimeException;
	public List<SituacaoDto> listSituacao() throws AkulaRuntimeException;
	public List<CorDto> listCor() throws AkulaRuntimeException;
}