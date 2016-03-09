var events = [];
var Calendar = function () {
    return {
        //main function to initiate the module
        init: function (ctx) {
          /*  App.addResponsiveHandler(function () {
                Calendar.initCalendar(ctx);
            });

            $('.page-sidebar .sidebar-toggler').click(function () {
                Calendar.initCalendar(ctx);
            });*/

        	events = [];
            Calendar.initCalendar(ctx);
            Calendar.initTimeLine(ctx);
        },

        initCalendar: function (ctx) {
            if (!jQuery().fullCalendar) {
                return;
            }
            var date = new Date();
            var d = date.getDate();
            var m = date.getMonth();
            var y = date.getFullYear();
            var h = {};
            if (App.isRTL()) {
                 if ($('#calendar').parents(".portlet").width() <= 720) {
                    $('#calendar').addClass("mobile");
                    h = {
                        right: 'title, prev, next',
                        center: '',
                        right: 'agendaDay, agendaWeek, month'
                    };
                } else {
                    $('#calendar').removeClass("mobile");
                    h = {
                        right: 'title',
                        center: '',
                        left: 'agendaDay, agendaWeek, month,prev,next'
                    };
                }                
            } else {
                 if ($('#calendar').parents(".portlet").width() <= 720) {
                    $('#calendar').addClass("mobile");
                    h = {
                        left: 'title, prev, next',
                        center: '',
                        right: 'month,agendaWeek,agendaDay'
                    };
                } else {
                    $('#calendar').removeClass("mobile");
                    h = {
                        left: 'title',
                        center: '',
                        right: 'prev,next,month,agendaWeek,agendaDay'
                    };
                }
            }
           

            var initDrag = function (el) {
                // create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
                // it doesn't need to have a start or end
                var eventObject = {
                    title: $.trim(el.text()) // use the element's text as the event title
                };
                // store the Event Object in the DOM element so we can get to it later
                el.data('eventObject', eventObject);
                // make the event draggable using jQuery UI
                el.draggable({
                    zIndex: 999,
                    revert: true, // will cause the event to go back to its
                    revertDuration: 0 //  original position after the drag
                });
            };

            var addEvent = function (title) {
                title = title.length == 0 ? "Untitled Event" : title;
                var html = $('<div class="external-event label">' + title + '</div>');
                jQuery('#event_box').append(html);
                initDrag(html);
            };

            $('#external-events div.external-event').each(function () {
                initDrag($(this));
            });

            $('#event_add').unbind('click').click(function () {
                var title = $('#event_title').val();
                addEvent(title);
            });
            //predefined events
            $('#event_box').html("");
           
            $('#calendar').fullCalendar('destroy'); // destroy the calendar
            //获取我的约看列表
            $.ajax({
        		url:ctx+'/custlook/custLookPlan/getMycustLookPlanList',
        		data: "",
        		type: "post",
        		dataType: "json",
        		async : false,
        		success: function(data) {
        			if (data == null)
        				return;
        			for (var i = 0; i < data.length; i++) {	
        				events.push({
        					id: data[i].pkid,
        					title:"约看时间:"+data[i].startDate+"~"+data[i].endDate+"\n客户名称:"+data[i].planDirection,
        					start:data[i].startDate,
        					end:data[i].endDate,
        					allDay: false, 
        					className: ""
        				});
        			}
        		}
            });
            
            $('#calendar').fullCalendar({ //re-initialize the calendar
                header: h,
                slotMinutes: 15,
                editable: true,
                droppable: true, 
                events:events,// this allows things to be dropped onto the calendar !!!
                drop: function (date, allDay) { // this function is called when something is dropped
                    // retrieve the dropped element's stored Event Object
                    var originalEventObject = $(this).data('eventObject');
                    // we need to copy it, so that multiple events don't have a reference to the same object
                    var copiedEventObject = $.extend({}, originalEventObject);
                    // assign it the date that was reported
                    copiedEventObject.start = date;
                    copiedEventObject.allDay = allDay;
                    copiedEventObject.className = $(this).attr("data-class");

                    // render the event on the calendar
                    // the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
                    $('#calendar').fullCalendar('renderEvent', copiedEventObject, true);

                    // is the "remove after drop" checkbox checked?
                    if ($('#drop-remove').is(':checked')) {
                        // if so, remove the element from the "Draggable Events" list
                        $(this).remove();
                    }
                },
                eventClick: function(event, jsEvent, view) {
                	//弹出查看我的约看页面 操作【删除/编辑/关闭】
                	var id = event.id;//获取约看的编号
                	$('#calendar_id').attr("href",ctx+'/custlook/custShow/box/toLookPlanHouEdit?custlookid='+id);
            		$("#calendar_id").trigger('click');
                },
                eventDrop: function(event,dayDelta,minuteDelta,allDay,revertFunc,jsEvent, ui, view ) {
                    if (confirm("确定要修改吗?")) {
                    	 var start = event.start;//开始时间
                    	 var end = event.end;//结束时间
                    	 var testDate = new Date(start);  
                    	 var testDate2 = new Date(end);  
                    	 var startDate = testDate.pattern("yyyy-MM-dd HH:mm");
                    	 var endDate = testDate2.pattern("yyyy-MM-dd HH:mm");
                    	 $.ajax({
                 			cache : true,
                 			type : "POST",
                 			url : ctx+'/custlook/custLookPlan/EditLookPlanById',
                 			async : false,
                 			dataType : "json",
                 			data:[ 
                 			   {name : 'pkid',value : event.id},
                 			   {name : 'startDate',value : startDate},
                 			   {name : 'endDate',value : endDate}
                 			   ],
                 			success : function(data) {
                 				alert(data.message);  
                 				//刷新当前的日历
                 				Calendar.init(ctx);
                 				//刷新时间轴
                 				parent.$.fancybox.close();
                 			},
                 			error : function(data) {
                 				Calendar.init(ctx);
                 				alert(data.message);  
                 			}
                 		});
                    	 
                    }else{
                    	revertFunc();
                    }
                }
            });
            $('.fc-button-today').click(function() {
                $('#calendar').fullCalendar('today');
            });

        },
        initTimeLine : function (ctx) {
        	$.ajax({
     			cache : true,
     			type : "POST",
     			url:ctx+'/custlook/custLookPlan/getMycustLookPlanList',
     			async : false,
     			dataType : "json",
     			data:"",
     			success : function(data) {
     				//填充时间轴
     				if (data == null){
    					return;
     				}
     				$("#timeline").empty();
    				   var classcolor="";
    			   for (var i = 0; i < data.length; i++) {
    				   //给时间控件赋值
    				   if(i%6==0){
    					   classcolor="timeline-yellow";
    				   }else if(i%6==1){
    					   classcolor="timeline-blue";
    				   }else if(i%6==2){
    					   classcolor="timeline-green";
    				   }else if(i%6==3){
    					   classcolor="timeline-purple";
    				   }else if(i%6==4){
    					   classcolor="timeline-red";
    				   }else{
    					   classcolor="timeline-grey";
    				   }
    				   var li = "<li class='"+classcolor+"'><div class=\"timeline-time\"><span class=\"date\">"+data[i].startDate+"</span><span class=\"time\" style=\"font-size:12px;\">"+data[i].endDate+"</span></div><div class=\"timeline-icon\"><i class=\"icon-comments\"></i></div><div class=\"timeline-body\"><h2>注意事项</h2><div class=\"timeline-content\">"+data[i].impInfo+"</div><div class=\"#\" class=\"nav-link\" style='cursor:pointer' onclick='ReplaceHref(\""+data[i].pkid+"\",\""+ctx+"\")'>详情<i class=\"m-icon-swapright m-icon-white\"></i> </a></div></div></li>";
    		    	   $("#timeline").append(li);
    			   } 
     			},
     			error : function(data) {
     				alert(data.message);  
     			}
     		});
        }

    };

}();

