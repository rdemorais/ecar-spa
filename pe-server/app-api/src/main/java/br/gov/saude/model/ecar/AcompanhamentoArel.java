package br.gov.saude.model.ecar;

import java.util.Date;

public interface AcompanhamentoArel {
	public Long getId();
	public void setId(Long id);
	public Date getDataUltimaManutencao();
	public void setDataUltimaManutencao(Date dataUltimaManutencao);
	public String getIndLiberado();
	public void setIndLiberado(String indLiberado);
	public String getDescricaoArel();
	public void setDescricaoArel(String descricaoArel);
	public Long getCodCor();
	public void setCodCor(Long codCor);
	public Long getCodSit();
	public void setCodSit(Long codSit);
	public Long getCodUsuarioUltimaManut();
	public void setCodUsuarioUltimaManut(Long codUsuarioUltimaManut);
	public Long getCodTpfa();
	public void setCodTpfa(Long codTpfa);
	public Long getCodTpfaUsuario();
	public void setCodTpfaUsuario(Long codTpfaUsuario);
}