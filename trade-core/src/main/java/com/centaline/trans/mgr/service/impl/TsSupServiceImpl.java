package com.centaline.trans.mgr.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.entity.TsSup;
import com.centaline.trans.mgr.repository.TsSupMapper;
import com.centaline.trans.mgr.service.TsFinOrgService;
import com.centaline.trans.mgr.service.TsSupService;
import com.centaline.trans.mgr.vo.TsSupVo;

@Service
public class TsSupServiceImpl implements TsSupService{

	@Autowired
	private TsSupMapper tsSupMapper;
	
	@Autowired
	private TsFinOrgService tsFinOrgService;
	
	@Override
	public void saveTsSup(TsSupVo tsSup) {
		
		TsSup tSup = tsSupMapper.findSupByFinOrgCodeAndSupCat(tsSup.getFinOrgCode(),tsSup.getSupCat(),tsSup.getPkid());
		if(tSup != null){
			throw new BusinessException("该类型的供应商已存在该编码！");
		}
		//修改供应商信息
		if(tsSup.getPkid() != null){
			TsSup sup = tsSupMapper.selectByPrimaryKey(tsSup.getPkid());
			sup.setContactName(tsSup.getContactName());
			sup.setContactPhone(tsSup.getContactPhone());
			sup.setFinOrgCode(tsSup.getFinOrgCode());
			sup.setRelFinOrgCode(tsSup.getRelFinOrgCode());
			sup.setSupCat(tsSup.getSupCat());
			
			tsSupMapper.updateByPrimaryKeySelective(sup);
			
		}else{
			//新增供应商信息
			TsSup sup = new TsSup();
			sup.setContactName(tsSup.getContactName());
			sup.setContactPhone(tsSup.getContactPhone());
			sup.setFinOrgCode(tsSup.getFinOrgCode());
			sup.setRelFinOrgCode(tsSup.getRelFinOrgCode());
			sup.setSupCat(tsSup.getSupCat());
			tsSupMapper.insertSelective(sup);
		}
	}

	@Override
	public TsSupVo findTsSupById(Long id) {
		TsSupVo vo = new TsSupVo();
		TsSup tsSup = tsSupMapper.selectByPrimaryKey(id);
		if(tsSup != null){
			vo.setPkid(tsSup.getPkid());
			vo.setContactName(tsSup.getContactName());
			vo.setContactPhone(tsSup.getContactPhone());
			vo.setFinOrgCode(tsSup.getFinOrgCode());
			vo.setRelFinOrgCode(tsSup.getRelFinOrgCode());
			vo.setSupCat(tsSup.getSupCat());
			TsFinOrg tsFinOrg = tsFinOrgService.findBankByFinOrg(tsSup.getFinOrgCode());
			if(tsFinOrg != null){
				vo.setFinOrgName(tsFinOrg.getFinOrgName());
			}
		}
		
		return vo;
	}

	@Override
	public void deleteTsSup(Long id) {
		tsSupMapper.deleteByPrimaryKey(id);	
	}
}
