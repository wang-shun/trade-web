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
				$("#sp_evalFee").text(data.evalFee);
				$("#sp_efConvRate").text(data.efConvRate);
				$("#sp_receiveCount").html(
						"<a href='"+ctx+"/report/statis/caseDetail?createTimeStart="+createTimeStart+"&createTimeEnd="+createTimeEnd+"&status=received&arg="+sUserId+"' target='_blank'>" +
						"<font  class='fa-2x font-bold text-danger'>"+data.receiveCount+"</font>" +
						"</a>"
				);
				$("#sp_signCount").html(
						"<a href='"+ctx+"/report/statis/caseDetail?createTimeStart="+createTimeStart+"&createTimeEnd="+createTimeEnd+"&status=signed&arg="+sUserId+"' target='_blank'>" +
						"<font  class='fa-2x font-bold text-danger'>"+data.signCount+"</font>" +
						"</a>"						
				);
				$("#sp_transferCount").html(
						"<a href='"+ctx+"/report/statis/caseDetail?createTimeStart="+createTimeStart+"&createTimeEnd="+createTimeEnd+"&status=transfered&arg="+sUserId+"' target='_blank'>" +
						"<font  class='fa-2x font-bold text-danger'>"+data.transferCount+"</font>" +
						"</a>"						
				);
				$("#sp_closeCount").html(
						"<a href='"+ctx+"/report/statis/caseDetail?createTimeStart="+createTimeStart+"&createTimeEnd="+createTimeEnd+"&status=closed&arg="+sUserId+"' target='_blank'>" +
						"<font  class='fa-2x font-bold text-danger'>"+data.closeCount+"</font>" +
						"</a>"						
				);
				setStaDetailDef();
				setStaVal($(data.staLoanApply),$(data.staLoanSign),$(data.staLoanRelease));
   	 			var d1 =toDonutData($(data.staLoanSign),'count');
   	 			var d2 =toDonutData($(data.staLoanSign),'amount');
   	 			setDonut(d1,d2);
			}
	 });
}