<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>签约贷款银行支行流向统计</title>
        <link href="${ctx }/static/css/bootstrap.min.css" rel="stylesheet"/>
	    <link href="${ctx }/css/font-awesome.css" rel="stylesheet"/>
		<link href="${ctx}/css/animate.css" rel="stylesheet">
	    <link href="${ctx}/css/style.css" rel="stylesheet">

        <!-- index_css -->
        <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
        <link rel="stylesheet" href="${ctx }/static/trans/css/common/input.css" />
        <link rel="stylesheet" href="${ctx }/static/trans/css/common/btn.css" />
        <link rel="stylesheet" href="${ctx }/static/iconfont/iconfont.css" ">
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
                            <h3 class="content-title pull-left">签约贷款银行支行流向统计</h3>
                            <div class="calendar-watch clearfix">
                                <p class="calendar-year">
                                    <a href="#" id="subtract"><em>&lt;</em></a>
                                    <span>2016</span>
                                    <a href="#" id="add"><em>&gt;</em></a>
                                </p>
                                <p class="calendar-month">
                                    <span >1月</span><span>2月</span><span>3月</span><span>4月</span><span>5月</span><span>6月</span><span>7月</span><span>8月</span><span>9月</span><span>10月</span><span>11月</span><span>12月</span>
                                </p>
                            </div>
                        </div>
                        <div class="left-content">
                            <div id="plotCont1" class="plot-leftone">
                            </div>
                            <table class="echarsTable">
                            </table>
                            <div class="label-tip clearfix">
                                <span class="labelbar laberbarcolor"></span><p>非入围银行</p>
                            </div>
                        </div>
                        <div class="pull-left">
                            <div class="plot-rightone relative merge-box">
                                <div class="sum-data">
                                  <h3>数据统计</h3>
                                    <ul class="data-list else-list">
                                        <li><em>商贷贷款单数</em><span id="span1"></span>单</li>
                                        <li><em>商贷金额</em><span id="span2"></span>万元</li>
                                    </ul>
                                    <hr />
                                    <table id="displayTable" class="table table-bordered text-center else-table">
                                            <thead>
                                                <tr>
                                                    <th class="text-center" colspan="3" id="th1">其他类：0万元
                                                    </th>
                                                </tr>
                                                <tr>
                                                    <th class="text-center">前十银行</th>
                                                    <th class="text-center">总金额</th>
                                                    <th class="text-center">是否入围</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                    <!-- 其他银行信息 -->
                                            </tbody>
                                    </table>
                                    
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
        <script src="${ctx }/static_res/js/echarts-all.js"></script>
        <script src="${ctx}/static/trans/js/common/echartCommon.js"></script>
        <%-- <script src="${ctx }/js/eachartdata/select_month.js"></script> --%>
        <script>
        /**
         * 案件统计详情
         */
     	var ctx = $("#ctx").val();

        function reloadGrid() {
        	// 初始化列表
        	var data = {};
        	data.queryId = "querySignBranchBankList";	
        	data.pagination = false;
        	var year = window.parent.yearDisplay;
	        var month = parseInt(window.parent.monthDisplay)+1;
        	data.choiceMonth = year + "-" + month;
        	
        	$.ajax({
        		async: true,
                url: ctx+"/quickGrid/findPage",
                method: "post",
                dataType: "json",
                data: data,
                success: function(data){
				if(data==null||data==undefined){
					window.wxc.alert("数据加载失败！");
					return;			
				}
				var xAxisData=[];
				var yAxis = [];
            	var legend = [];
            	var datas = [];
            	var type= [];
            	var color = [];
            	var myChart = null;
            	
            	var totalAmountArr = [];
            	var totalAmount = 0;
            	var title = null;
            	//
            	var span1Text = 0;
            	var span2Text = 0;
            	//1.
            	if(data.rows){  
            		//其他
            		var xAxisDataI = null;
            		var totalAmountArrI = 0;
            		var otherBankNameArr = [];
            		var otherAmountArr = [];
            		var IsRuweiBankArr = [];
            		var otherIsRuweiBankArr = [];
            		
            		$.each(data.rows,function(i,item){
            			span1Text = accAdd(Number(span1Text),accDiv(parseInt(item.CONTRACT_AMOUNT),10000));		
      					span2Text += parseInt(item.SIGN_NUM);
      					var fa_fin_org_name_yc = item.FA_FIN_ORG_NAME_YC.length>2?item.FA_FIN_ORG_NAME_YC.substring(0,2):item.FA_FIN_ORG_NAME_YC;
                        var fin_org_name_yc = item.FIN_ORG_NAME_YC.length>2?item.FIN_ORG_NAME_YC.substring(0,2):item.FIN_ORG_NAME_YC;
            			//前14个直接显示
      					if(i < 14){		
                            xAxisData.push(fa_fin_org_name_yc+fin_org_name_yc);
          					totalAmountArr.push(Math.round(accDiv(parseInt(item.CONTRACT_AMOUNT),10000)));	
          					IsRuweiBankArr.push(item.RUWEI_BANK == 'cl'?'1':'0');
            			//后面的加入到‘其他’
      					}else{
      						otherBankNameArr.push(fa_fin_org_name_yc+fin_org_name_yc);
      						otherAmountArr.push(Math.round(accDiv(parseInt(item.CONTRACT_AMOUNT),10000)));
      						otherIsRuweiBankArr.push(item.RUWEI_BANK == 'cl'?'1':'0');
      						totalAmountArrI = accAdd(totalAmountArrI,accDiv(parseInt(item.CONTRACT_AMOUNT),10000));
            			}                  
        			}) 
        			
        			if(data.rows.length > 14){
        				xAxisData.push("其他");
        				totalAmountArr.push(Math.round(totalAmountArrI));
        				$("#th1").html("其他类："+totalAmountArrI+"万元");
        				var tbodyContent = "";
        				for(var j = 0;j < 10;j++){
        					tbodyContent += "<tr>";
        					tbodyContent += "<td>"+otherBankNameArr[j]+"</td><td>"+otherAmountArr[j]+"</td>";
        					if(otherIsRuweiBankArr[i] == '1'){
        						tbodyContent += "<td class='ok-blue'>是</td>";
        					}else if(otherIsRuweiBankArr[i] == '0'){
        						tbodyContent += "<td>否</td>";
        					}else{
        						tbodyContent += "<td></td>";
        					} 
                            tbodyContent += "</tr>";
        				}

        				$("#displayTable tbody").html(tbodyContent);
        			}
            	}
            	//2.
            	yAxis =[ 
            	{
                    type: 'value',//左边
                    name: '金额',
                    min:0,
                    max:1000000,
                    axisLabel: {
                        formatter: '{value}万 '
                    }
                }
				];
            	//3.
            	legend = ['总金额'];
            	//4.
            	datas = [totalAmountArr];
            	//5.
            	type = ["bar"];
            	//6.
            	color = null;
            	//7.
            	myChart = echarts.init(document.getElementById('plotCont1'));
            	//8.
            	title = "签约贷款银行支行流向统计";
            	//生成柱状图 
            	returnBar(xAxisData,yAxis,legend,datas,type,color,myChart,title);
            	//非入围银行背景颜色修改
            	$(".echarsTable").find("thead .tabletitle~td").each(function(i,item){
            		if(IsRuweiBankArr[i] == '0'){
            			$(item).attr("bgcolor","#ff9695");
            		}
            	});
            	//填充span数据 
            	$("#span1").text(span2Text);
            	$("#span2").text(Math.round(span1Text));
                },
                error: function (e, jqxhr, settings, exception) {
                	   	 
                }  
          });

        }
        </script>
    </body>
</html>
