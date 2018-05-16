import Vue from 'vue'
import router from '../src/components/router'

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