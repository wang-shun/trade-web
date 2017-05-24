<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>计件奖金发放汇总列表</title>
		<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet" />
		<link href="${ctx}/css/animate.css" rel="stylesheet" />
		<link href="${ctx}/css/style.css" rel="stylesheet" />
		<!-- Data Tables -->
		<link href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css" rel="stylesheet" />
		<link href="${ctx}/css/plugins/dataTables/dataTables.responsive.css" rel="stylesheet" />
		<link href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css" rel="stylesheet" />
		<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
		
		<!-- 分页控件 -->
		<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
		<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet" />
		
		<!-- index_css -->
		<link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
		<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
		<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
		<link rel="stylesheet" href="${ctx}/static/css/btn.css" />
		<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">		
		<!-- 必须CSS -->
		<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />
    </head>
    
    <body class="pace-done">
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content border-bottom clearfix space_box">
			<h2 class="title">计件奖金发放汇总列表</h2>
			<form method="get" class="form_list">
				<div class="line">

					<div class="form_content">
						<label class="control-label sign_left_small">案件编号</label> <input class="teamcode input_type" placeholder="请输入" id="caseCode" name="caseCode" value="" />
					</div>

					<div class="form_content">
						<label class="control-label sign_left_small">产证地址</label> <input class="teamcode input_type" style="width: 435px;" name="propertyAddr" id="propertyAddr" placeholder="请输入" value="" />
					</div>
				</div>
				
				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small"> 贷款银行 </label> <select name="loanLostFinOrgName" id="loanLostFinOrgName" class="teamcode select_control ">
						</select>
					</div>
					<div class="form_content">
						<label class="control-label sign_left_small"> 贷款支行 </label> <select name="loanLostFinOrgNameYc" id="loanLostFinOrgNameYc" class="teamcode select_control ">
						</select>
					</div>
				</div>

				<div class="line">
					<div class="form_content">
						<label class="control-label sign_left_small">信贷员</label> <input class="teamcode input_type" placeholder="请输入" value="" name="loanerName" id="loanerName" onclick="loanerClick()"  readonly/>
						<div class="input-group float_icon organize_icon">
							<i class="icon iconfont" onclick="loanerClick()">&#xe627;</i>
						</div>
						<input type="hidden" id="loanerId" name="loanerId" value="" />
					</div>
					<div class="form_content space">
						<div class="add_btn" style="margin-left: 122px;">
							<button type="button" class="btn btn-success mr5" id="loanerConditionSearch">
								<i class="icon iconfont">&#xe635;</i>查询
							</button>
							<button type="button" class="btn btn-grey mr5" id="loanerConditionClean">清空</button>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="table_content">
					<table class="table table_blue table-striped table-bordered table-hover " id="editable">
						<thead>
							<tr>
								<th><span class="sort" sortColumn="TC.CASE_CODE" sord="desc" onclick="caseCodeSort();">案件编号</span><i id="caseCodeSorti" class="fa fa-sort-desc fa_down"></i></th>
								<th>产证地址</th>
								<th>过户审批时间</th>
								<th>奖金是否发放</th>
								<th>绩效奖金</th>
								<th>发放批次</th>
								<th>备注信息</th>
							</tr>
						</thead>
						<tbody id="awardCaseCollectList"></tbody>
					</table>
				</div>

				<div class="text-center page_box">
					<span id="currentTotalPage"><strong></strong></span> <span class="ml15">共<strong id="totalP"></strong>条
					</span>&nbsp;
					<div id="pageBar" class="pagergoto"></div>
				</div>
			</div>
		</div>
	</div>


	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
	<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
	<input type="hidden" id="userJobCode" value="${userJobCode}" />
	<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
	<input type="hidden" id="serviceDepId" value="${serviceDepId}" />         
        <!-- Mainly scripts -->
        <content tag="local_script"> 
        <%--  <script src="${ctx}/js/bootstrap.min.js"></script> --%>
        <script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
        <script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
         <!-- 日期控件 -->
    	<script	src="${ctx}/js/plugins/dateSelect/dateSelect.js?v=1.0.2"></script>
        <!-- Custom and plugin javascript -->
        <script src="${ctx}/js/inspinia.js"></script>
        <script src="${ctx}/js/plugins/pace/pace.min.js"></script>
       
	    <script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> 
        <!-- 分页控件  -->
        <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
        <script src= "${ctx}/js/template.js" type="text/javascript" ></script>
        <script src= "${ctx}/transjs/award/awardCaseCollect.js" type="text/javascript" ></script>
       	<!-- 必须JS --> 
		<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script> 
        <script src="${ctx}/js/plugins/jquery.custom.js"></script>
		<script id="template_awardCaseCollectList" type= "text/html">
      	{{each rows as item index}}
  				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}} 
                     <td><p class="big"><a href="{{ctx}}/case/caseDetail?caseId={{item.CASE_PKID}}" target="_blank">{{item.CASE_CODE}}</a></p></td>
					<td>
						<p class = "demo-top">{{item.PROPERTY_ADDR}}</p>
					</td>
					<td>							
                        <p class="big">{{item.GUOHU_APPROVE_TIME}}</p>
                    </td>
					<td>							
                       <p class="big">{{item.AWARD_STATUS_CN}}</p>
                    </td>
					<td>							
                        <p class="big">{{item.BASE_CASE_AMOUNT}}</p>
                    </td>
					<td>				
                            {{ if item.AWARD_MONTH !="" && item.AWARD_MONTH !=null}}			
                            	<p class="big">{{item.AWARD_MONTH.substring(0,7)}}</p>
							{{/if}}
                    </td>
					<td>							
                            <p class="big">{{item.AWARD_DESC}}</p>
                    </td>
                 </tr>			
       {{/each}}  
	    </script>	
	    </content> 
        <input type="hidden" id="ctx" value="${ctx}" />      
    </body>
</html>