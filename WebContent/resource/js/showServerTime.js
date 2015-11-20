function showServerTime(showElement,time){
	var date = new Date(Number(time));  //时间
	var year = date.getFullYear();//年
	var month = date.getMonth() + 1; //月
	month = month < 10 ? ("0" + month) : month;
	var day = date.getDate(); //日
	day = day < 10 ? ("0" + day) : day;
	var hour = date.getHours();//时
	hour = hour < 10 ? ("0" + hour) : hour;
	var minute = date.getMinutes();//分
	minute = minute < 10 ? ("0" + minute) : minute;
	var second = date.getSeconds(); //秒
	second = second < 10 ? ("0" + second) : second;
	var weekday=new Array(7)
	weekday[0]="星期日";
	weekday[1]="星期一";
	weekday[2]="星期二";
	weekday[3]="星期三";
	weekday[4]="星期四";
	weekday[5]="星期五";
	weekday[6]="星期六";
	var week = weekday[date.getDay()];
	var dateStr = " " + year + "年" + month + "月" + day + "日  " + hour + "时" + minute + "分" + second + "秒  " + week;
	$(showElement).text(dateStr);
	setTimeout("showServerTime('" + showElement + "','" + (date.getTime() + 1000) + "')",1000);
}