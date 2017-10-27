<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>

<html>
<head>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<!-- 上传相关 -->
	<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fancybox.css' />" rel="stylesheet">
	<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fileupload-ui.css' />" rel="stylesheet">
	<link href="<c:url value='/css/trunk/JSPFileUpload/select2_metro.css' />" rel="stylesheet">
	<!-- 展示相关 -->
	<link href="<c:url value='/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css' />" rel="stylesheet">
	<link href="<c:url value='/css/trunk/JSPFileUpload/bootstrap-tokenfield.css' />" rel="stylesheet">
	<link href="<c:url value='/css/trunk/JSPFileUpload/selectize.default.css' />" rel="stylesheet">
	<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
	<!-- 备件相关结束 -->
	<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
	<!-- jdGrid相关 -->
	<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
	<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
	<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
	<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
	<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
	<link href="<c:url value='/css/common/common.css' />" rel="stylesheet">
	<link href="<c:url value='/css/style.css' />" rel="stylesheet">
	<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
	<!-- 新调整页面样式 -->
	<link href="<c:url value='/css/common/caseDetail.css' />" rel="stylesheet">
	<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
	<link href="<c:url value='/css/iconfont/iconfont.css' />" rel="stylesheet">
	<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
	<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
	<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">
	<link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
	<link rel="stylesheet" href="<c:url value='/js/viewer/viewer.min.css' />" />
	<style type='text/css'>
		.divider {position: relative}
		.divider label {position: absolute;left: 0;top: -17px}
	</style>
		<script type="text/javascript">
		var ctx = "${ctx}";
		var taskitem = "${taskitem}";
		var caseCode = "${caseCode}";
		var source = "${source}";
		var finishYear = "${transSign.finishYear}";
		if ("${idList}" != "") {
			var idList = eval("(" + "${idList}" + ")");
		} else {
			var idList = [];
		}
	</script>
	<script>	
	//提交数据
	function submit() {	
		var pkid=$("#pkid").val();
		if(!pkid){
		 window.wxc.alert("请以正确的方式进入该页面!");
		return;		
		}
		save(true);
	}

	//保存数据
	function save(b) {	
		if(b){
			if (!checkForm()) {
				return;
			}													
		} 
		var jsonData={};
		var pkid=$('#pkid').val();
		var billTime=$('#billTime').val();
		var taskId=$('#taskId').val();
		var processInstanceId=$('#processInstanceId').val();
		var caseCode=$('#caseCode').val();
		var evaPointer=$('#evaPointer').val();
		
		jsonData.pkid=pkid;
		jsonData.billTime=billTime;
		jsonData.taskId=taskId;
		jsonData.processInstanceId=processInstanceId;
		jsonData.caseCode=caseCode;
		jsonData.evaPointer=evaPointer;
		
		var url = "${ctx}/eval/submitIssueInvoice";
		
		$.ajax({
	        cache : true,
	        async : false,
	        type : "POST",
	        url : url,
	        dataType : "json",
	        data : jsonData,
	        beforeSend : function() {
	            $.blockUI({
	                message : $("#salesLoading"),
	                css : {
	                    'border' : 'none',
	                    'z-index' : '9999'
	                }
	            });
	            $(".blockOverlay").css({
	                'z-index' : '9998'
	            });
	        },
	        success: function(data){
	        	$.unblockUI();	
				if(!data.success){
					 window.wxc.alert(data.message);						 
					 }else{					 
					 window.wxc.success("保存成功。",{"wxcOk":function(){
	                     window.close();
	                     window.opener.callback();
	                 }});
					 }
	        },
	        error:function(){
	        	window.wxc.alert("提交信息出错。。");
	        }
	    });
	}
	
	//验证控件checkUI();
	function checkForm() {
		if(!$("#billTime").val()){
			window.wxc.alert("开具发票时间为必填项!");
			$("#billTime").focus();
			$("#billTime").css("border-color","red");
			return false;
		}	
			return true;
		}

		$("input[type='text'],select").focus(function() {
			$(this).css("border-color", "rgb(229, 230, 231)");
		});
	</script>
	<title>开具评估发票</title>
