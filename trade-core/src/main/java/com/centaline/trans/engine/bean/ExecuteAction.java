package com.centaline.trans.engine.bean;

import java.util.List;

public class ExecuteAction {
	private String executionId;
	/**
	 * **options[signal, signalEventReceived ,messageEventReceived]
	 */
	private String action;

	private String signalName;
	private String messageName;
	private List<RestVariable> variables;

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSignalName() {
		return signalName;
	}

	public void setSignalName(String signalName) {
		this.signalName = signalName;
	}

	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public List<RestVariable> getVariables() {
		return variables;
	}

	public void setVariables(List<RestVariable> variables) {
		this.variables = variables;
	}

}
