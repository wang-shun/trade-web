package com.centaline.trans.task.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.engine.vo.TaskVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by caoyuan7 on 2017/5/5.
 */
@MyBatisRepository
public interface ActRuTaskMapper {

    List<TaskVo> getRuTask(@Param("caseCode") String caseCode);
    
    /**
     * 根据bizcode查询任务流程
     * @param bizCode
     * @return
     * @author jinwl6
     */
    List<TaskVo> getRuTaskByBizCode(@Param("bizCode") String bizCode);

}
