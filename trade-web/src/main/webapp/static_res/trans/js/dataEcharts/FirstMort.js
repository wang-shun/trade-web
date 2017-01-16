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
            total:[],//商业贷款总数
            loss:[],//流失单数
            lossRate:[],//流失率
            oldLossRate:[],//上月流失率
            legend:'',//纬度
            totalLossCount:0,//本月总流失单数
            totalMortCount:0,//本月总贷款单数
            pie_items : [],//饼图的数据
            url:'',//根路径
            /**
             * 初始化
             */
            init: function(year,month) {
                ECHART_LOAD_DATA.districtID.splice(0,ECHART_LOAD_DATA.districtID.length);//清空数组
                ECHART_LOAD_DATA.districtName.splice(0,ECHART_LOAD_DATA.districtName.length);
                ECHART_LOAD_DATA.xAxisData.splice(0,ECHART_LOAD_DATA.xAxisData.length);
                ECHART_LOAD_DATA.total.splice(0,ECHART_LOAD_DATA.total.length);
                ECHART_LOAD_DATA.loss.splice(0,ECHART_LOAD_DATA.loss.length);
                ECHART_LOAD_DATA.lossRate.splice(0,ECHART_LOAD_DATA.lossRate.length);
                ECHART_LOAD_DATA.oldLossRate.splice(0,ECHART_LOAD_DATA.oldLossRate.length);
                ECHART_LOAD_DATA.pie_items.splice(0,ECHART_LOAD_DATA.pie_items.length);
                ECHART_LOAD_DATA.totalLossCount=0,//本月总流失单数
                ECHART_LOAD_DATA.totalMortCount=0,//本月总贷款单数

                ECHART_LOAD_DATA.month=month;
                ECHART_LOAD_DATA.year=year;
                ECHART_LOAD_DATA.url=$("#ctx").val();
                ECHART_LOAD_DATA.legend =["商贷总单数","流失单数","流失率","上月流失率"];
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
                            ECHART_LOAD_DATA.totalLossCount= ECHART_LOAD_DATA.totalLossCount+item.LOST_COUNT;
                            ECHART_LOAD_DATA.totalMortCount= ECHART_LOAD_DATA.totalMortCount+item.MORTGAGET_TOTAL_COUNT;
                            if(dateFlag=='new'){
                                for(var i=0;i<ECHART_LOAD_DATA.districtID.length;i++){
                                    if(item.DISTRICT_ID == ECHART_LOAD_DATA.districtID[i]){
                                        ECHART_LOAD_DATA.total[i]=item.MORTGAGET_TOTAL_COUNT;
                                        ECHART_LOAD_DATA.loss[i]=item.LOST_COUNT;
                                        if(accAdd(item.MORTGAGET_TOTAL_COUNT,item.LOST_COUNT)!=0){
                                            ECHART_LOAD_DATA.lossRate[i]=accDiv(item.LOST_COUNT,accAdd(item.MORTGAGET_TOTAL_COUNT,item.LOST_COUNT));
                                        }else{
                                            ECHART_LOAD_DATA.lossRate[i]=0;
                                        }

                                    }
                                }
                            }
                            if(dateFlag=='old'){
                                for(var i=0;i<ECHART_LOAD_DATA.districtID.length;i++){
                                    if(item.DISTRICT_ID == ECHART_LOAD_DATA.districtID[i]){
                                        if(accAdd(item.MORTGAGET_TOTAL_COUNT,item.LOST_COUNT)!=0){
                                            ECHART_LOAD_DATA.oldLossRate[i]=accDiv(item.LOST_COUNT,accAdd(item.MORTGAGET_TOTAL_COUNT,item.LOST_COUNT));
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
                ECHART_LOAD_DATA.pie_items.push(ECHART_LOAD_DATA.totalMortCount);
                ECHART_LOAD_DATA.pie_items.push(ECHART_LOAD_DATA.totalLossCount);

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
                            ECHART_LOAD_DATA.total.push(0);
                            ECHART_LOAD_DATA.loss.push(0);
                            ECHART_LOAD_DATA.lossRate.push(0);
                            ECHART_LOAD_DATA.oldLossRate.push(0);
                        })
                    },
                    error:function(){}
                });
            },
            buildBarChart : function(myChart){
                if(ECHART_LOAD_DATA.month!=1){//如果不是1月则有上个月数据
                    ECHART_LOAD_DATA.getBarAjaxDate(ECHART_LOAD_DATA.year+'-'+ECHART_LOAD_DATA.turnNumber((Number(ECHART_LOAD_DATA.month)-1)),'old');
                    ECHART_LOAD_DATA.getBarAjaxDate(ECHART_LOAD_DATA.year+'-'+ECHART_LOAD_DATA.turnNumber(Number(ECHART_LOAD_DATA.month)),'new');
                }else{

                    ECHART_LOAD_DATA.getBarAjaxDate(ECHART_LOAD_DATA.year+'-'+ECHART_LOAD_DATA.turnNumber(Number(ECHART_LOAD_DATA.month)),'new');
                    ECHART_LOAD_DATA.bar_title= ECHART_LOAD_DATA.month+"月过户总量";
                }

                var datas=[ECHART_LOAD_DATA.total,ECHART_LOAD_DATA.loss,ECHART_LOAD_DATA.lossRate,ECHART_LOAD_DATA.oldLossRate];
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
                returnPie(data, ECHART_LOAD_DATA.pie_items, myChart, color,"商贷总单数");
            },
            buildListChart : function(list_chart){
                $("#"+list_chart).html('');
                var html='<li><i class="iconfont mr5 al-yellow al-icon-22">&#xe643;</i>'+ECHART_LOAD_DATA.month+'月总量<span>'+ECHART_LOAD_DATA.totalNewDataCount+'</span>单</li>';
                if(ECHART_LOAD_DATA.month!=1){
                    html=html +'<li><i class="iconfont mr5 al-grey al-icon-22">&#xe643;</i>'+(ECHART_LOAD_DATA.month-1)+'月总量<span>'+ECHART_LOAD_DATA.totalOldDataCount+'</span>单</li>';
                    var subtraction=ECHART_LOAD_DATA.totalNewDataCount-ECHART_LOAD_DATA.totalOldDataCount;
                    if(subtraction<0){
                        var percent=accDiv(Math.abs(subtraction),ECHART_LOAD_DATA.totalNewDataCount)*100+"%";
                        html=html+'<li><i class="iconfont mr5 al-maize  al-icon-22">&#xe651;</i>环比下降<span>'+percent+'</span></li>';
                    }else{
                        var percent=accDiv(Math.abs(subtraction),ECHART_LOAD_DATA.totalNewDataCount)*100+"%";
                        html=html+'<li><i class="iconfont mr5 al-maize  al-icon-22">&#xe651;</i>环比上升<span>'+percent+'</span></li>';
                    }
                }

                $("#"+list_chart).html(html);
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
