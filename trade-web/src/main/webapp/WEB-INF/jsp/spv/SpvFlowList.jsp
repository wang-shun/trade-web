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
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/spv.css" />
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
	<input type="hidden" id="CW"
		value="">
	 <div class="wrapper wrapper-content animated fadeInRight">
                <div class="ibox-content border-bottom clearfix space_box">
                    <h2 class="title">
                        监管资金出入账流水查询
                    </h2>
			<form method="get" class="form_list mt5  form-inline clear" id="serachForm">
                        <div class="row">

                            <div class="form_content form_nomargin">
                                <label class="control-label sign_left">
                                    合约流水申请编号
                                </label>
                                <input class="input_type ml10" name="cashFlowApplyCode" placeholder="" value="">
                            </div>
                            <div class="form_content form_nomargin">
                                <label class="control-label sign_left_small">
                                    出入账类型
                                </label>
                                <select class="select_control" name="usage" id="usage">
                                    <option value="">请选择</option>
							        <option value="in">入账</option>
									<option value="out">出帐</option>
                                </select>
                            </div>
                            <div class="form_content">
                                <label class="control-label sign_left_small">
                                    申请人
                                </label>
                               <input type="hidden" id="userName" name="applier" >
						        <input type="text" id="realName"  style="background-color:#FFFFFF;width:150px;" readonly="readonly" class="form-control" id="txt_proOrgId_gb" onclick="userSelect({startOrgId:'81E586DCB7354D438A4C38C7EAFBF53E',expandNodeId:'81E586DCB7354D438A4C38C7EAFBF53E',
												nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack})" value=''>
                                 <div class="input-group float_icon organize_icon">
                                        <i class="icon iconfont">&#xe627;</i>
                                 </div>
                            </div>

                        </div>
                        <div class="row">
                            <div class="form_content form_nomargin">
                                <label class="control-label sign_left">
                                    物业地址
                                </label>
                                <input class="input_type ml10" name="prAddress" style="width: 392px;" placeholder="" value="">
                            </div>
                            <div class="form_content" style="margin-left:60px;">
						<button type="button" id="btn_searchFrom" class="btn btn-success mr15">
							<i class="icon iconfont">&#xe635;</i> 查询
						</button>
						<input type="reset" class="btn btn-default mr15 btn-padding" id="CleanButton"
							value="清空">
					</div>
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
	<!-- 自定义扩展jQuery库 --> 
	<script src="${ctx}/js/plugins/jquery.custom.js"></script> 
<!-- 	<script src="${ctx}/static/trans/js/property/aist.jquery.custom.ps.js"></script> -->
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
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
       <script src="${ctx}/static/trans/js/spv/spvFlowList.js"></script>
	<script id="querSpvCaseFlowList" type="text/html">
         {{each rows as item index}}
                             <tr>
                                        <td>
                                            <p class="big">
 
                                                   {{item.CASHFLOW_APPLY_CODE}}
                                            </p>

                                               <span >{{item.DIRECTION}} {{item.VOUCHER_NO}}<span >
                                        </td>
                                        <td>
                                             <p class="big">
                                               {{if item.USAGE=="in"}}
                                                <span class="sign_normal navy_bg">入账</span>
                                               {{/if}}
												{{if item.USAGE=="out"}}
                                                <span class="sign_normal pink_bg">出账</span>
                                               {{/if}}
                                            </p>
                                           
                                            <p class="big">
                                               {{item.AMOUNT>0?item.AMOUNT/10000:0}}万元
                                            </p>
                                        </td>
                                        <td>
                                            <span class="">付：</span> <a class="hint hint-top " data-hint="账户/银行： {{item.PAYER_ACC}}/{{item.PAYER_BANK}}">{{item.PAYER}}</a></br>
                                            <span class="">收：</span><a class="hint hint-top " data-hint="账户/银行：{{item.RECEIVER_ACC}}/{{item.RECEIVER_BANK}}">{{item.RECEIVER}}</a>
                                        </td>
                                        <td>
                                            <p class="smll_sign">
                                                <i class="sign_normal">录入</i>
                                                {{item.applyerName}}&nbsp;{{item.CREATE_TIME}}
                                            </p>
                                            {{if item.CLOSE_TIME!=nudefined}}
                                            <p class="smll_sign">
                                                <i class="sign_normal">结束</i>
                                                {{item.CLOSE_TIME}}
                                            </p>
											{{/if}}
                                        </td>
                                        <td>
                                            <p>
                                                {{item.PR_ADDR}}
                                            </p>
                                            <p class="smll_sign">
                                                                                                                                        审核人：
                                                {{item.applyAuditorName}}
                                                {{if item.USAGE=="in" && item.STATUS=="02" &&item.ftPostAuditorName=="" }}&gt;{{wrapperData.cw}}{{/if}}
											    {{if item.USAGE=="out" && item.STATUS=="12" &&item.ftPreAuditorName=="" }}&gt;{{wrapperData.cw}}{{/if}}
                                                {{if item.ftPreAuditorName!=""}}&gt;{{/if}}
												{{item.ftPreAuditorName}}
                                                {{if item.USAGE=="out" && item.STATUS=="13" &&item.ftPostAuditorName=="" }}&gt;<span name="postAuditor">{{item.ftPreAuditorName}}</span>{{/if}}
											    {{if item.ftPostAuditorName!=""}}&gt;{{/if}}
											    {{item.ftPostAuditorName}}
                                            </p>
                                        </td>
                                    </tr>
			{{/each}}          
	    </script> <script>
						
						//初始化
						jQuery(document).ready(function() {
							initCW()
							//查询
							initFlowListData();		
							getPostAuditor();
						});
						function getPostAuditor(){
							var postAuditor=$("span[name='postAuditor']");
							var cw=$("#CW").val().split("/");
							console.info(cw);
							$.each(postAuditor,function(i,item){
								console.info($(item));
								var itemName=item.innerText;
								console.info(itemName);
								debugger;
								 var len=cw.length;
								 var str=""
								for (var i = 0; i < cw.length; i++) {
									debugger;
										if(itemName==cw[i]){
											continue;
										}
										if(i==(len-1)){
											 str+=cw[i];
											 continue;
										 }
										str+=cw[i]+"/"; 
								}
								 item.innerText=str;
							});
							
						}
						function initCW(){
							$.ajax({
								async: false,
								 url:ctx+"/rapidQuery/findPage",
								 method:"post",
								 dataType:"json",
								 data:{
									    page : 1,
										rows : 12,
										queryId:'getAllYCCW'
								 },
								 success:function(data){
									 var str="";
									 if(data.records>0){
										 var len=data.rows.length;
										 $.each(data.rows,function(i,item){
											 if(i==(len-1)){
												 str+=item.name;
												 return;
											 }
											str+=item.name+"/"; 
											
										 })
									 } 
									 $("#CW").val(str)
								 }
							});
						};
						$("#btn_searchFrom").click(function() {
					          params.search_cashFlowApplyCode=$("input[name='cashFlowApplyCode']").val();
					          params.search_usage=$("select[name='usage']").val();
					          params.search_applier=$("input[name='applier']").val();
					          params.search_prAddress=$("input[name='prAddress']").val();
					          initFlowListData();
									
				         })
					</script> </content>
</body>
</html>