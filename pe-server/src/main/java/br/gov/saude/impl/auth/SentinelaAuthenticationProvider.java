package br.gov.saude.impl.auth;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import br.gov.saude.auth.SentinelaPasswordHash;
import br.gov.saude.dao.EcarSentinelaDao;
import br.gov.saude.dao.EcarSiteDao;
import br.gov.saude.model.Usuario;
import br.gov.saude.model.UsuarioSentinela;

public class SentinelaAuthenticationProvider implements AuthenticationProvider {
	private static final Logger logger = LoggerFactory.getLogger(SentinelaAuthenticationProvider.class);
	
	@Autowired
	private SentinelaPasswordHash sentinelaPasswordHash;
	
	@Autowired
	private EcarSentinelaDao eCarSentinelaDao;
	
	@Autowired
	private EcarSiteDao eCarSiteDao;
	
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		logger.debug("Iniciando autenticacao para usuario " + auth.getName());
		String login = String.valueOf(auth.getPrincipal());
		String senha = String.valueOf(auth.getCredentials());
		
		String crypPass = sentinelaPasswordHash.criptografar(login+senha);
		UsuarioSentinela userSentinela = eCarSentinelaDao.consultar(login, crypPass);
		
		if(userSentinela != null) {
			Autenticacao autenticacao = new Autenticacao();
			Map<UserDetails, Object> userDet = new HashMap<UserDetails, Object>();
			autenticacao.setName(login);
			autenticacao.setAuthenticated(true);
			autenticacao.addPermissao(new PermissaoConcedida("ROLE_AUTENTICADO"));
			
			Usuario user = eCarSiteDao.loadUsuario(userSentinela.getEmailUsuario());
			
			userDet.put(UserDetails.ID_USER, user.getId());
			userDet.put(UserDetails.NOME_USER, userSentinela.getNomeUsuario());
			
			autenticacao.setDetails(userDet);
			
			return autenticacao;
		}else {
			throw new BadCredentialsException("Usuario ou senha invalidos");
		}
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}