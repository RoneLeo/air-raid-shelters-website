$(function () {
    createHeadTop();
    createMenu();
});

function createMenu() {
    var href = location.href;
    var startIndex = href.lastIndexOf('/');
    var endIndex = href.lastIndexOf('.');
    var htmlName = href.substring(startIndex+1,endIndex);
    var menu = [
        {id: 'index',name: '网站首页',children:[]},
        {id: 'goIn',name: '走进鑫旺',children:[
            {id: 'intro',name: '公司简介'},
            {id: 'certificate',name: '资质证书'}
            ]
        },
        {id: 'product',name: '产品中心',children:[]},
        {id: 'project',name: '工程案例',children:[]},
        {id: 'news',name: '新闻动态',children:[
            {id: 'companyNews',name: '公司新闻'},
            {id: 'industryNews',name: '行业新闻'}
            ]
        },
        {id: 'recruitment',name: '人才招聘',children:[]},
        {id: 'contact',name: '联系我们',children:[
            {id: 'contactUs',name: '联系我们'},
            {id: 'service',name: '网上服务'}
            ]
        },
    ];
    var headNavList = '';
    for(var i=0;i<menu.length;i++){
        var menuI = menu[i];
        var menuId = menuI.id;
        var menuName = menuI.name;
        var subMenu = menuI.children;
        headNavList += '<li class="">';
        if(subMenu.length){
            headNavList +='<a href="'+subMenu[0].id+'.html">'+menuName+'</a>'
            headNavList += '<div class="erji c">';
            for(var j=0;j<subMenu.length;j++){
                var subName = subMenu[j].name;
                var subId = subMenu[j].id;
                headNavList += '<p><a href="'+subId+'.html">'+subName+'</a></p>\n'
            }
            headNavList += '</div>';
        }else{
            headNavList +='<a href="'+menuId+'.html">'+menuName+'</a>'
        }
        headNavList += '</li>';
    }
    $('#headNavList').html(headNavList);

}


function createHeadTop() {
    var head = '<div class="head_logo">\n' +
        '<a href="index.html">\n' +
        '<img class="head_logo_img" src="img/rf-logo.png" alt="鑫旺人防">' +
        '    四川鑫旺人防设备有限公司\n' +
        '</a>\n' +
        '</div>\n' +
        '<div class="head_tel">\n' +
        '<div class="head_tel_img"><img src="http://www.ytrsrf.com/images/tel.png" alt="烟台日升人防"></div>\n' +
        '<div class="head_tel_phone">\n' +
        '    <div class="head_tel_phone_a c">联系电话</div>\n' +
        '    <div class="head_tel_phone_b c">028-4210288</div>\n' +
        '</div>\n' +
        '</div>'
    $('#headTop').html(head);
}

///获取页面参数
function getUrlParam() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = {};
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        var strs = [];
        if(str.indexOf('&') != -1){
            strs = str.split("&");
        }
        else{
            strs[0] = str;
        }

        for(var i = 0; i < strs.length; i ++) {
            theRequest[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}