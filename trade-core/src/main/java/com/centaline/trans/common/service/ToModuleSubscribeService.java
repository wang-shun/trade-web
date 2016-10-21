package com.centaline.trans.common.service;

import com.centaline.trans.cases.exception.CaseException;
import com.centaline.trans.common.vo.ToModuleSubscribeVo;

/**
 * Created by caoyuan7 on 2016/10/18.
 */
public interface ToModuleSubscribeService {

    /**
     * 用户关注案件
     * @author caoy
     * @param toModuleSubscribeVo
     * @return
     */
    void saveOrDeleteCaseSubscribe(ToModuleSubscribeVo toModuleSubscribeVo) throws CaseException;

    /**
     * 用户是否订阅此案件
     * @author caoy
     * @param caseCode
     * @param id
     * @param moduleType
     * @param subscribeType
     * @return
     */
    boolean checkIsSubscribe(String caseCode, String id, String moduleType, String subscribeType);
}
