
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
<!-- 必须CSS -->
<link rel="stylesheet" href="${ctx}/js/poshytitle/src/tip-twitter/tip-twitter.css" type="text/css" />
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
                     <input type="text" class="form-control input-one" placeholder="" id="caseCode" name="caseCode">
                 </div>
                 <div class="form-group form-margin form-space-one">
                     <label for="" class="lable-one">提交人</label>
                     <input type="text" class="form-control input-one" placeholder="" id="createBy" name="createBy">
                 </div>
                 <div class="form-group form-margin form-space-one">
                     <label for="" class="lable-one">保管人</label>
                     <input type="text" class="form-control input-one" placeholder="" id="itemManager" name="itemManager">
                 </div>
             </div>
             <div class="form-row form-rowbot">
                  <div class="form-group form-margin form-space-one">
                     <label for="" class="lable-one">物品类型</label>
                     <select class="form-control input-one" id="itemCategory">
                     	 <option value="" selected="selected">请选择</option>
                         <option value="1">身份证</option>
                         <option value="2">银行卡</option>
                         <option value="3">房产证</option>
                     </select>
                 </div>
                 <div class="form-group form-margin form-space-one">
                     <label for="" class="lable-one">物品状态</label>
                     <select class="form-control input-one" id="itemStatus">
                     	 <option value="" selected="selected">请选择</option>
                         <option value="1">完好无损</option>
                         <option value="2">已损坏</option>
                         <option value="3">部分破损</option>
                     </select>
                 </div>
                 <div class="form-group form-margin" style="margin-left:15px;">
                     <select class="form-control select-one" id="timeSelect">
                         <option value="ITEM_INPUT_TIME">入库时间</option>
                         <option value="ITEM_OUTPUT_TIME">借出时间</option>
                         <option value="ACTION_PRE_DATE">归还时间</option>
                         <option value="ITEM_BACK_TIME">退还时间</option>
                     </select>
                     <div class="input-daterange input-group" id="datepicker_0">
                         <input id="dtBegin_0"  type="text" class="form-control date-width" name="start" value="">
                         <span class="input-group-addon">到</span>
                         <input id="dtEnd_0"  type="text" class="form-control date-width" name="end" value="">
                     </div>
                 </div>
             </div>
             
             <div class="form-row">
                 <div class="form-group form-margin pull-left">
                     <label for="" class="lable-one">物业地址</label>
                     <input type="text" class="form-control" style="width:355px;" placeholder="" id="propertyAddr" name="propertyAddr">
                 </div>
                 <div class="btn-left btn-left-space ml40">
                     <button type="submit" class="btn btn-success btn-icon  mr5" id="searchButton"><i class="icon iconfont">&#xe635;</i> 查询</button>
                     <button type="reset" class="btn btn-grey mr5">清空</button>
                     <a href="../spv/spvStorageConfirm.html" class="btn btn-toggle mr5">入库</a>
                     <a href="javascript:void(0)" class="btn btn-toggle mr5" data-toggle="modal" data-target="#myModal">借用</a>
                     <a href="javascript:void(0)" class="btn btn-toggle mr5" data-toggle="modal" data-target="#Return">归还</a>
                     <a href="javascript:void(0)" class="btn btn-toggle mr5"
                     data-toggle="modal" data-target="#GiveBack">退还</a>
                     <a href="javascript:void(0)" class="btn btn-toggle mr5">删除</a>
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
                     <tbody id="materialInfoList"></tbody> 
                 </table>
              </div>
    		  <div class="text-center page_box">
					<span id="currentTotalPage"><strong></strong></span> <span
						class="ml15">共<strong id="totalP"></strong>条
					</span>&nbsp;
					<div id="pageBar" class="pagergoto"></div>
			  </div>
            </div>
         </div>
    </div>

  
<content tag="local_script"> 
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
<script src="${ctx}/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<!-- stickup plugin -->
<script src="${ctx}/static_res/trans/js/spv/jkresponsivegallery.js"></script>
<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> 
<script src="${ctx}/js/template.js" type="text/javascript"></script> <!-- stickup plugin -->
<script src="${ctx}/js/viewer/viewer.min.js"></script>
<script src="${ctx}/js/trunk/material/materialList.js"></script> 	
<script	id="template_materialInfoList" type="text/html">
      {{each rows as item index}}
  				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						<td> <input type="checkbox" class="i-checks" name="materialCheck"></td>
						<td><p class="big"><a href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}" target="_blank">{{item.CASE_CODE}}</a></p>
                            <p class="big">						
							{{if item.PROPERTY_ADDR != null && item.PROPERTY_ADDR!="" && item.PROPERTY_ADDR.length>24}}
								<p class = "demo-top"  title = "{{item.PROPERTY_ADDR}}">
							{{item.PROPERTY_ADDR.substring(item.PROPERTY_ADDR.length-24,item.PROPERTY_ADDR.length)}}
							{{else}}
							</p>
							<p>
								{{item.PROPERTY_ADDR}}
							{{/if}}	
							</p>
						</td>

						<td>
                            <p><i class="sign_blue"> 银行卡</i></p>
                            <p>中国建设银行储蓄卡</p>
                        </td>
						<td>
							<p><a class="demo-top" title="手机号： 1346754675<br/>虹口杨浦贵宾服务部B组" href="#">{{item.CREATE_BY}}</a> </p>
                        </td>

						<td>
							<p><a class="demo-top" title="手机号： 1346754675<br/>虹口杨浦贵宾服务部B组" href="#">{{item.ITEM_MANAGER}}</a> </p>
                        </td>
                        <td>
                            <p class="big">{{item.ITEM_ADDR_CODE}}</p>
                        </td>

                        <td>
                            <p class="big">{{item.ITEM_STATUS}}</p>
                        </td>

					    <td>
						{{if item.ITEM_STATUS == "1"}}
                                            <p class="smll_sign">
                                                <i class="sign_normal">入库</i>2016-09-22
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_normal">退还</i>2016-09-25
                                            </p>
						{{else item.ITEM_STATUS == "2"}}
                                           <p class="smll_sign">
                                                <i class="sign_normal">入库</i>2016-09-22
                                            </p>
						{{else item.ITEM_STATUS == "3"}}
                                            <p class="smll_sign">
                                                <i class="sign_normal">入库</i>2016-09-22
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_normal">借用</i>2016-09-22
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_normal">归还</i>2016-09-23
                                            </p>
						{{else item.ITEM_STATUS == "4"}}
                                            <p class="smll_sign">                                                
                                            </p>
						{{/if}}
                        </td>
						 <td class="text-center">
                            <a href="material/materialDetail.jsp">
                               <button type="button" class="btn btn-undertint btn-padding-3"><i class="icon iconfont color-corbule btn-look">&#xe63b;</i></button>
                            </a>
                        </td>
				  </tr>
       {{/each}}
</script> 
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
 	 var temp = $("[name=materialCheck]:checkbox");//document.getElementsByName("love");
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
	$("input[name='materialCheck']:checkbox").each(function(){ 
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
</content>
</body>
</html>