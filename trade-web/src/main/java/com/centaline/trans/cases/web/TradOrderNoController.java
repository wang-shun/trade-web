package com.centaline.trans.cases.web;


import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.task.entity.ToGetPropertyBook;
import com.centaline.trans.task.entity.ToHouseTransfer;
import com.centaline.trans.task.entity.ToSign;
import com.centaline.trans.task.service.SignService;
import com.centaline.trans.task.service.ToGetPropertyBookService;
import com.centaline.trans.task.service.ToHouseTransferService;


@Controller
@RequestMapping(value="/api/case")
public class TradOrderNoController {
	
	@Autowired
	private ToCaseInfoService tocaseInfoService;
	
	@Autowired
	private ToCaseService tocaseService;
	
	@Autowired
	private SignService signservice;
	
	@Autowired
	private ToHouseTransferService tohouseTransferService;
	
	@Autowired
	private ToGetPropertyBookService togetPropertyBookService;
	
	
	/**
	 * 功能：交易单编号查询
	 * 描述：根据 [ctm案件编号]为条件去查询 [交易单编号]
	 * @param ctmCode[ctm案件编号]
	 * @author zhangxb16
	 */
	@RequestMapping(value="code")
	@ResponseBody
	public AjaxResponse TradOrderSelect(HttpServletRequest request, HttpServletResponse response, Model model, String ctmCode){
		
		try{
			if("".equals(ctmCode) || null==ctmCode){
				throw new BusinessException("ctm案件编号为必填字段！");
			}
		}catch(BusinessException ex){
			ex.printStackTrace();
			return AjaxResponse.fail(ex.getMessage());
		}
		
		try{
			String caseCode=tocaseInfoService.findcaseCodeByctmCode(ctmCode);
			if(null !=caseCode){
				return AjaxResponse.successContent(caseCode);
			}else{
				return AjaxResponse.fail("-1");  // 无匹配编号时候返回-1
			}
		}catch(BusinessException ex){
			ex.printStackTrace();
			return AjaxResponse.fail("查询失败,请刷新后再次尝试！");
		}
	}

	
	/**
	 * 功能：在途单状态查询
	 * 描述：根据 交易单编号（誉萃编号）为条件到  T_TO_CASE表中去查询status[状态], 然后再根据查询到的状态的值, 
	 *     分别到不同的表中去查询, 取出日期。返回 currentStatus[当前状态], time	[当前状态的生成时间]
	 * @author zhangxb16
	 */
	@RequestMapping(value="status")
	@ResponseBody
	public AjaxResponse inRransit(HttpServletRequest request, HttpServletResponse response, Model model, String caseCode){
		
		try{
			if("".equals(caseCode) || null==caseCode){
				throw new BusinessException("交易单编号为必填字段！");
			}
		}catch(BusinessException ex){
			ex.printStackTrace();
			return AjaxResponse.fail(ex.getMessage());
		}
		
		try{
			ToCase tocase=tocaseService.findToCaseByCaseCode(caseCode);
			
			if(null != tocase){
				String status=tocase.getStatus(); // 获取当前状态
				String createtime=null;  // 当前状态的生成时间
				SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");  // 格式化日期
				
				if(null != status){
					// 根据 status 的值来处理不同的业务逻辑
					if(CaseStatusEnum.WFD.getCode().equals(status.trim())){  // 1 未分单 -> 操作 T_TO_CASE 表  -> 取 CreateTime 字段
						ToCase casewfd=tocaseService.findToCaseByCaseCode(caseCode);
						createtime=formatDate.format(casewfd.getCreateTime());  // 将日期转为字符串
					}else if(CaseStatusEnum.YFD.getCode().equals(status.trim())){  // 2 已分单 -> 操作T_TO_CASE_INFO 表 -> 取ResDate 字段
						ToCaseInfo caseinfoyfd=tocaseInfoService.findToCaseInfoByCaseCode(caseCode);
						createtime=formatDate.format(caseinfoyfd.getResDate());
					}else if(CaseStatusEnum.YQY.getCode().equals(status.trim())){  // 3 已签约 -> 操作T_TO_SIGN 表 -> 取RealConTime 字段
						ToSign signyqy=signservice.findToSignByCaseCode(caseCode);
						createtime=formatDate.format(signyqy.getRealConTime());
					}else if(CaseStatusEnum.YGH.getCode().equals(status.trim())){  // 4 过户 -> 操作T_TO_HOUSE_TRANSFER 表 -> 取RealHtTime 字段
						ToHouseTransfer houseferygh=tohouseTransferService.findToGuoHuByCaseCode(caseCode);
						createtime=formatDate.format(houseferygh.getRealHtTime());
					}else if(CaseStatusEnum.YLZ.getCode().equals(status.trim())){  // 5 已领证 -> 操作 T_TO_GET_PROPERTY_BOOK 表 -> 取 RealPropertyGetTime字段
						ToGetPropertyBook bookylz=togetPropertyBookService.findGetPropertyBookByCaseCode(caseCode);
						createtime=formatDate.format(bookylz.getRealPropertyGetTime());
					}else{  // 不存在该状态
						return AjaxResponse.fail("该状态不存在！");
					}
					
					String data="{[\"currentStatus\":"+status+",\"time\":"+createtime+"]}";
					return AjaxResponse.successContent(data);
				}else{
					return AjaxResponse.fail("根据该交易单编号查询到的当前状态为空！");
				}
			}else{
				return AjaxResponse.fail("根据该交易单编号未查询到结果！");
			}
		}catch(BusinessException ex){
			ex.printStackTrace();
			return AjaxResponse.fail("查询失败,请刷新后再次尝试！");
		}
	}
	
	
	// 功能：交易单编号查询
	@RequestMapping(value="codejsp")
	public String code(){
		
		return "case/code";
	}
	
	// 功能：在途单状态查询
	@RequestMapping(value="statusjsp")
	public String status(){
		
		return "case/status";
	}
	
	
}
