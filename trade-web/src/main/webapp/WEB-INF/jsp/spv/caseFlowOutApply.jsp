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
    <link href="${ctx}/js/viewer/viewer.min.css" rel="stylesheet" />
    <style>
	.borderClass {border:1px solid red!important;resize: none;}
	.borderClass:focus {border:1px solid red!important;resize: none;}
	.bar {height: 18px;background: green;position:fixed;bottom:0;}
   </style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<script type="text/javascript">
	var ctx = "${ctx}";
	var source = "${source}";
	var handle = "${handle}";
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

		</form>
		<%--出入账金额比较 --%>
		<input type="hidden" id="totalCashFlowInAmount" value="${totalCashFlowInAmount }" >
		<input type="hidden" id="totalCashFlowOutAmount" value="${totalCashFlowOutAmount }" >
		<%--上传图片添加的图片和上传成功的图片数量，用于确认是否完全上传成功 --%>
		<input type="hidden" id="addSum" value="0" >
		<input type="hidden" id="doneSum" value="0" >
		<%--流水--%>
		<input type="hidden" id="sum" value="${fn:length(spvChargeInfoVO.spvCaseFlowOutInfoVOList)}" >
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
                                        <th>凭证类型</th>
                                        <th>附件</th>
                                        </thead>
                                        <tbody id="addTr2">
                                        <tr>
                                            <td width="310">
                                                <select name="" class="table-select boderbbt">
                                                    <option value="">请选择</option>
                                                    <option value="">转账凭证</option>
                                                </select>
                                            </td>
                                            <td id="td_filex">
                                                <c:forEach items="${spvChargeInfoVO.toSpvCashFlowApplyAttachList }" var="toSpvCashFlowApplyAttach" varStatus="status">
	                                                 	<span>
	                                                 	<img id="image_${status.index }" href="${imgweb}/filesvr/downLoad?id=${toSpvCashFlowApplyAttach.attachId}" style="width:0px;height:0px;display: none;" title="${toSpvCashFlowApplyAttach.comment}" alt="${toSpvCashFlowApplyAttach.comment}"  class="viewer-toggle" />
	                                                 	<input type="hidden" name ="toSpvCashFlowApplyAttachList[${status.index }].pkid" value = "${toSpvCashFlowApplyAttach.pkid}"/>
														<input type="hidden" name ="toSpvCashFlowApplyAttachList[${status.index }].attachId" value = "${toSpvCashFlowApplyAttach.attachId}"/>
														<input type="hidden" name ="toSpvCashFlowApplyAttachList[${status.index }].comment" value = "${toSpvCashFlowApplyAttach.comment}" />
															<button type="button" class="btn btn-sm btn-default" style="margin-right:5px;margin-top:12px;" onClick="showImg('#image_${status.index }')" >${toSpvCashFlowApplyAttach.comment}
 															<c:if test="${empty handle or handle eq 'apply' }">
																<i class="icon iconfont icon_x" onClick="removeImg(this,event);">&#xe60a;
																</i>
															</c:if>
															<c:if test="${handle eq 'directorAduit' or handle eq 'financeAduit' or handle eq 'financeSecondAduit' or handle eq 'cashFlowOut' }">
															    <i class="icon iconfont icon_y" >&#xe635;
																</i>
															</c:if> 
															</button>
														</span>	
                                                </c:forEach>  
                                              	 <c:if test="${empty handle or handle eq 'apply' }">
                                                	 <span class="btn_file_x">                                                                                                                                                               
       			                                             <input id="fileupload_x" style="display:none" type="file" name="files[]" multiple="" data-url="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload" data-sequential-uploads="true"> 
			                                                      <label class="bnt-flie" alt="点击上传" style="positon:relative;display:inline-block;height:34px;width:52px;cursor:pointer; background-image:url(${ctx}/static/trans/img/bnt-flie.png)" onClick="$('#fileupload_x').trigger('click');"/>;
		                                             </span>
		                                         </c:if> 
                                            </td>
                                        </tr>
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
                                        	<c:forEach items="${spvChargeInfoVO.spvCaseFlowOutInfoVOList}" var="spvCaseFlowOutInfoVO" varStatus="status2">
	                                           	<tr>
	                                           	    <input type="hidden" name="spvCaseFlowOutInfoVOList[${status2.index }].toSpvCashFlow.pkid"   value="${spvCaseFlowOutInfoVO.toSpvCashFlow.pkid }" />
	                                           	    <input type="hidden" name="spvCaseFlowOutInfoVOList[${status2.index }].toSpvCashFlow.spvCode"   value="${spvCaseFlowOutInfoVO.toSpvCashFlow.spvCode }" />
	                                                <td>
	                                                    <input class="table-input-one boderbbt" type="text" name="spvCaseFlowOutInfoVOList[${status2.index }].toSpvCashFlow.payer"  placeholder="请输入姓名"  value="${spvCaseFlowOutInfoVO.toSpvCashFlow.payer }" />
	                                                </td>
	                                                <td>
	                                                    <p><input class="table_input boderbbt" type="text" value="${spvCaseFlowOutInfoVO.toSpvCashFlow.payerAcc }" placeholder="请输入银行卡号"  name="spvCaseFlowOutInfoVOList[${status2.index }].toSpvCashFlow.payerAcc" /></p>
	                                                    <p>
	                                                    <p><input class="table_input boderbbt" type="text" value="${spvCaseFlowOutInfoVO.toSpvCashFlow.payerBank }" placeholder="请输入银行名称" name="spvCaseFlowOutInfoVOList[${status2.index }].toSpvCashFlow.payerBank"/></p>
	                                                    <p>
	                                                </td>
	
	                                                <td class="text-left" >
	                                                    <input class="boderbbt" style="border:none;width: 50px;" type="text" value="${spvCaseFlowOutInfoVO.toSpvCashFlow.amount }" placeholder="金额" name="spvCaseFlowOutInfoVOList[${status2.index }].toSpvCashFlow.amount" />万
	                                                </td>
	                                                <td>
	                                                    <input class="table_input boderbbt" type="text" value="${spvCaseFlowOutInfoVO.toSpvCashFlow.voucherNo }" placeholder="请输入编号" name="spvCaseFlowOutInfoVOList[${status2.index }].toSpvCashFlow.voucherNo" />
	                                                </td>
	                                                <td>
	                                                    <select  id="select_direction" class="table-select boderbbt" name="spvCaseFlowOutInfoVOList[${status2.index }].toSpvCashFlow.direction"  onChange="this.value" >
	                                                        <option <c:if test="${spvCaseFlowOutInfoVO.toSpvCashFlow.direction eq ''}"> selected="selected" </c:if> value="">请选择</option>
	                                                        <option <c:if test="${spvCaseFlowOutInfoVO.toSpvCashFlow.direction eq '转账'}"> selected="selected" </c:if> value="转账">转账</option>                                                                                                                                                
															<option <c:if test="${spvCaseFlowOutInfoVO.toSpvCashFlow.direction eq '刷卡'}"> selected="selected" </c:if> value="刷卡">刷卡</option>  
															<option <c:if test="${spvCaseFlowOutInfoVO.toSpvCashFlow.direction eq '现金'}"> selected="selected" </c:if> value="现金">现金</option> 
	                                                    </select>
	                                                </td>
	                                                <td id="td_file${status2.index  }">
                                                	<c:forEach items="${spvCaseFlowOutInfoVO.toSpvVoucherList}" var="toSpvVoucher" varStatus="status3">
	                                                 	<span>
	                                                 	<img id="image_${status3.index }" src="${imgweb}/filesvr/downLoad?id=${toSpvVoucher.attachId}" style="width:0px;height:0px;display: none;" title="${toSpvVoucher.comment}" alt="${toSpvVoucher.comment}" class="viewer-toggle" />
	                                                 	<input type="hidden" name ="spvCaseFlowOutInfoVOList[${status2.index }].toSpvVoucherList[${status3.index}].pkid" value = "${toSpvVoucher.pkid}"/>
														<input type="hidden" name ="spvCaseFlowOutInfoVOList[${status2.index }].toSpvVoucherList[${status3.index}].attachId" value = "${toSpvVoucher.attachId}"/>
														<input type="hidden" name ="spvCaseFlowOutInfoVOList[${status2.index }].toSpvVoucherList[${status3.index}].comment" value = "${toSpvVoucher.comment}" />
															<button type="button" class="btn btn-sm btn-default" style="margin-right:5px;margin-top:12px;" onClick="showImg('#image_${status3.index }')">${toSpvVoucher.comment}
															<c:if test="${empty handle or handle eq 'apply' }">
																<i class="icon iconfont icon_x" onClick="removeImg(this,event);">&#xe60a;
																</i>
															</c:if>
															<c:if test="${handle eq 'directorAduit' or handle eq 'financeAduit' or handle eq 'financeSecondAduit' or handle eq 'cashFlowOut' }">
															    <i class="icon iconfont icon_y" >&#xe635;
																</i>
															</c:if>	
															</button>
														</span>	
                                                	 </c:forEach>  
                                                	 <c:if test="${empty handle or handle eq 'apply' }">
                                                	 <span class="btn_file${status2.index}">                                                                                                                                                               
       			                                             <input id="fileupload_${status2.index}" style="display:none" type="file" name="files[]" multiple="" data-url="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload" data-sequential-uploads="true">
			                                                      <label class="bnt-flie" alt="点击上传" style="positon:relative;display:inline-block;height:34px;width:52px;cursor:pointer; background-image:url(${ctx}/static/trans/img/bnt-flie.png)" onClick="$('#fileupload_${status2.index}').trigger('click');"/>;
		                                             </span>
		                                             </c:if>   
	                                                </td>
	                                                <c:if test="${empty handle or handle eq 'apply' }">
	                                                <td align="center">
		                                                <a href="javascript:void(0)" onClick="getTR(parseInt($('#sum').val())+1)">添加</span></a>
		                                                <a href="javascript:void(0)" onClick="getDel(this)">删除</span></a>
	                                                </td>
	                                                </c:if>
	                                            </tr>
	                                       </c:forEach>
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
            <div id="progress">
                <div class="bar" style="width: 0%;">
                    <span></span>
                </div>
            </div>
        </div>
    </div>
    <!-- Mainly scripts -->
    <content tag="local_script">
    <!-- Custom and plugin javascript -->
    <script src="${ctx}/static_res/trans/js/spv/pace.min.js"></script>
    <script src="${ctx}/static/js/plugins/toastr/toastr.min.js"></script> 
    <!-- 上传附件相关 --> 
    <script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script>
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
	<!-- 附件保存修改相关 -->
    <!-- stickup plugin -->
    <script src="${ctx}/static_res/trans/js/spv/jkresponsivegallery.js"></script>
    <script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> 
    <script src="${ctx}/js/template.js" type="text/javascript"></script> <!-- stickup plugin -->
    <script src="${ctx}/static_res/trans/js/spv/spvRecorded.js"></script>
    <script src="${ctx}/js/viewer/viewer.min.js"></script>
