package com.centaline.trans.common.service.impl;

import com.aist.uam.auth.remote.UamSessionService;
import com.centaline.trans.common.entity.ToModuleSubscribe;
import com.centaline.trans.cases.exception.CaseException;
import com.centaline.trans.common.enums.SubscribeModuleType;
import com.centaline.trans.common.enums.SubscribeType;
import com.centaline.trans.common.repository.ToModuleSubscribeMapper;
import com.centaline.trans.common.service.ToModuleSubscribeService;
import com.centaline.trans.common.vo.ToModuleSubscribeVo;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by caoyuan7 on 2016/10/18.
 */
@Service
public class ToModuleSubscribeServiceImpl implements ToModuleSubscribeService {
    @Resource
    private UamSessionService uamSessionService;
    @Resource
    ToModuleSubscribeMapper toModuleSubscribeMapper;
    /**
     * 用户关注案件
     * @author caoy
     * @param toModuleSubscribeVo
     * @return
     */
    public void saveOrDeleteCaseSubscribe(ToModuleSubscribeVo toModuleSubscribeVo) throws CaseException{
        ToModuleSubscribe toModuleSubscribe = new ToModuleSubscribe();
        toModuleSubscribe = ValidateSubscribeParameter(toModuleSubscribeVo,toModuleSubscribe);//验证提交的参数
        toModuleSubscribe.setSubscriberId(uamSessionService.getSessionUser().getId());//关注人
        toModuleSubscribe.setModuleCode(toModuleSubscribeVo.getModuleCode());//关注项目主键
        toModuleSubscribe.setRemark(toModuleSubscribeVo.getRemark());//关注原本
        List<ToModuleSubscribe> toModuleSubscribeIsExistList = toModuleSubscribeMapper.findByUserAndModule(toModuleSubscribe);
        if(toModuleSubscribeVo.getIsSubscribe()){//关注项目
            if(toModuleSubscribeIsExistList.size()>0){
                throw new CaseException("您已经关注了此项目，请勿重复提交");
            }else{
                toModuleSubscribeMapper.saveModuleSubscribe(toModuleSubscribe);
            }
        }else{
            if(toModuleSubscribeIsExistList.size()==0){
                throw new CaseException("您还没有关注此项目，无法取消关注");
            }else{
                toModuleSubscribeMapper.deleteModuleSubscribe(toModuleSubscribe);
            }
        }
    }

    /**
     * 用户是否订阅此案件
     * @author caoy
     * @param caseCode
     * @param id
     * @param moduleType
     * @param subscribeType
     * @return
     */
    public boolean checkIsSubscribe(String caseCode, String id, String moduleType, String subscribeType) {
        ToModuleSubscribe toModuleSubscribe = new ToModuleSubscribe();
        toModuleSubscribe.setSubscriberId(uamSessionService.getSessionUser().getId());//关注人
        toModuleSubscribe.setModuleCode(caseCode);//关注项目主键
        toModuleSubscribe.setModuleType(moduleType);
        toModuleSubscribe.setSubscribeType(subscribeType);
        List<ToModuleSubscribe> toModuleSubscribeIsExistList = toModuleSubscribeMapper.findByUserAndModule(toModuleSubscribe);
        if(toModuleSubscribeIsExistList.size()>0){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 验证提交的订阅信息
     * @author caoy
     * @param toModuleSubscribeVo
     * @param toModuleSubscribe
     * @return
     * @throws CaseException
     */
    private ToModuleSubscribe ValidateSubscribeParameter(ToModuleSubscribeVo toModuleSubscribeVo, ToModuleSubscribe toModuleSubscribe) throws CaseException{
        if(StringUtil.isBlank(toModuleSubscribeVo.getModuleCode())){
            throw new CaseException("请选择您要关注的项目");
        }
        if(StringUtil.isBlank(uamSessionService.getSessionUser().getId())){
            throw new CaseException("用户已登出");
        }
        if (toModuleSubscribeVo.getIsSubscribe()==null){
            throw new CaseException("提交的参数有误");//false是取消关注操作，true是关注操作
        }
        switch (SubscribeModuleType.from(toModuleSubscribeVo.getModuleType())) {
            case CASE:toModuleSubscribe.setModuleType(SubscribeModuleType.CASE.getValue());break;
            default:throw new CaseException("提交的模块类型有误");
        }
        switch (SubscribeType.from(toModuleSubscribeVo.getSubscribeType())) {
            case COLLECTION:toModuleSubscribe.setSubscribeType(SubscribeType.COLLECTION.getValue());
                break;
            default:throw new CaseException("提交的操作类型有误");
        }
        return toModuleSubscribe;
    }

}
