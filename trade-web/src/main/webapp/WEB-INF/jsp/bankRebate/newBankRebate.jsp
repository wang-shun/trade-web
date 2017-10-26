<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>银行返利-新增</title>
    <link rel="stylesheet" href="<c:url value='/static/css/bootstrap.min.css' />">
    <link rel="stylesheet" href="<c:url value='/static/css/select2.min.css' />">
    <link rel="stylesheet" href="<c:url value='/static/font-awesome/css/font-awesome.css' />">

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
    <link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />

    <!-- 必须CSS -->
    <link rel="stylesheet" href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" type="text/css"/>
    <content tag="pagetitle">银行返利-新增</content>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<input type="hidden" id="ctx" value="${ctx}"/>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="ibox-content" id="reportFive">
        <form action="#" id="changeRebateForm">
            <div class="form_list">
                <div class="title">银行返利-新增</div>
                <div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left_small">担保公司</label>
                        <aist:dict id="guaranteeCompany" name="toBankRebate.guaranteeCompany" display="select"
                                   dictType="bank_rebate_guarantee" clazz="select_control"/>
                    </div>
                    <div class="form_content">
                        <label class="control-label sign_left_small">返利总金额</label>
                        <input type="text" class="select_control sign_right_one" id="rebateMoney" name="toBankRebate.rebateTotal"
                               value="">
                    </div>
                </div>
            </div>
            <div class="form_list">
                <div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left_small">申请人</label>
                        <input type="text" class="select_control sign_right_one" id="applyer" name="toBankRebate.applyPerson"
                               readonly="readonly" value="${user.realName}"/>
                    </div>
                    <div class="form_content">
                        <label class="control-label sign_left_small">录入人所在部门</label>
                        <input type="text" class="select_control sign_right_one" id="applyDepartment"
                               readonly="readonly" value="${user.serviceDepName }"/>
                        <input type="hidden" name="toBankRebate.deptId" value="${user.serviceDepId}" />
                    </div>
                    <div class="form_content">
                        <label class="control-label sign_left_small">录入时间：</label>
                        <c:set var="now" value="<%=new java.util.Date()%>"/>
                        <input type="text" class="select_control sign_right_one" id="applyTime" name="applyTime"
                               readonly="readonly"
                               value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                    </div>
                </div>
            </div>
            <div class="form_list">
                <div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left_small">备注：</label>
                        <textarea class="select_control" name="toBankRebate.comment" cols="100"></textarea>
                    </div>
                </div>
            </div>


            <div class="form_list table-capital">
                <div class="table-box">
                    <table id="info" class="table table-bordered">
                        <thead>
                        <tr>
                            <th>案件编号</th>
                            <th>查看</th>
                            <th>银行名称</th>
                            <th>返利金额</th>
                            <th>权证返利</th>
                            <th>业务返利</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="t_body_data_contents">
                        <tr id="tr_0">
                            <input type="hidden" id="" name="" value=""/>
                            <td>
                                <input class="form-control" type="text" name="toBankRebateInfoList[0].caseCode" readonly placeholder="请选择案件" onclick="showCase(0)">
                                <input id="ccai_0" type="hidden" name="toBankRebateInfoList[0].ccaiCode" />
                            </td>
                            <td width="10%"><a>查看案件</a></td>
                            <td width="17%">
                                <aist:dict id="bank_0" name="toBankRebateInfoList[0].bankName" display="select" dictType="bank_rebate_bank" clazz="form-control"/>
                            </td>
                            <td width="17%"><input id="rebateMoney_0" class="form-control" type="text"
                                                   name="toBankRebateInfoList[0].rebateMoney" value=""></td>
                            <td width="12%"><input id="warrantMoney_0" class="form-control" type="text"
                                                   name="toBankRebateInfoList[0].rebateWarrant" value="" readonly></td>
                            <td width="12%"><input id="businessMoney_0" class="form-control" type="text"
                                                   name="toBankRebateInfoList[0].rebateBusiness" value="" readonly></td>
                            <td></td>
                        </tr>
                        <!-- </tbody> -->
                    </table>
                    <div class="form_list">
                        <div class="line">
                            <div class="form_content">
                                <button id="addRecord" type="button" class="btn btn-success mr5">添加新记录</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form_list">
                <div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left_small">已录入金额：</label>
                        <input type="text" class="select_control sign_right_one" id="enteringMoney" name="borrowerMoney"
                               readonly="readonly" value=""/>
                    </div>
                    <div class="form_content">
                        <label class="control-label sign_left_small">与总金额的差额</label>
                        <input type="text" class="select_control sign_right_one" id="differenceMoney"
                               name="borrowerMoney" readonly="readonly" value=""/>
                    </div>
                </div>
            </div>
            <div class="text-center mt40 mb30">
                <button type="button" class="btn btn-success mr5" id="submitBtn">保存</button>
                <button type="button" class="btn btn-grey" onclick="javascript:history.back();">返回</button>
            </div>
        </form>
    </div>
    <%--  选择案件modal --%>
    <div class="modal inmodal in" id="caseModal" role="dialog" aria-hidden="true">
        <div class="modal-dialog" style="width: 1070px;">
            <div class="modal-content animated fadeIn apply_box info_box">
                <form action="" class="form_list clearfix">
                    <div class="modal_title">选择案件</div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label mr10"> 案件编码 </label> <input
                                class="teamcode input_type" value="" placeholder="请选择案件"
                                id="caseCodet" name="caseCodet">
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left"> 产证地址 </label> <input
                                class="input_type" value="" placeholder="请输入"
                                style="width: 435px;" id="propertyAddr" name="propertyAddr">
                        </div>
                        <div class="form_content space">
                            <div class="add_btn">
                                <button type="button" class="btn btn-success"
                                        id="caseSearchButton">
                                    <i class="icon iconfont"></i> 查询
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
                <button type="button" class="close close_blue"
                        data-dismiss="modal">
                    <i class="iconfont icon_rong"> &#xe60a; </i>
                </button>
                <div class="eloanApply-table"></div>

            </div>
        </div>
    </div>
