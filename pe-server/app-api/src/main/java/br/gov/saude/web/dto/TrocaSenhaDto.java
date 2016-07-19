package br.gov.saude.web.dto;

public class TrocaSenhaDto {
	private String email;
	private String cpf;
	private String novaSenha;
	private String nomeUsuario;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNovaSenha() {
		return novaSenha;
	}
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	@Override
	public String toString() {
		return "TrocaSenhaDto [email=" + email + ", cpf=" + cpf + ", novaSenha=" + novaSenha + "]";
	}
}