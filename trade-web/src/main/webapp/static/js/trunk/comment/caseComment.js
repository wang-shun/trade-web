/****
 *  
 *    备注模块组件
 *    
 * 
 */
; !function($, window) {
	 "use strict";

	var ctx = window.ctx;
	var self = '';
	var tempSource = '';
	jQuery.fn.caseCommentGrid = function(options) {
		
		var templeteSource = '{{if rows.length>0}}'  
			+'{{each rows as item index}}'
			+ '{{if index == 0}}'
			+ '<div class="form_one col-sm-12 unline_none">'
	        + '{{else}}'
	        + '<div class="form_one col-sm-12">'
	        + '{{/if}}'
	        + '<span id="title">{{item.CREATE_TIME}}<span class="time">{{item.CREATE_BY}}</span><i class="sq_label">{{item.SRV_CODE}}</i></span>'
	        + '<span>{{item.COMMENT}}</span>'
	        + '</div>'
	        +'{{/each}}'
	        +'{{else}}'
	        +'<div class="form_one col-sm-12">无备注</div>'
	        +'{{/if}}';
		
		var commentButton = '<div class="input_line">';
		commentButton+='<input type="hidden" name="caseComment_caseCode" id="caseComment_caseCode"/>';
		commentButton+='<input type="hidden" name="caseComment_srvCode" id="caseComment_srvCode"/>';
		commentButton+='<input type="text" placeholder="填写内容" name="caseComment" id="caseComment" style="width:1030px;">';
		commentButton+='<div class="btn btn-primary add_btn" onclick="saveCaseComment()">添加案件跟进</div>';
		commentButton+='</div>';
		$(this).after(commentButton);
		
		var commentTitile ='<div style="padding:10px;margin-top:5px;background-color:#ffffff;"><h3>案件跟进</h3></div>';
		$(this).before(commentTitile);
		self = $(this);
		tempSource = templeteSource;
		
		var caseCode = options.caseCode;
		var srvCode = options.srvCode;
		$('#caseComment_caseCode').val(caseCode);
		$('#caseComment_srvCode').val(srvCode);
		
		$(this).aistGrid({
   			ctx : ctx,
   			queryId : 'queryCasePartCommentList',
   		    templeteSource : templeteSource,
   		    data : {caseCode:caseCode}
   		}); 
	};
	
}(jQuery, window);

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
	var templeteSource =  '{{if rows.length>0}}'
						+ '{{each rows as item index}}'
						+ '{{if index == 0}}'
						+ '<div class="form_one col-sm-12 unline_none">'
				        + '{{else}}'
				        + '<div class="form_one col-sm-12">'
				        + '{{/if}}'
				        + '<span id="title">{{item.CREATE_TIME}}<span class="time">{{item.CREATE_BY}}</span><i class="sq_label">{{item.SRV_CODE}}</i></span>'
				        + '<span>{{item.COMMENT}}</span>'
				        + '</div>'
				        +'{{/each}}'
				        +'{{else}}'
				        +'<div class="form_one col-sm-12">无备注</div>'
				        +'{{/if}}';;
	
	var caseCode = $('#caseComment_caseCode').val();
	$("#caseCommentList").reloadGrid({
    	ctx : ctx,
		queryId : 'queryCasePartCommentList',
		templeteSource : templeteSource,
		    data : {caseCode:caseCode}
    })
};