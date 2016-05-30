package com.centaline.trans.team.vo;

import java.util.List;

import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.vo.User;

public class CooperativeOrganizationVO {

	private List<User> userList;
	private List<Dict> dictList;
	private List<Integer> countList;
	
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public List<Dict> getDictList() {
		return dictList;
	}
	public void setDictList(List<Dict> dictList) {
		this.dictList = dictList;
	}
	public List<Integer> getCountList() {
		return countList;
	}
	public void setCountList(List<Integer> countList) {
		this.countList = countList;
	}
	
}
