package br.gov.saude.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.ecar.AcompanhamentoAref;
import br.gov.saude.service.dto.AnexoDto;

public class EcarDaoImpl extends DaoImpl implements EcarDao{
	
	public AcompanhamentoAref loadUltimoCiclo() throws AkulaRuntimeException {
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("FROM AcompanhamentoAref aref ");
			hql.append("ORDER BY aref.dataInicioAref DESC ");
			
			Query q = em.createQuery(hql.toString());
			
			q.setMaxResults(1);
			
			return (AcompanhamentoAref) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<AnexoDto> listaAnexos(Long codExe) throws AkulaRuntimeException {
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("SELECT new br.gov.saude.service.dto.AnexoDto( ");
			hql.append("ane.id, ");
			hql.append("ane.codIett, ");
			hql.append("ane.nomeOriginal) ");
			hql.append("FROM IettAnexo ane ");
			hql.append("WHERE ane.codIett = :codIett ");
			
			Query q = em.createQuery(hql.toString());
			
			q.setParameter("codIett", codExe);
			
			return q.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage(), e);
		}
	}
	
	public AnexoDto loadAnexo(Long codAnexo) throws AkulaRuntimeException {
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("SELECT new br.gov.saude.service.dto.AnexoDto( ");
			hql.append("ane.arquivo) ");
			hql.append("FROM IettAnexo ane ");
			hql.append("WHERE ane.id = :codAnexo ");
			
			Query q = em.createQuery(hql.toString());
			
			q.setParameter("codAnexo", codAnexo);
			
			return (AnexoDto) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage(), e);
		}
	}
}