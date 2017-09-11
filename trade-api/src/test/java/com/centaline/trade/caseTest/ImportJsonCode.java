package com.centaline.trade.caseTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.centaline.api.ccai.cases.vo.*;
import org.junit.Test;

import com.centaline.api.ccai.cases.vo.CcaiImportParticipant;
import com.centaline.trans.utils.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
public class ImportJsonCode {
	
	/**
	 * 根据导入对象生成
	 * 最少导入JSON
	 */
	@Test
	public void getTestJsonData(){
		CcaiImportCase acase = new CcaiImportCase();
		//案件基本信息
		List<CcaiImportParticipant> participants = new ArrayList<>();
		//经纪人信息
		CcaiImportParticipant agent = new CcaiImportParticipant();
		agent.setAssignId("agentAssignId");
		agent.setPosition("agent");
		agent.setUserName("zenglong");
		agent.setRealName("曾龙");
		agent.setMobile("17720100958");
		agent.setGrpCode("022C527");
		agent.setGrpName("昆明路分行1组");
		agent.setGrpMgrUserName("ahk-zhaoxu01");
		participants.add(agent);
		//分行秘书信息
		CcaiImportParticipant secretary = new CcaiImportParticipant();
		secretary.setAssignId("");
		secretary.setPosition("secretary");
		secretary.setUserName("ahk-zhaoxu01");
		secretary.setRealName("赵旭");
		secretary.setMobile("17720100958");
		participants.add(secretary);
		//权证专员信息
		CcaiImportParticipant warrant = new CcaiImportParticipant();
		warrant.setAssignId("warrantAssignId");
		warrant.setPosition("warrant");
		warrant.setUserName("shangfei");
		warrant.setRealName("尚飞");
		warrant.setMobile("17720100958");
		warrant.setGrpCode("022A458");
		warrant.setGrpName("交易按揭1组");
		warrant.setGrpMgrUserName("wangchx");
		warrant.setGrpMgrRealName("王姝予");
		warrant.setGrpMgrMobile("18622064168");
		participants.add(warrant);
		//贷款专员信息
		CcaiImportParticipant loan = new CcaiImportParticipant();
		loan.setPosition("loan");
		loan.setUserName("shangfei");
		loan.setRealName("尚飞");
		loan.setMobile("17720100958");
		loan.setGrpCode("022A458");
		loan.setGrpName("交易按揭1组");
		loan.setGrpMgrUserName("wangchx");
		loan.setGrpMgrRealName("王姝予");
		loan.setGrpMgrMobile("18622064168");
		participants.add(loan);
		acase.setParticipants(participants);
		//案件物业信息
		CcaiImportCaseProperty property = new CcaiImportCaseProperty();
		property.setAddress("天津**区*******小区*号楼*单元***室");
		property.setId("CCAI_HOUSE_ID");
		property.setCode("CCAI_HOUSE_CODE");
		property.setFinishYear("2012");
		property.setTotalFloor(20);
		property.setLocateFloor(5);
		property.setSquare(123.04);
		property.setPropertyType("普通住宅（私产）");//房屋类型
		property.setComment("房屋描述...");
		property.setDistrict("120101");
		acase.setProperty(property);
		//客户信息 上家
		CcaiImportCaseGuest guest1 = new CcaiImportCaseGuest();
		guest1.setId("001");
		guest1.setName("业主");
		guest1.setPosition("30006001");//上家  业主
		guest1.setCertType("certype");//证件类型编码
		guest1.setCertCode("111111111");//证件类型编号
		guest1.setMobile("13888888888");//业主手机号
		CcaiImportCaseGuest guest2 = new CcaiImportCaseGuest();
		guest2.setId("002");
		guest2.setName("买家");
		guest2.setPosition("30006002");//上家  业主
		guest2.setCertType("certype");//证件类型编码
		guest2.setCertCode("22222222");//证件类型编号
		guest2.setMobile("13666666666");//业主手机号
		List<CcaiImportCaseGuest> guests = new ArrayList<>();
		guests.add(guest1);guests.add(guest2);
		acase.setGuests(guests);
		//案件附件信息
		CcaiImportAttachment attachment = new CcaiImportAttachment();
		attachment.setId("1");
		attachment.setName("合同");
		attachment.setUrl("http://........");
		attachment.setType("jpg");
		attachment.setUploadTime(new Date());
		List<CcaiImportAttachment> attachments = new ArrayList<>();
		attachments.add(attachment);
		acase.setAttachments(attachments);
		//案件基本信息
		//模拟CCAI案件信息
		String sign = DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss");
		acase.setCcaiCode("CCAICODE"+sign);
		acase.setCcaiId("CCAIID"+sign);
		acase.setApplyId("CCAIFLOW"+sign);
		acase.setCreateTime(new Date());
		acase.setUpdateTime(new Date());
		acase.setReportTime(new Date());
		acase.setTradeType("0");//交易类型
		acase.setPayType("按揭贷款");//付款类型
		acase.setCity("120000");
		ObjectMapper mapper = new ObjectMapper();
		try {
			String str = mapper.writeValueAsString(acase);
			System.out.println(str);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
