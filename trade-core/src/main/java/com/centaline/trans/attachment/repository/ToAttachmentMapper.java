package com.centaline.trans.attachment.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.attachment.entity.ToAttachment;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface ToAttachmentMapper {
    int deleteByPrimaryKey(Long pkid);
    
    int deleteByFileAdress(String preFileAdress);

    int insert(ToAttachment record);

    int insertSelective(ToAttachment record);

    ToAttachment selectByPrimaryKey(ToAttachment record);

    int updateByPrimaryKeySelective(ToAttachment record);

    int updateByPrimaryKey(ToAttachment record);
    
    List<ToAttachment> quereyAttachments(ToAttachment record); 
    
    List<ToAttachment> quereyAttachmentForMaterial(ToAttachment record);
    
    List<ToAttachment> quereyAttachmentForDetails(ToAttachment record); 
    
    Integer findAttachmentByCount(ToAttachment record);

    /**
     * 查询附件信息
     * @param caseCode
     * @return
     */
	List<ToAttachment> findToAttachmentByCaseCode(String caseCode);
	
	int setMainFlowVailable(String caseCode);
	
	int updateToAttachmentByCaseCode(String caseCode);
	
	int updateAvaliableAttachmentByProperty(ToAttachment record);
	/**
	 * 更新附件casecode
	 * @author hejf10
	 * @param caseCode
	 * @param newCaseCod
	 * @return
	 */
	int updateToAttachmentForCaseCodeByCaseCode(@Param("caseCode")String caseCode,@Param("newCaseCode")String newCaseCod);
	
	
}