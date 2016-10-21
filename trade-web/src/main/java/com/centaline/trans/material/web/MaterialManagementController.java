package com.centaline.trans.material.web;

import java.util.ArrayList;
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
import com.centaline.trans.material.service.MmItemBatchService;
import com.centaline.trans.material.service.MmMaterialItemService;


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
	@RequestMapping("materialList")
	public String spvList(HttpServletRequest request){
		
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
		}else{
			request.setAttribute("mmMaterialItemList", null);	
		}
		return "material/materialStorageConfirm";
		
	}
	
	//物品产品详细信息
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
			
		}else{
			request.setAttribute("mmMaterialItem", null);	
		}		
		return "material/materialDetail";		
	}
	
	//封装查询 出入记录
	private  List<MmIoBatch>  getMmIoBatchList(String pkid){
		List<MmIoBatch> mmIoBatchlist = new  ArrayList<MmIoBatch>();
		List<MmItemBatch> mmItemBatchList = new  ArrayList<MmItemBatch>();
		MmIoBatch mmIoBatch = new MmIoBatch();
		mmItemBatchList = mmItemBatchService.queryMmItemBatchList(Long.parseLong(pkid));
		if(mmItemBatchList.size() > 0){
			for(int i=0; i<mmItemBatchList.size(); i++){
				//mmIoBatch = mmIoBatchlist mmItemBatchList.get(i).getBatchId();
			}			
		}
		
		return mmIoBatchlist;
	}
	
}
