<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">

<title>赎楼面签</title>

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


.fo{width:100%;}
.matching{float: left;margin:0 40px;padding-top: 5px;}
.form_list .sign_left_small{text-align: left;margin-right: -10px;}
.underline{margin-top: -30px;}

</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/ransom/ransomBaseInfo.jsp"></jsp:include>

	<form method="get" id="ransomSign" class="form_list">
		<input type="hidden" id="caseCode" name="caseCode" value="${detailVo.caseCode }">
		<input type="hidden" id="ransomCode" name="ransomCode" value="${detailVo.ransomCode }">
		<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId }">
		<input type="hidden" id="taskId" name="taskId" value="${taskId }">

		<div class="row">
			<div class="wrapper wrapper-content animated fadeInUp">
				<div class="ibox-content" id="base_info">
					<div class="title">信息录入</div>
					<hr>
						<div class="row clearfix line">
			    			<div class="form_content">
								<label  class="control-label matching"><font color=" red" class="mr5" >*</font>实际面签时间</label>
								<div  class="input-group sign-right  input-daterange"  data-date-format="yyyy-mm-dd"> 
									<input id="signTime" name="signTime" class="form-control input-one date-picker data_style" style="font-size: 13px;width: 200px; border-radius: 2px;" type="text" >
								</div>
							</div>
							<div class="form_content">
								<label  class="control-label matching"><font color=" red" class="mr5" >*</font>计划陪同还贷时间</label> 
								<div  class="input-group sign-right  input-daterange"  data-date-format="yyyy-mm-dd">
									<input id="planMortgageTime" name="planMortgageTime" class="form-control input-one date-picker data_style" style="font-size: 13px;width: 200px; border-radius: 2px;" type="text"  >
								</div>
							</div>
						</div>
						<div class="row clearfix line">
							<div class="form_content">
								<label class=" control-label" style="margin-left:40px;"><font color=" red" class="mr5" >*</font>面签金额</label>
								<input type="text" id="signMoney" name="signMoney" class="select_control yuanwid" style="width:200px;margin-left:63px;" onkeyup="checkNum(this)">
								<span class="date_icon">万元</span>
							</div>
							<div class="form_content">
								<label class=" control-label" style="margin-left:42px;"><font color=" red" class="mr5" >*</font>价格</label>
								<input type="text" id="interest" name="interest" class="select_control yuanwid" style="width:130px;margin-left:120px;" onkeyup="checkNum(this)">&nbsp; ‰。每天
							</div>
						</div>
						<div class="row clearfix">
							<div class="form_content">
								<label class=" control-label" style="margin-left:50px;">是否需要委托公证</label>
								<div class="controls ">
                                	<input type="radio" checked="checked" value="1"  name="isEntrust">是
                                	<input type="radio" value="0"  name="isEntrust" style="margin-left:40px;">否
                            </div>
							</div>
						</div>
				</div>
			</div>
		</div>
	</form>
	
	<div class="ibox-content border-bottom clearfix space_box noborder">
		<div id="caseCommentList" class="add_form"></div>
	</div>
	<div class="add_btn text-center mt20">
	   	<div class="more_btn">
		    <button id="submitButton" type="button" class="btn btn_blue">提交</button>
   	    	<button id="closeButton" type="button" class="btn btn_blue">关闭</button>
		</div>
	</div>
	
	
	<!-- main End -->
<content tag="local_script"> 
	<script src="<c:url value='/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.js' />"></script>
	<script src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
	<script src="<c:url value='/static/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/static/js/plugins/stickup/stickUp.js' />"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/common/textarea.js' />"></script>
	<script >
		$(document).ready(function(){
			
			//案件跟进,common.js 
			var caseCode = $('#caseCode').val();
			$("#caseCommentList").caseCommentGrid({
				caseCode : caseCode,
				srvCode : null
			});
		})
	
		//日期初始化
		$('#signTime').datepicker({
			format : 'yyyy-mm-dd',
			weekStart : 1,
			autoclose : true,
			todayBtn : 'linked',
			language : 'zh-CN'
		});
		$('#planMortgageTime').datepicker({
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
		
		//关闭
		$('#closeButton').click(function() {
			 window.close();	
		});
		
		//提交
		$('#submitButton').click(function(){
			if($('#signTime').val() == ''){
				window.wxc.alert("实际面签时间为必填项!");
				$('#signTime').focus();
				$('#signTime').css('border-color',"red");
				return;
			}
			if($('#planMortgageTime').val() == ''){
				window.wxc.alert("计划陪同还贷时间为必填项!");
				$('#planMortgageTime').focus();
				$('#planMortgageTime').css('border-color',"red");
				return;
			}
			
			if($('#signMoney').val() == ''){
				window.wxc.alert("面签金额为必填项!");
				$('#signMoney').focus();
				$('#signMoney').css('border-color',"red");
				return;
			}
			if($('#interest').val() == ''){	
				window.wxc.alert("价格为必填项!");
				$('#interest').focus();
				$('#interest').css('border-color',"red");
				return;
			}
			
			var jsonData = $('#ransomSign').serializeArray();
			
			var url = "${ctx}/task/ransom/submiSign";
	
			$.ajax({
				cache:true,
				async:false,
				type:"POST",
				url:url,
				data:jsonData,
				dataType:"json",
				success:function(data){
					if(data){
						window.wxc.success("提交成功!",{"wxcOk":function(){
							 window.close();	
						}});
					}else{
						window.wxc.error("提交失败!");
					}
				},
				error : function(errors) {
					window.wxc.error("提交失败!");
				}
			});
		});
	
	</script>
	</content> 
</body>
</html>



