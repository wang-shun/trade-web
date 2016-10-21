
<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>申请审批意见</title>

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
 	<link href="${ctx}/static/trans/css/workflow/details.css" rel="stylesheet" />
	<link href="${ctx}/js/viewer/viewer.min.css" rel="stylesheet" />
	<!-- 必须CSS -->
	<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />
 	 

</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
          <div class="row">
              <div class="wrapper wrapper-content animated fadeInUp">
                  <div class="ibox-content space30" >
                      <div class="agree-tile">
                        	  资金入账申请
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
                                  <span class="info_one">${spvBaseInfoVO.toSpv.amount }万元</span>
                              </p>

                              <p>
                                  <label>
                                    	  物业地址
                                  </label>
                                  <span class="info" ><span class="demo-top" title="${spvBaseInfoVO.toSpvProperty.prAddr }">${spvBaseInfoVO.toSpvProperty.prAddr }</span></span>
                              </p>

                          </div>
                          <div class="line">
                              <p>
                                  <label>
                                   	   收款人名称
                                  </label>
                                  <span class="info_one"><span class="demo-top" title="上海中原物业顾问有限公司">上海中原物业顾问有限公司</span></span>
                              </p>

                              <p>
                                  <label>
                                      	收款人账户
                                  </label>
                                  <span class="info_one"><span class="demo-top" title="${spvBaseInfoVO.toSpvAccountList[2].account }">${spvBaseInfoVO.toSpvAccountList[2].account }</span></span>
                              </p>

                              <p>
                                  <label>
                                      	收款人开户行
                                  </label>
                                  <span class="info"><%-- ${spvBaseInfoVO.toSpvAccountList[2].bank } --%>光大银行市北支行</span>
                              </p>
                          </div>
                      </div>
                      <div class="mt20">
                          <div class="agree-tile">
                             	 入账申请信息
                          </div>
                          <form class="form-inline table-capital" id="teacForm" >
                        <input type="hidden" name="prdCode" value="${spvBaseInfoVO.toSpv.prdCode==1?"光大四方资金监管":"" }" />
                        <input type="hidden" name="amount" value="${spvBaseInfoVO.toSpv.amount}" />
                        <input type="hidden" name="prAddr" value="${spvBaseInfoVO.toSpvProperty.prAddr}" />
                        <input type="hidden" name="spvAccountName" value="上海中原物业顾问有限公司" />
                        <input type="hidden" name="spvAccountCode" value="${spvBaseInfoVO.toSpvAccountList[2].account}" />
                        <input type="hidden" name="spvAccountBank" value="光大银行市北支行" />
                         <%-- 流程相关 --%>
						<input type="hidden" id="taskId" name="taskId" value="${taskId }" />
						<input type="hidden" id="instCode" name="instCode" value="${instCode}" />
						<input type="hidden" id="source" name="source" value="${source}" />
						<input type="hidden" id="urlType" name="source" value="${urlType}" />
						<input type="hidden" id="handle" name="handle" value="${handle }" />
						<input type="hidden" id="chargeInAppr" name="chargeInAppr" />
						<input type="hidden" id="businessKey" name="businessKey" value="${businessKey }" />
						<input type="hidden" id="turndownContent" name="turndownContent" />
                        <input type="hidden" name="oldpkid"  />
                              <div class="table-box" >
                                  <table class="table table-bordered customerinfo">
                                      <thead>
                                          <th style="width: 100px;">付款人姓名</th>
                                          <th>付款人账户</th>
                                          <th style="width: 100px;">入账金额</th>
                                          <th style="width: 120px;">贷记凭证编号</th>
                                          <th>付款方式</th>
                                          <th>凭证附件</th>
                                          <th>入账时间</th>
                                      </thead>
                                      <tbody id="addTr">
                                      <c:forEach items="${spvChargeInfoVO.spvCaseFlowOutInfoVOList}" var="spvCaseFlowOutInfoVO" varStatus="status2">
                                          <tr>
                                              <td>
                                                  <div class="big">${spvCaseFlowOutInfoVO.toSpvCashFlow.payer }</div>
                                              </td>
                                              <td>
                                                  <div class="marspace">${spvCaseFlowOutInfoVO.toSpvCashFlow.payerAcc }</div>
                                                  <div class="marspace">${spvCaseFlowOutInfoVO.toSpvCashFlow.payerBank }</div>
                                              </td>

                                              <td class="text-left" >
                                                  <div class="big">${spvCaseFlowOutInfoVO.toSpvCashFlow.amount }万元</div>
                                              </td>
                                              <td>
                                                  <div class="big">  ${spvCaseFlowOutInfoVO.toSpvCashFlow.voucherNo }  </div>
                                              </td>
                                              <td>
                                                  <div class="big"> ${spvCaseFlowOutInfoVO.toSpvCashFlow.direction } </div>
                                              </td>
                                              <td>
                                                  <c:forEach items="${spvCaseFlowOutInfoVO.toSpvReceiptList}" var="toSpvReceiptList" varStatus="status3">
													<img id="image_${status3.index }" src="<aist:appCtx appName='shcl-filesvr-web'/>/JQeryUpload/getfile?fileId=${toSpvReceiptList.attachId}" style="width:0px;height:0px;display: none;" class="viewer-toggle">
													<input type="hidden" name ="items[${status3.index}].fileId" value = "'+fileId+'" fileName="'+fileName+'"/>
													<button type="button" class="btn btn-sm btn-default" onClick="$('#image_${status3.index }').trigger('click');"><i class="icon iconfont icon_y" >&#xe635;
													${toSpvReceiptList.comment.length()>5?toSpvReceiptList.comment.substring(0,5):toSpvReceiptList.comment}
													</button>
                                               	 </c:forEach>
                                              </td>
                                               <td>
                                                  <div class="big">  
                                                  <fmt:formatDate value="${spvCaseFlowOutInfoVO.toSpvCashFlow.receiptTime }" pattern="yyyy-MM-dd"/>
                                                  </div>
                                              </td>
                                          </tr>
                                       </c:forEach>
                                      </tbody>
                                  </table>

                              </div>
                          </form>
                      </div>

                  </div>

                  <div class="ibox-content clearfix space30">
                      <div class="title">
                          <strong>审核意见</strong>
                      </div>
                      <div class="view-content">
                          <div class="view-box">
 							<c:forEach items="${spvChargeInfoVO.toSpvAduitList }" var="toSpvAduit" varStatus="status3">
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
                           <!--  <p class="text-center"><img src="'+ ctx + '/image/false.png" height="100" alt="" /></p> -->
                     	 </div>
                      </div>


                      <div class="submitter">
                     <%--      提交人：<span>${spvChargeInfoVO.toSpvCashFlowApply.createBy}(业务员)</span> --%>
                      </div>
                      <div class="excuse">
                          <form action="">
                              <textarea name="turndownContent_" id="turndownContent_" placeholder="请填写审核意见" style="width:100%; resize: none;height:140px;border-radius: 3px;border: 1px solid #d8d8d8;padding:10px;"></textarea>
                          </form>
                          <div class="form-btn">
                          <div class="text-center">
                              <button type="button" onclick="approvalby()" class="btn btn-success btn-space">审批通过</button>
                              <button type="button" onclick="turndown()"  class="btn btn-pink btn-space">审批驳回</button>
                              <button type="button"  onclick="rescCallbocak()" class="btn btn-default btn-space">取消</button>
                          </div>
                      </div>
                      </div>
                  </div>
              </div>
          </div>
          <!-- main End -->

      </div>
  </div>