<script>
var sum = $("#sum").val();
var attSum = $("#attSum").val();
var addSum = 0;
var doneSum = 0;

$(function() {
	//图片渲染
	$('.wrapper-content').viewer('destroy');
	$('.wrapper-content').viewer();

    renderFileUpload("x","attach");
    
    for(var i = 0;i<parseInt("${fn:length(spvChargeInfoVO.spvCaseFlowOutInfoVOList)}");i++){
    	var k = i;
    	renderFileUpload(k);
    }

    if((!handle || handle == 'apply') && sum == 0){
    	$("#addTr").append(getTR(sum));
    }

});

//添加入账申请信息tr
function getTR(thisIndex){
	var  $str='';
	$str+='<tr>';
	$str+='	<td>';
	$str+='		<input class="table-input-one boderbbt" type="text" placeholder="请输入姓名" name="spvCaseFlowOutInfoVOList['+thisIndex+'].toSpvCashFlow.payer" >';
	$str+='	</td>';
	$str+='	<td>';
	$str+='		<p><input class="table_input boderbbt" type="text"placeholder="请输入银行卡号"  onKeypress="if (!(event.keyCode > 47 && event.keyCode < 58)) event.returnValue = false;" name="spvCaseFlowOutInfoVOList['+thisIndex+'].toSpvCashFlow.payerAcc" ></p>';
	$str+='		<p><input class="table_input boderbbt" type="text" placeholder="请输入银行名称" name="spvCaseFlowOutInfoVOList['+thisIndex+'].toSpvCashFlow.payerBank" ></p>';
	$str+='	</td>';
	$str+='	<td class="text-left">';
	$str+='		<input class="boderbbt" style="border:none;width: 50px;" type="text" placeholder="金额" onKeypress="if (!(event.keyCode > 45 && event.keyCode < 58 &&event.keyCode !=47 ) ) event.returnValue = false;" name="spvCaseFlowOutInfoVOList['+thisIndex+'].toSpvCashFlow.amount" >万';
	$str+='	</td>';
	$str+='	<td>';
	$str+='		<input class="table_input boderbbt forvalue" type="text" placeholder="请输入编号" onKeypress="if ((event.keyCode > 32 && event.keyCode < 48) || (event.keyCode > 57 && event.keyCode < 65) || (event.keyCode > 90 && event.keyCode < 97)) event.returnValue = false;" name="spvCaseFlowOutInfoVOList['+thisIndex+'].toSpvCashFlow.voucherNo" >';
	$str+='	</td>';
	$str+='	<td>';
	$str+='		<select name="spvCaseFlowOutInfoVOList['+thisIndex+'].toSpvCashFlow.direction" class="table-select boderbbt" onChange="this.value">';
	$str+='			<option value="">请选择</option>';
	$str+='			<option value="转账">转账</option>';
	$str+='			<option value="刷卡">刷卡</option>';
	$str+='			<option value="现金">现金</option>';
	$str+='		</select>';
	$str+='	</td>';
	$str+='	<td id="td_file'+thisIndex+'">';
	$str+='		<span class="btn_file'+thisIndex+'">';
	$str+='			<input id="fileupload_'+thisIndex+'" style="display:none" type="file" name="files[]" multiple="" data-url="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload" data-sequential-uploads="true">';
	$str+='			<label class="bnt-flie" alt="点击上传" style="positon:relative;display:inline-block;height:34px;width:52px;cursor:pointer; background-image:url('+ctx+'/static/trans/img/bnt-flie.png) " onClick="$(\'#fileupload_'+thisIndex+'\').trigger(\'click\');"/>';
	$str+='		</span>';
	$str+='	</td>';
	$str+='	<td align="center"><a href="javascript:void(0)" onclick="getTR('+(parseInt(sum)+1)+')">添加</a>';
	if(thisIndex > 0){
		$str+=' &nbsp;<a onClick="getDel(this)" class="grey" href="javascript:void(0)">删除</a></td>';
	}
	$str+='</tr>';
	sum++;
	$("#sum").val(sum);
	
	$("#addTr").append($str);
    
	renderFileUpload(thisIndex);
}

