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
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />									
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet" />  									
<!-- Data Tables -->
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css" />
<!-- index_css -->
<link rel="stylesheet" href="${ctx}/css/workflow/myCaseList.css" />
<link rel="stylesheet" href="${ctx}/css/iconfont/iconfont.css" />
<!-- index_css -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
<!-- 必须CSS -->
<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />
<style type="text/css">
</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<input type="hidden" id="userId" value="${queryUserId}" />
<div class="wrapper wrapper-content animated fadeInRight">
     <div class="ibox-content border-bottom clearfix space_box">
         <h2 class="title">
           	  外单案件列表
         </h2>
		<form method="get" class="form-horizontal form_box">
             <div class="row clearfix">
                 <div class="form_content">
                		 <label class="sign_left control-label">案件编号 </label>
                		 <div class="sign_right teamcode">
							  <input id="caseCode" id="caseCode" type="text" class="teamcode form-control"  value="">
						 </div>
				 </div>
                 <div class="form_content">
                		<label class="control-label mr10 select_style mend_select" >创建时间</label>
						<div class="input-group sign-right dataleft input-daterange" data-date-format="yyyy-mm-dd" id="datepicker_0"  style=" padding-left: 10px; ">
							<input id="dtBegin_0" name="dtBegin"
								class="form-control data_style" type="text" value="${startTime}"
								placeholder="起始日期"> <span class="input-group-addon">到</span>
							<input id="dtEnd_0" name="dtEnd" class="form-control data_style"
								type="text" value="${endTime}" placeholder="结束日期">
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
                 <button type="reset" id="loanLostCleanButton" class="btn btn-grey">清&nbsp;&nbsp;空</button>
			</div>
	   </form>
	</div>
	<div class="table_content">
		<table border="0" cellpadding="0" cellspacing="0" class="table table_blue table-striped table-bordered table-hover ">
			<thead>
				<tr>
					<th ><span class="sort" sortColumn="A.CASE_CODE" sord="desc" onclick="caseCodeSort();" >案件编号</th>
					<th >产证地址</th>
					<th >上家</th>
					<th >下家</th>
					<th >经办人</th>
					<th >创建时间</th>
					<th >创建人</th>
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
<script src="${ctx}/js/trunk/task/newWDCaselist.js?version=1.1.1"></script> 
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
					{{item.CASE_CODE}}
				</p>
				<p class="big">
					<i class="tag_sign">c</i>{{item.CTM_CODE}}
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
					 <a class="demo-top" title="{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.AGENT_ORG_NAME}}" >
					{{if item.AGENT_NAME !=null && item.AGENT_NAME.length > 2}}			
					{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.AGENT_ORG_NAME.substring(0,10)}}...
					{{else}}
					{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.AGENT_ORG_NAME.substring(0,11)}}...
					{{/if}}						
					</a></p><p>
					{{else}}<a>
					{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.AGENT_ORG_NAME}}
					{{/if}}	
					</a>
				</p>
			</td>
			<td >
				<p  >
					{{ if item.SELLER !="" && item.SELLER !=null && item.SELLER.indexOf("/") >-1}}
					{{if item.SELLER.split("/").length-1 >1}}
					<a  class="demo-left" title="上家信息:{{item.SELLER}}" >
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
					{{else}}</a></p><p>
					{{item.SELLER}}
					{{/if}}
				</p>
			</td>
			<td >
				<p  >						
					{{ if item.BUYER !="" && item.BUYER !=null && item.BUYER.indexOf("/") >-1}}
					{{if item.BUYER.split("/").length-1 >1}}
					<a class="demo-left" title="下家信息:{{item.BUYER}}" >
					{{item.BUYER.substring(0,item.BUYER.indexOf("/"))}}<br>
					{{
					(item.BUYER.substring(item.BUYER.indexOf("/"),item.BUYER.length)).substring(1,((item.BUYER.substring(item.BUYER.indexOf("/")+1,item.BUYER.length)).indexOf("/"))+1)
					}}</br>...
					{{else}}
					{{item.BUYER.substring(0,item.BUYER.indexOf("/"))}}<br>
					{{
					(item.BUYER.substring(item.BUYER.indexOf("/"),item.BUYER.length)).substring(1,((item.BUYER.substring(item.BUYER.indexOf("/")+1,item.BUYER.length)).length)+1)
					}}</br>
					{{/if}}</a></p><p>
					{{else}}
					{{item.BUYER}}
					{{/if}}
				 </p>
			</td>
			<td >
				{{if "" != item.PROCESSOR_ID  && null != item.PROCESSOR_ID}}
						<p>经办人： {{item.PROCESSOR_ID}}</p> 	
				{{else}}
						<p>经办人： {{item.REQUIRE}}</p>
				{{/if}}
			   <p>主&nbsp;&nbsp;&nbsp;&nbsp;管：{{item.MANAGER}}</p>
			</td>
			<td >
				<p >
					{{item.createTime}}
				</p>
			</td>
			<td >
				<p >
					{{item.realName}}
				</p>
			</td>
			<td >
				<p >
					<i class="sign_blue">{{item.STATUS}}</i>
				</p>
				{{if item.CASE_ORIGIN == 'MERGE'}}
					<p class="tip"> <i class="sign_blue"> 合流 </i> </p>
				{{/if}}
				{{if item.CASE_ORIGIN == 'INPUT'}}
					<p class="tip" style="position:relative;overflow:visible;"> <i class="sign_brown"> 自录 </i> </p>
				{{/if}}
				{{if item.CASE_ORIGIN == 'CTM'}}
					<p class="tip" style="position:relative;overflow:visible;"> <i class="sign_blue"> 导入 </i> </p>
				{{/if}}
				{{if item.CASE_ORIGIN == 'PROCESS'}}
					<p class="tip"> <i class="sign_blue"> 合流申请中 </i>  </p>
				{{/if}}
			</td>
		</tr>
{{/each}}              
</script>
<script>
aist.sortWrapper({ reloadGrid : searchMethod }); 
/**
 * 日期控件
 */
$('#datepicker_0').datepicker({
	format : 'yyyy-mm-dd',
	weekStart : 1,
	autoclose : true,
	todayBtn : 'linked',
	language : 'zh-CN'
});
</script> 
</content>
</body>
</html>
