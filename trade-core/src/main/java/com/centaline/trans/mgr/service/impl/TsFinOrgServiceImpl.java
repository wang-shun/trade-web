package com.centaline.trans.mgr.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.centaline.trans.mgr.entity.TsBankEvaRelationship;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.entity.TsSup;
import com.centaline.trans.mgr.enums.SupCatEnum;
import com.centaline.trans.mgr.repository.TsBankEvaRelationshipMapper;
import com.centaline.trans.mgr.repository.TsFinOrgMapper;
import com.centaline.trans.mgr.repository.TsSupMapper;
import com.centaline.trans.mgr.service.TsFinOrgService;

@Service
public class TsFinOrgServiceImpl implements TsFinOrgService {

	@Autowired
	private TsFinOrgMapper tsFinOrgMapper;

	@Autowired
	private TsBankEvaRelationshipMapper tsBankEvaRelationshipMapper;

	@Autowired
	private TsSupMapper tsSupMapper;
	@Autowired
	private UamBasedataService uamBasedataService;

	@Override
	public void saveTsFinOrg(TsFinOrg tsFinOrg) {
		TsFinOrg org = tsFinOrgMapper.findTsFinOrgByFinOrgCode(
				tsFinOrg.getFinOrgCode(), tsFinOrg.getPkid());
		if (org != null) {
			throw new BusinessException("该供应商编码已存在！");
		}
		if (tsFinOrg.getPkid() != null) {

			tsFinOrgMapper.update(tsFinOrg);
		} else {

			tsFinOrgMapper.insert(tsFinOrg);
		}
	}

	@Override
	public List<TsFinOrg> findParentBankList(String flag,String tag) {
		if (StringUtils.equals(flag, "egu")) {
			String evaCompanyCode = "P00021";
			return tsFinOrgMapper.findEguParentBankList(evaCompanyCode,tag);
		} else {
			return tsFinOrgMapper.findParentBankList(tag);
		}
	}

	@Override
	public List<TsFinOrg> findBankListByParentCode(String flag,
			String faFinOrgCode,String tag) {
		List<TsFinOrg> result=null;
		if (StringUtils.equals(flag, "egu")) {
			String evaCompanyCode = "P00021";
		
			result= tsFinOrgMapper.findEguBankListByParentCode(faFinOrgCode,
					evaCompanyCode,tag);
		} else {
			result=tsFinOrgMapper.findBankListByParentCode(faFinOrgCode,tag);
		}
		Dict dict= uamBasedataService.findDictByType("yu_bank_co_level");
		result.parallelStream().forEach(fin ->{
			if(!StringUtils.isBlank(fin.getCoLevel())){
				if(dict!=null){
					dict.getChildren().stream().forEach(dic->{
						if(fin.getCoLevel().equals(dic.getCode())){
							fin.setCoLevelStr(dic.getName());
						}
					});
				}
			}
		});
		
		return result;
	}

	@Override
	public TsFinOrg findBankByFinOrg(String orgCode) {
		return tsFinOrgMapper.findBankByFinOrg(orgCode);
	}

	@Override
	public List<TsFinOrg> findEvaCompany() {

		return tsFinOrgMapper.findBySupCat(SupCatEnum.EVA_SUPPLIER.getCode());
	}

	@Override
	public List<TsFinOrg> findSpvCompany() {
		return tsFinOrgMapper.findBySupCat(SupCatEnum.MONEY_SUPPLIER.getCode());
	}

	@Override
	public List<TsFinOrg> findPrfLoanBank() {
		return tsFinOrgMapper.findBySupCat(SupCatEnum.PRF_SUPPLIER.getCode());
	}

	@Override
	public void deleteTsFinOrg(Long pkid) {

		TsFinOrg tsFinOrg = tsFinOrgMapper.findByPkid(pkid);
		if (tsFinOrg == null) {
			throw new BusinessException("未找到该金融机构！");
		}

		List<TsBankEvaRelationship> list = tsBankEvaRelationshipMapper
				.findByBankCode(tsFinOrg.getFinOrgCode());
		List<TsSup> tsSupList = tsSupMapper.findTsSupByFinOrgCode(tsFinOrg
				.getFinOrgCode());
		if (CollectionUtils.isNotEmpty(list)
				|| CollectionUtils.isNotEmpty(tsSupList)) {
			throw new BusinessException("该金融机构有关联的供应商或者是e估支持的机构，不能删除！");
		}

		tsFinOrgMapper.delete(pkid);
	}

	@Override
	public List<TsFinOrg> findBranchBank() {
		return tsFinOrgMapper.findBranchBank();
	}

	@Override
	public List<TsFinOrg> findAllFinOrg() {
		return tsFinOrgMapper.findAllFinOrg();
	}

	@Override
	public TsFinOrg findById(Long pkid) {
		return tsFinOrgMapper.findByPkid(pkid);
	}

	@Override
	public List<TsFinOrg> findFinCompany() {
		return tsFinOrgMapper.findBySupCat(SupCatEnum.FINANCE_LOAN_SUPPLIER
				.getCode());
	}
}
