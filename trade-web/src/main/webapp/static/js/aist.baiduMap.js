/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */

var BaiduMap = function () {
	
	 //创建地图容器
	var map=null; 
	var point=null;//=new BMap.Point(121.438917,31.221424);
	var marker=null;
	var markerPoint=function(){
		marker = new BMap.Marker(point);  // 创建标注
		map.addOverlay(marker);               // 将标注添加到地图中
		marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	}; 
	return {
		init: function (target,x,y) {
			map=new BMap.Map(target); 
			point=new BMap.Point(x,y);
			map.centerAndZoom(point, 16);
			map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
			//添加鱼骨控件
			map.addControl(new BMap.NavigationControl());
			//$("#tab6").css("display","none");
			//$("#tab6").hide();
			markerPoint();
			//手动延迟变，样式是地图位置偏移
			setTimeout(function(){
				map.setZoom(15);   
			}, 100);  //2秒后放大到14级
			},
		fun_search:function(name,target){
			map.clearOverlays(); 
			markerPoint();
			//智能搜索Localsearch类
			var options = {renderOptions: {map: map, panel: target,autoViewport: true}};
			//模糊查询search方法
			var myLocalsearch = new BMap.LocalSearch(map,options);
			//1000的范围
			myLocalsearch.searchNearby(name,point,1500);
		}
		/*,show:function(){
			map.clearOverlays(); 
			markerPoint();
			
			$("#tab6").css("display","block");
			//$("#tab6").show();
		}*/
	};
}();


   