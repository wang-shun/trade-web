<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<jsp:include page="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"></jsp:include>

<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>赎楼申请修改</title>

<!-- 展示相关 -->
<link
	href="<c:url value='/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/trunk/JSPFileUpload/bootstrap-tokenfield.css' />"
	rel="stylesheet">
<link
	href="<c:url value='/css/trunk/JSPFileUpload/selectize.default.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<!-- 备件相关结束 -->
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"
	rel="stylesheet">

<!-- jdGrid相关 -->
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link
	href="<c:url value='/font-awesshenpiyijianome/css/font-awesome.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link
	href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />"
	rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/static/css/style.css' />">

<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"
	rel="stylesheet" />

<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
<link href="<c:url value='/css/iconfont/iconfont.css' />"
	rel="stylesheet">
<link href="<c:url value='/css/common/btn.css' />" rel="stylesheet">
<link rel="stylesheet"
	href="<c:url value='/static/trans/css/common/input.css' />">
<link href="<c:url value='/css/common/table.css' />" rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />"	rel="stylesheet">

<!-- 图片查看CSS -->
<link href="<c:url value='/js/viewer/viewer.min.css' />"
	rel="stylesheet" />



<style type="text/css">


.fo{width:100%;}
.matching{float: left;margin:0 20px;padding-top: 5px;}
.form_list .sign_left_small{text-align: left;margin-right: -10px;}
.underline{margin-top: -30px;}

</style>

<content tag="pagetitle">赎楼申请</content>
</head>

<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/ransom/ransomBaseInfo.jsp"></jsp:include>

	<div class="ibox-content border-bottom clearfix space_box noborder">
		
 		<form method="get" id="ransomApply" class="form_list">
 			<input type="hidden" id="ctx" value="${ctx}" />
			<input type="hidden" id="caseCode" name="caseCode" value="${detailVo.caseCode }">
			<input type="hidden" id="ransomCode" name="ransomCode" value="${detailVo.ransomCode }">
			<input type="hidden" id="borrowName" name="borrowName" value="${detailVo.borrowName }">
			<input type="hidden" id="partCode" name="partCode" value="${partCode }">
			<div class="line">
				<div class="title">信息录入</div>
             	<div class="form_content fo">
          			<label class="control-label matching"><font color=" red" class="mr5" >*</font>匹配资方</label>
					<div class="control-div ">
						<div class="form_content">
					   		<select id="applyOrgCode" name="applyOrgCode" class= "btn btn-white chosen-select " style="float :left;margin-right:40px;width:200px;">
							</select>
						</div>
						<!-- <select id="loanOfficer" name="loanOfficer" class= "btn btn-white chosen-select" style="float :left;width:200px;margin-left:95px;" >
							<option value="信贷员1" selected>信贷员1</option>
							<option value="信贷员2">信贷员2</option>
							<option value="信贷员3">信贷员3</option>
						</select> -->
						<div class="form_content">
							<label class="sign_left_two control-label" style="margin-left:-28px;"><font color=" red" class="mr5" >*</font>信贷员</label>
							<input type="text" class="select_control" id="loanOfficer" name="loanOfficer" value="${applyVo.loanOfficer }" style="width:200px;">
						</div>
					</div>
                 </div>
			</div>
			<div class="form_content fo">
			    <div class="form_content">
					<label  class="control-label matching"><font color=" red" class="mr5" >*</font>申请时间</label> 
					<div  class="input-group sign-right  input-daterange"  data-date-format="yyyy-mm-dd">
						<input id="applyTime" name="applyTime" class="form-control input-one date-picker data_style" style="font-size: 13px;width: 200px; border-radius: 2px;" 
						type="text"  value="<fmt:formatDate value='${applyVo.applyTime }' pattern='yyyy-MM-dd'/>" placeholder="申请时间" disabled>
					</div>
				</div>
				<div class="form_content">
					<label  class="control-label matching"><font color=" red" class="mr5" >*</font>计划面签时间</label> 
					<div  class="input-group sign-right  input-daterange"  data-date-format="yyyy-mm-dd">
						<input id="planSignTime" name="planSignTime" class="form-control input-one date-picker data_style" style="font-size: 13px;width: 200px; border-radius: 2px;" 
						type="text" value="<fmt:formatDate value='${applyVo.planSignTime }' pattern='yyyy-MM-dd'/>" disabled>
					</div>
				</div>
			</div>
		</form>
				
	</div>
		<div class="ibox-content border-bottom clearfix space_box noborder">
			<div class="line">
	             <div class="form_content">
	                 <h2 class="title">历史申请记录</h2>
	                 <div class="table_content">
					  <table class="table table_blue table-striped table-bordered table-hover " >
						<thead>
							<tr>
								<th>申请人</th>
								<th>申请时间</th>
								<th>申请金额</th>
								<th>申请机构</th>
								<th>中止原因</th>
							</tr>
						</thead>
						<tbody id="applyRecord">
						</tbody>
					</table>
		 		</div>
	             </div>
	         </div>
         </div>
         
		<div class="ibox-content border-bottom clearfix space_box noborder">
			<div id="caseCommentList" class="add_form"></div>
		</div>
		<div class="add_btn text-center mt20">
	   		<div class="more_btn">
			    <button id="saveButton" type="button" class="btn btn_blue">保存</button>
	   	    	<button id="closeButton" type="button" class="btn btn_blue">关闭</button>
			</div>
		</div>
	
