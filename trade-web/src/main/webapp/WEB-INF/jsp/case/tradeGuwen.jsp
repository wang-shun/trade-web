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
				var agentID=$("#agentID").val();
				
				$.ajax({
					cache : true,
					async: false,
			        type: "POST",
			        url : ctx+'/api/ctm/guwen',
			        dataType:"json",
			        data:[{
			    		name:'agentID',
			    		value:agentID
			    	}],
			        success: function(data) {
			      	 	alert(data);
						console.info(data);
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
			<td>
				经纪人id： <input type="text" name="agentID" id="agentID" size="40" value="426367DE655841C79F9187056B32C220" />
				<input type="button" value="提交" onclick="submit()" /> 
			</td>
		</tr>
	</table>

</body>
</html>