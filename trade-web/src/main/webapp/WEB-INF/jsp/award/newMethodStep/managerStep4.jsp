<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>计件奖金自动化流程</title>      

		<!-- Data Tables -->
		<link href="<c:url value='/css/plugins/dataTables/dataTables.bootstrap.css'/>"  rel="stylesheet" />		
		<link href="<c:url value='/css/plugins/dataTables/dataTables.responsive.css'/>"  rel="stylesheet" />
		<link href="<c:url value='/css/plugins/dataTables/dataTables.tableTools.min.css'/>"  rel="stylesheet" />
		<link href="<c:url value='/css/plugins/datapicker/datepicker3.css'/>"  rel="stylesheet" />		
		<link href="<c:url value='/static/iconfont/iconfont.css' />" rel="stylesheet" />	
		<link href="<c:url value='/static/css/animate.css'/>"  rel="stylesheet"/> 
		<link href="<c:url value='/static/css/style.css' />" rel="stylesheet" />	
		
		<!-- 分页控件 -->
		<link href="<c:url value='/css/plugins/pager/centaline.pager.css'/>"  rel="stylesheet" />
		<link href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css'/>"  rel="stylesheet" />
		
		<!-- index_css -->
 		<link href="<c:url value='/static/trans/css/common/base.css' />" rel="stylesheet" />
		<link href="<c:url value='/static/trans/css/common/table.css' />" rel="stylesheet" />
		<link href="<c:url value='/static/trans/css/common/input.css' />" rel="stylesheet" />
		<link href="<c:url value='/static/trans/css/award/baseAward.css' />" rel="stylesheet" />	 		
		<link href="<c:url value='/static/trans/css/manager/managerIframe.css' />" rel="stylesheet" />	 

		<!-- 必须CSS -->
		<link href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" rel="stylesheet" /> 
    </head>
    
    <body class="pace-done">
			<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="ibox-content border-bottom clearfix space_box">
                    <div class="clearfix"> 
                        <h2 class="title pull-left ml15">计件奖金案件明细</h2>
                    </div>
                    <div class="form_list">
                        <div class="line">                           
                            <div class="form_content">
                                <label class="control-label sign_left_small">案件编号</label>
                                <input class=" input_type" placeholder="请输入" value="" id="caseCode" name="caseCode">
                            </div>
                            <div class="form_content">
                                <label class="control-label sign_left_small">产证地址</label>
                                <input class="teamcode input_type" style="width:435px;" placeholder="请输入" value="" id="propertyAddr" name="propertyAddr">
                            </div>
                            <div class="form_content ml5">
                                <div class="add_btn">
                                    <button class="btn btn-success mr5 btn-icon" id="caseDetailsSearch"><i class="icon iconfont">&#xe635;</i>查询</button>
                                    <button class="btn btn-success" onclick="javascript:exportBonusExcelButton()">案件奖金汇总导出</button>
                                    <button class="btn btn-success" onclick="javascript:exportToExcel()">案件环节明细导出</button>
                                    <button class="btn btn-grey" type="button" id="caseDetailsClean">清空</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-12">
                        <div class="table_content">
                            <table class="table table_blue table-striped table-bordered table-hover " id="editable" >
                                <thead>
                                    <tr>
                                        <th>案件编号</th>
                                        <th>案件地址</th>
                                        <th>过户时间</th>
                                        <th>结案时间</th>
                                        <th>绩效奖金（元）</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody id="TsAwardBaseList">
                                </tbody>
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
	<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>        
    <!-- Mainly scripts -->
    <content tag="local_script">    
    <script src="<c:url value='/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
    <script src="<c:url value='/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
     <!-- 日期控件 -->
    <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>     
    <!-- Custom and plugin javascript -->
    <script src="<c:url value='/js/inspinia.js' />"></script>
    <script src="<c:url value='/js/plugins/pace/pace.min.js' />"></script>       
 	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script> 
    <!-- 分页控件  -->
    <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
    <script src= "<c:url value='/js/template.js" type="text/javascript' />"></script>
    <script src= "<c:url value='/transjs/award/newBonus.js' />"></script>
   	<!-- 必须JS --> 
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script> 
    <script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>  
		<script id="tsAwardBaseList" type= "text/html">
        {{each rows as item index}}
 			<tr>                   
                     <td>{{item.CASE_CODE}}</td>
                     <td>{{item.PROPERTY_ADDR}}</td>
                     <td>{{item.GUOHU_TIME}}</td>
                     <td>{{item.CLOSE_TIME}}</td>
                     <td>{{item.BASE_CASE_AMOUNT}}</td>
                     <td><div class="expand"  style="color:#4bccec;cursor:pointer;" id="{{item.CASE_CODE}}">展开</div></td>
             </tr>
             <tr class="toogle-show border-e7" id="toggle{{item.CASE_CODE}}" style="display:none;"></tr>
		{{/each}}
	    </script>
	    
	    <script id="tsAwardSrvList" type= "text/html">
									 
             <td colspan="8" class="spread_td">
                <table class="table spread_tab table-bordered">
					<thead>
						<tr>
							<th>人员</th>
							<th>组织</th>
							<th>服务</th>
							<th>基础奖金</th>
							<th>满意度</th>
							<th>满意度占比</th>
							<th>金融达标</th>
							<th>贷款流失</th>
							<th>环节占比</th>
							<th>最终考核结果</th>
							<th>绩效奖金</th>
						</tr>
					</thead>
					<tbody>
					   {{each rows as item index}}
						<tr> 
							<td>{{item.PARTICIPANT}}</td>
							<td>{{item.ORG_NAME}}</td>
							<td>{{item.SRV_CODE}}</td>
							<td>{{item.BASE_AMOUNT}}</td>
							<td>{{item.SATISFACTION}}({{item.SKPI_RATE}})</td>
							<td>{{item.SRV_PART}}</td>
							<td>{{item.MKPI}}({{item.MKPIV}})</td>
							<td>{{item.COM_LS_RATE}}({{item.COM_LS_KPI}})</td>
							<td>{{item.SRV_PART_IN}}</td>
							<td>{{item.KPI_RATE_SUM}}</td>
							<td>{{item.AWARD_KPI_MONEY}}</td>
						</tr>
						{{/each}}
					</tbody>
				</table>
			</td>
	    </script>
	  </content>            
   </body>
</html>