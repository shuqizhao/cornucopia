<template>
<body class="hold-transition sidebar-mini">
<div class="wrapper skin-blue" style="min-height:500px;">

  <!-- Main Header -->
  <header class="main-header">

    <!-- Logo -->
    <a href="/" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini" v-html="this.getGlobalData().HomeMiniTitle"></span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg" v-html="this.getGlobalData().HomeTitle"></span>
    </a>

    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
      <!-- Navbar Right Menu -->
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <!-- User Account Menu -->
          <li class="dropdown user user-menu">
            <!-- Menu Toggle Button -->
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <!-- The user image in the navbar-->
              <img :src="avatar" class="user-image" alt="User Image">
              <!-- hidden-xs hides the username on small devices so only the image appears. -->
              <span class="hidden-xs">{{displayName}}</span>
            </a>
            <ul class="dropdown-menu">
              <!-- The user image in the menu -->
              <li class="user-header">
                <img :src="avatar" class="img-circle" alt="User Image">
                <p>
                  {{displayName}}
                  <span v-if="displayTitle"> - {{displayTitle}}</span> 
                  <small>今日时间{{getNow()}}</small>
                </p>
              </li>
              <!-- Menu Body -->
              <!-- <li class="user-body">
                <div class="row">
                  <div class="col-xs-4 text-center">
                    <a href="#">Followers</a>
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="#">Sales</a>
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="#">Friends</a>
                  </div>
                </div>
              </li> -->
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-left">
                  <router-link to="/auth/usermod"><a href="#" class="btn btn-default btn-flat">修改密码</a></router-link>
                </div>
                <div class="pull-right">
                  <a href="#" @click="signOut" class="btn btn-default btn-flat">退出系统</a>
                </div>
              </li>
            </ul>
          </li>
          <!-- Control Sidebar Toggle Button -->
          <li>
            <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
          </li>
        </ul>
      </div>
    </nav>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

      <!-- Sidebar user panel (optional) -->
      <div class="user-panel">
        <div class="pull-left image">
          <img :src="avatar" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>{{displayName}}</p>
          <!-- Status -->
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div>

      <!-- search form (Optional) -->
      <form action="#" method="get" class="sidebar-form">
        <div class="input-group">
          <input type="text" name="q" id="searchText" class="form-control" @keyup="filterMenus" placeholder="Search...">
          <span class="input-group-btn">
              <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
              </button>
            </span>
        </div>
      </form>
      <!-- /.search form -->

      <!-- Sidebar Menu -->
      <ul class="sidebar-menu" data-widget="tree">
        <li class="header">菜单列表</li>
        <!-- Optionally, you can add icons to the links -->
        <li v-for="level0 in menusLevel0" class="treeview menu-open">
          <a href="#"><i class="fa fa-th"></i> <span>{{level0.Name}}</span>
            <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
          </a>
          <ul class="treeview-menu" style="display: block;">
            <li v-for="level1 in getMenusLevel1(level0.Id)"><router-link :to="'/'+level1.Url"><i class="fa fa-circle-o"></i> <span>{{level1.Name}}</span></router-link></li>
          </ul>
        </li>
      </ul>
      <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div>
   <div style="min-height:480px;"><router-view></router-view></div>
  </div>
  <!-- /.content-wrapper -->

  <!-- Main Footer -->
  <footer class="main-footer">
    <!-- To the right -->
    <div class="pull-right hidden-xs">
      Anything you want
    </div>
    <!-- Default to the left -->
    <strong>Copyright &copy; 2018 <a href="#">shuqizhao</a>.</strong> All rights reserved.
  </footer>

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Create the tabs -->
    <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
      <li class="active"><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
      <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
      <!-- Home tab content -->
      <div class="tab-pane active" id="control-sidebar-home-tab">
        <h3 class="control-sidebar-heading">当前时间</h3>
        <ul class="control-sidebar-menu">
          <li>
            <a href="javascript:;">
              <i class="menu-icon fa fa-birthday-cake bg-red"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">{{getNow()}}</h4>

                <p>{{getWeekDay()}}</p>
              </div>
            </a>
          </li>
        </ul>
        <!-- /.control-sidebar-menu -->

        <!-- <h3 class="control-sidebar-heading">Tasks Progress</h3>
        <ul class="control-sidebar-menu">
          <li>
            <a href="javascript:;">
              <h4 class="control-sidebar-subheading">
                Custom Template Design
                <span class="pull-right-container">
                    <span class="label label-danger pull-right">70%</span>
                  </span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-danger" style="width: 70%"></div>
              </div>
            </a>
          </li>
        </ul> -->
        <!-- /.control-sidebar-menu -->

      </div>
      <!-- /.tab-pane -->
      <!-- Stats tab content -->
      <div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div>
      <!-- /.tab-pane -->
      <!-- Settings tab content -->
      <div class="tab-pane" id="control-sidebar-settings-tab">
        <form method="post">
          <h3 class="control-sidebar-heading">个性设置</h3>

          <div class="form-group">
            <label class="control-sidebar-subheading">
              保持登陆状态
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              持续一个月时间
            </p>
          </div>
          <!-- /.form-group -->
        </form>
      </div>
      <!-- /.tab-pane -->
    </div>
  </aside>
  <!-- /.control-sidebar -->
  <!-- Add the sidebar's background. This div must be placed
  immediately after the control sidebar -->
  <div class="control-sidebar-bg"></div>
