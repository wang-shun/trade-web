/**
 * 赎楼详情js
 * @author wbcaiyx
 */


$(document).ready(function(){
//	debugger;
	reloadDetail();
	reloadHistoryRecord();
	reloadTimeRecord();

});

/**
 * 详情table加载
 */
function reloadDetail(){
	var url = ctx + '/quickGrid/findPage';
	var ransomCode = $('#ransomCode').val();
	var data = {};
	data.page = 1;
	data.rows = 10;
	data.queryId = "queryRansomDetail";
	data.argu_ransomCode = ransomCode;
//	debugger;
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
        	var html = template('template_ransomDetail',data);
      		$('#tails').html(html);
        }
	});	
}

	/**
	 * 加载历史申请记录
	 * @returns
	 */
	function reloadHistoryRecord(){
		debugger;
		var url = ctx + '/quickGrid/findPage';
		var ransomCode = $('#ransomCode').val().trim();
		var borrowerUser = $("#borrowerUser").text();
		var data = {};
		data.page = 1;
		data.rows = 10;
		data.queryId = "queryRansomHistoryRecord";
		data.argu_ransomCode = ransomCode;
		data.argu_borrowerUser = borrowerUser;
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
	        	var html = template('template_ransomHistoryRecord',data);
	      		$('#his-record').html(html);
	        }
		});	
	}
	
	/**
	 * 加载时间信息记录
	 * @returns
	 */
	function reloadTimeRecord(){
		debugger;
		var url = ctx + '/quickGrid/findPage';
		var ransomCode = $('#ransomCode').val().trim();
		var data = {};
		data.page = 1;
		data.rows = 10;
		data.queryId = "queryRansomTimeRecord";
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
	        	var html = template('template_ransomTimeInfo',data);
	      		$('#time-record').html(html);
	        }
		});	
	}