
$(function(){
	
	//页面初始化
	init();
	
	 //全选
    $("#CheckedAll").click(function(){
        var isChecked = $(this).prop("checked");
        $('input[name=items]').prop("checked", isChecked );
        $("#work").prop("checked", false );

    });
    
    $('input[type=checkbox][name=items]').click(function(){
        var flag=true;
        $('input[type=checkbox][name=items]').each(function(){
            if(!$(this).prop("checked") == true){
                flag = false;
            }
        });
        if( flag ){
            $('#CheckedAll').prop('checked', true );
        }else{
            $('#CheckedAll').prop('checked', false );
        }
    });
    
    //工作日选择
    $("#work").click(function() {
        var isChecked = $(this).prop("checked");
        if($(this).prop("checked") == true){				 //如果当前点击的多选框被选中
            $('.work1').prop("checked", isChecked );
            $(".zhoumo").prop("checked", false );
            $("#CheckedAll").prop("checked", false );
        }else{
            $('.work1').prop("checked", false );
        }
    });

    $('.work1').click(function(){
        var flag=true;
        $('.work1').each(function(){
            if(!$(this).prop("checked") == true){
                flag = false;
            }
        });
        if( !flag ){
            $("#work").prop('checked', false );
        }
    });
    
    $(".zhoumo").click(function(){
        if($(this).prop("checked") == true){				 //如果当前点击的多选框被选中
            $('#work').prop("checked", false );
        }
    });

    //日历控件
    $('.input-daterange').datepicker({
        keyboardNavigation: false,
        forceParse: false,
        autoclose: true
    });
    
});

function init(){
	reloadGrid();
}

function reloadGrid(){
	var data = getParams();
	
	$("#signinglist").reloadGrid({
    	ctx : ctx,
		queryId : "signingList",
	    templeteId : 'template_signingList',
	    data : data,
	    wrapperData : data
    });
}

function getParams() {
	
	var data = {};
	
	return data;
}