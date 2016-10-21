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

	 <div class="wrapper wrapper-content animated fadeInRight">
                <div class="ibox-content border-bottom clearfix space_box">
                    <h2 class="title">
                        监管资金出入账申请
                    </h2>
			<form method="get" class="form_list mt5  form-inline clear" id="serachForm">
                        <div class="row">

                            <div class="form_content form_nomargin">
                                <label class="control-label sign_left">
                                    监管合约编号
                                </label>
                                <input class="input_type ml10" name="spvCode" placeholder="请输入合约编号" value="">
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
						<button type="button" id="btn_search" class="btn btn-success mr15">
							<i class="icon iconfont">&#xe635;</i> 查询
						</button>
						<button type="button" onclick="clearForm()" class="btn btn-default mr15 btn-padding">清空</button>
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
	<script id="querSpvCaseFlowApplyList" type="text/html">
         {{each rows as item index}}
                             <tr>
                                        <td>
                                            <p class="big">
                                                
                                                   {{item.CASHFLOW_APPLY_CODE}}
                                              
                                            </p>
                                        </td>
                                        <td>
                                            <p class="big">
                                                
                                                    {{item.SPV_CODE}}
                                                
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
                                                {{item.applyerName}}&nbsp;{{item.CREATE_TIME}}
                                            </p>
                                        </td>
                                        <td>
                                            <p>
                                                <i class="sign_blue">
                                                    {{item.status}}
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
                                                <th>结算时间</th>
                                            </tr>
                                            </thead>
                                            <tbody id="tbody{{item.PKID}}">
                                            </tbody>
                                        </table>
                                        </td>
                                    </tr>
			{{/each}}          
	    </script> <script>
						
						//初始化
						jQuery(document).ready(function() {
							initData();
						});
					</script> </content>
</body>
</html>