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
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />

<!-- 新调整页面样式 -->
<link href="<c:url value='/css/common/caseDetail.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">

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
            <h2 class="newtitle title-mark">填写计划信息</h2>
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
				<%--天津废弃<input type="hidden" id="pkidTr" name="pkidTr" value="${transPlan.pkidTr}"> --%>
				<input type="hidden" id="pkidGh" name="pkidGh" value="${transPlan.pkidGh}">
				<input type="hidden" id="pkidLz" name="pkidLz" value="${transPlan.pkidLz}">
				<input type="hidden" id="pkidFk" name="pkidFk" value="${transPlan.pkidFk}">
				<%-- 天津新增 --%>
				<input type="hidden" id="pkidPt" name="pkidPt" value="${transPlan.pkidPt}">
				<input type="hidden" id="pkidCs" name="pkidCs" value="${transPlan.pkidCs}">
				<input type="hidden" id="pkidBr" name="pkidBr" value="${transPlan.pkidBr}">
				<input type="hidden" id="pkidCc" name="pkidCc" value="${transPlan.pkidCc}">
				<input type="hidden" id="pkidPa" name="pkidPa" value="${transPlan.pkidPa}">
				<input type="hidden" id="pkidPfs" name="pkidPfs" value="${transPlan.pkidPfs}">
				<input type="hidden" id="pkidPfc" name="pkidPfc" value="${transPlan.pkidPfc}">
		
                 <div class="marinfo">
                 	 <div class="line">
                 	 	<div class="form_content mt3">
                             <label class="control-label sign_left_small select_style mend_select">
                               	<font color=" red" class="mr5" >*</font> 预计缴税时间
                             </label>
                             <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                             	<input type="text" class="input_type yuanwid datatime" name="estPartTimePt" id="estPartTimePt"
				 			onfocus="this.blur()" value="<fmt:formatDate  value='${transPlan.estPartTimePt}' type='both' pattern='yyyy-MM-dd'/>">
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
                                	<font color=" red" class="mr5" >*</font>预计领他证时间
                             </label>
                             <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                             	<input type="text" class="input_type yuanwid datatime" name="estPartTimeLz" id="estPartTimeLz"
							onfocus="this.blur()" value="<fmt:formatDate  value='${transPlan.estPartTimeLz}' type='both' pattern='yyyy-MM-dd'/>">
                             </div>
                         </div>
                 	 </div>
                 	 <div class="line">
                 	 	<div class="form_content mt3">
                             <label class="control-label sign_left_small select_style mend_select">
                                	<font color=" red" class="mr5" >*</font>预计商贷面签时间
                             </label>
                             <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                             	<input type="text" class="input_type yuanwid datatime" name="estPartTimeCs" id="estPartTimeCs"
				 			onfocus="this.blur()" value="<fmt:formatDate  value='${transPlan.estPartTimeCs}' type='both' pattern='yyyy-MM-dd'/>">
                             </div>
                         </div>
                         <div class="form_content mt3">
                             <label class="control-label sign_left_small select_style mend_select">
                                	<font color=" red" class="mr5" >*</font>预计商贷出评估报告时间
                             </label>
                             <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                             	<input type="text" class="input_type yuanwid datatime" name="estPartTimeBr" id="estPartTimeBr"
							onfocus="this.blur()" value="<fmt:formatDate  value='${transPlan.estPartTimeBr}' type='both' pattern='yyyy-MM-dd'/>">
                             </div>
                         </div>
                         <div class="form_content mt3">
                             <label class="control-label sign_left_small select_style mend_select">
                                	<font color=" red" class="mr5" >*</font>预计商贷批贷完成时间
                             </label>
                             <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                             	<input type="text" class="input_type yuanwid datatime" name="estPartTimeCc" id="estPartTimeCc"
				 			onfocus="this.blur()" value="<fmt:formatDate  value='${transPlan.estPartTimeCc}' type='both' pattern='yyyy-MM-dd'/>">
                             </div>
                         </div>
                 	 </div>
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
                               	<font color=" red" class="mr5" >*</font> 预计公积金贷款预约申请时间
                             </label>
                             <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                             	<input type="text" class="input_type yuanwid datatime" name="estPartTimePa" id="estPartTimePa"
				 			onfocus="this.blur()" value="<fmt:formatDate  value='${transPlan.estPartTimePa}' type='both' pattern='yyyy-MM-dd'/>">
                             </div>
                         </div>
                         <div class="form_content mt3">
                             <label class="control-label sign_left_small select_style mend_select">
                               	<font color=" red" class="mr5" >*</font> 预计公积金面签时间
                             </label>
                             <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                             	<input type="text" class="input_type yuanwid datatime" name="estPartTimePfs" id="estPartTimePfs"
				 			onfocus="this.blur()" value="<fmt:formatDate  value='${transPlan.estPartTimePfs}' type='both' pattern='yyyy-MM-dd'/>">
                             </div>
                         </div>
                         <div class="form_content mt3">
                             <label class="control-label sign_left_small select_style mend_select">
                               	<font color=" red" class="mr5" >*</font> 预计公积金批贷完成时间
                             </label>
                             <div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
                             	<input type="text" class="input_type yuanwid datatime" name="estPartTimePfc" id="estPartTimePfc"
				 			onfocus="this.blur()" value="<fmt:formatDate  value='${transPlan.estPartTimePfc}' type='both' pattern='yyyy-MM-dd'/>">
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
			<script	src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script> 
			<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
			<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script> 
			<script src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script> 
			<script	src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script> 
			<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
			<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
			<script src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
			<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
			<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
			<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
			<!-- 改版引入的新的js文件 -->
			<script src="<c:url value='/js/common/textarea.js' />"></script>
			<script src="<c:url value='/js/common/common.js' />"></script>
			
			<script>
				$(document).ready(function() {

					$('#data_1 .input-group.date').datepicker({
						todayBtn : "linked",
						keyboardNavigation : false,
						forceParse : false,
						autoclose : true
					});

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
						async : false,
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
									window.wxc.success("保存成功。",{"wxcOk":function(){
										window.close();
									 	window.opener.callback();
									}});
								}
						},
						error : function(errors) {
							window.wxc.error("数据保存出错");
						}
						
					});
				}
		
				function submit(){
					save(true);
				}
		
				//验证控件checkUI();
				function checkForm() {
					if(dy) {
						if($('input[name=estPartTimeHd]').val()=='') {
							window.wxc.alert("预计还贷时间为必填项!");
			                $('input[name=estPartTimeHd]').focus();
			                return false;
			           }
					}
					/*
					if($('input[name=estPartTimeTr]').val()=='') {
						window.wxc.alert("预计审税时间为必填项!");
		                $('input[name=estPartTimeTr]').focus();
		                return false;
		           }*/
		           if($('input[name=estPartTimePt]').val()=='') {
						window.wxc.alert("预计缴税时间为必填项!");
		                $('input[name=estPartTimePt]').focus();
		                return false;
		           }
					if($('input[name=estPartTimeGh]').val()=='') {
						window.wxc.alert("预计过户时间为必填项!");
		                $('input[name=estPartTimeGh]').focus();
		                return false;
		           }
					if($('input[name=estPartTimeLz]').val()=='') {
						window.wxc.alert("预计领他证时间为必填项!");
		                $('input[name=estPartTimeLz]').focus();
		                return false;
		           }
					if($('input[name=estPartTimeCS]').val()=='') {
						window.wxc.alert("预计商贷面签时间为必填项!");
		                $('input[name=estPartTimeCS]').focus();
		                return false;
		           }
					if($('input[name=estPartTimeBr]').val()=='') {
						window.wxc.alert("预计商贷出评估报告时间为必填项!");
		                $('input[name=estPartTimeBr]').focus();
		                return false;
		           }
					if($('input[name=estPartTimeCc]').val()=='') {
						window.wxc.alert("预计商贷批贷完成时间为必填项!");
		                $('input[name=estPartTimeCc]').focus();
		                return false;
		           }
					if($('input[name=estPartTimePa]').val()=='') {
						window.wxc.alert("预计公积金贷款预约申请时间为必填项!");
		                $('input[name=estPartTimePa]').focus();
		                return false;
		           }
					if($('input[name=estPartTimePfs]').val()=='') {
						window.wxc.alert("预计公积金面签时间为必填项!");
		                $('input[name=estPartTimePfs]').focus();
		                return false;
		           }
					if($('input[name=estPartTimePfc]').val()=='') {
						window.wxc.alert("预计公积金批贷完成时间为必填项!");
		                $('input[name=estPartTimePfc]').focus();
		                return false;
		           }
					if(dk) {
						if($('input[name=estPartTimeFk]').val()=='') {
							window.wxc.alert("预计放款时间为必填项!");
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
