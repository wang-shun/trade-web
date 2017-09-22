package com.centaline.trade.caseTest;

import java.util.ArrayList;
import java.util.List;

import com.centaline.api.ccai.vo.CaseImport;
import com.centaline.api.ccai.vo.CaseParticipantImport;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ApiApplication.class)
public class updateFlowApiTest {
	
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
	@Test
//	@WithMockUser(username="zhangsan",password="222")
	public void testUpdateNormal() throws Exception{
		String uri = "/api/ccai/v1/caseUpdate/flow.json";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(getTestNormalJsonData()))
                .andReturn();  
        String content = mvcResult.getResponse().getContentAsString();
        int state =  mvcResult.getResponse().getStatus();
        System.out.println(state+" and result:"+content);
        //将Json转为对象
        CcaiServiceResult result = objectMapper.readValue(content, CcaiServiceResult.class);
        System.out.println(result);
	}
	
	public String getTestNormalJsonData(){
		
		CaseImport acase = new CaseImport();
		//案件基本信息
		List<CaseParticipantImport> participants = new ArrayList<>();
		//金融权证
		CaseParticipantImport warrant = new CaseParticipantImport();
		warrant.setPosition("warrant");
		warrant.setUserName("zhanglei01");
		warrant.setRealName("张磊");
		warrant.setMobile("13821759451");
		warrant.setGrpCode("022A459");
		warrant.setGrpName("交易按揭2组");
		warrant.setGrpMgrUserName("liuhong");
		warrant.setGrpMgrRealName("刘宏");
		warrant.setGrpMgrMobile("13821188123");
		participants.add(warrant);
		
		//贷款权证专员信息
		CaseParticipantImport loan = new CaseParticipantImport();
		loan.setPosition("loan");
		loan.setUserName("shangfei");
		loan.setRealName("尚飞");
		loan.setMobile("17720100958");
		participants.add(loan);
		acase.setParticipants(participants);
		//案件基本信息
		//模拟CCAI案件信息
		String sign = "20170911160143";
		acase.setCcaiCode("CCAICODE"+sign);
		acase.setCcaiId("CCAIID"+sign);
		acase.setCity("123456");
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
