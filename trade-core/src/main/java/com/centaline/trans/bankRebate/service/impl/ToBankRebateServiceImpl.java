package com.centaline.trans.bankRebate.service.impl;

import com.centaline.trans.bankRebate.entity.ToBankRebateInfo;
import com.centaline.trans.bankRebate.repository.ToBankRebateInfoMapper;
import com.centaline.trans.bankRebate.vo.ToBankRebateInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.UamSessionService;
import com.centaline.trans.bankRebate.entity.ToBankRebate;
import com.centaline.trans.bankRebate.repository.ToBankRebateMapper;
import com.centaline.trans.bankRebate.service.ToBankRebateService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ToBankRebateServiceImpl implements ToBankRebateService {
	
	@Autowired
	ToBankRebateMapper toBankRebateMapper;
	@Autowired
	private ToBankRebateInfoMapper toBankRebateInfoMapper;


	@Override
	public void insertBankRebate(ToBankRebateInfoVO info) {
		toBankRebateMapper.insertSelective(info.getToBankRebate());
		List<ToBankRebateInfo> toBankRebateInfoList = info.getToBankRebateInfoList();
		for (ToBankRebateInfo toBankRebateInfo : toBankRebateInfoList) {
			toBankRebateInfo.setGuaranteeCompId(info.getToBankRebate().getGuaranteeCompId());
			toBankRebateInfoMapper.insertSelective(toBankRebateInfo);
		}
	}

	@Override
	public void deleteByGuaranteeCompId(String[] guaCompIds) {
		for(String compid : guaCompIds){
			toBankRebateInfoMapper.deleteRebateInfoByGuaranteeCompId(compid);
			toBankRebateMapper.deleteByGuaranteeCompId(compid);
		}
	}

	@Override
	public ToBankRebateInfoVO selectRebateByGuaranteeCompId(String guaCompId) {
		ToBankRebateInfoVO vo = new ToBankRebateInfoVO();
		vo.setToBankRebate(toBankRebateMapper.selectRebateByGuaranteeCompId(guaCompId));
		vo.setToBankRebateInfoList(toBankRebateInfoMapper.selectRebateInfoByGuaranteeCompId(guaCompId));
		return vo;
	}

	@Override
	public void deleteTobankRebateInfo(Long pkid) {
		toBankRebateInfoMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public void updateBankRebate(ToBankRebateInfoVO info) {
		toBankRebateMapper.updateByPrimaryKeySelective(info.getToBankRebate());
		for(ToBankRebateInfo bankRebateInfo : info.getToBankRebateInfoList()){
			if(bankRebateInfo!=null && bankRebateInfo.getPkid()!=null){
				toBankRebateInfoMapper.updateByPrimaryKeySelective(bankRebateInfo);
			}else if(bankRebateInfo!=null && StringUtils.isNotBlank(bankRebateInfo.getCcaiCode())){
				bankRebateInfo.setGuaranteeCompId(info.getToBankRebate().getGuaranteeCompId());
				toBankRebateInfoMapper.insertSelective(bankRebateInfo);
			}
		}
	}

}
