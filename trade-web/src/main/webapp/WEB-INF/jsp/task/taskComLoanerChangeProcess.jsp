<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">
<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/dropzone/basic.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/dropzone/dropzone.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/morris/morris-0.4.3.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/steps/jquery.steps.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/chosen/chosen.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />" rel="stylesheet">
<!-- datepikcer -->
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<link rel="stylesheet" href="<c:url value='/js/viewer/viewer.min.css' />" />
<!-- 新调整页面样式 -->
<link href="<c:url value='/css/common/caseDetail.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">

<link rel="stylesheet" href="<c:url value='/css/common/step-mend.css' />" >
<style type="text/css">
.wizard-big.wizard>.content {
	min-height: 450px;
}

.wizard>.content {
	min-height: 550px;
}

.wizard>.content>.body label {
	text-align: right
}

.form-control {
	margin-bottom: 5px;
	height:32px;
}
#addToEguPricingForm > .col-sm-10{
	height:37px;
}
.form-group label {
	text-align: right;
}

.radio {
	margin-left: 20px;margin-top:-10px
}

.wizard.vertical > .steps{
	width:16%
}

.wizard > .steps > ul > li{
    width: 16%;
}
.mouseover-color{
	background-color:#B4D5F5;
}
.star{color:red}
/* .chosen-drop {
    height: 120px;
    overflow-y: scroll;
} */
.wizard > .content > .body {
    width:100%;
}

.wizard > .content > .body input {
    display: inline-block;
}
input[readonly], select[disabled] {
		  background-color: #eee!important;
	}
</style>
<script language="javascript">
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var mainLoanBank = "1";

	if("${idList}" != "") {
		var idList = eval("("+"${idList}"+")");
	} else {
		var idList = [];
	}

</script>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>

<%--环节编码 --%>
<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
<input type="hidden" id="taskitem" name="taskitem" value="${taskitem}">

<!-- 交易单编号 -->
<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
<!-- 流程引擎需要字段 -->
<input type="hidden" id="taskId" name="taskId" value="${taskId }"> 
<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
<input type="hidden" name="bankOrgId" id="bankOrgId" value=""/>

