/*! nice Validator 0.7.3
 * (c) 2012-2014 Jony Zhang <zj86@live.cn>, MIT Licensed
 * http://niceue.com/validator/
 */
/*jshint evil:true, expr:true, strict:false*/
/*global define*/
(function($, undefined) {
    "use strict";

    var NS = 'validator',
        CLS_NS = '.' + NS,
        CLS_NS_RULE = '.rule',
        CLS_NS_FIELD = '.field',
        CLS_NS_FORM = '.form',
        CLS_WRAPPER = 'nice-' + NS,
        CLS_MSG_OK = 'n-ok',
        CLS_MSG_ERROR = 'n-error',
        CLS_MSG_TIP = 'n-tip',
        CLS_MSG_LOADING = 'n-loading',
        CLS_MSG_BOX = 'msg-box',
        ARIA_REQUIRED = 'aria-required',
        ARIA_INVALID = 'aria-invalid',
        DATA_RULE = 'data-rule',
        DATA_MSG = 'data-msg',
        DATA_TIP = 'data-tip',
        DATA_OK = 'data-ok',
        DATA_TARGET = 'data-target',
        DATA_INPUT_STATUS = 'data-inputstatus',
        NOVALIDATE = 'novalidate',
        INPUT_SELECTOR = ':verifiable',

        rRules = /(!?)\s?(\w+)(?:\[\s*(.*?\]?)\s*\]|\(\s*(.*?\)?)\s*\))?\s*(;|\||&)?/g,
        rRule = /(\w+)(?:\[\s*(.*?\]?)\s*\]|\(\s*(.*?\)?)\s*\))?/,
        rDisplay = /(?:([^:;\(\[]*):)?(.*)/,
        rDoubleBytes = /[^\x00-\xff]/g,
        rPos = /^.*(top|right|bottom|left).*$/,
        rAjaxType = /(?:(post|get):)?(.+)/i,
        rUnsafe = /<|>/g,

        noop = $.noop,
        proxy = $.proxy,
        isFunction = $.isFunction,
        isArray = $.isArray,
        isString = function(s) {
            return typeof s === 'string';
        },
        isObject = function(o) {
            return o && Object.prototype.toString.call(o) === '[object Object]';
        },
        isIE6 = !window.XMLHttpRequest,
        attr = function(el, key, value) {
            if (value !== undefined) {
                if (value === null) el.removeAttribute(key);
                else el.setAttribute(key, '' + value);
            } else {
                return el.getAttribute(key);
            }
        },
        debug = window.console || {
            log: noop,
            info: noop
        },
        submitButton,
        novalidateonce,

        defaults = {
            debug: 0,
            timely: 1,
            theme: 'default',
            ignore: '',
            //stopOnError: false,
            //focusCleanup: false,
            focusInvalid: true,
            beforeSubmit: noop,
            //dataFilter: null,
            //valid: null,
            //invalid: null,

            validClass: 'n-valid',
            invalidClass: 'n-invalid',

            msgWrapper: 'span',
            msgMaker: function(opt) {
                var html,
                    cls = {
                        error: CLS_MSG_ERROR,
                        ok: CLS_MSG_OK,
                        tip: CLS_MSG_TIP,
                        loading: CLS_MSG_LOADING
                    }[opt.type];

                html = '<span class="msg-wrap '+ cls +'" role="alert">';
                html += opt.arrow + opt.icon + '<span class="n-msg">' + opt.msg + '</span>';
                html += '</span>';
                return html;
            },
            msgIcon: '<span class="n-icon"></span>',
            msgArrow: '',
            msgClass: '',
            //msgStyle: null,
            //msgShow: null,
            //msgHide: null,
            //showOk: true,
            defaultMsg: '{0} is not valid.',
            loadingMsg: 'Validating...'
        },
        themes = {
            'default': {
                formClass: 'n-default',
                msgClass: 'n-right',
                showOk: ''
            }
        };

    /** jQuery Plugin
     * @param {Object} options
        debug         {Boolean}     false     Whether to enable debug mode
        timely        {Boolean}     true      Whether to enable timely verification
        theme         {String}     'default'  Using which theme
        stopOnError   {Boolean}     false     Whether to stop validate when found an error input
        focusCleanup  {Boolean}     false     Whether to clean up the field message when focus the field
        focusInvalid  {Boolean}     true      Whether to focus the field that is invalid
        ignore        {jqSelector}    ''      Ignored fields (Using jQuery selector)
        
        beforeSubmit  {Function}              Do something before submitting the form
        dataFilter    {Function}              Conversion ajax results
        valid         {Function}              Triggered when the form is valid
        invalid       {Function}              Triggered when the form is invalid
        validClass    {String}                Add the class name to a valid field
        invalidClass  {String}                Add the class name to a invalid field

        msgShow       {Function}    null      When show a message, will trigger this callback
        msgHide       {Function}    null      When hide a message, will trigger this callback
        msgWrapper    {String}     'span'     Message wrapper tag name
        msgMaker      {Function}              Message HTML maker
        msgIcon       {String}                Icon template
        msgArrow      {String}                Small arrow template
        msgStyle      {String}                Custom message style
        msgClass      {String}                Additional added to the message class names
        formClass     {String}                Additional added to the form class names

        defaultMsg    {String}                Default error message
        loadingMsg    {String}                Tips for asynchronous loading
        messages      {Object}      null      Custom messages for the current instance
        
        rules         {Object}      null      Custom rules for the current instance

        fields        {Object}                Field set to be verified
        {String} key    name|#id
        {String|Object} value                 Rule string, or an object is passed more arguments

        fields[key][rule]       {String}      Rule string
        fields[key][tip]        {String}      Custom friendly message when focus the input
        fields[key][ok]         {String}      Custom success message
        fields[key][msg]        {Object}      Custom error message
        fields[key][msgStyle]   {String}      Custom message style
        fields[key][msgClass]   {String}      Additional added to the message class names
        fields[key][msgWrapper] {String}      Message wrapper tag name
        fields[key][msgMaker]   {Function}    Custom message HTML maker
        fields[key][dataFilter] {Function}    Conversion ajax results
        fields[key][valid]      {Function}    Triggered when this field is valid
        fields[key][invalid]    {Function}    Triggered when this field is invalid
        fields[key][must]       {Boolean}     If set true, we always check the field even has remote checking
        fields[key][timely]     {Boolean}     Whether to enable timely verification
        fields[key][target]     {jqSelector}  Verify the current field, but the message can be displayed on target element
     */
    $.fn[NS] = function(options) {
        var that = this,
            args = arguments;

        if (that.is(':input')) return that;
        !that.is('form') && (that = this.find('form'));
        !that.length && (that = this);
        that.each(function() {
            var cache = $(this).data(NS);
            if (cache) {
                if (isString(options)) {
                    if (options.charAt(0) === '_') return;
                    cache[options].apply(cache, Array.prototype.slice.call(args, 1));
                } else if (options) {
                    cache._reset(true);
                    cache._init(this, options);
                }
            } else {
                new Validator(this, options);
            }
        });

        return this;
    };


    // Validate a field, or an area
    $.fn.isValid = function(callback, hideMsg) {
        var me = getInstance(this[0]),
            hasCallback = isFunction(callback),
            ret, opt;

        if (!me) return true;
        me.checkOnly = !!hideMsg;
        opt = me.options;

        ret = me._multiValidate(
            this.is(':input') ? this : this.find(INPUT_SELECTOR),
            function(isValid){
                if (!isValid && opt.focusInvalid && !me.checkOnly) {
                    // navigate to the error element
                    me.$el.find(':input[' + ARIA_INVALID + ']:first').focus();
                }
                hasCallback && callback.call(null, isValid);
                me.checkOnly = false;
            }
        );

        // If you pass a callback, we maintain the jQuery object chain
        return hasCallback ? this : ret;
    };


    // A faster selector than ":input:not(:submit,:button,:reset,:image,:disabled,[novalidate])"
    $.expr[":"].verifiable = function(elem) {
        var name = elem.nodeName.toLowerCase();

        return (name === 'input' && !({submit: 1, button: 1, reset: 1, image: 1})[elem.type] || name === 'select' || name === 'textarea') &&
               elem.disabled === false;
    };


    // Constructor for Validator
    function Validator(element, options) {
        var me = this;

        if (!me instanceof Validator) return new Validator(element, options);

        me.$el = $(element);
        me._init(element, options);
    }

    Validator.prototype = {
        _init: function(element, options) {
            var me = this,
                opt, themeOpt, dataOpt;

            // Initialization options
            if (isFunction(options)) {
                options = {
                    valid: options
                };
            }
            options = options || {};
            dataOpt = attr(element, 'data-'+ NS +'-option');
            dataOpt = dataOpt && dataOpt.charAt(0) === '{' ? (new Function("return " + dataOpt))() : {};
            themeOpt = themes[ options.theme || dataOpt.theme || defaults.theme ];
            opt = me.options = $.extend({}, defaults, themeOpt, dataOpt, me.options, options);

            me.rules = new Rules(opt.rules, true);
            me.messages = new Messages(opt.messages, true);
            me.elements = me.elements || {};
            me.deferred = {};
            me.errors = {};
            me.fields = {};

            // Initialization fields
            me._initFields(opt.fields);

            // Initialization group verification
            if (isArray(opt.groups)) {
                $.map(opt.groups, function(obj) {
                    if (!isString(obj.fields) || !isFunction(obj.callback)) return null;
                    obj.$elems = me.$el.find(keys2selector(obj.fields));
                    $.map(obj.fields.split(' '), function(k) {
                        me.fields[k] = me.fields[k] || {};
                        me.fields[k].group = obj;
                    });
                });
            }

            // Initialization message parameters
            me.msgOpt = {
                type: 'error',
                pos: getPos(opt.msgClass),
                wrapper: opt.msgWrapper,
                cls: opt.msgClass,
                style: opt.msgStyle,
                icon: opt.msgIcon,
                arrow: opt.msgArrow,
                show: opt.msgShow,
                hide: opt.msgHide
            };

            // Guess whether it use ajax submit
            me.isAjaxSubmit = false;
            if (opt.valid || !$.trim(attr(element, 'action'))) {
                me.isAjaxSubmit = true;
            } else {
                // if there is a "valid.form" event
                var events = $[ $._data ? '_data' : 'data' ](element, "events");
                if (events && events.valid &&
                    $.map(events.valid, function(e){
                        return e.namespace.indexOf('form') !== -1 ? 1 : null;
                    }).length
                ) {
                    me.isAjaxSubmit = true;
                }
            }

            // Initialization events and make a cache
            if (!me.$el.data(NS)) {
                me.$el.data(NS, me).addClass(CLS_WRAPPER +' '+ opt.formClass)
                      .on('submit'+ CLS_NS +' validate'+ CLS_NS, proxy(me, '_submit'))
                      .on('reset'+ CLS_NS, proxy(me, '_reset'))
                      .on('showtip'+ CLS_NS, proxy(me, '_showTip'))
                      .on('focusin'+ CLS_NS +' click'+ CLS_NS +' showtip'+ CLS_NS, INPUT_SELECTOR, proxy(me, '_focusin'))
                      .on('focusout'+ CLS_NS +' validate'+ CLS_NS, INPUT_SELECTOR, proxy(me, '_focusout'));

                if (opt.timely >= 2) {
                    me.$el.on('keyup'+ CLS_NS +' paste'+ CLS_NS, INPUT_SELECTOR, proxy(me, '_focusout'))
                          .on('click'+ CLS_NS, ':radio,:checkbox', proxy(me, '_focusout'))
                          .on('change'+ CLS_NS, 'select,input[type="file"]', proxy(me, '_focusout'));
                }

                // cache the novalidate attribute value
                me._novalidate = attr(element, NOVALIDATE);
                // Initialization is complete, stop off default HTML5 form validation
                // If use "jQuery.attr('novalidate')" in IE7 will complain: "SCRIPT3: Member not found."
                attr(element, NOVALIDATE, NOVALIDATE);
            }
        },

        _initFields: function(fields) {
            var me = this;

            // Processing field information
            if (isObject(fields)) {
                $.each(fields, function(k, v) {
                    // delete the field from settings
                    if (v === null) {
                        var el = me.elements[k];
                        if (el) me._resetElement(el, true);
                        delete me.fields[k];
                    } else {
                        me.fields[k] = isString(v) ? {
                            rule: v
                        } : v;
                    }
                });
            }

            // Parsing DOM rules
            me.$el.find(INPUT_SELECTOR).each(function() {
                me._parse(this);
            });
        },

        // Parsing a field
        _parse: function(el) {
            var me = this,
                field,
                key = el.name,
                dataRule = attr(el, DATA_RULE);

            dataRule && attr(el, DATA_RULE, null);

            // if the field has passed the key as id mode, or it doesn't has a name
            if (el.id && ('#' + el.id in me.fields) || !el.name) {
                key = '#' + el.id;
            }
            // doesn't verify a field that has neither id nor name
            if (!key) return;

            field = me.fields[key] || {};
            field.key = key;
            field.old = {};
            field.rule = field.rule || dataRule || '';
            if (!field.rule) return;

            if (field.rule.match(/match|checked/)) {
                field.must = true;
            }
            if (field.rule.indexOf('required') !== -1) {
                field.required = true;
                attr(el, ARIA_REQUIRED, true);
            }
            if ('timely' in field && !field.timely || !me.options.timely) {
                attr(el, 'notimely', true);
            }
            if (isString(field.target)) {
                attr(el, DATA_TARGET, field.target);
            }
            if (isString(field.tip)) {
                attr(el, DATA_TIP, field.tip);
            }

            me.fields[key] = me._parseRule(field);
        },

        // Parsing field rules
        _parseRule: function(field) {
            var arr = rDisplay.exec(field.rule),
                opt = this.options;

            if (!arr) return;
            // current rule index
            field._i = 0;
            if (arr[1]) {
                field.display = arr[1];
            }
            if (!field.display && opt.display) {
                field.display = opt.display;
            }
            if (arr[2]) {
                field.rules = [];
                arr[2].replace(rRules, function(){
                    var args = arguments;
                    args[3] = args[3] || args[4];
                    field.rules.push({
                        not: args[1] === "!",
                        method: args[2],
                        params: args[3] ? args[3].split(', ') : undefined,
                        or: args[5] === "|"
                    });
                });
            }

            return field;
        },

        // Verify a zone
        _multiValidate: function($inputs, doneCallbacks){
            var me = this,
                opt = me.options;

            me.verifying = true;
            me.isValid = true;
            if (opt.ignore) $inputs = $inputs.not(opt.ignore);

            $inputs.each(function(i, el) {
                var field = me.getField(el);
                if (field) {
                    me._validate(el, field);
                    if (!me.isValid && opt.stopOnError) {
                        // stop the verification
                        return false;
                    }
                }
            });

            // Need to wait for the completion of all field validation (especially asynchronous verification)
            $.when.apply(
                null,
                $.map(me.deferred, function(v){return v;})
            ).done(function(){
                doneCallbacks.call(me, me.isValid);
                me.verifying = false;
            });

            // If the form does not contain asynchronous validation, the return value is correct.
            // Otherwise, you should detect whether a form valid through "doneCallbacks".
            return !$.isEmptyObject(me.deferred) ? undefined : me.isValid;
        },

        // Verify the whole form
        _submit: function(e) {
            var me = this,
                opt = me.options,
                form = e.target,
                autoSubmit = e.type === 'submit';

            e.preventDefault();
            if (
                novalidateonce && !!~(novalidateonce = false) ||
                // Prevent duplicate submission
                me.submiting ||
                // Receive the "validate" event only from the form.
                e.type === 'validate' && me.$el[0] !== form ||
                // trigger the beforeSubmit callback.
                opt.beforeSubmit.call(me, form) === false
            ) {
                return;
            }

            opt.debug && debug.log("\n" + e.type);
            
            me._reset();
            me.submiting = true;

            me._multiValidate(
                me.$el.find(INPUT_SELECTOR),
                function(isValid){
                    var ret = (isValid || opt.debug === 2) ? 'valid' : 'invalid',
                        errors;

                    if (!isValid) {
                        if (opt.focusInvalid) {
                            // navigate to the error element
                            me.$el.find(':input[' + ARIA_INVALID + '="true"]:first').focus();
                        }
                        errors = $.map(me.errors, function(err){
                            return err;
                        });
                    }

                    // releasing submit
                    me.submiting = false;

                    // trigger callback and event
                    isFunction(opt[ret]) && opt[ret].call(me, form, errors);
                    me.$el.trigger(ret + CLS_NS_FORM, [form, errors]);

                    if (isValid && !me.isAjaxSubmit && autoSubmit) {
                        novalidateonce = true;
                        // For asp.NET controls
                        if (submitButton && submitButton.name) {
                            me.$el.append('<input type="hidden" name="'+ submitButton.name +'" value="'+ $(submitButton).val() +'">');
                        }
                        form.submit();
                    }
                }
            );
        },

        _reset: function(e) {
            var me = this;

            me.errors = {};
            if (e) {
                me.$el.find(INPUT_SELECTOR).each( function(i, el){
                    me._resetElement(el);
                });
            }
        },

        _resetElement: function(el, all) {
            var opt = this.options;
            $(el).removeClass(opt.validClass + ' ' + opt.invalidClass);
            this.hideMsg(el);
            if (all) {
                attr(el, ARIA_REQUIRED, null);
            }
        },

        _focusin: function(e) {
            var me = this,
                opt = me.options,
                el = e.target,
                msg;

            if (me.verifying) return;

            if (e.type !== 'showtip') {
                if ( attr(el, DATA_INPUT_STATUS) === 'error' ) {
                    if (opt.focusCleanup) {
                        $(el).removeClass(opt.invalidClass);
                        me.hideMsg(el);
                    }
                }
            }

            msg = attr(el, DATA_TIP);
            if (!msg) return;

            me.showMsg(el, {
                type: 'tip',
                msg: msg
            });
        },

        // Handle focusout/validate/keyup/click/change/paste events
        _focusout: function(e) {
            var me = this,
                opt = me.options,
                field,
                must,
                el = e.target,
                etype = e.type,
                ignoreType = {click:1, change:1, paste:1},
                timer = 0;

            if ( !ignoreType[etype] ) {
                // must be verified, if it is a manual trigger
                if (etype === 'validate') {
                    must = true;
                    //timer = 0;
                }
                // or doesn't require real-time verification, exit
                else if ( attr(el, 'notimely') ) return;
                // or it isn't a "keyup" event, exit
                else if (opt.timely >= 2 && etype !== 'keyup') return;

                // if the current field is ignored, exit
                if (opt.ignore && $(el).is(opt.ignore)) return;

                if (etype === 'keyup') {
                    var key = e.keyCode,
                        specialKey = {
                            8: 1,  // Backspace
                            9: 1,  // Tab
                            16: 1, // Shift
                            32: 1, // Space
                            46: 1  // Delete
                        };

                    // only gets focus, no verification
                    if (key === 9 && !el.value) return;

                    // do not validate, if triggered by these keys
                    if (key < 48 && !specialKey[key]) return;

                    // keyboard events, reducing the frequency of verification
                    timer = opt.timely >=100 ? opt.timely : 500;
                }
            }

            field = me.getField(el);
            if (!field) return;

            if (timer) {
                if (field._t) clearTimeout(field._t);
                field._t = setTimeout(function() {
                    me._validate(el, field, must);
                }, timer);
            } else {
                me._validate(el, field, must);
            }
        },

        _showTip: function(e){
            var me = this;

            if (me.$el[0] !== e.target) return;
            me.$el.find(INPUT_SELECTOR +"["+ DATA_TIP +"]").each(function(){
                me.showMsg(this, {
                    msg: attr(this, DATA_TIP),
                    type: 'tip'
                });
            });
        },

        // Validated a field
        _validatedField: function(el, field, ret) {
            var me = this,
                opt = me.options,
                isValid = ret.isValid = field.isValid = !!ret.isValid,
                callback = isValid ? 'valid' : 'invalid';

            ret.key = field.key;
            ret.rule = field._r;
            if (isValid) {
                ret.type = 'ok';
            } else {
                if (me.submiting) {
                    me.errors[field.key] = ret.msg;
                }
                me.isValid = false;
            }
            field.old.value = el.value;
            field.old.id = el.id;
            me.elements[field.key] = ret.element = el;
            me.$el[0].isValid = isValid ? me.isFormValid() : isValid;

            // trigger callback and event
            isFunction(field[callback]) && field[callback].call(me, el, ret);
            $(el).attr( ARIA_INVALID, isValid ? null : true )
                 .removeClass( isValid ? opt.invalidClass : opt.validClass )
                 .addClass( !ret.skip ? isValid ? opt.validClass : opt.invalidClass : "" )
                 .trigger( callback + CLS_NS_FIELD, [ret, me] );
            me.$el.triggerHandler('validation', [ret, me]);

            //自定义    是否显示边框
            if(this.options.theme == 'll'){
            	var elBorder = this.options.elBorder;
            	if(elBorder){
            		if(elBorder.show){
            			var $llp = $(el).parent('div');
            			//另外一种情况
            			if($llp.length == 0){
            				$llp = $(el).parent().parent('div');//.t_icontext
            			}
            			if($llp.length != 0){
            				$llp.removeClass(isValid ? elBorder.invalidClass : elBorder.validClass)
            					.addClass(isValid ? elBorder.validClass : elBorder.invalidClass);
            			}
            		}
            	}
            }
            
            if (me.checkOnly) return;

            // show or hide the message
            if (field.msgMaker || opt.msgMaker) {
                me[ ret.showOk || ret.msg ? 'showMsg' : 'hideMsg' ](el, ret, field);
            }
        },

        // Validated a rule
        _validatedRule: function(el, field, ret, msgOpt) {
            field = field || me.getField(el);
            msgOpt = msgOpt || {};

            var me = this,
                opt = me.options,
                msg,
                rule,
                method = field._r,
                transfer,
                isValid = false;

            // use null to break validation from a field
            if (ret === null) {
                me._validatedField(el, field, {isValid: true, skip: true});
                return;
            }
            else if (ret === true || ret === undefined || ret === '') {
                isValid = true;
            }
            else if (isString(ret)) {
                msg = ret;
            }
            else if (isObject(ret)) {
                if (ret.error) {
                    msg = ret.error;
                } else {
                    msg = ret.ok;
                    isValid = true;
                }
            }

            if (field.rules) {
                rule = field.rules[field._i];
                if (rule.not) {
                    msg = undefined;
                    isValid = method === "required" || !isValid;
                }
                if (rule.or) {
                    if (isValid) {
                        while ( field._i < field.rules.length && field.rules[field._i].or ) {
                            field._i++;
                        }
                    } else {
                        transfer = true;
                    }
                }
            }

            // message analysis, and throw rule level event
            if (!transfer) {
                if (isValid) {
                    msgOpt.isValid = isValid;
                    if (opt.showOk !== false) {
                        if (!isString(msg)) {
                            if (isString(field.ok)) {
                                msg = field.ok;
                            } else if (isString(attr(el, DATA_OK))) {
                                msg = attr(el, DATA_OK);
                            } else if (isString(opt.showOk)) {
                                msg = opt.showOk;
                            }
                        }
                        if (isString(msg)) {
                            msgOpt.showOk = isValid;
                            msgOpt.msg = msg;
                        }
                    }
                    $(el).trigger('valid'+CLS_NS_RULE, [method, msgOpt.msg]);
                } else {
                    /* rule message priority:
                        1. custom field message;
                        2. custom DOM message
                        3. global defined message;
                        4. rule returned message;
                        5. default message;
                    */
                    msgOpt.msg = (getDataMsg(el, field, msg, me.messages[method]) || defaults.defaultMsg).replace('{0}', me._getDisplay(el, field.display || ''));
                    $(el).trigger('invalid'+CLS_NS_RULE, [method, msgOpt.msg]);
                }
            }

            // output the debug message
            if (opt.debug) {
                debug.log('   ' + field._i + ': ' + method + ' => ' + (isValid || msgOpt.msg || isValid));
            }

            // the current rule has passed, continue to validate
            if (transfer || isValid && field._i < field.rules.length - 1) {
                field._i++;
                me._checkRule(el, field);
            }
            // field was invalid, or all fields was valid
            else {
                field._i = 0;
                me._validatedField(el, field, msgOpt);
            }
        },

        // Verify a rule form a field
        _checkRule: function(el, field) {
            var me = this,
                ret,
                old,
                key = field.key,
                rule = field.rules[field._i],
                method = rule.method,
                params = rule.params;

            // request has been sent, wait it
            if (me.submiting && me.deferred[key]) return;
            old = field.old;
            field._r = method;

            if ( !field.must && old.ret !== undefined &&
                 old.rule === rule && old.id === el.id &&
                 el.value && old.value === el.value )
            {
                // get result from cache
                ret = old.ret;
            }
            else {
                // get result from current rule
                ret = (getDataRule(el, method) || me.rules[method] || noop).call(me, el, params, field);
            }

            // asynchronous validation
            if (isObject(ret) && isFunction(ret.then)) {
                me.deferred[key] = ret;
                
                // show loading message
                !me.checkOnly && me.showMsg(el, {
                    type: 'loading',
                    msg: me.options.loadingMsg
                }, field);

                // waiting to parse the response data
                ret.then(
                    function(d, textStatus, jqXHR) {
                        var data = jqXHR.responseText,
                            result,
                            dataFilter = field.dataFilter || me.options.dataFilter;

                        // detect if it is json format
                        if (this.dataType === 'json') {
                            data = d;
                        } else if (data.charAt(0) === '{') {
                            data = $.parseJSON(data) || {};
                        }

                        if (!isFunction(dataFilter)) {
                            dataFilter = function(data) {
                                if (isString(data) || (isObject(data) && ('error' in data || 'ok' in data))) return data;
                            };
                        }

                        // filter data
                        result = dataFilter(data);
                        if (result === undefined) result = dataFilter(data.data);

                        old.rule = rule;
                        old.ret = result;
                        me._validatedRule(el, field, result);
                    },
                    function(jqXHR, textStatus){
                        me._validatedRule(el, field, textStatus);
                    }
                ).always(function(){
                    delete me.deferred[key];
                });
                // whether the field valid is unknown
                field.isValid = undefined;
            }
            // other result
            else {
                me._validatedRule(el, field, ret);
            }
        },

        // Processing the validation
        _validate: function(el, field) {
            // doesn't validate the element that has "disabled" or "novalidate" attribute
            if ( el.disabled || attr(el, NOVALIDATE) !== null ) return;

            var me = this,
                msgOpt = {},
                group = field.group,
                ret,
                isValid = field.isValid = true;

            if ( !field.rules ) me._parse(el);
            if (me.options.debug) debug.info(field.key);

            // group validation
            if (group) {
                ret = group.callback.call(me, group.$elems);
                if (ret !== undefined) {
                    me.hideMsg(group.target, {}, field);
                    if (ret === true) ret = undefined;
                    else {
                        field._i = 0;
                        field._r = 'group';
                        isValid = false;
                        me.hideMsg(el, {}, field);
                        $.extend(msgOpt, group);
                    }
                }
            }
            // if the field is not required and it has a blank value
            if (isValid && !field.required && !field.must && !el.value) {
                if ( attr(el, DATA_INPUT_STATUS) === 'tip' ) {
                    return;
                }
                if (!checkable(el)) {
                    me._validatedField(el, field, {isValid: true});
                    return;
                }
            }

            // if the results are out
            if (ret !== undefined) {
                me._validatedRule(el, field, ret, msgOpt);
            } else if (field.rule) {
                me._checkRule(el, field);
            }
        },

        /* Detecting whether the value of an element that matches a rule
         *
         * @interface: test
         */
        test: function(el, rule) {
            var me = this,
                ret,
                parts = rRule.exec(rule),
                method,
                params;

            if (parts) {
                method = parts[1];
                if (method in me.rules) {
                    params = parts[2] || parts[3];
                    params = params ? params.split(', ') : undefined;
                    ret = me.rules[method].call(me, el, params);
                }
            }

            return ret === true || ret === undefined || ret === null;
        },

        // Get a range of validation messages
        getRangeMsg: function(value, params, type, suffix) {
            if (!params) return;

            var me = this,
                msg = me.messages[type] || '',
                p = params[0].split('~'),
                a = p[0],
                b = p[1],
                c = 'rg',
                args = [''],
                isNumber = +value === +value;

            if (p.length === 2) {
                if (a && b) {
                    if (isNumber && value >= +a && value <= +b) return true;
                    args = args.concat(p);
                } else if (a && !b) {
                    if (isNumber && value >= +a) return true;
                    args.push(a);
                    c = 'gte';
                } else if (!a && b) {
                    if (isNumber && value <= +b) return true;
                    args.push(b);
                    c = 'lte';
                }
            } else {
                if (value === +a) return true;
                args.push(a);
                c = 'eq';
            }

            if (msg) {
                if (suffix && msg[c + suffix]) {
                    c += suffix;
                }
                args[0] = msg[c];
            }

            return me.renderMsg.apply(null, args);
        },

        /* @interface: renderMsg
         */
        renderMsg: function() {
            var args = arguments,
                tpl = args[0],
                i = args.length;

            if (!tpl) return;

            while (--i) {
                tpl = tpl.replace('{' + i + '}', args[i]);
            }

            return tpl;
        },

        _getDisplay: function(el, str) {
            return !isString(str) ? isFunction(str) ? str.call(this, el) : '' : str;
        },

        _getMsgOpt: function(obj) {
            return $.extend({}, this.msgOpt, isString(obj) ? {msg: obj} : obj);
        },

        /**
         * get显示提示信息的DOM    
         * this.$el 是指整个表单
         * @param el		触发事件的控件 eg: select input
         * @param msgOpt
         * @returns
         */
        _getMsgDOM: function(el, msgOpt) {
            var $el = $(el), $msgbox, datafor, tgt;
            
            if ($el.is(':input')) {
                tgt = msgOpt.target || attr(el, DATA_TARGET);
                if (tgt) {
                    tgt = isFunction(tgt) ? tgt.call(this, el) : this.$el.find(tgt);
                    if (tgt.length) {
                        if (tgt.is(':input')) {
                            el = tgt.get(0);
                        } else {
                            $msgbox = tgt;
                        }
                    }
                }
                if (!$msgbox) {
                    datafor = !checkable(el) && el.id ? el.id : el.name;
                    $msgbox = this.$el.find(msgOpt.wrapper + '.' + CLS_MSG_BOX + '[for="' + datafor + '"]');
                }
            } else {
                $msgbox = $el;
            }
            
            //第一次创建
            if (!$msgbox.length) {
                $el = this.$el.find(tgt || el);
                $msgbox = $('<'+ msgOpt.wrapper + '>').attr({
                    'class': CLS_MSG_BOX + (msgOpt.cls ? ' ' + msgOpt.cls : ''),
                    'style': msgOpt.style || '',
                    'for': datafor
                });
                //自定义
                if(this.options.theme == 'll'){
                	if(!this.options.isShowMsg){
                		return $msgbox;
                	}
                	var $lldiv = $el.parents('div.fl.J_toolsBar');// Jg_toolBar
                	if(!$lldiv || $lldiv.length == 0){
                		//$lldiv = $el.parent();
                		$lldiv = $el.parent('div');
                	}
                	if($lldiv.length > 0){
                		$msgbox[!msgOpt.pos || msgOpt.pos === 'right' ? 'insertAfter' : 'insertBefore']($lldiv.get(0));
                	}
                }else{
                	if (checkable(el)) {
                        var $parent = $el.parent();
                        $msgbox.appendTo( $parent.is('label') ? $parent.parent() : $parent );
                    } else {
                        $msgbox[!msgOpt.pos || msgOpt.pos === 'right' ? 'insertAfter' : 'insertBefore']($el);
                    }
                }
            }

            return $msgbox;
        },

        /* @interface: showMsg
         */
        showMsg: function(el, msgOpt, /*INTERNAL*/ field) {
            var me = this,
                opt = me.options,
                msgMaker;

            msgOpt = me._getMsgOpt(msgOpt);
            if (!msgOpt.msg && !msgOpt.showOk) return;
            el = $(el).get(0);

            if ($(el).is(INPUT_SELECTOR)) {
                // mark message status
                attr(el, DATA_INPUT_STATUS, msgOpt.type);
                field = field || me.getField(el);
                if (field) {
                    msgOpt.style = field.msgStyle || msgOpt.style;
                    msgOpt.cls = field.msgClass || msgOpt.cls;
                    msgOpt.wrapper = field.msgWrapper || msgOpt.wrapper;
                    msgOpt.target = field.target || opt.target;
                }
            }
            if (!(msgMaker = (field || {}).msgMaker || opt.msgMaker)) return;
            
            var $msgbox = me._getMsgDOM(el, msgOpt),
                cls = $msgbox[0].className;
                
            !rPos.test(cls) && $msgbox.addClass(msgOpt.cls);
            if ( isIE6 && msgOpt.pos === 'bottom' ) {
                $msgbox[0].style.marginTop = $(el).outerHeight() + 'px';
            }
            $msgbox.html( msgMaker.call(me, msgOpt) )[0].style.display = '';

            isFunction(msgOpt.show) && msgOpt.show.call(me, $msgbox, msgOpt.type);
        },

        /* @interface: hideMsg
         */
        hideMsg: function(el, msgOpt, /*INTERNAL*/ field) {
            var me = this,
                opt = me.options;

            el = $(el).get(0);
            msgOpt = me._getMsgOpt(msgOpt);
            if ($(el).is(INPUT_SELECTOR)) {
                attr(el, DATA_INPUT_STATUS, null);
                attr(el, ARIA_INVALID, null);
                field = field || me.getField(el);
                if (field) {
                    msgOpt.wrapper = field.msgWrapper || msgOpt.wrapper;
                    msgOpt.target = field.target || opt.target;
                }
            }

            var $msgbox = me._getMsgDOM(el, msgOpt);
            if (!$msgbox.length) return;

            if ( isFunction(msgOpt.hide) ) {
                msgOpt.hide.call(me, $msgbox, msgOpt.type);
            } else {
                $msgbox[0].style.display = 'none';
            }
        },

        /* @interface: mapMsg
         */
        mapMsg: function(obj) {
            var me = this;

            $.each(obj, function(name, msg) {
                var el = me.elements[name] || me.$el.find(':input[name="' + name + '"]')[0];
                me.showMsg(el, msg);
            });
        },

        /* @interface: setMsg
         */
        setMsg: function(obj) {
            new Messages(obj, this.messages);
        },

        /* @interface: setRule
         */
        setRule: function(obj) {
            new Rules(obj, this.rules);
            $.map(this.fields, function(field){
                field.old = {};
            });
        },

        // Get field information
        getField: function(el) {
            var me = this,
                key;

            if (el.id && '#' + el.id in me.fields || !el.name) {
                key = '#' + el.id;
            } else {
                key = el.name;
            }
            if (attr(el, DATA_RULE)) me._parse(el);

            return me.fields[key];
        },

        /* @interface: setField
         */
        setField: function(key, obj) {
            var fields = {};

            // update this field
            if (isString(key)) {
                fields[key] = obj;
            }
            // update fields
            else if (isObject(key)) {
                fields = key;
            }

            this._initFields(fields);
        },

        /* @interface: isFormValid
         */
        isFormValid: function() {
            var fields = this.fields;
            for (var k in fields) {
                if (!fields[k].isValid) {
                    return fields[k].isValid;
                }
            }
            return true;
        },

        /* @interface: holdSubmit
         */
        holdSubmit: function(hold) {
            this.submiting = hold === undefined || hold;
        },

        /* @interface: cleanUp
         */
        cleanUp: function() {
            this._reset(1);
        },

        /* @interface: destroy
         */
        destroy: function() {
            this._reset(1);
            this.$el.off(CLS_NS).removeData(NS);
            attr(this.$el[0], NOVALIDATE, this._novalidate);
        }
    };


    // Rule class
    function Rules(obj, context) {
        var that = context ? context === true ? this : context : Rules.prototype;

        if (!isObject(obj)) return;

        for (var k in obj) {
            that[k] = getRule(obj[k]);
        }
    }

    // Message class
    function Messages(obj, context) {
        var that = context ? context === true ? this : context : Messages.prototype;

        if (!isObject(obj)) return;

        for (var k in obj) {
            if (!obj[k]) return;
            that[k] = obj[k];
        }
    }

    // Rule converted factory
    function getRule(fn) {
        switch ($.type(fn)) {
            case 'function':
                return fn;
            case 'array':
                return function(el) {
                    return fn[0].test(el.value) || fn[1] || false;
                };
            case 'regexp':
                return function(el) {
                    return fn.test(el.value);
                };
        }
    }

    // Convert space-separated keys to jQuery selector
    function keys2selector(keys) {
        var selector = '';

        $.map(keys.split(' '), function(k) {
            selector += ',' + (k.charAt(0) === '#' ? k : '[name="' + k + '"]');
        });

        return selector.substring(1);
    }

    // Get instance by an element
    function getInstance(el) {
        var wrap;

        if (!el || !el.tagName) return;
        switch (el.tagName) {
            case 'INPUT':
            case 'SELECT':
            case 'TEXTAREA':
            case 'BUTTON':
            case 'FIELDSET':
                wrap = el.form || $(el).closest('.' + CLS_WRAPPER);
                break;
            case 'FORM':
                wrap = el;
                break;
            default:
                wrap = $(el).closest('.' + CLS_WRAPPER);
        }

        return $(wrap).data(NS) || $(wrap)[NS]().data(NS);
    }

    function initByInput(e) {
        
        var el = e.currentTarget, me;
        if (!el.form || attr(el.form, NOVALIDATE) !== null) return;

        me = getInstance(el);
        if (me) {
            me._parse(el);
            me['_'+e.type](e);
        } else {
            attr(el, DATA_RULE, null);
        }
    }

    // Get custom rules on the node
    function getDataRule(el, method) {
        var fn = $.trim(attr(el, DATA_RULE + '-' + method));

        if (!fn) return;
        fn = (new Function("return " + fn))();
        if (fn) return getRule(fn);
    }

    // Get custom messages on the node
    function getDataMsg(el, field, ret, m) {
        var msg = field.msg,
            item = field._r;

        if (isObject(msg)) msg = msg[item];
        if (!isString(msg)) {
            msg = attr(el, DATA_MSG + '-' + item) || attr(el, DATA_MSG) || ret || ( m ? isString(m) ? m : m[item] : '');
        }

        return msg;
    }

    // Get message position
    function getPos(str) {
        var pos;

        if (str) pos = rPos.exec(str);
        return pos ? pos[1] : '';
    }

    // Check whether the element is checkbox or radio
    function checkable(el) {
        return el.tagName === 'INPUT' && el.type === 'checkbox' || el.type === 'radio';
    }

    // parse date string to timestamp
    function parseDate(str) {
        return Date.parse(str.replace(/\.|\-/g, '/'));
    }


    // Global events
    $(document)
    .on('focusin', ':input['+DATA_RULE+']', function(e) {
        initByInput(e);
    })

    .on('click', 'input,button', function(e){
        var input = this, name = input.name;
        if (!input.form) return;

        if (input.type === 'submit') {
            submitButton = input;
            if (attr(input, NOVALIDATE) !== null) {
                novalidateonce = true;
            }
        }
        else if (name && checkable(input)) {
            var elem = input.form.elements[name];
            if (elem.length) elem = elem[0];
            if (attr(elem, DATA_RULE)) {
                initByInput(e);
            }
        }
    })

    .on('submit validate', 'form', function(e) {
        if (attr(this, NOVALIDATE) !== null) return;

        var $form = $(this), me;

        if (!$form.data(NS)) {
            me = $form[NS]().data(NS);
            if (!$.isEmptyObject(me.fields)) {
                me._submit(e);
            } else {
                attr(this, NOVALIDATE, NOVALIDATE);
                $form.off(CLS_NS).removeData(NS);
            }
        }
    });


    // Built-in rules (global)
    new Rules({

        /** required
         * @example:
            required
         */
        required: function(element, params) {
            var val = $.trim(element.value),
                isValid = true;

            if (params) {
                if (params.length === 1) {
                    if (!val && !this.test(element, params[0]) ) {
                        attr(element, ARIA_REQUIRED, null);
                        return null;
                    } else {
                        attr(element, ARIA_REQUIRED, true);
                    }
                } else if (params[0] === 'not') {
                    $.map(params.slice(1), function(v) {
                        if ( val === $.trim(v) ) {
                            isValid = false;
                        }
                    });
                }
            }

            return isValid && !!val;
        },

        /** integer
         * @example:
            integer
            integer[+]
            integer[+0]
            integer[-]
            integer[-0]
         */
        integer: function(element, params) {
            var re, z = '0|',
                p = '[1-9]\\d*',
                key = params ? params[0] : '*';

            switch (key) {
                case '+':
                    re = p;
                    break;
                case '-':
                    re = '-' + p;
                    break;
                case '+0':
                    re = z + p;
                    break;
                case '-0':
                    re = z + '-' + p;
                    break;
                default:
                    re = z + '-?' + p;
            }
            re = '^(?:' + re + ')$';

            return new RegExp(re).test(element.value) || this.messages.integer[key];
        },

        /** match another field
         * @example:
            match[password]    Match the password field (two values ​​must be the same)
            match[eq, password]  Ditto
            match[neq, count]  The value must be not equal to the value of the count field
            match[lt, count]   The value must be less than the value of the count field
            match[lte, count]  The value must be less than or equal to the value of the count field
            match[gt, count]   The value must be greater than the value of the count field
            match[gte, count]  The value must be greater than or equal to the value of the count field
         **/
        match: function(element, params, field) {
            if (!params) return;

            var me = this,
                a, b,
                key, msg, type = 'eq',
                selector2, elem2, field2;

            if (params.length === 1) {
                key = params[0];
            } else {
                type = params[0];
                key = params[1];
            }

            selector2 = key.charAt(0) === '#' ? key : ':input[name="' + key + '"]';
            elem2 = me.$el.find(selector2)[0];
            // If the compared field is not exist
            if (!elem2) return;
            field2 = me.getField(elem2);
            a = element.value;
            b = elem2.value;

            if (!field._match) {
                me.$el.on('valid'+CLS_NS_FIELD+CLS_NS, selector2, function(){
                    $(element).trigger('validate');
                });
                field._match = field2._match = 1;
            }

            // If both fields are blank
            if (!field.required && a === "" && b === "") {
                return null;
            }

            if (params[2]) {
                if (params[2] === 'date') {
                    a = parseDate(a);
                    b = parseDate(b);
                } else if (params[2] === 'time') {
                    a = +a.replace(':', '');
                    b = +b.replace(':', '');
                }
            }

            // If the compared field is incorrect, we only ensure that this field is correct.
            if (type !== "eq" && !isNaN(+a) && isNaN(+b)) {
                return true;
            }

            msg = me.messages.match[type].replace('{1}', me._getDisplay(element, field2.display || key));
            
            switch (type) {
                case 'lt':
                    return (+a < +b) || msg;
                case 'lte':
                    return (+a <= +b) || msg;
                case 'gte':
                    return (+a >= +b) || msg;
                case 'gt':
                    return (+a > +b) || msg;
                case 'neq':
                    return (a !== b) || msg;
                default:
                    return (a === b) || msg;
            }
        },

        /** range numbers
         * @example:
            range[0~99]    Number 0-99
            range[0~]      Number greater than or equal to 0
            range[~100]    Number less than or equal to 100
         **/
        range: function(element, params) {
            return this.getRangeMsg(+element.value, params, 'range');
        },

        /** how many checkbox or radio inputs that checked
         * @example:
            checked;       no empty, same to required
            checked[1~3]   1-3 items
            checked[1~]    greater than 1 item
            checked[~3]    less than 3 items
            checked[3]     3 items
         **/
        checked: function(element, params, field) {
            if (!checkable(element)) return;

            var me = this,
                elem, count;

            count = me.$el.find('input[name="' + element.name + '"]').filter(function() {
                var el = this;
                if (!elem && checkable(el)) elem = el;
                return !el.disabled && el.checked && $(el).is(':visible');
            }).length;

            if (params) {
                return me.getRangeMsg(count, params, 'checked');
            } else {
                return !!count || getDataMsg(elem, field, '') || me.messages.required;
            }
        },

        /** length of a characters (You can pass the second parameter "true", will calculate the length in bytes)
         * @example:
            length[6~16]        6-16 characters
            length[6~]          Greater than 6 characters
            length[~16]         Less than 16 characters
            length[~16, true]   Less than 16 characters, non-ASCII characters calculating two-character
         **/
        length: function(element, params) {
            var value = element.value,
                len = (params[1] ? value.replace(rDoubleBytes, 'xx') : value).length;

            return this.getRangeMsg(len, params, 'length', (params[1] ? '_2' : ''));
        },

        /** remote validation
         *  remote([get:]url [, name1, [name2 ...]]);
         *  Adaptation three kinds of results (Front for the successful, followed by a failure):
                1. text:
                    ''  'Error Message'
                2. json:
                    {"ok": ""}  {"error": "Error Message"}
                3. json wrapper:
                    {"status": 1, "data": {"ok": ""}}  {"status": 1, "data": {"error": "Error Message"}}
         * @example:
            The simplest:       remote(path/to/server.php);
            With parameters:    remote(path/to/server.php, name1, name2, ...);
            By GET:             remote(get:path/to/server.php, name1, name2, ...);
         */
        remote: function(element, params) {
            if (!params) return;

            var me = this,
                arr = rAjaxType.exec(params[0]),
                url = arr[2],
                type = (arr[1] || 'POST').toUpperCase(),
                search,
                data = {};

            data[element.name] = element.value;
            // There are extra fields
            if (params[1]) {
                $.map(params.slice(1), function(name) {
                    var arr = name.split(':'), selector;
                    name = $.trim(arr[0]);
                    selector = $.trim(arr[1] || '') || name;
                    data[ name ] = me.$el.find( selector.charAt(0)==='#' ? selector : ':input[name="' + selector + '"]').val();
                });
            }
            data = $.param(data);

            if (type === 'POST') {
                search = url.indexOf('?');
                if (search !== -1) {
                    data += '&' + url.substring(search + 1, url.length);
                    url = url.substring(0, search);
                }
            }

            // Asynchronous validation need to return jqXHR objects
            return $.ajax({
                url: url,
                type: type,
                data: data,
                cache: false
            });
        },

        /** filters, direct filtration without prompting error (support custom regular expressions)
         * @example:
         *  filter          filter "<>"
         *  filter(regexp)  filter the "regexp" matched characters
         */
        filter: function(element, params) {
            element.value = element.value.replace( params ? (new RegExp("[" + params[0] + "]", "gm")) : rUnsafe, '' );
        }
    });


    /** @interface: config
     *  @usage:
        .config( obj )
     */
    Validator.config = function(obj) {
        $.each(obj, function(k, o) {
            if (k === 'rules') {
                new Rules(o);
            } else if (k === 'messages') {
                new Messages(o);
            } else {
                defaults[k] = o;
            }
        });
    };

    /** @interface: setTheme
     *  @usage:
        .setTheme( name, obj )
        .setTheme( obj )
     */
    Validator.setTheme = function(name, obj) {
        if (isObject(name)) {
            $.each(name, function(i, o) {
                themes[i] = o;
            });
        } else if (isString(name) && isObject(obj)) {
            themes[name] = obj;
        }
    };

    $[NS] = Validator;

}(jQuery));