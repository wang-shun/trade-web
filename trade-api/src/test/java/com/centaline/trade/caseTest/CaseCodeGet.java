package com.centaline.trade.caseTest;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aist.common.utils.PasswordHelper;
import com.aist.uam.basedata.Repository.impl.SeqValZookeeperRepository;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.centaline.api.ApiApplication;
import com.centaline.trans.utils.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ApiApplication.class)
public class CaseCodeGet {
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
    private SeqValZookeeperRepository seqValZookeeperRepository;
	@Test
	public void zookeeperGet(){
		try {
			//TODO 空指针异常，由于Client中使用了uamBasedataService 但是没有使用@Autowired 去注入
			String month =  DateUtil.getFormatDate(new Date(), "yyyyMM");
			String caseCodeGenerate = uamBasedataService.nextSeqVal("CASE_CODE", month);
			System.out.println(caseCodeGenerate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Ignore
	@Test
	public void test(){
		PasswordHelper h = new PasswordHelper();
		System.out.println(h.encryptPassword("DAZR2bvq0W", "123456", "wbcaiyx"));
	}
}
