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
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>回访打回
    </title>
    <link rel="stylesheet" href="<c:url value='/static/css/bootstrap.min.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/font-awesome/css/font-awesome.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/css/animate.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/css/style.css' />" />
    <!-- Data Tables -->
    <link rel="stylesheet" href="<c:url value='/static/css/plugins/dataTables/dataTables.bootstrap.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/css/plugins/dataTables/dataTables.responsive.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/css/plugins/dataTables/dataTables.tableTools.min.css' />" />
    <link href="<c:url value='/static/css/plugins/datapicker/datepicker3.css' />" rel="stylesheet">
    <link href="<c:url value='/static/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
    <!-- index_css -->
    <link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/btn.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/trans/css/workflow/caseDetail.css' />" />
    <link rel="stylesheet" href="<c:url value='/static/trans/css/workflow/details.css' />" />
    <link rel="stylesheet" href="<c:url value='/css/satis/addOutlist.css' />">
    <link rel="stylesheet" href="<c:url value='/css/transcss/comment/caseComment.css' />">
</head>

<body class="pace-done">
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
    <div id="wrapper">
    <!-- 主要内容页面 -->
    <nav id="navbar-example" class="navbar navbar-default navbar-static" role="navigation">
        <div id="isFixed" style="position: relative; top: 0px;" class="collapse navbar-collapse bs-js-navbar-scrollspy stuckMenu stickup-nav-bar scroll_nav">
            <ul class="nav navbar-nav scroll_content">
                <li class="menuItem active"><a href="#"> 基本信息 </a></li>
            </ul>
        </div>
    </nav>
    <div class="wrapper wrapper-content">
        <div class="row animated fadeInDown">
            <div class="scroll_box fadeInDown animated marginbot">
                <div class="top12 panel" id="basicInfo">
                    <c:if test="${toCase.caseProperty=='30003001'}">
                   		<div class="sign sign-red" ><span
                   		<c:if test="${toApproveRecord!=''}">
                  		class="hint hint-top" data-hint="${toApproveRecord}"
                  		</c:if> >无效</span></div>

                  	</c:if>
                   	<c:if test="${toCase.caseProperty=='30003002'}">
                   			<div class="sign sign-red">结案</div>
                    </c:if>
                 	<c:if test="${toCase.caseProperty=='30003005'}">
                  		<div class="sign sign-red ">
                  		<span
                   		<c:if test="${toApproveRecord!=''}">
                  		class="hint hint-top" data-hint="${toApproveRecord}"
                  		</c:if> >爆单</span>

                  		</div>
                  	</c:if>
                   	<c:if test="${toCase.caseProperty=='30003003' || toCase.caseProperty=='30003007' || toCase.caseProperty=='30003008'}">
                   		<div class="sign sign-red">在途</div>
	                   <div class="sign sign-blue">
	                   	<c:if test="${toCase.status=='30001001'}">
	                   		未分单
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001002'}">
	                   		已分单
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001003'}">
	                   		已签约
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001004'}">
	                   		已过户
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001005'}">
	                   		已领证
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001006'}">
	                   		未指定
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001007'}">
	                   		被合流
	                   	</c:if>
	                   </div>
                    </c:if>
                   	<c:if test="${toCase.caseProperty=='30003004'}">
                   		<div class="sign sign-red">挂起</div>
	                   <div class="sign sign-blue">
	                   	<c:if test="${toCase.status=='30001001'}">
	                   		未分单
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001002'}">
	                   		已分单
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001003'}">
	                   		已签约
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001004'}">
	                   		已过户
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001005'}">
	                   		已领证
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001006'}">
	                   		未指定
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001007'}">
	                   		被合流
	                   	</c:if>
	                   </div>
                    </c:if>
                  	<c:if test="${toCase.caseProperty=='30003006'}">
                  		<div class="sign sign-red">全部</div>
	                   <div class="sign sign-blue">
	                   	<c:if test="${toCase.status=='30001001'}">
	                   		未分单
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001002'}">
	                   		已分单
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001003'}">
	                   		已签约
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001004'}">
	                   		已过户
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001005'}">
	                   		已领证
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001006'}">
	                   		未指定
	                   	</c:if>
	                   	<c:if test="${toCase.status=='30001007'}">
	                   		被合流
	                   	</c:if>
	                   </div>
                   	</c:if>
                   	<c:if test="${caseDetailVO.loanType=='30004005'}">
                  		<div class="sign sign-yellow">税费卡</div>
                  	</c:if>           
                    <div class="panel-body">
                        <div class="ibox-content-head">
                            <h2 class="title">
                                案件基本信息
                            </h2>
                            <small class="pull-right">誉萃编号：${toCaseInfo.caseCode}｜中原编号：${toCaseInfo.ctmCode}</small>
                        </div>
						<div id="infoDiv infos" class="row">
							<div class="ibox white_bg">
								<div class="info_box info_box_one col-sm-4 ">
									<span>物业信息</span>
									<div class="ibox-conn ibox-text">
										<dl class="dl-horizontal">
											<dt>CTM地址</dt>
											<dd>${toPropertyInfo.ctmAddr}</dd>
											<dt>产证地址</dt>
											<dd>${toPropertyInfo.propertyAddr}</dd>
											<dt>层高</dt>
											<dd>${toPropertyInfo.locateFloor}／${toPropertyInfo.totalFloor}</dd>
											<dt>产证面积</dt>
											<dd>${toPropertyInfo.square}平方</dd>
											<dt>房屋类型</dt>
											<dd>
												<aist:dict id="propertyType" name="propertyType"
													display="label" dictType="30014"
													dictCode="${toPropertyInfo.propertyType}" />
											</dd>
										</dl>
									</div>
								</div>
								<div class="info_box info_box_two col-sm-5">
									<span>买卖双方</span>
									<div class="ibox-conn else_conn">
										<dl class="dl-horizontal col-sm-6">
											<dt>上家姓名</dt>
											<dd>
												<div id="seller"></div>
											</dd>
										</dl>
										<dl class="dl-horizontal col-sm-6">
											<dt>下家姓名</dt>
											<dd>
												<div id="buyer"></div>
											</dd>
										</dl>
									</div>
									<span>经纪人信息</span>
									<div class="ibox-conn else_conn_two ">
										<dl class="dl-horizontal">
											<dt>姓名</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${toCaseInfo.agentPhone}">
													${caseDetailVO.agentName}</a>
											</dd>
											<dt>所属分行</dt>
											<dd>${toCaseInfo.grpName }</dd>
											<dt>直管经理</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseDetailVO.mcMobile}">
													${caseDetailVO.mcName} </a>
											</dd>
										</dl>
									</div>
								</div>
								<div class="info_box info_box_three col-sm-3">
									<span>经办人信息</span>
									<div class="ibox-conn  ibox-text">
										<dl class="dl-horizontal">
											<dt>交易顾问</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseDetailVO.cpMobile}">
													${caseDetailVO.cpName} </a>
											</dd>
											<c:if test="${empty caseDetailVO.proList}">
												<dt>合作顾问</dt>
												<dd></dd>
											</c:if>
											<c:if test="${!empty caseDetailVO.proList}">
												<c:forEach items="${caseDetailVO.proList}" var="pro">
													<dt>合作顾问</dt>
													<dd>
														<a data-toggle="popover" data-placement="right"
															data-content="${pro.processorMobile}">
															${pro.processorName} </a>
													</dd>
												</c:forEach>
											</c:if>
											<dt>助理</dt>
											<dd>
												<a data-toggle="popover" data-placement="right"
													data-content="${caseDetailVO.asMobile}">
													${caseDetailVO.asName} </a>
											</dd>
										</dl>
									</div>
								</div>

							</div>
						</div>
                    </div>
                </div>

        <div class="row wrapper white-bg new-heading ">
            <div class="pl10">
            <h2 class="newtitle-big">
                签约打回
            </h2>
            </div>
        </div>
