/// <reference path="jquery-1.8.3.min.js" />
/// <reference path="config.js" />
/// <reference path="common.js" />

jc.uiExtend("header", {
    rootColumnId: jc.param.get("rootColumnId"),
    $menu: null,
    updateMenu: function () {
        var _this = this;

        var html = '';

        window.resource("cmsApiColumnList", {
            level: 1
        }, function (data) {

            var menuData = {};

            if (!data.length) return false;

            for (var i = 0, l = data.length; i < l; i++) {
                var curData = data[i];
                var spiltPath = curData.path.split("/");
                var spiltPathRoot = spiltPath[0];
                if (!menuData[spiltPathRoot]) {
                    menuData[spiltPathRoot] = [];
                }
                menuData[spiltPathRoot].push(curData);
            }

            _this.$menu.each(function (i, obj) {
                var $obj = $(obj);

                var dataRootColumnId = $obj.attr("data-root-column-id");

                var curMenuData = menuData[dataRootColumnId];

                if (!curMenuData) return false;

                for (var i = 0, l = curMenuData.length; i < l; i++) {
                    $obj.append('<li><a onclick="window.router(\'menuAndTextlist\',{ rootColumnId :\'' + dataRootColumnId + '\' , columnListId : \'' + curMenuData[i].id + '\' })" href="javascript:;">' + curMenuData[i].name + '</a></li>');
                }

            });


        });



    },
    template: function (data) {
        var html = ''

        /* orderNo 排序 */
        data = window.arraySortASC(data, "orderNo");

        html += '<div class="navbar navbar-default navbar-fixed-top">';
        html += '<div class="container">';
        html += '<div class="navbar-header page-scroll">';
        html += '<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">';
        html += '<span class="sr-only">Toggle navigation</span>';
        html += '<span class="icon-bar"></span>';
        html += '<span class="icon-bar"></span>';
        html += '<span class="icon-bar"></span>';
        html += '</button>';
        html += '<a class="navbar-brand" href="#page-top"><img class="img-responsive" src="../../static/images/logo.png" alt=""></a>';
        html += '</div>';
        html += '<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">';
        html += '<ul class="nav navbar-nav navbar-right">';
        html += '<li class="hidden active">';
        html += '<a href="#page-top"></a>';
        html += '</li>';


        for (var i = 0, l = data.length; i < l; i++) {

            var curData = data[i];
            var curDataId = curData.id;
            var curDataName = curData.name;

            var routerName = "";
            if (curDataName == "首页") {
                routerName = "index";
            }
            else {
                routerName = "menuAndTextlist";
            }

            var currentClass = "";

            if ((!this.rootColumnId && i == 0) || this.rootColumnId == curDataId) {
                currentClass = " current";
            }

            html += '<li data-current="' + (curDataId) + '" class="dropdown ' + (currentClass) + '">';
            html += '<a href="javascript:;" onclick="window.router(\'' + (routerName) + '\',{rootColumnId:\'' + (curDataId) + '\'},true)" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">' + (curDataName) + '<span class="caret"></span><span class="underline"></span></a>';

            html += '<ul data-root-column-id="' + curDataId + '" data-level="1" class="dropdown-menu" ' + (i == 0 ? 'style="display:none;"' : "") + '>';

            /*
            html += '<li><a href="javascript:;">学校简介</a></li>';
            html += '<li><a href="javascript:;">学校领导</a></li>';
            html += '<li><a href="javascript:;">校长寄语</a></li>';
            html += '<li><a href="javascript:;">组织机构</a></li>';
            html += '<li><a href="javascript:;">办学成就</a></li>';
            html += '<li><a href="javascript:;">领导关怀</a></li>';
            html += '<li><a href="javascript:;">校园风光</a></li>';
            */


            html += '</ul>';

            html += '</li>';

        }



        html += '</ul>';
        html += '</div>';
        html += '</div>';
        html += '</div>';



        return html;
    },
    setup: function (data) {

        var _this = this;

        this.getTemplate(data, function (html) {
            _this.$element.html(html);
            _this.$menu = _this.$element.find(".dropdown-menu");
            _this.updateMenu();
        });

        this.$element.on("mouseover", ".dropdown", function () {
            if (!jc.isMobile) {
                $(this).addClass("open");
            }
        });

        this.$element.on("mouseout", ".dropdown", function () {
            if (!jc.isMobile) {
                $(this).removeClass("open");
            }
        });


    },
    init: function () {
        var _this = this;
        window.resource("cmsApiColumnList", {
            level: 0
        }, function (data) {
            _this.setup(data);
        }, false);

    }


});

