<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>E+贷款</title>

    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="${ctx}/static/css/animate.css" rel="stylesheet">
    <link href="${ctx}/static/css/style.css" rel="stylesheet">

    <!-- stickUp fixed css -->
    <link href="${ctx}/static/css/plugins/stickup/stickup.css" rel="stylesheet">
    <link href="${ctx}/static/trans/css/common/stickDash.css" rel="stylesheet">

    <link href="${ctx}/static/css/plugins/aist-steps/steps.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/toastr/toastr.min.css" rel="stylesheet">
    <link href="${ctx}/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <!-- index_css  -->

    <link href="${ctx}/static/trans/css/eloan/eloan/eloan.css" rel="stylesheet"/>
    <link href="${ctx}/static/trans/css/common/input.css" rel="stylesheet"/>
    <link href="${ctx}/static/trans/css/common/table.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" ">

</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
    <div id="wrapper">
            <!-- main Start -->
            <div class="row wrapper border-bottom nav_heading">
               <ul class="nav_bar">
                    <li class="">贷款申请任务</li>
                    <li class="active">面签任务</li>
                    <li class="">贷款放款任务</li>
                </ul>
            </div>
            <div class="row">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <!-- <div class="ibox"> -->
                    <div class="ibox-content" id="base_info">
                        <div class="main_titile" style="position: relative;">
		                           <h5>关联案件</h5>
		                           <div class="case_content">
		                           <div class="case_row">
		                               <div class="case_lump">
		                                   <p><em>案件编号</em><span class="span_one" id="content_caseCode">${caseCode}</span></p>
		                               </div>
		                               <div class="case_lump">
		                                   <p><em>产权地址</em><span class="span_two" id="content_propertyAddr">${propertyAddr}</span></p>
		                               </div>
		                           </div>
		                           <div class="case_row">
		                               <div class="case_lump">
		                                   <p><em>交易顾问</em><span class="span_one" id="content_processorId">${processorName}</span></p>
		                               </div>
		                               <div class="case_lump">
		                                   <p><em>上家姓名</em><span class="span_two" id="content_buyer">${sellerName}</span></p>
		                               </div>
		                           </div>
		                           <div class="case_row">
		                               <div class="case_lump">
		                                   <p><em>经纪人</em><span class="span_one" id="content_agentName">${agentName}</span></p>
		                               </div>
		                               <div class="case_lump">
		                                   <p><em>下家姓名</em><span class="span_two" id="content_seller">${buyerName}</span></p>
		                               </div>
		                           </div>
		                           </div>
		                 </div>
                        <div class="main_titile" style="position: relative;">
                            <h5>E+产品</h5>
                            <div class="case_content">
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>产品类型</em><span class="span_one">
                                       <aist:dict id="loanSrvCode" name="loanSrvCode" clazz="select_control sign_right_two"
						display="onlyLabel"  dictType="yu_serv_cat_code_tree" tag="Eloan" dictCode="${eloanCase.loanSrvCode}"
						ligerui='none'></aist:dict></span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>合作机构</em><span class="span_one" id="finOrgCode" name="finOrgCode" value="${eloanCase.finOrgCode}""></span></p>
                                   </div>
                                </div>
                                <div class="case_row">
                                <div class="case_lump">
									<p>
										<em>归属人</em><span class="span_one">${excutorName}</span>
									</p>
								</div>
                                   <div class="case_lump">
                                       <p><em>客户姓名</em><span class="span_one">${eloanCase.custName}</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>客户电话</em><span class="span_one">${eloanCase.custPhone}</span></p>
                                   </div>
                                </div>
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>申请金额</em><span class="span_one">${eloanCase.applyAmount}万</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>申请时间</em><span class="span_one"><fmt:formatDate value="${eloanCase.applyTime}" pattern="yyyy-MM-dd" /></span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>申请期数</em><span class="span_one">${eloanCase.month}月</span></p>
                                   </div>
                                </div>
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>转介人姓名</em><span class="span_one">${eloanCase.zjName}</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>转介人员工编号</em><span class="span_one">${eloanCase.zjCode}</span></p>
                                   </div>
                                </div>
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>产品部姓名</em><span class="span_one">${eloanCase.pdName}</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>产品部员工编号</em><span class="span_one">${eloanCase.pdCode}</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>分成比例贷款</em><span class="span_one">${eloanCase.pdPart}%</span></p>
                                   </div>
                                </div>
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>合作人姓名</em><span class="span_one">${eloanCase.coName}</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>合作人员工编号</em><span class="span_one">${eloanCase.coCode}</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>分成比例贷款</em><span class="span_one">${eloanCase.coPart}%</span></p>
                                   </div>
                                </div>
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>面签金额</em><span class="span_one">${eloanCase.signAmount}万</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>面签时间</em><span class="span_one"><fmt:formatDate value="${eloanCase.signTime}" pattern="yyyy-MM-dd" /></span></p>
                                   </div>
                                   <div class="case_lump">
                                   </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="ibox-content" id="zj_info">
                        <div class="main_titile">
                            <h5>主管审批</h5>
                        </div>
                        <form method="get" class="form_list" id="eloanSignForm">
                         <input type="hidden" name="caseCode" id="caseCode" value="${eloanCase.caseCode}"/>
                         <!-- 流程引擎需要字段 -->
					     <input type="hidden" id="taskId" name="taskId" value="${taskId }">
					     <input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}">
					     <input type="hidden" id="partCode" name="partCode" value="${taskitem}">
					     <!--  -->
					     <input type="hidden" id="eloanCode" name="eloanCode" value="${eloanCase.eloanCode}">
                       	 <ul class="form_lump">
                            <li>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">
                                                                                                      审批
                                    </label>
                                    <select name="approved" id="approved" class="select_control sign_right_two">
                                       <option value="1">同意</option>
                                       <option value="0">驳回</option>
                                    </select>
                                </div>
                            </li>
                        </ul>
                        <input type="button" class="btn btn-success submit_btn" value="确认"/>

                        </form>
                    </div>

                </div>
            </div>
            <!-- main End -->
    </div>

    <!-- Mainly scripts -->
     <content tag="local_script">
  <%--<script src="${ctx}/static/js/jquery-2.1.1.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js"></script>
    <script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <!-- ECharts.js -->
    <script src="${ctx}/static/js/echarts.min.js"></script> --%>



    <!-- Custom and plugin javascript -->
    <script src="${ctx}/static/js/inspinia.js"></script>
    <script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>

    <!-- stickup plugin -->
    <script src="${ctx}/static/js/plugins/stickup/stickUp.js"></script>
    <script src="${ctx}/static/trans/js/workbench/stickDash.js"></script>


    <!-- Toastr script -->
    <script src="${ctx}/static/js/plugins/toastr/toastr.min.js"></script>
    <script src="${ctx}/static/js/morris/morris.js"></script>
    <script src="${ctx}/static/js/morris/raphael-min.js"></script>

    <!-- index_js -->
    <script src="${ctx}/static/trans/js/demo/eloan/eloan.js"></script>
    <script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <script>
        $(document).ready(function () {
        	getBankList();
        	
        	$('.input-daterange').datepicker({
              	format : 'yyyy-mm-dd',
          		weekStart : 1,
          		autoclose : true,
          		todayBtn : 'linked',
          		language : 'zh-CN'
            });
            
            $('.submit_btn').click(function(){
            	saveEloanSignConfirm();
            })
        });
        
        /*获取银行列表*/
		function getBankList(){
			var finOrgCode = $("#finOrgCode").attr('value');
			var friend = $("#finOrgCode");
			friend.empty();
			 $.ajax({
			    url:ctx+"/manage/queryFin",
			    method:"post",
			    dataType:"json",
			    data:{"pcode":finOrgCode},
		    	success:function(data){
		    		if(data.bankList != null){
		    			for(var i = 0;i<data.bankList.length;i++){
		    				if(finOrgCode == data.bankList[i].finOrgCode) {
		    					friend.html(data.bankList[i].finOrgName);
		    				} 
		    			}
		    		}
		    	}
			  });
		}
        function saveEloanSignConfirm() {
			var jsonData = $("#eloanSignForm").serializeArray();
			var url = "${ctx}/eloan/saveEloanSignConfirm";
		 	$.blockUI({message:$("#salesLoading"),css:{border:0 }});
			$.ajax({
				cache : true,
				async : false,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				//contentType:"application/json",  
				data : jsonData,
				beforeSend : function() {
					$.blockUI({
						message : $("#salesLoading"),
						css : {
							'border' : 'none',
							'z-index' : '9999'
						}
					});
					$(".blockOverlay").css({
						'z-index' : '9998'
					});
				},
				complete : function() {
					$.unblockUI();
				},
				success : function(data) {
					alert(data.message);
					window.close();
					window.opener.callback();
				},
				error : function(errors) {
					alert("数据保存出错");
				}
			});
		}
    </script>
   </content>


</body>

</html>