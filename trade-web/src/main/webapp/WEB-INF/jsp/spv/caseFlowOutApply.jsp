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
	select[readonly] {  
    background: #eee;  
    cursor: no-drop;  
    }  
	select[readonly] option {  
	    display: none;  
	}  
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
		<input type="hidden" id="totalProcessCashFlowOutAmout" value="${totalProcessCashFlowOutAmout }" >
		<input type="hidden" id="totalCashFlowInAmount" value="${totalCashFlowInAmount }" >
		<input type="hidden" id="totalCashFlowOutAmount" value="${totalCashFlowOutAmount }" >
		<%--上传图片添加的图片和上传成功的图片数量，用于确认是否完全上传成功 --%>
		<input type="hidden" id="addSum" value="0" >
		<input type="hidden" id="doneSum" value="0" >
		<%--流水--%>
		<input type="hidden" id="sum" value="${fn:length(spvChargeInfoVO.spvCaseFlowOutInfoVOList)}" >
		<input type="hidden" id="attSum_" value="${fn:length(spvChargeInfoVO.toSpvCashFlowApplyAttachList)}" >
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
                                    <label> 案件编号  </label>
                                     <span class="info"><span  class="demo-top" title="${spvBaseInfoVO.toSpv.caseCode }" >${spvBaseInfoVO.toSpv.caseCode }</span></span>
                                </p>
                                <p>
                                    <label>合约编号 </label>
                                   <span  class="info demo-top" title="${spvBaseInfoVO.toSpv.spvCode }">${spvBaseInfoVO.toSpv.spvCode }</span>
                                </p>
                            </div>
                            <div class="line">
                                <p>
                                    <label>
                                       	产品类型
                                    </label>
                                    <span class="info" title="${spvBaseInfoVO.toSpv.prdCode eq 1?'光大四方资金监管':'' }">${spvBaseInfoVO.toSpv.prdCode eq 1?'光大四方资金监管':'' }</span>
                                </p>
                                <p>
                                    <label>
                                      	  监管金额
                                    </label>
                                    <span class="info" title="${spvBaseInfoVO.toSpv.amount }">${spvBaseInfoVO.toSpv.amount }万</span>
                                </p>

                                <p>
                                    <label>
                                        	物业地址
                                    </label>
                                    <span class="info" ><span class="demo-top" title="${spvBaseInfoVO.toSpvProperty.prAddr }">${spvBaseInfoVO.toSpvProperty.prAddr }</span></span>
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
<!--                                             <th>
                                                次数
                                            </th> -->
                                            <th>
                                              	  划转条件
                                            </th>
                                            <th>
                                               	账户
                                            </th>
                                            <th>
                                               	 划转金额
                                            </th>
                                            <th>
                                                	备注
                                            </th>