<form>
	<input type="hidden" id="urlType" name="urlType" value="${urlType}">
    <input type="hidden" id="taskId" name="taskId" value="${taskId}">
    <input type="hidden" id="instCode" name="instCode" value="${instCode}">
    <input type="hidden" id="caseCode" name="caseCode" value="${toCaseInfo.caseCode}">
    <input type="hidden" id="status" name="status" value="${satisfaction.status}">
    <input type="hidden" id="readOnly" name="readOnly" value="${readOnly}">
    <input type="hidden" id="pkid" name="pkid" value="${satisfaction.pkid}">
    <div class="ibox-content border-bottom clearfix space_box noborder">
        <div style="height: auto;">
		    <div class="mb15">
	           	<h2 class="newtitle title-mark">上传附件</h2>
	           	<div class="table-box" id="fileUploadContainer"></div>
	   		</div>	
		</div>
        <div id="caseCommentList" class="add_form"></div>
        <div class="form-btn">
               <div class="text-center">
               	   <c:if test="${satisfaction.status eq 3 and !readOnly}">
               	   		<a  class="btn btn-success btn-space" onclick="javascript:doSignFollow();">提交</a>
                   		<a class="btn btn-success btn-space" onclick="javascript:goBack();">取消</a>
               	   </c:if>
                   <c:if test="${satisfaction.status ne 3 or readOnly}">
                   		<a class="btn btn-success btn-space" onclick="javascript:goBack();">关闭</a>
                   </c:if>
               </div>
        </div>
     </div>
