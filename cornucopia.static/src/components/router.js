import Vue from 'vue'
import VueRouter from 'vue-router'
// import VueResource from 'vue-resource'

Vue.component('mimiApp', function(resolve) {
    require(['../components/mimiApp.vue'], resolve)
})

Vue.component('loginOrHome', function(resolve) {
    require(['../components/loginOrHome.vue'], resolve)
})

Vue.component('list', function(resolve) {
    require(['../components/list.vue'], resolve)
})

Vue.component('listV2', function(resolve) {
    require(['../components/listOfEleme.vue'], resolve)
})

Vue.component('editList', function(resolve) {
    require(['../components/editList.vue'], resolve)
})

Vue.component('mform', function(resolve) {
    require(['../components/form.vue'], resolve)
})

Vue.component('loading', function(resolve) {
    require(['../components/loading.vue'], resolve)
})

Vue.component('tree', function(resolve) {
    require(['../components/tree.vue'], resolve)
})

Vue.component('buttonBar', function(resolve) {
    require(['../components/buttonBar.vue'], resolve)
})

Vue.component('comment', function(resolve) {
    require(['../components/comment.vue'], resolve)
})

Vue.component('apply', function(resolve) {
    require(['../components/apply.vue'], resolve)
})

jQuery.ajaxSetup({
    xhrFields: {
        withCredentials: true
    }
});

Vue.prototype.validateXML = function(xmlContent) {
    //errorCode 0是xml正确，1是xml错误，2是无法验证 
    var xmlDoc, errorMessage, errorCode = 0;
    // code for IE 
    if (window.ActiveXObject) {
        xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
        xmlDoc.async = "false";
        xmlDoc.loadXML(xmlContent);

        if (xmlDoc.parseError.errorCode != 0) {
            errorMessage = "错误code: " + xmlDoc.parseError.errorCode + "\n";
            errorMessage = errorMessage + "错误原因: " + xmlDoc.parseError.reason;
            errorMessage = errorMessage + "错误位置: " + xmlDoc.parseError.line;
            errorCode = 1;
        } else {
            errorMessage = "格式正确";
        }
    }
    // code for Mozilla, Firefox, Opera, chrome, safari,etc. 
    else if (document.implementation.createDocument) {
        var parser = new DOMParser();
        xmlDoc = parser.parseFromString(xmlContent, "text/xml");
        var error = xmlDoc.getElementsByTagName("parsererror");
        if (error.length > 0) {
            if (xmlDoc.documentElement.nodeName == "parsererror") {
                errorCode = 1;
                errorMessage = xmlDoc.documentElement.childNodes[0].nodeValue;
            } else {
                errorCode = 1;
                errorMessage = xmlDoc.getElementsByTagName("parsererror")[0].innerHTML;
            }
        } else {
            errorMessage = "格式正确";
        }
    } else {
        errorCode = 2;
        errorMessage = "浏览器不支持验证，无法验证xml正确性";
    }
    return {
        "msg": errorMessage,
        "error_code": errorCode
    };
};
var jsonData = {};
$.ajax({
    url: '/config.json',
    async: false,
    success: function(result) {
        jsonData = result;
    },
    dataType: 'json'
});
Vue.prototype.getGlobalData = function() {
    return jsonData;
}
Vue.prototype.setCookie = function(cname, cvalue, exdays, domain) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
        var expires = "expires=" + d.toUTCString();
        var domain = "path=/;domain=" + domain;
        console.info(cname + "=" + cvalue + "; " + expires);
        document.cookie = cname + "=" + cvalue + "; " + expires + ";" + domain;
        console.info(document.cookie);
    }
    //获取cookie
Vue.prototype.getCookie = function(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') c = c.substring(1);
            if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
        }
        return "";
    }
    //清除cookie
Vue.prototype.clearCookie = function(cname, domain) {
    this.setCookie(cname, "", -1, domain);
}

Vue.prototype.openLoading = function(el, id, msg, timeout) {
    let self = this;
    let targetTemp = self;
    if (el) {
        targetTemp = el;
    }
    let idTemp = targetTemp.$vnode.tag;
    if (id) {
        idTemp = id;
    }
    self[idTemp] = self.$loading({
        lock: true,
        text: msg || '拼命加载中...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
        target: targetTemp.$el
    });
    setTimeout(() => {
        self[idTemp].close();
    }, timeout || 5000);
}

Vue.prototype.closeLoading = function(el, id) {
    let self = this;
    let targetTemp = self;
    if (el) {
        targetTemp = el;
    }
    let idTemp = targetTemp.$vnode.tag;
    if (id) {
        idTemp = id;
    }
    if (self[idTemp]) {
        self[idTemp].close();
    }
}

/**
 *  加载模块
 */
Vue.use(VueRouter)

/**
 *  配置路由
 */
