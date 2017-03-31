package com.centaline.trans.engine.bean;

import java.util.List;

public class ProcessInstance {
	private String processDefinitionId;
	private String processDefinitionKey;
	private String message;
	private String businessKey;
	private List<RestVariable> variables;
	private String tenantId;

	public ProcessInstance(String processDefinitionId, String businessKey) {
		this(processDefinitionId, businessKey, null);
	}

	public ProcessInstance(String processDefinitionId, String businessKey,
			List<RestVariable> variables) {
		this(processDefinitionId, businessKey, variables, null);
	}

	public ProcessInstance(String processDefinitionId, String businessKey,
			List<RestVariable> variables, String tenantId) {
		this.processDefinitionId = processDefinitionId;
		this.businessKey = businessKey;
		this.variables = variables;
		this.tenantId = tenantId;
	}

	public ProcessInstance() {
	}

	/**
	 * @return the processDefinitionId
	 */
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	/**
	 * @param processDefinitionId
	 *            the processDefinitionId to set
	 */
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	/**
	 * @return the processDefinitionKey
	 */
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	/**
	 * @param processDefinitionKey
	 *            the processDefinitionKey to set
	 */
	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the businessKey
	 */
	public String getBusinessKey() {
		return businessKey;
	}

	/**
	 * @param businessKey
	 *            the businessKey to set
	 */
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
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

}
