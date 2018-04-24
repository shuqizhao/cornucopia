<template>
<body class="hold-transition login-page">
  <div class="login-box">
  <div class="login-logo" v-html="this.getGlobalData().LoginTitle">
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">{{this.getGlobalData().LoginBoxMsg}}</p>

    <form onsubmit="return false;" method="post">
      <div class="form-group has-feedback">
        <input type="text" id="un" class="form-control" placeholder="UserName">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" id="pwd" class="form-control" placeholder="Password">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
         <el-checkbox label="下次自动登录" border></el-checkbox>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button id="signin" @click="submit" class="btn btn-primary btn-block btn-flat">登录</button>
        </div>
        <!-- /.col -->
      </div>
    </form>

    <div class="social-auth-links text-center">
      <p>- OR -</p>
      <a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-wechat"></i><center>联系管理员</center></a>
      <a href="#" class="btn btn-block btn-social btn-google btn-flat"><i class="fa fa-eye"></i><center>参阅文档</center></a>
    </div>
    <!-- /.social-auth-links -->

    <!-- <a href="#">I forgot my password</a><br> -->
    <!-- <a href="register.html" class="text-center">Register a new membership</a> -->

  </div>
  <!-- /.login-box-body -->
</div>
</body>
</template>
<script>
export default {
  data() {
    return {
      title: ""
    };
  },
  mounted: function() {},
  methods: {
    submit: function() {
      var self = this;
      var config = this.getGlobalData();
      var loginUrl = config.ApiBaseUrl + config.LoginUrl;

      $.ajax({
        url: loginUrl,
        xhrFields: {
          withCredentials: true
        },
        type: "POST",
        data: {
          Un: $("#un").val(),
          Pwd: $("#pwd").val()
        },
        success: function(result) {
          if (result.code == 200 && result.data == 1) {
            window.location.reload()
          } else {
            self.$message({
              message: "用户名或密码错误",
              type: "warning"
            });
          }
        }
      });
    },
    getFalse: function() {
      return false;
    }
  }
};
</script>
<style>

</style>