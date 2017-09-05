package com.centaline.trans.common.service.impl;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.common.service.CityUtilService;
import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.service.TsTeamPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author yinchao
 * @date 2017/9/4
 */
@Service
public class CityUtilServiceImpl implements CityUtilService {
	//默认后台岗位编码 贷款权证
	private final static String BACK_JOBCODE = "DKQZ";
	//默认前台岗位编码 过户权证
	private final static String FRONT_JOBCODE = "GHQZ";
	@Value("${user.property.check:jobcode}")
	private String property;
	@Value("${trade.cityCode:TIANJIN}")
	private String cityCode;

	@Autowired
	private TsTeamPropertyService teamPropertyService;

	@Override
	public boolean userIsBackRole(SessionUser currentUser) {
		System.out.println("===============property=" + property + ", at CityUtilServiceImpl.userIsBackRole() in line:31");
		if(currentUser==null) return false;
		if("jobcode".equals(property)){
			return BACK_JOBCODE.equals(currentUser.getServiceJobCode());
		}else if("teamproperty".equals(property)){
			TsTeamProperty tp = teamPropertyService.findTeamPropertyByTeamCode(currentUser.getServiceDepCode());
			if(tp!=null){
				//TODO 全流程组和后台流程组都返回true
				return "yu_back".equals(tp.getTeamProperty())||"yu_all".equals(tp.getTeamProperty());
			}
		}
		return false;
	}

	@Override
	public boolean userIsFrontRole(SessionUser currentUser) {
		System.out.println("===============property=" + property + ", at CityUtilServiceImpl.userIsFrontRole() in line:46");
		if(currentUser==null) return false;
		if("jobcode".equals(property)){
			return FRONT_JOBCODE.equals(currentUser.getServiceJobCode());
		}else if("teamproperty".equals(property)){
			TsTeamProperty tp = teamPropertyService.findTeamPropertyByTeamCode(currentUser.getServiceDepCode());
			if(tp!=null){
				//TODO 全流程组和前台流程组都返回true
				return "yu_front".equals(tp.getTeamProperty())||"yu_all".equals(tp.getTeamProperty());
			}
		}
		return false;
	}

	@Override
	public String getCityCode() {
		System.out.println("===============cityCode=" + cityCode + ", at CityUtilServiceImpl.getCityCode() in line:61");
		return cityCode;
	}
}
