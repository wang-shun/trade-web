package com.centaline.trans.knowledge.entity;

import java.util.Date;

/**
 * 知识库 Entity
 * 
 * @author yumin1
 * 
 */
public class KnowledgeRepo {
	private Long pkid;

	private String knowledgeCode;

	private String title;

	private Date pbTime;

	private String publisher;
	private String pubiisherCode;

	/**
	 * @return the pubiisherCode
	 */
	public String getPubiisherCode() {
		return pubiisherCode;
	}

	/**
	 * @param pubiisherCode
	 *            the pubiisherCode to set
	 */
	public void setPubiisherCode(String pubiisherCode) {
		this.pubiisherCode = pubiisherCode;
	}

	/**
	 * @return the pubiisherName
	 */
	public String getPubiisherName() {
		return pubiisherName;
	}

	/**
	 * @param pubiisherName
	 *            the pubiisherName to set
	 */
	public void setPubiisherName(String pubiisherName) {
		this.pubiisherName = pubiisherName;
	}

	private String pubiisherName;

	private String content;

	private Integer clickSum;
	private Integer likeSum;
	private String isRecommand;
	private String isTop;

	/**
	 * @return the clickSum
	 */
	public Integer getClickSum() {
		return clickSum;
	}

	/**
	 * @param clickSum
	 *            the clickSum to set
	 */
	public void setClickSum(Integer clickSum) {
		this.clickSum = clickSum;
	}

	/**
	 * @return the likeSum
	 */
	public Integer getLikeSum() {
		return likeSum;
	}

	/**
	 * @param likeSum
	 *            the likeSum to set
	 */
	public void setLikeSum(Integer likeSum) {
		this.likeSum = likeSum;
	}

	/**
	 * @return the isRecommand
	 */
	public String getIsRecommand() {
		return isRecommand;
	}

	/**
	 * @param isRecommand
	 *            the isRecommand to set
	 */
	public void setIsRecommand(String isRecommand) {
		this.isRecommand = isRecommand;
	}

	/**
	 * @return the isTop
	 */
	public String getIsTop() {
		return isTop;
	}

	/**
	 * @param isTop
	 *            the isTop to set
	 */
	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getKnowledgeCode() {
		return knowledgeCode;
	}

	public void setKnowledgeCode(String knowledgeCode) {
		this.knowledgeCode = knowledgeCode == null ? null : knowledgeCode
				.trim();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public Date getPbTime() {
		return pbTime;
	}

	public void setPbTime(Date pbTime) {
		this.pbTime = pbTime;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher == null ? null : publisher.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}
}