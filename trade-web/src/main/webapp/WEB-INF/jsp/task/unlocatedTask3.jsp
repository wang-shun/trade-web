<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css" rel="stylesheet" />  
<!-- Toastr style -->
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet"><%-- 
<link href="${ctx}/css/transcss/task/myTaskList.css" rel="stylesheet"> --%>
<!-- Gritter -->
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/unlocatedTask2/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/basic.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/dropzone.css" rel="stylesheet">
<!-- Morris -->
<link href="${ctx}/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
<!-- Data Tables -->
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.bootstrap.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet" href="${ctx}/css/plugins/dataTables/dataTables.tableTools.min.css" />

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/css/common/table.css" />
<link rel="stylesheet" href="${ctx}/css/tmpBank/input.css" />
<link rel="stylesheet" href="${ctx}/css/tmpBank/iconfont/iconfont.css">
<link rel="stylesheet" href="${ctx}/css/workflow/myCaseList.css" />
<!-- 必须CSS -->
<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />


<style type="text/css">

#selectDiv {
	width: 480px;
}

#inTextType_chosen {
	margin-left: 40px;
}
.ui-state-hover{
	cursor:pointer;
}
.aline{
text-decoration: underline;
}
.aline:HOVER{
text-decoration: underline !important;
}
.text-center{text-align:center;}
.text-right{text-align:right;}
#inTextVal{width:42%;}
#inTextType_chosen{margin-left:0}
.chosen-container{float:left;margin-right:10px}

