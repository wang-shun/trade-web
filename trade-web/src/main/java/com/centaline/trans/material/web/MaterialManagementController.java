package com.centaline.trans.material.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.material.entity.MmMaterialItem;
import com.centaline.trans.material.service.MmMaterialItemService;


@Controller
@RequestMapping(value="/material")
public class MaterialManagementController {
	
	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired(required=true)
	private MmMaterialItemService mmMaterialItemService;
	
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
	public String materialStorgae(HttpServletRequest request,String pkids){		
				
		List<MmMaterialItem>  mmMaterialItemList =  new ArrayList<MmMaterialItem>();
		MmMaterialItem mmMaterialItem= new MmMaterialItem();
		if(!"".equals(pkids) && pkids !=null){
			String pkid[] = pkids.split(",");
			for(int i=0; i<pkid.length;i++){				
				mmMaterialItem = mmMaterialItemService.queryMmMaterialByPkid(Long.parseLong(pkid[i]));
				mmMaterialItemList.add(mmMaterialItem);
			}			
		}
		request.setAttribute("mmMaterialItemList", mmMaterialItemList);
		return "material/materialStorgaeConfirm";
	}
	
	
	
}
