package com.centaline.trans.common.repository;

import com.centaline.trans.common.entity.ToModuleSubscribe;
import com.centaline.trans.common.MyBatisRepository;

import java.util.List;


/**
 * Created by caoyuan7 on 2016/10/18.
 */
@MyBatisRepository
public interface ToModuleSubscribeMapper {

    List<ToModuleSubscribe> findByUserAndModule(ToModuleSubscribe toModuleSubscribe);

    void saveModuleSubscribe(ToModuleSubscribe toModuleSubscribeVo);

    void deleteModuleSubscribe(ToModuleSubscribe toModuleSubscribeVo);
}
