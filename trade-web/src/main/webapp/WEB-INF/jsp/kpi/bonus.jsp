<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>首页</title>
        <link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${ctx}/fonts/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
        <link href="${ctx}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
        <!-- IonRangeSlider -->
        <link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.css" rel="stylesheet">
        <link href="${ctx}/css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css" rel="stylesheet">
        <link href="${ctx}/css/animate.css" rel="stylesheet">
        <%-- <link href="${ctx}/css/style.min.css" rel="stylesheet">  --%>
        <link href="${ctx}/css/transcss/kpi/bonus.css" rel="stylesheet">
        <!-- Gritter -->
        <link href="js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
    </head>
    <body class="pace-done">
        <div id="wrapper" class="Index">
       			<!-- Main view -->
                <div class="main-bonus">
                    <div class="bonus-wrap">
                        <div class="bonus-header">
                        <div class="bonus-m">
                            <div class="ibox-title">
                                <input type="button" class="btn btn-warning m-r-sm" value="&lt;">
                                <h5 class="month">2015/3月</h5>
                                <input type="button" class="btn btn-warning m-r-sm disable" disabled value="&gt;" style="margin-left:10px;">
                            </div>
                            <div class="ibox-tools">
                                <span class="nav-label">案件数<i>10</i></span>
                                <span class="nav-label">发放人员<i>31</i></span>
                                <span class="nav-label">奖金总额<i>123,200.00</i></span>
                            </div>
                        </div>
                        <div class="ibox-content bonus-m-con">
                            <div class="row m-t">
                                <div class="col-lg-4 col-md-4">
                                    <div class="form-group">
                                        <label class="col-lg-3 col-md-3 control-label font_w">案件编号</label>
                                        <div class="col-lg-9 col-md-9">
                                            <input type="text" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-4 col-md-4">
                                    <div class="form-group">
                                        <label class="col-lg-3 col-md-3 control-label font_w">物业地址</label>
                                        <div class="col-lg-9 col-md-9">
                                            <input type="text" class="form-control" >
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-4 col-md-4">                                   
                                    <button class="btn btn-warning"><i class="fa fa-search"></i><span class="bold">搜索</span></button>
                                    <button class="btn btn-warning" type="submit">提交</button>
                                    <button class="btn btn-primary" data-toggle="modal" data-target="#add-change">+添加调整</button>
                                </div>
                            </div>
                            <div class="alert">
                                提交前必须确认所有案件对账完成，提交后生成个人绩效奖金汇总
                            </div>
                        </div>
                    </div>
                    </div>
                    <div class="bonus-table">
                        <table>
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>案件编号</th>
                                    <th>物业地址</th>
                                    <th>过户时间</th>
                                    <th>结案时间</th>
                                    <th>奖金</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody id="TsAwardBaseList">
                                <tr class="border-e7">
                                    <td></td>
                                    <td>ZY-AJ-201604-0352</td>
                                    <td>上海虹口区临平路片区物华路246弄4号2402室</td>
                                    <td>2016-03-21</td>
                                    <td>2016-03-30</td>
                                    <td>780</td>
                                    <td><a class="expand">收起</a></td>
                                </tr>
                                <tr class="toogle-show border-e7">
                                    <td colspan="7" class="two-td">
                                        <table class="two-table">
                                            <thead>
                                                <tr>
                                                    <th>人员</th>
                                                    <th>服务</th>
                                                    <th>基础奖金</th>
                                                    <th>满意度</th>
                                                    <th>金融达标</th>
                                                    <th>考核结果</th>
                                                    <th>占比</th>
                                                    <th>绩效奖金</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </td>
                                </tr>
                                <tr class="border-e7">
                                    <td></td>
                                    <td>ZY-AJ-201604-0352</td>
                                    <td>上海虹口区临平路片区物华路246弄4号2402室</td>
                                    <td>2016-03-21</td>
                                    <td>2016-03-30</td>
                                    <td>780</td>
                                    <td><a class="expand">展开</a></td>
                                </tr>
                                <tr class="border-e7">
                                    <td></td>
                                    <td>ZY-AJ-201604-0352</td>
                                    <td>上海虹口区临平路片区物华路246弄4号2402室</td>
                                    <td>2016-03-21</td>
                                    <td>2016-03-30</td>
                                    <td>780</td>
                                    <td><a class="expand">展开</a></td>
                                </tr>
                                <tr class="border-e7">
                                    <td class="td-tag"><label class="label label-warning">NEW</label></td>
                                    <td>ZY-AJ-201604-0352</td>
                                    <td>上海虹口区临平路片区物华路246弄4号2402室</td>
                                    <td>2016-03-21</td>
                                    <td>2016-03-30</td>
                                    <td>780</td>
                                    <td><a class="expand">展开</a></td>
                                </tr>
                            </tbody>
                        </table>                     
                    </div>
                </div>
            </div>
            <!-- /Main view -->
        </div>
        <!-- End page wrapper-->
        <!-- Mainly scripts -->
        <script src="${ctx}/js/jquery-2.1.1.js"></script>
        <script src="${ctx}/js/bootstrap.min.js"></script>
        <script src="${ctx}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
        <script src="${ctx}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
        <!-- Custom and plugin javascript -->
        <script src="${ctx}/js/inspinia.js"></script>
        <script src="${ctx}/js/plugins/pace/pace.min.js"></script>
        <script src= "${ctx}/js/template.js" type="text/javascript" ></script>
		<script id="tsAwardBaseList" type= "text/html">
                           {{each rows as item index}}
 							  <tr class="border-e7">
                                    <td></td>
                                    <td>{{item.CASE_CODE}}</td>
                                    <td>{{item.PROPERTY_ADDR}}</td>
                                    <td>{{item.GUOHU_TIME}}</td>
                                    <td>{{item.CLOSE_TIME}}</td>
                                    <td>{{item.BASE_CASE_AMOUNT}}</td>
                                    <td><div class="expand" id="{{item.CASE_CODE}}">展开</div></td>
                                </tr>
                                <tr class="toogle-show border-e7" id="toggle{{item.CASE_CODE}}" style="display:none;">
                                    
                                </tr>
						{{/each}}
	    </script>
	    <script id="tsAwardSrvList" type= "text/html">
                                    <td colspan="7" class="two-td">
                                        <table class="two-table">
                                            <thead>
                                                <tr>
                                                    <th>人员</th>
                                                    <th>服务</th>
                                                    <th>基础奖金</th>
                                                    <th>满意度</th>
                                                    <th>金融达标</th>
                                                    <th>考核结果</th>
                                                    <th>环节占比</th>
													<th>满意度占比</th>
                                                    <th>绩效奖金</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                               {{each rows as item index}}
                                                <tr> 
                                                    <td>{{item.PARTICIPANT}}</td>
                                                    <td>{{item.SRV_CODE}}</td>
                                                    <td>{{item.BASE_AMOUNT}}</td>
                                                    <td>{{item.SATISFACTION}}</td>
                                                    <td>{{item.FIN_ORDER_RATE}}</td>
                                                    <td>{{item.KPI_RATE_SUM}}</td>
                                                    <td>{{item.SRV_PART_IN}}</td>
													<td>{{item.SRV_PART}}</td>
                                                    <td>{{item.AWARD_KPI_MONEY}}</td>
                                                </tr>
												{{/each}}
                                            </tbody>
                                        </table>
                                    </td>
	    </script>
	    <script>
	        var ctx = "${ctx}";
	    	$(document).ready(function(){
	    		var data = {};
	    	    data.queryId = "tsAwardBaseList";
	    	    data.rows = 8;
	    	    data.page = 1;
	    		$.ajax({
	    			  async: false,
	    	          url:ctx+ "/quickGrid/findPage" ,
	    	          method: "post",
	    	          dataType: "json",
	    	          data: data,
	    	          success: function(data){
	    	        	  var tsAwardBaseList= template('tsAwardBaseList' , data);
    	                  $("#TsAwardBaseList").empty();
    	                  $("#TsAwardBaseList").html(tsAwardBaseList);
	    	          }
	    	     });
	    		
	    		  $(".expand").click(function(){
	    			  var id = this.id;
	    			  if($(this).html() == "展开") {
	    				  $(this).html("收起");
	    				  // 发出请求
	    				    var data = {};
				    	    data.queryId = "tsAwardBaseDetailList";
				    	    data.rows = 8;
				    	    data.page = 1;
				    	    data.search_caseCode = id;
				    		$.ajax({
				    			  async: false,
				    	          url:ctx+ "/quickGrid/findPage" ,
				    	          method: "post",
				    	          dataType: "json",
				    	          data: data,
				    	          success: function(data){
				    	        	  var tsAwardSrvList= template('tsAwardSrvList' , data);
				    				  $("#toggle"+id).empty();
				    				  $("#toggle"+id).html(tsAwardSrvList);
				    	          }
				    	     });
	    			  } else {
	    				  $(this).html("展开");
	    			  }
	    			  $("#toggle"+id).toggle();
    			  });
	    		  
	    		  $("#test").click(function(){
	    			 alert('ok');
    			  });
	    	});
	    </script>
        <input type="hidden" id="ctx" value="${ctx}" />
    
        <!-- 弹窗 -->
        <div class="modal bonus-modal in" id="add-change" role="dialog" aria-hidden="false">
            <div class="modal-dialog modal-content animated fadeIn" >
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span></button>
                        <h4 class="modal-title">手动添加调整</h4>
                    </div>
                    <div class="modal-body">
                        <div class="ibox-content">
                            <ul>
                                <li class="header">
                                    <span class="wd88">人员</span>
                                    <span class="wd126">案件编号</span>
                                    <span class="wd88">服务</span>
                                    <span class="wd110">奖金</span>
                                    <span class="wd243">备注</span>
                                </li>
                                <li>
                                    <span><input type="text" class="wd88"></span>
                                    <span><input type="text" class="wd126"></span>
                                    <span><input type="text" class="wd88"></span>
                                    <span><input type="text" class="wd110" placeholder="可以为负数"></span>
                                    <span><input type="text" class="wd243"></span>
                                </li>
                                <li>
                                    <span><input type="text" class="wd88"></span>
                                    <span><input type="text" class="wd126"></span>
                                    <span><input type="text" class="wd88"></span>
                                    <span><input type="text" class="wd110" placeholder="可以为负数"></span>
                                    <span><input type="text" class="wd243"></span>
                                </li>
                                <li>
                                    <span><input type="text" class="wd88"></span>
                                    <span><input type="text" class="wd126"></span>
                                    <span><input type="text" class="wd88"></span>
                                    <span><input type="text" class="wd110" placeholder="可以为负数"></span>
                                    <span><input type="text" class="wd243"></span>
                                </li>
                            </ul>
                            <div class="add-btn">
                                <button class="btn btn-primary add-change">+继续添加</button>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <a href="pending.html" class="btn btn-primary confirm" id="approve" data-dismiss="modal">确认</a>
                        <button type="button" class="btn btn-white cancel" data-dismiss="modal">取消</button>
                    </div>       
                </div>
        </div>
        <!-- 弹窗 -->
    </body>
</html>