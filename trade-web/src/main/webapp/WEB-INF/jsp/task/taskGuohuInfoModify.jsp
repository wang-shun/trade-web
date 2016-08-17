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

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- jdGrid相关 -->
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/transcss/comment/caseComment.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<script type="text/javascript">
	var ctx = "${ctx}";
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
	var loanReqType = "${loanReqType}";
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
	<div class="">
		<div class=" wrapper border-bottom white-bg page-heading">
			<div class="row">
			<div class="col-lg-10">
				<h2>过户信息修改</h2>
				<ol class="breadcrumb">
					<li><a href="${ctx }/case/myCaseList">在途单列表</a></li>
					<li><a href="${ctx }/task/caseDetail?&caseCode=${caseCode}">案件视图</a></li>
				</ol>
			</div>
			<div class="col-lg-2"></div>
			</div>
		</div>		
		
		<!-- 修改表单数据-->
		<div id="changeForm-modal-form" class="modal fade" role="dialog"
			aria-labelledby="plan-modal-title" aria-hidden="true">
			<div class="modal-dialog" style="width: 1000px">
				<form id="changeForm-form" class="form-horizontal"
					method="post" target="_blank">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">×</button>
							<h4 class="modal-title" id="plan-modal-title">
								选择要修改的表单项目</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="col-lg-3"
									style="margin-top: 9px; margin-left: 15px;">
									请选择您要修改的环节</div>
								<div class="col-lg-3">
									<input name="caseCode" value="${toCase.caseCode}"
										id="hid_case_code" type="hidden"> <input
										name="source" value="caseDetails" type="hidden">
									<input name="instCode" value="${toWorkFlow.instCode}"
										type="hidden"> <select id="sel_changeFrom"
										name="changeFrom" class="form-control m-b"
										style="padding-bottom: 3px; height: 45.003px;">
										<c:forEach items="${myTasks}" var="item">
											<option value="${item.taskDefinitionKey }">${item.name }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-12"
									style="margin-top: 9px; margin-left: 10px;">
									<font color="red">*</font>注1：交易顾问只能修改归属自己的、已提交的任务，未完成的任务请在待办任务中填写。
								</div>
							</div>
							<div class="row">
								<div class="col-lg-12"
									style="margin-top: 9px; margin-left: 10px;">
									<font color="red">*</font>注2：在环节表单中，凡是涉及到交易时间或变更流程走向的信息，系统不允许用户修改。
								</div>
							</div>
						</div>
						<div class="modal-footer">


							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<button type="submit" class="btn btn-primary">去修改</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		
		<div class="ibox-title">
			<h5>修改信息</h5>
			<div class="ibox-content">
				<form method="get" class="form-horizontal" id="lamform">
					<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
					<%-- 原有数据对应id --%>
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
					<%-- 设置审批类型 --%>
					<input type="hidden" id="approveType" name="approveType" value="${approveType }">
					<input type="hidden" id="lapPkid" name="lapPkid" value="${toApproveRecord.pkid }">
					<input type="hidden" id="operator" name="operator" value="${operator }">
                    		
		            <a style="float: left;margin-left: -20px; margin-top: -10px;" class="btn btn-primary"
						href="javascript:showChangeFormModal();">我要修改</a>
				</form>

			</div>
		</div>

		<!-- 案件备注信息 -->
		<div id="caseCommentList" class="add_form">
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
		<div class="ibox-title">
			<!-- <a href="#" class="btn" onclick="save()">保存</a> -->
			<a href="#" class="btn btn-primary" onclick="submit()">提交</a>
		</div>
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
	<script src="${ctx}/js/trunk/comment/caseComment.js"></script>
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
	<script>   
	/**
	 * 案件详情
	 * @wanggh
	 */
	Array.prototype.contains = function(obj){
		 var i = this.length;
	     while (i--) {
	         if (this[i] === obj) {
	         return true;
	         }
	     }
	     return false;
	};
	var changeTaskList=['TransSign','PurchaseLimit','Pricing','TaxReview','LoanClose','ComLoanProcess','PSFApply','PSFSign', 'PSFApprove',
	                    'LoanlostApply','SelfLoanApprove','Guohu','HouseBookGet','LoanRelease'];
	var comLoanTasks=['ComLoanProcess'];
	var psfLoanTasks=["PSFApply","PSFSign","PSFApprove"];
	var loanLostTasks=['LoanlostApply','LoanlostApproveManager','LoanlostApproveDirector','SelfLoanApprove'];
	var fullPay=[];
	var loanTasks={'PSFLoan':psfLoanTasks,'ComLoan':comLoanTasks,SelfLoan:loanLostTasks,"FullPay":fullPay};
	var loanTaskArry= new Array();
	loanTaskArry = loanTaskArry.concat(comLoanTasks,psfLoanTasks,loanLostTasks,fullPay);

	$(document).ready(			
			function() {
				$("#sel_changeFrom option").each(function(){
					var _this=$(this);
					var taskDfKey=_this.val();
					if(!changeTaskList.contains(taskDfKey) ||(loanTaskArry.contains(taskDfKey)&&!loanTasks[loanReqType].contains(taskDfKey))){
						_this.remove();
					}
				});

				$("#sel_changeFrom").change(function(){
					$("#changeForm-form").attr('action','../../../task/'+$("#sel_changeFrom").val());
				});
				$("#sel_changeFrom").change();
				$("#changeForm-form").submit(function(){
					$('#changeForm-modal-form').modal("hide");
				});
				$("#changeForm-form").submit(function(){
					if($("#sel_changeFrom").val()==null||$("#sel_changeFrom").val()==''){
						alert('请选择要修改的项目！');
						return false;
					}
				});
				})
			
	function showChangeFormModal(){
		$('#changeForm-modal-form').modal("show");
	}
	
	/**提交数据*/
	function submit() {
		$.ajax({
			url:ctx+'/task/guohuApprove/guohuInfoModifySubmit',
			method:"post",
			dataType:"json",
			data:$("#lamform").serialize(),
			beforeSend:function(){  
    				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
    				$(".blockOverlay").css({'z-index':'9998'});
                },
            complete: function() {
		                 $.unblockUI(); 
                     if(status=='timeout'){ //超时,status还有success,error等值的情况
    	          	  Modal.alert(
    				  {
    				    msg:"抱歉，系统处理超时。"
    				  }); 
    		                } 
    		            } ,   
			success : function(data) {   
					 if(window.opener)
				     {
						 window.close();
						 window.opener.callback();
				     } else {
				    	 window.location.href = "${ctx }/task/myTaskList";
				     }
					 $.unblockUI();
				},
				
				error : function(errors) {
					$.unblockUI();   
					alert("数据保存出错:"+JSON.stringify(errors));
				}
		});
	}

	</script> 
	</content>
</body>


</html>