/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.centaline.parportal.mobile.track.web;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centaline.parportal.mobile.mortgage.web.MortgageListController;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.comment.service.ToCaseCommentService;

/**
 * 
 * @author sstonehu
 * @version $Id: TrackController.java, v 0.1 2016年12月16日 上午1:51:50 sstonehu Exp $
 */
@RestController
@RequestMapping({ "mobile/track" })
public class TrackController {

    private static Logger        logger = LoggerFactory.getLogger(MortgageListController.class);

    @Autowired
    private ToCaseCommentService toCaseCommentService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public String addTrack(Model model, @RequestBody ToCaseComment track) throws IOException {
        int insertCount = toCaseCommentService.insertToCaseComment(track);

        if (insertCount <= 0) {
            //return new JSONObject().put("msg", "对不起,跟进保存失败，请稍后再试").toString();
            throw new IOException("对不起,跟进保存失败");
        } else {
            return new StringBuilder("{\"msg\":\"").append(track.getCaseCode()).append("跟进保存成功")
                .append("\"}").toString();
        }

        //        return new StringBuilder("{\"msg\":\"").append(track.getCaseCode()).append("跟进保存成功")
        //            .append("\"}").toString();
    }

}
