$(function(){

    var str = $("#content").attr("value");
    var el = document.getElementById("content");
    el.innerHTML = str;

})

//点赞
function click(){
    alert("123")
    if($("#isLogin").attr("value") == null){
        alert("不能点赞，请先登陆");
        return
    }
    var praise_img = $("#zan");
    var praise_txt = $("#goods");
    var num=praise_txt.attr("value");
    if(praise_img.attr("src") == ("/image/yizan.png")){
        $(this).html("<img src='/image/zan.png' id='zan' class='animation' />");
        praise_txt.removeClass("hover");
        $(".add-animation").removeClass("hover");
        $.ajax({
            url:'/clickGoods?id='+$("#id").attr("value")+"&operate=decrease",
            success:function (data) {
                $("#goods").attr("value",data.message);
            }
        })
    }else{
        $(this).html("<img src='/image/yizan.png' id='zan' class='animation' />");
        praise_txt.addClass("hover");
        $(".add-animation").addClass("hover");
        $.ajax({
            url:'/clickGoods?id='+$("#id").attr("value")+"&operate=add",
            success:function (data) {
                $("#goods").attr("value",data.message);
            }
        })
    }
}


