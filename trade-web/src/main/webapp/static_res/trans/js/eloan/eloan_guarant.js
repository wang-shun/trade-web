var divIndex = 1;
function getAtr(i){
    var divTr = $('#hideAddTr').clone();
    divTr.show();
    /*$str+= '<div class="line" id="addTr_' + divIndex + '">'
                + '<div class="form_content">'
                + '<label class="control-label sign_left_small mar24">抵押物品类别</label>'
                +                     '<aist:dict id="mortgageCategory" name="mortgageCategory" clazz="select_control sign_right_one"'
				+ 'display="select"  dictType="MORTGAGE_TYPE" tag="card"'
				+ 'ligerui="none" defaultvalue=""></aist:dict>'
                + '</div>'
                + '<div class="form_content">'
                + '<label class="control-label sign_left_small mar24">抵押物品名称</label>'
                + '<input type="text" placeholder="" class="select_control teamcode" id="mortgageName" name="mortgageName">'
                + '</div>'
                + '<a href="javascript:void(0)" class="add_space" onclick="getAtr(this)">添加</a><a href="javascript:void(0)" class="add_space" onclick="getDel(this)">删除</a>'
                + '</div>'*/
    $("#mortgageList").append(divTr);
    divIndex++;
}

function getDel(k){
    $(k).parents('.line').remove();
    divIndex--;
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


