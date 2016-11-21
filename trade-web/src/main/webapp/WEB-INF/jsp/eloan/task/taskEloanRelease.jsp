<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>e+产品</title>

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
                            <h5>e+产品</h5>
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
                                       <p><em>归属人</em><span class="span_one">${excutorName}</span></p>
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
                                
                                <c:if test="${!empty eloanCase.chargeAmount}">
                                	<div class="case_row">
	                                   <div class="case_lump">
	                                       <p><em>手续费</em><span class="span_one">${eloanCase.chargeAmount}</span></p>
	                                   </div>
	                                   
	                                   <c:if test="${!empty eloanCase.remark}">
	                                   		<div class="case_lump">
		                                       <p><em>情况说明</em><span class="span_one">${eloanCase.remark}</span></p>
		                                   </div>
	                                   </c:if>
	                                </div>
                                </c:if>
                                
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
							                                   	   <input type="hidden"  name="eloanRelSize" value="${eloanRelList.size()}">
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
               <%--          		<ul class="form_lump">
                        		<li>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">
                                        	客户姓名
                                    </label>
                                    <input class="input_type sign_right_two" value="${eloanCase.custName}" name="custName" id="custName">
                                </div>
                                <div class="input-group">
                                    <label class="control-label sign_left_two">
                                        	客户电话
                                    </label>
                                    <input class="input_type sign_right_two" value="${eloanCase.custPhone}"  name="custPhone" id="custPhone"/>
                                </div>
                            </li>
                            
                            <li>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">
                                        	申请金额
                                    </label>
                                    <input class="input_type sign_right_two" value="${eloanCase.applyAmount}" name="applyAmount" id="applyAmount">
                                    <div class="input-group date_icon">
                                        <span class="danwei">万</span>
                                    </div>
                                </div>
                                <div class="input-group">
                                    <label class="control-label sign_left_two">
                                        	申请期数
                                    </label>
                                    <input class="input_type sign_right_two" value="${eloanCase.month}"  name="month" id="month"/>
<!--                                     <div class="input-group date_icon">
                                        <span class="danwei">月</span>
                                    </div> -->
                                   
                                   <label class="control-label sign_left_two">
                                        	面签金额
                                    </label>
                                    <input class="input_type sign_right_two" value="${eloanCase.signAmount}" name="signAmount" id="signAmount">
                                    <div class="input-group date_icon">
                                        <span class="danwei">万</span>
                                    </div>
                                </div>
                            </li>
                        		</ul> --%>
                        		<ul class="form_lump">
                        		<li>
                        	         <div class="form_content" id="eLoanApplyPassOrRefuseReasonForShow">
                                    <label class="control-label sign_left_two pull-left">驳回原因</label>
									<textarea class="input_type sign_right pull-left"  rows="2"  id="eLoanContent"	name="eLoanContent" style="margin-left: 4px;width: 757px;
    											height: 71px;resize:none;">${toApproveRecord.content }</textarea>
                                </div>  
                                </li>
                        		</ul> 
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
		                                                        

                           
		                                <p class="text-center">
		                                <input type="hidden" name="eloanCode" id="eloanCode" value="${eloanCase.eloanCode}"/>
		                                <input type="hidden" name="taskId" id="taskId" value="${taskId}"/>
								  		<input type="button" class="btn btn-success submit_From" value="提交">
		                           		<input type="button" onclick="closeWindow()" class="btn btn-grey ml5" value="关闭">
		                         </p>  
		                                     
                        </form>
                    </div>
                </div>
            </div>
            <!-- main End -->
    </div>

    <!-- Mainly scripts -->
    <content tag="local_script">
    <!-- Custom and plugin javascript -->
    <script src="${ctx}/static/js/inspinia.js"></script>
    <script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>
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
        	
			 //驳回原因显示问题
			 var eLoanContent = $("#eLoanContent").val();	
			 if(eLoanContent == '' || eLoanContent == null){			
				$("#eLoanApplyPassOrRefuseReasonForShow").hide();				
			 } else{
				 $("#eLoanContent").attr("disabled",true);
			 }	
        	
        	$('.input-daterange').datepicker({
              	format : 'yyyy-mm-dd',
          		weekStart : 1,
          		autoclose : true,
          		todayBtn : 'linked',
          		language : 'zh-CN'
            });
			function closeWindow(){
				window.close();
				window.opener.callback();
			}
            
            $('.submit_From').click(function(){
            	var eloanRelList = new Array();
            	var eloanCode =  $('#eloanCode').val();
            	var isRelFinish = $('#isRelFinish').val();
/*             	var custName = $('#custName').val();
            	var custPhone = $('#custPhone').val();            	
            	var applyAmount = $('#applyAmount').val();
            	var month = $('#month').val();
            	var signAmount = $('#signAmount').val(); */   
            	if(isRelFinish==""){
            		alert("请选择放款是否完成");
            		return;
            	}
            	var sumAmount = 0;
            	
            	if(clickCount > 0){
                	$(".loan_ul li").each(function(){
                		var releaseAmount = $(this).find("#releaseAmount").val();
                		sumAmount+=Number(releaseAmount);
                		var releaseTime = $(this).find("#releaseTime").val();            		
                		
                		var eloanRel = {
                			releaseAmount : releaseAmount,
                			releaseTime : releaseTime,
                			eloanCode : eloanCode,            			 
                		}
                		eloanRelList.push(eloanRel);
                	})
            	}else{
            		var eloanRel = {
                			eloanCode : eloanCode,            			 
                		}
            		eloanRelList.push(eloanRel);
            	}
            
            	var size=$("input[name='eloanRelSize']").val();
            	if(clickCount<=0&&size==undefined){
            		alert("请添加一条放款记录");
            		return;
            	}
            	
            	if(eloanRelList[0].releaseAmount==""||eloanRelList[0].releaseTime==""){
            		alert("请将信息填写完整");
            		return;
            	}
/*             	var toEloanCase = {};
            	toEloanCase.eloanCode = eloanCode;
            	toEloanCase.custName = custName;
            	toEloanCase.custPhone = custPhone;
            	toEloanCase.applyAmount = applyAmount;
            	toEloanCase.month = month;
            	toEloanCase.signAmount = signAmount; */
            	
            	
            	var eloanRelListVO = {
            		eloanRelList : eloanRelList,
            		isRelFinish : isRelFinish,
            		taskId : $('#taskId').val()
            		/* toEloanCase : toEloanCase      */       		
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
        var clickCount = 0;//判断是否添加了放款金额
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
        	clickCount++;
        }
        
        //删除日期控件
        function removeDiv(index) {
        	clickCount--;
        	$("#releaseDiv" + index).remove();
        }
    </script>
   </content>


</body>

</html>