
// 返回背景颜色
function getColor(colors) {
	return {
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
	 data.serviceDepHierarchy = $("#serviceDepHierarchy").val();
	 var startMonth=new Date($("#startDate").val()).getMonth()+1;
	$.ajax({
			  async: true,//异步请求
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
        	var jiean=[];
        	var xAxis = [];
        	$.each(all, function(i,item){
        		if(item.mm==0){
        			xAxis.push(startMonth+"月以前");
        		}else{
        			xAxis.push(item.mm+"月");
        		}
    			jiedan.push(item.jiedan);
    			qianyue.push(item.qianyue);
    			guohu.push(item.guohu);
    			jiean.push(item.jiean);
        	});   
        	StatusEchart(jiedan,qianyue,guohu,jiean,xAxis);
        },
        error:function(){
        	$("#mainwe").addClass("nullData");
        }
  });
}

function StatusEchart(jiedan, qianyue, guohu, jiean,xAxis) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('mainwe'));

	// 指定图表的配置项和数据

	var option = {
/*		title : {
			x : 'center',
			text : '半年案件分布统计',
			subtext : year + '年',
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
			data : [ '接单', '签约', '过户', '结案' ],
			selected : {
				'结案' : false
			}

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
			name : '接单',
			type : 'bar',
			barWidth : 30,
			stack : '7月',
			itemStyle : getColor('#f784a5'),
			data : jiedan
		}, {
			name : '签约',
			type : 'bar',
			barWidth : 30,
			stack : '7月',
			itemStyle : getColor('#ffad6b'),
			data : qianyue
		}, {
			name : '过户',
			type : 'bar',
			barWidth : 30,
			stack : '7月',
			itemStyle : getColor('#52bdbd'),
			data : guohu
		}, {
			name : '结案',
			type : 'bar',
			barWidth : 30,
			stack : '7月',
			itemStyle : getColor('#295aa5'),
			data : jiean
		} ]
	};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);

}