<!--                                             <th>
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
                                            </th> -->
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:set var="sumTotalAmt" value="0"/>
                                        <c:set var="sumSellerAmt" value="0"/>
                                        <c:set var="sumFundAmt" value="0"/>
                                        <c:set var="codeArrStr" value=""/>
                                        
                                        <c:forEach items="${spvBaseInfoVO.toSpvDeDetailList}" var="item">
                                        <c:set var="codeArrStr" value="${item.deCondCode},${codeArrStr }"/>
										<tr>
											<td>
												<aist:dict id="${item.deCondCode}" name="deCondCode" clazz="form-control input-one"
									    display="onlyLabel"  dictType="SPV_DE_COND"  
									    ligerui='none' dictCode="${item.deCondCode }"></aist:dict>	
											</td>
											<td>
											
											<c:choose>  
										    <c:when test="${item.payeeAccountId==spvBaseInfoVO.toSpvAccountList[0].pkid}">
										              ${spvBaseInfoVO.toSpvAccountList[0].name} (买方)
										   </c:when>  
										   <c:when test="${item.payeeAccountId==spvBaseInfoVO.toSpvAccountList[1].pkid}">
										             ${spvBaseInfoVO.toSpvAccountList[1].name} (卖方)
										   </c:when> 
										   <c:when test="${item.payeeAccountId==spvBaseInfoVO.toSpvAccountList[2].pkid}">
										            ${spvBaseInfoVO.toSpvAccountList[2].name} (监管账户 )  
										   </c:when> 
										   <c:when test="${item.payeeAccountId==spvBaseInfoVO.toSpvAccountList[3].pkid}">
										            ${spvBaseInfoVO.toSpvAccountList[3].name} (资金方)       
										   </c:when>  
										    <c:otherwise> 
										     <c:forEach items="${spvBaseInfoVO.toSpvAccountList}" begin='4' var="toSpvAccount">  
										    <c:if test="${item.payeeAccountId==toSpvAccount.pkid}">
										           ${toSpvAccount.name }(自定义)
										   </c:if>
										    </c:forEach>
									       </c:otherwise> 
										</c:choose>	
											</td>
											<td>${item.deAmount>0?item.deAmount:0}万元</td>
											<td>${item.deAddition}</td>
										</tr>
										</c:forEach>              
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
                                                    <a href="javascript:void(0)">${cashFlow.ftPostAuditorName }&nbsp;</a><fmt:formatDate value="${cashFlow.closeTime }" pattern="yyyy-MM-dd"/>
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
	                                                    <c:if test="${cashFlow.status eq 12 }">
	                                                    &gt;${financeName }
	                                                    </c:if>
	                                                    <c:if test="${cashFlow.status gt 12 }">
	                                                    &gt;${cashFlow.ftPreAuditorName }
	                                                    </c:if>
                                                    </c:if>
                                                    
                                                    <c:if test="${cashFlow.usage eq 'out'}" >
	                                                    <c:if test="${cashFlow.status eq 13 }">
	                                                    &gt;${financeName }
	                                                    </c:if>
	                                                    <c:if test="${cashFlow.status gt 13 }">
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
                        <form id="workFolwApplyForm" style="display:none;">
                              <input type="hidden" name="toSpvCashFlowApply.pkid" value="${spvChargeInfoVO.toSpvCashFlowApply.pkid }" />
                              <input type="hidden" name="toSpvCashFlowApply.cashflowApplyCode" value="${spvChargeInfoVO.toSpvCashFlowApply.cashflowApplyCode }" />
                              <input type="hidden" name="toSpvCashFlowApply.spvCode" value="${empty spvCode?spvChargeInfoVO.toSpvCashFlowApply.spvCode:spvCode }" />
                              <input type="hidden" name="toSpvCashFlowApply.usage" value="out" />
                        </form>
                        <div class="mt20">
                            <div class="agree-tile">
                                出账申请附件信息
                            </div>
                            <form class="form-inline table-capital">
                                <div class="table-box" >
                                    <table class="table table-bordered">
                                        <thead>
                                        <th>出账条件</th>
                                        <th>附件</th>
                                        </thead>
                                        <tbody id="addTr2">
                                        <tr>                                         
                                            <td width="310">
                                               <aist:dict id='toSpvCashFlowApplyAttachType' name='toSpvCashFlowApplyAttachType' 
                                                clazz='table-select' display='select'  dictType='SPV_DE_COND' ligerui='none' defaultvalue='${spvChargeInfoVO.toSpvCashFlowApplyAttachType }' ></aist:dict>
                                            </td>
                                            <td id="td_filex">
                                                <c:forEach items="${spvChargeInfoVO.toSpvCashFlowApplyAttachList }" var="toSpvCashFlowApplyAttach" varStatus="status">
	                                                 	<span  style='margin-bottom: 5px;margin-right:5px;padding: 0 8px;'>
	                                                 	<img id="image_${status.index }" src="<aist:appCtx appName='shcl-filesvr-web'/>/JQeryUpload/getfile?fileId=${toSpvCashFlowApplyAttach.attachId}" style="width:0px;height:0px;display: none;" alt="${toSpvCashFlowApplyAttach.comment}"  class="viewer-toggle" />
	                                                 	<input type="hidden" name ="toSpvCashFlowApplyAttachList[${status.index }].pkid" value = "${toSpvCashFlowApplyAttach.pkid}"/>
														<input type="hidden" name ="toSpvCashFlowApplyAttachList[${status.index }].attachId" value = "${toSpvCashFlowApplyAttach.attachId}"/>
														<input type="hidden" name ="toSpvCashFlowApplyAttachList[${status.index }].comment" value = "${toSpvCashFlowApplyAttach.comment}" />
															<button type="button" class="btn btn-sm btn-default" style="margin-bottom: 5px;margin-right:5px;margin-top:10px;padding: 0 8px;" onClick="showImg('#image_${status.index }')" >${toSpvCashFlowApplyAttach.comment}
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
			                                                      <label class="bnt-flie" alt="点击上传" style="positon:relative;display:inline-block;height:32px;width:100px;margin-bottom:-18px;cursor:pointer; background:url(${ctx}/static/trans/img/bnt-flie.png) no-repeat; background-size: 38%;" onClick="$('#fileupload_x').trigger('click');"/>
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
                                        <th style="width: 100px;">收款人姓名</th>
                                        <th>收款人账户</th>
                                        <th style="width: 100px;">出账金额</th>
                                        <th style="width: 120px;">贷记凭证编号</th>
                                        <th style="width: 90px;">付款方式</th>
                                        <th>凭证附件</th>
                                        <c:if test="${empty handle or handle eq 'apply' }">
                                        <th style="width: 50px;">操作</th>
                                        </c:if>
                                        </thead>
                                        <tbody id="addTr">
                                        	<c:forEach items="${spvChargeInfoVO.spvCaseFlowOutInfoVOList}" var="spvCaseFlowOutInfoVO" varStatus="status2">
	                                           	<tr>
	                                           	    <input type="hidden" name="spvCaseFlowOutInfoVOList[${status2.index }].toSpvCashFlow.pkid"   value="${spvCaseFlowOutInfoVO.toSpvCashFlow.pkid }" />
	                                           	    <input type="hidden" name="spvCaseFlowOutInfoVOList[${status2.index }].toSpvCashFlow.spvCode"   value="${spvCaseFlowOutInfoVO.toSpvCashFlow.spvCode }" />
	                                                <td>
	                                                    <select  class="table-select boderbbt"  
	                                                    <c:if test="${handle eq 'directorAduit' or handle eq 'financeAduit' or handle eq 'financeSecondAduit'}">
	                                                    title="${spvCaseFlowOutInfoVO.toSpvCashFlow.receiver }"
	                                                    </c:if> 
	                                                     name="spvCaseFlowOutInfoVOList[${status2.index }].toSpvCashFlow.receiver" value="${spvCaseFlowOutInfoVO.toSpvCashFlow.receiver }" onChange="doSearch(this)" ></select>
	                                                </td>
	                                                <td>
	                                                    <p><select class="table-select boderbbt" value="${spvCaseFlowOutInfoVO.toSpvCashFlow.receiverAcc }" name="spvCaseFlowOutInfoVOList[${status2.index }].toSpvCashFlow.receiverAcc" readonly ></select></p>
	                                                    <p>
	                                                    <p><select class="table-select boderbbt" value="${spvCaseFlowOutInfoVO.toSpvCashFlow.receiverBank }" name="spvCaseFlowOutInfoVOList[${status2.index }].toSpvCashFlow.receiverBank" readonly></select></p>
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
	                                                        <option value="">请选择</option>
	                                                        <option <c:if test="${spvCaseFlowOutInfoVO.toSpvCashFlow.direction eq '转账'}"> selected="selected" </c:if> value="转账">转账</option>                                                                                                                                                
															<option <c:if test="${spvCaseFlowOutInfoVO.toSpvCashFlow.direction eq '刷卡'}"> selected="selected" </c:if> value="刷卡">刷卡</option>  
															<%-- <option <c:if test="${spvCaseFlowOutInfoVO.toSpvCashFlow.direction eq '现金'}"> selected="selected" </c:if> value="现金">现金</option> --%> 
	                                                    </select>
	                                                </td>
	                                                <td id="td_file${status2.index  }">
                                                	<c:forEach items="${spvCaseFlowOutInfoVO.toSpvVoucherList}" var="toSpvVoucher" varStatus="status3">
	                                                 	<span>
	                                                 	<img id="image_${status3.index }" src="<aist:appCtx appName='shcl-filesvr-web'/>/JQeryUpload/getfile?fileId=${toSpvVoucher.attachId}" style="width:0px;height:0px;display: none;" title="${toSpvVoucher.comment}" alt="${toSpvVoucher.comment}" class="viewer-toggle" />
	                                                 	<input type="hidden" name ="spvCaseFlowOutInfoVOList[${status2.index }].toSpvVoucherList[${status3.index}].pkid" value = "${toSpvVoucher.pkid}"/>
														<input type="hidden" name ="spvCaseFlowOutInfoVOList[${status2.index }].toSpvVoucherList[${status3.index}].attachId" value = "${toSpvVoucher.attachId}"/>
														<input type="hidden" name ="spvCaseFlowOutInfoVOList[${status2.index }].toSpvVoucherList[${status3.index}].comment" value = "${toSpvVoucher.comment}" />
															<button type="button" class="btn btn-sm btn-default" style="margin-bottom: 5px;margin-right:5px;margin-top:10px;padding: 0 8px;" onClick="showImg('#image_${status3.index }')">${toSpvVoucher.comment}
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
                                                	 <span class="btn_file${status2.index}" >                                                                                                                                                               
       			                                             <input id="fileupload_${status2.index}" style="display:none" type="file" name="files[]" multiple="" data-url="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload" data-sequential-uploads="true">
			                                                      <label class="bnt-flie" alt="点击上传" style="positon:relative;display:inline-block;height:32px;width:100px;margin-bottom:-18px;cursor:pointer; background:url(${ctx}/static/trans/img/bnt-flie.png) no-repeat; background-size: 38%;" onClick="$('#fileupload_${status2.index}').trigger('click');"/>
		                                             </span>
		                                             </c:if>   
	                                                </td>
	                                                <c:if test="${empty handle or handle eq 'apply' }">
	                                                <td align="center">
		                                                <a href="javascript:void(0)" onClick="getTR(parseInt($('#sum').val()))">添加</span></a>
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

                        <c:if test="${not empty handle}">
                        <div class="ibox-content clearfix space30">
                        <div class="title">
                            <strong>审核意见</strong>
                        </div>
                        <div class="view-content">
                            <div class="view-box">
                            <c:if test="${not empty spvChargeInfoVO.toSpvAduitList }">
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
                            </c:if>
                            <c:if test="${empty spvChargeInfoVO.toSpvAduitList }">
                                    <%-- <p class="text-center"><img src="${ctx}/image/false2.png" height="100" alt="" /></p> --%>
                                    <div style="width:100%;height:100px;background:url(../../../static/image/false2.png) no-repeat center;background-size:100% 100%;" ></div>
                            </c:if>
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
                    </c:if>

                    <div class="form-btn">
                            <div class="text-center">
                            <c:if test="${empty handle }">
                                <!-- <button id="none_save_btn" class="btn btn-success mr15">保存</button> -->
                                <button id="none_submit_btn" class="btn btn-success mr15">提交</button>
                                <button onclick="rescCallbocak()" class="btn btn-default">关闭</button>
                            </c:if>
                            <c:if test="${handle eq 'apply' }">
                                <!-- <button id="apply_save_btn" class="btn btn-success mr15">保存</button> -->
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
var sum = parseInt($("#sum").val());
var attSum_ = parseInt($("#attSum_").val());
var addSum = 0;
var doneSum = 0;
var accountType = 'all';
var obj =${jsonList};
var deId = "0";

