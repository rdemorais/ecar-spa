package br.gov.saude.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.UsuarioSentinela;

public class EcarSentinelaDaoImpl extends DaoImpl implements EcarSentinelaDao{
	
	public UsuarioSentinela consultar(String login, String senha) throws AkulaRuntimeException {
		try {
			Query q = em.createQuery("FROM UsuarioSentinela usu WHERE usu.loginusuario = :l AND usu.senhausuario = :s");
			
			q.setParameter("l", login);
			q.setParameter("s", senha);
			
			return (UsuarioSentinela) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage(), e);
		}
	}
}