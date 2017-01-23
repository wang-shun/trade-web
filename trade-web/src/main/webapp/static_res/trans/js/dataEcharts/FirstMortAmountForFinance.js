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
                ECHART_LOAD_DATA.finID.splice(0,ECHART_LOAD_DATA.finID.length);
                ECHART_LOAD_DATA.finName.splice(0,ECHART_LOAD_DATA.finName.length);
                ECHART_LOAD_DATA.xAxisData.splice(0,ECHART_LOAD_DATA.xAxisData.length);
                ECHART_LOAD_DATA.com_total.splice(0,ECHART_LOAD_DATA.com_total.length);

                ECHART_LOAD_DATA.shou_total.splice(0,ECHART_LOAD_DATA.shou_total.length);
                ECHART_LOAD_DATA.getRate.splice(0,ECHART_LOAD_DATA.getRate.length);

                ECHART_LOAD_DATA.month=month;
                ECHART_LOAD_DATA.year=year;
                ECHART_LOAD_DATA.url=$("#ctx").val();
                ECHART_LOAD_DATA.legend= ["总金额","收单金额","收单率"];
            },
            /*报表一数据获得ajax*/
            getBarAjaxDate: function (dateMonth,dateFlag){
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
                    async:false,
                    success : function(data) {
                        if(data==null||data==undefined){
                            return;
                        }
                        $.each(data.rows,function(i,item){
                            if(i<ECHART_LOAD_DATA.finLimit){
                                ECHART_LOAD_DATA.finName.push(item.FA_FIN_ORG_NAME);
                                ECHART_LOAD_DATA.com_total.push(ECHART_LOAD_DATA.accDiv(item.COM_AMOUNT,10000));
                                ECHART_LOAD_DATA.shou_total.push(ECHART_LOAD_DATA.accDiv(accSub(item.LOST_AMOUNT,item.COM_AMOUNT),10000));
                                if(item.COM_AMOUNT!=0){
                                    ECHART_LOAD_DATA.getRate.push(accDiv(accSub(item.LOST_AMOUNT,item.COM_AMOUNT),item.COM_AMOUNT));
                                }else{
                                    ECHART_LOAD_DATA.getRate.push('0.00');
                                }
                            }
                            else{
                                isOutBound=true;
                                outBoundComTotal=ECHART_LOAD_DATA.accAdd(Number(outBoundComTotal),accDiv(item.COM_AMOUNT,10000));
                                outBoundShouTotal=ECHART_LOAD_DATA.accAdd(Number(outBoundShouTotal),accDiv(accSub(item.LOST_AMOUNT,item.COM_AMOUNT),10000));
                            }
                        })
                    },
                    error:function(){}
                });
                ECHART_LOAD_DATA.xAxisData = ECHART_LOAD_DATA.finName;//初始化横轴数据
                if(isOutBound){
                    ECHART_LOAD_DATA.finName.push('其他')
                    ECHART_LOAD_DATA.com_total.push(outBoundComTotal);
                    ECHART_LOAD_DATA.shou_total.push(outBoundShouTotal);
                    if(outBoundComTotal!=0){
                        ECHART_LOAD_DATA.getRate.push(accDiv(outBoundShouTotal,outBoundComTotal));
                    }else{
                        ECHART_LOAD_DATA.getRate.push('0.00');
                    }
                }
            },

            buildBarChart : function(myChart){
                ECHART_LOAD_DATA.getBarAjaxDate(ECHART_LOAD_DATA.year+'-'+ECHART_LOAD_DATA.turnNumber(Number(ECHART_LOAD_DATA.month)),'new');
                var datas=[ECHART_LOAD_DATA.com_total,ECHART_LOAD_DATA.shou_total,ECHART_LOAD_DATA.getRate];
                var type=["bar","bar","line"];
                var yAxis =[ {
                    type : 'value',//左边
                    name : '金额(万元)',
                    min:0,
                    max:100000,
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
                returnBar(ECHART_LOAD_DATA.xAxisData,yAxis,ECHART_LOAD_DATA.legend,datas,type,null,myChart,"贷款银行分配情况");
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
