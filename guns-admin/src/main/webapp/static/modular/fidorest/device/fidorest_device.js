/**
 * fidorest设备管理初始化
 */
var FidoRestDevice = {
	id : "FidoRestDeviceTable", // 表格id
	seItem : null, // 选中的条目
	table : null,
	layerIndex : -1
};

/**
 * 初始化表格的列
 */
FidoRestDevice.initColumn = function () {
	return [
		      {field : 'selectItem', radio : true},
		      {title : '序号', field : 'num', align : 'center', valign : 'middle',
		    	  formatter : function(value, row, index) {
		    		  			return index + 1;
		    	  			}
		      },
		      {title : 'id', field : 'id', visible : false, align : 'center', valign : 'middle', sortable : true},
		      {title : '用户名', field : 'userName', visible : true, align : 'center', valign : 'middle'},
		      {title : 'rpName', field : 'rpName', align : 'center', valign : 'middle', sortable : false},
		      {title : '设备名称', field : 'deviceName', align : 'center', valign : 'middle', sortable : false},
		      {title : 'publicKeyCose', field : 'publicKeyCose', align : 'center', valign : 'middle', sortable : false},
		      {title : 'credentialId', field : 'credentialId', align : 'center', valign : 'middle', sortable : false},
		      {title : '句柄', field : 'userHandle', align : 'center', valign : 'middle', sortable : false},
		      {title : '认证次数', field : 'signatureCount', align : 'center', valign : 'middle', sortable : false},
		      {title : '添加时间', field : 'registrationTime', align : 'center', valign : 'middle', sortable : false},
		      {title : '最后认证时间', field : 'lastAuthTime', align : 'center', valign : 'middle', sortable : true},
		      {title : '更新时间', field : 'updateTime', align : 'center', valign : 'middle', sortable : false
		    	  //获取日期列的值进行转换
		    	  /*,formatter: function (value, row, index) {
			    	     return changeDateFormat(value)
			      },*/  
		      }
	    ];
};

/**
 * 日期转换函数
 * 将毫秒转换为YYYY-MM-DD HH:mm:ss日期格式
 * @param cellval
 * @returns {String}
 */
function changeDateFormat(cellval) {
	//转换日期格式(时间戳转换为datetime格式)
	    var dateVal = cellval + "";
	    if (cellval != null) {
	        var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
	        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
	        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();

	        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
	        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
	        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

	        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
	    }
}

/**
 * 检查是否选中
 */
FidoRestDevice.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
    	FidoRestDevice.seItem = selected[0];
        return true;
    }
};

/**
 * 查询日志列表
 */
FidoRestDevice.search = function () {
    var queryData = {};

    queryData['systemName'] = $("#systemName").val();
    queryData['addTimeBegin'] = $("#addTimeBegin").val();
    queryData['addTimeEnd'] = $("#addTimeEnd").val();
    
    FidoRestDevice.table.refresh({query: queryData});
};

var enviroment;
var url = "";
$(function () {
	$.ajax({
		dataType : "json",
		contentType : "application/json",
		type : "get",
		url : "app/enviroment",
		success : function(date) {
			url = date.url + "device";
		}
	});

    init();
    var defaultColunms = FidoRestDevice.initColumn();
    var table = new BSTable(FidoRestDevice.id, "/fidorest/device/list2", defaultColunms);
    table.setPaginationType("server");
    FidoRestDevice.table = table.init();
});

/** 删除一条系统记录*/
FidoRestDevice.delDevice = function() {
	if (this.check()) {
		var operation = function() {
			var deviceId = FidoRestDevice.seItem.id;
			var ajax = new $ax(
					url + "/" + deviceId, function() {
						Feng.success("删除成功!");
						FidoRestDevice.getDeviceList();
					}, function(data) {
						Feng.error("删除失败!" + data.responseJSON.message + "!");
					});
			ajax.type = "DELETE";
			ajax.start();
		};
		Feng.confirm("是否删除所选记录" + "?", operation);
	}
};
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