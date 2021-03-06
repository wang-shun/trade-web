<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">

<title>E+贷款作废</title>

<link href="<c:url value='/static/trans/css/eloan/eloan/eloan.css' />" rel="stylesheet" />
<link rel="stylesheet" href="<c:url value='/static/trans/css/eloan/eloan_detail.css' />">
<link rel="stylesheet" href="<c:url value='/static/trans/css/eloan/eloan_guaranty.css' />">

<link rel="stylesheet" href="<c:url value='/static/css/bootstrap.min.css' />" >
<link rel="stylesheet" href="<c:url value='/static/font-awesome/css/font-awesome.css' />">
<link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />">
<link rel="stylesheet" href="<c:url value='/static/css/animate.css' />">
<link rel="stylesheet" href="<c:url value='/static/css/style.css' />">
<link rel="stylesheet" href="<c:url value='/static/css/plugins/aist-steps/steps.css' />">
<link rel="stylesheet" href="<c:url value='/static/css/plugins/toastr/toastr.min.css' />">
<!-- stickUp fixed css -->
<link rel="stylesheet" href="<c:url value='/static/css/plugins/stickup/stickup.css' />">
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/stickmenu.css' />">
<!-- index_css  -->
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/input.css' />">
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/table.css' />">
<link rel="stylesheet" href="<c:url value='/static/trans/css/common/uplodydome.css' />">
<link rel="stylesheet" href="<c:url value='/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.css' />">
<link rel="stylesheet" href="<c:url value='/static/trans/js/plugins/poshytip/tip-twitter/tip-twitter.css" type="text/css' />" />
<!-- 分页控件 -->
<link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
<!-- 备注信息 -->
<link href="<c:url value='/css/transcss/comment/caseComment.css' />" rel="stylesheet">
<link href="<c:url value='/css/common/details.css' />" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
   <div id="wrapper">
   	       <!-- main Start -->
            <div class="row">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <!-- <div class="ibox"> -->
                    <div class="ibox-content" id="base_info">
                        <div class="main_titile" style="position: relative;">
                            <h5>关联案件</h5>
                            <div class="case_content">
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>案件编号</em><span class="span_one">${eloanCase.caseCode }</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>产权地址</em><span class="span_two">${info.propertyAddr}</span></p>
                                   </div>
                                </div>
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>交易顾问</em><span class="span_one">${info.jingbanName }</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>上家姓名</em><span class="span_two">${info.sellerName }</span></p>
                                   </div>
                                </div>
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>经纪人</em><span class="span_one">${info.agentName }</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>下家姓名</em><span class="span_two">${info.buyerName }</span></p>
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
										display="onlyLabel"  dictType="yu_serv_cat_code_tree" tag="eplus,Eloan" dictCode="${eloanCase.loanSrvCode}"
										ligerui='none'></aist:dict></span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>合作机构</em><span class="span_one">${info.finOrgName}</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>证件类型</em><span class="span_one">
                                       <aist:dict id="custCardType" name="custCardType" clazz="select_control sign_right_two"
										display="onlyLabel"  dictType="CERT_TYPE" tag="forEloanApply" dictCode="${eloanCase.custCardType}"
										ligerui='none'></aist:dict></span></p>
                                   </div>
                                </div>
                                <div class="case_row">
                                   <div class="case_lump">
                                       <p><em>办卡人姓名</em><span class="span_one">${eloanCase.custName}</span></p>
                                   </div>
                                   <div class="case_lump">
                                       <p><em>办卡人电话</em><span class="span_one">${eloanCase.custPhone}</span></p>
                                   </div>
                                   
                                   <div class="case_lump">
                                       <p><em>办卡人证件号</em><span class="span_one">${eloanCase.custPaper}</span></p>
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
                            </div>
                        </div>
                    </div>

                    <div class="ibox-content" id="loan_info">
                    	<!-- 相关信息 -->
						<div id="caseCommentList" class="view-content"></div>
                        <div class="main_titile">
                            <h5>可修改内容</h5>
                        </div>
                        <form method="get" class="form_list">                        
                        <input type="hidden" name="pkId" id="pkId" value="${pkId}"/>
					    <input type="hidden" id="eloanCode" name="eloanCode" value="${eloanCase.eloanCode}">
                        <ul class="form_lump loan_ul">
                            <li>
   								<div class="form_content">
									<label class="control-label sign_left_two pull-left">作废原因</label>
									<textarea class="input_type sign_right pull-left"  rows="2"  id="eContent"	name="eContent" style="margin-left: 4px;width: 757px;
    											height: 71px;resize:none;"></textarea>
								</div>
                            </li>
                           
                        </ul>
                        <div class="line">
                                    <div class="add_btn" style="float:left;margin:15px 126px;">
                                        <button type="button" class="btn btn-success" id="invalidEloan">提交</button>
                                        <button type="reset" class="btn btn-grey" data-dismiss="modal" id="backEloanList">关闭</button>
                                    </div>
                                </div>

                        </form>
                    </div>

                </div>
            </div>
            <!-- main End -->
    </div>
	<!-- main End -->
	<content tag="local_script"> 
	   <!-- 开关按钮js -->
       <script src="<c:url value='/static/trans/js/plugins/bootstrap-switch/bootstrap-switch.js' />"></script>
       <%-- <script src="<c:url value='/static/js/plugins/stickup/stickUp.js' />"></script> --%>
       <script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script>
       <script src="<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
       <script src= "<c:url value='/js/template.js' />" type="text/javascript" ></script>
       <script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script>   
       <script src="<c:url value='/static/js/plugins/datapicker/bootstrap-datepicker.js' />"></script> 
       <script src="<c:url value='/js/common/textarea.js' />"></script> 
       <script src="<c:url value='/js/eloan/eloancommon.js' />"></script>
	   <script>
		   jQuery(document).ready(function() {
			   var signAmount = $("#signAmount").val();
			   if(signAmount =="" || signAmount == null){
				   $("#signAmountForShow").hide();
				   $("#signAmountForShow").attr("class","abc");
			   }			   
			   var releaseAmount = $("#releaseAmount").val();
			   var releaseTime = $("#releaseTime").val();
			   if(releaseAmount == "" || releaseAmount == null){
				   $("#releaseInfoForShow").hide();
			   }
			   $("input[name='releaseTime']").attr("disabled", true);
			   $("#caseCommentList").eloanCaseCommentGrid(
						{
							eloanCode : $("#eloanCode").val(),
							source : 'EPLUS'
							//type : 'TRACK'
						}	   
					   );
			  
		   }) 
		   
           $("#invalidEloan").click(function(){  
        	   if($("#eContent").val()==null||$("#eContent").val()==""){
        		   window.wxc.alert("请填写作废原因")
        		   return;
        	   }
        	   
        	   window.wxc.confirm("确定要作废这条数据吗？",{"wxcOk":function(){
        		   saveEloanInfoForUpdate();
        	   }});
           })
           
           $("#backEloanList").click(function(){
           	 window.location.href = "${ctx}/eloan/Eloanlist";
           })
 
 			function saveEloanInfoForUpdate(){
				$.ajax({
					type : "POST",
				    url:ctx+"/eloan/deteleItem",
					data:{pkid:$("#pkId").val(),action:"aban",content:$("#eContent").val()},
    				dataType : "json",
					success : function(data) {	
						if(data.success == true){
							 window.wxc.success("数据保存成功",{"wxcOk":function(){
								 window.location.href = "${ctx}/eloan/Eloanlist";
							 }});
						}else{
							window.wxc.error("数据保存出错");
						} 
					},
					error : function(errors) {
						$.unblockUI();    
						window.wxc.error("数据保存出错");
					}
				});
			}
		   
	   </script> 
	</content>
</body>
</html>



