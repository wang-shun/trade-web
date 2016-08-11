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
    <!-- 分页控件 -->
    <link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
    <link href="${ctx}/css/jquery.editable-select.min.css" rel="stylesheet">
</head>


<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
<div id="wrapper">
            <!-- main Start -->
            <div class="row wrapper border-bottom nav_heading">
               <ul class="nav_bar">
                    <li class="active">贷款申请任务</li>
                    <li class="">面签任务</li>
                    <li class="">贷款放款任务</li>
                </ul>
            </div>
            <div class="row">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <!-- <div class="ibox"> -->
                    <div class="ibox-content" id="base_info">
                        <div class="main_titile" style="position: relative;">
                            <h5>关联案件<button type="button" id="link_btn" class="btn btn-success btn-blue" data-toggle="modal" data-target="#myModal">关联案件</button></h5>
                            <c:if test="${taskId==null}">
								<div class="case_content" style="display:none;">
							</c:if>
                            <c:if test="${taskId!=null}">
								<div class="case_content">
							</c:if>
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
                                   <p><em>上家姓名</em><span class="span_two" id="content_seller">${sellerName}</span></p>
                               </div>
                           </div>
                           <div class="case_row">
                               <div class="case_lump">
                                   <p><em>经纪人</em><span class="span_one" id="content_agentName">${agentName}</span></p>
                               </div>
                               <div class="case_lump">
                                   <p><em>下家姓名</em><span class="span_two" id="content_buyer">${buyerName}</span></p>
                               </div>
                           </div>
                       </div>

                            <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
                                <div class="modal-dialog" style="width: 1070px;">
                                    <div class="modal-content animated fadeIn apply_box">
                                        <form action="" class="form_list clearfix">
                                            <div class="form_tan">
                                                <label class="control-label sign_left">
                                                    产证地址
                                                </label>
                                                <input class="sign_right input_type" placeholder="请输入" value="" id="propertyAddr" name="propertyAddr">
                                            </div>
                                            <div class="form_tan tan_space">
                                                <div class="add_btn">
                                                    <button type="button" class="btn btn-success" id="searchButton">
                                                        <i class="icon iconfont">&#xe635;</i>&nbsp;查询
                                                    </button>
                                                </div>
                                            </div>
                                        </form>
                                        <button type="button" class="close close_blue" data-dismiss="modal"><i class="iconfont icon_rong">
                                                &#xe60a;
                                            </i></button>
                                            <div class="eloanApply-table">
                                        <!-- <table class="table table_blue table-striped table-bordered table-hover " id="editable">
                                            <thead>
                                                <tr>
                                                    <th>
                                                        <span>案件编码</span><a href="#"><i class="fa fa-sort-desc fa_down"></i></a>
                                                    </th>
                                                    <th>
                                                        产证地址
                                                    </th>
                                                    <th>
                                                        工作人员
                                                    </th>
                                                    <th>
                                                        上家
                                                    </th>
                                                    <th>
                                                        下家
                                                    </th>
                                                    <th>
                                                        操作
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>
                                                        <p class="big">
                                                            <a href="javascript:;">
                                                                ZY-AJ-201605-0952
                                                            </a>
                                                        </p>
                                                        <p>
                                                            <i class="tag_sign">c</i>
                                                            BKS-2-451341-2154
                                                        </p>
                                                    </td>
                                                    <td>
                                                        <p class="big">
                                                            上海杨浦区平路街道（内环）鞍山八村29号0608室
                                                        </p>
                                                        <p class="tooltip-demo">
                                                            <i class="salesman-icon">
                                                            </i>
                                                            <a class="salesman-info" href="javascript:;" title="" data-toggle="tooltip" data-placement="top" data-original-title=" 张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体">
                                                                张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体
                                                            </a>
                                                        </p>
                                                    </td>
                                                    <td>
                                                        <p class="name">
                                                            <span>交易顾问：</span><a href="#" class="a_blue">龙一思</a>
                                                        </p>
                                                        <p class="name">
                                                            <span>经纪人：</span><a href="#" class="a_blue">唐丽娜</a>
                                                        </p>
                                                    </td>
                                                    <td class="center">
                                                        <p class="big">
                                                            张春伟
                                                        </p>
                                                        <p class="big">
                                                            张伟伟
                                                        </p>
                                                    </td>
                                                    <td class="center">
                                                        <p class="big">
                                                            陈一财
                                                        </p>
                                                        <p class="big">
                                                            陈一财
                                                        </p>
                                                    </td>
                                                    <td class="text-left">
                                                        <button type="button" class="btn btn-success">
                                                        关联案件
                                                        </button>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table> -->
                                    </div>

                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="ibox-content" id="zj_info">
                        <form method="get" class="form_list" id="eloanApplyForm">
                        <input type="hidden" name="caseCode" id="caseCode" value="${caseCode}"/>
                        <input type="hidden" id="excutorId" name="excutorId" value="${excutorId}"/>
                        
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
                                        产品类型
                                    </label>
                      <aist:dict id="loanSrvCode" name="loanSrvCode" clazz="select_control sign_right_two"
						display="select"  dictType="yu_serv_cat_code_tree" tag="Eloan" 
						ligerui='none' defaultvalue="${eloanCase.loanSrvCode}"></aist:dict>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">
                                        合作机构
                                    </label>
                                    <select  class="select_control sign_right_two" name="finOrgCode" id="finOrgCode" value="${eloanCase.finOrgCode}">
                                    </select>
                                </div>

                            </li>
                            <li>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">
                                        客户姓名
                                    </label>
                                    <div id="custNameParent" style="display:inline-block;"> 
                                       		<select class="select_control sign_right_two" id="custName" name="custName">
												<c:if test="${eloanCase.custName !=null}">
													<option value="${eloanCase.custName }" selected="selected">${eloanCase.custName }</option>
												</c:if>
											</select>
                                    </div>
                                    <!-- <input class="input_type sign_right_two" value="" name="custName" id="custName"> -->
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">
                                        客户电话
                                    </label>
                                    <input class="input_type sign_right_two" value="${eloanCase.custPhone}" name="custPhone" id="custPhone">
                                </div>
                                <shiro:hasPermission name="TRADE.LOAN.SUBMIT.BELONG">
                                 <div class="form_content">
								     <label class="control-label sign_left_two">
                                        案件归属
                                    </label>
									<input type="text" id="excutorName" name="excutorName" class="form-control tbspuser"
									    style="width:170px;display: inline-block;"
										hVal="${excutorId}" value="${excutorName}" readonly="readonly"
										onclick="userSelect({startOrgId:'${orgId}',expandNodeId:'${orgId}',
										nameType:'long|short',orgType:'',departmentType:'',departmentHeriarchy:'',chkStyle:'radio',callBack:selectUserBack})" />
								 </div>
								</shiro:hasPermission>
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
                                <div class="form_content input-daterange" data-date-format="yyyy-mm-dd">
                                    <label class="control-label sign_left_two">
                                        申请时间
                                    </label>
                                    <input class="input_type sign_right_two" value="<fmt:formatDate value="${eloanCase.applyTime}" pattern="yyyy-MM-dd" />" name="applyTime" id="applyTime" />
                                    <div class="input-group date_icon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">
                                        申请期数
                                    </label>
                                    <input class="input_type sign_right_two" value="${eloanCase.month}" name="month" id="month">
                                    <div class="input-group date_icon">
                                        <span class="danwei">月</span>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">
                                        转介人姓名
                                    </label>
                                    <input class="input_type sign_right_two" value="${eloanCase.zjName}" name="zjName" id="zjName">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">
                                        转介人员工编号
                                    </label>
                                    <input class="input_type sign_right_two" value="${eloanCase.zjCode}" name="zjCode" id="zjCode">
                                </div>

                            </li>
                            <li>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">
                                        产品部姓名
                                    </label>
                                    <input class="input_type sign_right_two" value="${eloanCase.pdName}" name="pdName" id="pdName">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">
                                        产品部员工编号
                                    </label>
                                    <input class="input_type sign_right_two" value="${eloanCase.pdCode}" name="pdCode" id="pdCode">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">
                                        产品部分成比例
                                    </label>
                                    <input class="input_type sign_right_two" value="${eloanCase.pdPart}" name="pdPart" id="pdPart">
                                    <div class="input-group date_icon">
                                        <span class="danwei">%</span>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">
                                        合作人姓名
                                    </label>
                                    <input class="input_type sign_right_two" value="${eloanCase.coName}" name="coName" id="coName">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">
                                        合作人员工编号
                                    </label>
                                    <input class="input_type sign_right_two" value="${eloanCase.coCode}" name="coCode" id="coCode">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_two">
                                     人员分配比例
                                    </label>
                                    <input class="input_type sign_right_two" value="${eloanCase.coPart}" name="coPart" id="coPart">
                                    <div class="input-group date_icon">
                                        <span class="danwei">%</span>
                                    </div>
                                </div>
                            </li>
                        </ul>
                        <input type="button" class="btn btn-success submit_btn" value="提交" />

                        </form>
                    </div>

                </div>
            </div>
            <!-- main End -->
