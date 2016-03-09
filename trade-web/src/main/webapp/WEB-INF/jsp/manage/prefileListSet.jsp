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

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- jdGrid相关 -->
<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/style.css" rel="stylesheet">

<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<script type="text/javascript">
	var ctx = "${ctx}";
	var taskitem = "${taskitem}";
	var attachmentCode = "LoanlostApply";
	var caseCode = "${caseCode}";
	var processInstanceId = "${processInstanceId}";
	var approveType = "${approveType }";
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

</style>
</head>
<body>

	<div class="row">
		<div class="ibox-title">
			<h5>备件列表</h5>
			<div class="ibox-content">
				<form method="get" class="form-horizontal" id="lamform">
					<!-- 交易单编号 -->
					<input type="hidden" id="caseCode" name="caseCode" value="${caseCode}">
					<div class="form-group">
						<label class="col-sm-3 control-label">环节选择</label>
						<div class="col-sm-7">
							<aist:dict clazz="form-control m-b" id="partCode" name="partCode" display="select" 
								defaultvalue="" dictType="part_code" />
						</div>
					</div>
					<div id="accesoryListDiv">
					
					</div>
					<div id="addLine" class="form-group" style="margin-left: 130px;margin-top:-2px;">
						<a href="javascript:addAccsory();" class="btn"><font>添加新备件</font></a>
					</div>
					
					<div id="guestDelDiv">
					</div>
				</form>

			</div>
		</div>
		<div class="ibox-title">
			<a href="#" class="btn" onclick="save()">保存</a>
			<!-- <a href="#" class="btn btn-primary" onclick="submit()">提交</a> -->
		</div>
	</div>
	<content tag="local_script"> 
	<!-- jqGrid -->
	<script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
	<script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> 
	<script src="${ctx}/transjs/task/loanlostApprove.js"></script> 
	<script src="${ctx}/transjs/task/showAttachment.js"></script> 
	
	<script>
		$(function(){
			$("#partCode").change(function(){
				var value = $("#partCode").val();
				initGuestInfo(value);
			});
		});
		/**提交数据*/
		function submit() {
			save();
		}

		/**保存数据*/
		function save() {
			var jsonData = $("#lamform").serializeArray();
			var url = "${ctx}/setting/saveToAccsoryList";
			$.ajax({
				cache : true,
				async : false,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				data : jsonData,
				success : function(data) {
					if(data) {
						alert("操作完成。");
					} else {
						alert('数据无变更。');
					}
					var value = $("#partCode").val();
					initGuestInfo(value);
				},
				error : function(errors) {
					alert("数据保存出错");
				}
			});
		}
		
		var divIndexUp = 1;
		function addAccsory() {
			var value = $("#partCode").val();
			if(value == null || value == "" || value == undefined) {
				alert("请选择操作环节！");
				return;
			}
			var txt = '<div id="dateDivU_' + divIndexUp + '" class="form-group">';
			txt += "<input type='hidden' name='pkidList' id='pkidList' value='0'>";
			txt += "<label class=\"col-sm-3 control-label\">备件名称<font color='red'>*</font></label>";
			txt += "<div class=\"col-sm-7\">";
			txt += "<div class=\"input-group \">";
			txt += "<aist:dict clazz='form-control m-b' id='accessoryCodeList' name='accessoryCodeList' display='select' defaultvalue='' dictType='yu_file_code' />";
			txt += '<span class="input-group-addon"><a href="javascript:removeDateDiv(\'dateDivU_'
					+ divIndexUp + '\');"><font>删除</font></a></span>';
			txt += '</div></div></div>';
			// alert(txt);
			$("#accesoryListDiv").append(txt);
			divIndexUp++;
		}
		
		function initGuestInfo(partCode) {
			$.ajax({
				cache : true,
				async : false,//false同步，true异步
				type : "POST",
				url : "${ctx}/setting/queryToAccsoryList",
				dataType : "json",
				data :  [{
					name:"partCode",
					value:partCode
				}],
				success : function(data) {
					var length = data.length;
					var txt = "";
					if(length > 0) {
					$.each(data, function(index, value){
						txt += '<div id=\"dateDivD_' + divIndexUp + '\" class=\"form-group\">';
						txt += "<input type='hidden' name='pkidList' id='pkidList' value='"+value.pkid+"'>";
						txt += "<label class=\"col-sm-3 control-label\">备件名称<font color='red'>*</font></label>";
						txt += "<div class=\"col-sm-7\">";
						txt += "<div class=\"input-group \">";
						txt += "<aist:dict clazz='form-control m-b' id='accessoryCodeList"+index+"' name='accessoryCodeList' display='select' defaultvalue='' dictType='yu_file_code'/>";
						txt += '<span class="input-group-addon"><a href="javascript:removeDiv(\'dateDivD_'+divIndexUp+'\','
							+ value.pkid + ');"><font>删除</font></a></span>';
						txt += '</div></div></div>';
						// alert(txt);
						$("#accessoryCodeList"+index).val(value.accessoryCode);
						divIndexUp++;
					});
					
					} else {
						txt = '<div id="dateDivU_' + divIndexUp + '" class="form-group">';
						txt += "<input type='hidden' name='pkidList' id='pkidList' value='0'>";
						txt += "<label class=\"col-sm-3 control-label\">备件名称<font color='red'>*</font></label>";
						txt += "<div class=\"col-sm-7\">";
						txt += "<div class=\"input-group \">";
						txt += "<aist:dict clazz='form-control m-b' id='accessoryCodeList' name='accessoryCodeList' display='select' defaultvalue='' dictType='yu_file_code' />";
						txt += '<span class="input-group-addon"><a href="javascript:removeDateDiv(\'dateDivU_'
								+ divIndexUp + '\');"><font>删除</font></a></span>';
						txt += '</div></div></div>';
						divIndexUp++;
					}
					$("#accesoryListDiv").html(txt);
					if(length > 0) {
						$.each(data, function(index, value){
							$("#accessoryCodeList"+index).val(value.accessoryCode);
						});
					}
				},
				error : function(errors) {
					alert("备件列表失败！");
				}
			});
		}
		
		function removeDiv(id,pkid) {
			var txt = "<input type='hidden' name='accesoryPkid' value='"+pkid+"'/>";
			$("#guestDelDiv").before(txt);
			$("#"+id).remove();
		}
		
		function removeDateDiv(index) {
			$("#" + index).remove();
		}
		
	</script> 
	</content>
</body>


</html>