function ReplaceHref(id,ctx){
	$.fancybox.open({ 
		  type : 'iframe',
		  'href':ctx+'/custlook/custShow/box/toLookPlanHouEdit?custlookid='+id,
              maxWidth: 1500,
			  maxHeight: 600,
			  padding : 0,
			  margin : 0,
			  autoScale : false,
			  fitToView : false,
			  width : '100%',
			  height : '70%',
			  autoDimensions : true,
			  closeClick : false,
			  iframe : {
				preload : false
			  },
			  openEffect : 'none',
			  closeEffect : 'none',
			  afterClose : function() {
				  //调用刷新日历
				  Calendar.init(ctx);
			}
	});
}
//格式化日期
Date.prototype.pattern=function(fmt) {         
    var o = {         
    "M+" : this.getMonth()+1, //月份         
    "d+" : this.getDate(), //日         
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时         
    "H+" : this.getHours(), //小时         
    "m+" : this.getMinutes(), //分         
    "s+" : this.getSeconds(), //秒         
    "q+" : Math.floor((this.getMonth()+3)/3), //季度         
    "S" : this.getMilliseconds() //毫秒         
    };         
    var week = {         
    "0" : "/u65e5",         
    "1" : "/u4e00",         
    "2" : "/u4e8c",         
    "3" : "/u4e09",         
    "4" : "/u56db",         
    "5" : "/u4e94",         
    "6" : "/u516d"        
    };         
    if(/(y+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
    }         
    if(/(E+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);         
    }         
    for(var k in o){         
        if(new RegExp("("+ k +")").test(fmt)){         
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
        }         
    }         
    return fmt;         
};       