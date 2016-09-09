//预警灯提示
function queryBizwarnCaseCount(){
	 $.ajax({
			//url  : ctx+'/workspace/workSpaceSta',
		 	url  : ctx+'/workspace/trafficLightTips',
		    data : "",
			type: "post",
			dataType: "json",
			async : true,
			success: function(data) {				
				
				//待分配的案件
				if(data.caseDistributeCount!=undefined && data.caseDistributeCount>=0){
					//$('.light_info').prepend($("#caseDistributeCount").parents("li"));
					$("#caseDistributeCount").html(data.caseDistributeCount).parent('p').siblings("i").addClass('martop20');
					$("#caseDistributeCount").parent('p').addClass('line').parents("li").addClass('light active').hover(function() {
				        $(this).css("background","#52cdec");
				        $(this).find(".icon,.line,span").addClass('white');
				    },function() {
				        $(this).css("background","#edfcfd");
				        $(this).find(".icon,.line,span").removeClass('white');
				    });
					$("#caseDistributeCount").parents("li").children().wrapAll(function() {
						  return '<a href="'+ctx+'/case/caseDistribute" target="_blank"></a>';
					});
				}
				
				//无主任务
				if(data.unLocatedTaskCount!=undefined && data.unLocatedTaskCount>=0){
					//$('.light_info').prepend($("#unLocatedTaskCount").parents("li"));
					$("#unLocatedTaskCount").html(data.unLocatedTaskCount).parent('p').siblings("i").addClass('martop20');
					$("#unLocatedTaskCount").parent('p').addClass('line').parents("li").addClass('light active').hover(function() {
				        $(this).css("background","#52cdec");
				        $(this).find(".icon,.line,span").addClass('white');
				    },function() {
				        $(this).css("background","#edfcfd");
				        $(this).find(".icon,.line,span").removeClass('white');
				    });
					$("#unLocatedTaskCount").parents("li").children().wrapAll(function() {
						  return '<a href="'+ctx+'/unlocatedTasks" target="_blank"></a>';
					});
				}
				
				//无主案件
				if(data.unLocatedCaseCount!=undefined && data.unLocatedCaseCount>=0){
					//$('.light_info').prepend($("#unLocatedCaseCount").parents("li"));
					$("#unLocatedCaseCount").html(data.unLocatedCaseCount).parent('p').siblings("i").addClass('martop20');
					$("#unLocatedCaseCount").parent('p').addClass('line').parents("li").addClass('light active').hover(function() {
				        $(this).css("background","#52cdec");
				        $(this).find(".icon,.line,span").addClass('white');
				    },function() {
				        $(this).css("background","#edfcfd");
				        $(this).find(".icon,.line,span").removeClass('white');
				    });
					$("#unLocatedCaseCount").parents("li").children().wrapAll(function() {
						  return '<a href="'+ctx+'/case/unlocatedCase" target="_blank"></a>';
					});
				}
				
				//贷款流失预警
				if(data.bizwarnCaseCount!=undefined && data.bizwarnCaseCount>=0){
					//$('.light_info').prepend($("#bizwarnCaseCount").parents("li"));
					$("#bizwarnCaseCount").html(data.bizwarnCaseCount).parent('p').siblings("i").addClass('martop20');
					$("#bizwarnCaseCount").parent('p').addClass('line').parents("li").addClass('light active').hover(function() {
				        $(this).css("background","#52cdec");
				        $(this).find(".icon,.line,span").addClass('white');
				    },function() {
				        $(this).css("background","#edfcfd");
				        $(this).find(".icon,.line,span").removeClass('white');
				    });
					$("#bizwarnCaseCount").parents("li").children().wrapAll(function() {
						  return '<a href="'+ctx+'/bizwarn/list?status=0" target="_blank"></a>';
					});
				}
				
				//黄灯任务
				if(data.yeLight!=undefined && data.yeLight>=0){
					//$('.light_info').prepend($("#yeLightCount").parents("li"));
					$("#yeLightCount").html(data.yeLight).parent('p').siblings("i").addClass('martop20');
					$("#yeLightCount").parent('p').addClass('line').parents("li").addClass('light active').hover(function() {
				        $(this).css("background","#52cdec");
				        $(this).find(".icon,.line,span").addClass('white');
				    },function() {
				        $(this).css("background","#edfcfd");
				        $(this).find(".icon,.line,span").removeClass('white');
				    });
					$("#yeLightCount").parents("li").children().wrapAll(function() {
						  return '<a href="'+ctx+'/workspace/ryLightList?color=1" target="_blank"></a>';
					});
				}
				
				//红灯任务
				if(data.redLight!=undefined && data.redLight>=0){
					//$('.light_info').prepend($("#redLightCount").parents("li"));
					$("#redLightCount").html(data.redLight).parent('p').siblings("i").addClass('martop20');
					$("#redLightCount").parent('p').addClass('line').parents("li").addClass('light active').hover(function() {
				        $(this).css("background","#52cdec");
				        $(this).find(".icon,.line,span").addClass('white');
				    },function() {
				        $(this).css("background","#edfcfd");
				        $(this).find(".icon,.line,span").removeClass('white');
				    });
					$("#redLightCount").parents("li").children().wrapAll(function() {
						  return '<a href="'+ctx+'/workspace/ryLightList?color=0" target="_blank"></a>';
					});
				}
				

			}
	 });
}
queryBizwarnCaseCount();
//龙虎榜
function queryGetRankBank(){
	 $.ajax({		
		 	url  : ctx+'/workspace/qqGetRank',
		    data : "",
			type: "post",
			dataType: "json",
			async : true,
			success: function(data) {
				$("#loanAmountRank").text(data.loanAmountRank==null?'':"你的排名："+ data.loanAmountRank); //你的排名：${rank.loanAmountRank} 
				$("#signAmountRank").text(data.signAmountRank==null?'':"你的排名："+ data.signAmountRank);//你的排名：${rank.signAmountRank} 
				$("#actualAmountRank").text(data.actualAmountRank==null?'':"你的排名："+ data.actualAmountRank);//你的排名：${rank.actualAmountRank}
				//E+金融申请榜
				var loanAmountRankList1=data.loanAmountRankList[0];			
				if(loanAmountRankList1 != null && loanAmountRankList1 != ''){					
					var  loanAmountRankListHtml='';
					var  loanAmountRankListHtmlForShow='';	
					
					for( var i=0;i<loanAmountRankList1.length;i++){							
						var  colorClass= loanAmountRankList1[i].RANK_NO==1 ?"badge-danger":loanAmountRankList1[i].RANK_NO==2 ? "badge-orange": loanAmountRankList1[i].RANK_NO==3 ? "badge-warning" : "text-white";
						var  picture1="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/"+loanAmountRankList1[i].EMP_CODE+".jpg";							
						loanAmountRankListHtml = 
							"<div class='feed-element'>"+"" +
							"<a href='#' class='pull-left'> "+
							"<span class='shead'>"+
							"<img class='himg' style='height: 38px; width: 38px;'  src='"+ picture1 + "'"+ 
							"onload='javascript:imgLoad(this);'>"+
							"</span>"+
							"<span class='badge  "+colorClass+"'>" 
							+ loanAmountRankList1[i].RANK_NO+							
							"</span>"+
							"</a>"+
							"<div class='media-body'>"+
							"<span class='pull-right'>"+
							"<strong class='fa-2x text-danger'>"+
							//"<fmt:formatNumber	value='"+ loanAmountRankList1[i].RANK_VALUE/10000 +"'"+
							//"pattern='###,##0.00' />万"+
							+toDecimal2((loanAmountRankList1[i].RANK_VALUE/10000))+"万"+							
							"</strong>"+
							"</span> <strong>"+loanAmountRankList1[i].REAL_NAME+
							"</strong><br>"+
							"<small	class='ext-muted'>"+ loanAmountRankList1[i].BELONG_ORG_NAME + 
							"</small>" +
							"</div>"+
							"</div>";
							loanAmountRankListHtmlForShow+=loanAmountRankListHtml;							
					}
					$("#loanAmountRankList").html(loanAmountRankListHtmlForShow);
				}
					//E+金融签约榜
					var signAmountRankList1=data.signAmountRankList[0];			
					if(signAmountRankList1 != null && signAmountRankList1 != ''){					
						var  signAmountRankListHtml='';
						var  signAmountRankListHtmlForShow='';							
						for( var i=0;i<signAmountRankList1.length;i++){							
							var  colorClass= signAmountRankList1[i].RANK_NO==1 ?"badge-danger":signAmountRankList1[i].RANK_NO==2 ? "badge-orange": signAmountRankList1[i].RANK_NO==3 ? "badge-warning" : "text-white";
							var  picture2="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/"+signAmountRankList1[i].EMP_CODE+".jpg";	
							signAmountRankListHtml = 
								"<div class='feed-element'>"+"" +
								"<a href='#' class='pull-left'> "+
								"<span class='shead img-circle'>"+
								"<img class='himg' style='height: 38px; width: 38px;' src=' "+ picture2 +"'"+
								"onload='javascript:imgLoad(this);'>"+
								"</span>"+
								"<span class='badge  "+colorClass+"'>" 
								+ signAmountRankList1[i].RANK_NO+							
								"</span>"+
								"</a>"+
								"<div class='media-body'>"+
								"<span class='pull-right'>"+
								"<strong class='fa-2x text-danger'>"+
								 +toDecimal2((signAmountRankList1[i].RANK_VALUE/10000))+"万"+							
								"</strong>"+
								"</span> <strong>"+signAmountRankList1[i].REAL_NAME+
								"</strong><br>"+
								"<small	class='ext-muted'>"+ signAmountRankList1[i].BELONG_ORG_NAME + 
								"</small>" +
								"</div>"+
								"</div>";
							signAmountRankListHtmlForShow+=signAmountRankListHtml;							
						}
						$("#signAmountRankList").html(signAmountRankListHtmlForShow);
					}	
						//E+金融放款榜
						var actualAmountRankList1=data.actualAmountRankList[0];			
						if(actualAmountRankList1 != null && actualAmountRankList1 != ''){					
							var  actualAmountRankListHtml='';
							var  actualAmountRankListHtmlForShow='';							
							for( var i=0;i<actualAmountRankList1.length;i++){							
								var  colorClass= actualAmountRankList1[i].RANK_NO==1 ?"badge-danger":actualAmountRankList1[i].RANK_NO==2 ? "badge-orange": actualAmountRankList1[i].RANK_NO==3 ? "badge-warning" : "text-white";
								var  picture3="http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/"+actualAmountRankList1[i].EMP_CODE+".jpg";
								actualAmountRankListHtml = 
									"<div class='feed-element'>"+"" +
									"<a href='#' class='pull-left'> "+
									"<span class='shead img-circle'>"+
									"<img class='himg' style='height: 38px; width: 38px;' src=' "+ picture3 +"'"+
									"onload='javascript:imgLoad(this);'>"+
									"</span>"+
									"<span class='badge  "+colorClass+"'>" 
									+ actualAmountRankList1[i].RANK_NO+							
									"</span>"+
									"</a>"+
									"<div class='media-body'>"+
									"<span class='pull-right'>"+
									"<strong class='fa-2x text-danger'>"+
									 +toDecimal2((actualAmountRankList1[i].RANK_VALUE/10000))+"万"+			//.toFixed(2)				
									"</strong>"+
									"</span> <strong>"+actualAmountRankList1[i].REAL_NAME+
									"</strong><br>"+
									"<small	class='ext-muted'>"+ actualAmountRankList1[i].BELONG_ORG_NAME + 
									"</small>" +
									"</div>"+
									"</div>";
								actualAmountRankListHtmlForShow+=actualAmountRankListHtml;							
							}
							$("#actualAmountRankList").html(actualAmountRankListHtmlForShow);				

				}	
			}
	 });

}


