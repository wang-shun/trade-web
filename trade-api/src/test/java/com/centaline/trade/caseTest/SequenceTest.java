package com.centaline.trade.caseTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.centaline.api.ApiApplication;
import com.centaline.api.ccai.cases.service.CcaiService;
import com.centaline.api.ccai.cases.service.impl.CcaiServiceImpl;
import com.centaline.api.ccai.cases.vo.CcaiImportCase;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ApiApplication.class)
public class SequenceTest {
	@Autowired
	CcaiService service;
	@Test
	public void getSequenctTest(){
		CcaiServiceImpl c = (CcaiServiceImpl) service;
		//TODO 测试需要把方法改为public 反射无法进行自动注入mapper
//		System.out.println(c.getCaseCode(getTestDate()));
	}
	
	private CcaiImportCase getTestDate(){
		CcaiImportCase ca = new CcaiImportCase();
		ca.setCity("120000");
		return ca;
	}
	
}
