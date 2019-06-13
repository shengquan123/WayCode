/**
 * 系统管理--用户管理的单例对象
 */
var MgrUser = {
	id : "managerTable", //表格id
	seItem : null, //选中的条目
	table : null,
	layerIndex : -1,
	deptid : 0
};

/**
 * 初始化表格的列
 */
MgrUser.initColumn = function() {
	var columns = [ {
		title : 'id',
		field : 'userId',
		visible : false,
		align : 'center',
		valign : 'middle',
		sortable : false
	}, {
		title : '帐号',
		field : 'accountName',
		align : 'center',
		valign : 'middle',
		sortable : false
	}, {
		title : '注册指纹总数量',
		field : 'fingerprintAmount',
		align : 'center',
		valign : 'middle',
		sortable : false,
	}, {
		title : '加密文件数量',
		field : 'encryptAmount',
		align : 'center',
		valign : 'middle',
		sortable : false
	}, {
		title : '直达应用文件数量',
		field : 'quickLaunchAmount',
		align : 'center',
		valign : 'middle',
		sortable : false
	}, {
		title : '短信发送数',
		field : 'messageSendAmount',
		align : 'center',
		valign : 'middle',
		sortable : false
	},{
		title : '邮件发送数',
		field : 'mailSendAmount',
		align : 'center',
		valign : 'middle',
		sortable : false
	},{
		title : '注册时间',
		field : 'registerTime',
		align : 'center',
		valign : 'middle',
		sortable : true
	}, {
		title : '注册至今时间(天)',
		field : 'registerToPresentTime',
		align : 'center',
		valign : 'middle',
		sortable : true
	} ];
	return columns;
};

/* 重置 */
MgrUser.resetSearch = function() {
	$("#name").val("");
	$("#beginTime").val("");
	$("#endTime").val("");
	MgrUser.search();
}

/**
 * 检查是否选中
 */
MgrUser.check = function() {
	var selected = $('#' + this.id).bootstrapTable('getSelections');
	if (selected.length == 0) {
		Feng.info("请先选中表格中的某一记录！");
		return false;
	} else {
		MgrUser.seItem = selected[0];
		return true;
	}
};

MgrUser.search = function() {
	var queryData = {};
	queryData['key'] = $("#name").val();
	queryData['startTime'] = $("#beginTime").val();
	queryData['endTime'] = $("#endTime").val();

	MgrUser.table.refresh({
		query : queryData
	});

}

/**
 * 点击选中
 */
MgrUser.onClickDept = function(e, treeId, treeNode) {
	MgrUser.deptid = treeNode.id;
	MgrUser.search();
};

/**
 * 点击查看指纹
 * @param userId 管理员id
 */
MgrUser.openFp = function(id, FindType) {
	var count = '<br /> <br />';
	//快捷
	var quickString = '';
	$
			.post(
					WAY_URL + "query/userChipList?userId=" + id,
					function(date) {
						if (date == null || date == "") {
							alert("该用户无数据");
							return false;
						}
						date = eval("(" + date + ")");
						$(date)
								.each(
										function() {
											//开始
											count += '<div class="row" style="width:900px;">'
													+ '<div class="col-sm-8" style="height:300px;"> '
													+ ' <img alt="" src="../static/img/20170824193425.png" width="500px;" style="position:absolute;" /> ';
											// 手指字符串
											var fpString = '';
											//排序字符串
											var sortString = '';

											// 快捷显示
											quickString = '<div class="row"  style="width:800px;"><div class="col-sm-12" style="padding:20px;"> ';
											if (this.fingerManagerModelList.length > 0) {
												$(this.fingerManagerModelList)
														.each(
																function() {
																	fpString += getFpHtml(this.location);
																	sortString += getSortHtml(
																			this.location,
																			this.addTime);
																	if (this.quickLaunchModel != null) {
																		quickString += '<span>&nbsp;&nbsp;&nbsp;'
																				+ this.quickLaunchModel.url
																				+ '</span><br /><br/>';
																	}
																});
											}
											quickString += '</div></div"> ';
											//手指结束
											count += fpString + '</div>';
											//排序开始
											count += '<div class="col-sm-4"><div class="col-sm-6"></div>'
													+ ' <div class="col-sm-6"><br />注册时间</div><br /><br />'
													+ sortString
													+ '</div></div><br /><br />';
										});
						var title = "";
						//判断是否是快捷
						if (FindType == "0") {
							count = quickString;
							title = "直达列表";
						} else {
							title = "设备指纹列表";
						}
						var index = layer.open({
							type : 1,
							title : title,
							area : [ '1000px', '500px' ], //宽高
							fix : false, //不固定
							maxmin : false,
							content : count
						});
						this.layerIndex = index;
					});

};

