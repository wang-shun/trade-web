/*根据日期查询统计 */
function queryConutCaseByDate(){
	var sUserId = $("#sUserId").val();
	var monthText = $(".irs-single").text();
	var month = "";
	var maxMonth = new Array("一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月");
	var minMonth = new Array("01","02","03","04","05","06","07","08","09","10","11","12");
	for(var m=0;m<maxMonth.length;m++){
		if(maxMonth[m]==monthText){
			month = minMonth[m];
			break;
		}
	}
	
	//生成起始时间和结束时间
	var d1 = new Date();
	var year = d1.getFullYear();
	var createTimeStart = year+"-"+month+"-"+"01";
	var d2 = new Date(year,month,0).getDate();
	var createTimeEnd = year+"-"+month+"-"+d2;
	
	 $.ajax({
			url  : ctx+'/workspace/workSpaceSta',
		    data : [{
				name : 'mo',
				value : month
			},{
				name : 'serachId',
				value : sUserId
			}],
			type: "post",
			dataType: "json",
			async : false,
			success: function(data) {
				$("#sp_loanAmount").text(data.loanAmount);
				$("#sp_signAmount").text(data.signAmount);
				$("#sp_convRate").text(data.convRate);
				$("#sp_actualAmount").text(data.actualAmount);
				$("#sp_evalFee").text(data.evalFee).attr({mo:month,serachId:sUserId});
				$("#sp_efConvRate").text(data.efConvRate);
				$("#ef_converRt").text(data.efConverRt);//评估费转化率

				$("#sp_receiveCount").html(
					"<a href='"+ctx+"/report/statis/caseDetail?createTimeStart="+createTimeStart+"&createTimeEnd="+createTimeEnd+"&status=received&arg="+sUserId+"' target='_blank'>" +
					data.receiveCount+"单" +
					"</a>"
				);
				$("#sp_signCount").html(
					"<a href='"+ctx+"/report/statis/historyTaskList?handleTimeStart="+createTimeStart+"&handleTimeEnd="+createTimeEnd+"&taskName=TransSign&arg="+sUserId+"' target='_blank'>" +
					data.signCount+"单" +
					"</a>"						
				);
				$("#sp_loanApplyCount").html(
					"<a href='"+ctx+"/report/statis/historyTaskList?handleTimeStart="+createTimeStart+"&handleTimeEnd="+createTimeEnd+"&taskName=ComLoanProcess&arg="+sUserId+"' target='_blank'>" +
					data.loanApplyCount+"单" +
					"</a>"						
				);
				$("#sp_closeCount").html(
					"<a href='"+ctx+"/report/statis/historyTaskList?handleTimeStart="+createTimeStart+"&handleTimeEnd="+createTimeEnd+"&taskName=CaseClose&arg="+sUserId+"' target='_blank'>" +
					data.closeCount+"单" +
					"</a>"						
				);
				
				var loanAmount = parseFloat(data.loanAmount.replace(/,/g,''));
				var signAmount = parseFloat(data.signAmount.replace(/,/g,''));
				var actualAmount = parseFloat(data.actualAmount.replace(/,/g,''));
				var max_bar1 = Math.max(loanAmount, signAmount, actualAmount);
				if(max_bar1){
					$('#sp_loanAmount_bar')[0].style.width = loanAmount/max_bar1*100+'%';
					$('#sp_signAmount_bar')[0].style.width = signAmount/max_bar1*100+'%';
					$('#sp_actualAmount_bar')[0].style.width = actualAmount/max_bar1*100+'%';
					$('#sp_evalFee_bar')[0].style.width=parseFloat(data.evalFee.replace(/,/g,''))*1000/loanAmount*100+'%';
				}else{
					$('#sp_loanAmount_bar')[0].style.width = '0%';
					$('#sp_signAmount_bar')[0].style.width = '0%';
					$('#sp_actualAmount_bar')[0].style.width = '0%';
					$('#sp_evalFee_bar')[0].style.width = '0%';
				}
				
				if(parseFloat(data.convRate)>100){
					$('#sp_convRate_bar')[0].style.width='100%';
				}else{
					$('#sp_convRate_bar')[0].style.width=data.convRate;
				}
				if(parseFloat(data.efConvRate)>100){
					$('#sp_efConvRate_bar')[0].style.width='100%';
				}else{
					$('#sp_efConvRate_bar')[0].style.width=data.efConvRate;
				}
				if(parseFloat(data.efConverRt)>100){
					$('#ef_converRt_bar')[0].style.width='100%';
				}else{
					$('#ef_converRt_bar')[0].style.width=data.efConverRt;
				}
				
				var max_bar2 = Math.max(data.receiveCount, data.signCount, data.loanApplyCount, data.closeCount);
				if(max_bar2){
					$('#sp_receiveCount_bar')[0].style.width=parseInt(data.receiveCount)/max_bar2*100+'%';
					$('#sp_signCount_bar')[0].style.width=parseInt(data.signCount)/max_bar2*100+'%';
					$('#sp_loanApplyCount_bar')[0].style.width=parseInt(data.loanApplyCount)/max_bar2*100+'%';
					$('#sp_closeCount_bar')[0].style.width=parseInt(data.closeCount)/max_bar2*100+'%';	
				}else{
					$('#sp_receiveCount_bar')[0].style.width = '0%';
					$('#sp_signCount_bar')[0].style.width='0%';
					$('#sp_loanApplyCount_bar')[0].style.width='0%';
					$('#sp_closeCount_bar')[0].style.width='0%';
				}
				
				setStaDetailDef();
				setStaVal($(data.staLoanApply),$(data.staLoanSign),$(data.staLoanRelease));
   	 			//d1 = toDonutData($(data.staLoanSign),'count');
   	 			//d2 = toDonutData($(data.staLoanSign),'amount');
   	 			//setDonut(d1,d2);
				var d1 = getData($(data.staLoanSign), 'count');
				var d2 = getData($(data.staLoanSign), 'amount');
   	 			if (!d1 || $.isEmptyObject(d1)) {
   	 				$("#bt_1").addClass("hide");
   	 			} else {
   	 				$("#bt_1").removeClass("hide");
   	 			}
   	 			if (!d2 || $.isEmptyObject(d2)) {
   	 				$("#bt_2").addClass("hide");
   	 			} else {
   	 				$("#bt_2").removeClass("hide");
   	 			}
   	 			ePlusLoanCount(document.getElementById('doughnutChart1'), d1, '{b}\n{c}');
   	 			ePlusLoanCount(document.getElementById('doughnutChart2'), d2, '{b}\n{c}万');
				addLinkHref(month,sUserId);
			}
	 });
}

