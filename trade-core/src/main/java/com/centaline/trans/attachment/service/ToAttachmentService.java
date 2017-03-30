package com.centaline.trans.attachment.service;

import java.util.List;

import com.centaline.trans.attachment.entity.ToAttachment;
import com.centaline.trans.common.vo.FileUploadVO;

public interface ToAttachmentService {

	public void saveAttachment(FileUploadVO fileUploadVO);
	
	public String saveAttachmentForMaterial(FileUploadVO fileUploadVO);
	
	public List<ToAttachment> quereyAttachments(ToAttachment toAttachment);
	
	public List<ToAttachment> quereyAttachmentForDetails(ToAttachment toAttachment);
	
	public List<ToAttachment> quereyAttachmentForMaterial(ToAttachment toAttachment);
	
	public boolean delAttachment(List<Long> pkIdArr);
	
	public boolean deleteByFileAdress(String preFileAdress);

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
	
	/**
	 * 设置主流程附件无效
	 * 
	 * @param caseCode
	 * @return
	 */
	public int setMainFlowVailable(String caseCode);
	
	public int updateToAttachmentByCaseCode(String caseCode);
	
	int updateAvaliableAttachmentByProperty(ToAttachment record);

}
