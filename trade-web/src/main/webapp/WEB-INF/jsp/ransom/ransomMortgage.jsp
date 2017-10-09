<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>

<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="<c:url value='/static/trans/css/eloan/eloan/eloan.css' />"
	rel="stylesheet" />
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">
<link rel="stylesheet"
	href="<c:url value='/static/css/bootstrap.min.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/font-awesome/css/font-awesome.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/iconfont/iconfont.css' />">
<link rel="stylesheet" href="<c:url value='/static/css/animate.css' />">
<link rel="stylesheet" href="<c:url value='/static/css/style.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/css/plugins/aist-steps/steps.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/css/plugins/toastr/toastr.min.css' />">
<!-- stickUp fixed css -->
<link rel="stylesheet"
	href="<c:url value='/static/css/plugins/stickup/stickup.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/stickmenu.css' />">
<!-- index_css  -->
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/input.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/table.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/uplodydome.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/eloan/eloan_detail.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/eloan/eloan_guaranty.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.css' />">
<link rel="stylesheet"
	href="<c:url value='/static/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css' />"
	type="text/css" />
<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"
	rel="stylesheet" />
<link href="<c:url value='/css/transcss/comment/caseComment.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">

<style type="text/css">

<style type="text/css">


.fo{width:100%;}
.matching{float: left;margin:0 40px;padding-top: 5px;}
.form_list .sign_left_small{text-align: left;margin-right: -10px;}
.underline{margin-top: -30px;}

