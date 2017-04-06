<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/basic.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/dropzone.css" rel="stylesheet">
<link href="${ctx}/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/steps/jquery.steps.css" rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">
<!-- datepikcer -->
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="${ctx}/css/transcss/comment/caseComment.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/js/viewer/viewer.min.css" />
<!-- 新调整页面样式 -->
<link href="${ctx}/css/common/caseDetail.css" rel="stylesheet">
<link href="${ctx}/css/common/details.css" rel="stylesheet">
<link href="${ctx}/css/iconfont/iconfont.css" rel="stylesheet">
<link href="${ctx}/css/common/btn.css" rel="stylesheet">
<link href="${ctx}/css/common/input.css" rel="stylesheet">
<link href="${ctx}/css/common/table.css" rel="stylesheet">

<link rel="stylesheet" href="${ctx}/css/common/step-mend.css" >
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
<input type="hidden" id="isMainLoanBank" name="isMainLoanBank" value="1"/>
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
                                    <li class="active"><a id="tab1" data-toggle="tab" href="#tab-1">主选银行</a></li>                                 
                                </ul>
                            </div>
                        </div>

                        <div class="panel-body no-padding">
                            <div class="tab-content">
                                <div id="tab-1" class="tab-pane active">
                                   <div class="ibox float-e-margins">
                                        <div>
                                        
                          <div id="wizard">
							<h3>贷款签约</h3>
							<section>
							<div class="step-content" style="margin-top: -30px;">
								<div class="ibox" style="width:988px;">
								<div class="ibox-content">
								<form id="mortgageForm" class="form_list">
								    <input type="hidden" name="pkid" id="pkid"  value="${toMortgage.pkid}"/>
									<input type="hidden" name="caseCode" value="${caseCode}">									
		                            <div class="marinfo">
		                                    <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small"> 主贷人<span class="star">*</span></label>
													 <input class=" input_type yuanwid" placeholder="" value="${custName}" readonly="readonly" style="background-color:#ccc;">
		                                         </div>
												<div class="form_content">
													<label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>贷款总额 </label>
													<input type="text" name="mortTotalAmount" style="background-color:#ccc;" id="mortTotalAmount" class="input_type yuanwid"  readonly="readonly"  onkeyup="checkNum(this)"
														   value="<fmt:formatNumber value='${toMortgage.mortTotalAmount/10000}' type='number' pattern='#0.00' />">
													<span class="date_icon">万元</span>
												</div>
												<div class="form_content">
													<label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>贷款类型</label>
													<aist:dict id="mortType" name="mortType"
															   clazz="select_control data_style" display="select" dictType="30016"
															   defaultvalue="${toMortgage.mortType }" />
												</div>
		                                     </div>    
		                                     
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">主贷人单位</label>
													 <input class="input_type mendwidth" placeholder="" style="background-color:#ccc;" value="${custCompany}" readonly="readonly">
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">认定套数</label>
													 <input type="text" name="houseNum" id="houseNum" style="background-color:#ccc;" class="input_type data_style"  readonly="readonly" value="${toMortgage.houseNum}" onkeyup="checkNum2(this)">
		                                         </div>
		                                     </div>
		                                     
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">商贷金额<span class="star">*</span></label>
													 <input type="text" name="comAmount" style="background-color:#ccc;" id="comAmount" class="input_type yuanwid"  readonly="readonly" onkeyup="checkNum(this)"
															value="<fmt:formatNumber value='${toMortgage.comAmount/10000}' type='number' pattern='#0.00' />">
													 <span class="date_icon">万元</span>
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">商贷利率折扣<span class="star">*</span></label>
													 <input type="text" name="comDiscount" style="background-color:#ccc;" id="comDiscount"  readonly="readonly" class="input_type yuanwid" onkeyup="autoCompleteComDiscount(this)" placeholder="0.50~1.50之间"
															value="<fmt:formatNumber value='${toMortgage.comDiscount}' type='number' pattern='#0.00' />">
		                                         </div>
		                                         <div class="form_content">
													 <label class="control-label sign_left_small"><font color=" red" class="mr5" >*</font>商贷部分年限</label>
													 <input type="text" name="comYear" style="background-color:#ccc;" id="comYear" class="input_type data_style"  readonly="readonly" value="${toMortgage.comYear}" onkeyup="checkNum2(this)">
		                                         </div>
		                                     </div>
		                                     
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">公积金贷款金额</label>
													 <input type="text" name="prfAmount" style="background-color:#ccc;" id="prfAmount" class="input_type yuanwid"  readonly="readonly" onkeyup="checkNum(this)"
															value="<fmt:formatNumber value='${toMortgage.prfAmount/10000}' type='number' pattern='#0.00' />">
													 <span class="date_icon">万元</span>		
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">公积金贷款年限</label>
													 <input type="text" name="prfYear" style="background-color:#ccc;" readonly="readonly"  id="prfYear"　 class="input_type data_style" value="${toMortgage.prfYear}" onkeyup="checkNum2(this)">
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left">放款方式<span class="star">*</span></label>
													 <aist:dict id="lendWay" name="lendWay" clazz="select_control data_style"
															display="select" dictType="30017" defaultvalue="${toMortgage.lendWay }" />
		                                         </div>
		                                     </div>
	
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small"> 信贷员 <span class="star">*</span></label>
													 <input  type="text" readonly='readonly' name="loanerName" id="loanerName" placeholder="" class="input_type yuanwid" value="${toMortgage.loanerName}"  onclick="userSelect({startOrgId:'10B1F16BDC5E7F33E0532429030A8872',expandNodeId:'10B1F16BDC5E7F33E0532429030A8872',
															nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectLoanerUser})">
													 <i style=" position: absolute; top: 5px; right: 20px; color:#52cdec; " class="icon iconfont loanerNameImage"  id="loanerNameImage" name ="loanerNameImage"  onclick="userSelect({startOrgId:'10B1F16BDC5E7F33E0532429030A8872',expandNodeId:'10B1F16BDC5E7F33E0532429030A8872',
														nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectLoanerUser})" >&#xe627;</i>
													 </input>			
													 <input type="hidden" id="loanerOrgCode"  name="loanerOrgCode" value="${toMortgage.loanerOrgCode}" />
													 <input type="hidden" id="loanerOrgId" name ="loanerOrgId" value="${toMortgage.loanerOrgId}" />
													 <input type="hidden"  id="loanerId" name="loanerId" value="${toMortgage.loanerId}" />
													 <div class="input-group float_icon organize_icon" ></div>	
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">信贷员电话<span class="star">*</span></label>
													 <input type="text" name="loanerPhone" id="loanerPhone"  value="${toMortgage.loanerPhone}"
															placeholder="联系方式" class="input_type data_style">
		                                         </div>
		                                         <div class="form_content" style="margin-top:8px;">
		                                             <label class="control-label sign_left">信贷员到场</label> 
		                                             <div class="controls" >
														 <c:if test="${toMortgage.isLoanerArrive=='1'}">
															 <label class="radio inline"> <input type="radio" value="1" checked="checked" name="isLoanerArrive">是</label>
															 <label class="radio inline"> <input type="radio" value="0" name="isLoanerArrive" checked="checked">否</label>
														 </c:if>
														 <c:if test="${toMortgage.isLoanerArrive=='0'}">
															 <label class="radio inline"> <input type="radio" value="1"  name="isLoanerArrive">是</label>
															 <label class="radio inline"> <input type="radio" value="0" checked="checked" name="isLoanerArrive">否</label>
														 </c:if>
														 <c:if test="${toMortgage.isLoanerArrive==null}">
															 <label class="radio inline"> <input type="radio" value="1"  name="isLoanerArrive">是</label>
															 <label class="radio inline"> <input type="radio" value="0"  name="isLoanerArrive" >否</label>
														 </c:if>

		                                             </div>
		                                         </div>
		                                     </div>
		                                     
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small select_style mend_select">签约时间<span class="star" >*</span>
		                                             </label>
		                                             <div class="input-group sign-right dataleft input-daterange pull-left" id="date_4">
														 <input type="text" value="<fmt:formatDate  value='${toMortgage.signDate}' type='both' pattern='yyyy-MM-dd'/>"
																class="input_type yuanwid" style="background-color:#ccc;" readonly="readonly"   name="signDate" id="signDate" readonly>
                                                           <%-- <input class="input_type yuanwid" type="text" placeholder=""　value name="signDate" id="signDate" readonly>--%>
                                                     </div>
		                                         </div>
		                                         <div class="form_content radio-seat" style="margin-top:7px;">
		                                             <label class="control-label sign_left_small">需要放款前报告</label>							             
										             <div class="controls">
														 <c:if test="${toMortgage.ifReportBeforeLend =='1'}">
															 <label class="radio inline"> <input type="radio" value="1"　 checked="checked" name="ifReportBeforeLend">是</label>

														 </c:if>
														 <c:if test="${toMortgage.ifReportBeforeLend =='0'}">

															 <label class="radio inline"> <input type="radio" value="0"  checked="checked"　name="ifReportBeforeLend" checked="checked">否</label>
														 </c:if>

                                                     </div>
		                                         </div>
		                                     </div>
		                                     
		                                     <div class="line">
												 <div class="form_content radio-seat" style="margin-top:5px;">
													 <label class="control-label sign_left_small">是否临时银行</label>
													 <div class="controls ">
														 <label class="radio inline"> <input type="radio" value="1" id="isTmpBank" name="isTmpBank" ${empty source?'':'readonly="true"' }>是</label>
														 <label class="radio inline"> <input type="radio" value="0" name="isTmpBank" ${empty source?'':'readonly="true"' } checked="checked">否</label>
													 </div>
												 </div>
												 <div class="form_content">
													 <label class="control-label sign_left_small select_style mend_select">推荐函编号<span class="star" >*</span>
													 </label>
													 <div class="input-group sign-right dataleft input-daterange pull-left">
														 <input type="text" name="recLetterNo" id="recLetterNo" readonly="readonly" style="background-color:#ccc;"  value="${toMortgage.recLetterNo}"  class="input_type yuanwid" />
													 </div>
												 </div>
		                                     </div>

		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">贷款银行</label>																										
													 <select  name="bank_type" class="select_control" id="bank_type" ></select>
													 <input type="hidden" value="${toMortgage.finOrgCode}" id="caseFinOrgCode" />
		                                         </div>
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small" style="width: 204px;">贷款支行<span class="star">*</span></label>
													 <select  name="finOrgCode" class="select_control" id="finOrgCode" ></select>
		                                         </div>
		                                     </div>
		                                     <div class="line">
		                                         <div class="form_content">
		                                             <label class="control-label sign_left_small">备注</label>
													 <input type="text" name="remark" id="remark" style="background-color:#ccc;" readonly="readonly"  value="${toMortgage.remark}"  class="input_type optionwid">
		                                         </div>
		                                     </div>		                                     
		                               </div>
									</form>
								</div>
							</div>
						</div>
						
						
						<div class="form-btn">
		                    <div class="text-center">
		                         <button class="btn btn-success btn-space" onclick="submit()">提交</button>
								<button class="btn btn-success btn-space" onclick="refuse()">驳回</button>
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
<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
<script src="${ctx}/js/plugins/dropzone/dropzone.js"></script>
<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
<script src="${ctx}/js/plugins/staps/jquery.steps.min.js"></script> 
<script src="${ctx}/js/plugins/chosen/chosen.jquery.js?v=1.01"></script> 
<!-- 上传附件结束 -->
<script	src="${ctx}/js/trunk/task/attachment.js"></script> 
<script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script> 
<script src="${ctx}/transjs/sms/sms.js"></script>	
<script src="${ctx}/js/jquery.blockui.min.js"></script>
<script src="${ctx}/transjs/common/caseTaskCheck.js?v=1.0.1"></script> 
<script src="${ctx}/js/trunk/comment/caseComment.js"></script>
<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
<script src="${ctx}/js/stickUp.js"></script>
<!-- 改版引入的新的js文件 --> 
<script src="${ctx}/js/viewer/viewer.min.js"></script>
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
<script>
	var isTmpBank = ${toMortgage.isTmpBank}

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
		//银行下拉列表
		if(isTmpBank=='1'){
			getParentBank($("#bank_type"),$("#finOrgCode"),$("#caseFinOrgCode").val(),"","equ");
		}else{
			getParentBank($("#bank_type"),$("#finOrgCode"),$("#caseFinOrgCode").val(),"cl","equ");
		}
		$("#mortType").attr("disabled","disabled").css("background-color", "#ccc");
		$("#lendWay").attr("disabled","disabled").css("background-color", "#ccc");
		$("input[name='isTmpBank']").on('click',isTmpBankChange);
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
		if(tag == 'cl'){
			param.tag = 'cl';
		}
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
						if(data[i].finOrgCode==finOrgCode){
							option.attr("selected",true);
						}
						selectorBranch.append(option);
					}
				}
			}
		});
		return true;
	}
	function selectLoanerUser(array) {
		selectLoanerUserCom(array,$("#mortgageForm"));
	}
	
	function selectLoanerUser_(array) {
		selectLoanerUserCom(array,$("#mortgageForm1"));
	}
	
	function selectLoanerUserCom(array,$form){
		if (array && array.length > 0) {
			$form.find("#loanerName").val(array[0].username);
			$.ajax({
				url : ctx + "/eloan/LoanerCode",
				method : "post",
				dataType : "json",
				data : {
					"userId" : array[0].userId
				},
				success : function(data) {
					$form.find("#loanerNameImage").css("color","#52cdec");
					$form.find("#loanerPhone").val(data.user.mobile);
					$form.find("#loanerId").val(data.user.id);
					$form.find("#loanerOrgCode").val(data.user.orgName);
					$form.find("#loanerOrgId").val(data.user.orgId);
				}
			})
		} else {
			$form.find("#loanerName").val("");
			$form.find("#loanerOrgCode").val("");
			$form.find("#loanerOrgId").val("");
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
				loanerName:$("#loanerName").val(),
				loanerPhone:$("#loanerPhone").val(),

				isLoanerArrive:$("input[name='isLoanerArrive']:checked").val(),
				isTmpBank:$("input[name='isTmpBank']:checked").val(),
				finOrgCode:$("#finOrgCode").val(),

				comAmount:$("#comAmount").val(),
				comDiscount:$("#comDiscount").val(),
				comYear:$("#comYear").val(),
				prfAmount:$("#prfAmount").val(),
				prfYear:$("#prfYear").val(),
				pkid:$("#pkid").val(),
				caseCode:$("#caseCode").val(),
				bankLevel: $("#finOrgCode").find('option:selected').attr('coLevel'),
				taskId:$("#taskId").val(),
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
	function isTmpBankChange(){
		if(!!$(this).attr('readOnly')){
			return false;
		}
		var f=$(this).closest('form');
		var checkBtnVal = $("input[name='isTmpBank']:checked").val();
		if(checkBtnVal == '1'){
			getParentBank($("#bank_type"),$("#finOrgCode"),$("#caseFinOrgCode").val(),"","equ");
			$("#bank_type").change(function(){
				getBranchBankList($("#finOrgCode"),$("#bank_type").val(),"");
			});
		}else{
			getParentBank($("#bank_type"),$("#finOrgCode"),$("#caseFinOrgCode").val(),"cl","equ");
			$("#bank_type").change(function(){
				getBranchBankList($("#finOrgCode"),$("#bank_type").val(),"cl");
			});
		}
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

</script>
 </content>
 <content tag="local_require"></content>
</body>
</html>