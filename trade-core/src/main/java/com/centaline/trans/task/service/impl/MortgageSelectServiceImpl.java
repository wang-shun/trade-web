package com.centaline.trans.task.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.enums.TransDictEnum;
import com.centaline.trans.common.repository.TgServItemAndProcessorMapper;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.service.MortgageSelectService;
import com.centaline.trans.task.vo.MortgageSelecteVo;


@Service
public class MortgageSelectServiceImpl implements MortgageSelectService {
	@Autowired
	private TgServItemAndProcessorService tgServItemAndProcessorService;
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private TgServItemAndProcessorMapper tgServItemAndProcessorMapper;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Override
	
	public boolean submit(MortgageSelecteVo vo) {
		String serivceCode=null;
		if("1".equals(vo.getMortageService())||"3".equals(vo.getMortageService())){
			serivceCode="3000400101";
		}else if("2".equals(vo.getMortageService())){
			serivceCode="3000400201";
		}
		if(serivceCode!=null){
			TgServItemAndProcessor tsiap = new TgServItemAndProcessor();
			tsiap.setCaseCode(vo.getCaseCode());
			tsiap.setSrvCode(serivceCode);
			tsiap=tgServItemAndProcessorService.findTgServItemAndProcessor(tsiap);
			if(tsiap==null){
				tsiap=new TgServItemAndProcessor();
			}
			User user=uamUserOrgService.getUserById(vo.getPartner());
			tsiap.setCaseCode(vo.getCaseCode());
			tsiap.setProcessorId(vo.getPartner());
			tsiap.setSrvCode(serivceCode);
			tsiap.setSrvCat(getSrcCat(serivceCode));
			tsiap.setOrgId(user.getOrgId());
			if(tsiap.getPkid()==null){
				tgServItemAndProcessorService.insertSelective(tsiap);
			}else{
				tgServItemAndProcessorMapper.updateByPrimaryKey(tsiap);
			}
			
		}
		
		//开始处理流程引擎
		

		List<RestVariable> variables = new ArrayList<RestVariable>();
		editRestVariables(variables,vo.getMortageService());
		

		return workFlowManager.submitTask(variables, vo.getTaskId(), vo.getProcessInstanceId(),
				null, vo.getCaseCode());

	}
	private List<RestVariable> editRestVariables(List<RestVariable> variables, String loanTyby) {

		RestVariable restVariable1 = new RestVariable();
		restVariable1.setName("ComLoanNeed");
		RestVariable restVariable2 = new RestVariable();
		restVariable2.setName("PSFLoanNeed");
		RestVariable restVariable5 = new RestVariable();
		restVariable5.setName("SelfLoanNeed");

		if (loanTyby.equals("1")) {/* 中原 组合贷 */
			restVariable1.setValue(true);
			restVariable2.setValue(false);
			restVariable5.setValue(false);
		} else if (loanTyby.equals("2")) {/* 中原 公积金 */
			restVariable1.setValue(false);
			restVariable2.setValue(true);
			restVariable5.setValue(false);
		} else if (loanTyby.equals("3")) {/* 自办 组合贷 */
			restVariable1.setValue(false);
			restVariable2.setValue(false);
			restVariable5.setValue(true);
		} else if (loanTyby.equals("4")) {/* 自办 公积金 */
			restVariable1.setValue(false);
			restVariable2.setValue(false);
			restVariable5.setValue(true);
		} else {/* 无贷款 */
			restVariable1.setValue(false);
			restVariable2.setValue(false);
			restVariable5.setValue(false);
		}

		variables.add(restVariable1);
		variables.add(restVariable2);
		variables.add(restVariable5);

		return variables;
	}
	/**
	 * 通过srvCode查询SrvCat
	 * @param srvCode
	 * @return
	 */
	private String getSrcCat(String srvCode) {
		Dict dict = uamBasedataService.findDictByTypeAndCode(TransDictEnum.TFWBM.getCode(), srvCode);
		if(dict == null) return null;
		Dict dictF = uamBasedataService.findDictById(dict.getParentId());
		if(dictF == null) return null;
		return dictF.getCode();
	}
}
