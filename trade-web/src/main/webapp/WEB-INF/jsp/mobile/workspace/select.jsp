<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
	<meta charset="utf-8">
	<title>屏幕首页</title>
	<%-- <link rel="stylesheet" href="${ctx}/momedia/css/workload/css/style.css"> --%>
	<style type="text/css">
		.img-circle{
			width: 74px;
			height: 74px;
		}
		select {
  /*Chrome和Firefox里面的边框是不一样的，所以复写了一下*/
  border: solid 1px #000;

  /*很关键：将默认的select选择框样式清除*/
  appearance:none;
  -moz-appearance:none;
  -webkit-appearance:none;

  /*在选择框的最右侧中间显示小箭头图片*/
  background: url("http://ourjs.github.io/static/2015/arrow.png") no-repeat scroll right center transparent;


  /*为下拉小箭头留出一点位置，避免被文字覆盖*/
  padding-right: 14px;
  font-size: 22px;
}


/*清除ie的默认选择框样式清除，隐藏下拉箭头*/
select::-ms-expand { display: none; }
.btn3 {
    BORDER-RIGHT: #7b9ebd 1px solid; 
    PADDING-RIGHT: 2px; 
    BORDER-TOP: #7b9ebd 1px solid; 
    PADDING-LEFT: 2px; 
    FONT-SIZE: 20px; 
    FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#cecfde); 
    BORDER-LEFT: #7b9ebd 1px solid; 
    CURSOR: hand; COLOR: black; 
    PADDING-TOP: 2px; 
    BORDER-BOTTOM: #7b9ebd 1px solid;
}
	</style>
</head>
  <body id="workload">
        <div style="margin-left: auto;
margin-right: auto;text-align: center;">
        	<select id="sel_org">
        		<c:forEach items="${orgs}"  var="item">
        		<option value="${ item.id}">${item.orgName }</option>
        		</c:forEach>
        	</select>
        	</br>
        	</br>
        	<input type="button" value="确定" class="btn3" onclick="">
          </div>
	<script src="../../../momedia/js/jquery-2.1.1.js"></script>
	    <script src= "../../../momedia/js/template.js" type="text/javascript" ></script>
	    <script>
	    	$(".btn3").click(function(){
	    		window.location.href="startPage?orgId="+$("#sel_org").find("option:selected").val();
	    	});
		</script>
</body>
</html>