//滚动条固定
/*jQuery(function($) {
	$(document).ready( function() {
   		$('.stickup-nav-bar').stickUp({
          parts: {
            0:'base_info',
            1:'zj_info',
            2:'loan_info',
          },
          itemClass: 'menuItem',
          itemHover: 'active',
          marginTop: 'auto'
        });
    });
});*/



$(document).ready(function() {
	var divIndex = 1;
	// 添加放款金额
	var html = '';
	// $("#add_money").click(function(event) {
	// 	var html =  '<li>'+
 //                        '<div class="form_content">'+
 //                            '<label class="control-label sign_left_two">'+
 //                                '放款金额'+
 //                            '</label>'+
 //                            '<input class="input_type sign_right_two" value="" >'+
 //                            '<div class="input-group date_icon">'+
 //                                '<span class="danwei">万</span>'+
 //                            '</div>'+
 //                        '</div>'+
 //                        '<div class="form_content form_nomargin">'+
 //                            '<label class="control-label sign_left_two">'+
 //                                '放款时间'+
 //                            '</label>'+
 //                            '<input class="input_type sign_right_two" value="" />'+
 //                            '<div class="input-group date_icon">'+
 //                                '<i class="fa fa-calendar"></i>'+
 //                            '</div>'+
 //                        '</div>'+
 //                        '<button type="button" class="btn btn-success margin_tagl15">确认</button>'+
 //                        '<button type="button" class="btn btn-default margin_tagl10" id="btnRm">删除</button>'+
 //                    '</li>';

	// 	// alert(txt);
	// 	$(".loan_ul").append(html);



		divIndex++;
	});

});






