<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>

<html>
<head>
<title>案件爆单审批</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- jdGrid相关 -->
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="<c:url value='/css/common/caseDetail.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link href="${ctx}/css/transcss/comment/caseComment.css" rel="stylesheet">
<script type="text/javascript">
	var ctx = "${ctx}";
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
</script>
<style type="text/css">
.radio.radio-inline > label{
	margin-left:10px;}
.radio.radio-inline > input{
	margin-left:10px;}
.checkbox.checkbox-inline > div{
	margin-left:25px;}
.checkbox.checkbox-inline > input{
	margin-left:20px;}

</style>
<content tag="pagetitle">案件爆单审批</content>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
		<div class="row wrapper border-bottom white-bg page-heading">
			<div class="col-lg-10">
				 <h2 class="newtitle-big">
                 	案件爆单审批
                 </h2>
                <div class="mt20">
                    <button type="button" class="btn btn-icon btn-blue mr5" id="btnZaitu">
                    	<i class="iconfont icon">&#xe640;</i> 在途单列表
                    </button>
                    <button type="button" class="btn btn-icon btn-blue mr5" id="btnCaseView" lang="${caseCode}">
                        <i class="iconfont icon">&#xe642;</i>案件视图
                    </button>
                </div>
			</div>
			<div class="col-lg-2"></div>
		</div>
		<div class="ibox-title">
			<h5>填写任务信息</h5>
			<div class="ibox-content">
				<form method="get" class="form-horizontal" id="caseStopApproveForm">
					<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">

					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId" name="instCode" value="${processInstanceId}">

					<div class="form-group">
						<label class="col-sm-2 control-label">审批结果</label>
						<div class="radio i-checks radio-inline">
							<label> 
								<input type="radio" checked="checked" value="true" id="optionsRadios1" name="isApproved">审批通过
							</label>
						</div>
						<div class="radio i-checks radio-inline">
							<label> 
								<input type="radio" value="false" id="optionsRadios2" name="isApproved">审批未通过
							</label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">审批意见</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="content" name="content" value="">
						</div>
					</div>
				</form>

			</div>
		</div>
		
		<div class="ibox-title">
			<h5>审批记录</h5>
			<div class="ibox-content">
				<div class="jqGrid_wrapper">
					<table id="reminder_list"></table>
					<div id="pager_list_1"></div>	
				</div>
			</div>
		</div>
		<div class="ibox-title" style="margin-left:400px;">
			<a href="#" class="btn btn-primary" onclick="submit()">提交</a>
		</div>

	<content tag="local_script"> 
	<!-- Peity --> 
	<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
	<!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/transjs/task/loanlostApprove.js"></script>
	<script src="${ctx}/transjs/task/showAttachment.js"></script> 
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
	<script src="<c:url value='/js/common/textarea.js' />"></script>
	<script src="<c:url value='/js/common/common.js' />"></script>
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<script src="${ctx}/js/trunk/comment/caseComment.js"></script>
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
	<script	src="<c:url value='/js/trunk/case/caseBaseInfo.js' />" type="text/javascript"></script>
	<script>
		/**提交数据*/
		function submit() {
			save();
		}
	
		/**保存数据*/
		function save() {
			var jsonData = $("#caseStopApproveForm").serializeArray();
			
			var url = "${ctx}/caseStop/approveSubmit";
			
			$.ajax({
				cache : true,
				async : true,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				data : jsonData,
    		    beforeSend:function(){  
    				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
    				$(".blockOverlay").css({'z-index':'9998'});
                },
                complete: function() {  
                     if(status=='timeout'){//超时,status还有success,error等值的情况
    	          	  Modal.alert(
    				  {
    				    msg:"抱歉，系统处理超时。"
    				  });
    		  		 $(".btn-primary").one("click",function(){
    		  				parent.$.fancybox.close();
    		  			});	 
    		                }
    		            } , 
				success : function(data) {
					if(data.success) {
						if(data) {
							window.wxc.success("操作成功",{"wxcOk":function(){
								window.close();
						 		//window.location.href = ctx + "/task/eval/evalTaskList";
						    }});
							//caseTaskCheck();
						} else {
							window.wxc.alert("操作失败。");
						}
					} else {
						window.wxc.alert("操作失败。");
					}
				},
				error : function(errors) {
					window.wxc.alert("操作失败。");
				}
			});
		}
	</script> 
	</content>
</body>


</html>