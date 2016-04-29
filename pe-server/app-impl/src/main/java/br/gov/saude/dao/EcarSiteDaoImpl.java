package br.gov.saude.dao;

import java.util.List;

import javax.persistence.Query;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.Estrutura;
import br.gov.saude.model.Etiqueta;
import br.gov.saude.model.OE;
import br.gov.saude.web.dto.FiltroDto;
import br.gov.saude.web.dto.ItemDto;
import br.gov.saude.web.dto.StatusDto;

public class EcarSiteDaoImpl extends DaoImpl implements EcarSiteDao{
	
	@SuppressWarnings("unchecked")
	public List<OE> loadOes() throws AkulaRuntimeException {
		try {
			Query q = em.createQuery("FROM OE oe ORDER BY oe.sigla ASC");
			
			return q.getResultList();
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Etiqueta> loadEtiquetas() throws AkulaRuntimeException {
		try {
			Query q = em.createQuery("SELECT DISTINCT new Etiqueta(et.id, et.nome) FROM Etiqueta et");
			
			return q.getResultList();
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<StatusDto> loadStatusCount(Long codExe) throws AkulaRuntimeException {
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
			q.setParameter("codExe", codExe);
			
			return q.getResultList();
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<StatusDto> loadStatusCountNaoMonitorado(Long codExe) throws AkulaRuntimeException {
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
			q.setParameter("codExe", codExe);
			
			return q.getResultList();
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemDto> loadListaItens(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException {
		try {
			StringBuffer hql = new StringBuffer();
			
			hql.append("SELECT new br.gov.saude.web.dto.ItemDto( ");
			hql.append("iett.id, "); 
			hql.append("mon.nomeCor, ");
			hql.append("iett.nome, ");
			hql.append("mon.descricaoSit, ");
			hql.append("iett.estrutura, ");
			hql.append("iett.siglaOrg, ");
			hql.append("mon.mes, ");
			hql.append("oe.sigla) ");
			hql.append("FROM Monitoramento mon ");
			hql.append("JOIN mon.iett iett ");
			if(filtro.getEtiquetas().size() > 0) {
				hql.append("JOIN iett.etiquetas etq ");
			}
			
			if(estrutura.equals(Estrutura.META) || estrutura.equals(Estrutura.INICIATIVA)) {
				hql.append("JOIN iett.oe oe ");
				hql.append("WHERE TYPE(iett) IN (MetaIniciativa) ");
				if((filtro.isIniciativa() || filtro.isMeta()) && !(filtro.isIniciativa() == true && filtro.isMeta() == true)) {
					hql.append("AND iett.estrutura = :apenasMetaIniciativa ");
				}
			}else if(estrutura.equals(Estrutura.PRODUTO_INTERMEDIARIO)) {
				hql.append("JOIN iett.metaIniciativa mi ");
				hql.append("JOIN mi.oe oe ");
				hql.append("WHERE TYPE(iett) IN (ProdutoIntermediario) ");
			}else if(estrutura.equals(Estrutura.ATIVIDADE)) {
				hql.append("JOIN iett.produtoIntermediario pi ");
				hql.append("JOIN pi.metaIniciativa mi ");
				hql.append("JOIN mi.oe oe ");
				hql.append("WHERE TYPE(iett) IN (Atividade) ");	
			}
			
			if(filtro.getOes().size() > 0) {
				hql.append("AND oe.id IN :oes ");
			}
			
			if(filtro.getStatus().size() > 0) {
				hql.append("AND mon.codCor IN :status ");
			}
			
			if(filtro.getEtiquetas().size() > 0) {
				hql.append("AND etq.id IN :etiquetas ");
			}
			
			hql.append("AND mon.exercicio = :codExe ");
			hql.append("AND mon.ultimoParecer = 'Y' ");
			
			Query q = em.createQuery(hql.toString());
			q.setParameter("codExe", filtro.getCodExe());
			
			if(!(filtro.isIniciativa() == true && filtro.isMeta() == true)) {
				if(filtro.isIniciativa()) {
					q.setParameter("apenasMetaIniciativa", Estrutura.INICIATIVA);
				}
				if(filtro.isMeta()) {
					q.setParameter("apenasMetaIniciativa", Estrutura.META);
				}
			}
			
			if(filtro.getOes().size() > 0) {
				q.setParameter("oes", filtro.getOes());
			}
			
			if(filtro.getStatus().size() > 0) {
				q.setParameter("status", filtro.getStatus());
			}
			
			if(filtro.getEtiquetas().size() > 0) {
				q.setParameter("etiquetas", filtro.getEtiquetas());
			}
			
			
			return q.getResultList();
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage(), e);
		}
	}
}