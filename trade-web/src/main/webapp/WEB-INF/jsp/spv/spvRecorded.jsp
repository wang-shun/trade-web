<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf" %>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>入账</title>
<%-- <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css">
<link rel="stylesheet" href="${ctx}/static/css/animate.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/static/css/style.css" rel="stylesheet">
<!-- stickUp fixed css -->
<link rel="stylesheet" href="${ctx}/static/css/plugins/stickup/stickup.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/common/stickmenu.css">
<link rel="stylesheet" href="${ctx}/static/css/plugins/aist-steps/steps.css">
<link rel="stylesheet" href="${ctx}/static/trans/static/css/plugins/toastr/toastr.min.css">
<link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css">
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/table.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/see.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/spv.css" />
<link rel="stylesheet" href="${ctx}/static/trans/css/spv/response/jkresponsivegallery.css " /> --%>

 <!-- 上传相关 -->
	<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fancybox.css"
		rel="stylesheet">
	<link href="${ctx}/css/trunk/JSPFileUpload/jquery.fileupload-ui.css"
		rel="stylesheet">
	<link href="${ctx}/css/trunk/JSPFileUpload/select2_metro.css"
		rel="stylesheet">
	<!-- 展示相关 -->
	<link href="${ctx}/css/trunk/JSPFileUpload/jquery-ui-1.10.3.custom.css"
		rel="stylesheet">
	<link href="${ctx}/css/trunk/JSPFileUpload/bootstrap-tokenfield.css"
		rel="stylesheet">
	<link href="${ctx}/css/trunk/JSPFileUpload/selectize.default.css"
		rel="stylesheet">
	<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
	<!-- 备件相关结束 -->
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/font-awesome.css">
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/animate.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/style.css" rel="stylesheet">
    <!-- stickUp fixed css -->
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/stickup.css">
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/stickmenu.css">
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/steps.css">
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/toastr.min.css">
    <!-- index_css  -->
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/iconfont.css" >
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/table.css" />
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/input2.css" />
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/see2.css" />
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/spv2.css" />
    <link rel="stylesheet" href="${ctx}/static_res/trans/css/spv/jkresponsivegallery2.css" />


</head>

<body >
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<script type="text/javascript">
	var ctx = "${ctx}";
	var source = "${source}";
	var handle = "${handle}";
	if ("${idList}" != "") {
		var idList = eval("(" + "${idList}" + ")");
	} else {
		var idList = [];
	}
