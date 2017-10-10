/**
 * 赎楼详情js
 * @author wbcaiyx
 */


$(document).ready(function(){
	reloadDetail();

});

/**
 * 详情table加载
 */
function reloadDetail(){
	var ctx = $('#ctx');
	var url = ctx + '/quickGrid/findPage';
	var ransomCode = $('#ransomCode');
	var data = {};
	data.queryId = "queryRansomDetail";
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
        	var html = template('template_ransomDetail',data);
      		$('#tails').html(html);
        }
	});	
}