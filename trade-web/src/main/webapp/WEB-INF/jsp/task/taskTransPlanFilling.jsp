<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include> 

<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<script type="text/javascript">
var ctx = "${ctx}";
var dy = "${dy}";
var dk = "${dk}";
var caseCode = "${caseCode}";
</script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	<div class="row">
	    <div class="ibox-title">
			<div class="row wrapper border-bottom white-bg page-heading">
				<div class="col-lg-10">
					<h2>填写交易计划</h2>
					<ol class="breadcrumb">
					<li><a href="${ctx }/case/myCaseList">在途单列表</a></li>
					<li><a href="${ctx }/task/caseDetail?&caseCode=${caseCode}">案件视图</a></li>
					</ol>
				</div>
				<div class="col-lg-2"></div>
			</div>
		</div>
		
		<div class="ibox-title">
			<h5>填写任务信息</h5>
			<div class="ibox-content">
				<form method="POST" class="form-horizontal" id="transPlanForm">
					<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
					<!-- 流程引擎需要字段 -->
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
					<%-- 原有数据对应id --%>
					<%-- <input type="hidden" id="pkidTp" name="pkidTp" value="${transPlan.pkidTp}"> --%>
					<input type="hidden" id="pkidPc" name="pkidPc" value="${transPlan.pkidHd}">
					<input type="hidden" id="pkidTr" name="pkidTr" value="${transPlan.pkidTr}">
					<input type="hidden" id="pkidGh" name="pkidGh" value="${transPlan.pkidGh}">
					<input type="hidden" id="pkidPl" name="pkidLz" value="${transPlan.pkidLz}">
					<input type="hidden" id="pkidPl" name="pkidFk" value="${transPlan.pkidFk}">
					<c:if test="${dy}">
						<div class="form-group" id="data_1">
							<label class="col-sm-2 control-label">预计还贷时间<font color="red">*</font></label>
							<div class="col-sm-10 input-group date">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								<input type="text" class="form-control" name="estPartTimeHd" id="estPartTimeHd"
								 	 onfocus="this.blur()" value="<fmt:formatDate  value='${transPlan.estPartTimeHd}' type='both' pattern='yyyy-MM-dd'/>">
							</div>
						</div>
					</c:if>
					<div class="form-group" id="data_1">
						<label class="col-sm-2 control-label">预计审税时间<font color="red">*</font></label>
						<div class="col-sm-10 input-group date">
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							<input type="text" class="form-control" name="estPartTimeTr" id="estPartTimeTr"
								 onfocus="this.blur()" value="<fmt:formatDate  value='${transPlan.estPartTimeTr}' type='both' pattern='yyyy-MM-dd'/>">
						</div>
					</div>
					<div class="form-group" id="data_1">
						<label class="col-sm-2 control-label">预计过户时间<font color="red">*</font></label>
						<div class="col-sm-10 input-group date">
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							<input type="text" class="form-control" name="estPartTimeGh" id="estPartTimeGh"
								 onfocus="this.blur()" value="<fmt:formatDate  value='${transPlan.estPartTimeGh}' type='both' pattern='yyyy-MM-dd'/>">
						</div>
					</div>
					<div class="form-group" id="data_1">
						<label class="col-sm-2 control-label">预计领证时间<font color="red">*</font></label>
						<div class="col-sm-10 input-group date">
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							<input type="text" class="form-control" name="estPartTimeLz" id="estPartTimeLz"
								onfocus="this.blur()" value="<fmt:formatDate  value='${transPlan.estPartTimeLz}' type='both' pattern='yyyy-MM-dd'/>">
						</div>
					</div>
					<c:if test="${dk}">
					<div class="form-group" id="data_1">
						<label class="col-sm-2 control-label">预计放款时间<font color="red">*</font></label>
						<div class="col-sm-10 input-group date">
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							<input type="text" class="form-control" name="estPartTimeFk" id="estPartTimeFk"
								onfocus="this.blur()" value="<fmt:formatDate  value='${transPlan.estPartTimeFk}' type='both' pattern='yyyy-MM-dd'/>">
						</div>
					</div>
					</c:if>

				</form>
			</div>
		</div>
		<div class="ibox-title">
			<a href="#" class="btn" onclick="save(false)">保存</a> 
			<a href="#" class="btn btn-primary" onclick="submit()">提交</a>
		</div>
	</div>
<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
	<content tag="local_script"> 
	<!-- Peity --> 
	<script	src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
	<!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 

	<script src="${ctx}/transjs/common/caseTaskCheck.js?v=1.0.1"></script> 
	<!-- Custom and plugin javascript -->
	<script	src="${ctx}/js/plugins/dropzone/dropzone.js"></script> 
	<!-- Data picker -->
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>

	<script src="${ctx}/js/jquery.blockui.min.js"></script>

	<script>
		$(document).ready(
			function() {


			$('#data_1 .input-group.date').datepicker({
				todayBtn : "linked",
				keyboardNavigation : false,
				forceParse : false,
				autoclose : true
			});

			// Add responsive to jqGrid
			$(window).bind('resize', function() {
				var width = $('.jqGrid_wrapper').width();
				$('#table_list_1').setGridWidth(width);
				$('#table_list_2').setGridWidth(width);

			});
		});
		
		function save(b) {
			if(!checkForm()) {
				return;
			}
			var jsonData = $("#transPlanForm").serializeArray();
			
			var url = "${ctx}/task/transPlan/saveTransPlan";
			if(b) {
				url = "${ctx}/task/transPlan/submitTransPlan";
			}
			
			$.ajax({
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
                	$.unblockUI();  
                 	if(b){ 
                         $.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'1900'}}); 
     				    $(".blockOverlay").css({'z-index':'1900'});
                 	}   
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
						if(b) {
							caseTaskCheck();
						} else {
						 	alert("保存成功。");
						 	window.close();
						 	window.opener.callback();
						}
				},
				error : function(errors) {
					alert("数据保存出错");
				}
				
			});
		}
		
		function submit(){
			save(true);
			//caseTaskCheck();
		}
		
		//验证控件checkUI();
		function checkForm() {
			if(dy) {
				if($('input[name=estPartTimeHd]').val()=='') {
	                alert("预计还贷为必填项!");
	                $('input[name=estPartTimeHd]').focus();
	                return false;
	           }
			}
			if($('input[name=estPartTimeTr]').val()=='') {
                alert("预计审税时间为必填项!");
                $('input[name=estPartTimeTr]').focus();
                return false;
           }
			if($('input[name=estPartTimeGh]').val()=='') {
                alert("预计过户时间为必填项!");
                $('input[name=estPartTimeGh]').focus();
                return false;
           }
			if($('input[name=estPartTimeLz]').val()=='') {
                alert("预计领证时间为必填项!");
                $('input[name=estPartTimeLz]').focus();
                return false;
           }
			if(dk) {
				if($('input[name=estPartTimeFk]').val()=='') {
	                alert("预计放款时间为必填项!");
	                $('input[name=estPartTimeFk]').focus();
	                return false;
	           }
			}
			return true;
		}
	</script> 
	</content>
</body>


</html>
