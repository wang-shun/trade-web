package com.centaline.trans.mgr.entity;

public class TsSup {
    private Long pkid;

    private String finOrgCode;

    private String contactName;

    private String contactPhone;

    private String supCat;

    private String relFinOrgCode;
    
    private String coLevel;
    private String tags;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getFinOrgCode() {
        return finOrgCode;
    }

    public void setFinOrgCode(String finOrgCode) {
        this.finOrgCode = finOrgCode == null ? null : finOrgCode.trim();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getSupCat() {
        return supCat;
    }

    public void setSupCat(String supCat) {
        this.supCat = supCat == null ? null : supCat.trim();
    }

    public String getRelFinOrgCode() {
        return relFinOrgCode;
    }

    public void setRelFinOrgCode(String relFinOrgCode) {
        this.relFinOrgCode = relFinOrgCode == null ? null : relFinOrgCode.trim();
    }

	public String getCoLevel() {
		return coLevel;
	}

	public void setCoLevel(String coLevel) {
		this.coLevel = coLevel;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
}