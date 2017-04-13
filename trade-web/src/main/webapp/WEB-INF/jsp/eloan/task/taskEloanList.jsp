<%@page import="com.centaline.trans.workspace.web.SessionUserConstants"%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->

    <!-- stickUp fixed css -->
      <link href="${ctx}/static/trans/css/common/hint.css" rel="stylesheet" />
    <link href="${ctx}/static/css/plugins/stickup/stickup.css" rel="stylesheet">
    <link href="${ctx}/static/trans/css/common/stickDash.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" >
    <link href="${ctx}/static/css/plugins/aist-steps/steps.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/toastr/toastr.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <!-- index_css  -->

    <link href="${ctx}/static/trans/css/eloan/eloan/eloan.css" rel="stylesheet"/>
    <link href="${ctx}/static/trans/css/common/input.css" rel="stylesheet"/>
    <link href="${ctx}/static/trans/css/common/table.css" rel="stylesheet"/>
 
    <!-- 分页控件 -->
    <link href="${ctx}/static/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
	<%
	response.setHeader("Cache-Control","no-store,no-cache,must-revalidate");
	response.setHeader("Pragrma","no-cache");
	response.setDateHeader("Expires",0);
	request.setAttribute("sessionUser", SessionUserConstants.getSesstionUser());
	%>
	
