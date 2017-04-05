package com.centaline.parportal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.centaline.parportal.mobile.mortgage.web.MortgageListController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParPortalApplicationTests {

	@Autowired
	private MortgageListController mortgageListController;

	@Test
	public void contextLoads() {
		String result = mortgageListController
				.caseList(1, 10, null, null, null);

		System.out.println(result);
	}
}
