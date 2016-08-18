
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
<!-- aist列表样式 -->
<link href="${ctx}/css/common/aist.grid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
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


<style type="text/css">


.hint { position: relative; display: inline-block; }

.hint:before, .hint:after {
	position: absolute;
	opacity: 0;
	z-index: 1000000;
	-webkit-transition: 0.3s ease;
	-moz-transition: 0.3s ease;
	pointer-events: none;
}		
.hint:hover:before, .hint:hover:after {
	opacity: 1;
}
.hint:before {
	content: '';
	position: absolute;
	background: transparent;
	border: 6px solid transparent;
	position: absolute;
}	
.hint:after {
	content: attr(data-hint);
	background: rgba(0, 0, 0, 0.8);
	color: white;
	padding: 8px 10px;
	font-size: 12px;
	white-space: nowrap;
	box-shadow: 4px 4px 8px rgba(0, 0, 0, 0.3);
}

/* top */
.hint-top:before {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -18px 0;
	border-top-color: rgba(0, 0, 0, 0.8);
}		
.hint-top:after {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -6px -10px;
}
.hint-top:hover:before {
	margin-bottom: -10px;
}
.hint-top:hover:after {
	margin-bottom: 2px;
}

/* top */
.hint-top1:before {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -18px 0;
	border-top-color: rgba(0, 0, 0, 0.8);
}		
.hint-top1:after {
    bottom: 100%;
	margin-bottom: 2px;
	width:130px!important;
	white-space: normal!important;
	word-break:break-all!important;
}
.hint-top1:hover:before {
	margin-bottom: -10px;
}
.hint-top1:hover:after {
	margin-bottom: 2px;
	width:130px!important;
	white-space: normal!important;
	word-break:break-all!important;
}
/* top */
.hint-top2:before {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -18px 0;
	border-top-color: rgba(0, 0, 0, 0.8);
}		
.hint-top2:after {
	bottom: 100%;
	left: 50%;
	margin: 0 0 -6px -10px;
}
.hint-top2:hover:before {
	margin-bottom: -10px;
}
.hint-top2:hover:after {
	margin-bottom: 2px;
	width:280px!important;
	white-space: normal!important;
	word-break:break-all!important;
}


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
 						<p class="big">
							{{item.PROPERTY_ADDR}}
						</p>
 							<span class="tooltip-demo">
                                  <i class="salesman-icon"> </i>
								  <a class="hint  hint-top" data-hint="{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.AGENT_ORG_NAME}} ">{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.AGENT_ORG_NAME}}</a>
							</span>
						</p>
					</td>
					<td >
 						<span >
						{{ if item.SELLER !="" && item.SELLER !=null && item.SELLER.indexOf("/") >-1}}
							<a class="hint  hint-top1" data-hint="{{item.SELLER}}" >{{item.SELLER.substring(0,item.SELLER.indexOf("/"))}}...</a>
						{{else}}
							<a class="hint  hint-top1" data-hint="{{item.SELLER}}" >{{item.SELLER}}</a>
						{{/if}}
						</span>
					</td>
					<td >
 						<span >
						{{ if item.BUYER !="" && item.BUYER !=null && item.BUYER.indexOf("/") >-1}}
							<a class="hint  hint-top1" data-hint="{{item.BUYER}}" >{{item.BUYER.substring(0,item.BUYER.indexOf("/"))}}...</a>
						{{else}}
							<a class="hint  hint-top1" data-hint="{{item.BUYER}}" >{{item.BUYER}}</a>
						{{/if}}
						</span>
					</td>
					<td >
						{{ if item.PROCESSOR_ID !="" && item.PROCESSOR_ID !=null && item.PROCESSOR_ID.indexOf("/") >-1 }}
                        		<span class="manager" ><a class="hint  hint-top1" data-hint="{{item.PROCESSOR_ID}}" ><em>经办人：</em>{{item.PROCESSOR_ID.substring(0,item.PROCESSOR_ID.indexOf("/"))}}...</a></span>
						{{else}}
							<span class="manager" ><a class="hint  hint-top1" data-hint="{{item.PROCESSOR_ID}}" ><em>经办人：</em>{{item.PROCESSOR_ID}}</a></span>
						{{/if}}
						{{ if item.MANAGER !="" && item.MANAGER !=null && item.MANAGER.indexOf(";") >-1 }}
                        	<span class="manager" ><a class="hint  hint-top1" data-hint="{{item.MANAGER}}" ><em>主&nbsp;&nbsp;&nbsp;&nbsp;管：</em>{{item.MANAGER.substring(0,item.MANAGER.indexOf(";"))}}...</a></span>
						{{else}}
							<span class="manager" ><a class="hint  hint-top1" data-hint="{{item.MANAGER}}" ><em>主&nbsp;&nbsp;&nbsp;&nbsp;管：</em>{{item.MANAGER}}</a></span>
						{{/if}}
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
