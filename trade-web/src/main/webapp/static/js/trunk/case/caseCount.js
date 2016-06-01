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
	addLinkHref(month,sUserId,'虹口贵宾服务部');
	
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
				$("#sp_evalFee").text(data.evalFee);
				$("#sp_efConvRate").text(data.efConvRate);
				$("#sp_receiveCount").text(data.receiveCount);
				$("#sp_signCount").text(data.signCount);
				$("#sp_transferCount").text(data.transferCount);
				$("#sp_closeCount").text(data.closeCount);
				setStaDetailDef();
				setStaVal($(data.staLoanApply),$(data.staLoanSign),$(data.staLoanRelease));
   	 			var d1 =toDonutData($(data.staLoanSign),'count');
   	 			var d2 =toDonutData($(data.staLoanSign),'amount');
   	 			setDonut(d1,d2);
			}
	 });
}

// 申请金额/面签金额/放款金额增加链接
function addLinkHref(month,sUserId,sUserName) {
	var d = new Date();
	var year = d.getYear();
	// 获得最后一天
	var lastday = new Date(year,month,0).getDate();   
	
	var startTime = d.Format("yyyy-"+month+"-01");
    var endTime = d.Format("yyyy-"+month+"-"+lastday);

	var applyTimeLink = "window.open('"+ctx+"/loan/loanAgentList?isLoanAgentTimeType=1&startTime="+startTime+"&endTime="+endTime+"&sUserId="+sUserId+"&sUserName="+sUserName+"')";
	var signTimeLink = "window.open('"+ctx+"/loan/loanAgentList?isLoanAgentTimeType=2&startTime="+startTime+"&endTime="+endTime+"&sUserId="+sUserId+"&sUserName="+sUserName+"')";
	var releaseTimeLink = "window.open('"+ctx+"/loan/loanAgentList?isLoanAgentTimeType=3&startTime="+startTime+"&endTime="+endTime+"&sUserId="+sUserId+"&sUserName="+sUserName+"')";
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