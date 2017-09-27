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
<link
	href="<c:url value='/css/trunk/JSPFileUpload/jquery.fancybox.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/trunk/JSPFileUpload/jquery.fileupload-ui.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/trunk/JSPFileUpload/select2_metro.css' />"
	rel="stylesheet">
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
.line_content{ margin-left: 15px;}
</style>
</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/ransom/ransomBaseInfo.jsp"></jsp:include>

	<div class="ibox-content border-bottom clearfix space_box ">
		<div class="col-lg-12 case-info">
			<h2 class="title">待审批内容</h2>
		</div>
		<div class="col-lg-12"><hr></div>
		<div class="line_content">
			<div class="form_content">
				<label>中止类型 </label>
				<div class="controls isnowid" style="width: 1000px;margin-left: 40px;">
					<span>客户放弃</span>
				</div>
			</div>
			<div class="form_content">
				<div class="form-group">
				    <label for="name" style="float: left;">详情描述</label>
				    <div class="controls isnowid" style="width: 1000px;margin-left: 40px;">
						<span>客户认为太贵，自己想办法凑钱赎楼</span>
					</div>
			  	</div>
			</div>
			<div class="form_content">
				<div class="form-group">
				    <label for="name" style="float: left;">审批内容</label>
				     <div class="controls isnowid" style="width: 1000px;margin-left: 40px;">
						<input type="radio" name="examContent" value="pass" checked> 通过&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="examContent" value="noPass" > 不通过
					</div> 
			  	</div>
			</div>
			<div class="mt20" id="aboutInfo">
				<h2 class="newtitle title-mark">审批记录</h2>
				<div class="jqGrid_wrapper">
					<table id="reminder_list"></table>
					<div id="pager_list_1"></div>
				</div>
			</div>
		</div>
		<div class="form-btn">
			<div class="text-center">
				<button class="btn btn-success btn-space" id="submit">提交</button>
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

				$("#reminder_list").jqGrid({
					//data : reminderdata,
					//url:ctx+"/quickGrid/findPage",
					datatype : "json",
					height : 120,
					width : 1059,
					shrinkToFit : true,
					rowNum : 4,
					sortname : 'OPERATOR_TIME',
					viewrecords : true,
					sortorder : "desc",
					viewrecords : true,
					colNames : [ '审批时间', '审批人员', '审批意见' ],
					colModel : [ {
						name : 'OPERATOR_TIME',
						index : 'OPERATOR_TIME',
						width : '15%'
					}, {
						name : 'OPERATOR',
						index : 'OPERATOR',
						width : '15%'
					}, {
						name : 'PART_CODE',
						index : 'PART_CODE',
						width : '20%'
					} ],
					pager : "#pager_list_1",
					viewrecords : true,
					pagebuttions : true,
					hidegrid : false,
					recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
					pgtext : " {0} 共 {1} 页",
					postData : {
						queryId : "queryLoanlostApproveList",
						search_caseCode : caseCode,
						search_approveType : approveType,
						search_processInstanceId : processInstanceId
					},
				});
				// Add responsive to jqGrid
				$(window).bind('resize', function() {
					var width = $('.jqGrid_wrapper').width();
					$('#reminder_list').setGridWidth(width);

				});
				
				//提交
				$("#submit").click(function(){
					window.location.href = ctx + "/ransomList/ransomCase/myRansom_list";
				});
				//取消
				$("#cancel").click(function(){
					if(confirm('您确定要取消吗？')){ 
						value="取消";
					}
				});
			});
		</script> </content>
</body>
</html>