</div>

    <!-- Mainly scripts -->
    <content tag="local_script">
   <%--  <script src="${ctx}/static/js/jquery-2.1.1.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js"></script>
    <script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script> 
    <script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="${ctx}/static/js/inspinia.js"></script>
    <script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>--%>

    <!-- stickup plugin -->
    <script src="${ctx}/static/js/plugins/stickup/stickUp.js"></script>
    <script src="${ctx}/static/trans/js/workbench/stickDash.js"></script>


    <!-- Toastr script -->
    <script src="${ctx}/static/js/plugins/toastr/toastr.min.js"></script>
    <script src="${ctx}/static/js/morris/morris.js"></script>
    <script src="${ctx}/static/js/morris/raphael-min.js"></script>

    <!-- index_js -->
    <script src="${ctx}/static/trans/js/eloan/eloan.js"></script>
    <script src="${ctx}/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    
    <!-- aist -->
    <script src="${ctx}/js/jquery.blockui.min.js"></script>
    <script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
    <script src= "${ctx}/js/template.js" type="text/javascript" ></script>
    <script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
    <script src="${ctx}/js/jquery.editable-select.min.js"></script>
    <script id="queryCastListItemList" type= "text/html">
                          {{each rows as item index}}
	<tr>
    <td>
        <p class="big">
            <a href="{{ctx}}/case/caseDetail?caseId={{item.PKID}}" class="case-num" target="_blank">
               <span id="modal_caseCode{{index}}">{{item.CASE_CODE}}</span>
            </a>
        </p>
        <p>
            <i class="tag_sign">c</i>
             {{item.ctmCode}}
        </p>
    </td>
    <td>
        <p class="big">
       		<span id="modal_propertyAddr{{index}}">{{item.PROPERTY_ADDR}}</span>
        </p>
        <p class="tooltip-demo">
            <i class="salesman-icon">
            </i>
            <a class="salesman-info" href="javascript:;" title="" data-toggle="tooltip" data-placement="top" data-original-title="">
            	{{item.AGENT_NAME}}/{{item.AGENT_ORG_NAME}}
            </a>
        </p>
    </td>
    <td>
        <p class="name">
            <span>交易顾问：</span><a href="#" class="a_blue" id="modal_processorId{{index}}">{{item.PROCESSOR_ID}}</a>
        </p>
        <p class="name">
            <span>经纪人：</span><a href="#" class="a_blue" id="modal_agentName{{index}}">{{item.AGENT_NAME}}</a>
        </p>
    </td>
    <td class="center">
        <p class="big">
       		<span id="modal_seller{{index}}">{{item.SELLER}}</span>
        </p>
    </td>
    <td class="center">
        <p class="big">
      	   <span id="modal_buyer{{index}}"> {{item.BUYER}}</span>
        </p>
    </td>
    <td class="text-left">
        <button type="button" class="btn btn-success linkCase" name="linkCase" id="{{index}}">
                          关联案件
        </button>
    </td>