jc.uiExtend("footer", {
    template: function (data) {

        var _this = this;

        var html = '';


        html += '<div class="hidden-print">';
        html += '<div class="container">';
        html += '<div class="row">';
        html += '<div class="col-md-5 col-sm-12">';
        html += '<h4>关于 天梯</h4>';
        html += '<p class="mt20"><i class="icon"></i>地址：广东省广州市天河区五山路381号</p>'
        html += '<p><i class="icon"></i>邮箱：xuzhexu@139.com</p>'
        html += '</div>';

        html += '<div class="col-md-3 col-sm-12">';
        html += '<h4>网站链接</h4>';
        html += '<ul class="list-unstyled list-inline">';


        for (var i = 0, l = data.length; i < l; i++) {
            var curData = data[i];
            var curDataId = curData.id;
            var curDataName = curData.name;
            html += '<li><a onclick="window.router(\'menuAndTextlist\',{rootColumnId:\'' + (curDataId) + '\'},true)"  href="javascript:;" target="_blank">' + curDataName + '</a></li>';
        }
       


        html += '</ul>';
        html += '</div>';

        html += '<div class="col-md-2 col-sm-12">';
        html += '<div class="mt20 text-center">';
        html += '<img style="width:120px;" src="../../static/images/code_1.png">';
        html += '<p>(微信打赏)</p>';
        html += '</div>';
        html += '</div>';

        html += '</div>';
        html += '</div>';
        html += '<div class="copy-right">';
        html += '<span>© 2013-2017</span>';
        html += '版权所有 天梯 Copyright © 1998 - 2017 Tencent. All Rights Reserved';
        html += '<span>粤公网安备11010802014853</span>';
        html += '</div>';
        html += '</div>';




        /*
        <div class="hidden-print">
            <div class="container">
                <div class="row">
                    <div class="col-md-5 col-sm-12">
                        <h4>关于 BootCDN</h4>
                        <p>BootCDN 是 <a href="http://www.bootcss.com/" target="_blank">Bootstrap 中文网</a>和<a href="https://www.upyun.com/" target="_blank">又拍云</a>共同支持并维护的前端开源项目免费 CDN 服务，由<a href="https://www.upyun.com/" target="_blank">又拍云</a>提供全部 CDN 支持，致力于为 Bootstrap、jQuery、Angular 一样优秀的前端开源项目提供稳定、快速的免费 CDN 服务。BootCDN 所收录的开源项目主要同步于 <a href="https://github.com/cdnjs/cdnjs" target="_blank">cdnjs</a> 仓库。</p><p>自2013年10月31日上线以来已经为上万家网站提供了稳定、可靠的免费 CDN 服务。</p><p>反馈或建议请发送邮件至：cdn@bootcss.com</p>
                    </div>
                    <div class="col-md-2 col-sm-12">
                        <h4>友情链接</h4>
                        <ul class="list-unstyled">
                            <li><a href="http://www.bootcss.com/" target="_blank">Bootstrap 中文网</a></li>
                            <li><a href="http://www.ghostchina.com/" target="_blank">Ghost 中国</a></li>
                            <li><a href="http://www.golaravel.com/" target="_blank">Laravel 中文网</a></li>
                            <li><a href="http://www.jquery123.com/" target="_blank">jQuery 中文 API</a></li>
                            <li><a href="http://pkg.phpcomposer.com/" target="_blank">Packagist 中国镜像</a></li>
                            <li><a href="http://www.phpcomposer.com/" target="_blank">Composer 中文网</a></li>
                        </ul>
                    </div>
                    <div class="col-md-3 col-sm-12">
                        <h4>我们用到的技术</h4>
                        <ul class="list-unstyled list-inline">
                            <li><a href="http://www.bootcss.com/" target="_blank">Bootstrap</a></li>
                            <li><a href="http://www.ghostchina.com/" target="_blank">Ghost</a></li>
                            <li><a href="http://www.jquery123.com/" target="_blank">jQuery</a></li>
                            <li><a href="http://babeljs.cn/" target="_blank">Babeljs</a></li>
                            <li><a href="http://lodashjs.com/" target="_blank">Lodash</a></li>
                            <li><a href="http://www.nodeapp.cn/" target="_blank">Node</a></li>
                            <li><a href="http://www.gruntjs.net/" target="_blank">Grunt</a></li>
                            <li><a href="http://www.gulpjs.com.cn/" target="_blank">Gulp</a></li>
                            <li><a href="http://www.npmjs.com.cn/" target="_blank">NPM</a></li>
                            <li><a href="https://webpackjs.com/" target="_blank">webpack</a></li>
                        </ul>
                    </div>
                    <div class="col-md-2 col-sm-12">
                        <h4>动力源自</h4><p>
                            <a href="https://www.upyun.com/" style="border-bottom: none" target="_blank"><img src="/assets/img/Upyun_LOGO_300.png" style="width: 120px" alt="又拍云存储"></a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="copy-right">
                <span>© 2013-2017</span>
                <a href="http://www.miibeian.gov.cn/" target="_blank">京ICP备11008151号</a>
                <span>京公网安备11010802014853</span>
            </div>
        </div>
        */


        return html;



    },
    setup: function (data) {

        var _this = this;

        this.getTemplate(data, function (html) {
            _this.$element.html(html);
        });

    },

    init: function () {

        var _this = this;

        window.resource("cmsApiColumnList", {
            level: 0
        }, function (data) {
            _this.setup(data);
        }, false);

    }

});


