/**
 * Created by dongbin on 2017/3/29.
 */
/**
 * 切换菜单 （loadArticle）
 *
 * @param id
 */
function getNewsDetail(id, template) {

    /* 加载内容 */
    $.ajax({
        type : "GET",
        contentType : "text/plain;charset=utf-8",
        url : "front/article/articleDetail.do?id=" + id,
        dataType : "json",
        success : function(data) {
            if (data.code == 200) {
                window.location.href = window.location.protocol + "//"
                    + window.location.host + "/" + template;// 你可以跟换里面的网
            } else if (data.code == 500) {
                alert('数据加载异常：' + JSON.stringify(data.error));
            }
        }
    });

}