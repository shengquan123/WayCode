/**
 * fidorest系统管理初始化
 */
var FidoRestSystem = {
	id : "FidoRestSystemTable", // 表格id
	seItem : null, // 选中的条目
	table : null,
	layerIndex : -1
};

/**
 * 初始化表格的列
 */
FidoRestSystem.initColumn = function() {
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
	}, {
		title : 'id',
		field : 'id',
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
		title : '系统域名',
		field : 'systemDoMain',
		align : 'center',
		valign : 'middle',
		sortable : false
	}, {
		title : '添加时间',
		field : 'addTime',
		align : 'center',
		valign : 'middle',
		sortable : true
	},

	{
		title : '更新时间',
		field : 'updateTime',
		align : 'center',
		valign : 'middle',
		sortable : true
	} ];
	return columns;
}
/**
 * 检查是否选中
 */
FidoRestSystem.check = function() {
	var selected = $('#' + this.id).bootstrapTable('getSelections');
	if (selected.length == 0) {
		Feng.info("请先选中表格中的某一记录！");
		return false;
	} else {
		FidoRestSystem.seItem = selected[0];
		return true;
	}
};

/**
 * 点击添加系统
 */
FidoRestSystem.openAddSystem = function () {
    var index = layer.open({
        type: 2,
        title: '添加系统',
        area: ['800px', '400px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/fidorest/system/add/'
    });
    this.layerIndex = index;
};
/**
 * 点击修改按钮时
 */
FidoRestSystem.openChangeSystem = function() {
	if (this.check()) {
		var index = layer.open({
			type: 2,
            title: '修改系统详情',
            area: ['800px', '400px'], //宽高
            fix: false, //不固定
            maxmin: true,
			content : Feng.ctxPath + '/fidorest/system/edit/' + this.seItem.id
		});
		this.layerIndex = index;
	}
};
FidoRestSystem.search = function() {
	var systemName = $("#systemName").val();
	FidoRestSystem.getSystemList(systemName);
}
/**
 * 删除一条系统记录
 */
FidoRestSystem.delSystem = function() {
	if (this.check()) {
		var operation = function() {
			var systemId = FidoRestSystem.seItem.id;
			var ajax = new $ax(
					url + "/" + systemId, function() {
						Feng.success("删除成功!");
						FidoRestSystem.getSystemList();
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
		url : "../fidorest/app/enviroment",
		success : function(date) {
			/*enviroment = date;
			if(enviroment == "dev") {//测试环境
				url = "http://39.104.18.117:8080/fido-rest/wayfidoapi/v1/system";
			} else if(enviroment == "produce") {
				url = "";
			} else {//默认本地测试环境
				url = "http://localhost:8080/fido-rest/wayfidoapi/v1/system";
			}*/
			url = date.url + "system";
			FidoRestSystem.getSystemList();
		}
	});
});
FidoRestSystem.getSystemList = function(systemName) {
	$.ajax({
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		type : "get",
		data : {
			systemName : systemName
		},
		url : url,
		success : function(date) {
			$.ajax({
				dataType : "json",
				contentType : "application/json",
				type : "post",
				url : "system/list",
				data : JSON.stringify(date.object),
				success : function() {
					var defaultColunms = FidoRestSystem.initColumn();
					var table = new BSTable(FidoRestSystem.id,
							"/fidorest/system/list2", defaultColunms);
					table.setPaginationType("client");
					FidoRestSystem.table = table.init(); // 分页参数在init方法中可设置
					FidoRestSystem.table.refresh();
				}
			});
		}
	});
}