<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
    <meta charset="utf-8">
    <title>签约贷款数据报表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${ctx}/css/font-awesome.css" rel="stylesheet"/>
	<link href="${ctx}/css/animate.css" rel="stylesheet">
    <link href="${ctx}/css/style.css" rel="stylesheet">

    <link rel="stylesheet" href="${ctx}/css/eachartdata/jquery.fullPage.css" />
    <link rel="stylesheet" href="${ctx}/css/eachartdata/eachartdata.css" />
</head>
<body style="background-color: #fff">

<ul id="menu">
    <li data-menuanchor="firstPage"><a href="#firstPage">派单签约量统计</a></li>
    <li data-menuanchor="secondPage"><a href="#secondPage">签约贷款金额占比</a></li>
    <li data-menuanchor="3rdPage"><a href="#3rdPage">签约贷款银行分布</a></li>
    <li data-menuanchor="4thPage"><a href="#4thPage">签约贷款银行支行流向统计</a></li>
</ul>

<div id="fullpage">
    <div class="section " id="section0">
        <iframe src="http://trade.centaline.com:8083/trade-web/report/echartsData5" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div>
    <div class="section" id="section1">
        <iframe src="http://trade.centaline.com:8083/trade-web/report/echartsData6" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div>
    <div class="section" id="section2">
        <iframe src="http://trade.centaline.com:8083/trade-web/report/echartsData7" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div> 
    <div class="section" id="section3">
        <iframe src="http://trade.centaline.com:8083/trade-web/report/echartsData8" width="100%" height="100%" frameborder="0" scrolling="no" name=""></iframe>
    </div>
</div>
<script  src="${ctx }/js/jquery-2.1.1.js"></script>
<script  src="${ctx }/js/eachartdata/jquery-ui.min.js"></script>
<script  src="${ctx }/js/eachartdata/scrolloverflow.js"></script>
<script  src="${ctx }/js/eachartdata/jquery.fullPage.js"></script>
<script  src="${ctx }/js/eachartdata/fullPage-setting.js"></script>
<script>
function reloadGrid5() {
	// 初始化列表
	var data={};
	data.queryId = "queryDispatchSignList";	
	data.pagination = false;
	var year = $(".calendar-year span").html();
	var month = $(".calendar-month span").has(".select-blue").html().substring(0,1);
	//data.choiceMonth = year + "-" + month;
    data.choiceMonth = "2016-11";
	
	$.ajax({
		async: true,
        url: ctx+"/quickGrid/findPage",
        method: "post",
        dataType: "json",
        data: data,
        success: function(data){
		if(data==null||data==undefined){
			alert("数据加载失败！");
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
    	$.each(data.rows,function(i,item){
			xAxisData.push(item.DISTRICT_NAME.length>2?item.DISTRICT_NAME.substring(0,2):item.DISTRICT_NAME);
			dispatchNumArr.push(item.DISPATCH_NUM);
			span1Text += parseInt(item.DISPATCH_NUM);
			signNumArr.push(item.SIGN_NUM);
			span2Text += parseInt(item.SIGN_NUM);
			guohuNumArr.push(item.GUOHU_NUM);
			span3Text += parseInt(item.GUOHU_NUM);
			comNumArr.push(item.COM_NUM);
			span4Text += parseInt(item.COM_NUM);
			prfNumArr.push(item.PRF_NUM);
			span5Text += parseInt(item.PRF_NUM);
			comPercentArr.push(accDiv(item.COM_NUM,item.SIGN_NUM)*100);
			prfPercentArr.push(accDiv(item.PRF_NUM,item.SIGN_NUM)*100);			
		})
    	span6Text = accDiv(span4Text,span2Text)*100+"%";
    	span7Text = accDiv(span5Text,span2Text)*100+"%";
    	//2.
    	yAxis =[ 
    	{
            type: 'value',//左边
            name: '金额(万)',
            min: 0,
            //max: 250,
            //interval: 50,
            axisLabel: {
                formatter: '{value} '
            }
        },
        {
            type: 'value',//右边
            name: '百分比',
            min: 0,
            max: 100,
            //interval: 6,
            axisLabel: {
                formatter: '{value} %'
            }
        }
		];
    	//3.
    	legend = ['派单量','签约量','过户量','商贷量','纯公积金量','商贷签贷占比','纯公积金占比'];
    	//4.
    	datas = [dispatchNumArr,signNumArr,guohuNumArr,comNumArr,prfNumArr,comPercentArr,prfPercentArr];
    	//5.
    	type = ["bar","bar","bar","bar","bar","line","line"];
    	//6.
    	color = null;
    	//7.
    	myChart = echarts.init(document.getElementById('plotCont1'));
    	//8.
    	title = "月派单、签约量统计";
    	//生成柱状图 
    	returnBar(xAxisData,yAxis,legend,datas,type,color,myChart,title);
    	console.dir(myChart);
    	//填充span数据 
    	$("#span1").text(span1Text);
    	$("#span2").text(span2Text);
    	$("#span3").text(span3Text);
    	$("#span4").text(span4Text);
    	$("#span5").text(span5Text);
    	$("#span6").text(span6Text);
    	$("#span7").text(span7Text);
        },
        error: function (e, jqxhr, settings, exception) {
        	   	 
        }  
  });

}
</script>
</script>
</body>
</html>