var menuData = {};
var functions = [];
var modals = [];
$.ajax({
    url: jsonData.ApiBaseUrl + '/auth/routerList',
    async: false,
    success: function(result) {
        menuData = result.data;
    },
    dataType: 'json'
});
var systemRouters = [];
for (var i = 0; i < menuData.length; i++) {
    let routerName = menuData[i].routerName;
    let url = menuData[i].Url;
    let functionName = menuData[i].functionName;
    let type = menuData[i].type;
    if (type == 1) {
        modals.push(functionName);
    }
    if (functionName) {
        functions.push(functionName);
    }
    if (url) {
        if (menuData[i].Type == 1) {
            Vue.component(routerName, r => require.ensure([], () => r(require('../biz' + url + '.vue')), 'biz'))
        } else {
            systemRouters.push({
                path: routerName,
                component: r => require.ensure([], () => r(require('../biz' + url + '.vue')), 'biz')
            });
        }
    }
}

Vue.prototype.showFunction = function(functionName) {
    var isUse = true;
    if (functionName) {
        isUse = false;
        if ($.inArray(functionName, functions) != -1) {
            isUse = true;
        }
    }
    return isUse;
}

Vue.prototype.showModal = function(functionName) {
    var isUse = false;
    if (functionName) {
        if ($.inArray(functionName, modals) != -1) {
            isUse = true;
        }
    }
    return isUse;
}

Vue.prototype.getButtonIcon = function(functionName) {
    if (!functionName) return "";
    for (var i = 0; i < menuData.length; i++) {
        var tempName = menuData[i].functionName;
        if (tempName == functionName) {
            return menuData[i].Icon;
        }
    }
    return '';
}

Vue.prototype.setBreadcrumbTitle = function(self, parentTitle, title, width) {
    var myWidth = width || '100%';
    if (title) {
        document.title = '流程管理系统';
        self.$root.$children[0].$children[0].$children[0].$children[0].showBreadcrumbTitle = true;
        self.$root.$children[0].$children[0].$children[0].$children[0].contentWidth = myWidth;
    } else {
        document.title = title;
        self.$root.$children[0].$children[0].$children[0].$children[0].showBreadcrumbTitle = false;
        self.$root.$children[0].$children[0].$children[0].$children[0].contentWidth = myWidth;
    }

    self.$root.$children[0].$children[0].$children[0].$children[0].breadcrumbTitle = title;
    self.$root.$children[0].$children[0].$children[0].$children[0].breadcrumbParentTitle = parentTitle;
}

Vue.prototype.setDocumentTitle = function(self, title, width) {
    var myWidth = width || '80%';
    document.title = title;
    self.$root.$children[0].$children[0].$children[0].$children[0].showBreadcrumbTitle = false;
    self.$root.$children[0].$children[0].$children[0].$children[0].contentWidth = myWidth;

    self.$root.$children[0].$children[0].$children[0].$children[0].breadcrumbTitle = '';
    self.$root.$children[0].$children[0].$children[0].$children[0].breadcrumbParentTitle = '';
}

function findRefStep(children, refName) {
    if (children && children.length > 0) {
        for (var i = 0; i < children.length; i++) {
            var child = children[i];
            if (child.$refs[refName]) {
                return child.$refs[refName];
            } else {
                var a = findRefStep(child.$children, refName);
                if (a) {
                    return a;
                }
            }
        }
    }
    return null;
}

Vue.prototype.findRef = function(refName) {
    return findRefStep(this.$root.$children, refName);
}

Vue.prototype.findCfgItem = function(cfg, itemName) {
    for (var i = 0; i < cfg.items.length; i++) {
        if (cfg.items[i].name == itemName) {
            return cfg.items[i];
        }
    }
    return null;
}

Vue.prototype.findCfgBtn = function(cfg, btnName) {
    for (var i = 0; i < cfg.buttons.length; i++) {
        if (cfg.buttons[i].name == btnName) {
            return cfg.buttons[i];
        }
    }
    return null;
}

function S4() {
    return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
}
Vue.prototype.newGuid = function() {
    return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
}

Vue.prototype.post = function(p) {
    if (p.dataType == 'json') {
        $.ajax({
            type: 'POST',
            xhrFields: {
                withCredentials: true
            },
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            async: p.async || true,
            url: jsonData.ApiBaseUrl + p.url,
            data: JSON.stringify(p.data) || [],
            success: function(result) {
                if (p.success) {
                    p.success(result);
                }
            }
        });
    } else {
        $.ajax({
            type: 'POST',
            xhrFields: {
                withCredentials: true
            },
            async: p.async || true,
            url: jsonData.ApiBaseUrl + p.url,
            data: p.data || [],
            success: function(result) {
                if (p.success) {
                    p.success(result);
                }
            }
        });
    }
}

Vue.prototype.get = function(p) {
    $.ajax({
        type: 'GET',
        xhrFields: {
            withCredentials: true
        },
        async: typeof(p.async) == "undefined"? true : p.async,
        url: jsonData.ApiBaseUrl + p.url,
        data: p.data || '',
        success: function(result) {
            if (p.success) {
                p.success(result);
            }
        }
    });
}

if (Vue.prototype.getCookie("adAuthCookie")) {
    $.ajax({
        url: jsonData.ApiBaseUrl + jsonData.UserUrl,
        success: function(result) {
            if (result.code == 200) {
                jsonData.LoginUser = result.data
            }
        },
        dataType: 'json'
    });
}

const router = new VueRouter({
    routes: systemRouters
});

export default router