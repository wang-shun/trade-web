//打开遮罩层
function openBlock(options){
	var __widowWidth = $(window).width(); var __popWidth = $("#Loading").width(); var __left = (__widowWidth - __popWidth)/2 + 'px'; var __windowHeight = $(window).height(); var __popHeight = $("#Loading").height(); var __top = (__windowHeight-__popHeight)/2 + 'px'; 
	var defaults = {
			css:{border:0
				,width:'80'
				,height:'25'
			    ,left:__left,width:__popWidth+'px',top:__top,position:'fixed', //居中 
			},
			message: "<span style='line-height:25px;font-size:14px;margin:5px 5px 0px 5px;'>请稍后...</span>"
		};
	if(options){$.blockUI($.extend(defaults, options));}else{$.blockUI(defaults);}
}
//关闭遮罩层
function closeBlock(){$.unblockUI();}
$(function(){
	//遮罩层ajax提交
	$.extend({blockAjax:function(options){try{if(parent.openBlock && typeof(parent.openBlock)=="function"){parent.openBlock();}else{openBlock();}}catch(e){} var _success = options.success; options.success = function(data) { try{ if(parent.closeBlock && typeof(parent.closeBlock)=="function"){ parent.closeBlock(); }else{ closeBlock(); }}catch(e){} _success(data);};$.ajax(options);}});
});
//input或textare光标定位插值组件
;(function($){
	$.fn.cursorposition = function(myValue) {
         var $t = $(this)[0];
         if (document.selection) {
             this.focus();
             sel = document.selection.createRange();
             sel.text = myValue;
             this.focus();
         } else if ($t.selectionStart || $t.selectionStart == '0') {
             var startPos = $t.selectionStart;
             var endPos = $t.selectionEnd;
             var scrollTop = $t.scrollTop;
             $t.value = $t.value.substring(0, startPos) + myValue + $t.value.substring(endPos, $t.value.length);
             this.focus();
             $t.selectionStart = startPos + myValue.length;
             $t.selectionEnd = startPos + myValue.length;
             $t.scrollTop = scrollTop;
         } else {
             this.value += myValue;
             this.focus();
         }
     }
})( jQuery );
