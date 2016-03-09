var orgJob = {
	getOrgJob : function() {
		$.ajax({
			type : 'get',
			url : ctx + '/workbench/listUserOrgJob.json',
			dataType : 'json',
			cache : false,
			success : function(data) {
				if (data) {
					$("#portalOrgJob").append(orgJob.createOrgJob(data));
				}
			}
		});
	},
	createOrgJob : function(data) {
		var orgJobHtml = "";
		for (var i = 0; i < data.length; i++) {
			orgJobHtml += "<li><a href='javascript:void(0);' onclick='orgJob.changeOrgJob(\""
					+ data[i].orgId
					+ "\",\""
					+ data[i].jobId
					+ "\")'>"
					+ data[i].jobName + "@" + data[i].orgName + "</a></li>";
		}
		return orgJobHtml;
	},
	changeOrgJob : function(orgId, jobId) {
		$.ajax({
			type : 'get',
			url : ctx + '/workbench/changUserOrgJob.json',
			data : "orgId=" + orgId + "&jobId=" + jobId,
			dataType : 'json',
			cache : false,
			success : function(data) {
				window.location.reload();
			}
		});
	}

};

var logOutModal = null;
var LogoutUtils = {
	appIndex : 0,
	logoutFrame : null,
	appLogout : {},
	logout : function() {
		LogoutUtils.appIndex = 0;
		if (!logOutModal) {
			logOutModal = $('#logoutmodal').clone().attr("id", 'logoutmodal_1');
			$(document.body).append(logOutModal);
			logOutModal = logOutModal.modal('show');
		} else {
			logOutModal.modal('show');
		}
		$(".modal-backdrop").unbind('click');
		LogoutUtils.appLogout['sales-web'] = appCtx['sales-web'];
		LogoutUtils.appLogout['aist-message-web'] = appCtx['aist-message-web'];
		LogoutUtils.appLogout['aist-uam-web'] = appCtx['aist-uam-web'];
		LogoutUtils.appLogout['aist-portal-web'] = appCtx['aist-portal-web'];
		LogoutUtils.appLogout['trade-web'] = appCtx['trade-web'];

		for (appName in LogoutUtils.appLogout) {
			iframe = document.createElement("iframe");
			iframe.style.display = "none";
			iframe.onload = function() {
				LogoutUtils.logoutFinishCallback();
			};
			iframe.src = LogoutUtils.appLogout[appName] + '/logout';
			document.body.appendChild(iframe);
		}
	},
	logoutFinishCallback : function() {
		LogoutUtils.appIndex++;
		console.log("logout app:" + LogoutUtils.appIndex);
		if (LogoutUtils.appIndex >= Object.keys(LogoutUtils.appLogout).length) {
			console.log("finished logout!");
			window.location.href = appCtx['aist-sso-web'] + '/login?service='
					+ appCtx['trade-web'] + '/cas';
		}
	}
};

/* ========================加载消息::开始========================= */
var message = {
	getMessage : function() {
		$.ajax({
			type : "get",
			// async : false,
			url : appCtx['aist-message-web']
					+ "/site/siteIndex.json?page.size=5",
			dataType : "jsonp",
			jsonp : "callback", // 传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(一般默认为:callback)
			jsonpCallback : "handler", // 自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名，也可以写"?"，jQuery会自动为你处理数据
			success : function(data) {
				if (data) {
					$("#messageul").empty();
					message.createMsgTip(data, $("#messageul"));
				}
			},
			error : function() {
				console.error('An error occurred getMessage()');
			}
		});
	},
	createMsgTip : function(data, e) {
		e.empty();

		$("#sp_badge").text(data.totalElements);
		if (data.content && data.content.length > 0) {
			for (var i = 0; i < data.content.length; i++) {
				var obj = data.content[i];
				var li = this.createMsgLi(obj);
				e.append(li);
				e.append($("<div>").addClass("divider"));
			}
		} else {
			var obj = {
				content : "&nbsp;&nbsp;暂无未读消息"
			};
			var li = this.createMsgLi(obj);
			e.append(li);
			e.append($("<div>").addClass("divider"));
		}

		e.append($("<li>").addClass("text-center link-block")
				.append($("<div>")).append(
						$("<a>").attr({
							href : ctx + "/message/box/siteList.html",
							target : "messageContent"
						}).append($("<i>").addClass("fa fa-envelope")).append(
								$("<strong>").text("去消息中心"))));
		return e;
	},
	createMsgLi : function(obj) {
		imgUrl = "http://img.sh.centanet.com/shanghai/staticfile/agent/agentphoto/"
				+ obj.employeeCode + ".jpg";
		var defImg = ctx + "/img/a5.png";
		var li = $("<li>");
		var messageBox = $("<div>").addClass("dropdown-messages-box");
		var mediaBody = $("<div>").addClass("media-body");
		var t = $("<strong>").text(obj.title);
		// var c=$("<strong>").text(obj.centent);
		var small = $("<small>").addClass("text-muted").text(obj.senderTime);
		imgSpan=$("<span>").addClass("shead");
	    var img = $('<img>').addClass('img-circle himg').attr('src', imgUrl).load(function() {
	          this.parentNode.style.backgroundImage="url("+this.src+")";
		});
	    imgSpan.append(img);
		messageBox.append($('<a>').addClass('pull-left').append(imgSpan));
		messageBox.append(mediaBody.append(t).append($("<br>")).append(
				obj.content).append($("<br>")).append(small));
		li.append(messageBox);
		return li;
	},
	readSiteMsg : function(title, id) {
		var options = {
			dialogId : "readSiteMsg",
			dialog : {
				title : title,
				height : 390,
				width : 700,
				url : appCtx['aist-message-web'] + "/site/siteDetail.html?id="
						+ id,
				buttons : [ {
					text : '关闭',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ]
			}
		};
		openDialog(options);
	}
};