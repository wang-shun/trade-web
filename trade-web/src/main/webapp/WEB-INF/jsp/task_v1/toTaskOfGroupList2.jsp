<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>本组待办任务列表</title>
<%-- <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="${ctx}/fonts/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet"> --%>


<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet"> 
<link href="${ctx}/css/style.css" rel="stylesheet"> 
<link href="${ctx}/css/plugins/dropzone/basic.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/dropzone.css" rel="stylesheet">
<link href="${ctx}/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/common/common.css" rel="stylesheet">

<!-- IonRangeSlider -->
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/transcss/award/bonus.css" rel="stylesheet">
<link href="${ctx}/css/transcss/task/myTaskList.css" rel="stylesheet">
<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<!-- aist列表样式 -->
<link href="${ctx}/css/common/aist.grid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">

<!-- Data Tables -->
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css" />

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/css/iconfont/iconfont.css" />
<!-- 必须CSS -->
<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />

<style type="text/css">

</style>
</head>
<body class="pace-done">
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

<div class="wrapper wrapper-content animated fadeInRight">
     <div class="ibox-content border-bottom clearfix space_box">
         <h2 class="title">
                        本组待办任务列表
         </h2>
		<form method="get" class="form_list">
			<div class="form_content">
			    <select id="inTextType"  class="form-control select_control sign_left" >
					<option value="1" selected>产证地址</option>
					<option value="0" >客户姓名</option>
					<option value="2">经纪人姓名</option>
					<option value="3">所属分行</option>
					<option value="4">案件编号</option>
					<option value="5">CTM编号</option>
				</select>
				<input id="inTextVal"  class="sign_right input_type">
			</div>
			 <div class="form_content">
				<label class="control-label sign_left" style="width:100px;">任务名</label>
			    <aist:dict id="taskDfKey" name="taskDfKey" clazz="select_control sign_right_one" display="select" dictType="part_code" defaultvalue="" />
			</div>
			<div class="form_content space">
				<div class="add_btn">
					<button id="searchButton" type="button" class="btn btn_blue"><i class="icon iconfont">&#xe635;</i>查询</button>&nbsp;&nbsp;
					<button onclick="showOptUsers();" type="button" class="btn btn_blue" disabled="true" id="caseDistributeButton">批量分配</button>
				</div>
			</div>
		</form>
		
	</div>
	
	<div class="table_content">  <form id="form1"> <input type="hidden" id="h_userId" name="userId">
		<table border="0" cellpadding="0" cellspacing="0" class="table table_blue table-striped table-bordered table-hover ">
			<thead>
				<tr>
					 <th >
                         <input type="checkbox"  class="i-checks" id="checkAllNot">
                     </th>
					 <th class="text-center light_icon">
                            <i class="iconfont icon_light">
                                &#xe604;
                            </i>
                       </th>
					<th ><span class="sort" sortColumn="tw.CASE_CODE" sord="desc" onclick="caseCodeSort();" >案件编号</span><i id="caseCodeSorti" class="fa fa-sort-desc fa_down"></i></th>
					<th >流程环节</th>
					<th >案件地址</th>
					<th ><span class="sort" sortColumn="CREATE_TIME" sord="asc" onclick="createTimeSort();" >创建时间</span><i id="createTimeSorti" class="fa fa-sort-asc fa_up"></i></th>
					<th >执行人</th>
					<th >操作</th>
				</tr>
			</thead>
			<tbody id="myTaskList">
				
			</tbody>
		</table>  </form>
	</div>
			
	<div class="text-center page_box">
		<span id="currentTotalPage"><strong ></strong></span>
		<span class="ml15">共<strong  id="totalP"></strong>条</span>&nbsp;
		<div id="pageBar" class="pagergoto">
		</div>  
    </div> 	
</div> 	
	  
	
 <!--  <form id="form1">
		<input type="hidden" id="h_userId" name="userId">
		<div class="bonus-table data-wrap-in"></div>
  </form> -->
<!-- Mainly scripts -->
<content tag="local_script"> 

<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<!-- Custom and plugin javascript -->
<script src="${ctx}/js/inspinia.js"></script>
<script src="${ctx}/js/plugins/pace/pace.min.js"></script>
<!-- iCheck -->
<script	src="${ctx}/js/plugins/iCheck/icheck.min.js"></script>
<script src="${ctx}/js/trunk/task/toTaskOfGroupList2.js?version=1.1.1"></script> 
<script src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>

<!-- 选择组织控件 -->
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
<!-- 分页控件  -->
<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
<script src="${ctx}/js/plugins/jquery.custom.js"></script>
<!-- 必须JS -->
<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>

