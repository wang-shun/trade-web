<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>出账</title>
    <!-- 上传相关 -->
	<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css"
		rel="stylesheet">
	<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css"
		rel="stylesheet">
	<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css"
		rel="stylesheet">
	<!-- 展示相关 -->
	<link href="${ctx}/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css"
		rel="stylesheet">
	<link href="${ctx}/css/trunk/JSPFileUpload/bootstrap-tokenfield.css"
		rel="stylesheet">
	<link href="${ctx}/css/trunk/JSPFileUpload/selectize.default.css"
		rel="stylesheet">
	<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
	<!-- 备件相关结束 -->
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/font-awesome.css">
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/animate.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/style.css" rel="stylesheet">
    <!-- stickUp fixed css -->
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/stickup.css">
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/stickmenu.css">
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/steps.css">
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/toastr.min.css">
    <!-- index_css  -->
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/iconfont.css" >
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/table.css" />
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/input2.css" />
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/see2.css" />
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/spv2.css" />
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/jkresponsivegallery2.css" />
    <style>
	.borderClass {border:1px solid red!important;resize: none;}
	.borderClass:focus {border:1px solid red!important;resize: none;}
   </style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<script type="text/javascript">
	var ctx = "${ctx}";
	var source = "${source}";
	var handle = "${handle}";
	if ("${idList}" != "") {
		var idList = eval("(" + "${idList}" + ")");
	} else {
		var idList = [];
	}
