package com.centaline.trans.task.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.centaline.trans.task.entity.TsPrResearchMap;
import com.centaline.trans.task.repository.TsPrResearchMapMapper;
import com.centaline.trans.task.service.TsPrResearchMapService;

@Service
public class TsPrResearchMapServiceImpl implements TsPrResearchMapService{

	@Autowired
	private TsPrResearchMapMapper tsPrResearchMapMapper;
	
	@Override
	public void saveTsPrResearchMap(TsPrResearchMap tsPrResearchMap) {
		List<TsPrResearchMap> list = null;
		if(tsPrResearchMap.getPkid() == null){
			list = tsPrResearchMapMapper.findByDistCodeAndYuDistCode(null,tsPrResearchMap.getDistCode(),null);
			if(CollectionUtils.isNotEmpty(list)){
				throw new BusinessException("已存在该行政区域，请选择修改！");
			}
		}


		list = tsPrResearchMapMapper.findByDistCodeAndYuDistCode(tsPrResearchMap.getPkid(),tsPrResearchMap.getDistCode(),tsPrResearchMap.getYuDistCode());
		if(CollectionUtils.isNotEmpty(list)){
			throw new BusinessException("该部门已经存在该行政区域！");
		}
		if(tsPrResearchMap.getPkid() == null){
			tsPrResearchMapMapper.insertSelective(tsPrResearchMap);
		}else{
			tsPrResearchMapMapper.updateByPrimaryKeySelective(tsPrResearchMap);
		}
		
	}

	@Override
	public TsPrResearchMap findById(Long pkid) {
		return tsPrResearchMapMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public void deleteTsPrResearchMap(Long pkid) {
		tsPrResearchMapMapper.deleteByPrimaryKey(pkid);
		
	}

	@Override
	public List<TsPrResearchMap> getTsPrResearchMapByProperty(TsPrResearchMap tsPrResearchMap) {
		return tsPrResearchMapMapper.getTsPrResearchMapByProperty(tsPrResearchMap);
	}

}