</div>
<!-- End page wrapper-->
<!-- Mainly scripts -->
<content tag="local_script">
    <script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
    <script src="<c:url value='/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
    <script src="<c:url value='/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
    <!-- 日期控件 -->
    <script src="<c:url value='/js/plugins/dateSelect/dateSelect.js' />"></script>
    <!-- Custom and plugin javascript -->
    <script src="<c:url value='/js/inspinia.js' />"></script>
    <script src="<c:url value='/js/plugins/pace/pace.min.js' />"></script>
    <!-- 弹出框插件 -->
    <script src="<c:url value='/js/plugins/layer/layer.js' />"></script>
    <script src="<c:url value='/js/plugins/layer/extend/layer.ext.js' />"></script>
    <!-- 分页控件  -->
    <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
    <script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
    <script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
    <script src="<c:url value='/js/plugins/autocomplete/jquery.autocomplete.js' />"></script>
    <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
    <script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
    <script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
    <script src="<c:url value='/js/common/textarea.js' />"></script>
    <!-- 评估结算  -->
    <script src="<c:url value='/js/trunk/bankRebate/bankRebate.js' />"></script>


    <script>
        $(document).ready(function () {
            $('#info').change(cal);
            $('#rebateMoney').blur(cal);
            pageInit(1,"<aist:dict id="bank_{index}" name="toBankRebateInfoList[{index}].bankName" display="select" dictType="bank_rebate_bank" clazz="form-control" />");
        });

        //保存数据
        function save(b) {
            //	var jsonData =	indata;
            var url = "${ctx}/bankRebate/saveNewBankRebate";

            $.ajax({
                cache: true,
                async: false,
                type: "POST",
                url: url,
                dataType: "json",
                data: $("#changeRebateForm").serializeArray(),
                beforeSend: function () {
                    $.blockUI({
                        message: $("#salesLoading"),
                        css: {
                            'border': 'none',
                            'z-index': '9999'
                        }
                    });
                    $(".blockOverlay").css({
                        'z-index': '9998'
                    });
                },
                success: function (data) {
                    $.unblockUI();
                    if (data.success) {
                        window.wxc.alert("保存成功",{"wxcOk":function(){
                            var ctx = $("#ctx").val();
                            window.location.href = ctx + "/bankRebate/bankRebateList";
                        }});

                    } else {
                        if (data.message) {
                            window.wxc.alert("保存失败:" + data.message);
                        }
                    }
                },
                error: function () {
                    window.wxc.alert("提交信息出错");
                }
            });
        }
    </script>

    <script id="queryCastListItemList" type="text/html">
        {{each rows as item index}}
        <tr>
            <td>
                <p class="big">
                    <a href="${ctx}/case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank">
                        <span id="modal_caseCode{{index}}">{{item.CASE_CODE}}</span>
                    </a>
                </p>
                <p>
                    <i class="tag_sign">c</i>
                    {{item.CCAI_CODE}}
                </p>
            </td>
            <td>
                <p class="big">
                    <span id="modal_status{{index}}">{{item.STATUS}}</span>
                </p>
            </td>
            <td>
                <p class="big">
                    <span id="modal_propertyAddr{{index}}">{{item.PROPERTY_ADDR}}</span>
                </p>
            </td>
            <td class="center">
                <p class="big">
                    <span id="modal_seller{{index}}">{{item.SELLER}}</span>
                </p>
            </td>
            <td class="center">
                <p class="big">
                    <span id="modal_buyer{{index}}"> {{item.BUYER}}</span>

                </p>
            </td>
            <td class="text-left">
                <button type="button" class="btn btn-success linkCase" name="linkCase" caseCode="{{item.CASE_CODE}}" ccaiCode="{{item.CCAI_CODE}}" caseId="{{item.PKID}}">
                    选择案件
                </button>
            </td>
        </tr>
        {{/each}}
    </script>

</content>
</body>
</html>