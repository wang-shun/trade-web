
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
<title>保管物品详情页</title>

<link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css" >
<link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css">
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" >

<link rel="stylesheet" href="${ctx}/static/css/animate.css">
<link rel="stylesheet" href="${ctx}/static/css/style.css">

<link rel="stylesheet" href="${ctx}/static/css/plugins/aist-steps/steps.css">
<link rel="stylesheet" href="${ctx}/static/css/plugins/toastr/toastr.min.css">
 
<!-- Data Tables -->
<link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.bootstrap.css">
<link rel="stylesheet" href="${ctx}/static/css/plugins/datapicker/datepicker3.css">
<link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.responsive.css" />
<link rel="stylesheet" href="${ctx}/static/css/plugins/dataTables/dataTables.tableTools.min.css" />

<!-- 提示 -->
<link rel="stylesheet" href="${ctx}/static/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css" />

<!-- index_css -->
<link rel="stylesheet" href="${ctx}/static/trans/css/common/uplodydome.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" /> 
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link rel="stylesheet" href="${ctx}/js/viewer/viewer.min.css" /> 

<!-- 分页控件 -->
<link href="${ctx}/css/plugins/pager/centaline.pager.css"	rel="stylesheet" />
<link href="${ctx}/css/plugins/autocomplete/jquery.autocomplete.css"	rel="stylesheet" />
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
                    
 <div class="row">
     <div class="wrapper wrapper-content animated fadeInUp">
         <div class="ibox-content" id="reportOne">
             <div class="row">
                 <div class="col-lg-12">
                     <div class="m-b-md">
                         <h4>扣押物品详情页</h4>
                     </div>
                     <div class="row" id="">
                         <div class="info_content">
                             <div class="line">
                                 <p>
                                     <label>案件编号</label>
                                     <span class="info_one">${mmMaterialItem.caseCode}</span>
                                 </p>
                                 <p>
                                     <label>档案柜编号</label>
                                     <span class="info_one">${mmMaterialItem.itemAddrCode}</span>
                                 </p>
                                  <p>
                                     <label>状态</label>
                                     <span class="info_one">${mmMaterialItem.itemStatus}</span>
                                 </p>
                             </div>
                             <div class="line">
                                 <p>
                                     <label>保管人</label>
                                     <span class="info_one">${user.realName}</span>
                                 </p>

                                 <p>
                                     <label>电话</label>
                                     <span class="info_one">${user.mobile}</span>
                                 </p>
                                 <p>
                                     <label>案件地址</label>
                                     <span>${toPropertyInfo.propertyAddr}</span>
                                 </p>
                             </div>
                         </div>
                     </div>
                 </div>
             </div>
             <div class="row mt20">
                 <div class="col-lg-12">
                     <div class="m-b-md">
                         <h4>抵押物品信息</h4>
                     </div>
                     <div class="row" id="">
                         <div class="info_content">
                             <div class="line">
                                 <p>
                                     <label>物品类型</label>
                                     <span class="info_one">${mmMaterialItem.itemCategory}</span>
                                 </p>
                                 <p>
                                     <label>物品名称</label>
                                     <span class="info_one">${mmMaterialItem.itemName}</span>
                                 </p>
                                  <p>
                                     <label>业务描述</label>
                                     <span>${mmMaterialItem.itemBusinessRemark}</span>
                                 </p>
                             </div>
                             <div class="line">
                                 <p>
                                     <label>备注</label>
                                     <span>${mmMaterialItem.itemRemark}</span>
                                 </p>
                             </div>
                         </div>
                     </div>
                 </div>
             </div>
             <div class="show-img mt20">
                 <h4>客户收取/归还确认证明</h4>
                 <table>
                     <tbody>
                         <tr>
                             <td>
                                 <ul class="filelist clearfix">
                                     <li id="WU_FILE_0">
                                         <p class="imgWrap">
                                             <img src="../static/trans/img/uplody01.png">
                                         </p>
                                         <div class="file-panel" >
                                             <span class="file-name">证明材料1</span>
                                         </div>
                                     </li>
                                     <li id="WU_FILE_0">
                                         <p class="imgWrap">
                                             <img src="../static/trans/img/uplody01.png">
                                         </p>
                                         <div class="file-panel" >
                                             <span class="file-name">证明材料2</span>
                                         </div>
                                     </li>
                                 </ul>
                             </td>
                         </tr>
                     </tbody>
                 </table>
             </div>
             <div class="record">
                 <h4>操作记录</h4>
                 <table class="table table-bordered customerinfo">
                     <thead>
                         <tr>
                             <th></th>
                             <th>时间</th>
                             <th>操作人</th>
                             <th>保管人</th>
                             <th>用途</th>
                             <th>备注</th>
                         </tr>
                     </thead>
                     <tbody>
                         <tr>
                             <td>押卡</td>
                             <td>2016-01-05</td>
                             <td><a href="#">张明</a></td>
                             <td><a href="#">张晓松</a></td>
                             <td>用于信息登记</td>
                             <td></td>
                         </tr>
                         <tr>
                             <td><i class="sign_brown">外借</i></td>
                             <td>2016-09-05</td>
                             <td><a href="#">赵相映</a></td>
                             <td><a href="#">张晓松</a></td>
                             <td>办理过户，预计2016-10-21归还</td>
                             <td></td>
                         </tr>
                         <tr>
                             <td>归还</td>
                             <td>2016-01-05</td>
                             <td><a href="#">史湘云</a></td>
                             <td><a href="#">张晓松</a></td>
                             <td>用于信息登记</td>
                             <td></td>
                         </tr>
                     </tbody>
                 </table>
             </div>
         </div>
     </div>
 </div>

  
<content tag="local_script"> 
<!-- Mainly scripts -->
<script src="${ctx}/static/js/jquery-2.1.1.js"></script>
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
                            <a href="../spv/spvRetainsDetail.html">
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