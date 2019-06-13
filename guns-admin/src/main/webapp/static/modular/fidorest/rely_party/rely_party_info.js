/**
 * 应用详情对话框（可用于添加和修改对话框）
 */
var RelyPartyInfoDlg = {
    relyPartyInfoData: {},
    validateFields: {
        rpName: {
            validators: {
                notEmpty: {
                    message: '依赖方名称不能为空'
                }
            }
        },
        rpId: {
            validators: {
                notEmpty: {
                    message: 'rpId不能为空'
                }
            }
        },
        origins: {
            validators: {
                notEmpty: {
                    message: 'origins不能为空'
                }
            }
        }
    }
};

$(function() {
    Feng.initValidator("relyPartyInfoForm", RelyPartyInfoDlg.validateFields);
});

/**
 * 清除数据
 */
RelyPartyInfoDlg.clearData = function () {
    this.relyPartyInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RelyPartyInfoDlg.set = function (key, val) {
    this.relyPartyInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RelyPartyInfoDlg.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
RelyPartyInfoDlg.close = function () {
    parent.layer.close(window.parent.FidoRestRelyParty.layerIndex);
};

/**
 * 收集数据
 */
RelyPartyInfoDlg.collectData = function () {
    this.set('id').set('rpName').set('rpId').set('origins');
};

/**
 * 验证数据是否为空
 */
RelyPartyInfoDlg.validate = function () {
    $('#relyPartyInfoForm').data("bootstrapValidator").resetForm();
    $('#relyPartyInfoForm').bootstrapValidator('validate');
    return $("#relyPartyInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加应用
 */
RelyPartyInfoDlg.addRelyParty = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    var ajax = new $ax("../addRelyParty", function (data) {
        if(data.code == 201) {
            Feng.error("添加失败!  " + data.message);
            return;
        }
        Feng.success("添加成功!");
        window.parent.FidoRestRelyParty.table.refresh();
        RelyPartyInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!");
    });
    ajax.set(this.relyPartyInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
RelyPartyInfoDlg.updateRelyParty = function () {
    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    var ajax = new $ax("../", function (data) {
        if(data.code == 201) {
            Feng.error("修改失败!  " + data.message);
            return;
        }
        Feng.success("修改成功!");
        window.parent.FidoRestRelyParty.table.refresh();
        RelyPartyInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!");
    });
    ajax.set(this.relyPartyInfoData);
    ajax.type = "PUT";
    ajax.start();
};