<!-- 临时银行审批 -->
<input type="hidden" id="tmpBankStatus" name="tmpBankStatus"/>
	<!-- 服务流程 -->
		<div class="panel " id="serviceFlow">
        <div class="row wrapper white-bg new-heading ">
             <div class="pl10">
                 <h2 class="newtitle-big">信贷员变更</h2>
                 <div class="mt20">
                        <button type="button" class="btn btn-icon btn-blue mr5" id="btnZaitu">
                            <i class="iconfont icon">&#xe600;</i> 在途单列表
                        </button>
                        <button type="button" class="btn btn-icon btn-blue mr5" id="btnCaseView" lang="${caseCode}">
                            <i class="iconfont icon">&#xe63f;</i> 案件视图
                        </button>
                  </div>
             </div>
        </div> 

        <div class="ibox-content border-bottom clearfix space_box noborder">
            <div class="">
                    <div class="panel blank-panel">
                        <div class="panel-heading" style="padding:0;">
                            <div class="panel-options">
                                <ul class="nav nav-tabs">
									<c:if test="${toMortgage.isMainLoanBank=='0'}">
										<li class="active"><a id="tab1" data-toggle="tab" href="#tab-1">候选银行重新派单</a></li>
									</c:if>
									<c:if test="${toMortgage.isMainLoanBank!='0'}">
										<li class="active"><a id="tab2" data-toggle="tab" href="#tab-1">主选银行重新派单</a></li>
									</c:if>
                                </ul>
                            </div>
                        </div>

                        <div class="panel-body no-padding">
                            <div class="tab-content">
                                <div id="tab-1" class="tab-pane active">
                                   <div class="ibox float-e-margins">
                                        <div>
                                        
                          <div id="wizard">							
							<section>
							<div class="step-content" style="margin-top: -30px;">
								<div class="ibox" style="width:988px;">
								<div class="ibox-content">
								<form id="orderform" >
                                                	<input type="hidden" name="pkid" id="pkid"  value="${toMortgage.pkid}"/>
									<input type="hidden" name="caseCode" id="caseCode" value="${caseCode}">	
									<input type="hidden" id="isMainLoanBank" name="isMainLoanBank" value="${toMortgage.isMainLoanBank}"/>
                                                    <div class="form_list" style="margin-top: 0;">
                                                        <div class="marinfo">
                                                            <h4>派单信息</h4>
                                                            <div class="line">
                                                                <div class="form_content radio-seat">
                                                                    <label class="control-label sign_left_small">是否临时银行</label>
                                                                    <div class="controls ">
                                                                       <label class="radio inline"> <input type="radio" value="1" name="isTmpBank" onclick="return false;" readonly="readonly" >是
                                                                        </label> <label class="radio inline"> <input type="radio" value="0" name="isTmpBank" onclick="return false;" readonly="readonly" checked="checked">否
                                                                        </label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">银行</label>
                                                                    <input type="hidden" value="${toMortgage.finOrgCode}" id="caseFinOrgCode" />
                                                                    <select name="bank_type" id='bank_type' class="select_control  ">
                                                                    </select>
                                                                    <select name="finOrgCode" id='finOrgCode' class=" select_control  ">
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                            	                                     
	                                         	 				<input type="hidden" id="loanerOrgCode"  name="loanerOrgCode" value="${toMortgage.loanerOrgCode}" />
												 				<input type="hidden" id="loanerOrgId" name ="loanerOrgId" value="${toMortgage.loanerOrgId}"/>
												 				<input type="hidden"  id="loanerId" name="loanerId" value="${toMortgage.loanerId}"/>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">信贷员</label> <input class="input_type yuanwid" readonly="readonly" placeholder="" value="${toMortgage.loanerName}" name='loanerName' onclick="selectLoanerByOrgId();">
                                                                    <i style=" position: absolute; top: 5px; right: 20px; color:#52cdec; " class="icon iconfont loanerNameImage" id="loanerNameImage" name="loanerNameImage" onclick="selectLoanerByOrgId();" ></i>
                                                                    
                                                                </div>
                                                                <div class="form_content">
					                                             <label class="control-label sign_left_small">信贷员电话<span class="star">*</span></label>
																 <input type="text" name="loanerPhone" id="loanerPhone" placeholder="联系方式" class="input_type data_style" value="${toMortgage.loanerPhone}">
		                                         				</div>
                                                            </div>

                                                            <div class="line tmpBankHide nonTmpBankShow">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small select_style mend_select">
                                                                        预定签约时间
                                                                    </label>
                                                                    <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                                                                        <input name="resSignTime" class="input_type yuanwid datatime" type="text" value="<fmt:formatDate value="${toMortLoaner.resSignTime}" pattern="yyyy-MM-dd"/>" placeholder="">
                                                                    </div>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">签约地点</label><select name="resSignAddr"  class=" select_control data_style ">
                                                                    </select>
                                                                </div>

                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form_list tmpBankHide nonTmpBankShow" style="margin-top: 0;" id='div_order'>
                                                        <div class="marinfo">
                                                            <h4>客户信息</h4>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small"> 主贷人姓名</label> 
                                                                    <input name='custName' class='input_type yuanwid'value="${custName}" readonly="readonly">
                                                                    
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">贷款总额</label> <input readonly="readonly" name="mortTotalAmount" class=" input_type yuanwid" value="<fmt:formatNumber value='${toMortgage.mortTotalAmount/10000}' type='number' pattern='#0.00' />" >
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">贷款类型</label>
                                                                         <aist:dict id="mortType" name="mortType"
															clazz="select_control data_style" display="select" dictType="30016"
															defaultvalue="${toMortgage.mortType }"  />
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">商贷部分金额</label> <input readonly="readonly" name="comAmount" value="<fmt:formatNumber value='${toMortgage.comAmount/10000}' type='number' pattern='#0.00' />" class=" input_type yuanwid"  >
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">商贷期望年限</label> <input readonly="readonly" name="comYear" value="${toMortgage.comYear}" class=" input_type data_style"  >
                                                                   <span class="date_icon">年</span>
                                                                </div>
                                                            </div>
                                                            <div class="line">
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">公积金部分金额</label> <input readonly="readonly" name='prfAmount' value="<fmt:formatNumber value='${toMortgage.prfAmount/10000}' type='number' pattern='#0.00' />" class=" input_type yuanwid"  >
                                                                   <span class="date_icon">万元</span>
                                                                </div>
                                                                <div class="form_content">
                                                                    <label class="control-label sign_left_small">公积金期望年限</label> <input readonly="readonly" name="prfYear" value="${toMortgage.prfYear}" class=" input_type data_style" >
                                                                   <span class="date_icon">年</span>
                                                                </div>
                                                            </div>
  
                                                        </div>
                                                    </div>

                                                    </form>
								
							
								</div>
							</div>
						</div>
						
						<div id="caseCommentList" class="view-content"></div>
						
						<div class="form-btn">
		                    <div class="text-center">
		                         <button class="btn btn-success btn-space" onclick="submit()">重新派单</button>
								<button class="btn btn-success btn-space" onclick="refuse()">取消派单</button>
		                    </div>
		                </div>
					</section>
				</div>
              </div>
        	</div>
          </div>
        </div>
      </div>
     </div>
   </div>
