<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style type="text/css">
	.l{float:left;}.r{float:right;}.fix{overflow:hidden; zoom:1;}
	.rot_box{width:512px; margin:0 auto; font-size:84%;}
	.image_box img{margin-top:-150px; margin-left:-400px;}
</style>

<script type="text/javascript">
window.onload = function(){
	var param = {
		right: document.getElementById("rotRight"),
		left: document.getElementById("rotLeft"),
		img: document.getElementById("rotImg"),
		cv: document.getElementById("canvas"),
		rot: 0
	};
	var rotate = function(canvas,img,rot){
		//获取图片的高宽
		var w = img.width;
		var h = img.height;
		//角度转为弧度
		if(!rot){
			rot = 0;	
		}
		var rotation = Math.PI * rot / 180;
		var c = Math.round(Math.cos(rotation) * 1000) / 1000;
		var s = Math.round(Math.sin(rotation) * 1000) / 1000;
		//旋转后canvas标签的大小
		canvas.height = Math.abs(c*h) + Math.abs(s*w);
		canvas.width = Math.abs(c*w) + Math.abs(s*h);
		//绘图开始
		var context = canvas.getContext("2d");
		context.save();
		//改变中心点
		if (rotation <= Math.PI/2) {
			context.translate(s*h,0);
		} else if (rotation <= Math.PI) {
			context.translate(canvas.width,-c*h);
		} else if (rotation <= 1.5*Math.PI) {
			context.translate(-c*w,canvas.height);
		} else {
			context.translate(0,-s*w);
		}
		//旋转90°
		context.rotate(rotation);
		//绘制
		context.drawImage(img, 0, 0, w, h);
		context.restore();
		img.style.display = "none";	
	}
	var fun = {
		right: function(){
			param.rot += 90;
			rotate(param.cv, param.img, param.rot);
			if(param.rot === 270){
				param.rot = -90;	
			}	
		},
		left: function(){
			param.rot -= 90;
			if(param.rot === -90){
				param.rot = 270;	
			}
			rotate(param.cv, param.img, param.rot);			
		}
	};
	param.right.onclick = function(){
		fun.right();
		return false;
	};
	param.left.onclick = function(){
		fun.left();
		return false;
	};
};
</script>

</head>
<body>
	
	<div class="rot_box">
    <p class="fix f9 mb10"><a id="rotLeft" class="l" href="javascript:void(0);">&lt;&lt;向左转</a><a id="rotRight" class="r" href="javascript:void(0);">向右转&gt;&gt;</a></p>
    <div class="image_box">
        <canvas id="canvas"></canvas>
        <img id="rotImg" src="${img }" />
    </div>
</div>
	
</body>
</html>