jc.uiExtend("banner", {
    ajaxData: {
        columnId: null,
        pageSize: 0,
        currentPage: 1
    },
    setAttr: function (opt) {
        this.$element.attr(opt);
    },
    update: function () {
        var _this = this;

        this.ajaxData.columnId = this.$element.attr("data-id");
        this.ajaxData.pageSize = this.$element.attr("data-page-size") || 10;

        var data_path = this.$element.attr("data-path");

        if (!this.ajaxData.columnId || !data_path) return;

        window.resource(data_path, this.ajaxData, function (data) {
            _this.setup(data);
        });


    },
    setup: function (data) {

        var _this = this;
        this.getTemplate(data, function (html) {
            _this.$element.html(html);
            $("#" + this.id).carousel({
                pause: "hover",
                interval: 6000
            });
        });

    },
    init: function () {
        this.update();
    }

});


jc.uiExtend("titleStyle1", {
    template: function (data) {
        var html = '';

        html += '<div class="page-header clearfix">';
        html += '<div class="pull-left">';
        html += '<i></i>';
        html += '<h4>' + this.getString(data.data_title_text) + '</h4>';
        html += '</div>';
        html += '<div class="pull-right"><a href="' + this.getString(data.data_more_href) + '" title="' + this.getString(data.data_more_text) + '">' + this.getString(data.data_more_text) + '</a></div>';
        html += '</div>';

        return html;

    },
    setup: function (data) {

        var _this = this;
        this.getTemplate(data, function (html) {
            _this.$element.html(html);
        });

    },
    init: function () {
        var data_title_text = this.$element.attr("data-title-text");
        var data_more_text = this.$element.attr("data-more-text");
        var data_more_href = this.$element.attr("data-more-href");

        var data = {
            data_title_text: data_title_text,
            data_more_text: data_more_text,
            data_more_href: data_more_href
        }

        this.setup(data);
    }

});


