package com.centaline.trans.material.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.entity.ToAccesoryList;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.material.entity.MmIoBatch;
import com.centaline.trans.material.entity.MmItemBatch;
import com.centaline.trans.material.entity.MmMaterialItem;
import com.centaline.trans.material.enums.MaterialActionEnum;
import com.centaline.trans.material.enums.MaterialStatusEnum;
import com.centaline.trans.material.service.MmIoBatchService;
import com.centaline.trans.material.service.MmItemBatchService;
import com.centaline.trans.material.service.MmMaterialItemService;
import com.centaline.trans.material.vo.MaterialPackageVo;



@Controller
@RequestMapping(value="/material")
public class MaterialManagementController {
	
	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired(required=true)
	private UamUserOrgService uamUserOrgService;
	
	@Autowired(required=true)
	private MmMaterialItemService mmMaterialItemService;
	
    @Autowired
    private ToPropertyInfoService toPropertyInfoService;
    
    @Autowired
    private MmItemBatchService mmItemBatchService;
    
    @Autowired
    private MmIoBatchService mmIoBatchService;
    
	@Autowired
	private ToAccesoryListService toAccesoryListService;
    
	//物品管理列表页面
    //快速查询
	@RequestMapping("materialList")
	public String materialList(HttpServletRequest request){
	
		SessionUser user = uamSessionService.getSessionUser();
		String userJob=user.getServiceJobCode();
		boolean queryOrgFlag = false;
		boolean isAdminFlag = false;

        StringBuffer reBuffer = new StringBuffer();
		if(!userJob.equals(TransJobs.TJYGW.getCode())){
			queryOrgFlag=true;
			String depString = user.getServiceDepHierarchy();
			String userOrgIdString = user.getServiceDepId();
			if(depString.equals(DepTypeEnum.TYCTEAM.getCode())){
				reBuffer.append(userOrgIdString);
			}else if(depString.equals(DepTypeEnum.TYCQY.getCode())){
				List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(userOrgIdString, DepTypeEnum.TYCTEAM.getCode());
				for(Org org:orgList){
					reBuffer.append(org.getId());
					reBuffer.append(",");
				}
				reBuffer.deleteCharAt(reBuffer.length()-1);
				
			}else{
				isAdminFlag=true;
			}
		}
		request.setAttribute("queryOrgs", reBuffer.toString());//org_id至jsp、js分割-->数组
		request.setAttribute("queryOrgFlag", queryOrgFlag);//判断是否是交易顾问 即判断是否有上下级组织
		request.setAttribute("isAdminFlag", isAdminFlag);		
		request.setAttribute("serviceDepId", user.getServiceDepId());//登录用户的org_id
		request.setAttribute("serviceDepName", user.getServiceDepName());	

		return "material/materialList";
	}
	
	
	//物品入库页面  案件信息查询
	@RequestMapping("materialStorgae")
	public String  materialStorgae(HttpServletRequest request,String pkids){
		List<MmMaterialItem>  mmMaterialItemList =  new ArrayList<MmMaterialItem>();
		MmMaterialItem mmMaterialItem= new MmMaterialItem();
		if(!"".equals(pkids) && pkids !=null){
			String pkid[] = pkids.split(",");
			for(int i=0; i<pkid.length;i++){				
				mmMaterialItem = mmMaterialItemService.queryMmMaterialByPkid(Long.parseLong(pkid[i]));
				mmMaterialItemList.add(mmMaterialItem);
			}			
		}
		
		if(mmMaterialItemList.size()>0){
			String caseCode = mmMaterialItemList.get(0).getCaseCode();
			ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(caseCode);
			if(null != toPropertyInfo){
				request.setAttribute("propertyAddr", toPropertyInfo.getPropertyAddr());
			}
			request.setAttribute("mmMaterialItemList", mmMaterialItemList);	
		}
		//查询附件信息记录表
		ToAccesoryList toAccesoryList = new ToAccesoryList();
		toAccesoryList.setPartCode("CustomerConfirmation");
		List<ToAccesoryList> list = toAccesoryListService.qureyToAccesoryList(toAccesoryList);
		if(list != null && list.size() > 0) {
			int size = list.size();
			request.setAttribute("accesoryList", list);
			List<Long> idList = new ArrayList<Long>(size);
			for(int i=0; i<size; i++) {
				idList.add(list.get(i).getPkid());
			}
			request.setAttribute("idList", idList);
		}
		
		return "material/materialStorageConfirm";
		
	}
	
