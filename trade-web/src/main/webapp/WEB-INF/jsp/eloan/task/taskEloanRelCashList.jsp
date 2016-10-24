<%@page import="com.centaline.trans.workspace.web.SessionUserConstants"%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->

    <!-- stickUp fixed css -->
      <link href="${ctx}/static/trans/css/common/hint.css" rel="stylesheet" />
    <link href="${ctx}/static/css/plugins/stickup/stickup.css" rel="stylesheet">
    <link href="${ctx}/static/trans/css/common/stickDash.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" >
    <link href="${ctx}/static/css/plugins/aist-steps/steps.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/toastr/toastr.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <!-- index_css  --> 
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/btn.css" />
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" ">

 
    <!-- 分页控件 -->
    <link href="${ctx}/static/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
	<%
	response.setHeader("Cache-Control","no-store,no-cache,must-revalidate");
	response.setHeader("Pragrma","no-cache");
	response.setDateHeader("Expires",0);
	request.setAttribute("sessionUser", SessionUserConstants.getSesstionUser());
	%>
	
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="serviceDepHierarchy"
		value="${sessionUser.serviceDepHierarchy }">
	<input type="hidden" id="userId" value="${sessionUser.id }">
	<input type="hidden" id="serviceDepId"
		value="${sessionUser.serviceDepId }">
	<input type="hidden" id="serviceJobCode"
		value="${sessionUser.serviceJobCode }">
		<input type="hidden" id="orgId"
		value="${orgId }">
		
		<input type="hidden" id="orgId"
		value="${sessionUser.serviceCompanyId }">

	 <!--*********************** HTML_main*********************** -->
                <div class="wrapper wrapper-content animated fadeInRight">
                    <div class="ibox-content border-bottom clearfix space_box">
                        <h2 class="title">资金流水列表</h2>
                        <form method="get" class="form_list">
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        产品名称
                                    </label>
                                    <select class="select_control sign_right_one">
                                        <option value="">
                                            请选择
                                        </option>
                                        <option value="">
                                            e+贷款
                                        </option>
                                    </select>
                                </div>
                                <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            状态
                                        </label>
                                        <select class="select_control sign_right_one">
                                            <option value="">
                                                已确认
                                            </option>
                                            <option value="">
                                                未确认
                                            </option>
                                        </select>
                                    </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        E+编号
                                    </label>
                                    <input class="teamcode input_type" placeholder="" value="">
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        合作机构
                                    </label>
                                    <select class="select_control sign_right_one">
                                        <option value="">
                                            请选择
                                        </option>
                                        <option value="">
                                            广大银行
                                        </option>
                                    </select>
                                </div>
                                <div class="line">
                                    <div class="form_content">
                                        <label class="control-label sign_left_small">
                                            贷款专员
                                        </label>
                                        <input class="sign_right_one input_type" placeholder="请输入" value="">
                                        <div class="input-group float_icon organize_icon">
                                            <i class="icon iconfont"></i>
                                        </div>
                                    </div>
                                    <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        专员所在组
                                    </label>
                                    <input class="teamcode input_type" placeholder="请输入" value="">
                                    <div class="input-group float_icon organize_icon">
                                        <i class="icon iconfont"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        产证地址
                                    </label>
                                    <input class="teamcode input_type" style="width:461px;" placeholder="请输入" value="">
                                </div>

                            </div>
                            <div class="line">
                                <div class="form_content mr30">
                                    <label class="control-label sign_left_small select_style mend_select">
                                        放款时间
                                    </label>
                                    <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd">
                                        <input name="" class="form-control data_style" type="text" value="" placeholder="起始日期"> <span class="input-group-addon">到</span>
                                        <input name="" class="form-control data_style" type="text" value="" placeholder="结束日期">
                                    </div>
                                </div>
                                <div>
                                    <button type="button" class="btn btn-success btn-icon" id="mortgageInfoSearchButton">
                                        <i class="icon iconfont"></i> 查询
                                    </button>
                                    <button type="button" id="mortgageInfoToExcel" class="btn btn-success"
                                            onclick="javascript:mortgageInfoToExcel()">导出列表
                                    </button>
                                    <button type="button" class="btn  btn-icon btn-toggle" id="TypeBtn">
                                    <i class="iconfont icon">&#xe63d;</i> 贷款报表
                                    </button>
                                     <button type="button" class="btn  btn-icon btn-toggle "  id="TypeBtn2"><i class="iconfont icon">&#xe63f;</i>
                                        组织报表
                                    </button>
                                    <button type="button" class="btn btn-grey" id="mortgageInfoCleanButton">清空</button>
                                </div>
                                    <!--图表-->
                                <div class="row charone" style="margin-top: 40px;padding-top: 10px;border-top: 1px solid #f4f4f4;">
                                    <div class="col-md-6">
                                        <div id="TypeOne"  style="width:100%;height:400px;"></div>
                                    </div>
                                    <div class="col-md-6">
                                        <div id="TypeTwo"  style="width:100%;height:400px;">1</div>
                                    </div>
                                </div>
                                <div class="row chartwo" style="margin-top: 40px;padding-top: 10px;border-top: 1px solid #f4f4f4;">
                                     <div class="col-md-12">
                                            <div id="Cont" style="width:100%;height:300px;"></div>
                                        </div>
                                </div>
                            </div>
                        </form>
                        </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="bonus-table "></div>
                        </div>
                    </div>
                </div>
                <!--*********************** HTML_main*********************** -->
	<!-- main End -->

	<content tag="local_script"> <!-- Peity --> <script
		src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> <!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> <!-- Custom and plugin javascript -->
	<script src="${ctx}/js/jquery.blockui.min.js"></script> <script
		src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script> <script
		src="${ctx}/js/plugins/dropzone/dropzone.js"></script> <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> <script
		src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> <!-- iCheck -->
	<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> <script
		src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
	<!-- 分页控件  --> <script
		src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script> <script
		src="${ctx}/js/template.js" type="text/javascript"></script> <script
		src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> <!-- 模板 -->
		       <!-- ECharts.js -->
        <script src="${ctx}/static/js/echarts.min.js"></script>
       <script src="${ctx}/static/trans/js/eloan/eloan.js"></script>
        <!-- index_js -->
	<script id="queryEloanCash" type="text/html">
         {{each rows as item index}}
			    <tr class="tr-1">
                                        <td>
                                            <p class="big">
                                                <a href="javascript:;">
                                                    {{item.ELOAN_CODE}}
                                                </a>
                                            </p>
                                        </td>
                                        <td>
                                            <p>
                                                <i class="sign_blue">
                                                 {{item.LOAN_SRV_CODE}}
                                                </i>
                                            </p>
                                            <p>
                                                {{item.PROPERTY_ADDR}}
                                            </p>
                                        </td>
                                        <td>
                                           <p class="big">
                                         {{item.FIN_ORG_CODE}}
                                           </p>
                                        </td>
                                        <td>
                                            <p class="big">
                                          {{item.CUST_NAME}}
                                            </p>
                                        </td>
                                        <td>
                                            <p class="smll_sign big">
                                            {{item.RELEASE_AMOUNT}}
                                            </p>
                                        </td>
                                        <td>
                                            <p class="smll_sign">
                                                <i class="sign_normal">放</i>{{item.RELEASE_TIME}}
                                            </p>
                                        </td>


                                        <td>
                                            <p class="big blue-text">
                                               {{item.EXCUTOR_ID}}
                                            </p>
                                            <p class="big blue-text">
                                               {{item.EXCUTOR_TEAM}}
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">
                                         {{item.CONFIRM_STATUS}}
                                           </p>
                                        </td>
                                    </tr>
			{{/each}}          
	    </script> <script>
	    $(document).ready(function () {
            //图标toggle切换显示隐藏JS
           $(".charone,.chartwo").hide();
           $("#TypeBtn").click(function() {
               $(".charone").toggle();
               $(".chartwo").hide();
               $("#TypeBtn").addClass('btn-bg');
               $("#TypeBtn2 ").removeClass('btn-bg');
               if($(".charone").is(":hidden")) {
                              $("#TypeBtn").removeClass('btn-bg');
                           }
           });

           $("#TypeBtn2").click(function() {

               $(".chartwo").toggle();
               $(".charone").hide();
               $("#TypeBtn2 ").addClass('btn-bg');
               $("#TypeBtn ").removeClass('btn-bg');
               if($(".chartwo").is(":hidden")) {
                              $("#TypeBtn2").removeClass('btn-bg');
                           }
           });

           $('.input-daterange').datepicker({
               keyboardNavigation: false,
               forceParse: false,
               autoclose: true
           });
       });


	    
						//初始化数据
						var  rule=false;
						var ctx=$("#ctx").val();
						if(serviceJobCode=='consultant'){
							rule=true;
						}
						var params = {
							rows : 10,
							page : 1,
							sessionUserId : $("#userId").val(),
							serviceDepId : $("#serviceDepId").val(),
							serviceOrgId : $("#orgId").val(),
							serviceJobCode : $("#serviceJobCode").val(),
							serviceDepHierarchy :$("#serviceDepHierarchy").val(),
						};

						//查询数据
						$("#btn_search")
								.click(
										function() {
											params.search_applyTimeStart=null;		
											params.search_applyTimeEnd=null;
											params.search_signTimeStart=null;
											params.search_signTimeEnd=null;
											params.releaseTimeStart='';
											params.releaseTimeEnd='';
											params.search_propertyAddr=null;
											params.search_custName=null;
											/* params.search_status = $("#sel_applyStatus").val() */
											var sel_time = $("#sel_time").val();
											if (sel_time == "applyTime") {
												params.search_applyTimeStart = $(
														"input[name='dtBegin']")
														.val();
												params.search_applyTimeEnd = $(
														"input[name='dtEnd']")
														.val();
											} 
											 else if (sel_time == "signTime") {
												    params.search_signTimeStart = $(
															"input[name='dtBegin']")
															.val();
													params.search_signTimeEnd = $(
															"input[name='dtEnd']")
															.val();
										    }
											else if (sel_time == "releaseTime") {
												params.releaseTimeStart = $(
														"input[name='dtBegin']")
														.val();
												params.releaseTimeEnd = $(
														"input[name='dtEnd']")
														.val();
												params.releaseTimeEnd+=" 23:59:59";
											}
											var sel_caseInfo = $(
													"#sel_caseInfo").val();
											if (sel_caseInfo == "addr") {
												params.search_propertyAddr = $(
														"#txt_caseInfo").val();
											} else if (sel_caseInfo == "custName") {
												params.search_custName = $(
														"#txt_caseInfo").val();
											}

											initData();
										})

						// 日期控件
						$('#datepicker_0').datepicker({
							format : 'yyyy-mm-dd',
							weekStart : 1,
							autoclose : true,
							todayBtn : 'linked'
						})

						//加载页面
						function initData() {
							$(".bonus-table")
									.aistGrid(
											{
												ctx : ctx,
												url:"/rapidQuery/findPage",
												queryId : 'queryEloanCashList',
												templeteId : 'queryEloanCash',
												gridClass : 'table table_blue table-striped table-bordered table-hover',
												data : params,
												columns : [ {
													colName : "E+编号"
												}, {
													colName : "产证地址"
												}, {
													colName : "合作机构"
												}, {
													colName : "借款人"
												}, {
													colName : "放款金额"
												}, {
													colName : "时间"
												}, {
													colName : "贷款专员"
												}, {
													colName : "状态"
												} 
												]

											});
						}

						//初始化
						jQuery(document).ready(function() {
							initData();
						});
					</script> </content>
</body>
</html>