package br.gov.saude.dao;

import java.util.List;

import javax.persistence.Query;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.Etiqueta;
import br.gov.saude.model.OE;
import br.gov.saude.web.dto.StatusDto;

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
	public List<Etiqueta> loadEtiquetas() throws AkulaRuntimeException {
		try {
			Query q = em.createQuery("SELECT DISTINCT new Etiqueta(et.id, et.nome) FROM Etiqueta et");
			
			return q.getResultList();
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<StatusDto> loadStatusCount() throws AkulaRuntimeException {
		try {
			
			StringBuffer hql = new StringBuffer();
			
			hql.append("SELECT new br.gov.saude.web.dto.StatusDto(iett.estrutura, mon.significadoCor, mon.nomeCor, count(mon.codCor)) ");
			hql.append("FROM Monitoramento mon ");
			hql.append("JOIN mon.iett iett ");
			hql.append("WHERE mon.exercicio = :codExe ");
			hql.append("AND mon.ultimoParecer = 'Y' ");
			hql.append("GROUP BY iett.estrutura, mon.significadoCor, mon.nomeCor, mon.codCor ");
			hql.append("ORDER BY iett.estrutura ASC");
			
			Query q = em.createQuery(hql.toString());
			q.setParameter("codExe", 1L);
			
			return q.getResultList();
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<StatusDto> loadStatusCountNaoMonitorado() throws AkulaRuntimeException {
		try {
			
			StringBuffer hql = new StringBuffer();
			
			hql.append("SELECT new br.gov.saude.web.dto.StatusDto(iett.estrutura, 'Não Monitorado', 'Branco', count(mon.iett)) ");
			hql.append("FROM Monitoramento mon ");
			hql.append("JOIN mon.iett iett ");
			hql.append("WHERE mon.exercicio = :codExe ");
			hql.append("AND mon.naoMonitorado = 'Y' ");
			hql.append("GROUP BY iett.estrutura, mon.significadoCor, mon.nomeCor, mon.codCor ");
			hql.append("ORDER BY iett.estrutura ASC");
			
			Query q = em.createQuery(hql.toString());
			q.setParameter("codExe", 1L);
			
			return q.getResultList();
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage());
		}
	}
}