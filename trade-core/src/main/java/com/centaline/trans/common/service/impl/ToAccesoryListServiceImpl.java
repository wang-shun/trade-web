package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.centaline.trans.common.entity.ToAccesoryList;
import com.centaline.trans.common.entity.ToAttachment;
import com.centaline.trans.common.repository.ToAccesoryListMapper;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.common.service.ToAttachmentService;
import com.centaline.trans.common.vo.AccsoryListVO;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;

@Service
public class ToAccesoryListServiceImpl implements ToAccesoryListService {

	@Autowired
	private ToAccesoryListMapper toAccesoryListMapper;
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
	private ToAttachmentService toAttachmentService;
	@Autowired
	private ToMortgageService toMortgageService;

	@Override
	public List<ToAccesoryList> qureyToAccesoryList(ToAccesoryList toAccesoryList) {
		return toAccesoryListMapper.qureyToAccesoryList(toAccesoryList);
	}

	@Override
	public String findAccesoryNameByCode(String accessoryCode) {
		return toAccesoryListMapper.findAccessoryNameByCode(accessoryCode);
	}

	@Override
	public void getAccesoryListLingZheng(HttpServletRequest request, String taskitem, boolean psf, boolean self,
			boolean com) {
		ToAccesoryList toAccesoryList = new ToAccesoryList();
		toAccesoryList.setPartCode(taskitem);
		List<ToAccesoryList> list = qureyToAccesoryList(toAccesoryList);
		List<ToAccesoryList> removeList = new ArrayList<ToAccesoryList>();
		/* 根据需求调整附件上传项目 */
		for (ToAccesoryList tal : list) {
			if (psf && (tal.getAccessoryCode().equals("third_part_right_cert")
					|| tal.getAccessoryCode().equals("new_house_book"))) {/* 公积金 */
				removeList.add(tal);
			}

			// add zhangxb16 2016-2-22
			if (tal.getAccessoryCode().equals("third_part_right_cert") && psf == false && self == false
					&& com == false) {
				removeList.add(tal);
			}
		}
		for (ToAccesoryList tal : removeList) {
			list.remove(tal);
		}
		if (list != null && list.size() > 0) {
			int size = list.size();
			request.setAttribute("accesoryList", list);
			List<Long> idList = new ArrayList<Long>(size);
			for (int i = 0; i < size; i++) {
				idList.add(list.get(i).getPkid());
			}
			request.setAttribute("idList", idList);
		}
	}

	@Override
	public void getAccesoryList(HttpServletRequest request, String taskitem) {
		ToAccesoryList toAccesoryList = new ToAccesoryList();
		toAccesoryList.setPartCode(taskitem);
		List<ToAccesoryList> list = qureyToAccesoryList(toAccesoryList);
		if (list != null && list.size() > 0) {
			int size = list.size();
			request.setAttribute("accesoryList", list);
			List<Long> idList = new ArrayList<Long>(size);
			for (int i = 0; i < size; i++) {
				idList.add(list.get(i).getPkid());
			}
			request.setAttribute("idList", idList);
		}
	}

	@Override
	public void getAccesoryListCaseClose(HttpServletRequest request, String caseCode) {
		List<ToAttachment> attachments = toAttachmentService.findToAttachmentByCaseCode(caseCode);
		List<ToAccesoryList> list = new ArrayList<ToAccesoryList>();

		if (attachments != null && attachments.size() > 0) {
			Iterator<ToAttachment> iter = attachments.iterator();
			while (iter.hasNext()) {
				ToAttachment attachment = iter.next();
				if (attachment.getPartCode().equals("property_research")) {
					iter.remove();
					continue;
				}
				if (!StringUtils.isEmpty(attachment.getPreFileCode())) {
					attachment.setPreFileName(findAccesoryNameByCode(attachment.getPreFileCode()));
					ToAccesoryList itemAccesoryList = findAccesoryByCode(attachment.getPreFileCode());
					boolean isHave = false;
					if (CollectionUtils.isNotEmpty(list)) {
						for (ToAccesoryList item : list) {
							if (item.getPkid() == itemAccesoryList.getPkid()) {
								isHave = true;
								break;
							}
						}
						if (!isHave) {
							list.add(itemAccesoryList);
						}
					}
				}
			}
		}
		if (list != null && list.size() > 0) {
			int size = list.size();
			request.setAttribute("accesoryList", list);
			List<Long> idList = new ArrayList<Long>(size);
			for (int i = 0; i < size; i++) {
				idList.add(list.get(i).getPkid());
			}
			request.setAttribute("idList", idList);
		}
	}

