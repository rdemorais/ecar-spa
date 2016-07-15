package br.gov.saude.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import br.gov.saude.exc.AkulaDaoRuntimeException;
import br.gov.saude.exc.AkulaRuntimeException;

public class DaoImpl implements Dao{
	
	@PersistenceContext
	protected EntityManager em;

	@Override
	public void create(Object o) throws AkulaRuntimeException {
		try {
			em.persist(o);
			em.flush();
		} catch (PersistenceException e) {
			throw new AkulaDaoRuntimeException(e.getMessage(), e, o);
		}
	}

	@Override
	public Object merge(Object o) throws AkulaRuntimeException {
		try {
			Object merged = em.merge(o);
			em.flush();
			return merged;
		} catch (RuntimeException e) {
			throw new AkulaDaoRuntimeException(e.getMessage(), e, o);
		}
	}

	@Override
	public void remove(Object o) throws AkulaRuntimeException {
		try {
			Object attEnt = em.merge(o);
			em.remove(attEnt);
			em.flush();
		} catch (PersistenceException e) {
			throw new AkulaDaoRuntimeException(e.getMessage(), e, o);
		} catch (IllegalArgumentException il) {
			throw new AkulaDaoRuntimeException(il.getMessage(), il, o);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T find(Class<T> c, Serializable pk) throws AkulaRuntimeException {
		try {
			String nomeClass = c.getCanonicalName();
			nomeClass += "Impl";
			return (T) em.find(Class.forName(nomeClass), pk);
		} catch (ClassNotFoundException e) {
			//TODO Informar que o prefixo da classe concreta deve ser "Impl" e o pacote deve ser o mesmo que a interface
			throw new AkulaDaoRuntimeException(e.getMessage(), e);
		}
	}

}