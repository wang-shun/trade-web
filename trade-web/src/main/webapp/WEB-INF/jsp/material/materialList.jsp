
<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=utf-8" 	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>扣押物品列表</title>

<!-- Data Tables -->
<link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css">
<link rel="stylesheet" href="${ctx}/static/css/plugins/datapicker/datepicker3.css">
<link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" />

<!-- 提示 -->
<link rel="stylesheet" href="${ctx}/static/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css" />

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/base.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" /> 
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/btn.css" />
<link rel="stylesheet" href="${ctx}/js/viewer/viewer.min.css" /> 

<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css"	rel="stylesheet" />
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css"	rel="stylesheet" />


</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
                    
 <div class="wrapper wrapper-content animated fadeInRight">
     <div class="ibox-content border-bottom clearfix space_box">
         <h2 class="title">
             	扣押物品列表
         </h2>
         <form class="form-inline">
             <div class="form-row form-rowbot">
                 <div class="form-group form-margin form-space-one">
                     <label for="" class="lable-one" >案件编号</label>
                     <input type="text" class="form-control input-one" placeholder="">
                 </div>
                 <div class="form-group form-margin form-space-one">
                     <label for="" class="lable-one">提交人</label>
                     <input type="text" class="form-control input-one" placeholder="">
                 </div>
                 <div class="form-group form-margin form-space-one">
                     <label for="" class="lable-one">保管人</label>
                     <input type="text" class="form-control input-one" placeholder="">
                 </div>
             </div>
             <div class="form-row form-rowbot">
                  <div class="form-group form-margin form-space-one">
                     <label for="" class="lable-one">物品类型</label>
                     <select class="form-control input-one">
                         <option>身份证</option>
                         <option>已完成</option>
                         <option>已完成</option>
                     </select>
                 </div>
                 <div class="form-group form-margin form-space-one">
                     <label for="" class="lable-one">物品状态</label>
                     <select class="form-control input-one">
                         <option>完好无损</option>
                         <option>已完成</option>
                         <option>已完成</option>
                     </select>
                 </div>
                 <div class="form-group form-margin" style="margin-left:15px;">
                     <select class="form-control select-one">
                         <option>入库时间</option>
                         <option>起草时间</option>
                     </select>
                     <div class="input-daterange input-group" id="datepicker">
                         <input type="text" class="form-control date-width" name="start" value="">
                         <span class="input-group-addon">到</span>
                         <input type="text" class="form-control date-width" name="end" value="">
                     </div>
                 </div>
             </div>
             <div class="form-row">
                 <div class="form-group form-margin pull-left">
                     <label for="" class="lable-one">物业地址</label>
                     <input type="text" class="form-control" style="width:418px;" placeholder="">
                 </div>
                 <div class="btn-left btn-left-space" style="margin-left:40px;">
                     <button type="submit" class="btn btn-success btn-icon  mr5"><i class="icon iconfont">&#xe635;</i> 查询</button>
                     <button type="reset" class="btn btn-grey mr5">清空</button>
                     <a href="javascript:void(0)" class="btn btn-toggle mr5">入库</a>
                     <a href="javascript:void(0)" class="btn btn-toggle mr5" data-toggle="modal" data-target="#myModal">借用</a>
                     <a href="javascript:void(0)" class="btn btn-toggle mr5" data-toggle="modal" data-target="#Return">归还</a>
                     <a href="javascript:void(0)" class="btn btn-toggle mr5"
                     data-toggle="modal" data-target="#GiveBack">退还</a>
                 </div>
             </div>

         </form>
     </div>
     <div class="row">
         <div class="col-md-12">
             <div class="table_content">
                 <table class="table table_blue table-striped table-bordered table-hover " id="editable" >
                     <thead>
                         <tr>
                             <th class="">
                                 <input type="checkbox" class="i-checks" name="input"  id="allOrNotChoose" onclick="mycheck(this)">
                             </th>
                             <th>案件编码</th>
                             <th>保管物品</th>
                             <th>提交人</th>
                             <th>保管人</th>
                             <th>文件位置</th>
                             <th>状态</th>
                             <th>时间记录</th>
                             <th>操作</th>
                         </tr>
                     </thead>
                     <tbody>
                         <tr>
                             <td>
                                 <input type="checkbox" class="i-checks" name="retainsCheck" value='${item.pkid}'>
                             </td>
                             <td>
                                 <p class="big">
                                     SH20160906001
                                 </p>
                                 <p class="big">
                                    	上海市长宁区延安西路889弄1号202
                                 </p>
                             </td>
                             <td>
                                 <p>
                                     <i class="sign_blue">
                                        	 银行卡
                                     </i>
                                 </p>
                                  <p>
                                     	中国建设银行储蓄卡
                                 </p>
                             </td>
                             <td>
                                 <p><a class="demo-top" title="手机号： 1346754675<br/>虹口杨浦贵宾服务部B组" href="#">小赵</a> </p>
                             </td>
                             <td>
                                 <p><a class="demo-top" title="手机号： 1548754875<br/>虹口杨浦贵宾服务部A组" href="#">王振宇</a> </p>
                             </td>
                             <td>
                                 <p class="big">
                                     DAG-001
                                 </p>
                             </td>
                             <td>
                                 <p class="big">
                                     	退还
                                 </p>
                             </td>
                             <td>
                                 <p class="smll_sign">
                                     <i class="sign_normal">入库</i>2016-09-22
                                 </p>
                                 <p class="smll_sign">
                                     <i class="sign_normal">退还</i>2016-09-25
                                 </p>
                             </td>
                             <td class="text-center">
                                 <div class="btn-group">
                                     <button type="button" class="btn btn-undertint btn-padding-3"><i class="icon iconfont color-corbule btn-look">&#xe63b;</i>
                                     </button>
                                 </div>
                             </td>
                         </tr>
                         <tr>
                             <td>
                                 <input type="checkbox" class="i-checks" name="retainsCheck">
                             </td>
                             <td>
                                 <p class="big">
                                     SH20160906001
                                 </p>
                                 <p class="big">
                                     	上海市长宁区延安西路889弄1号202
                                 </p>
                             </td>
                             <td>
                                 <p>
                                     <i class="sign_blue">
                                         	银行卡
                                     </i>
                                 </p>
                                  <p>
                                     	中国农业银行储蓄卡
                                 </p>
                             </td>
                             <td>
                                 <p><a class="demo-top" title="手机号： 1346754675<br/>虹口杨浦贵宾服务部B组" href="#">小赵</a> </p>
                             </td>
                             <td>
                                 <p><a class="demo-top" title="手机号： 1548754875<br/>虹口杨浦贵宾服务部A组" href="#">王振宇</a> </p>
                             </td>
                             <td>
                                 <p class="big">
                                     DAG-001
                                 </p>
                             </td>
                             <td>
                                 <p class="big">
                                   	  在库
                                 </p>
                             </td>
                             <td>
                                 <p class="smll_sign">
                                     <i class="sign_normal">入库</i>2016-09-22
                                 </p>
                                 <p class="smll_sign">
                                     <i class="sign_normal">退还</i>
                                 </p>
                             </td>
                             <td class="text-center">
                                 <div class="btn-group">
                                     <button type="button" class="btn btn-undertint btn-padding-3"><i class="icon iconfont color-corbule btn-look">&#xe63b;</i>
                                     </button>
                                 </div>
                             </td>
                         </tr>
                         <tr>
                             <td>
                                 <input type="checkbox" class="i-checks" name="retainsCheck">
                             </td>
                             <td>
                                 <p class="big">
                                     SH20160906001
                                 </p>
                                 <p class="big">
                                     	上海市长宁区延安西路889弄1号202
                                 </p>
                             </td>
                             <td>
                                 <p>
                                     <i class="sign_blue">
                                      	   银行卡
                                     </i>
                                 </p>
                                  <p>
                                   	  中国建设银行储蓄卡
                                 </p>
                             </td>
                             <td>
                                 <p><a class="demo-top" title="手机号： 1346754675<br/>虹口杨浦贵宾服务部B组" href="#">小赵</a> </p>
                             </td>
                             <td>
                                 <p><a class="demo-top" title="手机号： 1548754875<br/>虹口杨浦贵宾服务部A组" href="#">王振宇</a> </p>
                             </td>
                             <td>
                                 <p class="big">
                                     DAG-001
                                 </p>
                             </td>
                             <td>
                                 <p>
                                     <i class="sign_brown">
                                        	 外借
                                     </i>
                                 </p>
                             </td>
                             <td>
                                 <p class="smll_sign">
                                     <i class="sign_normal">入库</i>2016-09-22
                                 </p>
                                 <p class="smll_sign">
                                     <i class="sign_normal">退还</i>
                                 </p>
                             </td>
                             <td class="text-center">
                                 <div class="btn-group">
                                     <button type="button" class="btn btn-undertint btn-padding-3"><i class="icon iconfont color-corbule btn-look">&#xe63b;</i>
                                     </button>
                                 </div>
                             </td>
                         </tr>
                         <tr>
                             <td>
                                 <input type="checkbox" class="i-checks" name="retainsCheck">
                             </td>
                             <td>
                                 <p class="big">
                                     SH20160906001
                                 </p>
                                 <p class="big">
                                     	上海市长宁区延安西路889弄1号202
                                 </p>
                             </td>
                             <td>
                                 <p>
                                     <i class="sign_blue">
                                       	  银行卡
                                     </i>
                                 </p>
                                  <p>
                                    	 中国建设银行储蓄卡
                                 </p>
                             </td>
                             <td>
                                 <p><a class="demo-top" title="手机号： 1346754675<br/>虹口杨浦贵宾服务部B组" href="#">小赵</a> </p>
                             </td>
                             <td>
                                 <p><a class="demo-top" title="手机号： 1548754875<br/>虹口杨浦贵宾服务部A组" href="#">王振宇</a> </p>
                             </td>
                             <td>
                                 <p class="big">
                                     DAG-001
                                 </p>
                             </td>
                             <td>
                                 <p>
                                     <i class="sign_brown">
                                         	待入库
                                     </i>
                                 </p>
                             </td>
                             <td>
                                 <p class="smll_sign">
                                     <i class="sign_normal">入库</i>
                                 </p>
                                 <p class="smll_sign">
                                     <i class="sign_normal">退还</i>
                                 </p>
                             </td>
                             <td class="text-center">
                                 <div class="btn-group">
                                     <button type="button" class="btn btn-undertint btn-padding-3"><i class="icon iconfont color-corbule btn-look">&#xe63b;</i>
                                     </button>
                                 </div>
                             </td>
                         </tr>

                     </tbody>
                 </table>
    
    <!-- 分页 -->             
