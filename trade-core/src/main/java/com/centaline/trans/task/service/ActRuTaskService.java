package com.centaline.trans.task.service;

import com.centaline.trans.engine.vo.TaskVo;

import java.util.List;

/**
 * Created by caoyuan7 on 2017/5/5.
 */
public interface ActRuTaskService {
    List<TaskVo> getRuTask(String caseCode);
    
    /**
     * 根据bizcode查询任务流程
     * @param bizCode
     * @return
     * @author jinwl6
     */
    List<TaskVo> getRuTaskByBizCode(String bizCode);
}
