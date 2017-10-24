<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>银行返利-修改</title>
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
        <content tag="pagetitle">银行返利-修改</content>
    </head>
    
    <body>
    <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
    <input type="hidden" id="ctx" value="${ctx}" />
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="ibox-content" id="reportFive">
            <form  action="#" method="post" id="changeRebateForm">
            	<input type="hidden" id="" name="guaranteeCompId" value="${toBankRebate.guaranteeCompId}" />
                <div  class="form_list">
                    <div class="title">银行返利-修改</div>
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
                        <table id="info" class="table table-bordered customerinfo">
                            <thead>
                            <tr>
                                <th>成交报告编号</th>
                                <th>      </th>
                                <th>银行名称</th>
                                <th>返利金额</th>
                                <th>权证返利</th>
                                <th>业务返利</th>
                            </tr>
                            </thead>
                            <tbody id="t_body_data_contents">
                                <c:forEach items="${toBankRebateInfoVO.toBankRebateInfoList}" var="item" varStatus="status">
                                    <tr id="tr_${status.index}" >
                                        <input type="hidden" id="count" value="" />
                                        <input type="hidden" id="pkId_${status.index}" name="toBankRebateInfoList[${status.index}].pkid" value="${item.pkid}" />
                                        <td><input id="ccai_${status.index}" class="form-control" type="text" name="toBankRebateInfoList[${status.index}].ccaiCode" value="${item.ccaiCode}" ></td>
                                        <td width="10%"><a>查看成交</a></td>
                                        <td width="17%"><input id="bank_${status.index}" class="form-control"  type="text" name="toBankRebateInfoList[${status.index}].bankName" value="${item.bankName}" ></td>
                                        <td width="17%"><input id="rebateMoney_${status.index}" class="form-control" type="text" name="toBankRebateInfoList[${status.index}].rebateMoney" value="${item.rebateMoney}" ></td>
                                        <td width="12%"><input id="warrant_${status.index}" class="form-control" type="text" name="toBankRebateInfoList[${status.index}].rebateWarrant" value="${item.rebateWarrant}" readonly ></td>
                                        <td width="12%"><input id="business_${status.index}" class="form-control" type="text" name="toBankRebateInfoList[${status.index}].rebateBusiness" value="${item.rebateBusiness}" readonly ></td>
                                        <!--  <td><a href="javascript:void(0)" onclick="deleteParam(this)">删除</a></td> -->
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="form_list" >
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label sign_left_small">已录入金额：</label>
                            <input type="text" class="select_control sign_right_one"  id="enteringMoney" name="borrowerMoney" readonly="readonly" value="" />
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left_small">与总金额的差额</label>
                            <input type="text" class="select_control sign_right_one" id="differenceMoney" name="borrowerMoney" readonly="readonly" value="" />
                        </div>
                    </div>
                </div>
                <div class="text-center mt40 mb30">
                    <button type="button" class="btn btn-success mr5" id="submitBtn" >提交</button>
                    <button type="button" class="btn btn-grey" id="caseInfoClean" onclick="javascript:window.close()">关闭</button>
                </div>
            </form>
        </div>
    </div>
        <!-- End page wrapper-->
        <!-- Mainly scripts -->
        <content tag="local_script"> 
        <script src="<c:url value='/js/jquery-2.1.1.js' />"></script>
       <script src="<c:url value='/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
        <script src="<c:url value='/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
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
	    <script>
	    $(document).ready(function(){
            $('#info').change(cal);
            cal();
	    });

	    function cal(){
            var total = parseFloat($('#rebateMoney').val());
            var count = 0;
            $('#info').find("tr:gt(0)").each(function(index,tr){
                var rebateMoneyi = $(tr).find('td:eq(3)').find('input');
                var rebateWarranti = $(tr).find('td:eq(4)').find('input');
                var rebateBusinessi = $(tr).find('td:eq(5)').find('input');

                if(isNaN($(rebateMoneyi).val())){
                    window.wxc.alert("请输入正确的返利金额！");
                    return;
                }else{
                    var rebateMoney = parseFloat($(rebateMoneyi).val());
                    rebateWarranti.val((rebateMoney* 0.3).toFixed(2));
                    rebateBusinessi.val((rebateMoney* 0.7).toFixed(2));
                    count += rebateMoney;
                }
            });
            $("#enteringMoney").val(count.toFixed(2));
            $("#differenceMoney").val((total-count).toFixed(2));
        }

	 //提交数据
	    $('#submitBtn').click(function(){
	   	var guaranteeCompany = $("#guaranteeCompany").val();
		if(guaranteeCompany==""){
			window.wxc.alert("请选择担保公司！");
			return;
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
	   	
	   });
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
	    	
	        
	    }  */
	  
	   
	   
	    
	    </script>
	    </content> 
          </body>
</html>