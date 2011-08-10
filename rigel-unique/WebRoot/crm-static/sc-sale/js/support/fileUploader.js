/**
 * FileUploader:用于处理文件上传(在资质审核页面和合同返回页面已使用)
 * Usage:
 * var fu = new FileUploader(
 * 		{
 * 			'template' : textareaId,
 * 			'max' : max,
 * 			'table' : tab,
 * 			'tipper' : tip,
 * 			'form' : form
 * 		}
 * );
 * @param config
 * @return
 */
function FileUploader(config){
	this.config = config;
	this.panelCount = 0;
		
	this.init();	
};

FileUploader.Const = {
	'panelClassName' : 'upload-file-panel'
};

FileUploader.prototype = {
	'getInfo' : function(){
		var inputs = this.config.table.getElementsByTagName('input'),
			len = inputs.length;
		
		var results = [];
		for (var i = 0; i < len; i++) { 
			var item = inputs[i];
			if ( item.type == 'hidden' && item.name == this.config.fileName) {
				results.push(item.value);
			}
			
		}
		
		return results;
	},
	
	'getFileInputs' : function() {
		var infos = this.getInfo(),
			html = [];
		
		for (var i = 0, len = infos.length; i < len; i++ ) {
			html.push(
					'<input type="hidden" name="',this.config.fileName,'" value="',infos[i],'" />'
			); 
		}
		
		return html.join('');
	},
	
	'hasAvailablePanelCount' : function(){
		var number = 0;
		for (var item in this.config.fileList) {
			number += 1;
		}
		
		return  (this.config.max - number - this.panelCount) > 0;
	},
	
	'addUploadPanel' : function() {
		this.msg();
		if ( !this.hasAvailablePanelCount() ) {
			this.msg('最多上传'+this.config.max+'个附件!');
			return false;
		}
		
		var panel = document.createElement('div');
		panel.className = FileUploader.Const.panelClassName;
		panel.innerHTML = this.config.template.value.trim();
		this.config.panel.appendChild(panel);
		
		/*绑定时间框*/
		var times = getElementsByClassName('time', panel, 'input');
		if(times && times.length == 2) {
			RangeCalendar.init({
					"beginDate"	: times[0],
					"endDate"	: times[1],
					"splitter"	: "-",
					"editable"	: true,
					"history" 	: false,
					"future" 	: true
				}
			);
		}
		
		this.panelCount += 1;
		this.diablePanel(panel);
		return false;
	},
	
	'diablePanel' : function(panel){
		var inputs = panel.getElementsByTagName('input'),
			selects = panel.getElementsByTagName('select');
		
		for (var i = 0, len = inputs.length; i < len; i++) {
			var item = inputs[i];
			if (item.type == 'file') {
				continue;
			}
			
			item.disabled = true;
		}
		
		for (var i = 0, len = selects.length; i < len; i++) {
			selects[i].disabled = true;
		}
	},
	
	'enablePanel' : function(panel){
		var inputs = panel.getElementsByTagName('input'),
			selects = panel.getElementsByTagName('select');
		
		for (var i = 0, len = inputs.length; i < len; i++) {
			var item = inputs[i];
			if (item.type == 'file') {
				continue;
			}
			
			item.disabled = false;
		}
		
		for (var i = 0, len = selects.length; i < len; i++) {
			selects[i].disabled = false;
		}
	},
	
	'chkFile' : function(file){
		var fileReg  = /^.+\.(png|gif|jpg|jpeg|bmp)$/i,
			fileUrl = file.value.trim(),
			tip = file.parentNode.getElementsByTagName('span')[0],
			pass = true;
		
		if (fileUrl == ''){
			tip.innerHTML = '请选择图片文件';
			this.diablePanel(file.parentNode);
			pass = false;
		} else if(!fileReg.test(fileUrl)) {
			tip.innerHTML = '只能上传后缀为png,gif,jpg,bmp的图片文件';
			this.diablePanel(file.parentNode);
			pass = false;
		} else {
			this.enablePanel(file.parentNode);
			tip.innerHTML = '';
		}
		
		return pass;
	},
	
	'chkAll' : function() {
		var inputs = this.config.form.getElementsByTagName('input'),
			pass = true;
		
		for (var i = 0, len = inputs.length; i < len; i++) {
			var item = inputs[i];
			if (item.type == 'file') {
				if (!this.chkFile(item)) {
					pass = false;
				}
			} else if (item.getAttribute('minLen')) {
				var tip = item.parentNode.getElementsByTagName('span')[0],
					minLen = item.getAttribute('minLen').trim();
				
				if (item.value.trim().length < minLen) {
					tip.innerHTML = item.getAttribute('message');
					pass = false;
				} else {
					tip.innerHTML = '';
				}
			}
		}
		
		return pass;
	},
	
	'msg' : function(msg) {
		this.config.tipper.innerHTML = msg || '';
	},
	
	'updateAll' : function(list){
		this.config.fileList = list;
		
		this.render();
	},
	
	'updateRows' : function(list){
		//新增数据添加到文件列表
		for (var id in list) {
			if (!this.config.fileList[id]) {
				this.config.fileList[id] = list[id];
			}
		}
		
		//视图刷新
		this.render();
	},
	
	'delUploadPanel' : function(link){
		this.msg();
		if (this.panelCount == 1) {
			this.msg('最后1个上传组件，请保留');
			return false;
		}
		
		var panel = link.parentNode;
		while(panel.className != FileUploader.Const.panelClassName) {
			panel = panel.parentNode;
		}
		panel.parentNode.removeChild(panel);
	
		this.panelCount -= 1;
		return false;
	},
	
	'delFileAjaxOk' : function(id){
		if (this.config.fileList[id]) {
			//删除相关数据
			delete this.config.fileList[id];
			this.render();
		}
	},
	
	'delFileAjax' : function(link,fileId) {
		var self = this;
		if (confirm("确定删除吗?")) {
			var href = link.href;
			
			Request.get(
					{
						'url' : href,
						'ok' : function(data){
							var result = eval('(' + data + ')');
							
							if (result['flag'] == '0') {
								self.delFileAjaxOk(fileId);
							}
						},
						'bad' : function(){
							self.msg("删除文件失败!");
						}
					}
			);
		}
		
		return false;
	},
	
	'removeAllPanel' : function(){
		var panels = getElementsByClassName(FileUploader.Const.panelClassName, this.config.panel, 'div');
		
		for (var i = 0, len = panels.length; i < len; i++) {
			var item = panels[i];
			if ( item && item.className && (item.className == FileUploader.Const.panelClassName) ) {
				this.config.panel.removeChild(item);
			}
		}
	},
	
	'render' : function(){
		var data = [];
		for (var item in this.config.fileList) {
			data = data.concat(this.config.fileList[item]);
		}

		this.config.table.setData(data);
		this.config.table.parentNode.style.height = '';
		
		//this.config.panel.innerHTML = '';
		
		try {
			this.removeAllPanel();
		}catch(e){
			throw {'name':'自定义错误','message':'removeAllPanel出错'}
		}
		
		this.panelCount = 0;
		this.addUploadPanel();
	},
	
	'init' : function(){
		this.render();
	}
};

