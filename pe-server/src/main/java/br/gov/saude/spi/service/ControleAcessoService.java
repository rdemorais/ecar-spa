package br.gov.saude.spi.service;

import java.io.Serializable;

import br.gov.saude.exc.AkulaRuntimeException;

public interface ControleAcessoService {
	public String getNomeUsuario() throws AkulaRuntimeException;
	public Serializable usuarioLogadoId() throws AkulaRuntimeException;
}