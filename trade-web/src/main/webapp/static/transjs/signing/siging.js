$(function () {
    $(".choices span").click(function() {
        if($(this).hasClass("selected")) {
            $(this).removeClass("selected");
        } else {
            $(this).addClass("selected");
        }

    });
    
    $.ajax({
		url:ctx+"/signroom/generatePageDate",
		method:"post",
		dataType:"json",
		data:{
			curDate : $("#curDate").val()
		},
		success:function(data){
			alert(JSON.stringify(data));
			console.log(data);
			
		}
	});
    
    
    
    
    
})

