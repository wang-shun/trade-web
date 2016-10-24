package com.centaline.trans.common.web;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.exception.CaseException;
import com.centaline.trans.common.service.ToModuleSubscribeService;
import com.centaline.trans.common.vo.ToModuleSubscribeVo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by caoyuan7 on 2016/10/18.
 */
@Controller
@RequestMapping(value = "/subscribe")
public class ModuleSubscribeController {

    private Logger logger = Logger.getLogger(ModuleSubscribeController.class);
    @Resource
    private ToModuleSubscribeService toModuleSubscribeService;

    /**
     * 用户关注案件
     * @author caoy
     * @param toModuleSubscribeVo
     * @return
     */
    @RequestMapping(value = "/saveOrDeleteCaseSubscribe" , method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse saveCaseSubscribe(ToModuleSubscribeVo toModuleSubscribeVo){
       try{
           toModuleSubscribeService.saveOrDeleteCaseSubscribe(toModuleSubscribeVo);
           return AjaxResponse.success();
       }catch (CaseException e){
           logger.error(e.getMessage());
           return AjaxResponse.fail(e.getMessage());
       }
    }
}
