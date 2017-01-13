/**
 * Created by caoyuan7 on 2017/1/13.
 */

(function() {
    window.ECHART_LOAD_DATA = window.ECHART_LOAD_DATA || {

             month:1,//用户选择的月份
             year:2017,//用户选择的月份

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
             pie_title:month+'月过户总单量',//饼图title
             bar_title:'',//柱状图title
             list_title:month+'月过户总量',//列表title
             url:'',//根路径
            /**
             * 初始化
             */
            init: function() {
                ECHART_LOAD_DATA.month=ECHART_LOAD_DATA.getCurrentMonth();
                ECHART_LOAD_DATA.year=ECHART_LOAD_DATA.getCurrentYear();
                ECHART_LOAD_DATA.url=$("#ctx").val();
            },
            /*报表一数据获得ajax*/
            getBarAjaxDate: function (dateMonth,dateFlag){
                var data = {
                    queryId: "queryHourseTransferCaseBaseInfoForDistrict",
                    choiceMonth : dateMonth,
                    pagination : false
                }
                $.ajax({
                    url : ECHART_LOAD_DATA.url+"/quickGrid/findPage",
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
                                ECHART_LOAD_DATA.noMortCount=noMortCount+item.NO_MORT_COUNT;//最新月份没有贷款的案件数
                                ECHART_LOAD_DATA.prfMortCount=prfMortCount+item.PRF_COUNT;//最新月份公积金贷款的案件数
                                ECHART_LOAD_DATA.comMortCount=comMortCount+item.MORTGAGET_TOTAL_COUNT;//最新月份商业贷款的案件数
                                ECHART_LOAD_DATA.totalNewDataCount=totalNewDataCount+item.HOURSE_TRANSFER_COUNT;//最新月份过户的案件数
                                for(var i=0;i<districtID.length;i++){
                                    if(item.DISTRICT_ID == ECHART_LOAD_DATA.districtID[i])
                                        ECHART_LOAD_DATA.newData[i]=item.HOURSE_TRANSFER_COUNT;
                                }
                            }
                            if(dateFlag=='old'){
                                ECHART_LOAD_DATA.totalOldDataCount=totalOldDataCount+item.HOURSE_TRANSFER_COUNT;//上月份过户的案件数
                                for(var i=0;i<districtID.length;i++){
                                    if(item.DISTRICT_ID == ECHART_LOAD_DATA.districtID[i])
                                        ECHART_LOAD_DATA.oldData[i] = item.HOURSE_TRANSFER_COUNT;
                                }
                            }
                        })
                        ECHART_LOAD_DATA.legend.push(month+"月总量");
                    },
                    error:function(){}
                });
                ECHART_LOAD_DATA.xAxisData = ECHART_LOAD_DATA.districtName;//初始化横轴数据
            },

            getPieDate : function (myChart2){
                ECHART_LOAD_DATA.pie_items.push(ECHART_LOAD_DATA.noMortCount);
                ECHART_LOAD_DATA.pie_items.push(ECHART_LOAD_DATA. ECHART_LOAD_DATA.prfMortCount);
                ECHART_LOAD_DATA.pie_items.push(ECHART_LOAD_DATA.comMortCount);
            },
            getDistrict: function (){
                var data = {
                    queryId: "queryDistrict",
                    pagination : false
                }
                $.ajax({
                    url :  ECHART_LOAD_DATA.url+"/quickGrid/findPage",
                    method : "GET",
                    data : data,
                    dataType : "json",
                    async:false,
                    success : function(data) {
                        $.each(data.rows,function(i,item){
                            ECHART_LOAD_DATA.districtID.push(item.DISTRICT_ID)
                            ECHART_LOAD_DATA.districtName.push(item.DISTRICT_NAME.substring(0,2));
                            ECHART_LOAD_DATA.newData.push(0);
                            ECHART_LOAD_DATA.oldData.push(0);
                        })
                    },
                    error:function(){}
                });
            },
            getBarChart : function(myChart1){
                if(month!=1){//如果不是1月则有上个月数据
                    ECHART_LOAD_DATA.getBarAjaxDate(year+'-'+month-1,'old');
                    ECHART_LOAD_DATA.getBarAjaxDate(year+'-'+month,'new');
                    ECHART_LOAD_DATA.bar_title= month+"月与"+(month-1)+"月过户总量比较";

                }else{
                    ECHART_LOAD_DATA.getBarAjaxDate(year+'-'+month,'new');
                    ECHART_LOAD_DATA.bar_title= month+"月过户总量";
                }
                //生成柱状图
                var datas=[ECHART_LOAD_DATA.newData, ECHART_LOAD_DATA.oldData];
                var type=["bar","bar"];
                var bar_color=["#BFD8FF","#ff9696"];
                var yAxis =[ {
                    type : 'value',//左边
                    name : '数量(单)',
                    min : 0,
                    interval : 50,
                    axisLabel : {
                        formatter : '{value}'
                    }
                }];
                returnBar(ECHART_LOAD_DATA.xAxisData,yAxis,ECHART_LOAD_DATA.legend,datas,type,bar_color,myChart1,ECHART_LOAD_DATA.bar_title);
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
            }


        };
})();
