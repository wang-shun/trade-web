function getweeks(year, month,day) {
    var d = new Date();
    // what day is first day
    d.setFullYear(year, month-1, 1);
    var w1 = d.getDay();
    if (w1 == 0) w1 = 7;
    // total day of month
    d.setFullYear(year, month, day);
    var dd = d.getDate();
    // first Monday
    if (w1 != 1) d1 = -(w1-1);
    else d1 = 1;
    week_count = Math.ceil((dd+7-d1+1)/7);
    var weeks=[];
    for (var i = 0; i < week_count; i++) {
        var monday = d1+5+i*7;
        var sunday = monday + 6;
        if(monday!=1){
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
function getspanHtml(year, month,day){
	var weeks=getweeks(year, month,day)
	var spanhtml="";
		for(var i=0;i<weeks.length;i++){
			spanhtml+="<span value="+i+">"+weeks[i]+"</span>";	
		}
		return spanhtml;
}