<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="${ctx}/static/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.json.min.js"></script>
		    
		 <script type="text/javascript">
			function submit(){
				var ctx = $("#ctx").val();
				var ctmCaseCode=$("#ctmCaseCode").val();
				var approveTime=$("#approveTime").val();
				var approveStatus=$("#approveStatus").val();
				var approverId=$("#approverId").val();
				var itemList=$("#itemList").val();
				var initiatorId=$("#initiatorId").val();
				
				$.ajax({
					cache : true,
					async: false,
			        type: "POST",
			        url : ctx+'/api/ctm/daishou',
			        dataType:"json",
			        data:[{
			    		name:'ctm_case_code',
			    		value:ctmCaseCode
			    	},{
			    		name:'approve_time',
			    		value:approveTime
			    	},{
			    		name:'approve_status',
			    		value:approveStatus
			    	},{
			    		name:'approver_id',
			    		value:approverId
			    	},{
			    		name:'itemList',
			    		value:itemList
			    	},{
			    		name:'initiator_id',
			    		value:initiatorId
			    	}],
			        success: function(data) {
			        	console.info(data);
			        	alert(data.status);
			        },
			        error: function(errors) {
			        	alert("信息查询失败，请刷新后再次尝试！");
			        }
				});
			}
		</script>

</head>
<body>
	
	<input type="hidden" id="ctx" value="${ctx }" />

	<table>
		<tr>
			<td>案件编号: <input type="text" name="ctmCaseCode" id="ctmCaseCode" value='BLA-1-201510-0001' /></td>
			<td>审批时间: <input type="text" name="approveTime" id="approveTime" value='2015-10-22 00:00:00.000' /></td>
			<td>审批状态: <input type="text" name="approveStatus" id="approveStatus" value='0' /></td>
			<td>审批人ID: <input type="text" name="approverId" id="approverId" value='1F802B26668F4D99AA8CD6985096BC6E' /></td>
			<td>审批发起人ID: <input type="text" name="initiatorId" id="initiatorId" value='1F802B26668F4D99AA8CD6985096BC6E' /></td>
			<td><input type="text" name="itemList" id="itemList" value="[{'money':'8888','direction':'0','item':'0'}]" /></td>
		</tr>
		<tr>
			<td><input type="button" value="提交" onclick="submit()" /></td>
		</tr>
	</table>

</body>
</html>