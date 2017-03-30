<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
<link href="${ctx}${pubFolder }/css/trunk/salesLoading.css?v=1.0" rel="stylesheet" type="text/css"/>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="salesLoading" style="display: none">
	<div id="loading-center">
		<div id="loading-center-absolute">
			<div class="object" id="object_one"></div>
			<div class="object" id="object_two" style="left: 20px;"></div>
			<div class="object" id="object_three" style="left: 40px;"></div>
			<div class="object" id="object_four" style="left: 60px;"></div>
			<!--<div class="object" id="object_five" style="left:80px;"></div>-->
		</div>
	</div>
	<div id="loading-center-absolute-second">系统正在处理，请稍后...</div>
</div>