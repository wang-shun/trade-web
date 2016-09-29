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

<body>
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
                                    <span class="info_one">光大四方资金监管</span>spvRecordedInfoVO
                                </p>
                                <p>
                                    <label>
                                      	  监管金额
                                    </label>
                                    <span class="info_one">300万元人民币</span>
                                </p>

                                <p>
                                    <label>
                                      	  物业地址
                                    </label>
                                    <span class="info">上海市长宁区畅园3栋1702室</span>
                                </p>

                            </div>
                            <div class="line">
                                <p>
                                    <label>
                                     	   收款人名称
                                    </label>
                                    <span class="info_one">中原地产监管账户</span>
                                </p>

                                <p>
                                    <label>
                                        	收款人账户
                                    </label>
                                    <span class="info_one">62248757878587</span>
                                </p>

                                <p>
                                    <label>
                                        	收款人开户行
                                    </label>
                                    <span class="info">中原地产监管账户开户行</span>
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
								<input type="hidden" id="taskId" name="taskId" value="${taskId }">
								<input type="hidden" id="instCode" name="instCode" value="${instCode}">
								<input type="hidden" id="source" name="source" value="${source}">
								<input type="hidden" id="urlType" name="source" value="${urlType}">
								<input type="hidden" id="handle" name="handle" value="${handle }">
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
                                        </thead>
                                        <tbody id="addTr">
                                            <tr>
                                                <td>
                                                    <div class="big">林小胖</div>
                                                </td>
                                                <td>
                                                    <div class="marspace">6224567890123456789</div>
                                                    <div class="marspace">中国农村合作信用社</div>
                                                </td>

                                                <td class="text-left" >
                                                    <div class="big">50万元</div>
                                                </td>
                                                <td>
                                                    <div class="big">DJZP-000001</div>
                                                </td>
                                                <td>
                                                    <div class="big">转账凭证</div>
                                                </td>
                                                <td>
                                                    <a class="response" href="../static/trans/img/uplody01.png" title="凭证1"><button type="button" class="btn btn-sm btn-default" ><i class="icon iconfont icon_y">&#xe635;</i> 凭证1</button></a>
                                                    <a class="response" href="../static/trans/img/uplody02.png" title="凭证2"><button type="button" class="btn btn-sm btn-default" data-toggle="modal" data-target="#showImg"  ><i class="icon iconfont icon_y">&#xe635;</i>凭证2</button></a>
                                                </td>
                                            </tr>
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
                            <div class="view clearfix">
                                <p>
                                   <span class="auditor">审核人：<em>张小核(资金监管专员)</em></span>
                                   <span class="result pink_bg">未通过</span>
                                   <span class="time">审核日期:<em>2016-9-12</em></span>
                                </p>
                                <p>
                                    <span class="auditing">审核意见</span>
                                    <em class="view_content">账号与附件不一致，重新填写</em>
                                </p>
                            </div>
                            <div class="view clearfix">
                                <p>
                                   <span class="auditor">审核人：<em>张小核(资金监管专员)</em></span>
                                   <span class="result pink_bg">未通过</span>
                                   <span class="time">审核日期:<em>2016-9-12</em></span>
                                </p>
                                <p>
                                    <span class="auditing">审核意见</span>
                                    <em class="view_content">账号与附件不一致，重新填写,账号与附件不一致，重新填写账号与附件不一致，重新填写账号与附件不一致，重新填写,账号与附件不一致，重新填写账号与附件不一致，重新填写账号与附件不一致，重新填写,账号与附件不一致，重新填写账号与附件不一致，重新填写</em>
                                </p>
                            </div>

                            <div class="view clearfix">
                                <p>
                                   <span class="auditor">审核人：<em>张小核(资金监管专员)</em></span>
                                   <span class="result pink_bg">未通过</span>
                                   <span class="time">审核日期:<em>2016-9-12</em></span>
                                </p>
                                <p>
                                    <span class="auditing">审核意见</span>
                                    <em class="view_content">账号与附件不一致，重新填写</em>
                                </p>
                            </div>
                        </div>
                        </div>


                        <div class="submitter">
                            提交人：<span>张三(业务员)</span>
                        </div>
                        <div class="excuse">
                            <form action="">
                                <textarea name="" id="" placeholder="请填写审核意见" style="width:100%; resize: none;height:140px;border-radius: 3px;border: 1px solid #d8d8d8;padding:10px;"></textarea>
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

<!-- Mainly scripts -->
<script src="${ctx}/static/js/jquery-2.1.1.js"></script>
<script src="${ctx}/static/trans/js/spv/spvRecordShow.js"></script>
<script src="${ctx}/static/js/bootstrap.min.js"></script>
<script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<!-- Custom and plugin javascript -->
<script src="${ctx}/static/js/inspinia.js"></script>
<script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
<script src="${ctx}/static/trans/js/response/js/jkresponsivegallery.js"></script>


<script>
$(function() {
    $('.response').responsivegallery();
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



</body>

</html>