<content tag="pagetitle">开具评估发票</content>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
<nav id="navbar-example" class="navbar navbar-default navbar-static"
		role="navigation">
		<div id="isFixed" style="position: relative; top: 0px;"
			class="collapse navbar-collapse bs-js-navbar-scrollspy stuckMenu stickup-nav-bar scroll_nav">
			<ul class="nav navbar-nav scroll_content">
				<li class="menuItem active"><a href="#basicInfo"> 基本信息 </a></li>
				<li class="menuItem"> <a href="#serviceFlow"> 服务流程  </a></li>
				<li class="menuItem"> <a href="#aboutInfo"> 相关信息 </a> </li>

			</ul>
		</div>
	</nav>

 <div class="wrapper wrapper-content" id="basicInfo">
		<div class="row animated fadeInDown">
			<div class="scroll_box fadeInDown animated">
				<div class="top12 panel">
			       	<c:if test="${caseBaseVO.loanType=='30004005'}">
			          	<div class="sign sign-yellow">税费卡</div>
			        </c:if>
					<c:if test="${caseBaseVO.toCase.caseProperty=='30003001'}">
						<div class="sign sign-red" ><span
								<c:if test="${toApproveRecord!=''}">
									class="hint hint-top" data-hint="${toApproveRecord}"
								</c:if> >无效</span></div>
					</c:if>
					<c:if test="${caseBaseVO.toCase.caseProperty=='30003002'}">
						<div class="sign sign-red">结案</div>
					</c:if>
					<c:if test="${caseBaseVO.toCase.caseProperty=='30003005'}">
						<div class="sign sign-red ">
                  		<span
								<c:if test="${toApproveRecord!=''}">
									class="hint hint-top" data-hint="${toApproveRecord}"
								</c:if> >爆单</span>

						</div>
					</c:if>
					<c:if test="${caseBaseVO.toCase.caseProperty=='30003004'}">
						<div class="sign sign-red">挂起</div>
						<div class="sign sign-blue">
							<c:if test="${caseBaseVO.toCase.status=='30001001'}">
								未分单
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001002'}">
								已分单
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001003'}">
								已签约
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001004'}">
								已过户
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001005'}">
								已领证
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001006'}">
								未指定
							</c:if>
						</div>
					</c:if>
					<c:if test="${toCase.caseProperty=='30003006'}">
						<div class="sign sign-red">全部</div>
						<div class="sign sign-blue">
							<c:if test="${caseBaseVO.toCase.status=='30001001'}">
								未分单
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001002'}">
								已分单
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001003'}">
								已签约
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001004'}">
								已过户
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001005'}">
								已领证
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001006'}">
								未指定
							</c:if>
						</div>
					</c:if>
					<c:if test="${caseBaseVO.toCase.caseProperty=='30003003'}">
						<div class="sign sign-red">在途</div>
						<div class="sign sign-blue">
							<c:if test="${caseBaseVO.toCase.status=='30001001'}">
								未分单
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001002'}">
								已分单
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001003'}">
								已签约
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001004'}">
								已过户
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001005'}">
								已领证
							</c:if>
							<c:if test="${caseBaseVO.toCase.status=='30001006'}">
								未指定
							</c:if>
						</div>
					</c:if>

			       <div class="panel-body">
						<div class="ibox-content-head">
							<h5>案件基本信息</h5>
							<small class="pull-right">誉萃编号：${caseBaseVO.toCase.caseCode}｜中原编号：${caseBaseVO.toCase.ctmCode}</small>
						</div> 
						
						<div id="infoDiv infos" class="row">
							<div class="ibox white_bg">
								
							<!-- 调佣信息 -->
								<div  class="info_box info_box_two col-sm-12" >
									<span>开票申请信息</span>
									<div  class="ibox-conn else_conn_two">
										<table style="width:100%">
											<tr style="height:30px">
												<td>发票种类：</td>
												<td>${toEvaInvoice.invoiceType}</td>
												<td>申请日期：</td>
												<td>
												<fmt:formatDate value="${toEvaInvoice.applyDate}" type="both" pattern="yyyy-MM-dd"/>
												</td>
												<td>开票抬头：</td>
												<td>${toEvaInvoice.invoiceHeader}</td>
											</tr>
											
											<tr style="height:30px">
												<td>发票金额：</td>
												<td>${toEvaInvoice.invoiceAmount}</td>
												<td>开户银行：</td>
												<td>${toEvaInvoice.openAccountBank}</td>
												<td>银行账户：</td>
												<td>${toEvaInvoice.bankAccount}</td>
											</tr>
											
											<tr style="height:30px">
												<td>开票地址：</td>
												<td>${toEvaInvoice.invoiceAddress}</td>
												<td>税号:</td>
												<td>${toEvaInvoice.taxNum}</td>
												<td></td>
												<td></td>
											</tr>
									</table>
									</div>
								</div>
								
							</div>
							</div>                      
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
<div class="marginbot">
	<!-- 服务流程 -->
	<div class="panel " id="serviceFlow">
		<div class="wrapper white-bg new-heading">
			<div class="pl10">
				<h2 class="newtitle-big">开具发票</h2>
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

		<div class="ibox-content border-bottom clearfix space_box noborder">
						<form method="get" class="form_list" id="issueInvoiceform"
							style="overflow: visible;">
							<input type="hidden" name="pkid" id="pkid" value="${toEvaInvoice.pkid}"> 
							<input type="hidden" name="evaPointer" id="evaPointer" value="${toEvaInvoice.evaPointer}"> 
							<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
							<input type="hidden" id="evaCode" name="evaCode" value="${toEvaInvoice.evaCode}">
							<!-- 流程引擎需要字段 -->
							<input type="hidden" id="taskId" name="taskId" value="${taskId }">
							<input type="hidden" id="processInstanceId"
								name="processInstanceId" value="${processInstanceId}">
							<h2 class="newtitle title-mark">填写开票任务信息</h2>
							<div style="padding-left: 10px">
								<div class="line">

									<div class="form_content mt3">
										<label
											class="control-label sign_left_small select_style mend_select">
											<font color=" red" class="mr5">*</font>开具发票时间：
										</label>
										<div
											class="input-group sign-right dataleft input-daterange pull-left"
											id="estFinishTime" data-date-format="yyyy-mm-dd">
											<input type="text" class="input_type yuanwid datatime"
												id="billTime" name="billTime" onfocus="this.blur()"
												value="<fmt:formatDate  value='${toEvaInvoice.billTime}' type='both' pattern='yyyy-MM-dd'/>">
										</div>
									</div>
								</div>
							</div>
						</form>


						<div class="ibox-title" style="height: auto;border:0;padding-left:0;" id="aboutInfo">
			<h5 class="title-mark">
							上传备件</h5><br>
						<div class="table-box" id="transSignfileUploadContainer"></div>				
			</div>
			<div class="form-btn clear pt20">
				<div class="text-center">
					<button  class="btn btn-success btn-space" onclick="javascript:window.close()">关闭</button>
					<button class="btn btn-success btn-space" onclick="submit()" id="btnSubmit">提交</button>
				</div>
			</div>
		</div>
	</div>
