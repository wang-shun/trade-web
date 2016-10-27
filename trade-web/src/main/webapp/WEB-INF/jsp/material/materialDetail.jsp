
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
             
           	 <div class="ibox-title">    	
                <h4>客户收取/归还确认证明</h4>			
				<div class="">
					<div id="imgShow" class="lightBoxGallery"></div>
				</div>
			</div>
			
<!--              <div class="show-img mt20">
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
             </div> -->
             
             
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
                      <c:forEach items="${mmIoBatchlist}" var="mmIoBatch">
                         <tr>
                             <td>${mmIoBatch.logAction}</td>
                             <td><fmt:formatDate value="${mmIoBatch.createTime}" pattern="yyyy-MM-dd"/></td>
                             <td><a href="#">${mmIoBatch.actionUser}</a></td>
                             <td><a href="#">${mmIoBatch.manager}</a></td>
                             <td>${mmIoBatch.actionReason}</td>
                             <td>${mmIoBatch.actionRemark}</td>
                         </tr>
 						</c:forEach>
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
<script src="${ctx}/js/template.js" type="text/javascript"></script>
 <!-- stickup plugin -->
<script src="${ctx}/js/viewer/viewer.min.js"></script>
<script type="text/javascript">
var attaPkid = "${attaPkid}";
var ctx = "${ctx}";

$(function(){	
	//获取附件列表信息
	getShowAttachmentForMaterial();	
});


function getShowAttachmentForMaterial() {	
	$.ajax({
		type : 'post',
		cache : false,
		async : false,//false同步，true异步
		dataType : 'json',
		url : ctx+'/attachment/quereyAttachmentForMaterial',
		data : [{
			name : 'pkid',
			value : attaPkid
		}],
		dataType : "json",
		success : function(data) {
			dataLength=data.length;
			//将返回的数据进行包装
			var trStr = "";
			var ssss="";
			
			var oldType = "";
			$.each(data, function(indexAcc, value){
				if(value.partCode !='property_research'){
					if(value.preFileName!=oldType){
						if(oldType!=""){
							trStr += '</div></div>';
						}
						oldType=value.preFileName;
						trStr += '<div class="row ibox-content">';
						trStr += '<label class="col-sm-2 control-label" style="line-height:90px;text-align:center;">'+value.preFileName+'</label>';
						trStr += '<div class="col-sm-10 lightBoxGallery" style="text-align:left">';
					}
					trStr += "<a href='#' title='"+value.fileName+"' data-gallery='' style='height:90px;width:80px;margin-left:5px;margin-right:5px;margin-bottom:20px;'>";
					trStr += "<img src='"+appCtx['shcl-image-web'] +"/image/"+value.preFileAdress+"/_f.jpg' style='padding-bottom: 5px;padding-top: 36px;width:100px;'>";
					trStr += "</a>";
					
				}
			});
			$("#imgShow").append(trStr);
			$('.wrapper-content').viewer();
		},
		error : function(errors) {
		}
	});
}


function downLoad(fileId, fileName) {
	window.location.href = ctx+"/attachment/resourcelibrary/downLoadResource.do?&pash="
		+ fileId+"&name="+fileName;
}

function show(fileId) {	
	window.open(appCtx['shcl-image-web']+"/image/"+fileId);
}
</script>
</content>
</body>
</html>