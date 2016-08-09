package com.centaline.trade.eloan;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.centaline.trans.eloan.entity.ToEloanCase;
import com.centaline.trans.eloan.repository.ToEloanCaseMapper;
import com.centaline.trans.engine.annotation.TaskOperate;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;

@ActiveProfiles("junit" )
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/applicationContext.xml"})
public class TestEloanService {
	
	@Autowired
	private ProcessInstanceService processInstanceService; 
	@Autowired
	private TaskService taskService; 
	@Autowired
	private ToEloanCaseMapper toEloanCaseMapper; 
	 
    @Test
    public void test1() {
    	// 保存申请数据
    	//产品类型、案件绑定、合作机构、客户姓名、客户电话、申请金额、申请时间、申请期数
    	//转介人姓名、转介人员工编号、合作人姓名、合作人员工编号、产品部合作人、分成比例贷款。
    	//E+编号
    	ToEloanCase tEloanCase = new ToEloanCase();
    	tEloanCase.setLoanSrvCode("001");
    	tEloanCase.setCaseCode("AJ-2016-0709");
    	tEloanCase.setFinOrgCode("中国");
    	tEloanCase.setCustName("简佳敏");
    	tEloanCase.setCustPhone("123455");
    	tEloanCase.setEloanCode("E-001");
    	toEloanCaseMapper.insertSelective(tEloanCase);
    	
    	StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId("EloanProcess:1:663962", "E-001");
    	PageableVo pageableVo = taskService.listTasks(processInstance.getId(), false);
    	List<TaskVo> taskList = pageableVo.getData();
    	for(TaskVo task : taskList) {
    		taskService.claim(task.getId()+"", "申请人甲");
    		taskService.complete(task.getId()+"");
    	}
    	
    }
    
    
    
    

}
