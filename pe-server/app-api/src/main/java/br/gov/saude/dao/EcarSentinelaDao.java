package br.gov.saude.dao;

import br.gov.saude.exc.AkulaRuntimeException;
import br.gov.saude.model.UsuarioSentinela;

public interface EcarSentinelaDao extends Dao{
	public UsuarioSentinela verificarUsuario(String email, String cpf) throws AkulaRuntimeException;
	public UsuarioSentinela consultar(String login, String senha) throws AkulaRuntimeException;
}
