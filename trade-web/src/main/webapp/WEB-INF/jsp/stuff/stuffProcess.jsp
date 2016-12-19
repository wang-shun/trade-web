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
	<div class="">
		<div class=" wrapper border-bottom white-bg page-heading">
			<div class="row">
			<div class="col-lg-10">
				<h2>补件</h2>
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
			<div class="ibox-content form_content">
				<form method="get" class="form-horizontal" id="loanReleaseForm">
					<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
					<input type="hidden" id="taskitem" name="taskitem" value="${taskitem}">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
					<input type="hidden" id="caseComment_parentId" name="caseComment_parentId" value="${pComment.pkid }">
					<input type="hidden" id="caseComment_commentSource" name="caseComment_commentSource" value="INTER">
					<input type="hidden" id="caseComment_commentType" name="caseComment_commentType" value="TRACK">
					<input type="hidden" id="caseComment_bizCode" name="caseComment_bizCode" value="${pComment.bizCode }">
					<!-- 流程引擎需要字段 -->
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
				
					<div class="row">
						<div class="control-label sign_left_small" >
							<label class="col-md-2">发起人 </label>
							<div class="col-md-10 text-left ">${pComment.createByShow}</div>
						</div>
					</div>
					<div class="row">
						<div class="control-label sign_left_small" >
							<label class="col-md-2">发起机构 </label>
							<div class="col-md-10 text-left ">${pComment.creatorOrgIdShow}</div>
						</div>
					</div>

				</form>

			</div>
		</div>

		<!-- 案件备注信息 -->
		<div id="caseCommentList" class="add_form">
		</div>
	
		<div class="ibox-title">
			<a href="#" class="btn btn-primary" onclick="submit()" readOnlydata="1">提交</a>
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
	<!-- 上传附件 结束 -->
	<!-- 附件保存修改相关 -->
	<script	src="${ctx}/js/trunk/task/attachment.js"></script> 
	<script src="${ctx}/js/jquery.blockui.min.js"></script>
	
    <script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script>

	<script src="${ctx}/transjs/common/caseTaskCheck.js?v=1"></script>
	
	<script src="${ctx}/js/trunk/comment/caseComment.js?v=1.0.3"></script>
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>	
    <script src="${ctx}/js/viewer/viewer.min.js"></script>
	<script>
	var source = "${source}";
	function readOnlyForm(){
		$(".readOnly_date").removeClass('date');
		$(".readOnly_date input").attr('readOnly',true);
		$("select[readOnlydata=1]").closest('.row').hide();
		$("[readOnlydata=1]").attr('readonly',true);
		$("[readOnlydata=1]").each(function(){
			if($(this).is('a')){
				$(this).hide();
			}
		});
	}

	
	$(document).ready(function() {
		
		$("#caseCommentList").caseCommentGrid({
			caseCode : caseCode,
			srvCode : taskitem
		});
	});
		
		/**提交数据*/
		function submit() {
				save(true);
		}
		
		/**保存数据*/
		function save(b) {

					var jsonData = $("#loanReleaseForm").serializeArray();

					
					var url = "${ctx}/stuff/submit";
					
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
									alert(data.message);
								}
							} else {
								alert("保存成功。");
								 window.close();
								 window.opener.callback();
							}
						},
						error : function(errors) {
							alert("数据保存出错");
							$.unblockUI();  
						}
					});
		}
		
		//验证控件checkUI();
		function checkForm() {
			if($('input[name=lendDate]').val()=='') {
                alert("放款时间为必填项!");
                $('input[name=lendDate]').focus();
                return false;
           }
			if(tz && $('input[name=tazhengArrDate]').val()=='' && isSelfCom=='1') {
                alert("它证送抵时间为必填项!");
                $('input[name=tazhengArrDate]').focus();
                return false;
           }
			/* if($('input[name=commet]').val()=='') {
                alert("备注为必填项!");
                $('input[name=commet]').focus();
                return false;
           } */
			return true;
		}
		
		//渲染图片 
		function renderImg(){
			$('.wrapper-content').viewer('destroy');
			$('.wrapper-content').viewer();
		}
	</script> 
	</content>
</body>


</html>