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
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" >

</head>

<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
    <div id="wrapper">
            <!-- main Start -->
            <div class="row wrapper border-bottom nav_heading">
               <ul class="nav_bar">
                    <li class="">贷款申请任务</li>
                    <li class="">面签任务</li>
                    <li class="active">贷款放款任务</li>
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
                         <div class="main_titile" style="position: relative;">
		                           <h5>放款记录</h5>
		                           <div class="case_content">
		                           		      <c:forEach items="${eloanRelList}" var="item">
													    <div class="case_row">
							                               <div class="case_lump">
							                                   <p><em>放款金额</em><span class="span_one" id="content_caseCode">${item.releaseAmount}万</span></p>
							                               </div>
							                               <div class="case_lump">
							                                   <p><em>放款时间</em><span class="span_one" id="content_propertyAddr"><fmt:formatDate value="${item.releaseTime}" pattern="yyyy-MM-dd" /></span></p>
							                               </div>
							                             <div class="case_lump">
							                                   <p><em>放款状态</em><span class="span_one" id="content_caseCode">
							                                   	   <c:if test="${item.confirmStatus==1}">
																		审批通过
																	</c:if>
							                                        <c:if test="${item.confirmStatus==2}">
																		审批拒绝
																	</c:if>
																	 <c:if test="${item.confirmStatus==0}">
																		待确认
																	</c:if>
							                                   </span></p>
							                               </div>
							                           </div>
												</c:forEach>
		                           </div>
		                 </div>
                    </div>

                    <div class="ibox-content" id="loan_info">
                        <div class="main_titile">
                            <h5>贷款放款任务</h5>
                        </div>
                        <form method="get" class="form_list">
                        <ul class="form_lump loan_ul">
                           <!-- <li>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">放款金额</label>
                                    <input class="input_type sign_right_two" value="" >
                                    <div class="input-group date_icon">
                                        <span class="danwei">万</span>
                                    </div>
                                </div>
                                <div class="form_content form_nomargin input-daterange" data-date-format="yyyy-mm-dd">
                                    <label class="control-label sign_left_two">放款时间</label>
                                    <input class="input_type sign_right_two" value="" />
                                    <div class="input-group date_icon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </div>
                                <button type="button" class="btn btn-success margin_tagl15">删除</button>
                            </li> -->
                        </ul>
                        <p class="add_money"><a href="javascript:add_money();" id="add_money">添加放款金额</a> (可支持多条放款记录登记)</p>
                           <div class="form_content">
                                    <label class="control-label sign_left_two">
                                                                                               是否放款完成
                                    </label>
                                    <select name="isRelFinish" id="isRelFinish" class="select_control sign_right_two">
                                       <option value="">请选择</option>
                                       <option value="1">是</option>
                                       <option value="0">否</option>
                                    </select>
                                </div>
                                <p>
                                <input type="hidden" name="eloanCode" id="eloanCode" value="${eloanCase.eloanCode}"/>
                                <input type="hidden" name="taskId" id="taskId" value="${taskId}"/>
                        <input type="button" class="btn btn-success submit_btn" value="提交" />
						</p>
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
    <%-- <script src="${ctx}/static/js/plugins/stickup/stickUp.js"></script>
    <script src="${ctx}/static/trans/js/workbench/stickDash.js"></script> --%>


    <!-- Toastr script -->
    <script src="${ctx}/static/js/plugins/toastr/toastr.min.js"></script>
    <script src="${ctx}/static/js/morris/morris.js"></script>
    <script src="${ctx}/static/js/morris/raphael-min.js"></script>

    <!-- index_js -->
    <script src="${ctx}/static/trans/js/eloan/eloan.js"></script>
    <script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
    <script src= "${ctx}/js/template.js" type="text/javascript" ></script>
   	<script id="addMoneyRelease" type= "text/html">
                           <li id="releaseDiv{{divIndex}}">
                                <div class="form_content">
                                    <label class="control-label sign_left_two">放款金额</label>
                                    <input class="input_type sign_right_two" value="" id="releaseAmount" name="releaseAmount"/>
                                    <div class="input-group date_icon">
                                        <span class="danwei">万</span>
                                    </div>
                                </div>
                                <div class="form_content form_nomargin input-daterange" data-date-format="yyyy-mm-dd">
                                    <label class="control-label sign_left_two">放款时间</label>
                                    <input class="input_type sign_right_two" value="" id="releaseTime" name="releaseTime"/>
                                    <div class="input-group date_icon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </div>
                                <button class="btn btn-success margin_tagl15" id="remove{{divIndex}}" onclick="removeDiv('{{divIndex}}');">删除</button>
                            </li>
	</script>
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
            	var eloanRelList = new Array();
            	var eloanCode =  $('#eloanCode').val();
            	var isRelFinish = $('#isRelFinish').val();
            	if(isRelFinish==""){
            		alert("请选择房款是否完成");
            		return;
            	}
            	var sumAmount = 0;
            	$(".loan_ul li").each(function(){
            		var releaseAmount = $(this).find("#releaseAmount").val();
            		sumAmount+=Number(releaseAmount);
            		var releaseTime = $(this).find("#releaseTime").val();
            		
            		var eloanRel = {
            			releaseAmount : releaseAmount,
            			releaseTime : releaseTime,
            			eloanCode : eloanCode
            		}
            		eloanRelList.push(eloanRel);
            	})
            	var eloanRelListVO = {
            		eloanRelList : eloanRelList,
            		isRelFinish : isRelFinish,
            		taskId : $('#taskId').val()
            	}
            	//console.log(eloanRelListVO);
            	var msg = validateIsFinishRelease(eloanCode,sumAmount);
            	if(($.trim(msg) === '请选择放款完成!' && $('#isRelFinish').val()==1) || $.trim(msg) === '操作成功') {
            	}else {
            		alert(msg);
            		return false;
            	}
            	var url = "${ctx}/eloan/saveEloanRelease";
    			$.ajax({
    				cache : true,
    				async : false,//false同步，true异步
    				type : "POST",
    				url : url,
    				dataType : "json",
    				contentType:"application/json",  
    				data : JSON.stringify(eloanRelListVO),
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
            })
        });
        
        function validateIsFinishRelease(eloanCode,sumAmount) {
        	var flag = true;
        	var msg = '';
			var url = "${ctx}/eloan/validateIsFinishRelease";
			$.ajax({
				cache : false,
				async : false,//false同步，true异步
				type : "POST",
				url : url,
				dataType : "json",
				//contentType:"application/json",  
				data : {eloanCode:eloanCode,sumAmount:sumAmount},
				beforeSend : function() {
					$.blockUI({
						message : $("#salesLoading"),
						css : {
							'border' : 'none',
							'z-index' : '1900'
						}
					});
					$(".blockOverlay").css({
						'z-index' : '1900'
					});
				},
				complete : function() {
					$.unblockUI();
				},
				success : function(data) {
					flag = data.content;
					msg = data.message;
				},
				error : function(errors) {
					alert("数据保存出错");
				}
			});
			return msg;
        }
        
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
        // 添加日期查询条件
        var divIndex = 1;
        function add_money() {
        	var addMoneyReleaseHtml = template('addMoneyRelease' , {divIndex:divIndex});
        	$(".loan_ul").append(addMoneyReleaseHtml);
        	// 日期控件
        	$("input[name='releaseTime']").datepicker({
        		format : 'yyyy-mm-dd',
        		weekStart : 1,
        		autoclose : true,
        		todayBtn : 'linked',
        		language : 'zh-CN'
        	});
        	divIndex++;
        }
        
        //删除日期控件
        function removeDiv(index) {
        	$("#releaseDiv" + index).remove();
        }
    </script>
   </content>


</body>

</html>