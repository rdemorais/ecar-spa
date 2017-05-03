package br.gov.saude.dao;

import java.util.List;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.ecar.AcompanhamentoAref;
import br.gov.saude.model.ecar.Exercicio;
import br.gov.saude.service.dto.AnexoDto;

public interface EcarDao extends Dao{
	public AcompanhamentoAref loadUltimoCiclo() throws AkulaRuntimeException;
	public List<AnexoDto> listaAnexos(Long codExe) throws AkulaRuntimeException;
	public AnexoDto loadAnexo(Long codAnexo) throws AkulaRuntimeException;
	public Exercicio loadExercicio(Long codExe) throws AkulaRuntimeException;
}