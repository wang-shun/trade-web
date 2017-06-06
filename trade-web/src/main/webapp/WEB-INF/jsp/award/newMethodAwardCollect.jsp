<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>计件奖金自动化流程</title>
              
    	<link href="<c:url value='/static/css/bootstrap.min.css'/>" rel="stylesheet" />	
    	<link href="<c:url value='/static/font-awesome/css/font-awesome.css' />" rel="stylesheet" />
		<!-- Data Tables -->
		<link href="<c:url value='/css/plugins/dataTables/dataTables.bootstrap.css'/>"  rel="stylesheet" />		
		<link href="<c:url value='/css/plugins/dataTables/dataTables.responsive.css'/>"  rel="stylesheet" />
		<link href="<c:url value='/css/plugins/dataTables/dataTables.tableTools.min.css'/>"  rel="stylesheet" />
		<link href="<c:url value='/css/plugins/datapicker/datepicker3.css'/>"  rel="stylesheet" />		
		<link href="<c:url value='/static/iconfont/iconfont.css' />" rel="stylesheet" />
		<link href="<c:url value='/static/css/style.css' />" rel="stylesheet" />	
		
		<!-- 分页控件 -->
		<link href="<c:url value='/css/plugins/pager/centaline.pager.css'/>"  rel="stylesheet" />
		<link href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css'/>"  rel="stylesheet" />
		
		<!-- index_css -->
 		<link href="<c:url value='/static/trans/css/common/base.css' />" rel="stylesheet" />
		<link href="<c:url value='/static/trans/css/common/table.css' />" rel="stylesheet" />
		<link href="<c:url value='/static/trans/css/common/input.css' />" rel="stylesheet" />
		<%-- <link href="<c:url value='/static/css/btn.css' />" rel="stylesheet" /> --%>
			
		<link href="<c:url value='/static/trans/css/manager/manager.css' />" rel="stylesheet" />	 

		<!-- 必须CSS -->
		<link href="<c:url value='/js/poshytitle/src/tip-twitter/tip-twitter.css' />" rel="stylesheet" />
		<script>       
				document.domain = 'centaline.com';
		</script> 
    </head>
    
    <body class="pace-done">
			<jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
            <div class="wrapper wrapper-content animated fadeInRight">
                <div class="ibox-content border-bottom clearfix space_box" style="    padding-top: 30px;">
                    <div class="form_content pull-left">
                        <label class="control-label label_left">年月</label>
                        <div class="input-daterange inline_block" data-date-format="yyyy-mm-dd" id="datepicker_0">
                            <input  class="form-control" type="text" value="${belongMonth}" id="belongMonth" name="belongMonth"  placeholder="" style="width: 100px;">
                        </div>
                    </div>
                    <ul class="step_ul list-inline">
                        <li><button class="step-current">1.奖金配置</button><i class="icon iconfont icontend">&#xe611;</i></li>
                        <li><button>2.满意度</button><i class="icon iconfont icontend">&#xe611;</i></li>
                        <li><button>3.金融产品</button><i class="icon iconfont icontend">&#xe611;</i></li>
                        <li><button>4.流失率</button><i class="icon iconfont icontend">&#xe611;</i></li>
                        <li><button>5.明细</button><i class="icon iconfont icontend">&#xe611;</i></li>
                        <li><button>6.汇总</button></li>
                    </ul>
                    <div class="btn-box">
                        <button id="btnPre" class="btn btn-pre btn-useless">上一步</button>                   
                        <button id="btnNext" class="btn btn-success">下一步</button>
                        <button id="btnSubmit" class="btn btn-success hide">提交</button>
                    </div>
                </div>
                <div class="row box-content">
                    <div class="col-md-12">
                        <iframe src="./managerPiecework" id="UpdateUserItem"  name="" frameborder="0" width="100%"  scrolling="no"></iframe>
                        <!-- <iframe src="./managerStep2.html" name="" frameborder="0" width="100%" height="750" scrolling="no"></iframe> -->
                        <!-- <iframe src="./managerStep3.html" name="" frameborder="0" width="100%" height="750" scrolling="no"></iframe> -->
                        <!-- <iframe src="./managerStep4.html" name="" frameborder="0" width="100%" height="750" scrolling="no"></iframe> -->
                        <!-- <iframe src="./managerStep5.html" name="" frameborder="0" width="100%" height="750" scrolling="no"></iframe> -->
                        <!-- <iframe id="UpdateUserItem" src="./managerStep6.html" name="" frameborder="0" width="100%" scrolling="no"></iframe> -->
                    </div>
                </div>
            </div>

	<input type="hidden" id="ctx" value="${ctx}" />
	<input type="hidden" id="queryOrgFlag" value="${queryOrgFlag}" />
	<input type="hidden" id="isAdminFlag" value="${isAdminFlag}" />
	<input type="hidden" id="userJobCode" value="${userJobCode}" />
	<input type="hidden" id="queryOrgs" value="${queryOrgs}" />
	<input type="hidden" id="serviceDepId" value="${serviceDepId}" /> 
	<form action="#" accept-charset="utf-8" method="post" id="excelForm"></form>        
    <!-- Mainly scripts -->
    <content tag="local_script">
    <script src="<c:url value='/static/js/jquery-2.1.1.js' />" ></script>
    <script src="<c:url value='/static/js/bootstrap.min.js' />" ></script>      
    <script src="<c:url value='/js/plugins/metisMenu/jquery.metisMenu.js' />"></script>
    <script src="<c:url value='/js/plugins/slimscroll/jquery.slimscroll.min.js' />"></script>
     <!-- 日期控件 -->
    <script src="<c:url value='/js/plugins/datapicker/bootstrap-datepicker.js' />"></script>     
    <!-- Custom and plugin javascript -->
    <script src="<c:url value='/js/inspinia.js' />"></script>
    <script src="<c:url value='/js/plugins/pace/pace.min.js' />"></script>       
 	<script src="<c:url value='/js/plugins/aist/aist.jquery.custom.js' />"></script> 
    <!-- 分页控件  -->
    <script src= "<c:url value='/js/plugins/pager/jquery.twbsPagination.min.js' />"></script>
    <script src= "<c:url value='/js/template.js" type="text/javascript' />"></script>
    <script src= "<c:url value='/transjs/award/newMethodAwardCollect.js' />"></script>
    
   	<!-- 必须JS --> 
	<script src="<c:url value='/js/poshytitle/src/jquery.poshytip.js' />"></script> 
    <script src="<c:url value='/js/plugins/jquery.custom.js' />"></script>  
	<script>
	        $(window.parent.document).find("#UpdateUserItem").load(function () {
	            var main = $(window.parent.document).find("#UpdateUserItem");
	            var thisheight = $(document).height() + 30;
	            main.height(thisheight);
	        });			
	        
	        (function() {
	            var num = 0;
	            $('#btnNext').click(function() {
	                num ++;
	                if( num <= 4) {
	                    Next_step(num);
	                    $('#btnPre').addClass('btn-pre-use');
	                } else if(num == 5) {
	                    Next_step(num);
	                    $('#btnSubmit').removeClass('hide');
	                    $('#btnNext').addClass('hide');
	                } else {
	                    num = 5;
	                }
	                New_src(num);
	            });
	            $('#btnPre').click(function() {
	                num --;
	                if( num <= 0) {
	                    num = 0;
	                    Prev_step(num);
	                    $('#btnPre').removeClass('btn-pre-use');
	                } else if(num < 5) {
	                    Prev_step(num);
	                    $('#btnSubmit').addClass('hide');
	                    $('#btnNext').removeClass('hide');
	                } else {
	                    num = 5;
	                }
	                New_src(num);
	            });
	
	            function Next_step(sum) {
	                var step_eq = $('.step_ul li').eq(sum).find('button');
	                var step_pre = $('.step_ul li').eq(sum-1).find('button');
	                step_pre.removeClass('step-current').addClass('step-down');
	                step_eq.addClass('step-current');
	            };
	            function Prev_step(sum) {
	                var step_eq = $('.step_ul li').eq(num).find('button');
	                var step_pre = $('.step_ul li').eq(num+1).find('button');
	                step_pre.removeClass('step-current').addClass('step-down');
	                step_eq.addClass('step-current');
	            }
	            function New_src(sum) {
	            	var belongMonth = $("#belongMonth").val();
	                var new_src = "${ctx}/newAward/managerPiecework?belongMonth="+belongMonth;

	            	 /* var new_src = "";
	            	if(sum == 4){
	            		new_src = "../newAward/newBonus";
	            	}else if(sum == 5){
	            		new_src = "../newAward/personBonusCollect";
	            	} */	                
	                $("#UpdateUserItem",parent.document.body).attr("src",new_src);
	            }
	
	            $('#AddBtn').click(function(event) {
	                alert('ss');
	            });
	            
	        })();
	    </script>	    
	  </content>             
   </body>
</html>