</div>
</body>
</template>

<script>
export default {
  name: "app",
  mounted: function() {
    this.getMenus();
    this.displayName = this.getCookie("loginUser");
  },
  data() {
    return {
      avatar: this.getGlobalData().Avatar,
      menus: [],
      menusLevel0: [],
      menusLevel1: [],
      displayName: "",
      displayTitle: ""
    };
  },
  methods: {
    signOut: function() {
      var self = this;
      self.clearCookie(
        self.getGlobalData().LoginCookeName,
        self.getGlobalData().Domain
      );
      window.location.reload();
    },
    getMenus: function() {
      var self = this;
      var config = this.getGlobalData();
      var menuUrl = config.ApiBaseUrl + config.HomeMenusUrl;
      $.ajax({
        url: menuUrl,
        xhrFields: {
          withCredentials: true
        },
        success: function(result) {
          if (result.code == 200) {
            self.menus = result.data;
            self.menusLevel0 = self.getMenusLevel0();
          }
        }
      });
    },
    getMenusLevel0: function() {
      return this.menus.filter(this.filterLevel0);
    },
    filterLevel0: function(item) {
      if (item.ParentId == 0) {
        // var searchText=$('#searchText').val();
        // if(searchText){
        //   if(item.Name.indexOf(searchText)!=-1){
        //     return true;
        //   }else{
        //     return false;
        //   }
        // }
        return true;
      } else {
        return false;
      }
    },
    getMenusLevel1: function(id) {
      var arr = [];
      for (var i = 0; i < this.menus.length; i++) {
        if (this.menus[i].ParentId == id) {
           var searchText=$('#searchText').val();
            if(searchText){
              if(this.menus[i].Name.indexOf(searchText)!=-1){
                arr.push(this.menus[i]);
              }
            }else{
              arr.push(this.menus[i]);
            }
        }
      }
      return arr;
    },
    getNow: function() {
      var d = new Date();
      var str = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
      return str;
    },
    getWeekDay: function() {
      var str = "今天是星期";
      var week = new Date().getDay();
      switch (week) {
        case 0:
          str += "日";
          break;
        case 1:
          str += "一";
          break;
        case 2:
          str += "二";
          break;
        case 3:
          str += "三";
          break;
        case 4:
          str += "四";
          break;
        case 5:
          str += "五";
          break;
        case 6:
          str += "六";
          break;
      }
      return str;
    },
    filterMenus: function() {
       this.menusLevel0 = this.getMenusLevel0();
    }
  }
};
</script>

<style>

</style>