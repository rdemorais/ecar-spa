package br.gov.saude.exc;
public class AkulaServiceRuntimeException extends AkulaRuntimeException {

	private static final long serialVersionUID = 6120276937379725510L;
	
	public AkulaServiceRuntimeException(String msg) {
		super(msg);
	}

	public AkulaServiceRuntimeException(String msg, Throwable cause,
			Object objOnError) {
		super(msg, cause, objOnError);
		this.objOnError = objOnError;
	}

	public AkulaServiceRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}