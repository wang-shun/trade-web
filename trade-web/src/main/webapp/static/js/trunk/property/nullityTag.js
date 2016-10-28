/**
 * 无效标记
 * liaohail
 * 
 */
var ctx = $("#ctx").val();

//提交数据
function tagSubmit(ctx){
	//var unSuccessReason = $("#unSuccessReason").val();
	//var tagId = $("#tagId").val();
	if($("#unSuccessReason").val()==''){
		alert('请输入无效原因');
		return false;
	}
	var toPropertyResearch ={
			'pkid' :  $("#tagId").val(),
			'unSuccessReason' : $("#unSuccessReason").val()
	};
	toPropertyResearch=$.toJSON(toPropertyResearch);
	$.ajax({
		cache : false,
		type : "POST",
		url : ctx+'/property/nullityTag',
		contentType: "application/json; charset=utf-8",
		dataType : "json",
		data :toPropertyResearch,
		success : function(data) {
			alert(data.message)
			if(data.success){
				parent.location.reload();
				 parent.$.fancybox.close();
			}
		},
		error : function(errors) {
			alert("处理出错,请刷新后再次尝试！");
		}
	});
}
//关闭弹窗
function closeFancybox(){
	 parent.$.fancybox.close();
}