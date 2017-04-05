package com.centaline.trans.signroom.entity;

import java.io.Serializable;

/**
 * 签约中心跟贵宾服务部关系表
 */
public class RmSignRoomOrgMapping implements Serializable{
	
	
	private static final long serialVersionUID = 8534976259081747789L;

	private Long pkid;

    private String teamId; //组别ID

    private String districtId; //贵宾服务部id

    private Long tradeCenterId; //交易中心id

    private String districtName;//贵宾服务部名称

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId == null ? null : teamId.trim();
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId == null ? null : districtId.trim();
    }

    public Long getTradeCenterId() {
        return tradeCenterId;
    }

    public void setTradeCenterId(Long tradeCenterId) {
        this.tradeCenterId = tradeCenterId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName == null ? null : districtName.trim();
    }
}