// 柱状图表，初始化echarts实例

function echartData (id,data) {
    var myChart = echarts.init(document.getElementById(id));
    var legend=['一次性付款','纯公积金','公司办理','客户自办','贷款待办'];
    var option1 = {
    /*    title : {
            text: '签约、贷款数据',
            x:'left',
        },*/
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'horizontal',
            top:'200',
            left: 'center',
            data: legend
        },
        series : [
            {
                name: '签约、贷款数据',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    },
                    normal: {
                        color: function(params) {
                            var colorList = [
                              '#52bdbd','#f784a5','#fead6b','#52cdec','#428fcc'
                            ];
                            return colorList[params.dataIndex]
                        }
                    }
                }
            }
        ]
    };
	if(data==undefined ||data==null){return; };
	var items=data.rows;
	for (var i = 0; i < items.length; i++) {
		if(i>4){break;};
		var datai = {
			value : items[i].caseCount,
			name : legend[i]
		}
		option1.series[0].data.push(datai);
	}
    myChart.setOption(option1);
}
