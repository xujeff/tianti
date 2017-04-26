/// <reference path="zepto.min.js" />
/// <reference path="jquery-1.8.3.min.js" />
/// <reference path="zepto.animate.js" />

(function (window, undefined) {
    /* 命名空间 */
    var jc = {};

    /* require */
    jc.require = { url: [], success: null };

    /* 判断客户端 */
    jc.isMobile = !!(/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent)));

    /* 存放所有Ready要执行的函数 */
    jc.fnInit = [];

    /* 存放所有Scroll要执行的函数 */
    jc.fnScroll = [];

    /* 存放所有Resize要执行的函数 */
    jc.fnResize = [];

    /* 存放所有UI */
    jc.ui = {};

    /* 存放UI所有属性 */
    jc.tmpUiValue = {}

    /* 默认层级 */
    jc.zIndex = {
        now: 10000,
        get: function () {
            return this.now;
        },
        plus: function () {
            this.now++;
            return this.get();
        }
    }

    /* 创建dom */
    jc.createDOM = function (json) {

        var div = document.createElement("div");

        for (var attr in json) {
            var curAttr = attr;
            if (curAttr == "classname") {
                curAttr = "class";
            }
            else {
                curAttr = curAttr.replace(/([A-Z])/g, "-$1").toLowerCase();
            }
            div.setAttribute(curAttr, json[attr]);
        }

        return div;
    }

    /* ui继承 */
    jc.uiExtend = function (uiName, obj, asyn) {
        jc.tmpUiValue[uiName] = function ($element, _element, uiName) {
            this.$element = $element;
            this._element = _element;
            this.uiName = uiName;
            this.className = "J_" + this.uiName;
            this.isInit = false;
        };
        for (var attr in obj) {
            jc.tmpUiValue[uiName].prototype[attr] = obj[attr];
        }
        if (asyn) {
            if ($.isReady) {
                jc.uiInit(uiName);
            }
            else {
                jc.log("[" + uiName + "] is not asyn");
            }
        }
    };


    /* 异步加载文件 */
    jc.use = {
        reg: /[^a-zA-Z0-9]/g,
        queue: [],
        count: 0,
        cache: {},
        isLoad: false,
        loadSuccess: function (queue) {
            var url = queue.url[queue.progress];

            this.cache[url.replace(this.reg, '')] = true;
            if (queue.progress < queue.url.length - 1) {
                queue.progress++;
                this.load(queue);
            }
            else {
                if (queue.success) queue.success();
                this.isLoad = false;
                if (this.count < this.queue.length - 1) {
                    this.count++;
                    this.load(this.queue[this.count]);
                }
            }

        },
        load: function (queue) {
            var _this = this;

            this.isLoad = true;

            var element = null;
            var url = queue.url[queue.progress];

            /* 如果之前加载过 */
            if (this.cache[url.replace(this.reg, '')]) {
                this.loadSuccess(queue);
                return false;
            }

            if (url.indexOf(".js") != -1) {
                element = document.createElement("script");
                element.src = url;
                element.type = "text/javascript";
            }

            else if (url.indexOf(".css") != -1) {
                element = document.createElement("link");
                element.href = url;
                element.rel = "stylesheet";
            }

            queue.element = element;

            if (navigator.userAgent.indexOf('MSIE') != -1) {
                element.onreadystatechange = function () {
                    if (this.readyState && this.readyState == "loading") {
                        return;
                    }
                    else {
                        element.onreadystatechange = null;
                        _this.loadSuccess(queue);
                    }
                };
            }
            else {
                element.onload = element.onerror = function () {
                    _this.loadSuccess(queue);
                }
            }

            document.getElementsByTagName("head")[0].appendChild(element);

        },
        get: function (url, success) {
            if (!url || !url.length) {
                if (success) success();
                return false;
            }

            this.queue.push({ url: $.isArray(url) ? url : [url], success: success, progress: 0, error: [] });

            if (!this.isLoad) {
                this.load(this.queue[this.count]);
            }

        }
    };

    /* 判断UI是否存在 返回个数 */
    jc.hasUI = function (uiName) {
        if (typeof uiName != "string" || !jc.ui[uiName]) return 0;
        if (jc.ui[uiName].length) return jc.ui[uiName].length;
    }

    /* 寻找目标插件 */
    jc.uiUpdate = function () {

        /* 判断本页面是否启用懒加载 */
        if (document.body.getAttribute("data-lazyload")) {
            jc.lazyload.init();
        };

        /* 取出所有的DIV元素 */
        var element = document.querySelectorAll ? document.body.querySelectorAll('div[data-ui]') : document.body.getElementsByTagName("div");
        for (var i = 0, l = element.length; i < l; i++) {
            var curElement = element[i];

            /* 如果已存在ui */
            if (curElement.isSetup) continue;

            jc.uiSetup(curElement);

        }

        jc.uiInitAll();
    };

    /* 装载插件 */
    jc.uiSetup = function (element) {
        var plugName = element.getAttribute("data-ui");

        if (!plugName) return false;

        /* 如果没有这个UI 先写一个 */
        if (!jc.ui[plugName]) {
            jc.ui[plugName] = {
                length: 0
            }
        }

        /* 增加元素 */
        var curUI = jc.ui[plugName];

        /* 增加属性 看看有没有写业务 */
        if (jc.tmpUiValue[plugName]) {

            /* 添加标识 uiFlag 作用就是 防止重复 jc.update */
            element.isSetup = plugName;

            /* 当前暂存对象 */
            var tmpUiValue = jc.tmpUiValue[plugName];

            curUI[curUI.length] = new tmpUiValue($(element), element, plugName);

            /* 判断对象是否有 scroll 如果有就加到队列 */
            if (tmpUiValue.prototype.scroll) {
                jc.fnScroll.push(curUI[curUI.length]);
            }

            /* 判断对象是否有 resize 如果有就加到队列 */
            if (tmpUiValue.prototype.resize) {
                jc.fnResize.push(curUI[curUI.length]);
            }

            /* 自增 */
            curUI.length++;
        }

        return plugName;
    }

    /* 初始化全部控件 */
    jc.uiInitAll = function () {
        for (var attr in jc.ui) {
            jc.uiInit(attr);
        }
    }

    /* 初始化控件(单个) */
    jc.uiInit = function (uiName) {
        var info = jc.tools.getWindowInfo();

        var curUi = jc.ui[uiName];
        /* 如果元素数量不等于0 */
        if (jc.hasUI(uiName)) {
            for (var i = 0, l = curUi.length; i < l; i++) {
                /* 写上方法 */
                curUi.each = function (fn) {
                    for (var i = 0, l = this.length; i < l; i++) {
                        if (fn) fn.call(this[i], this[i].$element);
                    }
                }

                curUi.filter = function (term, fnName, a, b, c, d, e, f, g) {/* token=a */
                    var attr = $.trim(term).split("=");
                    if (attr.length < 2) return;
                    for (var i = 0, l = this.length; i < l; i++) {
                        if (this[i]._element.getAttribute("data-" + $.trim(attr[0])) === $.trim(attr[1])) {
                            if (this[i][fnName]) {
                                this[i][fnName](a, b, c, d, e, f, g);
                            }
                        }
                    }
                }

                curUi.trigger = function (fnName, a, b, c, d, e, f, g) {
                    for (var i = 0, l = this.length; i < l; i++) {
                        if (this[i][fnName]) this[i][fnName](a, b, c, d, e, f, g);
                    }
                }

                var curUiOnly = curUi[i];

                /* getString */
                curUiOnly.getString = function (text, defalutText) {
                    return (text || (defalutText || ""));
                }

                /* getTemplate */
                curUiOnly.getTemplate = function (data, fnSuccess) {
                    if (this.template) {
                        var html = this.template(data);
                        if (fnSuccess) fnSuccess(html);
                    }
                    else {
                        jc.data.jsonp(window.static + "template/" + uiName + ".js", function (result) {
                            curUiOnly.template = result;
                            var html = curUiOnly.template(data);
                            if (fnSuccess) fnSuccess(html);
                        });
                    }

                }

                /* getString */
                curUiOnly.getString = function (text, defalutText) {
                    return (text || (defalutText || ""));
                }

                if (curUiOnly.init && !curUiOnly.isInit) {

                    /* 如果有依赖文件先加载依赖文件 */
                    if (curUiOnly.use) {
                        jc.use.get(curUiOnly.use, function () {
                            curUiOnly.init(info);
                            curUiOnly.isInit = true;
                        });
                    }
                    else {
                        curUiOnly.init(info);
                        curUiOnly.isInit = true;
                    }

                }

            }
        }

    }

    /* 删除控件 */
    jc.uiRemove = function (uiName, features) {
        if (!uiName) return false;
        //console.log(uiName)
        for (var attr in jc.ui) {
            var cur = jc.ui[attr];

            if (uiName != attr) continue;

            if (!features) {
                delete cur;
            }

            if (typeof (features) == "object") {
                for (var i = 0, l = cur.length; i < l; i++) {
                    if (cur[i]._element == features) {
                        delete cur[i];
                        cur.length--;
                    }
                }
            }

        }
    }

    /* 初始化控件*/
    jc.fnInit.push(function () {
        //jc.uiSelectAll;
        jc.use.get(jc.require.url, function () {
            if (jc.require.success) jc.require.success();
            jc.uiUpdate();
            if (jc.uiReady) jc.uiReady();
        });
    });

    /* 工具方法 */
    jc.tools = {
        formatDate: function (timestamp, format) {
            var newDate = new Date(timestamp);
            var date = {
                "M+": newDate.getMonth() + 1,
                "d+": newDate.getDate(),
                "h+": newDate.getHours(),
                "m+": newDate.getMinutes(),
                "s+": newDate.getSeconds(),
                "q+": Math.floor((newDate.getMonth() + 3) / 3),
                "S+": newDate.getMilliseconds()
            };

            if (!format) {
                format = "M月dd日";
            }

            if (/(y+)/i.test(format)) {
                format = format.replace(RegExp.$1, (newDate.getFullYear() + '').substr(4 - RegExp.$1.length));
            }
            for (var k in date) {
                if (new RegExp("(" + k + ")").test(format)) {
                    format = format.replace(RegExp.$1, RegExp.$1.length == 1
                           ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
                }
            }
            return format;
        },
        eventListener: {
            add: function (obj, evType, fn) {
                obj.addEventListener ? obj.addEventListener(evType, fn, false) : obj.attachEvent("on" + evType, fn);
            },
            remove: function (obj, evType, fn) {
                obj.removeEventListener ? obj.removeEventListener(evType, fn, false) : obj.detachEvent("on" + evType, fn);
            }
        },
        range: function (iNow, iMin, iMax) {
            if (iNow > iMax) return iMax;
            else if (iNow < iMin) return iMin;
            return iNow;
        },
        getWindowInfo: function () {
            var result = {
                scrollTop: (document.documentElement.scrollTop || document.body.scrollTop),
                scrollLeft: (document.documentElement.scrollLeft || document.body.scrollLeft),
                scrollHeight: (document.documentElement.scrollHeight || document.body.scrollHeight),
                scrollWidth: (document.documentElement.scrollWidth || document.body.scrollWidth),
                windowWidth: (document.documentElement.clientWidth || document.body.clientWidth),
                windowHeight: (document.documentElement.clientHeight || document.body.clientHeight)
            }
            return result;
        },
        hasTransform: function () {
            if (!window.getComputedStyle) {
                return false;
            }
            var el = document.createElement('p'),
                has3d,
                transforms = {
                    'webkitTransform': '-webkit-transform',
                    'OTransform': '-o-transform',
                    'msTransform': '-ms-transform',
                    'MozTransform': '-moz-transform',
                    'transform': 'transform'
                };

            // Add it to the body to get the computed style.
            document.body.insertBefore(el, null);

            for (var t in transforms) {
                if (el.style[t] !== undefined) {
                    el.style[t] = "translate3d(1px,1px,1px)";
                    has3d = window.getComputedStyle(el).getPropertyValue(transforms[t]);
                }
            }

            document.body.removeChild(el);

            return (has3d !== undefined && has3d.length > 0 && has3d !== "none");
        },
        setStyleToCss3Hack: function (obj, styleName, value) {
            styleName = styleName.charAt(0).toUpperCase() + styleName.substring(1);
            obj.style['Webkit' + styleName] = value;
            obj.style['Moz' + styleName] = value;
            obj.style['ms' + styleName] = value;
            obj.style['O' + styleName] = value;
            obj.style[name] = value;
        }

    };

    /* 延迟加载 */
    jc.lazyload = {
        queue: [],
        push: function (element) {
            var dataSrc = element.getAttribute("data-src");
            if (!dataSrc) return;

            var $element = $(element);
            var top = $element.offset().top;
            var height = $(element).height();

            if (this.check(top, height)) {
                element.src = dataSrc;
            }
            else {
                this.queue.push({
                    element: element,
                    $element: $element,
                    top: top,
                    src: dataSrc,
                    height: height,
                    success: false
                });
                element.style.opacity = "0";
                element.style.filter = "alpha(opacity=0)";
            }

        },
        check: function (top, height) {
            var info = jc.tools.getWindowInfo();
            if (top >= info.scrollTop - height && top < (info.scrollTop + info.windowHeight)) {
                return true;
            }
            else {
                return false;
            }
        },
        listener: function () {
            if (this.queue.length) {

                for (var i = 0, l = this.queue.length; i < l; i++) {
                    var cur = this.queue[i];
                    if (cur.success) continue;
                    if (this.check(cur.top, cur.height)) {
                        cur.element.src = cur.src;
                        cur.$element.stop().animate({ opacity: 1 }, "slow");
                        cur.success = true;
                    }
                }
            }

        },
        init: function () {
            var _this = this;
            jc.tools.eventListener.add(window, "scroll", function () {
                _this.listener();
            });
            jc.tools.eventListener.add(window, "resize", function () {
                _this.listener();
            });

            var img = document.getElementsByTagName("img");

            if (!img.length) return;
            for (var i = 0, l = img.length; i < l; i++) {
                this.push(img[i]);
            }


        }
    }


    /* hash */
    jc.hash = {
        now: {},
        getObject: function () {
            this.update();
            return this.now;
        },
        stringify: function (obj) {
            var tmp = [];

            obj = obj ? obj : this.now;

            for (var attr in obj) {
                if (obj[attr] == "undefined") {
                    obj[attr] = "";
                }
                tmp.push(attr + "=" + obj[attr]);
            }
            return tmp.join(";");
        },
        update: function () {
            var hash = window.location.hash.substr(1);
            var split = hash.split(";");

            for (var i = 0, l = split.length; i < l; i++) {
                var key = split[i].split("=");

                if (key.length === 2) {
                    this.now[key[0]] = key[1];
                }
            }
        },
        write: function () {
            window.location.hash = "#" + this.stringify();
        },
        get: function (keyName) {
            this.update();
            return this.now[keyName] ? this.now[keyName] : '';
        },
        set: function (keyName, value) {
            this.now[keyName] = value;
        },
        remove: function (keyName) {
            if (this.now[keyName]) delete this.now[keyName];
        }

    }


    /* param */
    jc.param = {
        now: {},
        getObject: function () {
            this.update();
            return this.now;
        },
        stringify: function (obj) {
            var tmp = [];

            obj = obj ? obj : this.now;

            for (var attr in obj) {
                if (obj[attr] == "undefined") {
                    obj[attr] = "";
                }
                tmp.push(attr + "=" + obj[attr]);
            }
            return tmp.join("&");
        },
        update: function () {
            var hash = window.location.search.substr(1);
            var split = hash.split("&");

            for (var i = 0, l = split.length; i < l; i++) {
                var key = split[i].split("=");

                if (key.length === 2) {
                    this.now[key[0]] = key[1];
                }
            }
        },
        get: function (keyName) {
            this.update();
            return this.now[keyName] ? this.now[keyName] : '';
        },
        set: function (keyName, value) {
            this.now[keyName] = value;
        },
        remove: function (keyName) {
            if (this.now[keyName]) delete this.now[keyName];
        }

    }

    /* 碰撞检测 */
    jc.overlap = function (obj1, obj2) {
        if ($(obj1).offset().left > $(obj2).offset().left - obj1.offsetWidth && $(obj1).offset().top > $(obj2).offset().top - obj1.offsetHeight && $(obj1).offset().top < $(obj2).offset().top + obj2.offsetHeight && $(obj1).offset().left < $(obj2).offset().left + obj2.offsetWidth) {
            return true;
        }
    };

    /* cookie */
    jc.cookie = {
        add: function (name, value, path, days) {//四个参数，第一个是cookie的名称，第二个是值，第三个是路径， 第四个是保存天数
            var expDays = days || 30;	// 默认保存30天
            var exp = new Date(); //new Date("December 31, 9998");
            exp.setTime(exp.getTime() + expDays * 24 * 60 * 60 * 1000);
            document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString() + ((!path) ? ";path=/" : ";path=" + path);

        },
        get: function (name) {
            var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
            if (arr != null) return unescape(arr[2]);
            return null;
        },
        remove: function (name, path) {
            var exp = new Date();
            exp.setTime(exp.getTime() + (-1 * 24 * 60 * 60 * 1000));
            var cval = jc.cookie.get(name);
            document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString() + ((!path) ? ";path=/" : ";path=" + path);
        }

    };

    /* rem */
    jc.rem = {
        nowPx: 50,
        state: false,
        _fnResize: function () {
            var _width = document.documentElement.clientWidth || document.body.clientWidth;
            var zoom = _width / 320, fontsizerem = zoom * 50;
            if (_width < 320) fontsizerem = 50;

            var root = document.getElementById("root");
            if (!root) {
                var root = document.createElement("STYLE");
                root.id = "root"
                root.type = "text/css";
                document.head.appendChild(root);
            }

            root.innerHTML = 'html{font-size:' + fontsizerem + 'px;}';

            jc.rem.nowPx = fontsizerem;
        },
        on: function () {
            this.state = true;
            if (window.addEventListener) {
                window.addEventListener("resize", this._fnResize);
                this._fnResize();
            }
        },
        off: function () {
            this.state = false;
            if (window.removeEventListener) {
                window.removeEventListener("resize", this._fnResize);
                document.documentElement.style.cssText = "";
            }
        },
        pxToRem: function (px, def) {
            return (parseFloat(px) / (def ? 50 : this.nowPx)) + "rem";
        },
        remToPx: function (rem, def) {
            return (parseFloat(rem) * (def ? 50 : this.nowPx)) + "px";
        },
        log: function (iMin, iMax) {
            if ((iMin > iMax) || !iMin || !iMax || !window.console) return;

            var sLog = "/*\n";
            for (var i = iMin; i <= iMax; i++) {
                sLog += "* " + i + "px == " + this.pxToRem(i) + "\n";
            }
            console.log(sLog + "*/");
        }
    };

    /* jsonp */
    jc.data = {
        queue: [], //队列存放 每一条信息 {url:xxxx,data:{a:1,b2,c3},success:fn};
        count: 0,
        script: null,
        setup: function (str) {
            this.queue[this.count - 1].success(str);

            /* 销毁 递归  */
            document.head.removeChild(this.script);
            this.script = null;

            if (this.count < this.queue.length) {

                var cur = this.queue[this.count];
                this.load(cur.url, cur.data, cur.success);
                this.count++;
            }


        },
        load: function (url, data, success) {
            this.script = document.createElement("script");
            this.script.type = "text/javascript";
            var attribute = [];
            if (typeof data == "object") {
                for (var attr in data) {
                    attribute.push(attr + "=" + data[attr]);
                }
                /* 增加随机数 防止缓存 */
                attribute.push("r=" + parseInt(Math.random() * 10000));
                /* 增加jsonp的标识 */
                attribute.push("jsonp=" + "jc.data.setup");
            }

            this.script.src = url + (attribute.length ? "?" + attribute.join("&") : "");
            document.head.appendChild(this.script);
        },
        jsonp: function (url, data, success) {
            /* 处理传参 */
            if ($.isFunction(data)) {
                success = data;
                data = "";
            }
            this.queue.push({ url: url, data: data, success: success });


            if (!this.script) {
                var cur = this.queue[this.count]
                this.load(cur.url, cur.data, cur.success);
                this.count++;
            }
        }
    };


    /* 是否启用自定义滚动条 */
    jc.fnInit.push(function () {
        if ($("html").niceScroll && !jc.isMobile) {
            $("html").niceScroll({ zindex: 9999, autohidemode: false, cursorwidth: "4px", cursorcolor: "#333", cursorborder: 0, cursoropacitymax: 0.8 });
        }
    });

    /* 处理reday事件 */
    $(function () {
        if (!jc.fnInit.length) return;
        for (var i = 0, l = jc.fnInit.length; i < l; i++) {
            jc.fnInit[i]();
        }
    });

    /* 处理resize事件 */
    $(window).resize(function () {
        var info = jc.tools.getWindowInfo();

        for (var i = 0, l = jc.fnResize.length; i < l; i++) {
            jc.fnResize[i].resize(info);
        }
    });

    /* 处理scroll事件 */
    jc.fixScollTime = null;
    $(window).scroll(function () {
        var info = jc.tools.getWindowInfo();

        /* 防止快速执行 */
        clearTimeout(jc.fixScollTime);
        jc.fixScollTime = setTimeout(function () {
            for (var i = 0, l = jc.fnScroll.length; i < l; i++) {
                jc.fnScroll[i].scroll(info);
            }
        }, 1);

    });

    window.jc = jc;

})(window);