package com.centaline.parportal.mobile.attachment.web;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.attachment.service.ToAttachmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by caoyuan7 on 2017/5/17.
 */
@Controller
@RequestMapping(value = "/attachment")
public class AttachmentController {

    private Logger logger = Logger.getLogger(AttachmentController.class);


    @Autowired
    private ToAttachmentService toAttachmentService;

    @RequestMapping(value = "delAttachmentByFileAddress",method = RequestMethod.POST)
    @ResponseBody
    public Object delAttachmentByFileAdress(String preFileAdress) {
        AjaxResponse<String> response = new AjaxResponse<String>();
        try {
            toAttachmentService.deleteByFileAdress(preFileAdress);
            response.setSuccess(true);
            response.setMessage("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setSuccess(false);
            response.setMessage("删除失败！");
        }
        return response;
    }
}
