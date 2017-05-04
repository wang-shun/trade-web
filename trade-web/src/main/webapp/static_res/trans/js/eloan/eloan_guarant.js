
var x = 1;
function getAtr(i){
    $str='';
    $str+= '<div class="line" status="mark" id="addTr_' + x + '">'
        + '<div class="form_content">'
        + '<label class="control-label sign_left_small mar24">物品类别</label>'
        + '<select id="mortgageCategory" name="mortgageCategory" class="select_control sign_right_one mark" onclick="change(this)">'
        + '<option value="">请选择</option>'
        + '<option value="carded">身份证</option>'
        + '<option value="bankCard">银行卡</option>'
        + '</select>'
        + '</div>'
        + '<div class="form_content">'
        + '<label class="control-label sign_left_small">物品名称</label>'
        + '<input type="text" placeholder="" class="select_control teamcode" id="mortgageName" name="mortgageName" value="" style="margin-left:4px;">'
        + '</div>'
        + '<div class="form_content">'
        + '<label class="control-label sign_left_small"><font color="red">*</font>保管人</label>'
        + '<input id="itemManager" name="itemManager" class="teamcode input_type" value="" hVal="" onclick="chooseItemManager(this)" style="margin-left:4px;" readonly="readonly"/>'
        + '<div class="input-group float_icon organize_icon managerOnclick">'
	 	+ '<i class="icon iconfont">&#xe627;</i>'
	    + '</div>'
        + '</div>'
        + '<a href="javascript:void(0)" class="add_space" onclick="getAtr(this)">添加</a><a href="javascript:void(0)" class="add_space" onclick="getDel(this)">删除</a>'
        + '<div class="entry" style="display:none;">'
        + '<div class="form_content">'
        + '<label class="control-label sign_left_small mar24">姓名</label>'
        + '<input type="text" class="select_control sign_right_one" id="referName" name="referName">'
        + '</div>'
        + '<div class="form_content">'
        + '<label class="control-label sign_left_small mar24">'
            + '身份证号'
        + '</label>'
        + '<input type="text" class="select_control teamcode" id="referCode" name="referCode">'
        + '</div>'
        + '</div>'
        + '<div class="entry" style="display:none;">'
        + '<div class="form_content">'
        + '<label class="control-label sign_left_small mar24">'
            + '户名'
        + '</label>'
        + '<input type="text" class="select_control sign_right_one" id="referName" name="referName">'
        + '</div>'
        + '<div class="form_content">'
        + '<label class="control-label sign_left_small mar24">'
            + '银行卡号'
        + '</label>'
        + '<input type="text" class="select_control teamcode" id="referCode" name="referCode">'
        + '</div>'
        +  '</div>'
        + '</div>'
    $("#mortgageList").append($str);
    x++;
}
function getAdd(i){
    $str='';
    $str+= '<div class="line" status="mark" id="addTr_' + x + '">'
        + '<div class="form_content">'
        + '<label class="control-label sign_left_small mar24">物品类别</label>'
        + '<select id="mortgageCategory" name="mortgageCategory" class="select_control sign_right_one mark" onclick="change(this)">'
        + '<option value="">请选择</option>'
        + '<option value="propertyCard">产权证</option>'
        + '<option value="otherCard">他证</option>'
        + '</select>'
        + '</div>'
        + '<div class="form_content">'
        + '<label class="control-label sign_left_small mar24">'
            + '物品名称'
        + '</label>'
        + '<input type="text" placeholder="" class="select_control teamcode" id="mortgageName" name="mortgageName" value="">'
        + '</div>'
        + '<div class="form_content">'
        + '<label class="control-label sign_left_small"><font color="red">*</font>保管人</label>'
        + '<input id="itemManager" name="itemManager" class="teamcode input_type" value="" hVal="" onclick="chooseItemManager(this)" style="margin-left:4px;" readonly="readonly"/>'
        + '<div class="input-group float_icon organize_icon managerOnclick">'
	 	+ '<i class="icon iconfont">&#xe627;</i>'
	    + '</div>'
        + '</div>'
        + '<a href="javascript:void(0)" class="add_space" onclick="getAdd(this)">添加</a><a href="javascript:void(0)" class="add_space" onclick="getDel(this)">删除</a>'
        + '<div class="entry" style="display:none;">'
        + '<div class="form_content">'
        + '<label class="control-label sign_left_small mar24">产权编号</label>'
        + '<input type="text" class="select_control sign_right_one" id="referCode" name="referCode" value="">'
        + '</div>'
        + '<div class="form_content">'
        + '<label class="control-label sign_left_small mar24">'
            + '产权人姓名'
        + '</label>'
        + '<input type="text" class="select_control teamcode" id="referName" name="referName" value="">'
        + '</div>'
        + '<div class="form_content">'
        + '<label class="control-label sign_left_small mar24">'
            + '房屋地址'
        + '</label>'
        + '<input type="text" class="select_control teamcode" id="referAddreass" name="referAddreass" value="">'
        + '</div>'
        + '</div>'
        + '<div class="entry" style="display:none;">'
        + '<div class="form_content">'
        + '<label class="control-label sign_left_small mar24">'
            + '他证编号'
        + '</label>'
        + '<input type="text" class="select_control sign_right_one" id="referCode" name="referCode" value="">'
        + '</div>'
        + '<div class="form_content">'
        + '<label class="control-label sign_left_small mar24">'
            + '他项权利人'
        + '</label>'
        + '<input type="text" class="select_control teamcode" id="referName" name="referName" value="">'
        + '</div>'
        + '<div class="form_content">'
        + '<label class="control-label sign_left_small mar24">'
            + '房屋地址'
        + '</label>'
        + '<input type="text" class="select_control teamcode" id="referAddreass" name="referAddreass" value="">'
        + '</div>'
        +  '</div>'
        + '</div>'
    $("#mortgageList").append($str);
    x++;
}

