$(function()
{
    function showMain(){
        $.getJSON('themes/flcms/js/json/wuhan.json', function (data) {
            echarts.registerMap('wuhan', data);
            if(document.getElementById('map') === null) return;
            var chart = echarts.init(document.getElementById('map'));
            chart.setOption({
                title: {
                    text: "武汉市示范妇女之家",
                    left: "center",
                    textStyle: {
                        fontFamily: "'PingFang SC', 'Open Sans', Arial, 'Microsoft YaHei', 微软雅黑, STHeiti, SimSun, Helvetica, sans-serif",
                        color: '#58666e',
                        fontSize: '24'
                    }
                },
                legend: {},
                series: [{
                    type: 'map',
                    map: 'wuhan',
                    itemStyle: {
                        normal: {
                            borderColor: '#ff8c9f',
                            areaColor: '#ffced6',
                        },
                        emphasis: {
                            areaColor: '#ff8c9f',
                            borderWidth: 0
                        }
                    },
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                color: '#000'
                            }
                        }
                    },
                    selectedMode : 'single',
                    data:categoryList
                }],
                tooltip: {
                    trigger: 'item',
                    formatter: '{b}<br/>{c}个示范妇女之家'
                }
            });
            chart.on('click', function (params) {
				getMapArticle(params);
            });
            $(window).resize(function(){
                chart.resize();
            });
        });
    }

    function showChild(info){
        $.getJSON('themes/flcms/js/json/'+ info.data.en +'.json', function (data) {
            echarts.registerMap(info.data.en, data);

            var chart = echarts.init(document.getElementById('map'));
            chart.setOption({
                title: {
                    text: info.name + "示范妇女之家",
                    left: "center",
                    textStyle: {
                        fontFamily: "'PingFang SC', 'Open Sans', Arial, 'Microsoft YaHei', 微软雅黑, STHeiti, SimSun, Helvetica, sans-serif",
                        color: '#58666e',
                        fontSize: '24'
                    }
                },
                series: [{
                    type: 'map',
                    map: info.data.en,
                    label: {
                        normal: {
                            show: true
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                color: '#000'
                            }
                        }
                    },
                    itemStyle: {
                        normal: {
                            borderColor: '#ff8c9f',
                            areaColor: '#ffced6',
                        },
                        emphasis: {
                            areaColor: '#ff8c9f',
                            borderWidth: 0
                        }
                    },
                    data: info.data
                }]
            });
            chart.on('click', function (params) {
                showMain();
                var _h = $("#map").height();
                $("#map").css({"height": _h*2 + "px"});
                $("#info").css({"display": "none"});
            });
            $(window).resize(function(){
                chart.resize();
            });
        });
    }
    function setInfo(info){
    	var _tem;
        $("#info").empty();
        $("#info").append("<div class='row'></div>");
        for(var i=0; i<info.length; i++){
        	_tem = '<div class="col-md-6 m-b"> \n' +
        		'	<div class="fam-banner"> \n' +
        		'		<a class="h-main tras-01 padder-v-xxs padder block" href="article/' + info[i].id + '.html"> \n' +
        		'			<div class="text-ellipsis">'+ info[i].title +'</div> \n' +
        		'		</a> \n' +
        		'	</div> \n' +
        		'</div>';
            $("#info .row").append(_tem);
        }
    }

    var categoryList = [];
    var articleList = [];
    function getMapArticle(params)
    {
    	var cid = params.data.id;
        $.ajax({
            type : "GET",
            contentType : "text/plain;charset=utf-8",
            url : "front/article/findMapArticle.do?cid=" + cid,
            success : function(data)
            {
                if (data.code == 200)
                {
                    var article = data.article;
                    articleList = [];
                    if (article != null && article.length > 0)
                    {
                        for(var i=0; i<article.length; i++)
                        {
                            articleList.push(article[i]);
                        }
                        var _h = $("#map").height();
        				$("#map").css({"height": _h/2 + "px"});
        				$("#info").css({"display": "block"});
        				$("#info").css({"height": _h/2 + "px"});
						showChild(params);
						setInfo(articleList);
                    }
                }
                else if (data.code == 500)
                {
                    alert('数据加载异常：' + JSON.stringify(data.error));
                }
            },
            error : function ()
            {
            	console.error("getMapArticle");
            }
        });
    }

    function getMapCategory(pid)
    {
        /* 加载内容 */
        $.ajax({
            type : "GET",
            contentType : "text/plain;charset=utf-8",
            url : "front/category/findMapCategory.do?pid=" + pid,
            success : function(data)
            {
                if (data.code == 200)
                {
                    var category = data.category;
                    if(category != null && category.length > 0)
                    {
                        for (var i=0; i<category.length; i++)
                        {
                        	categoryList.push(category[i]);
                        }
            			showMain();
                    }
                }
                else if (data.code == 500)
                {
                    alert('数据加载异常：' + JSON.stringify(data.error));
                }
            },
            error : function ()
            {
                console.error("getMapCategory");
            }
        });
    }
    getMapCategory(78);
});
