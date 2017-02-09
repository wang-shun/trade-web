/**
 * Created by caoyuan7 on 2017/1/13.
 */

(function() {
    window.ECHART_D1_ = window.ECHART_D1_ || {

            month:3,//用户选择的月份
            year:2016,//用户选择的月份

            choiceMonth:'',//选择的年月
            oldChoiceMonth:'',//老的年月

            districtID:[],//所有贵宾服务部ID
            districtName:[],//所有贵宾服务部NAME
            xAxisData:[],//横坐标
            newData:[],//最新月份的数据
            oldData:[],//上个月的数据
            legend: [],//title
            pie_items : [],//饼图的数据
            noMortCount:0,//本月无贷款单数
            prfMortCount:0,//纯公积金单数
            comMortCount:0,//商业贷款单数
            totalNewDataCount:0,//最新月份的过户量
            totalOldDataCount:0,//老新月份的过户量
            pie_title:'',//饼图title
            bar_title:'',//柱状图title
            list_title:'',//列表title
            url:'',//根路径
            /**
             * 初始化
             */
            init: function(year,month) {
                //清空数组
                ECHART_D1_.districtID.splice(0,ECHART_D1_.districtID.length);
                ECHART_D1_.districtName.splice(0,ECHART_D1_.districtName.length);
                ECHART_D1_.xAxisData.splice(0,ECHART_D1_.xAxisData.length);
                ECHART_D1_.newData.splice(0,ECHART_D1_.newData.length);
                ECHART_D1_.oldData.splice(0,ECHART_D1_.oldData.length);
                ECHART_D1_.pie_items.splice(0,ECHART_D1_.pie_items.length);
                ECHART_D1_.legend.splice(0,ECHART_D1_.legend.length);
                ECHART_D1_.noMortCount=0,
                ECHART_D1_.prfMortCount=0,
                ECHART_D1_.comMortCount=0,
                ECHART_D1_.totalNewDataCount=0,
                ECHART_D1_.totalOldDataCount=0,
                ECHART_D1_.month=month;
                ECHART_D1_.year=year;

                ECHART_D1_.url=$("#ctx").val();
            },
            /*报表一数据获得ajax*/
            drawChart: function (choiceMonth,oldChoiceMonth){
                var myChart1 = echarts.init(document.getElementById('plotCont1'));
                var myChart2 = echarts.init(document.getElementById('plotCont2'));
                var data = {
                    queryId: "queryHourseTransferCaseBaseInfoForDistrict",
                    choiceMonth : choiceMonth,
                    belongMoth  : getBelongMonth(choiceMonth),
                    oldChoiceMonth:oldChoiceMonth,
                    pagination : false
                }
                $.ajax({
                    url : $("#ctx").val()+"/quickGrid/findPage",
                    method : "GET",
                    data : data,
                    dataType : "json",
                    async:true,
                    success : function(data) {
                        if(data==null||data==undefined){
                            return;
                        }
                        $.each(data.rows,function(i,item){
                            if(item.CHOICE_MONTH==ECHART_D1_.choiceMonth){
                                ECHART_D1_.noMortCount=ECHART_D1_.noMortCount+item.NO_MORT_COUNT;//最新月份没有贷款的案件数
                                ECHART_D1_.prfMortCount=ECHART_D1_.prfMortCount+item.PRF_COUNT;//最新月份公积金贷款的案件数
                                ECHART_D1_.comMortCount=ECHART_D1_.comMortCount+item.MORTGAGET_TOTAL_COUNT;//最新月份商业贷款的案件数
                                ECHART_D1_.totalNewDataCount=ECHART_D1_.totalNewDataCount+item.HOURSE_TRANSFER_COUNT;//最新月份过户的案件数
                                for(var i=0;i<ECHART_D1_.districtID.length;i++){
                                    if(item.DISTRICT_ID == ECHART_D1_.districtID[i]){
                                        ECHART_D1_.newData[i]=item.HOURSE_TRANSFER_COUNT;
                                    }
                                }
                            }
                            if(item.CHOICE_MONTH==ECHART_D1_.oldChoiceMonth){
                                ECHART_D1_.totalOldDataCount=ECHART_D1_.totalOldDataCount+item.HOURSE_TRANSFER_COUNT;//最新月份过户的案件数
                                for(var i=0;i<ECHART_D1_.districtID.length;i++){
                                    if(item.DISTRICT_ID == ECHART_D1_.districtID[i]){
                                        ECHART_D1_.oldData[i] = item.HOURSE_TRANSFER_COUNT;
                                    }
                                }
                            }
                        })
                        ECHART_D1_.xAxisData = ECHART_D1_.districtName;//初始化横轴数据
                        ECHART_D1_.list_title = ECHART_D1_.month+'月过户总量';//列表title
                        //生成柱状图
                        ECHART_D1_.legend=["过户总量","上月过户总量"];
                        var datas=[ECHART_D1_.newData, ECHART_D1_.oldData];
                        var type=["bar","bar"];
                        var bar_color=null;
                        var yAxis =[ {
                            type : 'value',//左边
                            min:0,
                            max:800,
                            name : '数量(单)',
                            axisLabel : {
                                formatter : '{value}'
                            }
                        }];
                        returnBar(ECHART_D1_.xAxisData,yAxis,ECHART_D1_.legend,datas,type,bar_color,myChart1,ECHART_D1_.bar_title);
                        //饼图生成
                        ECHART_D1_.getPieDate();
                        ECHART_D1_.pie_title=ECHART_D1_.month+'月过户总单量';
                        var pie_color=null;
                        var data = [ "无贷款", "纯公积金", "商业贷款" ];
                        returnPie(data, ECHART_D1_.pie_items, myChart2, pie_color,ECHART_D1_.pie_title);
                        ECHART_D1_.buildListChart();
                    },
                    error:function(){}
                });

            },

            getPieDate : function (){
                ECHART_D1_.pie_items.push(ECHART_D1_.noMortCount);
                ECHART_D1_.pie_items.push(ECHART_D1_.prfMortCount);
                ECHART_D1_.pie_items.push(ECHART_D1_.comMortCount);
            },
            getDistrict: function (){
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
                            ECHART_D1_.districtID.push(item.DISTRICT_ID);
                            ECHART_D1_.districtName.push(item.DISTRICT_NAME.substring(0,2));
                            ECHART_D1_.newData.push(0);
                            ECHART_D1_.oldData.push(0);
                        })
                    },
                    error:function(){}
                });
            },
            buildChart : function(){
                if(ECHART_D1_.month!=1){//如果不是1月则有上个月数据
                    ECHART_D1_.choiceMonth=ECHART_D1_.year+'-'+ECHART_D1_.turnNumber((Number(ECHART_D1_.month)));//选择的年月
                    ECHART_D1_.oldChoiceMonth=ECHART_D1_.year+'-'+ECHART_D1_.turnNumber((Number(ECHART_D1_.month)-1));//老的年月
                    ECHART_D1_.bar_title= ECHART_D1_.month+"月与"+((Number(ECHART_D1_.month))-1)+"月过户总量比较";
                   /* ECHART_D1_.legend.push((Number(ECHART_D1_.month))+"月过户总量");
                    ECHART_D1_.legend.push((Number(ECHART_D1_.month)-1)+"月过户总量");*/
                }else{
                    ECHART_D1_.choiceMonth=ECHART_D1_.year+'-'+ECHART_D1_.turnNumber((Number(ECHART_D1_.month)));//选择的年月
                    ECHART_D1_.oldChoiceMonth=(Number(ECHART_D1_.year)-1)+'-12';//老的年月
                    ECHART_D1_.bar_title= ECHART_D1_.month+"月与去年12月过户总量比较";
                   /* ECHART_D1_.legend.push(ECHART_D1_.year+"-"+ECHART_D1_.turnNumber((Number(ECHART_D1_.month)))+"月过户总量");
                    ECHART_D1_.legend.push((Number(ECHART_D1_.year)-1)+"-12月过户总量");*/
                }
                ECHART_D1_.drawChart(ECHART_D1_.choiceMonth,ECHART_D1_.oldChoiceMonth);

            },

            buildListChart : function(){
                $("#list_chart").html('');
                var html='<li><i class="iconfont mr5 al-yellow al-icon-22">&#xe643;</i>'+ECHART_D1_.month+'月总量<span>'+ECHART_D1_.totalNewDataCount+'</span>单</li>';
                if(ECHART_D1_.month!=1){
                    html=html +'<li><i class="iconfont mr5 al-grey al-icon-22">&#xe643;</i>'+(Number(ECHART_D1_.month)-1)+'月总量<span>'+ECHART_D1_.totalOldDataCount+'</span>单</li>';
                    var subtraction=ECHART_D1_.totalNewDataCount-ECHART_D1_.totalOldDataCount;
                    if(subtraction<0){
                        var percent=100;
                        if(ECHART_D1_.totalOldDataCount!=0){
                            var percent=ECHART_D1_.accMul(accDiv(Math.abs(subtraction),ECHART_D1_.totalOldDataCount),100);
                        }
                        html=html+'<li><i class="iconfont mr5 al-maize  al-icon-22">&#xe651;</i>环比下降<span>'+percent+'%</span></li>';
                    }else if(subtraction>0){
                        var percent=100;
                        if(ECHART_D1_.totalOldDataCount!=0){
                            var percent=ECHART_D1_.accMul(accDiv(Math.abs(subtraction),ECHART_D1_.totalOldDataCount),100);
                        }
                        html=html+'<li><i class="iconfont mr5 al-maize  al-icon-22">&#xe651;</i>环比上升<span>'+percent+'%</span></li>';
                    }
                    else{
                        html=html+'<li><i class="iconfont mr5 al-maize  al-icon-22">&#xe651;</i>无变化<span></span></li>';
                    }
                }

                $("#list_chart").html(html);
            },
            turnDate:function(){//改变年月的方法
                //默认选定上个月
                var yearDisplay,monthDisplay;
                var now = new Date();
                var yearNow = now.getFullYear();
                var monthNow = now.getMonth();
                if(monthNow == 0){
                    monthDisplay = 11;
                    yearDisplay = yearNow - 1;
                }else{
                    monthDisplay = monthNow - 1;
                    yearDisplay = yearNow;
                }
                //点击变换颜色&&默认当前月份
                var $month_list = $(".calendar-month span");
                $(".calendar-year span").html(yearDisplay);
                for (var i=0; i<$month_list.length; i++) {
                    if(i == monthDisplay) {
                        $month_list.eq(i).addClass("select-blue");
                    }
                }
                //增加年份置灰
                $("#add em").addClass("disabled");
                //月份置灰
                if(monthDisplay<11){
                    $(".calendar-month span:gt("+monthDisplay+")").addClass("disabled");
                }
                $month_list.on("click",function() {
                    var year=$(".calendar-year span").html();
                    var month = $(this).attr("value");
                    //置灰的月份点击事件失效
                    if($(this).hasClass("disabled")){
                        return false;
                    }
                    $(this).addClass("select-blue").siblings().removeClass('select-blue');

                    reloadGrid(year,month);
                });
                //年份加减
                $("#subtract").click(function(){
                    var year=$(".calendar-year span").html();
                    var month=$(".calendar-month span[class='select-blue']").attr("value");
                    //正常时间显示
                    $(".calendar-month span").removeClass("disabled");
                    $("#add em").removeClass("disabled");
                    $(".calendar-year span").html(parseInt(year)-1);
                    reloadGrid(Number(year)-1,month);
                })
                $("#add").click(function(){
                    //置灰的年份不让增加
                    if($("#add em").hasClass("disabled")){
                        return false;
                    }
                    var year=$(".calendar-year span").html();
                    var month=$(".calendar-month span[class='select-blue']").attr("value");
                    $(".calendar-year span").html(parseInt(year)+1);
                    if(yearDisplay == (parseInt(year)+1)){
                        $("#add em").addClass("disabled");
                        if(monthDisplay<11){
                            $(".calendar-month span:gt("+monthDisplay+")").addClass("disabled");
                        }
                    }
                    reloadGrid(Number(year)+1,month);
                })
            },
            accMul: function (arg1,arg2)
            {
                var m=0,s1=arg1.toString(),s2=arg2.toString();
                try{m+=s1.split(".")[1].length}catch(e){}
                try{m+=s2.split(".")[1].length}catch(e){}
                return (Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)).toFixed(0);
            },
            /*获取当前年份数据*/
            getCurrentYear: function() {
                var year= $(".calendar-year span").html();
                return year;
            },
            /*获取当前月数据*/
            getCurrentMonth: function() {
                var month=$(".calendar-month span[class='select-blue']").attr("value");
                return month;
            },
            turnNumber:function(num){
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

        };
})();