</script>
    <div id="wrapper">
        <%-- 流程相关 --%>
        <form id="procForm" action="">
		<input type="hidden" id="taskId" name="taskId" value="${taskId }">
		<input type="hidden" id="businessKey" name="businessKey" value="${businessKey}">
		<input type="hidden" id="instCode" name="instCode" value="${instCode}">
		<input type="hidden" id="source" name="source" value="${source}">
		<input type="hidden" id="urlType" name="urlType" value="${urlType}">
		<input type="hidden" id="handle" name="handle" value="${handle }">
		<input type="hidden" id="spvCode" name="spvCode" value="${spvCode }">
		<%--附件id --%>
		<input type="hidden" id="insertAttachIdArrStr" name="insertAttachIdArrStr" value="">
		</form>
		<input type="hidden" id="totalCashFlowInAmount" value="${totalCashFlowInAmount }" >
		<input type="hidden" id="totalCashFlowOutAmount" value="${totalCashFlowOutAmount }" >
		<!-- main Start -->
            <!-- main Start -->

            <div class="row">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <!-- <div class="ibox"> -->

                    <div class="ibox-content space30" >
                        <div class="agree-tile">
                            资金出账申请
                        </div>
                        <div class="info_content">
                            <div class="line">
                                <p>
                                    <label>
                                       产品类型
                                    </label>
                                    <span class="info_one">${spvBaseInfoVO.toSpv.prdCode eq 1?'光大四方资金监管':'' }</span>
                                </p>
                                <p>
                                    <label>
                                        监管金额
                                    </label>
                                    <span class="info_one">${spvBaseInfoVO.toSpv.amount }万</span>
                                </p>

                                <p>
                                    <label>
                                        物业地址
                                    </label>
                                    <span class="info">${spvBaseInfoVO.toSpvProperty.prAddr }</span>
                                </p>

                            </div>
                            <div class="line">
                                <p>
                                    <label>
                                        收款人名称
                                    </label>
                                    <span class="info_one">${spvBaseInfoVO.toSpvAccountList[1].name }</span>
                                </p>

                                <p>
                                    <label>
                                        收款人账户
                                    </label>
                                    <span class="info_one">${spvBaseInfoVO.toSpvAccountList[1].account }</span>
                                </p>

                                <p>
                                    <label>
                                        收款人开户行
                                    </label>
                                    <span class="info">${spvBaseInfoVO.toSpvAccountList[1].bank }</span>
                                </p>
                            </div>
                        </div>
                        <div class="mt20">
                            <div class="agree-tile">
                                资金放款方案
                            </div>
                            <form class="form-inline table-capital">
                                <div class="table-box" >
                                    <table class="table table-bordered  customerinfo" id="cashFlowRecord">
                                        <thead>
                                        <tr>
                                            <th>
                                                次数
                                            </th>
                                            <th>
                                                划转条件
                                            </th>
                                            <th>
                                                每次划转金额
                                            </th>
                                            <th>
                                                卖方账户
                                            </th>
                                            <th>
                                                资金方
                                            </th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:set var="sumTotalAmt" value="0"/>
                                        <c:set var="sumSellerAmt" value="0"/>
                                        <c:set var="sumFundAmt" value="0"/>
                                        <c:forEach items="${deDetailMixList }" var="mix" varStatus="status1">
                                        <c:set var="sumTotalAmt" value="${sumTotalAmt + mix.totalDeAmount }" />
                                        <c:set var="sumSellerAmt" value="${sumSellerAmt + mix.sellerDeAmount }"/>
                                        <c:set var="sumFundAmt" value="${sumFundAmt + mix.fundDeAmount }"/>
                                        <tr>
                                            <td>
                                                <c:if test="${status1.count eq 1 }">
                                                ${fn:length(deDetailMixList) }
                                                </c:if>
                                            </td>
                                            <td>
                                                <aist:dict id="" name="" clazz="form-control input-one"
									            display="select"  dictType="SPV_DE_COND"  
									            ligerui='none' defaultvalue="${mix.deCondCode }" ></aist:dict>
                                            </td>
                                            <td>
                                                ${mix.totalDeAmount }万
                                            </td>
                                            <td>
                                                ${mix.sellerDeAmount }万
                                            </td>
                                            <td>
                                                ${mix.fundDeAmount }万
                                            </td>
                                        </tr>
                                        </c:forEach>
                                        <tr>
                                            <td>
                                            </td>
                                            <td>
                                                合计
                                            </td>
                                            <td>
                                                ${sumTotalAmt }万
                                            </td>
                                            <td>
                                                ${sumSellerAmt }万
                                            </td>
                                            <td>
                                                ${sumFundAmt }万
                                            </td>
                                        </tr>                                       
                                        </tbody>
                                    </table>
                                </div>
                            </form>
                        </div>
                        <div class="mt20">
                            <div class="agree-tile">
                                出入账记录
                            </div>
                            <div class="table-capital">
                             <form>
                                <div class="table_content">
                                    <table class="table table-bordered customerinfo">
                                        <thead>
                                        <tr>
                                            <th>流水编号</th>
                                            <th>金额</th>
                                            <th>账户信息</th>
                                            <th>审批状态</th>
                                            <th>物业地址</th>
                                        </tr>
                                        </thead>
                                        <tbody> 
                                        <c:forEach items="${cashFlowList}" var="cashFlow" varStatus="status2">
                                        <tr>
                                            <td>
                                                <p class="big">
                                                    <a href="javascript:void(0);">
                                                        ${cashFlow.cashflowApplyId }
                                                    </a>
                                                </p>
                                                <p>
                                                    转账
                                                    <a href="javascript:;" class="under font12">
                                                        凭证编号
                                                    </a>
                                                </p>
                                            </td>
                                            <td>
                                                <p class="big">
                                                    <span class="sign_normal navy_bg">
                                                    ${cashFlow.usage eq in?'入账':'出账' }
                                                    </span>
                                                </p>
                                                <p class="big">
                                                    ${cashFlow.amount }万
                                                </p>
                                            </td>
                                            <td>
                                                <p><span class="pink">付：</span>${cashFlow.payer }&nbsp;&nbsp;${cashFlow.payerAcc }/${cashFlow.payerBank }</p>
                                                <p><span class="navy">收：</span>${cashFlow.receiver }&nbsp;&nbsp;${cashFlow.receiverAcc }/${cashFlow.receiverBank }</p>
                                            </td>
                                            <td>
                                                <p class="smll_sign">
                                                    <i class="sign_normal">录入</i>
                                                    <a href="javascript:void(0)">${cashFlow.createByName }&nbsp;</a><fmt:formatDate value="${cashFlow.createTime }" pattern="yyyy-MM-dd"/>
                                                </p>
                                                <p class="smll_sign">
                                                    <i class="sign_normal">结束</i>             
                                                    <fmt:formatDate value="${cashFlow.closeTime }" pattern="yyyy-MM-dd"/>
                                                </p>
                                            </td>
                                            <td>
                                                <p>
                                                   上海市长宁区延安西路998号1弄202
                                                </p>
                                                <p class="smll_sign">
                                                    审核人：<a href="javascript:void(0)">
                                                    ${empty cashFlow.applyAuditorName?'?':cashFlow.applyAuditorName }&gt;${empty cashFlow.ftPreAuditorName?'?':cashFlow.ftPreAuditorName }&gt;${empty cashFlow.ftPostAuditorName?'?':cashFlow.ftPostAuditorName }</a>
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
                        <form id="workFolwApplyForm" style="display:none;">
                              <input type="hidden" name="toSpvCashFlowApply.pkid" value="${spvChargeInfoVO.toSpvCashFlowApply.pkid }" />
                              <input type="hidden" name="toSpvCashFlowApply.cashflowApplyCode" value="${spvChargeInfoVO.toSpvCashFlowApply.cashflowApplyCode }" />
                              <input type="hidden" name="toSpvCashFlowApply.spvCode" value="${empty spvCode?spvChargeInfoVO.toSpvCashFlowApply.spvCode:spvCode }" />
                              <input type="hidden" name="toSpvCashFlowApply.usage" value="out" />
                        </form>
                        <div class="mt20">
                            <div class="agree-tile">
                                出账凭证信息
                            </div>
                            <form class="form-inline table-capital">
                                <div class="table-box" >
                                    <table class="table table-bordered">
                                        <thead>
                                        <th>出账条件</th>
                                        <th>附件</th>
                                        </thead>
                                        <tbody id="addTr2">
                                        <%-- <c:forEach items="${spvChargeInfoVO.toSpvCashFlowApplyAttachList }" var="toSpvCashFlowApplyAttach" varStatus="status3"> --%>
                                        <tr>
                                            <td width="310">
                                                <aist:dict id="" name="" clazz="form-control input-one"
									            display="select"  dictType="SPV_DE_COND"  
									            ligerui='none' defaultvalue="" ></aist:dict>
                                            </td>
                                            <td>
                                                <span class="btn_file">
                                                    <input type="file" class="file" />
                                                    <img class="bnt-flie" src="${ctx }/static_res/trans/img/bnt-flie.png" alt="" />
                                                </span> 
                                            </td>
                                        </tr>
                                        <%-- </c:forEach> --%>
                                        <%-- <c:if test="${empty spvChargeInfoVO.toSpvCashFlowApplyAttachList }">
                                        <tr><td colspan="5">没有相关信息</td></tr>
                                        </c:if> --%>
                                        </tbody>
                                    </table>

                                </div>
                            </form>
                        </div>
                        <div class="mt20">
                            <div class="agree-tile">
                                出账申请信息
                            </div>
                            <form class="form-inline table-capital">
                                <div class="table-box" >
                                    <table class="table table-bordered customerinfo" id="cashFlowTable">
                                        <thead>
                                        <th style="width: 100px;">付款人姓名</th>
                                        <th>付款人账户</th>
                                        <th style="width: 100px;">出账金额</th>
                                        <th style="width: 120px;">贷记凭证编号</th>
                                        <th>付款方式</th>
                                        <th>凭证附件</th>
                                        <th>操作</th>
                                        </thead>
                                        <tbody id="addTr">
                                        <%-- <c:if test="${not empty spvChargeInfoVO.spvCaseFlowOutInfoVOList }">
                                        <c:forEach items="spvChargeInfoVO.spvCaseFlowOutInfoVOList" var="spvCaseFlowOutInfoVO" varStatus="status">
                                        <tr>
                                            <td>
                                                <input class="table-input-one boderbbt" type="text" placeholder="请输入姓名" name="spvCaseFlowOutInfoVOList[${status.index }].toSpvCashFlow.payer" />
                                            </td>
                                            <td>
                                                <p><input class="table_input boderbbt" type="text" placeholder="请输入银行卡号" name="spvCaseFlowOutInfoVOList[${status.index }].toSpvCashFlow.payerAcc" /></p>
                                                <p>
                                                <p><input class="table_input boderbbt" type="text" placeholder="请输入银行名称" name="spvCaseFlowOutInfoVOList[${status.index }].toSpvCashFlow.payerBank" /></p>
                                                <p>
                                            </td>

                                            <td class="text-left" >
                                                <input class="boderbbt" style="border:none;width: 50px;" type="text" placeholder="金额" name="spvCaseFlowOutInfoVOList[${status.index }].toSpvCashFlow.amount" />万
                                            </td>
                                            <td>
                                                <input class="table_input boderbbt" type="text" placeholder="请输入编号" name="spvCaseFlowOutInfoVOList[${status.index }].toSpvCashFlow.voucherNo" />
                                            </td>
                                            <td>
                                                <select name="spvCaseFlowOutInfoVOList[${status.index }].toSpvCashFlow.direction" class="table-select boderbbt" onChanege="this.value">
                                                    <option value="">请选择</option>
                                                    <option value="转账">转账</option>
                                                    <option value="刷卡">刷卡</option>
                                                    <option value="现金">现金</option>
                                                </select>
                                            </td>
                                            <td>
                                            <c:choose>
					<c:when test="${accesoryList!=null}">
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
										<div class="" >
											<div role="presentation" >											    
												<div id="picContainer${accesory.pkid }" class="files"
													data-toggle="modal-gallery" data-target="#modal-gallery"></div>
												<c:forEach items="${spvChargeInfoVO.toSpvCashFlowApplyAttachList }">	
												<a id="chandiaotuBtn" class="response" href="${ctx }/static_res/trans/img/uplody01.png"  title="凭证3">
												<button type="button" class="btn btn-sm btn-default">凭证3
                                                <c:if test="${empty handle or handle eq 'apply' }"><i class="icon iconfont icon_x">&#xe60a;</i></c:if>
                                                <c:if test="${handle eq 'directorAduit' or handle eq 'financeAduit' or handle eq 'financeSecondAduit' or handle eq 'cashFlowOut' }"><i class="icon iconfont icon_y">&#xe635;</i></c:if>
                                                </button>
												</a>
												</c:forEach>
												<span class="btn_file">
                                                    <input id="picFileupload${accesory.pkid }" type="file" class="file"
													name="files[]" multiple
													data-url="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"
													data-sequential-uploads="true">
                                                    <img class="bnt-flie" src="${ctx }/static_res/trans/img/bnt-flie.png" alt="" />
												</span>
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
 												 <img src="${imgweb}/filesvr/downLoad?id={%=file.id%}" alt="" width="80px" height="80px">
  											  {% } %}
							            {% } %}</div>
							        {% } %}
							        <div class="delete span2" style="margin-left:85%;margin-top:-120px;">
							           <button class="btn red" style="line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;">
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
				</c:choose>
                                               <%--  <a class="response" href="${ctx }/static_res/trans/img/uplody01.png" title="凭证3"><button type="button" class="btn btn-sm btn-default" >凭证3<i class="icon iconfont icon_x">&#xe60a;</i></button></a>
                                                <a class="response" href="${ctx }/static_res/trans/img/uplody02.png" title="凭证4"><button type="button" class="btn btn-sm btn-default" >凭证4<i class="icon iconfont icon_x">&#xe60a;</i></button></a>
                                                <span class="btn_file">
                                                    <input type="file" class="file" />
                                                    <img class="bnt-flie" src="${ctx }/static_res/trans/img/bnt-flie.png" alt="" />
                                                </span>
                                            </td>
                                            <td align="center"><a href="javascript:void(0)" onClick="getAtr(this)">添加</span></a>
                                            </td>
                                        </tr>
              </c:forEach></c:if>  
              --%>
                                   <tr>
                                            <td>
                                                <input class="table-input-one boderbbt" type="text" placeholder="请输入姓名" name="spvCaseFlowOutInfoVOList[0].toSpvCashFlow.payer" />
                                            </td>
                                            <td>
                                                <p><input class="table_input boderbbt" type="text" placeholder="请输入银行卡号" name="spvCaseFlowOutInfoVOList[0].toSpvCashFlow.payerAcc" /></p>
                                                <p>
                                                <p><input class="table_input boderbbt" type="text" placeholder="请输入银行名称" name="spvCaseFlowOutInfoVOList[0].toSpvCashFlow.payerBank" /></p>
                                                <p>
                                            </td>

                                            <td class="text-left" >
                                                <input class="boderbbt" style="border:none;width: 50px;" type="text" placeholder="金额" name="spvCaseFlowOutInfoVOList[0].toSpvCashFlow.amount" />万
                                            </td>
                                            <td>
                                                <input class="table_input boderbbt" type="text" placeholder="请输入编号" name="spvCaseFlowOutInfoVOList[0].toSpvCashFlow.voucherNo" />
                                            </td>
                                            <td>
                                                <select name="spvCaseFlowOutInfoVOList[0].toSpvCashFlow.direction" class="table-select boderbbt" onChanege="this.value">
                                                    <option value="">请选择</option>
                                                    <option value="转账">转账</option>
                                                    <option value="刷卡">刷卡</option>
                                                    <option value="现金">现金</option>
                                                </select>
                                            </td>
                                            <td>
                                            <c:choose>
					<c:when test="${accesoryList!=null}">
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
										<div class="" >
											<div role="presentation" >											    
												<div id="picContainer${accesory.pkid }" class="files"
													data-toggle="modal-gallery" data-target="#modal-gallery"></div>
											    <%-- <c:forEach items="${spvChargeInfoVO.toSpvCashFlowApplyAttachList }">--%>
												<a id="chandiaotuBtn" class="response" href="${ctx }/static_res/trans/img/uplody01.png"  title="凭证3">
												<button type="button" class="btn btn-sm btn-default">凭证3
                                                <c:if test="${empty handle or handle eq 'apply' }"><i class="icon iconfont icon_x">&#xe60a;</i></c:if>
                                                <c:if test="${handle eq 'directorAduit' or handle eq 'financeAduit' or handle eq 'financeSecondAduit' or handle eq 'cashFlowOut' }"><i class="icon iconfont icon_y">&#xe635;</i></c:if>
                                                </button>
												</a>
												<%-- </c:forEach>--%>
												<span class="btn_file">
                                                    <input id="picFileupload${accesory.pkid }" type="file" class="file"
													name="files[]" multiple
													data-url="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"
													data-sequential-uploads="true">
                                                    <img class="bnt-flie" src="${ctx }/static_res/trans/img/bnt-flie.png" alt="" />
												</span>
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
 												 <img src="${imgweb}/filesvr/downLoad?id={%=file.id%}" alt="" width="80px" height="80px">
  											  {% } %}
							            {% } %}</div>
							        {% } %}
							        <div class="delete span2" style="margin-left:85%;margin-top:-120px;">
							           <button class="btn red" style="line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;">
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

				</c:choose>
                                               <%--  <a class="response" href="${ctx }/static_res/trans/img/uplody01.png" title="凭证3"><button type="button" class="btn btn-sm btn-default" >凭证3<i class="icon iconfont icon_x">&#xe60a;</i></button></a>
                                                <a class="response" href="${ctx }/static_res/trans/img/uplody02.png" title="凭证4"><button type="button" class="btn btn-sm btn-default" >凭证4<i class="icon iconfont icon_x">&#xe60a;</i></button></a> --%>
                                                <%-- <span class="btn_file">
                                                    <input type="file" class="file" />
                                                    <img class="bnt-flie" src="${ctx }/static_res/trans/img/bnt-flie.png" alt="" />
                                                </span> --%>
                                            </td>
                                            <td align="center"><a href="javascript:void(0)" onClick="getAtr(this)">添加</span></a>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>

                                </div>
                            </form>
                        </div>

                        <div class="ibox-content clearfix space30">
                        <div class="title">
                            <strong>审核意见</strong>
                        </div>
                        <div class="view-content">
                            <div class="view-box">
                            <c:forEach items="${spvChargeInfoVO.toSpvAduitList }" var="toSpvAduit" varStatus="status4">
                            <input type="hidden" name="toSpvAduitList[${status4.index }].pkid" value="${toSpvAduit.pkid }" />
                            <div class="view clearfix">
                                <p>
                                   <span class="auditor">审核人：<em>${toSpvAduit.operatorName }(${toSpvAduit.operatorJobName })</em></span>
                                   <span class="result pink_bg">${toSpvAduit.result }</span>
                                   <span class="time">审核日期:<em><fmt:formatDate value="${toSpvAduit.createTime }" pattern="yyyy-MM-dd"/> </em></span>
                                </p>
                                <p>
                                    <span class="auditing">审核意见</span>
                                    <em class="view_content">${toSpvAduit.content }</em>
                                </p>
                            </div>
                            </c:forEach>
                        </div>
                        </div>
                        
                        <c:if test="${handle eq 'directorAduit' or handle eq 'financeAduit' or handle eq 'financeSecondAduit' }">
                        <div class="submitter">
                            提交人：<span>${user.realName }(${user.serviceJobName })</span>
                        </div>
                        <div class="excuse">
                            <form action="">
                                <textarea name="toSpvAduitList[0].content" placeholder="请填写审核意见" style="width:100%; resize: none;height:140px;border-radius: 3px;border: 1px solid #d8d8d8;padding:10px;"></textarea>
                            </form>
                        </div>  
                        </c:if>
                    </div>

                    <div class="form-btn">
                            <div class="text-center">
                            <c:if test="${empty handle }">
                                <button id="none_save_btn" class="btn btn-success mr15">保存</button>
                                <button id="none_submit_btn" class="btn btn-success mr15">提交</button>
                                <button onclick="rescCallbocak()" class="btn btn-default">关闭</button>
                            </c:if>
                            <c:if test="${handle eq 'apply' }">
                                <button id="apply_save_btn" class="btn btn-success mr15">保存</button>
                                <button id="apply_submit_btn" class="btn btn-success mr15">提交</button>
                                <button onclick="rescCallbocak()" class="btn btn-default">关闭</button>
                            </c:if>
                            <c:if test="${handle eq 'directorAduit' }">
                                <button id="directorAduit_pass_btn" class="btn btn-success btn-space">审批通过</button>
                                <button id="directorAduit_reject_btn" class="btn btn-pink btn-space">审批驳回</button>
                                <button onclick="rescCallbocak()" class="btn btn-default">关闭</button>
                            </c:if>
                            <c:if test="${handle eq 'financeAduit' }">
                                <button id="financeAduit_pass_btn" class="btn btn-success btn-space">审批通过</button>
                                <button id="financeAduit_reject_btn" class="btn btn-pink btn-space">审批驳回</button>
                                <button onclick="rescCallbocak()" class="btn btn-default">关闭</button>
                            </c:if>
                            <c:if test="${handle eq 'financeSecondAduit' }">
                                <button id="financeSecondAduit_pass_btn" class="btn btn-success btn-space">审批通过</button>
                                <button id="financeSecondAduit_reject_btn" class="btn btn-pink btn-space">审批驳回</button>
                                <button onclick="rescCallbocak()" class="btn btn-default">关闭</button>
                            </c:if>
                            <c:if test="${handle eq 'cashFlowOut' }">
                                <button id="cashFlowOut_submit_btn" class="btn btn-success mr15">提交</button>
                                <button onclick="rescCallbocak()" class="btn btn-default">关闭</button>
                            </c:if>    
                            </div>
                        </div>
                </div>
            </div>
            <!-- main End -->
        </div>
    </div>
    <!-- Mainly scripts -->
    <content tag="local_script">
    <script src="${ctx}/static_res/trans/js/spv/jquery-2.1.1.js"></script>
    <script src="${ctx}/static_res/trans/js/spv/jquery.metisMenu.js"></script>
    <script src="${ctx}/static_res/trans/js/spv/jquery.slimscroll.min.js"></script>
    <!-- Custom and plugin javascript -->
    <script src="${ctx}/static_res/trans/js/spv/inspinia.js"></script>
    <script src="${ctx}/static_res/trans/js/spv/pace.min.js"></script>
    <script src="${ctx}/static/js/plugins/toastr/toastr.min.js"></script> 
    <!-- 上传附件相关 --> 
    <script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/tmpl.min.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/load-image.min.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-fp.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-ui.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
	<script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script> 
	<!-- 上传附件 结束 -->
	<!-- 附件保存修改相关 --> <script src="${ctx}/static_res/trans/js/spv/attachment.js"></script>
    <!-- stickup plugin -->
    <script src="${ctx}/static_res/trans/js/spv/jkresponsivegallery.js"></script>
    <script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> 
    <script src="${ctx}/js/template.js" type="text/javascript"></script> <!-- stickup plugin -->
    <script src="${ctx}/static_res/trans/js/spv/spvRecorded.js"></script>

