
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

<title>审批驳回</title><%-- 
<link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css">
<link rel="stylesheet" href="${ctx}/static/css/animate.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/static/css/style.css" rel="stylesheet"> --%>
<!-- stickUp fixed css --><%-- 
<link rel="stylesheet" href="${ctx}/static/css/plugins/stickup/stickup.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/stickmenu.css">
<link rel="stylesheet" href="${ctx}/static/css/plugins/aist-steps/steps.css">
<link rel="stylesheet" href="${ctx}/static/trans/static/css/plugins/toastr/toastr.min.css">
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/see.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/spv.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/response/jkresponsivegallery.css " /> --%>

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



</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<script type="text/javascript">
	var ctx = "${ctx}";
	var source = "${source}";
	if ("${idList}" != "") {
		var idList = eval("(" + "${idList}" + ")");
	} else {
		var idList = [];
	}
</script>
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
                                入账申请信息
                            </div>

                            <form class="form-inline table-capital" id="teacForm" >
		                        <input type="hidden" name="prdCode" value="${spvBaseInfoVO.toSpv.prdCode==1?"光大四方资金监管":"" }" />
		                        <input type="hidden" name="amount" value="${spvBaseInfoVO.toSpv.amount}" />
		                        <input type="hidden" name="prAddr" value="${spvBaseInfoVO.toSpvProperty.prAddr}" />
		                        <input type="hidden" name="spvAccountName" value="${spvBaseInfoVO.toSpvAccountList[2].name}" />
		                        <input type="hidden" name="spvAccountCode" value="${spvBaseInfoVO.toSpvAccountList[2].account}" />
		                        <input type="hidden" name="spvAccountBank" value="${spvBaseInfoVO.toSpvAccountList[2].bank}" />
		                         <%-- 流程相关 --%>
								<input type="hidden" id="taskId" name="taskId" value="${taskId }" />
								<input type="hidden" id="instCode" name="instCode" value="${instCode}" />
								<input type="hidden" id="source" name="source" value="${source}" />
								<input type="hidden" id="urlType" name="source" value="${urlType}" />
								<input type="hidden" id="handle" name="handle" value="${handle }" />
								<input type="hidden" id="chargeInAppr" name="chargeInAppr" />
								<input type="hidden" id="businessKey" name="businessKey" value="${businessKey }" />
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
                                            <th>操作</th>
                                        </thead>
                                        <tbody id="addTr">
	                                           <c:forEach items="${spvChargeInfoVO.spvCaseFlowOutInfoVOList}" var="spvCaseFlowOutInfoVO" varStatus="status2">
	                                           	<tr>
	                                                <td>
	                                                    <input class="table-input-one boderbbt" type="text" name="items[${status2.index }].payerName"   value="${spvCaseFlowOutInfoVO.toSpvCashFlow.payer }" />
	                                                </td>
	                                                <td>
	                                                    <p><input class="table_input boderbbt" type="text" value="${spvCaseFlowOutInfoVO.toSpvCashFlow.payerAcc }"  name="items[${status2.index }].payerAcc" /></p>
	                                                    <p>
	                                                    <p><input class="table_input boderbbt" type="text" value="${spvCaseFlowOutInfoVO.toSpvCashFlow.payerBank }"  name="items[${status2.index }].payerBank"/></p>
	                                                    <p>
	                                                </td>
	
	                                                <td class="text-left" >
	                                                    <input class="boderbbt" style="border:none;width: 50px;" type="text" value="${spvCaseFlowOutInfoVO.toSpvCashFlow.amount }" name="items[${status2.index }].payerAmount" />万元
	                                                </td>
	                                                <td>
	                                                    <input class="table_input boderbbt" type="text" value="${spvCaseFlowOutInfoVO.toSpvCashFlow.receiptNo }" name="items[${status2.index }].receiptNo" />
	                                                </td>
	                                                <td>
	                                                    <select  id="select_direction" class="table-select boderbbt" name="items[${status2.index }].voucherNo"  onChange="this.value" >
	                                                        <option <c:if test="${spvCaseFlowOutInfoVO.toSpvCashFlow.direction eq ''}"> selected="selected" </c:if> value="">请选择</option>
	                                                        <option <c:if test="${spvCaseFlowOutInfoVO.toSpvCashFlow.direction eq '转账'}"> selected="selected" </c:if> value="转账">转账</option>                                                                                                                                                
															<option <c:if test="${spvCaseFlowOutInfoVO.toSpvCashFlow.direction eq '刷卡'}"> selected="selected" </c:if> value="刷卡">刷卡</option>  
															<option <c:if test="${spvCaseFlowOutInfoVO.toSpvCashFlow.direction eq '现金'}"> selected="selected" </c:if> value="现金">现金</option> 
	                                                    </select>
	                                                </td>
	                                                <td id="td_file${status2.index }" >
                                                	<c:forEach items="${spvCaseFlowOutInfoVO.toSpvReceiptList}" var="toSpvReceiptList" varStatus="status6">
	                                                 	<a class="response" target="_blank" href="http://filesvr.centaline.com.cn/aist-filesvr-web/JQeryUpload/getfile?fileId=${toSpvReceiptList.attachId}" 
	                                                 	title="${toSpvReceiptList.comment}" 
	                                                 	alt="${toSpvReceiptList.comment}">
														<input type="hidden"  name ="items[${status2.index }].fileId" value = "${toSpvReceiptList.attachId}"/>
														<input type="hidden" name ="items[${status2.index }].fileName" value = "${toSpvReceiptList.comment}" />
															<button type="button" class="btn btn-sm btn-default" >
															${toSpvReceiptList.comment.length()>5?toSpvReceiptList.comment.substring(0,5):toSpvReceiptList.comment}
															
															<i class="icon iconfont icon_x" onClick="$(this).parent().remove();return false;">&#xe60a;</i></button>
														</a>
                                                	 </c:forEach>   
                                                		 <span class="btn_file${status2.index }">                                                                                                                                                                
															<input id="fileupload_${status2.index }" style="display:none" type="file" name="files[]" multiple="" data-url="http://a.sh.centanet.com/aist-filesvr-web/servlet/jqueryFileUpload" data-sequential-uploads="true">                                                                                                                                                 
															<img class="bnt-flie" src="${ctx}/static/trans/img/bnt-flie.png" alt="点击上传" style="cursor:pointer;" onClick="$('#fileupload_${status2.index }').trigger('click');">                                                                        
														</span>  
	                                                </td>
	                                                <td align="center">
		                                                <a href="javascript:void(0)" onClick="getTR(${spvChargeInfoVO.spvCaseFlowOutInfoVOList.size()})">添加</span></a>
		                                                <a href="javascript:void(0)" onClick="getDelHtml(this,${spvCaseFlowOutInfoVO.toSpvCashFlow.pkid })">删除</span></a>
	                                                </td>
	                                            </tr>
	                                       </c:forEach>
                                        </tbody>
                                    </table>
                                    <div class="form-btn">
                                <div class="text-center">
                                    <button type="button" onclick="saveRe()" class="btn btn-success mr15">保存</button>
                                    <button type="button" onclick="rescCallbocak()"class="btn btn-default mr15">关闭</button>
                                    <a onclick="sumbitRe()" class="btn btn-success">提交</a>
                                </div>
                                </div>
                                </div>
                            </form>
                        </div>

                    </div>
                     <div class="ibox-content clearfix space30">
                        <div class="title">
                            <strong>审核意见</strong>
                        </div>
                        <div class="view-content ">
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
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- main End -->


        </div>
    </div>
