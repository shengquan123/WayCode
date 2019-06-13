/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var SoftInfoDlg = {
    softInfoData: {},
    validateFields: {
    	versionName: {
            validators: {
            	notEmpty: {
            		message: "版本名称不能为空"
            	},
            	stringLength: {
            		max: 20,
                    message: '版本名称长度不能超过20个字符'
                }
            }
        },
        versionNumber: {
            validators: {
            	notEmpty: {
            		message: "版本号不能为空"
            	},
            	digits: {
                    message: '版本号只能是整数'
                },
                lessThan: {
                	value: 9999999999,
                	message: "版本号不能大于9999999999"
                }
            }
        },
        fileCheckCode: {
        	validators: {
        		notEmpty: {
        			message: "请输入文件校验码"
        		},
        		regexp: {
        			regexp: /^[a-fA-F0-9]+$/,
        			message: "校验码格式错误(0-9/a-f/A-F)"
        		},
        		stringLength: {
        			max: 256,
        			message: "文件校验码不能超过256个字符"
        		}
        	}
        },
        fileName: {
        	validators: {
        		notEmpty: {
        			message: "&nbsp;&nbsp上传文件为空"
        		}
        	}
        }
    }
};

var uploadUp;
$(function () {
	/**
	 * 修改选择文件按钮右侧提示信息CSS样式
	 */
    Feng.initValidator("softInfoForm", SoftInfoDlg.validateFields);
    // 初始化头像上传
    uploadUp = new $FileUpload("fileUploadBtn");
    uploadUp.setUploadBarId("progressBar");
    uploadUp.init();
});

/**
 * 当软件类型select值发生改变时,重新初始化文件上传工具类
 */
SoftInfoDlg.changeFunction = function () {
	$("#thelist i").css("display", "none");
	$("#thelist small").css("display", "none");
	$("#fileName").attr("value", "");		// 当软件类型发生改变时,选择的文件清零
	// 获取所选软件类型: 1:客户端App-SAFE	2：客户端App-NORMAL	3:设备固件-SAFE	4:设备固件-NORMAL1	5:设备固件-NORMAL3
	var softType = $("#softType").val();

	if(softType == 1 || softType == 2) {
		uploadUp.extensions = "exe";
		uploadUp.mimeTypes = ".exe";
	}

	if(softType == 3 || softType == 4 || softType == 5) {
		uploadUp.extensions = "bin";
		uploadUp.mimeTypes = ".bin";
	}

	uploadUp.init();
};
/**
 * 清除数据
 */
SoftInfoDlg.clearData = function () {
    this.softInfoData = {};
};
/**
 * 设置对话框中的数据
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SoftInfoDlg.set = function (key, val) {
    this.softInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};
/**
 * 设置对话框中的数据
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SoftInfoDlg.get = function (key) {
    return $("#" + key).val();
};
/**
 * 关闭此对话框
 */
SoftInfoDlg.close = function () {
    parent.layer.close(window.parent.SoftType.layerIndex);
};
/**
 * 收集数据
 */
SoftInfoDlg.collectData = function () {
    // 1.软件类型		2.版本名称	3.版本号		4.文件名(根据后缀名拼接下载路径)
	this.set("softType").set('versionName').set('versionNumber').set('fileName').set('fileCheckCode');
};
/**
 * 验证数据是否为空
 */
SoftInfoDlg.validate = function () {
    $('#softInfoForm').data("bootstrapValidator").resetForm();
    $('#softInfoForm').bootstrapValidator('validate');
    return $("#softInfoForm").data('bootstrapValidator').isValid();
};
/**
 * 提交添加用户
 */
SoftInfoDlg.addSubmit = function () {
    this.clearData();
    this.collectData();
    if (!SoftInfoDlg.validate()) {
    	$("#thelist i").css("top", "-4px");
    	$("#thelist i").css("right", "-70px");
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/version/addSubmit", function (data) {
        Feng.success("添加成功!");
        window.parent.SoftType.table.refresh();
        SoftInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.softInfoData);
    ajax.start();
};

