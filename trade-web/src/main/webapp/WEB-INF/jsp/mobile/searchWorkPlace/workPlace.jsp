<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/momedia/css/common/aui.2.0.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/momedia/css/common/style.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/momedia/iconfont/iconfont.css" />
    
    <link rel="stylesheet" type="text/css" href="${ctx}/static/momedia/css/workplace/workplace.css" />
</head>
<body>
    <header class="aui-bar aui-bar-nav">
        <a class="aui-pull-left aui-btn" onClick="javascript :history.back(-1);">
        </a>
        <div class="aui-title">办事地点查询</div>
    </header>
    <div class="aui-searchbar aui-clearfix relative search-grey-bg" id="search">
        <div class="aui-searchbar-input aui-border-radius"  >
            <i class="aui-iconfont aui-icon-search"></i>
            <form action="javascript:search();">
                <input type="search" placeholder="查询办事机构" id="search-input">
            </form>
        </div>
        <div class="aui-searchbar-cancel black" onclick="doSearch()" >查询</div>
    </div>
    <section class="aui-grid">
        <div class="row aui-text-center">
            <table class="table table-work">
              <tbody>
                <tr>
                  <td class="no-left work-selected" >签合同</td>
                  <td>办过户</td>
                  <td>公积金</td>
                  <td>调档</td>
                </tr>
                <tr>
                  <td>婚姻登记</td>
                  <td>公证委托</td>
                  <td>征信查询点</td>
                  <td></td>
                </tr>
              </tbody>
            </table>
        </div>
    </section>

    <section>
        <ul class="aui-list aui-media-list" id="Org"></ul>
       
    </section>
   </body>

<script type="text/javascript" src="${ctx}/static/momedia/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="${ctx}/static/momedia/js/api.js"></script>
<script type="text/javascript" src="${ctx}/static/momedia/js/aui-dialog.js"></script>
<script type="text/javascript" src="${ctx}/static/momedia/js/aui-main.js"></script>
<script type="text/javascript" src="${ctx}/static/momedia/js/layer.js"></script>

<script>
$(function () {
    var searchBar = document.querySelector(".aui-searchbar-input");

    document.querySelector(".aui-searchbar-cancel").onclick = function(){
        this.style.marginRight = "-"+(this.offsetWidth)+"px";
        document.getElementById("search-input").value = '';
        document.getElementById("search-input").blur();
    }

    //手机拨号弹窗层
    var $telbox = $(".tel-box");
    $telbox.hide();
    //遮罩弹窗层隐藏显示

    $(document).on("click",".tel-cancel, .layui-m-layershade",function() {
        $(".tel-box").hide();
    });

});


</script>

<script>
var items=[];

$(function(){

    $.ajax({
        type: "GET",
        url: "${ctx}/static/momedia/css/workplace/workplace.json",
        dataType: "json",
        success : function(data){
            items=data;
            placeInfo(items,0,"");
        }

    });

    var $work = $(".table-work tbody tr td");
    $work.on("click",function() {
        var index = $work.index(this);
        if(!$(this).text() == "") {
            $work.removeClass('work-selected');
            $(this).addClass("work-selected");
        }
        var name=$("#search-input").val();
        placeInfo(items,index,name);

    });
    //查询
    $("#search-input").bind("input propertychange", function() {
        doSearch();
    });
});
function doSearch(){
    var $work = $(".table-work tbody tr td");
    var indexItem=$(".work-selected");
    var index = $work.index(indexItem);
    var name=$("#search-input").val();
    placeInfo(items,index,name);
}
//显示电话列表
function showPhones(e){
    console.info($(e).parent().next());
    $(e).parent().next().slideToggle("slow");
}

function placeInfo(obj,index,name) {
        if(index==7){
           return;
           }
        $('#Org').empty();
        var html = '';
        $.each(obj, function(commentIndex, comment) {
        function content() {
                html += '<li class="aui-list-item place-list-item">'
                    + '<div class="aui-media-list-item-inner relative">'
                    + '<div class="aui-list-item-inner">'
                    +   '<div class="work-list-title" >'
                    +   comment['name']
                    +   '</div>'
                    +   '<div class="site">'
                    +   '<a href="http://map.baidu.com/?l=&s=s%26wd%3D'+comment['site']+'" target="_blank"><i class="iconfont mr5 font14">&#xe682;</i>'
                    + comment['site'] + '</a>'
                     +'</div>'
                    +'</div>';
                    var phones=[];
                    if(comment['tel'].indexOf("/")>-1){
                       phones=comment['tel'].split("/");
                    }else{
                        phones.push(comment['tel']);
                    }
                html +='<div class="tel-center tel-code"><a onclick="showPhones(this)"><i class="iconfont tel-icon ">&#xe681;</i></a></div>';
                html += '<div style="display:none;" class="layui-m-layer layui-m-layer0 tel-box" >'
                    +'<div class="layui-m-layershade"></div>'
                    +'<div class="layui-m-layermain">'
                    +'<div class="layui-m-layersection">'
                    +'<div class="layui-m-layerchild  layui-m-anim-scale noradius">'
                    +'<div class="tel-title">'+comment['name']+''
                    +'<span>点击电话图标，拨打相关工作人员联系电话</span></div>'
                    +'<article class="aui-content tel-text">'
                    +'<ul class="aui-list aui-date-list">'
                    for(var i=0;i<phones.length;i++){
                    html +='<a href="tel:'+phones[i]+'">'
                            +'<li class="aui-list-item aui-padded-lr20 boder-bottom-style">'
                                +'<div class="aui-info-item">'
                                    +'<span class="time">'+phones[i]+'</span>'
                                +'</div>'
                                +'<div class="aui-info-item aui-text-grey">'
                                   +'<i class="iconfont tel-icon font26">&#xe681;</i>'
                                +'</div>'
                           + '</li>'
                        +'</a>';
                     }
                    html+= '</ul>'
                    +'</article>'
                    +'<p class="tel-cancel" name="phonesList'+commentIndex+'" onclick="hideWndow('+commentIndex+')"> 取消</p>'
                    +'</div>'
                    +'</div>'
                    +'</div>'
                    + '</div>';

                html+='</div>'
                    +'<div class="aui-row  aui-row-padded aui-place">'
                    +'<div class="aui-col-xs-4">'
                    +'<p class="title">工作时间</p>'
                    +'</div>'
                    +'<div class="aui-col-xs-4 text-center">'
                    +'<p class="title">上午</p>'
                    +'</div>'
                    +'<div class="aui-col-xs-4 text-center">'
                    +'<p class="title">下午</p>'
                    +'</div>'
                    for(var i=0; i<comment['time'].length;i++){
                        /*console.log(comment['time'][i].day);*/
                        html += '<p class="aui-col-xs-4">'+ comment['time'][i].day +'</p>'
                            +'<p class="aui-col-xs-4 text-center">'+ comment['time'][i].am +'</p>'
                            +'<p class="aui-col-xs-4 text-center">'+ comment['time'][i].pm +'</p>'
                    }
                    +'</div>'
                    +'</li>';
        }

        //筛选搜索
        var num=comment['name'].indexOf(name);
        if(comment['code'] == index && num>-1) {
            content();
        }
        $('#Org').html(html);
    });
}

</script>

</html>