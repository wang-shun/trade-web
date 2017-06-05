<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>中台顾问待办任务列表</title>
        <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
        <link href="${ctx}/fonts/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
        <link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
        <!-- IonRangeSlider -->
        <link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
        <link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">
        <link href="${ctx}/css/animate.css" rel="stylesheet">
        <%-- <link href="${ctx}/css/style.min.css" rel="stylesheet">  --%>
        <link href="${ctx}/css/transcss/award/bonus.css" rel="stylesheet">
        <link href="${ctx}/css/transcss/task/myTaskList.css" rel="stylesheet">
        <!-- 分页控件 -->
        <link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
        <!-- aist列表样式 -->
        <link href="${ctx}/css/common/aist.grid.css" rel="stylesheet">
        <link href="${ctx}/css/style.css" rel="stylesheet">

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
.data-wrap-in .bg-assgin{
	background-image: url("../img/ico_assign.png");
}
        </style>
    </head>
    <body class="pace-done">
    <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
		 <div class="row">
			<div class="col-md-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>中台顾问待办任务列表</h5>
				</div>
				<div class="ibox-content">
					<form method="get" class="form-horizontal">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="col-md-2 control-label"></label>
									<div class="control-div">
									       <select id="inTextType" data-placeholder= "搜索条件设定"
		                                        class= "btn btn-white chosen-select" style="float :left;" >
											<option value="1" selected>产证地址</option>
											<option value="0" >客户姓名</option>
											<option value="2">经纪人姓名</option>
											<option value="3">所属分行</option>
											<option value="4">案件编号</option>
											<option value="5">CTM编号</option>
										</select>
								<input id="inTextVal" type="text" class="form-control pull-left">
									</div>
							</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label class="col-md-2 control-label">任务名</label>
									<div class="col-md-4">
										<aist:dict id="taskDfKey" name="taskDfKey"
										clazz="form-control m-b" display="select"
										dictType="part_code" defaultvalue="" />
								
									</div>
								</div>
							</div>
						</div>
						<div class="row">
						<div class="col-md-6">
								<div class="form-group">
							<label class="col-md-2 control-label">&nbsp;</label>
							<div class="col-md-4" >
										<button id="searchButton" type="button"
														class="btn btn-warning pull-left">查询</button>&nbsp;&nbsp;
																				<button onclick="showOptUsers();" type="button"
														class="btn btn-warning ">批量分配</button>
							</div>
							</div>
							</div>
							</div>
					</form>
				</div>
			</div>
		</div>
	</div>
			<form id="form1">
				<input type="hidden" id="h_userId" name="userId">
   	    <div class="bonus-table data-wrap-in">
        </div>
        </form>
        <!-- End page wrapper-->
        <!-- Mainly scripts -->
        <content tag="local_script"> 
        <%--  <script src="${ctx}/js/bootstrap.min.js"></script> --%>
        <script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
        <script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
        <script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
        <!-- Custom and plugin javascript -->
        <script src="${ctx}/js/inspinia.js"></script>
        <script src="${ctx}/js/plugins/pace/pace.min.js"></script>
        <!-- 选择组织控件 -->
        <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
        <!-- 分页控件  -->
        <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
        <script src= "${ctx}/js/template.js" type="text/javascript" ></script>
       	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
       	  <script src="${ctx}/js/plugins/jquery.custom.js"></script>
		<script id="queryTaskOfGroup" type= "text/html">
                           {{each rows as item index}}
                 {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						<td rowspan='2'>
							<input type='checkbox' name='ckb_task' onclick="ckbChange();" style="margin-top: 9px;float: left;">
							<input type='hidden' name='taskIds' value="{{item.ID}}" disabled>
							<input type='hidden' name='caseCodes' value="{{item.CASE_CODE}}" disabled>
						{{if item.DATELAMP < wrapperData.l1|| item.DATELAMP==null}}
    
						{{else if item.DATELAMP < wrapperData.l2}}
                 			<div class="sk-spinner sk-spinner-double-bounce" style="width:18px;height:18px;margin-top:-5px;margin-left: 20px;">
                 			<div class="sk-double-bounce1" style="background-color:green"></div>
                 			<div class="sk-double-bounce2" style="background-color:green"></div>
                 			</div>

				        {{else if item.DATELAMP < wrapperData.l3}}
							<div class="sk-spinner sk-spinner-double-bounce" style="width:18px;height:18px;margin-top:-5px;margin-left: 20px;">
                 			<div class="sk-double-bounce1" style="background-color:yellow"></div>
                 			<div class="sk-double-bounce2" style="background-color:yellow"></div>
                 			</div>
  						{{else}}
                			<div class="sk-spinner sk-spinner-double-bounce" style="width:18px;height:18px;margin-top:-5px;margin-left: 20px;">
                 			<div class="sk-double-bounce1" style="background-color:red"></div>
                 			<div class="sk-double-bounce2" style="background-color:red"></div>
                 			</div>
						{{/if}}
							
						{{if item.RED_LOCK==1}}
							<i class="time-icon" style="margin-left:15px;margin-left: 8px;"></i>
						{{else}}
                          	<i class="time-icon time-off" style="margin-left:15px;margin-left: 8px;"></i>  
						{{/if}}
					</td>
					<td class="t-left"><a href="{{wrapperData.ctx}}/case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank">{{item.CASE_CODE}}</a></td>
					<td class="t-left">
						<a href="{{ctx}}/task/{{item.PART_CODE}}?taskId={{item.ID}}&caseCode={{item.CASE_CODE}}&instCode={{item.INST_CODE}}" class="case-task" target="_blank">{{item.NAME}}</a>
                    </td>
					<td class="t-left">{{item.assignee}}</td>
					<td class="t-left">{{item.CREATE_TIME}}</td>
					<td class="t-center">{{item.EST_PART_TIME}}</td>
				</tr>
				{{if index%2 == 0}}
 				     <tr class="tr-1">
                {{else}}
                     <tr class="tr-2">
                {{/if}}

						<td class="t-left"><span class="ctm-tag">C</span><span class="case-ctm">{{item.CTM_CODE}}</span></td>
						<td class="t-left"><span class="case-addr">{{item.PROPERTY_ADDR}}</span></td>
						<td colspan="2" class="t-left"><i class="salesman-icon"></i><a class="hint  hint-top" data-hint="直管经理: {{item.MANAGER_INFO.realName}}  电话: {{item.MANAGER_INFO.mobile}} ">{{item.AGENT_NAME}}<span class="slash">/</span>{{item.MOBILE}}<span class="slash">/</span>{{item.AGENT_ORG_NAME}}</a></td>
						<td><a onclick="showOptUsers('{{item.ID}}','{{item.CASE_CODE}}')" class="case-deal bg-assgin" target="_blank"></a></td>
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
	    	function ckbChange(){
	    		var parE=$(event.target).closest('td');
	    		if($(event.target).attr('checked')){
	    			parE.find("input[name='taskIds']").attr("disabled",true);
					parE.find("input[name='caseCodes']").attr("disabled",true);
	    		}else{
	    			parE.find("input[name='taskIds']").removeAttr("disabled");
					parE.find("input[name='caseCodes']").removeAttr("disabled");	
	    		}
				
	    	}
	    	jQuery(document).ready(function() {
        		//初始化数据
	    		var data = getParams(1);

        	    $(".bonus-table").aistGrid({
        			ctx : "${ctx}",
        			queryId : 'queryUncompleteZtgwTask',
        		    templeteId : 'queryTaskOfGroup',
        		    wrapperData :{l1:Lamp1,l2:Lamp2,l3:Lamp3,ctx:ctx},
        		    data : data,
        		    columns : [
        		               {
        		            	   colName :"红绿灯",
        		            		   sortColumn : "DATELAMP",
            		    	           sord: "desc",
            		    	           sortActive : true
        		               },
        		               {
        		    	           colName :"案件编号",
    		            		   sortColumn : "tw.CASE_CODE",
        		    	           sord: "desc",
        		    	      },{
        		    	           colName :"当前任务"
        		    	      },{
       		    	                colName :"执行人"
    		    	          },{
		   		    	           colName :"创建时间",
        		            	   sortColumn : "ART.CREATE_TIME_",
            		    	       sord: "desc",
				    	      },{
				    	           colName :"预计执行时间 ",
        		            	   sortColumn : "tp.EST_PART_TIME",
            		    	       sord: "desc",
				    	      }]
        		
        		}); 
        		
        	 	// 查询
     			$('#searchButton').click(function() {
     				reloadGrid();
     			});
	    	});
        	

			function reloadGrid() {
				var data = getParams();
        	    $(".bonus-table").reloadGrid({
        	    	ctx : "${ctx}",
        			queryId : 'queryUncompleteZtgwTask',
        		    templeteId : 'queryTaskOfGroup',
        		    wrapperData :{l1:Lamp1,l2:Lamp2,l3:Lamp3,serviceDepId:sDepId},
        		    data : data
        	    })
	    	}


			function getParams(page) {
		   	

		   		// 客户姓名 物业地址 经纪人
		   		var inTextVal = $('#inTextVal').val();
		   		var hVal = $('#inTextVal').attr('hVal');
		   		var guestName = "";
		   		var agentName = "";
		   		var propertyAddr = "";
		   		var agentOrgName = "";
		   		var taskDfKey=$("#taskDfKey").val();
		   		// caseCode与ctmCode
		   		var caseCode =  "";
		   		var ctmCode = "";
		   		if (inTextVal != null && inTextVal.trim() != "") {
		   			var inTextType = $('#inTextType').val();
		   			if (inTextType == '0') {
		   				guestName = inTextVal.trim();
		   			} else if (inTextType == '1') {
		   				propertyAddr = inTextVal.trim();
		   			} else if (inTextType == '2') {
		   				// 经纪人姓名
		   				agentName = inTextVal.trim();
		   			}else if (inTextType == '3') {
		   				agentOrgName = inTextVal.trim();
		   			}else if (inTextType == '4') {
		   				caseCode = inTextVal.trim();
		   			}else if (inTextType == '5') {
		   				ctmCode = inTextVal.trim();
		   			}
		   		}

		   		if(!page) {
		   			page = 1;
		   		}
		   		var data = {
		   				search_caseCode : caseCode,
		   				search_ctmCode : ctmCode,
		   				argu_guestname : guestName,
		   				search_agentName : agentName,
		   				search_agentOrgName : agentOrgName,
		   				search_propertyAddr : propertyAddr,
		   				search_taskDfKey:taskDfKey,
		   				queryId : "queryUncompleteZtgwTask",
		   				rows : 20,
		   				page : page
		   			};
	    	    return data;
			}
			function showOptUsers(taskId,cc){
				if(taskId && cc){
					optTaskId=taskId;
					caseCode=cc;
				}else{
					var chks=$("input[name='ckb_task']:checked");
					if(chks.length==0){
						alert('请至少选择一个任务');
						return ;
					}
				}
				
				userSelect({startOrgId:sDepId,expandNodeId:sDepId,
					nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:taskUserSelectBack});
			}
			function taskUserSelectBack(array){
				if(array && array.length >0){
					var selectUserId=array[0].userId;
					var selectUserRName=array[0].username;
					if(confirm('是否确定将任务分配给"'+selectUserRName+'"?')){
						$("#h_userId").val(selectUserId);
						if(optTaskId){
							var sendData={'taskIds[0]':optTaskId,userId:selectUserId,'caseCodes[0]':caseCode};
							changeTaskAssignee(sendData);
						}else{
							changeTaskAssignee();
						}
					}
				}
			}
			function changeTaskAssignee(sendData){
				if(!sendData){
					sendData=$('#form1').serialize();
				}
				$.ajax({
					cache : false,
					async : false,//false同步，true异步
					type : "POST",
					url : ctx+"/case/changeTaskAssignee",
					dataType : "json",
					data : sendData,
					beforeSend:function(){  
							$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
							$(".blockOverlay").css({'z-index':'9998'});
			         },
					success : function(data) {
						if(data.success){
							alert("变更成功");
							reloadGrid();
						}else{
							alert(data.message);
						}
					},complete: function() { 
						 $.unblockUI(); 
						 optTaskId='';
					},
					error : function(errors) {
						alert("数据保存出错");
						 $.unblockUI();
					}
				});
			}
			
        	
	    </script>
	    </content> 
        <input type="hidden" id="ctx" value="${ctx}" />

    </body>
</html>