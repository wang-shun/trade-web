package com.centaline.trade.caseTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.centaline.api.ccai.cases.vo.*;
import com.centaline.api.common.vo.CcaiServiceResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.centaline.api.ApiApplication;
import com.centaline.api.ccai.cases.vo.CcaiImportParticipant;
import com.centaline.trans.utils.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ApiApplication.class)
public class ImportApiTest {
	
	MockMvc mvc;
	MockHttpSession session;
	
	@Autowired
	WebApplicationContext context;
	
	//用于转换返回的json
	ObjectMapper objectMapper = null;
	@Before //每个测试方法前都会运行
	public void init() throws Exception{
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		objectMapper = new ObjectMapper();
	}
	/**
	 * 用于测试返回json数据的api接口
	 * 
	 * 返回html页面的方法 直接在页面进行直观的查看 不进行测试 只测试对应的Service即可
	 * 
	 * 未进行登录 也可以测试
	 * @throws Exception
	 * 
	 * 使用该标签@WithMockUser @WithSecurityContext @WithUserDetails @WithAnonymousUser 可以注入测试时需要的用户信息
	 * 需要spring-security-test包 根据使用信息来设置标签的值，并不检验正确性
	 */
	@Test
//	@WithMockUser(username="zhangsan",password="222")
	public void testImport() throws Exception{
		String uri = "/api/ccai/v1/caseSync.json";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(getTestJsonData()))
                .andReturn();  
        String content = mvcResult.getResponse().getContentAsString();
        int state =  mvcResult.getResponse().getStatus();
        System.out.println(state+" and result:"+content);
        //将Json转为对象
        CcaiServiceResult result = objectMapper.readValue(content, CcaiServiceResult.class);
        System.out.println(result);
	}
	
	public String getTestJsonData(){
		
		CcaiImportCase acase = new CcaiImportCase();
		//案件基本信息
		List<CcaiImportParticipant> participants = new ArrayList<>();
		//经纪人信息
		CcaiImportParticipant agent = new CcaiImportParticipant();
		agent.setPosition("agent");
		agent.setAssignId("agentAssignId");
		agent.setUserName("zenglong");
		agent.setRealName("曾龙");
		// agent.setMobile("17720100958");
		agent.setGrpCode("022C527");
		agent.setGrpName("昆明路分行1组");
		agent.setGrpMgrUserName("daikang");
		participants.add(agent);
		
		//分行秘书信息
		CcaiImportParticipant secretary = new CcaiImportParticipant();
		secretary.setPosition("secretary");
		secretary.setUserName("ahk-zhaoxu01");
		secretary.setRealName("赵旭");
		// secretary.setMobile("17720100958");
		participants.add(secretary);
		
//		//权证专员信息
		CcaiImportParticipant warrant = new CcaiImportParticipant();
		warrant.setPosition("warrant");
		warrant.setAssignId("warrantAssignId");
		warrant.setUserName("shangfei");
		warrant.setRealName("尚飞");
		// warrant.setMobile("17720100958");
		warrant.setGrpCode("022A458");
		warrant.setGrpName("交易按揭1组");
		warrant.setGrpMgrUserName("wangchx");
		// warrant.setGrpMgrRealName("王姝予");
		// warrant.setGrpMgrMobile("18622064168");
		participants.add(warrant);
		acase.setParticipants(participants);
//		//案件物业信息
		CcaiImportCaseProperty property = new CcaiImportCaseProperty();
		property.setAddress("天津**区*******小区*号楼*单元***室");
		property.setId("CCAI_HOUSE_ID");
		property.setCode("CCAI_HOUSE_CODE");
		// property.setFinishYear("2012");
		// property.setTotalFloor(20);
		// property.setLocateFloor(5);
		// property.setSquare(123.04);
		// property.setPropertyType("HOUSE_TYPE");//房屋类型
		// property.setComment("房屋描述...");
		property.setDistrict("120101");
		acase.setProperty(property);
//		//客户信息 上家
		CcaiImportCaseGuest guest1 = new CcaiImportCaseGuest();
		guest1.setId("001");
		guest1.setName("业主");
		guest1.setPosition("30006001");//上家  业主
		// guest1.setCertType("certype");//证件类型编码
		// guest1.setCertCode("111111111");//证件类型编号
		guest1.setMobile("13888888888");//业主手机号
		CcaiImportCaseGuest guest2 = new CcaiImportCaseGuest();
		guest2.setId("002");
		guest2.setName("买家");
		guest2.setPosition("30006002");//上家  业主
		// guest2.setCertType("certype");//证件类型编码
		// guest2.setCertCode("22222222");//证件类型编号
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
		acase.setCreateTime(new Date());
		acase.setUpdateTime(new Date());
		acase.setReportTime(new Date());
		acase.setTradeType("11");//交易类型
		acase.setPayType("11");//付款类型
		acase.setCity("120000");
		acase.setApplyId("12333");
		ObjectMapper mapper = new ObjectMapper();
		try {
			String str = mapper.writeValueAsString(acase);
			System.out.println(str);
			return str;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
