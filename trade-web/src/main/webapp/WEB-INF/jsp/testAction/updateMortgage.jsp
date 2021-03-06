<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>e+产品</title>

<link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/static/font-awesome/css/font-awesome.css' />"
	rel="stylesheet">

<link href="<c:url value='/static/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/static/css/style.css' />" rel="stylesheet">

<!-- stickUp fixed css -->
<link href="<c:url value='/static/css/plugins/stickup/stickup.css' />"
	rel="stylesheet">
<link href="<c:url value='/static/trans/css/common/stickDash.css' />"
	rel="stylesheet">

<link href="<c:url value='/static/css/plugins/aist-steps/steps.css' />"
	rel="stylesheet">
<link href="<c:url value='/static/css/plugins/toastr/toastr.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/static/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">
<!-- index_css  -->

<link href="<c:url value='/static/trans/css/eloan/eloan/eloan.css' />"
	rel="stylesheet" />
<link href="<c:url value='/static/trans/css/common/input.css' />" rel="stylesheet" />
<link href="<c:url value='/static/trans/css/common/table.css' />" rel="stylesheet" />
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"
	rel="stylesheet" />
<link href="<c:url value='/css/jquery.editable-select.min.css' />" rel="stylesheet">
</head>


<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

		<div class="row">
			<div class="ibox-content" id="zj_info">
				<form method="get" class="form_list" id="mortgageForm">
					<%-- <input type="hidden" name="caseCode" id="caseCode"
						value="${caseCode}" /> <input type="hidden" id="excutorId"
						name="excutorId" value="${excutorId}" />

					<!-- 流程引擎需要字段 -->
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId"
						name="processInstanceId" value="${processInstanceId}"> <input
						type="hidden" id="partCode" name="partCode" value="${taskitem}">
					<!--  -->
					<input type="hidden" id="eloanCode" name="eloanCode"
						value="${eloanCase.eloanCode}">
						<input type="hidden" id="createTime" name="createTime"
						value="<fmt:formatDate value="${eloanCase.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" >
						<input type="hidden" id="createBy" name="createBy"
						value="${eloanCase.createBy}"> --%>
					<ul class="form_lump">
						<li>
<%-- 							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"> </i> PKID
								</label>
							 <input class="input_type sign_right_two" readonly="readonly" value="${mortgage.pkid}"
									name="pkid" id="pkid">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"> </i>案件编号
								</label>  <input class="input_type sign_right_two" readonly="readonly"  value="${mortgage.caseCode}"
									name="caseCode" id="caseCode">
							</div> --%>
							<input type="hidden" name="pkid" value="${mortgage.pkid}" />
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"> </i>按揭贷款类型
								</label>
									<aist:dict id="mortType" name="mortType"
									clazz="input_type sign_right_two" display="select" dictType="30016"
									defaultvalue="${mortgage.mortType}" />
							</div>

						</li>
						<li>
							<%-- <div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>贷款总额
								</label>
								<input class="input_type sign_right_two" value="${mortgage.mortTotalAmount}"
									name="mortTotalAmount" id="mortTotalAmount">
							</div> --%>
							<div class="form_content">
<%--  								<label class="control-label sign_left_two"> <i
									class="red"></i>贷款机构
								</label> -->
 								<input class="input_type sign_right_two" value="${mortgage.finOrgCode}"
									name="finOrgCode" id="finOrgCode"> --%>
								
                                 <div class="form_content">
                                     <label class="control-label sign_left_small">贷款机构银行</label>																										
	 								 <select  name="bank_type" class="select_control" id="bank_type1" ></select>	
                                 </div>
                                 <div class="form_content" style="margin-left:40px;">
                                     <label class="control-label sign_left_small">贷款机构支行</label>
		 							 <select  name="finOrgCode" class="select_control" id="finOrgCode1" ></select>
                                 </div>	
                                 
							</div>
						</li>
						<li>
							<div class="form_content">
                                 <div class="form_content">
                                     <label class="control-label sign_left_small">最终贷款银行</label>																										
	 								 <select name="bank_type2"  class="select_control" id="bank_type2" ></select>
                                 </div>
                                 <div class="form_content" style="margin-left:40px;">
                                     <label class="control-label sign_left_small">最终贷款支行</label>
		 							 <select name="lastLoanBank" class="select_control" id="finOrgCode2" ></select>
                                 </div>	
							</div>
						</li>
<%-- 					<li>
					        <div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>商贷金额
								</label>
								<input class="input_type sign_right_two" value="${mortgage.comAmount}"
									name="comAmount" id="comAmount">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>商贷年限
								</label>
								<input class="input_type sign_right_two" value="${mortgage.comYear}"
									name="comYear" id="comYear">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>商贷利率折扣
								</label>
								<input class="input_type sign_right_two" value="${mortgage.comDiscount}"
									name="comDiscount" id="comDiscount">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>公积金金额
								</label>
								<input class="input_type sign_right_two" value="${mortgage.prfAmount}"
									name="prfAmount" id="prfAmount">
							</div>
						</li> --%>
						<%-- <li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>公积金贷款年限
								</label>
								<input class="input_type sign_right_two" value="${mortgage.prfYear}"
									name="prfYear" id="prfYear">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>放款方式
								</label>
								<input class="input_type sign_right_two" value="${mortgage.lendWay}"
									name="lendWay" id="lendWay">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>认定套数
								</label>
								<input class="input_type sign_right_two" value="${mortgage.houseNum}"
									name="houseNum" id="houseNum">
							</div>
						</li>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>备注
								</label>
								<input class="input_type sign_right_two" value="${mortgage.remark}"
									name="remark" id="remark">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>信贷员是否到场签约
								</label>
 								<select class="input_type sign_right_two" name="isLoanerArrive" id="isLoanerArrive">
								    <option value="1" ${mortgage.isLoanerArrive eq 1?'selected="selected"':''}>到场</option>
								    <option value="0" ${mortgage.isLoanerArrive eq 0?'selected="selected"':''}>未到场</option>
								</select>	
							</div>
							<div class="form_content input-daterange" data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i
									class="red"></i> 签约时间
								</label> <input class="input_type sign_right_two" value="<fmt:formatDate value="${mortgage.signDate}" pattern="yyyy-MM-dd" />"
									name="signDate" id="signDate" />
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</li> --%>
						<%-- <li>
							<div class="form_content input-daterange" data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i
									class="red"></i> 批贷时间
								</label> <input class="input_type sign_right_two" value="<fmt:formatDate value="${mortgage.apprDate}" pattern="yyyy-MM-dd" />"
									name="apprDate" id="apprDate" />
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
							<div class="form_content input-daterange" data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i
									class="red"></i> 他证送达时间
								</label> <input class="input_type sign_right_two" value="<fmt:formatDate value="${mortgage.tazhengArrDate}" pattern="yyyy-MM-dd" />"
									name="tazhengArrDate" id="tazhengArrDate" />
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
							<div class="form_content input-daterange" data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i
									class="red"></i> 放款时间
								</label> <input class="input_type sign_right_two" value="<fmt:formatDate value="${mortgage.lendDate}" pattern="yyyy-MM-dd" />"
									name="lendDate" id="lendDate" />
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</li> --%>
						<%-- <li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>客户编号
								</label>
								<input class="input_type sign_right_two" value="${mortgage.custCode}"
									name="custCode" id="custCode">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>客户单位
								</label>
								<input class="input_type sign_right_two" value="${mortgage.custCompany}"
									name="custCompany" id="custCompany">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>客户名称
								</label>
								<input class="input_type sign_right_two" value="${mortgage.custName}"
									name="custName" id="custName">
							</div>
						</li> --%>
						<%-- <li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>是否需要评估报告
								</label>
								<select class="input_type sign_right_two" name="ifReportBeforeLend" id="ifReportBeforeLend">
								    <option value="1" ${mortgage.ifReportBeforeLend eq 1?'selected="selected"':''}>需要</option>
								    <option value="0" ${mortgage.ifReportBeforeLend eq 0?'selected="selected"':''}>不需要</option>
								</select>	
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>信贷员姓名
								</label>
								<input class="input_type sign_right_two" value="${mortgage.loanerName}"
									name="loanerName" id="loanerName">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>信贷员电话
								</label>
								<input class="input_type sign_right_two" value="${mortgage.loanerPhone}"
									name="loanerPhone" id="loanerPhone">
							</div>
						</li> --%>
						<%-- <li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>最终贷款银行
								</label>
								<input class="input_type sign_right_two" value="${mortgage.lastLoanBank}"
									name="lastLoanBank" id="lastLoanBank">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>办理方式
								</label>
								<input class="input_type sign_right_two" value="${mortgage.isDelegateYucui}"
									name="isDelegateYucui" id="isDelegateYucui">
							</div>
							<div class="form_content input-daterange"  data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i
									class="red"></i>公积金申请时间
								</label>
								<input class="input_type sign_right_three" value="<fmt:formatDate value="${mortgage.prfApplyDate}" pattern="yyyy-MM-dd" />"
									name="prfApplyDate" id="prfApplyDate" />
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>

						</li> --%>
						<li>
<%-- 							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>是否需要复议
								</label>
								<select class="input_type sign_right_two" name="ifRequireReconsider" id="ifRequireReconsider">
								    <option value="1" ${mortgage.ifRequireReconsider eq 1?'selected="selected"':''}>需要</option>
								    <option value="0" ${mortgage.ifRequireReconsider eq 0?'selected="selected"':''}>不需要</option>
								</select>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>是否主贷银行
								</label>
								<select class="input_type sign_right_two" name="isMainLoanBank" id="isMainLoanBank">
								    <option value="1" ${mortgage.isMainLoanBank eq 1?'selected="selected"':''}>是</option>
								    <option value="0" ${mortgage.isMainLoanBank eq 0?'selected="selected"':''}>否</option>
								</select>	
							</div> --%>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>是否有效
								</label>
								<select class="input_type sign_right_two" name="isActive" id="isActive">
								    <option value="1" ${mortgage.isActive eq 1?'selected="selected"':''}>是</option>
								    <option value="0" ${mortgage.isActive eq 0?'selected="selected"':''}>否</option>
								</select>	
							</div>
						</li>
						<%-- <li>
							<div class="form_content input-daterange"  data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i
									class="red"></i>创建时间
								</label>
								<input class="input_type sign_right_two" disabled="disabled"  value="<fmt:formatDate value="${mortgage.createTime}" pattern="yyyy-MM-dd" />"
									name="createTime" id="createTime" />
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>创建人
								</label>
						        <input type="hidden" id="createBy" name="createBy" value='${mortgage.createBy}'>
						        <input type="text" id="realName"  disabled="disabled" class="input_type sign_right_two" style="background-color:#FFFFFF;" readonly="readonly"   onclick="userSelect({startOrgId:'ff8080814f459a78014f45a73d820006',expandNodeId:'ff8080814f459a78014f45a73d820006',
												nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectCreateBack})" value='${createBy}'>
                                 <div class="input-group float_icon organize_icon">
                                        <i class="icon iconfont">&#xe627;</i>
                                    </div>
							</div>
								<div class="form_content input-daterange"  data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i
									class="red"></i>更新时间
								</label>
								<input class="input_type sign_right_two"  disabled="disabled" value="<fmt:formatDate value="${mortgage.updateTime}" pattern="yyyy-MM-dd" />"
									name="updateTime" id="updateTime" />
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</li> --%>
<%-- 						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>更新人
								</label>
								<input type="hidden" id="updateBy" name="updateBy" value='${mortgage.updateBy}'>
						        <input type="text" id="realName2"  disabled="disabled" class="input_type sign_right_two" style="background-color:#FFFFFF;" readonly="readonly"   onclick="userSelect({startOrgId:'ff8080814f459a78014f45a73d820006',expandNodeId:'ff8080814f459a78014f45a73d820006',
												nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUpdateBack})" value='${updateBy}'>
                                 <div class="input-group float_icon organize_icon">
                                        <i class="icon iconfont">&#xe627;</i>
                                    </div>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>贷款推荐函编号
								</label>
								<input class="input_type sign_right_two" value="${mortgage.recLetterNo}"
									name="recLetterNo" id="recLetterNo">
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>是否临时银行
								</label>
								<select class="input_type sign_right_two" name="isTmpBank" id="isTmpBank" onchange="javascript:toggleTmpBank(this)">
                                    <option value="1" ${mortgage.isTmpBank eq 1?'selected="selected"':''}>是</option>
                                    <option value="0" ${mortgage.isTmpBank eq 0?'selected="selected"':''}>否</option>
                                </select>	
							</div>
						</li> --%>
						<%-- <li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>临时银行处理人
								</label>
								<input type="hidden" id="tmpBankUpdateBy" name="tmpBankUpdateBy" value='${mortgage.tmpBankUpdateBy}'>
						        <input type="text" id="realName2"  class="input_type sign_right_two" style="background-color:#FFFFFF;" readonly="readonly"   onclick="userSelect({startOrgId:'ff8080814f459a78014f45a73d820006',expandNodeId:'ff8080814f459a78014f45a73d820006',
												nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectTmpBankBack})" value='${mortgage.tmpBankUpdateBy}'>
                                 <div class="input-group float_icon organize_icon">
                                        <i class="icon iconfont">&#xe627;</i>
                                    </div>
							</div>
							<div class="form_content input-daterange"  data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_three"> <i
									class="red"></i>临时银行处理时间
								</label>
								<input class="input_type sign_right_two" value="<fmt:formatDate value="${mortgage.tmpBankUpdateTime}" pattern="yyyy-MM-dd" />"
									name="tmpBankUpdateTime" id="tmpBankUpdateTime" />
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i
									class="red"></i>临时银行原因
								</label>
								<input class="input_type sign_right_two" value="${mortgage.isActive}"
									name="isActive" id="isActive">
							</div>
						</li> --%>
					</ul>
					<p class="text-center">
						<input type="button" class="btn btn-success submit_From" onclick="saveEloanApply()"
							value="提交"> <a type="button"
							href="${ctx}/test/mortgageList" class="btn btn-grey ml5">关闭</a>
					</p>

				</form>
			</div>

		</div>
	</div>
	<!-- main End -->
	</div>

	<!-- Mainly scripts -->
	<content tag="local_script"> <!-- stickup plugin --> <script
	<%@include
		file="/WEB-INF/jsp/tbsp/common/userorg.jsp"%>
	<script src="<c:url value='/static/js/plugins/toastr/toastr.min.js' />"></script> 
		src="<c:url value='/static/js/morris/morris.js' />"></script> <script
		src="<c:url value='/static/js/morris/raphael-min.js' />"></script> <!-- index_js -->
	   <script src="<c:url value='/static/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<!-- aist --> <script src="<c:url value='/js/jquery.blockui.min.js' />"></script> <script
		src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script> <script
		src="<c:url value='/js/template.js' />" type="text/javascript"></script> <script
		src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script> <script
		src="<c:url value='/js/jquery.editable-select.min.js' />"></script> 

 <script>
		$(document).ready(
			function() {
				$('.input-daterange').datepicker({
					format : 'yyyy-mm-dd',
					weekStart : 1,
					autoclose : true,
					todayBtn : 'linked',
					language : 'zh-CN'
				});
				//初始化银行列表1
				getParentBank($("#bank_type1"),$("#finOrgCode1"),"${mortgage.finOrgCode}","${mortgage.isTmpBank}" == "1"?"":"cl");
				
  				$("#bank_type1").change(function(){
  					getBranchBankList($("#finOrgCode1"),$("#bank_type1").val(),"${mortgage.finOrgCode}","${mortgage.isTmpBank}" == "1"?"":"cl")
			    });
  				
				//初始化银行列表2
				getParentBank($("#bank_type2"),$("#finOrgCode2"),"${mortgage.lastLoanBank}","${mortgage.isTmpBank}" == "1"?"":"cl");
				
  				$("#bank_type2").change(function(){
  					getBranchBankList($("#finOrgCode2"),$("#bank_type2").val(),"${mortgage.lastLoanBank}","${mortgage.isTmpBank}" == "1"?"":"cl")
			    });
			}
		);
		function saveEloanApply() {		
			var jsonData = $("#mortgageForm").serializeArray();
			console.log(jsonData);
			console.log("===Result==="+JSON.stringify(jsonData));
			var url = "${ctx}/test/saveMortgage";
			$.ajax({
				cache : false,
				async : true,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				data : jsonData,
				beforeSend : function() {
					$.blockUI({
						message : $("#salesLoading"),
						css : {
							'border' : 'none',
							'z-index' : '1900'
						}
					});
					$(".blockOverlay").css({
						'z-index' : '1900'
					});
				},
				complete : function() {
					$.unblockUI();
				},
				success : function(data) {
					window.wxc.success(data.message);
					var bohui = $("#processInstanceId").val();
					if (bohui != null && bohui != '') {
						window.close();
						window.opener.initData();
					} else {
						window.close();
						window.opener.initData();
					}

				},
				error : function(errors) {
					window.wxc.error("数据保存出错");
				}
			});
		}
		function selectCreateBack(array) {
			if (array && array.length > 0) {
				$("#realName").val(array[0].username);
				$("#createBy").val(array[0].userId);

			} else {
				$("#realName").val("");
				$("#createBy").val("");
			}
		}
		function selectUpdateBack(array) {
			if (array && array.length > 0) {
				$("#realName2").val(array[0].username);
				$("#updateBy").val(array[0].userId);

			} else {
				$("#realName2").val("");
				$("#updateBy").val("");
			}
		}
		function selectTmpBankBack(array) {
			if (array && array.length > 0) {
				$("#realName3").val(array[0].username);
				$("#tmpBankUpdateBy").val(array[0].userId);

			} else {
				$("#realName3").val("");
				$("#tmpBankUpdateBy").val("");
			}
		}
		
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
		
		//切换临时银行
		function toggleTmpBank(element){
			//初始化银行列表
			getParentBank($("#bank_type1"),$("#finOrgCode1"),"${mortgage.finOrgCode}",$(element).val() == "1"?"":"cl");
			
			$("#bank_type1").change(function(){
				getBranchBankList($("#finOrgCode1"),$("#bank_type1").val(),"${mortgage.finOrgCode}",$(element).val() == "1"?"":"cl");
		    });
		}
	</script> </content>


</body>

</html>