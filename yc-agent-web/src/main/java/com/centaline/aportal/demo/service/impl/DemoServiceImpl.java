package com.centaline.aportal.demo.service.impl;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.common.quickQuery.utils.QuickQueryJdbcTemplate;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.message.core.remote.UamMessageService;
import com.aist.uam.auth.remote.UamSessionService;
import com.centaline.aportal.demo.service.DemoService;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.signroom.service.RmSignRoomService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by linjiarong on 2016/10/12.
 */
@Service
public class DemoServiceImpl implements DemoService{
	@Autowired
	private RmSignRoomService rmSignRoomService;
	@Autowired
	private ToCaseInfoService toCaseInfoService;
	@Qualifier("uamMessageServiceClient")
    @Autowired
    private UamMessageService uamMessageService;
    @Override
    public String helloworld() {
        return "hello world";
    }

	@Override
	public AjaxResponse<Map> sayHi(String realName,JQGridParam gp) {
		uamMessageService.read("d");
		ToCaseInfo caseInfo= toCaseInfoService.findToCaseInfoByCaseCode(realName);
		if(caseInfo!=null){
			System.out.println( "CTM Code is "+caseInfo.getCtmCode());
		}
		
		return rmSignRoomService.generatePageDate(gp);
		
	}
}
