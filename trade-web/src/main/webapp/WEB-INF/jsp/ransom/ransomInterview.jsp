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
<title>赎楼面签</title>
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
.fo{width:100%;}
.matching{float: left;margin:0 40px;padding-top: 5px;}
.form_list .sign_left_small{text-align: left;margin-right: -10px;}
.underline{margin-top: -30px;}
</style>
<content tag="pagetitle">赎楼面签</content>
</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/ransom/ransomBaseInfo.jsp"></jsp:include>

	<div class="ibox-content border-bottom clearfix space_box noborder">
		
	 	<form method="get" class="form_list text-center">
             <div class="line">
                <div class="form_content">
                    <label class="control-label sign_left_small select_style mend_select"><font color=" red" class="mr5" >*</font>实际面签时间</label>
                    <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
                        <input name="changeTimeStart" id="changeTimeStart" class="form-control data_style" type="text" value="${curMonthStart }" placeholder="">
                    </div>
                </div>
                <div class="form_content">
                    <label class="control-label sign_left_small select_style mend_select"><font color=" red" class="mr5" >*</font>陪同还贷时间</label>
                    <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
                        <input name="changeTimeStart" id="changeTimeStart" class="form-control data_style" type="text" value="${curMonthStart }" placeholder="">
                    </div>
                </div>
             </div>
             <div class="line">
                <div class="form_content">
					<label class="control-label sign_left_small select_style mend_select"><font color=" red" class="mr5" >*</font>面签金额 </label>
					<input type="text" placeholder="面签金额"
						   value="<fmt:formatNumber value='${ transSign.conPrice/10000}' type='number' pattern='#0.00' />"
						   class="input_type yuanwid" id="conPrice" name="conPrice"
						   onkeyup="checkNum(this)">
					<span >万元</span>
				</div>
                <div class="form_content">
					<label class="control-label sign_left_small select_style mend_select"><font color=" red" class="mr5" >*</font>价格 </label>
					<input type="text" placeholder="价格"
						   value="<fmt:formatNumber value='${ transSign.conPrice/10000}' type='number' pattern='#0.00' />"
						   class="input_type yuanwid" id="conPrice" name="conPrice"
						   onkeyup="checkNum(this)">
					<span>‱每天</span>
				</div>
             </div>
             <div class="line">
             	<div class="form_content">
					<label class="control-label sign_left_small select_style mend_select"><font color=" red" class="mr5" >*</font>是否需要委托公证 </label>
					<label  class="radio-inline"><input type="radio" name="prove" value="" checked>是</label>
					<label  class="radio-inline"><input type="radio" name="prove" value="">否</label>
				</div>
             </div>
         </form>
		<div id="caseCommentList" class="view-content"></div>
		<div class="form-btn">
			<div class="text-center">
				<button class="btn btn-success btn-space" id="submit">提交</button>
				<button class="btn btn-grey btn-space" id="close">关闭</button>
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
			 	$('.input-daterange').datepicker({
                    keyboardNavigation: false,
                    forceParse: false,
                    autoclose: true
                });
			 	var caseCode = $('#caseCode').val();
				var srvCode = 'TransSign';
				$("#caseCommentList").caseCommentGrid({
					caseCode : caseCode,
					srvCode : srvCode
				});
				$("#submit").click(function(){
					window.location.href = ctx + "/ransomList/ransomUp/ransomPaymentOne";
				});
				
			});
		</script> </content>
</body>
</html>
