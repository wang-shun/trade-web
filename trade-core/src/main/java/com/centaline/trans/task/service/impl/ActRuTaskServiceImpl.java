package com.centaline.trans.task.service.impl;

import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.task.repository.ActRuTaskMapper;
import com.centaline.trans.task.service.ActRuTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by caoyuan7 on 2017/5/5.
 */
@Service
public class ActRuTaskServiceImpl implements ActRuTaskService {

    @Autowired
    ActRuTaskMapper actRuTaskMapper;
    @Override
    public List<TaskVo> getRuTask(String caseCode) {
        return actRuTaskMapper.getRuTask(caseCode);
    }
    
	@Override
	public List<TaskVo> getRuTaskByBizCode(String bizCode) {
		return actRuTaskMapper.getRuTaskByBizCode(bizCode);
	}
}
