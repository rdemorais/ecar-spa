package br.gov.saude.dao;

import java.util.List;

import javax.persistence.Query;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.OE;

public class EcarSiteDaoImpl extends DaoImpl implements EcarSiteDao{
	
	@SuppressWarnings("unchecked")
	public List<OE> loadOes() throws AkulaRuntimeException {
		try {
			Query q = em.createQuery("FROM OE oe ORDER BY oe.sigla ASC");
			
			return q.getResultList();
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<OE> loadEtiquetas() throws AkulaRuntimeException {
		try {
			Query q = em.createQuery("FROM Etiqueta et");
			
			return q.getResultList();
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage());
		}
	}
}