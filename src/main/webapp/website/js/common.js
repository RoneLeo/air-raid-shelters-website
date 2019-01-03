var ServerUrl = 'http://182.151.22.247:8081/';
var Gsid = 1;

$(function () {
    createHeadTop();
    createMenu();
});

//菜单信息
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
        var navActiveClass = '';
        //菜单选中状态
        if(htmlName == menuId){
            navActiveClass = 'head_nav_li_select';
        }
        else if(subMenu.length && (htmlName == subMenu[0].id || htmlName == subMenu[1].id) ){
            navActiveClass = 'head_nav_li_select';
        }
        headNavList += '<li class="'+navActiveClass+'">';
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

//获取顶部和底部等公共
function createHeadTop() {
    $('#foot').load('template/footer.html');
    $.post(ServerUrl + 'contactUs/findAllByGsid',{gsid: Gsid,page:1,size:1},function (json) {
        var content = json.data.content[0];
        var gsmc = content.gsmc;
        var lxdh = content.lxdh;
        var lxcz = content.lxcz;
        var gswz = content.gswz;
        var gsjj = content.gsjj;
        var lxyb = content.lxyb;
        var lxdz = content.lxdz;
        var footerArchival = 'Copyright © 2017, Sichuan Xinwang Civil Air Defence Equipment Co., Ltd. All Rights Reserved. 四川鑫旺人防设备有限公司';

        //顶部信息
        var head = '<div class="head_logo">\n' +
            '<a href="index.html">\n' +
            '<img class="head_logo_img" src="img/rf-logo.png" alt="鑫旺人防">' + gsmc +
            '</a>\n' +
            '</div>\n' +
            '<div class="head_tel">\n' +
            '<div class="head_tel_img"><img src="http://www.ytrsrf.com/images/tel.png"></div>\n' +
            '<div class="head_tel_phone">\n' +
            '    <div class="head_tel_phone_a c">联系电话</div>\n' +
            '    <div class="head_tel_phone_b c">'+lxdh+'</div>\n' +
            '</div>\n' +
            '</div>'
        $('#headTop').html(head);

        //公共底部信息
        $('#footerPhone').text(lxdh);
        $('#footerAddress').text(lxdz);
        $('#footerUrl').text(gswz);
        $('#footerFax').text(lxcz);
        $('#footerPostcode').text(lxyb);
        $('#footerArchival').text(footerArchival);

        //公司简介
        $('#introContent').html(gsjj);

    });
}

function createBaner() {
    $.post(ServerUrl + 'file/findAllByGsid',{gsid: Gsid,wjlx:2,page:1,size:10000},function (json) {
        console.log(json.data)
        var content = json.data.content;
        if (!content.length) {
            return false;
        }
        var bannerList = '';
        for(var i=0;i<content.length;i++){
            var item = content[i];
            var url = ServerUrl + item.wjlj;
            var name = item.wjmc;
            bannerList += ' <li>\n' +
                '<div class="banner_li c" style="background: url(\''+url+'\') no-repeat center;"></div>\n' +
                '</li>'
        }
        $('#bannerList').html(bannerList);
        $('#bannerBox').flexslider();
    });


}

//资质证书
function createCertificate() {
    $.post(ServerUrl + 'file/findAllByGsid',{gsid: Gsid,wjlx:1,page:1,size:10000},function (json) {
        console.log(json.data)
        var content = json.data.content;
        if(!content.length){
            return false;
        }
        var certificateList = '';
        var li4Class = '';    
        for(var i=0;i<content.length;i++){
            if((i+1)%4 == 0){
                li4Class = 'product_main_li4';
            }
            var item = content[i];
            var name = item.wjmc;
            var url = ServerUrl + item.wjlj;
            certificateList += '<li class="'+li4Class+'">\n' +
                '<a href="'+url+'">\n' +
                '    <div class="product_main_li c">\n' +
                '        <div class="product_main_img c" style="line-height: 210px;"><img\n' +
                '                style="max-width: 180px;max-height: 180px;margin-top: 0;"\n' +
                '                src="'+url+'" alt="">\n' +
                '        </div>\n' +
                '        <div class="product_main_name c">'+name+'</div>\n' +
                '    </div>\n' +
                '</a>\n' +
            '</li>';
        }
        $('#certificateList').html(certificateList)
    });
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