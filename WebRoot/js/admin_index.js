Ext.onReady( function() {
	
	//Ext.Msg.alert('ext','welcome you!');
	
	var addPanel = function(btn, event) {
		var n;
		n = tabPanel.getComponent(btn.id);
		if(n) 
		{
			tabPanel.setActiveTab(n);
			return;
		}
			
		n = tabPanel.add( {
			id : btn.id,
			title : btn.id,
			//html : '<iframe width=100% height=100% src=' + btn.id + ' />',
			autoLoad : 'test2.html',
			closable : 'true'
		} );
		tabPanel.setActiveTab(n);
	}

	var item1 = new Ext.Panel( {
		title : '分类1',
		//html : '&lt;empty panel&gt;',
		cls : 'empty',
		items : [
		    new Ext.Button( {
		    	id : 'Category_list',
		        text : '子类1',
		        width : '100%',
		        listeners : { click : addPanel }
		    } ),

		    new Ext.Button( {
		        id : 'test',
		        text : '子类2',
		        width : '100%',
		        listeners : { click : addPanel }
		    } )
		]
	} );

	var item2 = new Ext.Panel( {
		title : 'Accordion Item 2',
		html : '&lt;empty panel&gt;',
		cls : 'empty'
	} );

	var item3 = new Ext.Panel( {
		title : 'Accordion Item 3',
		html : '&lt;empty panel&gt;',
		cls : 'empty'
	} );

	var item4 = new Ext.Panel( {
		title : 'Accordion Item 4',
		html : '&lt;empty panel&gt;',
		cls : 'empty'
	} );

	var item5 = new Ext.Panel( {
		title : 'Accordion Item 5',
		html : '&lt;empty panel&gt;',
		cls : 'empty'
	} );

	var accordion = new Ext.Panel( {
		region : 'west',
		margins : '5 0 5 5',
		split : true,
		width : 210,
		layout : 'accordion',
		items : [ item1, item2, item3, item4, item5 ]
	} );

	var tabPanel = new Ext.TabPanel( {
		region : 'center',
		enableTabScroll : true,
		deferredRender : false,
		activeTab : 0,
		items : [ {
			title : 'index',
			//html : 'aaaaaa'
			autoLoad : 'test1.html'
		} ]
	} );

	var viewport = new Ext.Viewport( {
		layout : 'border',
		items : [ accordion, tabPanel ]
		} );
	} );