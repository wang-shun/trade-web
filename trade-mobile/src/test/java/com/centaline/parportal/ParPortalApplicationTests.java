package com.centaline.parportal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.centaline.parportal.mobile.eloancase.web.ELoanCaseController;
import com.centaline.parportal.mobile.mortgage.web.MortgageListController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParPortalApplicationTests {

	@Autowired
	private ELoanCaseController eLoanCaseController;

	@Autowired
	private MortgageListController mortgageListController;

	@Test
	public void contextLoads() {

		// String result = mortgageListController.caseList(1, 10, null, null,
		// "8a8493d45921d36901593e4adc95007b", null);

		String result = eLoanCaseController.list(1, 10, null, null,
				"8a8493d45921d56d01593fd036e100f1", null);

		System.out.println(result);
	}
}
