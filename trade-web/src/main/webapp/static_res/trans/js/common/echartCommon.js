/**
 * 
 * @param xAxisData  横轴数据
 * @param yAxis      竖轴数据
 * @param legend     维度
 * @param datas      表中数据
 * @param type       数据显示类型(柱状、折线等)
 * @param color      维度对应颜色
 * @param myChart    图表div
 */
function returnBar(xAxisData,yAxis,legend,datas,type,color,myChart) {

			if(color==null){
				color=[
		               '#295aa5', '#f784a5', '#ffad6b', '#52bdbd','#0e73da','#ff9696','#ffac88','#58cfc2','#439cf0','#fc96d0','#ffd480','#84d3dc','#7aa6ea','#ffd2df','#ffdadb','#ade9e9'
		               ]
			}
			var option = {
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : legend,
					x : 'center'
				},
				xAxis : [ {
					type : 'category',
					borderColoe : '#333',
					data : xAxisData
				} ],
				yAxis :yAxis,
				series : [ {
					name : '10月总量',
					type : 'bar',
					itemStyle : {//顔色
						normal : {
							color : '#52bdbd'
						}
					},
					data : [ 2.0, 4.9, 7.0, 23.2, 25.6 ]
				//数据
				}, {
					name : '11月总量',
					type : 'bar',
					itemStyle : {//顔色
						normal : {
							color : '#ff9696'
						}
					},
					data : [ 2.6, 5.9, 9.0, 26.4, 28.7 ]
				}]

			};			
			if(datas!=[]||datas!=undefined){
				option.series=[];			
			}
			$.each(datas,function(j,item){
				var seriej={
						name :legend[j] ,
						type : type[j],
						itemStyle : {//顔色
							normal : {
								color : color[j]
							}
						},
						data : item	
				}
				option.series.push(seriej);
			})

			myChart.setOption(option);
			var html="<thead><td class='tabletitle'></td><td></td><td></td><td></td><td></td><td></td>";
			    html+="<td></td><td></td><td></td></thead>";
				for(var i=0;i<legend.length;i++){
					
					 html+="<tr><td class='tabletitle'>";
					 if(type[i]=="bar"){
					    html+= "<span class='colorBar' style='background-color:"+color[i]+"'></span>";
					 }else{
						 html+= "<span class='colorBar' style='background-color:"+color[i]+"'></span>"; 
					 }
					 html+=legend[i]+"</td>";
					 $.each(datas[i],function(j,item){
						 html+="<td>"+item+"</td>";
					 })
					 html+="</tr>"
			}
				$(".echarsTable").append(html);
		}

/**
 * 
 * @param data       维度
 * @param items      表中数据
 * @param myChart1   图表div
 * @param color      维度对应颜色
 */
function returnPie(data, items, myChart1, color) {
	if(color==null){
		color=[
               '#295aa5', '#f784a5', '#ffad6b', '#52bdbd','#0e73da','#ff9696','#ffac88','#58cfc2','#439cf0','#fc96d0','#ffd480','#84d3dc','#7aa6ea','#ffd2df','#ffdadb','#ade9e9'
               ]
	}
	var option = {
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		legend : {
			x : 'center',
			data : data
		},
		color : color,
		series : [ {
			name : '访问来源',
			type : 'pie',
			radius : '55%',
			center : [ '50%', '60%' ],
			animation : true,
			selectedMode : 'multiple',
			data : [],
			itemStyle : {
				normal : {
					label : {
						show : true,
						formatter : "{b}:\n{c}个 ({d}%)"
					}
				}
			}
		} ]
	};
	if(items==undefined ||items==null){return; };
	for (var i = 0; i < data.length; i++) {
		var datai = {
			value : items[i],
			name : data[i]
		}
		option.series[0].data.push(datai);
	}
	myChart1.setOption(option);
}