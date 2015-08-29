+function($){
	$.crowbar.menu={
		child_template: '<li><a id="{0}" href="{1}"><i class="fa fa-circle-o"></i> {2}</a></li>',
		parent_template: '<li class="treeview">'
						+ '  <a id="{0}" href="{1}">'
						+ '    <i class="fa fa-folder"></i> <span>{2}</span>'
						+ '    <i class="fa fa-angle-left pull-right"></i>'
						+ '  </a>'
						+ '  <ul class="treeview-menu">{3}</ul>'
						+ '</li>',
		buildMenu: function(data){
			var html = "";
			for(var i in data){
				var node = data[i],
					isParent = (node.children && node.children.length>0),
					url = node.url?(node.url.startsWith("/")?(info.contextPath+node.url):node.url):'#';
				if(isParent){
		    		html += $.crowbar.menu.parent_template.format(node.id, url, node.text, $.crowbar.menu.buildMenu(node.children));
				}else{
		    		html += $.crowbar.menu.child_template.format(node.id, url, node.text);
				}
			}
			return html;
		}
	};
}(jQuery);

$(function(){
	$(".username").text(info.username);
	
	var menu = [
		{id:'1',text:'系统管理',url:"",children:[
			{id:'1-1',text:'用户管理',url:"/admin/user"},
			{id:'1-2',text:'二级菜单',url:""}
		]},
		{id:'2',text:'一级菜单',url:""}
	];
	
	$(".sidebar-menu").html('<li class="header">菜单</li>' + $.crowbar.menu.buildMenu(menu));
	$.AdminLTE.tree('.sidebar');
});

+function($){
	$.crowbar.tab={
		addTabs: function (obj) {
		    id = "tab_" + obj.id;
		    $(".crow-main-tabs .active").removeClass("active");
		     
		    //如果TAB不存在，创建一个新的TAB
		    if (!$(".crow-main-tabs #" + id)[0]) {
		        //固定TAB中IFRAME高度
		        mainHeight = $(".crow-main-tabs .tab-content").height();
		        //创建新TAB的title
		        title = '<li role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tab" data-toggle="tab">' + obj.title;
		        //是否允许关闭
		        if (obj.close) {
		            title += ' <i class="fa fa-close" tabclose="' + id + '"></i>';
		        }
		        title += '</a></li>';
		        //是否指定TAB内容
		        if (obj.content) {
		            content = '<div role="tabpanel" class="tab-pane" id="' + id + '">' + obj.content + '</div>';
		        } else {//没有内容，使用IFRAME打开链接
		            content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe src="' + obj.url + '" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe></div>';
		        }
		        //加入TABS
		        $(".crow-main-tabs>.nav-tabs").append(title);
		        $(".crow-main-tabs>.tab-content").append(content);
		    }
		     
		    //激活TAB
		    $("#tab_" + id).addClass('active');
		    $("#" + id).addClass("active");
		},
		closeTab: function (id) {
		    //如果关闭的是当前激活的TAB，激活他的前一个TAB
		    if ($("li.active").attr('id') == "tab_" + id) {
		        $("#tab_" + id).prev().addClass('active');
		        $("#" + id).prev().addClass('active');
		    }
		    //关闭TAB
		    $("#tab_" + id).remove();
		    $("#" + id).remove();
		}
	};
}(jQuery);


$(function () {
    $(".sidebar-menu").on("click", "a", function (e) {
    	if($(this).attr("href") && ($(this).attr("href")==="#")==false){
    		$.crowbar.tab.addTabs({id: $(this).attr("id"), title: $(this).text(), url: $(this).attr("href"), close: true});
    	}
        return false;
    });
 
    $(".crow-main-tabs>.nav-tabs").on("click", "[tabclose]", function (e) {
        id = $(this).attr("tabclose");
        $.crowbar.tab.closeTab(id);
    });
});


+function($){
	$.crowbar.fix = function(){
		var h_window = $(window).height();
		var h_header = $(".main-header").outerHeight(true);
		var h_footer = $(".main-footer").outerHeight(true);
		var h_tab = $(".crow-main-tabs .nav-tabs").outerHeight(true);
		$(".crow-main-tabs>.tab-content").css('height', h_window - h_header - h_footer - h_tab);
	};
}(jQuery);

$(function () {
	$.crowbar.fix();
	$(window).resize(function(){
		$.crowbar.fix();
	});
});