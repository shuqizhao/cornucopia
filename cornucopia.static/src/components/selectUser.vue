<template>
  <el-row>
    <el-col :span="8">
      <tree ref="tree" :cfg="cfg"></tree>
    </el-col>
    <el-col :span="16">
      <listV2 ref="user" :cfg="cfgUser"></listV2>
    </el-col>
    
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
        // title: "组织机构",
        // parentTitle: "权限管理",
        url: "/auth/allOrg",
        hideCheckBox: true
      },
      cfgUser: {
        // title: "用户管理",
        // parentTitle: "系统管理",
        autoLoad:false,
        url: "/user/list",
        columns: [
          {
            title: "用户名",
            name: "name",
            isSearch: true
          },
          {
            title: "工号",
            name: "personNumber"
          },
          {
            title: "职位",
            name: "jobId"
          },
          {
            title: "工作邮箱",
            name: "email"
          },
          {
            title: "所属上级",
            name: "managerId"
          },
          {
            title: "所在部门",
            name: "orgId"
          }
        ],
        idName: "id"
      },
      id: 0,
      parainst: {}
    };
  },
  updated: function() {
    var self = this;
  },
  methods: {}
};
</script>
<style>
.el-dialog--center .el-dialog__body {
    padding: 30px 20px;
    color: #606266;
    line-height: 24px;
    font-size: 14px;
}
</style>
