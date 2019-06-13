/**
 * 上传文件工具类
 * @author shengquan
 */
(function() {
	var $FileUpload = function(fileId) {
		this.fileId = fileId;
		this.uploadBtnId = fileId + "BtnId";
		this.uploadPreId = fileId + "PreId";
		// /version/upload?prefix=app
		this.uploadUrl = Feng.ctxPath + '/version/upload';	//上传的文件交给哪个URL处理(Controller)
		this.fileSizeLimit = 100 * 1024 * 1024;
		this.extensions = "exe";
		this.mimeTypes = ".exe";
	};
	
	$FileUpload.prototype = {
		/**
		 * 初始化fileUploader
		 */
		init : function () {
			var $ = jQuery,
		        $list = $('#thelist'),
		        $btn = $('#ctlBtn'),
		        state = 'pending';
			var uploader = this.create();
			this.bindEvent(uploader);
			return uploader;
		},
	
		/**
		 * 创建fileUploader对象
		 */
		create : function () {
			var fileUploader = WebUploader.create({
				auto : true,	// {Boolean} [可选] [默认值：false] 设置为 true 后，不需要手动调用上传，有文件选择即开始上传。
				pick : {		// {Selector, Object} [可选] [默认值：undefined] 指定选择文件的按钮容器，不指定则不创建按钮。
					id : '#' + this.uploadBtnId,	// {Seletor|dom} 指定选择文件的按钮容器，不指定则不创建按钮。注意:这里虽然写的是 id, 但是不是只支持 id, 还支持 class, 或者 dom 节点。
					multiple : false,// 只上传一个	// {Boolean} 是否开起同时选择多个文件能力。
					innerHTML : '选择文件呗',			// {String} 指定按钮文字。不指定时优先从指定的容器中看是否自带文字。
				},
				accept : {		// {Arroy} [可选] [默认值：null] 指定接受哪些类型的文件。 由于目前还有ext转mimeType表，所以这里需要分开指定。
					title : 'Images',	// {String} 文字描述
					extensions : this.extensions,	// {String} 允许的文件后缀，不带点，多个用逗号分割。
                    mimeTypes : this.mimeTypes	// {String} 多个用逗号分割。
				},
				swf : Feng.ctxPath + '/static/js/plugins/webuploader/Uploader.swf',
				disableGlobalDnd : true,	// {Selector} [可选] [默认值：false] 是否禁掉整个页面的拖拽功能，如果不禁用，图片拖进来的时候会默认被浏览器打开。
				duplicate : true,			// {Boolean} [可选] [默认值：undefined] 去重， 根据文件名字、文件大小和最后修改时间来生成hash Key.
				server : this.uploadUrl,
				fileSingleSizeLimit : this.fileSizeLimit	//{int} [可选] [默认值：undefined] 验证单个文件大小是否超出限制, 超出则不允许加入队列。
			});
			return fileUploader;
		},
		/**
		 * 绑定事件
		 */
		bindEvent : function(bindedObj) {
			var me =  this;
			bindedObj.on('fileQueued', function(file) {
				/*$("#thelist").html("").append( '<div id="' + file.id + '" class="item">' +
						'<input class="info" value=' + file.name +' readonly="readonly">' + 
				'</div>' );*/
				
				$("#thelist input").attr("value", file.name);
				SoftInfoDlg.validate();
			});

			// 文件上传过程中创建进度条实时显示。
			bindedObj.on('uploadProgress', function(file, percentage) {
                $("#"+me.uploadBarId).css("width",percentage * 100 + "%");
			});

			// 文件上传成功，给item添加成功class, 用样式标记上传成功。
			bindedObj.on('uploadSuccess', function(file,response) {
				Feng.success("上传成功");
				$("#" + me.pictureId).val(response);
				//alert("response:" + response);
				$("#thelist").data("path", response);
			});

			// 文件上传失败，显示上传出错。
			bindedObj.on('uploadError', function(file) {
				Feng.error("上传失败");
			});

			// 其他错误
			bindedObj.on('error', function(type) {
				if ("Q_EXCEED_SIZE_LIMIT" == type) {
					Feng.error("文件大小超出了限制");
				} else if ("Q_TYPE_DENIED" == type) {
					alert("type:" + type);
					alert(uploadUp.mimeTypes);
					Feng.error("文件类型不满足");
				} else if ("Q_EXCEED_NUM_LIMIT" == type) {
					Feng.error("上传数量超过限制");
				} else if ("F_DUPLICATE" == type) {
					Feng.error("图片选择重复");
				} else {
					Feng.error("上传过程中出错");
				}
			});

			// 完成上传完了，成功或者失败
			bindedObj.on('uploadComplete', function(file) {
			});
		},

        /**
         * 设置图片上传的进度条的id
         */
        setUploadBarId: function (id) {
            this.uploadBarId = id;
        }
	};
	
	window.$FileUpload = $FileUpload;
}());