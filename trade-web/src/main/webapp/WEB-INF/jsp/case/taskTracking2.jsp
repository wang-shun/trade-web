
<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
<link href="${ctx}/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
<link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">
<link href="${ctx}/css/common/common.css" rel="stylesheet">
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet">
<!-- aist列表样式 --><%-- 
<link href="${ctx}/css/common/aist.grid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet"> --%>
<!-- 分页控件 -->									
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />									
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet" />  									


<!-- Data Tables -->
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css" />

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/css/workflow/myCaseList.css" />
<link rel="stylesheet" href="${ctx}/css/iconfont/iconfont.css" />
<!-- 必须CSS -->
<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />


<style type="text/css">


</style>
</head>
<body>
<div class="wrapper wrapper-content animated fadeInRight">
     <div class="ibox-content border-bottom clearfix space_box">
         <h2 class="title">
           	  案件筛选
         </h2>
		<form method="get" class="form-horizontal form_box">
             <div class="row clearfix">
                 <div class="form_content">
                		 <label class="sign_left control-label">案件编号 </label>
                		 <div class="sign_right teamcode">
							  <input id="caseCode" type="text" class="teamcode form-control" >
						 </div>
				 </div>
                 <div class="form_content">
                		 <label class="sign_left control-label">CTM编号 </label>
                		 <div class="sign_right teamcode">
							  <input id="ctmCode" type="text" class="teamcode form-control" >
						 </div>
				 </div>
			</div>
			<div class="row clearfix">
                 <div class="form_content">
                		 <label class="sign_left control-label">产证地址 </label>
                		 <div class="sign_right intextval">
							  <input id="caseAddress" type="text"  class="form-control pull-left" >
						 </div>
				 </div>
				 <button id="searchButton" type="button" class="btn btn-success" style="margin-left: 10px;" >
                        <i class="icon iconfont">&#xe635;</i>   查询
                 </button>
			</div>
	   </form>
	</div>
	<div class="table_content">
		<table border="0" cellpadding="0" cellspacing="0" class="table table_blue table-striped table-bordered table-hover ">
			<thead>
				<tr>
					<th ><span class="sort" sortColumn="A.CASE_CODE" sord="desc" onclick="caseCodeSort();" >案件编号</span><i id="caseCodeSorti" class="fa fa-sort-desc fa_down"></i></th>
					<th >产权地址</th>
					<th >上家</th>
					<th >下家</th>
					<th >经办人</th>
					<th >案件状态</th>
				</tr>
			</thead>
			<tbody id="myTaskList">
				
			</tbody>
		</table>
	</div>
			
	<div class="text-center page_box">
		<span id="currentTotalPage"><strong ></strong></span>
		<span class="ml15">共<strong  id="totalP"></strong>条</span>&nbsp;
		<div id="pageBar" class="pagergoto">
		</div>  
    </div> 	
	
</div>

<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>

<content tag="local_script">
<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<!-- Custom and plugin javascript -->
<script src="${ctx}/js/inspinia.js"></script>
<script src="${ctx}/js/plugins/pace/pace.min.js"></script>

<!-- 选择组织控件 -->
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>

<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
<!-- iCheck -->
<script	src="${ctx}/js/plugins/iCheck/icheck.min.js"></script>
<script src="${ctx}/js/trunk/task/taskTracking2.js?version=1.1.1"></script> 
<script src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
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
					<td >
 						<p class="big">
							<a href="${ctx}/case/caseDetail?caseId={{item.PKID}}"  target="_blank">{{item.CASE_CODE}}</a>
						</p>
 						<p class="big">
							<i class="tag_sign">c</i>{{item.CTM_CODE}}
						</p>
					</td>
					<td >

