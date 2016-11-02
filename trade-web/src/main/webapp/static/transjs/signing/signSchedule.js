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