package br.gov.saude.service;

import java.io.Serializable;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.impl.auth.UserDetails;

public class ControleAcessoServiceImpl implements ControleAcessoService{
	
	@Override
	@SuppressWarnings("unchecked")
	public String getNomeUsuario() throws AkulaRuntimeException {
		Authentication auth = ((OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication()).getUserAuthentication();
		
		Map<UserDetails, Object> userDet = (Map<UserDetails, Object>) auth.getDetails();
		return (String) userDet.get(UserDetails.NOME_USER);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Serializable usuarioLogadoId() throws AkulaRuntimeException {
		Authentication auth = ((OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication()).getUserAuthentication();
		
		Map<UserDetails, Object> userDet = (Map<UserDetails, Object>) auth.getDetails();
		return (Serializable) userDet.get(UserDetails.ID_USER);
	}
}