<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>

<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- jdGrid相关 -->
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link href="${ctx}/css/transcss/comment/caseComment.css" rel="stylesheet">
<!-- 新调整页面样式 -->
<link href="${ctx}/css/common/caseDetail.css" rel="stylesheet">
<link href="${ctx}/css/common/details.css" rel="stylesheet">
<link href="${ctx}/css/iconfont/iconfont.css" rel="stylesheet">
<link href="${ctx}/css/common/btn.css" rel="stylesheet">
<link href="${ctx}/css/common/input.css" rel="stylesheet">
<link href="${ctx}/css/common/table.css" rel="stylesheet">
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
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	<div class="row wrapper border-bottom white-bg page-heading marginbot">
		<div class="row animated fadeInDown">
            <div class="scroll_box fadeInDown animated">
            	<div class="row wrapper white-bg new-heading">
             		<div class="pl10">
                 		<h2 class="newtitle-big">流程重启审批</h2>
                		<div class="mt20">
		                     <button type="button" class="btn btn-icon btn-blue mr5" id="btnZaitu">
		                    	<i class="iconfont icon">&#xe640;</i> 在途单列表
		                    </button>
		                    <button type="button" class="btn btn-icon btn-blue mr5" id="btnCaseView" lang="${caseCode}">
		                        <i class="iconfont icon">&#xe642;</i>案件视图
		                    </button>
                		</div>
             		</div>
        		</div>

        		<div class="ibox-content clearfix space_box noborder">           
        			<div>
            			<h2 class="newtitle title-mark">填写任务信息</h2>
            			<div class="form_list">
                			<div class="marinfo">
                				<form method="get" class="form-horizontal" id="lamform">
									<%--环节编码 --%>
									<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
									<!-- 交易单编号 -->
									<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
									<%-- 原有数据对应id --%>
									<input type="hidden" id="taskId" name="taskId" value="${taskId }">
									<input type="hidden" id="processInstanceId" name="instCode" value="${processInstanceId}">
									
				                    <div class="line">
				                        <div class="form_content">
				                            <label class="control-label sign_left_small">审批结果</label>
				                            <div class="controls ">
				                               	<label class="radio inline"> 
				                               		<input type="radio" checked="checked" value="true" id="optionsRadios1" name="isApproved">审批通过
				                               </label> 
				                               <label class="radio inline"> 
				                               		<input type="radio" value="false" id="optionsRadios2" name="isApproved">审批未通过
				                               </label>
				                            </div>
				                        </div>
				                    </div>
				                    <div class="line">
				                        <div class="form_content">
				                            <label class="control-label sign_left_small">审批意见</label>
				                            <input class="input_type optionwid" id="content" name="content" value="" >
				                        </div>
				                    </div>
		                    	</form>
                			</div>
            			</div>
        			</div>
        			
					<!-- 案件备注信息 -->
				  	<div id="caseCommentList" class="view-content"></div>
				  	
				  	<div class="clearfix">
						<h2 class="newtitle title-mark">审批记录</h2>
						<div class="jqGrid_wrapper">
							<table id="reminder_list"></table>
							<div id="pager_list_1"></div>
						</div>
					</div>
				  
			      	<div class="form-btn">
		                <div class="text-center">
		                     <button class="btn btn-success btn-space" onclick="submit()">提交</button>
		                </div>
                   </div>
            </div>
        </div>
    </div>

	<content tag="local_script"> 
		<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
		<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
		<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
		<script src="${ctx}/transjs/task/loanlostApprove.js"></script>
		<script src="${ctx}/transjs/task/showAttachment.js"></script> 
		<script src="${ctx}/js/jquery.blockui.min.js"></script>
		<script src="${ctx}/js/trunk/comment/caseComment.js"></script>
		<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
		<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
		<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
		<!-- 改版引入的新的js文件 -->
	    <script src="${ctx}/js/common/textarea.js?v=1.0.1"></script> 
		<script src="${ctx}/js/common/common.js?v=1.0.1"></script>
		
		<script>
			//提交数据
			function submit() {			
				save();
			}
	
			//保存数据
			function save() {
				
				if($("#optionsRadios2").prop("checked") && $("#content").val().trim() == ''){
					alert("驳回时需填写审批意见！");
					$("#content").focus();
					return false;
				}
				
				var jsonData = $("#lamform").serializeArray();
				
				var url = "${ctx}/service/approve";
				
				$.ajax({
					cache : true,
					async : true,
					type : "POST",
					url : url,
					dataType : "json",
					data : jsonData,
	    		    beforeSend:function(){  
	    				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
	    				$(".blockOverlay").css({'z-index':'9998'});
	                },
	                complete: function() {  
	                     if(status=='timeout'){
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
							alert(data.message);
							 if(window.opener)
						     {
								 window.close();
								 window.opener.callback();
						     } else {
						    	 window.location.href = "${ctx }/task/myTaskList";
						     }
						} else {
							alert("操作失败。");
						}
					},
					error : function(errors) {
						alert("操作失败。");
					}
				});
			}
		</script> 
	</content>
</body>
</html>