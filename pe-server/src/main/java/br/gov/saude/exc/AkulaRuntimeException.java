package br.gov.saude.exc;

public class AkulaRuntimeException extends RuntimeException{

	private static final long serialVersionUID = -624075139510025583L;
	
	/**
	 * Armazena o objeto envolvido no erro
	 */
	public Object objOnError = null;
	
	public AkulaRuntimeException(String msg) {
		super(msg);
	}
	
	public AkulaRuntimeException(String msg, Throwable cause, Object objOnError) {
		super(msg, cause);
	}
	
	public AkulaRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public Object getObjOnError() {
		return objOnError;
	}

}