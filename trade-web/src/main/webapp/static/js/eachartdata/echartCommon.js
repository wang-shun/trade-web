/**
 * @param xAxisData  横轴数据
 * @param yAxis      竖轴数据
 * @param legend     维度
 * @param datas      表中数据
 * @param type       数据显示类型(柱状、折线等)
 * @param color      维度对应颜色
 * @param myChart    图表div
 */
function returnBar(xAxisData,yAxis,legend,datas,type,color,myChart,title) {
		$(".echarsTable").html('');
            //横轴长度
	        var xAxisSize = xAxisData.length;
	  
			if(color==null){
				color=[
		               '#295aa5', '#f784a5', '#ffad6b', '#52bdbd','#0e73da','#ff9696','#58cfc2','#439cf0','#fc96d0','#ffd480','#84d3dc','#7aa6ea','#ffd2df','#ffdadb','#ade9e9'
		               ]
			}
			var option = {
		        title : {
		            text: title,
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
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : legend,
					x : 'center',
					y:'7%'
				},
				grid: {
		            x: '140',
		            y: '25%',
		            x2: '50',
		            y2: '15%',
	                width:'700'
		        },
				xAxis :  {
					type : 'category',
					data :xAxisData,
					axisLine:{
						lineStyle:{
					    color: '#000',
					    width: 1,
					    type: 'solid'
					}},  
					axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
			            show: false
			                },
			        axisTick:{
			        	show: false,
			            interval:0
			        },
			        splitLine:{
			            show:false
			             }
				},
				yAxis :yAxis
			};		
			option.yAxis[0].axisLine={
			 lineStyle:{
			    color: '#000',
			    width: 1,
			    type: 'solid'}
			};
			if(option.yAxis[1]!=undefined){
			option.yAxis[1].axisLine={
			 lineStyle:{
			    color: '#000',
			    width: 1,
			    type: 'solid'}
				};
			option.yAxis[1].splitNumber=5;
			}
			option.yAxis[0].splitNumber=5;
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
				if(type[j]=="line"){
					seriej.yAxisIndex=1;
				}
				option.series.push(seriej);
			})

			myChart.setOption(option);
			
			var html="<thead><td class='tabletitle'></td>";
            for(var k=0;k<xAxisSize;k++){
           	      html+="<td>"+xAxisData[k]+"</td>";
            }
		          html+="</thead>";
			for(var i=0;i<legend.length;i++){
				if(datas[i].length<=0){
				 return;
				 }
				html+="<tr><td class='tabletitle'>";
				 if(type[i]=="bar"){
				    html+= "<span class='colorBar' style='background-color:"+color[i]+"'></span>";
				    html+=legend[i]+"</td>"
				 }else if(type[i]=="line"){
					 html+= "<i class='iconfont al-iconbt ml5 ' style='color:"+color[i]+"'>&#xe687;</i>"; 
					 html+="<p class='al-text'>"+legend[i]+"</p></td>";
				 }
				 $.each(datas[i],function(j,item){
					 if(title == '11月派单、签约量统计' && type[i]=="line"){
						 html+="<td>"+item+" %</td>"; 
					 }else{
						 html+="<td>"+item+"</td>"; 
					 }
				 })
				 html+="</tr>"
		    }
				$(".echarsTable").append(html);
				var optionWidth=option.grid.width-2-(2*xAxisSize);
				 $(".tabletitle~td").width(optionWidth/(xAxisSize));
		}

/**
 * @param data       维度
 * @param items      表中数据
 * @param myChart1   图表div
 * @param color      维度对应颜色
 */
function returnPie(data, items, myChart1, color, title) {
	var unit = '';
	if(title=='商贷总金额'){
		unit = '万元'
	}else{
		unit = '个'
	}
	if(color==null){
		color=[
	            "#ade9e9","#ffdadb","#7aa6ea","#84d3dc","#ffd480","#fc96d0","#439cf0","#58cfc2","#ffac88","#ff9696","#0e73da","#52bdbd","#ffad6b","#f784a5","#295aa5" 
	            ]
	}
	var option = {
		 title : {
	            text: title,
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
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} \n({d}%)"
		},
        legend: {
            orient: 'horizontal',
            x : 'left',
            y: '14%',
			data : data
		},
		color : color,
		series : [ {
			name : '访问来源',
			type : 'pie',
			radius : '30%',
			center : [ '50%', '60%' ],
			animation : true,
			selectedMode : 'multiple',
			data : [],
			itemStyle : {
				normal : {
					label : {
						show : true,
						formatter : "{b}:\n{c}"+unit+" \n({d}%)"
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

/**********************************************JS浮点数精确运算*******************************************************************/
//加法函数，用来得到精确的加法结果
//说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
//调用：accAdd(arg1,arg2)
//返回值：arg1加上arg2的精确结果
function accAdd(arg1,arg2){
  var r1,r2,m;
  try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
  try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
  m=Math.pow(10,Math.max(r1,r2));
  return ((arg1*m+arg2*m)/m).toFixed(2);
}
//减法函数，用来得到精确的加法结果
//说明：javascript的减法结果会有误差，在两个浮点数相减的时候会比较明显。这个函数返回较为精确的减法结果。
//调用：accSub(arg1,arg2)
//返回值：arg1减上arg2的精确结果
function accSub(arg1,arg2){
     var r1,r2,m,n;
     try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
     try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
     m=Math.pow(10,Math.max(r1,r2));
     //last modify by deeka
     //动态控制精度长度
     n=(r1>=r2)?r1:r2;
     return ((arg2*m-arg1*m)/m).toFixed(2);
}
//乘法函数，用来得到精确的乘法结果
//说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
//调用：accMul(arg1,arg2)
//返回值：arg1乘以arg2的精确结果
function accMul(arg1,arg2)
{
  var m=0,s1=arg1.toString(),s2=arg2.toString();
  try{m+=s1.split(".")[1].length}catch(e){}
  try{m+=s2.split(".")[1].length}catch(e){}
  return (Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)).toFixed(2);
}
//除法函数，用来得到精确的除法结果
//说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
//调用：accDiv(arg1,arg2)
//返回值：arg1除以arg2的精确结果
function accDiv(arg1,arg2){
  var t1=0,t2=0,r1,r2;
  try{t1=arg1.toString().split(".")[1].length}catch(e){}
  try{t2=arg2.toString().split(".")[1].length}catch(e){}
  with(Math){
      r1=Number(arg1.toString().replace(".",""));
      r2=Number(arg2.toString().replace(".",""));
      return ((r1/r2)*pow(10,t2-t1)).toFixed(2);
  }
}
