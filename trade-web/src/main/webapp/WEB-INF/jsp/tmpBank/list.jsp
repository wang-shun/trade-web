<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Toastr style -->
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">

<!-- Gritter -->
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">

<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet">
<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link href="${ctx}/css/transcss/case/case_filter.css" rel="stylesheet">

<!-- Data Tables -->
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">

<!-- index_css -->
 <link rel="stylesheet" href="${ctx}/css/tmpBank/base.css" />
 <link rel="stylesheet" href="${ctx}/css/tmpBank/table.css" />
 <link rel="stylesheet" href="${ctx}/css/tmpBank/input.css" />
 <link rel="stylesheet" href="${ctx}/css/tmpBank/iconfont/iconfont.css">
 
 <!-- 必须CSS -->
<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />
 
</style>

</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div id="wrapper">
                <div class="wrapper wrapper-content animated fadeInRight">
                    <div class="ibox-content border-bottom clearfix space_box">
                        <h2 class="title">
                            临时银行案件列表
                        </h2>
                        <form method="get" class="form_list">
                        	<input type="hidden" id="depHierarchy" value="${org.depHierarchy }"/>
                        	<input type="hidden" id="currentOrgId" value="${currentUser.adminOrg }"/>
                        	<input type="hidden" id="userLoginName" value="${currentUser.username }"/>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        案件编号
                                    </label>
                                    <input class="teamcode input_type" placeholder="" value="" id="caseCode"/>
                                </div>

                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        产证地址
                                    </label>
                                    <input class="teamcode input_type" style="width:435px;" placeholder="" value="" id="propertyAddress"/>
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        申请人
                                    </label>
                                    <c:choose>
                                    	<c:when test="${org.depHierarchy == 'yucui_team' }">
                                    		<input class="teamcode input_type" placeholder="" hVal="${currentUser.id }" value="${currentUser.realName }" id="realName"
		                                    readonly="readonly"/>
                                    	</c:when>
                                    	<c:otherwise>
                                    		<input class="teamcode input_type" placeholder="" hVal="${serUserId }" value="${userInfo }" id="realName"
			                                    readonly="readonly"
												onclick="userSelect({startOrgId:'${serviceDepId}',expandNodeId:'${serviceDepId}',
												nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack})"/>
                                    	</c:otherwise>
                                    </c:choose>
                                    <div class="input-group float_icon organize_icon">
                                        <i class="icon iconfont"></i>
                                    </div>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        申请人所在组
                                    </label>
                                    
                                    <c:choose>
                                    	<c:when test="${org.depHierarchy == 'yucui_team' }">
                                    		<input class="teamcode input_type" placeholder="" id="teamCode" name="teamCode" readonly="readonly" value="${org.orgName}"/>
										   <input class="m-wrap" type="hidden" id="yuCuiOriGrpId" name="yuCuiOriGrpId" value="${org.id }">
                                    	</c:when>
                                    	<c:otherwise>
                                    		<input class="teamcode input_type" placeholder="" value="" id="teamCode" name="teamCode" readonly="readonly"
                                   			onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName', startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'',
										   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack})"
										   value="${serviceDepName}"/>
										   
										   <input class="m-wrap" type="hidden" id="yuCuiOriGrpId" name="yuCuiOriGrpId">
                                    	</c:otherwise>
                                    </c:choose>
                                    <div class="input-group float_icon organize_icon">
                                        <i class="icon iconfont"></i>
                                    </div>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        状态筛选
                                    </label>
                                    <select name="" class="select_control selwidth " id="selStatus">
                                        <option value="">请选择</option>
                                        <option value="auditing">审批中</option>
                                        <option value="success">已通过</option>
                                        <option value="reject">已驳回</option>
                                    </select>
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        贷款银行
                                    </label>
                                    <select name="" class="teamcode select_control" id="selMainBank">
                                        <option value="">请选择</option>
                                        <c:forEach var="tsFinOrg" items="${tsFinOrgList }">
                                        	<option value="${tsFinOrg.finOrgNameYc }" id="${tsFinOrg.finOrgCode }">${tsFinOrg.finOrgNameYc }</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small select_style mend_select">
                                        申请时间
                                    </label>
                                    <div id="datepicker_1" class="input-group input-medium date-picker input-daterange pull-left"  data-date-format="yyyy-mm-dd">
										<input id="beginApplyDatetime" name="dtBegin" class="form-control" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="起始日期"> 
											<span class="input-group-addon">到</span> 
										<input id="endApplyDatetime" name="dtEnd" class="form-control" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="结束日期">
									</div>
                                </div>

                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        贷款支行
                                    </label>
                                     <select name="" id="selBranchBank" class="teamcode select_control ">
                                        <option value="">请选择</option>
                                    </select>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small select_style mend_select">
                                        审批时间
                                    </label>
                                    <div id="datepicker_0" class="input-group input-medium date-picker input-daterange pull-left"  data-date-format="yyyy-mm-dd">
										<input id="beginAuditDatetime" name="dtBegin" class="form-control" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="起始日期"> 
											<span class="input-group-addon">到</span> 
										<input id="endAuditDateime" name="dtEnd" class="form-control" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="" placeholder="结束日期">
									</div>
                                </div>
                            </div>
                            <div class="line">
                                <div class="add_btn" style="margin-left:126px;">
                                    <button type="button" class="btn btn-success" id="submitButton">
                                        <i class="icon iconfont">&#xe635;</i>
                                        查询
                                    </button>
                                    <button type="button" class="btn btn-success" id="exportButton" onClick="javascript:exportExcel();">
                                        导出列表
                                    </button>
                                    <button type="button" class="btn btn-grey" id="clearButton">
                                        清空
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="table_content">
                                <table class="table table_blue table-striped table-bordered table-hover " id="editable" >
                                    <thead>
                                        <tr>
                                            <th style="background-color:#52cdec;">
                                                <span>案件编码</span><a href="#"></a>
                                            </th>
                                            <th style="background-color:#52cdec;">
                                                当前环节
                                            </th>
                                            <th style="background-color:#52cdec;">
                                                产证地址
                                            </th>
                                            <th style="background-color:#52cdec;">
                                                时间
                                            </th>
                                            <th style="background-color:#52cdec;">
                                                主办人
                                            </th>
                                            <th style="background-color:#52cdec;">
                                                申请人所在组
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody id="tmpBankCastList">
                                       
                                    </tbody>
                                </table>
                                
                                <div class="text-center">
									<span id="currentTotalPage"><strong class="bold"></strong></span> <span
										class="ml15">共<strong class="bold" id="totalP"></strong>
									</span>&nbsp;
									<div id="pageBar" class="pagination my-pagination text-center m0"></div>
								</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <content tag="local_script"> 
			<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> 
			<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
			<script src="${ctx}/js/jquery.blockui.min.js"></script> 
			<script src="${ctx}/js/plugins/ionRangeSlider/ion.rangeSlider.min.js"></script>
			<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
			<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
			<script src="${ctx}/js/plugins/jquery.custom.js"></script> 
			<script src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
			<script src="${ctx}/js/trunk/tmpBank/list.js?v=1.1"></script> 
			
			<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include> 
			
			<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> 
			
			<!-- 分页控件  -->
			<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
			<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
			<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
			<script src="${ctx}/js/plugins/jquery.custom.js"></script>
			<script src="${ctx}/js/workflow/myCaseList.js"></script>
			
			<!-- 必须JS -->
			<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>
			

