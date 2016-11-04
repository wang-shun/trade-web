package com.centaline.trans.engine.bean;

import java.util.List;

public class SignalEvent {
	private String signalName;
	private String tenantId;
	private final Boolean async = true;
	private List<RestVariable> variables;

	/**
	 * @return the signalName
	 */
	public String getSignalName() {
		return signalName;
	}

	/**
	 * @param signalName
	 *            the signalName to set
	 */
	public void setSignalName(String signalName) {
		this.signalName = signalName;
	}

	/**
	 * @return the tenantId
	 */
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * @return the variables
	 */
	public List<RestVariable> getVariables() {
		return variables;
	}

	/**
	 * @param variables
	 *            the variables to set
	 */
	public void setVariables(List<RestVariable> variables) {
		this.variables = variables;
	}

	/**
	 * @return the async
	 */
	public Boolean getAsync() {
		return async;
	}
}
