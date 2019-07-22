package br.gov.saude.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import br.gov.saude.exc.AkulaDaoRuntimeException;
import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.Estrutura;
import br.gov.saude.model.Etiqueta;
import br.gov.saude.model.Monitoramento;
import br.gov.saude.model.OE;
import br.gov.saude.model.Usuario;
import br.gov.saude.model.ecar.UsuarioPermissaoMonitoramento;
import br.gov.saude.service.dto.CorDto;
import br.gov.saude.service.dto.FiltroDto;
import br.gov.saude.service.dto.ItemDto;
import br.gov.saude.service.dto.OeDto;
import br.gov.saude.service.dto.SecretariaDto;
import br.gov.saude.service.dto.SituacaoDto;
import br.gov.saude.service.dto.StatusDto;

public class EcarSiteDaoImpl extends DaoImpl implements EcarSiteDao {
	
	public void updateUltimoParecerENaoMonitorado(Long codIett, Long codArel) throws AkulaRuntimeException {
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("UPDATE Monitoramento mon SET mon.naoMonitorado = 'N', mon.ultimoParecer = 'N' ");
			hql.append("WHERE mon.iett.id = :codIett ");
			hql.append("AND mon.codArel <> :codArel ");
			
			Query q = em.createQuery(hql.toString());
			
			q.setParameter("codIett", codIett);
			q.setParameter("codArel", codArel);
			
			q.executeUpdate();
		} catch (Exception e) {
			throw new AkulaDaoRuntimeException(e.getMessage(), e);
		}
	}
	
	public Monitoramento loadMonitoramento(Long codIett, String mes, String ano) throws AkulaRuntimeException {
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("SELECT mon FROM Monitoramento mon  ");
			hql.append("JOIN mon.iett iett ");
			hql.append("WHERE mon.mes = :mes ");
			hql.append("AND mon.ano = :ano ");
			hql.append("AND iett.id = :codIett ");
			
			Query q = em.createQuery(hql.toString());
			
			q.setParameter("mes", mes);
			q.setParameter("ano", ano);
			q.setParameter("codIett", codIett);
			
			return (Monitoramento) q.getSingleResult();
		} catch (NoResultException e) {
			throw new AkulaDaoRuntimeException("Nenhum resultado encontrado para IETT [" + codIett + "] - Mes/Ano ["+mes+"/" + ano + "]", e);
		} catch (NonUniqueResultException e) {
			throw new AkulaDaoRuntimeException("Mais de um Monitoramento encontrado para para IETT [" + codIett + "] - Mes/Ano ["+mes+"/" + ano + "]", e);
		} catch (Exception e) {
			throw new AkulaDaoRuntimeException(e.getMessage(), e);
		}
	}
	
	public Monitoramento loadMonitoramento(Long codArel) throws AkulaRuntimeException {
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("FROM Monitoramento mon WHERE mon.codArel = :codArel ");
			
			Query q = em.createQuery(hql.toString());
			
			q.setParameter("codArel", codArel);
			
			return (Monitoramento) q.getSingleResult();
		} catch (NoResultException e) {
			throw new AkulaDaoRuntimeException("Monitormento eh obrigatorio e nao foi encontrado com o codArel [" + codArel + "]", e);
		} catch (NonUniqueResultException e) {
			throw new AkulaDaoRuntimeException("Mais de um Monitoramento encontrado para codArel [" + codArel + "]", e);
		} catch (Exception e) {
			throw new AkulaDaoRuntimeException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<CorDto> listCor() throws AkulaRuntimeException {
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("FROM Cor c WHERE c.id <> -1 ");
			
			Query q = em.createQuery(hql.toString());
			
			return q.getResultList();
		} catch (Exception e) {
			throw new AkulaDaoRuntimeException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<SituacaoDto> listSituacao() throws AkulaRuntimeException {
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("FROM Situacao");
			
			Query q = em.createQuery(hql.toString());
			
			return q.getResultList();
		} catch (Exception e) {
			throw new AkulaDaoRuntimeException(e.getMessage(), e);
		}
	}
	
	public UsuarioPermissaoMonitoramento loadUsuarioPermissaoMonitoramento(Long codUsu, Long codIett) throws AkulaRuntimeException {
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("FROM UsuarioPermissaoMonitoramento upm ");
			hql.append("WHERE ");
			hql.append("upm.tpPerm = 'F' ");
			hql.append("AND upm.codIett = :codIett ");
			hql.append("AND upm.codUsu = :codUsu ");
			
			Query q = em.createQuery(hql.toString());
			
			q.setParameter("codIett", codIett);
			q.setParameter("codUsu", codUsu);
			
			return (UsuarioPermissaoMonitoramento) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new AkulaDaoRuntimeException(e.getMessage(), e);
		}
	}
	
	public Usuario loadUsuario(Long id) throws AkulaRuntimeException {
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("FROM Usuario u ");
			hql.append("WHERE u.id = :id ");
			
			Query q = em.createQuery(hql.toString());
			
			q.setParameter("id", id);
			
			return (Usuario) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new AkulaDaoRuntimeException(e.getMessage(), e);
		}
	}
	
	public Usuario loadUsuario(String email) throws AkulaRuntimeException {
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("FROM Usuario u ");
			hql.append("WHERE u.email = :email ");
			
			Query q = em.createQuery(hql.toString());
			
			q.setParameter("email", email);
			
			return (Usuario) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new AkulaDaoRuntimeException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<OeDto> listOEPns() throws AkulaRuntimeException {
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("SELECT new br.gov.saude.service.dto.OeDto( ");
			hql.append("oePns.id, ");
			hql.append("oePns.nome, ");
			hql.append("oePns.descricao) ");
			hql.append("FROM OEPns oePns ");
			hql.append("WHERE oePns.codSga = 48 ");
			hql.append("ORDER BY oePns.id ");
			
			Query q = em.createQuery(hql.toString());
			
			return q.getResultList();
		} catch (Exception e) {
			throw new AkulaDaoRuntimeException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<OE> loadOes() throws AkulaRuntimeException {
		try {
			Query q = em.createQuery("FROM OE oe WHERE oe.ativoOe = 'S' ORDER BY oe.sigla ASC");
			
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
			
			hql.append("SELECT new br.gov.saude.service.dto.StatusDto(cor.nome, count(cor.id)) ");
			hql.append("FROM Monitoramento mon ");
			hql.append("JOIN mon.cor cor ");
			hql.append("JOIN mon.iett iett ");
			hql.append("WHERE mon.exercicio = :codExe ");
			hql.append("AND iett.estrutura = :est ");
			hql.append("AND mon.ultimoParecer = 'Y' ");
			if(filtro.isPns() && (estrutura.equals(Estrutura.META) || estrutura.equals(Estrutura.INICIATIVA))) {
				hql.append("AND iett.coOePns IS NOT NULL ");
			}
			hql.append("GROUP BY iett.estrutura, cor.significado, cor.nome, cor.id ");
			
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
			
			hql.append("SELECT DISTINCT new br.gov.saude.service.dto.SecretariaDto( ");
			hql.append("mi.codOrgMi, ");
			hql.append("mi.siglaOrgMi) ");
			hql.append("FROM MetaIniciativa mi ");
			hql.append("WHERE mi.codOrgMi IS NOT NULL ");
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
			
			hql.append("SELECT new br.gov.saude.service.dto.StatusDto('Branco', count(mon.iett)) ");
			hql.append("FROM Monitoramento mon ");
			hql.append("JOIN mon.iett iett ");
			hql.append("JOIN mon.cor cor ");
			hql.append("WHERE mon.exercicio = :codExe ");
			hql.append("AND mon.naoMonitorado = 'Y' ");
			hql.append("AND iett.estrutura = :est ");
			if(filtro.isPns() && (estrutura.equals(Estrutura.META) || estrutura.equals(Estrutura.INICIATIVA))) {
				hql.append("AND iett.coOePns IS NOT NULL ");
			}
			hql.append("GROUP BY iett.estrutura, cor.significado, cor.nome, cor.id ");
			
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
			hql.append("SELECT new br.gov.saude.service.dto.ItemDto( ");
			hql.append("iett.id, "); 
			hql.append("cor.id, ");
			hql.append("cor.nome, ");
			hql.append("cor.significado, ");
			hql.append("sit.id, ");
			hql.append("sit.descricao, ");
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
				hql.append("mon.dataLimite, ");
				hql.append("iett.descProduto, ");
				hql.append("iett.descEspecificacaoProduto, ");
				hql.append("iett.linhaBase, ");
				hql.append("iett.anoLinhaBase, ");
				hql.append("iett.dataApuracao, ");
				hql.append("iett.metodoApuracao, ");
				hql.append("iett.polaridadeIndicador, ");
				hql.append("iett.periodicidade, ");
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
				hql.append("mon.dataLimite, ");
				hql.append("'', ");
				hql.append("'', ");
				hql.append("0.0, ");
				hql.append("0, ");
				hql.append("null, ");
				hql.append("'', ");
				hql.append("'', ");
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
				hql.append("mon.dataLimite, ");
				hql.append("'', ");
				hql.append("'', ");
				hql.append("0.0, ");
				hql.append("0, ");
				hql.append("null, ");
				hql.append("'', ");
				hql.append("'', ");
				hql.append("'', ");
				hql.append("mon.parecer) ");
			}
			
			hql.append("FROM Monitoramento mon ");
			hql.append("JOIN mon.cor cor ");
			hql.append("LEFT JOIN mon.situacao sit ");
			hql.append("JOIN mon.iett iett ");
			hql.append("LEFT JOIN mon.usuario usu ");
			
			if(estrutura.equals(Estrutura.META) || estrutura.equals(Estrutura.INICIATIVA)) {
				hql.append("JOIN iett.oe oe ");
				hql.append("WHERE TYPE(iett) IN (MetaIniciativa) ");
				hql.append("AND iett.ativoMi = 'S' ");
			}else if(estrutura.equals(Estrutura.PRODUTO_INTERMEDIARIO)) {
				hql.append("JOIN iett.metaIniciativa mi ");
				hql.append("JOIN mi.oe oe ");
				hql.append("WHERE TYPE(iett) IN (ProdutoIntermediario) ");
				hql.append("AND iett.ativoPi = 'S' ");
			}else if(estrutura.equals(Estrutura.ATIVIDADE)) {
				hql.append("JOIN iett.produtoIntermediario pi ");
				hql.append("JOIN pi.metaIniciativa mi ");
				hql.append("JOIN mi.oe oe ");
				hql.append("WHERE TYPE(iett) IN (Atividade) ");	
				hql.append("AND iett.ativoAtv = 'S' ");
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
	public List<ItemDto> loadListaItens(FiltroDto filtro, Estrutura estrutura, boolean nMonitorado, boolean comParecer, boolean anteriores) throws AkulaRuntimeException {
		try {
			StringBuffer hql = new StringBuffer();
			
			hql.append("SELECT new br.gov.saude.service.dto.ItemDto( ");
			hql.append("iett.id, ");
			hql.append("cor.id, ");
			hql.append("cor.nome, ");
			hql.append("cor.significado, ");
			hql.append("sit.id, ");
			hql.append("sit.descricao, ");
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
				hql.append("current_date(), ");
				if(comParecer) {
					hql.append("mon.parecer) ");
				}else {
					hql.append("'') ");					
				}
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
				hql.append("current_date(), ");
				if(comParecer) {
					hql.append("mon.parecer) ");
				}else {
					hql.append("'') ");					
				}
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
				hql.append("current_date(), ");
				if(comParecer) {
					hql.append("mon.parecer) ");
				}else {
					hql.append("'') ");					
				}
			}
			
			hql.append("FROM Monitoramento mon ");
			hql.append("JOIN mon.iett iett ");
			hql.append("JOIN mon.cor cor ");
			hql.append("LEFT JOIN mon.situacao sit ");
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
					hql.append("AND iett.codOrgMi IN :secs ");
				}
				
				if(filtro.isMinhaVisao()) {
					hql.append("AND iett.id IN (SELECT upm.codIett FROM UsuarioPermissaoMonitoramento upm "
							+ "WHERE upm.codUsu = :codUsu AND upm.tpPerm = 'F') ");
				}
				
				hql.append("AND iett.ativoMi = 'S' ");
				
			}else if(estrutura.equals(Estrutura.PRODUTO_INTERMEDIARIO)) {
				hql.append("JOIN iett.metaIniciativa mi ");
				hql.append("JOIN mi.oe oe ");
				hql.append("WHERE TYPE(iett) IN (ProdutoIntermediario) ");
				hql.append("AND mi.id = :codIett ");
				hql.append("AND iett.ativoPi = 'S' ");
			}else if(estrutura.equals(Estrutura.ATIVIDADE)) {
				hql.append("JOIN iett.produtoIntermediario pi ");
				hql.append("JOIN pi.metaIniciativa mi ");
				hql.append("JOIN mi.oe oe ");
				hql.append("WHERE TYPE(iett) IN (Atividade) ");
				hql.append("AND pi.id = :codIett ");
				hql.append("AND iett.ativoAtv = 'S' ");
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
				hql.append("AND cor.id IN :status ");
			}
			
			if(filtro.getEtiquetas().size() > 0) {
				hql.append("AND etq.id IN :etiquetas ");
			}
			
			if(nMonitorado) {
				hql.append("AND mon.naoMonitorado = 'Y' ");
			}else {
				if(!anteriores) {
					hql.append("AND mon.ultimoParecer = 'Y' ");
				}else {
					hql.append("AND iett.id = :codIett ");
				}
			}
			
			hql.append("AND mon.exercicio = :codExe ");
			
			Query q = em.createQuery(hql.toString());
			
			if(estrutura.equals(Estrutura.PRODUTO_INTERMEDIARIO) || estrutura.equals(Estrutura.ATIVIDADE) || anteriores) {
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
				
				if(filtro.isMinhaVisao()) {
					q.setParameter("codUsu", filtro.getCodUsu());
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