</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="serviceDepHierarchy"
		value="${sessionUser.serviceDepHierarchy }">
	<input type="hidden" id="userId" value="${sessionUser.id }">
	<input type="hidden" id="serviceDepId"
		value="${sessionUser.serviceDepId }">
	<input type="hidden" id="serviceJobCode"
		value="${sessionUser.serviceJobCode }">
		<input type="hidden" id="orgId"
		value="${orgId }">
		
		<input type="hidden" id="orgId"
		value="${sessionUser.serviceCompanyId }">

	<div class="wrapper wrapper-content animated fadeInUp">
		<div class="row">
			<div class="ibox-content border-bottom clearfix space_box">
				<h2 class="title">e+产品</h2>
				<form method="get" class="form_list">
					<%-- 	<div class="form_content">
						<label class="control-label sign_left"> 状态搜索项 </label>
						<aist:dict id="sel_applyStatus" name="applyStatus"
							clazz="select_control sign_right_one" display="select"
							hasEmpty="true" dictType="yu_eplus_status"
							defaultvalue="${loanAgent.applyStatus }" ligerui='none'></aist:dict>
					</div> --%>
					<div class="form_content form_nomargin">
						<label class="control-label sign_left"> 案件信息 </label> <select
							id="sel_caseInfo" style="width: 130px;"
							class="select_control sign_right_one">
							<option value="addr">产证地址</option>
							<option value="custName">客户姓名</option>
						</select> <input class="sign_right input_type ml10" id="txt_caseInfo"
							placeholder="请输入" value="">
					</div>
					<div class="form_content ">
						<label class="control-label sign_left"> 时间搜索项 </label> <select
							id="sel_time" class="select_control" style="width: 130px;">
							<option value="applyTime">申请时间</option>
							<option value="signTime">面签时间</option>
							<option value="releaseTime">放款时间</option>
						</select>
						<div id="datepicker_0"
							class="input-group input-medium date-picker input-daterange sign_right_speciale"
							data-date-format="yyyy-mm-dd">
							<input id="dtBegin_0" name="dtBegin"
								class="form-control data_style" type="text" value=""
								placeholder="起始日期"> <span class="input-group-addon">到</span>
							<input id="dtEnd_0" name="dtEnd" class="form-control data_style"
								type="text" value="" placeholder="结束日期">
						</div>

					</div>

					<div class="form_content btn-sign">
						<div class="add_btn">
							<button type="button" class="btn btn_blue" id="btn_search">
								<i class="icon iconfont"></i> 查询
							</button>
							 <shiro:hasPermission name="TRADE.ELONE.ADD">
							<a type="button" class="btn btn_blue "
								href="${ctx}/eloan/task/eloanApply/process"> <i
								class=" iconfont">&#xe617;</i>&nbsp;新增
							</a>
							</shiro:hasPermission>
						</div>
					</div>
				</form>
			</div>
			<!-- <div class="ibox"> -->
		</div>
		<div class="row white_bg">
			<div class="bonus-table "></div>
		</div>
	</div>
	<!-- main End -->
	<!-- 设置隐藏字段，动态改变 下面form的参数值-->
	<input type="hidden" id="bizCode" />
	<input type="hidden" id="consultantId" />
	<input type="hidden" id="consultantOrgId" />
	<input type="hidden" id="consultantRealName" />
	<input type="hidden" id="managerId" />
	<input type="hidden" id="managerOrgId" />
	<input type="hidden" id="managerRealName" />
	<!-- 责任人变更 -->
	<div id="srv-modal-form" class="modal fade" role="dialog"
		aria-labelledby="srv-modal-title" aria-hidden="true">
		<div class="modal-dialog" style="width: 700px">
			<div class="modal-content">
				<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
			    
				<h4 class="modal-title" id="srv-modal-title">选择交易顾问：</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<form class="form-horizontal">
							<div class="form-group">
								<div class="col-lg-5 checkbox i-checks checkbox-inline">
									<label for="" class="lable-one">
								    	<input type="hidden" id="userId1" name="consultant" >
			        					<input type="text" id="realName1"  style="background-color:#FFFFFF" readonly="readonly" class="form-control" id="txt_proOrgId_gb" onclick="userSelect({startOrgId:$('#consultantOrgId').val(),expandNodeId:$('#consultantOrgId').val(),
										jobCode:'consultant',nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack1})" value="">
										<div class="input-group float_icon organize_icon" style="margin-top: 5px;">
                                     		<i class="icon iconfont">&#xe627;</i>
                                 		</div>
                                    </label>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="modal-header">
 				<h4 class="modal-title" id="srv-modal-title">选择主管：</h4>
 				</div>
				<div class="modal-body">
					<div class="row">
						<form class="form-horizontal">
							<div class="form-group">
								<div class="col-lg-5 checkbox i-checks checkbox-inline">
									<label>
							    		<input type="hidden" id="userId2" name="manager" value=''>
	        							<input type="text" id="realName2"  style="background-color:#FFFFFF" readonly="readonly" class="form-control" id="txt_proOrgId_gb" onclick="userSelect({startOrgId:$('#managerOrgId').val(),expandNodeId:$('#managerOrgId').val(),
										jobCode:'manager',nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack2})" value="">
										<div class="input-group float_icon organize_icon" style="margin-top: 5px;">
                                    		<i class="icon iconfont">&#xe627;</i>
                               			</div>
                               		</label>
								</div>
							</div>
						</form>
					</div>
				</div> 
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" style="background-color: #f8ac59;border-color: #f8ac59;color: #FFFFFF;" onclick="javascript:changeOwner()">提交</button>
					<button type="button" class="btn btn-default"
						data-dismiss="modal">取消</button>
				</div>
				</div>
			</div>
		</div>

	<content tag="local_script"> <!-- Peity --> <script
		src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script> <!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> <!-- Custom and plugin javascript -->
	<script src="${ctx}/js/jquery.blockui.min.js"></script> <script
		src="${ctx}/js/plugins/jquery-ui/jquery-ui.min.js"></script> <script
		src="${ctx}/js/plugins/dropzone/dropzone.js"></script> <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> <script
		src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> <!-- iCheck -->
	<script src="${ctx}/js/plugins/iCheck/icheck.min.js"></script> <script
		src="${ctx}/js/plugins/autocomplete/jquery.autocomplete.js"></script>
	<!-- 分页控件  --> <script
		src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script> <script
		src="${ctx}/js/template.js" type="text/javascript"></script> <script
		src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> <!-- 模板 -->
	<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	<script id="queryMortgageApproveLost" type="text/html">
         {{each rows as item index}}
			<tr>
				<td class="text-center">
				    <p>{{item.loanSrvCode}}</p>
                       {{if item.STATUS=='ABAN'}}
						 <span class="yes_color">作废</span>
					    {{/if}}
				</td>
				<td>
				      {{item.propertyAddress}}
				</td>
				<td>
                           <a class="hint hint-top" data-hint="{{item.phone}}">
                                <p class="bb"> {{item.REAL_NAME}}</p>
                           </a>
				</td>
				<td class="center">
					<p class="big">
					     {{item.custName}}
					</p>
				</td> 
				<td>
					<p>                             
					
					 {{if item.applyTime==undefined}}
						<i class="sign_grey">申</i>
                      {{else}}
						<i class="sign_normal">申</i>
                      {{/if}}
					       {{item.applyTime}}
					</p>
					<p>
					  {{if item.signTime==undefined}}
						<i class="sign_grey">面</i>
                      {{else}}
						<i class="sign_normal">面</i>
                      {{/if}}
					       {{item.signTime}}
					</p>
					<p>
					 {{if item.releaseTime==undefined}}
						<i class="sign_grey">放</i>
                      {{else}}
						<i class="sign_normal">放</i>
                      {{/if}}
					       {{item.releaseTime}}
					</p>
				</td>
				<td class="text-center">
                    {{if  item.applyTime!=undefined &&item.signTime==undefined&&item.releaseTime==undefined}}
					   申请
                      {{/if}}
 					{{if item.signTime!=undefined&&item.releaseTime==undefined}}
					    面签
                      {{/if}}
				     {{if item.releaseTime!=undefined}}
                 
					    放款
                      {{/if}}

				</td>
				<td class="center">       
                             {{item.Applymoney}}万
				         <input name="{{item.pkId}}" type="hidden" value="{{item.releaseTime}}">
				</td>
				<td class="text-center">					
                      <div class="btn-group">
                         <button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown" aria-expanded="false">操作
                             <span class="caret"></span>
                         </button>
                               <ul class="dropdown-menu" role="menu" style="left:-95px;">
                                      <li><a href="${ctx}/eloan/getEloanCaseDetails?pkid={{item.pkId}}">查看</a></li>
                                      <shiro:hasPermission name="TRADE.ELONE.UPDATE">
                                     {{if item.STATUS!='ABAN'}}
                                      <li><a href="${ctx}/eloan/getEloanCaseDetails?pkid={{item.pkId}}&action=update">修改</a></li>
                                      {{/if}}
                                      </shiro:hasPermission>
                                       <shiro:hasPermission name="TRADE.ELONE.DELETE">
                                       {{if item.taskKey =='EloanApply'}}
                                       	<li><a id="link_btn" onclick="deleteItem({{item.pkId}},'delete')">删除</a></li>{{/if}}
                                       {{if item.taskKey !='EloanApply'&& item.applyTime!=undefined &&item.STATUS!='ABAN'}}
                                        <li><a href="${ctx}/eloan/getEloanCaseDetails?pkid={{item.pkId}}&action=invalid">作废</a></li>{{/if}}
                                      </shiro:hasPermission>
                                      <shiro:hasPermission name="TRADE.ELOAN.CASEDETAIL.CHANGEOWNER">
										<li><a href="javascript:showSelectForm({{item.pkId}},'{{item.eloanCode}}');">变更责任人</a></li>
									  </shiro:hasPermission>
                               </ul>
                      </div>
                </td>
			</tr>
			{{/each}}          
	    </script> 
	    <script>
						//初始化数据
						 var  rule=false;
						var serviceJobCode=$("#serviceJobCode").val(); 
						var serviceDepHierarchy=$("#serviceDepHierarchy").val(); 
						if(serviceJobCode=='consultant'){
							rule=true;
						}
						var params = {
							rows : 10,
							page : 1,
							sessionUserId : $("#userId").val(),
							serviceDepId : $("#serviceDepId").val(),
							serviceOrgId : $("#orgId").val(),
							serviceJobCode : serviceJobCode,
							serviceDepHierarchy :serviceDepHierarchy,
							releaseTimeStart:'',
						    releaseTimeEnd:''
						};
						//删除
						function deleteItem(pkid,status){
							window.wxc.confirm("确定要删除这条数据吗？",{"wxcOk":function(){
								$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}});
								
								$.ajax({
									cache:false,
									async:true,
									type:"POST",
									url:ctx+"/eloan/deteleItem",
									dataType:'json',
									data:{pkid:pkid,action:status},
									success:function(data){
											window.wxc.success(data.message);
											initData();//刷新列表
										    $.unblockUI();
									}
								});
							}});
						}
						//查询数据
						$("#btn_search")
								.click(
										function() {
											params.search_applyTimeStart=null;		
											params.search_applyTimeEnd=null;
											params.search_signTimeStart=null;
											params.search_signTimeEnd=null;
											params.releaseTimeStart='';
											params.releaseTimeEnd='';
											params.search_propertyAddr=null;
											params.search_custName=null;
											/* params.search_status = $("#sel_applyStatus").val() */
											var sel_time = $("#sel_time").val();
											if (sel_time == "applyTime") {
												params.search_applyTimeStart = $(
														"input[name='dtBegin']")
														.val();
												params.search_applyTimeEnd = $(
														"input[name='dtEnd']")
														.val();
											} 
											 else if (sel_time == "signTime") {
												    params.search_signTimeStart = $(
															"input[name='dtBegin']")
															.val();
													params.search_signTimeEnd = $(
															"input[name='dtEnd']")
															.val();
										    }
											else if (sel_time == "releaseTime") {
												params.releaseTimeStart = $(
														"input[name='dtBegin']")
														.val();
												params.releaseTimeEnd = $(
														"input[name='dtEnd']")
														.val();
												params.releaseTimeEnd+=" 23:59:59";
											}
											var sel_caseInfo = $(
													"#sel_caseInfo").val();
											if (sel_caseInfo == "addr") {
												params.search_propertyAddr = $(
														"#txt_caseInfo").val();
											} else if (sel_caseInfo == "custName") {
												params.search_custName = $(
														"#txt_caseInfo").val();
											}

											initData();
										})

						// 日期控件
						$('#datepicker_0').datepicker({
							format : 'yyyy-mm-dd',
							weekStart : 1,
							autoclose : true,
							todayBtn : 'linked'
						})
						$("#sel_time").change(function() {
							$("input[name='dtBegin']").val("");
							$("input[name='dtEnd']").val("");
						})

						//加载页面
						function initData() {
							$(".bonus-table")
									.aistGrid(
											{
												ctx : "${ctx}",
												queryId : 'EloanCaseListQuery',
												templeteId : 'queryMortgageApproveLost',
												gridClass : 'table table_blue table-striped table-bordered table-hover',
												data : params,
												columns : [ {
													colName : "产品类型	"
												//sortColumn : "CASE_CODE",
												//sord: "desc",
												//sortActive : true
												}, {
													colName : "产权地址"
												}, {
													colName : "案件归属"
												}, {
													colName : "客户"
												}, {
													 colName :"<span style='color:#ffffff' onclick='caseCodeSort();' >流程时间</span><i id='caseCodeSorti' class='fa fa-sort-desc fa_down'></i>",
						    		    	           sortColumn : "APPLY_TIME",
						    		    	           sord: "desc",
						    		    	           sortActive : true
												}, {
													colName : "状态"
												}, {
													colName : "申请金额"
												}, {
													colName : "操作"
												} ]

											});
						}

						//初始化
						jQuery(document).ready(function() {
							
							var busFlag = "${busFlag}";
							if(busFlag !="" && busFlag != null && busFlag != undefined){
								window.wxc.success("恭喜,新建案件成功,请等待主管分配！");
							}
							initData();
						});
						
						function showSelectForm(id,eloanCode){
							//查询交易顾问和主管相关信息
							$.ajax({
				   	      		url:ctx+"/eloan/selectConsAndManager",
				   	      		method:"post",
				   	      		dataType:"json",
				   	      		data:{pkId:id},   		        				        		    
				   	       		beforeSend:function(){  
				   					$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				   					$(".blockOverlay").css({'z-index':'9998'});
				   	            },
				   		        complete: function() {
				   		                 $.unblockUI(); 
				   		                 if(status=='timeout'){ //超时,status还有success,error等值的情况
				   			          	  Modal.alert(
				   						  {
				   						    msg:"抱歉，系统处理超时。"
				   						  }); 
				   				                } 
				   				            } ,   
				   				success : function(data) {  
				   					        if(data.success){
				   					        	console.log("信息："+JSON.stringify(data.content));
				   					        	$("#consultantId").val(data.content[0].split(",")[0]);
				   					        	$("#userId1").val(data.content[0].split(",")[0]);
				   					        	$("#consultantOrgId").val(data.content[0].split(",")[1]);
				   					        	$("#consultantRealName").val(data.content[0].split(",")[2]);
				   					        	$("#realName1").val(data.content[0].split(",")[2]);
				   					        	$("#managerId").val(data.content[1].split(",")[0]);
				   					        	$("#userId2").val(data.content[1].split(",")[0]);
				   					        	$("#managerOrgId").val(data.content[1].split(",")[1]);
				   					        	$("#managerRealName").val(data.content[1].split(",")[2]);
				   					        	$("#realName2").val(data.content[1].split(",")[2]);
				   					        	$("#bizCode").val(eloanCode);
				   					        	$('#srv-modal-form').modal('show');
				   					        }else{
				   					        	window.wxc.error("操作失败！");
				   					        }		    		
											$.unblockUI();
				   					},		
				   				error : function(errors) {
				   						$.unblockUI();   
				   						window.wxc.error("请求出错！");
				   					}  
				   	       });
						}
						/**
						 * 更新input的值
						 */
						function selectUserBack1(array){
							if(array && array.length >0){
						        $("#realName1").val(array[0].username);
								$("#userId1").val(array[0].userId);
							}else{
						        $("#realName1").val("");
								$("#userId1").val("");
							}
						}
						
						function selectUserBack2(array){
							if(array && array.length >0){
						        $("#realName2").val(array[0].username);
								$("#userId2").val(array[0].userId);
							}else{
						        $("#realName2").val("");
								$("#userId2").val("");
							}
						}
						
						//更改交易顾问和主管方法
						function changeOwner(){
							$('#srv-modal-form').modal('hide');
				   	 		$.ajax({
				   	      		url:ctx+"/eloan/changeOwner",
				   	      		method:"post",
				   	      		dataType:"json",
				   	      		data:{eloanCode:$("#bizCode").val(),oldConsultantId:$("#consultantId").val(),newConsultantId:$("#userId1").val(),oldManagerId:$("#managerId").val(),newManagerId:$("#userId2").val()},   		        				        		    
				   	       		beforeSend:function(){  
				   					$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				   					$(".blockOverlay").css({'z-index':'9998'});
				   	            },
				   		        complete: function() {
				   		                 $.unblockUI(); 
				   		                 if(status=='timeout'){ //超时,status还有success,error等值的情况
				   			          	  Modal.alert(
				   						  {
				   						    msg:"抱歉，系统处理超时。"
				   						  }); 
				   				                } 
				   				            } ,   
				   				success : function(data) {  
				   					        if(data.success){
				   					        	window.wxc.alert("操作成功！");
				   					        }else{
				   					        	window.wxc.error("操作失败！");
				   					        }		    		
											$.unblockUI();
				   					},		
				   				error : function(errors) {
				   						$.unblockUI();   
				   						window.wxc.error("请求出错！");
				   					}  
				   	       });
						}
					</script> 
			</content>
</body>
</html>