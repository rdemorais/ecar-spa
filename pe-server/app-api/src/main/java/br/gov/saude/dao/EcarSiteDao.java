package br.gov.saude.dao;

import java.util.List;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.OE;

public interface EcarSiteDao extends Dao{
	public List<OE> loadOes() throws AkulaRuntimeException;
	public List<OE> loadEtiquetas() throws AkulaRuntimeException;
}