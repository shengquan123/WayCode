$(function() {
	get_user_action();

	// 设置开始/结束时间只读
	$("#beginTime").attr("readonly", "readonly").css("background", "white");
	$("#endTime").attr("readonly", "readonly").css("background", "white");

	$("#beginTime").blur(function() {
		if (!$("#beginTime").val() == "") {
			$("#beginTime").css("border", "");
		}
	});

	$("body").mousemove(function() {
		if (!$("#beginTime").val() == "") {
			$("#beginTime").css("border", "");
		}
		if (!$("#endTime").val() == "") {
			$("#endTime").css("border", "");
		}
	});

});
/**
 * 1.获取实时用户最新行为数据
 */
function get_user_action() {

	$.post(WAY_URL + "statistical/userAction", function(date) {
		if (date.responseStatus == "N") {
			$(".week").html("0");
			$(".month").html("0");
			$(".day_avg").html("-");

			var responsebody = eval("(" + date.responsebody + ")");
			$(responsebody).each(function() {
				if (this.type == "客户端启动的次数") {
					set_user_action(1, this.timeType, this.count);
				}
				if (this.type == "客户端退出的次数") {
					set_user_action(2, this.timeType, this.count);
				}
				if (this.type == "客户端切换帐号登录的次数") {
					set_user_action(3, this.timeType, this.count);
				}
				if (this.type == "指纹登录/解锁点击次数") {
					set_user_action(4, this.timeType, this.count);
				}
				if (this.type == "文件加解密点击次数") {
					set_user_action(5, this.timeType, this.count);
				}
				if (this.type == "网页/应用快捷直达点击次数") {
					set_user_action(6, this.timeType, this.count);
				}
				if (this.type == "应用/网站免密登录点击次数") {
					set_user_action(7, this.timeType, this.count);
				}
				if (this.type == "使用小贴士点击次数") {
					set_user_action(8, this.timeType, this.count);
				}
				if (this.type == "帮助中心点击次数") {
					set_user_action(9, this.timeType, this.count);
				}
			});
		} else {
			alert(date.responseMessage);
			return false;
		}
	});
}

/**
 * 2.根据开始&结束日期搜索该时间段内用户行为数据
 */
function search_user_action() {

	var beginTime = $("#beginTime").val();
	var endTime = $("#endTime").val();
	if (beginTime == "" & endTime == "") {
		$("#beginTime").css("border", "");
		$("#endTime").css("border", "");
		get_user_action();
		return;
	}

	if (beginTime == "") {
		$("#beginTime").css("border", "solid red 1px");
		return;
	}
	if (endTime == "") {
		$("#endTime").css("border", "solid red 1px");
		return;
	}

	$.post(WAY_URL + "statistical/getUserAction?beginTime=" + beginTime
			+ "&endTime=" + endTime, function(date) {
		if (date.responseStatus == "N") {
			var responsebody = eval("(" + date.responsebody + ")");
			$(responsebody).each(function() {
				if (this.type == "客户端启动的次数") {
					set_user_action(1, this.timeType, this.count);
				}
				if (this.type == "客户端退出的次数") {
					set_user_action(2, this.timeType, this.count);
				}
				if (this.type == "客户端切换帐号登录的次数") {
					set_user_action(3, this.timeType, this.count);
				}
				if (this.type == "指纹登录/解锁点击次数") {
					set_user_action(4, this.timeType, this.count);
				}
				if (this.type == "文件加解密点击次数") {
					set_user_action(5, this.timeType, this.count);
				}
				if (this.type == "网页/应用快捷直达点击次数") {
					set_user_action(6, this.timeType, this.count);
				}
				if (this.type == "应用/网站免密登录点击次数") {
					set_user_action(7, this.timeType, this.count);
				}
				if (this.type == "使用小贴士点击次数") {
					set_user_action(8, this.timeType, this.count);
				}
				if (this.type == "帮助中心点击次数") {
					set_user_action(9, this.timeType, this.count);
				}
			});
		} else {
			alert(date.responseMessage);
			return false;
		}
		/**
		 * 搜索后,将日平均数为"-"的栏的值改为0:
		 */
		$(".day_avg").each(function(index, element) {
			if ($(this).html() == "-") {
				$(this).html("0");
			}
		});
	});
}

/**
 * 
 * 用户行为赋值
 */
function set_user_action(type, day, value) {
	if (day == "日") {
		day = "day";
	}
	if (day == "周") {
		day = "week";
	}
	if (day == "月") {
		day = "month";
	}
	if (day == "日平均数") {
		day = "day_rg";
	}
	$("#" + day + "_num_" + type).html(value);
}