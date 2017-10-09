<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>首页</title>
        <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
        <%-- <link href="<c:url value='/fonts/font-awesome/css/font-awesome.min.css' />" rel="stylesheet"/> --%>
        <link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">
        <!-- IonRangeSlider -->
        <link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />" rel="stylesheet">
        <link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />" rel="stylesheet">
        <link href="<c:url value='/css/animate.css' />" rel="stylesheet">
        <%-- <link href="<c:url value='/css/style.min.css' />" rel="stylesheet">  --%>
        <link href="<c:url value='/css/transcss/award/bonus.css' />" rel="stylesheet">
        <!-- Gritter -->
       <!--  <link href="js/plugins/gritter/jquery.gritter.css" rel="stylesheet"> -->
        <!-- 分页控件 -->
        <link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
        <link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">
	<link
	href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css' />"
	rel="stylesheet">
	<style>
		.bonus-m-con .bonus-search{margin-left:15px;}
		.case-num{
text-decoration: underline !important;
}
.case-num:HOVER{
text-decoration: underline !important;
}
.case-num:visited{
 text-decoration: underline !important;
}
.hideDiv{
display: none;}
	</style>
    </head>
    
    <body class="pace-done">
    <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
    <input type="hidden" id="ctx" value="${ctx}" />
    <span>评估结算案件列表</span>
        <div id="wrapper" class="Index">
       			<!-- Main view -->
                <div class="main-bonus">
                    <div class="bonus-wrap">
                        <div class="ibox-content bonus-m-con">
                            <div class="row">
                                <div class="col-lg-5 col-md-5">
                                        <label class="col-lg-3 col-md-3 control-label font_w">评估公司</label>
                                        <div class="col-lg-9 col-md-9">
                                        	<select  id="evalCompany" class="form-control select_control">
                                        	
                                        	</select>
                                            <!-- <input type="text" class="form-control" id="evalCompany" name="evalCompany"> -->
                                            <!-- <aist:dict id="evalCompany" name="evalCompany" tag="myCaseList" display="select" dictType="30003" clazz="select_control sign_right_one_case"/> -->
                                        </div>
                                </div>
                                <div class="col-lg-5 col-md-5">
                                    <div class="form-group">
                                        <label class="col-lg-3 col-md-3 control-label font_w">结算费用</label>
                                        <!--  <div id="datepicker_0" class="input-group sign-right dataleft input-daterange"  data-date-format="yyyy-mm-dd">
											<input id="dtBegin_0" name="dtBegin" class="form-control data_style" style="font-size: 13px; width: 159px; border-radius: 2px;" type="text" value="">  -->
										<input type="text" class="" id="endfee" name="endfee">
										</div>
                                </div>
                                 <div class="col-lg-5 col-md-5">
                                        <label class="col-lg-3 col-md-3 control-label font_w">案件状态</label>
                                        <div class="col-lg-9 col-md-9">
												<select name="" class="form-control" id="caseStatus">
													<option value="" selected="selected">请选择</option>
													<option value="0">已结算</option>
													<option value="未结算">未结算</option>
													<option value="2">已核对</option>
													<option value="未核对">未核对</option>
													<option value="4">已审批</option>
													<option value="5">未审批</option>
													<!--  <option value="6">已驳回</option>-->
												</select>
                                          <!--  <aist:dict id="caseProperty" name="case_property" tag="myCaseList" display="select" dictType="30003" clazz="select_control sign_right_one_case"/> -->
                                        </div>
                                </div>
                                
                                <div class="col-lg-5 col-md-5">
                                        <label class="col-lg-3 col-md-3 control-label font_w">费用调整类型</label>
                                        <div class="col-lg-9 col-md-9">
                                        
												<select name="" class="form-control" id="costUpdateType">
													<option value="" selected="selected">请选择</option>
													<option value="发票税点">发票税点</option>
													<option value="1">退报告</option>
													<option value="爆单">爆单</option>
													<!-- <option value="3">未核对</option>
													<option value="4">已审批</option>
													<option value="5">未审批</option>
													<option value="6">已驳回</option>-->
												</select>
												
                                          <!--  <aist:dict id="caseProperty" name="case_property" tag="myCaseList" display="select" dictType="30003" clazz="select_control sign_right_one_case"/> -->
                                        </div>
                                </div>
                                
                                <div class="col-lg-5 col-md-5">    
                            		 <div class="form-group">
                                        <label class="col-lg-3 col-md-3 control-label font_w">贷款权证</label>
                                        <div class="col-lg-9 col-md-9">
                                       		 <input type="text" class="teamcode form-control" id="loadWarrant" name="ctmNo" value="">
                                         <!--  <aist:dict id="caseProperty" name="case_property" tag="myCaseList" display="select" dictType=" " clazz="select_control sign_right_one_case"/> -->
                                        </div>
                                    </div>
                            	</div>

                            </div>
                          
                        </div>
                    </div>
                    
                 </div>
                 <div class="row m-t-sm">
						<div class="form_content">
							<div class="more_btn">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button id="searchButton" type="button" class="btn btn-success">查询</button>&nbsp;&nbsp;&nbsp;
								
	                            	<shiro:hasPermission name="TRADE.CASE.LIST.EXPORT">  
										<!-- <a data-toggle="modal" class="btn btn-success"  href="javascript:void(0)" id="exportBtn">导出到excel</a> -->
										<a data-toggle="modal" class="btn btn-success" onclick="javascript:exportToExcel()" href="" id="exportBtn">导出到excel</a>
									</shiro:hasPermission>&nbsp;&nbsp;&nbsp;&nbsp;
								<button id="batchappro" onclick="showOptUsers()" type="button" class="btn btn-success" disabled="true">批量审批</button>&nbsp;&nbsp;&nbsp;
								<button id="caseAdd"  onclick="javascript:caseAdd()" type="button" class="btn btn-success" disabled="true">新增结算单</button> &nbsp;&nbsp;&nbsp;
							</div>
						</div>
				 </div>
				  <div class="bonus-table">
                    <form id="form1"> <input type="hidden" id="h_userId" name="userId">
                        <table>
                            <thead>
                                <tr>
                                	<th ><input type="checkbox" id="checkAllNot" class="i-checks"/></th>
                                    <th>案件编号</th>
                                    <th>产权地址</th>
                                    <th>费用调整类型</th>
                                    <th>评估公司</th>
                                     <th>
                                    	<p class="aa">评估申请日期</p>
                                    	<p class="aa">出报告日期</p>
                                    </th>
                                    <th>实收评估费</th>
                                   <!--  <th>返利金额</th> -->
                                    <th>结算费用</th>
                                    <th>贷款权证</th>
                                    <th>驳回原因</th>
                                   <th>状态</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody id="t_body_data_contents">
                                                              
                            </tbody>
                        </table>   </form>                   
                    </div>
		</div>
		<div class="text-center">
			<span id="currentTotalPage"><strong class="bold"></strong></span>
			<span class="ml15">共<strong class="bold" id="totalP"></strong>条</span>&nbsp;
			<div id="pageBar" class="pagination my-pagination text-center m0"></div>  
	    </div>
	    <form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>
				<div class="modal inmodal" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                             <div class="modal-dialog" style="width: 1070px;">
                                 <div class="modal-content animated fadeIn apply_box">
                                 	<%-- <input type="hidden" value="${caseCode}" id="caseCode" /> --%>
                                     <form action="" class="form_list clearfix" style="margin-bottom: 20px;">
                                         <div class="form_tan">
                                         	<button type="button" class="close" data-dismiss="modal"
													aria-hidden="true">×</button>
                                             <label class="control-label">
                                            		     请输入无需结算原因：
                                             </label>
                                             <input class="input_type input_extent" placeholder="请输入" value="" style="width:320px" id="noEndCause"/>
                                         </div>
                                         <div class="form_tan tan_space">
                                             <button type="button" class="btn btn-success" id="noEnd2" onclick="">
                                                 <i class="icon iconfont"></i>&nbsp;确认
                                             </button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                             <!-- <button type="button" class="btn btn-success" id="cancel" onclick="">
                                                 <i class="icon iconfont"></i>&nbsp;取消
                                             </button> -->
                                             <button type="button" class="btn btn-default"
													data-dismiss="modal">关闭</button>
                 						</div>
                                     </form>
                                    <!--  <button type="button" class="close close_blue" data-dismiss="modal"><i class="iconfont icon_rong"> &#xe60a; </i></button> -->
								 </div>
							</div>
				</div>
                  
                
        
        <!-- End page wrapper-->
        <!-- Mainly scripts -->
        <content tag="local_script"> 
        <%--  <script src="<c:url value='/js/bootstrap.min.js' />"></script> --%>
        <script src="<c:url value='/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
        <script src="<c:url value='/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
         <!-- 日期控件 -->
    	<script	src="<c:url value='/js/plugins/dateSelect/dateSelect.js' />"></script>
        <!-- Custom and plugin javascript -->
        <script src="<c:url value='/js/inspinia.js' />"></script>
        <script src="<c:url value='/js/plugins/pace/pace.min.js' />"></script>
        <!-- 弹出框插件 -->
	    <script src="<c:url value='/js/plugins/layer/layer.js' />"></script>
	    <script src="<c:url value='/js/plugins/layer/extend/layer.ext.js' />"></script>
        <!-- 分页控件  -->
        <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
        <script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
        <script src="<c:url value='/js/plugins/autocomplete/jquery.autocomplete.js' />"></script>
        <script	src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
        <script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
		<!-- 评估待结算  -->
        <script	src="<c:url value='/js/trunk/eval/settle/evalWaitEndList.js' />"></script>
        <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
		<script id="evalWaitAccountList" type= "text/html">
                           {{each rows as item index}}
 							  <tr class="border-e7">
				<td class="center">
					<input type="checkbox" name="my_checkbox" class="i-checks" onclick="_checkbox()" value="{{item.caseCode}}" 
					 
					 caseCode="{{item.caseCode}}" />
                    <input id='caseCode' type='hidden' name='case_code' value="{{item.caseCode}}"/>
					<input type='hidden' name='pkId' value="{{item.pkid}}"/>
					<input type='hidden' name='taskIds' value="{{item.ID}}"/>
					
				</td>
                                    <td>{{item.caseCode}}
									{{if item.STATUS=='2'}}
						 				<span class="yes_color">无需结算</span>
					    			{{/if}}
									</td>
                                    <td>{{item.PROPERTY_ADDR}}</td>
									<td>{{item.FEE_CHANGE_REASON}}</td>
                                    <td>{{item.FIN_ORG_ID}}</td>
									<td>
										<p>评：{{item.APPLY_DATE}}</p>
										<p>出：{{item.ISSUE_DATE}}</p>
									</td>
                                    <td>{{item.EVAL_REAL_CHARGES}}</td>
                                    <td>{{item.SETTLE_FEE}}</td>
                                    <td>小张</td>
									<td>{{item.REJECT_CAUSE}}</td>
									
									<td>
									{{if item.STATUS=='0'}}
						 				<span class="yes_color">未提交</span>
					    			{{/if}}
									{{if item.STATUS=='1'}}
						 				<span class="yes_color">未核对</span>
					    			{{/if}}
									{{if item.STATUS=='6'}}
						 				<span class="yes_color">已提交财务审批中</span>
					    			{{/if}}
									</td>
									<td class="center">
									<a href="${ctx}/eval/settle/evalEndUpdate?pkid={{item.pkId}}&&caseCode={{item.caseCode}}" target="_blank">修改</a>
									<a onclick="noEnd({{item.pkId}})" target="_blank">无需结算</a>
                        			</td>
									
                                </tr>
						{{/each}}
	    </script>
	    <script>
	  //无需结算
	    function noEnd(pkid){
	    	$('#myModal').modal("show");
	    	$("#noEnd2").click(function(){
	 	    	//var caseCode=$("#caseCode").val();
	 			var noReason= $('#noEndCause').val();
	 			window.location.href = ctx+"/eval/settle/noEndEvalList?pkid="+pkid+"&settleNotReason="+noReason;
	 	    })
	 	    
	 	   /*  $("#cancel").click(function(){
	 	    	//$("#myModal").css("display","none");
	 			window.location.href = ctx+"/eval/settle/evalWaitEndList";
	 	    }) */
	    }
	  	
	   
	    /* function ckbChange(){
	    	
	    	$("#batchappro").attr("disabled", false);
	    	var parE=$(event.target).closest('td');
	    	if($(event.target).attr('checked')){
	    		parE.find("input[name='taskIds']").attr("disabled",true);
	    		parE.find("input[name='caseCodes']").attr("disabled",true);
	    	}else{
	    		parE.find("input[name='taskIds']").removeAttr("disabled");
	    		parE.find("input[name='caseCodes']").removeAttr("disabled");	
	    	}
	    	
	    }
	    
	    function showOptUsers(taskId,cc){
	    	if(taskId && cc){
	    		optTaskId=taskId;
	    		caseCode=cc;
	    	}else{
	    		var chks=$("input[name='my_checkbox']:checked");
	    		if(chks.length==0){
	    			window.wxc.alert('请至少选择一个任务');
	    			return ;
	    		}
	    	} */
	    
	    /*	if(serviceJobCode == "YCYYZG"){
	    		sDepId = "ff8080814f459a78014f45a73d820006";
	    	}*/
	    	
	    	/* userSelect({startOrgId:sDepId,expandNodeId:sDepId,
	    		nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:taskUserSelectBack});
	    }
	    
	    function taskUserSelectBack(array){
	    	if(array && array.length >0){
	    		var selectUserId=array[0].userId;
	    		var selectUserRName=array[0].username;
	    		//alert(selectUserId+"==="+selectUserRName);
	    		
	    		window.wxc.confirm('是否确定将任务分配给"'+selectUserRName+'"?',{"wxcOk":function(){
	    			$("#h_userId").val(selectUserId);
	    			if(optTaskId){
	    				var sendData={'taskIds[0]':optTaskId,userId:selectUserId,'caseCodes[0]':caseCode};
	    				changeTaskAssignee(sendData);
	    			}else{
	    				changeTaskAssignee();
	    			}
	    		}});
	    	}
	    } */
	    
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
	             /*success : function(data) {
	    			 if(data.success){
	    				window.wxc.success("变更成功",{"wxcOk":function(){
	    					$("#t_body_data_contents").empty();
	    					searchMethod(1);
	    				}});
	    				//reloadGrid();
	    				/*var data = getParams(1);
	    			    aist.wrap(data);
	    				reloadGrid(data);
	    			}else{
	    				window.wxc.error(data.message);
	    			}
	    		},complete: function() { 
	    			 $.unblockUI(); 
	    			 optTaskId='';
	    		},
	    		error : function(errors) {
	    			window.wxc.error("数据保存出错");
	    			 $.unblockUI();
	    		} */
	    	});
	    }
	    //修改
	    /* function update(pkId){
	    	/* $("#taskId").val(taskId);
	    	$('#myModal').modal("show"); 
	    	window.location.href = ctx+"/eval/settle/evalEndUpdate?pkid={{item.pkId}}";
	    } */
	    
	    
	    
	    
	    </script>
	    </content> 
          </body>
         
</html>