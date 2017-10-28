<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>银行返利-新增</title>
        <link rel="stylesheet" href="<c:url value='/static/css/bootstrap.min.css' />">
        <link rel="stylesheet" href="<c:url value='/static/css/select2.min.css' />">
        <link rel="stylesheet" href="<c:url value='/static/font-awesome/css/font-awesome.css' />">

        <!-- aist列表样式 -->
        <link href="<c:url value='/css/common/aist.grid.css' />" rel="stylesheet">

        <link rel="stylesheet" href="<c:url value='/static/css/animate.css' />" rel="stylesheet">
        <link rel="stylesheet" href="<c:url value='/static/css/style.css' />" rel="stylesheet">

        <!-- stickUp fixed css -->
        <link rel="stylesheet" href="<c:url value='/static/css/plugins/stickup/stickup.css' />">
        <link rel="stylesheet" href="<c:url value='/static/trans/css/common/stickmenu.css' />">

        <!-- index_css  -->
        <link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />">
        <link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />">
        <link rel="stylesheet" href="<c:url value='/static_res/trans/css/common/report.css' />">

        <link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">

        <!-- 必须CSS -->
        <link rel="stylesheet" href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" type="text/css" />
        <content tag="pagetitle">银行返利-新增</content>
    </head>
    
    <body>
    <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
    <input type="hidden" id="ctx" value="${ctx}" />
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="ibox-content" id="reportFive">
            <form  action="#"  id="changeRebateForm">
            	<input type="hidden" id="" name="guaranteeCompId" value="${toBankRebate.guaranteeCompId}" />
                <div  class="form_list">
                    <div class="title">银行返利-新增</div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">担保公司</label>
                            <select name="guaranteeCompany" class="select_control" id="guaranteeCompany">
                                <option value="" selected="selected">请选择</option>
                                <option value="0">评估公司</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">返利总金额</label>
                            <input type="text"  class="select_control sign_right_one" id="rebateMoney" name="rebateTotal" value="${toBankRebate.rebateTotal}">
                        </div>
                    </div>
                </div>
                <div class="form_list" >
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">公司账户</label>
                            <select name="" class="select_control" id="finOrgId"  readonly="readonly">
                                <option value="" selected="selected">请选择</option>
                            </select>
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">申请人</label>
                            <input type="text" class="select_control sign_right_one" id="applyer" name="applyer" readonly="readonly" value="${user.realName}" />
                        </div>
                    </div>
                </div>
                <div class="form_list" >
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">录入时间：</label>
                            <input type="text" class="select_control sign_right_one"  id="applyTime" name="applyTime" readonly="readonly" value="<fmt:formatDate value="${toBankRebate.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">录入人所在部门</label>
                            <input type="text" class="select_control sign_right_one" id="applyTime" name="applyTime" readonly="readonly" value="${user.serviceDepName }" />
                        </div>
                    </div>
                </div>

                <div class="form_list" >
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">备注：</label>
                            <textarea class="select_control" name="comment" cols="100" >${toBankRebate.comment}</textarea>
                        </div>
                    </div>
                </div>


                <div class="form_list table-capital" >
                    <div class="table-box">
                        <table class="table table-bordered customerinfo">
                            <thead>
                            <tr>
                                <th>成交报告编号</th>
                                <th>      </th>
                                <th>银行名称</th>
                                <th>返利金额</th>
                                <th>权证返利</th>
                                <th>业务返利</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="t_body_data_contents">
                                    <tr id="tr_0" >
                                        <input type="hidden" id="" name="" value="" />
                                        <td><input id="ccai_" class="form-control" type="text" name="" value="" ></td>
                                        <td width="10%"><a>查看成交</a></td>
                                        <td width="17%"><input id="bank_" class="form-control"  type="text" name="" value="" ></td>
                                        <td width="17%"><input id="rebateMoney_" class="form-control" type="text" name="rebateMoney" value="" ></td>
                                        <td width="12%"><input id="warrant_" class="form-control" type="text" name="rebateWarrant" value="" ></td>
                                        <td width="12%"><input id="business_" class="form-control" type="text" name="rebateBusiness" value="" ></td>
                                        <td></td>
                                    </tr>
                                    <input type="hidden" id="addInput" />
                            <!-- </tbody> -->
                        </table>
                        <div id="addCcaiLine" class="input-group" style="">
								<a class="" id="addRecord"><font>添加新记录</font></a>
						</div>
                    </div>
                </div>
			 <div class="form_content">
					<label class="control-label sign_left_small" >已录入金额</label> 
					<input type="text" class="input_type yuanwid" id="enteringMoney"
						name="borrowerMoney" 
						value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
					<label class="control-label sign_left_small" >与总金额的差额</label> 
					<input type="text" class="input_type yuanwid" id="differenceMoney"
						name="borrowerMoney" 
						readonly="readonly" value=""  >
				</div> 
                <div class="text-center mt40 mb30">
                    <button type="button" class="btn btn-success mr5" id="cc" >提交</button>
                    <button type="button" class="btn btn-grey" id="caseInfoClean" onclick="javascript:window.close()">关闭</button>
                </div>
            </form>
        </div>

        <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true" >
            <div class="modal-dialog">
                <div class="modal-content animated fadeIn">
                    <div class="modal-body" style="background:#fff;">
                        <p class="text-center" style="font-size: 20px;">点击删除，将丢失本次填写信息！选择保存按钮可保存本次填写信息！</p>
                    </div>
                    <div class="modal-footer" style="text-align:center;">
                        <button type="button" class="btn btn-save" id="newCaseInfoSave">确认保存</button>
                        <button type="button" class="btn btn-success" id="newCaseInfoDelete">我要删除</button>
                        <button type="button" class="btn btn-white" id="newCaseInfoCancel">取消</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
        <!-- End page wrapper-->
        <!-- Mainly scripts -->
        <content tag="local_script"> 
        <script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
       <script src="<c:url value='/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
        <script src="<c:url value='/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
         <!-- 日期控件 -->
    	<script	src="<c:url value='/js/plugins/dateSelect/dateSelect.js' />"></script>
        <!-- Custom and plugin javascript -->
        <script src="<c:url value='/js/inspinia.js' />"></script>
        <script src="<c:url value='/js/plugins/pace/pace.min.js' />"></script>
        <!-- 弹出框插件 -->
	    <script src="<c:url value='/js/plugins/layer/layer.js' />"></script>
	    <script src="<c:url value='/js/plugins/layer/extend/layer.ext.js' />"></script>
        <!-- 分页控件  -->
        <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
        <script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
        <script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>
        <script src="<c:url value='/js/plugins/autocomplete/jquery.autocomplete.js' />"></script>
        <script	src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>
        <script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>
        <script src="<c:url value='/js/common/textarea.js' />"></script>
        <!-- 评估结算  -->
       <%--  <script	src="<c:url value='/js/trunk/bankRebate/bankRebate.js' />"></script> --%>
        <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
        
		<script id="evalListTemp" type= "text/html">
                           {{each rows as item index}}
 							  <tr class="border-e7">
				<td class="center">
					<input type="checkbox" name="my_checkbox" class="i-checks" onclick="_checkbox()" value="{{item.caseCode}}" 
					 
					 caseCode="{{item.caseCode}}" />
                    <input id='caseCode' type='hidden' name='case_code'  value="{{item.caseCode}}"/>
					<input type='hidden' name='pkId' value="{{item.pkid}}" disabled>
					<input type='hidden' name='taskIds' value="{{item.ID}}" disabled>
					
				</td>
                                    <td>{{item.caseCode}}</td>
                                    <td>{{item.PROPERTY_ADDR}}</td>
									<td>{{item.FIN_ORG_ID}}</td>
                                    <td>{{item.EVAL_REAL_CHARGES}}</td>
									<td>{{item.EVA_PRICE}}</td>
									<td class="center">
										{{if item.STATUS=='4'}}
											<a href="${ctx}/eval/settle/evalEndUpdate?pkid={{item.pkId}}&&caseCode={{item.caseCode}}" target="_blank">修改</a>
											<a href="${ctx}/eval/settle/evalEndUpdate?pkid={{item.pkId}}&&caseCode={{item.caseCode}}" target="_blank">提交</a>
										{{/if}}
                        			</td>
                                </tr>
						{{/each}}
	    </script>
	    <script>
	    $(document).ready(function(){
	    		 refeshShareAmount();
				//绑定计算百分比事件
	            $('.shareAmount').bind('keyup onpropertychange', function() {   
	            	debugger;
	                console.log($(this).val());
	                var sharePacentage=$(this).val()*0.3;
	                sharePacentage=sharePacentage.toFixed(2)
	                console.log(sharePacentage);
	                console.log($(this).parent().siblings().children(".aa").val(sharePacentage));
	                
	                var sharePacentage2=$(this).val()*0.7;
	                sharePacentage2=sharePacentage2.toFixed(2)
	                console.log(sharePacentage2);
	                console.log($(this).parent().siblings().children(".bb").val(sharePacentage2));
	                refeshShareAmount();
	            }); 
				
				var index = $("#t_body_data_contents tr").length;
	    		var sum = 0;
	    		for(var i = 0; i < index; i++ ){
	    			var resMoney = parseInt($("#rebateMoney_" +  i +"").val());
	    			sum += resMoney;
	    			var rebateMoney = parseInt($("#rebateMoney").val());
	    		}
	    		
	    		var differ = parseInt(sum)-parseInt($("#rebateMoney").val());
	    		$("#enteringMoney").val(sum);
	    		$("#differenceMoney").val(differ);	
				
	    		
	    })
	    
	    var index = 1;
	    $('#addRecord').click(function(){
			
			var txt='<tr id="trId' + index + '"><td><input name="ccai"';
			txt +='	id="ccai" type="text" class="form-control" placeholder="请在此输入成交编号">';
		    txt += '</input></td>';
		    txt +='<td >'
		    txt+= '<a href="">查看成交</a></td>';
			txt +='<td width="17%"><input id="bank_" class="form-control"  type="text" name="" value="" ></td>';
			txt +='<td width="17%"><input id="rebateMoney" name="loanMoney" type="text" class="form-control"  value="" /></td>';
			txt +='<td width="12%"><input id="warrantMoney" name="restMoney" type="text" class="form-control"  value="" /></td>';
			txt +='<td width="12%"><input id="businessMoney" name="businessMoney" type="text" class="form-control" placeholder="" value="" /></td>';
			txt +='<td><span><a href="javascript:removeTr('+ index + ');"><font>删除</font></a></span></td></tr></tbody>';
			//alert(txt);
			$("#addInput").before(txt);
			//$("#addMoneyLine").css("display","none");
			index++;
		});
		
		function removeTr(index) {
			$("#trId" + index).remove();
			$("#addCcaiLine").css("display","block");
			
		}

	    $('#enteringMoney').click(function(){   
	    	var index = $("#t_body_data_contents tr").length;
    		var sum = 0;
    		for(var i = 0; i < index; i++ ){
    			var resMoney = parseInt($("#rebateMoney_" +  i +"").val());
    			sum += resMoney;
    			var rebateMoney = parseInt($("#rebateMoney").val());
    		}
    		
    		var differ = parseInt(sum)-parseInt($("#rebateMoney").val());
    		$("#enteringMoney").val(sum);
    		$("#differenceMoney").val(differ);	 
	                
	     });
	    
	    function refeshShareAmount(){
			$('.shareAmount').each(function(){
            	var sharePacentage=$(this).val()*0.3;
                sharePacentage=sharePacentage.toFixed(2)
            	$(this).parent().siblings().children(".aa").val(sharePacentage);
                var sharePacentage2=$(this).val()*0.7;
                sharePacentage2=sharePacentage2.toFixed(2)
            	$(this).parent().siblings().children(".bb").val(sharePacentage2);
            })
		}
	    
	  
	 //提交数据
	    $('#cc').click(function(){   	
	   	var guaranteeCompany = $("#guaranteeCompany").val();
		if(guaranteeCompany==""){
			window.wxc.alert("请选择担保公司！");
		} 
	  	var index = $("#t_body_data_contents tr").length;
		var sum = 0;
		for(var i = 0; i < index; i++ ){
			var resMoney = parseInt($("#rebateMoney_" +  i +"").val());
			sum += resMoney;
			var rebateMoney = parseInt($("#rebateMoney").val());
			
			//alert(resMoney);
		}
		if(sum == rebateMoney){
			//alert("保存成功！");
			save(true);
		}else{
			window.wxc.alert("返利单明细【返利金额】之和与【总金额】不匹配，请检查！");
		}
	   	
	   }) 
	 //保存数据
	   function save(b) {
	   	var jsonData = $("#changeRebateForm").serializeArray();

	   	var url = "${ctx}/bankRebate/submitChangeBankRebate";
	   	
	   	$.ajax({
	           cache : true,
	           async : false,
	           type : "POST",
	           url : url,
	           dataType : "json",
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
	           success: function(data){
	               $.unblockUI();
	               console.log(data);
	               if (b) {
	                   if (data) {
	                      window.wxc.alert("提交成功");
	                   }
	                   var ctx = $("#ctx").val();
	               }else{
	               	if (data.message) {
	                       window.wxc.alert("提交成功"+data);
	                   }
	               }
	           },
	           error:function(){
	           	window.wxc.alert("提交信息出错。。");
	           }
	       });
	   }
	 
		//删除行;(obj代表连接对象)  
		/*  function deleteParam(obj){
		    	//在js端删除一整行数据  
		        $(obj).parent().parent().remove();  
		        
		    } 
	   */
	   
	   
	    
	    </script>
	    </content> 
          </body>
</html>