<p class="demo-top" title="{{item.PROPERTY_ADDR}}">
{{if item.PROPERTY_ADDR != null && item.PROPERTY_ADDR!="" && item.PROPERTY_ADDR.length>24}}
{{item.PROPERTY_ADDR.substring(item.PROPERTY_ADDR.length-24,item.PROPERTY_ADDR.length)}}
{{else}}
{{item.PROPERTY_ADDR}}
{{/if}}					 
						</p>
 							<p >
								 <i class="salesman-icon"> </i>
								 <a class="demo-top" title="{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.AGENT_ORG_NAME}}" >
{{if item.AGENT_ORG_NAME !="" && item.AGENT_ORG_NAME !=null && item.AGENT_ORG_NAME.length>8}}							
{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.AGENT_ORG_NAME.substring(0,10)}}...
{{else}}
{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.AGENT_ORG_NAME}}
{{/if}}	
								 </a>
							</p>
					</td>
					<td >

 <p  >
						<a  class="demo-top" title="上家信息:{{item.SELLER}}" >
{{ if item.SELLER !="" && item.SELLER !=null && item.SELLER.indexOf("/") >-1}}
{{if item.SELLER.split("/").length-1 >1}}
{{item.SELLER.substring(0,item.SELLER.indexOf("/"))}}<br>
{{
(item.SELLER.substring(item.SELLER.indexOf("/"),item.SELLER.length)).substring(1,((item.SELLER.substring(item.SELLER.indexOf("/")+1,item.SELLER.length)).indexOf("/"))+1)
}}</br>...
{{else}}
{{item.SELLER.substring(0,item.SELLER.indexOf("/"))}}<br>
{{
(item.SELLER.substring(item.SELLER.indexOf("/"),item.SELLER.length)).substring(1,((item.SELLER.substring(item.SELLER.indexOf("/")+1,item.SELLER.length)).length)+1)
}}</br>
{{/if}}
{{else}}
{{ if item.SELLER.length>4}}
{{item.SELLER.substring(0,4)}}...
{{else}}
{{item.SELLER}}
{{/if}}
{{/if}}
						</a>
						</p>


					</td>
					<td >
  <p  >
							<a class="demo-left" title="下家信息:{{item.BUYER}}" >
{{ if item.BUYER !="" && item.BUYER !=null && item.BUYER.indexOf("/") >-1}}
{{if item.BUYER.split("/").length-1 >1}}
{{item.BUYER.substring(0,item.BUYER.indexOf("/"))}}<br>
{{
(item.BUYER.substring(item.BUYER.indexOf("/"),item.BUYER.length)).substring(1,((item.BUYER.substring(item.BUYER.indexOf("/")+1,item.BUYER.length)).indexOf("/"))+1)
}}</br>...
{{else}}
{{item.BUYER.substring(0,item.BUYER.indexOf("/"))}}<br>
{{
(item.BUYER.substring(item.BUYER.indexOf("/"),item.BUYER.length)).substring(1,((item.BUYER.substring(item.BUYER.indexOf("/")+1,item.BUYER.length)).length)+1)
}}</br>
{{/if}}
{{else}}
{{ if item.BUYER.length>4}}
{{item.BUYER.substring(0,4)}}...
{{else}}
{{item.BUYER}}
{{/if}}
{{/if}}
							</a>
                          </p>
					</td>
					<td >
					<span class="manager" ><a class="demo-left" title="{{item.PROCESSOR_ID}}" ><em>经办人：</em>
						{{ if item.PROCESSOR_ID !="" && item.PROCESSOR_ID !=null && item.PROCESSOR_ID.indexOf("/") >-1 }}
                        		{{item.PROCESSOR_ID.substring(0,item.PROCESSOR_ID.indexOf("/"))}}...
						{{else}}
							    {{item.PROCESSOR_ID}}
						{{/if}}
						</a></span>
						<span class="manager" ><a class="demo-left" title="{{item.MANAGER}}" ><em>主&nbsp;&nbsp;&nbsp;&nbsp;管：</em>
						{{ if item.MANAGER !="" && item.MANAGER !=null && item.MANAGER.indexOf(";") >-1 }}
                        	{{item.MANAGER.substring(0,item.MANAGER.indexOf(";"))}}...
						{{else}}
							{{item.MANAGER}}
						{{/if}}
						</a></span>
					</td>

					<td >
 						<p >
							<i class="sign_blue">{{item.STATUS}}</i>
						</p>
					</td>
				</tr>
		{{/each}}
</script>

<script>
aist.sortWrapper({
	reloadGrid : searchMethod
}); 
</script> 
</content>
</body>
</html>