<script id="template_tmpBankCaseList" type="text/html">

      {{each rows as item index}}
  				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						<td>
                            <p class="big">
                                <a href="../case/caseDetail?caseId={{item.PKID}}"  target="_blank" style="text-decoration:underline;">{{item.caseCode}}</a>
                            </p>
                        </td>

						<td>
                            <p>
								{{if item.currentProcess1}}
                                	<i class="sign_blue">
										{{item.currentProcess1}}
									</i>
								{{/if}}
                            </p>
                            <p>
                                {{item.tmpBankStatus1}}   {{if item.tmpBankStatus == '0'}}<a href="#" class="ml5 demo-top" title="驳回原因:{{item.rejectReason}}">驳回原因</a>{{/if}}
                            </p>
                        </td>

						 <td>
							{{if item.propertyAddress != null && item.propertyAddress!="" && item.propertyAddress.length>24}}
								<p class="big" title="{{item.propertyAddress}}">{{item.propertyAddress.substring(item.propertyAddress.length-24,item.propertyAddress.length)}}</p>
							{{else}}
								<p class="big">{{item.propertyAddress}}</p>
							{{/if}}
                             <p>
                                <a class="salesman-info" href="javascript:;" >
                                  {{item.mainBankName}}/{{item.branchBankName}}
                                </a>
                             </p>
                         </td>

						<td>
                             <p class="smll_sign">
                                   <i class="sign_normal">申</i>
                                   {{if item.applyDateTime}} 
										{{item.applyDateTime}}
								   {{else}}
										无
									{{/if}}
                             </p>
                             <p class="smll_sign">
									{{if item.auditDateTime}} 
										<i class="sign_normal">审</i>{{item.auditDateTime}}
								    {{else}}
										<i class="sign_grey">审</i>
									{{/if}}
                             </p>
                        </td>

						<td class="center">
                             <p class="manager">申请人：<a href="#">{{item.realName}}</a></p>
                             <p class="manager">审批人：<a href="#">
								{{if item.approver}} 
										{{item.approver}}
							    {{/if}}</a></p>
                        </td>

						<td class="center">
                            {{item.orgName}}                   
                        </td>
				  </tr>
       {{/each}}
</script> 
</content>
</body>
</html>