<content tag="local_script"> 
	<script src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	<script src="<c:url value='/transjs/task/showAttachment.js' />"></script>
	<script src="<c:url value='/js/plugins/dropzone/dropzone.js' />"></script>
	<script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<script src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
	<script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script src="<c:url value='/js/viewer/viewer.min.js' />"></script> 
	<script src="<c:url value='/js/common/textarea.js' />"></script>
	<script src="<c:url value='/js/common/common.js' />"></script> 
	
	<script id="template_applyRecord" type="text/html">
		{{each rows as item inddex}}
			<tr>
				<td>
					{{item.borrName}}
				</td>
				<td>
					{{item.applyTime}}
				</td>
				<td>
					{{if item.borrMoney != null}}
						{{item.borrMoney / 10000}}&nbsp;万元
					{{/if}}	
				</td>
				<td>
					{{item.comOrgName}}
				</td>
				<td>
					{{item.stopReason}}
				</td>
			</tr>
		{{/each}}
	</script>
	<script>
		$(document).ready(function(){
			
			getEvaFinOrg('applyOrgCode');
			
			//案件跟进,common.js 
			var caseCode = $('#caseCode').val();
			$("#caseCommentList").caseCommentGrid({
				caseCode : caseCode,
				srvCode : null
			});
			//获取历史申请记录
			var data = {};
			data.queryId = "getApplyRecord";
			data.ransomCode = $('#ransomCode').val();
			data.rows = 10;
		    data.page = 1;
			queryApplyRecord(data);
		})
	
		function getEvaFinOrg(finOrgId){
			var url = "/manage/queryCooperation";
			$.ajax({
				async: true,
				type:'POST',
				url:ctx+url,
				dataType:'json',
				success:function(data){
					var html = '';
					if(data != null){
						$.each(data,function(i,item){
							html += '<option value="'+item.finOrgCode+'">'+item.finOrgName+'</option>';
						});
					}					
					$('#'+finOrgId).empty();
					$('#'+finOrgId).append(html);
				},
				error : function(errors) {
				}
			});
		}
		
		//日期初始化
		$('#applyTime').datepicker({
			format : 'yyyy-mm-dd',
			weekStart : 1,
			autoclose : true,
			todayBtn : 'linked',
			language : 'zh-CN'
		});
		
		$('#planSignTime').datepicker({
			format : 'yyyy-mm-dd',
			weekStart : 1,
			autoclose : true,
			todayBtn : 'linked',
			language : 'zh-CN'
		});
		
		function queryApplyRecord(data){
			$.ajax({
				async: true,
		        url:ctx+ "/quickGrid/findPage" ,
		        method: "post",
		        dataType: "json",
		        data: data,
		        beforeSend: function () {  
		        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
					$(".blockOverlay").css({'z-index':'9998'});
		        },  
		        success: function(data){
		          $.unblockUI();
		      	  var myTaskList = template('template_applyRecord' , data);
					  $("#applyRecord").empty();
					  $("#applyRecord").html(myTaskList);

		        }
		  });
			
		}
	
		//关闭
		$('#closeButton').click(function() {
			   window.close();	
		});
		
		//提交
		$('#saveButton').click(function(){
			debugger;
			if($('#loanOfficer').val() == ''){
				window.wxc.alert("信贷员为必填项!");
				$('#loanOfficer').focus();
				$('#loanOfficer').css('border-color',"red");
				return;
			}
			
			if($('#applyTime').val() == ''){
				window.wxc.alert("申请时间为必填项!");
				$('#applyTime').focus();
				$('#applyTime').css('border-color',"red");
				return;
			}
			if($('#planSignTime').val() == ''){
				window.wxc.alert("计划 面签时间为必填项!");
				$('#planSignTime').focus();
				$('#planSignTime').css('border-color',"red");
				return;
			}
			
			var jsonData = $('#ransomApply').serializeArray();
			var object = {
					name:'partCode',
					value:$('#partCode').val()
			}
			jsonData.push(object); 
			var url = "${ctx}/ransomChange/saveApply";
			var caseCode = $("#caseCode").val();
			$.ajax({
				cache:true,
				async:false,
				type:"POST",
				url:url,
				data:jsonData,
				dataType:"json",
				success:function(data){
					if(data){
						window.wxc.success("保存成功!",{"wxcOk":function(){
							window.location.href = ctx + "/ransomList/updateRansomInfo?caseCode=" + caseCode;
						}});
					}else{
						window.wxc.error("保存失败!");
					}
					
				},
				error : function(errors) {
					window.wxc.error("保存失败!");
				}
			});
		});
	</script> 
</content>
</body>
</html>
