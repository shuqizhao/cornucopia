<template>
  <el-row>
    <el-col :span="4"><tree  ref="tree" :cfg="treeCfg"></tree></el-col>
    <el-col :span="4"><tree  ref="tree1" :cfg="treeCfg1"></tree></el-col>
    <el-col :span="16"><list ref="positionList" :cfg="cfg1"></list></el-col>
  </el-row>
</template>
<script>
export default {
  mounted: function() {},
  data() {
    var self = this;
    return {
      treeCfg: {
        title: "审批路线(一)",
        // parentTitle: "流程管理",
        filterType: "combox",
        hideToolBar: true,
        dialogWidth: "95%",
        hideCheckBox: true,
        option1Url: "/process/alllist",
        option2Url:
          "/processnode/alllist?processId=",
        url:"/approve/alllist?processNodeId=",
        functions: [
          {
            text: "新增",
            type: "btn-success",
            icon: "el-icon-circle-plus",
            onClick: function() {
              if (!self.$refs.tree.value2) {
                self.$message({
                  message: "请先选择流程节点!",
                  type: "warning"
                });
                return;
              }
              self.$refs.tree.currentComponent = "approveAdd";
              self.$refs.tree.dialogVisible = true;
            }
          },
          {
            text: "编辑",
            type: "btn-success",
            icon: "el-icon-edit",
            onClick: function() {
              if (!self.$refs.tree.value2) {
                self.$message({
                  message: "请先选择流程节点!",
                  type: "warning"
                });
                return;
              }
              if (self.currentApproveId == 0) {
                self.$message({
                  message: "请先选择审批路线(一)!",
                  type: "warning"
                });
                return;
              }
              self.$refs.tree.currentComponent = "approveEdit";
              self.$refs.tree.dialogVisible = true;
            }
          }
        ],
        onNodeClick: function(data) {
          self.currentApproveId = data.id;
          self.currentApproveId2 = 0;
          self.$refs.tree1.loadUrl(1, data.id);
        },
        option1Change: function() {
          self.currentApproveId = 0;
          self.currentApproveId2 = 0;
          self.$refs.tree.clearOption2();
          self.$refs.tree.clearData();
          self.$refs.tree1.clearData();
          self.$refs.positionList.clearData();
        },
        option2Change: function() {
          self.currentApproveId = 0;
          self.currentApproveId2 = 0;
          self.$refs.tree1.clearData();
          self.$refs.positionList.reloadSimpleData(
              "/approve/positionlist?processNodeId=" +
              (self.$refs.tree.value2 || 0)
          );
        }
      },
      treeCfg1: {
        title: "审批路线(二)",
        // parentTitle: "流程管理",
        filterType: "combox",
        dialogWidth: "95%",
        hideCheckBox: true,
        hideToolBar: true,
        url:"/approve/allchildren?approveId=",
        onNodeClick: function(data) {
          self.currentApproveId2 = data.id;
        },
        functions: [
          {
            text: "新增",
            type: "btn-success",
            icon: "el-icon-circle-plus",
            onClick: function() {
              if (self.currentApproveId == 0) {
                self.$message({
                  message: "请先选择审批路线(一)!",
                  type: "warning"
                });
                return;
              }
              self.$refs.tree1.currentComponent = "approveAdd";
              self.$refs.tree1.dialogVisible = true;
            }
          },
          {
            text: "编辑",
            type: "btn-success",
            icon: "el-icon-edit",
            onClick: function() {
              if (self.currentApproveId2 == 0) {
                self.$message({
                  message: "请先选择审批路线(二)!",
                  type: "warning"
                });
                return;
              }
              self.$refs.tree1.currentComponent = "approveEdit";
              self.$refs.tree1.dialogVisible = true;
            }
          }
        ]
      },
      cfg1: {
        title: "审批岗位",
        // parentTitle: "流程管理",
        lengthMenu: [[-1], ["ALL"]],
        sDom: 'f<"dataTables_function"/>',
        bServerSide: false,
        hideCheckBox: true,
        dialogWidth: "70%",
        showSelectedRowColor: true,
        columns: [
          {
            title: "id",
            name: "id",
            isHide: true
          },
          {
            title: "岗位名",
            name: "name"
          },
          {
            title: "岗位类型",
            name: "type"
          },
          {
            title: "岗位规则",
            name: "ruleStr"
          },
          {
            title: "是否启用",
            name: "isEnabled"
          },
          {
            title: "虚拟职位",
            name: "vitualTitle"
          },
          {
            name: "preSign",
            title: "前加签",
            type: "yesno"
          },
          {
            name: "afterSign",
            title: "后加签",
            type: "yesno"
          },
          {
            name: "transfer",
            title: "转办",
            type: "yesno"
          },
          {
            name: "modify",
            title: "申请人修订",
            type: "yesno"
          },
          {
            title: "创建时间",
            name: "createTime"
          }
        ],
        idName: "id",
        fnRowCallback: function(row, data) {
          if (data.isEnabled) {
            $("td:eq(3)", row).html('<i class="fa fa-fw fa-check-circle"></i>');
          } else {
            $("td:eq(3)", row).html('<i class="el-icon-close"></i>');
          }

          if (data.preSign) {
            $("td:eq(5)", row).html('<i class="fa fa-fw fa-check-circle"></i>');
          } else {
            $("td:eq(5)", row).html('<i class="el-icon-close"></i>');
          }

          if (data.afterSign) {
            $("td:eq(6)", row).html('<i class="fa fa-fw fa-check-circle"></i>');
          } else {
            $("td:eq(6)", row).html('<i class="el-icon-close"></i>');
          }

          if (data.transfer) {
            $("td:eq(7)", row).html('<i class="fa fa-fw fa-check-circle"></i>');
          } else {
            $("td:eq(7)", row).html('<i class="el-icon-close"></i>');
          }

          if (data.modify) {
            $("td:eq(8)", row).html('<i class="fa fa-fw fa-check-circle"></i>');
          } else {
            $("td:eq(8)", row).html('<i class="el-icon-close"></i>');
          }

          if (data.type==1) {
            $("td:eq(1)", row).html('角色');
          } else {
            $("td:eq(1)", row).html('规则');
          }

        },
        functions: {
          common: [
            {
              text: "添加岗位",
              url: "approvePositionAdd",
              mode: "modal",
              functionName: "approvePositionAdd"
            },
            {
              text: "编辑岗位",
              url: "approvePositionEdit",
              mode: "modal",
              functionName: "approvePositionEdit"
            }
          ],
          more: [
            {
              text: "停用",
              url: "/process/disable"
            },
            {
              text: "启用",
              url: "/process/enable"
            },
            {
              text: "删除",
              url: "/process/delete"
            }
          ]
        },
        onClickRow: function(data, target) {
          self.currentPositionId = data.id;
        },
        
      },
      currentApproveId: 0,
      currentApproveId2: 0,
      currentPositionId:0
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
