/**
 * Created by caoyuan7 on 2016/10/19.
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
