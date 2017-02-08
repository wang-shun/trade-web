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
select[readonly] {  
   background: #eee;  
   cursor: no-drop;  
   }  
select[readonly] option {  
    display: none;  
}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	<div class="">
	<div class="panel " id="serviceFlow">
            <div class="row wrapper white-bg new-heading ">
             <div class="pl10">
                 <h2 class="newtitle-big">
                        临时银行处理
                    </h2>
                <div class="mt20">
                        <button type="button" class="btn btn-icon btn-blue mr5" id="btnZaitu">
                            <i class="iconfont icon">&#xe600;</i> 在途单列表
                        </button>
                        <button type="button" class="btn btn-icon btn-blue mr5" id="btnCaseView" lang="${caseCode}">
                            <i class="iconfont icon">&#xe63f;</i> 案件视图
                        </button>
                    </div>
             </div>
        </div>
				
        <div class="ibox-content border-bottom clearfix space_box noborder">
                    <div>
                        <div class="form_list">
                            <div class="marinfo">
                                <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">临时银行原因</label>
                                        <span>${mortage.tmpBankReason }</span>
                                    </div>
                                </div>
                                <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">贷款银行</label>
                                        <select name="bank_type" class="select_control" id="bank_type"  ${post ne 'manager'?'disabled="true" readonly':''} ></select>
                                    </div>
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">贷款支行</label>
                                        <select name="finOrgCode" class="select_control" id="finOrgCode" ${post ne 'manager'?'disabled="true" readonly':''} ></select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                <div>
                <h2 class="newtitle title-mark">填写任务信息</h2>
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
                <div class="form_list">
                    <div class="marinfo">
                        <div class="line">
                            <div class="form_content">
                                <label class="control-label sign_left_small">审批结果</label>
                                <div class="controls ">
                                   <label class="radio inline"> 
                                   <input type="radio" checked="checked" value="true" id="optionsRadios1" name="tmpBankCheck">审批通过
                                   </label> 
                                   <label class="radio inline"> 
                                   <input type="radio" value="false" id="optionsRadios2" name="tmpBankCheck">审批不通过
                                   </label>
                                </div>
                            </div>
                        </div>
                        <div class="line">
                            <div class="form_content">
                                <label class="control-label sign_left_small">备注</label>
                                <input class="input_type optionwid" id="temBankRejectReason" name="temBankRejectReason">
                            </div>
                        </div>
                    </div>
                </div>
                </form>
            </div> 
            </div>

		<!-- 案件备注信息 -->
		<div class="ibox-title">
		<div class="view-content" id="caseCommentList"> </div>
		</div>
		
		<div id="aboutInfo" class="ibox-title">
			<h5 class="newtitle title-mark">审批记录</h5>
			<div class="ibox-content">
				<div class="jqGrid_wrapper">
					<table id="reminder_list"></table>
					<div id="pager_list_1"></div>	
				</div>
			</div>
		</div>
		<div class="ibox-title">
			<div class="form-btn">
                <div class="text-center">
                     <button class="btn btn-success btn-space" onclick="submit()">提交</button>
                </div>
            </div>
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
	<script src="${ctx}/js/stickUp.js"></script>
	<!-- 改版引入的新的js文件 --> 
	<script src="${ctx}/js/common/textarea.js?v=1.0.1"></script>
	<script src="${ctx}/js/common/common.js?v=1.0.1"></script>
	<script>   
		$(document).ready(function(){
			//添加银行和支行 
			getParentBank($("#bank_type"),$("#finOrgCode"),"${mortage.finOrgCode}");
			$("#bank_type").change(function(){
		    	getBranchBankList($("#finOrgCode"),$("#bank_type").val());
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
				url:ctx+'/mortgage/tmpBankAudit/audit',
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
						window.wxc.error("数据保存出错:"+JSON.stringify(errors));
					}
			});

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
				window.wxc.alert('请选择贷款支行！');
				return false;
			}
			//审批意见必填 
			if(!$("#temBankRejectReason").val() && $("#optionsRadios2").prop("checked")){
				window.wxc.alert("请填写审批意见！");
				return false;
			}

			return true;
		}
	</script> 
	</content>
</body>
</html>