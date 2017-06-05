<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	<img src="${picUrl}">
	<content tag="local_script"> 
		<script src="${ctx}/js/rotate/jquery.rotate.min.js"></script>
		<script>
			var value2 = 0
			$("img").rotate({ 
			   bind: 
			     { 
			        click: function(){
			            value2 +=90;
			            $(this).rotate({ animateTo:value2})
			        }
			     } 
			});
		</script>
	</content>
</body>
</html>
