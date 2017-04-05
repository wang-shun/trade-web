package com.centaline.parportal;

import com.centaline.parportal.mobile.mortgage.web.MortgageController;
import com.centaline.parportal.mobile.mortgage.web.MortgageListController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParPortalApplicationTests {

	@Autowired
	private MortgageListController mortgageListController;

	@Autowired
	private MortgageController mortgageController;

	@Test
	public void contextLoads() {

		String result = mortgageListController.caseList(1, 10, null, null,
				"ff80808158bd58c10158bda37f100020", null);

	}

}