function getData(d, it) {
	var data = [];
	$.each(d, function(i, item){
		if(typeof(this[it])=='string'){
			data.push({
				value : parseFloat(this[it].replace(/,/g,'')).toFixed(2),
				name : item.staItemStr
			});
		}else if(typeof(this[it])=='number'){
			data.push({
				value : parseFloat(this[it]).toFixed(2),
				name : item.staItemStr
			});			
		}
	});
	return data;
}

/*function getLegend(data) {
	var d = [];
	$.each(data,function(i,item) {
		d.push(item.name);
	});
	return d;	
}*/

function ePlusLoanCount(dom, data, formatter) {
	var myChart = echarts.init(dom);
	
	var option = {
		    tooltip: {
		        trigger: 'item',
		        formatter: formatter
		    },
		    /*legend: {
		        orient: 'vertical',
		        x: 'left',
		        data: getLegend(data)
		    },*/
		    color: [ '#87d6c6', '#54cdb4', '#1ab394'],
		    series: [
		        {
		            name:'E+贷款',
		            type:'pie',
		            radius: ['40%', '70%'],
		            avoidLabelOverlap: false,
		            
		            label: {
		                normal: {
		                    show: true,
		                    position: 'outside',
		                    textStyle: {
		                        fontSize: '13',
		                        fontWeight: 'bold',
		                       // color : 'black',
		                    }
		                    
		                },
		                emphasis: {
		                    show: true,
		                    
		                    //formatter : "{b}\n{c}万",
		                    /*formatter : formatter,
		                    textStyle: {
		                        fontSize: '25',
		                        fontWeight: 'bold',
		                        color : 'black',
		                    }*/
		                }
		            },
		            
		            labelLine: {
		                normal: {
		                    show: true
		                }
		            },
		            data:data
		        }
		    ]
		};
	myChart.setOption(option);
}


// 申请金额/面签金额/放款金额增加链接
function addLinkHref(month,sUserId) {
	var d = new Date();
	var year = d.getFullYear();
	// 获得最后一天
	var lastday = new Date(year,month,0).getDate();   
	
	var startTime = d.Format("yyyy-"+month+"-01");
    var endTime = d.Format("yyyy-"+month+"-"+lastday);

	var applyTimeLink = "window.open('"+ctx+"/loan/loanAgentList?isLoanAgentTimeType=1&startTime="+startTime+"&endTime="+endTime+"&sUserId="+sUserId+"')";
	var signTimeLink = "window.open('"+ctx+"/loan/loanAgentList?isLoanAgentTimeType=2&startTime="+startTime+"&endTime="+endTime+"&sUserId="+sUserId+"')";
	var releaseTimeLink = "window.open('"+ctx+"/loan/loanAgentList?isLoanAgentTimeType=3&startTime="+startTime+"&endTime="+endTime+"&sUserId="+sUserId+"')";
	$('#sp_loanAmount').attr("onclick",applyTimeLink);
	$('#sp_signAmount').attr("onclick",signTimeLink);
	$('#sp_actualAmount').attr("onclick",releaseTimeLink);
}

Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function evalFeeClick(){
	var mo=$(this).attr('mo');
	var serachId=$(this).attr('serachId');
	var purl="?source=dashboard";
	if(mo){
		purl=purl+'&mo='+mo;
	}
	if(serachId){
		purl=purl+'&serachId='+serachId;
	}
	window.open(ctx+'/eval/evalListStatistics'+purl);
}