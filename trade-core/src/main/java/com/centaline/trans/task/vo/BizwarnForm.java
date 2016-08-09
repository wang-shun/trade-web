/**
 * 
 */
package com.centaline.trans.task.vo;

/**
 * 添加商贷预警信息表单
 * @author yinjf2
 * @date 2016年8月8日
 */
public class BizwarnForm {

	private String caseCode;   //案件编号
	
	private String content;  //预警内容

	/**
	 * @return the caseCode
	 */
	public String getCaseCode() {
		return caseCode;
	}

	/**
	 * @param caseCode the caseCode to set
	 */
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
