/**
 * Created by caoyuan7 on 2017/1/13.
 */

(function() {
    window.ECHART_LOAD_DATA = window.ECHART_LOAD_DATA || {

            month:11,//用户选择的月份
            year:2016,//用户选择的月份

            finID:[],//金融机构
            finName:[],//金融机构NAME
            xAxisData:[],//横坐标
            mort_total:[],//贷款总额
            com_total:[],//商业贷款总金额
            mort_loss:[],//流失贷款总金额
            getRate:[],//收单率
            legend:[],//纬度

            url:'',//根路径
            /**
             * 初始化
             */
            init: function(year,month) {
                ECHART_LOAD_DATA.finID.splice(0,ECHART_LOAD_DATA.finID.length);
                ECHART_LOAD_DATA.finName.splice(0,ECHART_LOAD_DATA.finName.length);
                ECHART_LOAD_DATA.xAxisData.splice(0,ECHART_LOAD_DATA.xAxisData.length);
                ECHART_LOAD_DATA.mort_total.splice(0,ECHART_LOAD_DATA.mort_total.length);

                ECHART_LOAD_DATA.com_total.splice(0,ECHART_LOAD_DATA.com_total.length);
                ECHART_LOAD_DATA.mort_loss.splice(0,ECHART_LOAD_DATA.mort_loss.length);
                ECHART_LOAD_DATA.getRate.splice(0,ECHART_LOAD_DATA.getRate.length);

                ECHART_LOAD_DATA.month=month;
                ECHART_LOAD_DATA.year=year;
                ECHART_LOAD_DATA.url=$("#ctx").val();
                ECHART_LOAD_DATA.legend= ["总金额","收单金额","收单率"];
            },
            /*报表一数据获得ajax*/
            getBarAjaxDate: function (dateMonth,dateFlag){
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
                    async:false,
                    success : function(data) {
                        if(data==null||data==undefined){
                            return;
                        }
                        $.each(data.rows,function(i,item){
                            ECHART_LOAD_DATA.finName.push(item.FA_FIN_ORG_NAME),
                            ECHART_LOAD_DATA.mort_total.push(accDiv(item.MORTGAGET_TOTAL_AMOUNT,10000)),
                            ECHART_LOAD_DATA.com_total.push(accDiv(item.COM_AMOUNT,10000));
                            if(item.MORTGAGET_TOTAL_AMOUNT!=0){
                                ECHART_LOAD_DATA.getRate.push(accDiv(item.COM_AMOUNT,item.MORTGAGET_TOTAL_AMOUNT));
                            }else{
                                ECHART_LOAD_DATA.getRate.push('0.00');
                            }
                        })

                    },
                    error:function(){}
                });
                ECHART_LOAD_DATA.xAxisData = ECHART_LOAD_DATA.finName;//初始化横轴数据
            },

            buildBarChart : function(myChart){
                ECHART_LOAD_DATA.getBarAjaxDate(ECHART_LOAD_DATA.year+'-'+ECHART_LOAD_DATA.turnNumber(Number(ECHART_LOAD_DATA.month)),'new');

                var datas=[ECHART_LOAD_DATA.mort_total,ECHART_LOAD_DATA.com_total,ECHART_LOAD_DATA.getRate];

                var type=["bar","bar","line"];
                var yAxis =[ {
                    type : 'value',//左边
                    name : '金额(万元)',
                    axisLabel : {
                        formatter : '{value}'
                    }
                },{
                    type : 'value',//右边
                    name : '比例',
                    axisLabel : {
                        formatter : '{value}'
                    }
                }


                ];
                returnBar(ECHART_LOAD_DATA.xAxisData,yAxis,ECHART_LOAD_DATA.legend,datas,type,null,myChart,"贷款银行分配情况");
            },
            turnDate:function(){//改变年月的方法
                //年份加减
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
                $(".calendar-year span").html(yearDisplay);
                //点击变换颜色&&默认当前月份
                var $month_list = $(".calendar-month span");
                for (var i=0; i<$month_list.length; i++) {
                    if(i == monthDisplay) {
                    	$month_list.eq(i).addClass("select-blue");
                        break;
                    }            
                }
                //增加年份置灰
                $("#add em").addClass("disabled");
                //月份置灰
                if(monthDisplay<11){
                $(".calendar-month span:gt("+monthDisplay+")").addClass("disabled");
                }
                $month_list.on("click",function() {
                    //置灰的月份点击事件失效
                    if($(this).hasClass("disabled")){
                    	return;
                    }
                    $(this).addClass("select-blue").siblings().removeClass('select-blue');
                    var year = $(".calendar-year span").html();
                    var month = $(this).attr("value");
                    reloadGrid(year,month);
                });
                $("#subtract").click(function(){
                	  //正常时间显示
                    $(".calendar-month span").removeClass("disabled");
                    $("#add em").removeClass("disabled");
                    var year=$(".calendar-year span").html();
                    var month=$(".calendar-month span[class='select-blue']").attr("value");
                    $(".calendar-year span").html(year-1);
                    reloadGrid(Number(year)-1,month);
                })
                $("#add").click(function(){
                    //置灰的年份不让增加
                    if($("#add em").hasClass("disabled")){
                    	return;
                    }
                    var year=$(".calendar-year span").html();
                    var month=$(".calendar-month span[class='select-blue']").attr("value");
                    $(".calendar-year span").html(Number(year)+1);
                    if(yearDisplay == (Number(year)+1)){
                    	$("#add em").addClass("disabled");
                    	if(monthDisplay<11){
                    	$(".calendar-month span:gt("+monthDisplay+")").addClass("disabled");
                    	}
                    }
                    reloadGrid(Number(year)+1,month);
                })

           /*     var monthnow = function (){
                    var now   = new Date();
                    var month = now.getMonth();
                    return month;
                }
                var month = monthnow();
                for (var i=0; i<$month_list.length; i++) {
                    if(i == month) {
                        $month_list.eq(i).addClass("select-blue");
                    }
                    return false;
                }*/

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
