package com.centaline.trans.mortgage.entity;

import java.math.BigDecimal;

public class ToEguPropertyInfo {
    private Long pkid;

    private String evaCode;

    private String residenceName;

    private String buildingNo;

    private String roomNo;

    private Double area;

    private Double areaGround;

    private Double areaBasement;

    private Integer room;

    private Integer hall;

    private Integer toilet;

    private Integer floor;

    private Integer totalFloor;

    private String propType;

    private String planeType;

    private String towards;

    private String scape;

    private String nearStreet;

    private String remark;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getEvaCode() {
        return evaCode;
    }

    public void setEvaCode(String evaCode) {
        this.evaCode = evaCode == null ? null : evaCode.trim();
    }

    public String getResidenceName() {
        return residenceName;
    }

    public void setResidenceName(String residenceName) {
        this.residenceName = residenceName == null ? null : residenceName.trim();
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo == null ? null : buildingNo.trim();
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo == null ? null : roomNo.trim();
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getAreaGround() {
        return areaGround;
    }

    public void setAreaGround(Double areaGround) {
        this.areaGround = areaGround;
    }

    public Double getAreaBasement() {
        return areaBasement;
    }

    public void setAreaBasement(Double areaBasement) {
        this.areaBasement = areaBasement;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public Integer getHall() {
        return hall;
    }

    public void setHall(Integer hall) {
        this.hall = hall;
    }

    public Integer getToilet() {
        return toilet;
    }

    public void setToilet(Integer toilet) {
        this.toilet = toilet;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(Integer totalFloor) {
        this.totalFloor = totalFloor;
    }

    public String getPropType() {
        return propType;
    }

    public void setPropType(String propType) {
        this.propType = propType == null ? null : propType.trim();
    }

    public String getPlaneType() {
        return planeType;
    }

    public void setPlaneType(String planeType) {
        this.planeType = planeType == null ? null : planeType.trim();
    }

    public String getTowards() {
        return towards;
    }

    public void setTowards(String towards) {
        this.towards = towards == null ? null : towards.trim();
    }

    public String getScape() {
        return scape;
    }

    public void setScape(String scape) {
        this.scape = scape == null ? null : scape.trim();
    }

    public String getNearStreet() {
        return nearStreet;
    }

    public void setNearStreet(String nearStreet) {
        this.nearStreet = nearStreet == null ? null : nearStreet.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}