$(function(){
	$("#searchBtn").click(function(){
		reloadGrid();
	});
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
var changed = false;//是否变更值班人员
function chooseDutyOfficer(date,type) {
	dutyDate = date;
	dutyType = type;
	var tdid = $("#"+dutyDate+dutyType).html();
	if(tdid==''){
		changed=false;
	}else{
		changed = true;
	}
	userSelect({
		startOrgId : '',//非营业部
		expandNodeId : '',
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
	        url:ctx+ "/signroom/addOrUpdateTradeCenterSchedule" ,
	        method: "post",
	        dataType: "json",
	        data: {
	        	dutyOfficer : dutyOfficer,
	        	dutyDate : dutyDate,
	        	dutyType : dutyType,
	        	tradeCenterId : tradeCenterId,
	        	changed : changed
	        },
	        beforeSend: function () {  
	        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
				$(".blockOverlay").css({'z-index':'9998'});
	        },  
	        success: function(data){
	          $.unblockUI();   	
	          if(data.success){
	        	  alert(data.message);
	        	  $("#"+dutyDate+dutyType).html(officerName);
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
		if(!changed){
			return;
		}else{
			$.ajax({
				async: true,
		        url:ctx+ "/signroom/deleteTradeCenterSchedule" ,
		        method: "post",
		        dataType: "json",
		        data: {
		        	dutyDate : dutyDate,
		        	dutyType : dutyType,
		        	tradeCenterId : tradeCenterId
		        },
		        beforeSend: function () {  
		        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
					$(".blockOverlay").css({'z-index':'9998'});
		        },  
		        success: function(data){
		          $.unblockUI();   	
		          if(data.success){
		        	  alert(data.message);
		        	  $("#"+dutyDate+dutyType).html("");
		          }else{
		        	  alert(data.message);
		          }
		        },
		        error: function (e, jqxhr, settings, exception) {
		        	$.unblockUI();   	 
		        }  
		  });
		}
	}
}

