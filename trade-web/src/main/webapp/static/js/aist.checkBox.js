/**
 * AISThink.com Inc. bootstrap 手动给checkbox 设置click事件 原因 1 如果由aist:dict生成输出的 是
 * input type=checkbox框 bootstrap加载时候回 改变 input div结构而界面排版错误, 和界面渐变效果 input由大→表小
 * 原因 2由jsp标签aist:dict 生成的bootstrap 原生态的checkbox加载在 bootstrap checkbox click之后
 * 而无效 所以 2比1好 总结 bootstrap和普通的 input checkbox存在很大的差异 Copyright (c) 2015-2015
 * All Rights Reserved.
 */

var AistCheckBox = function() {

	var click = function(inputName) {
		$('input[name="' + inputName + '"]').click(function() {
					if (!$(this.parentElement).hasClass("checked")) {
						$(this.parentElement).addClass('checked');
						$(this).prop('checked', true);
					} else {
						$(this.parentElement).removeClass('checked');
						$(this).prop('checked', false);
					}
				});

	}
	/***
	 *  针对checkBox后面带标签的点击事件，需要动态设置其样式为红色
	 */
	var clickChangeBg = function(inputName,className) {
		$('input[name="' + inputName + '"]').click(function() {
					if (!$(this.parentElement).hasClass("checked")) {
						$(this.parentElement).addClass('checked');
						$(this).prop('checked', true);
						$(this).parent().parent().parent().addClass(className);   // 设置整个背景色
					} else {
						$(this.parentElement).removeClass('checked');
						$(this).prop('checked', false);
						$(this).parent().parent().parent().removeClass(className);
					}
				});

	}
	var cleanCheck = function(inputName) {
		$('input[name="' + inputName + '"]').each(function() {
					if ($(this.parentElement).hasClass("checked")) {
						$(this.parentElement).removeClass('checked');
						$(this).prop('checked', false);
					} 
				});
	}
	return {
		'click' : click,
		'clickChangeBg' : clickChangeBg,
		'cleanCheck':cleanCheck
	};

}();