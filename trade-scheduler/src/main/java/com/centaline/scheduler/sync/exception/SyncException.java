package com.centaline.scheduler.sync.exception;
/**
 * 同步异常
 * @author yinchao
 *
 */
public class SyncException extends Exception{
	private static final long serialVersionUID = 1L;

	public SyncException() {}
	public SyncException(String message) { super(message);}
}
