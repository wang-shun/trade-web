<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<link href="${ctx}/css/transcss/task/myTaskList.css" rel="stylesheet">
<!-- Gritter -->
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">


<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/basic.css" rel="stylesheet">
<link href="${ctx}/css/plugins/dropzone/dropzone.css" rel="stylesheet">


<!-- Morris -->
<link href="${ctx}/css/plugins/morris/morris-0.4.3.min.css"
	rel="stylesheet">
	
    <!-- Data Tables -->
    <link rel="stylesheet" href="../static/css/plugins/dataTables/dataTables.bootstrap.css" />
    <link rel="stylesheet" href="../static/css/plugins/dataTables/dataTables.responsive.css" />
    <link rel="stylesheet" href="../static/css/plugins/dataTables/dataTables.tableTools.min.css" />

    <!-- index_css -->
    <link rel="stylesheet" href="../static/trans/css/common/base.css" />
    <link rel="stylesheet" href="../static/trans/css/common/table.css" />
    <link rel="stylesheet" href="../static/iconfont/iconfont.css" ">
    <link rel="stylesheet" href="../static/trans/css/workflow/myCaseList.css" />
	
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
<input type="hidden" id="taskId">
<div class="row">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="col-lg-12">
			<div class="ibox ">
				<div class="ibox-title">
					<h5>未分配任务列表</h5>
				</div>
				<div class="ibox-content">
					<div class="row form-horizontal">
						<div class="col-md-6">
							<div class="form-group">
								<label class="col-md-2 control-label"></label>
								<div class="control-div">
							       	<select id="inTextType" data-placeholder= "搜索条件设定"
                                        class= "btn btn-white chosen-select" style="float :left;" onchange="intextTypeChange()">
										<option value="0" id="propertyAddr">产证地址</option>
										<option value="1" id="caseCode" selected>案件编号</option>
									</select>
									<input id="inTextVal" type="text" class="form-control pull-left" value="${caseCode}">
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
							    <label class="col-md-2 control-label text-right">
							        <button id="searchButton" class="btn btn-success" style="margin-left: 10px;" type="button"> <i class="icon iconfont"></i> 查询 </button>	
							     </label>
<!-- 							     <label class="col-md-2 control-label"> -->
<!-- 							        <button onclick="javascript:clean()" class="btn btn-primary" type="button"> 清空 </button>	 -->
<!-- 							     </label> -->
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
	</div>
	
</div>
	
