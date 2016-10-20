
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
<title>入库确认</title>

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
    <div class="row">
       <div class="wrapper wrapper-content animated fadeInUp marginbot-30 ">
           <div class="ibox-content" id="reportOne">
                <div class="row">
                   <div class="col-lg-12">
                       <div class="m-b-md">
                           <h4>入库</h4>
                       </div>
                       <div class="row" id="">
                           <div class="info_content">
                               <div class="line clearfix">
                                   <p>
                                       <label> 案件编号</label>
                                       <span class="info_one">${mmMaterialItemList.get(0).caseCode}</span>
                                   </p>
                                   <p>
                                       <label>案件地址</label>
                                       <span>上海市长宁区延安西路889号1弄202</span>
                                   </p>
                               </div>
                               <div class="line">
                                   <p class="input-infoht">
                                       <label>
                                           	文件位置编号
                                       </label>
                                       <input type="text" value=" " class="select_control info_two"  name="itemAddrCode"  id="itemAddrCode">
                                   <p>
                               </div>
                           </div>
                       </div>
                   </div>
               </div>
               <table class="table table_blue mt20 no-border">
                   <thead>
                       <tr>
                           <th>
                              	 客户确认书
                           </th>
                       </tr>
                   </thead>
                   <tbody>
                       <tr>
                           <td>
                               <ul class="filelist clearfix">
                                   <li id="WU_FILE_0">
                                       <p class="imgWrap">
                                           <img src="../static/trans/img/uplody01.png">
                                       </p>
                                       <div class="file-panel" >
                                           <span class="file-name">公证书1</span>
                                           <span class="cancel pull-right">删除</span>
                                       </div>
                                   </li>
                                   <li>
                                       <p class="imgWrap fileposition">
                                           <img src="../static/trans/img/uplody02.png">
                                           <input type="file" name="file" class="webupload_file" multiple="multiple" accept="image/*">
                                       </p>
                                   </li>
                               </ul>
                           </td>
                       </tr>
                   </tbody>
               </table>
               
               <div class="enregister">
                   <div class="modal_title">
                       <h4>物品信息登记</h4>
                   </div>
                   <c:forEach items="${mmMaterialItemList}" var="mmMaterialInfo">
                   <div class="form_list">
                       <div class="line">
                           <div class="form_content">
                               <label class="control-label sign_left_small">
                                  	 物品类型
                               </label>
                               <input class="input_type extent-one"  name="itemCategory" id="itemCategory" value="${mmMaterialInfo.itemCategory}">
                           </div>
                           <div class="form_content">
                               <label class="control-label sign_left_small">
                                   	物品名称
                               </label>
                               <input class="input_type"  value="${mmMaterialInfo.itemName}" name="itemName" id="itemName" >
                           </div>
                           <div class="form_content">
                               <label class="control-label sign_left_small">
                                  	 业务描述
                               </label>
                               <input class="input_type extent-three" value="${mmMaterialInfo.itemBusinessRemark}" name="itemBusinessRemark" id="itemBusinessRemark" >
                           </div>
                       </div>


                       <div class="line clearfix">
                           <div class="form_content">
                               <label class="control-label sign_left_small pull-left">
                                  	 备注
                               </label>
                               <textarea class="enregisterarea textarea" name="itemRemark" id="itemRemark" ></textarea>
                           </div>
                       </div>
                   </div>
                 </c:forEach>
               </div>

               <div class="status_btn text-center">
                   <button class="btn btn-success btn-space">提交</button>
                   <button class="btn btn-grey" data-dismiss="modal" data-toggle="modal" data-target="#myModal">关闭</button>
               </div>
           </div>
       </div>
   </div>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="signTimeStart" value="${signTimeStart}" />
	<input type="hidden" id="signTimeEnd" value="${signTimeEnd}" />

	<input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
	<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
	<input type="hidden" id="userJobCode" value="${userJobCode}" />
	<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
	<input type="hidden" id="serviceDepId" value="${serviceDepId}" />
  
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

<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>	
<script	id="template_materialInfoList" type="text/html">
      {{each rows as item index}}
  				  {{if index%2 == 0}}
 				      <tr class="tr-1">
                  {{else}}
                       <tr class="tr-2">
                   {{/if}}
						<td> <input type="checkbox" class="i-checks" name="materialCheck"></td>
						<td><p class="big"><a href="${ctx}/case/caseDetail?caseId={{item.PKID}}" target="_blank">{{item.CASE_CODE}}</a></p>
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
                            <p><i class="sign_blue">{{item.ITEM_CATEGORY}}</i></p>
                            <p>中国建设银行储蓄卡</p>
                        </td>
						<td>
							<p><a class="demo-top" title="手机号： {{item.CREATE_BY_MOBILE}}<br/>{{item.CREATE_BY_ORG_NAME}}" href="#">{{item.CREATE_BY_REAL_NAME}}</a> </p>
                        </td>

						<td>
							<p><a class="demo-top" title="手机号： {{item.ITEM_MANAGER_MOBILE}}<br/>{{item.ITEM_MANAGER_ORG_NAME}}" href="#">{{item.ITEM_MANAGER_REAL_NAME}}</a> </p>
                        </td>
                        <td>
                            <p class="big">{{item.ITEM_ADDR_CODE}}</p>
                        </td>

                        <td>
						{{if item.ITEM_STATUS == "back"}}
                         	<p class="big">退还</p>
						{{else if item.ITEM_STATUS == "instock"}}
							<p class="big">在库</p>
						{{else if item.ITEM_STATUS == "borrow"}}
								{{if item.TODAY  < item.ACTION_PRE_DATE}}
                            		<p><i class="sign_brown">外借</i></p>
								{{else}}
									<p class="big">外借</p>
								{{/if}}   	
						{{else if item.ITEM_STATUS == "stay"}}
                            <p><i class="sign_brown"> 待入库</i></p>
						{{/if}}                           
                        </td>

					    <td>
						{{if item.ITEM_STATUS == "back"}}
                                            <p class="smll_sign">
                                                <i class="sign_normal">入库</i>{{item.ITEM_INPUT_TIME}}
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_normal">退还</i>{{item.ITEM_BACK_TIME}}
                                            </p>
						{{else if item.ITEM_STATUS == "instock"}}
                                           <p class="smll_sign">
                                                <i class="sign_normal">入库</i>{{item.ITEM_INPUT_TIME}}
                                            </p>
						{{else if item.ITEM_STATUS == "borrow"}}
                                            <p class="smll_sign">
                                                <i class="sign_normal">入库</i>{{item.ITEM_INPUT_TIME}}
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_normal">借用</i>{{item.ITEM_OUTPUT_TIME}}
                                            </p>
                                            <p class="smll_sign">
                                                <i class="sign_normal">归还</i>{{item.ACTION_PRE_DATE}}
                                            </p>
						{{else if item.ITEM_STATUS == "stay"}}
                                            <p class="smll_sign">                                                
                                            </p>
						{{/if}}
                        </td>
						 <td class="text-center">
                            <a href="./materialDetail.jsp">
                               <button type="button" class="btn btn-undertint btn-padding-3"><i class="icon iconfont color-corbule btn-look">&#xe63b;</i></button>
                            </a>
                        </td>
				  </tr>
       {{/each}}
</script> 
<script type="text/javascript">

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