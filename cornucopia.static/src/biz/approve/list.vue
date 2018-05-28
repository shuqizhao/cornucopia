<template>
  <el-row>
    <el-col :span="6"><tree  ref="tree" :cfg="treeCfg"></tree></el-col>
    <el-col :span="6"><tree  ref="tree" :cfg="treeCfg1"></tree></el-col>
    <el-col :span="12"><list :cfg="cfg1"></list></el-col>
  </el-row>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      treeCfg: {
        title: "审批路线(一)",
        parentTitle: "流程管理",
        filterType:"combox",
        hideToolBar:true,
        options1: [{
          value: '1',
          label: '离职流程'
        }],
        options2: [{
          value: '1',
          label: 'Doa节点'
        }],
      },
       treeCfg1: {
        title: "审批路线(二)",
        parentTitle: "流程管理",
        filterType:"combox",
        hideToolBar:true,
        // url: this.getGlobalData().ApiBaseUrl + "/auth/allResource",
        functions: [
          {
            text: "保存",
            type: "btn-success",
            icon: "el-icon-success",
            onClick: function() {
              if (!self.currentRoleId) {
                self.$message({
                  message: "请先选择角色!",
                  type: "warning"
                });
                return;
              }
              self
                .$confirm("确定要保存吗", "提示", {
                  confirmButtonText: "确定",
                  cancelButtonText: "取消",
                  type: "info"
                })
                .then(() => {
                  $.ajax({
                    type: "POST",
                    xhrFields: {
                      withCredentials: true
                    },
                    data: {
                      roleId: self.currentRoleId,
                      checkedList: self.$refs.tree.getCheckedKeys()
                    },
                    url:
                      self.getGlobalData().ApiBaseUrl + "/auth/saveCheckedList",
                    success: function(response) {
                      if (response.code == "200") {
                        self.$message({
                          message: "操作成功!",
                          type: "success"
                        });
                      } else if (response.message) {
                        self.$message({
                          type: "warning",
                          message: response.message
                        });
                      }
                    }
                  });
                });
            }
          }
          // {
          //   text: "全选",
          //   type: "btn-success",
          //   icon:'el-icon-circle-check-outline',
          //   onClick:function(){
          //     self.$refs.tree.checkAll();
          //   }
          // },
          // {
          //   text: "清空",
          //   type: "btn-success",
          //   icon:'el-icon-circle-close-outline',
          //   onClick:function(){
          //     self.$refs.tree.clearAll();
          //   }
          // }
        ]
      },
      cfg1: {
        title: "审批岗位",
        parentTitle: "流程管理",
        simpleUrl: this.getGlobalData().ApiBaseUrl + "/process/alllist",
        lengthMenu: [[-1], ["ALL"]],
        sDom: 'f<"dataTables_function"/>',
        bServerSide: false,
        hideCheckBox: true,
        showSelectedRowColor: true,
        columns: [
          {
            title: "id",
            name: "id",
            isHide: true
          },
          {
            title: "节点名",
            name: "name"
          },
          {
            title: "是否启用",
            name: "isEnabled"
          },
          {
            title: "创建时间",
            name: "createTime"
          }
        ],
        idName: "id",
        fnRowCallback: function(row, data) {
          if (data.isEnabled) {
            $("td:eq(1)", row).html('<i class="fa fa-fw fa-check-circle"></i>');
          } else {
            $("td:eq(1)", row).html('<i class="el-icon-close"></i>');
          }
        },
        functions: {
          common: [
            {
              text: "添加岗位",
              url: "processAdd",
              mode: "modal",
              functionName: "processAdd"
            }
          ],
          more: [
            {
              text: "停用",
              url: this.getGlobalData().ApiBaseUrl + "/process/disable"
            },
            {
              text: "启用",
              url: this.getGlobalData().ApiBaseUrl + "/process/enable"
            },
            {
              text: "删除",
              url: this.getGlobalData().ApiBaseUrl + "/process/delete"
            }
          ]
        },
        onClickRow: function(data, target) {
          self.$refs.tree.cfg.title = data.name;
          self.openLoading($("#resTree")[0]);
          $.ajax({
            type: "GET",
            xhrFields: {
              withCredentials: true
            },
            url:
              self.getGlobalData().ApiBaseUrl +
              "/auth/getCheckedList?roleId=" +
              data.id,
            success: function(response) {
              if (response.code == "200") {
                self.$refs.tree.setCheckedKeys(response.data);
                self.closeLoading();
                self.currentRoleId = data.id;
              } else if (response.message) {
                self.$message({
                  type: "warning",
                  message: response.message
                });
              }
            }
          });
        }
      }
    };
  },
  updated: function() {
    var self = this;
    // self.$parent.title = self.cfg.title;
    // self.$parent.parentTitle = self.cfg.parentTitle;
  }
};
</script>
<style>
.dataTables_filter {
  margin-bottom: -40px;
}
</style>
