<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">

<title>赎楼注销抵押</title>
<script
	src="<c:url value='/static/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
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
</head>
<body>
	<jsp:include page="/ransom/ransomBaseInfo.jsp"></jsp:include>
	<input type="hidden" id="caseCode" value="${detailVo.caseCode }">
	<input type="hidden" id="ransomCode" value="${detailVo.ransomCode }">
	<input type="hidden" id="diyaType" value="${diyaType }">
	<input type="hidden" id="processInstanceId" value="${processInstanceId }">
	<input type="hidden" id="taskId" value="${taskId }">
	<div id="wrapper">

		<div class="row">
			<div class="wrapper wrapper-content animated fadeInUp">
				<div class="ibox-content" id="base_info">
					<form class="form-inline" >
						<div class="title">信息录入</div>
						<div class="form-row form-rowbot clear">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><font color=" red" class="mr5" >*</font>注销抵押时间</label> 
								<input id="cancelDiyaTime" name="cancelDiyaTime" class="form-control input-one date-picker data_style" style="font-size: 13px;width: 178px; border-radius: 2px;" type="text"  placeholder="注销抵押时间">
							</div>
						</div>
					</form>
				</div>
				
			</div>
		</div>
		<div id="caseCommentList" class="add_form"></div>
		
		<div class="add_btn text-center mt20">
		   	<div class="more_btn">
			    <button id="submitButton" type="button" class="btn btn_blue">提交</button>
	   	    	<button id="closeButton" type="button" class="btn btn_blue">关闭</button>
			</div>
		</div>
	</div>

	<content tag="local_script"> 
	<!-- 开关按钮js --> 
	<script
		src="<c:url value='/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.js' />"></script>
	<script
		src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/static/js/plugins/stickup/stickUp.js' />"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/common/textarea.js' />"></script>
	<script >
	
		$(document).ready(function(){
			
			//案件跟进,common.js 
			var caseCode = $('#caseCode').val();
			$("#caseCommentList").caseCommentGrid({
				caseCode : caseCode,
				srvCode : null
			});
		})
	
		//日期初始化
		$('#cancelDiyaTime').datepicker({
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
			if($('#cancelDiyaTime').val() == ''){
				window.wxc.alert("注销抵押时间为必填项!");
				$('#cancelDiyaTime').focus();
				$('#cancelDiyaTime').css('border-color',"red");
				return;
			}

			var ransomCode = $('#ransomCode').val();
			var diyaType = $('#diyaType').val();
			var cancelDiyaTime = $('#cancelDiyaTime').val();
			var processInstanceId = $('#processInstanceId').val();
			var taskId = $('#taskId').val();
			
			var url = "${ctx}/ransomList/submitCancelDiya";
			var data = "&ransomCode=" + ransomCode + "&diyaType=" + diyaType 
						+ "&cancelDiyaTime=" + cancelDiyaTime + "&processInstanceId=" + processInstanceId + "&taskId=" + taskId;
			$.ajax({
				cache:true,
				async:false,
				type:"POST",
				url:url,
				data:data,
				dataType:"json",
				success:function(data){
					window.wxc.success("提交成功!",{"wxcOk":function(){
						window.close();
					}});
				},
				error : function(errors) {
					window.wxc.error("提交失败!");
				}
			});
		});
	</script>
	</content> 
</body>
</html>



