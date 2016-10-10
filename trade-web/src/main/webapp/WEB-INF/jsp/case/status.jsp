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
				var caseCode=$("#caseCode").val();
				
				$.ajax({
					cache : true,
					async: false,
			        type: "POST",
			        url : ctx+'/api/case/status',
			        dataType:"json",
			        data:[{
			    		name:'caseCode',
			    		value:caseCode
			    	}],
			        success: function(data) {
			        	alert(data.message);
			        	alert(data.content);
			        },
			        error: function(errors) {
			        	alert("信息修改失败，请刷新后再次尝试！");
			        }
				});
			}
		</script>

</head>
<body>
	
	<input type="hidden" id="ctx" value="${ctx }" />

	<table>
		<tr>
			<td>交易单编号: <input type="text" name="caseCode" id="caseCode" /></td>
		</tr>
		<tr>
			<td><input type="button" value="提交" onclick="submit()" /></td>
		</tr>
	</table>

</body>
</html>