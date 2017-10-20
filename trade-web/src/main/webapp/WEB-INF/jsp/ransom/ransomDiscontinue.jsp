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
<title>赎楼中止申请</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- 展示相关 -->
<link
	href="<c:url value='/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/trunk/JSPFileUpload/bootstrap-tokenfield.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/trunk/JSPFileUpload/selectize.default.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">

<!-- jdGrid相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link
	href="<c:url value='/font-awesshenpiyijianome/css/font-awesome.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link
	href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/style.css' />" rel="stylesheet">

<link href="<c:url value='/css/transcss/comment/caseComment.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"
	rel="stylesheet" />
<!-- 新调整页面样式 -->
<link href="<c:url value='/css/common/caseDetail.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">

<!-- 图片查看CSS -->
<link href="<c:url value='/js/viewer/viewer.min.css' />"
	rel="stylesheet" />


<script type="text/javascript">
	var ctx = "${ctx}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index = 0;
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
	if ("${idList}" != "") {
		var idList = eval("(" + "${idList}" + ")");
	} else {
		var idList = [];
	}
</script>
<style type="text/css">
.radio.radio-inline>label {
	margin-left: 10px;
}

.radio.radio-inline>input {
	margin-left: 10px;
}

.checkbox.checkbox-inline>div {
	margin-left: 25px;
}

.checkbox.checkbox-inline>input {
	margin-left: 20px;
}

#notApproves label {
	font-weight: normal;
	margin: 0;
}

#notApproves {
	padding: 20px 0px;
}

#notApproves .col-sm-4 {
	margin: 5px 0px;
}

#notApproves input[type=checkbox], input[type=radio] {
	margin: 0px 0 0;
	line-height: normal;
}

.form_sign .sign {
	margin-top: 3px;
	margin-bottom: 3px;
}

.other_reason_title {
	float: left;
	width: 76px;
	margin-left: 24px;
	text-align: right;
	color: #808080;
	font-size: 14px;
}

.other_reason {
	float: left;
	padding-left: 15px;
	color: #333;
	width: 725px;
}
.form-group{margin-top: 15px;}
</style>
<content tag="pagetitle">赎楼中止申请</content>
</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/ransom/ransomBaseInfo.jsp"></jsp:include>

	<div class="ibox-content border-bottom clearfix space_box noborder">
		<form method="post" id="submitDiscontinue" >
			<%--环节编码 --%>
			<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
			<!-- 交易单编号 -->
			<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
			<input type="hidden" id="ransomCode" name="ransomCode" value="${ransomCase.ransomCode}">
			<%-- 原有数据对应id --%>
			<input type="hidden" id="taskId" name="taskId" value="${taskId }">
			<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
			<%-- 设置审批类型 --%>
			<input type="hidden" id="approveType" name="approveType" value="${approveType }">
			<input type="hidden" id="operator" name="operator" value="${operator }">
			
			<div class="form_content">
				<label class="control-label sign_left_small">中止类型 </label>
				<div class="controls isnowid" style="width: 1000px;margin-left: 40px;">
					<aist:dict id="stopType" name="stopType" display="select" dictType="71016" defaultvalue="${ransomCase.stopType}" clazz="select_control data_style"/>
				</div>
			</div>
			<div class="form_content">
				<div class="form-group">
				    <label for="name" style="float: left;">详情描述</label>
				    <textarea class="form-control" rows="8" style="resize:none;width:960px;margin-left:95px;" id="stopReason" name="stopReason">${ransomCase.stopReason}</textarea>
			  	</div>
			</div>
		</form>
		<div class="mt20" id="aboutInfo">
			<h2 class="newtitle title-mark">审批记录</h2>
			<div class="jqGrid_wrapper">
				<table id="reminder_list"></table>
				<div id="pager_list_1"></div>
			</div>
		</div>
		<div class="form-btn">
			<div class="text-center">
				<button class="btn btn-success btn-space" id="discontinue">提交</button>
				<button class="btn btn-grey btn-space" id="cancel">取消</button>
			</div>
		</div>
	</div>

	<content tag="local_script"> <!-- Peity --> <script
		src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script>
	<!-- jqGrid --> <script
		src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	<script src="<c:url value='/transjs/task/showAttachment.js' />"></script>
	<%-- --%> <!-- Custom and plugin javascript --> <script
		src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script> <!-- Data picker -->
	<script
		src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script	src="${ctx}/transjs/task/loanlostApprove.js"></script>
	<script
		src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<script src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
	<script
		src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/viewer/viewer.min.js' />"></script> <!-- 改版引入的新的js文件 -->
	<script src="<c:url value='/js/common/textarea.js' />"></script> <script
		src="<c:url value='/js/common/common.js' />"></script> <script>
			$(document).ready(function() {
				$("#caseCommentList").caseCommentGrid({
					caseCode : caseCode,
					srvCode : taskitem
				});

				$(window).bind('resize', function() {
					var width = $('.jqGrid_wrapper').width();
					$('#reminder_list').setGridWidth(width);

				});
				
				$("#discontinue").click(function(){
					if($('#stopType').val() == ''){
						window.wxc.alert("中止类型为必填项!");
						return;
					}
					
					if($('#stopReason').val() == ''){
						window.wxc.alert("详情描述为必填项!");
						$('#stopReason').focus();
						$('#stopReason').css('border-color',"red");
						return;
					}
					
					var jsonData = $('#submitDiscontinue').serializeArray();
					var url = "${ctx}/task/ransomDiscontinue/submitDiscontinue";
					
					$.ajax({
						async:false,
						type:"POST",
						url:url,
						data:jsonData,
						dataType:"json",
						success:function(data){
							if(data){
								window.wxc.success("提交成功!",{"wxcOk":function(){
									 window.close();	
								}});
							}else{
								window.wxc.error("申请中止失败,请确认是否已经开启中止流程,或确认您是否是该赎楼流程环节责任人");
							}
							
						},
						error : function(errors) {
							window.wxc.error("提交失败!");
						}
					});
					
				});
				
				$("#cancel").click(function(){
					if(confirm('您确定要取消吗？')){ 
						 window.close();
					}
				});
			});
		</script> </content>
</body>
</html>
