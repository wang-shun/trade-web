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
				var ctmcode=$("#ctmcode").val();
				
				$.ajax({
					cache : true,
					async: false,
			        type: "POST",
			        url:"${validctmdealUrl}",
			        dataType:"json",
			        data:[{
			    		name:'ctmcode',
			    		value:ctmcode
			    	}],
			        success: function(data) {
			        	console.info(data);
			        	alert(data);
			        },
			        error: function(errors) {
			        	alert("信息查询失败，请刷新后再次尝试！");
			        }
				});
			}
		</script>

</head>
<body>

	<table>
		<tr>
			<td>案件编号: <input type="text" name="ctmcode" id="ctmcode" value='BHY-1-201512-0001' /></td>
		</tr>
		<tr>
			<td><input type="button" value="提交" onclick="submit()" /></td>
		</tr>
	</table>

</body>
</html>