<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>出具评估报告</title>

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
				出具评估报告
			</h2>
			<div class="mt20">
				<button type="button" class="btn btn-icon btn-blue mr5" id="btnEvalView">
					<i class="iconfont icon">&#xe600;</i> 评估视图
				</button>
				<button type="button" class="btn btn-icon btn-blue mr5" id="btnCaseView" lang="${caseCode}">
					<i class="iconfont icon">&#xe63f;</i> 案件视图
				</button>
			</div>
		</div>
		</div>
		<div class="ibox-title">
			<h5>填写任务信息</h5>
			<div class="ibox-content">
				<form method="get" class="form_list" id="evalIssueForm">
				<input type="hidden" id="evaCode" name="evaCode" value="${toEvalReportProcess.evaCode }">
					<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode"
						value="${taskitem}">
					<!-- 流程引擎需要字段 -->
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
						<ul class="form_lump">
						<div class="modal_title title-mark">
                                	填写上报任务
                        </div>
						<li>
							<div class="form_content input-daterange" data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i style="color:red">* </i> 实际出具评估报告日期</label> 
								<input class="input_type sign_right_two"  value='' name="toIssueDate" id="toIssueDate" value="${toEvalReportProcess.toIssueDate}"/>
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</li>
						<li>
							<div class="form_content input-daterange" data-date-format="yyyy-mm-dd">
								<label class="control-label sign_left_two"> <i style="color:red">* </i> 收取报告日期</label> 
								<input class="input_type sign_right_two"  value='' name="reportGetDate" id="reportGetDate" value="${toEvalReportProcess.reportGetDate}"/>
								<div class="input-group date_icon">
									<i class="fa fa-calendar"></i>
								</div>
							</div>
						</li>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"><i style="color:red">* </i> 评估价</label> 
								<input class="input_type sign_right_two" name="evaPrice" id="evaPrice"></input>
								<div class="input-group date_icon">
									<span class="danwei">万</span>
								</div>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_two"><i style="color:red">* </i> 房龄</label> 
								<input class="input_type sign_right_two" name="houseAgeIssue" id="houseAgeIssue"></input>
								<div class="input-group date_icon">
									<span class="danwei">年</span>
								</div>
							</div>
						</li>
						<li>
							<div class="form_content">
								<label class="control-label sign_left_two"><i style="color:red">* </i>评估报告份数</label> 
								<input class="input_type sign_right_two" name="reportNumIssue" id="reportNumIssue"></input>
							</div>
						</li>
						<li>
						  
                      </li>
                      <div class="modal_title title-mark">
                                	上传附件
                        </div>
                        <li>
				                     <div class="ibox-content" id="aboutInfo">
										<div class="table-box" id="evalReportsFileUploadContainer"></div>
				 					</div>
                        </li>
					</ul>
					<p class="text-center">
							<input type="button" class="btn btn-success submit_From" value="提交"> 
						    <a type="button" href="${ctx}/eloan/Eloanlist" class="btn btn-grey ml5">关闭</a>
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
	<!-- 上传附件相关 --> 
	<script src="<c:url value='/js/trunk/JSPFileUpload/app.js' />"></script> 
	<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.ui.widget.js' />"></script> 
	<script	src="<c:url value='/js/trunk/JSPFileUpload/tmpl.min.js' />"></script> 
	<script	src="<c:url value='/js/trunk/JSPFileUpload/load-image.min.js' />"></script> 
	<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload.js' />"></script> 
	<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-fp.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-ui.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/clockface.js' />"></script> 
	<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js' />"></script>
	<script	src="<c:url value='/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.multi-select.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/form-fileupload.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/aist.upload.js' />"></script> 
	<script	src="<c:url value='/js/trunk/JSPFileUpload/jssor.js' />"></script> 
	<script	src="<c:url value='/js/trunk/JSPFileUpload/jssor.slider.js' />"></script> 
    <!-- 上传附件结束 -->
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script> 
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script> 
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script> 
	<script src="<c:url value='/js/jquery.editable-select.min.js' />"></script> 
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	<script src="<c:url value='/js/common/textarea.js' />"></script> 
    <%-- <script src="<c:url value='/js/eloan/eloancommon.js' />"></script> --%>
    <script src="<c:url value='/js/common/common.js' />"></script>
		<script>	
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
					saveEvalIssue();
				});
		});
		

		
		function saveEvalIssue(){
			var jsonData = $("#evalIssueForm").serializeArray();
			var url = "${ctx}/task/eval/submitIssue";			
			
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
					window.wxc.success("评估出具提交成功",{"wxcOk":function(){
							window.location.href = ctx + "/task/eval/evalTaskList";
					}});
				},
				error : function(errors) {
					window.wxc.error("数据保存出错");
				}
			});
		}
		
		//必填项
		function checkForm() {
			if ($("#forwardDate").val() == '') {
				window.wxc.alert("请填写实际出具评估报告日期");
				return false;
			}
			if ($("#reportGetDate").val() == '') {
				window.wxc.alert("请填写收取报告日期");
				return false;
			}
			if ($("#houseAgeApply").val() == '') {
				window.wxc.alert("请填写评估价");
				return false;
			}
			if ($("#houseAgeIssue").val() == '') {
				window.wxc.alert("请填写房龄");
				return false;
			}
			if ($("#reportNum").val() == '') {
				window.wxc.alert("请填写评估报告份数");
				return false;
			}
			
			if ($("#eval_report_letter_pic_list li").length == undefined
					|| $("#eval_report_letter_pic_list li").length == 0 ) {
				window.wxc.alert("评估报告未上传!");
				return false;
			}
			
			var option = [];
 			option.container = "evalReportsFileUploadContainer";
 			
 			//验证上传文件是否全部上传
			var isCompletedUpload = fileUpload.isCompletedUploadById(option);
			
			if(!isCompletedUpload){
				window.wxc.alert("附件还未全部上传!");
				return false;
			}
			return true;
		}
		</script>
	</content>
	<content tag="local_require">
	    <script>
	    	var fileUpload;
		    require(['main'], function() {
				requirejs(['jquery','aistFileUpload','validate','grid','jqGrid','additional','blockUI','steps','ligerui','aistJquery','modal','modalmanager','twbsPagination'],function($,aistFileUpload){
					fileUpload = aistFileUpload; 
					
					fileUpload.init({
			    		caseCode : $('#caseCode').val(),
			    		partCode : "eval_issue",
			    		preFileCode : "eval_report_letter",
			    		fileUploadContainer : "evalReportsFileUploadContainer"
			    	});
			    });
		    });
		</script>
	</content>
</body>


</html>