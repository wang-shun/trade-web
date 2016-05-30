package com.centaline.trans.common.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.centaline.trans.common.entity.ToAccesoryList;
import com.centaline.trans.common.repository.ToAccesoryListMapper;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.common.vo.AccsoryListVO;

@Service
public class ToAccesoryListServiceImpl implements ToAccesoryListService {

	@Autowired
	private ToAccesoryListMapper toAccesoryListMapper;
	@Autowired
	private UamBasedataService uamBasedataService;
	
	@Override
	public List<ToAccesoryList> qureyToAccesoryList(
			ToAccesoryList toAccesoryList) {
		return toAccesoryListMapper.qureyToAccesoryList(toAccesoryList);
	}

	@Override
	public String findAccesoryNameByCode(String accessoryCode) {
		return toAccesoryListMapper.findAccessoryNameByCode(accessoryCode);
	}

	@Override
	public boolean saveAccesoryList(AccsoryListVO accsoryListVO) {
		int index = 0;/*记录变化的数据*/
		if(accsoryListVO.getAccesoryPkid() != null && accsoryListVO.getAccesoryPkid().size() > 0) {
			for(Long pkid:accsoryListVO.getAccesoryPkid()) {
				if(pkid != 0) {
					toAccesoryListMapper.deleteByPrimaryKey(pkid);
					index++;
				}
			}
		}
		if(accsoryListVO.getPkidList() != null) {
			ToAccesoryList toAccesoryList = new ToAccesoryList();
			toAccesoryList.setPartCode(accsoryListVO.getPartCode());
			for(int i=0; i<accsoryListVO.getPkidList().size(); i++) {
				if(StringUtils.isBlank(accsoryListVO.getAccessoryCodeList().get(i))) {
					continue;
				}
				toAccesoryList.setAccessoryCode(accsoryListVO.getAccessoryCodeList().get(i));
				toAccesoryList.setAccessoryName(uamBasedataService.getDictValue("yu_file_code", accsoryListVO.getAccessoryCodeList().get(i)));
				if(accsoryListVO.getPkidList().get(i)!=0) {
					toAccesoryList.setPkid(accsoryListVO.getAccesoryPkid().get(i));
					toAccesoryListMapper.updateByPrimaryKeySelective(toAccesoryList);
					index++;
				} else {
					toAccesoryList.setPkid(null);
					List<ToAccesoryList> list = toAccesoryListMapper.qureyToAccesoryList(toAccesoryList);
					if(list == null || list.size() == 0) {
						toAccesoryListMapper.insertSelective(toAccesoryList);
						index++;
					}
				}
			}
		}
		if(index > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ToAccesoryList findAccesoryByCode(String accessoryCode) {
		// TODO Auto-generated method stub
		return toAccesoryListMapper.findAccesoryByCode(accessoryCode);
	}

}
