package cn.author.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.common.entity.ToCcaiAttachment;
import com.centaline.trans.common.repository.ToCcaiAttachmentMapper;
import com.centaline.trans.eval.entity.ToEvaCommPersonAmount;
import com.centaline.trans.eval.entity.ToEvaCommissionChange;
import com.centaline.trans.eval.repository.ToEvaCommPersonAmountMapper;
import com.centaline.trans.eval.repository.ToEvaCommissionChangeMapper;
import com.centaline.trans.eval.service.ToEvaCommPersonAmountService;
import com.centaline.trans.eval.vo.EvalChangeCommVO;
import com.centaline.trans.task.entity.AuditCase;
import com.centaline.trans.task.repository.AuditCaseMapper;
import com.centaline.trans.workspace.entity.CacheGridParam;
/*
 * 使用注册来完成对profile的激活,
 * 传入对应的profile名字即可,可以传入produce或者dev
 */
@WebAppConfiguration
@ActiveProfiles({"development","jedis"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml", 
		"classpath*:com/aist/common/**/META-INF/beans.xml",
		"classpath*:com/aist/uam/**/META-INF/httpInvoke-beans.xml", 
		"classpath*:com/aist/uam/**/META-INF/beans.xml",
		"classpath*:com/aist/uam/auth/META-INF/shiro-beans.xml",
		"classpath*:com/centaline/trans/**/META-INF/core-beans.xml",
		"classpath*:com/centaline/trans/**/META-INF/web-beans.xml",
		"classpath*:com/aist/message/**/META-INF/beans.xml"})
public class EvalTest {
	
	@Autowired
    private ToEvaCommPersonAmountService toEvaCommPersonAmountService;	
	@Autowired
    private ToEvaCommissionChangeMapper toEvaCommissionChangeMapper;	
	@Autowired
    private ToCaseMapper toCaseMapper;	
	@Autowired
	private QuickGridService quickGridService;
	@Autowired
    private ToCaseParticipantMapper toCaseParticipantMapper;
	@Autowired
	private ToCcaiAttachmentMapper toCcaiAttachmentMapper;
	@Autowired
	private ToEvaCommPersonAmountMapper toEvaCommPersonAmountMapper;
	@Test
	public void testtoEvaCommPersonAmountService() throws Exception {
		String caseCode="caseCode123123";
		EvalChangeCommVO fullEvalChangeCommVO = toEvaCommPersonAmountService.getFullEvalChangeCommVO(caseCode);
		System.out.println(fullEvalChangeCommVO.toString());
	}
	@Test
	public void testtoEvaCommissionChangeMapper() throws Exception {
		ToEvaCommissionChange toEvaCommissionChange = new ToEvaCommissionChange();
		toEvaCommissionChange.setCaseCode("caseCode123123");
		toEvaCommissionChange.setChangeChargesItem("评估公司变更");
		toEvaCommissionChange.setChangeChargesCause("客户喜欢这家评估公司");
		toEvaCommissionChange.setChangeChargesType("type2");
		int insertSelective = toEvaCommissionChangeMapper.insertSelective(toEvaCommissionChange);
		System.out.println(insertSelective);
	}
	@Test
	public void testtoEvaCommPersonAmountMapper() throws Exception {
		ToEvaCommPersonAmount toEvaCommPersonAmount = new ToEvaCommPersonAmount();
		toEvaCommPersonAmount.setCaseCode("casecode1930349307");
		toEvaCommPersonAmount.setPosition("分成人01");
		toEvaCommPersonAmount.setShareAmount(new BigDecimal("123.23"));
		toEvaCommPersonAmount.setEmployeeName("xiefei2");
		int insertSelective = toEvaCommPersonAmountMapper.insertSelective(toEvaCommPersonAmount);
		System.out.println(insertSelective);
	}
	@Test
	public void testInsertEvalChangeCommVO() throws Exception {
		String caseCode="caseCode123123";
		ArrayList<ToEvaCommPersonAmount> list = new ArrayList<ToEvaCommPersonAmount>();
		for(int i=0;i<6;i++){
			ToEvaCommPersonAmount toEvaCommPersonAmount = new ToEvaCommPersonAmount();
			toEvaCommPersonAmount.setPosition("分成人");
			toEvaCommPersonAmount.setEmployeeName("小张");
			toEvaCommPersonAmount.setDepartment("天津开发1组");
			toEvaCommPersonAmount.setShareAmount(new BigDecimal("10000"));
			toEvaCommPersonAmount.setShareReason("介绍交易");
			toEvaCommPersonAmount.setCaseCode(caseCode);
			list.add(toEvaCommPersonAmount);
		}
		
		for(int i=0;i<3;i++){
			ToEvaCommPersonAmount toEvaCommPersonAmount = new ToEvaCommPersonAmount();
			toEvaCommPersonAmount.setPosition("合作人");
			toEvaCommPersonAmount.setEmployeeName("小李");
			toEvaCommPersonAmount.setDepartment("天津合作1组");
			toEvaCommPersonAmount.setShareAmount(new BigDecimal("10000"));
			toEvaCommPersonAmount.setShareReason("促进交易");
			toEvaCommPersonAmount.setCaseCode(caseCode);
			list.add(toEvaCommPersonAmount);
		}
		
		for(int i=0;i<2;i++){
			ToEvaCommPersonAmount toEvaCommPersonAmount = new ToEvaCommPersonAmount();
			toEvaCommPersonAmount.setPosition("过户权证");
			toEvaCommPersonAmount.setEmployeeName("小王");
			toEvaCommPersonAmount.setDepartment("天津权证1组");
			toEvaCommPersonAmount.setShareAmount(new BigDecimal("10000"));
			toEvaCommPersonAmount.setShareReason("实施交易");
			toEvaCommPersonAmount.setCaseCode(caseCode);
			list.add(toEvaCommPersonAmount);
		}
		for (ToEvaCommPersonAmount toEvaCommPersonAmount : list) {
			toEvaCommPersonAmountService.insertSelective(toEvaCommPersonAmount);			
		}
	}
	
	
}
