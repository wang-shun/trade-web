package com.centaline.trans.track.service;

import com.centaline.trans.comment.entity.ToCaseComment;

public interface TrackService {
	int buJian(ToCaseComment track, Boolean isNotifyCustomer);
}
