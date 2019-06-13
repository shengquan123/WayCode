/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var SystemInfoDlg = {
	systemInfoData : {},
	validateFields : {
		systemName : {
			validators : {
				notEmpty : {
					message : "系统名称不能为空"
				}
			}
		},
		systemDoMain : {
			validators : {
				notEmpty : {
					message : "系统域名不能为空"
				}
			}
		}
	}
};

var enviroment;
var url = "";
$(function() {
	$.ajax({
		dataType : "json",
		contentType : "application/json",
		type : "get",
		url : "../../app/enviroment",
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
			//修改选择文件按钮右侧提示信息CSS样式
			Feng.initValidator("systemInfoForm", SystemInfoDlg.validateFields);
		}
	});
});

/**
 * 清除数据
 */
SystemInfoDlg.clearData = function() {
	this.systemInfoData = {};
};
/**
 * 设置对话框中的数据
 * 
 * @param key
 *            数据的名称
 * @param val
 *            数据的具体值
 */
SystemInfoDlg.set = function(key, val) {
	this.systemInfoData[key] = (typeof value == "undefined") ? $("#" + key)
			.val() : value;
	return this;
};
/**
 * 设置对话框中的数据
 * 
 * @param key
 *            数据的名称
 * @param val
 *            数据的具体值
 */
SystemInfoDlg.get = function(key) {
	return $("#" + key).val();
};
/**
 * 关闭此对话框
 */
SystemInfoDlg.close = function() {
	parent.layer.close(window.parent.FidoRestSystem.layerIndex);
};
/**
 * 收集数据
 */
SystemInfoDlg.collectData = function() {
	this.set("systemName").set('systemDoMain').set("id");
};

/**
 * 验证数据是否为空
 */
SystemInfoDlg.validate = function() {
	$('#systemInfoForm').data("bootstrapValidator").resetForm();
	$('#systemInfoForm').bootstrapValidator('validate');
	return $("#systemInfoForm").data('bootstrapValidator').isValid();
};
/**
 * 提交添加系统
 */
SystemInfoDlg.addSystem = function() {
	this.clearData();
	this.collectData();
	if (!SystemInfoDlg.validate()) {
		return;
	}

    var ajax = new $ax(url, function (data) {
        Feng.success("添加成功!");
        window.parent.FidoRestSystem.getSystemList();
        SystemInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.systemInfoData);
    ajax.start();
};
/**
 * 提交修改系统
 */
SystemInfoDlg.updateSystem = function() {
	
	// 添加修改应用表中系统信息
	
	this.clearData();
	this.collectData();
	if (!SystemInfoDlg.validate()) {
		return;
	}
    var ajax = new $ax(url, function (data) {
        Feng.success("修改成功!");
        window.parent.FidoRestSystem.getSystemList();
        SystemInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.systemInfoData);
    ajax.type = "PUT";
    ajax.start();
};