<!--     <div class="text-center">
		<span id="currentTotalPage"><strong class="bold"></strong></span>
		<span class="ml15">共<strong class="bold" id="totalP"></strong>条</span>&nbsp;
		<div id="pageBar" class="pagination my-pagination text-center m0"></div>  
    </div> -->
                 
                 <div class="text-center page_box">
                     <span id="currentTotalPage">
                         <strong>
                             1/8
                         </strong>
                     </span>
                     <span class="ml15">
                        	 共
                         <strong id="totalP">
                             144
                         </strong>
                        	 条
                     </span>
                     &nbsp;
                     <div id="pageBar" class="pagination text-center">
                         <ul class="pagination">
                             <li class="first disabled">
                                 <a href="#"><i class="fa fa-step-backward"></i></a>
                             </li>
                             <li class="prev disabled">
                                 <a href="#"><i class="fa fa-chevron-left"></i></a>
                             </li>
                             <li class="page active">
                                 <a href="#">
                                     1
                                 </a>
                             </li>
                             <li class="page">
                                 <a href="#">
                                     2
                                 </a>
                             </li>
                             <li class="page">
                                 <a href="#">
                                     3
                                 </a>
                             </li>
                             <li class="page">
                                 <a href="#">
                                     4
                                 </a>
                             </li>
                             <li class="page">
                                 <a href="#">
                                     5
                                 </a>
                             </li>
                             <li class="page">
                                 <a href="#">
                                     6
                                 </a>
                             </li>
                             <li class="page">
                                 <a href="#">
                                     7
                                 </a>
                             </li>
                             <li class="page">
                                 <a href="#">
                                     8
                                 </a>
                             </li>
                             <li class="next">
                                 <a href="#"><i class="fa fa-chevron-right"></i></a>
                             </li>
                             <li class="last">
                                 <a href="#"><i class="fa fa-step-forward"></i></a>
                             </li>
                         </ul>
                         <div class="pagergoto">
                             <span class="go_text">
                               	  跳转至
                             </span>
                             <span>
                             <input type="text" class="go_input" value="">
                             <input type="button" class="go_btn" value="GO"></span>
                         </div>

                     </div>
                 </div>
             </div>
         </div>
     </div>
 </div>
  