<script>

$(function() {
    $(".icon_x").click(function() {
        $(this).parent().parent().remove();
        return false;
    });


});
$('.response').responsivegallery();


var sum = parseInt("${fn:length(spvChargeInfoVO.spvCaseFlowOutInfoVOList)}")+1;

function getAtr(i){
    $str='';
    $str+="<tr>";
    $str+="<input type='hidden' name='spvCaseFlowOutInfoVOList["+sum+"].toSpvCashFlow.pkid' >";
    $str+=" <td><input class='table-input-one' type='text' placeholder='请输入姓名' name='spvCaseFlowOutInfoVOList["+sum+"].toSpvCashFlow.payer' /></td>";
    $str+="<td><p><input class='table_input' type='text' placeholder='请输入银行卡号' name='spvCaseFlowOutInfoVOList["+sum+"].toSpvCashFlow.payerAcc' /></p><p><input class='table_input' type='text' placeholder='请输入银行名称' name='spvCaseFlowOutInfoVOList["+sum+"].toSpvCashFlow.payerBank' /></p></td>";
    $str+="<td><input style='border:none;width: 50px;' type='text' placeholder='金额' name='spvCaseFlowOutInfoVOList["+sum+"].toSpvCashFlow.amount' />万</td>";
    $str+="<td><input class='table_input' type='text' placeholder='请输入编号' name='spvCaseFlowOutInfoVOList["+sum+"].toSpvCashFlow.amount' /></td>";
    $str+="<td><select class='table-select' name='spvCaseFlowOutInfoVOList["+sum+"].toSpvCashFlow.direction' onChange='this.value'><option value=''>请选择</option><option value='转账'>转账</option><option value='刷卡'>刷卡</option><option value='现金'>现金</option></select></td>";
    $str+="<td><button class='btn btn-sm btn-x space3'>凭证1<i class='icon iconfont icon_x'>&#xe60a;</i></button><button class='btn btn-sm btn-x space3'>凭证2<i class='icon iconfont icon_x'>&#xe60a;</i></button><span class='btn_file'><input type='file' class='file' /><img class='bnt-flie' src='${ctx }/static_res/trans/img/bnt-flie.png' alt='' /></span></td>";
    $str+="<td class='btn-height' align='center'><a href='javascript:void(0);'  onClick='getAtr(this)'  >添加</a><a onClick='getDel(this)' class='grey' href='javascript:void(0)' >删除</a></td>";
    $str+="</tr>";
    $("#addTr").append($str);
    sum++;
    $("#sum").html(sum);
}

function getDel(k){
    $(k).parents('tr').remove();
    sum--;
    $("#sum").html(sum);
}

</script>
</content>
</body>
</html>