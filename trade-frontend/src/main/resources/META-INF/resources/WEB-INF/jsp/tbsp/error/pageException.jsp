<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>错误页面</title>
 <%@include file="/WEB-INF/jsp/tbsp/common/metas.jsp"%>
 <%@include file="/WEB-INF/jsp/tbsp/common/scriptBase.jsp"%>
<script type="text/javascript">
$(document).ready(function()
{

$("#error").click(function(){
    $("#error-box").toggle();
   });
	
});
</script>
</head>
<body>
<div class="errormain">
  <div class="picerror"></div>
  <div class="errorbg">
    <div class="errortxt">
      <h3>对不起，您所请求的页面出现错误无法访问！</h3>
      <!-- <p>系统将自动跳转到上一页。</p> -->
      您可以：<a class="green" id="error" href="#">查看错误详情</a> 或者 <a class="green" href="#">联系我们</a> ！
    </div>
  </div>
  <div id="error-box" class="bordergrey bgwhite p10 dn">${msg}</div>
</div>
</body>
</html>