/**
 * centaline.sh.com Inc. bootstrap 手动给checkbox 设置click事件 原因 1 如果由aist:dict生成输出的 是
 * input type=checkbox框 bootstrap加载时候回 改变 input div结构而界面排版错误, 和界面渐变效果 input由大→表小
 * 原因 2由jsp标签aist:dict 生成的bootstrap 原生态的checkbox加载在 bootstrap checkbox click之后
 * 而无效 所以 2比1好 总结 bootstrap和普通的 input checkbox存在很大的差异 Copyright (c) 2015-2015
 * All Rights Reserved.
 */

var CentalineCheckBox = function() {

	var click = function(inputName) {
		
		$('input[name="' + inputName + '"]').click(function() {
			var inode=$(this).parent().parent().parent().find("i");
			
					if (!$(this.parentElement).hasClass("checked")) {
						$(this.parentElement).addClass('checked');
						$(inode).show();
						$(this).prop('checked', true);
						
					} else {
						$(this.parentElement).removeClass('checked');
						$(inode).hide();
						$(this).prop('checked', false);
					}
				});

	}
	
	var cleanCheck = function(inputName) {
		$('input[name="' + inputName + '"]').each(function() {
					if ($(this.parentElement).hasClass("checked")) {
						$(this.parentElement).removeClass('checked');
						$(this).prop('checked', false);
						var inode=$(this).parent().parent().parent().find("i");
						$(inode).hide();
					} 
				});
		
	}
	return {
		'click' : click,
		'cleanCheck':cleanCheck
	};

}();