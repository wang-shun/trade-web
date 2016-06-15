<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>

<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- jdGrid相关 -->
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">
<!-- 上传相关 -->
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="${ctx}/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<!-- bank  select -->
<link href="${ctx}/css/plugins/chosen/chosen.css" rel="stylesheet">
<script type="text/javascript">
	var ctx = "${ctx}";
	var coworkService = "${firstFollow.coworkService }";
	var teamProperty = "${teamProperty}";
	var caseProperty = "${firstFollow.caseProperty}";
	var cooperationUser = "${firstFollow.cooperationUser}";
	/**记录附件div变化，%2=0时执行自动上传并清零*/
	var index=0;
	var taskitem = "${taskitem}";
	var caseCode = "${caseCode}";
	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
	if("${idList}" != "") {
		var idList = eval("("+"${idList}"+")");
	} else {
		var idList = [];
	}
</script>
<style type="text/css">
.radio.radio-inline > label{
	margin-left:10px;}
.radio.radio-inline > input{
	margin-left:10px;}
.checkbox.checkbox-inline > div{
	margin-left:25px;}
.checkbox.checkbox-inline > input{
	margin-left:20px;}
.product-type span{margin:0 5px 5px 0}
.product-type .selected,.product-type span:hover{border-color:#f8ac59}
.ibox-content-task{padding-bottom:40px !important;}
#corss_area{padding:0 8px 0 0;}
#corss_area select{float:right;height:34px;border-radius:2px;margin-left:20px;}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	<div class="">
		<div class="row wrapper border-bottom white-bg page-heading">
	         <div class="col-md-10">
	             <h2>首次跟进</h2>
	             <ol class="breadcrumb">
					<li><a href="${ctx }/case/myCaseList">在途单列表</a></li>
					<li><a href="${ctx }/task/caseDetail?&caseCode=${caseCode}">案件视图</a></li>
	             </ol>
	         </div>
 		</div>	
		<div class="ibox-title">
			<h5>填写任务信息</h5>
			<div class="ibox-content ibox-content-task">
				<form method="get" class="form-horizontal" id="firstFollowform">
					
					<%--环节编码 --%>
					<input type="hidden" id="partCode" name="partCode" value="${taskitem}">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
					<!-- 流程引擎需要字段 -->
					<input type="hidden" id="taskId" name="taskId" value="${taskId }">
					<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
					<%-- 原有数据对应id --%>
					<input type="hidden" id="caseId" name="caseId" value="${firstFollow.caseId }">
					<input type="hidden" id="tTLId" name="tTLId" value="${firstFollow.tTLId }">
					<input type="hidden" id="signId" name="signId" value="${firstFollow.signId}">
					<input type="hidden" id="propertyInfoId" name="propertyInfoId" value="${firstFollow.propertyInfoId}">
					<%-- 设置审批类型 --%>
					<input type="hidden" id="approveType" name="approveType" value="${approveType }">
					<input type="hidden" id="operator" name="operator" value="${operator }">
					<%-- T_TO_FIRST_FOLLOW 表  --%>
					<input type="hidden" id="firstfollowId" name="firstfollowId" value="${firstFollow.firstfollowId}" />
					<%--自办服务 --%>
					<input type="hidden" id="zbkServices" name="zbkServices" value="3000401001" />
					
					
					<div class="row">
						<div class="col-xs-12 col-md-7">
							<div class="form-group">
								<label class="col-md-2 control-label">案件标记</label>
								<div class="col-md-4">
									<div class="radio i-checks radio-inline">
										<c:choose>
											<c:when test="${firstFollow.caseProperty == '30003001'}">
												<label> <input type="radio" value="30003003"
													id="optionsRadios1" name="caseProperty">有效
												</label>
												<label> <input type="radio" checked="checked"
													value="30003001" id="optionsRadios2" name="caseProperty">无效
												</label>
											</c:when>
											<c:otherwise>
												<label> <input type="radio" checked="checked"
													value="30003003" id="optionsRadios1" name="caseProperty">有效
												</label>
												<label> <input type="radio" value="30003001"
													id="optionsRadios2" name="caseProperty">无效
												</label>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								
							</div>
						</div>
						<div class="col-xs-12 col-md-5">
							<div class="form-group" id="data_1" name="isYouXiao">
									<label class="col-md-4 control-label"><font color="red">*</font>预计签约时间</label>
									<div class="col-md-8">
										<div class="input-group date">
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span> <input type="text"
												class="form-control" id="realConTime" name="realConTime"
												onfocus="this.blur()"
												value="<fmt:formatDate  value='${firstFollow.realConTime}' type='both' pattern='yyyy-MM-dd'/>">
										</div>
									</div>
								</div>
						</div>
					</div>
					<div class="row">
					<div class="col-md-7">
					<div class="from-group" name="isShiXiao" style="display: none;">
						<label class="col-md-2 control-label"><font color="red">*</font>无效原因</label>
						<div class="col-md-10 input-group">
							<input type="text" class="form-control" id="invalid_reason" name="invalid_reason" 
								value="${houseTransfer.invalid_reason}">
						</div>
					</div>
					</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-md-7">
							<div class="form-group"  name="isYouXiao">
						<label class="col-md-2 control-label"><font color="red">*</font>价格</label>
						<div class="col-md-10">
							<div class="row">
								<div class="col-md-5">
								<div class="input-group">
									<span class="input-group-addon">成交价</span>
									<input type="text" placeholder="成交价" class="form-control" id="realPrice" name="realPrice" onkeyup="checkNum(this)"
										value="<fmt:formatNumber value='${ firstFollow.realPrice}' type='number' pattern='#0.00'/>">
									<span class="input-group-addon">万元</span>
								</div>
								</div>
								<div class="col-md-5">
								<div class="input-group">
									<span class="input-group-addon">合同价</span>
									<input type="text" placeholder="合同价" class="form-control" id="conPrice" name="conPrice" onkeyup="checkNum(this)"
										value="<fmt:formatNumber value='${ firstFollow.conPrice}' type='number' pattern='#0.00'/>">
									<span class="input-group-addon">万元</span>
								</div>
								</div>
							</div>
						</div>
					</div>
						</div>
						<div class="col-xs-12 col-md-5">
							<div class="form-group"  name="isYouXiao">
						<label class="col-md-4 control-label"><font color="red">*</font>产证地址</label>
						<div class="col-md-8">
							<input type="text" class="form-control" id="propertyAddr" name="propertyAddr" value="${firstFollow.propertyAddr}">
						</div>
					</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-xs-12 col-md-7">
							<div class="form-group"  name="isYouXiao">
						<label class="col-md-2 control-label"><font color="red">*</font>所在区域</label>
						<div class="col-md-10">
							<aist:dict clazz="form-control" id="distCode" name="distCode" display="select" defaultvalue="${firstFollow.distCode}" dictType="yu_shanghai_district" />
						</div>
					</div>
						</div>
						<div class="col-xs-12 col-md-5">
							<div class="form-group" id="data_1" name="isYouXiao">
									<label class="col-md-4 control-label"><font color="red">*</font>产证面积</label>
									<div class="col-md-8">
										<div class="input-group">
							<input type="text" class="form-control" id="square" name="square" onkeyup="checkNum(this)"
								value="<fmt:formatNumber value='${firstFollow.square}' type='number' pattern='#0.0#' />">
							<span class="input-group-addon">平米</span>
						</div>
									</div>
								</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-xs-12 col-md-7">
							<div class="form-group"  name="isYouXiao">
						<label class="col-md-2 control-label"><font color="red">*</font>抵押情况</label>
						<div class="col-md-10">
							<select class="form-control" name="diya" id="diya">
								<option value="">请选择</option>
								<option value="true" ${firstFollow.isLoanClose=="1"?'selected':''}>有抵押</option>
								<option value="false" ${firstFollow.isLoanClose=="0"?'selected':''}>无抵押</option>
							</select>
						</div>
					</div>
						</div>
						<div class="col-xs-12 col-md-5">
							<div class="form-group" id="data_1" name="isYouXiao">
									<label class="col-md-4 control-label"><font color="red">*</font>是否需要查限购</label>
									<div class="col-md-8">
										<select class="form-control" name="chaxiangou" id="chaxiangou">
											<option value="">请选择</option>
											<option value="true" ${firstFollow.isPerchaseReserachNeed=="1"?'selected':''}>是</option>
											<option value="false" ${firstFollow.isPerchaseReserachNeed=="0"?'selected':''}>否</option>
										</select>
									</div>
								</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-xs-12 col-md-7">
							<div class="form-group"  name="isYouXiao">
						<label class="col-md-2 control-label">备注</label>
						<div class="col-md-10">
							<input type="text" class="form-control" id="comment" name="comment" value="${firstFollow.comment}" />
						</div>
					</div>
						</div>
					
					
					</div>
					<div class="row product-type">
						<div class="col-xs-12 col-md-12">
							<div class="form-group"  name="isYouXiao">
						<label class="col-md-1 control-label m-l">产品需求</label>
						<div class="col-md-10">
							<aist:dict id="srvCode" name="srvCode" clazz="btn btn-white" display="checkboxcustom" defaultvalue="${firstFollow.srvCode }" dictType="yu_serv_cat_code_tree" tag="FIRST_FOLLOW"/>
						</div>
					</div>
						</div>
					</div>	
					<hr>
	
					<%-- <div class="row">
						<div class="col-xs-12 col-md-8">
							<div class="form-group"  name="isYouXiao">
						<label class="col-md-2 control-label">合作项目</label>
						<div class="col-md-10">
						<input type="hidden" id="zbkServices" name="zbkServices" value="${zbkServices }">
							<p id="" class="form-control-static">交易过户（除签约外）</p>
						</div>
					</div>
						</div>
						<div class="col-xs-12 col-md-4">
							<div class="form-group" id="data_1" name="isYouXiao">
									<label class="col-md-4 control-label"><font color="red">*</font>合作顾问</label>
									<div class="col-md-8">
										<select class="form-control" name="chaxiangou" id="chaxiangou">
											<option value="">请选择</option>
											<option value="true">有限购</option>
											<option value="false">无限购</option>
										</select>
									</div>
								</div>
						</div>
					</div> --%>
					<div id="hzxm">
					</div>
				</form>
			</div>
		</div>
		<div class="ibox-title" name="isYouXiao" style="display: display;">
			<h5>ctm附件</h5>
			<div class="ibox-content">
	            <table id="gridTable"></table>
	    		<div id="gridPager"></div>
			</div>
		</div>
		<div class="ibox-title" name="isShiXiao" style="display: none;">
			<h5>审批记录</h5>
			<div class="ibox-content">
				<div class="jqGrid_wrapper">
					<table id="reminder_list"></table>
					<div id="pager_list_1"></div>	
				</div>
			</div>
		</div>
			
		<div class="ibox-title">
			<a name="isYouXiao" href="#" class="btn" onclick="save(false)">保存</a>
			<a href="#" class="btn btn-primary" onclick="submit()">提交</a>
		</div>
		
	</div>
	
<jsp:include page="/WEB-INF/jsp/common/taskListByCaseCode.jsp"></jsp:include>
	<content tag="local_script"> 
	<!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/transjs/task/loanlostApprove.js"></script>
	<script src="${ctx}/transjs/task/showAttachment.js"></script> 
	<script src="${ctx}/transjs/common/caseTaskCheck.js?v=1"></script> 
	<!-- Custom and plugin javascript -->
	<script	src="${ctx}/js/plugins/dropzone/dropzone.js"></script> 

	<!-- Data picker -->
	<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
	<script src="${ctx}/js/jquery.blockui.min.js"></script>

    <script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script>
	<!-- bank select -->
	<script src="${ctx}/js/plugins/chosen/chosen.jquery.js"></script> 
	
	<script src="${ctx}/transjs/task/follow.pic.list.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/jquery.json.min.js"></script>
	<script src="${ctx}/js/plugins/jquery.custom.js"></script>
	<!-- 校验 -->
    <script src="${ctx}/js/plugins/validate/jquery.validate.min.js"></script>
    <script src="${ctx}/js/plugins/validate/common/additional-methods.js"></script>
	<script src="${ctx}/js/plugins/validate/common/messages_zh.js"></script>
	<script src="${ctx}/js/trunk/task/taskFirstFollow.validate.js"></script>
	<!-- 弹出框插件 -->
	<script src="${ctx}/js/plugins/layer/layer.js"></script>
	<script src="${ctx}/js/plugins/layer/extend/layer.ext.js"></script>
	<script>
		$(document).ready(function(){
			
			TaskFirstFollowValidate.init("firstFollowform","");
			
			initMortageService();
			
			
			/*  字典对应表关系
				合作项目
				30004001	商业贷／组合贷
				30004002	纯公积金贷
				30004010	交易过户服务（包括审税／核价／查限购／过户／领证）
				合作项
				0	无贷款
				1	商贷／组合贷款（委托中原）
				2	纯公积金（委托中原）
				3	商贷／组合贷款（自办）
				4	纯公积金贷款（自办）
			 */
			
			/*监听案件是否有效*/
			$("input:radio[name='caseProperty']").change(function (){ //拨通
				var optionsRadios =  $('input[name=caseProperty]:checked').val(); 
				if(optionsRadios=='30003001') {/*无效案件*/
					isShow('isYouXiao', 'none');
					isShow('isShiXiao', '');
				} else {/*有效案件*/
					isShow('isYouXiao', '');
					isShow('isShiXiao', 'none');
				}
			});
			
			if(caseProperty == '30003001') {/*无效案件*/
				isShow('isYouXiao', 'none');
				isShow('isShiXiao', '');
			} else {/*有效案件*/
				isShow('isYouXiao', '');
				isShow('isShiXiao', 'none');
			}
			
			/*加载默认基础服务和合作项目*/
			
			
			/* $("input:checkbox[name='srvCode'][value='30004010']").parent().parent().parent().hide();
			$("input:checkbox[name='srvCode'][value='30004002']").parent().parent().parent().hide();
			$("input:checkbox[name='srvCode'][value='30004001']").parent().parent().parent().hide(); */
			/* $("span[value='30004010']").hide();
			$("span[value='30004002']").hide();
			$("span[value='30004001']").hide(); */
			
			FollowPicList.init('${ctx}','/quickGrid/findPage','gridTable','gridPager','${ctmCode}','${caseCode}');
			
			$("span[name='srvCode']").click(function(){
				var id = $(this).attr("id");
				$("span[id='"+id+"']").changeSelect();
			});
			
		});
		
		/*设置div显示或隐藏*/
		function isShow(divName, stats) {
		    var div_array = document.getElementsByName(divName);   
		    for(i=0;i<div_array.length;i++)  
		    {  
			    div_array[i].style.display = stats; 
		    }  
		}
		
		/**日期组件*/
        $('#data_1 .input-group.date').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: false,
            autoclose: true
        });
		
        function initMortageService() {
			
			var url = "${ctx}/task/firstFollow/queryMortageServiceByServiceCode";
			$("#hzxm").html("");
			$.ajax({
				cache : false,
				async : true,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				data : {"serviceCode":'3000401002'},
				success : function(data) {
					
						    txt = "<div class='row'>";
						    txt += "<div class='col-xs-12 col-md-8'>";
						    txt += "<div class='form-group'  name='isYouXiao'>";
						    txt += "<label class='col-md-2 control-label'>合作项目</label>";
						    txt += "<div class='col-md-10'>";
							txt += "<input type='hidden' name='coworkService' value='"+data.dic.code+"'/>";
							txt += "<p id='' class='form-control-static'>"+data.dic.name+"</p>";
							txt += "</div>";
							txt += "</div>";
							txt += "</div>";
							txt += "<div class='col-xs-12 col-md-4'>";
							txt += "<div class='form-group' id='data_1' name='isYouXiao'>";
							txt += "<label class='col-md-4 control-label'><font color='red'>*</font>合作顾问</label>";
							txt += "<div class='col-md-8'>";
							txt += "<select class='form-control m-b' name='unCrossCooperationUser' id='cooperationUser"+index+"'>";
							txt += "<option value='0'>----未选择----</option>";
							$.each(data.users, function(j, user){
									txt += "<option value='"+user.id+"'>"+user.realName+"("+user.orgName+"):"+user.count+"件</option>";	
								
							});
							txt += "<option value='-1'>----跨区选择----</option>";
							txt += '</select>';
							txt += '<input type="hidden" id="coUser'+index+'" name="cooperationUser" value=""/>';
							txt += "</div></div></div>";
							txt += "</div>";
							/* var txt = '<div class="form-group" name="isYouXiao" style="display: display;">';
							txt += "<input type='hidden' name='coworkService' value='"+value.dicCode+"'/>";
							txt += "<label class='col-sm-2 control-label'>合作项目</label>";
							txt += "<div class='col-sm-3' style='padding-top: 6px;'><span>"+value.dicName+"</span></div>";
							txt += "<label class='col-sm-2 control-label'>合作顾问</label>";
							txt += "<div class=\"col-sm-3\">";
						    txt += "<div class=\"input-group \"  style='width: 250px;'>";
							txt += "<select class='form-control m-b' name='cooperationUser' id='cooperationUser"+index+"'>";
							txt += "<option value='0'>----未选择----</option>";
							$.each(value.users, function(j, user){
							
								txt += "<option value='"+user.id+"'>"+user.realName+"("+user.orgName+"):"+user.count+"件</option>";
							});
							txt += '</select></div></div>'; */
							$("#hzxm").append(txt);
							
							$('#coUser'+index).val($("#cooperationUser" + index).find(':selected').val());
							//alert($('#coUser'+index).val());
							
							var chaxiangou = $("#cooperationUser" + index);
							chaxiangou.chosen({no_results_text:"未找到该选项",width:"98%",search_contains:true,disable_search_threshold:10});

				},
				error : function(errors) {
					alert("数据出错。");
				}
			});
		}

		/*点击生成或清除合作顾问下拉框*/
		$(document).on("click","#cooperationUser0_chosen",function(){
			$(".chosen-single>span").each(function(){
				if($(this).text()=="----跨区选择----"){
					$('#coUser'+index).val('');
					if($("#corss_area").length==0){
						crossAreaCooperation();
					}
					//alert($('#coUser'+index).val());
				}else{
					$('#coUser'+index).val($("#cooperationUser" + index).find(':selected').val());
					if($("#corss_area").length>0){
						removeCrossAreaCooperation();
					}
					//alert($('#coUser'+index).val());
				}	
			});
		});
		
		/*删除跨区合作的DOM节点*/
		function removeCrossAreaCooperation(){
			$("#corss_area").remove();
		}        
        
		 /*点击下拉框跨区合作的选项触发跨区合作的选项*/
		function crossAreaCooperation(){
				
				var url = "${ctx}/task/firstFollow/getCrossAeraCooperationItems";
				var corsstxt = "";
				corsstxt += "<div class='col-md-12' id='corss_area'>";
				corsstxt += "<select name='crossCooperationUser' id='crossConsult"+index+"'>";
				corsstxt += "<option value='0'>----人员----</option>";
				corsstxt += '</select>';
				corsstxt += "<select name='crossOrg' id='corssOrg"+index+"'>";
				corsstxt += "<option value='0'>----组别----</option>";
				corsstxt += '</select>';				
				corsstxt += "<select id='crossDistrict"+index+"'>";
				corsstxt += "<option value='0'>----部门----</option>";
				corsstxt += '</select></div>';
				$("#hzxm").append(corsstxt);
				
				$.ajax({
					cache : true,
					async : false,//false同步，true异步
					type : "POST",
					url : url,
					dataType : "json",
					success : function(data) {
						
						/*三级联动*/
						var district = $('#crossDistrict'+index);
						var org = $('#corssOrg'+index);
						var consult = $("#crossConsult"+index);
						var districtStr="";
						
						$.each(data.cross,function(j,items){
							districtStr += "<option value='"+ items.districtId+"'>" + items.districtName+"</option>";
						});
						district.empty().append("<option value='0'>----部门----</option>"+districtStr);
						
						district.bind("change", function(){
							var orgStr="";
							var myIndex = district.find(":selected").index()-1;
							if(myIndex>=0){
								$.each(data.cross[myIndex].orgs, function(i, items){
									orgStr += "<option value='"+items.orgId+"'>"+items.orgName+"</option>";
								})
								org.empty().append("<option value='0'>----组别----</option>"+orgStr);
								var val1 = org.find(":selected").val();
								if(val1!='0'){
									changeConsult();
								}
							}else{
								org.empty().append("<option value='0'>----组别----</option>");
								consult.empty().append("<option value='0'>----人员----</option>");
							}
						});
						
						org.bind("change", changeConsult);
						function changeConsult(){
							var consultStr="";
							var index1 = district.find(":selected").index()-1;
							var index2 = org.find(":selected").index()-1;
							if(index2>=0){
								$.each(data.cross[index1].orgs[index2].userItems, function(k,items) {
									consultStr += "<option value='"+items.id+"'>"+items.realName+"("+items.count+"件)</option>";
								});
								consult.empty().append("<option value='0'>----人员----</option>"+consultStr);
								if(consultStr == ""){
									consult.empty();
									consult.append("<option value='0'>----人员----</option>");
								}
								getVals();
							}else{
								consult.empty().append("<option value='0'>----人员----</option>");
							}
						}
						
						consult.bind("change", getVals);
						/*改变隐藏框的值*/
						function getVals(){
							var guwen=consult.find(':selected').val();
							
							if(guwen!='0'){
								$('#coUser'+index).val(guwen);
							}
						//alert($('#coUser'+index).val());
						}						
					},
					error : function(errors) {
						alert("数据出错。");
					}
				});
		 }
		
		/**提交数据*/
		function submit() {
			save(true);
		}

		/**保存数据*/
		function save(b) {
			if(!b){
			if(!checkForm()) {
				return;
				}
			}
			if(!$("#firstFollowform").valid()){
				return;
			}
			
			var jsonData = $("#firstFollowform").serializeArray();
			var result = ''
			$("span.selected[name='srvCode']").each(function(){ 
				result += $(this).attr('value')+',';
			});
			var obj = {name:'srvCode',value:result.substring(0, result.length-1)};
			jsonData.push(obj);
			
			for(var i=0;i<jsonData.length;i++)
			{
				var item = jsonData[i];
				if(item["name"]=='cooperationUser' && (item["value"] == 0 || item["value"] == -1)){
					delete jsonData[parseInt(i)];
				}
			}
		
			var url = "${ctx}/task/firstFollow/saveFirstFollow";
			if(b) {
				url = "${ctx}/task/firstFollow/submit";
			}
			
			$.ajax({
				cache : true,
				async : false,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				//contentType:"application/json",  
				data : jsonData,
    		    beforeSend:function(){  
    				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
    				$(".blockOverlay").css({'z-index':'9998'});
                },
                complete: function() {  

                    $.unblockUI();  
                	if(b){ 
                        $.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'1900'}}); 
    				    $(".blockOverlay").css({'z-index':'1900'});
                	}   
                     if(status=='timeout'){//超时,status还有success,error等值的情况
    	          	  Modal.alert(
    				  {
    				    msg:"抱歉，系统处理超时。"
    				  });
    		  		 $(".btn-primary").one("click",function(){
    		  				parent.$.fancybox.close();
    		  			});	 
    		                } 
    		            } ,  
				success : function(data) {
					if(b) {
						setTimeout('caseTaskCheck()',1000); 
						//$('#case-task-modal-form').modal("show");
					} else {
						if(data.firstFollowVO.isrepeat==true){
							alert("请不要重复保存数据");
						}else{
							alert("保存成功.");
							window.close();
						 	window.opener.callback();
						}
					}
				},
				error : function(errors) {
					alert("数据保存出错");
				}
			});
		}
		
		/*double 验证*/
	    function checkNum(obj) { 
	        //先把非数字的都替换掉，除了数字和.
	        obj.value = obj.value.replace(/[^\d.]/g,"");
	        //必须保证第一个为数字而不是.
	        obj.value = obj.value.replace(/^\./g,"");
	        //保证只有出现一个.而没有多个.
	        obj.value = obj.value.replace(/\.{2,}/g,".");
	        //保证.只出现一次，而不能出现两次以上
	        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	     }  

		//验证控件checkUI();
		function checkForm() {
			if($("#cooperationUser0").val()== 0){
				alert("合作顾问未选择");
				return false;
			}
			// 如果选择了跨区合作并且人员为空
			if($("#cooperationUser0").val()== -1 && $("#consult0").val()== 0){
				alert("跨区合作顾问未选择");
				return false;
			}
			
			var optionsRadios =  $('input[name=caseProperty]:checked').val(); 
			
			if(optionsRadios=='有效案件'  || (optionsRadios!='30003001' && optionsRadios!=undefined)) {
				/*有效案件*/
				if($('#distCode').val() == ""){
					alert("所在区域为必选项!");
					$('#distCode').focus();
		             return false;
				}
				var flag = false;
				$('select[name="unCrossCooperationUser"] option:selected').each(function(i,item){
					if(item.value == "0"){
						 alert("合作顾问为必选项!");
//	 					 item.focus();
						 flag = true;
						 return false;
					}else if(item.value == "-1"){
						$('#consult'+index+' option:selected').each(function(j,item2){
							if(item2.value == "0"){
								 alert("跨区合作顾问未选择!");
								 flag = true;
								 return false;
							}
						});
					}
				});
				if(flag)return false;
				if($('input[name=realConTime]').val()=='') {
	                alert("签约时间为必填项!");
	                $('input[name=realConTime]').focus();
	                return false;
	           }
				if($('input[name=realPrice]').val()=='') {
	                alert("成交价为必填项!");
	                $('input[name=realPrice]').focus();
	                return false;
	           }
				if($('input[name=conPrice]').val()=='') {
	                alert("合同价为必填项!");
	                $('input[name=conPrice]').focus();
	                return false;
	           }
				if($('input[name=propertyAddr]').val()=='') {
	                alert("产证地址为必填项!");
	                $('input[name=propertyAddr]').focus();
	                return false;
	           }
				if($('input[name=square]').val()=='') {
	                alert("产证面积为必填项!");
	                $('input[name=square]').focus();
	                return false;
	           }
			} else {/*无效案件*/
				if($('input[name=invalid_reason]').val()=='') {
	                alert("无效案件必须填写失效原因!");
	                $('input[name=invalid_reason]').focus();
	                return false;
	           }
			}
			if($('select[name="unCrossCooperationUser"]').size()==0){
				 alert("正在加载合作项目!");
				 return false;
			}
			return true;
		}
	</script> 
	</content>
</body>


</html>