</div>
</div>  

<content tag="local_script">
<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
<script src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script> 
<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
<script src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script>
<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
<script src="<c:url value='/js/plugins/staps/jquery.steps.min.js' />"></script> 
<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script> 
<!-- 上传附件结束 -->
<script	src="<c:url value='/js/trunk/task/attachment.js' />"></script> 
<script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script> 
<script src="<c:url value='/transjs/sms/sms.js' />"></script>	
<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
<script src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script> 
<script src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
<script src="<c:url value='/js/stickUp.js' />"></script>
<!-- 改版引入的新的js文件 --> 
<script src="<c:url value='/js/viewer/viewer.min.js' />"></script>
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>

<!-- 改版引入的新的js文件 --> 
<script src="<c:url value='/js/common/textarea.js' />"></script>
<script src="<c:url value='/js/common/common.js' />"></script> 
<script>
	var isTmpBank = '${toMortgage.isTmpBank}';
	var tradeCenter;
	var resSignAddr ='${toMortLoaner.resSignAddr}';
	/**
	 * 加载签约地点 
	 *val 选中的ID
	 */
	function loadTradeCenterList(val){
		if(tradeCenter){ //只加载一次
			return buildTradeCenter(tradeCenter,val);
		}
		$.ajax({
		    url:ctx+"/weixin/signroom/getTradeCenterList",
		    async:false,
	    	method:"post",
	    	dataType:"json",
	    	success:function(data){
	    		if(data){
	    			tradeCenter=data;
	    			return buildTradeCenter(tradeCenter,val);
	    		}
	    }});
	}
	/***
	 * 生成签约地点下拉框
	 */
	function buildTradeCenter(tradeCenter,val){
		var saddr = $('[name=resSignAddr]');
		saddr.find('option').remove();
		saddr.append($("<option value=''>请选择</option>"));
		$(tradeCenter).each(function(i,e){
			saddr.append($("<option value='"+e.pkid+"'>"+e.centerName+"</option>"));	
		});
		if(val){
			saddr.val(val);
		}
	}
	function checknum(obj){
		obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
		obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是.
		obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.
		obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	}

	/*贷款折扣自动补全*/
	function autoCompleteComDiscount(obj){

		obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
		obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是.
		obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.
		obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");

		var inputVal = obj.value;
		if(inputVal>=0.5 && inputVal<=1.5){
			var reg =/^[01]{1}\.{1}\d{3,}$/;
			if(reg.test(inputVal)){
				obj.value = inputVal.substring(0,4);
			}
		}
	}
	jQuery(document).ready(function() {		
		$("#caseCommentList").caseCommentGrid({
			caseCode : caseCode
			
		});
		loadTradeCenterList(resSignAddr);
		
		getParentBank($("#bank_type"),$("#finOrgCode"),$("#caseFinOrgCode").val(),"cl","equ");
		
		$("[name=mortType]").attr("disabled","disabled");
	
		
		
 		$("select[name='finOrgCode']").change(function(){     	
    		var finOrgCode = $("select[name='finOrgCode']").val();	  		
    		var finOrgId='';			
    		getFinOrgId(finOrgCode);   		
    	}) 
    	
		$("#bank_type").change(function(){
			//getBranchBankList(selectorBranch,pcode,finOrgCode,tag,flag)
			getBranchBankList($("#finOrgCode"),$("#bank_type").val(),"cl");
		});		

	});

	//查询分行信息
	function getParentBank(selector,selectorBranch,finOrgCode,tag,flag){
		var bankHtml = "<option value=''>请选择</option>";
		var param = {nowCode:finOrgCode};
		if(tag == 'cl'){
			param.tag = 'cl';
		}
		$.ajax({
			cache:true,
			url:ctx+"/manage/queryParentBankList",
			method:"post",
			dataType:"json",
			async:false,
			data:param,
			success:function(data){
				if(data != null){
					for(var i = 0;i<data.length;i++){
						if((data[i].finOrgCode!='1032900'&&data[i].finOrgCode!='3082900')||flag!='egu'){//不作农业银行的讯价
							var coLevelStr='';
							bankHtml+="<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>";
						}
					}
				}
			},
			error:function(e){
				window.wxc.error(e);
			}
		});
		selector.find('option').remove();
		selector.append($(bankHtml));
		$.ajax({
			url:ctx+"/manage/queryParentBankInfo",
			method:"post",
			dataType:"json",
			async:false,
			data:{finOrgCode:finOrgCode,flag:flag},
			success:function(data){
				if(data != null){
					selector.val(data.content);
				}
			}
		});

		getBranchBankList(selectorBranch,selector.val(),finOrgCode,tag,flag);
		return bankHtml;
	}
	//查询支行信息
	function getBranchBankList(selectorBranch,pcode,finOrgCode,tag,flag){
		selectorBranch.find('option').remove();
		selectorBranch[0];
		selectorBranch.append($("<option value=''>请选择</option>"));

		var param = {faFinOrgCode:pcode,flag:flag,nowCode:finOrgCode};
		
		param.tag = 'cl';
		$.ajax({
			cache:true,
			url:ctx+"/manage/queryBankListByParentCode",
			method:"post",
			dataType:"json",
			async:false,
			data:param,
			success:function(data){
				if(data != null){
					for(var i = 0;i<data.length;i++){
						var coLevelStr='('+data[i].coLevelStr+')';

						var option = $("<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>");
						if(data[i].finOrgCode == finOrgCode){
							getFinOrgId(finOrgCode);						
							option.attr("selected",true);			
							
						}
						selectorBranch.append(option);
					}
				}
			}
		});
		return true;
	}

	function getFinOrgId(finOrgCode){
		var finOrgId='';
		if(null != finOrgCode && "" != finOrgCode && undefined != finOrgCode){
			$.ajax({
			    url:ctx+"/manage/queryBankOrgIdByOrgCode",
			    method:"post",
			    dataType:"json",
				async:false,
			    data:{finOrgCode:finOrgCode},
			    
			    success:function(data){
		    		if(data != null){
		    			finOrgId = data.content;
		    		}
		    	}
		 });
		$("#bankOrgId").val(finOrgId);  
		} 
	}
	
	function submit(){
		if(!checkForm()) {
			return;
		}

		$.ajax({
			url:ctx+"/task/loanerProcessSubmit",
			method:"post",
			dataType:"json",
			data:{
				loanerOrgCode:$("#loanerOrgCode").val(),
				loanerOrgId:$("#loanerOrgId").val(),
				loanerId:$("#loanerId").val(),
				loanerName:$("[name=loanerName]").val(),
				loanerPhone:$("#loanerPhone").val(),
				isTmpBank:$("input[name='isTmpBank']:checked").val(),
				finOrgCode:$("[name=finOrgCode]").val(),
				isMainLoanBank:$("#isMainLoanBank").val(),				
				mortTotalAmount:$("input[name=mortTotalAmount]").val(),
				custName:$("input[name=custName]").val(),
				mortType:$("[name=mortType]").val(),				
				comAmount:$("[name=comAmount]").val(),
				comDiscount:$("[name=comDiscount]").val(),
				comYear:$("[name=comYear]").val(),
				prfAmount:$("[name=prfAmount]").val(),
				prfYear:$("[name=prfYear]").val(),
				pkid:$("#pkid").val(),
				caseCode:$("#caseCode").val(),
				bankLevel: $("[name=finOrgCode]").find('option:selected').attr('coLevel'),
				taskId:$("#taskId").val(),
				resSignTime:$('[name=resSignTime]').val(),
				resSignAddr:$('[name=resSignAddr]').val(),
				processInstanceId:$("#processInstanceId").val()
			},
			beforeSend:function(){
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}});
				$(".blockOverlay").css({'z-index':'9998'});
			},
			complete: function() {
				$.unblockUI();
				if(status=='timeout'){//超时,status还有success,error等值的情况
					Modal.alert(
							{
								msg:"抱歉，系统处理超时。"
							});
					$(".btn-primary").one("click",function(){
						parent.$.fancybox.close();
					});
				}
			} ,
			success : function(data) {
				if(data.success){
					window.wxc.success("保存成功。",{"wxcOk":function(){
						window.close();
						window.opener.callback();
					}});
				}else{
					window.wxc.error(data.message);
				}
			},
			error : function(errors) {
				window.wxc.error(errors);
			}

		});
	}
	function refuse(){
		$.ajax({
			url:ctx+"/task/loanerProcessDelete",
			method:"post",
			dataType:"json",
			data:{
				pkid:$("#pkid").val(),
				caseCode:$("#caseCode").val(),
				taskId:$("#taskId").val(),
				processInstanceId:$("#processInstanceId").val(),
				isMainLoanBank:$("#isMainLoanBank").val()
			},
			beforeSend:function(){
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}});
				$(".blockOverlay").css({'z-index':'9998'});
			},
			complete: function() {
				$.unblockUI();
				if(status=='timeout'){//超时,status还有success,error等值的情况
					Modal.alert(
							{
								msg:"抱歉，系统处理超时。"
							});
					$(".btn-primary").one("click",function(){
						parent.$.fancybox.close();
					});
				}
			} ,
			success : function(data) {
				if(data.success){
					window.wxc.success("交易顾问派单流程成功结束",{"wxcOk":function(){
						window.close();
						window.opener.callback();
					}});
				}else{
					window.wxc.error(data.message);
				}
			},
			error : function(errors) {
				window.wxc.error(errors);
			}

		});


	}
	
	//验证控件checkUI();
	function checkForm() {

		if($('input[name=loanerName]').val()=='') {
			window.wxc.alert("信贷员为必选项!");
			$('input[name=loanerName]').focus();
			return false;
		}
		if($('input[name=loanerPhone]').val()=='') {
			window.wxc.alert("信贷员电话为必填项!");
			$('input[name=loanerPhone]').focus();
			return false;
		}
		return true;
	}
	
	
	//信贷员选择
	function selectLoanerByOrgId(){	
		var finOrgId = $("#bankOrgId").val();			
		if(finOrgId != null  && finOrgId !=""  && finOrgId != undefined){			
			userSelect({
				startOrgId : finOrgId,//非营业部
				expandNodeId : finOrgId,
				nameType : 'long|short',
				orgType : '',
				departmentType : '',
				departmentHeriarchy : '',
				chkStyle : 'radio',
				jobCode : '',		
				callBack : selectLoanerUser
			});	
		}else{	
			window.wxc.alert('您选择的银行暂时未添加信贷员信息，请联系管理员！');
		}	
	}
	
	function selectLoanerUser(array){			
		if (array && array.length > 0) {
				$("#loanerName").val(array[0].username);				
				$.ajax({
					url : ctx + "/eloan/LoanerCode",
					method : "post",
					dataType : "json",
					data : {
						"userId" : array[0].userId
					},
					success : function(data) {						
						$("#loanerNameImage").css("color","#52cdec");
						$("#loanerPhone").val(data.user.mobile);
						$("#loanerId").val(data.user.id);
						$("#loanerOrgCode").val(data.user.orgName);
						$("#loanerOrgId").val(data.user.orgId);
					}
				})
			} else {
				$("#loanerName").val("");				
				$("#loanerOrgCode").val("");
				$("#loanerOrgId").val("");
			}
	}

</script>
 </content>
 <content tag="local_require"></content>
</body>
</html>