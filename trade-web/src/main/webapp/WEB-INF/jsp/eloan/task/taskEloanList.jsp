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

    <link href="${ctx}/static/trans/css/eloan/eloan/eloan.css" rel="stylesheet"/>
    <link href="${ctx}/static/trans/css/common/input.css" rel="stylesheet"/>
    <link href="${ctx}/static/trans/css/common/table.css" rel="stylesheet"/>
 
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

	<div class="wrapper wrapper-content animated fadeInUp">
		<div class="row">
			<div class="ibox-content border-bottom clearfix space_box">
				<h2 class="title">E+贷款</h2>
				<form method="get" class="form_list">
					<%-- 	<div class="form_content">
						<label class="control-label sign_left"> 状态搜索项 </label>
						<aist:dict id="sel_applyStatus" name="applyStatus"
							clazz="select_control sign_right_one" display="select"
							hasEmpty="true" dictType="yu_eplus_status"
							defaultvalue="${loanAgent.applyStatus }" ligerui='none'></aist:dict>
					</div> --%>
					<div class="form_content form_nomargin">
						<label class="control-label sign_left"> 案件信息 </label> <select
							id="sel_caseInfo" style="width: 130px;"
							class="select_control sign_right_one">
							<option value="addr">产证地址</option>
							<option value="custName">客户姓名</option>
						</select> <input class="sign_right input_type ml10" id="txt_caseInfo"
							placeholder="请输入" value="">
					</div>
					<div class="form_content ">
						<label class="control-label sign_left"> 时间搜索项 </label> <select
							id="sel_time" class="select_control" style="width: 130px;">
							<option value="applyTime">申请时间</option>
							<option value="signTime">面签时间</option>
							<option value="releaseTime">放款时间</option>
						</select>
						<div id="datepicker_0"
							class="input-group input-medium date-picker input-daterange sign_right_speciale"
							data-date-format="yyyy-mm-dd">
							<input id="dtBegin_0" name="dtBegin"
								class="form-control data_style" type="text" value=""
								placeholder="起始日期"> <span class="input-group-addon">到</span>
							<input id="dtEnd_0" name="dtEnd" class="form-control data_style"
								type="text" value="" placeholder="结束日期">
						</div>

					</div>

					<div class="form_content btn-sign">
						<div class="add_btn">
							<button type="button" class="btn btn_blue" id="btn_search">
								<i class="icon iconfont"></i> 查询
							</button>
							<a type="button" class="btn btn_blue "
								href="${ctx}/eloan/task/eloanApply/process"> <i
								class="icon_add iconfont">&#xe617;</i>&nbsp;新增
							</a>
						</div>
					</div>
				</form>
			</div>
			<!-- <div class="ibox"> -->
		</div>
		<div class="row white_bg">
			<div class="bonus-table "></div>
		</div>
	</div>

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
	<script id="queryMortgageApproveLost" type="text/html">
         {{each rows as item index}}
			<tr>
				<td class="text-center">
				    {{item.loanSrvCode}}
				</td>
				<td>
				      {{item.propertyAddress}}
				</td>
				<td>
                           <a class="hint hint-top" data-hint="{{item.phone}}">
                                <p class="bb"> {{item.excutorName}}</p>
                           </a>
				</td>
				<td class="center">
					<p class="big">
					     {{item.custName}}
					</p>
				</td> 
				<td>
					<p>                             
					
					 {{if item.applyTime==undefined}}
						<i class="sign_grey">申</i>
                      {{else}}
						<i class="sign_normal">申</i>
                      {{/if}}
					       {{item.applyTime}}
					</p>
					<p>
					  {{if item.signTime==undefined}}
						<i class="sign_grey">面</i>
                      {{else}}
						<i class="sign_normal">面</i>
                      {{/if}}
					       {{item.signTime}}
					</p>
					<p>
					 {{if item.releaseTime==undefined}}
						<i class="sign_grey">放</i>
                      {{else}}
						<i class="sign_normal">放</i>
                      {{/if}}
					       {{item.releaseTime}}
					</p>
				</td>
				<td class="text-center">
                    {{if  item.applyTime!=undefined &&item.signTime==undefined&&item.releaseTime==undefined}}
					   申请
                      {{/if}}
 					{{if item.signTime!=undefined&&item.releaseTime==undefined}}
					    放款
                      {{/if}}
				     {{if item.releaseTime!=undefined}}
					    放款
                      {{/if}}
				</td>
				<td class="center">       
                             {{item.Applymoney}}万
				      
				</td>
				<td class="text-center">
					<a href="${ctx}/eloan/getEloanCaseDetails?pkid={{item.pkId}}">
				    <button type="button" id="link_btn"  class="btn btn-success btn-blue">详情</button>
				   </a>		
	                <shiro:hasPermission name="TRADE.ELONE.DELETE">
				    <button type="button" id="link_btn" onclick="deleteItem({{item.pkId}})" class="btn btn-success btn-blue">删除</button>
                    </shiro:hasPermission>                 
               </td>
			</tr>
			{{/each}}          
	    </script> <script>
	   
	    
						//初始化数据
						 var  rule=false;
						var serviceJobCode=$("#serviceJobCode").val();
						var serviceDepHierarchy=$("#serviceDepHierarchy").val();
						if(serviceJobCode=='consultant'){
							rule=true;
						}
						var params = {
							rows : 10,
							page : 1,
							sessionUserId : $("#userId").val(),
							serviceDepId : $("#serviceDepId").val(),
							serviceJobCode : serviceJobCode,
							serviceDepHierarchy :serviceDepHierarchy
						};
						//删除
						function deleteItem(pkid){
							/* if(serviceJobCode != 'consultant') */
							
							var confim= confirm("确定要删除这条数据吗？")
							if(!confim){
							return
							}
							$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}});
							$.ajax({
								cache:false,
								async:true,
								type:"POST",
								url:ctx+"/eloan/deteleItem",
								dataType:'json',
								data:{pkid:pkid},
								success:function(data){
										alert(data.message);
										initData();//刷新列表
									    $.unblockUI();
								}
							});
							
						}
						//查询数据
						$("#btn_search")
								.click(
										function() {
											params.search_applyTimeStart=null;		
											params.search_applyTimeEnd=null;
											params.search_signTimeStart=null;
											params.search_signTimeEnd=null;
											params.releaseTimeStart=null;
											params.releaseTimeEnd=null;
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
						$("#sel_time").change(function() {
							$("input[name='dtBegin']").val("");
							$("input[name='dtEnd']").val("");
						})

						//加载页面
						function initData() {
							$(".bonus-table")
									.aistGrid(
											{
												ctx : "${ctx}",
												queryId : 'EloanCaseListQuery',
												templeteId : 'queryMortgageApproveLost',
												gridClass : 'table table_blue table-striped table-bordered table-hover',
												data : params,
												columns : [ {
													colName : "产品类型	"
												//sortColumn : "CASE_CODE",
												//sord: "desc",
												//sortActive : true
												}, {
													colName : "产权地址"
												}, {
													colName : "案件归属"
												}, {
													colName : "客户"
												}, {
													colName : "流程时间"
												}, {
													colName : "状态"
												}, {
													colName : "申请金额"
												}, {
													colName : "操作"
												} ]

											});
						}

						//初始化
						jQuery(document).ready(function() {
							initData();
						});
					</script> </content>
</body>
</html>