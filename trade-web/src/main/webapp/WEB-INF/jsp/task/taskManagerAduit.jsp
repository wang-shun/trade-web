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
				<h2>临时银行审批</h2>
				<ol class="breadcrumb">
					<li><a href="${ctx }/case/myCaseList">在途单列表</a></li>
					<li><a href="${ctx }/task/caseDetail?&caseCode=${caseCode}">案件视图</a></li>
				</ol>
			</div>
			<div class="col-lg-2"></div>
			</div>
		</div>
		
		
		
			<div class="ibox-content">
			<div class="row form-group">
				<label class="col-sm-2 control-label">临时银行原因:</label>
				<div class="col-xs-3">${mortage.tmpBankReason }</div>
			</div>
			<div class="row form-group">
				<label class="col-sm-2 control-label">贷款银行：</label>
				<div class="col-md-4" style="height: 38px">

				<select name="bank_type" class="form-control" id="bank_type"  ${post ne 'manager'?'disabled="true"':''} >
				</select>
				</div>

				<label class="col-sm-2 control-label">贷款支行<span class="star">*</span>：
				</label>
				<div class="col-md-4" style="height: 38px">

					<select name="finOrgCode" class="form-control" id="finOrgCode" ${post ne 'manager'?'disabled="true"':''} >
					</select>
				</div>
			</div>
			
		</div>
		
		
		<div class="ibox-title">
			<h5>填写任务信息</h5>
			<div class="ibox-content">
				<form method="get" class="form-horizontal" id="lamform">
				    <input type="hidden" name="post" value="${post}">
				    <input type="hidden" name="bankCode" id="bankCode">
				    <input type="hidden" name="prAddress" value="${caseBaseVO.toPropertyInfo.propertyAddr }">
		            <input type="hidden" name="tmpBankName" id="tmpBankName">
		            <input type="hidden" name="pkid" value="${mortage.pkid }">
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
					<div class="form-group">
						<label class="col-sm-2 control-label">审批结果</label>
						<div class="col-sm-3">
							<div class="radio i-checks radio-inline">
								<label> 
									<input type="radio" checked="checked" value="true" id="optionsRadios1" name="tmpBankCheck">审批通过
								</label>
								<label> 
									<input type="radio" value="false" id="optionsRadios2" name="tmpBankCheck">审批不通过
								</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">拒绝原因</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="temBankRejectReason" name="temBankRejectReason" value="" disabled="disabled" >
						</div>
					</div>
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
		$(document).ready(function(){
			//添加银行和支行 
			getParentBank($("#bank_type"),$("#finOrgCode"),"${mortage.finOrgCode}");
			$("#bank_type").change(function(){
		    	getBranchBankList($("#finOrgCode"),$("#bank_type").val());
		    });
			$("input[name='tmpBankCheck']").click(function(){
				if($(this).val() == 'false'){
					 $("#temBankRejectReason").prop("disabled",false);
				}else if($(this).val() == 'true'){
					 $("#temBankRejectReason").prop("disabled",true);
				}
			});
			})
		
		/**提交数据*/
		function submit() {
			save();
		}
	
		/**保存数据*/
		function save() {
			$("#tmpBankName").val($("#finOrgCode").find("option:selected").text());
			$("#bankCode").val($("#finOrgCode").val());
			var jsonData = $("#lamform").serializeArray();
            //alert(JSON.stringify(jsonData));return;
			//更新银行和支行
			if(!fCheck()){
				return ;
			}

			$.ajax({
				url:ctx+'/mortgage/tmpBankAudit/aduit',
				method:"post",
				dataType:"json",
				data:jsonData,
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
						/*if(data.message){
							alert(data.message);
						}*/
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
			
			/*$.ajax({
				cache : true,
				async : false,//false同步，true异步
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
					if(data.message){
						alert(data.message);
					}
					 if(window.opener)
				     {
						 window.close();
						 window.opener.callback();
				     } else {
				    	 window.location.href = "${ctx }/task/myTaskList";
				     }
				},
				error : function(errors) {
					alert("数据保存出错");
				}
			});*/
		}

		function getParentBank(selector,selectorBranch,finOrgCode){
			var bankHtml = "<option value=''>请选择</option>";
		    $.ajax({
		    	cache:true,
		    	url:ctx+"/manage/queryParentBankList",
				method:"post",
				dataType:"json",
				async:false,
				data:{nowCode:finOrgCode},
				success:function(data){
					if(data != null){
						for(var i = 0;i<data.length;i++){
							var coLevelStr='';
							bankHtml+="<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>";
						}
					}
				}
		     });
		    selector.find('option').remove();
			 selector.append($(bankHtml));
			 $.ajax({
				    url:ctx+"/manage/queryParentBankInfo",
				    method:"post",
				    dataType:"json",
					async:false,
				    data:{finOrgCode:finOrgCode},
				    success:function(data){
			    		if(data != null){
			    			selector.val(data.content);
			    		}
			    	}
				});
			 getBranchBankList(selectorBranch,selector.val(),finOrgCode);
			 return bankHtml;
		}
		function getBranchBankList(selectorBranch,pcode,finOrgCode){
			selectorBranch.find('option').remove();
			selectorBranch[0];
			selectorBranch.append($("<option value=''>请选择</option>"));
			$.ajax({
				cache:true,
			    url:ctx+"/manage/queryBankListByParentCode",
			    method:"post",
			    dataType:"json",
				async:false,
			    data:{faFinOrgCode:pcode,nowCode:finOrgCode},
			    	success:function(data){
			    		if(data != null){
			    			for(var i = 0;i<data.length;i++){
								var coLevelStr='('+data[i].coLevelStr+')';
					
								var option = $("<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>");
								if(data[i].finOrgCode==finOrgCode){
									option.attr("selected",true);
								}
								selectorBranch.append(option);
			    			}
			    		}
			    	}
			 });
			return true;
		}
		
		function fCheck(){
			if(''==$("#finOrgCode").val()){
				alert('请选择贷款支行');
				return false;
			}
			return true;
		}
	</script> 
	</content>
</body>


</html>