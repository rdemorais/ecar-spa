package br.gov.saude.model;

public interface UsuarioSentinela {
	public Long getId();
	public void setId(Long id);
	public String getLoginusuario();
	public void setLoginusuario(String loginusuario);
	public String getSenhausuario();
	public void setSenhausuario(String senhausuario);
	public String getEmailUsuario();
	public void setEmailUsuario(String emailUsuario);
}