</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/ransom/ransomBaseInfo.jsp"></jsp:include>
	
	<form method="get" id="ransomMortgage" class="form_list">
		<input type="hidden" id="caseCode" name="caseCode" value="${detailVo.caseCode }">
		<input type="hidden" id="ransomCode" name="ransomCode" value="${detailVo.ransomCode }">
		<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId }">
		<input type="hidden" id="taskId" name="taskId" value="${taskId }">
		<input type="hidden" id="diyaType" name="diyaType" value="${diyaType }">
		
		<div class="ibox-content border-bottom clearfix space_box noborder">
			<div class="line">
            	<div class="title">信息录入</div>
            	<hr>
           		<div class="form-row form-rowbot clear">
	           		<div class="row clearfix">
	           			<div class="form_content">
							<label  class="control-label matching"><font color=" red" class="mr5" >*</font>陪同还贷时间(
								<c:if test="${diyaType == 1 }">一</c:if>
								<c:if test="${diyaType == 2 }">二</c:if>抵)</label> 
							<div  class="input-group sign-right  input-daterange"  data-date-format="yyyy-mm-dd"> 
								<input id="mortgageTime" name="mortgageTime" class="form-control input-one date-picker data_style" style="font-size: 13px;width: 200px; border-radius: 2px;" type="text"  placeholder="陪同还贷时间">
							</div>
						</div>
						<div class="form_content">
							<label class="sign_left_two control-label"><font color=" red" class="mr5" >*</font>还贷金额</label>
							<input type="text" id="repayLoanMoney" name="repayLoanMoney" class="select_control yuanwid" style="width: 200px;" onkeyup="checkNum(this)">
							<span class="date_icon">万元</span>
						</div>
					</div>
				</div>
				
				<h4 class="other_reason_title">时间计划</h4>
					<hr>
           			<div class="row clearfix line">
	           			<div class="form_content">
							<label  class="control-label matching"><font color=" red" class="mr5" >*</font>注销抵押时间(
								<c:if test="${diyaType == 1 }">一</c:if>
								<c:if test="${diyaType == 2 }">二</c:if>抵)</label>
								<div  class="input-group sign-right  input-daterange"  data-date-format="yyyy-mm-dd"> 
									<input id="planCancelTime" name="planCancelTime" class="form-control input-one date-picker data_style" style="font-size: 13px;width: 200px; border-radius: 2px;" type="text"  >
								</div>
						</div>
					</div>
					<div class="row clearfix line">
						<div class="form_content">
							<label  class="control-label matching" style="margin-right:68px;"><font color=" red" class="mr5" >*</font>领取产证(
								<c:if test="${diyaType == 1 }">一</c:if>
								<c:if test="${diyaType == 2 }">二</c:if>抵)</label> 
							<div  class="input-group sign-right  input-daterange"  data-date-format="yyyy-mm-dd">
								<input id="planPermitTime" name="planPermitTime" class="form-control input-one date-picker data_style" style="font-size: 13px;width:200px; border-radius: 2px;" type="text"  >
							</div>
						</div>
					</div>	
					<div class="row clearfix line">
						<div class="form_content">
							<label  class="control-label matching" style="margin-right:114px;"><font color=" red" class="mr5" >*</font>回款时间</label> 
							<div  class="input-group sign-right  input-daterange"  data-date-format="yyyy-mm-dd">
								<input id="planPaymentTime" name="planPaymentTime" class="form-control input-one date-picker data_style" style="font-size: 13px;width:200px; border-radius: 2px;" type="text"  >
							</div>
						</div>
					</div>
           	
			</div>
		</div>
	</form> 
	
	<c:if test="${diyaType == 1 }">
		<h4 class="other_reason_title">上传附件（银行还款凭证）</h4>
		<div class="form-row form-rowbot clear">
			<div class="table-box" id="ransomMortgageOnefileUploadContainer"></div>
		</div>
	</c:if>
	
	<div class="ibox-content border-bottom clearfix space_box noborder">
		<div id="caseCommentList" class="add_form"></div>
	</div>
	
	<div class="add_btn text-center mt20">
	   	<div class="more_btn">
		    <button id="submitButton" type="button" class="btn btn_blue">提交</button>
   	    	<button id="closeButton" type="button" class="btn btn_blue">关闭</button>
		</div>
	</div>

	<content tag="local_script"> 
	
	<!-- 上传附件相关 -->
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
	
	<!-- 上传附件 结束 -->

	<script src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script>
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>

	<script src="<c:url value='/transjs/task/showAttachment.js' />"></script>
 	<script src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script> <!-- Data picker -->
	<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>

	<script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<script src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/viewer/viewer.min.js' />"></script> <!-- 改版引入的新的js文件 -->
	<script src="<c:url value='/js/common/textarea.js' />"></script> 
	<script src="<c:url value='/js/common/common.js' />"></script>  
	<script>
	$(document).ready(function(){
		
		//案件跟进,common.js 
		var caseCode = $('#caseCode').val();
		$("#caseCommentList").caseCommentGrid({
			caseCode : caseCode,
			srvCode : null
		});
	})

	//日期初始化
	$('#mortgageTime').datepicker({
		format : 'yyyy-mm-dd',
		weekStart : 1,
		autoclose : true,
		todayBtn : 'linked',
		language : 'zh-CN'
	});
	$('#planCancelTime').datepicker({
		format : 'yyyy-mm-dd',
		weekStart : 1,
		autoclose : true,
		todayBtn : 'linked',
		language : 'zh-CN'
	});
	$('#planPermitTime').datepicker({
		format : 'yyyy-mm-dd',
		weekStart : 1,
		autoclose : true,
		todayBtn : 'linked',
		language : 'zh-CN'
	});
	$('#planPaymentTime').datepicker({
		format : 'yyyy-mm-dd',
		weekStart : 1,
		autoclose : true,
		todayBtn : 'linked',
		language : 'zh-CN'
	});

	//double验证
	function checkNum(obj) {
		//先把非数字的都替换掉，除了数字和.
		obj.value = obj.value.replace(/[^\d.]/g, "");
		//必须保证第一个为数字而不是.
		obj.value = obj.value.replace(/^\./g, "");
		//保证只有出现一个.而没有多个.
		obj.value = obj.value.replace(/\.{2,}/g, ".");
		//保证.只出现一次，而不能出现两次以上
		obj.value = obj.value.replace(".", "$#$").replace(/\./g, "")
				.replace("$#$", ".");
	}
	
	function checkForm(){
		
		if($('#mortgageTime').val() == ''){
			window.wxc.alert("陪同还贷时间为必填项!");
			$('#MortgageTime').focus();
			$('#MortgageTime').css('border-color',"red");
			return;
		}
		if($('#repayLoanMoney').val() == ''){
			window.wxc.alert("还贷金额为必填项!");
			$('#repayLoanMoney').focus();
			$('#repayLoanMoney').css('border-color',"red");
			return;
		}
		if($('#planCancelTime').val() == ''){
			window.wxc.alert("注销抵押计划时间为必填项!");
			$('#planCancelTime').focus();
			$('#planCancelTime').css('border-color',"red");
			return;
		}
		if($('#planPermitTime').val() == ''){
			window.wxc.alert("领取产证计划时间为必填项!");
			$('#planPermitTime').focus();
			$('#planPermitTime').css('border-color',"red");
			return;
		}
		if($('#planPaymentTime').val() == ''){
			window.wxc.alert("回款计划时间为必填项!");
			$('#planPaymentTime').focus();
			$('#planPaymentTime').css('border-color',"red");
			return;
		}

		if($('#diyaType').val() == 1){	
			if ($("#ransomMortgageOnefileUploadContainer li").length == undefined
					|| $("#ransomMortgageOnefileUploadContainer li").length == 0 ) {
				window.wxc.alert("银行还款凭证未上传!");
				return false;
			}
		
		    //验证上传文件是否全部上传
		    var isCompletedUpload = fileUpload.isCompletedUpload();
		
		    if(!isCompletedUpload){
		        window.wxc.alert("银行还款凭证还未全部上传!");
		        return false;
		    }
	    }
		return true;
	}
    
	//关闭
	$('#closeButton').click(function() {
		 window.close();	
	});
	
	//提交
	$('#submitButton').click(function(){
		
		if(!checkForm()){
			return;
		}
		
		var jsonData = $('#ransomMortgage').serializeArray();
		
		var url = "${ctx}/task/ransom/submitMortgage";

		$.ajax({
			cache:true,
			async:false,
			type:"POST",
			url:url,
			data:jsonData,
			dataType:"json",
			success:function(data){
				window.wxc.success("提交成功!",{"wxcOk":function(){
					 window.close();	
				}});
			},
			error : function(errors) {
				window.wxc.error("提交失败!");
			}
		});
	});
	</script> 
</content>

<content tag="local_require">
	<script>
		var fileUpload;
		require(['main'], function() {
			requirejs(['jquery','aistFileUpload'],function($,aistFileUpload){
				fileUpload = aistFileUpload;
				fileUpload.init({
					caseCode : $('#caseCode').val(),
					partCode : "RansomMortgageOne",
					fileUploadContainer : "ransomMortgageOnefileUploadContainer",
					isAllUpdateY:false
				});
			});
		});
	</script>
</content>
</body>
</html>
