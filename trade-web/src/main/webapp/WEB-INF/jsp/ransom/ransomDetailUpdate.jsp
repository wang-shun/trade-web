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
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<input type="hidden" value="${tailinsVo.caseCode }" id="caseCode">
<input type="hidden" value="${tailinsVo.ransomCode }" id="ransomCode">
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
							<select class="input_type yuanwid" id="borrowerName">
								<option value="">请选择</option>
								<option value="${guestInfo.get(0).guestName }">${guestInfo.get(0).guestName }</option>
								<option value="${guestInfo.get(1).guestName }">${guestInfo.get(1).guestName }</option>
							</select>
						</div>
						<div class="form_content">
							<label class="control-label sign_left_small"><font color=" red" class="mr5">*</font>电话</label> 
							<input type="text" class="input_type yuanwid" id="borrowerPhone" name="borrowerPhone" value="${guestInfo.get(1).guestPhone }"/>
						</div>
						<div class="form_content">
							<label class="control-label sign_left_small"><font color=" red" class="mr5">*</font>受理时间 </label> 
								<input type="text" class="input_type yuanwid" id="signTime" name="signTime" value="<fmt:formatDate value='${tailinsVo.signTime }' pattern='yyyy-MM-dd'/>" />
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
										<aist:dict id="finOrgCode" name="finOrgCode" clazz=" select_control yuanwid " display="select" dictType="FINAL_ORG" defaultvalue="${tailinsVo.finOrgCode }" />
										</td>
										<td>
											<aist:dict id="mortgageType" name="mortgageType" clazz=" select_control yuanwid " display="select" dictType="30016" defaultvalue="${tailinsVo.mortgageType }" />
										</td>
										<td>
											<aist:dict id="diyaType" name="diyaType" clazz=" select_control yuanwid " display="select" dictType="71015" defaultvalue="${tailinsVo.diyaType }" />
										</td>
										<td>
											<input id="loanMoney" name="loanMoney" type="text" class="form-control input-one" placeholder="单位：万元"  value="${ tailinsVo.loanMoney}"> 万
									    </td>
										<td>
											<input id="restMoney" name="restMoney" type="text" class="form-control input-one" placeholder="单位：万元" value="${tailinsVo.restMoney}"> 万
										</td>
									</tr>
								</tbody>
								<input type="hidden" id="addInput" />
							</table>
							<div class="form_content">
								<label class="control-label sign_left_small">借款金额总计</label> 
								<input type="text" class="input_type yuanwid" id="borrowerMoney"
									name="borrowerMoney" 
									value="<fmt:formatNumber value='${ caseVo.borroMoney}' type='number' pattern='#0.00' />">
								<span>万</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
			<h4 class="title">作业信息修改</h4>
			<hr>
			<table class="table table_blue table-striped table-bordered table-hover ">
				<thead>
					<tr>
						<td>已完成环节</td>
						<td>完成日期</td>
						<td>执行人</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody id="work-info"></tbody>
			</table>
			<div>
				<div class="text-center">
					<a class='btn btn-primary ' href="javascript:void(0)" id="save">保存</a>
					<a class='btn btn-primary ' href="javascript:void(0)" id="close">关闭</a>
				</div>
			</div>
		</div>
	<script src="<c:url value='/js/trunk/report/dealChangeList.js' />"></script> 
	<script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
	<script	type="text/javascript" src="<c:url value='/js/jquery.json.min.js' />"></script>
	<script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
	<script src="<c:url value='/js/ransom/ransomDetailUpdate.js'/>" type="text/javascript"></script>
	<script>
    $(document).ready(function () {
        $('.input-daterange').datepicker({
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true
        });
    });
</script>
	<script id="template_workInfo" type= "text/html">
		{{each rows as item index}}
			<tr>
				<td>
					<p>
						{{if item.RANSOM_PROPERTY == "DEAL"}}
		 					{{item.RANSOM_PROPERTY_NAME}}
						{{/if}}

						{{if item.RANSOM_PROPERTY == "APPLY"}}
		 					{{item.RANSOM_PROPERTY_NAME}}
						{{/if}}
						
						{{if item.RANSOM_PROPERTY == "SIGN"}}
		 					{{item.RANSOM_PROPERTY_NAME}}
						{{/if}}
						
						{{if item.RANSOM_PROPERTY == "PAYLOAN_ONE"}}
		 					{{item.RANSOM_PROPERTY_NAME}}
						{{/if}}
						
						{{if item.RANSOM_PROPERTY == "PAYLOAN_TWO"}}
		 					{{item.RANSOM_PROPERTY_NAME}}
						{{/if}}
						
						{{if item.RANSOM_PROPERTY == "CANCELDIYA_ONE"}}
		 					{{item.RANSOM_PROPERTY_NAME}}
						{{/if}}
						
						{{if item.RANSOM_PROPERTY == "CANCELDIYA_TWO"}}
		 					{{item.RANSOM_PROPERTY_NAME}}
						{{/if}}
						
						{{if item.RANSOM_PROPERTY == "RECEIVE_ONE"}}
		 					{{item.RANSOM_PROPERTY_NAME}}
						{{/if}}
						
						{{if item.RANSOM_PROPERTY == "RECEIVE_TWO"}}
		 					{{item.RANSOM_PROPERTY_NAME}}
						{{/if}}
						
						{{if item.RANSOM_PROPERTY == "PAYCLEAR"}}
		 					{{item.RANSOM_PROPERTY_NAME}}
						{{/if}}
							
						
					</p>
				</td>
				<td>
					<p>
						{{if item.RANSOM_PROPERTY == "DEAL"}}
		 					{{item.DEAL_TIME}}
						{{/if}}

						{{if item.RANSOM_PROPERTY == "APPLY"}}
		 					{{item.APPLY_TIME}}
						{{/if}}
						
						{{if item.RANSOM_PROPERTY == "SIGN"}}
		 					{{item.INTERVIEW_TIME}}
						{{/if}}
						
						{{if item.RANSOM_PROPERTY == "PAYLOAN_ONE"}}
		 					{{item.REPAY_TIME}}
						{{/if}}
						
						{{if item.RANSOM_PROPERTY == "PAYLOAN_TWO"}}
		 					{{item.REPAY_TIME}}
						{{/if}}
						
						{{if item.RANSOM_PROPERTY == "CANCELDIYA_ONE"}}
		 					{{item.CANCEL_TIME}}
						{{/if}}
						
						{{if item.RANSOM_PROPERTY == "CANCELDIYA_TWO"}}
		 					{{item.CANCEL_TIME}}
						{{/if}}
						
						{{if item.RANSOM_PROPERTY == "RECEIVE_ONE"}}
		 					{{item.REDEEM_TIME}}
						{{/if}}
						
						{{if item.RANSOM_PROPERTY == "RECEIVE_TWO"}}
		 					{{item.REDEEM_TIME}}
						{{/if}}
						
						{{if item.RANSOM_PROPERTY == "PAYCLEAR"}}
		 					{{item.PAYMENT_TIME}}
						{{/if}}
					</p>
				</td>
				<td>
					<p>
						{{item.UPDATE_USER}}
					</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0)">前往修改</a>
					</p>
				</td>
			</tr>
		{{/each}}
	</script>
</body>
</html>