<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>新建自录单</title>

<link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/static/css/select2.min.css">


<!-- index_css  -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css">
<link rel="stylesheet" href="${ctx}/static_res/trans/css/common/report.css">
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" ">


		<link rel="stylesheet" type="text/css" href="http://sales.centaline.com:8082/sales-web/media/css/jquery.fancybox.css?v=2.1.5" media="screen" />
		
		<!-- Add Button helper (this is optional) -->
		<link rel="stylesheet" type="text/css" href="http://sales.centaline.com:8082/sales-web/media/css/jquery.fancybox-buttons.css?v=1.0.5" />
	
		<!-- Add Thumbnail helper (this is optional) -->
		<link rel="stylesheet" type="text/css" href="http://sales.centaline.com:8082/sales-web/media/css/jquery.fancybox-thumbs.css?v=1.0.7" />

</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="appCtx" value="${appCtx['sales-web']}" />
	<div class="control-group span9">
		<label class="control-label" style="margin-right:8px;">地址：</label>
		<div id="blocksSelect_div" class="blocksSelect-div">
		<div class="select2-container span3" id="s2id_blocksSelect"><a href="javascript:void(0)" onclick="return false;" class="select2-choice" tabindex="-1">   <span>虹口商城</span><abbr class="select2-search-choice-close" style=""></abbr>   <div><b></b></div></a><input class="select2-focusser select2-offscreen" id="s2id_autogen1" type="text"></div><input id="blocksSelect" class="select2 span3 select2-offscreen" tabindex="-1" type="text"> 
		</div>
		<input id="oppoBlocksSelect" style="display:none;margin-left:2px;" type="text">
					<select id="buildingsSelect" class=" select2" style="margin-left:4%;"><option value="">请选择</option><option value="21751">独栋</option></select>
					<select id="floorSelect" class=" select2"><option value="">请选择</option><option value="1">1层</option><option value="2">2层</option><option value="3">3层</option><option value="4">4层</option><option value="5">5层</option><option value="6">6层</option><option value="7">7层</option><option value="8">8层</option><option value="9">9层</option><option value="10">10层</option><option value="11">11层</option><option value="12">12层</option><option value="13">13层</option><option value="14">14层</option><option value="15">15层</option><option value="16">16层</option><option value="17">17层</option><option value="18">18层</option><option value="19">19层</option><option value="20">20层</option><option value="21">21层</option><option value="22">22层</option></select>
					<select id="roomSelect" class=" select2"><option value="">无房屋</option></select>
					<input id="houseId" name="houseId" value="" type="hidden">
	</div>

    
    
<content tag="local_script"> 


<script >
var ctx = "${ctx}";
var trade_web_ctx = "${ctx}";
var sales_web_ctx = "${appCtx['sales-web']}";
</script>
<script src="${ctx}/js/plugins/autocomplete/select2.full.min.js"></script>
<!-- 新增房源页面所有的JS -->
<script src="http://sales.centaline.com:8082/sales-web/static/salesjs/house/estateSelect2.js?v=1.6.0"></script>
<script src="http://sales.centaline.com:8082/sales-web/static/salesjs/house/houdel.addNew.js?v=1.4.9"></script>
	
<script src="http://sales.centaline.com:8082/sales-web/media/js/jquery.fancybox.pack.js"></script>


</content>
</body>
</html>
