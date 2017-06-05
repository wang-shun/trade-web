package com.centaline.trans.attachment.entity;

public class ToAccesoryList {
    private Long pkid;

    private String partCode;

    private String accessoryName;

    private String accessoryCode;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode == null ? null : partCode.trim();
    }

    public String getAccessoryName() {
        return accessoryName;
    }

    public void setAccessoryName(String accessoryName) {
        this.accessoryName = accessoryName == null ? null : accessoryName.trim();
    }

    public String getAccessoryCode() {
        return accessoryCode;
    }

    public void setAccessoryCode(String accessoryCode) {
        this.accessoryCode = accessoryCode == null ? null : accessoryCode.trim();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accessoryCode == null) ? 0 : accessoryCode.hashCode());
		result = prime * result + ((accessoryName == null) ? 0 : accessoryName.hashCode());
		result = prime * result + ((partCode == null) ? 0 : partCode.hashCode());
		result = prime * result + ((pkid == null) ? 0 : pkid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ToAccesoryList other = (ToAccesoryList) obj;
		if (accessoryCode == null) {
			if (other.accessoryCode != null)
				return false;
		} else if (!accessoryCode.equals(other.accessoryCode))
			return false;
		if (accessoryName == null) {
			if (other.accessoryName != null)
				return false;
		} else if (!accessoryName.equals(other.accessoryName))
			return false;
		if (partCode == null) {
			if (other.partCode != null)
				return false;
		} else if (!partCode.equals(other.partCode))
			return false;
		if (pkid == null) {
			if (other.pkid != null)
				return false;
		} else if (!pkid.equals(other.pkid))
			return false;
		return true;
	}
}