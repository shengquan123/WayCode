@/*
    文件上传参数的说明:
    name : 名称
    id : 头像的id
@*/
<div class="form-group">
    <label class="col-sm-3 control-label">${name}</label>
    <div class="col-sm-2">
    	<!--用来存放文件信息-->
        <div class="upload-btn" id="${id}BtnId">
            <i class="fa fa-upload"></i>&nbsp;选择文件
            <button id="ctlBtn" class="btn btn-default">开始上传</button>
        </div>
    </div>
    	<div id="thelist" name="thelist" class="col-sm-2 uploader-list" style="margin-top: 5px;">
    		<input style="border:none;"id="fileName" name="fileName" readonly="readonly"/>
    	</div>
</div>
    <input type="hidden" id="${id}" value="${avatarImg!}"/>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}


