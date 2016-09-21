package com.centaline.trans.signroom.entity;

import java.io.Serializable;

/**
 * 签约室策略表(T_RM_ROOM_SCHE_STRAGEGY)
 * @author zhoujp7
 *
 */
public class RmRoomScheStragegy implements Serializable{
	

	private static final long serialVersionUID = -7911866334958977254L;

	private Long pkid;

    private Long roomId;//房间id

    private Long stragegyWeekVal;//策略值

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getStragegyWeekVal() {
        return stragegyWeekVal;
    }

    public void setStragegyWeekVal(Long stragegyWeekVal) {
        this.stragegyWeekVal = stragegyWeekVal;
    }
}