//金额之后 添加.00
function toDecimal2(x) {
	 var f = parseFloat(x);    
	 if (isNaN(f)) { 
	       return false;    
	  }    
	   
	 var f = Math.round(x*100)/100;    
	 var s = f.toString();    
     var rs = s.indexOf('.');    
	 if (rs < 0) {    
	       rs = s.length;    
	       s += '.';    
	 }    
	 while (s.length <= rs + 2) {    
	       s += '0';    
	 }
	 return s;    
}  

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
			//url  : ctx+'/workspace/workSpaceSta',
		 	url  : ctx+'/workspace/newWorkSpaceSta',
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
				console.log("===Result==="+JSON.stringify(data));
				$("#sp_loanAmount").text(data.loanAmount);
				$("#sp_signAmount").text(data.signAmount);
				$("#sp_convRate").text(data.convRate);
				$("#sp_actualAmount").text(data.actualAmount);
				$("#sp_evalFee").text(data.evalFee).attr({mo:month,serachId:sUserId});
				$("#sp_efConvRate").text(data.efConvRate);
				$("#ef_converRt").text(data.efConverRt);//评估单转化率

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
					//$('#sp_evalFee_bar')[0].style.width=parseFloat(data.evalFee.replace(/,/g,''))*1000/loanAmount*100+'%';
				}else{
					$('#sp_loanAmount_bar')[0].style.width = '0%';
					$('#sp_signAmount_bar')[0].style.width = '0%';
					$('#sp_actualAmount_bar')[0].style.width = '0%';
					//$('#sp_evalFee_bar')[0].style.width = '0%';
				}
				
				if(parseFloat(data.convRate)>100){
					$('#sp_convRate_bar')[0].style.width='100%';
				}else{
					$('#sp_convRate_bar')[0].style.width=data.convRate;
				}
				/*if(parseFloat(data.efConvRate)>100){
					$('#sp_efConvRate_bar')[0].style.width='100%';
				}else{
					$('#sp_efConvRate_bar')[0].style.width=data.efConvRate;
				}*/
				/*if(parseFloat(data.efConverRt)>100){
					$('#ef_converRt_bar')[0].style.width='100%';
				}else{
					$('#ef_converRt_bar')[0].style.width=data.efConverRt;
				}*/
				
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
		    /*tooltip: {
		        trigger: 'item',
		        formatter: formatter
		    },*/
		    /*legend: {
		        orient: 'vertical',
		        x: 'left',
		        data: getLegend(data)
		    },*/
		    color: ['#ffae6b','#275da5','#4dbcbe', '#0a8dc9', '#f989a5','#13bfa1'],
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
		                    formatter : formatter,
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