
$(function(){
	
	//页面初始化
	init();
	
	//条件查询
	$("#searchButton").click(function(){
		reloadGrid();
	});
	
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
    
  //选择切换
    $(".choices span").click(function() {
        if($(this).hasClass("selected")) {
            $(this).removeClass("selected");
        } else {
            $(this).addClass("selected");
        }
    });

    $(".datatime").val(show());

    $("#today").click(function(){
        $(".datatime").val(getDateWeek(0));
    });
    
    $("#tommrow").click(function(){
        $(".datatime").val(getDateWeek(1));
    });
    
});

function init(){
	//加载预约时间
	getResTime();
	
	//加载签约室预约列表信息
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
	var resPersonId = $("input[name='resPersonId']").attr("hVal");
	var resNo = $("input[name='resNo']").val();
	var mobile = $("input[name='mobile']").val();
	var startDateTime = $("input[name='startDateTime']").val();
	var endDateTime = $("input[name='endDateTime']").val();
	var resTime = $("#selResTime option:selected").val();
	var resStatus = $("#selResStatus option:selected").val();
	
	var startResTime = '';
	var endResTime = '';
	if(resTime != ""){
		startResTime = resTime.substring(0,resTime.indexOf("-")) + ":00";
		endResTime = resTime.substring(resTime.indexOf("-") + 1,resTime.length) + ":00";
		
		var startNum = Number(startResTime.substring(0,startResTime.indexOf(":")));
		if(startNum < 10){
			startResTime = "0" + startResTime; 
		}
		
		var endNum = Number(endResTime.substring(0,endResTime.indexOf(":")));
		if(endNum < 10){
			endResTime = "0" + endResTime;
		}
	}
	
	var data = {};
	data.resPersonId = resPersonId;
	data.resNo = resNo;
	data.mobile = mobile;
	data.startDateTime = startDateTime;
	data.endDateTime = endDateTime;
	data.startResTime = startResTime;
	data.endResTime = endResTime;
	data.resStatus = resStatus;
	
	return data;
}

//选取营业部经纪人
function chooseManager(id) {
	var serviceDepId = id;
	var yuCuiOriGrpId = $("#yuCuiOriGrpId").val();
	userSelect({
		startOrgId : '1D29BB468F504774ACE653B946A393EE',//营业部
		expandNodeId : '1D29BB468F504774ACE653B946A393EE',
		nameType : 'long|short',
		orgType : '',
		departmentType : '',
		departmentHeriarchy : '',
		chkStyle : 'radio',
		jobCode : 'JWYGW',
		callBack : signRoomSelectUserBack
	});
}

function signRoomSelectUserBack(array) {
	if (array && array.length > 0) {
		$("#resPersonId").val(array[0].username);
		$("#resPersonId").attr('hVal', array[0].userId);
		$("#resPersonOrgId").val(array[0].orgId);//预约人组织ID

	} else {
		$("#resPersonId").val("");
		$("#resPersonId").attr('hVal', "");
		$("#resPersonOrgId").val("");
	}
}

//今天明天选择
function Appendzero (obj) {
    if (obj < 10) return "0" + obj; else return obj;
}

function getDateWeek (x) {
    var now = new Date();
    var year = now.getFullYear ();//获取四位数年数
    var month = now.getMonth () + 1;
    var date = now.getDate () + x;
    var s = year + "-" + Appendzero (month) + "-" + Appendzero (date) ;
    return s;
}

function show(){
    var mydate = new Date();
    var str = "" + mydate.getFullYear() + "-";
    str += "0" + (mydate.getMonth()+1) + "-";
    str += mydate.getDate();
    return str;
}

//获取预约时间
function getResTime(){
	var strHtml = "<option value=''>预约时间</option>";
	
	$.ajax({
		cache:false,
		async:false,
		type:"POST",
		dataType:"json",
		url:ctx+"/mobile/reservation/getBespeakTime",
		success:function(data){
			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					strHtml += "<option value='"+ data[i] + "'>" + data[i] + "</option>";
				}
			}
		}
	});
	
	$("#selResTime").html(strHtml);
}
