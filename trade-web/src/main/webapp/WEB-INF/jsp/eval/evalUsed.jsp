<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>使用评估报告</title>

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
<link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/evalBaseInfo.jsp"></jsp:include>
	<div class="">
		<div class="wrapper border-bottom white-bg page-heading">
			<div class="pl10">
			<h2 class="newtitle-big">
				使用评估报告
			</h2>
			<div class="mt20">
				<button type="button" class="btn btn-icon btn-blue mr5" id="btnEvalView" lang="${toEvalReportProcessVo.evaCode }">
					<i class="iconfont icon">&#xe600;</i> 评估视图
				</button>
				<button type="button" class="btn btn-icon btn-blue mr5" id="btnCaseView" lang="${caseCode }">
					<i class="iconfont icon">&#xe63f;</i> 案件视图
				</button>
			</div>
		</div>
		</div>
		<div class="ibox-title">
			<h5>填写任务信息</h5>
			<div class="ibox-content">
				<form method="get" class="form_list" id="evalUsedForm">
				    <c:if test="${source != 'evalDetails'}">
				         <input type="hidden" id="evaCode" name="evaCode" value="${toEvalReportProcessVo.evaCode }">
				    </c:if>
				    <input type="hidden" id="source" name="source" value="${source }">
					<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode"
						value="${taskitem}">
					<!-- 流程引擎需要字段 -->
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId"
						name="processInstanceId" value="${processInstanceId}">
						<ul class="form_lump">
						<div class="modal_title title-mark">
                                	填写任务信息
                        </div>
						<li>
							<div class="form_content input-daterange" data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i style="color:red">* </i> 领取报告时间</label> 
								<input class="input_type sign_right_two"  name="reportRevDate" id="reportRevDate" value="<fmt:formatDate value="${toEvalReportProcessVo.reportRevDate}" type="date" pattern="yyyy-MM-dd"/>"/>
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</li>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"><i style="color:red">* </i> 领取人</label> 
								<input class="input_type sign_right_two" name="receiver" id="receiver" value="${toEvalReportProcessVo.receiver}"/>
							</div>
						</li>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"> <i style="color:red">* </i> 领取份数</label> 
								<input class="input_type sign_right_two" name="receiveNum" id="receiveNum" value="${toEvalReportProcessVo.receiveNum}"/>
							</div>
						</li>
					</ul>
					<p class="text-center">
							<c:if test="${source == null}">
							<input type="button" class="btn btn-success submit_From" value="提交">
							</c:if>
							<c:if test="${source == 'evalDetails'}">
							 <input type="hidden" id="evaCode" name="evaCode" value="${evaCode}">
							<input type="button" class="btn btn-success submit_save" value="保存">
							</c:if> 
						    <a type="button" href="${ctx}/task/eval/evalTaskList" class="btn btn-grey ml5">关闭</a>
					</p>
				</form>
			</div>
		</div>
	</div>

	<!-- Mainly scripts -->
	<content tag="local_script">
	<script src="<c:url value='/static/js/plugins/toastr/toastr.min.js' />"></script> 
	<script src="<c:url value='/static/js/morris/raphael-min.js' />"></script> <!-- index_js -->
	<script src="<c:url value='/static/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script> 
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script> 
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script> 
	<script src="<c:url value='/js/jquery.editable-select.min.js' />"></script> 
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	<script src="<c:url value='/js/common/textarea.js' />"></script> 
    <%-- <script src="<c:url value='/js/eloan/eloancommon.js' />"></script> --%>
    <script src="<c:url value='/js/common/common.js' />"></script>
		<script>
		var taskitem;
		$(document).ready(function() {
				
				$('.input-daterange').datepicker({
					format : 'yyyy-mm-dd',
					weekStart : 1,
					autoclose : true,
					todayBtn : 'linked',
					language : 'zh-CN'
				});
				
				$('.submit_From').click(function() {
					if (!checkForm()) {
						return;
					}
					saveEvalUsed("${ctx}/task/eval/submitUsed","评估使用提交成功");
				});
				$('.submit_save').click(function() {
					if (!checkForm()) {
						return;
					}
					saveEvalUsed("${ctx}/task/eval/saveUsed","评估使用提交成功");
				});
		});
		

		
		function saveEvalUsed(url,message){
			var jsonData = $("#evalUsedForm").serializeArray();
			$.ajax({
				cache : false,
				async : true,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				//contentType:"application/json",  
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
					window.wxc.success(message,{"wxcOk":function(){
						if($("#source").val()=='evalDetails'){
						    location.reload();
					   }else{
						   window.location.href = ctx + "/task/eval/evalTaskList";
					   }
					}});
				},
				error : function(errors) {
					window.wxc.error("数据保存出错");
				}
			});
		}
		
		//必填项
		function checkForm() {
			if ($("#reportRevDate").val() == '') {
				window.wxc.alert("请填写领取报告时间");
				return false;
			}
			if ($("#reveiver").val() == '') {
				window.wxc.alert("请填写领取人");
				return false;
			}
			if ($("#receiveNum").val() == '') {
				window.wxc.alert("请填写领取份数");
				return false;
			}
			return true;
		}
		</script>
	</content>
</body>


</html>