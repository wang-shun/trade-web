<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>入账</title>
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
    <link rel="stylesheet" href="${ctx}/static/css/plugins/datapicker/datepicker3.css">
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/iconfont.css" >
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/table.css" />
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/input2.css" />
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/see2.css" />
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/spv2.css" />
    
    <link href="${ctx}/js/viewer/viewer.min.css" rel="stylesheet" />
    <!-- 必须CSS -->
	<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />

</head>

<body >
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
            <div class="row">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox-content space30" >
                        <div class="agree-tile"> 资金入账申请  </div>
                        <div class="info_content">
                            <div class="line">
                                <p>
                                    <label> 产品类型  </label>
                                    <span class="info_one"  id="prdCode" >${spvBaseInfoVO.toSpv.prdCode==1?"光大四方资金监管":"" }</span>
                                </p>
                                <p>
                                    <label>
                                        	监管金额
                                    </label>
                                    <span class="info_one" id="amount" value="${spvBaseInfoVO.toSpv.amount}" >${spvBaseInfoVO.toSpv.amount}万元人民币</span>
                                </p>

                                <p>
                                    <label>
                                     	   物业地址
                                    </label>
                                    <span class="info" id="prAddr" ><span class="demo-top" title="${spvBaseInfoVO.toSpvProperty.prAddr}">${spvBaseInfoVO.toSpvProperty.prAddr}</span></span>
                                </p>

                            </div>
                            <div class="line">
                                <p>
                                    <label>
                                      	  收款人名称
                                    </label>
                                    <span class="info_one" id="spvAccountName" ><span class="demo-top" title="${spvBaseInfoVO.toSpvAccountList[2].name==1?"上海中原物业顾问有限公司":""}">${spvBaseInfoVO.toSpvAccountList[2].name==1?"上海中原物业顾问有限公司":""}</span></span>
                                </p>

                                <p>
                                    <label>
                                        	收款人账户
                                    </label>
                                    <span class="info_one" id="spvAccountCode"><span class="demo-top" title="${spvBaseInfoVO.toSpvAccountList[2].account}">${spvBaseInfoVO.toSpvAccountList[2].account}</span></span>
                                </p>

                                <p>
                                    <label>
                                        	收款人开户行
                                    </label>
                                    <span class="info" id="spvAccountBank" >光大银行市北支行<%-- ${spvBaseInfoVO.toSpvAccountList[2].bank} --%></span>
                                </p>
                            </div>
                        </div>
                        <div class="mt20">
                            <div class="agree-tile">
                                	入账申请信息
                            </div>
                        <form class="form-inline table-capital" id="teacForm" >
                        
                        <input type="hidden" name="type" value="addCashFlow" />
                        <input type="hidden" name="prdCode" value="${spvBaseInfoVO.toSpv.prdCode==1?"光大四方资金监管":"" }" />
                        <input type="hidden" name="amount" value="${spvBaseInfoVO.toSpv.amount}" />
                        <input type="hidden" name="prAddr" value="${spvBaseInfoVO.toSpvProperty.prAddr}" />
                        <input type="hidden" name="spvAccountName" value="${spvBaseInfoVO.toSpvAccountList[2].name}" />
                        <input type="hidden" name="spvAccountCode" value="${spvBaseInfoVO.toSpvAccountList[2].account}" />
                        <input type="hidden" name="spvAccountBank" value="${spvBaseInfoVO.toSpvAccountList[2].bank}" />
                        <input type="hidden" name="spvConCode" value="${spvBaseInfoVO.toSpv.spvCode}" />
                        <input type="hidden" name="caseCode" value="${spvBaseInfoVO.toSpv.caseCode}" />
                        
                         <%-- 流程相关 --%>
						<input type="hidden" id="taskId" name="taskId" value="" />
						<input type="hidden" id="instCode" name="instCode" value="" />
						<input type="hidden" id="urlType" name="source" value="" />
						<input type="hidden" id="handle" name="handle" value="addCashFlow" />
                         <%-- 保存数据返回的pkid --%>
						<input type="hidden"  id="toSpvCashFlowApplyPkid" name="toSpvCashFlowApplyPkid" />
						<input type="hidden"  id="ToSpvCashFlowPkid" name="ToSpvCashFlowPkid" value="" />
						<input type="hidden"  id="ToSpvReceiptPkid" name="ToSpvReceiptPkid" value="" />
						
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
                                        <th>操作</th>
                                    </thead>
                                    <tbody id="addTr">
                                    </tbody>
                                </table>
                                <div class="form-btn">
                            <div class="text-center">
                                <button type="button" onclick="rescCallbocak()"  class="btn btn-default mr15">关闭</button>
                                <a onclick="sumbitRe()" class="btn btn-success">提交</a>
                            </div>
                        </div>
                            </div>
                        </form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- main End -->

        </div>
    </div>
<content tag="local_script">
<!-- 上传附件相关 --> 
<script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script> 

<script src="${ctx}/js/trunk/JSPFileUpload/load-image.min.js"></script> 
<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload.js"></script> 
<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-fp.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-ui.js"></script>

<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script> 
<script src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>

<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>
<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script> 
<script src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script> 
<script src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script> 
<!-- 上传附件 结束 -->

<script src="${ctx}/static_res/trans/js/spv/pace.min.js"></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> 
<script src="${ctx}/js/template.js" type="text/javascript"></script>

<script src="${ctx}/static_res/trans/js/spv/spvReDetails.js"></script>
<script src="${ctx}/js/viewer/viewer.min.js"></script>
<!-- 必须JS -->
<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>

<input type="hidden" id="ctx" value="${ctx}" />
<input type="hidden" id="appCtx" value="<aist:appCtx appName='shcl-filesvr-web'/>" />

<script type="text/javascript">
$(function(){
		$('.demo-top').poshytip({
			className: 'tip-twitter',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'center',
			alignY: 'top',
			offsetX: 8,
			offsetY: 5,
		});
	});

</script>
</content>
</body>

</html>