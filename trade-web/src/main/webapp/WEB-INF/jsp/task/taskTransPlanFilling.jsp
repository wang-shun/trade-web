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
	var dy = "${dy}";
	var dk = "${dk}";
	var caseCode = "${caseCode}";
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	<div class="row wrapper white-bg new-heading">
             <div class="pl10">
                <h2 class="newtitle-big">
                        	填写交易计划
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
        </div>

        <div class="ibox-content border-bottom clearfix space_box noborder marginbot">
            <div>
                <h2 class="newtitle title-mark">填写任务信息</h2>
                <div class="form_list">
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
						
	                    <div class="marinfo">
	                        <div class="line">
	                        	<c:if test="${dy}">
		                            <div class="form_content mt3">
		                                <label class="control-label sign_left_small select_style mend_select">
		                                   	<font color="red" class="mr5" >*</font>预计还贷时间
		                                </label>
		                                <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
		                                	<input type="text" class="input_type yuanwid datatime" name="estPartTimeHd" id="estPartTimeHd"
								 	 			onfocus="this.blur()" value="<fmt:formatDate  value='${transPlan.estPartTimeHd}' type='both' pattern='yyyy-MM-dd'/>">
		                                </div>
		                            </div>
	                            </c:if>
	                            <div class="form_content mt3">
	                                <label class="control-label sign_left_small select_style mend_select">
	                                  	<font color=" red" class="mr5" >*</font> 预计审税时间
	                                </label>
	                                <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
	                                	<input type="text" class="input_type yuanwid datatime" name="estPartTimeTr" id="estPartTimeTr"
								 			onfocus="this.blur()" value="<fmt:formatDate  value='${transPlan.estPartTimeTr}' type='both' pattern='yyyy-MM-dd'/>">
	                                </div>
	                            </div>
	                        
	                            <div class="form_content mt3">
	                                <label class="control-label sign_left_small select_style mend_select">
	                                   	<font color=" red" class="mr5" >*</font>预计过户时间
	                                </label>
	                                <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
	                                	<input type="text" class="input_type yuanwid datatime" name="estPartTimeGh" id="estPartTimeGh"
								 			onfocus="this.blur()" value="<fmt:formatDate  value='${transPlan.estPartTimeGh}' type='both' pattern='yyyy-MM-dd'/>">
	                                </div>
	                            </div>
	                            <div class="form_content mt3">
	                                <label class="control-label sign_left_small select_style mend_select">
	                                   	<font color=" red" class="mr5" >*</font>预计领证时间
	                                </label>
	                                <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
	                                	<input type="text" class="input_type yuanwid datatime" name="estPartTimeLz" id="estPartTimeLz"
											onfocus="this.blur()" value="<fmt:formatDate  value='${transPlan.estPartTimeLz}' type='both' pattern='yyyy-MM-dd'/>">
	                                </div>
	                            </div>
	                        <c:if test="${dk}">
		                            <div class="form_content mt3">
		                                <label class="control-label sign_left_small select_style mend_select">
		                                   	<font color=" red" class="mr5" >*</font>预计放款时间
		                                </label>
		                                <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
		                                	<input type="text" class="input_type yuanwid datatime" name="estPartTimeFk" id="estPartTimeFk"
												onfocus="this.blur()" value="<fmt:formatDate  value='${transPlan.estPartTimeFk}' type='both' pattern='yyyy-MM-dd'/>">
		                                </div>
		                            </div>
		                        </div>
	                        </c:if>
	                    </div>
                    </form>
                </div>
            </div>
            
            <div class="view-content" id="caseCommentList">
            </div>

            <div class="form-btn">
                    <div class="text-center">
                         <button class="btn btn-success btn-space" onclick="save(false)">保存</button>
                         <button class="btn btn-success btn-space" onclick="submit()">提交</button>
                    </div>
                </div>
            </div>
                    
	<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
	<content tag="local_script"> 
		<script	src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
		<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
		<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
		<script src="${ctx}/transjs/common/caseTaskCheck.js?v=1.0.1"></script> 
		<script	src="${ctx}/js/plugins/dropzone/dropzone.js"></script> 
		<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
		<script src="${ctx}/js/jquery.blockui.min.js"></script>
		<script src="${ctx}/js/trunk/comment/caseComment.js"></script>
		<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
		<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
		<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
		
		<!-- 改版引入的新的js文件 -->
		<script src="${ctx}/js/common/textarea.js?v=1.0.1"></script>
		<script src="${ctx}/js/common/common.js?v=1.0.1"></script>
	<script>
		$(document).ready(function() {

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
			
			$("#caseCommentList").caseCommentGrid({
				caseCode : caseCode,
				srvCode : 'TransPlanFilling'
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
	                alert("预计还贷时间为必填项!");
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
