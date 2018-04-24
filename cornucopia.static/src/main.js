import Vue from 'vue'
import '../src/components/register'
//start
import router from './router/router'

import App from './App.vue'

//获取当前页面路由信息
router.afterEach(function(transition) {
    console.log("当前路由参数")
    console.log(transition)
})
// document.domain = "mycrm.shop";
new Vue({
    router,
    render: h => h(App)
}).$mount('#app')