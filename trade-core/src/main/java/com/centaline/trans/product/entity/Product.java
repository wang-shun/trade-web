package com.centaline.trans.product.entity;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {
	private Long pkid;

	private String prdcCode;

	private String prodName;

	private String prodCode;

	private String vendor;

	private Integer recRate;

	private Date createTime;

	private String createBy;

	private Date updateTime;

	private String updateBy;

	private int sort; // 排序字段

	private int status; // 状态(0:启用,1:禁用,2:删除)

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getPrdcCode() {
		return prdcCode;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public void setPrdcCode(String prdcCode) {
		this.prdcCode = prdcCode == null ? null : prdcCode.trim();
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode == null ? null : prodCode.trim();
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor == null ? null : vendor.trim();
	}

	public Integer getRecRate() {
		return recRate;
	}

	public void setRecRate(Integer recRate) {
		this.recRate = recRate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy == null ? null : updateBy.trim();
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}