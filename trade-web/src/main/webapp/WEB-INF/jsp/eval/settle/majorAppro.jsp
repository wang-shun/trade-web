<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>首页</title>
        <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
        <link href="<c:url value='/fonts/font-awesome/css/font-awesome.min.css' />" rel="stylesheet"/>
        <link href="<c:url value='/css/plugins/toastr/toastr.min.css' />" rel="stylesheet">
        <!-- IonRangeSlider -->
        <link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.css' />" rel="stylesheet">
        <link href="<c:url value='/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css' />" rel="stylesheet">
        <link href="<c:url value='/css/animate.css' />" rel="stylesheet" />
        <link href="<c:url value='/css/style.min.css' />" rel="stylesheet" /> 
        <link href="<c:url value='/css/transcss/award/bonus.css' />" rel="stylesheet" />
        <!-- Gritter -->
        <link href="js/plugins/gritter/jquery.gritter.css" rel="stylesheet" />
        <!-- 分页控件 -->
        <link href="<c:url value='/css/plugins/pager/centaline.pager.css' />" rel="stylesheet" />
        <link href="<c:url value='/css/plugins/datapicker/datepicker3.css' />" />

        <link  href="<c:url value='/css/common/base.css' />" rel="stylesheet" />
        <link  href="<c:url value='/css/common/table.css' />" rel="stylesheet" /> 
        <link  href="<c:url value='/css/common/input.css' />" rel="stylesheet" />  
        <link rel="stylesheet" href="<c:url value='/static/iconfont/iconfont.css' />"> />
		<link href="<c:url value='/css/plugins/autocomplete/jquery.autocomplete.css' />" rel="stylesheet"/>
	<style>
        .apply_box{
            padding: 30px 15px 50px 15px;
            border-radius: 0px;
        } 
	</style>
    </head>
    
    <body class="pace-done">
    <jsp:include page="/WEB-INF/jsp/common/salesLoading.jsp"></jsp:include>
    <span>待结算审批列表</span>
        <div id="wrapper" class="Index">
                    <div class="bonus-table">
                        <table class="table table_blue table-striped table-bordered table-hover">
                            <thead style="background:#f9c">
                                <tr>
                                    <th>案件编号</th>
                                    <th>产证地址</th>
                                    <th>评估公司</th>
                                    <th>实收评估费</th>
                                   <!--  <th>返利金额</th> -->
                                    <th>
                                    	结算费用
                                    </th>
                                    <!-- <th>备注信息</th> -->
                                 	<th>贷款权证</th>
                                    <th>审批<input type="checkbox" id="checkAllNot" class="i-checks"/></th>
                                    
                                </tr>
                            </thead>
                            <tbody id="t_body_data_contents">
                                                              
                            </tbody>
                        </table>                     
                    </div>
                    <div class="text-center">
						<span id="currentTotalPage"><strong class="bold"></strong></span>
						<span class="ml15">共<strong class="bold" id="totalP"></strong>条</span>&nbsp;
						<div id="pageBar" class="pagination my-pagination text-center m0"></div>  
				    </div>
                    <div class="modal-footer">
						<button type="button" class="btn btn-primary" id="appro1" disabled="true"
						>审批通过</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 	<button class="btn btn-primary" id="appro2" disabled="true" >审批不通过</button>
					</div>
                    <div class="tab-content">
                    	<span>审批记录</span>
                    	<table class="table table_blue table-striped table-bordered table-hover" border="0" >
                    		<thead style="background:#f9c">
                    			 <tr>
                                    <th>案件编号</th>
                                    <th>审批时间</th>
                                    <th>审批意见</th>
                                    
                                </tr>
                    		</thead>
                    		<tbody class="table_content_pre">
                                <c:forEach items="${updateLogList}" var="item" varStatus="status">  
								  <tr >  
								  	<td>${item.caseCode}</td>  
								    <td><fmt:formatDate value="${item.approTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>  
								    <td>${item.rejectCause}</td>  
								  </tr>  
								</c:forEach>                        
                            </tbody>
                    	</table>
                    </div>
                	 <!--<div class="modal-footer">
						<button type="button" class="btn btn-primary" onclick="javascript:closeEval()"
						>关闭</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 	 <button class="btn btn-primary" id="btnSave">提交</button> 
					</div>-->
          </div>
        <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
            <div class="modal-dialog" style="width: 1070px;">
                <div class="modal-content animated fadeIn apply_box">
                    <%-- <input type="hidden" value="${caseCode}" id="caseCode" /> --%>
                    <form action="" class="form_list clearfix" style="margin-bottom: 20px;">
                        <div class="line">
                            <%--<button type="button" class="close" data-dismiss="modal"
                                    aria-hidden="true">×</button>
                            <label class="control-label">
                                审批驳回意见：
                            </label>
                            <input class="input_type input_extent" placeholder="请输入" value="" style="width:420px" id="rejectCause"/>--%>

                            <label class="control-label">
                                审批驳回意见：
                            </label>
                            <input class="input_type input_extent" placeholder="请输入" value="" style="width:420px" id="rejectCause"/>
                        </div>
                        <div class="line">
                            <div class="add_btn text-center" style="margin:30px 126px;">
                                <button type="button" class="btn btn-success" id="doSure" onclick="">
                                    <i class="icon iconfont"></i>&nbsp;确认
                                </button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <!-- <button type="button" class="btn btn-success" id="cancel" onclick="">
                                    <i class="icon iconfont"></i>&nbsp;取消
                                </button> -->
                                <button type="button" class="btn btn-default"
                                        data-dismiss="modal">关闭</button>
                            </div>
                        </div>
                    </form>
                    <button type="button" class="close close_blue" data-dismiss="modal"><i class="iconfont icon_rong"> &#xe60a; </i></button>
                </div>
            </div>
        </div>
        <!-- End page wrapper-->
        <!-- Mainly scripts -->
        <content tag="local_script"> 
        <%--  <script src="<c:url value='/js/bootstrap.min.js' />"></script> --%>
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
        <script	src="<c:url value='/js/plugins/jquery-ui/jquery-ui.min.js' />"></script>
		<script	type="text/javascript" src="<c:url value='/js/jquery.json.min.js' />"></script>
        <jsp:include page="/WEB-INF/jsp/tbsp/common/userorg.jsp"></jsp:include>
		<script id="evalApproListTemp" type= "text/html">
                           {{each rows as item index}}
 							  <tr class="border-e7">
                                    <td>{{item.caseCode}}
									</td>
                                    <td>{{item.PROPERTY_ADDR}}</td>
                                    <td>{{item.EVA_COMPANY}}</td>
                                    <td>{{item.EVAL_REAL_CHARGES}}</td>
                                    <td>{{item.SETTLE_FEE}}</td>
                                    
									<td>{{item.LOAN}}</td>
				<td class="center">
					<input type="checkbox" name="my_checkbox" class="i-checks" onclick="_checkbox()" value="{{item.caseCode}}" 
					 
					 caseCode="{{item.caseCode}}" />
                    <input id='caseCode' type='hidden' name='case_code' value="{{item.caseCode}}"/>
					<input type='hidden' name='pkId' value="{{item.pkid}}"/>
					<input type='hidden' name='taskIds' value="{{item.ID}}"/>
					
				</td>
                                </tr>
						{{/each}}
	    </script>
	    
	    
	    <script>
	    /**关闭页面*/
		function closeEval(){
			window.close();
		}
	        var ctx = "${ctx}";
	  		
    		
        	jQuery(document).ready(function() {
        		aist.sortWrapper({
        			reloadGrid : reloadGrid
        		});

        		//初始化数据
        	    reloadGrid();
        	 	
        	});
	    	
        	function packgeData(page){
        		var data1 = {};
        	    
        	    data1.rows = 30;
        	    data1.page = 1;
        	    if(page){
        	    	data1.page=page;
        	    }
				return data1;
        	}
			function reloadGrid(page) {
				var data1=packgeData(page);
				data1.queryId = "queryEvalWaitApproList";
				aist.wrap(data1);
        	    fetchData(data1);
	    	}
			function fetchData(p){
				  $.ajax({
		  			  async: true,
		  	          url:ctx+ "/quickGrid/findPage" ,
		  	          method: "post",
		  	          dataType: "json",
		  	          data: p,
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
		  	        	  data.ctx = ctx;
		  	        	  var tsAwardBaseList= template('evalApproListTemp' , data);
			                  $("#t_body_data_contents").empty();
			                  $("#t_body_data_contents").html(tsAwardBaseList);
			                  
			                 initpage(data.total,data.pagesize,data.page, data.records);
		  	          }
		  	     });
			} 
			function initpage(totalCount,pageSize,currentPage,records)
			{
				if(totalCount>1500){
					totalCount = 1500;
				}
				var currentTotalstrong=$('#currentTotalPage').find('strong');
				if (totalCount<1 || pageSize<1 || currentPage<1)
				{
					$(currentTotalstrong).empty();
					$('#totalP').text(0);
					$("#pageBar").empty();
					return;
				}
				$(currentTotalstrong).empty();
				$(currentTotalstrong).text(currentPage+'/'+totalCount);
				$('#totalP').text(records);
				
				$("#pageBar").twbsPagination({
					totalPages:totalCount,
					visiblePages:9,
					startPage:currentPage,
					first:'<i class="icon-step-backward"></i>',
					prev:'<i class="icon-chevron-left"></i>',
					next:'<i class="icon-chevron-right"></i>',
					last:'<i class="icon-step-forward"></i>',
					showGoto:true,
					onPageClick: function (event, page) {
						 //console.log(page);
						reloadGrid(page);
				    }
				});
			}
			
			/*全选框绑定全选/全不选属性*/
			$('#checkAllNot').click(function(){
				var my_checkboxes = $('input[name="my_checkbox"]');
				if($(this).prop('checked')){
					for(var i=0; i<my_checkboxes.length; i++){
						$('input[name="my_checkbox"]:eq('+i+')').prop('checked',true);
					}
					$("#appro1").attr("disabled", false);
					$("#appro2").attr("disabled", false);
				}else{
					for(var i=0; i<my_checkboxes.length; i++){
						$('input[name="my_checkbox"]:eq('+i+')').prop('checked',false);
					}
					$("#appro1").attr("disabled", true);
					$("#appro2").attr("disabled", true);
				}
			});
			
			/*单选框*/
			function _checkbox(){
				var my_checkboxes = $('input[name="my_checkbox"]');
				var flag =false;
				var count=0;
				$.each(my_checkboxes, function(j, item){
					if($('input[name="my_checkbox"]:eq('+j+')').prop('checked')){
						flag=true;
						++count;
					}
				});
				if(flag){
					$("#appro1").attr("disabled", false);
					$("#appro2").attr("disabled", false);
					if(count==my_checkboxes.length){
						$('#checkAllNot').prop('checked', true);
					}else if(count<my_checkboxes.length){
						$('#checkAllNot').prop('checked', false);
					}
				}else{
					$("#appro1").attr("disabled", true);
					$("#appro2").attr("disabled", true);
					$('#checkAllNot').prop('checked', false);
				}
			}
			
			/**审批不通过*/
			$("#appro2").click(function(){
				$('#myModal').modal("show");
				$("#doSure").click(function(){
					//$('#myModal').modal("hidden");
					var ids = new Array();
					var checkeds=$('input[name="my_checkbox"]:checked');
					$.each(checkeds, function(i, items){
						var $td = $(items).parent();
						var id = $('input[name="case_code"]',$td).val();
						ids.push(id);
					});
					var ctx = $("#ctx").val();
		 			var rejectCause= $('#rejectCause').val();
		 			//var ids=JSON.stringify(ids);
		 			var url = ctx+"/eval/settle/majorNoAppro";
		 			/* var data={"caseCodes":ids,"rejectCause":rejectCause};
		 			$.ajax({
						cache : false,
						async : true,//false同步，true异步
						type : "POST",
						url : url,
						data: data,
						dataType : "json",		
						success : function(data) {
							window.location.reload();
							
						
						},
						error : function(errors) {
							window.wxc.error("数据出错."+errors.message);
						}
					}); */
		 			window.location.href = ctx+"/eval/settle/majorNoAppro?caseCodes="+ids+"&rejectCause="+rejectCause;
		 	    })
	 	    })
			
	 	    /**审批通过*/
			$("#appro1").click(function(){
					var ids = new Array();
					var checkeds=$('input[name="my_checkbox"]:checked');
					$.each(checkeds, function(i, items){
						var $td = $(items).parent();
						var id = $('input[name="case_code"]',$td).val();
						ids.push(id);
					});
					var ctx = $("#ctx").val();
					
					window.wxc.confirm("确定批量审批通过吗？",{"wxcOk":function(){
						window.location.href = ctx+"/eval/settle/majorIsAppro?caseCodes="+ids;
					}});
		 			
	 	    })

	    </script>
	    </content> 
          </body>
</html>