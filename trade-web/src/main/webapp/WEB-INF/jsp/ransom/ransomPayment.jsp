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
<title>赎楼回款结清</title>

<link href="<c:url value='/static/trans/css/eloan/eloan/eloan.css' />"
	rel="stylesheet" />
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">
<link rel="stylesheet"
	href="<c:url value='/static/css/bootstrap.min.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/font-awesome/css/font-awesome.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/iconfont/iconfont.css' />">
<link rel="stylesheet" href="<c:url value='/static/css/animate.css' />">
<link rel="stylesheet" href="<c:url value='/static/css/style.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/css/plugins/aist-steps/steps.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/css/plugins/toastr/toastr.min.css' />">
<!-- stickUp fixed css -->
<link rel="stylesheet"
	href="<c:url value='/static/css/plugins/stickup/stickup.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/stickmenu.css' />">
<!-- index_css  -->
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/input.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/table.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/uplodydome.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/eloan/eloan_detail.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/eloan/eloan_guaranty.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css' />"
	type="text/css" />
<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"
	rel="stylesheet" />
<link href="<c:url value='/css/transcss/comment/caseComment.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">

<style type="text/css">

<style type="text/css">


.fo{width:100%;}
.matching{float: left;margin:0 40px;padding-top: 5px;}
.form_list .sign_left_small{text-align: left;margin-right: -10px;}
.underline{margin-top: -30px;}

</style>
<content tag="pagetitle">赎楼回款结清</content>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/ransom/ransomBaseInfo.jsp"></jsp:include>
	<div class="ibox-content border-bottom clearfix space_box noborder">
		<form method="get" id="ransomPayment" class="form_list">
			<input type="hidden" id="caseCode" name="caseCode" value="${detailVo.caseCode }">
			<input type="hidden" id="ransomCode" name="ransomCode" value="${detailVo.ransomCode }">
			<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId }">
			<input type="hidden" id="taskId" name="taskId" value="${taskId }">
		
			<div class="line">
				<div class="title">信息录入</div>
				<HR>
			    <div class="form_content ">
			        <label class="control-label matching"><font color=" red" class="mr5" >*</font>回款结清时间</label>
			        <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" >
			            <input id="paymentTime" name="paymentTime" class="form-control input-one date-picker data_style" style="font-size: 13px;width: 178px; border-radius: 2px;" type="text"  placeholder="回款结清时间">
			        </div>
			    </div>
			</div>
		</form>
	</div>

	<div class="ibox-content border-bottom clearfix space_box noborder">
		<div id="caseCommentList" class="view-content"></div>
	</div>
		<div class="add_btn text-center mt20">
	   	<div class="more_btn">
		    <button id="submitButton" type="button" class="btn btn_blue">提交</button>
   	    	<button id="closeButton" type="button" class="btn btn_blue">关闭</button>
		</div>
	</div>
	
<content tag="local_script">
	
	<script src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script>
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>

	<script src="<c:url value='/transjs/task/showAttachment.js' />"></script>
 	<script src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script> <!-- Data picker -->
	<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>

	<script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<script src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/viewer/viewer.min.js' />"></script> <!-- 改版引入的新的js文件 -->
	<script src="<c:url value='/js/common/textarea.js' />"></script> 
	<script src="<c:url value='/js/common/common.js' />"></script> 
	
	<script>
	$(document).ready(function(){
		
		//案件跟进,common.js 
		var caseCode = $('#caseCode').val();
		$("#caseCommentList").caseCommentGrid({
			caseCode : caseCode,
			srvCode : null
		});
	})

	//日期初始化
	$('#paymentTime').datepicker({
		format : 'yyyy-mm-dd',
		weekStart : 1,
		autoclose : true,
		todayBtn : 'linked',
		language : 'zh-CN'
	});

	
	//关闭
	$('#closeButton').click(function() {
		window.close();
	});
	
	//提交
	$('#submitButton').click(function(){
		if($('#paymentTime').val() == ''){
			window.wxc.alert("注销抵押时间为必填项!");
			$('#paymentTime').focus();
			$('#paymentTime').css('border-color',"red");
			return;
		}

		var jsonData = $('#ransomPayment').serializeArray();
		
		var url = "${ctx}/task/ransom/submitPayment";
		
		$.ajax({
			cache:true,
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
					window.wxc.error("提交失败!");
				}
			},
			error : function(errors) {
				
			}
		});
	});
	</script> 
</content>
</body>
</html>
