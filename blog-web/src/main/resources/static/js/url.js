//下载文件
function operation(value, row, index)
{
    if($("#isSupper").attr("value") == 'N'){
        if($("#userId").attr("value") != row.fileId){
            if($("#money").attr("value") < row.money ){
                return "零钱不足，请充值下载币，或者充值会员"
            }
        }
    }
    //return "<a href='http://shenzc.blog.com/MyFile/" + value + "' >点击下载</a>";
    return "<a href='http://localhost:9009/download/"+row.fileId+"'>点击下载</a>";

}