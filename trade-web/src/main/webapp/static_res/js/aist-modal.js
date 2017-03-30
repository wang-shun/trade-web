var Modal = (function(){
	var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
	var _alert = function (options) {
		var settings = $.extend({
			Title: '系统提示',
			Message: '提示内容',
			BtnCancel: '取消',
			BtnOk : '确定'
		},options||{});
		
		var html = $("#mymodal").html().replace(reg, function(node, key) {
			return {
				Title : settings.Title,
				Message : settings.Message,
				BtnOk : settings.BtnOk,
				BtnCancel : settings.BtnCancel
			}[key];
		});
		$("#mymodal").html(html);
		
		$("#mymodal").find('.btn-default').hide();
		$("#mymodal").modal("show");
	};
	
	return {
		alert : _alert
	};
	
})();
/*$(function() {
			window.Modal = function() {
				var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
				var alr = $("#myModal");
				var ahtml = alr.html();
				var _alert = function(options) {
					alr.html(ahtml); // 复原
					alr.find('.cancel').hide();
					alr.find('.close').hide();
					_dialog(options);

					
				};
				var _confirm = function(options) {
					alr.html(ahtml); // 复原
					alr.find('.cancel').show();
					_dialog(options);
					
				};
				var _reset=function(){
					alr.html(ahtml); // 复原
					alr.hide();
				};
				
				var _dialog = function(options) {
					alr.show();
					var ops = {
						msg : "提示内容",
						title : "操作提示",
						btnok : "确定",
						btncl : "取消"
					};
					$.extend(ops, options);
					var html = alr.html().replace(reg, function(node, key) {
								return {
									Title : ops.title,
									Message : ops.msg,
									BtnOk : ops.btnok,
									BtnCancel : ops.btncl
								}[key];
							});
					alr.html(html);
					alr.modal({
								width : 500,
								backdrop : 'static'
							});
				};
				return {
					alert : _alert,
					confirm : _confirm,
					reset:_reset
				};

			}();
		});*/