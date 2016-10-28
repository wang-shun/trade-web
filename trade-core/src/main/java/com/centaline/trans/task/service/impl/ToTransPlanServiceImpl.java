package com.centaline.trans.task.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.task.entity.ToTransPlan;
import com.centaline.trans.task.entity.ToTransPlanOrToPropertyInfo;
import com.centaline.trans.task.repository.ToTransPlanMapper;
import com.centaline.trans.task.service.ToTransPlanService;
import com.centaline.trans.task.vo.TransPlanVO;

@Service
public class ToTransPlanServiceImpl implements ToTransPlanService {

	@Autowired
	private ToTransPlanMapper toTransPlanMapper;
	
	@Override
	public boolean saveToTransPlan(TransPlanVO transPlanVO) {
		if(transPlanVO.getCaseCode()!=null && transPlanVO.getCaseCode().intern().length()!=0) {
			/**还贷      贷款结清*/
			ToTransPlan toTransPlanHd = new ToTransPlan();
			if(transPlanVO.getEstPartTimeHd() != null) {
				toTransPlanHd.setEstPartTime(transPlanVO.getEstPartTimeHd()); 
				toTransPlanHd.setCaseCode(transPlanVO.getCaseCode());
				if(transPlanVO.getPkidHd() != null) {
					toTransPlanHd.setPkid(transPlanVO.getPkidHd());
					toTransPlanHd.setCaseCode(transPlanVO.getCaseCode());
					toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanHd);
				} else {
					toTransPlanHd.setPartCode("LoanClose");
					if(toTransPlanMapper.findTransPlan(toTransPlanHd) == null) {
						toTransPlanMapper.insertSelective(toTransPlanHd);
					}
				}
			}
			/**审税*/
			ToTransPlan toTransPlanTr = new ToTransPlan();
			toTransPlanTr.setEstPartTime(transPlanVO.getEstPartTimeTr()); 
			toTransPlanTr.setCaseCode(transPlanVO.getCaseCode());
			if(transPlanVO.getPkidTr() != null) {
				toTransPlanTr.setPkid(transPlanVO.getPkidTr());
				toTransPlanTr.setCaseCode(transPlanVO.getCaseCode());
				toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanTr);
			} else {
				toTransPlanTr.setPartCode("TaxReview");
				if(toTransPlanMapper.findTransPlan(toTransPlanTr) == null) {
					toTransPlanMapper.insertSelective(toTransPlanTr);
				}
			}
			/**过户*/
			ToTransPlan toTransPlanGh = new ToTransPlan();
			toTransPlanGh.setEstPartTime(transPlanVO.getEstPartTimeGh()); 
			toTransPlanGh.setCaseCode(transPlanVO.getCaseCode());
			if(transPlanVO.getPkidGh() != null) {
				toTransPlanGh.setPkid(transPlanVO.getPkidGh());
				toTransPlanGh.setCaseCode(transPlanVO.getCaseCode());
				toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanGh);
			} else {
				toTransPlanGh.setPartCode("Guohu");
				if(toTransPlanMapper.findTransPlan(toTransPlanGh) == null) {
					toTransPlanMapper.insertSelective(toTransPlanGh);
				}
			}
			/**领证*/
			ToTransPlan toTransPlanLz = new ToTransPlan();
			toTransPlanLz.setEstPartTime(transPlanVO.getEstPartTimeLz()); 
			toTransPlanLz.setCaseCode(transPlanVO.getCaseCode());
			if(transPlanVO.getPkidLz() != null) {
				toTransPlanLz.setPkid(transPlanVO.getPkidLz());
				toTransPlanLz.setCaseCode(transPlanVO.getCaseCode());
				toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanLz);
			} else {
				toTransPlanLz.setPartCode("HouseBookGet");
				if(toTransPlanMapper.findTransPlan(toTransPlanLz) == null) {
					toTransPlanMapper.insertSelective(toTransPlanLz);
				}
			}
			
			/**放款*/
			ToTransPlan toTransPlanFk = new ToTransPlan();
			if(transPlanVO.getEstPartTimeFk() != null) {
				toTransPlanFk.setEstPartTime(transPlanVO.getEstPartTimeFk()); 
				toTransPlanFk.setCaseCode(transPlanVO.getCaseCode());
				if(transPlanVO.getPkidFk() != null) {
					toTransPlanFk.setPkid(transPlanVO.getPkidFk());
					toTransPlanFk.setCaseCode(transPlanVO.getCaseCode());
					toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanFk);
				} else {
					toTransPlanFk.setPartCode("LoanRelease");
					if(toTransPlanMapper.findTransPlan(toTransPlanFk) == null) {
						toTransPlanMapper.insertSelective(toTransPlanFk);
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public TransPlanVO findTransPlanByCaseCode(String caseCode) {
		List<ToTransPlan> list = toTransPlanMapper.findTransPlanByCaseCode(caseCode);
		TransPlanVO transPlanVO = new TransPlanVO();
		for(ToTransPlan toTransPlan:list) {
			if(toTransPlan.getPartCode().equals("LoanClose")){/*还贷，贷款结清*/
				transPlanVO.setEstPartTimeHd(toTransPlan.getEstPartTime());
				transPlanVO.setPkidHd(toTransPlan.getPkid());
			} else if(toTransPlan.getPartCode().equals("HouseBookGet")){/*领证*/
				transPlanVO.setEstPartTimeLz(toTransPlan.getEstPartTime());
				transPlanVO.setPkidLz(toTransPlan.getPkid());
			} else if(toTransPlan.getPartCode().equals("TaxReview")){/*审税*/
				transPlanVO.setEstPartTimeTr(toTransPlan.getEstPartTime());
				transPlanVO.setPkidTr(toTransPlan.getPkid());
			} else if(toTransPlan.getPartCode().equals("LoanRelease")){/*放款*/
				transPlanVO.setEstPartTimeFk(toTransPlan.getEstPartTime());
				transPlanVO.setPkidFk(toTransPlan.getPkid());
			} else if(toTransPlan.getPartCode().equals("Guohu")){/*过户*/
				transPlanVO.setEstPartTimeGh(toTransPlan.getEstPartTime());
				transPlanVO.setPkidGh(toTransPlan.getPkid());
			}
		}
		return transPlanVO;
	}

	@Override
	public ToTransPlan findTransPlan(ToTransPlan toTransPlan) {
		return toTransPlanMapper.findTransPlan(toTransPlan);
	}

	@Override
	public Boolean updateTransPlan(ToTransPlan toTransPlan) {
		if(toTransPlanMapper.findTransPlan(toTransPlan) != null) {
			if(toTransPlanMapper.updateTransPlanSelective(toTransPlan) > 0) {
				return true;
			}
		} else {
			if(toTransPlanMapper.insertSelective(toTransPlan) > 0) {
				return true;
			};
		}
		return false;
	}

	@Override
	public List<ToTransPlanOrToPropertyInfo> getToTransPlanByUserId(String leadingProcessId) {
		List<ToTransPlanOrToPropertyInfo> toTransPlanList = toTransPlanMapper.getToTransPlanByUserId(leadingProcessId);
		return toTransPlanList;
	}

	@Override
	public List<ToTransPlan> queryPlansByCaseCode(String caseCode) {
		return toTransPlanMapper.findTransPlanByCaseCode(caseCode);
	}

	@Override
	public int updateByPrimaryKeySelective(ToTransPlan record) {
		return toTransPlanMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteTransPlansByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return toTransPlanMapper.deleteTransPlansByCaseCode(caseCode);
	}

	@Override
	public int insertSelective(ToTransPlan record) {
		// TODO Auto-generated method stub
		return toTransPlanMapper.insertSelective(record);
	}

	@Override
	public List<ToTransPlanOrToPropertyInfo> getToTransPlanByDictOrUserId(
			List<String> dictCodeList, String id) {
		return toTransPlanMapper.getToTransPlanByDictOrUserId(dictCodeList,id);
	
	}

	@Override
	public ToTransPlan selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return toTransPlanMapper.selectByPrimaryKey(pkid);
	}

}