jc.uiExtend("titleStyle2", {
    template: function (data) {
        var html = '';

        html += '<div class="page-header clearfix">';
        html += '<div class="pull-left">';
        html += '<i></i>';
        html += '<h4>' + this.getString(data.data_title_text) + '</h4>';
        html += '</div>';
        if (this.getString(data.data_title_text)) {
            html += '<div class="pull-right"><a href="' + this.getString(data.data_more_href) + '" title="' + this.getString(data.data_more_text) + '">' + this.getString(data.data_more_text) + '</a></div>';
        }
        html += '</div>';

        return html;

    },
    setup: function (data) {

        var _this = this;

        this.getTemplate(data, function (html) {
            _this.$element.html(html);
        });

    },
    init: function () {

        var data_title_text = this.$element.attr("data-title-text");
        var data_more_text = this.$element.attr("data-more-text");
        var data_more_href = this.$element.attr("data-more-href");

        var data = {
            data_title_text: data_title_text,
            data_more_text: data_more_text,
            data_more_href: data_more_href
        }

        this.setup(data);

    }

});


jc.uiExtend("textList", {
    ajaxData: {
        columnId: null,
        pageSize: 0,
        currentPage: 1
    },
    change: function (index, panel, _this) {
        _this.ajaxData.currentPage = index + 1;
        _this.update();
    },
    update: function () {
        var _this = this;


        this.ajaxData.columnId = this.$element.attr("data-id");
        this.ajaxData.pageSize = this.$element.attr("data-page-size") || 10;
        var data_path = this.$element.attr("data-path");


        window.resource(data_path, this.ajaxData, function (data) {
            _this.setup(data);
            _this.$page.pagination(data.totalCount, {
                proxy: _this,
                num_edge_entries: 1, //边缘页数
                num_display_entries: 6, //主体页数
                callback: _this.change,
                items_per_page: _this.ajaxData.pageSize, //每页显示1项
                prev_text: "前一页",
                next_text: "后一页",
                current_page: _this.ajaxData.currentPage - 1
            });
            if (data.list.length < 1) {
                this.$page.hide();
            }
        });



    },
    setup: function (data) {

        var _this = this;

        var id = this.$element.attr("data-id");

        if (id) {
            data.id = id;
        }

        _this.getTemplate(data, function (html) {
            _this.$list.html(html);

        });



    },
    init: function () {

        this.$element.append(jc.createDOM({ classname: "t_list" }));
        this.$element.append(jc.createDOM({ classname: "t_page" }));

        this.$list = this.$element.find(".t_list");
        this.$page = this.$element.find(".t_page");


        this.update();
    }

});


jc.uiExtend("mediaList", {
    setup: function (data) {

        var _this = this;

        this.getTemplate(data, function (html) {
            _this.$element.html(html);
        });

    },
    init: function () {

        this.setup();

    }

});


jc.uiExtend("imageList", {
    setup: function (data) {

        var _this = this;

        this.getTemplate(data, function (html) {
            _this.$element.html(html);
        });

    },
    init: function () {

        this.setup();

    }

});



jc.uiExtend("pageBanner", {
    init: function () {
        this.$element.html('<img src="../../static/cache/1.jpg" />');
    }
});



jc.uiExtend("menuList", {
    setup: function (data) {

        var _this = this;

        this.getTemplate(data, function (html) {
            _this.$element.html(html);
        });

    },
    init: function () {


    }

});