function getDel(k){
/*    var status = $(k).parents('.line').attr("status");
    console.log(status);
    if(text = "mark" ) {
      
    }*/
    // 如果数据库中已经存在，然后点击删除
    var line = $(k).parents('.line');
    var obj = line.children("input[name='pkid']").val();
    if(obj === undefined) {
    	$(k).parents('.line').remove();
    } else {
    	console.log(line.children("input[name='pkid']").val());
    	$(k).parents('.line').hide();
    }
}
// type : carded , bankCard
function change(boj) {
    var $entry = $(boj).parents(".line").find(".entry");
    var $mark = $(boj).val();
    if ($mark == "" || $mark == 'mortgageContract') {
      $entry.hide();
    }
    if ($mark == 'carded' || $mark == 'propertyCard') {
      $entry.eq(0).show();
      $entry.eq(1).hide();
    }
    if ($mark == 'bankCard' || $mark == 'otherCard') {
      $entry.eq(1).show();
      $entry.eq(0).hide();
    }
}

function hide() {
    $(".entry").hide();
}

$(function() {

/*开关按钮*/
 /* $('[name="isModifyPhone"]').bootstrapSwitch({
      onText:"未修改",
      offText:"已修改",
      onColor:"primary",
      offColor:"default",
      size:"small",
      onSwitchChange:function(event,state){
          if(state==true){
              $(this).val("1");
          }else{
              $(this).val("2");
          }
      }
  })*/


  /*poshytip 提示框JS*/
  //left
    $('.demo-left').poshytip({
      className: 'tip-twitter',
      showTimeout: 1,
      alignTo: 'target',
      alignX: 'left',
      alignY: 'center',
      offsetX: 8,
      offsetY: 5,
    });

    //right
    $('.demo-right').poshytip({
      className: 'tip-twitter',
      showTimeout: 1,
      alignTo: 'target',
      alignX: 'right',
      alignY: 'center',
      offsetX: 8,
      offsetY: 5,
    });

    //top
    $('.demo-top').poshytip({
      className: 'tip-twitter',
      showTimeout: 1,
      alignTo: 'target',
      alignX: 'center',
      alignY: 'top',
      offsetX: 8,
      offsetY: 5,
    });

    //bottom
    $('.demo-bottom').poshytip({
      className: 'tip-twitter',
      showTimeout: 1,
      alignTo: 'target',
      alignX: 'center',
      alignY: 'bottom',
      offsetX: 8,
      offsetY: 5,
    });

    //押卡信息登记添加删除效果





})


