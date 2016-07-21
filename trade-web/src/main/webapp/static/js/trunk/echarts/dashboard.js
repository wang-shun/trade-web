//获取当前年月
var mydate = new Date();
var year= mydate.getFullYear();
var month=mydate.getMonth()+1;

//返回背景颜色
function getColor(colors){
	 return{
			normal : {
				color : colors,

			}
		}
	
}

function reloadStatus() {
	var data={};
	 data.queryId="getStatus";
	 data.pagination = false;
	 data.startDate = $("#startDate").val();
	 data.sessionUserId  = $("#userId").val();
	 data.serviceDepId  = $("#serviceDepId").val();
	 data.serviceJobCode = $("#serviceJobCode").val();

	$.ajax({
			  async: false,
   	          url:ctx+ "/quickGrid/findPage" ,
   	          method: "post",
   	          dataType: "json",
   	          data: data,
        success: function(data){
        	var all=data.rows;
        	if(all.length<=1){
        		$("#mainwe").addClass("nullData");
        		return;
        	}
        	var jiedan=[];
        	var qianyue=[];
        	var guohu=[];
        	var time=[];
        	$.each(all, function(i,item){
    			time.push(item.mouth);
    			jiedan.push(item.jiedan);
    			qianyue.push(item.qianyue);
    			guohu.push(item.guohu);
        	});   
        	
        	StatusEchart(jiedan,qianyue,guohu,time);
        },
        error:function(){
        	$("#mainwe").addClass("nullData");
        }
  });
}


//柱状图
function StatusEchart(jiedan,qianyue,guohu,time){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('mainwe'));

	// 指定图表的配置项和数据
	
	option = {
/*		title : {
			x : 'center',
			text : year+'年上半年内状态数量',
			subtext : 'Rainbow bar example',
			link : 'http://echarts.baidu.com/doc/example.html'
		},*/
		tooltip : {
			trigger : 'axis',
			axisPointer : { // 坐标轴指示器，坐标轴触发有效
				type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
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
			data : [ '接单', '签约', '过户' ]

		},
		grid : {
			left : '1%',
			right : '10%',
			bottom : '1%',
			containLabel : true
		},
		xAxis : [ {
			type : 'category',
			data : time
		} ],
		yAxis : [ {
			type : 'value'
		} ],
		series : [ {
			name : '接单',
			type : 'bar',
			barWidth : 40,
			stack : '7月',
			itemStyle :getColor('#f784a5'),
			data : jiedan
		}, {
			name : '签约',
			type : 'bar',
			barWidth : 40,
			stack : '7月',
			itemStyle :getColor('#ffad6b'),
			data : qianyue
		}, {
			name : '过户',
			type : 'bar',
			barWidth : 40,
			stack : '7月',
			itemStyle :getColor('#52bdbd'),
			data : guohu
		}]
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	
}