$(function () {
    var counter = 0;
    // 每页展示4个
    var num = 5;
    var pageStart = 0, pageEnd = 0;

    // dropload
    $('#pageData').dropload({
        scrollArea: window,
        domUp: {
            domClass: 'dropload-up',
            domRefresh: '<div class="dropload-refresh">↓下拉刷新-自定义内容</div>',
            domUpdate: '<div class="dropload-update">↑释放更新-自定义内容</div>',
            domLoad: '<div class="dropload-load"><span class="loading"></span>加载中-自定义内容...</div>'
        },
        domDown: {
            domClass: 'dropload-down',
            domRefresh: '<div class="dropload-refresh">↑上拉加载更多-自定义内容</div>',
            domLoad: '<div class="dropload-load"><span class="loading"></span>加载中-自定义内容...</div>',
            domNoData: '<div class="dropload-noData">暂无数据-自定义内容</div>'
        },
        //上拉加载
        loadUpFn: function (me) {
            $.ajax({
                type: 'POST',
                url: httpurl + '/api/devAll',
                data: {pageSize: num, pageIndex: 0},
                dataType: 'json',
                success: function (data) {
                    var result;
                    for (var i = 0; i < data.data.length; i++) {
                        result += ['<tr><td class="one-font">', data.data[i].devHouseName, '<p class="two-font">', data.data[i].viewDec, '</p></td><td class="tab1">预定', '</td><td class="tab2">', data.data[i].houseHomeType,
                            '</td><td class="css-img"><img src="', data.data[i].viewModiaUrl, '" class="img-responsive" width="60%"/></td><td class="s-tab"><span><p class="money-font">', data.data[i].viewMoney, '<i>单位</i></p></span><p><span class="label  label-success">',
                            '标签', '</span></p><p><span class="label  label-success">', '标签', '</span></p><p><span class="label  label-success">', '标签', '</span></p><p><span><img src="../../assets/img/dev/坐标@2x(1).png" alt="" class="img-responsive" width="15%"/><div class="add-css"><span>',
                            data.data[i].viewAdd, '</span><span>2.1km</span></div></span></p></td></tr>'].join('');
                    }
                    $('#pageData').html(result);
                    // 每次数据加载完，必须重置
                    me.resetload();
                    // 重置索引值，重新拼接more.json数据
                    counter = 0;
                    // 解锁
                    me.unlock();
                    me.noData(false);
                },
                error: function (xhr, type) {
                    alert('Ajax error!');
                    // 即使加载出错，也得重置
                    me.resetload();
                }
            });
        },

        //下拉刷新
        loadDownFn: function (me) {
            console.log("下拉了");
            $.ajax({
                type: 'POST',
                url: httpurl + '/api/devAll',
                data: {pageSize: num, pageIndex: pageStart},
                dataType: 'json',
                success: function (data) {
                    var result = '';
                    counter++;
                    pageEnd = num * counter;
                    pageStart = pageEnd - num;
                    for (var i = pageStart; i < pageEnd; i++) {
                        result += ['<tr><td class="one-font">', data.data[i].devHouseName, '<p class="two-font">', data.data[i].viewDec, '</p></td><td class="tab1">预定', '</td><td class="tab2">', data.data[i].houseHomeType,
                            '</td><td class="css-img"><img src="', data.data[i].viewModiaUrl, '" class="img-responsive" width="60%"/></td><td class="s-tab"><span><p class="money-font">', data.data[i].viewMoney, '<i>单位</i></p></span><p><span class="label  label-success">',
                            '标签', '</span></p><p><span class="label  label-success">', '标签', '</span></p><p><span class="label  label-success">', '标签', '</span></p><p><span><img src="../../assets/img/dev/坐标@2x(1).png" alt="" class="img-responsive" width="15%"/><div class="add-css"><span>',
                            data.data[i].viewAdd, '</span><span>2.1km</span></div></span></p></td></tr>'].join('');
                        if ((i + 1) >= data.data.length) {
                            // 锁定
                            me.lock();
                            // 无数据
                            me.noData();
                            break;
                        }
                    }

                    $('#pageData').append(result);
                    // 每次数据加载完，必须重置
                    me.resetload();
                },
                error: function (xhr, type) {
                    alert('Ajax error!');
                    // 即使加载出错，也得重置
                    me.resetload();
                }
            });
        },
        threshold: 50
    });
});
