package com.centaline.trans.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.repository.TgServItemAndProcessorMapper;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.team.entity.TsTeamProperty;
import com.centaline.trans.team.entity.TsTeamScope;
import com.centaline.trans.team.repository.TsTeamPropertyMapper;
import com.centaline.trans.team.repository.TsTeamScopeMapper;


@Service
public class TgServItemAndProcessorServiceImpl implements
		TgServItemAndProcessorService {

	@Autowired
	TgServItemAndProcessorMapper tgServItemAndProcessorMapper;
	
	@Autowired
	private ToCaseMapper tocaseMapper;
	
	@Autowired
	private UamSessionService sessionService;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	@Autowired
	private TsTeamScopeMapper tsteamScopeMapper;
	
	@Autowired
	private TsTeamPropertyMapper tsteamPropertyMapper;
	
	@Autowired
	private UamBasedataService uamBasedataService;/*字典*/
	
	@Autowired
	private ToCaseInfoMapper tocaseInfoMapper;
	
	
	@Override
	public List<TgServItemAndProcessor> findTgServItemAndProcessorByUserIdAndCaseCode(
			String processorId,String caseCoe) {
		// TODO Auto-generated method stub
		return tgServItemAndProcessorMapper.findTgServItemAndProcessorByUserIdAndCaseCode(processorId,caseCoe);
	}

	@Override
	public List<TgServItemAndProcessor> findTgServItemAndProcessorByCaseCode(
			String caseCode) {
		// TODO Auto-generated method stub
		return tgServItemAndProcessorMapper.findTgServItemAndProcessorByCaseCode(caseCode);
	}

	@Override
	public List<String> findCaseCodesByUserId(String processorId) {
		// TODO Auto-generated method stub
		return tgServItemAndProcessorMapper.findCaseCodesByUserId(processorId);
	}

	@Override
	public List<String> findProcessorsByCaseCode(TgServItemAndProcessor record) {
		// TODO Auto-generated method stub
		return tgServItemAndProcessorMapper.findProcessorsByCaseCode(record);
	}

	@Override
	public Map<String, String> findServiceMap(String caseCode) {
		List<TgServItemAndProcessor> list = tgServItemAndProcessorMapper.findTgServItemAndProcessorByCaseCode(caseCode);
		Map<String, String> map = new HashMap<String, String>();
		for(TgServItemAndProcessor tsiap:list) {
			map.put(tsiap.getSrvCode(), tsiap.getProcessorId());
		}
		return map;
	}

	@Override
	public int deleteByPrimaryCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return tgServItemAndProcessorMapper.deleteByPrimaryCaseCode(caseCode);
	}

	@Override
	public int insertSelective(TgServItemAndProcessor record) {
		// TODO Auto-generated method stub
		return tgServItemAndProcessorMapper.insertSelective(record);
	}

	@Override
	public int deleteByUser(TgServItemAndProcessor record) {
		return tgServItemAndProcessorMapper.deleteByUser(record);
	}

	@Override
	public List<String> findSrvsByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return tgServItemAndProcessorMapper.findSrvsByCaseCode(caseCode);
	}
	@Override
	public List<String> findSrvCatsByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return tgServItemAndProcessorMapper.findSrvCatsByCaseCode(caseCode);
	}

	@Override
	public int updateByCaseCode(TgServItemAndProcessor record) {
		// TODO Auto-generated method stub
		return tgServItemAndProcessorMapper.updateByCaseCode(record);
	}
	
	
	@Override
	public List<TgServItemAndProcessor> selectBycasecodeandProcessorid(String caseCode) {
		
		// 1 根据 caseCode 到 T_TO_CASE 表中查询出 LEADING_PROCESS_ID
		ToCase tocase=tocaseMapper.findToCaseByCaseCode(caseCode);
		String leadingProcessId=tocase.getLeadingProcessId();
		
		// 2 根据 caseCode 和 leadingProcessId 到 T_TG_SERV_ITEM_AND_PROCESSOR 表中去查询
		TgServItemAndProcessor itemprocess=new TgServItemAndProcessor();
		itemprocess.setCaseCode(caseCode);
		itemprocess.setProcessorId(leadingProcessId);
		List<TgServItemAndProcessor> servitemList=tgServItemAndProcessorMapper.selectBycasecodeandProcessorid(itemprocess);
		
		// 3 将 SrvCode 变为 SrvName
		TgServItemAndProcessor serps=null;
		List<TgServItemAndProcessor> serList=new ArrayList<TgServItemAndProcessor>();
		for(int k=0; k<servitemList.size(); k++){
			serps=new TgServItemAndProcessor();
			Dict dt = uamBasedataService.findDictByTypeAndCode("yu_serv_cat_code_tree", servitemList.get(k).getSrvCode());
			
			serps.setSrvCode(servitemList.get(k).getSrvCode());  // srvCode
			serps.setProcessorId(servitemList.get(k).getProcessorId());  // processorId
			serps.setOrgId(servitemList.get(k).getOrgId());  // orgId
			serps.setSrvName(dt.getName());  // srvName
			serList.add(serps);
		}
		
		return serList;
	}

	
	/**
	 * 根据字典类型，获得相应字典数据
	 * @param dictByType
	 */
	private List<Dict> getDictList(String dictByType) {
		List<Dict> list = new ArrayList<Dict>();
		Dict dict = uamBasedataService.findDictByType(dictByType);
		if(dict != null) {
			list = dict.getChildren();
		}
		return list;
	}
	
	
	/**
	 * 根据caseCode 去查询他所在组的服务项
	 * @return
	 */
	@Override
	public List<Dict> selectOwnService(String caseCode) {
		
		// 1 根据 caseCode 表到 T_TO_CASE_INFO 表中去查询 GRP_CODE
		ToCaseInfo caseinfo=tocaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
		String grpCode=caseinfo.getGrpCode();
		String yuTeamCode=null;
		Dict ct=null;
		List<Dict> dtList=new ArrayList<Dict>();
		
		if(null!=grpCode){
			List<TsTeamScope> teamScope=tsteamScopeMapper.selectByYuagentTeamCode(grpCode); // 誉萃的店组code
			if(null!=teamScope && teamScope.size()>0){
				if(teamScope.size()==1){
					yuTeamCode = teamScope.get(0).getYuTeamCode();  // 查询出誉萃的orgid
					TsTeamProperty teampy=tsteamPropertyMapper.findTeamPropertyByTeamCode(yuTeamCode);
					if(null!=teampy){
						// 在根据在T_TS_TEAM_PROPERTY 表中查询到的 TEAM_PROPERTY 去到字典表中查询出对应的服务项
						List<Dict> dictList=getDictList(teampy.getTeamProperty());
						for(int i=0; i<dictList.size(); i++){
							ct=new Dict();
							ct.setCode(dictList.get(i).getCode());
							ct.setDictName(dictList.get(i).getDictName());
							dtList.add(ct);
						}
					}
				}else{
					// 如果 teamScope.size>1 则存在多个, 则从T_TS_TEAM_PROPERTY 表中找出为非组办组的[即IS_RESPONSE_TEAM=0] 的  yuTeamCode
					for(int i=0; i<teamScope.size(); i++){
						yuTeamCode=teamScope.get(i).getYuTeamCode();  // 查询出誉萃的orgid
						TsTeamProperty teamproperty=tsteamPropertyMapper.selectTeamPropertyByNoTeamCode(yuTeamCode);  // 根据 yuTeamCode 去查询组办组的
						if(null !=teamproperty){
							List<Dict> dictList=getDictList(teamproperty.getTeamProperty());
							for(int k=0; k<dictList.size(); k++){
								ct=new Dict();
								ct.setCode(dictList.get(i).getCode());
								ct.setDictName(dictList.get(k).getDictName());
								dtList.add(ct);
							}
						}
					}
				}
			}
		}
		
		return dtList;
	}
	
	
	@Override
	public int updateCoope(TgServItemAndProcessor pro) {
		
		int updatecoope=tgServItemAndProcessorMapper.updateCoope(pro);
		return updatecoope;
	}

	@Override
	public TgServItemAndProcessor findTgServItemAndProcessor(
			TgServItemAndProcessor record) {
		// TODO Auto-generated method stub
		return tgServItemAndProcessorMapper.findTgServItemAndProcessor(record);
	}
}