	//物品产品详细信息查看
	@RequestMapping("materialDetail")
	public String  materialDetail(HttpServletRequest request,String pkid){		
		MmMaterialItem mmMaterialItem= new MmMaterialItem();	
		if(!"".equals(pkid) && pkid !=null){				
			mmMaterialItem = mmMaterialItemService.queryMmMaterialByPkid(Long.parseLong(pkid));	
			
			List<MmIoBatch> mmIoBatchlist = getMmIoBatchList(pkid);
			if(mmIoBatchlist.size() > 0){
				request.setAttribute("mmIoBatchlist", mmIoBatchlist);	
			}
		}
		
		//获取物品及相关信息
		if(null != mmMaterialItem){
			//查询产证地址信息
			if(null != mmMaterialItem.getCaseCode() && !"".equals(mmMaterialItem.getCaseCode())){
				ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(mmMaterialItem.getCaseCode());
				if(null != toPropertyInfo){
					request.setAttribute("toPropertyInfo", toPropertyInfo);
				}				
			}
			//查询保管人员信息
			if(null != mmMaterialItem.getItemManager() && !"".equals(mmMaterialItem.getItemManager())){
				User user = uamUserOrgService.getUserById(mmMaterialItem.getItemManager());
				if(null != user){
					request.setAttribute("user", user);
				}				
			}
			if(null != mmMaterialItem.getItemCategory() && !"".equals(mmMaterialItem.getItemCategory())){				
				if(mmMaterialItem.getItemCategory().equals("carded")){
					mmMaterialItem.setItemCategory("身份证");
				}else if(mmMaterialItem.getItemCategory().equals("mortgageContract")){
					mmMaterialItem.setItemCategory("抵押合同");
				}else if(mmMaterialItem.getItemCategory().equals("bankCard")){
					mmMaterialItem.setItemCategory("银行卡");
				}else if(mmMaterialItem.getItemCategory().equals("propertyCard")){
					mmMaterialItem.setItemCategory("产权证");
				}else if(mmMaterialItem.getItemCategory().equals("otherCard")){
					mmMaterialItem.setItemCategory("他证");
				}				
			}
			if(null != mmMaterialItem.getItemStatus() && !"".equals(mmMaterialItem.getItemStatus())){				
				if(mmMaterialItem.getItemStatus().equals("stay")){
					mmMaterialItem.setItemStatus("待入库");
				}else if(mmMaterialItem.getItemStatus().equals("borrow")){
					mmMaterialItem.setItemStatus("外借");
				}else if(mmMaterialItem.getItemStatus().equals("instock")){
					mmMaterialItem.setItemStatus("在库");
				}else if(mmMaterialItem.getItemStatus().equals("back")){
					mmMaterialItem.setItemStatus("退还");
				}				
			}
			request.setAttribute("mmMaterialItem", mmMaterialItem);				
		}	
		return "material/materialDetail";		
	}
	