<div class="data-wrap">
		<div class="data-wrap-in">
			<table class="table-fenpei" border="0" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th>案件编号</th>
						<th>产证地址</th>
						<th><span class="sort" >服务名称</span></th>
						<th>处理角色</th>
						<th>贵宾服务中心</th>
						<th>组别名称</th>
						<th>开始时间</th>						
						<th><span class="sort" sortColumn="CREATE_TIME" sord="asc">操作</span></th>
					</tr>
				</thead>
				<tbody id="tab_unlocatedTask">
					
				</tbody>
			</table>
		</div>
	</div>
	<div class="text-center">
		<span id="currentTotalPage"><strong class="bold"></strong></span>
		<span class="ml15">共<strong class="bold" id="totalP"></strong>条</span>&nbsp;
		<div id="pageBar" class="pagination my-pagination text-center m0"></div>  
    </div>
		<div id="modal-form" class="modal fade" aria-hidden="true">
			<div class="modal-dialog" style="width: 900px">
				<div class="modal-content">
				 <div class="modal-header">
            <button type="button" class="close"
               data-dismiss="modal" aria-hidden="true">
                 &nbsp; &times;
            </button>
         </div>
					<div class="modal-body">
						<div style="padding-bottom: 3px;">
						<label>员工姓名:</label><input type="text" id="txt_username">&nbsp;&nbsp;<button onclick="loadUser();" class='btn red' >搜索 </button>
						</div>
								<div class="jqGrid_wrapper" >
									<table id="table_list_3"></table>
									<div id="pager_list_3"></div>
								</div>

						</div>
					</div>
				</div>
				<a title="关闭" class="fancybox-item fancybox-close" href="javascript:;"></a>
			</div>
		</div>
	</div>


	<content tag="local_script"> <!-- Peity --> <script
		src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> <!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> <!-- Custom and plugin javascript -->
	<script src="${ctx}/js/trunk/case/unlocatedTask.js?v=1.1"></script>
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script id="template_unlocatedTask" type="text/html">
         {{each rows as item index}}
 				     <tr class="tr-1">
						<td><a href="{{ctx}}/case/caseDetail?caseId={{item.caseId}}" target="_blank"><span class="sort active">{{item.caseCode}}</span></td>
						 <td>
                                <p class="big">
                                <a href="{{ctx}}/case/caseDetail?caseId={{item.caseId}}" target="_blank">
                                     {{item.caseCode}}              
                                </a>
                                </p>
                                <p>
                               <i class="tag_sign">c</i>
                                                
                               </p>
                        </td>
						<td><span class="salesman-info">{{item.propertyAddr}}</span></td>
						<td><span class="case-addr">{{item.taskDfKey}}</span></td>
						<td><span class="salesman-info">{{item.taskJobCode}}</span></td>
						<td><span class="salesman-info">{{item.groupParentName}}</span></td>
						<td><span class="salesman-info">{{item.groupName}}</span></td>
						<td><span class="salesman-info">{{item.createTime}}</span></td>						
						<td>
						 {{if item.candidateId&&item.candidateId!=''}}
						 <button type="button" class="btn fenpei" onclick="doGroupClaim({{item.taskId}})">分配给自己</button>
						 {{else}}
						 <button type="button" class="btn fenpei" onclick="showLocate({{item.taskId}})">分配任务</button>
						 {{/if}}
						</td>
				</tr>
		{{/each}}
	 </script> 
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
		
			$(document)
					.ready(
							function() {
							userGrid=$("#table_list_3").jqGrid(
						{
							datatype : 'json',
							url : "${ctx}"+ "/manage/listUser",
							height : 250,
							autowidth : true,
							shrinkToFit : true,
							rowNum : 5,
							/*   rowList: [10, 20, 30], */
							colNames : [ '编号','姓名', '登录人',
							'所在组织', '操作' ],
							colModel : [
								{
									name : 'employeeCode',
								 	index : 'employeeCode',
									width : 100
								},
								{
									name : 'realName',
									index : 'realName',
									width : 150
								},
								{
									name : 'username',
									index : 'username',
									width : 250
								},
								{
									name : 'orgName',
									index : 'orgName',
									width : 250
								},
								{
									name : 'username',
									index : 'username',
									width : 90,
									formatter : function(cellvalue,options,rawObject) {
										var btn2 = "<button onclick='doLocateTask(\""
											+ rawObject.username
											+ "\")' class='btn red' >分配 </button>";
											return btn2;
										}
								}
								],
								add : true,
								addtext : 'Add',
								pager : "#pager_list_3",
								viewrecords : true,
								pagebuttions : true,
								hidegrid : false,
								recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
								pgtext : " {0} 共 {1} 页",

							});
							reloadGrid();
					});
			
			function reloadGrid(page) {
				var data1=packgeData(page);
				data1.queryId = "queryUnlocatedTask";
				//aist.wrap(data1);
        	    fetchData(data1);
	    	}
			function packgeData(page){
        		var data1 = {};
        		var inTextVal = $('#inTextVal').val();
        		var hVal = $('#inTextVal').attr('hVal');
        		var propertyAddr = "";
        		var caseCode = "";
        		if (inTextVal != null && inTextVal.trim() != "") {
        			var inTextType = $('#inTextType').val();
        			if (inTextType == '0') {
        				propertyAddr = inTextVal.trim();
        			} else if (inTextType == '1') {
        				caseCode = inTextVal.trim();
        			}
        		}       		        		        		
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
        	    return data1;
			}
			function fetchData(p){
				  $.ajax({
		  			  async: false,
		  	          url:ctx+ "/quickGrid/findPage" ,
		  	          method: "post",
		  	          dataType: "json",
		  	          data: p,
		  	          success: function(data){
		  	        	  data.ctx = ctx;
		  	        	  var tsAwardBaseList= template('template_unlocatedTask' , data);
			                  $("#tab_unlocatedTask").empty();
			                  $("#tab_unlocatedTask").html(tsAwardBaseList);
			                  
			                 initpage(data.total,data.pagesize,data.page, data.records);
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
					first:'<i class="icon-step-backward"></i>',
					prev:'<i class="icon-chevron-left"></i>',
					next:'<i class="icon-chevron-right"></i>',
					last:'<i class="icon-step-forward"></i>',
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
						taskDelGrid.trigger('reloadGrid');
					}
				});
			}			
			function showLocate(taskId){
				$("#taskId").val(taskId);
				$('#modal-form').modal("show");
			}
			function doLocateTask(candidateId) {
				var taskId=$("#taskId").val();
				$.ajax({
					url : ctx + "/unlocatedTasks/doLocateTask/"+taskId+"/"+candidateId,
					method : "post",
					dataType : "json",
					success : function(data) {
						if(data.sc&&data.sc=='0'){
							alert('分配成功！');
						}else{
							alert('分配失败！');
						}
						$('#modal-form').modal("hide");
						taskDelGrid.trigger('reloadGrid');
					}
				});
			}

		</script> </content>
</body>
</html>