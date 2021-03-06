<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewpoart" content="width=device-width, initial-scale=1.0">

<title>监管合约</title>
<!-- 上传相关 -->
<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fancybox.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/jquery.fileupload-ui.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/select2_metro.css' />"
	rel="stylesheet">
<!-- 展示相关 -->
<link href="<c:url value='/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/bootstrap-tokenfield.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/trunk/JSPFileUpload/selectize.default.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/static/font-awesome/css/font-awesome.css' />"
	rel="stylesheet">

<link href="<c:url value='/static/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/static/css/style.css' />" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
<!-- index_css  -->
<link href="<c:url value='/static/trans/css/common/input.css' />" rel="stylesheet"/>
<link href="<c:url value='/static/trans/css/common/table.css' />" rel="stylesheet"/> 
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />" >
<!-- stickUp fixed css -->
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/stickmenu.css' />">
<link href="<c:url value='/static/css/plugins/stickup/stickup.css' />"
	rel="stylesheet">
<link href="<c:url value='/static/css/plugins/aist-steps/steps.css' />"
	rel="stylesheet">
<link href="<c:url value='/static/css/plugins/toastr/toastr.min.css' />"
	rel="stylesheet">

<!-- index_css  -->
<link rel="stylesheet" href="<c:url value='/static/trans/css/spv/table.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/spv/input.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/spv/see.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/spv/spv.css' />" />
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<link href="<c:url value='/css/jquery.editable-select.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/js/viewer/viewer.min.css' />" rel="stylesheet" />
<link href="<c:url value='/css/jquery.editable-select.min.css' />" rel="stylesheet">
<!-- stickUp fixed css -->
<!--弹出框样式  -->
<link href="<c:url value='/css/common/xcConfirm.css' />" rel="stylesheet">
<style>
.borderClass {border:1px solid red!important;resize: none;}
.borderClass:focus {border:1px solid red!important;resize: none;}
</style>
<script type="text/javascript">
	var ctx = "${ctx}";
	var taskitem = "SpvApplyApprove";
	var source = "${source}";
	var handle = "${handle}";
	if ("${idList}" != "") {
		var idList = eval("(" + "${idList}" + ")");
	} else {
		var idList = [];
	}
</script>
</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div>
	    <form id="instForm">
	    <%-- 流程相关 --%>
	    <input type="hidden" id="caseCode" name="caseCode" value="${spvBaseInfoVO.toSpv.caseCode }">
	    <input type="hidden" id="businessKey" name="businessKey" value="${businessKey }">
		<input type="hidden" id="taskId" name="taskId" value="${taskId }">
		<input type="hidden" id="taskitem" name="taskitem" value="${taskitem }">
		<input type="hidden" id="instCode" name="instCode" value="${instCode}">
		<input type="hidden" id="source" name="source" value="${source}">
		<input type="hidden" id="urlType" name="urlType" value="${urlType}">
		</form>
		<!-- main Start -->
		<div
			class="row wrapper border-bottom white-bg page-heading stickup-nav-bar">
			<ul class="nav navbar-nav">
				<li class="menuItem active"><a href="#base_info">客户信息</a></li>
				<li class="menuItem"><a href="#spvone_info">房产及交易信息</a></li>
				<li class="menuItem"><a href="#spvtwo_info">监管资金及账户信息</a></li>
				<li class="menuItem"><a href="#spvthree_info">资金方案填写</a></li>
				<li class="menuItem"><a href="#spvfour_info">上传备件</a></li>
				<li class="menuItem"><a href="#spvfive_info">"中止/结束"流程</a></li>
			</ul>
		</div>
		<div class="row">
			<div class="wrapper wrapper-content animated fadeInUp">
				<!-- <div class="ibox"> -->
				<div class="ibox-content" id="base_info">
					<form class="form-inline">
						<div class="title">买方客户信息</div>
						<div class="form-row form-rowbot clear">
							<div class="form-group form-margin form-space-one left-extent">							
								<label for="" class="lable-one"><i style="color:red;">*</i> 姓名</label> <input name="spvCustList[0].name" 
								value="${spvBaseInfoVO.spvCustList[0].name}" type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 手机号码</label> <input name="spvCustList[0].phone"
								    value="${spvBaseInfoVO.spvCustList[0].phone}"
									type="text" class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one ">
								<label for="" class="lable-one"><i style="color:red;">*</i> 性别</label> <span
									class="sex-char"> <label class="radio-inline"> <input 
										type="radio" name="spvCustList[0].gender" id="sex1" value="1" ${spvBaseInfoVO.spvCustList[0].gender eq '1'?'checked="checked"':'' }>
										男
								</label> <label class="radio-inline"> <input 
								        type="radio" name="spvCustList[0].gender" id="sex2" value="0" ${spvBaseInfoVO.spvCustList[0].gender eq '0'?'checked="checked"':'' }> 女
								</label>
								</span>
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件类型</label> 
									<aist:dict id="spvCustList[0].idType" name="spvCustList[0].idType" clazz="form-control input-one"
									display="select"  dictType="CERT_TYPE"  
									ligerui='none' defaultvalue="${spvBaseInfoVO.spvCustList[0].idType }"></aist:dict>
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件有效期</label> 
								<input id="date-picker0" name="spvCustList[0].idValiDate" class="form-control input-one date-picker" 
								style="font-size: 13px;" type="text" value="<fmt:formatDate value="${spvBaseInfoVO.spvCustList[0].idValiDate }" pattern="yyyy-MM-dd"/>" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
						    <div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件编号</label> <input name="spvCustList[0].idCode"
								value="${spvBaseInfoVO.spvCustList[0].idCode }"
								type="text" class="form-control input-two" placeholder="">
							</div>
<%-- 							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">发证机关</label> <input type="text" name="spvCustList[0].idIssueInst"
								    value="${spvBaseInfoVO.spvCustList[0].idIssueInst }"
									class="form-control input-four" placeholder="">
							</div> --%>
						</div>
						<div class="form-row form-rowbot">
