/**
 * 版本管理初始化
 */
var SoftType = {
    id: "SoftTypeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SoftType.initColumn = function () {
	return [
	     {field: 'selectItem', radio: true},
	     {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
	     {title: '软件类型', field: 'type', align: 'center', valign: 'middle'},
	     {title: '版本名称', field: 'version_name', align: 'center', valign: 'middle'},
	     {title: '版本号', field: 'version_number', align: 'center', valign: 'middle', sortable: true},
	     {title: '文件校验码', field: 'file_checkcode', align: 'center', valign: 'middle'},
	     {title: '下载地址', field: 'download_path', align: 'center', valign: 'middle'},
	     {title: '上传发布时间', field: 'publish_time', align: 'center', valign: 'middle', sortable: true}];
}

/**
 * 检查是否选中
 */
SoftType.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        SoftType.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象(获取软件类型)
 * @returns {{}}
 */
SoftType.formParams = function () {
	var queryData = {};
	queryData['softType'] = $("#softType").val();
	return queryData;
}
/**
 * 删除一条版本记录
 */
SoftType.delSoft = function () {
    if (this.check()) {
        var operation = function(){
            var versionId = SoftType.seItem.id;
            var ajax = new $ax(Feng.ctxPath + "/version/delete", function () {
                Feng.success("删除成功!");
                SoftType.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("versionId", versionId);
            ajax.start();
        };
        Feng.confirm("是否删除所选记录" /*+ SoftType.seItem.id*/ + "?",operation);
    }
};
/**
 * 根据软件类型参数获取版本列表
 */
SoftType.search = function () {
    SoftType.table.refresh({query: SoftType.formParams()});
};

$(function () {
    var defaultColunms = SoftType.initColumn();
    var table = new BSTable(SoftType.id, "/version/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(SoftType.formParams());
    SoftType.table = table.init();	// 分页参数在init方法中可设置
});

/**
 * 点击添加软件
 */
SoftType.addSoft = function () {
    var index = layer.open({
        type: 2,
        title: '添加软件',
        area: ['800px', '560px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/version/version_add'
    });
    this.layerIndex = index;
};