<script id="template_myTaskList" type= "text/html">
               {{each rows as item index}}
                 {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
					<td class="text-center">
							<input type='checkbox' name='ckb_task' onclick="ckbChange();" style="margin-top: 9px;float: left;">
							<input type='hidden' name='taskIds' value="{{item.ID}}" disabled>
							<input type='hidden' name='caseCodes' value="{{item.CASE_CODE}}" disabled>
					</td>
					<td >
						{{if item.DATELAMP < l1|| item.DATELAMP==null}}
    
						{{else if item.DATELAMP < l2}}
                 			<div class="sk-spinner sk-spinner-double-bounce sk-spinner-light">
                 			<div class="sk-double-bounce1 green_light"></div>
                 			<div class="sk-double-bounce2 green_light"></div>
                 			</div>

				        {{else if item.DATELAMP < l3}}
							<div class="sk-spinner sk-spinner-double-bounce sk-spinner-light">
                 			<div class="sk-double-bounce1 orange_light"></div>
                 			<div class="sk-double-bounce2 orange_light"></div>
                 			</div>
  						{{else}}
                			<div class="sk-spinner sk-spinner-double-bounce sk-spinner-light mt3">
                 			<div class="sk-double-bounce1 red_light"></div>
                 			<div class="sk-double-bounce2 red_light"></div>
                 			</div>
						{{/if}}
							
						{{if item.RED_LOCK==1}}
							<p class="text-center clock clock_red">
                               <i class="icon iconfont clock_icon">&#xe60b;</i>
                            </p>
						{{else}}
							<p class="text-center clock ">
                               <i class="icon iconfont clock_icon">&#xe60b;</i>
                            </p>
						{{/if}}
					</td>

					<td >
 						<p class="big">
							<a href="${ctx}/case/caseDetail?caseId={{item.PKID}}"  target="_blank">{{item.CASE_CODE}}</a>
						</p>
 						<p class="big">
							<i class="tag_sign">c</i>{{item.CTM_CODE}}
						</p>
					</td>
					<td class="center">
 						<p >
							<i class="sign_blue">{{item.NAME}}</i>
						</p>
 						<p >
							{{item.WFE_NAME}}
						</p>
					</td>
					<td >
						

{{if item.PROPERTY_ADDR != null && item.PROPERTY_ADDR!="" && item.PROPERTY_ADDR.length>24}}
<p class="demo-top" title="{{item.PROPERTY_ADDR}}">
{{item.PROPERTY_ADDR.substring(item.PROPERTY_ADDR.length-24,item.PROPERTY_ADDR.length)}}
{{else}}
</p><p>
{{item.PROPERTY_ADDR}}
{{/if}}					 
						</p>
 							<p >
								 <i class="salesman-icon"> </i>
								
{{if item.AGENT_ORG_NAME !="" && item.AGENT_ORG_NAME !=null && item.AGENT_ORG_NAME.length>11}}	
 <a class="demo-top" title="直管经理: {{item.MANAGER_INFO.realName}}  电话: {{item.MANAGER_INFO.mobile}}   经纪人信息: {{item.AGENT_NAME}}/{{item.MOBILE}}/{{item.AGENT_ORG_NAME}}" >	
{{if item.AGENT_NAME !=null && item.AGENT_NAME.length > 2}}			
{{item.AGENT_NAME}}/{{item.MOBILE}}/{{item.AGENT_ORG_NAME.substring(0,10)}}...
{{else}}
{{item.AGENT_NAME}}/{{item.MOBILE}}/{{item.AGENT_ORG_NAME.substring(0,11)}}...
{{/if}}					
{{else}}
</a>
 <a class="demo-top" title="直管经理: {{item.MANAGER_INFO.realName}}  电话: {{item.MANAGER_INFO.mobile}}  " >
{{item.AGENT_NAME}}/{{item.MOBILE}}/{{item.AGENT_ORG_NAME}}
{{/if}}	
								 </a>
							</p>
					</td>
					<td >
 						
						{{if item.CREATE_TIME!=null}}
						   <p>  
                              <i class="sign_normal">创</i>
                                 {{item.CREATE_TIME}}          
                          </p>
						{{else}}
                            <p>  
                              <i class="sign_grey">创</i>
                                 {{item.CREATE_TIME}}          
                           </p>
						{{/if}}
						{{if item.EST_PART_TIME!=null}}
						   <p>  
                              <i class="sign_normal">预</i>
                                 {{item.EST_PART_TIME}}          
                          </p>
						{{else}}
                            <p>  
                              <i class="sign_grey">预</i>
                                 {{item.EST_PART_TIME}}          
                           </p>
						{{/if}}

					</td>
					<td class="center">
 						 <p>执行人：{{item.assignee}}</p>
					</td>
					<td class="text-center">
 						 <i class="iconfont icon_revise" onclick="showOptUsers('{{item.ID}}','{{item.CASE_CODE}}')" target="_blank">&#xe603;
                         </i>
					</td>
				</tr>
				
		{{/each}}
</script>

<script>
var ctx = "${ctx}";
var sDepId = "${serviceDepId}";
var Lamp1=${Lamp1};
var Lamp2=${Lamp2};
var Lamp3=${Lamp3};
var optTaskId;
var caseCode;
aist.sortWrapper({
	reloadGrid : searchMethod
});       
        	
</script>
</content> 
<input type="hidden" id="ctx" value="${ctx}" />
</body>
</html>