.case-num,.case-task { text-decoration: underline !important;}
.case-num:HOVER,case-task:HOVER{
text-decoration: underline !important;
}
.case-num:visited,case-task:visited{
 text-decoration: underline !important;
}
.slash{font-weight:bold !important;}
.table-fenpei th{text-align:center;}
.data-wrap-in .fenpei{padding:0 8px;display:inline-block;height: 30px;line-height: 30px;background-color:#ddd;border-radius: 3px;}


</style>	

</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<input type="hidden" id="taskId">
	 <div class="wrapper wrapper-content animated fadeInRight">
             <div class="ibox-content border-bottom clearfix space_box">
                    <h2 class="title">
                       	 未分配任务列表
                    </h2>
                     <form method="get" class="form-horizontal form_box">
                        <div class="row clearfix">
                            <div class="form_content">
                                <label class="sign_left control-label">
                                   	 案件编号
                                </label>
                                <div class="sign_right teamcode">
                                    <input type="text" class="teamcode form-control" id="caseCode" >
                                </div>
                            </div>
                            <div class="form_content">
                                <label class="sign_left control-label">
                                    CTM编号
                                </label>
                                <div class="sign_right teamcode">
                                    <input type="text" class="teamcode form-control" id="ctmCODE" >
                                </div>
                            </div>
                        </div>
                        <div class="row clearfix">
                            <div id="select_div_1" class="form_content">
                                <label class="sign_left control-label">
                                    	产证地址
                                </label>
                                <div class="sign_right intextval">
                                    <input type="text" class="form-control pull-left" id="propertyAddr">
                                </div>
                            </div>
                        </div>
                        <div class="row clearfix">
                        	 <div class="form_content">
                                <label class="sign_left control-label">
                                    	贵宾服务中心
                                </label>
                                <div class="sign_right teamcode" style="position: relative;">
                                    <input type="text" class="teamcode form-control" id="guestServiceCenter"
                                    onclick="orgSelect({displayId:'oriGrpId',displayName:'radioOrgName', startOrgId:'${serviceDepId}', orgType:'',departmentType:'',departmentHeriarchy:'yucui_team',
										   chkStyle:'radio',callBack:radioYuCuiOrgSelectCallBack})"/>
                                    <input type="hidden" id="groupParentID" />
                                    
                                    <div class="input-group float_icon organize_icon">
                                        <i class="icon iconfont"></i>
                                    </div>
                                </div>
                            </div>
                            
                        	<button id="searchButton" class="btn btn-success" style="margin-left: 10px;" type="button"> <i class="icon iconfont"></i> 查询 </button>
                        </div>
					</form>
				</div>
				<div class="row">
                    <div class="col-md-12">
                        <div class="table_content">
							<table class="table table_blue table-striped table-bordered table-hover ">
								<thead>
									<tr>
										<th ><span class="sort" sortColumn="b.case_Code" sord="desc" onclick="caseCodeSort();" >案件编号</span><i id="caseCodeSorti" class="fa fa-sort-desc fa_down"></i></th>
										<th>流程环节</th>	
										<th>产证地址</th>
										<th>贵宾服务中心</th>
										<th ><span class="sort" sortColumn="createTime" sord="asc" onclick="createTimeSort();" >开始时间</span><i id="createTimeSorti" class="fa fa-sort-asc fa_up"></i></th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody id="tab_unlocatedTask">
									
								</tbody>
							</table>
							<div class="text-center page_box">
								<span id="currentTotalPage"><strong ></strong></span>
								<span class="ml15">共<strong  id="totalP"></strong>条</span>&nbsp;
								<div id="pageBar" class="pagination text-center"></div>  
						    </div>
					</div>
				</div>
			</div>
			
			<div class="modal inmodal" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                                <div class="modal-dialog" style="width: 1070px;">
                                    <div class="modal-content animated fadeIn apply_box">
                                        <form action="" class="form_list clearfix" style="margin-bottom: 20px;">
                                            <div class="form_tan">
                                                <label class="control-label">
                                               		     员工姓名
                                                </label>
                                                <input class="input_type input_extent" placeholder="请输入" value="" id="username">
                                            </div>
                                            <div class="form_tan tan_space">
                                                <button type="button" class="btn btn-success" id="searchUsername">
                                                    <i class="icon iconfont">&#xe635;</i>&nbsp;查询
                                                </button>
                                            </div>
                                        </form>
                                        <button type="button" class="close close_blue" data-dismiss="modal"><i class="iconfont icon_rong"> &#xe60a; </i></button>
                                      <div class="apply_table">
                                        <table class="table table_blue table-striped table-bordered table-hover " id="editable">
                                            <thead>
                                                <tr>
                                                    <th> <span>编号</span><!-- <a href="#"><i class="fa fa-sort-desc fa_down"></i></a> --> </th>
                                                    <th> 姓名 </th>
                                                    <th> 登录人 </th>
                                                    <th> 所在组织 </th>
                                                    <th> 操作 </th>
                                                </tr>
                                            </thead>
                                           <tbody id="taskListf">
										   </tbody>
										</table>
										<div class="text-center page_box">
											<span id="currentTotalPagef"><strong ></strong></span>
											<span class="ml15">共<strong  id="totalPf"></strong>条</span>&nbsp;
											<div id="pageBarf" class="pagination text-center"></div>  
									    </div>
								</div>
							</div>
						</div>
					</div>
			
</div>


<content tag="local_script"> 
<!-- Peity --> 
<script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> <!-- jqGrid -->
<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> <!-- Custom and plugin javascript -->
<script src="${ctx}/js/trunk/case/unlocatedTask3.js?v=1.1"></script><%-- 
<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script> --%>

<!-- 分页控件  -->
<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
<!-- 必须JS -->
<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>

<script id="template_unlocatedTask" type="text/html">
         {{each rows as item index}}
 				     <tr class="tr-1">
						<td>
                                <p class="big">
                                <a href="{{ctx}}/case/caseDetail?caseId={{item.caseId}}" target="_blank">
                                     {{item.caseCode}}              
                                </a>
                                </p>
                                <p>
                               <i class="tag_sign">c</i>
                                     {{item.CTM_CODE}}                
                               </p>
                        </td>
						<td class="center">
                                <p class="big">
                                <i class="sign_blue">
                                    {{item.taskDfKey}}           
                                </i>
                                </p>
                                <p>
                                    {{item.WFE_NAME}}                
                               </p>
                        </td>
						<td>