/*  */
$(function() {
	var defaultColunms = MgrUser.initColumn();
	var table = new BSTable("managerTable",
			"/wayapi/v1/accessory/query/userList?key=" + $("#name").val()
					+ "&startTime=" + $("#beginTime").val() + "&endTime="
					+ $("#endTime").val(), defaultColunms);
	table.setPaginationType("client");
	MgrUser.table = table.init();
	// 短信条数
	get_user_sms();
	// 邮件条数
	get_user_mailNum();
});

/**
 * 手指html
 */
function getFpHtml(type) {
	if (type == "10") {
		return '<div style="position: absolute;float: left;margin-left:24px;margin-top:60px;">'
				+ '<img src="../static/img/fp333605b.png" style="width: 27px;"/></div>';
	}
	if (type == "9") {
		return '<div style="position: absolute;float: left;margin-left:55px;margin-top:20px;">'
				+ '<img src="../static/img/fp333605b.png" style="width: 27px;"/></div>';
	}
	if (type == "8") {
		return '<div style="position: absolute;float: left;margin-left:95px;margin-top:0px;">'
				+ '<img src="../static/img/fp333605b.png" style="width: 27px;"/></div>';
	}
	if (type == "7") {
		return '<div style="position: absolute;float: left;margin-left:140px;margin-top:20px;">'
				+ '<img src="../static/img/fp333605b.png" style="width: 27px;"/></div>';
	}
	if (type == "6") {
		return '<div style="position: absolute;float: left;margin-left:195px;margin-top:110px;">'
				+ '<img src="../static/img/fp333605b.png" style="width: 27px;"/></div>';
	}
	if (type == "5") {
		return '<div style="position: absolute;float: left;margin-left:280px;margin-top:100px;">'
				+ '<img src="../static/img/fp333605b.png" style="width: 27px;"/></div>';
	}
	if (type == "4") {
		return '<div style="position: absolute;float: left;margin-left:335px;margin-top:20px;">'
				+ '<img src="../static/img/fp333605b.png" style="width: 27px;"/></div>';
	}
	if (type == "3") {
		return '<div style="position: absolute;float: left;margin-left:380px;margin-top:5px;">'
				+ '<img src="../static/img/fp333605b.png" style="width: 27px;"/></div>';
	}
	if (type == "2") {
		return '<div style="position: absolute;float: left;margin-left:420px;margin-top:20px;">'
				+ '<img src="../static/img/fp333605b.png" style="width: 27px;"/></div>';
	}
	if (type == "1") {
		return '<div style="position: absolute;float: left;margin-left:455px;margin-top:50px;">'
				+ '<img src="../static/img/fp333605b.png" style="width: 27px;"/></div>';
	}
}
/**
 * 手指排序html
 */
function getSortHtml(type, time) {
	var count = "";
	if (type == "10") {
		count = "左手小指"
	}
	if (type == "9") {
		count = "左手无名指"
	}
	if (type == "8") {
		count = "左手中指"
	}
	if (type == "7") {
		count = "左手食指"
	}
	if (type == "6") {
		count = "左手拇指"
	}
	if (type == "5") {
		count = "右手拇指"
	}
	if (type == "4") {
		count = "右手食指"
	}
	if (type == "3") {
		count = "右手中指"
	}
	if (type == "2") {
		count = "右手无名指"
	}
	if (type == "1") {
		count = "右手小指"
	}
	return '<br /><br /><div class="col-sm-6">' + count + '</div> '
			+ '<div class="col-sm-6"> <span>' + time + '</span></div>'
	'<br /><br />';
}

/** 
 * 获取用户短信总数
 */
function get_user_sms() {
	$.post(WAY_URL + "query/messageSendInfo", function(date) {
		if (date.responseStatus == "N") {
			$("#sms_num").html(date.responsebody);
		} else {
			alert(date.responseMessage);
			return false;
		}
	});
}
/** 
 * 获取用户邮件总数
 */
function get_user_mailNum() {
	$.post(WAY_URL + "query/mailSendInfo", function(date) {
		if (date.responseStatus == "N") {
			$("#mail_num").html(date.responsebody);
		} else {
			alert(date.responseMessage);
			return false;
		}
	});
}
