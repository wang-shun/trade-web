
 
// 返回背景颜色
function getColor(colors) {
	return {
		normal : {
			color : colors,

		}
	}

}

function  StatusEchart2(finOrgNames,number,amount){
	// 基于准备好的dom，初始化echarts实例
    var myChart1 = echarts.init(document.getElementById('TypeOne'));
    var myChart2 = echarts.init(document.getElementById('TypeTwo'));
	// 指定图表的配置项和数据
    var option1 = {
        title: {
            text: '贷款类型分析（金额）',
            subtext:'',
            padding: [25, 10],
            x:'center',
        },
        tooltip: {
            trigger: 'item',
            triggerOn: 'mousemove',
            /*alwaysShowContent:true,*/
            hideDelay: 1500,
            enterable: true,
            formatter: "{b}: {c}万 ({d}%)"
        },
        legend: {
            x : 'center',
            y : 'bottom',
            data:finOrgNames,
            size:'9'
        },
        color:[
               '#295aa5', '#f784a5', '#ffad6b', '#52bdbd','#0e73da','#ff9696','#ffac88','#58cfc2','#439cf0','#fc96d0','#ffd480','#84d3dc','#7aa6ea','#ffd2df','#ffdadb','#ade9e9'        ],
        series: [

            {
                name:'总金额: ' + 1 + ' 万',
                type:'pie',
                radius: ['25%', '45%'],
                animation: true,
                selectedMode: 'multiple',
                data:amount
            }
        ]
};
 // 指定图表的配置项和数据
    var option2 = {
        title: {
            text: '贷款类型分析（单数）',
            subtext:'',
            padding: [25, 10],
            x:'center',
        },
        tooltip: {
            trigger: 'item',
            triggerOn: 'mousemove',
            /*alwaysShowContent:true,*/
            hideDelay: 1500,
            formatter: "{b}: {c}个 ({d}%)"
        },

        legend: {
            x : 'center',
            y : 'bottom',
            data:finOrgNames
        },
        color:[
               '#295aa5', '#f784a5', '#ffad6b', '#52bdbd','#0e73da','#ff9696','#ffac88','#58cfc2','#439cf0','#fc96d0','#ffd480','#84d3dc','#7aa6ea','#ffd2df','#ffdadb','#ade9e9'
           ],
        series: [

            {
                name:'贷款类型分析（金额）',
                type:'pie',
                radius: ['25%', '45%'],
                animation: true,
                selectedMode: 'multiple',
                data:number
            }
        ]
};
	
	// 使用刚指定的配置项和数据显示图表。
    myChart1.setOption(option1);
    myChart2.setOption(option2);
   }

function StatusEchart1(kas,dais,xAxis) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('Cont'));

	// 指定图表的配置项和数据

	var option = {
		tooltip : {
			trigger : 'axis',
			axisPointer : { // 坐标轴指示器，坐标轴触发有效
				type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			},
	        formatter: function(params) {  
	              var res = params[0].name +'<br/>'+params[0].seriesName+'：'+params[0].value+'万/'+params[0].data.num+'个</br>';  
	                  res+=params[1].seriesName+'：'+params[1].value+'万/'+params[1].data.num+'个';  
	              return res;  
				 },
		},
		toolbox : {
			show : true,
			feature : {
				dataView : {
					show : true,
					readOnly : false
				},
				restore : {
					show : true
				},
				saveAsImage : {
					show : true
				}
			}
		},
		legend : {
			orient : 'vertical',
			x : 'right',
			y : 'center',
			data : [ '卡类', '贷款类' ],

		},
		grid : {
			left : '1%',
			right : '10%',
			bottom : '1%',
			containLabel : true
		},
		xAxis : [ {
			type : 'category',

			data :xAxis
		} ],
		yAxis : [ {
			type : 'value'
		} ],
		series : [ {
			name : '卡类',
			type : 'bar',
			barWidth : 30,
			stack : 'mm',
			itemStyle : getColor('#52bdbd'),
			dataType:'json',
			data : kas
		}, {
			name : '贷款类',
			type : 'bar',
			barWidth : 30,
			dataType:'json',
			stack : '7月',
			itemStyle : getColor('#295aa5'),
			data :dais
		} ]
	};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);

}