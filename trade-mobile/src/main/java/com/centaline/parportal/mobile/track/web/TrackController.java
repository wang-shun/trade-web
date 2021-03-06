/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.centaline.parportal.mobile.track.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aist.common.exception.BusinessException;
import com.alibaba.fastjson.JSONObject;
import com.centaline.parportal.mobile.track.vo.CommentVo;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.comment.service.ToCaseCommentService;
import com.centaline.trans.common.vo.MobileHolder;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.stuff.enums.CommentType;
import com.centaline.trans.stuff.service.StuffService;
import com.centaline.trans.track.service.TrackService;
import com.centaline.trans.utils.BeanUtils;

/**
 * 
 * @author sstonehu
 * @version $Id: TrackController.java, v 0.1 2016年12月16日 上午1:51:50 sstonehu Exp
 *          $
 */
@RestController
@RequestMapping({ "mobile/track" })
public class TrackController {

	@Autowired
	private ToCaseCommentService toCaseCommentService;

	@Autowired
	private ToMortgageService toMortgageService;

	@Autowired
	private StuffService stuffService;
	@Autowired
	private TrackService trackService;

	@RequestMapping(value = "/add")
	@ResponseBody
	public JSONObject addTrack(Model model, @RequestBody CommentVo cmtVo) {

		ToCaseComment track = new ToCaseComment();
		// boolean isNofigyCustomer = cmt.isNotifyCustomer();
		BeanUtils.copyProperties(cmtVo, track);

		// 检查track的完整性。不完整时抛出业务异常
		this.checkTrackIntegrity(track);
		int resultCount = 0;

		// TODO: 避免switch-case写法，有空的时候修改
		switch (CommentType.valueOf(track.getType())) {
		case CMT:
			resultCount = toCaseCommentService.insertToCaseComment(track);
			break;
		case BUJIAN:
			Boolean isNotifyCustomer = cmtVo.getIsNotifyCustomer() != null ? cmtVo
					.getIsNotifyCustomer() : false;
			resultCount = trackService.buJian(track, isNotifyCustomer);
			break;
		case REJECT:
			break;
		case TRACK:
			resultCount = toMortgageService.addMortgageTrack4Par(track);
			break;
		default:
			break;
		}

		if (resultCount <= 0) {
			throw new BusinessException("对不起,跟进保存失败");
		} else {
			JSONObject json  = new JSONObject(); 
			json.put("msg", track.getCaseCode() + "跟进保存成功");
			return json;
		}

	}

	private boolean checkTrackIntegrity(ToCaseComment track) {

		if (null == track)
			throw new BusinessException("抱歉，提交的跟进为空");
		if (null == track.getSource())
			throw new BusinessException("抱歉，提交的跟进source为空,请联系技术支持");
		if (null == track.getType())
			throw new BusinessException("抱歉，提交的跟进type为空,请联系技术支持");
		if (null == track.getBizCode())
			throw new BusinessException("抱歉，提交的跟进bizCode为空,请联系技术支持");

		if (MobileHolder.getMobileUser() != null)
			track.setCreatorOrgId(MobileHolder.getMobileUser()
					.getServiceDepId());

		return false;
	}

}
