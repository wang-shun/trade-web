package com.centaline.trans.remote.vo;

public class HouseInfoVo {

	//案件编号
	private String caseCode;
	
	//小区编号
	private String residence_id;
	
	//小区地址或名称
	private String residence_name;
	
	//楼栋号
	private String building_no;

	//室号
	private String room_no;

	//房屋类型
	private String prop_type;

	//建筑面积
	private Double area;

	//建筑地上面积
	private Double area_ground;

	//建筑地下面积
	private Double area_basement;

	//室
	private Integer room;

	//厅
	private Integer hall;

	//卫
	private Integer toilet;

	//朝向
	private String towards;

	//是否临街
	private String near_street;

	//所在楼层
	private Integer floor;

	//总楼层
	private Integer total_floor;
	
	//平面类型
	private String plane_type;

	//是否景观
	private String scape;

	//主贷银行编码
	private String bank_type;

	//主贷银行所属支行编号
	private String bank_branch_id;

	//期望价格
	private Double deal_price;
	
	//备注附加说明
	private String remark;
	
	//是否主选银行
	private String isMainLoanBank;

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getResidence_id() {
		return residence_id;
	}

	public void setResidence_id(String residence_id) {
		this.residence_id = residence_id;
	}

	public String getResidence_name() {
		return residence_name;
	}

	public void setResidence_name(String residence_name) {
		this.residence_name = residence_name;
	}

	public String getBuilding_no() {
		return building_no;
	}

	public void setBuilding_no(String building_no) {
		this.building_no = building_no;
	}

	public String getRoom_no() {
		return room_no;
	}

	public void setRoom_no(String room_no) {
		this.room_no = room_no;
	}

	public String getProp_type() {
		return prop_type;
	}

	public void setProp_type(String prop_type) {
		this.prop_type = prop_type;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Double getArea_ground() {
		return area_ground;
	}

	public void setArea_ground(Double area_ground) {
		this.area_ground = area_ground;
	}

	public Double getArea_basement() {
		return area_basement;
	}

	public void setArea_basement(Double area_basement) {
		this.area_basement = area_basement;
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

	public String getTowards() {
		return towards;
	}

	public void setTowards(String towards) {
		this.towards = towards;
	}

	public String getNear_street() {
		return near_street;
	}

	public void setNear_street(String near_street) {
		this.near_street = near_street;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Integer getTotal_floor() {
		return total_floor;
	}

	public void setTotal_floor(Integer total_floor) {
		this.total_floor = total_floor;
	}

	public String getPlane_type() {
		return plane_type;
	}

	public void setPlane_type(String plane_type) {
		this.plane_type = plane_type;
	}

	public String getScape() {
		return scape;
	}

	public void setScape(String scape) {
		this.scape = scape;
	}

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public String getBank_branch_id() {
		return bank_branch_id;
	}

	public void setBank_branch_id(String bank_branch_id) {
		this.bank_branch_id = bank_branch_id;
	}

	public Double getDeal_price() {
		return deal_price;
	}

	public void setDeal_price(Double deal_price) {
		this.deal_price = deal_price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsMainLoanBank() {
		return isMainLoanBank;
	}

	public void setIsMainLoanBank(String isMainLoanBank) {
		this.isMainLoanBank = isMainLoanBank;
	}
}