{{if item.propertyAddr != null && item.propertyAddr!="" && item.propertyAddr.length>24}}
<p class="demo-top" title="{{item.propertyAddr}}">
{{item.propertyAddr.substring(item.propertyAddr.length-24,item.propertyAddr.length)}}
{{else}}</p><p>
{{item.propertyAddr}}
{{/if}}					 
						</p>
 							<p >
								 <i class="salesman-icon"> </i>
								 <a class="demo-top" title="{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.GRP_NAME}}" >
{{if item.GRP_NAME !="" && item.GRP_NAME !=null && item.GRP_NAME.length>11}}		
{{if item.AGENT_NAME !=null && item.AGENT_NAME.length > 2}}			
{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.GRP_NAME.substring(0,10)}}...
{{else}}
{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.GRP_NAME.substring(0,11)}}...
{{/if}}					
{{else}}
{{item.AGENT_NAME}}/{{item.AGENT_PHONE}}/{{item.GRP_NAME}}
{{/if}}	
								 </a>
							</p>

                        </td>
						<td>
                                <p class="big">
                                     {{item.groupParentName}}            
                                </p>
                                <p>
                                    {{item.taskJobCode}}     
                               </p>
                        </td>
						<td>
                               <p class="smll_sign">
                                       	<i class="sign_normal">创</i>
                                     		{{item.createTime}}    
										</i>      
                                </p>
                                
                        </td>
						<td class="center">
								{{if item.candidateId&&item.candidateId!=''}}
						 			<button type="button" class="btn btn-success" onclick="doGroupClaim({{item.taskId}})">分配给自己</button>
								{{else}}
						 			<button type="button" class="btn btn-success" onclick="showLocate({{item.taskId}})">分配任务</button>
						 		{{/if}}
                        </td>
					</tr>
		{{/each}}
</script> 

<script id="template_taskListf" type="text/html">
         {{each rows as item index}}
 				     <tr class="tr-1">
						<td>
                                     {{item.employeeCode}}              
                        </td>
						<td>
                                     {{item.realName}}              
                        </td>
						<td>
                                     {{item.username}}              
                        </td>
						<td class="center">
                                     {{item.orgName}}              
                        </td>
						<td>
                               <button type="button" class="btn btn-success" onclick="doLocateTask('{{item.username}}')" > 分配  </button>
                        </td>
						
					</tr>
		{{/each}}
</script> 
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
<script src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script> 
<script src="${ctx}/js/plugins/dropzone/dropzone.js"></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
<script src= "${ctx}/js/template.js" type="text/javascript" ></script>

<script>
var ctx="${ctx}";
var taskDelGrid,userGrid;
function loadUser(){
	var userQuery={username : $("#txt_username").val()};
       		$('#table_list_3').jqGrid('setGridParam',{
       			datatype:'json',  
       			mtype : 'POST',
       			postData: userQuery
       		}).trigger('reloadGrid');
}


$(document).ready(function() {
	 /*加载排序查询组件*/
	aist.sortWrapper({
		reloadGrid : reloadGrid
	});
	reloadGrid();
	//
	changeTaskAssignee(1);
});

function reloadGrid(page) {
	var data1=packgeData(page);
	data1.queryId = "queryUnlocatedTask";
	aist.wrap(data1);
	
	var sortcolumn=$('span.active').attr("sortColumn");
	var sortgz=$('span.active').attr("sord");
	data1.sidx=sortcolumn;
	data1.sord=sortgz;
	
    fetchData(data1);
  	}
