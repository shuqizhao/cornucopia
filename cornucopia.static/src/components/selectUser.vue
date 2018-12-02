<template>
  <el-row>
    <el-col :span="12"><tree  ref="tree" :cfg="cfg"></tree></el-col>
    <el-col :span="12"><list ref="user" :cfg="cfgUser"></list></el-col>
  </el-row>
</template>
<script>
export default {
  mounted: function() {
    var self = this;
    // this.openLoading(self.$refs.func,"func");
    // var parainstId = self.$parent.$parent.getPopupValue();
    // if (parainstId) {
    //   this.openLoading(self.$refs.para, "para");
    //   self.get({
    //     url: "/function/getParainst?parainstId=" + parainstId,
    //     success: function(response) {
    //       if (response.code == 200) {
    //         self.parainst = response.data;
    //         self.getPara(self.parainst.functionId);
    //         self.$refs.func.reloadSimpleData(
    //           "/function/alllist?id=" + self.parainst.functionId
    //         );
    //       }
    //     }
    //   });
    // }
  },
  data() {
    var self = this;
    return {
      cfg: {
        title: "组织机构",
        parentTitle: "权限管理",
        url:  "/auth/allOrg",
      },
      cfgUser: {
        // isShowSearchArea:"true",
        scrollX: true,
        scrollCollapse: true,
        fixedColumns: {
          leftColumns: 4
        },
        title: "用户管理",
        parentTitle: "系统管理",
        url:  "/user/list",
        columns: [
          {
            title: "用户名",
            name: "name",
            isSearch: true
          },
          {
            title: "是否启用",
            name: "isEnabled",
            isSearch: true,
            type: "combox",
            data: [
              {
                id: "2",
                value: "不限"
              },
              {
                id: "1",
                value: "是"
              },
              {
                id: "0",
                value: "否"
              }
            ]
          },
          {
            title: "工号",
            name: "personNumber",
            isSearch: true
          },{
            title: "职位",
            name: "jobId"
          },{
            title: "工作邮箱",
            name: "email"
          },{
            title: "手机号码",
            name: "phone"
          },{
            title: "所属上级",
            name: "managerId"
          },{
            title: "所在部门",
            name: "orgId"
          },
            {
            title: "创建时间",
            name: "createTime",
            isSearch: true,
            type: "timer"
          },
           {
            title: "修改时间",
            name: "updateTime",
            isSearch: true,
            type: "timer"
          },
          {
            title: "最后登录时间",
            name: "lastLoginTime"
          }
        ],
        fnRowCallback: function(row, data) {
          if (data.isEnabled) {
            $("td:eq(3)", row).html('<i class="fa fa-fw fa-check-circle"></i>');
          } else {
            $("td:eq(3)", row).html('<i class="el-icon-close"></i>');
          }
        },
        idName: "id",
        functions: {
          more: [
            {
              text: "停用",
              url:  "/user/disable",
              functionName:'userDisable'
            },
            {
              text: "启用",
              url:  "/user/enable",
              functionName:'userEnable'
            }
          ],
          common: [
            {
              text: "添加用户",
              url: "/auth/userAdd",
              mode: "navigate",
              functionName:'userAdd'
            }
          ]
        },
        operations: [
          {
            text: "查看",
            url: "/auth/userView"
          }
        ]
      },
      id: 0,
      parainst: {}
    };
  },
  updated: function() {
    var self = this;
  },
  methods: {
    
  }
};
</script>
<style>
.dataTables_filter {
  margin-bottom: -40px;
}
</style>
