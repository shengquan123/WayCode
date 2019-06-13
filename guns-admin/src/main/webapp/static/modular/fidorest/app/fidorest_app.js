/**
 * fidorest系统管理初始化
 */
var FidoRestApp = {
	id : "FidoRestAppTable", // 表格id
	seItem : null, // 选中的条目
	table : null,
	layerIndex : -1
};

/**
 * 初始化表格的列
 */
FidoRestApp.initColumn = function() {
	var columns = [ {
		field : 'selectItem',
		radio : true
	}, {
		title : '序号',
		field : 'num',
		align : 'center',
		valign : 'middle',
		formatter : function(value, row, index) {
			return index + 1;
		}
	},{
		title : 'id',
		field : 'id',
		visible : false,
		align : 'center',
		valign : 'middle'
	}, {
		title : 'systemId',
		field : 'systemId',
		visible : false,
		align : 'center',
		valign : 'middle'
	}, {
		title : '系统名称',
		field : 'systemName',
		align : 'center',
		valign : 'middle',
		sortable : false
	}, {
		title : '应用名称',
		field : 'appName',
		align : 'center',
		valign : 'middle',
		sortable : false
	}, {
		title : 'appRestId',
		field : 'appRestId',
		align : 'center',
		valign : 'middle',
		sortable : false
	}, {
		title : 'appRestSecret',
		field : 'appRestSecret',
		align : 'center',
		valign : 'middle',
		sortable : false
	}, {
		title : 'token',
		field : 'token',
		align : 'center',
		valign : 'middle',
		sortable : false
	}, {
		title : '是否可用',
		field : 'status',
		align : 'center',
		valign : 'middle',
		sortable : false
	}, {
		title : '添加时间',
		field : 'addTime',
		align : 'center',
		valign : 'middle',
		sortable : true
	}, {
		title : '更新时间',
		field : 'updateTime',
		align : 'center',
		valign : 'middle',
		sortable : true
	}, {
		title : 'token过期时间',
		field : 'expireTime',
		align : 'center',
		valign : 'middle',
		sortable : true
	}
	
	];
	return columns;
}
/**
 * 检查是否选中
 */
FidoRestApp.check = function() {
	var selected = $('#' + this.id).bootstrapTable('getSelections');
	if (selected.length == 0) {
		Feng.info("请先选中表格中的某一记录！");
		return false;
	} else {
		FidoRestApp.seItem = selected[0];
		return true;
	}
};

/**
 * 点击添加系统
 */
FidoRestApp.openAddSystem = function () {
    var index = layer.open({
        type: 2,
        title: '添加应用',
        area: ['800px', '400px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/fidorest/app/add/'
    });
    this.layerIndex = index;
};
/**
 * 点击修改按钮时
 */
FidoRestApp.openChangeSystem = function() {
	if (this.check()) {
		var index = layer.open({
			type: 2,
            title: '修改应用详情',
            area: ['800px', '400px'], //宽高
            fix: false, //不固定
            maxmin: true,
			content : Feng.ctxPath + '/fidorest/app/edit/' + this.seItem.id
		});
		this.layerIndex = index;
	}
};
/**
 * 更新token
 */
FidoRestApp.updateToken = function() {
	if (this.check()) {
		var operation = function() {
			var id = FidoRestApp.seItem.id;
			var ajax = new $ax(
					url + "/token/" + id, function() {
						Feng.success("更新成功!");
						FidoRestApp.getAppList();
					}, function(data) {
						Feng.error("更新失败!" + data.responseJSON.message + "!");
					});
			ajax.type = "PUT";
			ajax.start();
		};
		Feng.confirm("是否更新所选记录token值?", operation);
	}
}
/**
 * 搜索
 */
FidoRestApp.search = function() {
	var systemName = $("#systemName").val();
	var appName = $("#appName").val();
	FidoRestApp.getAppList(systemName, appName);
}
/**
 * 删除一条系统记录
 */
FidoRestApp.delSystem = function() {
	if (this.check()) {
		var operation = function() {
			var systemId = FidoRestApp.seItem.id;
			var ajax = new $ax(
					url + "/" + systemId, function() {
						Feng.success("删除成功!");
						FidoRestApp.getAppList();
					}, function(data) {
						Feng.error("删除失败!" + data.responseJSON.message + "!");
					});
			ajax.type = "DELETE";
			ajax.start();
		};
		Feng.confirm("是否删除所选记录" + "?", operation);
	}
};

var enviroment;
var url = "";
$(function() {
	$.ajax({
		dataType : "json",
		contentType : "application/json",
		type : "get",
		url : "enviroment",
		success : function(date) {
			url = date.url + "app";
			FidoRestApp.getAppList();
		}
	});
});
FidoRestApp.getAppList = function(systemName, appName) {
	
	$.ajax({
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		type : "get",
		data : {
			systemName : systemName,
			appName : appName
		},
		url : url,
		success : function(date) {
			$.ajax({
				dataType : "json",
				contentType : "application/json",
				type : "post",
				url : "list",
				data : JSON.stringify(date.object),
				success : function() {
					var defaultColunms = FidoRestApp.initColumn();
					var table = new BSTable(FidoRestApp.id,
							"/fidorest/app/list2", defaultColunms);
					table.setPaginationType("client");
					FidoRestApp.table = table.init(); // 分页参数在init方法中可设置
					FidoRestApp.table.refresh();
				}
			});
		}
	});
}