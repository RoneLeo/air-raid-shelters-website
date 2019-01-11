$(function(){
    function sizeIn(){
        var sizeCont = parseInt($(".newshow_cont").css("fontSize")); // 获取原设定的font-size的值
        if(sizeCont == 30){ // 判断font-size增大到30像素时停止
            $(".newshow_cont").css({fontSize:sizeCont});
        }else{
            $(".newshow_cont").css({fontSize:sizeCont + 2});
        }
    }
    function sizeOut(){
        var sizeCont = parseInt($(".newshow_cont").css("fontSize"));
        if(sizeCont == 12){ // 判断font-size减小到10像素时停止
            $(".newshow_cont").css({fontSize:sizeCont});
        }else{
            $(".newshow_cont").css({fontSize:sizeCont - 2});
        }
    }
    function sizeDefault(){
        $(".newshow_cont").css({fontSize:""})
    }
    $(".newshow_ctrl a").click(function(){
        if($(this).index() == 0){
            sizeIn();
        }else if($(this).index() == 1){
            sizeOut();
        }else{
            sizeDefault();
        }

    })
});