<%-- 						    <div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 家庭地址</label> <input name="spvCustList[0].homeAddr"
								    value="${spvBaseInfoVO.spvCustList[0].homeAddr }"
									type="text" class="form-control input-five" placeholder="">
							</div> --%>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">委托他人办理</label> <label
									class="radio-inline"> <input type="radio"
									name="spvCustList[0].hasDele" id="BuyRadio1" value="1" ${spvBaseInfoVO.spvCustList[0].hasDele eq '1'?'checked="checked"':'' } > 是
								</label> <label class="radio-inline"> <input type="radio"
									name="spvCustList[0].hasDele" id="BuyRadio2" value="0" ${spvBaseInfoVO.spvCustList[0].hasDele ne '1'?'checked="checked"':'' }> 否
								</label>
							</div>

						</div>
						<div class="form-row form-rowbot">
							<div
								class="form-group form-margin form-space-one left-extent buyinfo">
								<label for="" class="lable-one"><i style="color:red;">*</i> 姓名</label> <input type="text" name="spvCustList[0].agentName"
								    value="${spvBaseInfoVO.spvCustList[0].agentName }"
									class="form-control input-one" placeholder="">
							</div>
							<div
								class="form-group form-margin form-space-one left-extent buyinfo">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件类型</label> 
									<aist:dict  id="spvCustList[0].agentIdType" name="spvCustList[0].agentIdType" clazz="form-control input-one"
									display="select"  dictType="CERT_TYPE"  
									ligerui='none' defaultvalue="${spvBaseInfoVO.spvCustList[0].agentIdType }"></aist:dict>
								
							</div>
							<div class="form-group form-margin form-space-one buyinfo">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件编号</label> <input type="text" name="spvCustList[0].agentIdCode"
								    value="${spvBaseInfoVO.spvCustList[0].agentIdCode }"
									class="form-control input-two" placeholder="">
							</div>
						</div>
					</form>
					<form class="form-inline" >
						<div class="title">卖方客户信息</div>

						<div class="form-row form-rowbot clear">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 姓名</label> <input type="text" name="spvCustList[1].name"
								     value="${spvBaseInfoVO.spvCustList[1].name}"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 手机号码</label> <input name="spvCustList[1].phone"
								    value="${spvBaseInfoVO.spvCustList[1].phone}"
									type="text" class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one ">
								<label for="" class="lable-one"><i style="color:red;">*</i> 性别</label> <span
									class="sex-char"> <label class="radio-inline"> <input 
										type="radio" name="spvCustList[1].gender" id="sex3" value="1" ${spvBaseInfoVO.spvCustList[1].gender eq '1'?'checked="checked"':'' }>
										男
								</label> <label class="radio-inline"> <input type="radio"
										name="spvCustList[1].gender" id="sex4" value="0" ${spvBaseInfoVO.spvCustList[1].gender eq '0'?'checked="checked"':'' }> 女
								</label>
								</span>
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件类型</label> 
									<aist:dict id="spvCustList[1].idType" name="spvCustList[1].idType" clazz="form-control input-one"
									display="select"  dictType="CERT_TYPE"  
									ligerui='none' defaultvalue="${spvBaseInfoVO.spvCustList[1].idType }"></aist:dict>
								
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件有效期</label> 
								<input id="date-picker1" name="spvCustList[1].idValiDate" class="form-control input-one date-picker" 
								style="font-size: 13px;" type="text" value="<fmt:formatDate value="${spvBaseInfoVO.spvCustList[1].idValiDate }" pattern="yyyy-MM-dd"/>" placeholder="">
							</div>

						</div>
						<div class="form-row form-rowbot">
                            <div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件编号</label> <input type="text" name="spvCustList[1].idCode"
								    value="${spvBaseInfoVO.spvCustList[1].idCode }"
									class="form-control input-two" placeholder="">
							</div>
<%-- 							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">发证机关</label> <input type="text" name="spvCustList[1].idIssueInst"
								    value="${spvBaseInfoVO.spvCustList[1].idIssueInst }"
									class="form-control input-four" placeholder="">
							</div> --%>
						</div>
						<div class="form-row form-rowbot">