</div>
</div>

<content tag="local_script">
<script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
	<script src="<c:url value='/js/trunk/case/caseBaseInfo.js' />"></script>
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<script src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	<script src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script>
	<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/app.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.ui.widget.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/tmpl.min.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/load-image.min.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-fp.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.fileupload-ui.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/clockface.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jquery.multi-select.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/form-fileupload.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/aist.upload.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jssor.js' />"></script>
	<script src="<c:url value='/js/trunk/JSPFileUpload/jssor.slider.js' />"></script>
	<script src="<c:url value='/js/stickUp.js' />"></script>
	<script src="<c:url value='/js/trunk/task/attachment3.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/common/additional-methods.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/common/messages_zh.js' />"></script>
	<script src="<c:url value='/js/trunk/task/taskTransSign.validate.js' />"></script>
	<script src="<c:url value='/js/plugins/layer/layer.js' />"></script>
	<script src="<c:url value='/js/plugins/layer/extend/layer.ext.js' />"></script>
	<script src="<c:url value='/transjs/sms/sms.js' />"></script>
	<script src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script>
	<script type="text/javascript" src="<c:url value='/js/jquery.json.min.js' />"></script>
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/viewer/viewer.min.js' />"></script>
	<!-- 改版引入的新的js文件 -->
	<script src="<c:url value='/js/common/textarea.js' />"></script>
	<script src="<c:url value='/js/common/common.js' />"></script>
	<script>
	$(document).ready(function(){
		var ctx = $("#ctx").val();
		var caseCode=$("#caseCode").val();
	//日期组件
       $('#issueInvoiceTime').datepicker({
           todayBtn: "linked",
           keyboardNavigation: false,
           forceParse: false,
           calendarWeeks: false,
           autoclose: true
       });	            
	})//end ready function
	</script>
</content>
<content tag="local_require">
	<script>
	var caseCode=$("#caseCode").val();
	var evaCode=$("#evaCode").val();
	var fileUpload;
	require(['main'], function() {
		requirejs(['jquery','aistFileUpload'],function($,aistFileUpload){
			fileUpload = aistFileUpload;

			fileUpload.init({
				caseCode : caseCode,
				bizCode : evaCode,
				partCode : "eval_invoice_manage",
				fileUploadContainer : "transSignfileUploadContainer"
			});
		});
	});
	</script>
</content>
</body>

</html>