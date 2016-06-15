package br.gov.saude.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.gov.saude.exc.AkulaDaoRuntimeException;
import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.Estrutura;
import br.gov.saude.model.Etiqueta;
import br.gov.saude.model.OE;
import br.gov.saude.web.dto.FiltroDto;
import br.gov.saude.web.dto.ItemDto;
import br.gov.saude.web.dto.OeDto;
import br.gov.saude.web.dto.SecretariaDto;
import br.gov.saude.web.dto.StatusDto;

public class EcarSiteDaoImpl extends DaoImpl implements EcarSiteDao {
	
	
	@SuppressWarnings("unchecked")
	public List<OeDto> listOEPns() throws AkulaRuntimeException {
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("SELECT new br.gov.saude.web.dto.OeDto( ");
			hql.append("oePns.id, ");
			hql.append("oePns.nome, ");
			hql.append("oePns.descricao) ");
			hql.append("FROM OEPns oePns ");
			hql.append("WHERE oePns.codSga = 48 ");
			hql.append("ORDER BY oePns.descricao ");
			
			Query q = em.createQuery(hql.toString());
			
			return q.getResultList();
		} catch (Exception e) {
			throw new AkulaDaoRuntimeException(e.getMessage(), e);
		}
	}
	
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
	public List<StatusDto> loadStatusCount(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException {
		try {
			
			StringBuffer hql = new StringBuffer();
			
			hql.append("SELECT new br.gov.saude.web.dto.StatusDto(mon.nomeCor, count(mon.codCor)) ");
			hql.append("FROM Monitoramento mon ");
			hql.append("JOIN mon.iett iett ");
			hql.append("WHERE mon.exercicio = :codExe ");
			hql.append("AND iett.estrutura = :est ");
			hql.append("AND mon.ultimoParecer = 'Y' ");
			if(filtro.isPns() && (estrutura.equals(Estrutura.META) || estrutura.equals(Estrutura.INICIATIVA))) {
				hql.append("AND iett.coOePns IS NOT NULL ");
			}
			hql.append("GROUP BY iett.estrutura, mon.significadoCor, mon.nomeCor, mon.codCor ");
			
			Query q = em.createQuery(hql.toString());
			q.setParameter("codExe", filtro.getCodExe());
			q.setParameter("est", estrutura);
			
			return q.getResultList();
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<SecretariaDto> loadSecretarias() throws AkulaRuntimeException {
		try {
			
			StringBuffer hql = new StringBuffer();
			
			hql.append("SELECT DISTINCT new br.gov.saude.web.dto.SecretariaDto( ");
			hql.append("mi.codOrg, ");
			hql.append("mi.siglaOrgMi) ");
			hql.append("FROM MetaIniciativa mi ");
			hql.append("WHERE mi.codOrg IS NOT NULL ");
			hql.append("ORDER BY mi.siglaOrgMi ");
			Query q = em.createQuery(hql.toString());
			
			return q.getResultList();
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage());
		}
	}
	
	public StatusDto loadStatusCountNaoMonitorado(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException {
		try {
			
			StringBuffer hql = new StringBuffer();
			
			hql.append("SELECT new br.gov.saude.web.dto.StatusDto('Branco', count(mon.iett)) ");
			hql.append("FROM Monitoramento mon ");
			hql.append("JOIN mon.iett iett ");
			hql.append("WHERE mon.exercicio = :codExe ");
			hql.append("AND mon.naoMonitorado = 'Y' ");
			hql.append("AND iett.estrutura = :est ");
			if(filtro.isPns() && (estrutura.equals(Estrutura.META) || estrutura.equals(Estrutura.INICIATIVA))) {
				hql.append("AND iett.coOePns IS NOT NULL ");
			}
			hql.append("GROUP BY iett.estrutura, mon.significadoCor, mon.nomeCor, mon.codCor ");
			
			Query q = em.createQuery(hql.toString());
			q.setParameter("codExe", filtro.getCodExe());
			q.setParameter("est", estrutura);
			
			return (StatusDto) q.getSingleResult();
		} catch (NoResultException e) {
			return new StatusDto();
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage());
		}
	}
	
	public ItemDto loadItem(FiltroDto filtro, Estrutura estrutura) throws AkulaRuntimeException {
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("SELECT new br.gov.saude.web.dto.ItemDto( ");
			hql.append("iett.id, "); 
			hql.append("LOWER(mon.nomeCor), ");
			hql.append("mon.descricaoSit, ");
			hql.append("iett.estrutura, ");
			hql.append("mon.mes, ");
			hql.append("mon.ano, ");
			hql.append("oe.sigla, ");
			hql.append("usu.nome, ");
			
			if(estrutura.equals(Estrutura.META) || estrutura.equals(Estrutura.INICIATIVA)) {
				hql.append("iett.estrutura, ");
				hql.append("iett.nomeMi, ");
				hql.append("iett.siglaOrgMi, ");
				hql.append("iett.id, ");
				hql.append("-1L, ");
				hql.append("-1L, ");
				hql.append("iett.siglaMi, ");
				hql.append("'', ");
				hql.append("'', ");
				hql.append("iett.estrutura, ");
				hql.append("iett.codPpa, ");
				hql.append("iett.coOePns, ");
				hql.append("iett.oePns, ");
				hql.append("mon.parecer) ");
			}else if(estrutura.equals(Estrutura.PRODUTO_INTERMEDIARIO)) {
				hql.append("mi.estrutura, ");
				hql.append("iett.nomePi, ");
				hql.append("iett.siglaOrgPi, ");
				hql.append("mi.id, ");
				hql.append("iett.id, ");
				hql.append("-1L, ");
				hql.append("mi.siglaMi, ");
				hql.append("iett.siglaPi, ");
				hql.append("'', ");
				hql.append("mi.estrutura, ");
				hql.append("'', ");
				hql.append("-1L, ");
				hql.append("'', ");
				hql.append("mon.parecer) ");
			}else if(estrutura.equals(Estrutura.ATIVIDADE)) {
				hql.append("mi.estrutura, ");
				hql.append("iett.nomeAtv, ");
				hql.append("iett.siglaOrgAtv, ");
				hql.append("mi.id, ");
				hql.append("pi.id, ");
				hql.append("iett.id, ");
				hql.append("mi.siglaMi, ");
				hql.append("pi.siglaPi, ");
				hql.append("iett.siglaAtv, ");
				hql.append("pi.estrutura, ");
				hql.append("'', ");
				hql.append("-1L, ");
				hql.append("'', ");
				hql.append("mon.parecer) ");
			}
			
			hql.append("FROM Monitoramento mon ");
			hql.append("JOIN mon.iett iett ");
			hql.append("LEFT JOIN mon.usuario usu ");
			
			if(estrutura.equals(Estrutura.META) || estrutura.equals(Estrutura.INICIATIVA)) {
				hql.append("JOIN iett.oe oe ");
				hql.append("WHERE TYPE(iett) IN (MetaIniciativa) ");
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
			
			hql.append("AND mon.exercicio = :codExe ");
			hql.append("AND iett.id = :codIett ");
			
			if(filtro.getMes() != null && filtro.getAno() != null) {
				hql.append("AND mon.mes = :mes ");
				hql.append("AND mon.ano = :ano ");
			}else {
				hql.append("AND (mon.ultimoParecer = 'Y' OR mon.naoMonitorado = 'Y')");
			}
			
			Query q = em.createQuery(hql.toString());
			
			q.setParameter("codExe", filtro.getCodExe());
			q.setParameter("codIett", filtro.getCodIett());
			
			if(filtro.getMes() != null && filtro.getAno() != null) {
				q.setParameter("mes", filtro.getMes());
				q.setParameter("ano", filtro.getAno());
			}
			
			return (ItemDto) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new AkulaRuntimeException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemDto> loadListaItens(FiltroDto filtro, Estrutura estrutura, boolean nMonitorado) throws AkulaRuntimeException {
		try {
			StringBuffer hql = new StringBuffer();
			
			hql.append("SELECT new br.gov.saude.web.dto.ItemDto( ");
			hql.append("iett.id, "); 
			if(nMonitorado) {
				hql.append("'branco', ");
			}else {
				hql.append("LOWER(mon.nomeCor), ");
			}
			hql.append("mon.descricaoSit, ");
			hql.append("iett.estrutura, ");
			hql.append("mon.mes, ");
			hql.append("mon.ano, ");
			hql.append("oe.sigla, ");
			hql.append("usu.nome, ");
			
			if(estrutura.equals(Estrutura.META) || estrutura.equals(Estrutura.INICIATIVA)) {
				hql.append("iett.estrutura, ");
				hql.append("iett.nomeMi, ");
				hql.append("iett.siglaOrgMi, ");
				hql.append("iett.id, ");
				hql.append("-1L, ");
				hql.append("-1L, ");
				hql.append("iett.siglaMi, ");
				hql.append("'', ");
				hql.append("'', ");
				hql.append("iett.estrutura, ");
				hql.append("iett.codPpa, ");
				hql.append("iett.coOePns, ");
				hql.append("iett.oePns, ");
				hql.append("'') ");
			}else if(estrutura.equals(Estrutura.PRODUTO_INTERMEDIARIO)) {
				hql.append("mi.estrutura, ");
				hql.append("iett.nomePi, ");
				hql.append("iett.siglaOrgPi, ");
				hql.append("mi.id, ");
				hql.append("iett.id, ");
				hql.append("-1L, ");
				hql.append("mi.siglaMi, ");
				hql.append("iett.siglaPi, ");
				hql.append("'', ");
				hql.append("mi.estrutura, ");
				hql.append("'', ");
				hql.append("-1L, ");
				hql.append("'', ");
				hql.append("'') ");
			}else if(estrutura.equals(Estrutura.ATIVIDADE)) {
				hql.append("mi.estrutura, ");
				hql.append("iett.nomeAtv, ");
				hql.append("iett.siglaOrgAtv, ");
				hql.append("mi.id, ");
				hql.append("pi.id, ");
				hql.append("iett.id, ");
				hql.append("mi.siglaMi, ");
				hql.append("pi.siglaPi, ");
				hql.append("iett.siglaAtv, ");
				hql.append("pi.estrutura, ");
				hql.append("'', ");
				hql.append("-1L, ");
				hql.append("'', ");
				hql.append("'') ");
			}
			
			hql.append("FROM Monitoramento mon ");
			hql.append("JOIN mon.iett iett ");
			hql.append("LEFT JOIN mon.usuario usu ");
			if(filtro.getEtiquetas().size() > 0) {
				hql.append("JOIN iett.etiquetas etq ");
			}
			
			if(estrutura.equals(Estrutura.META) || estrutura.equals(Estrutura.INICIATIVA)) {
				hql.append("JOIN iett.oe oe ");
				hql.append("WHERE TYPE(iett) IN (MetaIniciativa) ");
				if((filtro.isIniciativa() || filtro.isMeta()) && !(filtro.isIniciativa() == true && filtro.isMeta() == true)) {
					hql.append("AND iett.estrutura = :apenasMetaIniciativa ");
				}
				
				if(filtro.isPpa()) {
					hql.append("AND iett.codPpa IS NOT NULL ");
				}
				
				if(filtro.getSecretarias().size() > 0) {
					hql.append("AND iett.codOrg IN :secs ");
				}
			}else if(estrutura.equals(Estrutura.PRODUTO_INTERMEDIARIO)) {
				hql.append("JOIN iett.metaIniciativa mi ");
				hql.append("JOIN mi.oe oe ");
				hql.append("WHERE TYPE(iett) IN (ProdutoIntermediario) ");
				hql.append("AND mi.id = :codIett ");
			}else if(estrutura.equals(Estrutura.ATIVIDADE)) {
				hql.append("JOIN iett.produtoIntermediario pi ");
				hql.append("JOIN pi.metaIniciativa mi ");
				hql.append("JOIN mi.oe oe ");
				hql.append("WHERE TYPE(iett) IN (Atividade) ");
				hql.append("AND pi.id = :codIett ");
			}
			
			if(filtro.isPns() && (estrutura.equals(Estrutura.META) || estrutura.equals(Estrutura.INICIATIVA))) {
				hql.append("AND iett.coOePns IS NOT NULL ");
			}
			
			if(filtro.getOes().size() > 0) {
				if(filtro.isPns() && (estrutura.equals(Estrutura.META) || estrutura.equals(Estrutura.INICIATIVA))) {
					hql.append("AND iett.coOePns IN :oes ");
				}else {
					hql.append("AND oe.id IN :oes ");
				}
			}
			
			if(filtro.getStatus().size() > 0) {
				hql.append("AND mon.codCor IN :status ");
			}
			
			if(filtro.getEtiquetas().size() > 0) {
				hql.append("AND etq.id IN :etiquetas ");
			}
			
			if(nMonitorado) {
				hql.append("AND mon.naoMonitorado = 'Y' ");
			}else {
				hql.append("AND mon.ultimoParecer = 'Y' ");
			}
			
			hql.append("AND mon.exercicio = :codExe ");
			
			Query q = em.createQuery(hql.toString());
			
			if(estrutura.equals(Estrutura.PRODUTO_INTERMEDIARIO) || estrutura.equals(Estrutura.ATIVIDADE)) {
				q.setParameter("codIett", filtro.getCodIett());
			}
			
			q.setParameter("codExe", filtro.getCodExe());
			
			if(estrutura.equals(Estrutura.META) || estrutura.equals(Estrutura.INICIATIVA)) {
				if(!(filtro.isIniciativa() == true && filtro.isMeta() == true)) {
					if(filtro.isIniciativa()) {
						q.setParameter("apenasMetaIniciativa", Estrutura.INICIATIVA);
					}
					if(filtro.isMeta()) {
						q.setParameter("apenasMetaIniciativa", Estrutura.META);
					}
				}
				
				if(filtro.getSecretarias().size() > 0) {
					q.setParameter("secs", filtro.getSecretarias());
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