<%-- 							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 家庭地址</label> <input name="spvCustList[1].homeAddr"
								    value="${spvBaseInfoVO.spvCustList[1].homeAddr }"
									type="text" class="form-control input-five" placeholder="">
							</div> --%>
						</div>	
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">委托他人办理</label> <label
									class="radio-inline"> <input type="radio"
									name="spvCustList[1].hasDele" id="SellRadio1" value="1" ${spvBaseInfoVO.spvCustList[1].hasDele eq '1'?'checked="checked"':'' }> 是
								</label> <label class="radio-inline"> <input type="radio"
									name="spvCustList[1].hasDele" id="SellRadio2" value="0" ${spvBaseInfoVO.spvCustList[1].hasDele ne '1'?'checked="checked"':'' }> 否
								</label>
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div
								class="form-group form-margin form-space-one left-extent sellinfo">
								<label for="" class="lable-one"><i style="color:red;">*</i> 姓名</label> <input type="text" name="spvCustList[1].agentName"
								    value="${spvBaseInfoVO.spvCustList[1].agentName }"
									class="form-control input-one" placeholder="">
							</div>
							<div
								class="form-group form-margin form-space-one left-extent sellinfo">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件类型</label> 
										<aist:dict  id="spvCustList[1].agentIdType" name="spvCustList[1].agentIdType" clazz="form-control input-one"
									display="select"   dictType="CERT_TYPE"  
									ligerui='none' defaultvalue="${spvBaseInfoVO.spvCustList[1].agentIdType }"></aist:dict>
							</div>
							<div class="form-group form-margin form-space-one sellinfo">
								<label for="" class="lable-one"><i style="color:red;">*</i> 证件编号</label> <input type="text" name="spvCustList[1].agentIdCode"
								    value="${spvBaseInfoVO.spvCustList[1].agentIdCode }"
									class="form-control input-two" placeholder="">
							</div>
						</div>
					</form>
				</div>
				<div class="ibox-content" id="spvone_info">
					<form class="form-inline">
						<div class="title">房产及交易信息</div>
						<div class="form-row form-rowbot clear">
						<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 房产权利人</label> <input name="toSpvProperty.prOwnerName" type="text"
									class="form-control input-one" placeholder="" value="${spvBaseInfoVO.toSpvProperty.prOwnerName}">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 房产证号</label> <input name="toSpvProperty.prNo" type="text"
									class="form-control input-five" placeholder="" value="${spvBaseInfoVO.toSpvProperty.prNo }">
							</div>
							
							<!-- <div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">委托人姓名</label> <input  type="text"
									class="form-control input-one" placeholder="" value="">
							</div> -->
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 面积</label> <input name="toSpvProperty.prSize" type="text"
									class="form-control input-one" placeholder="" value="${spvBaseInfoVO.toSpvProperty.prSize}"> 
									<span class="date_icon">㎡</span>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 产证地址</label> <input name="toSpvProperty.prAddr" type="text"
									class="form-control input-five" placeholder="" value="${spvBaseInfoVO.toSpvProperty.prAddr}">
							</div>
						</div>
						<div class="form-row form-rowbot"></div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-two">是否偿清</label> <label
									class="radio-inline"> <input type="radio"
									name="toSpvProperty.isMortClear" id="Pledge1" value="1" ${spvBaseInfoVO.toSpvProperty.isMortClear ne '0'?'checked="checked"':'' }> 是
								</label> <label class="radio-inline"> <input type="radio" 
									name="toSpvProperty.isMortClear" id="Pledge2" value="0" ${spvBaseInfoVO.toSpvProperty.isMortClear eq '0'?'checked="checked"':'' }> 否
								</label> <span class="span-tag pledgeinfo">交易房屋抵押贷信息</span>
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div
								class="form-group form-margin form-space-one left-extent pledgeinfo">
								<label for="" class="lable-one"><i style="color:red;">*</i> 抵押方</label> <input type="text" name="toSpvProperty.mortgageeName"
									class="form-control input-one" placeholder="" value="${spvBaseInfoVO.toSpvProperty.mortgageeName }">
							</div>
							<div class="form-group form-margin form-space-one pledgeinfo">
								<label for="" class="lable-one"><i style="color:red;">*</i> 开户行</label> <input name="toSpvProperty.mortgageeBank"
								value="${spvBaseInfoVO.toSpvProperty.mortgageeBank }" type="text"
									class="form-control input-four" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div
								class="form-group form-margin form-space-one left-extent pledgeinfo">
								<label for="" class="lable-one">未偿还金额</label> <input name="toSpvProperty.leftAmount"
								value="<fmt:formatNumber type="number" value="${spvBaseInfoVO.toSpvProperty.leftAmount }" pattern="0.00" maxFractionDigits="2"/>" type="text"
									class="form-control input-one" placeholder=""><span
									class="date_icon">万元</span>
							</div>
                            <div class="form-group form-margin form-space-one pledgeinfo">
								<label for="" class="lable-one">金额大写</label> <input type="text" id="leftAmountDX"  readonly="readonly"
									class="form-control input-two" placeholder="">
							</div>	 	
						</div>
						<div class="form-row form-rowbot">

							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 网签合同号</label> <input name="toSpvProperty.signNo"
								value="${spvBaseInfoVO.toSpvProperty.signNo }" type="text"
									class="form-control input-one" placeholder="">
							</div>
							<!-- <div class="form-group form-margin form-space-one pledgeinfo">
								<label for="" class="lable-one">金额大写</label> <input type="text"
									class="form-control input-one" placeholder="">
							</div> -->
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 网签金额</label> <input name="toSpvProperty.signAmount"
								value="<fmt:formatNumber type="number" value="${spvBaseInfoVO.toSpvProperty.signAmount }" pattern="0.00" maxFractionDigits="2"/>" type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">万元</span>
							</div>
							<div class="form-group form-margin form-space-one">
							<label for="" class="lable-one">金额大写</label> <input type="text" id="signAmountDX"  readonly="readonly"
								class="form-control input-two" placeholder="">
						    </div> 
						</div>	


					</form>
				</div>
				<form class="form-inline">
				<div class="ibox-content" id="spvtwo_info">		
						<div class="title">监管资金及账户信息</div>
						<div class="form-row form-rowbot clear">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 监管总金额</label> <input name="toSpv.amount"
								value="<fmt:formatNumber type="number" value="${spvBaseInfoVO.toSpv.amount}" pattern="0.00" maxFractionDigits="2"/>" type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">万元</span>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">金额大写</label> <input type="text" id="amountDX" readonly="readonly"
									class="form-control input-two" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
						    <div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 监管产品</label>
									<input name="toSpv.prdCode" class="form-control input-two" value="${spvBaseInfoVO.toSpv.prdCode eq 1?'光大四方资金监管':'光大三方资金监管'}">
									<%-- <select id="prd" class="form-control input-one"></select>
									<select name="toSpv.prdCode" class="form-control input-two" value="${spvBaseInfoVO.toSpv.prdCode }"></select> --%>
							</div>
						</div>
						<div class="title">监管资金的支付</div>
						<div class="form-row form-rowbot clear">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 下家付款方式</label>
								<aist:dict id="toSpv.buyerPayment" name="toSpv.buyerPayment" clazz="form-control input-one"
									display="select"  dictType="SPV_BUYER_PAYMENT"  
									ligerui='none' defaultvalue="${spvBaseInfoVO.toSpv.buyerPayment }"></aist:dict>
							</div>
						</div>
						<div class="form-row form-rowbot">	
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">自筹资金</label> <input name="toSpv.amountOwn"
								    value="<fmt:formatNumber type="number" value="${spvBaseInfoVO.toSpv.amountOwn }" pattern="0.00" maxFractionDigits="2"/>" type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">万元</span>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">金额大写</label> <input type="text" id="amountOwnDX" readonly="readonly"
									class="form-control input-two" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
						<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">贷款资金</label> <input name="toSpv.amountMort"
								    value="<fmt:formatNumber type="number" value="${spvBaseInfoVO.toSpv.amountMort }" pattern="0.00" maxFractionDigits="2"/>" type="text"
									class="form-control input-one" placeholder="">
									<span class="date_icon">万元</span>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">金额大写</label> <input type="text" id="amountMortDX"  readonly="readonly"
									class="form-control input-two" placeholder="">
							</div>		
						</div>
						<div class="form-row form-rowbot">
						    <div class="form-group form-margin form-space-one" style="margin-left:28px;">
								<label for="" class="rate">其中： 商业贷款</label> <input name="toSpv.amountMortCom"
								    value="<fmt:formatNumber type="number" value="${spvBaseInfoVO.toSpv.amountMortCom }" pattern="0.00" maxFractionDigits="2"/>" type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">万元</span>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">金额大写</label> <input type="text" id="amountMortComDX"  readonly="readonly"
									class="form-control input-two" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
						    <div class="form-group form-margin form-space-one" style="margin-left:55px;">
								<label for="" class="rate">公积金贷款</label> <input name="toSpv.amountMortPsf"
								    value="<fmt:formatNumber type="number" value="${spvBaseInfoVO.toSpv.amountMortPsf }" pattern="0.00" maxFractionDigits="2"/>" type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">万元</span>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">金额大写</label> <input type="text" id="amountMortPsfDX"  readonly="readonly"
									class="form-control input-two" placeholder="">
						    </div>
						</div>
						<div class="title">资金监管账号信息</div>
						<div class="form-row form-rowbot clear">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">卖方收款账号名称</label> <input name="toSpvAccountList[1].name"
								 value="${spvBaseInfoVO.toSpvAccountList[1].name }" onBlur="updateAccTypeOptions()"
								 type="text" class="form-control input-one" placeholder="">
							</div>
                            <div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">账号</label> <input name="toSpvAccountList[1].account"
								    value="${spvBaseInfoVO.toSpvAccountList[1].account }"  type="text"
									class="form-control input-two" placeholder="">
							</div>
							
						</div>
						<div class="form-row form-rowbot">
						<div class="form-group form-margin form-space-one">
							<label for="" class="lable-one">电话</label> <input name="toSpvAccountList[1].telephone"
							    value="${spvBaseInfoVO.toSpvAccountList[1].telephone }" type="text"
								class="form-control input-one" placeholder="">
						</div>
						<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">开户行</label>
									<input id="bank_1" name="toSpvAccountList[1].bank" class="form-control input-two" value="${spvBaseInfoVO.toSpvAccountList[1].bank }" ></input>
									<input name="toSpvAccountList[1].branchBank" class="form-control input-two" value="${spvBaseInfoVO.toSpvAccountList[1].branchBank }" ></input>
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 买方退款账号名称</label> <input name="toSpvAccountList[0].name"
								    value="${spvBaseInfoVO.toSpvAccountList[0].name }" onBlur="updateAccTypeOptions()"
									type="text" class="form-control input-one" placeholder="">
							</div>
                            <div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 账号</label> <input name="toSpvAccountList[0].account"
								    value="${spvBaseInfoVO.toSpvAccountList[0].account }" type="text"
									class="form-control input-two" placeholder="">
							</div>
							
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 电话</label> <input name="toSpvAccountList[0].telephone"
								    value="${spvBaseInfoVO.toSpvAccountList[0].telephone }" type="text"
									class="form-control input-one" placeholder="">
							</div>	
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 开户行</label>
									<input id="bank_0" name="toSpvAccountList[0].bank" class="form-control input-two" value="${spvBaseInfoVO.toSpvAccountList[0].bank }" >
									<input name="toSpvAccountList[0].branchBank" class="form-control input-two" value="${spvBaseInfoVO.toSpvAccountList[0].branchBank }" >
							</div>	
						</div>
						
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 托管账户名称</label> 
								<input name="toSpvAccountList[2].name" value="${spvBaseInfoVO.toSpvAccountList[2].name }" class="form-control input-two">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 账号</label> <input type="text" name="toSpvAccountList[2].account" readOnly="readOnly" value="${spvBaseInfoVO.toSpvAccountList[2].account }"class="form-control input-two" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">资金方账户名称</label>
								<input name="toSpvAccountList[3].name" value="${spvBaseInfoVO.toSpvAccountList[3].name }" class="form-control input-two">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">账号</label> <input type="text" name="toSpvAccountList[3].account"  readOnly="readOnly"
								    value="${spvBaseInfoVO.toSpvAccountList[3].account }"
									class="form-control input-two" placeholder="">			
							</div>
							<div class="form-group form-margin form-space-one">
							    <label for="" class="lable-one">开户行</label> <input type="text" name="toSpvAccountList[3].branchBank"  readOnly="readOnly"
								    value="${spvBaseInfoVO.toSpvAccountList[3].branchBank }"
									class="form-control input-three" placeholder="">
							</div>
						</div>
						
						<c:if test="${fn:length(spvBaseInfoVO.toSpvAccountList) gt 4}">
						<c:forEach items="${spvBaseInfoVO.toSpvAccountList }" var="toSpvAccount" varStatus="status4">
						<c:if test="${status4.index gt 3 }">
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 账号名称</label> <input name="toSpvAccountList[${status4.index }].name"
								    value="${toSpvAccount.name }" onBlur="updateAccTypeOptions()"
									type="text" class="form-control input-one" placeholder="">
							</div>
                            <div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 账号</label> <input name="toSpvAccountList[${status4.index }].account"
								    value="${toSpvAccount.account }" type="text"
									class="form-control input-two" placeholder="">
							</div>
							
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 电话</label> <input name="toSpvAccountList[${status4.index }].telephone"
								    value="${toSpvAccount.telephone }" type="text"
									class="form-control input-one" placeholder="">
							</div>	
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 开户行</label>
									<input id="bank_${status4.index }" name="toSpvAccountList[${status4.index }].bank" class="form-control input-two"  value="${toSpvAccount.bank }" >
									<input name="toSpvAccountList[${status4.index }].branchBank" class="form-control input-two" value="${toSpvAccount.branchBank }" >
							</div>	
						</div>
						</c:if>
						</c:forEach>
						</c:if>
						
						<div id="spvAccDiv" class="form-row form-rowbot">
						    <div class="form-group form-margin form-space-one">
						        <label for="" class="lable-one"><i style="color:red;">*</i> 申请人</label>
						        <input type="text" id="realName" class="form-control" value='${applyUserName }'>
                                    </div>
							</div>
					    </div>
					    
						<div class="form-row form-rowbot" id="signDiv" style="display:none;">
						    <div class="form-group form-margin form-space-one">
								<label for="" class="lable-one"><i style="color:red;">*</i> 监管合同号</label> <input type="text" name="toSpv.spvConCode"
								    value="${spvBaseInfoVO.toSpv.spvConCode }"
									class="form-control input-two" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one"><i style="color:red;">*</i> 签约时间</label> 
								<input id="date-picker2" name="toSpv.signTime" class="form-control input-one date-picker" 
								style="font-size: 13px;" type="text" value="<fmt:formatDate value="${spvBaseInfoVO.toSpv.signTime }" pattern="yyyy-MM-dd"/>" placeholder="">
							</div>
						</div>
					</form>
				
				<div class="ibox-content" id="spvthree_info" >
					<div class="agree-tile">资金出款约定</div>
					<form class="form-inline table-capital">
						<p>
							监管资金次数合计<span id="sum"> ${fn:length(spvBaseInfoVO.toSpvDeDetailList)}</span> 次
						</p>
						<div class="table-box">
							<table class="table table-bordered customerinfo">
								<thead>
									<th>划转条件</th>
									<th>账户</th>
									<th style="width: 16%;">金额</th>
									<th>备注</th>
								</thead>					
								<tbody id="addTr">
								  <c:forEach items="${spvBaseInfoVO.toSpvDeDetailList }" var="toSpvDeDetail" varStatus="status">
									<tr align="center">
										<td class="text-left">
										<aist:dict id="toSpvDeDetailList[${status.index }].deCondCode" name="toSpvDeDetailList[${status.index }].deCondCode" clazz="form-control input-one"
									    display="select"  dictType="SPV_DE_COND"  
									    ligerui='none' defaultvalue="${toSpvDeDetail.deCondCode }"></aist:dict>	
										</td>
										<td class="text-left">
									    <select name="toSpvDeDetailList[${status.index }].payeeAccountType" value="${toSpvDeDetail.payeeAccountType }" class="table-select" onChange="this.value" >
									    </select>
										</td>
										<td><input name="toSpvDeDetailList[${status.index }].deAmount" value="<fmt:formatNumber type="number" value="${toSpvDeDetail.deAmount }" pattern="0.00" maxFractionDigits="2"/>" class="table-input-one" type="text"
											placeholder="请输入金额" />万元</td>
										<td class="text-left"><input name="toSpvDeDetailList[${status.index }].deAddition" value="${toSpvDeDetail.deAddition }" class="table-input"
											type="text" placeholder="" /></td>
										<td align="center">
									</tr>
								   </c:forEach>
								   <c:if test="${empty spvBaseInfoVO.toSpvDeDetailList  }">
                                        <tr><td colspan="5">没有相关信息</td></tr>
                                   </c:if>
								</tbody>					
							</table>							
						</div>
					</form>
				</div>
				
				<div class="ibox-content">
                            <div class="agree-tile">
                                出入账记录
                            </div>
                            <div class="table-capital">
                             <form>
                                <div class="table_content">
                                    <table class="table table-bordered customerinfo">
                                        <thead>
                                        <tr>
                                            <th>凭证编号</th>
                                            <th>金额</th>
                                            <th>账户信息</th>
                                            <th>审批时间</th>
                                            <th>审核人</th>
                                        </tr>
                                        </thead>
                                        <tbody> 
                                        <c:forEach items="${cashFlowList}" var="cashFlow" varStatus="status2">
                                        <tr>
                                            <td>
                                                <p class="big">
                                                    <a href="javascript:void(0);">
                                                        ${cashFlow.voucherNo }
                                                    </a>
                                                </p>
                                                <p>
                                                    ${cashFlow.direction }
                                                </p>
                                            </td>
                                            <td>
                                                <p class="big">
                                                <c:if test="${cashFlow.usage eq 'in'}">
                                                    <span class="sign_normal navy_bg">
                                                    入账
                                                    </span>
                                                </c:if>
                                                <c:if test="${cashFlow.usage eq 'out'}">
                                                    <span class="sign_normal pink_bg">
                                                   出账
                                                    </span>
                                                </c:if>    
                                                </p>
                                                <p class="big">
                                                    ${cashFlow.amount }万
                                                </p>
                                            </td>
                                            <td>
                                                <p><span>付：</span>${cashFlow.payer }&nbsp;&nbsp;${cashFlow.payerAcc }/${cashFlow.payerBank }</p>
                                                <p><span>收：</span>${cashFlow.receiver }&nbsp;&nbsp;${cashFlow.receiverAcc }/${cashFlow.receiverBank }</p>
                                            </td>
                                            <td>
                                                <p class="smll_sign">
                                                    <i class="sign_normal">录入</i>
                                                    <a href="javascript:void(0)">${cashFlow.createByName }&nbsp;</a><fmt:formatDate value="${cashFlow.createTime }" pattern="yyyy-MM-dd"/>
                                                </p>
                                                <p class="smll_sign">
                                                    <i class="sign_normal">结束</i>       
                                                    <c:if test="${not empty cashFlow.closeTime }">      
                                                    <a href="javascript:void(0)">${cashFlow.ftPostAuditorName }&nbsp;</a><fmt:formatDate value="${cashFlow.closeTime }" pattern="yyyy-MM-dd"/>
                                                    </c:if>
                                                </p>
                                            </td>
                                            <td>
                                                <%-- <p>
                                                   ${spvBaseInfoVO.toSpvProperty.prAddr }
                                                </p> --%>
                                                <p class="smll_sign">
                                                 	   审核人：<a href="javascript:void(0)">
                                                    ${cashFlow.applyAuditorName }
                                                    
                                                    <c:if test="${cashFlow.usage eq 'out' }">
	                                                    <c:if test="${cashFlow.status ge 12 }">
	                                                    &gt;${cashFlow.ftPreAuditorName }
	                                                    </c:if>
                                                    </c:if>
                                                    
                                                    <c:if test="${cashFlow.usage eq 'out'}" >
	                                                    <c:if test="${cashFlow.status ge 13 }">
	                                                    &gt;${cashFlow.ftPostAuditorName }
	                                                    </c:if>
                                                    </c:if>
                                                    
                                                    <c:if test="${cashFlow.usage eq 'in'}" >
                                                    <c:if test="${cashFlow.ftPostAuditorName.length()>0 }">
                                                    &gt;
                                                    ${cashFlow.ftPostAuditorName }
                                                    </c:if>
                                                    </c:if>
                                                    </a>
                                                </p>
                                            </td>
                                        </tr>
                                        </c:forEach>
                                        <c:if test="${empty cashFlowList }">
                                        <tr><td colspan="5">没有相关信息</td></tr>
                                        </c:if>
                                        </tbody>
                                    </table>
                                </div>
                                </form>
                            </div>
                        </div>
				
				<div class="ibox-content" id="spvfour_info" >
				<div style="height: auto;">
				<c:choose>
					<c:when test="${accesoryList!=null}">
						<h5>上传备件<br> <br> <br>${accesoryList[0].accessoryName }</h5>
						<c:forEach var="accesory" items="${accesoryList}"
							varStatus="status">
							<div class="" id="fileupload_div_pic">
								<form id="fileupload"
									action="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"
									method="POST" enctype="multipart/form-data">
									<noscript>
										<input type="hidden" name="redirect"
											value="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload">
										<input type="hidden" id="preFileCode" name="preFileCode"
											value="${accesory.accessoryCode }">
									</noscript>
									<c:if test="${status.index != 0}">
										<h5 align="left">
											<br>${accesory.accessoryName }</h5>
									</c:if>
									<div class="row-fluid fileupload-buttonbar">
										<div class="" style="height: auto">
											<div role="presentation" class="table table-striped "
												style="height: auto; margin-bottom: 10px; line-height: 80px; text-align: center; border-radius: 4px; float: left;">
												<div id="picContainer${accesory.pkid }" class="files"
													data-toggle="modal-gallery" data-target="#modal-gallery"></div>
												<span class=" fileinput-button "
													style="margin-left: 10px !important; width: 80px;">
													<div id="chandiaotuBtn" class=""
														style="height: 80px; width: 100%; border: 1px solid #ccc; line-height: 80px; text-align: center; border-radius: 4px;">
														<i class="fa fa-plus"></i>
													</div> <input id="picFileupload${accesory.pkid }" type="file"
													name="files[]" multiple
													data-url="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"
													data-sequential-uploads="true">
												</span>
											</div>
										</div>
									</div>
								</form>
							</div>
		
							<div class="row-fluid">
								<div class="">
									<script id="templateUpload${accesory.pkid }" type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv1" class="template-upload fade row-fluid span2 in" style="height:80px;border:1px solid #ccc;margin-left:10px;margin-bottom:20px;line-height:80px;text-align:center;border-radius:4px;float:left;">
									<!--图片缩图  -->
							        <div class="preview"><span class="fade"></span></div>
									<!--  错误信息 -->
							        {% if (file.error) { %}
							            <div class="error span12" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
							        {% } else if (o.files.valid && !i) { %}
									<!-- 单个对应的按钮  -->
							            <div class="start span1" style="display: none">
										{% if (!o.options.autoUpload) { %}
							                <button class="btn">
							                    <i class="icon-upload icon-white"></i>
							                    <span>上传</span>
							                </button>
							            {% } %}
										</div>
							        {% } else { %}
							            <div class="span1" colspan="2"></div>
							        {% } %}
							        <div class="cancel" style="margin-top:-125px;margin-left:85%;">
									{% if (!i) { %}
							            <button class="btn red" style="width:20px;height:20px;border-radius:80px;line-height:20px;text-align:center;padding:0!important;">
							                <i class="icon-remove"></i>
							            </button>
							        {% } %}
									</div>
							    </div>
							{% } %}
						</script>
									<script id="templateDownload${accesory.pkid }"
										type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv1" class="template-download fade row-fluid span2" style="height:80px;border:1px solid #ccc;margin-bottom:20px;margin-left:10px;line-height:80px;text-align:center;border-radius:4px;float:left;">
							        {% if (file.error) { %}
							            <div class="error span2" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
							        {% } else { %}
							            <div class="preview span12">
										<input type="hidden" name="preFileAdress" value="{%=file.id%}"></input>
										<input type="hidden" name="picTag" value="${accesory.accessoryCode }"></input>
										<input type="hidden" name="picName" value="{%=file.name%}"></input>
							            {% if (file.id) { %}
                                              {% if (((file.name).substring((file.name).lastIndexOf(".")+1))=='tif') { %}
							               		<img src="${ctx }/img/tif.png" alt="" width="80px" height="80px">
                                              {% } else { %}
 												 <img src="<aist:appCtx appName='shcl-filesvr-web'/>/JQeryUpload/getfile?fileId={%=file.id%}" alt="" width="80px" height="80px">
  											  {% } %}
							            {% } %}</div>
							        {% } %}
							        <div class="delete span2" style="margin-left:85%;margin-top:-120px;">
							           <button data-url="<aist:appCtx appName='shcl-filesvr-web'/>/JQeryUpload/deleteFile?fileId={%=file.id%}" data-type="GET" class="btn red" style="line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;">
							                <i class="icon-remove"></i>
							            </button>
							        </div>
							    </div>
							{% } %}
						</script>
								</div>
							</div>
						</c:forEach>
		
						<div class="row-fluid" style="display: none;">
							<div class="span4">
								<div class="control-group">
									<a class="btn blue start" id="startUpload"
										style="height: 30px; width: 50px"> <i
										class="icon-upload icon-white"></i> <span>上传</span>
									</a>
								</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<h5>
							上传备件<br>无需上传备件
						</h5>
					</c:otherwise>
				</c:choose>
				</div>
				<div class="ibox-content">			
				</div>
				</div>
				
				<div class="ibox-content" id="spvfive_info">
				<form id="spvfive">
				    <input type="hidden" id="handle" name="handle" value="${handle }">
                    <div class="stop-content">
                        <div class="form-inline">
                            <div class="title">"中止/结束"流程</div>
                            <div class="form-row form-rowbot clear">
                                <div class="form-group form-margin margin-host">
                                    <input type="hidden" name="toSpvCloseApply.pkid" value="${spvCloseInfoVO.toSpvCloseApply.pkid}" >
                                    <input type="hidden" name="toSpvCloseApply.spvCode" value="${empty spvCloseInfoVO.toSpvCloseApply.spvCode?spvCode:spvCloseInfoVO.toSpvCloseApply.spvCode}" >
                                    <label for="" class=""><i style="color:red;">*</i> 申请状态</label> 
                                    <label class="radio-inline"> 
                                    <input type="radio" name="toSpvCloseApply.closeType" value="1" ${spvCloseInfoVO.toSpvCloseApply.closeType eq '1'?'checked="checked"':''} >中止
                                    </label>
                                    <label class="radio-inline"> 
                                    <input type="radio" name="toSpvCloseApply.closeType" value="0" ${spvCloseInfoVO.toSpvCloseApply.closeType eq '0'?'checked="checked"':''}> 结束
                                    </label>
                                </div>
                            </div>
                            <div class="form-row form-rowbot" style="margin-left:-80px;">
                                <div class="form-group form-margin form-space-one">
                                    <label for="" class="lable-one"><i style="color:red;">*</i> 原因</label>
                                    <%-- <input type="text" name="toSpvCloseApply.comment" value="${spvCloseInfoVO.toSpvCloseApply.comment}" class="form-control space-host input-five" placeholder="" > --%>
                                    <textarea name="toSpvCloseApply.comment"  class="form-control space-host" placeholder="请填写中止/结束原因" style="width:100%; resize: none;height:140px;border-radius: 3px;border: 1px solid #d8d8d8;padding:10px;margin-left:150px;margin-top:-25px;">${spvCloseInfoVO.toSpvCloseApply.comment}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                
                        <c:if test="${handle eq 'hostAudit' or handle eq 'directorAudit' or handle eq 'apply'}" >
                        <div class="title">
                            <strong>审核记录</strong>
                        </div>
                        <div class="view-content">
                            <div class="view-box">
                            <c:if test="${not empty spvCloseInfoVO.toSpvCloseApplyAuditList }">
                            <c:forEach items="${spvCloseInfoVO.toSpvCloseApplyAuditList }" var="toSpvCloseAduit" varStatus="status4">
                            <input type="hidden" name="toSpvAduitList[${status4.index }].pkid" value="${toSpvCloseAduit.pkid }" />
                            <div class="view clearfix">
                                <p>
                                   <span class="auditor">审核人：<em>${toSpvCloseAduit.operatorName }(${toSpvCloseAduit.operatorJobName })</em></span>
                                   <span class="result pink_bg">${toSpvCloseAduit.result }</span>
                                   <span class="time">审核日期:<em><fmt:formatDate value="${toSpvCloseAduit.createTime }" pattern="yyyy-MM-dd"/> </em></span>
                                </p>
                                <p>
                                    <span class="auditing">审核意见</span>
                                    <em class="view_content">${toSpvCloseAduit.content }</em>
                                </p>
                            </div>
                            </c:forEach>
                            </c:if>
                            <c:if test="${empty spvCloseInfoVO.toSpvCloseApplyAuditList }">
                                    <%-- <p class="text-center"><img src="${ctx}/image/false2.png" height="100" alt="" /></p> --%>
                                    <div style="width:100%;height:100px;background:url(../../../image/false2.png) no-repeat center;background-size:100% 100%;" ></div>
                            </c:if>
                        </div>
                        </div>
                        </c:if>

                        <div class="submitter">
                            提交人：<span>${user.realName }(${user.serviceJobName })</span>
                        </div>
                        
                        <div class="excuse">  
                        <c:if test="${handle eq 'hostAudit' or handle eq 'directorAudit' }">
                            <form id="auditContent">
                                <i style="color:red;">*</i> <textarea name="toSpvCloseApplyAuditList[0].content" id="" placeholder="请填写审核意见" style="width:100%; resize: none;height:140px;border-radius: 3px;border: 1px solid #d8d8d8;padding:10px;"></textarea>
                            </form>
                        </c:if>    
                            <div class="form-btn">
                            <div class="text-center">                     
                            <c:if test="${handle eq 'apply' }">
							    <div>
									<a id="apply_submit_btn" class="btn btn-success btn-space">提交申请</a>
									<a id="apply_cancel_btn" class="btn btn-pink btn-space">取消申请</a>
									<a onclick="rescCallback()" class="btn btn-default btn-space">关闭</a>
								</div>
							</c:if>			
							<c:if test="${handle eq 'hostAudit' }">
							    <div>
                                <a id="hostAudit_pass_btn" class="btn btn-success btn-space">审批通过</a>
                                <a id="hostAudit_reject_btn" class="btn btn-pink btn-space">审批驳回</a>
                                <a onclick="rescCallback()" class="btn btn-default btn-space">关闭</a>
								</div>
							</c:if>											
							<c:if test="${handle eq 'directorAudit' }">
							    <div>
                                <a id="directorAudit_pass_btn" class="btn btn-success btn-space">审批通过</a>
                                <a id="directorAudit_reject_btn" class="btn btn-pink btn-space">审批驳回</a>
                                <a onclick="rescCallback()" class="btn btn-default btn-space">关闭</a>
								</div>
							</c:if>							
							<c:if test="${empty handle}">
							    <div>
								<a id="page_submit_btn" class="btn btn-success btn-space">提交申请</a>
                                <a onclick="rescCallback()" class="btn btn-default btn-space">关闭</a>
								</div>
							</c:if>			
                            </div>
                        </div>
                        </div>
                </div>
                    
                </div>
            </div>
			</div>
