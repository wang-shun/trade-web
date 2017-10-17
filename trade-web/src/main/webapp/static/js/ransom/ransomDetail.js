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
		var ransomCode = $('#ransomCode').val();
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
	
	/**
	 * 权证变更
	 */
	function showOrgCp() {
		debugger;
		var url = "/case/getUserOrgCpUserList";
		var ctx = $("#ctx").val();
		var caseCode= $("#caseCode").val();
		url = ctx + url;
		var data={operation:""};
		$.ajax({
			cache : false,
			async : true,
			type : "POST",
			url : url,
			dataType : "json",
			timeout : 10000,
			data : data,
			success : function(data) {
				console.info(data);
				showLeadingModal(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
	
	/**
	 * 选择权证modal
	 * @param data
	 */
	function showLeadingModal(data) {
		var addHtml = '';
		var ctx = $("#ctx").val();
		$.each(data,function(i, n) {
							addHtml += '<div class="col-lg-4"><div class="contact-box">';
							addHtml += '<a href="javascript:changeLeadingUser(' + i
									+ ')">';
							addHtml += '<div class="col-sm-4"><div class="text-center">';
							addHtml+='<span class="userHead">';
							if(n.imgUrl!=null){
								addHtml += '<img onload="javascript:imgLoad(this)" alt="image" class="himg" src="'+n.imgUrl+'">';
							}
							addHtml+='</span>';
							addHtml += '<div class="m-t-xs font-bold">金融权证</div></div></div>';
							addHtml += '<div class="col-sm-8">';
							addHtml += '<input id="user_' + i
									+ '" type="hidden" value="' + n.id + '">';
							addHtml += '<h3><strong>' + n.realName
									+ '</strong></h3>';
							addHtml += '<p>联系电话：' + n.mobile + '</p>';
							addHtml += '<p>当前单数：' + n.userCaseCount + '</p>';
							addHtml += '<p>本月接单：' + n.userCaseMonthCount + '</p>';
							addHtml += '<p>未过户单：' + n.userCaseUnTransCount + '</p>';
							addHtml += '</div><div class="clearfix"></div></a>';
							addHtml += '</div></div>';
						})
		$("#leading-modal-data-show").html(addHtml);

		$('.contact-box').each(function() {
			animationHover(this, 'pulse');
		});
		$('#leading-modal-form').modal("show");
	}
	/**
	 * 变更权证
	 * @param index
	 */
	function changeLeadingUser(index) {
		window.wxc.confirm("您是否确认进行责任人变更？",{"wxcOk":function(){
			var caseCode = $("#caseCode").val();
			var instCode = $("#instCode").val();
			var userId = $("#user_" + index).val();

			var url = "/case/changeLeadingUser";
			var ctx = $("#ctx").val();
			url = ctx + url;
			var params = '&userId=' + userId + '&caseCode=' + caseCode+"&instCode="+instCode;

			$.ajax({
				cache : false,
				async : true,
				type : "POST",
				url : url,
				dataType : "json",
				timeout : 10000,
				data : params,
				
				success : function(data) {
					if(data.success){
						window.wxc.success("变更成功");
						location.reload();
					}else{
						window.wxc.error(data.message);
					}
					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			});
		}});
	}