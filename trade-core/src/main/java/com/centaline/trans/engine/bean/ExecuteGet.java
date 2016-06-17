package com.centaline.trans.engine.bean;

public class ExecuteGet {
	private String id;
	private String activityId;
	private String processDefinitionKey;
	private String processDefinitionId;
	private String processInstanceId;
	private String messageEventSubscriptionName;
	private String signalEventSubscriptionName;
	private String parentId;
	private String tenantId;
	private String tenantIdLike;
	private Boolean withoutTenantId;
	private String sort;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getMessageEventSubscriptionName() {
		return messageEventSubscriptionName;
	}

	public void setMessageEventSubscriptionName(String messageEventSubscriptionName) {
		this.messageEventSubscriptionName = messageEventSubscriptionName;
	}

	public String getSignalEventSubscriptionName() {
		return signalEventSubscriptionName;
	}

	public void setSignalEventSubscriptionName(String signalEventSubscriptionName) {
		this.signalEventSubscriptionName = signalEventSubscriptionName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantIdLike() {
		return tenantIdLike;
	}

	public void setTenantIdLike(String tenantIdLike) {
		this.tenantIdLike = tenantIdLike;
	}

	public Boolean getWithoutTenantId() {
		return withoutTenantId;
	}

	public void setWithoutTenantId(Boolean withoutTenantId) {
		this.withoutTenantId = withoutTenantId;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
