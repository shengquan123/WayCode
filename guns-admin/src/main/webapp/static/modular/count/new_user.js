/**
 * 用户列表管理
 */
var MgrUser = {
	id : "managerTable", //表格id
	seItem : null, //选中的条目
	table : null,
	layerIndex : -1,
	deptid : 0
};

/** 
 * 获取实时用户最新数据 
 */
function new_user() {
	$.post(WAY_URL + "statistical/newUserCount", function(date) {
		if (date.responseStatus == "N") {
			var responsebody = eval("(" + date.responsebody + ")");
			$("#userCount").html(responsebody.userCount);
			$("#dayCount").html(responsebody.dayCount);
			$("#dayPercent").html(responsebody.dayPercent);
			$("#weekCount").html(responsebody.weekCount);
			$("#weekPercent").html(responsebody.weekPercent);
			$("#monthCount").html(responsebody.monthCount);
			$("#monthPercent").html(responsebody.monthPercent);
		} else {
			alert(date.responseMessage);
			return false;
		}
	});
}

/**
 * 初始化表格的列
 */
MgrUser.initColumn = function() {
	var columns = [
		{
			title : 'id',
			field : 'userId',
			visible : false,
			align : 'center',
			valign : 'middle'
		},
		{
			title : '帐号',
			field : 'accountName',
			align : 'center',
			valign : 'middle',
			sortable : true
		},
		{
			title : '注册指纹总数量',
			field : 'fingerprintAmountNo',
			align : 'center',
			valign : 'middle',
			sortable : true
		},
		{
			title : '加密文件数量',
			field : 'encryptAmount',
			align : 'center',
			valign : 'middle',
			sortable : true
		},
		{
			title : '直达应用文件数量',
			field : 'quickLaunchAmountNo',
			align : 'center',
			valign : 'middle',
			sortable : true
		},
		{
			title : '短信发送数',
			field : 'messageSendAmount',
			align : 'center',
			valign : 'middle',
			sortable : true
		},{
			title : '邮件发送数',
			field : 'mailSendAmount',
			align : 'center',
			valign : 'middle',
			sortable : true
		},
		{
			title : '注册时间',
			field : 'registerTime',
			align : 'center',
			valign : 'middle',
			sortable : true
		},

		{
			title : '注册至今时间(天)',
			field : 'registerToPresentTime',
			align : 'center',
			valign : 'middle',
			sortable : true
		}];
	return columns;
};


/* 重置 */
MgrUser.resetSearch = function() {
	$("#name").val("");
	$("#beginTime").val("");
	$("#endTime").val("");

	MgrUser.search();
}

/* 给时间和条件赋值 */
MgrUser.search = function() {
	var queryData = {};

	queryData['deptid'] = MgrUser.deptid;
	queryData['name'] = $("#name").val();
	queryData['beginTime'] = $("#beginTime").val();
	queryData['endTime'] = $("#endTime").val();

	MgrUser.table.refresh({
		query : queryData
	});
}

MgrUser.search = function () {
    var queryData = {};

    queryData['key'] = $("#name").val();
    queryData['startTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();

    MgrUser.table.refresh({query: queryData});
}

/**
 * 点击选中
 */
MgrUser.onClickDept = function(e, treeId, treeNode) {
	MgrUser.deptid = treeNode.id;
	MgrUser.search();
};


$(function() {
	var defaultColunms = MgrUser.initColumn();
	var table = new BSTable("managerTable", "/wayapi/v1/accessory/query/userList?key="+$("#name").val()+"&startTime="+$("#beginTime").val()+"&endTime="+$("#endTime").val(), defaultColunms);
	table.setPaginationType("client");
	MgrUser.table = table.init();
	
	var ztree = new $ZTree("deptTree", "/dept/tree");
	ztree.bindOnClick(MgrUser.onClickDept);
	ztree.init();
});