</form>     
                </div>
            </div>
        </div>
      </div>
        <content tag="local_script">
     	    <script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
        	<script	src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
        	<script	src="<c:url value='/js/template.js' />" type="text/javascript"></script>
       		<script	src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
        	<script	src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
        	<script src="<c:url value='/js/trunk/JSPFileUpload/aist.upload.js' />"></script>
        	<script src="<c:url value='/js/viewer/viewer.min.js' />"></script>
        	<script src="<c:url value='/js/common/xcConfirm.js' />"></script>
	        <script type="text/javascript">
	        var caseCode = $("#caseCode").val();
	        var urlType = $("#urlType").val();
	        var status = $("#status").val();
	        var readOnly = $("#readOnly").val()
	        
	        $(function(){
				$("#caseCommentList").caseCommentGrid({
					caseCode : caseCode,
					srvCode : "survey_s_return"
				});
	        	
			    $('#seller').append(generateSellerAndBuyer('${caseDetailVO.sellerName}', '${caseDetailVO.sellerMobile}'));
				$('#buyer').append(generateSellerAndBuyer('${caseDetailVO.buyerName}', '${caseDetailVO.buyerMobile}'));
				
				if(status != '3' || readOnly == 'true'){
		 	    	readOnlyForm();
		 	    }
	        })
			      
			/*动态生成上下家*/
			function generateSellerAndBuyer(name, phone){
	 			var nameArr = name.split('/');
	 			var phoneArr = phone.split('/');
	 			var str='';
	 			for (var i=0; i<nameArr.length; i++) {
	 				if(i%2==0){
	 					str += '<a data-toggle="popover" data-placement="right" data-content="'+phoneArr[i]+'">'+nameArr[i]+'</a>&nbsp;';
	 				}else{
	 					str += '<a data-toggle="popover" data-placement="right" data-content="'+phoneArr[i]+'">'+nameArr[i]+'</a><br/>';
	 				}
	 			}
	 			return str;
	 		}
	        
	        /*签约跟进*/
	        function doSignFollow(){
	        	var data = $("form").serializeArray();

	        	window.wxc.confirm("确定要提交跟进吗？",{"wxcOk":function(){
					$.ajax({
						url:ctx+"/satis/doSignFollow",
						method:"post",
						dataType:"json",
						data:data,
						beforeSend:function(){  
							$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
							$(".blockOverlay").css({'z-index':'9998'});
				        },
						success:function(data){
							 $.unblockUI();
							 if(data.success){
								 window.wxc.alert("操作成功！",{"wxcOk":function(){
									 goBack();
								   }
						   		 })
							 }else{
								 window.wxc.error(data.message);
							 } 
						 }
					})
				  }
				})
	        }
	        
	        /*页面返回*/
	        function goBack(){
	        	if(urlType == 'list')
					 window.location.href = ctx+"/satis/list";
				 else
					 window.location.href = ctx+"/task/myTaskList";
	        }
	        
	        /*只读表单*/
	        function readOnlyForm(){
	        	$("input:not('#caseComment'),select").prop("disabled",true);
	        }
	        </script>
	        </content>
	        <content tag="local_require">
		       <script>
		        var caseCode = '${toCaseInfo.caseCode}';
	       		var fileUpload;
			    require(['main'], function() {
					requirejs(['jquery','aistFileUpload','validate','grid','jqGrid','blockUI','steps','ligerui','aistJquery','poshytip','twbsPagination','bootstrapModal','modalmanager','eselect'],function($,aistFileUpload){
						fileUpload = aistFileUpload;
							fileUpload.init({
					    		caseCode : caseCode,
					    		partCode : "Survey",
					    		fileUploadContainer : "fileUploadContainer",
					    	});
						})
				    });
				</script>
        </content>
    </body>
    </html>