jc.uiExtend("alert", {
    template: function (str, title, btnText) {
        var html = '';

        html += '<div class="modal-dialog" role="document">';
        html += '<div class="modal-content">';
        html += '<div class="modal-header">';
        html += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
        html += '<h4 class="modal-title" id="gridSystemModalLabel">' + (title || '温馨提示') + '</h4>';
        html += '</div>';
        html += '<div class="modal-body">';
        html += str;
        html += '</div>';
        html += '<div class="modal-footer text-center">';
        html += '<div class="text-center">';
        html += '<button type="button" class="btn btn-primary" data-dismiss="modal" data-success="true">' + (btnText || '我知道了') + '</button>';
        html += '</div>';
        html += '</div>';
        html += '</div>';
        html += '</div>';


        return html;

    },
    show: function (str, fn, title, btnText) {

        this.fn = fn ? fn : null;

        this.$element.html(this.template(str, title, btnText));

        this.$element.modal('show');

    },
    init: function () {
        var _this = this;

        this.$element.on("click", "[data-success='true']", function (event) {
            if (_this.fn) _this.fn(true);
        });
    }
});




jc.uiExtend("confirm", {
    fn: null,
    template: function (str, title, btnCancelText, btnSuccessText) {
        var html = '';

        html += '<div class="modal-dialog" role="document">';
        html += '<div class="modal-content">';
        html += '<div class="modal-header">';
        html += '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
        html += '<h4 class="modal-title" id="gridSystemModalLabel">' + (title || '询问') + '</h4>';
        html += '</div>';
        html += '<div class="modal-body">';
        html += str;
        html += '</div>';
        html += '<div class="modal-footer text-center">';
        html += '<button type="button" class="btn btn-default" data-dismiss="modal" data-cancel="true">' + (btnCancelText || '取消') + '</button>';
        html += '<button type="button" class="btn btn-primary" data-dismiss="modal" data-success="true">' + (btnSuccessText || '我知道了') + '</button>';
        html += '</div>';
        html += '</div>';
        html += '</div>';


        return html;

    },
    show: function (str, title, fn, btnCancelText, btnSuccessText) {

        this.fn = fn ? fn : null;

        this.$element.html(this.template(str, title, btnCancelText, btnSuccessText));

        this.$element.modal('show');

    },
    init: function () {

        var _this = this;

        this.$element.on("click", "[data-cancel='true']", function (event) {
            if (_this.fn) _this.fn(false);
        });

        this.$element.on("click", "[data-success='true']", function (event) {
            if (_this.fn) _this.fn(true);
        });

    }
});


jc.uiExtend("detail", {
    id: null,
    setup: function (data) {

        var _this = this;

        this.id = data.id;

        this.articleDate = jc.tools.formatDate(data.createDate, 'yyyy-MM-dd hh:mm:ss');

        this.getTemplate(data, function (html) {
            _this.$element.html(html);
        });

    },
    next: function () {

        var _this = this;

        window.resource("cmsApiArticleNext", {
            columnId: window.columnListId,
            articleDate: this.articleDate,
            currentArticleId: this.id
        }, function (data) {
            if (!data) {
                jc.ui.alert.trigger("show", "没有下一篇了");
            }
            else {
                if (window.detailPrev) window.detailNext(data.id);
            }

        });

    },
    prev: function () {

        var _this = this;

        window.resource("cmsApiArticlePre", {
            columnId: window.columnListId,
            articleDate: this.articleDate,
            currentArticleId: this.id
        }, function (data) {
            if (!data) {
                jc.ui.alert.trigger("show", "没有上一篇了");
            }
            else {
                if (window.detailPrev) window.detailPrev(data.id);
            }

        });

    },
    init: function () {

        var _this = this;

        this.$element.on("click", "a", function () {
            var $this = $(this);
            var type = $this.attr("data-type");
            if (type == "prev") {
                _this.prev();
            }
            else if (type == "next") {
                _this.next();
            }

        });

    }
});

