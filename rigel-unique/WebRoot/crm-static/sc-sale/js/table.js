/**
 * 初始化表格控件
 * @public
 *
 * @param {Element} table 表格控件的外框元素或者外框元素名称
 * @param {Number} column 表格控件的列数
 * @param {Object} config 表格控件的配置信息, 配置信息分为三个属性: checkbox标识复选框, sort标识排序信息,
 *                        edit标识编辑状态的处理. checkbox包含属性name(编辑框的名称)与custom(自定义表头显
 *                        示层, 可以为空). sort包含属性action(触发排序时的事件处理)与info(数组, 排序的关
 *                        键字, 如果某列不允许排序, 对应的值为null). edit包含属性action(提交时的事件处理)
 *                        与info(数组, 如果某一列可以编辑, 对应的值是字符串表示编辑框名称, 值是列表表示下
 *                        拉框信息, 值是函数名表示通过调用函数来设置编辑框, 值是true表示这一列是操作列)
 */
function initTable(table, column, config)
{
	var el, i, width;
	table = G(table);
	var parents = [];
	for (var parent = G(table.parentNode); parent.tagName.toLowerCase() != 'body'; parent = G(parent.parentNode))
	{
		var display = parent.css('display');
		if (display == 'none')
		{
			parent.style.display = 'block';
			parents.push(parent);
		}
	}
	list = table.getChildren('div');
	// 计算整个表格的宽度
	for (i = 0, width = 0; i < column; i++)
	{
		el = G(list[i]);
		el.offsetWidth;
		width += el.offsetWidth;
		el.w = el.offsetWidth
			- el.css('borderLeftWidth').toNumber()
			- el.css('borderRightWidth').toNumber();
		el.css('width', el.w + "px");
		el.addClass("title");
	}
	table.css('width',
		(width + table.css('borderLeftWidth').toNumber()
		+ table.css('borderRightWidth').toNumber() + column) + "px");
	if (config)
	{
		var conf = config.checkbox;
		if (conf)
		{
			var item = list[0];
			if (conf.custom)
			{
				el = G(conf.custom);
				el.table = table;
				el.css({display:'none',position:'absolute'});
				conf.custom = el;
				item.addClass("menu");
			}
			else
			{
				item.addClass("nochecked");
			}
			item.innerHTML = "";
			item.onclick = Table.clickMenu;
			for (i = column; el = G(list[i]); i += column)
			{
				Table.initCell(el, conf.name);
			}
			table.getCheckedCount = Table.getCheckedCount;
		}
		conf = config.sort;
		if (conf)
		{
			for (i = config.checkbox ? 1 : 0; i < column; i++)
			{
				item = list[i];
				item.sortName = conf.info[i];
				item.onclick = Table.clickSort;
			}
		}
		if (config.edit)
		{
			table.edit = Table.edit;
			table.save = Table.save;
			table.cancel = Table.cancel;
			table.saveClick = Table.saveClick;
			table.cancelClick = Table.cancelClick;
		}
	}
	table.config = config;
	table.column = column;
	table.setData = Table.setData;
	table.add = Table.add;
	table.flush = Table.flush;
	table.onmousemove = Table.onMouseMove;
	table.onmousedown = Table.onMouseDown;
	table.flush();
	for (i = parents.length - 1; i >= 0; i--)
	{
		parents[i].style.display = 'none';
	}
}

function initTable2(table, column, config)
{
	var el, i, width;
	table = G(table);
	var parents = [];
	for (var parent = G(table.parentNode); parent.tagName.toLowerCase() != 'body'; parent = G(parent.parentNode))
	{
		var display = parent.css('display');
		if (display == 'none')
		{
			parent.style.display = 'block';
			parents.push(parent);
		}
	}
	list = table.getElementsByTagName('td');
	// 计算整个表格的宽度
	for (i = 0, width = 0; i < column; i++)
	{
		el = G(list[i]);
		width += el.css('width').toNumber();
		el.w = el.css('width').toNumber()
			- el.css('borderLeftWidth').toNumber()
			- el.css('borderRightWidth').toNumber();
		el.css('width', el.w + "px");
		el.addClass("title");
	}
	table.css('width',
		(width + table.css('borderLeftWidth').toNumber()
		+ table.css('borderRightWidth').toNumber()) + "px");
	if (config)
	{
		var conf = config.checkbox;
		if (conf)
		{
			var item = list[0];
			if (conf.custom)
			{
				el = G(conf.custom);
				el.table = table;
				el.css({display:'none',position:'absolute'});
				conf.custom = el;
				item.addClass("menu");
			}
			else
			{
				item.addClass("nochecked");
			}
			item.innerHTML = "";
			item.onclick = Table.clickMenu;
			for (i = column; el = G(list[i]); i += column)
			{
				Table.initCell(el, conf.name);
			}
			table.getCheckedCount = Table.getCheckedCount;
		}
		conf = config.sort;
		if (conf)
		{
			for (i = config.checkbox ? 1 : 0; i < column; i++)
			{
				item = list[i];
				item.sortName = conf.info[i];
				item.onclick = Table.clickSort2;
			}
		}
	}
	table.config = config;
	table.column = column;
	table.setData = Table.setData;
	table.add = Table.add;
	table.flush = Table.flush;
	table.flush();
	for (i = parents.length - 1; i >= 0; i--)
	{
		parents[i].style.display = 'none';
	}
}

