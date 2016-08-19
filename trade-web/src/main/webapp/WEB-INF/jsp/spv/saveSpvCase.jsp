<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewpoart" content="width=device-width, initial-scale=1.0">

<title>E+贷款</title>

<link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/static/font-awesome/css/font-awesome.css"
	rel="stylesheet">

<link href="${ctx}/static/css/animate.css" rel="stylesheet">
<link href="${ctx}/static/css/style.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">

<!-- stickUp fixed css -->
<link rel="stylesheet"
	href="${ctx}/static/trans/css/common/stickmenu.css">
<link href="${ctx}/static/css/plugins/stickup/stickup.css"
	rel="stylesheet">
<link href="${ctx}/static/css/plugins/aist-steps/steps.css"
	rel="stylesheet">
<link href="${ctx}/static/css/plugins/toastr/toastr.min.css"
	rel="stylesheet">

<!-- index_css  -->
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/input.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/see.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/spv.css" />
<!-- stickUp fixed css -->
</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div id="wrapper">
		<!-- main Start -->
		<div
			class="row wrapper border-bottom white-bg page-heading stickup-nav-bar">
			<ul class="nav navbar-nav">
				<li class="menuItem active"><a href="#base_info">客户信息</a></li>
				<li class="menuItem"><a href="#spvone_info">房产及交易信息</a></li>
				<li class="menuItem"><a href="#spvtwo_info">监管资金及账户信息</a></li>
				<li class="menuItem"><a href="#spvthree_info">资金方案填写</a></li>
			</ul>
			<div class="menu_btn"
				style="float: right; margin-right: 250px; margin-top: 6px">
				<button class="btn btn-warning">保存</button>
			</div>
		</div>
		<div class="row">
			<div class="wrapper wrapper-content animated fadeInUp">
				<!-- <div class="ibox"> -->

				<div class="ibox-content" id="base_info">
					<form class="form-inline">
						<div class="title">买方客户信息</div>

						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">买方姓名</label> <input type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one ">
								<label for="" class="lable-one">买方性别</label> <span
									class="sex-char"> <label class="radio-inline"> <input
										type="radio" name="sex" id="sex1" value="option1" checked="">
										男
								</label> <label class="radio-inline"> <input type="radio"
										name="sex" id="sex2" value="option2"> 女
								</label>
								</span>
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">买方手机号码</label> <input
									type="text" class="form-control input-one" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">买方证件类型</label> <select name=""
									id="" class="form-control input-one">
									<option value="">证件类型</option>
								</select>
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">证件有效期</label> <input type="text"
									class="form-control input-one" placeholder="如：2010-08">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">证件编号</label> <input type="text"
									class="form-control input-two" placeholder="">
							</div>

						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">发证机关</label> <input type="text"
									class="form-control input-four" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">买方家庭地址</label> <input
									type="text" class="form-control input-five" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">是否委托他人办理</label> <label
									class="radio-inline"> <input type="radio"
									name="RadioOptions" id="BuyRadio1" value="option1"> 是
								</label> <label class="radio-inline"> <input type="radio"
									name="RadioOptions" id="BuyRadio2" value="option2"> 否
								</label>
							</div>

						</div>
						<div class="form-row form-rowbot">
							<div
								class="form-group form-margin form-space-one left-extent buyinfo">
								<label for="" class="lable-one">委托人姓名</label> <input type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div
								class="form-group form-margin form-space-one left-extent buyinfo">
								<label for="" class="lable-one">委托人证件类型</label> <input
									type="text" class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one buyinfo">
								<label for="" class="lable-one">证件编号</label> <input type="text"
									class="form-control input-two" placeholder="">
							</div>
						</div>
					</form>
					<form class="form-inline">
						<div class="title">卖方客户信息</div>

						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">卖方姓名</label> <input type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one ">
								<label for="" class="lable-one">卖方性别</label> <span
									class="sex-char"> <label class="radio-inline"> <input
										type="radio" name="sex" id="sex3" value="option1" checked="">
										男
								</label> <label class="radio-inline"> <input type="radio"
										name="sex" id="sex4" value="option2"> 女
								</label>
								</span>
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">卖方手机号码</label> <input
									type="text" class="form-control input-one" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">卖方证件类型</label> <select name=""
									id="" class="form-control input-one">
									<option value="">证件类型</option>
								</select>
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">证件有效期</label> <input type="text"
									class="form-control input-one" placeholder="如：2010-08">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">证件编号</label> <input type="text"
									class="form-control input-two" placeholder="">
							</div>

						</div>
						<div class="form-row form-rowbot">

							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">发证机关</label> <input type="text"
									class="form-control input-four" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">卖方家庭地址</label> <input
									type="text" class="form-control input-five" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">是否委托他人办理</label> <label
									class="radio-inline"> <input type="radio"
									name="RadioOptions" id="SellRadio1" value="option1"> 是
								</label> <label class="radio-inline"> <input type="radio"
									name="RadioOptions" id="SellRadio2" value="option2"> 否
								</label>
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div
								class="form-group form-margin form-space-one left-extent sellinfo">
								<label for="" class="lable-one">委托人姓名</label> <input type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div
								class="form-group form-margin form-space-one left-extent sellinfo">
								<label for="" class="lable-one">委托人证件类型</label> <input
									type="text" class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one sellinfo">
								<label for="" class="lable-one">证件编号</label> <input type="text"
									class="form-control input-two" placeholder="">
							</div>
						</div>
					</form>
				</div>
				<div class="ibox-content" id="spvone_info">
					<form class="form-inline">
						<div class="title">房产及交易信息</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">房产证号</label> <input type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">房产权利人</label> <input type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">委托人姓名</label> <input type="text"
									class="form-control input-one" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">面积</label> <input type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">平方米</span>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">房屋地址</label> <input type="text"
									class="form-control input-five" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot"></div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-two">是否偿清</label> <label
									class="radio-inline"> <input type="radio"
									name="RadioOptions" id="Pledge1" value="option1"> 是
								</label> <label class="radio-inline"> <input type="radio"
									name="RadioOptions" id="Pledge2" value="option2"> 否
								</label> <span class="span-tag pledgeinfo">交易房屋抵押贷信息</span>
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div
								class="form-group form-margin form-space-one left-extent pledgeinfo">
								<label for="" class="lable-one">抵押方</label> <input type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one pledgeinfo">
								<label for="" class="lable-one">开户行</label> <input type="text"
									class="form-control input-four" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div
								class="form-group form-margin form-space-one left-extent pledgeinfo">
								<label for="" class="lable-one">未偿还金额</label> <input type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one pledgeinfo">
								<label for="" class="lable-one">金额大写</label> <input type="text"
									class="form-control input-one" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">网签合同号</label> <input type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">下家付款方式</label> <select
									class="form-control input-one">
									<option value="">全数</option>
								</select>
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">网签金额</label> <input type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">元</span>
							</div>
						</div>
					</form>
				</div>
				<div class="ibox-content" id="spvtwo_info">
					<form class="form-inline">
						<div class="title">监管资金及账户信息</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">监管总金额</label> <input type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">元</span>
							</div>
						</div>
						<div class="title">监管资金的支付</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">自筹资金</label> <input type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">元</span>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">贷款资金</label> <input type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="rate">其中： 商业贷款</label> <input type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">元</span>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="rate">公积金贷款</label> <input type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">元</span>
							</div>
						</div>
						<div class="form-row form-rowbot"></div>
						<div class="title">资金监管账号信息</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">卖方收款账号名称</label> <input
									type="text" class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">电话</label> <input type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">开户行</label> <input type="text"
									class="form-control input-four" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">账号</label> <input type="text"
									class="form-control input-two" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">买方退款账号名称</label> <input
									type="text" class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">电话</label> <input type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">开户行</label> <input type="text"
									class="form-control input-four" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">账号</label> <input type="text"
									class="form-control input-two" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">托管账户名称</label> <input
									type="text" class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">账号</label> <input type="text"
									class="form-control input-two" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">资金方账户名称</label> <input
									type="text" class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">账号</label> <input type="text"
									class="form-control input-two" placeholder="">
							</div>
						</div>
					</form>
				</div>
				<div class="ibox-content" id="spvthree_info" style="height: 450px;">
					<div class="agree-tile">资金出款约定</div>
					<form class="form-inline table-capital">
						<p>
							监管资金次数合计<span id="sum"> 1</span> 次
						</p>
						<div class="table-box">
							<table class="table table-bordered customerinfo">
								<thead>
									<th>划转条件</th>
									<th>账户</th>
									<th style="width: 16%;">金额</th>
									<th>备注</th>
									<th>操作</th>
								</thead>
								<tbody id="addTr">
									<tr align="center">
										<td class="text-left"><select class="table-select">
												<option value="">买方贷款审批完成</option>
										</select></td>
										<td class="text-left"><select class="table-select">
												<option value="">资金方</option>
												<option value="">卖方</option>
										</select></td>
										<td><input class="table-input-one" type="text"
											placeholder="请输入金额" />元</td>
										<td class="text-left"><input class="table-input"
											type="text" placeholder="" /></td>
										<td align="center"><a href="javascript:void(0)"
											onClick="getAtr(this)">添加</span></a>
									</tr>
								</tbody>
							</table>
							<div class="form-btn">
								<div>
									<button type="submit" class="btn btn-success">提交申请</button>
									<button type="submit" class="btn btn-default">取消</button>
								</div>
							</div>
						</div>
					</form>

				</div>

			</div>
		</div>
		<!-- main End -->

	</div>

	<!-- Mainly scripts -->
	<content tag="local_script"> <!-- Custom and plugin javascript -->
	<script src="${ctx}/static/js/inspinia.js"></script> <script
		src="${ctx}/static/js/plugins/pace/pace.min.js"></script> <!-- Toastr script -->
	<script src="${ctx}/static/js/plugins/toastr/toastr.min.js"></script> <script
		src="${ctx}/static/js/morris/morris.js"></script> <script
		src="${ctx}/static/js/morris/raphael-min.js"></script> <!-- index_js -->
	<script src="${ctx}/static/trans/js/eloan/eloan.js"></script> <script
		src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> <script
		src="${ctx}/js/template.js" type="text/javascript"></script> <!-- stickup plugin -->
	<script src="${ctx}/static/js/plugins/stickup/stickUp.js"></script> <script
		src="${ctx}/static/trans/js/spv/spvDetails.js"></script> <script>
			
		</script> </content>


</body>

</html>