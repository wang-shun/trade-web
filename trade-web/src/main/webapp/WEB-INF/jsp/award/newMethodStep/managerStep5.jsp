<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>计件奖金自动化流程</title>      
              
    	<link rel="stylesheet" href="<c:url value='/static/css/bootstrap.min.css'/>" rel="stylesheet" />	
    	<link rel="stylesheet" href="<c:url value='/static/font-awesome/css/font-awesome.css' />" rel="stylesheet" />
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
		<link href="<c:url value='/static/css/btn.css' />" rel="stylesheet" />			
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
                <h2 class="title">奖金汇总</h2>
            </div>
            <div class="form_list">
                <div class="line">
                    <div class="form_content">
                        <label class="control-label sign_left_small">员工姓名</label>
                        <input class=" input_type" placeholder="" value=""  name="realName" id="realName" style="width:120px">
                    </div>
                    <div class="form_content">
                        <label class="control-label sign_left_small">组织</label>
                        <input class="teamcode input_type" placeholder="请输入" value="" id="orgName" name="orgName"                         
						onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName', startOrgId:'${serviceDepId}',
								orgType:'',departmentType:'',departmentHeriarchy:'',expandNodeId:'${serviceDepId}',
								chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack})">
                        <input type="hidden" id="yuCuiOriGrpId" value="">
						<input type="hidden" id="orgHierarchy" value="">
                        <div class="input-group float_icon organize_icon" id="organizeOnclick">
                            <i class="icon iconfont"></i>
                        </div>
                        
                    </div>
                
                    <div class="form_content ml5">
                        <div class="add_btn">
                            <button type="button" class="btn btn-success mr5 btn-icon"  id="personBonusCollectSearch"><i class="icon iconfont">&#xe635;</i>查询</button>
                            <button class="btn btn-success" onclick="javascript:exportPersonBonusToExcel()" >导出Excel</button>
                            <button type="button" class="btn btn-grey"  id="personBonusCollectClean">清空</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
		<div class="row">
			<div class="col-md-12">
				<div class="table_content">
					<table class="table table_blue table-striped table-bordered table-hover " id="editable">
						<thead>
							<tr>
								<th>计件年月</th>
								<th>姓名</th>
								<th>员工编号</th>
								<th>所在组别</th>
								<th>绩效奖金(元)</th>
							</tr>
						</thead>
						<tbody id="personCollectList"></tbody>
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
    
    <script src="<c:url value='/static/js/jquery-2.1.1.js' />" ></script>
    <script src="<c:url value='/static/js/bootstrap.min.js' />" ></script>
    <script	src="<c:url value='/js/jquery.blockui.min.js' />" ></script>
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
    <script src= "<c:url value='/transjs/award/personBonusCollect.js' />"></script>
   	<!-- 必须JS --> 
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script> 
    <script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>  
    <!-- 选择组织控件 -->
    <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
    <script	src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script>
  	<script src="<c:url value='/js/plugins/chosen/chosen.jquery.js' />"></script>
   
	<script id="template_personCollectList" type= "text/html">
      	{{each rows as item index}}
  				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}} 
					<td><p class="big">{{item.BELONG_MONTH}}</p></td>
					<td><p class="big">{{item.REAL_NAME}}</p></td>
					<td><p class="big">{{item.EMPLOYEE_CODE}}</p></td>
					<td><p class="big">{{item.ORG_NAME}}</p></td>
					<td><p class="big">{{item.SUMMONEY}}</p></td>					
                 </tr>			
       {{/each}}  
	    </script>	    
	  </content>             
   </body>
</html>