<!-- Mainly scripts -->
<script src="${ctx}/static/js/jquery-2.1.1.js"></script>
<script src="${ctx}/static/trans/js/spv/spvRecordShow.js"></script>
<script src="${ctx}/static/js/bootstrap.min.js"></script>
<script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<!-- Custom and plugin javascript -->
<script src="${ctx}/static/js/inspinia.js"></script>
<script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
<!-- 必须JS -->
<script src="${ctx}/js/poshytitle/src/jquery.poshytip.js"></script>

<!-- stickup plugin -->
<script src="${ctx}/static_res/trans/js/spv/jkresponsivegallery.js"></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> 
<script src="${ctx}/js/template.js" type="text/javascript"></script> <!-- stickup plugin -->
<script src="${ctx}/js/viewer/viewer.min.js"></script>

<script type="text/javascript">
$(function(){
		//top
		$('.demo-top').poshytip({
			className: 'tip-twitter',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'center',
			alignY: 'top',
			offsetX: 8,
			offsetY: 5,
		});
});

$('.wrapper-content').viewer();


//通过复选框 设置全选 和  全不选
function mycheck(a) {
 	 var temp = $("[name=retainsCheck]:checkbox");//document.getElementsByName("love");
	 if (a.checked == true) {
  		for ( var i = 0; i < temp.length; i++) {
   			var val = temp[i];
   			val.checked = true;
  			}
 	 }else{
  		for ( var i = 0; i < temp.length; i++) {
   		var val = temp[i];
   		val.checked = false;
  	}
  }
}
//判断复选框是否选中
function getCheck(){
	var ids = ''; 
	var flag = 0; 
	$("#ids").attr("value",ids); 
	$("input[name='retainsCheck']:checkbox").each(function(){ 
		if (true == $(this).attr("checked")) { 
			ids += $(this).attr('value')+','; 
			flag += 1; 
		} 
	}); 
	if(flag > 0) { 
		$("#ids").attr("value",ids); 
		return true; 
	}else { 
		alert('请至少选择一项！'); 
		return false; 
	} 
}

</script>
</body>
</html>