	@Override
	public boolean saveAccesoryList(AccsoryListVO accsoryListVO) {
		int index = 0;/* 记录变化的数据 */
		if (accsoryListVO.getAccesoryPkid() != null && accsoryListVO.getAccesoryPkid().size() > 0) {
			for (Long pkid : accsoryListVO.getAccesoryPkid()) {
				if (pkid != 0) {
					toAccesoryListMapper.deleteByPrimaryKey(pkid);
					index++;
				}
			}
		}
		if (accsoryListVO.getPkidList() != null) {
			ToAccesoryList toAccesoryList = new ToAccesoryList();
			toAccesoryList.setPartCode(accsoryListVO.getPartCode());
			for (int i = 0; i < accsoryListVO.getPkidList().size(); i++) {
				if (StringUtils.isBlank(accsoryListVO.getAccessoryCodeList().get(i))) {
					continue;
				}
				toAccesoryList.setAccessoryCode(accsoryListVO.getAccessoryCodeList().get(i));
				toAccesoryList.setAccessoryName(
						uamBasedataService.getDictValue("yu_file_code", accsoryListVO.getAccessoryCodeList().get(i)));
				if (accsoryListVO.getPkidList().get(i) != 0) {
					toAccesoryList.setPkid(accsoryListVO.getAccesoryPkid().get(i));
					toAccesoryListMapper.updateByPrimaryKeySelective(toAccesoryList);
					index++;
				} else {
					toAccesoryList.setPkid(null);
					List<ToAccesoryList> list = toAccesoryListMapper.qureyToAccesoryList(toAccesoryList);
					if (list == null || list.size() == 0) {
						toAccesoryListMapper.insertSelective(toAccesoryList);
						index++;
					}
				}
			}
		}
		if (index > 0) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public void getAccesoryListGuoHu(HttpServletRequest request, String taskitem, String caseCode) {
		ToAccesoryList toAccesoryList = new ToAccesoryList();
		toAccesoryList.setPartCode(taskitem);
		List<ToAccesoryList> list = qureyToAccesoryList(toAccesoryList);
		List<ToAccesoryList> removeList = new ArrayList<ToAccesoryList>();
		/*根据需求调整附件上传项目*/
		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode2(caseCode);
		for(ToAccesoryList tal:list) {
			if((toMortgage == null || toMortgage.getMortType() == null) && (tal.getAccessoryName().equals("抵押登记表") || tal.getAccessoryName().equals("商贷利率页"))) {/*无贷款*/
				removeList.add(tal);
			} else if(toMortgage != null && "30016003".equals(toMortgage.getMortType()) && "商贷利率页".equals(tal.getAccessoryName())) {
				removeList.add(tal);
			}
		}
		for(ToAccesoryList tal:removeList) {
			list.remove(tal);
		}
		if(list != null && list.size() > 0) {
			int size = list.size();
			request.setAttribute("accesoryList", list);
			List<Long> idList = new ArrayList<Long>(size);
			for(int i=0; i<size; i++) {
				idList.add(list.get(i).getPkid());
			}
			request.setAttribute("idList", idList);
		}
    }

	@Override
	public ToAccesoryList findAccesoryByCode(String accessoryCode) {
		// TODO Auto-generated method stub
		return toAccesoryListMapper.findAccesoryByCode(accessoryCode);
	}

}
