<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>计件奖金发放汇总列表</title>      

		<!-- Data Tables -->
		<link href="<c:url value='/css/plugins/dataTables/dataTables.bootstrap.css'/>"  rel="stylesheet" />		
		<link href="<c:url value='/css/plugins/dataTables/dataTables.responsive.css'/>"  rel="stylesheet" />
		<link href="<c:url value='/css/plugins/dataTables/dataTables.tableTools.min.css'/>"  rel="stylesheet" />
		<link href="<c:url value='/css/plugins/datapicker/datepicker3.css'/>"  rel="stylesheet" />		
		
		<!-- 分页控件 -->
		<link href="<c:url value='/css/plugins/pager/centaline.pager.css'/>"  rel="stylesheet" />
		<link href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css'/>"  rel="stylesheet" />
		
		<!-- index_css -->
 		<link href="<c:url value='/static/trans/css/common/base.css' />" rel="stylesheet" />
		<link href="<c:url value='/static/trans/css/common/table.css' />" rel="stylesheet" />
		<link href="<c:url value='/static/trans/css/common/input.css' />" rel="stylesheet" />
		<link href="<c:url value='/static/css/btn.css' />" rel="stylesheet" />
		<link href="<c:url value='/static/iconfont/iconfont.css' />" rel="stylesheet" />
		<link href="<c:url value='/static/trans/css/award/baseAward.css' />" rel="stylesheet" />	 

		<!-- 必须CSS -->
		<link href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" rel="stylesheet" />
    </head>
    
    <body class="pace-done">
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content border-bottom clearfix space_box">
			  <div class="clearfix"> 
                   <h2 class="title pull-left ml15">计件奖金发放汇总列表</h2>
              </div>
              <div class="form_list">
                        <div class="line">
                            <div class="form_content">
                                <label class="control-label sign_left_small">计件年月</label>
                              	<div  id="datepicker_0" class="input-daterange" data-date-format="yyyy-mm" style="display:inline-block;">
                                     <input class="form-control" type="text"  style="width: 100px;" value="${belongMonth}" id="belongMonth" name="belongMonth">
                                </div>
                            </div>

                            <div class="form_content">
                                    <label class="control-label sign_left_small select_style mend_select">过户审核日期</label>
                                    <div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" id="datepicker_1">
                                        <input  class="form-control data_style" type="text" value="" placeholder="起始日期"  id="dtBegin_0" name="dtBegin">
                                        <span class="input-group-addon">到</span>
                                        <input  class="form-control data_style" type="text" value="" placeholder="结束日期"  id="dtEnd_0" name="dtEnd">
                                    </div>
                            </div>
                        </div>
                        <div class="line">
                            <div class="form_content">
                                <label class="control-label sign_left_small">案件编号</label>
                                <input class=" input_type" placeholder="请输入" value="" id="caseCode" name="caseCode">
                            </div>
                            <div class="form_content" style="margin-left: 49px;">
                                <label class="control-label sign_left" style="width:100px;">发放状态</label>
                                <select class="select_control sign_right_one"  id="awardStatus" name="awardStatus">
                                    <option value="all">请选择</option>
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                </select>
                            </div>
                        </div>
                        <div class="line">
                            <div class="form_content">
                                <label class="control-label sign_left_small">产证地址</label>
                                <input class="teamcode input_type" style="width:435px;" placeholder="请输入" value="" name="propertyAddr" id="propertyAddr">
                            </div>                            
                            <div class="form_content ml5">
                                <div class="add_btn">
                                    <button class="btn btn-success mr5 btn-icon" id="awardCaseCollectSearch"><i class="icon iconfont">&#xe635;</i>查询</button>
                                    <button type="button" class="btn btn-grey mr5" id="awardCaseCollectClean">清空</button>
                                    <button type="button" class="btn btn-success"  id="awardCaseCollectToExcel"  onclick="javascript:exportAwardBaseToExcel()">导出Excel</button>
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
								<th><span class="sort" sortColumn="TC.CASE_CODE" sord="desc" onclick="caseCodeSort();">案件编号</span><i id="caseCodeSorti" class="fa fa-sort-desc fa_down"></i></th>
								<th>产证地址</th>
								<th>过户审批时间</th>
								<th>奖金是否发放</th>
								<th>绩效奖金(元)</th>
								<th>计件年月</th>
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
	<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>        
        <!-- Mainly scripts -->
        <content tag="local_script"> 
        <%--  <script src="${ctx}/js/bootstrap.min.js"></script> --%>
        <script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
        <script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
         <!-- 日期控件 -->
        <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>     
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
	       <script>
        $(document).ready(function () {

            $('.input-daterange').datepicker({
                keyboardNavigation: false,
                forceParse: false,
                autoclose: true
            });


            //新增
            $('#AddBtn').click(function(event) {
                $('#addModal').modal('show');
            });


        });
    </script>
	    
	    </content> 
        <input type="hidden" id="ctx" value="${ctx}" />      
    </body>
</html>