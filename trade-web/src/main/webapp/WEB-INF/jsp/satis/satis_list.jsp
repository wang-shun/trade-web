<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>案件回访</title>
<link href="<c:url value='/static/css/bootstrap.min.css' />" rel="stylesheet" />
<link href="<c:url value='/static/font-awesome/css/font-awesome.css' />" rel="stylesheet" />
<link href="<c:url value='/static/css/animate.css' />" rel="stylesheet" />
<link href="<c:url value='/static/css/style.css' />" rel="stylesheet" />
<!-- Data Tables -->
<link href="<c:url value='/static/css/plugins/dataTables/dataTables.bootstrap.css' />" rel="stylesheet" />
<link href="<c:url value='/static/css/plugins/dataTables/dataTables.responsive.css' />" rel="stylesheet" />
<link href="<c:url value='/static/css/plugins/dataTables/dataTables.tableTools.min.css' />" rel="stylesheet" />
<!-- index_css -->
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/base.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />" />
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />" />
<link rel="stylesheet" href="<c:url value='/css/satis/casevist.css' />" />
<!-- 分页控件 -->
<link href="<c:url value='/static/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet" />
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<div id="wrapper">
		<!--*********************** HTML_main*********************** -->
		<div class="wrapper wrapper-content animated fadeInRight">
			<div class="ibox-content border-bottom clearfix space_box">
				<h2 class="title">案件回访</h2>
				<form method="get" class="form_list">
					<input type="hidden" id="sessionUserId" name="sessionUserId" value="${sessionUserId}" />
					<input type="hidden" id="serviceDepId" name="serviceDepId" value="${serviceDepId}" />
					<input type="hidden" id="serviceJobCode" name="serviceJobCode" value="${serviceJobCode}" />
					<!-- 保存分单的客服专员ID -->
					<input type="hidden" id="callerId" name="callerId">
					<!-- 保存选定案件编号 -->
					<input type="hidden" id="caseCodes" name="caseCodes">
					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small"> 案件编号 </label> 
							<input name="caseCode" class="teamcode input_type" placeholder="请输入" value="" />
						</div>
						<div class="form_content">
							<label class="control-label sign_left_small"> 产证地址 </label> 
							<input name="prAddress" class="teamcode input_type" style="width: 435px;" placeholder="请输入" value="" />
						</div>
					</div>
					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small"> 签约回访时间 </label> 
							<input name="surveySignTimeStart" class="teamcode input_type date-picker" style="width: 106px;" placeholder="起始时间" value="" readonly="readonly" />到
							<input name="surveySignTimeEnd" class="teamcode input_type date-picker" style="width: 106px;" placeholder="结束时间" value="" readonly="readonly" />
						</div>
						<div class="form_content">
							<label class="control-label sign_left_small"> 过户回访时间 </label> 
							<input name="surveyGuohuTimeStart" class="teamcode input_type date-picker" style="width: 106px;" placeholder="起始时间" value="" readonly="readonly" />到
							<input name="surveyGuohuTimeEnd" class="teamcode input_type date-picker" style="width: 106px;" placeholder="结束时间" value="" readonly="readonly" />
						</div>
					</div>
					<div class="line">
						<div class="form_content">
							<label class="control-label sign_left_small"> 客服 </label> 
							<input type="hidden" id="userId" value=''> 
							<input type="text" id="realName" readonly="readonly" class="teamcode input_type"
								onclick="userSelect({startOrgId:'${kefuOrgId}',expandNodeId:'${kefuOrgId}',
									jobCode:'JYUKFZY',nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack})"
								placeholder="请输入" value=''>
							<div class="input-group float_icon organize_icon">
								<i class="icon iconfont">&#xe627;</i>
							</div>

						</div>
						<div class="form_content mr10">
							<label class="control-label sign_left_small"> 回访状态 </label> 
							<select name="status" class="select_control sign_right_one">
								<option value="">请选择</option>
								<option value="1">未分单</option>
								<option value="2">签约回访</option>
								<option value="3">签约打回</option>
								<option value="4">待过户</option>
								<option value="5">过户回访</option>
								<option value="6">过户打回</option>
								<option value="7">已回访</option>
							</select>
						</div>
						<div class="add_btn">
							<button id="btn_search" type="button" class="btn btn-success mr5">
								<i class="icon iconfont">&#xe635;</i> 查询
							</button>
							<shiro:hasPermission name="TRADE.SURVEY.LIST.DISPATCH">
								<a href="javascript:dispatch();"><button type="button"
										class="btn btn-success mr5">派单</button></a>
 								<a href="javascript:exportToExcel();"><button type="button"
										class="btn btn-success mr5">导出</button></a>		
							</shiro:hasPermission>		
							<button type="button" onclick="clearForm()"
								class="btn btn-grey mr5">清空</button>
						</div>
					</div>
				</form>
			</div>
			<!-- 列表DIV -->
			<div class="row">
				<div class="col-md-12">
					<div class="table_content">
						<div class="bonus-table"></div>
					</div>
				</div>
			</div>
		</div>
		<!--*********************** HTML_main*********************** -->
	</div>

	<content tag="local_script"> 
	<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script> 
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script> 
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script> 
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/static/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
	<script id="template_satisList" type="text/html">
         {{each rows as item index}}
                <tr>
				<shiro:hasPermission name="TRADE.SURVEY.LIST.DISPATCH">					
				{{if item.STATUS == 1}}
                   <td>
					 <input type="checkbox" class="i-checks" name="checkRow" value="{{item.CASE_CODE}}">
				   </td>
				{{else}}
                   <td>
					 <input type="checkbox" class="i-checks" name="checkRow" value="{{item.CASE_CODE}}" disabled="disabled">
				   </td>
				{{/if}}
				</shiro:hasPermission>
				   <td>
					 <p>
					{{if item.STATUS == 1}}
                       <i class="color_visited grey_visited">
                                                    待分单
                       </i>
					{{/if}}
					{{if item.STATUS == 2}}
						<a href="${ctx}/satis/task/signDetail?satisId={{item.PKID}}&urlType=list&readOnly=true" target="_blank">
						<i class="color_visited blue_visited">
                                                    签约回访
                       </i>
					{{/if}}
					{{if item.STATUS == 3}}
						<a href="${ctx}/satis/task/signDetail?satisId={{item.PKID}}&urlType=list&readOnly=true" target="_blank">
						<i class="color_visited red_visited">
					  签约打回
                       </i>
					{{/if}}
					{{if item.STATUS == 4}}
						<a href="${ctx}/satis/task/signDetail?satisId={{item.PKID}}&urlType=list&readOnly=true" target="_blank">
						<i class="color_visited blue_visited">
                                                    待过户                              
                       </i>
					{{/if}}
					{{if item.STATUS == 5}}
						<a href="${ctx}/satis/task/guohuDetail?satisId={{item.PKID}}&urlType=list&readOnly=true" target="_blank">
						<i class="color_visited blue_visited">
                                                    过户回访                              
                       </i>
					{{/if}}
					{{if item.STATUS == 6}}
						<a href="${ctx}/satis/task/guohuDetail?satisId={{item.PKID}}&urlType=list&readOnly=true" target="_blank">
						<i class="color_visited red_visited">
                                                    过户打回
                       </i>
					{{/if}}
					{{if item.STATUS == 7}}
						<a href="${ctx}/satis/task/guohuDetail?satisId={{item.PKID}}&urlType=list&readOnly=true" target="_blank">
						<i class="color_visited grey_visited">
                                                    已回访
                       </i>
					{{/if}}
                     </p>
				   </td>
                   <td>
                      <p class="big">
                        <a href="${ctx}/case/caseDetail?caseId={{item.CASE_ID}}" target="_blank">
							{{item.CASE_CODE}}
						</a>
                      </p>
                      <p>
                        <i class="tag_sign">c</i>
                            {{item.CTM_CODE}}
                      </p>
                   </td>
                   <td>
                      <p class="big">
                       	{{item.PROPERTY_ADDR}}
					  </p>
					  <span >
						<i class="salesman-icon"></i>
 						<a class="hint hint-top" >{{item.AGENT_NAME}}<span class="slash">/</span>{{item.AGENT_PHONE}}<span class="slash">/</span>{{item.C_ORG_NAME}}</a>						 
					  </span>
                    </td>
                  	<td>
                    <p class="smll_sign">                          
					 {{if item.SIGN_TIME==undefined}}
						<i class="sign_grey">签</i>
                      {{else}}
						<i class="sign_normal">签</i>
                      {{/if}}
					       {{item.SIGN_TIME}}
					</p>
					<p class="smll_sign">
					  {{if item.GUOHU_TIME==undefined}}
						<i class="sign_grey">过</i>
                      {{else}}
						<i class="sign_normal">过</i>
                      {{/if}}
					       {{item.GUOHU_TIME}}
					</p>
					<!--<p class="smll_sign">
					 {{if item.CLOSE_TIME==undefined}}
						<i class="sign_grey">完</i>
                      {{else}}
						<i class="sign_normal">完</i>
                      {{/if}}
					       {{item.CLOSE_TIME}}
					</p>-->
                    </td>
                    <td class="center">
                        <span class="manager">
							<a href="#"><em>{{item.WZ_NAME}}</em></a>
                        </span>
                    	<span class="manager">
                            <a href="#"><em>{{item.C_ORG_NAME}}</em></a>
                        </span>
                     </td>
					<shiro:hasPermission name="TRADE.SURVEY.LIST.DISPATCH">
                     	<td class="text-center"> 
                        	{{item.CALLER_NAME}}
                     	</td>
					</shiro:hasPermission>
            </tr>
			{{/each}}          
	    	</script> 
	    	<script type="text/javascript">
							jQuery(document).ready(function() {
								initData();
							});

							function dispatch() {
								var caseCodes = '';
								
								var length_ = $("input[name='checkRow']:enabled:checked").length;
								$("input[name='checkRow']:enabled:checked").each(function(i,e){
									caseCodes += $(e).val()+",";
									if(i == (length_-1)) caseCodes = caseCodes.substring(0,$(e).val().length-1);
								})
								
								$("#caseCodes").val(caseCodes);
								
								if($.trim(caseCodes) == ''){
									window.wxc.error("请选择案件！");
									return false;
								}
								
								userSelect({startOrgId:'${kefuOrgId}',expandNodeId:'${kefuOrgId}',
									jobCode:'JYUKFZY',nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectCallerBack});

							}
							
							function exportToExcel(){
								var params = {
										page : 1,
										sessionUserId : $("#sessionUserId").val(),
										serviceDepId : $("#serviceDepId").val(),
										serviceJobCode : $("#serviceJobCode").val(),
								}
								params.caseCode=$("input[name='caseCode']").val().trim();
								params.prAddress=$("input[name='prAddress']").val().trim();
								params.surveySignTimeStart = $("input[name='surveySignTimeStart']").val();
								params.surveySignTimeEnd = $("input[name='surveySignTimeEnd']").val();
								params.surveyGuohuTimeStart = $("input[name='surveyGuohuTimeStart']").val();
								params.surveyGuohuTimeEnd = $("input[name='surveyGuohuTimeEnd']").val();
								params.callerId=$("#userId").val();
								params.status=$("select[name='status']").val();
								
								aist.exportExcel({
									ctx : '${ctx}',
									queryId : 'SatisListQueryForExport',
									colomns : ['STATUS_CN', 'CASE_CODE', 'PROPERTY_ADDR','SIGN_TIME', 'GUOHU_TIME','C_ORG_NAME', 'WZ_NAME','CALLER_NAME'],
									data : params
								});
							}

							//清空数据
							function clearForm() {
								$(".input_type,#userId").val("");
								$(".select_control option:first").prop("selected", true);
							}

							//初始化数据
							var params = {
								page : 1,
								sessionUserId : $("#sessionUserId").val(),
								serviceDepId : $("#serviceDepId").val(),
								serviceJobCode : $("#serviceJobCode").val(),
							}
							
							//查询
							$("#btn_search").click(function() {
								params.caseCode = $("input[name='caseCode']").val().trim();
								params.prAddress = $("input[name='prAddress']").val().trim();
								params.surveySignTimeStart = $("input[name='surveySignTimeStart']").val();
								params.surveySignTimeEnd = $("input[name='surveySignTimeEnd']").val();
								params.surveyGuohuTimeStart = $("input[name='surveyGuohuTimeStart']").val();
								params.surveyGuohuTimeEnd = $("input[name='surveyGuohuTimeEnd']").val();
								params.callerId = $("#userId").val();
								params.status = $("select[name='status']").val();

								initData();
							})

							//加载页面
							function initData() {
								var columns = [
												{
													colName : "回访状态"
												},
												{
													colName : "<span style='color:#ffffff' onclick='caseCodeSort();' >案件编号</span><i id='caseCodeSorti' class='fa fa-sort-desc fa_down'></i>",
													sortColumn : "CASE_CODE",
													sord : "desc",
													sortActive : false
												},
												{
													colName : "产证地址"
												},
												{
													colName : "签建时间"
												}, {
													colName : "归属组"
												}];
								<shiro:hasPermission name="TRADE.SURVEY.LIST.DISPATCH">
									columns.unshift({colName : "<input type=\"checkbox\" name=\"split\" onclick=\"javascript:toggleSelectAll(this.checked);\">"});
									columns.push({colName : "客服"});
								</shiro:hasPermission>
								$(".bonus-table").aistGrid(
								{
									ctx : "${ctx}",
									queryId : 'SatisListQuery',
									rows : '30',
									templeteId : 'template_satisList',
									gridClass : 'table table_blue table-striped table-bordered table-hover ',
									data : params,
									wrapperData : {
										job : $("#serviceJobCode").val()
									},
									columns : columns
								});
							}				
							/**
							 * 更新input的值
							 */
							function selectUserBack(array) {
								if (array && array.length > 0) {
									$("#realName").val(array[0].username);
									$("#userId").val(array[0].userId);
								} else {
									$("#realName").val("");
									$("#userId").val("");
								}
							}
							
							function selectCallerBack(array){
								if (array && array.length > 0) {
									$("#callerId").val(array[0].userId);
								} else {
									$("#callerId").val("");
								}
								
								var callerId = $("#callerId").val();
 								if($.trim(callerId) == ''){
									window.wxc.error("请选择客服专员！");
									return false;
								} 	
									
								$.ajax({
									url:"${ctx}/satis/doDispatch",
									method:"post",
									dataType:"json",
									data:{caseCodes:$("#caseCodes").val(),userId:$("#callerId").val()},
									beforeSend:function(){  
										$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
										$(".blockOverlay").css({'z-index':'9998'});
							        },
									success:function(data){
										 $.unblockUI();
										 if(data.success){
											 window.wxc.alert("派单成功！",{"wxcOk":function(){
												 initData();
											 	}
											 });
										 }else{
											 window.wxc.error("派单失败！\n"+data.message);
										 } 
									 }
								})
							}
							
							/**
							 * 全选/反选
							 */
							function toggleSelectAll(b){
								$("input[name='checkRow']:enabled").prop("checked", b);
							}
							
							//日期控件
							$('.date-picker').datepicker({
					        	format : 'yyyy-mm-dd',
					        	weekStart : 1,
					        	autoclose : true,
					        	todayBtn : 'linked',
					        	language : 'zh-CN'
					        })
						</script> 
					</content>
</body>
</html>
