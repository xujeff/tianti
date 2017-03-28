/// <reference path="jquery-1.8.3.min.js" />
/// <reference path="common.js" />
$(function () {
    $(document.body).on("change", "select", function () {
        var $prev = $(this).prev();
        if ($prev.is("span")) {
            $prev.html($(this).find("option:selected").html());
        }
    });

    $(document.body).on("mouseover", "td", function () {
        var parentTable = $(this).parents("table");
        if (!parentTable.hasClass("not_hightlight")) {
            $(this).parents("tr").addClass("hightlight");
        }
    });
    $(document.body).on("mouseout", "td", function () {
        var parentTable = $(this).parents("table");
        if (!parentTable.hasClass("not_hightlight")) {
            $(this).parents("tr").removeClass("hightlight");
        }
    });

});

jc.uiExtend("nav", {
    setHeight: function () {
        var height = $(window).height();
        this.$list.height(height - 59 - 82 - 40);
    },
    setNiceScroll: function () {
        this.$list.niceScroll({ zindex: 9999, autohidemode: true, cursorwidth: "4px", cursorcolor: "#999", cursorborder: 0, cursoropacitymax: 0.8 });
    },
    resize: function () {
        this.setHeight();
        this.$list.getNiceScroll().resize();
    },
    visible: function (flag) {
        if (flag) {
            $(document.body).removeClass("mini");
            this.$visible_i.html("&#xe5c4;");
        }
        else {
            $(document.body).addClass("mini");
            this.$visible_i.html("&#xe5c8;");
        }
        this.resize();
    },
    init: function () {

        var _this = this;


        this.$list = this.$element.find(".n_list");

        this.$visible = this.$element.find(".n_visible a");
        this.$visible_i = this.$visible.find("i");

        this.$element.on("click", ".n_list .l_item", function () {
            //_this.visible(true);
            $(this).addClass("current").siblings().removeClass("current");
        });

        this.$visible.click(function () {
            if ($(document.body).hasClass("mini")) {
                _this.visible(true);
            }
            else {
                _this.visible(false);
            }
        });

        this.setHeight();
        this.setNiceScroll();

    }
});

jc.uiExtend("header", {
    init: function () {

        var _this = this;

        this.$element.on("mouseover", ".h_left .l_nav .n_item", function () {
            var $menu = $(this).find(".i_menu");
            if ($menu.length) {
                $(this).addClass("show");
            }
        });
        this.$element.on("mouseout", ".h_left .l_nav .n_item", function () {
            var $menu = $(this).find(".i_menu");
            if ($menu.length) {
                $(this).removeClass("show");
            }
        });


        this.$element.click(function (e) {
            var e = e || window.event;
            if (e.stopPropagation) {
                e.stopPropagation()
            }
            else {
                window.event.cancelBubble = true
            }
        });

        this.$element.on("click", ".h_right .r_menu .m_item", function (e) {
            $(this).toggleClass("active");
        });

        $(document.body).click(function () {
            _this.$element.find(".h_right .r_menu .m_item").removeClass("active");
        });


    }
});


jc.uiExtend("filter", {
    init: function () {
        if (!this.$element.attr("data-disabled")) {
            this.$element.on("click", "a", function () {
                $(this).addClass("current").siblings().removeClass("current");
            });
        }
    }
});


jc.uiExtend("skinList", {
    init: function () {

    }
});