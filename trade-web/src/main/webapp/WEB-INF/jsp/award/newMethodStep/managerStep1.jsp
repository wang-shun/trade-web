<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>计件奖金自动化流程</title>      
    	<link href="<c:url value='/static/css/bootstrap.min.css'/>" rel="stylesheet" />	
    	<link href="<c:url value='/static/font-awesome/css/font-awesome.css' />" rel="stylesheet" />
		<!-- Data Tables -->
		<link href="<c:url value='/css/plugins/dataTables/dataTables.bootstrap.css'/>"  rel="stylesheet" />		
		<link href="<c:url value='/css/plugins/dataTables/dataTables.responsive.css'/>"  rel="stylesheet" />
		<link href="<c:url value='/css/plugins/dataTables/dataTables.tableTools.min.css'/>"  rel="stylesheet" />
		<link href="<c:url value='/css/plugins/datapicker/datepicker3.css'/>"  rel="stylesheet" />		
		<link href="<c:url value='/static/iconfont/iconfont.css' />" rel="stylesheet" />	
		<link href="<c:url value='/static/css/animate.css'/>"  rel="stylesheet"/> 
		<link href="<c:url value='/static/css/style.css' />" rel="stylesheet" />	
		<!--弹出框样式  -->
		<link href="<c:url value='/css/common/xcConfirmForAward.css' />" rel="stylesheet" />
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
		<script>       
				document.domain = 'centaline.com';
		</script>
    </head>
    
    <body class="pace-done">
			<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
        <div class="wrapper wrapper-content animated fadeInRight">
	        <div class="ibox-content border-bottom clearfix space_box">
	            <div class="clearfix"> 
	                <h2 class="title pull-left ml15">满意度列表</h2>
	            </div>
	            <div class="form_list">
	                <div class="line">
	                    <div class="form_content">
	                        <label class="control-label sign_left_small">案件编号</label>
	                        <input class="teamcode input_type" placeholder="请输入" value=""  name="caseCode"  id="caseCode">
	                    </div>
	                    <div class="form_content ml5">
	                        <div class="add_btn">	                            
	                            <button type="button" class="btn btn-success mr5 btn-icon"  id="satisSearch">
	                                <i class="icon iconfont"></i>查询</button>
	                            <button class="btn btn-success"  onClick="javascript:exportStaisToExcel();">导出Excel</button>
	                            <button class="btn btn-success"  onClick="javascript:syncSatisListToKpi();">满意度同步</button>
	                            <button type="button" class="btn btn-grey" id="satisClean">清空</button>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
        
            <div class="col-md-12">
                <div class="table_content">
                    <table class="table table_blue table-hover table-striped table-bordered">
                        <thead>
                            <tr>
                                <th rowspan="2"><span class="sort" sortColumn="CASE_CODE" sord="desc" onclick="caseCodeSort();">案件编号</span><i id="caseCodeSorti" class="fa fa-sort-desc fa_down"></i></th>
                                <th colspan="3" class="text-center">上家</th>
                                <th colspan="4" class="text-center">下家</th>
                                <th>上家-下家</th>
                                <th rowspan="2">计件年月</th>
                            </tr>
                            <tr>
                              <th>签约评分</th>
                              <th>过户评分</th>
                              <th>陪同还贷评分</th>
                              <th>签约评分</th>
                              <th>过户评分</th>
                              <th>商贷评分</th>
                              <th>纯公积金评分</th>
                              <th>电话是否接通 </th>
                            </tr>
                        </thead>
                        <tbody id="satisList"></tbody>
                    </table>
                  </div>
                  <div class="text-center page_box">
						<span id="currentTotalPage"><strong></strong></span> <span class="ml15">共<strong id="totalP"></strong>条
						</span>&nbsp;
						<div id="pageBar" class="pagergoto"></div>
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
    <script src="<c:url value='/static/js/jquery-2.1.1.js' />" ></script>
    <script src="<c:url value='/static/js/bootstrap.min.js' />" ></script>  
      
    <script src="<c:url value='/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
    <script src="<c:url value='/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
    <script	src="<c:url value='/js/jquery.blockui.min.js' />" ></script>
     <!-- 日期控件 -->
    <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>     
    <!-- Custom and plugin javascript -->
    <script src="<c:url value='/js/inspinia.js' />"></script>
    <script src="<c:url value='/js/plugins/pace/pace.min.js' />"></script>       
 	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script> 
    <!-- 分页控件  -->
    <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
    <script src= "<c:url value='/js/template.js" type="text/javascript' />"></script>    
    <script src= "<c:url value='/transjs/award/satisfied.js' />"></script>
   	<!-- 必须JS --> 
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script> 
    <script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>  
    <!-- 引入弹出框js文件 -->
    <script src="<c:url value='/js/common/xcConfirm.js' />"></script>
	<script id="template_satisList" type= "text/html">
      	{{each rows as item index}}
  				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                  {{/if}}                  
					<td >
						{{item.CASE_CODE}}
					</td>
					<td>
						{{item.SALER_SIGN_SAT}}
					</td>
					<td>
						{{item.SALER_GUOHU_SAT}}
					</td>
					<td>
						{{item.SALER_LOAN_SAT}}
					</td>
					<td>
						{{item.BUYER_SIGN_SAT}}
					</td>
					<td>
						{{item.BUYER_GUOHU_SAT}}
					</td>
					<td>
						{{item.BUYER_COM_SAT}}
					</td>
					<td>
						{{item.BUYER_PSF_SAT}}
					</td>
					<td>{{item.SALER_CALLBACK == "1"?"是":"否"}}-{{item.BUYER_CALLBACK == "1"?"是":"否"}}</td>
					<td>{{item.BELONG_MONTH}}</td>			
                 </tr>			
       {{/each}}  
	    </script>	    
	  </content>             
   </body>
</html>