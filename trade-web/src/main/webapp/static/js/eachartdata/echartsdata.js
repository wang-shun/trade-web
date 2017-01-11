    // 柱状图表，初始化echarts实例
function echartData (id) {
    var myChart = echarts.init(document.getElementById(id));
    // 指定图表的配置项和数据
    var option = {
        title : {
            text: '11月与10月过户量比较',
            textStyle: {
                color: '#555',
                fontSytle: 'normal',
                fontWeight: 'normal',
                fontSize: '16'
            },
            x:'left',
            y: 'top',
            padding: [
                0,  // 上
                10, // 右
                10,  // 下
                0, // 左
            ],
        },
        legend: {
            y: '10%',
            data:['11月总单量','10月总单量'],
            x:'center'
        },
        tooltip: {
            trigger: 'axis'
        },
        grid: {
            left: '48',
            top: '30%',
            right: '84',
            bottom: '15%'
        },
        xAxis: [
            {
                type: 'category',
                borderColoe:'#333',
                data: ['宝山','嘉定','普陀','市区','松江','闵行','虹杨','浦东']
            }
        ],
        yAxis: [
            {
                type: 'value',//左边
                name: '单量',
                min: 0,
                //max: 600,
                interval: 50,
                axisLabel: {
                    formatter: '{value} 单'
                }
            }
        ],
        series: [
            {
                name:'11月总单量',
                type:'bar',
                itemStyle:{//顔色
                  normal : {
                  color : '#52bdbd'
                      }
                },
                data:[206, 135, 136, 162, 130, 143, 208, 544]//数据
            },
            {
                name:'10月总单量',
                type:'bar',
                itemStyle:{//顔色
                  normal : {
                  color : '#ff9696'
                      }
                },
                data:[260, 180, 198, 137, 99, 193, 207, 559]
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

// 饼图，初始化echarts实例
function echartSet (id) {
    var myChart = echarts.init(document.getElementById(id));
    // 指定图表的配置项和数据
    var option = {
            title : {
                text: '11月过户总量',
                x:'left',
                y: 'top',
                padding: [
                    0,  // 上
                    10, // 右
                    10,  // 下
                    0, // 左
                ],
                textStyle: {
                    color: '#555',
                    fontSytle: 'normal',
                    fontWeight: 'normal',
                    fontSize: '16'
                },
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            toolbox: {//工具栏
                x:'right',
                feature: {
                    dataView: {show: true, readOnly: false},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            legend: {
                orient: 'horizontal',
                x : 'left',
                y: '14%',
                data: ['无贷款','纯公积金','商业贷款']
            },
            color:['#52bdbd','#ffd480','#bfd8ff'],
            series : [
                {
                    name: '访问来源',
                    type: 'pie',
                    radius : '30%',
                    center: ['50%', '60%'],
                    animation: true,
                    selectedMode: 'multiple',
                    data:[
                        {value:324, name:'无贷款'},
                        {value:155, name:'纯公积金'},
                        {value:1185, name:'商业贷款',selected:true}
                    ],
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                formatter: "{b}:\n{c}个 ({d}%)"
                             }
                          }
                    }
                }
            ]
        };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

}