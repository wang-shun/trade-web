<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">
<link href="${ctx}/css/transcss/comment/caseComment.css" rel="stylesheet">
<link href="${ctx}/css/plugins/pager/centaline.pager.css" rel="stylesheet" />
</head>
<body>
<div class="ibox-title">
                  <h5>填写表单，完成任务 </h5>    
                    							        
                 <form name="varForm" id="varForm">
                   <div class="ibox-content">
					<div class="form-group"><label class="col-sm-2 control-label">备注</label>
					<div class="col-sm-10"><input type="text" class="form-control" name="comment1" id="comment"></div>
			        </div>
			        <input type="button" name="save-button" value="保存" class="btn">
					<input type="button" name="submit-button" value="提交" class="btn btn-primary" onclick="completeTask()">
					</div>      
                    </form>                               
</div>
<!-- <div id="caseCommentList" class="add_form">
</div> -->
<content tag="local_script">
    <!-- Peity -->
    <script src="${ctx}/js/plugins/peity/jquery.peity.min.js"></script>

    <!-- jqGrid -->
    <script src="${ctx}/js/plugins/jqGrid/i18n/grid.locale-en.js"></script>
    <script src="${ctx}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
    
	<script src="${ctx}/js/trunk/comment/caseComment.js"></script>
	<script src="${ctx}/js/plugins/pager/jquery.twbsPagination.min.js"></script>
	<script src= "${ctx}/js/template.js" type="text/javascript" ></script>
	<script src="${ctx}/js/plugins/aist/aist.jquery.custom.js"></script>
<script>
$.fn.serializeObject = function() {    
		   var o = {};    
		   var a = this.serializeArray();    
		   $.each(a, function() {    
		       if (o[this.name]) {    
		           if (!o[this.name].push) {    
		               o[this.name] = [o[this.name]];    
		           }    
		           o[this.name].push(this.value || '');    
		       } else {    
		           o[this.name] = this.value || '';    
		       }    
		   });    
		   return o;    
		}; 
      function completeTask(){
    	
		  //convert form element to json format. if only certain element, use comma to seperate;Remenber to set id for the element
		  //or can use document.FormName.Comment (as example)
    	  var jsonuserinfo = $('#comment').serializeObject();  
          alert(JSON.stringify(jsonuserinfo));  

   /*  $.ajax({         
            	
            	url:"${ctx}/rest/task/TaskComplete/"+taskId,
            	type:'POST',
            	dataType:"json",
            	data: {
            		"name":"apply",
            		"value":"true",
            		
            	},
            	error:function(msg){
            		alert("更新失败");
            		
            	},
            	success:function(data){
            		//对data进行封装
            	
            		alert(data);
            		
            	}
            	
            });  */
      }
    
        
</script>
</content>
</body>


</html>