/**
 * Created by caoyuan7 on 2017/1/13.
 */

(function() {
    window.ECHART_D3_ = window.ECHART_D3_ || {

            month:12,//用户选择的月份
            year:2016,//用户选择的月份

            choiceMonth:'',//选择的年月
            oldChoiceMonth:'',//老的年月

            districtID:[],//所有贵宾服务部ID
            districtName:[],//所有贵宾服务部NAME
            xAxisData:[],
            mort_total:[],//商业贷款总金额
            mort_loss:[],//流失贷款总金额
            lossRate:[],//流失率
            oldLossRate:[],//上月流失率
            legend:[],//纬度
            totalLossAmount:0,//本月总流失金额
            totalComMortAmount:0,//本月商业贷款金额
            pie_items : [],//饼图的数据
            url:'',//根路径
            /**
             * 初始化
             */
            init: function(year,month) {
                ECHART_D3_.districtID.splice(0,ECHART_D3_.districtID.length);
                ECHART_D3_.districtName.splice(0,ECHART_D3_.districtName.length);
                ECHART_D3_.xAxisData.splice(0,ECHART_D3_.xAxisData.length);
                ECHART_D3_.mort_total.splice(0,ECHART_D3_.mort_total.length);
                ECHART_D3_.mort_loss.splice(0,ECHART_D3_.mort_loss.length);
                ECHART_D3_.lossRate.splice(0,ECHART_D3_.lossRate.length);
                ECHART_D3_.oldLossRate.splice(0,ECHART_D3_.oldLossRate.length);
                ECHART_D3_.legend.splice(0,ECHART_D3_.legend.length);
                ECHART_D3_.pie_items.splice(0,ECHART_D3_.pie_items.length);
                ECHART_D3_.totalLossAmount=0;
                ECHART_D3_.totalComMortAmount=0;

                ECHART_D3_.month=month;
                ECHART_D3_.year=year;
                ECHART_D3_.url=$("#ctx").val();
                ECHART_D3_.legend = ["商贷总额","流失金额","流失率","上月流失率"];
            },
            /*报表一数据获得ajax*/
            drawChart: function (choiceMonth,oldChoiceMonth){
                var myChart1 = echarts.init(document.getElementById('plotCont1'));
                var myChart2 = echarts.init(document.getElementById('plotCont2'));
                var data = {
                    queryId: "queryHourseTransferCaseBaseInfoForDistrict",
                    choiceMonth : choiceMonth,
                    oldChoiceMonth:oldChoiceMonth,
                    belongMoth  : getBelongMonth(choiceMonth),
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
                            if(item.CHOICE_MONTH==ECHART_D3_.choiceMonth){
                                ECHART_D3_.totalLossAmount=accAdd(ECHART_D3_.totalLossAmount,item.LOST_AMOUNT);
                                ECHART_D3_.totalComMortAmount= accAdd(ECHART_D3_.totalComMortAmount,item.MORTGAGET_COM_AMOUNT);
                                for(var i=0;i<ECHART_D3_.districtID.length;i++){
                                    if(item.DISTRICT_ID == ECHART_D3_.districtID[i]){
                                        ECHART_D3_.mort_total[i]=(item.MORTGAGET_COM_AMOUNT/10000).toFixed(0);
                                        ECHART_D3_.mort_loss[i]=(item.LOST_AMOUNT/10000).toFixed(0);
                                        if(item.MORTGAGET_COM_AMOUNT!=0){
                                            ECHART_D3_.lossRate[i]=accDiv(item.LOST_AMOUNT,item.MORTGAGET_COM_AMOUNT);
                                            ECHART_D3_.oldLossRate[i]=accDiv(item.LOST_AMOUNT,item.MORTGAGET_TOTAL_AMOUNT);
                                        }
                                    }
                                }
                            }
                            if(item.CHOICE_MONTH==ECHART_D3_.oldChoiceMonth){
                                for(var i=0;i<ECHART_D3_.districtID.length;i++){
                                    if(item.DISTRICT_ID == ECHART_D3_.districtID[i]){
                                        if(item.MORTGAGET_TOTAL_AMOUNT!=0){
                                            ECHART_D3_.oldLossRate[i]=accDiv(item.LOST_AMOUNT,item.MORTGAGET_TOTAL_AMOUNT);
                                        }
                                    }

                                }
                            }

                        })
                        ECHART_D3_.xAxisData = ECHART_D3_.districtName;//初始化横轴数据
                        var datas=[ECHART_D3_.mort_total,ECHART_D3_.mort_loss,ECHART_D3_.lossRate,ECHART_D3_.oldLossRate];
                        var type=["bar","bar","line","line"];
                        var yAxis =[ {
                            type : 'value',//左边
                            name : '金额(万元)',
                            min:0,
                            max:80000,
                            axisLabel : {
                                formatter : '{value}'
                            }
                        },{
                            type : 'value',//右边
                            name : '比例',
                            min:0,
                            max:1,
                            axisLabel : {
                                formatter : '{value}'
                            }
                        }

                        ];
                        returnBar(ECHART_D3_.xAxisData,yAxis,ECHART_D3_.legend,datas,type,null,myChart1,"各贵宾中心商贷比较");
                        var unit='';
                        var pie1=accDiv(accSub(ECHART_D3_.totalLossAmount,ECHART_D3_.totalComMortAmount),10000);
                        var pie2=accDiv(ECHART_D3_.totalLossAmount,10000);
                        ECHART_D3_.pie_items.push(Number(pie1).toFixed(0));
                        ECHART_D3_.pie_items.push(Number(pie2).toFixed(0));
                        var color=null;
                        var data = [ "收单", "流失" ];
                        returnPie(data, ECHART_D3_.pie_items, myChart2, color,"商贷总金额",unit);
                    },
                    error:function(){}
                });

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
                            ECHART_D3_.districtID.push(item.DISTRICT_ID);
                            ECHART_D3_.districtName.push(item.DISTRICT_NAME.substring(0,2));
                            ECHART_D3_.mort_total.push(0);
                            ECHART_D3_.mort_loss.push(0);
                            ECHART_D3_.lossRate.push('0.00');
                            ECHART_D3_.oldLossRate.push('0.00');
                        })
                    },
                    error:function(){}
                });
            },
            buildChart : function(){
                if(Number(ECHART_D3_.month)!=1){//如果不是1月则有上个月数据
                    ECHART_D3_.choiceMonth=ECHART_D3_.year+'-'+ECHART_D3_.turnNumber((Number(ECHART_D3_.month)));//选择的年月
                    ECHART_D3_.oldChoiceMonth=ECHART_D3_.year+'-'+ECHART_D3_.turnNumber((Number(ECHART_D3_.month)-1));//老的年月
                }else{
                    ECHART_D3_.choiceMonth=ECHART_D3_.year+'-'+ECHART_D3_.turnNumber((Number(ECHART_D3_.month)));//选择的年月
                    ECHART_D3_.oldChoiceMonth=(Number(ECHART_D3_.year)-1)+'-12';//老的年月
                    ECHART_D3_.bar_title= ECHART_D3_.month+"月过户总量";
                }

                ECHART_D3_.drawChart(ECHART_D3_.choiceMonth,ECHART_D3_.oldChoiceMonth);


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