$(function() {
 	//筛选条件 
	var codeArrStr = "${codeArrStr}";
	$("#addTr2").find("option").each(function(i,e){
		if(codeArrStr.indexOf($(e).val()) == -1){
			$(e).remove();
		}
	});
	
	$("#addTr2").find("select").change(function(){
		 deId=$(this).find("option:selected").val();
		 addselect(deId,obj);		
	}).change(); 
	
	$("select[name$='toSpvCashFlow.receiver']").each(function(i,e){
		$(e).find("option").each(function(i_,e_){
			if($(e).attr('value') == $(e_).val()){
				$(e_).prop('selected',true);
			}
		});
	}).change();
		
	//图片渲染
	if($("img[id^='image_']").size()>0){
		renderImg();
	}

    renderFileUpload("x","attach");
    
    for(var i = 0;i<parseInt("${fn:length(spvChargeInfoVO.spvCaseFlowOutInfoVOList)}");i++){
    	var k = i;
    	renderFileUpload(k);
    }

    if((!handle || handle == 'apply') && sum == 0){
    	$("#addTr").append(getTR(0));
    }

});
function doSearch(this_){
	//$(this_).attr("title",$(this_).find("option:selected").val());
	var index = $(this_).find("option:selected").attr("gl");
	var i = $(this_).attr("name").replace('spvCaseFlowOutInfoVOList[','').replace('].toSpvCashFlow.receiver','');

    $("select[name='spvCaseFlowOutInfoVOList["+i+"].toSpvCashFlow.receiverAcc'] option[gl="+index+"]").prop("selected", true);
    $("select[name='spvCaseFlowOutInfoVOList["+i+"].toSpvCashFlow.receiverBank'] option[gl="+index+"]").prop("selected", true);
}

