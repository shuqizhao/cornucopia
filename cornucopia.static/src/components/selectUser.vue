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
  },
  data() {
    var self = this;
    return {
      cfg: {
        url: "/auth/allOrg",
        hideCheckBox: true,
        onNodeClick: function(data) {
          self.$refs.user.fillData({
            orgId: data.id
          });
        }
      },
      cfgUser: {
        autoLoad: false,
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
