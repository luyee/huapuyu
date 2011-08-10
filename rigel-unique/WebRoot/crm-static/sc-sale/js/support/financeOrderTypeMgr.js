var FinanceOrderTypeMgr = {
		validate_msg : [],
		_unselected : function(sel){
			if (sel) {
				var value = sel.value.trim();
				return value == '' || value == '-1';
			}
			
			return false;
		},
		
		_unfilled : function(input) {
			if (input) {
				var value = input.value.trim();
				return value == '';
			}
			return false;
		},
		
		_chkMoneyValid : function() {
			var moneyReg=/^((?:[1-9][0-9]*)|[0-9])(?:\.[0-9]{1,2})?$/i;
			var paySumA = G('paySumA').value.trim(),
				paySumB = G('paySumB').value.trim(),
				financeSum = G('financeSumSpan').innerHTML.trim();
			
			if (!moneyReg.test(paySumA) || !moneyReg.test(paySumB)) {
				this.validate_msg.push('A单或B单金额格式错误');
			} else {
				var pa = parseFloat(paySumA),
					pb = parseFloat(paySumB),
					fs = parseFloat(financeSum);
				
				if (pa <= 0 || pb <= 0){
					this.validate_msg.push('金额必须大于0');
				}
				
				if (Math.abs(pa + pb - fs) > 0.1) {
					this.validate_msg.push('AB单金额之和不等于发票金额');
				}
			}
		},
		
		chk_AB0 : function() {
			var payTypeListSingle = G('payTypeListSingle'),
				commentValueId = G('commentValueId'),
				payTypeSelectedSingle = G('payTypeSelectedSingle'),
				payMethodTip = G('payMethodTip');
			
			if (this._unselected(payTypeListSingle)) {
				this.validate_msg.push('未选择付款方式');
			}
			
			if (this._unselected(payTypeSelectedSingle)) {
				this.validate_msg.push('未选择付款备注');
			}
			
			if (this._unfilled(commentValueId)) {
				this.validate_msg.push('未填写现金(或支票,网银)号码');
			}
			
			if (this.validate_msg.length > 0) {
				payMethodTip.innerHTML = this.validate_msg.join('<br />');
				return false;
			}
			
			payMethodTip.innerHTML = '';
			return true;
	
		},
		
		chk_AB1 : function(){
			var payTypeListA = G('payTypeListA'),
				payTypeSelectedA = G('payTypeSelectedA'),
				commentValueA = G('commentValueA'),
				paySumA = G('paySumA'),
				payTypeListB = G('payTypeListB'),
				payTypeSelectedB = G('payTypeSelectedB'),
				commentValueB = G('commentValueB'),
				paySumB = G('paySumB'),
				payMethodTip = G('payMethodTip');
			
			if (this._unselected(payTypeListA) || this._unselected(payTypeListB)) {
				this.validate_msg.push('A单或B单未选择付款方式');
			}
			
			if (this._unselected(payTypeSelectedA) || this._unselected(payTypeSelectedB)) {
				this.validate_msg.push('A单或B单未选择付款备注');
			}
			
			if (this._unfilled(commentValueA) || this._unfilled(commentValueB)) {
				this.validate_msg.push('A单或B单未填写现金(或支票,网银)号码');
			}
			
			if (this._unfilled(paySumA) || this._unfilled(paySumB)) {
				this.validate_msg.push('A单或B单未填写金额')
			} else {
				this._chkMoneyValid();
			}
			
			if (this.validate_msg.length > 0) {
				payMethodTip.innerHTML = this.validate_msg.join('<br />');
				return false;
			}
			
			//TODO:验证金额
			payMethodTip.innerHTML = '';
			return true;
			
		},
		
		pass : function() {
			if (!this.doms) {
				this.doms = {};
				this.doms['isAB0'] = G('isAB0');
				this.doms['isAB1'] = G('isAB1');
				this.doms['payMethodTip'] = G('payMethodTip');
				
				this.doms.is_ok = this.doms['isAB0'] && this.doms['isAB1'];
			}
			
			if (this.doms.is_ok) {
				this.validate_msg.length = 0;
				
				if (this.doms['isAB0'].checked) {
					return this.chk_AB0();
				} else if (this.doms['isAB1'].checked) {
					return this.chk_AB1();
				}
			}
			
			return false;
		},
		
		getHtml : function(){
			var html = [];
			if (this.doms['isAB0'].checked) {
				html.push('<input type="hidden" name="financeDto.receiptInfo.isAb" value="'+this.doms['isAB0'].value+'" />');
				html.push(new FormMgr('payTypeSingle').serializeHtml());
				
			} else if(this.doms['isAB1'].checked) {
				html.push('<input type="hidden" name="financeDto.receiptInfo.isAb" value="'+this.doms['isAB1'].value+'" />');
				html.push(
						new FormMgr('payTypeA').serializeHtml(),
						new FormMgr('payTypeB').serializeHtml()
				);
			}
			
			return html.join('');
		}
};