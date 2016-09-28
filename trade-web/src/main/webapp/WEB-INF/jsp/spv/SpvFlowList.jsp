<%@page import="com.centaline.trans.workspace.web.SessionUserConstants"%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->

<!-- stickUp fixed css -->
<link href="${ctx}/static/trans/css/common/hint.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">

<!-- Data Tables -->
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css">
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/datapicker/datepicker3.css">
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet"
	href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/input.css" />
<!-- index_css -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
 <link rel="stylesheet" href="${ctx}/static/trans/css/common/inputSec.css" />
  <link rel="stylesheet" href="${ctx}/static/trans/css/spv/spv2.css" />
<!-- 分页控件 -->
<link href="${ctx}/static/css/plugins/pager/centaline.pager.css"
	rel="stylesheet" />
<%
	response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
	response.setHeader("Pragrma", "no-cache");
	response.setDateHeader("Expires", 0);
	request.setAttribute("sessionUser", SessionUserConstants.getSesstionUser());
%>
</head>

<body>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="serviceDepHierarchy"
		value="${sessionUser.serviceDepHierarchy }">
	<input type="hidden" id="userId" value="${sessionUser.id }">
	<input type="hidden" id="serviceDepId"
		value="${sessionUser.serviceDepId }">
	<input type="hidden" id="serviceJobCode"
		value="${sessionUser.serviceJobCode }">

	 <div class="wrapper wrapper-content animated fadeInRight">
                <div class="ibox-content border-bottom clearfix space_box">
                    <h2 class="title">
                        监管资金出入账流水查询
                    </h2>
                    <form method="get" class="form-horizontal form_box">
                            <div class="row clearfix">
                                <div class="form_content">
                                    <div id="dateDiv_0">
                                        <div class="sign_left">
                                            <select name="case_date" class="form-control" id="case_date_0">
                                                <option value="" selected="selected">
                                                    请选择
                                                </option>
                                                <option value="">
                                                    签约日期
                                                </option>
                                                <option value="">
                                                    过户日期
                                                </option>
                                                <option value="">
                                                    领证日期
                                                </option>

                                            </select>
                                        </div>
                                        <div id="datepicker_0" class="input-group input-medium date-picker input-daterange pull-left" data-date-format="yyyy-mm-dd">
                                            <input id="dtBegin_0" name="dtBegin" class="form-control" style="font-size: 13px; width: 159px; border-radius: 0px;" type="text" value="" placeholder="起始日期">
                                            <span class="input-group-addon">
                                                到
                                            </span>
                                            <input id="dtEnd_0" name="dtEnd" class="form-control" style="font-size: 13px; width: 159px; border-radius: 0px;" type="text" value="" placeholder="结束日期">
                                        </div>
                                    </div>
                                </div>
                                <div class="form_content">
                                    <label class="sign_left control-label">
                                        监管机构
                                    </label>
                                    <div class="sign_right big_pad">
                                        <select id="" name="" class="form-control patch_formatone">
                                            <option value="" selected="selected">
                                                请选择
                                            </option>
                                            <option value="">
                                                光大银行
                                            </option>
                                            <option value="">
                                                光大银行
                                            </option>
                                            <option value="">
                                                光大银行
                                            </option>
                                        </select>
                                    </div>
                                </div>

                            </div>
                            <div class="row clearfix">
                                <div class="form_content">
                                    <a class="add_btn" href="javascript:void(0);" id="addBtn">
                                        <span class="retrieve">
                                            添加日期检索
                                        </span>
                                    </a>
                                </div>
                            </div>

                            <div class="row m-t-sm">
                                <div class="form_content">
                                    <div class="more_btn">
                                        <button id="searchButton" type="button" class="btn btn-success mr15"><i class="icon iconfont"></i>
                                            查询
                                        </button>
                                        <button type="reset" class="btn btn-grey mr15">
                                            清空
                                        </button>
                                        <button type="button" class="btn btn-success mr15">
                                            导出列表
                                        </button>

                                    </div>
                                </div>
                            </div>
                            <div id="exportExcel">
                            </div>
                        </form>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="table_content">
                            
						  <div class="bonus-table "></div>
                        </div>
                    </div>
                </div>
            </div>
            <!--*********************** HTML_main*********************** -->
        </div>
    </div>

	<content tag="local_script"> <!-- Peity -->
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
		<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script> 
	<script src="${ctx}/static/tbsp/js/userorg/userOrgSelect.js" type="text/javascript"></script>	

	 <script
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
       <script src="${ctx}/static/trans/js/spv/FlowDetail.js"></script>
	<script id="querSpvCaseFlowList" type="text/html">
         {{each rows as item index}}
                             <tr>
                                        <td>
                                            <p class="big">
                                                <a href="javascript:;">
                                                   {{item.CASHFLOW_APPLY_CODE}}
                                                </a>
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">
                                                <a href="javascript:;">
                                                    {{item.SPV_CODE}}
                                                </a>
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">
                                               {{item.amount>0?item.amount/10000:0}}万元
                                            </p>
                                        </td>
                                        <td>
                                            <p>
                                               {{if item.USAGE=='in'}}
                                                <span class="sign_normal navy_bg">入账</span>
                                               {{/if}}
												{{if item.USAGE=='out'}}
                                                <span class="sign_normal pink_bg">出账</span>
                                               {{/if}}
                                            </p>
                                        </td>
                                        <td>
                                            <p>
                                                {{item.PR_ADDR}}
                                            </p>
                                        </td>
                                        <td>
                                            <p class="smll_sign">
                                                <a href="javascript:void(0)">{{item.applyerName}}&nbsp;</a>{{item.CREATE_TIME}}
                                            </p>
                                        </td>
                                        <td>
                                            <p>
                                                <i class="sign_blue">
                                                    {{item.STATUS}}
                                                </i>
                                            </p>
                                        </td>
                                        <td>
                                           <a href="javascript:void(0)" class="spread" id="caozuo{{item.PKID}}" onclick="show({{item.PKID}})">展开</a>
                                        </td>
                                    </tr>
                                    <tr class="spread_line cashFlow" id="cashFlow{{item.PKID}}">
                                        <td colspan="8" class="spread_td">
                                        <table class="table spread_tab table-bordered">
                                            <thead>
                                            <tr>
                                                <th>凭证</th>
                                                <th>金额</th>
                                                <th>付款方式</th>
                                                <th>付款人账户信息</th>
                                                <th>收款人账户信息</th>
                                            </tr>
                                            </thead>
                                            <tbody id="tbody{{item.PKID}}">
                                            </tbody>
                                        </table>
                                        </td>
                                    </tr>
			{{/each}}          
	    </script> <script>
						//初始化数据
						var params = {
							page : 1,
							sessionUserId : $("#userId").val(),
							serviceDepId : $("#serviceDepId").val(),
							serviceJobCode : $("#serviceJobCode").val(),
							serviceDepHierarchy : $("#serviceDepHierarchy")
									.val()
						};
						//清空数据
						function clearForm(){  
						 $("#serachForm").find("input").val("")
						 $("#status option:first").prop("selected", 'selected');
						/*  $("#qicao option:first").prop("selected", 'selected'); */
						}

