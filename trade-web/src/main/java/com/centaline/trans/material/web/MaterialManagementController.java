package com.centaline.trans.material.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.common.entity.ToPropertyInfo;
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
    
    
	//物品管理列表页面
    //快速查询
	@RequestMapping("materialList")
	public String materialList(HttpServletRequest request){
		
		SessionUser currentUser = uamSessionService.getSessionUser();
		String currentDeptId = currentUser.getServiceDepId();
		
		//MmMaterialItem mmMaterialItem=new MmMaterialItem();		
		request.setAttribute("currentDeptId", currentDeptId);
		return "material/materialList";
	}
	
	
	//物品入库前信息查询
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
		
		MmIoBatch mmIoBatch = new MmIoBatch();		
		mmItemBatchList = mmItemBatchService.queryMmItemBatchList(Long.parseLong(pkid));
		if(mmItemBatchList.size() > 0){
			for(int i=0; i<mmItemBatchList.size(); i++){
				if(null != mmItemBatchList.get(i).getBatchId()){
					mmIoBatch = mmIoBatchService.queryMmIoBatchByPkid(mmItemBatchList.get(i).getBatchId());
					if(null != mmIoBatch){						
						if(null != mmIoBatch.getLogAction() && !"".equals(mmIoBatch.getLogAction())){
							if(mmIoBatch.getLogAction().equals("stay")){
								mmIoBatch.setLogAction("入库");
							}else if(mmIoBatch.getLogAction().equals("borrow")){
								mmIoBatch.setLogAction("借用");
							}else if(mmIoBatch.getLogAction().equals("instock")){
								mmIoBatch.setLogAction("在库");
							}else if(mmIoBatch.getLogAction().equals("back")){
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
		MmIoBatch mmIoBatch = new MmIoBatch();
		//long insertMmIoBatch = 0;//插入动作表返回的主键  返回的主键id在对象里面取值
		
		if( null != material){
			itemAddrCode = material.getItemAddrCode();
			materialList = material.getMaterialList();
		}
		
		if(materialList.size() > 0){
			mmIoBatch.setCaseCode(materialList.get(0).getCaseCode());				
			mmIoBatch.setLogAction(MaterialActionEnum.IN.getCode());//入库操作
			mmIoBatch.setManager(userId);
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
				
				//向中间表插入记录	 插入之前先插入记录，获取pkid作为BATCH_ID插入下表				
				MmItemBatch mmItemBatch = new MmItemBatch();
				mmItemBatch.setItemId(materialList.get(i).getPkid());
				mmItemBatch.setBatchId(mmIoBatch.getPkid());//sqlserver返回插入的主键id
				mmItemBatchService.insertSelective(mmItemBatch);
			}	
		}	
		return  "material/materialList";
	}
	
}
