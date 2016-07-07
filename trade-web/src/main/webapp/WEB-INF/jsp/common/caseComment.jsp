<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<meta charset="UTF-8">
	<title>列表页申请办理</title>
	
	<style>
		*{padding:0;margin:0;box-sizing:border-box;}
		.main{background-color:#f3f3f3;}
		.apply-wrap{background-color:#fff;margin:20px 0px 12px;font-family:'微软雅黑','Arial';}
		.apply-wrap .table{font-size:14px;}
		.apply-wrap .table{width:100%;padding-bottom:200px;background-color:#fff;}
		.apply-wrap .apply-table{width:100%;border:1px solid #eaeaea;border-right:0;}
		.apply-wrap .apply-table th{font-weight:normal;color:#fff;height:35px;background-color:#52cdec;}
		.apply-table th,.apply-table td{text-align:left;padding-left:10px;border-right:1px solid #eaeaea;}
		.apply-table tr td{color:#333;height:31px;}
		.apply-table .tr-1{background-color:#fff;}
		.apply-table .tr-2{background-color:#f4f4f4;}
		.apply-table tr .color-666{color:#666;}
		.apply-table tr .fs12{font-size:12px;}
		.apply-table tr .btn-y{color:#fff;padding:2px 10px;border-radius:3px;background-color:#f9ad58;text-decoration:none;}
		.triangle-up,.triangle-down{width:0;height:0;border-left:7px solid transparent;border-right:7px solid transparent;position:relative;left:8px;top:16px;}
		.triangle-up{border-bottom:10px solid #fff;top:-13px;}
		.triangle-down {border-top:10px solid #fff;}
		.fs12 i,.sqr i{color:#fff;font-size:12px;font-style:normal;padding:0px 1px;margin-right:5px;border-radius:2px;background-color:#52cdec;}
		.fs12 i.valid-label{background-color:#00cb1d;}
		.fs12 i.invalid-label{background-color:#ccc;}
		.sqr i.jl-label{background-color:#ee6384;margin-left:5px;}
		.sqr i.yc-label{background-color:#52cdec;margin-left:5px;}
		.fs12 span{display:block;padding:0px 0px 1px 0px;}
		.apply-table tr .sq-state{padding:0px 0px 0px 10px !important;}
		
		.apply-table tr td.invalid{width:150px;padding-right:10px !important;}
		.apply-table tr em{font-style:normal;font-size:12px;line-height:18px;}
		.apply-table tbody a{color:#1a5f8e;}
		.input_line {
			margin-top: 5px;
		}
		.input_line input {
			width:100%;
			border: 1px solid #eee;
			border-radius: 5px;
			padding:10px 15px;
			font-family: '微软雅黑','Arial';
			font-size: 14px;
		}
		.add_form {
			border: 1px solid #eee;
			border-radius: 5px;
			background: #fff;
			padding:15px;
			height: 210px;
			margin-top:4px;
		}
		.form_one {
			border-top:1px solid #eee;
			padding: 12px 0px;
		}
		.form_one h4 {
			color: #333;
			font-weight: normal;
		}
		.unline_none {
			border: none;
		}
		.form_one p {
			font-size: 14px;
			padding-top: 6px;
			color: #666;
		}
		.sq_label {
			margin-left: 6px;
			padding:2px 5px;
			color: #fff;
		    font-size: 14px;
		    font-style: normal;
		    margin-right: 5px;
		    border-radius: 2px;
		    background-color: #52cdec;
		}
		.time {
			margin-left: 15px;
		}
		.add_btn {
			margin: 5px 0px;
		}
		.add_btn a {
		color:#fff;}
	</style>
</head>

<div id="caseCommentList">
</div>
<div class="input_line">
    	<input type="hidden" name="caseComment_caseCode" id="caseComment_caseCode"/>
    	<input type="hidden" name="caseComment_srvCode" id="caseComment_srvCode"/>
	   <input type="text" placeholder="填写内容" name="caseComment" id="caseComment">
	   <div class="btn btn-primary add_btn" onclick="saveCaseComment()">添加备注</div>
</div>

<script id="queryCasePartCommentList" type= "text/html">
	<div class="add_form" style="overflow-y:auto;">
		{{each rows as item index}}
		   <div class="form_one col-sm-12">
			   <h4>{{item.CREATE_BY}}<span class="time">{{item.CREATE_TIME}}</span><i class="sq_label">{{item.SRV_CODE}}</i></h4>
			   <p>{{item.COMMENT}}</p>
		   </div>
		{{/each}}
	</div>
</script>
<script src="${ctx}/js/jquery-2.1.1.js"></script>
<script>
		function initCaseComment(caseCode,srvCode) {
			$('#caseComment_caseCode').val(caseCode);
			$('#caseComment_srvCode').val(srvCode);
			
       	    $("#caseCommentList").aistGrid({
       			ctx : "${ctx}",
       			queryId : 'queryCasePartCommentList',
       		    templeteId : 'queryCasePartCommentList',
       		    data : {caseCode:caseCode}
       		}); 
		}
		
		function saveCaseComment() {
			var caseCode = $('#caseComment_caseCode').val();
			var comment = $("#caseComment").val();
			var srvCode = $('#caseComment_srvCode').val();
			var toCaseComment = {
				caseCode : 	caseCode,
				srvCode : srvCode,
				comment : comment
			};
			//toCaseComment = $.toJSON(toCaseComment);
			$.ajax({
                url:ctx+ "/caseComment/insertToCaseComment",
                method: "post",
                dataType: "json",
                //contentType: "application/json; charset=utf-8",
                data: toCaseComment,
                success: function(data){
                    //console.log(data);
                	reloadGrid();
                }
            });
		};
		
		function reloadGrid() {
			var caseCode = $('#caseComment_caseCode').val();
			 $("#caseCommentList").reloadGrid({
    	    	ctx : "${ctx}",
   			    queryId : 'queryCasePartCommentList',
      		    templeteId : 'queryCasePartCommentList',
      		    data : {caseCode:caseCode}
    	    })
		}
</script>