</div>
    

	<!-- Mainly scripts -->
	<content tag="local_script">
	<script src="<c:url value='/static/js/plugins/toastr/toastr.min.js' />"></script> 
	<script src="<c:url value='/static/js/morris/morris.js' />"></script> 
	<script src="<c:url value='/static/js/morris/raphael-min.js' />"></script> <!-- index_js -->
	<!-- 上传附件相关 --> <script src="<c:url value='/js/trunk/JSPFileUpload/app.js' />"></script>
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
	<script src="<c:url value='/js/trunk/JSPFileUpload/jssor.slider.js' />"></script> <!-- 上传附件 结束 -->
	<!-- 附件保存修改相关 -->
    <script src="<c:url value='/js/trunk/task/attachment5.js' />"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script> 
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/static/trans/js/spv/spvCloseApply.js' />"></script> <!-- stickup plugin -->
	<script src="<c:url value='/static/js/plugins/stickup/stickUp.js' />"></script>
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script> 
	<script src="<c:url value='/js/viewer/viewer.min.js' />"></script>	
	<!-- 引入弹出框js文件 -->
    <script src="<c:url value='/js/common/xcConfirm.js' />"></script>
		
		<script>
		$(document).ready(function(){
			$("#amountDX").val(DX($("input[name='toSpv.amount']").val()*10000));
			$("#amountOwnDX").val(DX($("input[name='toSpv.amountOwn']").val()*10000));
			$("#amountMortDX").val(DX($("input[name='toSpv.amountMort']").val()*10000));
			$("#amountMortComDX").val(DX($("input[name='toSpv.amountMortCom']").val()*10000));
			$("#amountMortPsfDX").val(DX($("input[name='toSpv.amountMortPsf']").val()*10000));
			$("#signAmountDX").val(DX($("input[name='toSpvProperty.signAmount']").val()*10000));
			$("#leftAmountDX").val(DX($("input[name='toSpvProperty.leftAmount']").val()*10000));
			
			var $idValiDate0 = $("input[name='spvCustList[0].idValiDate']");
			var $idValiDate1 = $("input[name='spvCustList[1].idValiDate']");
			if($idValiDate0.val() == '3000-01-01'){
				$idValiDate0.val("长期有效");
			}
			if($idValiDate1.val() == '3000-01-01'){
				$idValiDate1.val("长期有效");
			}
			
		jQuery(function($) {	
 	       $('.stickup-nav-bar').stickUp({
                  parts: {
                    0:'base_info',
                    1:'spvone_info',
                    2:'spvtwo_info',
                    3:'spvthree_info',
                    4:'spvfour_info',
                    5:'spvfive_info'
                  },
                  itemClass: 'menuItem',
                  itemHover: 'active',
                  marginTop: 'auto'
          }); 
		});
		       
		$(".buyinfo, .sellinfo, .pledgeinfo").hide();
		
		if($("#BuyRadio1").is(":checked")){
			$(".buyinfo").show();
		}
		if($("#SellRadio1").is(":checked")){
			$(".sellinfo").show();
		}
		if($("#Pledge2").is(":checked")){
			$(".pledgeinfo").show();
		}
		
		 //更新账户类型下拉选 
		 updateAccTypeOptions();
		
		})
		
	function rescCallback(){
	   if($("#urlType").val() == 'myTask'){    	 
		   window.opener.location.reload(); //刷新父窗口
  	       window.close(); //关闭子窗口.
	     }else{
	    	 window.location.href = ctx+"/spv/spvList";
	     }
	}
  
/*******************************************************控件相关*********************************************************************/ 
		//图片查看器控件  
		function renderImg(){		
			$('.wrapper-content').viewer('destroy');
			$('.wrapper-content').viewer({zIndex:15001});
		}
               
        //转大写函数 
        var DX = function (num) {  
		  var strOutput = "";  
		  var strUnit = '仟佰拾亿仟佰拾万仟佰拾元角分';  
		  num += "00";  
		  var intPos = num.indexOf('.');  
		  if (intPos >= 0)  
		    num = num.substring(0, intPos) + num.substr(intPos + 1, 2);  
		  strUnit = strUnit.substr(strUnit.length - num.length);  
		  for (var i=0; i < num.length; i++)  
		    strOutput += '零壹贰叁肆伍陆柒捌玖'.substr(num.substr(i,1),1) + strUnit.substr(i,1);  
		    return strOutput.replace(/零角零分$/, '整').replace(/零[仟佰拾]/g, '零').replace(/零{2,}/g, '零').replace(/零([亿|万])/g, '$1').replace(/零+元/, '元').replace(/亿零{0,3}万/, '亿').replace(/^元/, "零元");  
		};

		</script> 
		</content>

</body>

</html>