	//封装查询 出入记录、
	//todo 根据pkid获取上传的客户确认书需完善
	private  List<MmIoBatch>  getMmIoBatchList(String pkid){
		//出入记录
		List<MmIoBatch> mmIoBatchlist = new  ArrayList<MmIoBatch>();
		//中间表
		List<MmItemBatch> mmItemBatchList = new  ArrayList<MmItemBatch>();
		//查询中间表的BatchId列表，关联查询动作表
		MmIoBatch mmIoBatch = new MmIoBatch();		
		mmItemBatchList = mmItemBatchService.queryMmItemBatchList(Long.parseLong(pkid));
		if(mmItemBatchList.size() > 0){
			for(int i=0; i<mmItemBatchList.size(); i++){
				if(null != mmItemBatchList.get(i).getBatchId()){
					//通过BatchId关联查询动作表
					mmIoBatch = mmIoBatchService.queryMmIoBatchByPkid(mmItemBatchList.get(i).getBatchId());
					if(null != mmIoBatch){						
						if(null != mmIoBatch.getLogAction() && !"".equals(mmIoBatch.getLogAction())){
							if(mmIoBatch.getLogAction().equals("in")){
								mmIoBatch.setLogAction("入库");
							}else if(mmIoBatch.getLogAction().equals("out")){
								mmIoBatch.setLogAction("借用");
							}else if(mmIoBatch.getLogAction().equals("return")){
								mmIoBatch.setLogAction("归还");
							}else if(mmIoBatch.getLogAction().equals("refund")){
								mmIoBatch.setLogAction("退还");
							}
						}
						//查询操作人相关信息
						if(null != mmIoBatch.getActionUser() && !"".equals(mmIoBatch.getActionUser())){
							User actionUser = uamUserOrgService.getUserById(mmIoBatch.getActionUser());
							if(null != actionUser){
								mmIoBatch.setActionUser(actionUser.getRealName());
							}				
						}
						//查询负责人相关信息
						if(null != mmIoBatch.getManager() && !"".equals(mmIoBatch.getManager())){
							User managerUser = uamUserOrgService.getUserById(mmIoBatch.getManager());
							if(null != managerUser){
								mmIoBatch.setManager(managerUser.getRealName());
							}				
						}
						
					}
					mmIoBatchlist.add(mmIoBatch);
				}
			}			
		}
		
		return mmIoBatchlist;
	}
	
