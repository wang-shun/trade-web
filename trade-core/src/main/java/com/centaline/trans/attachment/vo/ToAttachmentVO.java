package com.centaline.trans.attachment.vo;

import java.util.ArrayList;
import java.util.List;

import com.centaline.trans.attachment.entity.ToAccesoryList;
import com.centaline.trans.attachment.entity.ToAttachment;

public class ToAttachmentVO {

	List<ToAccesoryList> toAccesoryList = new ArrayList<ToAccesoryList>();

	List<ToAttachment> attachmentList = new ArrayList<ToAttachment>();

	public List<ToAccesoryList> getToAccesoryList() {
		return toAccesoryList;
	}

	public void setToAccesoryList(List<ToAccesoryList> toAccesoryList) {
		this.toAccesoryList = toAccesoryList;
	}

	public List<ToAttachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<ToAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

}
