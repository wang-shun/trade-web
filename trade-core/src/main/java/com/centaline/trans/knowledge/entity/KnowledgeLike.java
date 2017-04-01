package com.centaline.trans.knowledge.entity;

public class KnowledgeLike {
	private Long pkid;
	private String knowledgeCode;
	private String likerId;
	private String isLike;

	/**
	 * @return the pkid
	 */
	public Long getPkid() {
		return pkid;
	}

	/**
	 * @param pkid
	 *            the pkid to set
	 */
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	/**
	 * @return the knowledgeCode
	 */
	public String getKnowledgeCode() {
		return knowledgeCode;
	}

	/**
	 * @param knowledgeCode
	 *            the knowledgeCode to set
	 */
	public void setKnowledgeCode(String knowledgeCode) {
		this.knowledgeCode = knowledgeCode;
	}

	/**
	 * @return the likerId
	 */
	public String getLikerId() {
		return likerId;
	}

	/**
	 * @param likerId
	 *            the likerId to set
	 */
	public void setLikerId(String likerId) {
		this.likerId = likerId;
	}

	/**
	 * @return the isLike
	 */
	public String getIsLike() {
		return isLike;
	}

	/**
	 * @param isLike
	 *            the isLike to set
	 */
	public void setIsLike(String isLike) {
		this.isLike = isLike;
	}
}
