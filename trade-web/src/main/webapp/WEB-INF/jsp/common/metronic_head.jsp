<%@page import="com.centaline.sales.workbench.web.SessionUserConstants"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>
<%
	request.setAttribute("sessionUser", SessionUserConstants.getSesstionUser());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
/*========================加载岗位::开始=========================*/
var orgJob = {
	getOrgJob:function(){
		$.ajax({    
	           type:'get',        
	           url:'${ctx}/workbench/listUserOrgJob.json',    
	           dataType:'json', 
	           cache:false, 
	           success:function(data){
	        	   if(data){
		        	   $("#portalOrgJob").append(orgJob.createOrgJob(data));
	        	   }
	           }    
	    });
	},
	createOrgJob:function(data){
		var orgJobHtml = "";
		for(var i=0;i<data.length;i++){
			orgJobHtml +="<li><a href='javascript:void(0);' onclick='orgJob.changeOrgJob(\""+data[i].orgId+"\",\""+data[i].jobId+"\")'>"+data[i].jobName+"@"+data[i].orgName+"</a></li>";
		} 
		return orgJobHtml;
	},
	changeOrgJob:function(orgId,jobId){
		$.ajax({    
	           type:'get',        
	           url:'${ctx}/workbench/changUserOrgJob.json', 
	           data:"orgId="+orgId+"&jobId="+jobId,
	           dataType:'json', 
	           cache:false, 
	           success:function(data){
	        	   window.location.reload();
	           }    
	    });
	}
	
};
function showPersonInfo(){
	//打开弹出窗口
	var options = {
		dialogId : "personInfoDialog", //指定别名，自定义关闭时需此参数
		dialog : {
			title : "个人中心",
			height : 280,
            width : 550,
            showMax: true, 
			url : ctx+'/workbench/box/personInfo.html?data='+ new Date(),
			buttons: [
                       { text: '关闭', onclick: function (item, dialog) { dialog.close(); } }
                    ]
		},
		callBack : "closepersonInfoDialogCallBack" //自定义窗口关闭回调函数
	};
	openDialog(options);
}
function changePasswd(){
	//打开弹出窗口
	var options = {
		dialogId : "changePasswdDialog", //指定别名，自定义关闭时需此参数
		dialog : {
			title : "修改密码",
			height : 280,
            width : 550,
            showMax: true, 
			url : ctx+'/workbench/box/changePasswd.html?data='+ new Date(),
			buttons: [
			          { text: '保存', onclick: function (item, dialog) { dialog.frame.save();}},
                       { text: '关闭', onclick: function (item, dialog) { dialog.close(); } }
                    ]
		}
		//,callBack : "closechangePasswdDialogCallBack" //自定义窗口关闭回调函数
	};
	openDialog(options);
}
/*=======================登出所有模块操作=========================*/
var logOutModal=null;
var LogoutUtils = {
		appIndex : 0,
		logoutFrame : null,
		logout : function(){
			LogoutUtils.appIndex=0;
			if(!logOutModal){
				logOutModal= $('#logoutmodal').clone().attr("id",'logoutmodal_1');
				$(document.body).append(logOutModal);
				logOutModal=logOutModal.modal('show');
			}else{
				logOutModal.modal('show');
			}
			$(".modal-backdrop").unbind('click');
			
			 for(appName in appCtx){
				iframe = document.createElement("iframe");
				iframe.style.display = "none";
				iframe.onload = function(){
					LogoutUtils.logoutFinishCallback();
			    };
			    iframe.src = appCtx[appName]+'/logout';
				document.body.appendChild(iframe);
			} 
		},
		logoutFinishCallback : function(){
			var i = 0;
			LogoutUtils.appIndex++;
			console.log("logout app:"+LogoutUtils.appIndex);
			if(LogoutUtils.appIndex >= Object.keys(appCtx).length){
				console.log("finished logout!");
				window.location.href=appCtx['aist-sso-web']+'/login?service='+appCtx['sales-web']+'/cas';  
			}
		}
	};