var Table =
{
	EditMessage: "有记录处于编辑状态, 是否继续?",
	SaveText: "保存",
	CancelText: "取消",
	/**
	 * 改变单选列的Class属性
	 * @private
	 *
	 * @param {Element} el 需要缩放的元素
	 * @param {String} name 新的Class属性
	 */
	changeCheckbox: function (el, name)
	{
		el.removeClass("checked");
		el.removeClass("nochecked");
		el.removeClass("partchecked");
		el.addClass(name);
	},
	/**
	 * 表格控件上的鼠标移动事件, 需要在表格栏上改变鼠标的形状
	 * @public
	 *
	 * @param {Event} event 事件对象
	 */
	onMouseMove: function (event)
	{
		event = getEvent(event);
		var pos = this.getPosition();
		var offsetX = event.pageX - pos.left;
		var list = this.getChildren("div");
		var column = this.column;
		this._x = offsetX;
		if (event.pageY - pos.top < list[0].offsetHeight)
		{
			for (var i = 0; i < column; i++)
			{
				offsetX -= list[i].offsetWidth;
				if (Math.abs(offsetX) < 3)
				{
					this.style.cursor = 'col-resize';
					this._i = i;
					return;
				}
				else if (offsetX < 0)
				{
					break;
				}
			}
		}
		this.style.cursor = '';
	},
	/**
	 * 表格控件上的鼠标点击事件, 判断是否进入拖动状态
	 * @public
	 *
	 * @param {Event} event 事件对象
	 */
	onMouseDown: function (event)
	{
		if (this.style.cursor)
		{
			this.onmousemove = "";
			Table.drag = this;
			document.onmousemove = Table.dragMouseMove;
			document.onmouseup = Table.dragMouseUp;
		}
		else
		{
			Table.drag = "";
		}
	},
	/**
	 * 表格控件上的鼠标拖动处理
	 * @public
	 *
	 * @param {Event} event 事件对象
	 */
	dragMouseMove: function (event)
	{
		event = getEvent(event);
		var table = Table.drag;
		var list = table.getChildren("div");
		var item = list[table._i];
		// 限制最小的间隔为10
		var width = Math.max(10 - item.w, event.pageX - table.getPosition().left - table._x);
		item.w += width;
		item.style.width = item.w + "px";
		table.style.width = (table.style.width.toNumber() + width) + "px";
		table._x += width;
		table.flush();
	},
	/**
	 * 表格控件上的鼠标拖动结束
	 * @public
	 *
	 * @param {Event} event 事件对象
	 */
	dragMouseUp: function (event)
	{
		Table.drag.onmousemove = Table.onMouseMove;
		document.onmousemove = "";
		document.onmouseup = "";
	},
	/**
	 * 点击表格控件的单选列表头的处理
	 * @public
	 */
	clickMenu: function ()
	{
		if (!Table.drag)
		{
			var table = this.tagName == 'TD' ? this.parentNode.parentNode.parentNode : this.parentNode;
			var conf = table.config.checkbox;
			if (conf.custom)
			{
				// 如果有自定义属性需要做额外的处理
				var menu = conf.custom;
				if (menu.parentNode !== document.body)
				{
					menu.remove();
					document.body.appendChild(menu);
				}
				var pos = table.getPosition();
				menu.style.top = pos.top + 'px';
				menu.style.left = pos.left + 'px';
				menu.style.display = '';
			}
			else
			{
				// 没有自定义属性自动计算全选/半选/全不选状态
				var list = table.tagName == 'TABLE' ? table.getElementsByTagName('td') : table.getChildren("div");
				var column = table.column;
				var className, value;
				if (list[0].containClass("checked"))
				{
					className = "nochecked";
					value = false;
				}
				else {
					className = "checked";
					value = true;
				}
				Table.changeCheckbox(list[0], className);
				for (var i = column, item; item = list[i]; i += column)
				{
					if (item.checkbox)
					{
						Table.changeCheckbox(item, className);
						item.checkbox.checked = value;
					}
				}
			}
			conf.onclick && conf.onclick(this);
		}
	},
	/**
	 * 初始化单选列的单元格数据
	 * @public
	 *
	 * @param {Element} el 需要初始化的单元格
	 * @param {String} name 单元格被提交时的数据名称
	 * @param {Boolean} value 单元格的默认值, true为选中状态
	 */
	initCell: function (el, name, value)
	{
		if (el.innerHTML)
		{
			el.innerHTML = "<input type='checkbox' style='display:none' name='"
				+ name + "' value='" + el.innerHTML.replace("'", "&#39;") + "' />";
			el.checkbox = el.getElementsByTagName("input")[0];
			el.checkbox.checked = value;
			el.addClass(value ? "checked" : "nochecked");
			el.onclick = Table.clickCheckbox;
		}
	},
	/**
	 * 计算单选列被选中的项数
	 * @public
	 */
	getCheckedCount: function ()
	{
		var list = this.tagName == "TABLE" ? this.getElementsByTagName("td") : this.getChildren("div");
		var column = this.column;
		var count = 0, item;
		for (var i = column; item = list[i]; i += column)
		{
			if (item.containClass("checked"))
			{
				count++;
			}
		}
		return count;
	},
	/**
	 * 点击单选列中的单元格的处理, 改变原来的选中状态
	 * @public
	 */
	clickCheckbox: function ()
	{
		if (this.containClass("partchecked"))
		{
			return;
		}
		if (this.containClass("nochecked"))
		{
			this.removeClass("nochecked");
			this.addClass("checked");
			this.checkbox.checked = true;
		}
		else
		{
			this.removeClass("checked");
			this.addClass("nochecked");
			this.checkbox.checked = false;
		}
		var table = this.tagName == 'TD' ? this.parentNode.parentNode.parentNode : this.parentNode;
		var conf = table.config.checkbox;
		if (!conf.custom)
		{
			var list = table.tagName == 'TABLE' ? table.getElementsByTagName('td') : table.getChildren("div");
			var column = table.column;
			var count = table.getCheckedCount() + 1;
			Table.changeCheckbox(list[0],
				count == Math.ceil(list.length / column)
				? "checked"
			    : (count == 1 ? "nochecked" : "partchecked"));
		}
		conf.onclick && conf.onclick(this);
	},
	/**
	 * 点击表头触发排序处理
	 * @public
	 */
	clickSort: function ()
	{
		if (!Table.drag && this.sortName)
		{
			this.parentNode.config.sort.action(this.sortName);
		}
	},
	/**
	 * 点击表头触发排序处理2
	 * @public
	 */
	clickSort2: function ()
	{
		if (!Table.drag && this.sortName)
		{
			this.parentNode.parentNode.parentNode.config.sort.action(this.sortName);
		}
	},
	/**
	 * 向表格控件填充数据
	 * @public
	 *
	 * @param {Array} data 数据的一维数组表现形式, 按先行后列的顺序放入, 例如
	 *                     (Row1,Col1),(Row2,Col2)...(Row2,Col1),(Row2,Col2)...
	 */
	setData: function (data)
	{
		var list = this.getChildren("div");
		var column = this.column;
		var item;
		for (var i = column; item = list[i]; i++)
		{
			this.removeChild(item);
		}
		this.add(data);
	},
	/**
	 * 向表格控件增加数据
	 * @public
	 *
	 * @param {Array} data 数据的一维数组表现形式, 按先行后列的顺序放入, 例如
	 *                     (Row1,Col1),(Row2,Col2)...(Row2,Col1),(Row2,Col2)...
	 */
	add: function (data)
	{
		var list = this.getChildren("div");
		var column = this.column;
		var item;
		for (var i = 0; true; i++)
		{
			item = data[i];
			if (item === undefined)
			{
				break;
			}
			var el = document.createElement("div");
			el.innerHTML = item;
			if (this.config && this.config.checkbox && !(i % column))
			{
				Table.initCell(el, this.config.checkbox.name);
			}
			this.appendChild(el);
		}
		this.flush();
	},
	/**
	 * 刷新表格控件, 重新设置列宽
	 * @public
	 */
	flush: function ()
	{
		var column = this.column;
		var list = this.tagName == "TABLE" ? this.getElementsByTagName("td") : this.getChildren("div");
		for (var i = column, item; item = G(list[i]); i++)
		{
			var title = list[i % column];
			if (this.config && (!this.config.checkbox || (i % column)))
			{
				item.className = title.className;
				item.removeClass("title");
				item.removeClass("sortdown");
				item.removeClass("sortup");
			}
			if (Math.floor(i / column) % 2 == 0)
			{
				item.addClass("dark");
			}
			else
			{
				item.removeClass("dark");
			}
			item.css("width", title.w + "px");
		}
	},
	/**
	 * 表格内编辑初始化操作, 表格初始化的config.edit.info记录了每一类的编辑设定, 如果对应列为
	 * null表示不能编辑, 如果为String表示<input>的名称, 如果为Array表示<select>框属性(其中第0
	 * 项是名称, 其余是<option>), 如果为函数表示由函数来填充编辑区域, 如果为true表示这一列是
	 * 最终的操作列(将自动填充保存与取消链接)
	 * @public
	 *
	 * @param {Number} row 需要编辑的行号
	 * @param {String} custParam 客户自定义的参数对象, 会在编辑完成时提交给定义的编辑保存函数
	 */
	edit: function (row, custParam)
	{
		if (Table.row)
		{
			if (!confirm(Table.EditMessage))
			{
				return;
			}
			this.cancel();
		}
		Table.row = row;
		Table.custParam = custParam;
		var column = this.column;
		var list = this.getChildren("div");
		var index = row * column;
		for (var i = 0; i < column; i++)
		{
			var flag = this.config.edit.info[i];
			if (flag)
			{
				var item = list[index + i];
				item.oldHTML = item.innerHTML;
				if (flag === true)
				{
					item.innerHTML =
						"<span class='link'"
					    + " onclick='this.parentNode.parentNode.saveClick()'>"
					    + Table.SaveText
						+ "</span>"
						+ " <span class='link'"
						+ " onclick='this.parentNode.parentNode.cancelClick()'>"
						+ Table.CancelText
						+ "</span>"
				}
				else if (flag.constructor === Array)
				{
					var s = ["<select name='",flag[0],"'>"];
					var text = item.innerHTML;
					for (var j = 1; j < flag.length; j += 2)
					{
						s.push("<option value='");
						s.push(flag[j]);
						s.push("'");
						if (flag[j + 1] == text)
						{
							s.push(" selected");
						}
						s.push(">");
						s.push(flag[j + 1]);
						s.push("</option>");
					}
					s.push("</select>");
					item.innerHTML = s.join("");
				}
				else
				{
					var name = flag.constructor === Function ? flag(item) : flag;
					if (name)
					{
						item.innerHTML = "<input name='" + name + "' value='" + item.innerHTML.replace("'", "&#39;") + "'>";
					}
				}
			}
		}
	},
	/**
	 * 表格内编辑操作取消, 恢复编辑前的数据
	 * @public
	 */
	cancel: function ()
	{
		var column = this.column;
		var list = this.getChildren("div");
		var index = Table.row * column;
		var info = this.config.edit.info;
		for (var i = 0; i < column; i++)
		{
			if (info[i])
			{
				var item = list[index + i];
				item.cancel ? item.cancel() : item.innerHTML = item.oldHTML;
			}
		}
		Table.row = "";
		Table.custParam = "";
	},
	/**
	 * 表格内编辑状态时取消链接点击操作
	 * @public
	 */
	cancelClick: function ()
	{
		if (Table.row)
		{
			this.cancel(Table.row);
		}
	},
	/**
	 * 表格内编辑状态时取消链接点击操作
	 * @private
	 *
	 * @param {Boolean} flag 是否需要读取
	 * @param {Element} el 需要读取的单元格元素
	 */
	_getInput: function (flag, el)
	{
		if (flag)
		{
			return el.getElementsByTagName(flag.constructor === Array ? "select" : "input")[0];
		}
	},
	/**
	 * 表格内编辑结果保存(回写入表格)
	 * @public
	 *
	 * @param {Object} data 需要回写的数据对象
	 */
	save: function (data)
	{
		var column = this.column;
		var list = this.getChildren("div");
		var index = Table.row * column;
		var info = this.config.edit.info;
		for (var i = 0; i < column; i++)
		{
			if (info[i])
			{
				var item = list[index + i];
				if (item.save)
				{
					item.save();
				}
				else
				{
					var el = Table._getInput(info[i], item);
					if (el)
					{
						var value = el.value;
						if (el.tagName == 'SELECT')
						{
							for (var j = 0, options = el.options, option; option = options[j]; j++)
							{
								if (option.value == value)
								{
									value = option.text;
									break;
								}
							}
						}
						else
						{
							value = data[el.name];
						}
					}
					item.innerHTML = el ? value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;") : item.oldHTML;
				}
			}
		}
		Table.row = "";
		Table.custParam = "";
	},
	/**
	 * 表格内编辑状态时保存链接点击操作, 内部会触发客户自定义的保存处理函数
	 * @public
	 */
	saveClick: function ()
	{
		if (Table.row)
		{
			var column = this.column;
			var list = this.getChildren("div");
			var index = Table.row * column;
			var result = {};
			var conf = this.config.edit;
			for (var i = 0; i < column; i++)
			{
				var flag = conf.info[i];
				if (flag)
				{
					var item = list[index + i];
					if (item.set)
					{
						item.set(result);
					}
					else
					{
						var el = Table._getInput(flag, item);
						if (el)
						{
							result[el.name] = el.value;
						}
					}
				}
			}
			conf.action(result, Table.custParam);
		}
	}
};