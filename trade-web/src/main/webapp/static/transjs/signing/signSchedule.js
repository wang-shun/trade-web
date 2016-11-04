$(function(){
	
})
//加载数据
function reloadGrid(bm) {
	if(!bm){
		bm=monthSel.getDate().format('yyyy-MM-dd');	
	}else{
		bm=bm.format('yyyy-MM-dd');
	}
	var centerId = $('#centerId').val();
	var data = {
			centerId : centerId,
			date : bm
	};
	$.ajax({
		async: true,
        url:ctx+ "/signroom/showSchedulingData" ,
        method: "post",
        dataType: "json",
        data: data,
        beforeSend: function () {  
        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
			$(".blockOverlay").css({'z-index':'9998'});
        },  
        success: function(data){
          console.log(data);
          $.unblockUI();   	
          if(data.success){
        	  var tradeCenterSchedule = template('template_tradeCenterScheduleList' , data);
			  $("#tradeCenterSchedule").empty();
			  $("#tradeCenterSchedule").html(tradeCenterSchedule);
          }else{
        	  alert(data.message);
          }
        },
        error: function (e, jqxhr, settings, exception) {
        	$.unblockUI();   	 
        }  
  });
}

//选择值班人
var dutyDate;
var dutyType;
function chooseDutyOfficer(date,type) {
	dutyDate = date;
	dutyType = type;
	userSelect({
		startOrgId : 'ff8080814f459a78014f45a73d820006',//非营业部
		expandNodeId : 'ff8080814f459a78014f45a73d820006',
		nameType : 'long|short',
		orgType : '',
		departmentType : '',
		departmentHeriarchy : '',
		chkStyle : 'radio',
		jobCode : '',
		callBack : dealDutyOfficerBack
	});
	
	
}
//选择值班人回调函数
function dealDutyOfficerBack(array){
	var tradeCenterId = $("#centerId").val();
	if (array && array.length > 0) {
		var dutyOfficer = array[0].userId;
		var officerName = array[0].username;
		$.ajax({
			async: true,
	        url:ctx+ "/signroom/addTradeCenterSchedule" ,
	        method: "post",
	        dataType: "json",
	        data: {
	        	dutyOfficer : dutyOfficer,
	        	dutyDate : dutyDate,
	        	dutyType : dutyType,
	        	tradeCenterId : tradeCenterId
	        },
	        beforeSend: function () {  
	        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
	        },  
	        success: function(data){
	          console.log(data);
	          $.unblockUI();   	
	          if(data.success){
	        	  
	          }else{
	        	  alert(data.message);
	          }
	        },
	        error: function (e, jqxhr, settings, exception) {
	        	$.unblockUI();   	 
	        }  
	  });

	} else {
		//删除该日期的值班经理
		
	}
}

