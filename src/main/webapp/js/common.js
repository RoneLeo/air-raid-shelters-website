var ServerUrl = 'http://47.96.85.104:80';
var Gsid = 2;

$(function () {
    getCompanyInfo();
    createMenu();

    if(1){
        $('#appCss').prop('href','css/red.css');
    }
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
    $.post(ServerUrl + '/contactUs/findAllByGsid',{gsid: Gsid,page:1,size:1},function (json) {
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

//获取banner信息
function banner() {
    $.post(ServerUrl + '/file/findAllByGsid',{gsid: Gsid,wjlx:2,page:1,size:10000},function (json) {
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
    $.post(ServerUrl + '/file/findAllByGsid',{gsid: Gsid,wjlx:1,page:1,size:10000},function (json) {
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
            $.post(ServerUrl + '/netService/addFront',formData,function (json) {
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

//招聘信息
function recruitment() {
    $.post(ServerUrl + '/recruitment/findAllByGsid',{gsid:Gsid,page:1,size:1000},function (json) {
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
        $.post(ServerUrl + '/recruitment/findById',{id:id},function (json) {
            var data = json.data;
            var xxsm = data.xxsm;
            $('#recruitmentDetails').html(xxsm);
            $('#positionName').text(data.zpgw);
            $('#publicTime').text(data.cjsj);
        })
    }
}

//工程案例
function projectCase() {
    getCaseData(false);
}
function getCaseData(isPage,page) {
    var page = page || 1;
    var size = 12;//一页显示多少条数据
    $.post(ServerUrl + '/projectCase/findAllByGsid',{gsid: Gsid,page:page,size:size},function (json) {
        var content = json.data.content;
        var pages = json.data.totalPages;
        var totalElements = json.data.totalElements;
        if(pages && !isPage && (size < totalElements)){
            $("#casePage").page({
                pages: pages, //页数
                curr: 1, //当前页
                type: 'default', //主题
                groups: 5, //连续显示分页数
                prev: '<', //若不显示，设置false即可
                next: '>', //若不显示，设置false即可
                first: "首页",
                last: "尾页", //false则不显示
                jump: function(context, first) {
                    var curr = context.option.curr;
                    getCaseData(true,curr);
                }
            });
        }else{
            var projectCaseList = '';
            for(var i=0;i<content.length;i++){
                var lastClass = '';
                var item = content[i];
                var gcmc = item.gcmc;
                var tp = item.tp;
                if((i+1)%4 == 0){
                    lastClass = 'product_main_li4';
                }
                projectCaseList += '<li class="'+lastClass+'">\n' +
                    '<a href="projectDetails.html?id='+item.id+'">\n' +
                    '    <div class="product_main_li c">\n' +
                    '        <div class="product_main_img c">\n' +
                    '            <img src="'+ ServerUrl + tp +'">\n' +
                    '        </div>\n' +
                    '        <div class="product_main_name c">'+gcmc+'</div>\n' +
                    '    </div>\n' +
                    '</a>\n' +
                    '</li>'
            }
            $('#projectCaseList').html(projectCaseList);
        }
    });
}

//工程案例详情
function projectCaseDetails(){
    var id = getUrlParam().id;
    if(id){
        $.post(ServerUrl + '/projectCase/findById',{id:id},function (json) {
            var data = json.data;
            $('#caseName').html(data.gcmc);
            $('#caseImg').prop('src',ServerUrl + data.tp);
            $('#caseTxt').html(data.aljs);
            $('#caseTime').text(data.cjsj);
        });
    }
}

//news
function news(type) {
    getNewsData(type,false);

}
//getNewsData
function getNewsData(type,isPage,page) {
    var page = page || 1;
    var size = 10; //一页显示多少条数据
    var detailsPage = 'companyNewsDetails.html';
    if(type == 2){
        detailsPage = 'industryNewsDetails.html'
    }
    $.post(ServerUrl + '/news/findAllByGsid',{gsid:Gsid,xwlx:type,page:page,size:size},function (json) {
        console.log('news',json);
        var content = json.data.content;
        var pages = json.data.totalPages;
        var totalElements = json.data.totalElements;
        //加载分页效果
        if(pages && !isPage && (size < totalElements)){
            $("#newsPage").page({
                pages: pages, //页数
                curr: 1, //当前页
                type: 'default', //主题
                groups: 5, //连续显示分页数
                prev: '<', //若不显示，设置false即可
                next: '>', //若不显示，设置false即可
                first: "首页",
                last: "尾页", //false则不显示
                jump: function(context, first) {
                    var curr = context.option.curr;
                    getNewsData(type,true,curr);
                }
            });
        }else{
            var newsList = '';
            for(var i=0;i<content.length;i++){
                var item = content[i];
                newsList += '<li>\n' +
                    '<a href="'+detailsPage+'?id='+item.id+'">\n' +
                    '    <div class="n_main_con_list c">\n' +
                    '        <div class="n_main_con_title c">'+item.xwbt+'</div>\n' +
                    '        <div class="n_main_con_js c">'+item.xwnr+'</div>\n' +
                    '        <div class="n_main_con_time c">'+item.cjsj+'</div>\n' +
                    '    </div>\n' +
                    '</a>\n' +
                '</li>'
            }
            $('#newsList').html(newsList);
        }
    })
}

//新闻详情
function newsDetails() {
    var id = getUrlParam().id;
    if(id){
        $.post(ServerUrl + '/news/findById',{id:id},function (json) {
            var data = json.data;
            $('#newsTitle').html(data.xwbt);
            $('#newsImg').prop('src', ServerUrl + data.xwtp);
            $('#newsContent').text(data.xwnr);
            $('#newsTime').text(data.cjsj);
        });
    }
}

//product
function product() {
    //获取全部设备类型
    $.post(ServerUrl + '/equipmentType/findByGsid',{gsid:Gsid},function (json) {
        var data = json.data;
        var productType = '';
        for(var i=0;i<data.length;i++){
            var item = data[i];
            productType += '<p class="n_main_nav_erji_p">\n' +
                '<a class="product-type-item" style="cursor: pointer;"  typeId="'+item.id+'">'+item.name+'</a>\n' +
            '</p>';
        }
        //点击设备类型
        $('#productType').html(productType).off('click.a').on('click.a','.product-type-item',function () {
            var $this = $(this);
            $('.product-type-item').removeClass('n_main_nav_li_now');
            $this.addClass('n_main_nav_li_now');
            var typeId = $this.attr('typeId');
            var productType2 = $this.text();
            $('#productType2,#productType3,#productType4').text(productType2);
            $('#productListBox').show();
            $('#productDetailsBox').hide();
            getProduct(typeId,false);
        }).find('.product-type-item')[0].click();
    });

    $('#productType2').off('click').on('click',function () {
        $('#productListBox').show();
        $('#productDetailsBox').hide();
    })
}
function getProduct(sblx,isPage,page) {
    var size = 3;
    var page = page || 1;
    $.post(ServerUrl + '/equipment/frontFindAllByGsidAndSblx',{gsid:Gsid,sblx:sblx,page:page,size:size},function (json) {
        var data = json.data;
        var pages = json.totalPages;
        var totalElements = json.totalElements;
        console.log(pages,json);
        if(!isPage && (size < totalElements)){
            $("#productPage").page({
                pages: pages, //页数
                curr: 1, //当前页
                type: 'default', //主题
                groups: 5, //连续显示分页数
                prev: '<', //若不显示，设置false即可
                next: '>', //若不显示，设置false即可
                first: "首页",
                last: "尾页", //false则不显示
                jump: function(context, first) {
                    var curr = context.option.curr;
                    getProduct(sblx,true,curr);
                }
            });
        }else{
            var productList = '';
            for(var i=0;i<data.length;i++){
                var liClass = '';
                if((i+1)%4 == 0){
                    liClass = 'product_main_li4';
                }
                var item = data[i];
                var imgSrc = ServerUrl + item.cptp;
                var cpmc = item.cpmc;
                productList += '<li index="'+i+'" class="product-item '+liClass+'">\n' +
                    '<a style="cursor: pointer">\n' +
                    '    <div class="product_main_li c">\n' +
                    '        <div class="product_main_img c"><img src="'+imgSrc+'" alt=""></div>\n' +
                    '        <div class="product_main_name c">'+cpmc+'</div>\n' +
                    '    </div>\n' +
                    '</a>\n' +
                '</li>';
                
            }
            $('#productList').html(productList).off('click.a').on('click.a','.product-item',function () {
                var index = $(this).attr('index');
                var product = data[index];
                var name = product.cpmc;
                var productDetailsArr = product.cpxq;
                var imgSrc = ServerUrl + product.cptp;
                $('#productImg,#productImg2').prop('src',imgSrc);
                $('#productName,#productName2').text(name);
                $('#productListBox').hide();
                $('#productDetailsBox').show();
                $('#productIntroTitle').text(productDetailsArr[0].bt);
                $('#productIntroContent').html(productDetailsArr[0].xxnr);
                var productIntroTitleList = '';
                var productIntroContentList = '';
                for(var i=0;i<productDetailsArr.length;i++){
                    var item = productDetailsArr[i];
                    productIntroTitleList += '<li class="">'+item.bt+'</li>';
                    productIntroContentList += '<h1>'+item.bt+'</h1><p>'+item.xxnr+'</p>'
                }
                $('#productIntroTitleList').html(productIntroTitleList);
                $('#productIntroContentList').html(productIntroContentList);
                animateScroll();
                console.log(product);
            });
        }


    })
}
function animateScroll() {
    $('.show_chanpin_nav_ul li').each(function (i) {
        $(this).click(function () {
            $('html,body').animate({scrollTop: $('.show_chanpin_content_box h1').eq(i).offset().top - 80}, 500);
            $('.show_chanpin_nav_ul li:not(:eq(i))').removeClass('show_chanpin_nav_li_select');
            $(this).addClass('show_chanpin_nav_li_select');
        })
    })
}

function getInfoForHome() {
    //产品
    $.post(ServerUrl + '/equipment/frontFindAllByGsidAndSblx',{gsid:Gsid,sblx:'',page:1,size:4},function (json){
        var data = json.data;
        if(data.length) {
            $('#productListHome').find('li').each(function (index) {
                var $this = $(this);
                $this.find('.product_list_li_bg span').text(data[index].cpmc);
                $this.find('.product_list_li_name').text(data[index].cpmc);
                $this.find('img').prop('src',ServerUrl + data[index].cptp);
            });
        }
    });

    //新闻
    $.post(ServerUrl + '/news/findAllByGsid',{gsid:Gsid,xwlx:'',page:1,size:10},function (json) {
        var newsType = {1: '公司新闻',2: '行业新闻'};
        var data = [];
        var newsListHome = '';
        var newsListHome2 = '';
        for(let i = 0; i < json.data.content.length; i ++) {
            if(json.data.content[i].xwtp) {
                data.push(json.data.content[i]);
            }
        }
        for(var i=0;i<data.length;i++){
            var item = data[i];
            var type = newsType[item.xwlx];
            var title = item.xwbt;
            var time = item.cjsj;
            var content = item.xwnr;
            var imgSrc = ServerUrl + item.xwtp;
            var id = item.id;
            var detailsUrl = 'companyNewsDetails.html?id=' + id;
            if(item.xwlx == 2){
                detailsUrl = 'industryNewsDetails.html?id=' + id;
            }

            if(i<2){
                newsListHome += '<li>\n' +
                    '<a href="'+detailsUrl+'">\n' +
                    '<div class="news_list_li c">\n' +
                    '    <div class="news_list_li_img"><img src="'+imgSrc+'"></div>\n' +
                    '    <div class="news_list_li_con">\n' +
                    '        <div class="news_list_li_name c"><span> ['+type+'] </span>'+title+'</div>\n' +
                    '        <div class="news_list_li_time c">'+time+'</div>\n' +
                    '        <div class="news_list_li_js c">'+content+'</div>\n' +
                    '        <div class="news_list_li_more c">[ 查看详情 ]</div>\n' +
                    '    </div>\n' +
                    '</div>\n' +
                    '</a>\n' +
                '</li>';
            }else{
                var scrollCount = data.length- 2;
                var curr = i + 1 - 2;
                    newsListHome2 += '<li>\n' +
                        '<a href="'+detailsUrl+'">\n' +
                        '<div class="news_list_ban_li c" style="background: url('+imgSrc+') no-repeat center;">\n' +
                        '    <div class="am-slider-desc">\n' +
                        '        <div class="am-slider-counter"><span class="am-active">'+curr+'</span>/'+scrollCount+'</div>\n' + title +
                        '    </div>\n' +
                        '</div>\n' +
                        '</a>\n' +
                        '</li>';
            }
        }
        $('#newsListHome').html(newsListHome);
        $('#newsListHome2').html(newsListHome2);
        $('#newsListHome22').flexslider()

    });

    //案例
    $.post(ServerUrl + '/projectCase/findAllByGsid',{gsid:Gsid,page:1,size:3},function (json){
        var data = json.data.content;
        var projectListHome = ''
        for(var i=0;i<data.length;i++){
            var imgSrc = ServerUrl + data[i].tp;
            projectListHome += '<li>\n' +
                '<a href="projectDetails.html?id='+data[i].id+'">\n' +
                '    <div class="application_li c">\n' +
                '        <div class="application_li_img c"><img style="width:100%;height: 100%" src="'+imgSrc+'" ></div>\n' +
                '        <div class="application_li_name c">'+data[i].gcmc+'</div>\n' +
                '    </div>\n' +
                '</a>\n' +
            '</li>'
        }
        $('#projectListHome').html(projectListHome);
        $('#projectListHome').parent().flexslider({itemWidth: 384, itemMargin: 22, slideshow: false});

    });
}





//联系我们地图配置
//创建和初始化地图函数：
var map;
function initMap(companyInfo) {
    var jwdArr = companyInfo.jwd.split(',');
    var companyPosition = {lat: jwdArr[0], lng: jwdArr[1]}; //经纬度
    var companyName = companyInfo.gsmc; //名称
    var companyAddr = companyInfo.lxdz; //地址
    createMap(companyPosition);//创建地图
    // setMapEvent();//设置地图事件
    addMapControl();//向地图添加控件
    addMapOverlay(companyName,companyAddr,companyPosition);//向地图添加覆盖物
}

function createMap(companyPosition) {
    map = new AMap.Map('map', {
        zoom: 16,
    })
    map.setCenter(new AMap.LngLat(companyPosition.lng,companyPosition.lat));
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
    var marker = new AMap.Marker({
        icon: "//vdata.amap.com/icons/b18/1/2.png",
        position: new AMap.LngLat(companyPosition.lng,companyPosition.lat),
        offset: new AMap.Pixel(-12, -12)
    });
    marker.setMap(map);

    var text = new AMap.Text({
        text:companyName,
        textAlign:'center', // 'left' 'right', 'center',
        verticalAlign:'middle', //middle 、bottom
        draggable:false,
        cursor:'pointer',
        angle:0,
        style:{
            'padding': '.1rem .2rem',
            'border-radius': '.15rem',
            'background-color': 'transparent',
            'width': 'auto',
            'border': '2px solid red',
            'text-align': 'center',
            'font-size': '14px',
            'color': 'black'
        },
        position: new AMap.LngLat(companyPosition.lng,companyPosition.lat),
        offset: new AMap.Pixel(105, 10),
    });
    text.setMap(map);
}

//向地图添加控件
function addMapControl() {
    AMap.plugin([
        'AMap.ToolBar',
    ], function(){
        // 在图面添加工具条控件，工具条控件集成了缩放、平移、定位等功能按钮在内的组合控件
        map.addControl(new AMap.ToolBar({
            // 简易缩放模式，默认为 false
            liteStyle: true
        }));
    });
}

//获取页面参数
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

