/**
 * 应用详情对话框（可用于添加和修改对话框）
 */
var AppInfoDlg = {
    appInfoData: {},
    systemZtree: null,
    /*pNameZtree: null,*/
    validateFields: {
    	systemName: {
            validators: {
                notEmpty: {
                    message: '系统名称不能为空'
                }
            }
        },
        appName: {
            validators: {
                notEmpty: {
                    message: '应用名称不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
AppInfoDlg.clearData = function () {
    this.appInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AppInfoDlg.set = function (key, val) {
    this.appInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AppInfoDlg.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
AppInfoDlg.close = function () {
    parent.layer.close(window.parent.FidoRestApp.layerIndex);
};

/**
 * 点击部门input框时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
AppInfoDlg.onClickDept = function (e, treeId, treeNode) {
    $("#systemName").attr("value", AppInfoDlg.systemZtree.getSelectedVal());
    $("#systemId").attr("value", treeNode.id);
};
AppInfoDlg.onDblClickDept = function (e, treeId, treeNode) {
    $("#systemName").attr("value", AppInfoDlg.systemZtree.getSelectedVal());
    $("#systemId").attr("value", treeNode.id);
    $("#systemContent").fadeOut("fast");
};

/**
 * 显示部门选择的树
 * @returns
 */
AppInfoDlg.showSystemSelectTree = function () {
    Feng.showInputTree("systemName", "systemContent");
};

/**
 * 收集数据
 */
AppInfoDlg.collectData = function () {
    this.set('id').set('systemName').set('appName').set('systemId');
};

/**
 * 验证数据是否为空
 */
AppInfoDlg.validate = function () {
    $('#appInfoForm').data("bootstrapValidator").resetForm();
    $('#appInfoForm').bootstrapValidator('validate');
    return $("#appInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加应用
 */
AppInfoDlg.addApp = function () {
	
    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    var ajax = new $ax(url, function (data) {
        Feng.success("添加成功!");
        window.parent.FidoRestApp.getAppList();
        AppInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.appInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
AppInfoDlg.updateApp = function () {
    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息-----------------------------------------------------------------------------
    var ajax = new $ax(url, function (data) {
        Feng.success("修改成功!");
        window.parent.FidoRestApp.getAppList();
        AppInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.appInfoData);
    ajax.type = "PUT";
    ajax.start();
};
var enviroment;
var url = "";
$(function () {
	$.ajax({
		dataType : "json",
		contentType : "application/json",
		type : "get",
		url : "../enviroment",
		success : function(date) {
			url = date.url + "app";
			/*enviroment = date;
			if(enviroment == "dev") {//测试环境
				url = "http://39.104.18.117:8080/fido-rest/wayfidoapi/v1/app";
			} else if(enviroment == "produce") {
				url = "";
			} else {//默认本地测试环境
				url = "http://localhost:8080/fido-rest/wayfidoapi/v1/app";
			}*/
		}
	});
    Feng.initValidator("appInfoForm", AppInfoDlg.validateFields);

    var systemTree = new $ZTree("systemTree", "/fidorest/app/tree"); // 这里需要一个controller
    systemTree.bindOnClick(AppInfoDlg.onClickDept);
    systemTree.bindOnDblClick(AppInfoDlg.onDblClickDept);
    systemTree.init();
    AppInfoDlg.systemZtree = systemTree;
});
