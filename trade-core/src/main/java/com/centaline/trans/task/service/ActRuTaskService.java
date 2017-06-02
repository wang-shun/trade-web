package com.centaline.trans.task.service;

import com.centaline.trans.engine.vo.TaskVo;

import java.util.List;

/**
 * Created by caoyuan7 on 2017/5/5.
 */
public interface ActRuTaskService {
    List<TaskVo> getRuTask(String caseCode);
}
