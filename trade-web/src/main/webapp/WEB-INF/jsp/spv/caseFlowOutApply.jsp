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
<script type="text/javascript">
	var ctx = "${ctx}";
	var source = "${source}";
	if ("${idList}" != "") {
		var idList = eval("(" + "${idList}" + ")");
	} else {
		var idList = [];
	}
</script>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
    <div id="wrapper">
        <%-- 流程相关 --%>
		<input type="hidden" id="taskId" name="taskId" value="${taskId }">
		<input type="hidden" id="instCode" name="instCode" value="${instCode}">
		<input type="hidden" id="source" name="source" value="${source}">
		<input type="hidden" id="urlType" name="source" value="${urlType}">
		<input type="hidden" id="handle" name="handle" value="${handle }">
		<input type="hidden" id="spvCode" name="spvCode" value="${spvCode }">
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
                                资金放款方案
                            </div>
                            <form class="form-inline table-capital">
                                <div class="table-box" >
                                    <table class="table table-bordered  customerinfo">
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
                                        <c:forEach items="${spvBaseInfoVO.toSpvDeDetailList }" var="toSpvDeDetail" varStatus="status1">
                                        <tr>
                                            <td>
                                                <c:if test="${status1.count eq 1 }">
                                                ${fn:length(spvBaseInfoVO.toSpvDeDetailList) }
                                                </c:if>
                                            </td>
                                            <td>
                                                <aist:dict id="" name="" clazz="form-control input-one"
									            display="select"  dictType="SPV_DE_COND"  
									            ligerui='none' defaultvalue="${toSpvDeDetail.deCondCode }" ></aist:dict>
                                            </td>
                                            <td>
                                                ${toSpvDeDetail.deAmount }万元
                                            </td>
                                            <td>
                                                ${toSpvDeDetail.deAmount }万元
                                            </td>
                                            <td>
                                                ${toSpvDeDetail.deAmount }万元
                                            </td>
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
                                        <c:forEach items="${spvChargeInfoVO.spvCaseFlowOutInfoVOList}" var="spvCaseFlowOutInfoVO" varStatus="status2">
                                        <tr>
                                            <td>
                                                <p class="big">
                                                    <a href="javascript:void(0);">
                                                        ${spvCaseFlowOutInfoVO.toSpvCashFlowApply.cashflowApplyId }
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
                                                    ${spvCaseFlowOutInfoVO.toSpvCashFlowApply.usage eq in?'入账':'出账' }
                                                    </span>
                                                </p>
                                                <p class="big">
                                                    ${spvCaseFlowOutInfoVO.toSpvCashFlow.amount }
                                                </p>
                                            </td>
                                            <td>
                                                <p><span class="pink">付：</span>${spvCaseFlowOutInfoVO.toSpvCashFlow.payer }&nbsp;&nbsp;${spvCaseFlowOutInfoVO.toSpvCashFlow.payerAcc }/${spvCaseFlowOutInfoVO.toSpvCashFlow.payerBank }</p>
                                                <p><span class="navy">收：</span>${spvCaseFlowOutInfoVO.toSpvCashFlow.receiver }&nbsp;&nbsp;${spvCaseFlowOutInfoVO.toSpvCashFlow.receiverAcc }/${spvCaseFlowOutInfoVO.toSpvCashFlow.receiverBank }</p>
                                            </td>
                                            <td>
                                                <p class="smll_sign">
                                                    <i class="sign_normal">录入</i>
                                                    <a href="javascript:void(0)">${createByName }&nbsp;</a><fmt:formatDate value="${spvCaseFlowOutInfoVO.toSpvCashFlow.createTime }" pattern="yyyy-MM-dd"/>
                                                </p>
                                                <p class="smll_sign">
                                                    <i class="sign_normal">结束</i>             
                                                    <fmt:formatDate value="${spvCaseFlowOutInfoVO.toSpvCashFlow.closeTime }" pattern="yyyy-MM-dd"/>
                                                </p>
                                            </td>
                                            <td>
                                                <p>
                                                   上海市长宁区延安西路998号1弄202
                                                </p>
                                                <p class="smll_sign">
                                                    审核人：<a href="javascript:void(0)">
                                                    ${applyAuditorName }&gt;${ftPreAuditorName }&gt;${ftPostAuditorName }</a>
                                                </p>
                                            </td>
                                        </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <form id="workFolwApplyForm" style="display:none;">
                              <input type="hidden" name="toSpvCashFlowApply.cashflowApplyCode" value="${spvChargeInfoVO.toSpvCashFlowApply.cashflowApplyCode }" />
                              <input type="hidden" name="toSpvCashFlowApply.spvCode" value="${spvCode }" />
                              <input type="hidden" name="toSpvCashFlowApply.usage" value="out" />
                        </form>
                        <div class="mt20">
                            <div class="agree-tile">
                                出账凭证信息
                            </div>
                            <form class="form-inline table-capital">
                                <div class="table-box" >
                                    <table class="table table-bordered customerinfo">
                                        <thead>
                                        <th>凭证类型</th>
                                        <th>凭证附件</th>
                                        </thead>
                                        <tbody id="addTr2">
                                        <tr>
                                            <td width="310">
                                                <select name="" class="table-select boderbbt">
                                                    <option value="">请选择</option>
                                                    <option value="">转账凭证</option>
                                                </select>
                                            </td>
                                            <td>
                                                <span class="btn_file">
                                                    <input type="file" class="file" />
                                                    <img class="bnt-flie" src="${ctx }/static_res/trans/img/bnt-flie.png" alt="" />
                                                </span>
                                            </td>
                                        </tr>

                                        </tbody>
                                    </table>

                                </div>
                            </form>
                        </div>
                        <div class="mt20">
                            <div class="agree-tile">
                                出账流水信息
                            </div>
                            <form class="form-inline table-capital">
                                <div class="table-box" >
                                    <table class="table table-bordered customerinfo">
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
                                                <input class="boderbbt" style="border:none;width: 50px;" type="text" placeholder="金额" name="spvCaseFlowOutInfoVOList[0].toSpvCashFlow.amount" />万元
                                            </td>
                                            <td>
                                                <input class="table_input boderbbt" type="text" placeholder="请输入编号" name="spvCaseFlowOutInfoVOList[0].toSpvVoucher.attachId" />
                                            </td>
                                            <td>
                                                <select name="" class="table-select boderbbt">
                                                    <option value="">请选择</option>
                                                    <option value="">转账凭证</option>
                                                </select>
                                            </td>
                                            <td>
                                                <a class="response" href="${ctx }/static_res/trans/img/uplody01.png" title="凭证3"><button type="button" class="btn btn-sm btn-default" >凭证3<i class="icon iconfont icon_x">&#xe60a;</i></button></a>
                                                <a class="response" href="${ctx }/static_res/trans/img/uplody02.png" title="凭证4"><button type="button" class="btn btn-sm btn-default" >凭证4<i class="icon iconfont icon_x">&#xe60a;</i></button></a>
                                                <span class="btn_file">
                                                    <input type="file" class="file" />
                                                    <img class="bnt-flie" src="${ctx }/static_res/trans/img/bnt-flie.png" alt="" />
                                                </span>
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
                            <c:forEach items="${spvChargeInfoVO.toSpvAduitList }" var="toSpvAduit" varStatus="status3">
                            <div class="view clearfix">
                                <p>
                                   <span class="auditor">审核人：<em>${toSpvAduit.operator }(资金监管专员)</em></span>
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
                        
                        <div class="submitter">
                            提交人：<span>张三(业务员)</span>
                        </div>
                        <div class="excuse">
                            <form action="">
                                <textarea name="toSpvAduitList[0].content" placeholder="请填写审核意见" style="width:100%; resize: none;height:140px;border-radius: 3px;border: 1px solid #d8d8d8;padding:10px;"></textarea>
                            </form>
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
                </div>
            </div>
            <!-- main End -->
        </div>

    <!-- Mainly scripts -->
    <script src="${ctx}/static_res/trans/js/spv/jquery-2.1.1.js"></script>
    <script src="${ctx}/static_res/trans/js/spv/bootstrap.min.js"></script>
    <script src="${ctx}/static_res/trans/js/spv/jquery.metisMenu.js"></script>
    <script src="${ctx}/static_res/trans/js/spv/jquery.slimscroll.min.js"></script>
    <!-- Custom and plugin javascript -->
    <script src="${ctx}/static_res/trans/js/spv/inspinia.js"></script>
    <script src="${ctx}/static_res/trans/js/spv/pace.min.js"></script>
    <!-- stickup plugin -->
    <script src="${ctx}/static_res/trans/js/spv/jkresponsivegallery.js"></script>
    <script src="${ctx}/static_res/trans/js/spv/spvRecorded.js"></script>

<script>

$(function() {
    $(".icon_x").click(function() {
        $(this).parent().parent().remove();
        return false;
    });


});
$('.response').responsivegallery();


var sum = 0;

function getAtr(i){
    $str='';
    $str+="<tr>";
    $str+=" <td><input class='table-input-one' type='text' value='请输入姓名' /></td>";
    $str+="<td><p><input class='table_input' type='text' value='请输入银行卡号' /></p><p><input class='table_input' type='text' value='请输入银行名称' /></p></td>";
    $str+="<td><input style='border:none;width: 50px;' type='text' value='金额' />万元</td>";
    $str+="<td><input class='table_input' type='text' value='请输入编号' /></td>";
    $str+="<td><select name='' class='table-select'><option value=''>请选择</option><option value=''>转账凭证</option></select></td>";
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

</body>

</html>