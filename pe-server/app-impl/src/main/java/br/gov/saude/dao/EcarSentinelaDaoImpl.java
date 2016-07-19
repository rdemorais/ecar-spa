package br.gov.saude.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.UsuarioSentinela;

public class EcarSentinelaDaoImpl extends DaoImpl implements EcarSentinelaDao{
	
	public UsuarioSentinela verificarUsuario(String email, String cpf) throws AkulaRuntimeException {
		try {
			Query q = em.createQuery("FROM UsuarioSentinela usu WHERE usu.emailUsuario = :email AND usu.cpf = :cpf");
			
			q.setParameter("email", email);
			q.setParameter("cpf", cpf);
			
			return (UsuarioSentinela) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage(), e);
		}
	}
	
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