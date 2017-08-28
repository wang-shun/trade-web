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
<link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<link rel="stylesheet" href="<c:url value='/js/viewer/viewer.min.css' />" />

<!-- 新调整页面样式 -->
<link href="<c:url value='/css/common/caseDetail.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/input.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">
	<script type="text/javascript">
		var ctx = "${ctx}";
		/**记录附件div变化，%2=0时执行自动上传并清零*/
		var index = 0;
		var taskitem = "${taskitem}";
		var caseCode = "${caseCode}";
		if ("${idList}" != "") {
			var idList = eval("(" + "${idList}" + ")");
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
	<!-- 服务流程 -->
	<div class="row wrapper white-bg new-heading " id="serviceFlow">
		<div class="pl10">
			<h2 class="newtitle-big">
				缴税
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
				<form method="get" class="form-horizontal" id="ratePaymentForm">
					<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode"
						   value="${taskitem}">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode"
						   value="${caseCode}">
					<!-- 流程引擎需要字段 -->
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId"
						   name="processInstanceId" value="${processInstanceId}">
					<%-- 原有数据对应id --%>
					<input type="hidden" id="pkid" name="pkid"
						   value="${houseTransfer.pkid}">
					<%-- 设置审批类型 --%>
					<input type="hidden" id="approveType" name="approveType"
						   value="${approveType }"> <input type="hidden"
														   id="operator" name="operator" value="${operator }">
					<div class="marinfo">
						<div class="line">
							<div class="form_content">
								<label class="control-label sign_left_small select_style mend_select">
									缴税时间<font color=" red" class="mr5" >*</font>
								</label>
								<div class="input-group sign-right dataleft input-daterange pull-left" data-date-format="yyyy-mm-dd">
									<input type="text" class="input_type yuanwid datatime" id="paymentTime"
										   name="paymentTime"
										   value="<fmt:formatDate  value='${ratePayment.paymentTime}' type='both' pattern='yyyy-MM-dd'/>"
										   onfocus="this.blur()">
								</div>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_small">个人所得税<font color=" red" class="mr5" >*</font></label>
								<input type="text" class=" input_type yuanwid" id="personalIncomeTax"
									   name="personalIncomeTax" onkeyup="checkNum(this)"
									   value="<fmt:formatNumber value='${ ratePayment.personalIncomeTax}' type='number' pattern='#0.00' />">
								<span class="date_icon">万元</span>
							</div>
						
						<%-- 
							<div class="form_content">
								<label class="control-label sign_left_small">核税价<font color=" red" class="mr5" >*</font></label>
								<input type="text" class=" input_type yuanwid" id="taxPricing"
									   name="taxPricing" onkeyup="checkNum(this)"
									   value="<fmt:formatNumber value='${ ratePayment.taxPricing}' type='number' pattern='#0.00' />">
								<span class="date_icon">万元</span>
							</div> --%>
							<div class="form_content">
								<label class="control-label sign_left_small">卖方增值税<font color=" red" class="mr5" >*</font></label>
								<input type="text" class=" input_type yuanwid" id="businessTax"
									   name="businessTax" onkeyup="checkNum(this)"
									   value="<fmt:formatNumber value='${ ratePayment.businessTax}' type='number' pattern='#0.00' />">
								<span class="date_icon">万元</span>
							</div>
							
							</div>
							<div class="line">
							<div class="form_content">
								<label class="control-label sign_left_small">买方契税<font color=" red" class="mr5" >*</font></label>
								<input type="text" class=" input_type yuanwid" id="contractTax"
									   name="contractTax" onkeyup="checkNum(this)"
									   value="<fmt:formatNumber value='${ ratePayment.contractTax}' type='number' pattern='#0.00' />">
								<span class="date_icon">万元</span>
							</div>
							<div class="form_content">
								<label class="control-label sign_left_small">土地增值税<font color=" red" class="mr5" >*</font></label>
								<input type="text" class=" input_type yuanwid" id="landIncrementTax"
									   name="landIncrementTax" onkeyup="checkNum(this)"
									   value="<fmt:formatNumber value='${ ratePayment.landIncrementTax}' type='number' pattern='#0.00' />">
								<span class="date_icon">万元</span>
							</div>
							
							</div>
							<div class="line">
							<div class="form_content">
									<label class="control-label sign_left_small">备注</label>
									<input class=" input_type yuanwid" name="comment" id="comment" placeholder="" value="${ratePayment.comment }"  type="text" style="width:800px;"/></div>
										   </div>
					</div>
				</form>
			</div>
		</div>
		
		
		<!-- 案件跟进 -->
		<div class="view-content" id="caseCommentList"> </div>

		<div class="form-btn">
			<div class="text-center">
				<a href="#" class="btn btn-success btn-space" onclick="save(false)">保存</a>
				<a href="#" class="btn btn-success btn-space" onclick="submit()" readOnlydata="1">提交</a>
			</div>
		</div>

	</div>
</div>
<content tag="local_script">
	<!-- Peity -->
	<script src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script>
	<!-- jqGrid -->
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	<!-- Custom and plugin javascript -->
	<script src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script>
	<!-- Data picker -->
	<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	
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
	<script src="<c:url value='/js/stickUp.js' />"></script>
	<!-- 上传附件 结束 -->
	<!-- 附件保存修改相关 -->
	<script src="<c:url value='/js/trunk/task/attachment3.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<script src="<c:url value='/transjs/sms/sms.js' />"></script>
	<script src="<c:url value='/transjs/common/caseTaskCheck.js' />"></script>
	<!-- 审批记录 -->
	<script src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/viewer/viewer.min.js' />"></script>

	<!-- 改版引入的新的js文件 -->
	<script src="<c:url value='/js/common/textarea.js' />"></script>
	<script src="<c:url value='/js/common/common.js' />"></script>

	<!-- 必须JS -->
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<script>
	var source = "${source}";

	$(document).ready(function() {
		if('caseDetails'==source){
			readOnlyForm();
		}
		
		//渲染案件备注信息
		$("#caseCommentList").caseCommentGrid({
			caseCode : caseCode,
			srvCode : taskitem
		});		
});
	
	//提交数据
	function submit() {
		if(checkAttachment()) {
			save(true);
		}
	}

	//保存数据
	function save(b) {
		if(!checkForm()) {
			return;
		}
		var jsonData = $("#ratePaymentForm").serializeArray();
		deleteAndModify();
		
		var url = "${ctx}/task/ratePayment/saveRatePayment";
		if(b) {
			url = "${ctx}/task/ratePayment/submitRatePayment";
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
					if(null!=data.message){
						window.wxc.alert(data.message);
					}
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
	
	//double 验证
    function checkNum(obj) { 
        //先把非数字的都替换掉，除了数字和.
        obj.value = obj.value.replace(/[^\d.]/g,"");
        //必须保证第一个为数字而不是.
        obj.value = obj.value.replace(/^\./g,"");
        //保证只有出现一个.而没有多个.
        obj.value = obj.value.replace(/\.{2,}/g,".");
        //保证.只出现一次，而不能出现两次以上
        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
     }  
	
	//验证控件checkUI();
	function checkForm() {
			$("input").css("border-color","#ccc");

			if ($('input[name=paymentTime]').val() == '') {
				window.wxc.alert("缴税时间为必填项!");
				$('input[name=paymentTime]').focus();
				$('input[name=paymentTime]').css("border-color","red");
				return false;
			}
			if ($('input[name=taxPricing]').val() == '') {
				window.wxc.alert("核税价为必填项!");
				$('input[name=taxPricing]').focus();
				$('input[name=taxPricing]').css("border-color","red");
				return false;
			}
			/* if($('input[name=commet]').val()=='') {
			 alert("备注为必填项!");
			 $('input[name=commet]').focus();
			 return false;
			 } */
			if ($('input[name=personalIncomeTax]').val() == '') {
				window.wxc.alert("个人所得税为必填项!");
				$('input[name=personalIncomeTax]').focus();
				$('input[name=personalIncomeTax]').css("border-color","red");

				return false;
			}
			if ($('input[name=businessTax]').val() == '') {
				window.wxc.alert("卖方增值税为必填项!");
				$('input[name=businessTax]').focus();
				$('input[name=businessTax]').css("border-color","red");
				return false;
			}
			if ($('input[name=contractTax]').val() == '') {
				window.wxc.alert("买方契税为必填项!");
				$('input[name=contractTax]').focus();
				$('input[name=contractTax]').css("border-color","red");

				return false;
			}
			if ($('input[name=landIncrementTax]').val() == '') {
				window.wxc.alert("土地增值税为必填项!");
				$('input[name=landIncrementTax]').focus();
				$('input[name=landIncrementTax]').css("border-color","red");
				return false;
			}
			return true;
		}
	
	//渲染图片 
	function renderImg(){
		$('.wrapper-content').viewer('destroy');
		$('.wrapper-content').viewer();
	}
	
	function readOnlyForm(){
		//设置核价时间不可修改
		$("#pricingTime").parent().removeClass("input-daterange");
		$("#pricingTime").removeClass("datatime");
		$("#pricingTime").attr("readonly",true);
		$("#pricingTime").css("background-color","#ccc");
		
		//设置提交按钮隐藏
		$("#btnSubmit").hide();
	}
	</script>
</content>

</body>
</html>