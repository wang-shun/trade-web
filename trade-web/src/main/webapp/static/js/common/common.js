
/*  ---------------备注模块组件开始------------------*/

//渲染跟进备注控件
; !function($, window) {
	 "use strict";

	var ctx = window.ctx;
	var self = '';
	var tempSource = '';
	jQuery.fn.caseCommentGrid = function(options) {
		
		var templeteSource = '<div class="view-box" style="height:100px;">{{if rows.length>0}}'  
			+'{{each rows as item index}}'
			+ '<div class="view clearfix">'
			+ '<p><span class="auditor">跟进人：<em>{{item.CREATE_BY}}({{item.jobName}})</em></span>'
			+ '<span class="time">跟进日期:<em>{{item.CREATE_TIME}}</em></span></p>'
			+ '<p><span class="auditing">跟进备注</span><em class="view_content">{{item.COMMENT}}</em></p>'
			+ '</div>'
	        + '{{/each}}'
	        + '{{else}}'
	        + '<p class="text-center"><img src="'+ ctx + '/image/false.png" height="100" alt="" /></p>'
	        + '{{/if}}</div>';
		
		
		var commentButton = '<div class="excuse clearfix">';
		commentButton += '<input type="hidden" name="caseComment_caseCode" id="caseComment_caseCode"/>';
		commentButton += '<input type="hidden" name="caseComment_srvCode" id="caseComment_srvCode"/>';
		commentButton += '<div class="form_list clearfix"><input class="input_type pull-left" name="caseComment" id="caseComment" placeholder="" value="" style="width:93%;">';
		commentButton += '<button class="btn btn_more pull-right" style="width:60px;" onclick="saveCaseComment()">跟进</button>';
		/*commentButton += '<div class="pull-left"><textarea class="chackTextarea" name="caseComment" id="caseComment"></textarea></div>';
		commentButton += '<div class="pull-left"><button type="button" class="btn btn-icon btn-blue ml10" onclick="saveCaseComment()">提交跟进</button></div></div>';*/
		$(this).after(commentButton);	
            
		var commentTitile ='<div class="title mb20 title-mark"><strong style="font-weight:bold;">案件跟进</strong></div>';
		$(this).before(commentTitile);
		self = $(this);
		tempSource = templeteSource;
		
		var caseCode = options.caseCode;
		var srvCode = options.srvCode;
		$('#caseComment_caseCode').val(caseCode);
		$('#caseComment_srvCode').val(srvCode);
		
		setMaxHeight();
		
		$(this).aistGrid({
   			ctx : ctx,
   			queryId : 'queryCasePartCommentList',
   		    templeteSource : templeteSource,
   		    data : {caseCode:caseCode}
   		}); 
	};
	
}(jQuery, window);

//保存跟进备注信息
function saveCaseComment() {
	var caseCode = $('#caseComment_caseCode').val();
	var comment = $("#caseComment").val();
	if(comment==''){
		alert('添加案件备注不能为空');
		return false;
	}
	var srvCode = $('#caseComment_srvCode').val();
	var toCaseComment = {
		caseCode : 	caseCode,
		srvCode : srvCode,
		comment : comment
	};
	//toCaseComment = $.toJSON(toCaseComment);
	$.ajax({
        url:ctx+ "/caseComment/insertToCaseComment",
        method: "post",
        dataType: "json",
        //contentType: "application/json; charset=utf-8",
        data: toCaseComment,
        success: function(data){
            //console.log(data);
        	$("#caseComment").val('');
        	reloadGrid();
        	$('#caseCommentList').scrollTop(0);
        }
    });
};

function reloadGrid() {
	var templeteSource = '<div class="view-box" style="height:100px;">{{if rows.length>0}}'  
		+'{{each rows as item index}}'
		+ '<div class="view clearfix">'
		+ '<p><span class="auditor">跟进人：<em>{{item.CREATE_BY}}({{item.jobName}})</em></span>'
		+ '<span class="time">跟进日期:<em>{{item.CREATE_TIME}}</em></span></p>'
		+ '<p><span class="auditing">跟进备注</span><em class="view_content">{{item.COMMENT}}</em></p>'
		+ '</div>'
        +' {{/each}}'
        + '{{else}}'
        + '<p class="text-center"><img src="'+ ctx + '/image/false.png" height="100" alt="" /></p>'
        +' {{/if}}</div>';
	
	var caseCode = $('#caseComment_caseCode').val();
	$("#caseCommentList").reloadGrid({
    	ctx : ctx,
		queryId : 'queryCasePartCommentList',
		templeteSource : templeteSource,
		    data : {caseCode:caseCode}
    })
};

  /*  ---------------备注模块组件结束------------------*/

 
	$(function(){
		
		//在途列表点击链接转向
		$("#btnZaitu").click(function(){
			location.href = ctx + "/case/myCaseList";
		});
		
		//案件视图点击链接转向
		$("#btnCaseView").click(function(){
			var caseCode = $(this).attr("lang");
			location.href = ctx + "/task/caseDetail?&caseCode=" + caseCode;
		});
		
		//日历控件
	    $('.input-daterange').datepicker({
	    	todayBtn: "linked",
	        keyboardNavigation: false,
	        forceParse: false,
	        autoclose: true
	    });
	    
	    //发送短信提醒
	    $("#sendSMS").click(function() {
			var t = '';
			var s = '/';
			$("#reminder_list").find("input:checkbox:checked").closest('td').next().each(function() {
				t += ($(this).text() + s);
			});
			
			if (t != '') {
				t = t.substring(0, t.length - 1);
			}
			
			$("#smsPlatFrom").smsPlatFrom({
				ctx : '${ctx}',
				caseCode : $('#caseCode').val(),
				serviceItem : t
			});
		});
	    
	    //设置提交跟进信息文本框高度自适应
	    $(".chackTextarea").autoTextarea({
	        maxHeight:220,
	    });
	});
	
	//设置提交跟进信息文本框高度自适应
	function setMaxHeight(){
		$(".chackTextarea").autoTextarea({
	        maxHeight:220,
	    });
	}

	
	

	
