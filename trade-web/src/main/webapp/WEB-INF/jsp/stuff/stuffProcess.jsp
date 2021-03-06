<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>
<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<!-- 上传相关 -->
<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fancybox.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fileupload-ui.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/select2_metro.css' />"
	rel="stylesheet">
<!-- 展示相关 -->
<link href="<c:url value='/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/bootstrap-tokenfield.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/selectize.default.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">
<!-- jdGrid相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"
	rel="stylesheet" />
<link href="<c:url value='/css/transcss/comment/caseComment.css' />"
	rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/js/viewer/viewer.min.css' />" />
<link href="<c:url value='/css/iconfont/iconfont.css" rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/css/workflow/caseDetail.css' />">
<link rel="stylesheet" href="<c:url value='/css/workflow/details.css' />">
<script type="text/javascript">
	var ctx = "${ctx}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index = 0;
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

	<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	<div class=" row wrapper white-bg new-heading ">
		<div class="pl10">
				<h2 class="newtitle-big">补件</h2>
				<div class="mt20">
					<button type="button" class="btn btn-icon btn-blue mr5"
						id="btnZaitu">
						<i class="iconfont icon"></i> 在途单列表
					</button>
					<button type="button" class="btn btn-icon btn-blue mr5"
						id="btnCaseView" lang="${caseCode}">
						<i class="iconfont icon"></i> 案件视图
					</button>
				</div>

		</div>
	</div>
	<div class="ibox-content border-bottom clearfix space_box noborder">
	<form method="get" class="form-horizontal" id="loanReleaseForm">
	<%--环节编码 --%>
				<input type="hidden" id="partCode" name="partCode"
					value="${taskitem}"> <input type="hidden" id="taskitem"
					name="taskitem" value="${taskitem}">
				<!-- 交易单编号 -->
				<input type="hidden" id="caseCode" name="caseCode"
					value="${caseCode}"> <input type="hidden"
					id="caseComment_parentId" name="caseComment_parentId"
					value="${pComment.pkid }"> <input type="hidden"
					id="caseComment_commentSource" name="caseComment_commentSource"
					value="INTER"> <input type="hidden"
					id="caseComment_commentType" name="caseComment_commentType"
					value="TRACK"> <input type="hidden"
					id="caseComment_bizCode" name="caseComment_bizCode"
					value="${pComment.bizCode }">
				<!-- 流程引擎需要字段 -->
				<input type="hidden" id="taskId" name="taskId" value="${taskId }">
				<input type="hidden" id="processInstanceId" name="processInstanceId"
					value="${processInstanceId}">
	
	
	
            <h2 class="newtitle title-mark">填写任务信息</h2>
            <div class="form_list">
            <div class="marinfo">
                <div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left_small"> 发起人 </label>
                        <span>${pComment.createByShow}</span>
                    </div>
                    <div class="form_content">
                        <label class="control-label sign_left_small">发起机构</label>
                        <span>${pComment.creatorOrgIdShow}</span>
                    </div>
                </div>
                </div>
            </div>
     
           <div class="panel " id="aboutInfo">
			<div id="caseCommentList" class="view-content"></div>
             <div class="form-btn">
                    <div class="text-center">
                        <button id="btn_submit" type="button" class="btn btn-success btn-space" >提交</button>
                    </div>
                </div>
                </form>
            </div>

	<content tag="local_script"> <!-- Peity --> <script
		src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script> <!-- jqGrid -->
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> <!-- Custom and plugin javascript -->
	
	
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script> 
	<script	src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script> 
	<script	src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script>
	<script src="<c:url value='/js/stickUp.js' />"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>  
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	  
	<script>
			var source = "${source}";
			$(document).ready(function() {
				$("#caseCommentList").caseCommentGrid({
					caseCode : caseCode,
					srvCode : taskitem
				});
				buildEvent();
			});
			function buildEvent(){
				$("#btn_submit").on('click',doSubmit);
			}
			/**提交数据*/
			function doSubmit() {
				save(true);
			}

			/**保存数据*/
			function save(b) {

				var jsonData = $("#loanReleaseForm").serializeArray();

				var url = "${ctx}/stuff/submit";

				$.ajax({
					cache : true,
					async : false,//false同步，true异步
					type : "POST",
					url : url,
					dataType : "json",
					data : jsonData,
					beforeSend : function() {
						$.blockUI({
							message : $("#salesLoading"),
							css : {
								'border' : 'none',
								'z-index' : '9999'
							}
						});
						$(".blockOverlay").css({
							'z-index' : '9998'
						});
					},
					complete : function() {

						$.unblockUI();
						if (b) {
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
						}
						if (status == 'timeout') {//超时,status还有success,error等值的情况
							Modal.alert({
								msg : "抱歉，系统处理超时。"
							});
							$(".btn-primary").one("click", function() {
								parent.$.fancybox.close();
							});
						}
					},
					success : function(data) {
						if (b) {
							caseTaskCheck();
							if (null != data.message) {
								window.wxc.alert(data.message);
							}
						} else {
							window.wxc.success("保存成功。");
							window.close();
							window.opener.callback();
						}
					},
					error : function(errors) {
						window.wxc.error("数据保存出错");
						$.unblockUI();
					}
				});
			}

		</script>
		<script src="<c:url value='/js/common/textarea.js' />"></script>
		<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
		<script src="<c:url value='/js/common/common.js' />"></script> 
		 </content>
</body>


</html>