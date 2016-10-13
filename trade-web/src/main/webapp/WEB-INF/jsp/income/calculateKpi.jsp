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
				
				var roleCode=$("#roleCode").val();
				var score=$("#score").val();
				var phoneRate=$("#phoneRate").val();
			    var financialCount=$("#financialCount").val();
				var signedCount=$("#signedCount").val();
				var outflowRate=$("#outflowRate").val();
				
				$.ajax({
					cache : true,
					async: false,
			        type: "POST",
			        url : ctx+'/kpi/calculateKpi',
			        dataType:"json",
			        data:[{
			    		name:'roleCode',
			    		value:roleCode
			    	},{
			    		name:'score',
			    		value:score
			    	},{
			    		name:'phoneRate',
			    		value:phoneRate
			    	},{
			    		name:'financialCount',
			    		value:financialCount
			    	},{
			    		name:'signedCount',
			    		value:signedCount
			    	},{
			    		name:'outflowRate',
			    		value:outflowRate
			    	}],
			        success: function(data) {
			        	alert(data.message);
			        	alert(data.content);
			        },
			        error: function(errors) {
			        	alert("信息计算失败，请刷新后再次尝试！");
			        }
				});
			}
		</script>

</head>
<body>
	
	<input type="hidden" id="ctx" value="${ctx }" />
	
	<table>
		<tr>
			<td>角色编码: <input type="text" name="roleCode" value="Manager" id="roleCode" /></td>
			<td>满意度评分: <input type="text" name="score" value="6.5" id="score" /></td>
			<td>电话准确率: <input type="text" name="phoneRate" value="0.9" id="phoneRate" /></td>
			<td>金融产品单数: <input type="text" name="financialCount" value="0" id="financialCount" /></td>
			<td>组别月签约单数: <input type="text" name="signedCount" value="3" id="signedCount" /></td>
			<td>流失率: <input type="text" name="outflowRate" value="0.2" id="outflowRate" /></td>
		</tr>
		<tr>
			<td><input type="button" value="提交" onclick="submit()" /></td>
		</tr>
	</table>

</body>
</html>