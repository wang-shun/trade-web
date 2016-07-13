<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>知识库列表</title>

<!-- Toastr style -->
<link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
<!-- Add fancyBox main JS and CSS files -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/jquery.fancybox.css?v=2.1.5" media="screen" />
<!-- Add Button helper (this is optional) -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/jquery.fancybox-buttons.css?v=1.0.5" />
<!-- Add Thumbnail helper (this is optional) -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/css/jquery.fancybox-thumbs.css?v=1.0.7" />

<!-- Gritter -->
<link href="${ctx}/js/plugins/gritter/jquery.gritter.css"
	rel="stylesheet">
<link href="${ctx}/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx}/css/animate.css" rel="stylesheet">
<link href="${ctx}/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/plugins/datapicker/datepicker3.css"
	rel="stylesheet">

<style type="text/css">
#table_list_2>tbody>tr>td {
	text-align: center;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>


	<input type="hidden" id="ctx" value="${ctx}" />
	<form id='tmpBankForm' class="form-horizontal">
		<input type="hidden" name="prAddress" value="${caseBaseVO.toPropertyInfo.propertyAddr }">
		<input type="hidden" name="tmpBankName" id="tmpBankName">
		<input type="hidden" name="pkid" value="${mortage.pkid }">
	<div class="ibox">
		<div class="ibox-title">
			<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
		</div>
		<div class="ibox-content">
			<div class="row form-group">
				<label class="col-sm-2 control-label">临时银行原因:</label>
				<div class="col-xs-3">${mortage.tmpBankReason }</div>
			</div>
			<div class="row form-group">
				<label class="col-sm-2 control-label">贷款银行：</label>
				<div class="col-md-4" style="height: 38px">
					<select name="bank_type" class="form-control" id="bank_type">
					</select>
				</div>

				<label class="col-sm-2 control-label">贷款支行<span class="star">*</span>：
				</label>
				<div class="col-md-4" style="height: 38px">
					<select name="finOrgCode" class="form-control" id="finOrgCode">
					</select>

				</div>
			</div>

			<div class="row form-group">
				<div class="col-md-12 ">
				<button type="button" class="btn btn-sm btn-primary pull-right" id="btn_save">&nbsp;&nbsp;&nbsp;处理&nbsp;&nbsp;&nbsp;</button>
				</div>
			</div>
			<div class="row form-group">
				<div class="jqGrid_wrapper ">
					<table id="table_list_3"></table>
					<div id="pager_list_3"></div>
				</div>
			</div>
		</div>

	</div>
	</form>

	<content tag="local_script"> <!-- jqGrid --> <script
		src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script> <script
		src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script> <script
		src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script> <script
		src="${ctx}/js/jquery.blockui.min.js"></script> <script>
			var ctx = "${ctx}";

			$(document).ready(function() {
				getParentBank($("#bank_type"),$("#finOrgCode"),"${mortage.finOrgCode}");
				$("#bank_type").change(function(){
			    	getBranchBankList($("#finOrgCode"),$("#bank_type").val());
			    });
				$("#btn_save").on('click',doProcessTmpBank);
			});
			function fCheck(){
				if(''==$("#finOrgCode").val()){
					alert('请选择贷款支行');
					return false;
				}
				return true;
			}
			function doProcessTmpBank(){
				if(!fCheck()){
					return ;
				}
				$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
				$("#tmpBankName").val($("#finOrgCode").find("option:selected").text());
				$.ajax({
					url:ctx+'/task/doProcessTmpBank',
					method:"post",
					dataType:"json",
					data:$("#tmpBankForm").serialize(),
					success:function(data){
						$.unblockUI();
						if(!!data.success){
							alert('处理成功');
							parent.$.fancybox.close();
						}else{
							alert(data.message);
						}
						
					}
				});
			}
			function getParentBank(selector,selectorBranch,finOrgCode){
				var bankHtml = "<option value=''>请选择</option>";
			    $.ajax({
			    	cache:true,
			    	url:ctx+"/manage/queryParentBankList",
					method:"post",
					dataType:"json",
					async:false,
					data:{nowCode:finOrgCode},
					success:function(data){
						if(data != null){
							for(var i = 0;i<data.length;i++){
								var coLevelStr='';
								bankHtml+="<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>";
							}
						}
					}
			     });
			    selector.find('option').remove();
				 selector.append($(bankHtml));
				 $.ajax({
					    url:ctx+"/manage/queryParentBankInfo",
					    method:"post",
					    dataType:"json",
						async:false,
					    data:{finOrgCode:finOrgCode},
					    success:function(data){
				    		if(data != null){
				    			selector.val(data.content);
				    		}
				    	}
					});
				 getBranchBankList(selectorBranch,selector.val(),finOrgCode);
				 return bankHtml;
			}
			function getBranchBankList(selectorBranch,pcode,finOrgCode){
				selectorBranch.find('option').remove();
				selectorBranch[0];
				selectorBranch.append($("<option value=''>请选择</option>"));
				$.ajax({
					cache:true,
				    url:ctx+"/manage/queryBankListByParentCode",
				    method:"post",
				    dataType:"json",
					async:false,
				    data:{faFinOrgCode:pcode,nowCode:finOrgCode},
				    	success:function(data){
				    		if(data != null){
				    			for(var i = 0;i<data.length;i++){
									var coLevelStr='('+data[i].coLevelStr+')';
						
									var option = $("<option coLevel='"+data[i].coLevel+"' value='"+data[i].finOrgCode+"'>"+data[i].finOrgNameYc+coLevelStr+"</option>");
									if(data[i].finOrgCode==finOrgCode){
										option.attr("selected",true);
									}
									selectorBranch.append(option);
				    			}
				    		}
				    	}
				 });
				return true;
			}

			//得到事件
			function getEvent() {
				if (window.event) {
					return window.event;
				}
				func = getEvent.caller;
				while (func != null) {
					var arg0 = func.arguments[0];
					if (arg0) {
						if ((arg0.constructor == Event
								|| arg0.constructor == MouseEvent || arg0.constructor == KeyboardEvent)
								|| (typeof (arg0) == "object"
										&& arg0.preventDefault && arg0.stopPropagation)) {
							return arg0;
						}
					}
					func = func.caller;
				}
				return null;
			}
			//阻止冒泡
			function cancelBubble() {
				var e = getEvent();
				if (window.event) {
					e.returnValue = false;//阻止自身行为
					e.cancelBubble = true;//阻止冒泡
				} else if (e.preventDefault) {
					e.preventDefault();//阻止自身行为
					e.stopPropagation();//阻止冒泡
				}
			}
		</script> </content>
</body>
</html>