function packgeData(page){
     		var data1 = {};
     		var hVal = $('#inTextVal').attr('hVal');
     		var propertyAddr = $.trim($('#propertyAddr').val());
     		var caseCode = $.trim($('#caseCode').val());
     		var ctmCODE = $.trim($('#ctmCODE').val());
     		var groupParentID = $("#groupParentID").val();
     		
     	    data1.rows = 5;
     	     if(!page) {
     	    	 data1.page = 1;
     	    } else {
     	    	data1.page = page;
     	    }   
     	     
     	    data1.argu_candidateId = "${candidateId}";
     	    data1.argu_mOrgId="${orgId}";
     	    data1.argu_managerFlag="${managerFlag}";
     	    data1.argu_caseCode=caseCode;
     	    data1.argu_propertyAddr=propertyAddr;
     	    data1.argu_ctmCODE=ctmCODE;
     	    data1.groupParentID = groupParentID;
     	    
     	    return data1;
}
function fetchData(p){
	  $.ajax({
 			  async: false,
 	          url:ctx+ "/quickGrid/findPage" ,
 	          method: "post",
 	          dataType: "json",
 	          data: p,
			  beforeSend:function(){  				
					$.blockUI({
						message : $("#salesLoading"),
						css : {
							'border' : 'none',
							'z-index' : '9999'
						}
					});
					$(".blockOverlay").css({
						'z-index' : '9998'
					});
		       },
 	          success: function(data){
 	        	  //console.log("数据"+JSON.stringify(data));
 	        	  data.ctx = ctx;
 	        	  var tsAwardBaseList= template('template_unlocatedTask' , data);
                  $("#tab_unlocatedTask").empty();
                  $("#tab_unlocatedTask").html(tsAwardBaseList);
                  
                 initpage(data.total,data.pagesize,data.page, data.records);
                 $('.demo-left').poshytip({
         			className: 'tip-twitter',
         			showTimeout: 1,
         			alignTo: 'target',
         			alignX: 'left',
         			alignY: 'center',
         			offsetX: 8,
         			offsetY: 5,
         		});

         		//top
         		$('.demo-top').poshytip({
         			className: 'tip-twitter',
         			showTimeout: 1,
         			alignTo: 'target',
         			alignX: 'center',
         			alignY: 'top',
         			offsetX: 8,
         			offsetY: 5,
         		});
         		 $.unblockUI();
 	          }
 	     });
} 
function initpage(totalCount,pageSize,currentPage,records)
{
	if(totalCount>1500){
		totalCount = 1500;
	}
	var currentTotalstrong=$('#currentTotalPage').find('strong');
	if (totalCount<1 || pageSize<1 || currentPage<1)
	{
		$(currentTotalstrong).empty();
		$('#totalP').text(0);
		$("#pageBar").empty();
		return;
	}
	$(currentTotalstrong).empty();
	$(currentTotalstrong).text(currentPage+'/'+totalCount);
	$('#totalP').text(records);
	
	$("#pageBar").twbsPagination({
		totalPages:totalCount,
		visiblePages:9,
		startPage:currentPage,
		first:'<i class="fa fa-step-backward"></i>',
		prev:'<i class="fa fa-chevron-left"></i>',
		next:'<i class="fa fa-chevron-right"></i>',
		last:'<i class="fa fa-step-forward"></i>',
		showGoto:true,
		onPageClick: function (event, page) {
			 //console.log(page);
			reloadGrid(page);
	    }
	});
}
function doGroupClaim(taskId){
	$.ajax({
		url : ctx + "/unlocatedTasks/doGroupClaim/"+taskId,
		method : "post",
		dataType : "json",
		success : function(data) {
			if(data.sc&&data.sc=='0'){
				alert('分配成功！');
				reloadGrid(1);
			}else{
				alert('分配失败！');
			}
			//taskDelGrid.trigger('reloadGrid');
			reloadGrid(1);
		}
	});
}			
function showLocate(taskId){
		$("#taskId").val(taskId);
		$('#myModal').modal("show");
}

</script> 
</content>				
</body>
</html>