<content tag="local_script">
  <!-- Mainly scripts -->
<script src="${ctx}/static/js/jquery-2.1.1.js"></script>
<script src="${ctx}/static/js/bootstrap.min.js"></script>
<script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<!-- Custom and plugin javascript -->
<script src="${ctx}/static/js/inspinia.js"></script>
<script src="${ctx}/static/js/plugins/pace/pace.min.js"></script><%-- 
<script src="${ctx}/static/trans/js/response/js/jkresponsivegallery.js"></script> --%>

<!-- 上传附件相关 --> 
<script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script><%-- 
<script src="${ctx}/js/trunk/JSPFileUpload/tmpl.min.js"></script>  --%>
<script src="${ctx}/js/trunk/JSPFileUpload/load-image.min.js"></script> 
<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload.js"></script> 
<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-fp.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-ui.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script> 
<script src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script> 
<script src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script> 
<script src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script> 
<!-- 上传附件 结束 -->
<!-- stickup plugin -->
<script src="${ctx}/static_res/trans/js/spv/jkresponsivegallery.js"></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> <%-- 
<script src="${ctx}/js/template.js" type="text/javascript"></script> <!-- stickup plugin --> --%>
<script src="${ctx}/static/trans/js/spv/spvRecordedApp.js"></script>

<script>

$(function() {
	$('.response').responsivegallery();
    $(".icon_x").click(function() {
        $(this).parent().parent().remove();
        return false;
    });
    var fileRowSize = ${spvChargeInfoVO.spvCaseFlowOutInfoVOList.size()};
    for(var i=0;i<fileRowSize;i++){
    	render_fileupload(i);
    }

});

function rescCallbocak(){
	 /*   if($("#urlType").val() == 'myTask'){    	 
		   window.opener.location.reload(); //刷新父窗口
   	   window.close(); //关闭子窗口.
	     }else{ */
	    	 window.location.href = ctx+"/spv/spvList";
	    // }
	}

</script>
</content>
</body>
</html>