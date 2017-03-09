<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>分单签约量统计</title>
        <link href="${ctx }/static/css/bootstrap.min.css" rel="stylesheet"/>
	    <link href="${ctx }/css/font-awesome.css" rel="stylesheet"/>
		<link href="${ctx}/css/animate.css" rel="stylesheet">
	    <link href="${ctx}/css/style.css" rel="stylesheet">

        <!-- index_css -->
        <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
        <link rel="stylesheet" href="${ctx }/static/trans/css/common/input.css" />
        <link rel="stylesheet" href="${ctx }/static/trans/css/common/btn.css" />
        <link rel="stylesheet" href="${ctx }/static/iconfont/iconfont.css">
        <link rel="stylesheet" href="${ctx }/css/eachartdata/eachartdata.css">
    </head>
    <body style="background-color:#fff;">
        <input type="hidden" id="ctx" value="${ctx }"/>
         <!--*********************** HTML_main*********************** -->
        <div>
            <div class="ibox-content" id="base_info">
                <div class="row chartwo">
                    <div class="col-md-12">
                         <div class="clearfix mb30">
                            <h3 class="content-title pull-left">分单、签约量统计</h3>
                            <div class="calendar-watch clearfix">
                                <p class="calendar-year">
                                    <a href="#" id="subtract"><em>&lt;</em></a>
                                    <span>2016</span>
                                    <a href="#" id="add"><em>&gt;</em></a>
                                </p>
                                <p class="calendar-month">
		                            <span value="1">1月</span>
		                            <span value="2">2月</span>
		                            <span value="3">3月</span>
		                            <span value="4">4月</span>
		                            <span value="5">5月</span>
		                            <span value="6">6月</span>
		                            <span value="7">7月</span>
		                            <span value="8">8月</span>
		                            <span value="9">9月</span>
		                            <span value="10">10月</span>
		                            <span value="11">11月</span>
		                            <span value="12">12月</span>
                                </p>
                            </div>
                        </div>
                        <div class="left-content">
                            <div id="plotCont1" class="plot-leftone">
                            </div>
                            <table class="echarsTable">
                            <!-- 图表 -->
                            </table>
                        </div>
                        <div class="pull-left">
                            <div class="plot-rightone relative merge-box">
                                <div class="sum-data">
                                    <h3>数据统计</h3>
                                    <ul class="data-list">
                                        <li><i class='colorBar' style='background-color:#295aa5'></i><em>分单量</em><span id="span1"></span>单</li>
                                        <li><i class='colorBar' style='background-color:#f784a5'></i><em>签约量</em><span id="span2"></span>单</li>
                                        <li><i class='colorBar' style='background-color:#ffad6b'></i><em>过户量</em><span id="span3"></span>单</li>
                                        <li><i class='colorBar' style='background-color:#52bdbd'></i><em>商贷量</em><span id="span4"></span>单</li>
                                        <li><i class='colorBar' style='background-color:#439cf0'></i><em>纯公积金量</em><span id="span5"></span>单</li>
                                    </ul>
                                    <ul class="data-list data-border">
                                        <li><em>商贷签贷占比</em><span id="span6" class="red"></span></li>
                                        <li><em>纯公积金签贷占比</em><span id="span7" class="red"></span></li>
                                    </ul>
                                </div>
                                <p class="zhyu-icon"><img src="${ctx }/css/images/zhongyuan.png" alt="" /></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--*********************** HTML_main*********************** -->

        <!-- Mainly scripts -->
        <script src="${ctx }/js/jquery-2.1.1.js"></script>
        <script src="${ctx }/js/bootstrap.min.js"></script>
        <!-- ECharts.js -->
        <script src="${ctx }/static/js/echarts-all.js"></script>
        <script src="${ctx}/static/trans/js/common/echartCommon.js"></script>
        <script>
        var ctx = $("#ctx").val();
 
        function reloadGrid() {
        	//完整的区(8)
        	var districtIDArr = [];
        	var districtNameArr = [];
        	//获取所有大区
            var data = {
                    queryId: "queryDistrict",
                    pagination : false
                }
           $.ajax({
               url : ctx+"/quickGrid/findPage",
               method : "GET",
               data : data,
               dataType : "json",
               async:false,
               success : function(data) {
                   $.each(data.rows,function(i,item){
                   	districtIDArr.push(item.DISTRICT_ID);
                       districtNameArr.push(item.DISTRICT_NAME.substring(0,2));
                   })
               },
               error:function(){}
           });
        	
        	// 初始化列表
        	var data={};
        	data.queryId = "queryDispatchSignList";	
        	data.pagination = false;
        	var year = window.parent.yearDisplay;
	        var month_ = parseInt(window.parent.monthDisplay)+1;
	        var month = month_ > 9 ? month_:("0"+month_);
        	data.choiceMonth = year + "-" + month;
        	data.belongMoth  = getBelongMonth(year + "-" + month),
        	$.ajax({
        		async: true,
                url: ctx+"/quickGrid/findPage",
                method: "post",
                dataType: "json",
                data: data,
                success: function(data){
				if(data==null||data==undefined){
                    window.parent.wxc.alert("数据加载失败！");
					return;			
				}
				var xAxisData=[];
				var yAxis = [];
            	var legend = [];
            	var datas = [];
            	var type= [];
            	var color = [];
            	var myChart = null;
            	
            	var dispatchNumArr = [];
            	var signNumArr = [];
            	var guohuNumArr = [];
            	var comNumArr = [];
            	var prfNumArr = [];
            	var comPercentArr = [];
            	var prfPercentArr = [];
            	var title = null;
            	//
            	var span1Text = 0;
            	var span2Text = 0;
            	var span3Text = 0;
            	var span4Text = 0;
            	var span5Text = 0;
            	var span6Text = 0;
            	var span7Text = 0;
            	//1.	
        		for(var i in districtIDArr){
        			var flag = false;
                	if(data.rows.length > 0){
            			for(var j in data.rows){
            				item = data.rows[j];
            				if(districtIDArr[i] == item.DISTRICT_ID){
            					xAxisData[i] = districtNameArr[i];
            					dispatchNumArr[i] = parseInt(item.DISPATCH_NUM);
            					span1Text += parseInt(item.DISPATCH_NUM);
            					signNumArr[i] = parseInt(item.SIGN_NUM);
            					span2Text += parseInt(item.SIGN_NUM);
            					guohuNumArr[i] = parseInt(item.GUOHU_NUM);
            					span3Text += parseInt(item.GUOHU_NUM);
            					comNumArr[i] = parseInt(item.COM_NUM);
            					span4Text += parseInt(item.COM_NUM);
            					prfNumArr[i] = parseInt(item.PRF_NUM);
            					span5Text += parseInt(item.PRF_NUM);
            					comPercentArr[i] = accMul(accDiv(parseInt(item.COM_NUM),parseInt(item.SIGN_NUM)),100).replace(".00","");
            					prfPercentArr[i] = accMul(accDiv(parseInt(item.PRF_NUM),parseInt(item.SIGN_NUM)),100).replace(".00","");
            					flag = true;
            				}
            			}
                	}
    			
        			if(!flag){
        				xAxisData[i] = districtNameArr[i];
        				dispatchNumArr[i] = 0;
        				signNumArr[i] = 0;
        				guohuNumArr[i] = 0;
        				comNumArr[i] = 0;
        				prfNumArr[i] = 0;
        				comPercentArr[i] = 0;
        				prfPercentArr[i] = 0;
        			}
        		}
            	
            	span6Text = accMul(accDiv(span4Text,span2Text),100).replace(".00","");
            	span7Text = accMul(accDiv(span5Text,span2Text),100).replace(".00","");
            	//2.
            	yAxis =[ 
            	{
                    type: 'value',//左边
                    name: '单数',
                    min:0,
                    max:800,
                    axisLabel: {
                        formatter: '{value}单 '
                    }
                },
                {
                    type: 'value',//右边
                    name: '百分比',
                    min:0,
                    max:100,
                    axisLabel: {
                        formatter: '{value}%'
                    }
                }
				];
            	//3.
            	legend = data.rows.length>0?['分单量','签约量','过户量','商贷量','纯公积金量','商贷签贷占比','纯公积金占比']:[];
            	//4.
            	datas = [dispatchNumArr,signNumArr,guohuNumArr,comNumArr,prfNumArr,comPercentArr,prfPercentArr];
            	//5.
            	type = ["bar","bar","bar","bar","bar","line","line"];
            	//6.
            	color = null;
            	//7.
            	myChart = echarts.init(document.getElementById('plotCont1'));
            	//8.
            	title = "月分单、签约量统计";
            	//生成柱状图 
            	returnBar(xAxisData,yAxis,legend,datas,type,color,myChart,title);
            	//填充span数据 
            	$("#span1").text(span1Text);
            	$("#span2").text(span2Text);
            	$("#span3").text(span3Text);
            	$("#span4").text(span4Text);
            	$("#span5").text(span5Text);
            	$("#span6").text(span6Text+"%");
            	$("#span7").text(span7Text+"%");
                },
                error: function (e, jqxhr, settings, exception) {
                	   	 
                }  
          });

        }
        </script>

    </body>
</html>
