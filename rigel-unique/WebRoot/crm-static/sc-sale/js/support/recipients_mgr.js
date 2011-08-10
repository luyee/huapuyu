/**
 *收件人管理器
 */
var RecipientTabMgr = {
	items : [],
	
	update : function(data) {
		
		for(var i = 0, len = this.items.length; i < len; i++) {
			var recipient = this.items[i];
			var tData = recipient.transformData(data.custRecipList);//服务端返回的数据格式需要转换
			//alert(tData);
			recipient.saveStatus();
			recipient.renderReciptList(tData);
			recipient.recoverStatus();
			
			recipient.clearAddTipPanel();
		}	
	},
	
	addItem : function(item) {
		this.items.push(item);
	}
};

function RecipientMgr(domStr,recieverName) {
	this.domStr = domStr;
	this.recieverName = recieverName;
	this.doms = {};
	this.getDoms();
	
	this.curEditItem = null;
	this.TYPE = 'ADD';			//默认状态为新增收件人

	this.fm = new FormMgr(this.doms.editPanel);

	RecipientTabMgr.addItem(this);
	
	this.disabled = false;
}


RecipientMgr.prototype = {
	"saveStatus" : function() {
		this.lastSelectedItemValue = this.getSelectedItemValue();
	},
	
	"disableMgr" : function(){
		var inputs = this.doms.tabListWrap.getElementsByTagName('input');
		
		for (var i = 0, len = inputs.length; i < len; i++ ) {
			var item = inputs[i];
			if (item.type == 'radio') {
				item.disabled = true;
			}
		}
		
		this.disabled = true;
	},
	
	"enableMgr" : function(){
		var inputs = this.doms.tabListWrap.getElementsByTagName('input');
		
		for (var i = 0, len = inputs.length; i < len; i++ ) {
			var item = inputs[i];
			if (item.type == 'radio') {
				item.disabled = false;
			}
		}
		
		this.disabled = false;
	},
	
	"isDisabled" : function(){
		return this.disabled;
	},
	
	"clearAddTipPanel" : function(){
		if (this.doms.addTipPanel) {
			this.doms.addTipPanel.style.display = 'none';
			this.showRecipientList();
			
			this.doms.addTipPanel = null;
		}
		
	},

	"recoverStatus" : function() {
		if (this.lastSelectedItemValue != '') {
			var inputs = this.doms.dataTable.getElementsByTagName('input');
		
			for (var i = 0, len = inputs.length; i < len; i++) {
				var item = inputs[i];
				if (item.name == this.recieverName ) {
					if (item.value == this.lastSelectedItemValue) {
						item.checked = true;
						break;
					}
				}
			}
		}
	},
	
	"showAddTipPanel" : function(){
		if (this.doms.addTipPanel) {
			this.doms.addTipPanel.show();
		}
	},

	"showEditPanel" : function(){	
		this.doms.editPanel.show('block');

		if (this.doms.ctrlPanel) {
			this.doms.ctrlPanel.hide();
		}
	},

	"hideEditPanel" : function() {
		this.doms.editPanel.hide();
	},

	"getDoms" : function() {
		this.doms.editPanel = G(this.domStr + 'EditPanel');
		this.doms.ctrlPanel = G(this.domStr + 'RecieverCtrlPanel');
		this.doms.tabListWrap   = G(this.domStr + 'RecieverTabWrap');
		this.doms.editForm	= G(this.domStr + 'EditForm');
		this.doms.dataTable = this.doms.tabListWrap.getElementsByTagName("div")[1];
		this.doms.addTipPanel = G(this.domStr + 'RecieverCtrlPanel');
	},

	"hideRecipientList" : function() {
		this.doms.tabListWrap.hide();
	},

	"showRecipientList" : function() {
		this.doms.tabListWrap.show();
	},

	"addItem" : function() {
		if (this.isDisabled()) {
			alert('您没有新增收件人权限!');
			return false;
		}
		
		this.TYPE = 'ADD';

		if (g_reciptCount >= 10) {
			alert("最多添加10个收件人!");
			return false;
		}
		
		this.clearFormData();
		this.showEditPanel();
		this.hideRecipientList();

		return false;
	},
	
	"getSelectedItemValue" : function() {
		var inputs = this.doms.dataTable.getElementsByTagName('input');
		
		for (var i = 0, len = inputs.length; i < len; i++) {
			var item = inputs[i];
			if (item.name == this.recieverName) {
				if (item.checked) {
					return item.value;
				}
			}
		}

		return '';
	},

	"editItem" : function(id) {
		if (this.isDisabled()) {
			alert('您没有修改收件人权限!');
			return false;
		}
		
		var recipient = g_reciptListHashTab[id];	//reciptList是全局变量
		
		if (recipient) {
			this.curEditItem = id;		//当前编辑项目
			this.showEditPanel();
			this.hideRecipientList();

			this.padInfo();

			this.TYPE = 'EDIT';

		} else {
			alert('出错了!');
		}

		return false;
	},

	"padInfo" : function() {
		if (!this.curEditItem) {return false;}

		var info = g_reciptListHashTab[this.curEditItem];
			
			this.setFormData(
				["id",info.id],
				["name",info.name],
				["phoneId",info.phone && info.phone.phoneId || ''],
				["phone1",info.phone && info.phone.phone1   || ''],
				["phone2",info.phone && info.phone.phone2   || ''],
				["phone3",info.phone && info.phone.phone3   || ''],
				["cellPhoneId",info.cell &&info.cell.cellId || ''], //呼出过来的数据电话,手机可能只有1个
				["cellPhone",info.cell && info.cell.phone2  || ''],
				["address",info.address],
				["postNumber",info.postNumber]
			);
	},

	"setFormData" : function() {
		var f = this.doms.editForm;
		f.elements = f.elements || (function() {
			var inputs = f.getElementsByTagName('input');
			return function(name) {
				for (var i = 0, len = inputs.length; i < len; i++ ) {
					if (inputs[i].name == name) {
						return inputs[i];
					}
				}
				
				return null;
			};
		})();

		for (var i = 0, len = arguments.length; i < len; i++) {
			var item = arguments[i],
				input = f.elements(item[0]);
			if (input) {
				input.value = item[1];
			}
		}
	},

	"clearFormData" : function() {
		this.curEditItem = null;
		this.setFormData(
				["id",''],
				["name",''],
				["phoneId",''],
				["phone1",''],
				["phone2",''],
				["phone3",''],
				["cellPhoneId",''],
				["cellPhone",''],
				["address",''],
				["postNumber",'']
			);
	},

	"cancel" : function() {
		this.hideEditPanel();
		
		if (g_reciptCount > 0) {
			this.showRecipientList();
		} else {
			this.showAddTipPanel();
		}
		
		return false;
	},

	"submit" : function() {
		var that = this;
		var pass = this.isLegalData();
		
		if (pass) {
			var url = this.getSubmitUrl();
			var postData = this.getSubmitData();
			
			var ajax = new Ajax(function(data){
				var data = eval('(' + data + ')');
				
				if (data && data.flag != 0) {
					alert(data.error);
				} else {
					//服务端返回的数据格式需要转换
					//var tData = that.transformData(data.custRecipList);
				
					RecipientTabMgr.update(data);
					
					//更新收件人HashTable数据
					that.updateReciptListHashTab(data.custRecipList);
					
					that.showRecipientList();
					that.hideEditPanel();

				}
			});
			
			ajax.post(url,postData);
		}
	},
	
	"updateReciptListHashTab" : function(list) {
		//g_reciptListHashTab 

		for (var i = 0, len = list.length; i < len; i++) {
			var item = list[i],
				temp = {};

				temp.id = item.id;
				temp.name = item.name;
				temp.address = item.address;
				temp.postNumber = item.postNumber;
				
			var phoneList = item.phoneList;
				temp.phone = {};
				temp.cell = {};

			for (var j = 0, jLen = phoneList.length; j < jLen; j++ ) {
				var pl = phoneList[j];
				if (pl.phoneType == "0") {
					temp.phone.phone1 = pl.phone1;
					temp.phone.phone2 = pl.phone2;
					temp.phone.phone3 = pl.phone3;
					temp.phone.phoneId = pl.phoneId;
				} else if (pl.phoneType == "1") {
					temp.cell.phone1 = pl.phone1;
					temp.cell.phone2 = pl.phone2;
					temp.cell.phone3 = pl.phone3;
					temp.cell.cellId = pl.phoneId;
				}
			}

			g_reciptListHashTab[item.id] = temp;
			g_reciptCount = len; //收件人个数更新
		}


		//todo
	},

	"renderReciptList" : function(list) {
	
		this.doms.dataTable.setData(list);
		this.doms.dataTable.parentNode.style.height = '';
	},

	"transformData" : function(list) {
		var data = [];
		
		for (var i = 0, len = list.length; i < len; i++) {
			var item = list[i],
				phone = item.phoneList,
				phoneStr = [],
				cellStr = [];

			for (var j = 0, jLen = phone.length; j < jLen; j++) {
				var p = phone[j];
				if (p.phoneType == "0") {

					p.phone1 && phoneStr.push(p.phone1);
					phoneStr.push(p.phone2);
					p.phone3 && phoneStr.push(p.phone3);
				} else {
					cellStr.push(p.phone2);
				}
			}

			data.push(
				'<input type="radio" value="'+item.id+'" name="' + this.recieverName + '" />',
				encodeHTML(item.name),
				phoneStr.join('-'),
				cellStr.join(''),
				encodeHTML(item.address),item.postNumber,
				'<a href="#" onClick="return '+ this.domStr + 'ReceiperMgr.editItem('+item.id+')">修改</a>');
		}

		return data;
	},
		
	"isLegalData" : function() {
		var sourceInput = this.doms.editForm.getElementsByTagName("input");
		var phone1_is_blank = true,
			phone2_is_blank = true,
			cellPhone_is_blank = true;

		for(var i=0,tempLen=sourceInput.length;i<tempLen;i++){
			var tempInput = sourceInput[i];
			if(tempInput.type=="text" || tempInput.type=="hidden"){
				tempInput.value = tempInput.value.trim();

				if(tempInput.name=="name"){
					
					if(tempInput.value.length<=0){
						alert("收件人必填");
						return false;
					}
				}else if(tempInput.name=="phone1"){
					if(tempInput.value.length > 0){
						if(!tempInput.value.trim().match(/^0\d{2,3}$/ig)){
							alert("电话区号长度只能3位或者4位，并且以0开头");
							
							return false;
						}
						phone1_is_blank = false;
					} 
					
				}else if(tempInput.name=="phone2"){
					if(tempInput.value.length > 0){
						if(!tempInput.value.match(/^\d{7,8}$/ig)){
							alert("电话号码只能是7位或者8位的数字");

							
							return false;
						}
						phone2_is_blank = false;
					}
				}else if(tempInput.name=="phone3"){
					if(!tempInput.value.match(/^\d{0,8}$/ig)){
						alert("分机号只能是数字");
						return false;
					}
				}else if(tempInput.name=="cellPhone"){
					if(tempInput.value.length > 0){
						if(!tempInput.value.match(/^\d{11}$/ig)){
							alert("手机只能是11位数字");
							return false;
						}
						
						cellPhone_is_blank = false;
					}
				}else if(tempInput.name=="address"){
					if(tempInput.value.length<=0){
						alert("收件人地址必填");
						return false;
					}
				}else if(tempInput.name=="postNumber"){
					if(tempInput.value.length<=0){
						alert("邮编必填");
						return false;
					}else if(!tempInput.value.match(/^\d{6,7}$/ig)){
						alert("邮编只能是6位或者7位数字");
						return false;
					}
				}
			}
		}
		
		if (phone1_is_blank 
			&& phone2_is_blank 
			&& cellPhone_is_blank)
		{
			alert("座机或手机至少填写其一");
			return false;
		}

		return true;
	},

	"getSubmitUrl" : function() {
		if (this.TYPE == 'ADD') {
			return g_addReciptUrl;
		}else if(this.TYPE == 'EDIT') {
			return g_modifyReciptUrl;
		}

		return '';
	},

	"getSubmitData" : function() {
		return this.fm.serializeStrAll();
	}

};

