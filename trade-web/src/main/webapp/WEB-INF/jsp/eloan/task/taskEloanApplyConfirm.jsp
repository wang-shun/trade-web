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
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
</head>


<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div id="wrapper">

		<!-- main Start -->
		<div class="row wrapper border-bottom nav_heading">
			<ul class="nav_bar">
				<li class="active">贷款申请任务</li>
				<li class="">面签任务</li>
				<li class="">贷款放款任务</li>
			</ul>
		</div>
		<div class="row">
			<div class="wrapper wrapper-content animated fadeInUp">
				<!-- <div class="ibox"> -->
				<div class="ibox-content" id="base_info">
					<div class="main_titile" style="position: relative;">
						<h5 class="title-mark">关联案件</h5>
						<div class="case_content">
							<div class="case_row">
								<div class="case_lump">
									<p>
										<em>案件编号</em><span class="span_one" id="content_caseCode">${caseCode}</span>
									</p>
								</div>
								<div class="case_lump">
									<p>
										<em>产权地址</em><span class="span_two" id="content_propertyAddr">${propertyAddr}</span>
									</p>
								</div>
							</div>
							<div class="case_row">
								<div class="case_lump">
									<p>
										<em>交易顾问</em><span class="span_one" id="content_processorId">${processorName}</span>
									</p>
								</div>
								<div class="case_lump">
									<p>
										<em>上家姓名</em><span class="span_two" id="content_buyer">${sellerName}</span>
									</p>
								</div>
							</div>
							<div class="case_row">
								<div class="case_lump">
									<p>
										<em>经纪人</em><span class="span_one" id="content_agentName">${agentName}</span>
									</p>
								</div>
								<div class="case_lump">
									<p>
										<em>下家姓名</em><span class="span_two" id="content_seller">${buyerName}</span>
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="ibox-content" id="zj_info">
					<div class="modal_title title-mark">
                                	产品信息
                    </div>
					<form method="get" class="form_list" id="eloanApplyConfirmForm">
						<input type="hidden" name="caseCode" id="caseCode"	value="${caseCode}" /> 
						<input type="hidden" name="processInstanceId" id="processInstanceId"	value="${processInstanceId}" /> 
						<input type="hidden" name="eloanCode" id="eloanCode" value="${eloanCase.eloanCode}" /> 
						<input type="hidden" name="taskId" id="taskId" value="${taskId}" />
						<ul class="form_lump">
							<li>
								<div class="form_content">
									<label class="control-label sign_left_two"> 产品类型 </label>
									<aist:dict id="loanSrvCode" name="loanSrvCode"
										clazz="select_control sign_right_two" display="select"
										dictType="yu_serv_cat_code_tree" tag="eplus,Eloan" ligerui='none'
										defaultvalue="${eloanCase.loanSrvCode}"></aist:dict>
								</div>
								<div class="form_content">
									<label class="control-label sign_left_two"> 合作机构 </label> <select
										class="select_control sign_right_two" name="finOrgCode"
										id="finOrgCode" value="${eloanCase.finOrgCode}">
									</select>
								</div>
							</li>	
							
							<li>
								<div class="form_content">
									<label class="control-label sign_left_two"> 申请金额 </label> <input
										class="input_type sign_right_two" name="applyAmount"
										id="applyAmount" value="${eloanCase.applyAmount}">
									<div class="input-group date_icon">
										<span class="danwei">万</span>
									</div>
								</div>
								<div class="form_content input-daterange"
									data-date-format="yyyy-mm-dd">
									<label class="control-label sign_left_two"> 申请时间 </label> <input
										class="input_type sign_right_two" name="applyTime" id="applyTime"
										value="<fmt:formatDate value="${eloanCase.applyTime}" pattern="yyyy-MM-dd" />">
									<div class="input-group date_icon">
										<i class="fa fa-calendar"></i>
									</div>
								</div>
								<div class="form_content">
									<label class="control-label sign_left_two"> 申请期数 </label> <input
										class="input_type sign_right_two" name="month" id="month"
										value="${eloanCase.month}">
									<div class="input-group date_icon">
										<span class="danwei">月</span>
									</div>
								</div>
							</li>
							
							<li>
								<div class="form_content">
									<label class="control-label sign_left_two"> 信贷员 </label> <input
										class="input_type sign_right_two" name="loanerName" id="loanerName"
										value="${eloanCase.loanerName}">
								</div>
								<div class="form_content">
									<label class="control-label sign_left_two"> 信贷员电话 </label> <input
										class="input_type sign_right_two" name="loanerPhone" id="loanerPhone"
										value="${eloanCase.loanerPhone}">
								</div>
							</li>
							
							<li>
								<div class="form_content">
									<label class="control-label sign_left_two"> 办卡人姓名 </label> <input
										class="input_type sign_right_two" name="custName"
										id="custName" value="${eloanCase.custName}">
								</div>
								<div class="form_content">
									<label class="control-label sign_left_two"> 办卡人电话 </label> <input
										class="input_type sign_right_two" name="custPhone"
										id="custPhone" value="${eloanCase.custPhone}">
								</div>
								<div class="form_content">
									<label class="control-label sign_left_two"> 案件归属 </label> <input
										type="text" id="excutorName" name="excutorName"
										class="input_type sign_right_two"
										style="width: 170px; display: inline-block;"
										value="${excutorName}" />
								</div>
							</li>
							
							<li>
								<div class="form_content">
									<label class="control-label sign_left_two"> 证件类型 </label>
									<aist:dict id="custCardType" name="custCardType"
										clazz="select_control sign_right_two" display="select"
										dictType="CERT_TYPE" tag="forEloanApply" ligerui='none'
										defaultvalue="${eloanCase.custCardType}"></aist:dict>
								</div>
								<div class="form_content">
									<label class="control-label sign_left_two"> 办卡人证件号 </label> <input
										type="text" id="custPaper" name="custPaper"
										class="input_type sign_right_two"
										style="width: 172px; display: inline-block;"
										value="${eloanCase.custPaper}" />
								</div>
								
							</li>

							<c:if test="${!empty eloanCase.chargeAmount }">
								<li>
									<div class="form_content">
										<label class="control-label sign_left_two"> 手续费 </label> <input
											class="input_type sign_right_two" name="charge" id="charge"
											value="${eloanCase.chargeAmount}">
									</div> <c:if test="${!empty eloanCase.remark}">
										<div class="form_content">
											<label class="control-label sign_left_two"> 情况说明 </label> <input
												class="input_type sign_right_two" name="remark" id="remark"
												value="${eloanCase.remark}">
										</div>
									</c:if>
								</li>
							</c:if>

							<li>
								<div class="form_content">
									<label class="control-label sign_left_two"> 转介人姓名 </label> <input
										class="input_type sign_right_two" name="zjName" id="zjName"
										value="${eloanCase.zjName}">
								</div>
								<div class="form_content">
									<label class="control-label sign_left_two"> 转介人员工编号 </label> <input
										class="input_type sign_right_two" name="zjCode" id="zjCode"
										value="${eloanCase.zjCode}">
								</div>
							</li>
							
							<div class="modal_title title-mark">
                                	业绩拆分信息
                        	</div>
							
							<li>
								<div class="form_content">
									<label class="control-label sign_left_two"> 产品部姓名 </label> <input
										class="input_type sign_right_two" name="pdName" id="pdName"
										value="${eloanCase.pdName}">
								</div>
								<div class="form_content">
									<label class="control-label sign_left_two"> 产品部员工编号 </label> <input
										class="input_type sign_right_two" name="pdCode" id="pdCode"
										value="${eloanCase.pdCode}">
								</div>
								<div class="form_content">
									<label class="control-label sign_left_two"> 分成比例贷款 </label> <input
										class="input_type sign_right_two" name="pdPart" id="pdPart"
										value="${eloanCase.pdPart}">
									<div class="input-group date_icon">
										<span class="danwei">%</span>
									</div>
								</div>
							</li>
							<li>
								<div class="form_content">
									<label class="control-label sign_left_two"> 合作人姓名 </label> <input
										class="input_type sign_right_two" name="coName" id="coName"
										value="${eloanCase.coName}">
								</div>
								<div class="form_content">
									<label class="control-label sign_left_two"> 合作人员工编号 </label> <input
										class="input_type sign_right_two" name="coCode" id="coCode"
										value="${eloanCase.coCode}">
								</div>
								<div class="form_content">
									<label class="control-label sign_left_two"> 分成比例贷款 </label> <input
										class="input_type sign_right_two" name="coPart" id="coPart"
										value="${eloanCase.coPart}">
									<div class="input-group date_icon">
										<span class="danwei">%</span>
									</div>
								</div>
							</li>
							
							<li>
	                            <div class="form_content">
	                                <label class="control-label sign_left_two">
	                                   	 归属人
	                                </label>
	                                <input class="input_type form-control sign_right_two" value="${excutorName }" disabled="disabled" readonly="readonly">
	                            </div>
	                            <div class="form_content">
	                                <label class="control-label  sign_left_two">
	                                       	归属人编号
	                                </label>
	                                <input class="input_type form-control sign_right_two" value="${excutorCode }" disabled="disabled" readonly="readonly">
	                            </div>
	                            <div class="form_content">
	                                <label class="control-label sign_left_two">
	                                   	 归属人业绩比例
	                                </label>
	                                <input class="input_type form-control sign_right_two" id="ownerPart" value="50" disabled="disabled" readonly="readonly">
	                                <div class="input-group date_icon">
	                                    <span class="danwei">%</span>
	                                </div>
	                            </div>
	                        </li>
	                        
	                        <!-- 跟进信息 -->
                    		<div id="caseCommentList" class="view-content"></div>
							
							<li>
								<c:if test="${ v!=2 }">
									<div class="form_content">
									<label class="control-label sign_left_two"> 审批 </label> 
									<select name="approved" id="approved"
										class="select_control sign_right_two">
										<option value="1">同意</option>
										<option value="0">驳回</option>
									</select>
									</div>
								</c:if>
								<input type="hidden" name="param" id="param" value="${v }"> 
								<div class="form_content"></div>
								<div class="form_content"></div>
							</li>
							
							<li>
								<div class="form_content" id="eapplyPassOrRefuseReasonForShow">
									<!-- style="display:none;" -->
									<label class="control-label sign_left_two pull-left">审批意见</label>
									<textarea class="input_type sign_right pull-left"  rows="2"  id="eContent"	name="eContent" style="margin-left: 4px;width: 757px;
    											height: 71px;resize:none;">${toApproveRecord.content }</textarea>
								</div>
							</li>
						</ul>
                         <p class="text-center" >
                            <input type="button" class="btn btn-success submit_From" value="确认">
                           <input type="button" onclick="closeWindow()" class="btn btn-grey ml5" value="关闭">
                        </p>
					</form>
				</div>

			</div>
		</div>
		<!-- main End -->
	</div>

	<!-- Mainly scripts -->
	<content tag="local_script"> <%--  <script src="<c:url value='/static/js/jquery-2.1.1.js' />"></script>
    <script src="<c:url value='/static/js/bootstrap.min.js' />"></script>
    <script src="<c:url value='/static/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
    <script src="<c:url value='/static/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>

    <!-- Custom and plugin javascript -->
    <script src="<c:url value='/static/js/inspinia.js' />"></script>
    <script src="<c:url value='/static/js/plugins/pace/pace.min.js' />"></script>--%>

	<!-- stickup plugin --> <%--     <script src="${ctx}/static/js/plugins/stickup/stickUp.js' />"></script>
    <script src="<c:url value='/static/trans/js/workbench/stickDash.js' />"></script> --%>


	<!-- Toastr script --> 
	<script src="<c:url value='/static/js/plugins/toastr/toastr.min.js' />"></script>
	<script src="<c:url value='/static/js/morris/morris.js' />"></script>
	<script src="<c:url value='/static/js/morris/raphael-min.js' />"></script> <!-- index_js -->
	<script src="<c:url value='/static/trans/js/eloan/eloan.js' />"></script>
	<script src="<c:url value='/static/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>

	<!-- aist --> 
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/common/textarea.js' />"></script>
    <script src="<c:url value='/js/eloan/eloancommon.js' />"></script>
	<script>
			$(document).ready(function() {
				
				var eloanCode = "${eloanCase.eloanCode}";
				
				//初始化归属人业绩比例
				initRate();
				
				$("#loanSrvCode").attr("disabled", true);
				$("#finOrgCode").attr("disabled", true);
				$("#custCardType").attr("disabled", true);
				$("input:not([type='button'])").attr("disabled", true);
				$("input[type='hidden']").attr("disabled", false);
				if($("#param").val()!=2){
					$("select:not([name='approved'])").attr("disabled", true);
				}
				
/* 				$("input[name='custName']").attr("disabled", false);
				$("input[name='custPhone']").attr("disabled", false);
				$("input[name='applyAmount']").attr("disabled", false);
				$("input[name='month']").attr("disabled", false);	 */		
				
				getBankList($("#finOrgCode").val());
				
				//跟进信息
	        	$("#caseCommentList").eloanCaseCommentGrid({
							eloanCode : eloanCode,
							source : 'EPLUS'
							//type : 'TRACK'
				});
				
				//初始化归属人业绩比例
				function initRate(){
					//产品部分成比例
					var pdPart = Number($("#pdPart").val());
					
					//合作人分成比例
					var coPart = Number($("#coPart").val());
					
					//计算归属人分成比例
					var ownerPart = 100 - pdPart - coPart;
					
					if(coPart != 0){
						$("#coPart").val(coPart.toFixed(2));
					}
					
					$("#pdPart").val(pdPart.toFixed(2));
					$("#ownerPart").val(ownerPart.toFixed(2));
				}
				
				$(".submit_From").click(function() {
					if($("#param").val()!=2){
						//提交之前先验证 是否驳回
						 var eContent = $("#eContent").val();				
				    	 var approved = $("#approved").val();
				     	 if(approved==0){
				     		 if(eContent == '' || eContent == null){
				     			window.wxc.alert("申请驳回时请填写驳回原因！");
					     		 return;
				     		 }
				     	 }
					}
					saveEloanApplyConfirm();
				})
			});
			function closeWindow(){
				window.close();
				window.opener.callback();
			}
			/*获取银行列表*/
			function getBankList(pcode) {
				var friend = $("#finOrgCode");
				friend.empty();
				$
						.ajax({
							url : ctx + "/manage/queryFin",
							method : "post",
							dataType : "json",
							data : {
								"pcode" : pcode
							},
							success : function(data) {
								if (data.bankList != null) {
									for (var i = 0; i < data.bankList.length; i++) {
										if (pcode == data.bankList[i].finOrgCode) {
											friend
													.append("<option value='"+data.bankList[i].finOrgCode+"' selected='selected'>"
															+ data.bankList[i].finOrgName
															+ "</option>");
										} else {
											friend
													.append("<option value='"+data.bankList[i].finOrgCode+"'>"
															+ data.bankList[i].finOrgName
															+ "</option>");
										}
									}
									friend
											.find(
													"option[value='${eloanCase.finOrgCode }']")
											.attr("selected", true);
								}
							}
						});
			}
			function saveEloanApplyConfirm() {
				$.blockUI({
					message : $("#salesLoading"),
					css : {
						border : 0
					}
				});
				var jsonData = $("#eloanApplyConfirmForm").serializeArray();
				var url = "${ctx}/eloan/saveEloanApplyConfirm";
				$.ajax({
					cache : false,
					async : false,//false同步，true异步
					type : "POST",
					url : url,
					dataType : "json",
					//contentType:"application/json",  
					data : jsonData,
					success : function(data) {
						$.unblockUI();
						window.wxc.success(data.message);
						window.close();
						window.opener.callback();
					},
					error : function(errors) {
						$.unblockUI();
						window.wxc.error("数据保存出错");
					}
				});
			}
		</script> </content>

</body>

</html>