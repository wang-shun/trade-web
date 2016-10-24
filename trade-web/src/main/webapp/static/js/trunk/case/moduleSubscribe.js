/**
 * Created by caoyuan7 on 2016/10/19.
 */
/**
 *插件注释:
 *1.页面按钮效果html
 *  moduleCode为关注模块主键，isSubscribe为true为关注，false为不关注
<span id="myCaseList">
    <span style="cursor: pointer;" class="starmack subscribe"  moduleCode="{{item.CASE_CODE}}" isSubscribe="true">
        <i class="iconfont markstar star_subscribe" status="1"></i>
     </span>
 <span>
 *调用方法:
  $("#myCaseList").subscribeToggle({
        moduleType:"1001",//模块类型1001为案件类型
        subscribeType:"2001"//模块功能类型2001为收藏
    });
 *基本CSS
     .starmack .star_subscribe{
      color: #ccc;
    }
     .starmack .star_text_2{
      display: none;
    }
     .starmack .star_text_1{
      display: inline;
    }
     .starmack.active .star_subscribe{
      color:#f8cd59;
    }
     .starmack.active .star_text_1{
      display: none;
    }
     .starmack.active .star_text_2{
      display: inline;
    }
 */
(function() {
    $.fn.subscribeToggle = function (options) {
        var ops = $.extend({},$.fn.subscribeToggle.defaults,options);
        return this.each(function(){
            $this = $(this);
            $this.find(".subscribe").click(function(){
                $this_c = $(this);
                var moduleCode =  $this_c.attr("modulecode");
                var isSubscribe =  $this_c.attr("isSubscribe");
                SUBSCRIBE._subscribe(moduleCode,ops.moduleType,ops.subscribeType,"",isSubscribe,function(obj){
                    if(isSubscribe=='true'){
                        $this_c.addClass("active");
                        $this_c.attr("isSubscribe",false);
                    }else{
                        $this_c.removeClass("active");
                        $this_c.attr("isSubscribe",true);
                    }
                },function(obj){
                    alert(obj.msg);
                });
            });
            $.fn.subscribeToggle.defaults={
                moduleType:"1001",
                subscribeType:"2001"
            };
        })
    }

    window.SUBSCRIBE = window.SUBSCRIBE || {
            _Server : 'http://trade.centaline.com:8083/trade-web',
            /**
             * 初始化
             */
            init: function() {
                SUBSCRIBE._Server = $("#ctx").val()==''? SUBSCRIBE._Server :  $("#ctx").val() ;
            },
            _subscribe: function(moduleCode, moduleType,subscribeType, remark, isSubscribe,callback,onError) {
                var data ={
                    "moduleCode": moduleCode,
                    "moduleType" : moduleType,
                    "subscribeType": subscribeType,
                    "remark" : remark,
                    "isSubscribe": isSubscribe
                };
                $.ajax({
                    url: SUBSCRIBE._Server+"/subscribe/saveOrDeleteCaseSubscribe",
                    dataType: 'json',
                    type: "POST",
                    data: data,
                    success: function(result) {
                        if (result.success != true) {
                            var resp = {};
                            resp.success = result.result;
                            resp.msg = result.message;
                            onError(resp);
                            return;
                        } else {
                            var resp = {};
                            resp.success = result.result;
                            resp.msg = result.message;
                            callback(resp);
                            return;
                        }
                    },
                    error: function(result) {
                        var resp = {};
                        resp.msg = result.message;
                        onError(resp);
                    },
                    timeout: 5000
                });
            },
        };
})();