function getUploadImage(thisIndex,fileUrl,fileId,fileName){
	var shortName = fileName.length>5?fileName.substring(0,5):fileName;
	var image = '<span><img id="image_'+thisIndex+'" src="'+fileUrl+'" style="width:0px;height:0px;display: none;" title="'+fileName+'" alt="'+fileName+'" class="viewer-toggle" />';
	image += '<input type="hidden" name ="spvCaseFlowOutInfoVOList['+thisIndex+'].ToSpvCashFlow.attachIdArr" value = "'+fileId+'" fileName="'+fileName+thisIndex+'"/>';
	image += '<input type="hidden" name ="spvCaseFlowOutInfoVOList['+thisIndex+'].ToSpvCashFlow.commentArr" value="'+fileName+'" />';
	image += '<button type="button" class="btn btn-sm btn-default" style="margin-right:5px;margin-top:10px;" onClick="showImg(\'#image_'+thisIndex+'\')">'+shortName+'<i class="icon iconfont icon_x" onClick="removeImg(this,event);">&#xe60a;</i></button></span>';
	return image;
}

function getUploadImage2(thisIndex,fileUrl,fileId,fileName){
	var shortName = fileName.length>5?fileName.substring(0,5):fileName;
	var image = '<span><img id="image_'+addSum+'" src="'+fileUrl+'" style="width:0px;height:0px;display: none;" title="'+fileName+'" alt="'+fileName+'" class="viewer-toggle" />';
	image += '<input type="hidden" name ="toSpvCashFlowApplyAttachList['+addSum+'].attachId" value = "'+fileId+'" fileName="'+fileName+addSum+'"/>';
	image += '<input type="hidden" name ="toSpvCashFlowApplyAttachList['+addSum+'].comment" value="'+fileName+'" />';
	image += '<button type="button" class="btn btn-sm btn-default" style="margin-right:5px;margin-top:10px;" onClick="showImg(\'#image_'+addSum+'\')">'+shortName+'<i class="icon iconfont icon_x" onClick="removeImg(this,event);">&#xe60a;</i></button></span>';
	return image;
}
//删除入账申请信息tr
function getDel(k){
	if(!confirm("是否删除！")){
		  return false;
	    }
    $(k).parents('tr').remove();
}

