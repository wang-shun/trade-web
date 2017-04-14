(function($, window){
	
	var ctx = window.ctx;
	$.fn.eloanCaseCommentGrid = function(options) {

		var templeteSource = '<div class="view-box" style="height:100px;">{{if rows.length>0}}'  
			+'{{each rows as item index}}'
			+ '<div class="view clearfix">'
			+ '<p><span class="auditor">跟进人：<em>{{item.CREATE_BY}}({{item.jobName}})</em></span><i class="sq_label">{{item.SRV_CODE}}</i>'
			+ '<span class="time">跟进日期:<em>{{item.CREATE_TIME}}</em></span></p>'
			+ '<p><span class="auditing">跟进备注</span><em class="view_content">{{item.COMMENT}}</em></p>'
			+ '</div>'
	        + '{{/each}}'
	        + '{{else}}'
	        + '<div style="width: 100%;height: 81px;background:url('+ctx+'/image/false.png) no-repeat center -7px;;background-size:45%;" ></div>'
	        + '{{/if}}</div>';
		
		var commentTitile ='<div class="title mb20 title-mark"><strong style="font-weight:bold;">e+跟进</strong></div>';
		$(this).before(commentTitile);
		
		
		setMaxHeight();
		
		$(this).aistGrid({
   			ctx : ctx,
   			queryId : 'queryEloanCommentList',
   		    templeteSource : templeteSource,
   		    data : options
   		}); 
	};
	
	//设置提交跟进信息文本框高度自适应
	function setMaxHeight(){
		$(".chackTextarea").autoTextarea({
	        maxHeight:110,
	    });
	}
	
	
})(jQuery, window)