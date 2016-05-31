package com.centaline.trans.common.service;

import java.util.List;

import com.centaline.trans.common.entity.ToAttachment;
import com.centaline.trans.common.vo.FileUploadVO;

public interface ToAttachmentService {

	public void saveAttachment(FileUploadVO fileUploadVO);
	
	public List<ToAttachment> quereyAttachments(ToAttachment toAttachment);
	
	public boolean delAttachment(List<Long> pkIdArr);

	void saveToAttachment(ToAttachment toAttachment);

	/**
	 * 保存附件(图片)信息
	 * @param toAttachment
	 * @return
	 */
	public int saveFiles(ToAttachment toAttachment);

	/**
	 * 查询附件信息
	 * @param caseCode
	 * @return
	 */
	public List<ToAttachment> findToAttachmentByCaseCode(String caseCode);

	/**
	 * 删除图片
	 * @param pkid
	 * @return
	 */
	public int delFilesByPkid(Long pkid);

}