</script>
            <div class="row">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox-content space30" >
                        <div class="agree-tile"> 资金入账申请  </div>
                        <div class="info_content">
                            <div class="line">
                                <p>
                                    <label> 产品类型  </label>
                                    <span class="info_one"  id="prdCode" >${spvBaseInfoVO.toSpv.prdCode==1?"光大四方资金监管":"" }</span>
                                </p>
                                <p>
                                    <label>
                                        	监管金额
                                    </label>
                                    <span class="info_one" id="amount">${spvBaseInfoVO.toSpv.amount}万元人民币</span>
                                </p>

                                <p>
                                    <label>
                                     	   物业地址
                                    </label>
                                    <span id="prAddr" >${spvBaseInfoVO.toSpvProperty.prAddr}</span>
                                </p>

                            </div>
                            <div class="line">
                                <p>
                                    <label>
                                      	  收款人名称
                                    </label>
                                    <span class="info_one" id="spvAccountName" >${spvBaseInfoVO.toSpvAccountList[2].name}</span>
                                </p>

                                <p>
                                    <label>
                                        	收款人账户
                                    </label>
                                    <span class="info_one" id="spvAccountCode">${spvBaseInfoVO.toSpvAccountList[2].account}</span>
                                </p>

                                <p>
                                    <label>
                                        	收款人开户行
                                    </label>
                                    <span class="info" id="spvAccountBank" >${spvBaseInfoVO.toSpvAccountList[2].bank}</span>
                                </p>
                            </div>
                        </div>
                        <div class="mt20">
                            <div class="agree-tile">
                                	入账申请信息
                            </div>
                        <form class="form-inline table-capital" id="teacForm" >
                        
                        <input type="hidden" name="type" value="addCashFlow" />
                        <input type="hidden" name="prdCode" value="${spvBaseInfoVO.toSpv.prdCode==1?"光大四方资金监管":"" }" />
                        <input type="hidden" name="amount" value="${spvBaseInfoVO.toSpv.amount}" />
                        <input type="hidden" name="prAddr" value="${spvBaseInfoVO.toSpvProperty.prAddr}" />
                        <input type="hidden" name="spvAccountName" value="${spvBaseInfoVO.toSpvAccountList[2].name}" />
                        <input type="hidden" name="spvAccountCode" value="${spvBaseInfoVO.toSpvAccountList[2].account}" />
                        <input type="hidden" name="spvAccountBank" value="${spvBaseInfoVO.toSpvAccountList[2].bank}" />
                        <input type="hidden" name="spvConCode" value="${spvBaseInfoVO.toSpv.spvCode}" />
                        <input type="hidden" name="caseCode" value="${spvBaseInfoVO.toSpv.caseCode}" />
                        
                         <%-- 流程相关 --%>
						<input type="hidden" id="taskId" name="taskId" value="" />
						<input type="hidden" id="instCode" name="instCode" value="" />
						<input type="hidden" id="urlType" name="source" value="" />
						<input type="hidden" id="handle" name="handle" value="addCashFlow" />
                         <%-- 保存数据返回的pkid --%>
						<input type="hidden"  id="toSpvCashFlowApplyPkid" name="toSpvCashFlowApplyPkid" value="" />
						<input type="hidden"  id="ToSpvCashFlowPkid" name="ToSpvCashFlowPkid" value="" />
						<input type="hidden"  id="ToSpvReceiptPkid" name="ToSpvReceiptPkid" value="" />
						
                            <div class="table-box" >
                                <table class="table table-bordered customerinfo">
                                    <thead>
                                        <th style="width: 100px;">付款人姓名</th>
                                        <th>付款人账户</th>
                                        <th style="width: 100px;">入账金额</th>
                                        <th style="width: 120px;">贷记凭证编号</th>
                                        <th>付款方式</th>
                                        <th>凭证附件</th>
                                        <th>操作</th>
                                    </thead>
                                    <tbody id="addTr">
                                    </tbody>
                                    	<!-- 附件上传 -->
                                    	
                                    	<td>
                                            <c:choose>
					<c:when test="${accesoryList!=null}">
						<c:forEach var="accesory" items="${accesoryList}"
							varStatus="status">
							<div class="" id="fileupload_div_pic">
								<form id="fileupload"
									action="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"
									method="POST" enctype="multipart/form-data">
									<noscript>
										<input type="hidden" name="redirect"
											value="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload">
										<%-- <input type="hidden" id="preFileCode" name="preFileCode"
											value="${accesory.type }"> --%>
									</noscript>
										<div class="" >
											<div role="presentation" >
												<div id="picContainer${accesory.pkid }" class="files"
													data-toggle="modal-gallery" data-target="#modal-gallery"></div>
												<a id="chandiaotuBtn" class="response" href="${ctx }/static_res/trans/img/uplody01.png"  title="凭证3">
												<button type="button" class="btn btn-sm btn-default">凭证3
                                                <c:if test="${empty handle or handle eq 'apply' }"><i class="icon iconfont icon_x">&#xe60a;</i></c:if>
                                                <c:if test="${handle eq 'directorAduit' or handle eq 'financeAduit' or handle eq 'financeSecondAduit' or handle eq 'cashFlowOut' }"><i class="icon iconfont icon_y">&#xe635;</i></c:if>
                                                </button>
												</a>
												<span class="btn_file">
                                                    <input id="picFileupload${accesory.pkid }" type="file" class="file"
													name="files[]" multiple
													data-url="<aist:appCtx appName='shcl-filesvr-web'/>/servlet/jqueryFileUpload"
													data-sequential-uploads="true">
                                                    <img class="bnt-flie" src="${ctx }/static_res/trans/img/bnt-flie.png" alt="" />
												</span>
											</div>
										</div>
								</form>
							</div>
		
							<div class="row-fluid">
								<div class="">
									<script id="templateUpload${accesory.pkid }" type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv1" class="template-upload fade row-fluid span2 in" style="height:80px;border:1px solid #ccc;margin-left:10px;margin-bottom:20px;line-height:80px;text-align:center;border-radius:4px;float:left;">
									<!--图片缩图  -->
							        <div class="preview"><span class="fade"></span></div>
									<!--  错误信息 -->
							        {% if (file.error) { %}
							            <div class="error span12" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
							        {% } else if (o.files.valid && !i) { %}
									<!-- 单个对应的按钮  -->
							            <div class="start span1" style="display: none">
										{% if (!o.options.autoUpload) { %}
							                <button class="btn">
							                    <i class="icon-upload icon-white"></i>
							                    <span>上传</span>
							                </button>
							            {% } %}
										</div>
							        {% } else { %}
							            <div class="span1" colspan="2"></div>
							        {% } %}
							        <div class="cancel" style="margin-top:-125px;margin-left:85%;">
									{% if (!i) { %}
							            <button class="btn red" style="width:20px;height:20px;border-radius:80px;line-height:20px;text-align:center;padding:0!important;">
							                <i class="icon-remove"></i>
							            </button>
							        {% } %}
									</div>
							    </div>
							{% } %}
						</script>
									<script id="templateDownload${accesory.pkid }"
										type="text/x-tmpl">
							{% for (var i=0, file; file=o.files[i]; i++) { %}
							    <div name="allPicDiv1" class="template-download fade row-fluid span2" style="height:80px;border:1px solid #ccc;margin-bottom:20px;margin-left:10px;line-height:80px;text-align:center;border-radius:4px;float:left;">
							        {% if (file.error) { %}
							            <div class="error span2" colspan="2"><span class="label label-important">错误</span> {%=file.error%}</div>
							        {% } else { %}
							            <div class="preview span12">
										<input type="hidden" name="preFileAdress" value="{%=file.id%}"></input>
										<%-- <input type="hidden" name="picTag" value="${accesory.type }"></input>--%>
										<input type="hidden" name="picName" value="{%=file.name%}"></input>
							            {% if (file.id) { %}
                                              {% if (((file.name).substring((file.name).lastIndexOf(".")+1))=='tif') { %}
							               		<img src="${ctx }/img/tif.png" alt="" width="80px" height="80px">
                                              {% } else { %}
 												 <img src="${imgweb}/filesvr/downLoad?id={%=file.id%}" alt="" width="80px" height="80px">
  											  {% } %}
							            {% } %}</div>
							        {% } %}
							        <div class="delete span2" style="margin-left:85%;margin-top:-120px;">
							           <button class="btn red" style="line-height:10px;width:30px;padding:0;height:30px;text-align:center;border-radius:30px!important;">
							                <i class="icon-remove"></i>
							            </button>
							        </div>
							    </div>
							{% } %}
						</script>
								</div>
							</div>
						</c:forEach>
		
						<div class="row-fluid" style="display: none;">
							<div class="span4">
								<div class="control-group">
									<a class="btn blue start" id="startUpload"
										style="height: 30px; width: 50px"> <i
										class="icon-upload icon-white"></i> <span>上传</span>
									</a>
								</div>
							</div>
						</div>
					</c:when>
				</c:choose>
                                               <%--  <a class="response" href="${ctx }/static_res/trans/img/uplody01.png" title="凭证3"><button type="button" class="btn btn-sm btn-default" >凭证3<i class="icon iconfont icon_x">&#xe60a;</i></button></a>
                                                <a class="response" href="${ctx }/static_res/trans/img/uplody02.png" title="凭证4"><button type="button" class="btn btn-sm btn-default" >凭证4<i class="icon iconfont icon_x">&#xe60a;</i></button></a> --%>
                                                <%-- <span class="btn_file">
                                                    <input type="file" class="file" />
                                                    <img class="bnt-flie" src="${ctx }/static_res/trans/img/bnt-flie.png" alt="" />
                                                </span> --%>
                                            </td>
                                    	
                                    	<!-- 附件上传 -->
                                    
                                </table>
                                <div class="form-btn">
                            <div class="text-center">
                                <button type="button" onclick="saveRe()" class="btn btn-success mr15">保存</button>
                                <button type="button" onclick="rescCallbocak()"  class="btn btn-default mr15">关闭</button>
                                <a onclick="sumbitRe()" class="btn btn-success">提交</a>
                            </div>
                        </div>
                            </div>
                        </form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- main End -->

        </div>
    </div>