function addselect(deId,obj,index){
	$("select[name$='toSpvCashFlow.receiver']").empty(); 
	$("select[name$='toSpvCashFlow.receiverAcc']").empty(); 
	$("select[name$='toSpvCashFlow.receiverBank']").empty(); 

	$.each(obj,function(n,data) { 
		if(deId==data.type){
			 $("select[name$='toSpvCashFlow.receiver']").append("<option gl='"+n+"' value='"+data.name+"'>"+data.name+"</option>");
		     $("select[name$='toSpvCashFlow.receiverAcc']").append("<option gl='"+n+"' value='"+data.account+"'>"+data.account+"</option>");
		     $("select[name$='toSpvCashFlow.receiverBank']").append("<option gl='"+n+"' value='"+data.bankName+"'>"+data.bankName+"</option>");
		}
  	});

	//$("select[name$='toSpvCashFlow.receiver']").attr("title",$("select[name$='toSpvCashFlow.receiver']").val());
}

function addselectOne(deId,obj,index){	
	$.each(obj,function(n,data) { 
		if(deId==data.type){
				 $("select[name='spvCaseFlowOutInfoVOList["+index+"].toSpvCashFlow.receiver']").append("<option gl='"+n+"' value='"+data.name+"'>"+data.name+"</option>");
			     $("select[name='spvCaseFlowOutInfoVOList["+index+"].toSpvCashFlow.receiverAcc']").append("<option gl='"+n+"' value='"+data.account+"'>"+data.account+"</option>");
			     $("select[name='spvCaseFlowOutInfoVOList["+index+"].toSpvCashFlow.receiverBank']").append("<option gl='"+n+"' value='"+data.bankName+"'>"+data.bankName+"</option>");
		}
  	}); 
	
	//$("select[name='spvCaseFlowOutInfoVOList["+index+"].toSpvCashFlow.receiver']").attr("title",$("select[name$='toSpvCashFlow.receiver']").val());
}

