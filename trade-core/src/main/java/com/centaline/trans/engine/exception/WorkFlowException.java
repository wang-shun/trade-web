package com.centaline.trans.engine.exception;

public class WorkFlowException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6116283508552955451L;

	public WorkFlowException() {
		super();
	}

	public WorkFlowException(String message) {
		super(message);
	}

	public WorkFlowException(Throwable cause) {
		super(cause);
	}

	public WorkFlowException(String message, Throwable cause) {
		super(message, cause);
	}
}