</tr>
{{/each}}
	</script>
    <script>
        $(document).ready(function () {
        	$('#custName').editableSelect({
        		effects: 'slide',
        		filter: false
        	});
        	
            $('.input-daterange').datepicker({
            	format : 'yyyy-mm-dd',
        		weekStart : 1,
        		autoclose : true,
        		todayBtn : 'linked',
        		language : 'zh-CN'
            });
            
			// 初始化银行列表
			getBankList('');
			
			$('.submit_btn').click(function(){
				//关联案件必须填写
				if(!checkForm()){
					return;
				}
				if(!$.isBlank($("#excutorName").attr('hVal'))) {
					$("#excutorId").val($("#excutorName").attr('hVal'));
				}
				if($.isBlank($("#caseCode").val())) {
					alert('请选择关联案件');
					return false;
				}
				if(validateEloanApply()) {
					saveEloanApply();
				} else {
					alert('该案件的产品已经存在，不允许重复添加');
					return false;
				}
			})
			//必填项
			function checkForm(){
				var ds = $('.case_content').css('display');
				if(ds=='none'){
					alert("请选择关联案件");
					return false;	
				}
				 var loanSrvCode=$("#loanSrvCode").val();			
				 if(loanSrvCode==null||loanSrvCode==''){
						alert("请选择产品类型");
						return false;	
					}
				 var applyAmount=$("#applyAmount").val();
				 if(applyAmount==null||applyAmount==''){
						alert("请填写申请金额");
						return false;	
					}
				 var date=$("#applyTime").val();
				 if(date==null||date==''){
						alert("请选择申请时间");
						return false;	
					}
				 return true;
			}

			$(".eloanApply-table").aistGrid({
    			ctx : "${ctx}",
    			queryId : 'queryCastListItemList',
    		    templeteId : 'queryCastListItemList',
    		    rows : '6',
    		    gridClass : 'table table_blue table-striped table-bordered table-hover',
    		    data : '',
    		    wrapperData :{ctx: ctx},
    		    columns : [{
    		    	           colName :"案件编号",
    		    	           sortColumn : "CASE_CODE",
    		    	           sord: "desc",
    		    	           sortActive : true
    		    	      },{
    		    	           colName :"产证地址"
    		    	      },{
   		    	                colName :"工作人员"
		    	          },{
	   		    	           colName :"上家"
			    	      },{
			    	           colName :"下家"
			    	      },{
    		    	           colName :"操作"
    		    	      }]
    		
    		}); 
    		
    	 	// 查询
 			$('#searchButton').click(function() {
 				reloadGrid();
 			});
    	 	
    	 	// 关联案件
   	 		$('.eloanApply-table').on("click",'.linkCase',function(){
   	 			//
   	 			var index = $(this).attr("id");
   	 			$("#content_caseCode").html($("#modal_caseCode"+index).html());
   	 		    $("#caseCode").val($("#modal_caseCode"+index).html());
   	 		    $("#content_propertyAddr").html($("#modal_propertyAddr"+index).html());
   	 	        $("#content_processorId").html($("#modal_processorId"+index).html());
   	            $("#content_buyer").html($("#modal_buyer"+index).html());
             	$("#content_agentName").html($("#modal_agentName"+index).html());
   	            $("#content_seller").html($("#modal_seller"+index).html());
 				$('.case_content').show();
 				$('#myModal').modal('hide');
 				
 				getCustomerNameAndTel($("#caseCode").val());
 			});
 			
        });
        
        function reloadGrid() {
        	var propertyAddr = $("#propertyAddr").val();
    	    $(".eloanApply-table").reloadGrid({
    	    	ctx : "${ctx}",
    	    	rows : '6',
    			queryId : 'queryCastListItemList',
    		    templeteId : 'queryCastListItemList',
    		    wrapperData :{ctx : ctx},
    		    data : {propertyAddr:propertyAddr}
    	    })
    	}
  
		/*获取银行列表*/
		function getBankList(pcode){
			var fiCode = $("#finOrgCode").attr("value");
			var friend = $("#finOrgCode");
			friend.empty();
			 $.ajax({
			    url:ctx+"/manage/queryFin",
			    method:"post",
			    dataType:"json",
			    data:{"pcode":pcode},
		    	success:function(data){
		    		if(data.bankList != null){
		    			for(var i = 0;i<data.bankList.length;i++){
		    				if(fiCode == data.bankList[i].finOrgCode) {
		    					friend.append("<option value='"+data.bankList[i].finOrgCode+"' selected='selected'>"+data.bankList[i].finOrgName+"</option>");
		    				} else {
		    					friend.append("<option value='"+data.bankList[i].finOrgCode+"'>"+data.bankList[i].finOrgName+"</option>");
		    				}
		    			}
		    			friend.find("option[value='${loanAgent.finOrgCode }']").attr("selected",true);
		    		}
		    	}
			  });
		}
		/**
		根据银行选择产品分成比例
		*/
		$("#finOrgCode").change(function(){
			var finOrgCode=$("#finOrgCode").val();
			if(finOrgCode=="W0001"){
				$("#pdPart").val(20);
				$("#pdPart").attr("disabled",true);
			}else if(finOrgCode=="W0003"||finOrgCode=="W0004"){
				$("#pdPart").val(10);
				$("#pdPart").attr("disabled",true);
			}else{
				$("#pdPart").val('');
				$("#pdPart").attr("disabled",false);	
			}
		})
		function validateEloanApply() { 
			var flag = false;
			var jsonData = $("#eloanApplyForm").serializeArray();
			var url = "${ctx}/eloan/validateEloanApply";
			$.ajax({
				cache : false,
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
				},
				error : function(errors) {
					alert("数据保存出错");
				}
			});
			return flag;
		}
		
		function saveEloanApply() {
			var jsonData = $("#eloanApplyForm").serializeArray();
			var url = "${ctx}/eloan/saveEloanApply";
			$.ajax({
				cache : false,
				async : true,//false同步，true异步
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
					alert(data.message);
					window.location.href=ctx+"/loan/submit";
				},
				error : function(errors) {
					alert("数据保存出错");
				}
			});
		}
		/*绑定时获取客户名和客户电话号码并生成下拉框*/
		function getCustomerNameAndTel(case_code){
			$.ajax({
				url:ctx+"/eloan/getCaseDetails",
				method:"post",
				dataType:"json",
				data:{"caseCode":case_code},
				success:function(data){
					var cusPhone = $('#custPhone');
					var txt='<select class="select_control sign_right_two" id="custName" name="custName">';
								
					$.each(data, function(i, item){
						if(i==0) {
							txt +=  "<option value='"+ item.guestName+"' selected>" + item.guestName+"</option>";
							cusPhone.val(data[i].guestPhone);
						} else {
							txt +=  "<option value='"+ item.guestName+"'>" + item.guestName+"</option>";
						} 
					});
					$('#custNameParent').empty().append(txt+'</select>');	
								
					$('#custName').editableSelect({
						effects: 'slide',
						filter: false,
						onSelect: function (element) {
							var myIndex = $(element).index();
							if(myIndex>=0){
								cusPhone.val(data[myIndex].guestPhone);
							}
						}
					});
				}
			});				
		}
		function selectUserBack(array){
			if(array && array.length >0){
		        $("#excutorName").val(array[0].username);
				$("#excutorName").attr('hVal',array[0].userId);
			}else{
				$("#executorName").val("");
				$("#executorName").attr('hVal',"");
			}
		}
    </script>
    </content>


</body>

</html>