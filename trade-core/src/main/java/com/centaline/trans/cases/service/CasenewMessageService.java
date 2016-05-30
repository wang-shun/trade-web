package com.centaline.trans.cases.service;

import java.util.List;

import com.centaline.trans.cases.vo.CaseGuwenVo;
import com.centaline.trans.common.entity.TgGuestInfo;

public interface CasenewMessageService {

	
	/**
	 * 功能：新建案件信息推送
	 * @param ctmCode  案件编号
	 * @param agentCode  成交经纪人编号
	 * @param tgGuestInfo  客户[对象]
	 * @param propertyAddr  物业地址
	 * @param propetyCode  房屋编码
	 * @param requireProcessorId  请求处理人编号[交易顾问ID]
	 * @author zhangxb16
	 */
	public String insertCasenewMsg(String ctm_case_code, String agent_id, String agent_name, List<TgGuestInfo> guestList, String property_address, String property_code, String property_agent_id, String consult_id, String grp_code, String grp_name, String caseCode);
	
	
	public List<CaseGuwenVo> selectConsultInfo(String agentID);
	
	
	public String guwenList(String agentID);
	
}
