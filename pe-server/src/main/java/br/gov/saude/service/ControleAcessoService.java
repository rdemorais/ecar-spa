package br.gov.saude.service;

import java.io.Serializable;

import br.gov.saude.exc.AkulaRuntimeException;

public interface ControleAcessoService {
	public String getNomeUsuario() throws AkulaRuntimeException;
	public Serializable usuarioLogadoId() throws AkulaRuntimeException;
}