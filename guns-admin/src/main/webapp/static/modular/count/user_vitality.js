/**
 * 活跃数据
 */
function get_user_vitality() {
	$.post(WAY_URL + "statistical/userActive?startTime=1997-01-01&endTime=" + dateUtil.getDay() + "&type=1",
		function(date) {
			if (date.responseStatus == "N") {

				var responsebody = eval("(" + date.responsebody + ")");
				$("#day_num").html(responsebody[0].amount);
				$("#day_rg").html(responsebody[0].persent);

				$("#week_num").html(responsebody[1].amount);
				$("#week_rg").html(responsebody[1].persent);

				$("#month_num").html(responsebody[2].amount);
				$("#month_rg").html(responsebody[2].persent);

			} else {
				alert(date.responseMessage);
				return false;
			}
			
		});
}

/**
 * 列表显示排名
 */
function city_sort() {
	$("#city_sort_div").empty();
	$.post(WAY_URL + "statistical/userArea", function(date) {
		if (date.responseStatus == "N") {
			var responsebody = eval("(" + date.responsebody + ")");

			/** 列表 */
			var es = "<table class='table table-striped table-hover table-bordered'>";
			// 计量单位
			var i = 0;
			// 饼状图数据
			var obj = new Array();

			$(responsebody).each(function() {
				//数组
				var city = new Array(2);
				if (i % 4 == 0) {
					es += "<tr>";
				}
				es += "<td>" + this.city + "： &nbsp;&nbsp;" + this.countPercent + "</td>";

				city[0] = this.city;
				city[1] = this.count;
				obj[i] = city;

				i++;
				if (i % 4 == 0) {
					es += "</tr>";
				}
			});
			es += "</table>";
			$("#city_sort_div").append(es);

			/** 饼状图  */
			$('#container').highcharts({
				title : {
					text : ''
				},
				tooltip : {
					headerFormat : '{series.name}<br>',
					pointFormat : '{point.name}: <b>{point.percentage:.1f}%</b>'
				},
				series : [ {
					type : 'pie',
					name : '用户所在地占比',
					data : obj
				} ]
			});

		} else {
			alert(date.responseMessage);
			return false;
		}
	});

}

/**
* 饼状图
*/