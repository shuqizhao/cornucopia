<template>
    <home v-if="isLogin"></home>
    <login v-else></login>
</template>

<script>
import Vue from "vue";

import "bootstrap/dist/css/bootstrap.css";

//AdminLTE
import "admin-lte/dist/css/skins/_all-skins.min.css";
import "admin-lte/dist/css/AdminLTE.min.css";

//font-awesome
import "font-awesome/css/font-awesome.min.css";

import "ionicons/dist/css/ionicons.min.css";

import "admin-lte/dist/css/skins/skin-blue.min.css";

import "bootstrap/dist/js/bootstrap";
import "admin-lte/dist/js/adminlte";

import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";

import "../components/select2select";

Vue.use(ElementUI);

const Home = r =>
  require.ensure([], () => r(require("../components/home.vue")), "home");

const Login = r =>
  require.ensure([], () => r(require("../components/login.vue")), "login");
export default {
  data() {
    return {
      isLogin: this.getCookie(this.getGlobalData().LoginCookeName),
      isNeedWatch: false
    };
  },
  mounted: function() {
    this.watchLoginCookie();
    if (this.isLogin) {
      this.isNeedWatch = true;
    }
    this.show = true;
  },
  methods: {
    watchLoginCookie: function() {
      var self = this;
      setInterval(function() {
        if (
          !self.getCookie(self.getGlobalData().LoginCookeName) &&
          self.isNeedWatch
        ) {
          window.location.reload();
        }
      }, 100);
    }
  },
  components: {
    home: Home,
    login: Login
  }
};
</script>

<style>

</style>
