package br.gov.saude.exc;
public class AkulaDaoRuntimeException extends AkulaRuntimeException {

	private static final long serialVersionUID = -7649012428016303326L;
	
	public AkulaDaoRuntimeException(String msg) {
		super(msg);
	}
	
	public AkulaDaoRuntimeException(String msg, Throwable cause, Object objOnError) {
		super(msg, cause);
		this.objOnError = objOnError;
	}
	
	public AkulaDaoRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}