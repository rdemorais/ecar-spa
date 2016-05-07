package br.gov.saude.dao;

import java.util.List;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.web.dto.AnexoDto;

public interface EcarDao extends Dao{
	public List<AnexoDto> listaAnexos(Long codExe) throws AkulaRuntimeException;
	public AnexoDto loadAnexo(Long codAnexo) throws AkulaRuntimeException;
}