/*========================加载消息::开始=========================*/
var message = {
	getMessage:function(){
	    $.ajax({  
            type : "get",  
            //async : false,  
            url:appCtx['aist-message-web']+"/site/siteIndex.json?page.size=5",  
            dataType : "jsonp",  
            jsonp : "callback", //传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(一般默认为:callback)  
            jsonpCallback : "handler", //自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名，也可以写"?"，jQuery会自动为你处理数据  
            success : function(data) {  
            	 if(data){
            		$("#messageul").empty();
		        	message.createMsgTip(data,$("#messageul"));
	        	 }
            },  
            error : function() {  
                console.error('An error occurred getMessage()');  
            }  
        });  
	} ,
	createMsgTip:function(data,e){
		var fli;
		if(data.totalElements > 0){
			fli=$("<li>").append($("<p>").text("你有"+data.totalElements+"条新的站内信"));
		}else{
			fli=$("<li>").append($("<p>").text("暂时没有新的站内信"));
		}
		$("#sp_badge").text(data.totalElements);
		e.append(fli);
		if(data.content && data.content.length > 0){
			for(var i=0;i<data.content.length;i++){
				var obj = data.content[i];
				var li =this.createMsgLi(obj); 
				e.append(li);
				}
		}
		e.append($("<li>").addClass("external").append($("<a>").attr("href","${ctx}/siteMessage/siteList.html").text("马上去消息中心").append($("<i>").addClass("fa fa-arrow-right"))));
		return e;
	},
	createMsgLi:function(obj){
		
		var li=$("<li>");
		var a=$("<a>").attr("href","javascript:void(0);").attr("onclick","message.readSiteMsg(\""+obj.title+"\",\""+obj.id+"\")");
		li.append(a);
		var span=$("<span>").addClass("photo").append($("<img>").attr("src",'${ctx}/media/image/avatar2.jpg').attr("alt",""));
		a.append(span);
		var spanSubject=$("<span>").addClass("subject")
			.append($("<span>").addClass("from").text(obj.sender))
			.append($("<span>").addClass("time").text(obj.relativeDate));
		var spenMessage=$("<span>").addClass("message").text(obj.title);
		spanSubject.insertAfter(span);
		spenMessage.insertAfter(spanSubject)
		return li;
	},
	readSiteMsg:function(title,id){
		var options = {
		        dialogId : "readSiteMsg", 
		        dialog : {
		            title : title,
		            height : 390,
		            width : 700,
		            url : appCtx['aist-message-web']+"/site/siteDetail.html?id="+id,
		            buttons: [
		               { text: '关闭', onclick: function (item, dialog) { dialog.close(); } }
		            ]
		        }
		    };
		    openDialog(options);
	}
};
$(document).ready(function(){
	orgJob.getOrgJob();	
	message.getMessage();
	setInterval(message.getMessage,1000*60*5);
});

/*========================加载岗位::结束=========================*/
</script>
<div class="modal hide" id="logoutmodal" >
<!--  
    <div class="modal-header">
        &nbsp;<button type="button" class="close" data-dismiss="modal">x</button>
    </div> -->
 
    <div class="modal-body">
     <img alt="" src="${ctx}/images/load.jpg"><span>正在退出</span>
    </div>
 
<!--     <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal">Close</a>
    </div> -->
</div>


<div class="navbar-inner" style="height: 51px;">
	<div class="container-fluid" >

		<!-- BEGIN LOGO -->

		<a class="" href="${ctx}/workspace/dashboard" > <img
			src="${ctx}/images/centaline/cen_logo.gif" width="100" height="43"alt="logo"   />
		</a>
		<!-- END LOGO -->
		<!-- BEGIN RESPONSIVE MENU TOGGLER -->

		<a href="javascript:;" class="btn-navbar collapsed"
			data-toggle="collapse" data-target=".nav-collapse"> <img
			src="${ctx}/media/image/menu-toggler.png" alt="" />
		</a>

		<!-- END RESPONSIVE MENU TOGGLER -->

		<ul class="nav pull-right user">
			<li class="dropdown" id="header_inbox_bar"><a href="#"
				class="dropdown-toggle" data-toggle="dropdown"> <i
					class="icon-envelope"></i> <span class="badge" id="sp_badge">0</span>
			</a>
			<ul class="dropdown-menu extended inbox" id="messageul"></ul>
			</li>
			<!-- BEGIN USER LOGIN DROPDOWN -->
			<li class="dropdown">
					<a href="#" class='dropdown-toggle' data-toggle="dropdown">${sessionUser.realName} - ${sessionUser.serviceJobName}@${sessionUser.serviceDepName}
						<img class="headiconradio" src="${ctx}/media/image/avatar1_small.jpg" alt="">
					</a>
					<ul class="dropdown-menu pull-right">
						<li>
							<a href="#" onclick="showPersonInfo()">个人中心</a>
						</li>
						<li>
							<a href="#" onclick="changePasswd()">密码修改</a>
						</li>
						<li>
							<a href="javascript:void(0);" onclick="LogoutUtils.logout()">退出</a>
						</li>
					</ul>
				</li>
                <li class="dropdown"><a href="#" data-toggle="dropdown" style="height:45px; line-height:28px; padding:10px 12px 6px 12px;">切换岗位</a>
                	<ul id="portalOrgJob" class="dropdown-menu pull-right">
					</ul>
                </li>
			<!-- END USER LOGIN DROPDOWN -->

		</ul>
		<!-- END TOP NAVIGATION MENU -->

	</div>

</div>