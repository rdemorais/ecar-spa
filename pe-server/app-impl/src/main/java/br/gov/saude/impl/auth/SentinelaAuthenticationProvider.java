package br.gov.saude.impl.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class SentinelaAuthenticationProvider implements AuthenticationProvider {
	private static final Logger logger = LoggerFactory.getLogger(SentinelaAuthenticationProvider.class);
	
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		logger.debug("Iniciando autenticacao para usuario");
		
		Autenticacao autenticacao = new Autenticacao();
		autenticacao.setName("rafael");
		autenticacao.setAuthenticated(true);
		autenticacao.addPermissao(new PermissaoConcedida("ROLE_AUTENTICADO"));
		
		return autenticacao;
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}