<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include> 
<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<!-- 上传相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css" rel="stylesheet">
<!-- 展示相关 -->
<link href="${ctx}/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/bootstrap-tokenfield.css" rel="stylesheet">
<link href="${ctx}/css/trunk/JSPFileUpload/selectize.default.css" rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
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

<link rel="stylesheet" href="${ctx}/js/viewer/viewer.min.css" />
<script type="text/javascript">
	var ctx = "${ctx}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index=0;
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var tz="${tz}";
	var isSelfCom = "${loanRelease.isDelegateYucui}";
	if("${idList}" != "") {
		var idList = eval("("+"${idList}"+")");
	} else {
		var idList = [];
	}
</script>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	   <!-- 服务流程 -->
		<div class="row wrapper white-bg new-heading " id="serviceFlow">
             <div class="pl10">
                 <h2 class="newtitle-big">
                        放款
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
            <h2 class="newtitle title-mark">填写任务信息</h2>
            <div class="form_list">
            <form method="get" class="form-horizontal" id="loanReleaseForm">
					<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
					<input type="hidden" id="taskitem" name="taskitem" value="${taskitem}">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
					<!-- 流程引擎需要字段 -->
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
					<%-- 原有数据对应id --%>
					<input type="hidden" id="pkid" name="pkid" value="${loanRelease.pkid}">
					
					<c:if test="${tz}">
					<div class="marinfo">
                    <div class="line">
                        <div class="form_content" id="data_1">
                            <label class="control-label sign_left select_style mend_select">
									他证送抵时间<c:if test="${loanRelease.isDelegateYucui=='1'}"><font color=" red" class="mr5" >*</font></c:if>
                            </label>
                            <div class="input-group sign-right dataleft input-daterange pull-left date" data-date-format="yyyy-mm-dd">
                                <input type="text" class="input_type yuanwid datatime" id="tazhengArrDate" name="tazhengArrDate" onfocus="this.blur()"
									value="<fmt:formatDate  value='${loanRelease.tazhengArrDate}' type='both' pattern='yyyy-MM-dd'/>">
                            </div>
                        </div>
                    </div>
                </div>
					</c:if>
					
                <div class="marinfo">
                    <div class="line">
                        <div class="form_content" id="data_1_forBank">
                            <label class="control-label sign_left select_style mend_select">
                                	银行真实放款时间<font color=" red" class="mr5" >*</font>
                            </label>
                            <div class="input-group sign-right dataleft input-daterange pull-left date" data-date-format="yyyy-mm-dd">
                                <input type="text" class="input_type yuanwid datatime" id="lendDate" name="lendDate" onfocus="this.blur()"
								value="<fmt:formatDate  value='${loanRelease.lendDate}' type='both' pattern='yyyy-MM-dd'/>" >
                            </div>
                        </div>
                    </div>
                </div>
                </form>
            </div>
        </div>
        
        <!-- 相关信息 -->
		<div id="aboutInfo">
		<!-- 案件跟进 -->
        <div class="view-content" id="caseCommentList"> </div>

        <div class="form-btn">
            <div class="text-center">
                <button  class="btn btn-success btn-space" onclick="save(false)">保存</button>
                <button class="btn btn-success btn-space" onclick="submit()" id="btnSubmit">提交</button>
            </div>
        </div>
        </div>
        
        </div>
        

	<content tag="local_script"> 
	<!-- Peity --> 
	<script	src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> 
	<!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<!-- Custom and plugin javascript -->
	<script	src="${ctx}/js/plugins/dropzone/dropzone.js"></script> 

	<!-- Data picker -->
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>

	<!-- 上传附件相关 --> 
	<script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/tmpl.min.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/load-image.min.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-fp.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-ui.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
	<script	src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script> 
	<script	src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script>
	<script src="${ctx}/js/stickUp.js"></script> 
	<!-- 上传附件 结束 -->
	<!-- 附件保存修改相关 -->
	<script	src="${ctx}/js/trunk/task/attachment.js"></script> 
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
	
    <script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script>

	<script src="${ctx}/transjs/common/caseTaskCheck.js?v=1"></script>
	
	<script src="${ctx}/js/trunk/comment/caseComment.js"></script>
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>	
    <script src="${ctx}/js/viewer/viewer.min.js"></script>
    
    <!-- 改版引入的新的js文件 -->
	<script src="${ctx}/js/common/textarea.js?v=1.0.1"></script>
	<script>
	var source = "${source}";
	function readOnlyForm(){
		
		//设置银行真实放款时间不可修改 
		$("#lendDate").parent().removeClass("input-daterange");
		$("#lendDate").removeClass("datatime");
		$("#lendDate").attr("readonly",true);
		$("#lendDate").css("background-color","#ccc");	
		//设置他证送抵时间不可修改 
		$("#tazhengArrDate").parent().removeClass("input-daterange");
		$("#tazhengArrDate").removeClass("datatime");
		$("#tazhengArrDate").attr("readonly",true);
		$("#tazhengArrDate").css("background-color","#ccc");	
		
		$("select[readOnlydata=1]").closest('.row').hide();
		$("[readOnlydata=1]").attr('readonly',true);
		$("[readOnlydata=1]").each(function(){
			if($(this).is('a')){
				$(this).hide();
			}
		});
		
		//设置提交按钮隐藏
		$("#btnSubmit").hide();
	}

	
	$(document).ready(function() {
		if('caseDetails'==source){
			readOnlyForm();
		}
		
		$('#data_1 .input-group.date').datepicker({
			todayBtn : "linked",
			keyboardNavigation : false,
			forceParse : false,		
			autoclose : true			
		});

		$('#data_1_forBank .input-group.date').datepicker({
			todayBtn : "linked",
			keyboardNavigation : false,
			forceParse : false,		
			autoclose : true,
			endDate : new Date()  //限制选取的结束时间
		});		
		
		$("#caseCommentList").caseCommentGrid({
			caseCode : caseCode,
			srvCode : taskitem
		});
	});
		
		/**提交数据*/
		function submit() {
			if(checkAttachment()) {
				save(true);
			}
		}
		
		/**保存数据*/
		function save(b) {
				if(!checkForm()) {
					return;
				}
					
				if(confirm("请确认银行是否已真实放款")){
					var jsonData = $("#loanReleaseForm").serializeArray();
					deleteAndModify();
					
					var url = "${ctx}/task/mortgage/saveMortgage";
					if(b) {
						url = "${ctx}/task/mortgage/submitLoanRelease";
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
								if(null!=data.message){
									window.wxc.alert(data.message);
								}
							} else {
								window.wxc.success("保存成功。");
								 window.close();
								 window.opener.callback();
							}
						},
						error : function(errors) {
							window.wxc.error("数据保存出错");
						}
					});
			}		

		}
		
		//验证控件checkUI();
		function checkForm() {
			$("input").css("border-color","#ccc");
			
			if(tz && $('input[name=tazhengArrDate]').val()=='' && isSelfCom=='1') {
				window.wxc.alert("他证送抵时间为必填项!");
                $('input[name=tazhengArrDate]').focus();
                $('input[name=tazhengArrDate]').css("border-color","red");
                return false;
           }
			
			if($('input[name=lendDate]').val()=='') {
				window.wxc.alert("银行真实放款时间为必填项!");
                $('input[name=lendDate]').focus();
                $('input[name=lendDate]').css("border-color","red");
                return false;
           }
			/* if($('input[name=commet]').val()=='') {
                alert("备注为必填项!");
                $('input[name=commet]').focus();
                return false;
           } */
			return true;
		}
		
		$("input[type='text']").focus(function(){
			$(this).css("border-color","rgb(204, 204, 204)");
		});
		
		//渲染图片 
		function renderImg(){
			$('.wrapper-content').viewer('destroy');
			$('.wrapper-content').viewer();
		}
	</script> 
	<script src="${ctx}/js/common/common.js?v=1.0.1"></script>
	</content>
</body>


</html>