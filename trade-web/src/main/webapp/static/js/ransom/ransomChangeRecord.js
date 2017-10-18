$(document).ready(function() {
	var ctx = "${ctx}";
	loadChangDetailInfo();
});

function loadChangDetailInfo(){
	debugger;
	var url = ctx + '/quickGrid/findPage';
	var ransomCode = $('#ransomCode').val();
	var data = {};
	data.page = 1;
	data.rows = 10;
	data.queryId = "queryRansomTimeRecord1";
	data.argu_ransomCode = ransomCode;
	$.ajax({
		async: true,
		type:'POST',
		url:url,
		dataType:'json',
		data:data,
		beforeSend: function () {  
        	$.blockUI({message:$("#salesLoading"),css:{'border':'none','z-index':'9999'}}); 
        },  
        success: function(data){
        	$.unblockUI();
        	var html = template('template_timeRecord',data);
      		$('#record-detail').html(html);
      		console.log(data);
      		console.log(html+$('#record-detail').html())
      		
        }
	});	
}