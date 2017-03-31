package com.centaline.trans.track.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.comment.service.ToCaseCommentService;
import com.centaline.trans.stuff.service.StuffService;
import com.centaline.trans.track.service.TrackService;

@Service
public class TrackServiceImpl implements TrackService {
	@Autowired
	private ToCaseCommentService toCaseCommentService;
	@Autowired
	private StuffService stuffService;

	@Override
	public int buJian(ToCaseComment track, Boolean isNotifyCustomer) {
		//插入补件comment
		int resultCount = toCaseCommentService.insertToCaseComment(track);
		// 启动补件流程
		stuffService.reqStuff(track, isNotifyCustomer);
		return resultCount;
	}

}
