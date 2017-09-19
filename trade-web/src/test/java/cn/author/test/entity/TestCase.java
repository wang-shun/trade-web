package cn.author.test.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToCcaiAttachment;
import com.centaline.trans.common.entity.ToPropertyInfo;

public class TestCase {
	private String caseCode;
	private String ccaiId;
	private String ccaiCode;
	private String tradeType;
	private String payType;
	private String leadingProcessorId;
	private Date createTime;
	private Date updateTime;
	private List<ToCaseParticipant> participants;
	private ToPropertyInfo toPropertyInfo;
	private List<TgGuestInfo> guests;
	private List<ToCcaiAttachment> attachments;
	private String city;
	
				
	public TestCase() {
		participants=new ArrayList<ToCaseParticipant>();
		guests=new ArrayList<TgGuestInfo>();
		attachments=new ArrayList<ToCcaiAttachment>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmssSSS");	
		String strDate = simpleDateFormat.format(new Date());
		String primaryCaseCode="xiefeiCase"+strDate;
		this.caseCode = primaryCaseCode;
		this.ccaiId = "testCcaiId"+strDate;
		this.ccaiCode = "testccaiCode"+strDate;
		this.tradeType = "11";
		this.payType = "一次性付清";
		this.createTime = new Date();
		this.updateTime = new Date();
		ToCaseParticipant toCaseParticipant = new ToCaseParticipant();
		toCaseParticipant.setCcaiCode(this.ccaiId);
		toCaseParticipant.setCaseCode(primaryCaseCode);
		toCaseParticipant.setPosition("agent");
		toCaseParticipant.setUserName("zenglong");
		toCaseParticipant.setRealName("曾龙");
		toCaseParticipant.setMobile("17720100958");
		toCaseParticipant.setGrpCode("022C527");
		toCaseParticipant.setGrpName("昆明路分行1组");
		toCaseParticipant.setGrpMgrUsername(null);
		toCaseParticipant.setGrpMgrRealname(null);
		toCaseParticipant.setGrpMgrRealname(null);
		toCaseParticipant.setAvailable("Y");
		participants.add(toCaseParticipant);
		
		ToCaseParticipant toCaseParticipant1 = new ToCaseParticipant();
		toCaseParticipant1.setCaseCode(primaryCaseCode);
		toCaseParticipant1.setCcaiCode(this.ccaiId);
		toCaseParticipant1.setPosition("warrant");
		toCaseParticipant1.setUserName("xiefei2");
		toCaseParticipant1.setRealName("谢飞");
		toCaseParticipant1.setMobile("17720100958");
		toCaseParticipant1.setGrpCode("022A458");
		toCaseParticipant1.setGrpName("交易按揭1组");
		toCaseParticipant1.setGrpMgrUsername("xiefeifei");
		toCaseParticipant1.setGrpMgrRealname("谢飞飞");
		toCaseParticipant1.setGrpMgrMobile("18622064168");
		toCaseParticipant.setAvailable("Y");
		participants.add(toCaseParticipant1);
		
		ToCaseParticipant toCaseParticipant2 = new ToCaseParticipant();
		toCaseParticipant2.setCaseCode(primaryCaseCode);
		toCaseParticipant2.setCcaiCode(this.ccaiId);
		toCaseParticipant2.setPosition("loan");
		toCaseParticipant2.setUserName("xiefei2");
		toCaseParticipant2.setRealName("谢飞");
		toCaseParticipant2.setMobile("17720100958");
		toCaseParticipant2.setGrpCode("022A458");
		toCaseParticipant2.setGrpName("交易按揭1组");
		toCaseParticipant2.setGrpMgrUsername("xiefeifei");
		toCaseParticipant2.setGrpMgrRealname("谢飞飞");
		toCaseParticipant2.setGrpMgrMobile("18622064168");
		toCaseParticipant.setAvailable("Y");
		participants.add(toCaseParticipant2);
		this.participants = participants;
		ToPropertyInfo toPropertyInfo2 = new ToPropertyInfo();
		toPropertyInfo2.setCaseCode(primaryCaseCode);
		toPropertyInfo2.setPropertyCode("CCAI_HOUSE_CODE");
		toPropertyInfo2.setDistCode("120101");
		toPropertyInfo2.setPropertyAddr("天津**区*******小区*号楼*单元***室");
		toPropertyInfo2.setTotalFloor(20);
		toPropertyInfo2.setLocateFloor(5);
		toPropertyInfo2.setSquare(188d);
		toPropertyInfo2.setPropertyType("HOUSE_TYPE");
		toPropertyInfo2.setFinishYear(new Date());
		toPropertyInfo2.setComment("房屋描述...");
		this.toPropertyInfo = toPropertyInfo2;
		
		TgGuestInfo tgGuestInfo = new TgGuestInfo();
		tgGuestInfo.setCaseCode(primaryCaseCode);
		tgGuestInfo.setGuestCode("001");
		tgGuestInfo.setTransPosition("30006001");
		tgGuestInfo.setGuestName("业主");
		tgGuestInfo.setGuestPhone("13888888888");
		tgGuestInfo.setIdentifyType("certype");
		tgGuestInfo.setIdentifyNumber("111111111");
		guests.add(tgGuestInfo);
		
		TgGuestInfo tgGuestInfo1 = new TgGuestInfo();
		tgGuestInfo1.setCaseCode(primaryCaseCode);
		tgGuestInfo1.setGuestCode("002");
		tgGuestInfo1.setTransPosition("30006002");
		tgGuestInfo1.setGuestName("买家");
		tgGuestInfo1.setGuestPhone("13666666666");
		tgGuestInfo1.setIdentifyType("certype");
		tgGuestInfo1.setIdentifyNumber("22222222");
		guests.add(tgGuestInfo1);
		this.guests = guests;
		ToCcaiAttachment toCcaiAttachment = new ToCcaiAttachment();
		toCcaiAttachment.setCaseCode(primaryCaseCode);
		toCcaiAttachment.setFileName("测试用的合同..");
		toCcaiAttachment.setFileCat("jpg");
		toCcaiAttachment.setUrl("http://img10.360buyimg.com/imgzone/jfs/t5626/299/9549080912/409150/6aedaad4/59883014N0340711a.jpg");
		attachments.add(toCcaiAttachment);
		this.attachments = attachments;
		this.city = "120000";
	}
	public String getCaseCode() {
		return caseCode;
	}
	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}
	public String getCcaiId() {
		return ccaiId;
	}
	public void setCcaiId(String ccaiId) {
		this.ccaiId = ccaiId;
	}
	public String getCcaiCode() {
		return ccaiCode;
	}
	public void setCcaiCode(String ccaiCode) {
		this.ccaiCode = ccaiCode;
	}
	
	public String getLeadingProcessorId() {
		return leadingProcessorId;
	}
	public void setLeadingProcessorId(String leadingProcessorId) {
		this.leadingProcessorId = leadingProcessorId;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public List<ToCaseParticipant> getParticipants() {
		return participants;
	}
	public void setParticipants(List<ToCaseParticipant> participants) {
		this.participants = participants;
	}
	public ToPropertyInfo getToPropertyInfo() {
		return toPropertyInfo;
	}
	public void setToPropertyInfo(ToPropertyInfo toPropertyInfo) {
		this.toPropertyInfo = toPropertyInfo;
	}
	public List<TgGuestInfo> getGuests() {
		return guests;
	}
	public void setGuests(List<TgGuestInfo> guests) {
		this.guests = guests;
	}
	public List<ToCcaiAttachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<ToCcaiAttachment> attachments) {
		this.attachments = attachments;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	

}