<content tag="local_script">
<script src="${ctx}/static/trans/js/spv/spvRecordShow.js"></script>
<script src="${ctx}/static/js/inspinia.js"></script>
<script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
<!-- stickup plugin -->
<script src="${ctx}/static_res/trans/js/spv/jkresponsivegallery.js"></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> 
<script src="${ctx}/js/template.js" type="text/javascript"></script> <!-- stickup plugin -->
<script src="${ctx}/js/viewer/viewer.min.js"></script>
<!-- 必须JS -->
<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>

<script type="text/javascript">
$(function(){

		//left
		$('.demo-left').poshytip({
			className: 'tip-twitter',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'left',
			alignY: 'center',
			offsetX: 8,
			offsetY: 5,
		});

		//right
		$('.demo-right').poshytip({
			className: 'tip-twitter',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'right',
			alignY: 'center',
			offsetX: 8,
			offsetY: 5,
		});

		//top
		$('.demo-top').poshytip({
			className: 'tip-twitter',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'center',
			alignY: 'top',
			offsetX: 8,
			offsetY: 5,
		});

		//bottom
		$('.demo-bottom').poshytip({
			className: 'tip-twitter',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'center',
			alignY: 'bottom',
			offsetX: 8,
			offsetY: 5,
		});
	});

$('.wrapper-content').viewer();

</script>
</content>
</body>
</html>