//添加入账申请信息tr
function getTR(index){
	index = sum;
	
	var  $str='';
	$str+='<tr>';
	$str+='	<td>';
	$str+='		<select  class="table-select boderbbt"  name="spvCaseFlowOutInfoVOList['+index+'].toSpvCashFlow.receiver" onChange="doSearch(this)" >';
	$str+='		</select>';	
	$str+='	</td>';
	$str+='	<td>';
	$str+='		<p><select class="table-select boderbbt"  name="spvCaseFlowOutInfoVOList['+index+'].toSpvCashFlow.receiverAcc"    readonly >';
	$str+='		</select>';	
	$str+='</p>';
	$str+='		<p><select class="table-select boderbbt"  name="spvCaseFlowOutInfoVOList['+index+'].toSpvCashFlow.receiverBank"  readonly>';
	$str+='		</select>';	
	$str+=' </p>';
	$str+='	</td>';
	$str+='	<td class="text-left">';
	$str+='		<input class="boderbbt" style="border:none;width: 50px;" type="text" placeholder="金额" onKeypress="if (!(event.keyCode > 45 && event.keyCode < 58 &&event.keyCode !=47 ) ) event.returnValue = false;" name="spvCaseFlowOutInfoVOList['+index+'].toSpvCashFlow.amount" >万';
	$str+='	</td>';
	$str+='	<td>';
	$str+='		<input class="table_input boderbbt forvalue" type="text" placeholder="请输入编号" onKeypress="if ((event.keyCode > 32 && event.keyCode < 48) || (event.keyCode > 57 && event.keyCode < 65) || (event.keyCode > 90 && event.keyCode < 97)) event.returnValue = false;" name="spvCaseFlowOutInfoVOList['+index+'].toSpvCashFlow.voucherNo" >';
	$str+='	</td>';
	$str+='	<td>';
	$str+='		<select name="spvCaseFlowOutInfoVOList['+index+'].toSpvCashFlow.direction" class="table-select boderbbt" onChange="this.value">';
	$str+='			<option value="">请选择</option>';
	$str+='			<option value="转账">转账</option>';
	$str+='			<option value="刷卡">刷卡</option>';
	$str+='			<option value="现金">现金</option>';
	$str+='		</select>';
	$str+='	</td>';
	$str+='	<td id="td_file'+index+'">';
	$str+='		<span class="btn_file'+index+'">';
	$str+='			<input id="fileupload_'+index+'" style="display:none" type="file" name="files[]" multiple="" data-url="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload" data-sequential-uploads="true">';
	$str+='			<label class="bnt-flie" alt="点击上传" style="positon:relative;display:inline-block;height:32px;width:100px;margin-bottom:-18px;cursor:pointer; background:url('+ctx+'/static/trans/img/bnt-flie.png) no-repeat; background-size: 38%;" onClick="$(\'#fileupload_'+index+'\').trigger(\'click\');" ></label>';
	$str+='		</span>';
	$str+='	</td>';
	$str+='	<td align="center"><a href="javascript:void(0)" onclick="getTR('+sum+')">添加</a>';
	if(index > 0){
		$str+=' &nbsp;<a onClick="getDel(this)" class="grey" href="javascript:void(0)">删除</a></td>';
	}
	$str+='</tr>';
	$("#addTr").append($str);

	sum++;
	$("#sum").val(sum);	
	addselectOne(deId,obj,index);
	
	renderFileUpload(index);
}

