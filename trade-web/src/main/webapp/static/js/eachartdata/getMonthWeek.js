//得到整周并生产span
function getSpanHtml(year, month,day){
	var weeks=getWeeks(year, month,day)
	var spanhtml="";
	for(var i=0;i<weeks.length;i++){
		spanhtml+="<span value="+i+">"+weeks[i]+"</span>";	
	}
	return spanhtml;
}

//获取本月当前日期前(包括今天)的所有整周
function getWeeks(year, month,day) {
    var d = new Date();
    //选择的时间
    d.setFullYear(year, month-1, 1);
    var w1 = d.getDay();//1号周几
    if (w1 == 0) w1 = 7;
    d.setFullYear(year, month,day);
    var dd = d.getDate();//获取天数
    // 
    if  (w1 >=5) d1 =w1-5; //第一周差的天数
    else d1=7+w1-5
    week_count = Math.ceil((dd+d1)/7);//总共几周
    var weeks=[];
    for (var i = 0; i < week_count; i++) {
        var monday = -d1+1+i*7;
        var sunday = monday + 6;
        if(monday!=5){
        	d.setFullYear(year, month-1, monday);
        	from =d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
        }else{
        	var from = year+"-"+month+"-"+monday;	
        }
        var to;
        if (sunday <= dd) {
            to = year+"-"+month+"-"+sunday;
        } else {
           break;
        }
        weeks.push(from+"至"+to);
        }
        return weeks;
    }

//将形如2017-2-17至2017-2-23 转换为  20170217至20170223 方便sql传参
function convertWeekForSql(week){
	var paramWeekArr = [];
	var startWeekDay = week.split("至")[0];
	var endWeekDay = week.split("至")[1];
	var startWeekDay_ = startWeekDay.substr(0,4);
	var endWeekDay_ = endWeekDay.substr(0,4);

	startWeekDay_ += (startWeekDay.split("-")[1].length == 1?"0"+startWeekDay.split("-")[1]:startWeekDay.split("-")[1])
	              + (startWeekDay.split("-")[2].length == 1?"0"+startWeekDay.split("-")[2]:startWeekDay.split("-")[2]);
    endWeekDay_   += (endWeekDay.split("-")[1].length == 1?"0"+endWeekDay.split("-")[1]:endWeekDay.split("-")[1])
	              + (endWeekDay.split("-")[2].length == 1?"0"+endWeekDay.split("-")[2]:endWeekDay.split("-")[2]);

	paramWeekArr[0] = startWeekDay_;
	paramWeekArr[1] = endWeekDay_;
	return paramWeekArr;
}

/**
 * 获取最小周
 */
function getMinWeek(week){
	var _week = [];
	
	if(week[0] < 20170217){
		_week[0] = 20170217;
		_week[1] = 20170223;
		
		return _week;
	}
		return week;
}

/**
 *	获取本月第一天和最后一天
 */
function getFirstAndLastDay(year,month) {         
     var new_year = year;    //取当前的年份          
     var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）          
     if(month>12) {         
      new_month -=12;        //月份减          
      new_year++;            //年份增          
     }         
     var first_day = new Date(new_year,new_month,1);            //取当年当月中的第一天          
     var last_day = new Date(first_day.getTime()-1000*60*60*24);//获取当月最后一天日期 
     first_day.setMonth(first_day.getMonth()-1);
     var sectionMap = [];
     sectionMap.push(String(first_day.getFullYear())+String((first_day.getMonth()+1)<9?"0"+(first_day.getMonth()+1):(first_day.getMonth()+1))+String(first_day.getDate()<9?"0"+first_day.getDate():first_day.getDate()));
     sectionMap.push(String(last_day.getFullYear())+String((last_day.getMonth()+1)<9?"0"+(last_day.getMonth()+1):(last_day.getMonth()+1))+String(last_day.getDate()<9?"0"+last_day.getDate():last_day.getDate()));
     
     return sectionMap;
} 

/**
 *
 * @param year
 * @param month
 * @param day
 * @returns {Array}
 * @author gongjd
 * @for @TEST FUNC
 *
 * function getWeeks(year, month,day) {
    var d1 = new Date();
    // what day is first day
    d1.setFullYear(year, month-1, 1);
    var _start = d1;
    var startDate = _start.getDate();
    var startWeekDay = _start.getDay();
    //找到之后第一个周四:周日、1、2、3、4取本周周四，周5、6取下周四
    if(startWeekDay == 0 || startWeekDay == 1 || startWeekDay == 2 || startWeekDay == 3 || startWeekDay == 4){
        _start.setDate(_start.getDate() - startWeekDay + 4);
    }else if(startWeekDay == 5 || startWeekDay == 6){
        _start.setDate(_start.getDate() - startWeekDay + 4 + 7);
    }
    var d2 = new Date();
    d2.setFullYear(year, month-1, day);
    var _end = d2;
    var endDate = _end.getDate();

    var thursdayDay = _start.getDate();
    _start.setDate(_start.getDate() - 6);
    var _friday = _start;

    var weeks=[];
    var from,to;
    for (var i = 0; i < 6; i++) {
        if(i == 0){
            from = _friday.getFullYear() + "-" + (_friday.getMonth()+1) + "-" + _friday.getDate();
            to = year + "-" + month + "-" + thursdayDay;
        }else if(thursdayDay <= endDate){
            from =year + "-" + month + "-" + (thursdayDay - 6);
            to = year + "-" + month + "-" + thursdayDay;
        }else{
            break;
        }
        weeks.push(from+"至"+to);

        thursdayDay = thursdayDay + 7;
    }
    return weeks;
}
 */
