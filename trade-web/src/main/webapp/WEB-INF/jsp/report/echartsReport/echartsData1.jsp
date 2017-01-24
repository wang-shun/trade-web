<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>e+放款流水清单</title>
    <link href="${ctx }/static/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${ctx }/css/font-awesome.css" rel="stylesheet"/>
    <link href="${ctx}/css/animate.css" rel="stylesheet">
    <link href="${ctx}/css/style.css" rel="stylesheet">

    <!-- index_css -->
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css" />
    <link rel="stylesheet" href="${ctx }/static/trans/css/common/input.css" />
    <link rel="stylesheet" href="${ctx }/static/trans/css/common/btn.css" />
    <link rel="stylesheet" href="${ctx }/static/iconfont/iconfont.css"/>
    <link rel="stylesheet" href="${ctx }/css/eachartdata/eachartdata.css"/>
</head>
<body style="background-color:#fff;">
<!--*********************** HTML_main*********************** -->
<div>
    <div class="ibox-content" id="base_info">
        <div class="row chartwo">
            <div class="col-md-12">
                <div class="clearfix mb30">
                    <h3 class="content-title pull-left">过户数据统计</h3>
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

                    </table>
                </div>
                <div class="pull-left">
                    <div class="plot-rightone">
                        <div id="plotCont2"  style="width:100%;height:300px; ">
                        </div>
                    </div>
                    <div class="plot-righttwo mt10 relative">
                        <div class="total-data">
                            <h3 id="list_title"></h3>
                            <ul class="total-list" id="list_chart">

                            </ul>
                        </div>
                        <p class="zhyu-icon"><img src="${ctx }/css/images/zhongyuan.png" alt="" /></p>
                    </div>
                    <input type="hidden" value="${ctx}" id="ctx">

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
<script src="${ctx }/js/eachartdata/echartCommon.js"></script>

<script>
    $(function() {
        //window.ECHART_LOAD_DATA.turnDate();
        reloadGrid(2016,12);
    })

    function reloadGrid(year,month) {
        var myChart1 = echarts.init(document.getElementById('plotCont1'));
        var myChart2 = echarts.init(document.getElementById('plotCont2'));
        //完整的区(8)
        var districtID = [];
        var districtName = [];
        var data = {
            queryId: "queryDistrict",
            pagination : false
        }
        $.ajax({
            url : $("#ctx").val()+"/quickGrid/findPage",
            method : "GET",
            data : data,
            dataType : "json",
            async:false,
            success : function(data) {
                $.each(data.rows,function(i,item){
                    districtID.push(item.DISTRICT_ID);
                    districtName.push(item.DISTRICT_NAME.substring(0,2));
//                    ECHART_LOAD_DATA.newData.push(0);
//                    ECHART_LOAD_DATA.oldData.push(0);
                })
            },
            error:function(){}
        })

        var xAxisData=[];
        var yAxis = [];
        var legend = [];
        var datas = [];
        var type= [];
        var color = [];
        var myChart = null;
        var pie_items=[];
        var pie_title='';

        var dateMonth='2016-12';


        var newData=[];//最新月份的数据
        var oldData=[];//最新月份的数据

        var noMortCount =0;
        var prfMortCount =0;
        var comMortCount = 0;
        var totalNewDataCount = 0;
        var totalOldDataCount =0;



        if(month!=1){
            legend.push((Number(month))+"月过户总量");
            legend.push((Number(month)-1)+"月过户总量");
        }else{
            legend.push(year+"-"+turnNumber((Number(month)))+"月过户总量");
            legend.push((Number(year)-1)+"-12月过户总量");
        }
        var data = {
            queryId: "queryHourseTransferCaseBaseInfoForDistrict",
            choiceMonth : dateMonth,
            pagination : false
        }

        $.ajax({
            url : $("#ctx").val()+"/quickGrid/findPage",
            method : "GET",
            data : data,
            dataType : "json",
            async:false,
            success : function(data) {
                if(data==null||data==undefined){
                    return;
                }
                $.each(data.rows,function(i,item){
                    noMortCount=noMortCount+item.NO_MORT_COUNT;//最新月份没有贷款的案件数
                    prfMortCount=prfMortCount+item.PRF_COUNT;//最新月份公积金贷款的案件数
                    comMortCount=comMortCount+item.MORTGAGET_TOTAL_COUNT;//最新月份商业贷款的案件数
                    totalNewDataCount=totalNewDataCount+item.HOURSE_TRANSFER_COUNT;//最新月份过户的案件数
                    for(var i=0;i<districtID.length;i++){
                        if(item.DISTRICT_ID == districtID[i]){
                            newData[i]=item.HOURSE_TRANSFER_COUNT;
                        }
                    }
                })
            },
            error:function(){}
        });
        dateMonth='2016-11';
        data = {
            queryId: "queryHourseTransferCaseBaseInfoForDistrict",
            choiceMonth : dateMonth,
            pagination : false
        }

        $.ajax({
            url : $("#ctx").val()+"/quickGrid/findPage",
            method : "GET",
            data : data,
            dataType : "json",
            async:false,
            success : function(data) {
                if(data==null||data==undefined){
                    return;
                }
                $.each(data.rows,function(i,item){
                    totalOldDataCount=totalOldDataCount+item.HOURSE_TRANSFER_COUNT;//上月份过户的案件数
                    for(var i=0;i<districtID.length;i++){
                        if(item.DISTRICT_ID == districtID[i]){
                            oldData[i] = item.HOURSE_TRANSFER_COUNT;
                        }

                    }
                })
            },
            error:function(){}
        });
        xAxisData = districtName;//初始化横轴数据


        //生成柱状图
        var datas=[newData,oldData];
        var type=["bar","bar"];
        var bar_color=null;
        var yAxis =[ {
            type : 'value',//左边
            min:0,
            max:600,
            name : '数量(单)',
            axisLabel : {
                formatter : '{value}'
            }
        }];
        returnBar(xAxisData,yAxis,legend,datas,type,bar_color,myChart1,'报表数据');

        pie_items.push(noMortCount);
        pie_items.push(prfMortCount);
        pie_items.push(comMortCount);

        pie_title=month+'月过户总单量';
        var pie_color=null;
        var dataPie = [ "无贷款", "纯公积金", "商业贷款" ];
        returnPie(dataPie, pie_items, myChart2, pie_color,pie_title);
    }




    /*获取当前年份数据*/
    function getCurrentYear() {
        var year= $(".calendar-year span").html();
        return year;
    }
    /*获取当前月数据*/
    function getCurrentMonth() {
        var month=$(".calendar-month span[class='select-blue']").attr("value");
        return month;
    }
    function turnNumber(num){
        var x;
        switch (num){
            case 1:x="01";break;
            case 2:x="02";break;
            case 3:x="03";break;
            case 4:x="04";break;
            case 5:x="05";break;
            case 6:x="06";break;
            case 7:x="07";break;
            case 8:x="08";break;
            case 9:x="09";break;
            case 10:x="10";break;
            case 11:x="11";break;
            case 12:x="12";break;
        }
        return x;
    }
</script>

</body>
</html>