function getUploadImage(thisIndex,fileUrl,fileId,fileName){
	var shortName = fileName.length>5?fileName.substring(0,5):fileName;
	var image = '<span><img id="image_'+thisIndex+'" src="'+fileUrl+'" style="width:0px;height:0px;display: none;" title="'+fileName+'" alt="'+fileName+'" class="viewer-toggle" />';
	image += '<input type="hidden" name ="spvCaseFlowOutInfoVOList['+thisIndex+'].ToSpvCashFlow.attachIdArr" value = "'+fileId+'" fileName="'+fileName+thisIndex+'"/>';
	image += '<input type="hidden" name ="spvCaseFlowOutInfoVOList['+thisIndex+'].ToSpvCashFlow.commentArr" value="'+fileName+'" />';
	image += '<button type="button" class="btn btn-sm btn-default" style="margin-bottom: 5px;margin-right:5px;margin-top:10px;padding: 0 8px;" onClick="showImg(\'#image_'+thisIndex+'\')">'+shortName+'<i class="icon iconfont icon_x" onClick="removeImg(this,event);">&#xe60a;</i></button></span>';
	return image;
}

function getUploadImage2(thisIndex,fileUrl,fileId,fileName){
	var shortName = fileName.length>5?fileName.substring(0,5):fileName;
	var attSum = attSum_;
	var image = '<span><img id="image_'+attSum+'" src="'+fileUrl+'" style="width:0px;height:0px;display: none;" title="'+fileName+'" alt="'+fileName+'" class="viewer-toggle" />';
	image += '<input type="hidden" name ="toSpvCashFlowApplyAttachList['+attSum+'].attachId" value = "'+fileId+'" fileName="'+fileName+addSum+'"/>';
	image += '<input type="hidden" name ="toSpvCashFlowApplyAttachList['+attSum+'].comment" value="'+fileName+'" />';
	image += '<button type="button" class="btn btn-sm btn-default" style="margin-bottom: 5px;margin-right:5px;margin-top:10px;padding: 0 8px;" onClick="showImg(\'#image_'+attSum+'\')">'+shortName+'<i class="icon iconfont icon_x" onClick="removeImg(this,event);">&#xe60a;</i></button></span>';
	attSum_++;
	return image;
}
//删除入账申请信息tr
function getDel(k){
	if($("input[name$='receiver']").size()==1){
		alert("入账申请信息不能少于一行数据！");
		return false;
	}
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
	renderImg();
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
            		renderImg();
            	}
        	}
        },
        progressall: function (e, data) {
        	$('#progress').show();
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css('width',progress+'%').find("span").css('color','red').text(progress+'%');
            if(progress == 100){
                $('#progress').fadeOut(4000);
            }
        }
    })
}

function rescCallbocak(){
	if($("#urlType").val() == 'myTask'){    	 
		   window.opener.location.reload(); //刷新父窗口
  	       window.close(); //关闭子窗口.
	     }else{
	       window.location.href = ctx+"/spv/spvList";
	     }
}

//渲染图片 
function renderImg(){
	$('.wrapper-content').viewer('destroy');
	$('.wrapper-content').viewer();
}
</script>
</content>
</body>
</html>