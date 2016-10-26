package com.centaline.trans.common.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.entity.Pic;
import com.centaline.trans.common.entity.ToAttachment;
import com.centaline.trans.common.enums.AttachmentPartCodeEnum;
import com.centaline.trans.common.enums.ToPropertyResearchEnum;
import com.centaline.trans.common.repository.ToAttachmentMapper;
import com.centaline.trans.common.service.ToAttachmentService;
import com.centaline.trans.common.vo.FileUploadVO;
import com.centaline.trans.eloan.entity.RcRiskControl;
import com.centaline.trans.eloan.entity.ToRcAttachment;
import com.centaline.trans.eloan.repository.RcRiskControlMapper;
import com.centaline.trans.eloan.repository.ToRcAttachmentMapper;

@Service
public class ToAttachmentServiceImpl implements ToAttachmentService {


	@Autowired
	private ToAttachmentMapper toAttachmentMapper; 
	
	@Autowired
	private RcRiskControlMapper rcRiskControlMapper; 
	
	@Autowired
	private ToRcAttachmentMapper toRcAttachmentMapper; 
	
	@Override
	public void saveToAttachment(ToAttachment toAttachment){
		toAttachmentMapper.insert(toAttachment);
	}
	
	/**
	 * 保存附件改变
	 */
	@Override
	public void saveAttachment(FileUploadVO fileUploadVO) {
		List<String> preFileCodes = fileUploadVO.getFramePart();
		int size = preFileCodes.size();
		
		if(fileUploadVO.getPkIdArr() != null) {
			delAttachment(fileUploadVO.getPkIdArr());
		}
		
		for(int i=0; i<size; i++) {
			ToAttachment toAttachment = new ToAttachment();
			toAttachment.setCaseCode(fileUploadVO.getCaseCode());
			toAttachment.setPartCode(fileUploadVO.getPartCode());
			toAttachment.setFileName(fileUploadVO.getPicName().get(i));
			
			int length = toAttachment.getFileName().length();
			int index = toAttachment.getFileName().lastIndexOf(".");
			toAttachment.setFileCat(fileUploadVO.getPicName().get(i).substring(index+1, length));
			
			toAttachment.setPreFileAdress(fileUploadVO.getPictureNo().get(i));
			toAttachment.setPreFileCode(preFileCodes.get(i));
			if(toAttachmentMapper.findAttachmentByCount(toAttachment) == 0) {
				toAttachmentMapper.insertSelective(toAttachment);
				saveRcAttachment(fileUploadVO.getCaseCode(),fileUploadVO.getPartCode(),toAttachment.getPkid());
			}
		}
	}
	
	/***
	 *   风控维持附件表的一个关联关系
	 */
	private void saveRcAttachment(String caseCode,String partCode,Long toAttachmentPkId) {
		if(AttachmentPartCodeEnum.RISKCONTROL.getCode().equals(partCode)) {
			// 查询风控项目为强制公证
			RcRiskControl property = new RcRiskControl();
			property.setEloanCode(caseCode);
			property.setRiskType("forceRegister");
			List<RcRiskControl> rcRiskControlList = rcRiskControlMapper.getRiskControlByProperty(property);
			if(CollectionUtils.isNotEmpty(rcRiskControlList)) {
				ToRcAttachment record = new ToRcAttachment();
				record.setRiskControlId(rcRiskControlList.get(0).getPkid());
				record.setAttachmentId(toAttachmentPkId);
				toRcAttachmentMapper.insertSelective(record);
			}
		} else if(AttachmentPartCodeEnum.RISKCONTROL_CARD.getCode().equals(partCode)||AttachmentPartCodeEnum.RISKCONTROL_MORTGAGE.getCode().equals(partCode)) {
			RcRiskControl property = new RcRiskControl();
			property.setEloanCode(caseCode);
			property.setRiskType(partCode);
			List<RcRiskControl> rcRiskControlList = rcRiskControlMapper.getRiskControlByProperty(property);
			if(CollectionUtils.isNotEmpty(rcRiskControlList)) {
				ToRcAttachment record = new ToRcAttachment();
				record.setRiskControlId(rcRiskControlList.get(0).getPkid());
				record.setAttachmentId(toAttachmentPkId);
				toRcAttachmentMapper.insertSelective(record);
			}
		}
	}

	@Override
	public List<ToAttachment> quereyAttachments(ToAttachment toAttachment) {
		return toAttachmentMapper.quereyAttachments(toAttachment);
	}

	@Override
	public boolean delAttachment(List<Long> pkIdArr) {
		int b = 1;
		for(Long pkid:pkIdArr) {
			if(toAttachmentMapper.deleteByPrimaryKey(pkid) < 1) {
				b=0;
			}
		}
		return b==1;
	}

	@Override
	public int saveFiles(ToAttachment toAttachment) {
		List<Pic> picList = toAttachment.getPic();
		int t = 0;
		for (Pic pic : picList) {
			ToAttachment toAtta = new ToAttachment();
			toAtta.setPreFileAdress(pic.getPreFileAdress());//图片的地址(ID)
			toAtta.setFileName(pic.getFileName());//图片的名字
			toAtta.setCaseCode(toAttachment.getPrCode());//产调编号
			toAtta.setPreFileCode(ToPropertyResearchEnum.PROPERTY_RESEARCH_LETTER.getCode());//附件编码
			toAtta.setFileCat(ToPropertyResearchEnum.FILE_TYPE.getCode());//附件类型
			toAtta.setPartCode(ToPropertyResearchEnum.PROPERTY_RESEARCH.getCode());//环节编码
			
			t = toAttachmentMapper.insert(toAtta);
		}
		return t;
	}

	@Override
	public List<ToAttachment> findToAttachmentByCaseCode(String caseCode) {
		
		return toAttachmentMapper.findToAttachmentByCaseCode(caseCode);
	}

	@Override
	public int delFilesByPkid(Long pkid) {
		
		return toAttachmentMapper.deleteByPrimaryKey(pkid);
	}


}
