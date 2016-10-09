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
<link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css">
<link rel="stylesheet" href="${ctx}/static/css/animate.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/static/css/style.css" rel="stylesheet">
<!-- stickUp fixed css -->
<link rel="stylesheet" href="${ctx}/static/css/plugins/stickup/stickup.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/stickmenu.css">
<link rel="stylesheet" href="${ctx}/static/css/plugins/aist-steps/steps.css">
<link rel="stylesheet" href="${ctx}/static/trans/static/css/plugins/toastr/toastr.min.css">
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/see.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/spv.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/response/jkresponsivegallery.css " />
</head>

<body >
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
                                    <span class="info_one" id="amount">${spvBaseInfoVO.toSpv.amount}万元人民币</span>
                                </p>

                                <p>
                                    <label>
                                     	   物业地址
                                    </label>
                                    <span id="prAddr" >${spvBaseInfoVO.toSpvProperty.prAddr}</span>
                                </p>

                            </div>
                            <div class="line">
                                <p>
                                    <label>
                                      	  收款人名称
                                    </label>
                                    <span class="info_one" id="spvAccountName" >${spvBaseInfoVO.toSpvAccountList[2].name}</span>
                                </p>

                                <p>
                                    <label>
                                        	收款人账户
                                    </label>
                                    <span class="info_one" id="spvAccountCode">${spvBaseInfoVO.toSpvAccountList[2].account}</span>
                                </p>

                                <p>
                                    <label>
                                        	收款人开户行
                                    </label>
                                    <span class="info" id="spvAccountBank" >${spvBaseInfoVO.toSpvAccountList[2].bank}</span>
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
						<input type="hidden"  id="toSpvCashFlowApplyPkid" name="toSpvCashFlowApplyPkid" value="" />
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
                                        <th>操作</th>
                                    </thead>
                                    <tbody id="addTr">
                                    </tbody>
                                </table>
                                <div class="form-btn">
                            <div class="text-center">
                                <button type="button" onclick="saveRe()" class="btn btn-success mr15">保存</button>
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
<!-- Mainly scripts -->
<script src="${ctx}/static/js/jquery-2.1.1.js"></script>
<script src="${ctx}/static/trans/js/spv/spvReDetails.js"></script>
<script src="${ctx}/static/js/bootstrap.min.js"></script>
<script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<!-- Custom and plugin javascript -->
<script src="${ctx}/static/js/inspinia.js"></script>
<script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
<script src="${ctx}/static/trans/js/response/js/jkresponsivegallery.js"></script>

<script>

$(function() {
    $(".icon_x").click(function() {
        $(this).parent().parent().remove();
        return false;
    });


});
$('.response').responsivegallery();

function rescCallbocak(){
 	 /*   if($("#urlType").val() == 'myTask'){    	 
 		   window.opener.location.reload(); //刷新父窗口
     	   window.close(); //关闭子窗口.
	     }else{ */
	    	 window.location.href = ctx+"/spv/spvList";
	    // }
	}

</script>
<input type="hidden" id="ctx" value="${ctx}" />
</body>

</html>