<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>银行返利-查看</title>
    <link rel="stylesheet" href="<c:url value='/static/css/bootstrap.min.css' />">
    <link rel="stylesheet" href="<c:url value='/static/css/select2.min.css' />">
    <link rel="stylesheet" href="<c:url value='/static/font-awesome/css/font-awesome.css' />">

    <%-- jdGrid相关  --%>
    <link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
    <link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
    <link href="<c:url value='/css/style.css' />" rel="stylesheet">

    <!-- aist列表样式 -->
    <link href="<c:url value='/css/common/aist.grid.css' />" rel="stylesheet">

    <link rel="stylesheet" href="<c:url value='/static/css/animate.css' />" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/static/css/style.css' />" rel="stylesheet">

    <!-- stickUp fixed css -->
    <link rel="stylesheet" href="<c:url value='/static/css/plugins/stickup/stickup.css' />">
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/stickmenu.css' />">

    <!-- index_css  -->
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />">
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />">
    <link rel="stylesheet" href="<c:url value='/static_res/trans/css/common/report.css' />">

    <link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">

    <link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
    <!-- 分页控件 -->
    <link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet"/>

    <!-- 必须CSS -->
    <link rel="stylesheet" href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" type="text/css"/>
    <content tag="pagetitle">银行返利-查看</content>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<input type="hidden" id="ctx" value="${ctx}"/>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content" >
        <form action="#" method="post" id="changeRebateForm">
            <input type="hidden" name="toBankRebate.pkid" value="${rebate.toBankRebate.pkid}"/>
            <input type="hidden" id="" name="toBankRebate.guaranteeCompId" value="${rebate.toBankRebate.guaranteeCompId}" />
            <div class="form_list">
                <div class="title">银行返利-查看</div>
                <div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left_small">担保公司</label>
                        <aist:dict id="guaranteeCompany" name="toBankRebate.guaranteeCompany" display="select"
                                   dictType="bank_rebate_guarantee" clazz="select_control"
                                   defaultvalue="${rebate.toBankRebate.guaranteeCompany}" hasEmpty="true"/>
                    </div>
                    <div class="form_content">
                        <label class="control-label sign_left_small">返利总金额:</label>
                        <label class="select_control sign_right_one">${rebate.toBankRebate.rebateTotal}</label>
                    </div>
                    <c:if test="${rebate.toBankRebate.companyAccount != null}">
                        <div class="form_content">
                            <label class="control-label sign_left_small">公司账户</label>
                            <label class="select_control sign_right_one">${rebate.toBankRebate.companyAccount}</label>
                        </div>
                    </c:if>
                </div>
            </div>
            <div class="form_list">
                <div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left_small">申请人</label>
                        <label class="select_control sign_right_one">${rebate.toBankRebate.applyPerson}</label>
                    </div>
                    <div class="form_content">
                        <label class="control-label sign_left_small">录入人所在部门</label>
                        <label class="select_control sign_right_one">${org.orgName}</label>
                    </div>
                    <div class="form_content">
                        <label class="control-label sign_left_small">录入时间：</label>
                        <label class="select_control sign_right_one"><fmt:formatDate value="${rebate.toBankRebate.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
                    </div>
                </div>
            </div>
            <div class="form_list">
                <div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left_small">备注：</label>
                        <textarea class="select_control" name="toBankRebate.comment"
                                  cols="100">${rebate.toBankRebate.comment}</textarea>
                    </div>
                </div>
            </div>


            <div class="form_list table-capital">
                <div class="table-box">
                    <table id="info" class="table table-bordered customerinfo">
                        <thead>
                        <tr>
                            <th>案件编号</th>
                            <th>查看</th>
                            <th>银行名称</th>
                            <th>返利金额</th>
                            <th>权证返利</th>
                            <th>业务返利</th>
                            <th>状态</th>
                            <th>审批查看</th>
                        </tr>
                        </thead>
                        <tbody id="t_body_data_contents">
                        <c:forEach items="${rebate.toBankRebateInfoList}" var="item" varStatus="status">
                            <tr id="tr_${status.index}">
                                <td>
                                    ${item.caseCode}
                                </td>
                                <td width="10%">
                                    <a target="_blank" href="${ctx}/case/caseDetail?caseId=${item.caseId}">查看案件</a>
                                </td>
                                <td width="17%">
                                    <aist:dict id="bank_${status.index}" name="1" display="select"
                                               dictType="bank_rebate_bank" defaultvalue="${item.bankName}"
                                               clazz="form-control"/>
                                </td>
                                <td width="17%">
                                        ${item.rebateMoney}
                                </td>
                                <td width="12%">
                                        ${item.rebateWarrant}
                                </td>
                                <td width="12%">
                                        ${item.rebateBusiness}
                                </td>
                                <td width="12%">
                                    <c:if test="${rebate.toBankRebate.status == 9 && item.finishTime == null}">
                                        审批中
                                    </c:if>
                                    <c:if test="${rebate.toBankRebate.status == 9 && item.finishTime != null}">
                                        审批完成
                                    </c:if>
                                </td>
                                <td width="12%">
                                    <c:if test="${rebate.toBankRebate.status == 9 && item.finishTime != null}">
                                        <a href="javascript:void(0)" onclick="showReportApprove('${item.reportCode}')"> 审批信息</a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <br/>
            <div class="form_list">
                <div class="title">审批记录</div>
                <div class="line">
                    <div class="view-content">
                        <table id="gridTable" class=""></table>
                        <div id="gridPager"></div>
                    </div>
                </div>
            </div>
            <div class="text-center mt40 mb30">
                <button type="button" class="btn btn-grey" id="caseInfoClean" onclick="javascript:history.back()">返回
                </button>
            </div>
        </form>
    </div>
</div>
<!-- End page wrapper-->
<!-- Mainly scripts -->
<content tag="local_script">
    <script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
    <script src="<c:url value='/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
    <script src="<c:url value='/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
    <!-- jqGrid -->
    <script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
    <script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
    <!-- Custom and plugin javascript -->
    <script src="<c:url value='/js/inspinia.js' />"></script>
    <script src="<c:url value='/js/plugins/pace/pace.min.js' />"></script>
    <!-- 分页控件  -->
    <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
    <script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
    <script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
    <script src="<c:url value='/js/plugins/autocomplete/jquery.autocomplete.js' />"></script>
    <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
    <script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
    <script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
    <script src="<c:url value='/js/common/textarea.js' />"></script>
    <!-- 银行返利  -->
    <script src="<c:url value='/js/trunk/bankRebate/bankRebate.js' />"></script>

    <script>
        $(document).ready(function () {
            $('input,select,textarea').attr("disabled",true);
            //审批记录列表
            AttachmentList.init('${ctx}','/quickGrid/findPage','gridTable','gridPager','${rebate.toBankRebate.guaranteeCompId}');
        });
    </script>
</content>
</body>
</html>