<!-- Mainly scripts -->
<script src="${ctx}/static/js/jquery-2.1.1.js"></script>
<script src="${ctx}/static/trans/js/spv/spvReDetails.js"></script>
<script src="${ctx}/static/js/bootstrap.min.js"></script>
<script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>


  <!-- 上传附件相关 --> 
   <%--  <script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/tmpl.min.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/load-image.min.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-fp.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-ui.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
	<script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script> 
	<script src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script>  --%>
	<!-- 上传附件 结束 -->
	
	<!-- 上传附件相关 --> <script src="${ctx}/js/trunk/JSPFileUpload/app.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.ui.widget.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/tmpl.min.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/load-image.min.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-fp.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.fileupload-ui.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/clockface.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.inputmask.bundle.min.js"></script>
	<script
		src="${ctx}/js/trunk/JSPFileUpload/jquery.input-ip-address-control-1.0.min.js"></script>
	<script src="${ctx}/js/trunk/JSPFileUpload/jquery.multi-select.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/form-fileupload.js"></script>

	<script src="${ctx}/js/trunk/JSPFileUpload/aist.upload.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jssor.js"></script> <script
		src="${ctx}/js/trunk/JSPFileUpload/jssor.slider.js"></script> <!-- 上传附件 结束 -->
	
	<!-- 附件保存修改相关 --> <script src="${ctx}/static_res/trans/js/spv/attachmentPostIn.js"></script>

<!-- Custom and plugin javascript -->
    <script src="${ctx}/static_res/trans/js/spv/inspinia.js"></script>
    <script src="${ctx}/static_res/trans/js/spv/pace.min.js"></script>
    <script src="${ctx}/static_res/trans/js/spv/jkresponsivegallery.js"></script>
    <script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script> 
    <script src="${ctx}/js/template.js" type="text/javascript"></script> <!-- stickup plugin -->




<script>

$(function() {
    $(".icon_x").click(function() {
        $(this).parent().parent().remove();
        return false;
    });


});
/* $('.response').responsivegallery(); */

function rescCallbocak(){
 	 /*   if($("#urlType").val() == 'myTask'){    	 
 		   window.opener.location.reload(); //刷新父窗口
     	   window.close(); //关闭子窗口.
	     }else{ */
	    	 window.location.href = ctx+"/spv/spvList";
	    // }
	}

</script>
<input type="hidden" id="ctx" value="${ctx}" />
</body>

</html>