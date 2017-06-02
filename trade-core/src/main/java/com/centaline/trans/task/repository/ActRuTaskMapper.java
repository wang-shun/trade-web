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

}
