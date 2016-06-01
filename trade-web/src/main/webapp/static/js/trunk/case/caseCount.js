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