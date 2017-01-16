/**
 * Created by caoyuan7 on 2017/1/13.
 */

(function() {
    window.ECHART_LOAD_DATA = window.ECHART_LOAD_DATA || {

            month:12,//用户选择的月份
            year:2016,//用户选择的月份

            districtID:[],//所有贵宾服务部ID
            districtName:[],//所有贵宾服务部NAME
            xAxisData:[],
            mort_total:[],//商业贷款总金额
            mort_loss:[],//流失贷款总金额
            lossRate:[],//流失率
            oldLossRate:[],//上月流失率
            legend:'',//纬度
            totalLossAmount:0,//本月总流失金额
            totalComMortAmount:0,//本月商业贷款金额
            pie_items : [],//饼图的数据
            /* xAxisData:[],//横坐标
             newData:[],//最新月份的数据
             oldData:[],//上个月的数据
             legend: [],//title

             noMortCount:0,//本月无贷款单数
             prfMortCount:0,//纯公积金单数
             comMortCount:0,//商业贷款单数
             totalNewDataCount:0,//最新月份的过户量
             totalOldDataCount:0,//老新月份的过户量
             pie_title:'',//饼图title
             bar_title:'',//柱状图title
             list_title:'',//列表title*/
            url:'',//根路径
            /**
             * 初始化
             */
            init: function() {
                /*ECHART_LOAD_DATA.month=ECHART_LOAD_DATA.getCurrentMonth();*///***************************************测试暂时注释掉,正式测试时清打开***************************************
                /*ECHART_LOAD_DATA.year=ECHART_LOAD_DATA.getCurrentYear();*///***************************************测试暂时注释掉,正式测试时清打开***************************************
                ECHART_LOAD_DATA.url=$("#ctx").val();
                ECHART_LOAD_DATA.legend = ["商贷总额(万元)","流失金额(万元)","流失率","上月流失率"];
            },
            /*报表一数据获得ajax*/
            getBarAjaxDate: function (dateMonth,dateFlag){
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
                            if(dateFlag=='new'){
                                ECHART_LOAD_DATA.totalLossAmount=accAdd(ECHART_LOAD_DATA.totalLossAmount,item.LOST_AMOUNT);
                                ECHART_LOAD_DATA.totalComMortAmount= accAdd(ECHART_LOAD_DATA.totalComMortAmount,item.MORTGAGET_COM_AMOUNT);
                                for(var i=0;i<ECHART_LOAD_DATA.districtID.length;i++){
                                    if(item.DISTRICT_ID == ECHART_LOAD_DATA.districtID[i]){
                                        ECHART_LOAD_DATA.mort_total[i]=item.MORTGAGET_COM_AMOUNT/10000;
                                        ECHART_LOAD_DATA.mort_loss[i]=item.LOST_AMOUNT/10000;
                                        if(item.MORTGAGET_TOTAL_AMOUNT!=0){
                                            ECHART_LOAD_DATA.lossRate[i]=accDiv(item.LOST_AMOUNT,item.MORTGAGET_TOTAL_AMOUNT);
                                        }else{
                                            ECHART_LOAD_DATA.lossRate[i]=0;
                                        }

                                    }
                                }
                            }
                            if(dateFlag=='old'){
                                for(var i=0;i<ECHART_LOAD_DATA.districtID.length;i++){
                                    if(item.DISTRICT_ID == ECHART_LOAD_DATA.districtID[i]){
                                        if(item.MORTGAGET_TOTAL_AMOUNT!=0){
                                            ECHART_LOAD_DATA.oldLossRate[i]=accDiv(item.LOST_AMOUNT,item.MORTGAGET_TOTAL_AMOUNT);
                                        }else{
                                            ECHART_LOAD_DATA.oldLossRate[i]=0;
                                        }
                                    }

                                }
                            }

                        })

                    },
                    error:function(){}
                });
                ECHART_LOAD_DATA.xAxisData = ECHART_LOAD_DATA.districtName;//初始化横轴数据
            },

            getPieDate : function (){
                ECHART_LOAD_DATA.pie_items.push(accDiv(ECHART_LOAD_DATA.totalComMortAmount,10000));
                ECHART_LOAD_DATA.pie_items.push(accDiv(ECHART_LOAD_DATA.totalLossAmount,10000));
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
                            ECHART_LOAD_DATA.districtID.push(item.DISTRICT_ID)
                            ECHART_LOAD_DATA.districtName.push(item.DISTRICT_NAME.substring(0,2));
                            ECHART_LOAD_DATA.mort_total.push(0);
                            ECHART_LOAD_DATA.mort_loss.push(0);
                            ECHART_LOAD_DATA.lossRate.push(0);
                            ECHART_LOAD_DATA.oldLossRate.push(0);
                        })
                    },
                    error:function(){}
                });
            },
            buildBarChart : function(myChart){
                if(ECHART_LOAD_DATA.month!=1){//如果不是1月则有上个月数据
                    ECHART_LOAD_DATA.getBarAjaxDate(ECHART_LOAD_DATA.year+'-'+ECHART_LOAD_DATA.turnNumber((ECHART_LOAD_DATA.month-1)),'old');
                    ECHART_LOAD_DATA.getBarAjaxDate(ECHART_LOAD_DATA.year+'-'+ECHART_LOAD_DATA.turnNumber(ECHART_LOAD_DATA.month),'new');
                }else{
                    ECHART_LOAD_DATA.getBarAjaxDate(ECHART_LOAD_DATA.year+'-'+ECHART_LOAD_DATA.turnNumber(ECHART_LOAD_DATA.month),'new');
                    ECHART_LOAD_DATA.bar_title= ECHART_LOAD_DATA.month+"月过户总量";
                }

                var datas=[ECHART_LOAD_DATA.mort_total,ECHART_LOAD_DATA.mort_loss,ECHART_LOAD_DATA.lossRate,ECHART_LOAD_DATA.oldLossRate];
                var type=["bar","bar","line","line"];
                var yAxis =[ {
                    type : 'value',//左边
                    name : '数量(单)',
                    axisLabel : {
                        formatter : '{value}'
                    }
                },{
                    type : 'value',//右边
                    name : '比率',
                    axisLabel : {
                        formatter : '{value}'
                    }
                }

                ];
                returnBar(ECHART_LOAD_DATA.xAxisData,yAxis,ECHART_LOAD_DATA.legend,datas,type,null,myChart,"各贵宾中心商贷比较");
            },
            buildPieChart : function(myChart){
                ECHART_LOAD_DATA.getPieDate();
                var color=["#BFD8FF","#ff9696"];
                var data = [ "收单", "流失" ];
                returnPie(data, ECHART_LOAD_DATA.pie_items, myChart, color,"商贷总金额");
            },
            /*获取当前年份数据*/
            getCurrentYear: function() {
                var date=new Date;
                var year=date.getFullYear();
                return year;
            },
            /*获取当前月数据*/
            getCurrentMonth: function() {
                var date=new Date;
                var month=date.getMonth()+1;
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
