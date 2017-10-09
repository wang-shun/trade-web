<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@  taglib  uri="http://java.sun.com/jsp/jstl/functions"   prefix="fn"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Toastr style -->
<link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">
<!-- stickUp fixed css -->
<link href="<c:url value='/static/trans/css/common/hint.css' />" rel="stylesheet" />
<!-- Gritter -->
<link href="<c:url value='/js/plugins/gritter/jquery.gritter.css' />"	rel="stylesheet">
<link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
<link href="<c:url value='/font-awesome/css/font-awesome.css' />" rel="stylesheet">
<link href="<c:url value='/css/animate.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jQueryUI/jquery-ui-1.10.4.custom.min.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/jqGrid/ui.jqgrid.css' />" rel="stylesheet">
<link href="<c:url value='/css/plugins/iCheck/custom.css' />" rel="stylesheet">

<link href="<c:url value='/css/plugins/jasny/jasny-bootstrap.min.css' />"	rel="stylesheet">
<link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />"	rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />"	rel="stylesheet">
<link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />"	rel="stylesheet">
<link href="<c:url value='/css/transcss/comment/caseComment.css' />"	rel="stylesheet">
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />"	rel="stylesheet" />
<link href="<c:url value='/static/trans/css/workflow/caseDetail.css' />" rel="stylesheet" />
<link href="<c:url value='/static/trans/css/workflow/details.css' />" rel="stylesheet" />
<link href="<c:url value='/js/viewer/viewer.min.css' />" rel="stylesheet" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />" />
<link href="<c:url value='/css/common/subscribe.css' />" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />" />
<link href="<c:url value='/static/trans/css/workflow/details.css' />" rel="stylesheet" />
<link href="<c:url value='/js/viewer/viewer.min.css' />" rel="stylesheet" />
</head>
<body>
<style>
.table thead tr th {
    background-color: #4bccec;
    font-size: 14px;
    font-weight: normal;
    color: #fff;
}
</style>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="caseCode" value="${evalVO.caseCode}" />
	<%-- <input type="hidden" id="ctm" value="${toCaseInfo.ctmCode}" />
	<input type="hidden" id="Lamp1" value="${Lamp1}" />
	<input type="hidden" id="Lamp2" value="${Lamp2}" />
	<input type="hidden" id="Lamp3" value="${Lamp3}" />
	<input type="hidden" id="Lamp3" value="${Lamp3}" />
	<input type="hidden" id="activityFlag" value="${toCase.caseProperty}" />
	
	<input type="hidden" id="instCode" value="${toWorkFlow.instCode}" />
	<input type="hidden" id="srvCodes" value="${caseDetailVO.srvCodes}" />
	<input type="hidden" id="processDefinitionId"
		value="${toWorkFlow.processDefinitionId}" /> --%>
	<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>

	<script>
		var resourceDistributionBtn = false;
		<%if (request.getAttribute("msg") == null || request.getAttribute("msg") == "") {%>
		<%} else {%>
			window.wxc.alert("<%=request.getAttribute("msg")%>");
		<%}%>
		<shiro:hasPermission name="TRADE.CASE.DISTRIBUTION">
		 resourceDistributionBtn = true;
		</shiro:hasPermission>
	</script>

	<jsp:include page="/WEB-INF/jsp/common/caseBaseInfo.jsp"></jsp:include>
	<!-- 主要内容页面 -->
				<div class="panel-body">
					<!-- 结算信息 -->
					<div class="panel-body ibox-content">
						<div class="ibox white_bg" id="content">
							<div class="info_box info_box_one col-sm-12 ">
						  		<span>结算信息</span>
									
									<div class="height_line"></div>
									<div class="row font-family" style=" margin-top:10px;">
										<label class="col-sm-4 control-label">评估公司：${evalVO.finOrgId}</label>
										<label class="col-sm-3 control-label">评估费实收金额:${evalVO.evalRealCharges}&nbsp;&nbsp;元</label>
										<label class="col-sm-3 control-label">评估申请日期:${evalVO.applyDate}</label>
									</div>
									<div class="height_line1"></div>
									<div class="row font-family"  style=" margin-top:50px;">
										<label class="col-sm-4 control-label">出具评估报告的日期:${evalVO.issueDate}</label>
										<label class="col-sm-3 control-label">评估值：${evalVO.evaPrice}</label>
									</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 填写结算任务-->
				<div class="panel " id="serviceFlow">
					<div class="panel-body ibox-content">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#settings" data-toggle="tab">填写结算任务</a>
							</li>
						</ul>

						<div class="tab-content">
								 <form  action="${ctx}/caseMerge/saveCaseInfo/${flag}"  method="post"  id="saveCaseInfo">
									<div class="tab-pane active fade in" id="settings">
										<div class="jqGrid_wrapper row">
														<div class="form_content">
													
															<label class="control-label sign_left_small">* 原结算费用 </label> <input
																class="teamcode input_type" 
																placeholder="" value="${evalVO.evalComAmount}　元" id="endCost" name="endCost" readonly="readonly" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														
														
															<label >* 修改结算费用为</label> <input 
																class="teamcode input_type" 
																placeholder="" value="" id="editEndCost" name="editEndCost" />
														</div>
													</div>
										<div class="jqGrid_wrapper row">
											<div class="form_content" style=" margin-top:40px;">
												<label class="control-label sign_left_small">* 修改原因 </label>
													<!-- <textarea class="teamcode input_type" rows="5" cols="150"></textarea> -->
												 <input
													class="teamcode input_type"  style="width: 435px;"
													placeholder="" value="" id="updateCause" name="updateCause" />
											</div>
										</div>
									</div>
								</form>
							
							<div class="tab-pane fade" id="messages">
								<c:if test="${not empty toWorkFlow.processDefinitionId}">
									<c:if test="${not empty toWorkFlow.instCode}">
										<iframe frameborder="no" border="0" marginwidth="0"
											marginheight="0" scrolling="auto" allowtransparency="yes"
											overflow:auto;
												style="height: 1068px; width: 100%;"
											src="<aist:appCtx appName='aist-activiti-web'/>/diagram-viewer/index.html?processDefinitionId=${toWorkFlow.processDefinitionId}&processInstanceId=${toWorkFlow.instCode}"></iframe>
									</c:if>
								</c:if>
							</div>
							<div class="tab-pane fade" id="home">
								<table id="caseCommenTable"></table>
								<div id="caseCommenPager"></div>
							</div>
						</div>
					</div>
				</div>


				<!-- 修改记录 -->
				<div class="panel " id="aboutInfo" style="min-height: 800px;">
					
					<div class="panel-body ibox-content">
						<ul class="nav nav-tabs">
							<li>修改记录</li>
						</ul>
						<div class="tab-content">
							<div  >
                                <div >
                                    <table class="table table_blue table-striped table-bordered table-hover" id="editable" >
                                         <thead>
                                            <tr>
                                                <th>
                                                   修改次数
                                                </th>
                 
                                                <th>
                                                    修改时间
                                                </th>
                                                <th>
                                                   修改原因
                                                </th>
                                              
                                                <th>
                                                   审批人
                                                </th>
                                                <th>
                                                  审批时间
                                                </th>
                                                <th>
                                                   审批意见
                                                </th>
                                               
                                            </tr>
                                        </thead>
                                        <tbody class="table_content_pre">
                                      		<c:forEach items="${updateLogList}" var="item" varStatus="status">  
											  <tr >  
											    <td class="center"><span class="center">${status.index+1}</span></td>  
											    <td><fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>  
											    <td>${item.updateReason}</td>  
											    <td>审批人</td>  
											    <td>审批时间</td>
											    <td>审批意见</td>
											  </tr>  
											</c:forEach> 
                                        </tbody>
                                    </table> 
                                </div>
				                <div class="modal-footer">
									<button type="button" class="btn btn-primary" id="newCaseInfoSave" onclick="javascript:closeEval()"
										>关闭</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button class="btn btn-primary" id="submit" >提交</button>
								</div>
								<!-- <div class="add_btn text-center mt20">
								   	<div class="more_btn">
							   	    	<button id="closeButton" type="button" class="btn btn_blue">关闭</button>
									</div>
								</div> -->
                            </div> 
						</div>
					</div>
				</div>
				
			
	
	<content tag="local_script"> <!-- Peity -->
	<!-- 公共页面caseBaseInfo.js引用 -->
	<script src="<c:url value='/js/trunk/case/caseBaseInfo.js' />"></script>
	<script	src="<c:url value='/js/plugins/peity/jquery.peity.min.js' />"></script>
	<!-- jqGrid -->
	<script src="<c:url value='/js/plugins/jqGrid/i18n/grid.locale-en.js' />"></script>
	<script src="<c:url value='/js/plugins/jqGrid/jquery.jqGrid.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/jquery-ui/jquery-ui.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/iCheck/icheck.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
	<script	src="<c:url value='/js/plugins/ionRangeSlider/ion.rangeSlider.min.js' />"></script>
	<script src="<c:url value='/js/plugins/jasny/jasny-bootstrap.min.js' />"></script>
	<script src="<c:url value='/js/jquery.blockui.min.js' />"></script>
	<%-- <script src="<c:url value='/transjs/task/follow.pic.list.js' />"></script> --%>
	<%-- <script src="<c:url value='js/trunk/case/showCaseAttachment.js' />"></script> --%>
	<script src="<c:url value='/js/viewer/viewer.min.js' />"></script>
	<script src="<c:url value='/js/trunk/case/showCaseAttachmentByJagd.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/jquery.validate.min.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/common/additional-methods.js' />"></script>
	<script src="<c:url value='/js/plugins/validate/common/messages_zh.js' />"></script>
	<script src="<c:url value='/js/stickUp.js' />"></script>
	<script	src="<c:url value='/js/plugins/toastr/toastr.min.js' />"></script>
	<!-- 放款监管信息  -->
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
	<script	src="<c:url value='/transjs/task/caseflowlist.js' />"></script>
	<script	type="text/javascript" src="<c:url value='/js/jquery.json.min.js' />"></script>
	<script	src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
	<script	src="<c:url value='/js/template.js' />" type="text/javascript"></script>
	<script	src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
	<script	src="<c:url value='/js/trunk/comment/caseComment.js' />"></script>
	<!-- 各个环节的备注信息  -->
	
	<jsp:include	page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
	<script>
	/**关闭页面*/
	function closeEval(){
		window.close();
		//window.location.href = ctx+"/eval/settle/evalWaitEndList";
	}
	//提交按钮
	$("#submit").click(function(){
		var evalAccountShowVO = {
				caseCode : $("#caseCode").val(),
				settleFee : $("#editEndCost").val(),
				updateReason:$("#updateCause").val()
			};
		evalAccountShowVO = $.toJSON(evalAccountShowVO);
	   	  $.ajax({
					cache:false,
					async:true,
					type:"POST",
					dataType:"json",
					contentType: "application/json; charset=utf-8" ,
					url:ctx+"/eval/settle/evalEndUpdateFee",
					data:evalAccountShowVO,
					success:function(data){
						if(data.success){
							location.href = "../settle/evalWaitEndList"
						}else{
							window.wxc.error(data.message);
						}
					},
					
			}); 
   	  	
     });
	
	/* $("#newCaseInfoSave").click(function(){	
		
		$("#saveCaseInfo").submit();
	});
		var caseCode = $("#caseCode").val();
		var ctmCode = $("#ctm").val();
		var url = "/quickGrid/findPage";
		var ctx = $("#ctx").val();
	    var r1 =false;
	    var changeTaskRole=false;
	    var serivceDepId='${serivceDefId}';
	    var loanReqType="${loanReqType}";
	    <shiro:hasPermission name="TRADE.CASE.DEALPRICE:SHOW">
			r1 = true;
		</shiro:hasPermission>
		<shiro:hasPermission name="TRADE.CASE.TASK:ASSIGN">
			changeTaskRole=true;
		</shiro:hasPermission>
		var isNewFlow =${isNewFlow};
		var isCaseManager=${isCaseManager};
	      $('#seller').append(generateSellerAndBuyer('${caseDetailVO.sellerName}', '${caseDetailVO.sellerMobile}'));
 	      $('#buyer').append(generateSellerAndBuyer('${caseDetailVO.buyerName}', '${caseDetailVO.buyerMobile}'));

 	     function getCurrentDate(){
 	    	var d = new Date()
 	    	var vYear = d.getFullYear();
 	    	var vMon = d.getMonth() + 1;
 	    	var vDay = d.getDate();

 	    	var str = vYear + "-" + (vMon<10 ? "0" + vMon : vMon) + "-" + (vDay<10 ? "0"+ vDay : vDay);
	    	return str;
    	  }

 	      $("#btnSave").click(function(){
 	    	  var content = $("#bizwarnForm-modal-form input[name=content]").val();
 	    	  var caseCode = $("#bizwarnForm-modal-form input[name=caseCode]").val();
 	    	  var caseId = $("#bizwarnForm-modal-form input[name=caseId]").val();

 	    	 $.ajax({
					cache:false,
					async:true,
					type:"POST",
					dataType:"json",
					url:ctx+"/bizwarn/addBizWarnInfo",
					data:{caseCode:caseCode,content:content},
					success:function(data){
						if(data.success){
							location.href = "../case/caseDetail?caseId=" + caseId;
						}else{
							window.wxc.error('添加失败');
						}
					}
				});

 	      });

 	      $("#add").click(function(){
 	    	  $("#bizwarnForm-modal-form").modal("show");
 	      });

 	     $("#edit").click(function(){
 	    	 var caseCode = $("#editBizwarnForm-modal-form input[name=caseCode]").val();


 	    	 var status;
 	    	 var content;
 	    	 $.ajax({
					cache:false,
					async:false,
					type:"POST",
					dataType:"json",
					url:ctx+"/bizwarn/getBizWarnInfo",
					data:{caseCode:caseCode},
					success:function(data){
						status = data.status;
						content = data.content
					}
				});

 	    	 	if(status == "1"){
 	    	 		window.wxc.alert("解除状态不能修改商贷预警信息！");
 	    	 		return false;
 	    	 	}

 	    	  $("#editBizwarnForm-modal-form input[name=content]").val(content);
	    	  $("#editBizwarnForm-modal-form").modal("show");
	      });

 	     $("#btnEdit").click(function(){
 	    	 var content = $("#editBizwarnForm-modal-form input[name=content]").val();
	    	 var caseCode = $("#editBizwarnForm-modal-form input[name=caseCode]").val();
	    	 var caseId = $("#editBizwarnForm-modal-form input[name=caseId]").val();

	    	 $.ajax({
					cache:false,
					async:true,
					type:"POST",
					dataType:"json",
					url:ctx+"/bizwarn/editBizWarnInfo",
					data:{caseCode:caseCode,content:content},
					success:function(data){
						if(data.success){
							location.href = "../case/caseDetail?caseId=" + caseId;
						}else{
							window.wxc.error('修改失败');
						}
					}
				});
 	     });

 	      $("#relieve").click(function(){
 	    	  var status = $("input[name=status]").val();

 	    	 window.wxc.confirm("是否确定解除？",{"wxcOk":function(){
  				$.ajax({
  					cache:false,
  					async:true,
  					type:"POST",
  					dataType:"json",
  					url:ctx+"/bizwarn/relieve",
  					data:{caseCode:caseCode},
  					success:function(data){
  						if(data.success){
  							$("#spnStatus").html("解除");
  							$("#spnRelieveTime").html(getCurrentDate());
  							$("#relieve").hide();
  						}else{
  							window.wxc.error('解除失败');
  						}
  					}
  				});
 	    	}});
 	      }); */

		//jqGrid 初始化
		/* $("#gridTable").jqGrid({
			url : ctx+url,
			mtype : 'GET',
			datatype : "json",
			height : 250,
			autowidth : true,
			shrinkToFit : true,
			rowNum : 50,
			colNames : [ '附件类型','附件名称','附件路径','上传时间','操作'],
			colModel : [ {
				name : 'ATT_TYPE',
				index : 'ATT_TYPE',
				align : "center",
				width : 20,
				resizable : false
			},{
				name : 'ATT_NAME',
				index : 'ATT_NAME',
				align : "center",
				width : 20,
				resizable : false
			}, {
				name : 'ATT_PATH',
				index : 'ATT_PATH',
				align : "center",
				width : 20,
				resizable : false
				//formatter : linkhouseInfo
			}, {
				name : 'UPLOAD_DATE',
				index : 'UPLOAD_DATE',
				align : "center",
				width : 20,
				resizable : false
			},{
				name : 'READ',
				index : 'READ',
				align : "center",
				width : 20,
				resizable : false
			}],
			multiselect: true,
			pager : "#gridPager",
			//sortname:'UPLOAD_DATE',
	        //sortorder:'desc',
			viewrecords : true,
			pagebuttions : true,
			multiselect:false,
			hidegrid : false,
			recordtext : "{0} - {1}\u3000共 {2} 条", // 共字前是全角空格
			pgtext : " {0} 共 {1} 页",
			gridComplete:function(){
				var ids = jQuery("#gridTable").jqGrid('getDataIDs');
				for (var i = 0; i < ids.length; i++) {
    				var id = ids[i];
    				var rowDatas = jQuery("#gridTable").jqGrid('getRowData', ids[i]); // 获取当前行

    				var link = "<button  class='btn red' onclick='addAttachmentReadLog(\""+ctx+"\",\""+ctmCode+"\",\""+caseCode+"\",\""+rowDatas['ATT_NAME']+"\",\""+rowDatas['ATT_PATH']+"\")'>查看附件</a>";

    				//var detailBtn = "<button  class='btn red' id='alertOper' onclick='openLoan(\""+ctx+"\",\""+rowDatas['pkId']+"\")' style='width:90px;'>详细</button>";

    				jQuery("#gridTable").jqGrid('setRowData', ids[i], { READ: link});

    				var attType = rowDatas["ATT_TYPE"];
    				if(!r1 && attType =='买卖居间协议') {
   					   $("#gridTable").jqGrid("delRowData", id);
    				}
				}
			},
			postData : {
				queryId : "followPicListQuery",
				argu_ctmCode : ctmCode
			}

		}); */

		//附件连接
		function linkhouseInfo(cellvalue, options, rowObject){
			 var link = '<a href="" target="_black" onclick="addAttachmentReadLog('+cellvalue+')">'+cellvalue+'</a>';
			 return link;
		}

		function addAttachmentReadLog(ctx,ctmCode,caseCode,attachName,attachPath) {
			var tsAttachmentReadLog = {
				 	'caseCode':caseCode,
				 	'ctmCode':ctmCode,
				 	'attachmentName':attachName,
				 	'attachmentAddress':attachPath
			};
			//tsAttachmentReadLog=$.toJSON(tsAttachmentReadLog);

			$.ajax({
				type : 'post',
				cache : false,
				async : true,
				url : ctx+'/log/addAttachmentReadLog',
				data : tsAttachmentReadLog,
				dataType : "json",
				success : function(data) {
					//alert("记录日志成功");
				},
				error : function(errors) {
					//alert("记录日志失败");
					return false;
				}
			});

			window.open(attachPath);
			/*var url=ctx+"/api/imageshow/imgShow?img="+attachPath;
			window.open(encodeURI(encodeURI(url)));*/
		}

		//加载页面获取屏幕高度
 		$(function(){

			var caseCode = $('#caseCode').val();

			$("#caseCommentList").caseCommentGrid({
				caseCode : caseCode,
				srvCode : null
			});
/* 	        var h= window.screen.availHeight;
			$("#scroll").css("height",h-h*0.32); */
			  //点击浏览器任何位置隐藏提示信息
		      $("body").bind("click",function(evt){
	              if($(evt.target).attr("data-toggle")!='popover'){
	             	$('a[data-toggle="popover"]').popover('hide');
	              }
	          });
	    	//隐藏头部信息
 	        window.onscroll = function(){
	        	if(document.body.scrollTop>62){
	        		$("#isFixed").css("position","fixed");
	        		$("#isFixed").addClass("istauk");
	        	/* 	$(".wrapper").css("margin-top","px"); */
	        	}else{
	        		$("#isFixed").css("position","relative");
	        		$("#isFixed").removeClass("istauk");

	        	}
	        }

		});

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
        jQuery(function($) {
            $(document).ready( function() {
               $('.stickup-nav-bar').stickUp({
                // $('.col-lg-9').stickUp({
                                    parts: {
                                      0:'basicInfo',
                                      1:'serviceFlow',
                                      2:'aboutInfo'
                                    },
                                    itemClass: 'menuItem',
                                    itemHover: 'active',
                                    marginTop: 'active'
                                  });
            });
        });
        /**
         * 新建外单案件
         */
        $('#addLiushui').click(function() {	
        	window.location.href = ctx+"/caseMerge/addLiushui?caseCode="+$('#caseCode').val();
        });



	</script>
	<script
		id="template_successList" type="text/html">
	{{each rows as item index}}
    	<tr>
       		 <td>
                                                {{item.shareTime}}
                                                    
                                                </td>
                                                
                                                <td>
                                                    {{item.bizCode}}
                                                </td>
                                                <td>
                                                    {{item.shareBase}}
                                                </td>
                                                <td>
                                                   {{item.sharesRate}}
                                                </td>
                                                <td>
                                                    {{item.shareAmount}}
                                                </td>
                                                <td>
                                                    {{item.createBy}}
                                                </td>
                                                <td>
                                                	{{item.treamId}}
                                                </td>
                                                <td>
				{{if item.status !=null && item.status == 'GENERATED'}}有效
				{{/if}}
				{{if item.status !=null && item.status == 'CANCELED'}}
				无效<a href="#" class="demo-left" title="案件重启<br/>" style="margin-left:2px;"><i class="icon iconfont" style="font-size: 20px;color:#808080;">&#xe609;</i></a>
				{{/if}}
                                                </td>
                                            </tr>                                        
	{{/each}}
	</script>
	
</content>

</body>
</html>

