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
<!-- index_css  -->
<link href="${ctx}/static/trans/css/eloan/eloan/eloan.css" rel="stylesheet"/>
<link href="${ctx}/static/trans/css/common/input.css" rel="stylesheet"/>
<link href="${ctx}/static/trans/css/common/table.css" rel="stylesheet"/> 
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" >
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
				<button id="saveBtn" class="btn btn-warning">保存</button>
			</div>
		</div>
		<div class="row">
			<div class="wrapper wrapper-content animated fadeInUp">
			                    <!-- <div class="ibox"> -->
                    <div class="ibox-content" id="case_info">
                        <div class="main_titile" style="position: relative;">
                            <h5>关联案件<button type="button" id="link_btn" class="btn btn-success btn-blue" data-toggle="modal" data-target="#myModal">关联案件</button></h5>
                            <c:if test="${caseCode == null}">
								<div class="case_content" style="display:none;">
							</c:if>
                            <c:if test="${caseCode != null}">
								<div class="case_content">
							</c:if>
                           <div class="case_row">
                               <div class="case_lump">
                                   <p><em>案件编号</em><span class="span_one" id="content_caseCode">${caseCode}</span></p>
                               </div>
                               <div class="case_lump">
                                   <p><em>产权地址</em><span class="span_two" id="content_propertyAddr">${propertyAddr}</span></p>
                               </div>
                           </div>
                           <div class="case_row">
                               <div class="case_lump">
                                   <p><em>交易顾问</em><span class="span_one" id="content_processorId">${processorName}</span></p>
                               </div>
                               <div class="case_lump">
                                   <p><em>上家姓名</em><span class="span_two" id="content_seller">${sellerName}</span></p>
                               </div>
                           </div>
                           <div class="case_row">
                               <div class="case_lump">
                                   <p><em>经纪人</em><span class="span_one" id="content_agentName">${agentName}</span></p>
                               </div>
                               <div class="case_lump">
                                   <p><em>下家姓名</em><span class="span_two" id="content_buyer">${buyerName}</span></p>
                               </div>
                           </div>
                       </div>

                            <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                                <div class="modal-dialog" style="width: 1070px;">
                                    <div class="modal-content animated fadeIn apply_box">
                                        <form action="" class="form_list clearfix">
                                            <div class="form_tan">
                                                <label class="control-label sign_left">
                                                    产证地址
                                                </label>
                                                <input class="sign_right input_type" placeholder="请输入" value="" id="propertyAddr" name="propertyAddr">
                                            </div>
                                            <div class="form_tan tan_space">
                                                <div class="add_btn">
                                                    <button type="button" class="btn btn-success" id="searchButton">
                                                        <i class="icon iconfont">&#xe635;</i>&nbsp;查询
                                                    </button>
                                                </div>
                                            </div>
                                        </form>
                                        <button type="button" class="close close_blue" data-dismiss="modal"><i class="iconfont icon_rong">
                                                &#xe60a;
                                            </i></button>
                                            <div class="eloanApply-table">
                                            </div>

                                    </div>
                                </div>
                            </div>
                        </div>
				<!-- <div class="ibox"> -->
				<div class="ibox-content" id="base_info">
					<form class="form-inline">
						<div class="title">买方客户信息</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
							    <input type="hidden" name="spvCustList[0].pkid" value="${spvBaseInfoVO.spvCustList[0].pkid }" />
								<label for="" class="lable-one">买方姓名</label> <input name="spvCustList[0].name" 
								value="${spvBaseInfoVO.spvCustList[0].name }" type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one ">
								<label for="" class="lable-one">买方性别</label> <span
									class="sex-char"> <label class="radio-inline"> <input
										type="radio" name="spvCustList[0].gender" id="sex1" value="1" ${spvBaseInfoVO.spvCustList[0].gender eq '1'?'checked="checked"':'' }>
										男
								</label> <label class="radio-inline"> <input 
								        type="radio" name="spvCustList[0].gender" id="sex2" value="0" ${spvBaseInfoVO.spvCustList[0].gender eq '0'?'checked="checked"':'' }> 女
								</label>
								</span>
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">买方手机号码</label> <input name="spvCustList[0].phone"
								    value="${spvBaseInfoVO.spvCustList[0].phone }"
									type="text" class="form-control input-one" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">买方证件类型</label> <select name="spvCustList[0].idType"
								    value="${spvBaseInfoVO.spvCustList[0].idType }"
									id="" class="form-control input-one">
									<option value="">证件类型</option>
								</select>
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">证件有效期</label> 
								<input type="hidden" name="spvCustList[0].idValiDate" value="${spvBaseInfoVO.spvCustList[0].idValiDate }" >
								<input value="<fmt:formatDate value="${spvBaseInfoVO.spvCustList[0].idValiDate }" pattern="yyyy-MM"/>"
								type="text" class="form-control input-one" placeholder="如：2010-08" >
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">证件编号</label> <input name="spvCustList[0].idCode"
								value="${spvBaseInfoVO.spvCustList[0].idCode }"
								type="text" class="form-control input-two" placeholder="">
							</div>

						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">发证机关</label> <input type="text" name="spvCustList[0].idIssueInst"
								    value="${spvBaseInfoVO.spvCustList[0].idIssueInst }"
									class="form-control input-four" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">买方家庭地址</label> <input name="spvCustList[0].homeAddr"
								    value="${spvBaseInfoVO.spvCustList[0].homeAddr }"
									type="text" class="form-control input-five" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">是否委托他人办理</label> <label
									class="radio-inline"> <input type="radio"
									name="spvCustList[0].hasDele" id="BuyRadio1" value="1" ${spvBaseInfoVO.spvCustList[0].hasDele eq '1'?'checked="checked"':'' } > 是
								</label> <label class="radio-inline"> <input type="radio"
									name="spvCustList[0].hasDele" id="BuyRadio2" value="0" ${spvBaseInfoVO.spvCustList[0].hasDele eq '0'?'checked="checked"':'' }> 否
								</label>
							</div>

						</div>
						<div class="form-row form-rowbot">
							<div
								class="form-group form-margin form-space-one left-extent buyinfo">
								<label for="" class="lable-one">委托人姓名</label> <input type="text" name="spvCustList[0].agentName"
								    value="${spvBaseInfoVO.spvCustList[0].agentName }"
									class="form-control input-one" placeholder="">
							</div>
							<div
								class="form-group form-margin form-space-one left-extent buyinfo">
								<label for="" class="lable-one">委托人证件类型</label> <input name="spvCustList[0].agentIdType"
								    value="${spvBaseInfoVO.spvCustList[0].agentIdType }"
									type="text" class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one buyinfo">
								<label for="" class="lable-one">证件编号</label> <input type="text" name="spvCustList[0].agentIdCode"
								    value="${spvBaseInfoVO.spvCustList[0].agentIdCode }"
									class="form-control input-two" placeholder="">
							</div>
						</div>
					</form>
					<form class="form-inline" >
						<div class="title">卖方客户信息</div>

						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
							    <input type="hidden" name="spvCustList[1].pkid" value="${spvBaseInfoVO.spvCustList[1].pkid }" />
								<label for="" class="lable-one">卖方姓名</label> <input type="text" name="spvCustList[1].name"
								     value="${spvBaseInfoVO.spvCustList[1].name }"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one ">
								<label for="" class="lable-one">卖方性别</label> <span
									class="sex-char"> <label class="radio-inline"> <input
										type="radio" name="spvCustList[1].gender" id="sex3" value="1" ${spvBaseInfoVO.spvCustList[1].gender eq '1'?'checked="checked"':'' }>
										男
								</label> <label class="radio-inline"> <input type="radio"
										name="spvCustList[1].gender" id="sex4" value="0" ${spvBaseInfoVO.spvCustList[1].gender eq '0'?'checked="checked"':'' }> 女
								</label>
								</span>
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">卖方手机号码</label> <input name="spvCustList[1].phone"
								    value="${spvBaseInfoVO.spvCustList[1].phone }"
									type="text" class="form-control input-one" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">卖方证件类型</label> <select name="spvCustList[1].idType"
								    value="${spvBaseInfoVO.spvCustList[1].idType }"
									id="" class="form-control input-one">
									<option value="">证件类型</option>
								</select>
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">证件有效期</label> 
								<input type="hidden" name="spvCustList[1].idValiDate" value="${spvBaseInfoVO.spvCustList[0].idValiDate }" >
								<input type="text" value="<fmt:formatDate value="${spvBaseInfoVO.spvCustList[1].idValiDate }" pattern="yyyy-MM"/>"
									class="form-control input-one" placeholder="如：2010-08">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">证件编号</label> <input type="text" name="spvCustList[1].idCode"
								    value="${spvBaseInfoVO.spvCustList[1].idCode }"
									class="form-control input-two" placeholder="">
							</div>

						</div>
						<div class="form-row form-rowbot">

							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">发证机关</label> <input type="text" name="spvCustList[1].idIssueInst"
								    value="${spvBaseInfoVO.spvCustList[1].idIssueInst }"
									class="form-control input-four" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">卖方家庭地址</label> <input name="spvCustList[1].homeAddr"
								    value="${spvBaseInfoVO.spvCustList[1].homeAddr }"
									type="text" class="form-control input-five" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">是否委托他人办理</label> <label
									class="radio-inline"> <input type="radio"
									name="spvCustList[1].hasDele" id="SellRadio1" value="1" ${spvBaseInfoVO.spvCustList[1].hasDele eq '1'?'checked="checked"':'' }> 是
								</label> <label class="radio-inline"> <input type="radio"
									name="spvCustList[1].hasDele" id="SellRadio2" value="0" ${spvBaseInfoVO.spvCustList[1].hasDele eq '0'?'checked="checked"':'' }> 否
								</label>
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div
								class="form-group form-margin form-space-one left-extent sellinfo">
								<label for="" class="lable-one">委托人姓名</label> <input type="text" name="spvCustList[1].agentName"
								    value="${spvBaseInfoVO.spvCustList[1].agentName }"
									class="form-control input-one" placeholder="">
							</div>
							<div
								class="form-group form-margin form-space-one left-extent sellinfo">
								<label for="" class="lable-one">委托人证件类型</label> <input name="spvCustList[1].agentIdType"
								    value="${spvBaseInfoVO.spvCustList[1].agentIdType }"
									type="text" class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one sellinfo">
								<label for="" class="lable-one">证件编号</label> <input type="text" name="spvCustList[1].idCode"
								    value="${spvBaseInfoVO.spvCustList[1].idCode }"
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
							    <input type="hidden" name="toSpvProperty.pkid" value="${spvBaseInfoVO.toSpvProperty.pkid }" />
								<label for="" class="lable-one">房产证号</label> <input name="toSpvProperty.prNo" type="text"
									class="form-control input-one" placeholder="" value="${spvBaseInfoVO.toSpvProperty.prNo }">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">房产权利人</label> <input name="toSpvProperty.prOwnerName" type="text"
									class="form-control input-one" placeholder="" value="${spvBaseInfoVO.toSpvProperty.prOwnerName }">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">委托人姓名</label> <input name="toSpvProperty.mortgageeName" type="text"
									class="form-control input-one" placeholder="" value="${spvBaseInfoVO.toSpvProperty.mortgageeName }">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">面积</label> <input name="toSpvProperty.prSize" type="text"
									class="form-control input-one" placeholder="" value="${spvBaseInfoVO.toSpvProperty.prSize }"> <span
									class="date_icon">平方米</span>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">房屋地址</label> <input name="toSpvProperty.prAddr" type="text"
									class="form-control input-five" placeholder="" value="${spvBaseInfoVO.toSpvProperty.prAddr }">
							</div>
						</div>
						<div class="form-row form-rowbot"></div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-two">是否偿清</label> <label
									class="radio-inline"> <input type="radio"
									name="toSpvProperty.isMortClear" id="Pledge1" value="1" ${spvBaseInfoVO.toSpvProperty.isMortClear eq '1'?'checked="checked"':'' }> 是
								</label> <label class="radio-inline"> <input type="radio"
									name="toSpvProperty.isMortClear" id="Pledge2" value="0" ${spvBaseInfoVO.toSpvProperty.isMortClear eq '0'?'checked="checked"':'' }> 否
								</label> <span class="span-tag pledgeinfo">交易房屋抵押贷信息</span>
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div
								class="form-group form-margin form-space-one left-extent pledgeinfo">
								<label for="" class="lable-one">抵押方</label> <input type="text" name="toSpvProperty.mortgageeName"
									class="form-control input-one" placeholder="" value="${spvBaseInfoVO.toSpvProperty.mortgageeName }">
							</div>
							<div class="form-group form-margin form-space-one pledgeinfo">
								<label for="" class="lable-one">开户行</label> <input name="toSpvProperty.mortgageeBank"
								value="${spvBaseInfoVO.toSpvProperty.mortgageeBank }" type="text"
									class="form-control input-four" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div
								class="form-group form-margin form-space-one left-extent pledgeinfo">
								<label for="" class="lable-one">未偿还金额</label> <input name="toSpvProperty.leftAmount"
								value="${spvBaseInfoVO.toSpvProperty.leftAmount }" type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one pledgeinfo">
								<label for="" class="lable-one">金额大写</label> <input type="text"
									class="form-control input-one" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">网签合同号</label> <input name="toSpvProperty.signNo"
								value=""${spvBaseInfoVO.toSpvProperty.signNo } type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">下家付款方式</label> <select
									class="form-control input-one">
									<option value="">全数</option>
								</select>
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">网签金额</label> <input name="toSpvProperty.signAmount"
								value="${spvBaseInfoVO.toSpvProperty.signAmount }" type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">元</span>
							</div>
						</div>
					</form>
				</div>
				<div class="ibox-content" id="spvtwo_info">
					<form class="form-inline">
					    <input type="hidden" name="toSpv.pkid" value="${spvBaseInfoVO.toSpv.pkid }"/>
					    <input type="hidden" name="toSpv.caseCode" value="${spvBaseInfoVO.toSpv.caseCode }"/>
						<div class="title">监管资金及账户信息</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">监管总金额</label> <input name="toSpv.amount"
								value="${spvBaseInfoVO.toSpv.amount }" type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">元</span>
							</div>
						</div>
						<div class="title">监管资金的支付</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">自筹资金</label> <input name="toSpv.amountOwn"
								    value="${spvBaseInfoVO.toSpv.amountOwn }" type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">元</span>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">贷款资金</label> <input name="toSpv.amountMort"
								    value="${spvBaseInfoVO.toSpv.amountMort }" type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="rate">其中： 商业贷款</label> <input name="toSpv.amountMortCom"
								    value="${spvBaseInfoVO.toSpv.amountMortCom }" type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">元</span>
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="rate">公积金贷款</label> <input name="toSpv.amountMortPsf"
								    value="${spvBaseInfoVO.toSpv.amountMortPsf }" type="text"
									class="form-control input-one" placeholder=""> <span
									class="date_icon">元</span>
							</div>
						</div>
						<div class="form-row form-rowbot"></div>
						<div class="title">资金监管账号信息</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
							    <input type="hidden" name="toSpvAccountList[1].pkid" value="${spvBaseInfoVO.toSpvAccountList[1].pkid }"/>
								<label for="" class="lable-one">卖方收款账号名称</label> <input name="toSpvAccountList[1].name"
								 value="${spvBaseInfoVO.toSpvAccountList[1].name }"
								 type="text" class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">电话</label> <input name="toSpvAccountList[1].telephone"
								    value="${spvBaseInfoVO.toSpvAccountList[1].telephone }" type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">开户行</label> <input name="toSpvAccountList[1].bank"
								    value="${spvBaseInfoVO.toSpvAccountList[1].bank }" type="text"
									class="form-control input-four" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">账号</label> <input name="toSpvAccountList[1].account"
								    value="${spvBaseInfoVO.toSpvAccountList[1].account }"  type="text"
									class="form-control input-two" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
							    <input type="hidden" name="toSpvAccountList[0].pkid" value="${spvBaseInfoVO.toSpvAccountList[0].pkid }"/>
								<label for="" class="lable-one">买方退款账号名称</label> <input name="toSpvAccountList[0].name"
								    value="${spvBaseInfoVO.toSpvAccountList[0].name }"
									type="text" class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">电话</label> <input name="toSpvAccountList[0].telephone"
								    value="${spvBaseInfoVO.toSpvAccountList[0].telephone }" type="text"
									class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">开户行</label> <input name="toSpvAccountList[0].bank"
								    value="${spvBaseInfoVO.toSpvAccountList[0].bank }" type="text"
									class="form-control input-four" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">账号</label> <input name="toSpvAccountList[0].account"
								    value="${spvBaseInfoVO.toSpvAccountList[0].account }" type="text"
									class="form-control input-two" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
								<label for="" class="lable-one">托管账户名称</label> <input name="toSpvAccountList[2].name"
								    value="${spvBaseInfoVO.toSpvAccountList[2].name }"
									type="text" class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one">
							    <input type="hidden" name="toSpvAccountList[2].pkid" value="${spvBaseInfoVO.toSpvAccountList[2].pkid }"/>
								<label for="" class="lable-one">账号</label> <input type="text" name="toSpvAccountList[2].account"
								    value="${spvBaseInfoVO.toSpvAccountList[2].account }"
									class="form-control input-two" placeholder="">
							</div>
						</div>
						<div class="form-row form-rowbot">
							<div class="form-group form-margin form-space-one left-extent">
							    <input type="hidden" name="toSpvAccountList[3].pkid" value="${spvBaseInfoVO.toSpvAccountList[3].pkid }"/>
								<label for="" class="lable-one">资金方账户名称</label> <input name="toSpvAccountList[3].name"
								    value="${spvBaseInfoVO.toSpvAccountList[3].name }"
									type="text" class="form-control input-one" placeholder="">
							</div>
							<div class="form-group form-margin form-space-one">
								<label for="" class="lable-one">账号</label> <input type="text" name="toSpvAccountList[3].account"
								    value="${spvBaseInfoVO.toSpvAccountList[3].account }"
									class="form-control input-two" placeholder="">
							</div>
						</div>
					</form>
				</div>
				<div class="ibox-content" id="spvthree_info" style="height: 450px;">
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
									<th>操作</th>
								</thead>					
								<tbody id="addTr">
								  <input type="hidden" id="toSpvDeDetailListSize" value="${fn:length(spvBaseInfoVO.toSpvDeDetailList)}" />
								  <input type="hidden" name="ToSpvDe.pkid" value="${spvBaseInfoVO.toSpvDe.pkid}" />
								  <c:forEach items="${spvBaseInfoVO.toSpvDeDetailList }" var="toSpvDeDetail" varStatus="status">
									<tr align="center">
									    <input type="hidden" name="toSpvDeDetailList[${status.index }].pkid" value="${toSpvDeDetail.pkid }" />
										<td class="text-left"><select name="toSpvDeDetailList[${status.index }].deCondCode" class="table-select">
												<option value="">${toSpvDeDetail.deCondCode }</option>
										</select></td>
										<td class="text-left"><select class="table-select">
												<option name="toSpvDeDetailList[${status.index }].payeeAccountId" value="1" ${toSpvDeDetail.payeeAccountId eq 1?'selected="selected"':''} >资金方</option>
												<option name="toSpvDeDetailList[${status.index }].payeeAccountId" value="2" ${toSpvDeDetail.payeeAccountId eq 2?'selected="selected"':''} >卖方</option>
										</select></td>
										<td><input name="toSpvDeDetailList[${status.index }].deAmount" value="${toSpvDeDetail.deAmount }" class="table-input-one" type="text"
											placeholder="请输入金额" />元</td>
										<td class="text-left"><input name="toSpvDeDetailList[${status.index }].deAddition" value="${toSpvDeDetail.deAddition }" class="table-input"
											type="text" placeholder="" /></td>
										<td align="center"><a href="javascript:void(0)"
											onClick="getAtr(this)">添加</span></a>
									</tr>
								   </c:forEach>
								</tbody>					
							</table>
							<div class="form-btn">
								<div>
									<button id="submitBtn" type="submit" class="btn btn-success">提交申请</button>
									<button id="resetBtn"  type="submit" class="btn btn-default">取消</button>
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
		src="${ctx}/static/trans/js/spv/spvDetails.js"></script>
		<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script> 
		<script id="queryCastListItemList" type= "text/html">
        {{each rows as item index}}
    	<tr>
        <td>
            <p class="big">
                <a href="${ctx}/case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank">
                   <span id="modal_caseCode{{index}}">{{item.CASE_CODE}}</span>
                </a>
            </p>
            <p>
                <i class="tag_sign">c</i>
                 {{item.ctmCode}}
            </p>
        </td>
        <td>
            <p class="big">
           		<span id="modal_propertyAddr{{index}}">{{item.PROPERTY_ADDR}}</span>
            </p>
            <p class="tooltip-demo">
                <i class="salesman-icon">
                </i>
                <a class="salesman-info" href="javascript:;" title="" data-toggle="tooltip" data-placement="top" data-original-title="">
                	{{item.AGENT_NAME}}/{{item.AGENT_ORG_NAME}}
                </a>
            </p>
        </td>
        <td>
            <p class="name">
                <span>交易顾问：</span><a href="#" class="a_blue" id="modal_processorId{{index}}">{{item.PROCESSOR_ID}}</a>
            </p>
            <p class="name">
                <span>经纪人：</span><a href="#" class="a_blue" id="modal_agentName{{index}}">{{item.AGENT_NAME}}</a>
            </p>
        </td>
        <td class="center">
            <p class="big">
           		<span id="modal_seller{{index}}">{{item.SELLER}}</span>
            </p>
        </td>
        <td class="center">
            <p class="big">
          	   <span id="modal_buyer{{index}}"> {{item.BUYER}}</span>
            </p>
        </td>
        <td class="text-left">
            <button type="button" class="btn btn-success linkCase" name="linkCase" id="{{index}}">
                              关联案件
            </button>
        </td>
    </tr>
    {{/each}}		
		</script>
		<script>
		$(document).ready(function(){
			
/* 			$('.submit_btn').click(function(){
				//关联案件必须填写
				if(!checkForm()){
					return;
				}
				if(!$.isBlank($("#excutorName").attr('hVal'))) {
					$("#excutorId").val($("#excutorName").attr('hVal'));
				}
				if($.isBlank($("#caseCode").val())) {
					alert('请选择关联案件');
					return false;
				}
				if(validateEloanApply()) {
					saveEloanApply();
				} else {
					alert('该案件的产品已经存在，不允许重复添加');
					return false;
				}
			}) */
			//必填项
			/* function checkForm(){
				var ds = $('.case_content').css('display');
				if(ds=='none'){
					alert("请选择关联案件");
					return false;	
				}
				 var loanSrvCode=$("#loanSrvCode").val();			
				 if(loanSrvCode==null||loanSrvCode==''){
						alert("请选择产品类型");
						return false;	
					}
				 var applyAmount=$("#applyAmount").val();
				 if(applyAmount==null||applyAmount==''){
						alert("请填写申请金额");
						return false;	
					}
				 var date=$("#applyTime").val();
				 if(date==null||date==''){
						alert("请选择申请时间");
						return false;	
					}
				 var month=$('#month').val();
				 if(month==null||month==''){
					 alert("请填写申请期数");
					 return false;	
				 }
				 return true;
			} */

			$(".eloanApply-table").aistGrid({
    			ctx : "${ctx}",
    			queryId : 'queryCastListItemList',
    		    templeteId : 'queryCastListItemList',
    		    rows : '6',
    		    gridClass : 'table table_blue table-striped table-bordered table-hover',
    		    data : '',
    		    wrapperData :{ctx: ctx},
    		    columns : [{
    		    	           colName :"案件编号",
    		    	           sortColumn : "CASE_CODE",
    		    	           sord: "desc",
    		    	           sortActive : true
    		    	      },{
    		    	           colName :"产证地址"
    		    	      },{
   		    	                colName :"工作人员"
		    	          },{
	   		    	           colName :"上家"
			    	      },{
			    	           colName :"下家"
			    	      },{
    		    	           colName :"操作"
    		    	      }]
    		
    		}); 
    		
    	 	// 查询
 			$('#searchButton').click(function() {
 				reloadGrid();
 			});
    	 	
    	 	// 关联案件
   	 		$('.eloanApply-table').on("click",'.linkCase',function(){	 			
   	 			var index = $(this).attr("id");
   	 		    //刷新回到原页面
   	 			window.location.href = "${ctx}/spv/saveHTML?&caseCode="+$("#modal_caseCode"+index).html();
   	 			
/*    	 			$("#content_caseCode").html($("#modal_caseCode"+index).html());
   	 		    $("#caseCode").val($("#modal_caseCode"+index).html());
   	 		    $("#content_propertyAddr").html($("#modal_propertyAddr"+index).html());
   	 	        $("#content_processorId").html($("#modal_processorId"+index).html());
   	            $("#content_buyer").html($("#modal_buyer"+index).html());
             	$("#content_agentName").html($("#modal_agentName"+index).html());
   	            $("#content_seller").html($("#modal_seller"+index).html());
 				$('.case_content').show();
 				$('#myModal').modal('hide'); */
 			});
 			
        });
        
        function reloadGrid() {
        	var propertyAddr = $("#propertyAddr").val();
    	    $(".eloanApply-table").reloadGrid({
    	    	ctx : "${ctx}",
    	    	rows : '6',
    			queryId : 'queryCastListItemList',
    		    templeteId : 'queryCastListItemList',
    		    wrapperData :{ctx : ctx},
    		    data : {propertyAddr:propertyAddr}
    	    })
    	}
		
		</script> 
		</content>

</body>

</html>