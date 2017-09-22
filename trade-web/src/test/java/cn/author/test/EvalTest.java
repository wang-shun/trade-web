package cn.author.test;

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
import com.centaline.trans.eval.entity.ToEvaCommissionChange;
import com.centaline.trans.eval.repository.ToEvaCommissionChangeMapper;
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
    private ToEvaCommissionChangeMapper toEvaCommissionChangeMapper;	
	@Autowired
    private ToCaseMapper toCaseMapper;	
	@Autowired
	private QuickGridService quickGridService;
	@Autowired
    private ToCaseParticipantMapper toCaseParticipantMapper;
	@Autowired
	private ToCcaiAttachmentMapper toCcaiAttachmentMapper;
	
	@Test
	public void toCCAIattachment() throws Exception {
		ToEvaCommissionChange toEvaCommissionChange = new ToEvaCommissionChange();
		toEvaCommissionChange.setCaseCode("casecode1930349305");
		toEvaCommissionChange.setChangeChargesItem("评估公司变更");
		toEvaCommissionChange.setChangeChargesCause("客户嫌太便宜了，要我们报高点价");
		int insertSelective = toEvaCommissionChangeMapper.insertSelective(toEvaCommissionChange);
		System.out.println(insertSelective);
	}
	
	
}