	//物品入库   提交信息保存	
	@RequestMapping("materialStay")
	public String materialStay(HttpServletRequest request,MaterialPackageVo material){
		
		SessionUser currentUser = uamSessionService.getSessionUser();
		String userId = currentUser.getId();
		List<MmMaterialItem> materialList = new ArrayList<MmMaterialItem>();
		String itemAddrCode = "";//物品存放路径
		String attachPkid = ""; //上传附件的pkid
		String relevantUser = ""; //动作申请人
		MmIoBatch mmIoBatch = new MmIoBatch();
		//long insertMmIoBatch = 0;//插入动作表返回的主键  返回的主键id在对象里面取值		
		if( null != material){
			itemAddrCode = material.getItemAddrCode();
			attachPkid = material.getAttachPkid();
			relevantUser = material.getRelevantUser();
			materialList = material.getMaterialList();
		}
		
		if(materialList.size() > 0){
			mmIoBatch.setCaseCode(materialList.get(0).getCaseCode());				
			mmIoBatch.setLogAction(MaterialActionEnum.IN.getCode());//入库操作
			mmIoBatch.setManager(userId);
			if(!"".equals(attachPkid) && null != attachPkid){
				mmIoBatch.setAttachId(Long.parseLong(attachPkid));
			}
			mmIoBatch.setActionUser(relevantUser);
			mmIoBatchService.insertMmIoBatchInfo(mmIoBatch);			
			
			//插入操作获取pkid
			for(int i=0; i<materialList.size() ;i++){
				//更新主表状态
				MmMaterialItem mmMaterialItem = materialList.get(i);
				mmMaterialItem.setItemAddrCode(itemAddrCode);
				mmMaterialItem.setItemManager(userId);
				mmMaterialItem.setItemStatus(MaterialStatusEnum.INSTOCK.getCode());
				mmMaterialItem.setItemInputTime(new Date());
				mmMaterialItemService.updateMaterialInfoByPkid(mmMaterialItem);
				
				//向中间表插入记录，插入之前先获取插入表mmIoBatch记录的pkid作为BATCH_ID插入下表				
				MmItemBatch mmItemBatch = new MmItemBatch();
				mmItemBatch.setItemId(materialList.get(i).getPkid());
				mmItemBatch.setBatchId(mmIoBatch.getPkid());//sqlserver返回插入的主键id
				mmItemBatchService.insertSelective(mmItemBatch);
			}	
		}	
		return  "material/materialList";
	}

    
	//借用
    @RequestMapping(value="materialBorrowSave")
    @ResponseBody
    public AjaxResponse<String> materialBorrowSave(String  pkids,String actionUser,String actionPreDate,String actionReason,String actionRemark){
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	SessionUser currentUser = uamSessionService.getSessionUser();
		String userId = currentUser.getId();
    	
		MmMaterialItem mmMaterialItem = new MmMaterialItem();//物品主表
    	MmMaterialItem mmMaterialItemForCaseCode = new MmMaterialItem();//物品主表 获取caseCode
    	long m=0,n=0,k=0;
     	try{
        	if(!"".equals(pkids) && pkids != null){
        		String pkid[] = pkids.split(",");
        		//单次操作的caseCode相同
        		mmMaterialItemForCaseCode = mmMaterialItemService.queryMmMaterialByPkid(Long.parseLong(pkid[0]));
        		//插入动作表
        		MmIoBatch mmIoBatch = new MmIoBatch();
     			mmIoBatch.setActionPreDate(sdf.parse(actionPreDate));
     			mmIoBatch.setActionReason(actionReason);
     			mmIoBatch.setActionRemark(actionRemark);
     			mmIoBatch.setActionUser(actionUser);
     			mmIoBatch.setCaseCode(mmMaterialItemForCaseCode == null ? "":mmMaterialItemForCaseCode.getCaseCode());     			
     			mmIoBatch.setManager(userId);
     			mmIoBatch.setLogAction(MaterialActionEnum.OUT.getCode());
     			m = mmIoBatchService.insertMmIoBatchInfo(mmIoBatch);//插入外借动作	             		
     		
			for(int i=0; i<pkid.length; i++){
				mmMaterialItem.setItemStatus(MaterialStatusEnum.BORROW.getCode());
				mmMaterialItem.setItemOutputTime(new Date());
				mmMaterialItem.setActionPreDate(sdf.parse(actionPreDate));
				mmMaterialItem.setPkid(Long.parseLong(pkid[i]));
				n = mmMaterialItemService.updateMaterialInfoByPkid(mmMaterialItem);
				
				//向中间表插入记录	 插入之前先插入记录，获取pkid作为BATCH_ID插入下表				
				MmItemBatch mmItemBatch = new MmItemBatch();
				mmItemBatch.setItemId(Long.parseLong(pkid[i]));
				mmItemBatch.setBatchId(mmIoBatch.getPkid());//sqlserver返回插入的主键id
				k = mmItemBatchService.insertSelective(mmItemBatch);
				}			
        	} 	
     		if(m>0 && n>0 && k>0){
     			response.setSuccess(true);
     			response.setMessage("恭喜,借用成功！"); 
     		}else{
     			response.setSuccess(false);
     			response.setMessage("物品借用操作失败！"); 
     		}
     	}catch(Exception e){
     		response.setSuccess(false);
     		response.setMessage(e.getMessage());	
     	}
     	return response;
    }
    
    
    //归还 退还合二为一
    @RequestMapping(value="materialReturnSave")
    @ResponseBody
    public AjaxResponse<String> materialReturnSave(String  pkids,String actionUser,String actionRemark,String flag){ 
    	AjaxResponse<String> response = new AjaxResponse<String>();
    	SessionUser currentUser = uamSessionService.getSessionUser();
		String userId = currentUser.getId();
    	
		MmMaterialItem mmMaterialItem = new MmMaterialItem();//物品主表
    	MmMaterialItem mmMaterialItemForCaseCode = new MmMaterialItem();//物品主表 获取caseCode
    	long m=0,n=0,k=0;
     	try{
        	if(!"".equals(pkids) && pkids != null){
        		String pkid[] = pkids.split(",");
        		//单次操作的caseCode相同
        		mmMaterialItemForCaseCode = mmMaterialItemService.queryMmMaterialByPkid(Long.parseLong(pkid[0]));
        		//插入动作表
        		MmIoBatch mmIoBatch = new MmIoBatch();     			     			
     			mmIoBatch.setActionRemark(actionRemark);
     			mmIoBatch.setActionUser(actionUser);
     			mmIoBatch.setCaseCode(mmMaterialItemForCaseCode == null ? "":mmMaterialItemForCaseCode.getCaseCode());     			
     			mmIoBatch.setManager(userId);
     			if("true".equals(flag)){
     				//归还     	
     				mmIoBatch.setLogAction(MaterialActionEnum.RETURN.getCode());
     			}else if("false".equals(flag)){
     				//退还     	
     				mmIoBatch.setLogAction(MaterialActionEnum.REFUND.getCode());
     			}     			
     			m = mmIoBatchService.insertMmIoBatchInfo(mmIoBatch);//插入外借动作	             		
     		
			for(int i=0; i<pkid.length; i++){				
     			if("true".equals(flag)){
     				mmMaterialItem.setItemStatus(MaterialStatusEnum.INSTOCK.getCode());
     				mmMaterialItem.setPkid(Long.parseLong(pkid[i]));
     			}else if("false".equals(flag)){
     				mmMaterialItem.setItemStatus(MaterialStatusEnum.BACK.getCode());
     				mmMaterialItem.setItemBackTime(new Date());
     				mmMaterialItem.setPkid(Long.parseLong(pkid[i]));
     				
     			}
				n = mmMaterialItemService.updateMaterialInfoByPkid(mmMaterialItem);
				
				//向中间表插入记录	 插入之前先插入记录，获取pkid作为BATCH_ID插入下表				
				MmItemBatch mmItemBatch = new MmItemBatch();
				mmItemBatch.setItemId(Long.parseLong(pkid[i]));
				mmItemBatch.setBatchId(mmIoBatch.getPkid());//sqlserver返回插入的主键id
				k = mmItemBatchService.insertSelective(mmItemBatch);
				}			
        	} 	
     		if(m>0 && n>0 && k>0){
     			response.setSuccess(true);
     			response.setMessage("恭喜,归还成功！"); 
     		}else{
     			response.setSuccess(false);
     			response.setMessage("物品归还操作失败！"); 
     		}
     	}catch(Exception e){
     		response.setSuccess(false);
     		response.setMessage(e.getMessage());	
     	}
     	return response;
    }
    
    
    //物品删除
    @RequestMapping(value="materialDelete")
    @ResponseBody
    public AjaxResponse<String> materialDelete(String  pkids,String actionUser,String actionRemark,String flag){    	
    	
    	AjaxResponse<String> response = new AjaxResponse<String>();    	
		MmMaterialItem mmMaterialItem = new MmMaterialItem();//物品主表
    	long m=0;
     	try{
        	if(!"".equals(pkids) && pkids != null){
        		String pkid[] = pkids.split(","); 
        		for(int i=0; i<pkid.length; i++){
        			//逻辑删除
     				mmMaterialItem.setIsDeleted("Y");				
     				mmMaterialItem.setPkid(Long.parseLong(pkid[i])); 
     				m = mmMaterialItemService.updateMaterialInfoByPkid(mmMaterialItem);
				}			
        	} 	
     		if(m>0){
     			response.setSuccess(true);
     			response.setMessage("恭喜,物品信息删除成功！"); 
     		}else{
     			response.setSuccess(false);
     			response.setMessage("物品信息删除失败！"); 
     		}
     	}catch(Exception e){
     		response.setSuccess(false);
     		response.setMessage(e.getMessage());	
     	}
     	return response;
    }
	
}
