package com.centaline.trans.workspace.entity;

import com.aist.common.quickQuery.bo.JQGridParam;

public class CacheGridParam extends JQGridParam {

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.getQueryId());
		sb.append(super.getParamtMap());
		return sb.toString();
	}
}
