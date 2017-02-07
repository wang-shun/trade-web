/**
 * Created by caoyuan7 on 2017/1/13.
 */

(function() {
    window.ECHART_D4_ = window.ECHART_D4_ || {

            month:11,//用户选择的月份
            year:2016,//用户选择的月份

            finID:[],//金融机构
            finName:[],//金融机构NAME
            xAxisData:[],//横坐标
            com_total:[],//贷款总额
            shou_total:[],//收单金额
            getRate:[],//收单率
            legend:[],//纬度
            finLimit:13,//银行限制条数
            url:'',//根路径
            /**
             * 初始化
             */
            init: function(year,month) {
                ECHART_D4_.finID.splice(0,ECHART_D4_.finID.length);
                ECHART_D4_.finName.splice(0,ECHART_D4_.finName.length);
                ECHART_D4_.xAxisData.splice(0,ECHART_D4_.xAxisData.length);
                ECHART_D4_.com_total.splice(0,ECHART_D4_.com_total.length);

                ECHART_D4_.shou_total.splice(0,ECHART_D4_.shou_total.length);
                ECHART_D4_.getRate.splice(0,ECHART_D4_.getRate.length);

                ECHART_D4_.month=month;
                ECHART_D4_.year=year;
                ECHART_D4_.url=$("#ctx").val();
                ECHART_D4_.legend= ["总金额","收单金额","收单率"];
            },
            /*报表一数据获得ajax*/
            getBarAjaxDate: function (dateMonth){
                var myChart1 = echarts.init(document.getElementById('plotCont1'));
                var isOutBound=false;
                var outBoundComTotal=0;
                var outBoundShouTotal=0;
                var data = {
                    queryId: "queryHourseTransferCaseBaseInfoForFin",
                    choiceMonth : dateMonth,
                    pagination : false
                }
                $.ajax({
                    url : $("#ctx").val()+"/quickGrid/findPage",
                    method : "GET",
                    data : data,
                    dataType : "json",
                    async:true,
                    success : function(data) {
                        ECHART_D4_.finID.splice(0,ECHART_D4_.finID.length);
                        ECHART_D4_.finName.splice(0,ECHART_D4_.finName.length);
                        ECHART_D4_.xAxisData.splice(0,ECHART_D4_.xAxisData.length);
                        ECHART_D4_.com_total.splice(0,ECHART_D4_.com_total.length);

                        ECHART_D4_.shou_total.splice(0,ECHART_D4_.shou_total.length);
                        ECHART_D4_.getRate.splice(0,ECHART_D4_.getRate.length);

                        if(data==null||data==undefined){
                            return;
                        }
                        $.each(data.rows,function(i,item){
                            if(i<ECHART_D4_.finLimit){
                                ECHART_D4_.finName.push(item.FA_FIN_ORG_NAME);
                                ECHART_D4_.com_total.push(ECHART_D4_.accDiv(item.COM_AMOUNT,10000));
                                ECHART_D4_.shou_total.push(ECHART_D4_.accDiv(accSub(item.LOST_AMOUNT,item.COM_AMOUNT),10000));
                                if(item.COM_AMOUNT!=0){
                                    ECHART_D4_.getRate.push(accDiv(accSub(item.LOST_AMOUNT,item.COM_AMOUNT),item.COM_AMOUNT));
                                }else{
                                    ECHART_D4_.getRate.push('0.00');
                                }
                            }
                            else{
                                isOutBound=true;
                                outBoundComTotal=ECHART_D4_.accAdd(Number(outBoundComTotal),accDiv(item.COM_AMOUNT,10000));
                                outBoundShouTotal=ECHART_D4_.accAdd(Number(outBoundShouTotal),accDiv(accSub(item.LOST_AMOUNT,item.COM_AMOUNT),10000));
                            }
                        });
                        if(isOutBound){
                            ECHART_D4_.finName.push('其他')
                            ECHART_D4_.com_total.push(outBoundComTotal);
                            ECHART_D4_.shou_total.push(outBoundShouTotal);
                            if(outBoundComTotal!=0){
                                ECHART_D4_.getRate.push(accDiv(outBoundShouTotal,outBoundComTotal));
                            }else{
                                ECHART_D4_.getRate.push('0.00');
                            }
                        }
                        ECHART_D4_.xAxisData = ECHART_D4_.finName;//初始化横轴数据
                        var datas=[ECHART_D4_.com_total,ECHART_D4_.shou_total,ECHART_D4_.getRate];
                        var type=["bar","bar","line"];
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
                        returnBar(ECHART_D4_.xAxisData,yAxis,ECHART_D4_.legend,datas,type,null,myChart1,"贷款银行分配情况");

                    },
                    error:function(){}
                });
            },

            buildBarChart : function(){
                ECHART_D4_.getBarAjaxDate(ECHART_D4_.year+'-'+ECHART_D4_.turnNumber(Number(ECHART_D4_.month)));
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
            accAdd : function (arg1,arg2){
                var r1,r2,m;
                try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
                try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
                m=Math.pow(10,Math.max(r1,r2));
                return ((arg1*m+arg2*m)/m).toFixed(0);
            },
            accDiv: function (arg1,arg2){
                var t1=0,t2=0,r1,r2;
                try{t1=arg1.toString().split(".")[1].length}catch(e){}
                try{t2=arg2.toString().split(".")[1].length}catch(e){}
                with(Math){
                    r1=Number(arg1.toString().replace(".",""));
                    r2=Number(arg2.toString().replace(".",""));
                    return ((r1/r2)*pow(10,t2-t1)).toFixed(0);
                }
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