/* 						//删除
						function deleteSpv(pkid){
							var deleteItem= confirm("确定要删除这条数据吗？")
							if(!deleteItem){
							return
							}
							$.ajax({
							 url:ctx+"/spv/deleteSpv",
							 method:"post",
							 dataType:"json",
							 data:{pkid:pkid},
							 success:function(data){
								 if(data.ajaxResponse.success==true){
									 alert(data.ajaxResponse.message);
								 }else{
									 alert("删除出错！");
								 } 
								 initData();
							 }
									
						})
							
						} */
					 
						//查询
						$("#btn_search")
								.click(
										function() {
											params.search_spvCode=$(
											"input[name='spvCode']")
											.val();
											params.search_prAddress=$(
											"input[name='prAddress']")
											.val();
											params.search_status=$(
											"select[name='status']")
											.val();
											params.search_applyUser=$(
											"input[name='applyUser']")
											.val();
											params.search_applyTimeStart=null;		
											params.search_applyTimeEnd=null;
											params.search_signTimeStart=null;
											params.search_signTimeEnd=null;
											params.search_closeTimeStart=null;
											params.search_closeTimeEnd=null;
											var sel_time = $("#sel_time").val();
											var start= $("input[name='dtBegin']").val();
											var end=$("input[name='dtEnd']").val()==""?$("input[name='dtEnd']").val():$("input[name='dtEnd']").val()+" 23:59:59";
											if (sel_time == "applyTime") {
												params.search_applyTimeStart = start;
												params.search_applyTimeEnd = end;
											} 
											 else if (sel_time == "signTime") {
												    params.search_signTimeStart =start;
													params.search_signTimeEnd = end;
										    }
											else if (sel_time == "closeTime") {
												params.search_closeTimeStart = start;
												params.search_closeTimeEnd = end;
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
						$("#sel_time").change(function() {
							$("input[name='dtBegin']").val("");
							$("input[name='dtEnd']").val("");
						})

						function show(pkid){
							var hide=$("#cashFlow"+pkid).css("display");
							$("#caozuo"+pkid).html(hide=="none"?"收起":"展开");
							$("#cashFlow"+pkid).slideToggle("slow");
							if(hide!="none")return;
							$.ajax({
								url:ctx+"/quickGrid/findPage",
								 method:"post",
								 dataType:"json",
								 data:{
									    page : 1,
										rows : 12,
										queryId:'ToSpvCashFlowById',
										id:pkid
								 },
								 success:function(data){
									 var tbodyhtml=$("#tbody"+pkid);
									 tbodyhtml.html("");
									 var htmlText="";
									$.each(data.rows,function(i,item){
										htmlText+="<tr><td>"+item.VOUCHER_NO+"</td>";
										htmlText+="<td> <p class='big'>"+(item.AMOUNT>0?item.AMOUNT/10000:0)+"万元</p></td>";
										htmlText+="<td> <p class='big'>"+item.DIRECTION+"</p></td>";
										htmlText+="<td> <p>"+item.PAYER+"&nbsp;&nbsp;"+item.PAYER_ACC+"/"+item.PAYER_BANK+"</p></td>";
										htmlText+="<td> <p>"+item.RECEIVER+"&nbsp;&nbsp;"+item.RECEIVER_ACC+"/"+item.RECEIVER_BANK+"</p></td></tr>";
									})
									 tbodyhtml.html(htmlText);	
							
							}
						  })
						}
						//加载页面
						function initData() {
							$(".bonus-table")
									.aistGrid({
												ctx : "${ctx}",
												queryId : 'ToSpvCaseFlowListQuery',
												   rows : '12',
												templeteId : 'querSpvCaseFlowList',
												gridClass : 'table table_blue table-striped table-bordered table-hover ',
												data : params,
												wrapperData : {job : $("#serviceJobCode").val()},
												columns : [
														{
																   colName :"<span style='color:#ffffff' onclick='caseCodeSort();' >流水流水编号</span><i id='caseCodeSorti' class='fa fa-sort-desc fa_down'></i>",
	/* 								    		    	           sortColumn : "SPV_CODE",
									    		    	           sord: "desc",
									    		    	           sortActive : true */
														},{
															colName : "监管合约编号"
														},{
															colName : "金额"
														},{
															colName : "类别"
														}, {
															colName : "物业地址"
														}, {
															colName : "创建"
														}, {
															colName : "审批状态"
														} , {
															colName : "操作"
														}  ]

											});
						}
						//排序
						function caseCodeSort(){
							if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down"){
								$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up ');
							}else if($("#caseCodeSorti").attr("class")=="fa fa-sort-desc fa_down icon-chevron-down"){
								$("#caseCodeSorti").attr("class",'fa fa-sort-asc fa_up');
							}else{
								$("#caseCodeSorti").attr("class",'fa fa-sort-desc fa_down');
							}
						}
						
						/**
						 * 选择用户
						 * @param param
						 */
						function userSelect(param){
							var options = {
							        dialogId : "selectUserDialog", //指定别名，自定义关闭时需此参数
							        dialog : { 
										height: 463
									   ,width: 756
									   ,title:'选择用户'
									   ,url: appCtx['aist-uam-web']+'/userOrgSelect/userSelect.html'
									   ,data:param
									   ,buttons: [
							                      { text: '确定', onclick: function (item, dialog) { dialog.frame.save();}},
							                      { text: '取消', onclick: function (item, dialog) { dialog.close(); } }
							                   ]
									}
							    };
							openDialog(options);
						} 
						/**
						 * 更新input的值
						 */
						function selectUserBack(array){
							if(array && array.length >0){
								 $("#realName").val(array[0].username);
								$("#userName").val(array[0].userId);
							}else{
								 $("#realName").val("");
								$("#userName").val("");
							}
						}
						
						//初始化
						jQuery(document).ready(function() {
							initData();
						});
					</script> </content>
</body>
</html>