
function addTab(node){
	if ($('#center-tabs').tabs('exists', node.text)){
		$('#center-tabs').tabs('select', node.text);
	} else {
		top.$.messager.progress({
			msg:'页面加载中...',
			interval: 100
		});
		
		var content = '<iframe id="showPage-'+node.id+'" scrolling="auto" frameborder="0"  src="'+node.action+'" style="width:100%;height:100%;"></iframe>';
		$('#center-tabs').tabs('add',{
			title:node.text,
			pill: true,
			iconCls: node.iconCls,
			content:content,
			closable:true
		});
		
		// iframe加载完成后关闭进度条
		$('#showPage-'+node.id).load(function(){
			setTimeout(function(){
				top.$.messager.progress('close');
			}, 500);
		});
	}
}

/*function addTab(node){
	if(node.attributes.type==CommMode.navigate){
		return;
	}
	var tabObj = $('#center-tabs');
	if(tabObj.tabs('exists', node.text)){
		tabObj.tabs('select', node.text);
		var tab = tabObj.tabs('getSelected');
		tabObj.tabs('update', {
			tab: tab,
			options: {
				content : '<iframe src="'+phenix.getProjectPath()+node.attributes.url+'?_doc='+node.attributes.suffix+'" frameborder="0" style="border:0;padding:1px;width:99.5%;height:99.5%;"></iframe>'
			}
		});
		return;
	}
	top.$.messager.progress({
		msg:'页面加载中....',
		interval: 100
	});
	tabObj.tabs('add', {
		title: node.text,
		content : '<iframe src="'+phenix.getProjectPath()+node.attributes.url+'?_doc='+node.attributes.suffix+'" frameborder="0" style="border:0;padding:1px;width:99.5%;height:99.5%;"></iframe>',
		closable: true
	});
}*/