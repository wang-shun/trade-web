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
<title>赎楼修改</title>
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
</head>
<body>
<div class="wrapper wrapper-content animated fadeInUp">
	<form method="post" class="form-horizontal" >
		<div class="ibox-content" id="reportOne">
			<h4 class="title">客户需求修改</h4>
			<div class="form_list">
				<div class="marinfo">
					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small">
							<font color=" red" class="mr5">*</font>主贷人姓名</label> 
							<input type="text" class="input_type yuanwid" id="personalIncomeTax"
								name="personalIncomeTax" onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${ transSign.personalIncomeTax}' type='number' pattern='#0.00' />">
						</div>
						<div class="form_content">
							<label class="control-label sign_left_small"><font
								color=" red" class="mr5">*</font>电话</label> <input type="text"
								class="input_type yuanwid" id="sellerTax" name="sellerTax"
								onkeyup="checkNum(this)" value="<fmt:formatNumber value='${ transSign.sellerTax}' type='number' pattern='#0.00' />">
						</div>
						<div class="form_content">
							<label class="control-label sign_left_small">
								<font color=" red" class="mr5">*</font>受理时间 </label> 
								<input type="text" class="input_type yuanwid" id="buyerTax" name="buyerTax"
								onkeyup="checkNum(this)" value="<fmt:formatNumber value='${ transSign.buyerTax}' type='number' pattern='#0.00' />">
						</div>
					</div>
					<div class="line">
						<div class="form_content">
							<table class="table table_blue table-striped table-bordered table-hover ">
								<thead>
									<tr>
										<td>尾款机构</td>
										<td>尾款类型</td>
										<td>抵押类型</td>
										<td>贷款金额</td>
										<td>剩余金额</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											<select name="loanLostFinOrgName" id="loanLostFinOrgName" class="teamcode select_control "></select>
										</td>
										<td>
											<aist:dict id="mortType" name="mortType" clazz=" select_control yuanwid " display="select" dictType="30016" defaultvalue="${toMortgage.mortType }" />
										</td>
										<td>
											<aist:dict id="mortType" name="mortType" clazz=" select_control yuanwid " display="select" dictType="30016" defaultvalue="${toMortgage.mortType }" />
										</td>
										<td>
											<input name="" value="" type="text" class="form-control input-one" placeholder=""> 
									    </td>
										<td>
											<input name="" value="" type="text" class="form-control input-one" placeholder=""> 
										</td>
									</tr>
								</tbody>
								<input type="hidden" id="addInput" />
							</table>
							<div class="form_content">
								<label class="control-label sign_left_small">借款金额总计</label> 
								<input type="text" class="input_type yuanwid" id="personalIncomeTax"
									name="personalIncomeTax" onkeyup="checkNum(this)"
									value="<fmt:formatNumber value='${ transSign.personalIncomeTax}' type='number' pattern='#0.00' />">
								<span>万</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		
			<h4 class="title">作业信息修改</h4>
			<hr>
			<table class="table table_blue table-striped table-bordered table-hover ">
				<tr>
					<td>已完成环节</td>
					<td>完成日期</td>
					<td>执行人</td>
					<td>操作</td>
				</tr>
				<tr>
					<td>申请</td>
					<td>2017-01-01</td>
					<td>小王</td>
					<td><a href="javascript:void(0)">前往修改</a></td>
				</tr>
				<tr>
					<td>面签</td>
					<td>2017-01-01</td>
					<td>小王</td>
					<td><a href="javascript:void(0)">前往修改</a></td>
				</tr>
			</table>
			<div>
				<div class="text-center">
					<a class='btn btn-primary ' href="javascript:void(0)" id="save">保存</a>
					<a class='btn btn-primary ' href="javascript:void(0)" id="close">关闭</a>
				</div>
			</div>
		</div>
	</form>
	<%-- <script src="${ctx}/static/js/jquery-1.10.1.min.js" type="text/javascript"> --%>
	<script src="<c:url value='/js/jquery-1.10.1.min.js' />"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		
		var ctx = "${ctx}";
		$('#save').click(function() {
			window.location.href = ctx + "/ransomList/ransom/ransomDetail";
		});
		$("#close").click(function(){
			if (confirm("您确定要关闭本页吗？")) { 
				window.open('', '_self').close(); 
			}
		});
	});
	</script>
</body>
</html>