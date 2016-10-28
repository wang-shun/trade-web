package com.centaline.trans.engine.exception;

public class WorkFlowException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6116283508552955451L;
	private int statusCode;
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
	
	public WorkFlowException(String message,int statusCode) {
		super(message);
		this.statusCode=statusCode;
	}

	public WorkFlowException(Throwable cause,int statusCode) {
		super(cause);
		this.statusCode=statusCode;
	}

	public WorkFlowException(String message, Throwable cause,int statusCode) {
		super(message, cause);
		this.statusCode=statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
