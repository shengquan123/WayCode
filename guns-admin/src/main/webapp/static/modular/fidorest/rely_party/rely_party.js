/**
 * fidorest系统管理初始化
 */
var FidoRestRelyParty = {
    id: "FidoRestRelyPartyTable", // 表格id
    seItem: null, // 选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
FidoRestRelyParty.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {
            title: '序号', field: 'num', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return index + 1;
            }
        },
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle', sortable: true},
        {title: '依赖方名称', field: 'rpName', align: 'center', valign: 'middle', sortable: false},
        {title: 'rpId', field: 'rpId', align: 'center', valign: 'middle', sortable: false},
        {title: 'origins', field: 'origins', align: 'center', valign: 'middle', sortable: false},
        {title: 'appId', field: 'appId', visible: false, align: 'center', valign: 'middle', sortable: false},
        {title: 'token值', field: 'token', align: 'center', valign: 'middle', sortable: false},
        {title: '添加时间', field: 'addTime', align: 'center', valign: 'middle', sortable: true},
        {title: '过期时间', field: 'expireTime', align: 'center', valign: 'middle', sortable: false},
        {title: '更新时间', field: 'updateTime', align: 'center', valign: 'middle', sortable: true}
    ];
    return columns;
}
/**
 * 检查是否选中
 */
FidoRestRelyParty.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        FidoRestRelyParty.seItem = selected[0];
        return true;
    }
};

/**
 * 更新Token
 */
FidoRestRelyParty.openChangeRelyPartyToken = function() {
    if (this.check()) {
        var operation = function() {
            var rpName = FidoRestRelyParty.seItem.rpName;
            var ajax = new $ax(
                url + "token", function() {
                    Feng.success("更新成功!");
                    FidoRestRelyParty.table.refresh();
                }, function(data) {
                    Feng.error("更新失败!");
                    console.log(JSON.stringify(data));
                });
            ajax.set("rpName", rpName);
            ajax.type = "PUT";
            ajax.start();
        };
        Feng.confirm("是否更新所选记录token值?", operation);
    }
}

/**
 * 点击添加系统
 */
FidoRestRelyParty.openAddRelyParty = function () {
    var index = layer.open({
        type: 2,
        title: '添加系统',
        area: ['800px', '400px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/fidorest/rely_party/add/'
    });
    this.layerIndex = index;
};
/**
 * 点击修改按钮时
 */
FidoRestRelyParty.openChangeRelyParty = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改系统详情',
            area: ['800px', '400px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/fidorest/rely_party/edit/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 查询rp列表
 */
FidoRestRelyParty.search = function () {
    var queryData = {};
    var rpName = $("#rpName").val();
    if(rpName == "") {
        return;
    }
    queryData['rpName'] = rpName;
    FidoRestRelyParty.table.refresh({query: queryData});
};
/**
 * 删除一条系统记录
 */
FidoRestRelyParty.delRelyParty = function () {
    if (this.check()) {
        var operation = function () {
            var rpId = FidoRestRelyParty.seItem.id;
            var ajax = new $ax(
                "rely_party/" + rpId, function () {
                    Feng.success("删除成功!");
                    FidoRestRelyParty.table.refresh();
                }, function (data) {
                    Feng.error("删除失败! -"+ JSON.stringify(data));
                });
            ajax.type = "DELETE";
            ajax.start();
        };
        Feng.confirm("是否删除所选记录" + "?", operation);
    }
};

var enviroment;
var url = "";
$(function () {
    $.ajax({
        dataType: "json",
        contentType: "application/json",
        type: "get",
        url: "app/enviroment",
        success: function (date) {
            url = date.url;
        }
    });

    init();
    var defaultColunms = FidoRestRelyParty.initColumn();
    var table = new BSTable(FidoRestRelyParty.id, "/fidorest/rely_party/list", defaultColunms);
    table.setPaginationType("server");
    FidoRestRelyParty.table = table.init();
});

function init() {
    var BootstrapTable = $.fn.bootstrapTable.Constructor;
    BootstrapTable.prototype.onSort = function (event) {
        var $this = event.type === "keypress" ? $(event.currentTarget) : $(event.currentTarget).parent(),
            $this_ = this.$header.find('th').eq($this.index()),
            sortName = this.header.sortNames[$this.index()];
        this.$header.add(this.$header_).find('span.order').remove();
        if (this.options.sortName === $this.data('field')) {
            this.options.sortOrder = this.options.sortOrder === 'asc' ? 'desc' : 'asc';
        } else {
            this.options.sortName = sortName || $this.data('field');
            this.options.sortOrder = $this.data('order') === 'asc' ? 'desc' : 'asc';
        }
        this.trigger('sort', this.options.sortName, this.options.sortOrder);
        $this.add($this_).data('order', this.options.sortOrder);
        // Assign the correct sortable arrow
        this.getCaret();
        if (this.options.sidePagination === 'server') {
            this.initServer(this.options.silentSort);
            return;
        }
        this.initSort();
        this.initBody();
    };
    BootstrapTable.prototype.getCaret = function () {
        var that = this;
        $.each(this.$header.find('th'), function (i, th) {
            var sortName = that.header.sortNames[i];
            $(th).find('.sortable').removeClass('desc asc').addClass((sortName || $(th).data('field')) === that.options.sortName ? that.options.sortOrder : 'both');
        });
    };
}