<template>
  <el-row>
    <el-col :span="12"><tree  ref="tree" :cfg="treeCfg"></tree></el-col>
    <el-col :span="12"><tree  ref="tree1" :cfg="treeCfg1"></tree></el-col>
  </el-row>
</template>
<script>
export default {
  mounted: function() {},
  data() {
    var self = this;
    return {
      treeCfg: {
        title: "岗位规则",
        // parentTitle: "流程管理",
        // filterType: "combox",
        hideToolBar: true,
        // dialogWidth: "95%",
        hideCheckBox: true,
        url:
          this.getGlobalData().ApiBaseUrl + "/approve/rulelist",
        functions: [
          {
            text: "新增",
            type: "btn-success",
            icon: "el-icon-circle-plus",
            onClick: function() {
              self.$refs.tree.currentComponent = "ruleAdd";
              self.$refs.tree.dialogVisible = true;
            }
          },
          {
            text: "编辑",
            type: "btn-success",
            icon: "el-icon-edit",
            onClick: function() {
              if (self.currentRuleId == 0) {
                self.$message({
                  message: "请先选择岗位规则!",
                  type: "warning"
                });
                return;
              }
              self.$refs.tree.currentComponent = "ruleEdit";
              self.$refs.tree.dialogVisible = true;
            }
          }
        ],
        onNodeClick: function(data) {
          self.currentRuleId = data.id;
          self.$refs.tree1.loadUrl(1, data.id);
        }
      },
      treeCfg1: {
        title: "岗位子规则",
        filterType: "combox",
        dialogWidth: "95%",
        hideCheckBox: true,
        hideToolBar: true,
        url:
          this.getGlobalData().ApiBaseUrl + "/approve/getRuleChildren?ruleId=",
        onNodeClick: function(data) {
          self.currentRuleId2 = data.id;
        },
        functions: [
          {
            text: "新增",
            type: "btn-success",
            icon: "el-icon-circle-plus",
            onClick: function() {
              if (self.currentRuleId == 0) {
                self.$message({
                  message: "请先选择岗位规则!",
                  type: "warning"
                });
                return;
              }
              self.$refs.tree1.currentComponent = "ruleChildAdd";
              self.$refs.tree1.dialogVisible = true;
            }
          },
          {
            text: "编辑",
            type: "btn-success",
            icon: "el-icon-edit",
            onClick: function() {
              if (self.currentRuleId2 == 0) {
                self.$message({
                  message: "请先选择岗位子规则!",
                  type: "warning"
                });
                return;
              }
              self.$refs.tree1.currentComponent = "ruleChildEdit";
              self.$refs.tree1.dialogVisible = true;
            }
          }
        ]
      },
      currentRuleId:0,
      currentRuleId2:0
    };
  },
  updated: function() {
    var self = this;
  }
};
</script>
<style>
.dataTables_filter {
  margin-bottom: -40px;
}
</style>
