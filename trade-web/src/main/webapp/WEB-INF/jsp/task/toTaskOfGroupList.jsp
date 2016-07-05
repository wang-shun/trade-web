<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>本组待办任务列表</title>
        <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
        <link href="${ctx}/fonts/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
        <link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
        <!-- IonRangeSlider -->
        <link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
        <link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">
        <link href="${ctx}/css/animate.css" rel="stylesheet">
        <%-- <link href="${ctx}/css/style.min.css" rel="stylesheet">  --%>
        <link href="${ctx}/css/transcss/kpi/bonus.css" rel="stylesheet">
        <!-- 分页控件 -->
        <link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
        <!-- aist列表样式 -->
        <link href="${ctx}/css/common/aist.grid.css" rel="stylesheet">
        <link href="${ctx}/css/style.css" rel="stylesheet">
    </head>
    <body class="pace-done">
    <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
		 <div class="row">
			<div class="col-md-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>本组待办任务列表</h5>
				</div>
				<div class="ibox-content">
					<form method="get" class="form-horizontal">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label class="col-md-2 control-label m-l">案件编号</label>
									<div class="col-md-8">
									    <input type="text" class="form-control" id="caseCode" name="caseCode"/>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group ">
									<label class="col-md-3 control-label m-l">物业地址</label>
									<div class="col-md-8"> 	
										<input type="text" class="form-control" id="propertyAddr" name="propertyAddr">
									</div>
								</div>
							</div>
							<div class="col-md-6"  style="">
							</div>
						</div>
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
									<label class="col-md-4 control-label"></label>
									<div>
									   <button id="searchButton" type="button" class="btn btn-warning">查询</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
   	    <div class="bonus-table">
        </div>
        
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
                                    <td>
										<DIV CLASS="sk-spinner sk-spinner-double-bounce" STYLE="width:18px;height:18px;margin-top:-5px;">
										{{if item.DATELAMP <wrapperData.l1 || item.DATELAMP ==null}}
													
										{{else if item.DATELAMP <wrapperData.l2}}
											<div class="sk-double-bounce1" style="background-color:green"></div>
											<div class="sk-double-bounce2" style="background-color:green"></div>

										{{else if item.DATELAMP <wrapperData.l3}}
											<div class="sk-double-bounce1" style="background-color:yellow"></div>
											<div class="sk-double-bounce2" style="background-color:yellow"></div>

										{{else}}
											<div class="sk-double-bounce1" style="background-color:red"></div>
											<div class="sk-double-bounce2" style="background-color:red"></div>
										{{/if}}
										</DIV>
									</td>
									<td><a href="http://trade.centaline.com:8083/trade-web/case/caseDetail?caseId={{item.caseId}}" class="case-num" target="_blank">{{item.CASE_CODE}}</a></td>
                                    <td>{{item.PROPERTY_ADDR}}</td>
                                    <td>{{item.NAME}}</td>
                                    <td>{{item.ASSIGNEE_STR}}</td>
                                    <td>{{item.EST_PART_TIME}}</td>
                                    <td><button type="button" class="btn btn-xs btn-warning pull-left" onclick="showOptUsers('{{item.ID}}','{{wrapperData.serviceDepId}}','{{item.CASE_CODE}}')">分配</button></td>
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
	    	jQuery(document).ready(function() {
        		//初始化数据
	    		var data = {};  
	    	    data.mOrgId = sDepId;

        	    $(".bonus-table").aistGrid({
        			ctx : "${ctx}",
        			queryId : 'queryUncompleteTask',
        		    templeteId : 'queryTaskOfGroup',
        		    wrapperData :{l1:Lamp1,l2:Lamp2,l3:Lamp3,serviceDepId:sDepId},
        		    data : data,
        		    columns : [
        		               {
        		            	   colName :"红绿灯"
        		               }
        		               ,
        		               {
        		    	           colName :"案件编号",
        		    	           sortColumn : "CASE_CODE",
        		    	           sord: "desc",
        		    	           sortActive : true
        		    	      },{
        		    	           colName :"产证地址"
        		    	      },{
       		    	                colName :"任务名"
    		    	          },{
       		    	                colName :"执行人"
    		    	          },{
		   		    	           colName :"预计执行时间"
				    	      },{
				    	           colName :"操作"
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
        			queryId : 'queryUncompleteTask',
        		    templeteId : 'queryTaskOfGroup',
        		    wrapperData :{l1:Lamp1,l2:Lamp2,l3:Lamp3,serviceDepId:sDepId},
        		    data : data
        	    })
	    	}
			
			function getParams() {
		   		var data = {};
		   		data.mOrgId = sDepId;
        	    data.caseCode = $("#caseCode").val();
        	    data.propertyAddr = $("#propertyAddr").val();
	    	    return data;
			}
			function showOptUsers(taskId,beginOrgId,cc){
				optTaskId=taskId;
				caseCode=cc;
				userSelect({startOrgId:beginOrgId,expandNodeId:beginOrgId,
					nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:taskUserSelectBack});
			}
			function taskUserSelectBack(array){
				if(array && array.length >0&&optTaskId!=''){
					var selectUserId=array[0].userId;
					var selectUserRName=array[0].username;
					if(confirm('是否确定将任务分配给"'+selectUserRName+'"?')){
						var sendData={taskId:optTaskId,userId:selectUserId,caseCode:caseCode};
						changeTaskAssignee(sendData);
					}
				}
			}
			function changeTaskAssignee(sendData){
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