var ServerUrl = 'http://182.151.22.247:8081/';
var Gsid = 1;

$(function () {
    getCompanyInfo();
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
        {id: 'goIn',name: '<span class="go-in">走进公司</span>',children:[
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
function getCompanyInfo() {
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
        var lxyx = content.lxyx;
        var baxx = content.baxx;
        var gsgjz = content.gjz;
        var gsms = content.gsms;
        var goInTxt = gsmc.substr(2,2);
        //顶部信息
        var head = '<div class="head_logo">\n' +
            '<a href="index.html">\n' +
            '<img class="head_logo_img" src="img/rf-logo.png" alt="">' + gsmc +
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
        $('#headTitle,#contactName').text(gsmc);
        $('#headKeywords').attr('content',gsgjz);
        $('#headDescription').attr('content',gsms);

        //公共底部信息
        $('#footerPhone,#contactPhone').text(lxdh);
        $('#footerAddress,#contactAddress').text(lxdz);
        $('#footerUrl,#contactUrl').text(gswz);
        $('#footerFax,#contactFax').text(lxcz);
        $('#footerPostcode,#contactPostcode').text(lxyb);
        $('#contactEmail').text(lxyx);
        $('#baxx').text(baxx);

        //公司简介
        $('#introContent').html(gsjj);
        $('.go-in').text('走进' + goInTxt);

        //初始化地图
        if($('#map').length){
            initMap(content);
        }

    });
}

function createBaner() {
    $.post(ServerUrl + 'file/findAllByGsid',{gsid: Gsid,wjlx:2,page:1,size:10000},function (json) {
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

//提交网上服务信息
function submitService() {
    $('#submitBtn').off('click').on('click',function () {
        var $this = $(this);
        var isValidate = true;
        var formData = {};
        var $form = $('#submitForm').serializeArray();
        $.each($form, function() {
            if(this.value){
                formData[this.name] = this.value;
            }else {
                isValidate = false;
            }
        });
        if(isValidate){
            formData.gsid = Gsid;
            $this.prop('disabled',true).val('提交中...');
            $.AMUI.progress.start();
            $.post(ServerUrl + 'netService/addFront',formData,function (json) {
                if(json.resCode == 200){
                    alert('信息提交成功!');
                }
                console.log(json);

                $this.val('提交').prop('disabled',false);
                $.AMUI.progress.done();
                $('#submitForm')[0].reset();
            });
        }
        else {
            alert('请完整填写咨询信息,谢谢!');
        }
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

//招聘信息
function recruitment() {
    $.post(ServerUrl + 'recruitment/findAllByGsid',{gsid:Gsid,page:1,size:1000},function (json) {
        var content = json.data.content;
        var recruitmentTable = '';
        for(var i=0;i<content.length;i++){
            var item = content[i];
            var zpgw = item.zpgw;
            var zpbm = item.zpbm;
            var xzdy = item.xzdy;
            var zpqx = item.zpqx;
            var id = item.id;
            recruitmentTable += '<tr class="">\n' +
                '<td><a href="recruitmentDetails.html?id='+id+'">'+zpgw+'</a></td>\n' +
                '<td><a href="recruitmentDetails.html?id='+id+'">'+zpbm+'</a></td>\n' +
                '<td><a href="recruitmentDetails.html?id='+id+'">'+xzdy+'</a></td>\n' +
                '<td><a href="recruitmentDetails.html?id='+id+'">'+zpqx+'</a></td>\n' +
            '</tr>'
        }
        $('#recruitmentTable').html(recruitmentTable);
    });
}

//招聘信息详情
function recruitmentDetails(){
    var id = getUrlParam().id;
    if(id){
        $.post(ServerUrl + 'recruitment/findById',{id:id},function (json) {
            var data = json.data;
            var xxsm = data.xxsm;
            $('#recruitmentDetails').html(xxsm);
            $('#positionName').text(data.zpgw);
            $('#publicTime').text(data.cjsj);
        })
    }
}








//联系我们地图配置
//创建和初始化地图函数：
var map;
function initMap(companyInfo) {
    var companyPosition = {lat: 30.5520405517, lng: 104.0737911656}; //经纬度
    var companyName = '四川鑫旺人防科技有限公司'; //名称
    var companyAddr = '四川省成都市高新区天府三街69号新希望国际B座28楼2811号'; //地址
    createMap(companyPosition);//创建地图
    setMapEvent();//设置地图事件
    addMapControl();//向地图添加控件
    addMapOverlay(companyName,companyAddr,companyPosition);//向地图添加覆盖物
}

function createMap(companyPosition) {
    map = new BMap.Map("map");
    map.centerAndZoom(new BMap.Point(companyPosition.lng, companyPosition.lat), 16);
}

function setMapEvent() {
    map.enableScrollWheelZoom();
    map.enableKeyboard();
    map.enableDragging();
    map.enableDoubleClickZoom()
}

function addClickHandler(target, window) {
    target.addEventListener("click", function () {
        target.openInfoWindow(window);
    });
}

function addMapOverlay(companyName,companyAddr,companyPosition) {
    var markers = [
        {
            title: companyName,
            content: companyAddr,
            position: companyPosition,
            imageOffset: {width: -46, height: -21}
        }
    ];
    for (var index = 0; index < markers.length; index++) {
        var point = new BMap.Point(markers[index].position.lng, markers[index].position.lat);
        var marker = new BMap.Marker(point, {
            icon: new BMap.Icon("http://api.map.baidu.com/lbsapi/createmap/images/icon.png", new BMap.Size(20, 25), {
                imageOffset: new BMap.Size(markers[index].imageOffset.width, markers[index].imageOffset.height)
            })
        });
        var label = new BMap.Label(markers[index].title, {offset: new BMap.Size(25, 5)});
        var opts = {
            width: 200,
            title: markers[index].title,
            enableMessage: false
        };
        var infoWindow = new BMap.InfoWindow(markers[index].content, opts);
        marker.setLabel(label);
        addClickHandler(marker, infoWindow);
        map.addOverlay(marker);
    }
}

//向地图添加控件
function addMapControl() {
    var scaleControl = new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_LEFT});
    scaleControl.setUnit(BMAP_UNIT_IMPERIAL);
    map.addControl(scaleControl);
    var navControl = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_LEFT, type: 0});
    map.addControl(navControl);
    var overviewControl = new BMap.OverviewMapControl({
        anchor: BMAP_ANCHOR_BOTTOM_RIGHT,
        isOpen: false
    });
    map.addControl(overviewControl);
}

