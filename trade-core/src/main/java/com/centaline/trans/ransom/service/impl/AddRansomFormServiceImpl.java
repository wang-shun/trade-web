package com.centaline.trans.ransom.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomFormVo;
import com.centaline.trans.ransom.entity.ToRansomPlanVo;
import com.centaline.trans.ransom.repository.AddRansomFormMapper;
import com.centaline.trans.ransom.repository.RansomMapper;
import com.centaline.trans.ransom.service.AddRansomFormService;
import com.centaline.trans.ransom.service.RansomListFormService;

@Service
@Transactional
public class AddRansomFormServiceImpl implements AddRansomFormService {

	@Autowired
	private AddRansomFormMapper addRansomFormMapper;
	@Autowired
	private RansomMapper ransomMapper;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired(required = true)
	private RansomListFormService ransomListFormService;
	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Override
	public int addRansomForm(List<ToRansomFormVo> list,String ransomCode) {
		
		try {
			SessionUser user = uamSessionService.getSessionUser();
			String caseCode = list.get(0).getCaseCode();
			List<ToRansomFormVo> ransomList = new ArrayList<ToRansomFormVo>();
			
			for (int i = 0; i < list.size(); i++) {
				ToRansomFormVo ransomFormVo = new ToRansomFormVo();

				ransomFormVo.setCaseCode(caseCode);
				ransomFormVo.setRansomCode(ransomCode);
				ransomFormVo.setSignTime(list.get(0).getSignTime());
				ransomFormVo.setPlanTime(list.get(0).getPlanTime());
				ransomFormVo.setFinOrgCode(list.get(i).getFinOrgCode());
				ransomFormVo.setMortgageType(list.get(i).getMortgageType());
				ransomFormVo.setDiyaType(list.get(i).getDiyaType());
				ransomFormVo
						.setLoanMoney((list.get(i).getLoanMoney()).multiply(new BigDecimal(Double.toString(10000.00))));
				ransomFormVo
						.setRestMoney((list.get(i).getRestMoney().multiply(new BigDecimal(Double.toString(10000.00)))));
				ransomFormVo.setCreateTime(new Date());
				ransomFormVo.setCreateUser(user.getId());
				ransomFormVo.setUpdateTime(new Date());
				ransomFormVo.setUpdateUser(user.getId());

				ransomList.add(ransomFormVo);
			}
			
			int count = addRansomFormMapper.insertRansomForm(ransomList );
			
			if (count > 0) {
				ToRansomPlanVo planVo = new ToRansomPlanVo();
				planVo.setRansomCode(ransomCode);
				planVo.setPartCode("APPLY");
				planVo.setEstPartTime(list.get(0).getPlanTime());
				planVo.setCreateUser(user.getId());
				planVo.setCreateTime(new Date());
				planVo.setUpdateTime(new Date());
				planVo.setUpdateUser(user.getId());
				
				ToRansomCaseVo trco = new ToRansomCaseVo();
				// 赎楼列表单插入数据
				trco.setRansomCode(ransomCode);
				trco.setCaseCode(caseCode);
				trco.setRansomStatus("1");
				trco.setRansomProperty("DEAL");
				trco.setBorrowerName(list.get(0).getBorrowerName());
				trco.setBorrowerTel(list.get(0).getBorrowerTel());
				trco.setBorroMoney((list.get(0).getBorroMoney().multiply(new BigDecimal(Double.toString(10000.00)))));
				trco.setAcceptTime(list.get(0).getPlanTime());
				trco.setCreateTime(new Date());
				trco.setCreateUser(user.getId());
				trco.setUpdateTime(new Date());
				trco.setUpdateUser(user.getId());

				ransomListFormService.addRansomDetail(trco);
				ransomListFormService.insertRansomPlanTimeInfo(planVo);
				
				return count;
			}else return count;
		} catch (Exception e) {
			logger.error("",e);
			return 0;
		}
		
	}


}
