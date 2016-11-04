var ctx = $("#ctx").val();

$(function(){
	
	reloadGrid();
	
	//进入预约取号界面点击事件
	$("#goToBespeakUI").click(function(){
		var remainResNum = getRemainResNum();  //判断是否有可用预约次数
		
		if(remainResNum == 0){
			alert("您这两周的预约次数已用完！");
			return false;
		}
		
		location.href = ctx + "/weixin/signroom/bespeakUI";
	});
});

//获取剩余预约次数
function getRemainResNum(){
	var remainResNum;
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"text",
		url:ctx+"/weixin/signroom/getRemainBespeakNumber",
		success:function(data){
			remainResNum = data;
		}
	});
	
	return remainResNum;
}

function reloadGrid(){
	var data = getParams();
	
	$("#myReservationList").reloadGrid({
    	ctx : ctx,
		queryId : "myReservationList",
	    templeteId : 'template_myReservationList',
	    data : data,
	    wrapperData : data
    });
}

function getParams() {
	var agentCode = $("#agentCode").val();
	
	var data = {};
	data.resPersonId = agentCode;
	data.pagination = false;
	
	return data;
}

//弹出取消预约确认框
function openConfirm(resId){
	layer.open({
        type: 0,
        title: '',
        content: "<div class='dialog-user'>"
        + "<h2 class='dialog-head mt20 font18'>您确定要取消本次预约吗？</h2>"
        + "<div class='btn-box mt20'>"
        + "<a href='javascript:void(0);' onclick=\"cancelReservation('" + resId + "');\"><div class='aui-btn aui-btn-primary aui-margin-r-10'>确定</div></a>"
        + "<a href='javascript:void(0);' onclick='layer.closeAll();'><div class='aui-btn aui-btn-grey aui-margin-l-10'>取消</div></a>"
        + "</div>"
    });
}

//打开取消预约确定弹出框
var dialog = new auiDialog({})
function openDialog(text,resId){
    dialog.alert({
        title:"",
        msg:'您确定要取消本次预约吗?',
        buttons:['确定','取消']
    },function(ret){
        if(ret){
            if(ret.buttonIndex == 1) {
            	cancelReservation(resId);
            } else {
                return false;
            }
        }
    })
}


//取消预约
function cancelReservation(resId){
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"text",
		url:ctx+"/weixin/signroom/cancelReservation",
		data:{resId:resId},
		success:function(data){
			if(data == "true"){
				setTimeout(function(){ 
					window.location.href = ctx + "/weixin/signroom/myReservationList";
			    }, 1000);
			}
		}
	});
}

