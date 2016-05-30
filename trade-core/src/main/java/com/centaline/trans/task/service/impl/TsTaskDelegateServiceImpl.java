package com.centaline.trans.task.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aist.common.quickQuery.web.vo.DatagridVO;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.engine.bean.TaskOperate;
import com.centaline.trans.engine.bean.TaskQuery;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.task.entity.TsTaskDelegate;
import com.centaline.trans.task.repository.TsTaskDelegateMapper;
import com.centaline.trans.task.service.TsTaskDelegateService;
import com.centaline.trans.task.vo.UserPagebleVO;

@Service
public class TsTaskDelegateServiceImpl implements TsTaskDelegateService {
	public static final String YC_ORG_CODE = "033G970";
	public static final String TASK_DELEGATE_STATUS_CLOSE = "20011001";
	public static final String TASK_DELEGATE_STATUS_OPEN = "20011002";
	@Autowired
	private TsTaskDelegateMapper tsTaskDelegateMapper;
	@Autowired
	private UamUserOrgService uamuserOrgService;
	@Autowired
	private WorkFlowManager workFlowManager;

	@Override
	//@Cacheable
	public List<User> listYCUsers() {
		List<Org> orgs = getYCOrgs();
		List<User> users = new ArrayList<>();
		for (Org org : orgs) {
			users.addAll(uamuserOrgService.getUserByBelongOrgId(org.getId()));
		}
		return users;
	}

	/**
	 * 查询 获得所有誉萃用户
	 * 
	 * @return
	 */
	private List<Org> getYCOrgs() {
		Org org = uamuserOrgService.getOrgByCode(YC_ORG_CODE);
		List<Org> orgs = getSubOrgs(org.getId());
		orgs.add(org);
		return orgs;

	}

	private List<Org> getSubOrgs(String parentId) {
		List<Org> orgs = uamuserOrgService.getOrgByParentId(parentId);
		List<Org> temp = new ArrayList<>();
		if (orgs != null && !orgs.isEmpty()) {
			Iterator<Org> orgIt = orgs.iterator();
			while (orgIt.hasNext()) {
				Org org = orgIt.next();
				List<Org> subOrgs = getSubOrgs(org.getId());
				temp.addAll(subOrgs);
			}
		}
		orgs.addAll(temp);
		return orgs;
	}

	@Override
	public DatagridVO listUser(UserPagebleVO page) {
		List<User> users = listYCUsers();
		List<User> fusers;
		if (StringUtils.isBlank(page.getUsername())) {
			fusers = users;
		} else {
			fusers = new ArrayList<>();
			String nameLike = page.getUsername();
			for (User user : users) {
				if (user.getRealName().startsWith(nameLike)) {
					fusers.add(user);
				}
			}
		}
		if (fusers == null || fusers.isEmpty()) {
			Pageable pageable = new PageRequest(page.getPage(), page.getRows());
			Page pageDate = new PageImpl<>(new ArrayList<>(), pageable, 0);
			DatagridVO vo = new DatagridVO(pageDate);
			return vo;
		} else {
			for (User user : fusers) {
				user.setPassword(null);
			}
		}
		return pageble(fusers, page);
	}

	public DatagridVO pageble(List<User> fusers, UserPagebleVO page) {

		int pageNum = page.getPage();
		int pageSize = page.getRows();
		int fPoint = pageSize * (pageNum - 1);
		int lPoint = fPoint + pageSize;

		int total = fusers.size();
		fPoint = fPoint > total ? total : fPoint;
		lPoint = lPoint > total ? total : lPoint;
		List content = fusers.subList(fPoint, lPoint);
		Pageable pageable = new PageRequest(pageNum, pageSize);
		Page pageDate = new PageImpl<>(content, pageable, total);
		DatagridVO vo = new DatagridVO(pageDate);
		return vo;
	}

	@Override
	public int taskDelegate(String owner, String assignee) {
		TsTaskDelegate ttd = new TsTaskDelegate();
		ttd.setOwner(owner);
		ttd.setStatus(TASK_DELEGATE_STATUS_CLOSE);
		ttd.setCloseDate(new Date());
		tsTaskDelegateMapper.batchClose(ttd);
		ttd.setCloseDate(null);
		ttd.setCreateDate(new Date());
		ttd.setAssignee(assignee);
		ttd.setStatus(TASK_DELEGATE_STATUS_OPEN);
		tsTaskDelegateMapper.insert(ttd);
		taskDelegateWorkFlowOperate(owner, assignee);
		return 1;
	}

	public void taskDelegateWorkFlowOperate(String owner, String assignee) {
		TaskQuery tq = new TaskQuery();
		tq.setAssignee(owner);
		tq.setActive(true);
		PageableVo<TaskVo> vo = workFlowManager.listTasks(tq);
		List<TaskVo> listVo = vo.getData();
		for (TaskVo taskVo : listVo) {
			TaskOperate topt = new TaskOperate(taskVo.getId() + "", "delegate");
			topt.setAssignee(assignee);
			workFlowManager.operaterTask(topt);
		}
	}

	@Override
	public int closeTaskDelegate(String owner, Long id) {
		TsTaskDelegate ttd = new TsTaskDelegate();
		ttd.setPkid(id);
		ttd.setStatus(TASK_DELEGATE_STATUS_CLOSE);
		ttd.setCloseDate(new Date());
		tsTaskDelegateMapper.updateByPrimaryKey(ttd);
		closeTaskDelegateWorkFlowOperate(owner);
		return 0;
	}

	public void closeTaskDelegateWorkFlowOperate(String owner) {
		TaskQuery tq = new TaskQuery();
		tq.setOwner(owner);
		tq.setActive(true);
		
		PageableVo<TaskVo> vo = workFlowManager.listTasks(tq);
		List<TaskVo> listVo = vo.getData();
		for (TaskVo taskVo : listVo) {
			TaskOperate topt = new TaskOperate(taskVo.getId() + "", "resolve");
			topt.setAssignee(owner);
			workFlowManager.operaterTask(topt);
		}
	}

	@Override
	public String getTaskAgent(String owner) {
		List<TsTaskDelegate> ttds = tsTaskDelegateMapper
				.findOpenTaskDelegatesByOwner(owner);
		if (ttds == null || ttds.isEmpty())
			return null;
		return ttds.get(0).getAssignee();
	}
}