function showImg(imgId){
	$(imgId).trigger("click");
}

function removeImg(this_,event){
	event.stopPropagation(); 
	$(this_).parent().parent().remove();
	$('.wrapper-content').viewer('destroy');
	$('.wrapper-content').viewer();
}

//添加上传方法
function renderFileUpload(k,a){
	$('#fileupload_'+k).fileupload({
		acceptFileTypes:'/(gif|jpg|jpeg|bmp|png|tif|tiff)/i',
		autoUpload: true,
        dataType: 'json',
        add:function(e,data){
        	var fileName = data.files[0].name;
        	if($("input[fileName='"+fileName+k+"']").size()==0){
        		data.submit();
        	}
        	addSum++;
        	$("#addSum").val(addSum);
        },
        done: function (e, data) {
        	if(data.result){
        		var fileId =  data.result.files[0].id;
            	var fileUrl = data.result.files[0].url;
            	var fileName = data.result.files[0].name;
            	var image;
            	if(a == 'attach'){
            		image = getUploadImage2(k,fileUrl,fileId,fileName);
            	}else{
                	image = getUploadImage(k,fileUrl,fileId,fileName);
            	}
            	$('#td_file'+k).prepend(image);	
            	doneSum++;
            	$("#doneSum").val(doneSum);
            	if(addSum==doneSum){
            		$('.wrapper-content').viewer('destroy');
            		$('.wrapper-content').viewer();
            	}
        	}
        },
        progressall: function (e, data) {
        	$('#progress').show();
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css('width',progress+'%').find("span").css('color','red').text(progress+'%');
            if(progress == 100){
                setTimeout($('#progress').fadeOut(2000));
            }
        }
    })
}
</script>
</content>
</body>
</html>