<template>
  <div>
    <el-row>
      <el-col :span="8">
        <listV2 :cfg="cfgRole"></listV2>
      </el-col>
      <el-col :span="8">
        <listV2 ref="user" :cfg="cfgUser"></listV2>
      </el-col>
      <el-col :span="8">
        <tree ref="tree" :cfg="treeCfg"></tree>
      </el-col>
    </el-row>
    <el-dialog
      ref="applyDialog"
      append-to-body
      v-if="dialogVisible"
      :visible.sync="dialogVisible"
      :center="true"
      :width="'65%'"
      :title="applyDialogTitle"
    >
      <selectUser
        ref="myApplyCompent"
        :multiple="true"
        style="margin-top:-40px;margin-bottom:-40px;"
      ></selectUser>
      <span slot="footer" class="dialog-footer">
        <el-button @click="onDialogBtnCancel">取 消</el-button>
        <el-button type="primary" @click="onDialogBtnOk">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import selectUser from "../../components/selectUser.vue";
export default {
  components: {
    selectUser
  },
  data() {
    var self = this;
    return {
      applyDialogTitle: "",
      dialogVisible: false,
      currentComponent: "",
      cfgRole: {
        title: "角色管理",
        parentTitle: "权限管理",
        url: "/role/list",
        showRadio: true,
        // showCheckBox:false,
        columns: [
          // {
          //   title: "id",
          //   name: "id",
          //   isHide: true
          // },
          {
            title: "角色名",
            name: "name",
            isSearch: true
          },
          {
            title: "是否启用",
            name: "isEnabled",
            formatter: function(data) {
              if (data.isEnabled) {
                return '<center><i class="fa fa-fw fa-check-circle"></i></center>';
              } else {
                return '<center><i class="el-icon-close"></i></center>';
              }
            }
          },
          {
            title: "创建时间",
            name: "createTime"
          }
        ],
        idName: "id",
        // fnRowCallback: function(row, data) {
        //   if (data.isEnabled) {
        //     $("td:eq(1)", row).html('<i class="fa fa-fw fa-check-circle"></i>');
        //   } else {
        //     $("td:eq(1)", row).html('<i class="el-icon-close"></i>');
        //   }
        // },
        functions: {
          common: [
            {
              text: "添加角色",
              url: "roleAdd",
              mode: "modal"
            }
          ],
          more: [
            {
              text: "停用",
              url: "/role/disable"
            },
            {
              text: "启用",
              url: "/role/enable"
            },
            {
              text: "删除",
              url: "/role/delete"
            }
          ]
        },
        onRowSelected: function(datas) {
          var data = datas[0];
          self.currentRoleId = data.id;
          self.$refs.tree.cfg.title = data.name;
          self.openLoading(self.$refs.tree);
          self.get({
            url: "/auth/getCheckedList?roleId=" + data.id,
            success: function(response) {
              if (response.code == "200") {
                self.$refs.tree.setCheckedKeys(response.data);
                self.closeLoading(self.$refs.tree);
              } else if (response.message) {
                self.$message({
                  type: "warning",
                  message: response.message
                });
              }
            }
          });
          self.$refs.user.fillData({
            roleId: self.currentRoleId
          });
        }
      },
      cfgUser: {
        title: "角色人员",
        parentTitle: "权限管理",
        url: "/user/getByRoleId",
        // showRadio:true,
        autoLoad: false,
        // showCheckBox:false,
        columns: [
          // {
          //   title: "id",
          //   name: "id",
          //   isHide: true
          // },
          {
            title: "编号",
            name: "no"
          },
          {
            title: "姓名",
            name: "name",
            isSearch: true
          },
          {
            title: "邮箱",
            name: "createTime"
          }
        ],
        idName: "id",
        beforeFillData: function() {
          return {
            roleId: self.currentRoleId
          };
        },
        functions: {
          common: [
            {
              text: "添加人员",
              url: selectUser,
              mode: "modal",
              onClick: function() {
                if (!self.currentRoleId) {
                  self.$message({
                    message: "请先选择角色!",
                    type: "warning"
                  });
                  return;
                }
                self.dialogVisible = true;
                self.applyDialogTitle = "选择员工";
              }
            }
          ],
          more: [
            {
              text: "删除",
              url: "/role/delete"
            }
          ]
        }
      },
      treeCfg: {
        title: "资源管理",
        parentTitle: "权限管理",
        url: "/auth/allResource",
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
                  self.post({
                    data: {
                      roleId: self.currentRoleId,
                      checkedList: self.$refs.tree.getCheckedKeys()
                    },
                    url: "/auth/saveCheckedList",
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
          },
          {
            text: "新增",
            type: "btn-success",
            icon: "el-icon-circle-plus-outline",
            onClick: function() {
              self.$refs.tree.dialogVisible = true;
              self.$refs.tree.currentComponent = "resourceAdd";
            }
          },
          {
            text: "修改",
            type: "btn-success",
            icon: "el-icon-edit-outline",
            onClick: function() {
              var checkedKeys = self.$refs.tree.getCheckedKeys();
              if (
                checkedKeys.length == 0 ||
                (checkedKeys.length == 1 && checkedKeys[0] == 0)
              ) {
                self.$message({
                  type: "warning",
                  message: "请选择资源!"
                });
              } else if (checkedKeys.length > 1) {
                self.$message({
                  type: "warning",
                  message: "只能选择一个资源!"
                });
              } else {
                $("body").attr("menuId", checkedKeys[0]);
                self.$refs.tree.dialogVisible = true;
                self.$refs.tree.currentComponent = "resourceUpdate";
              }
            }
          }
          // {
          //   text: "清空",
          //   type: "btn-success",
          //   icon:'el-icon-circle-close-outline',
          //   onClick:function(){
          //     self.$refs.tree.clearAll();
          //   }
          // }
        ]
      }
    };
  },
  updated: function() {
    var self = this;
    // self.$parent.title = self.cfg.title;
    // self.$parent.parentTitle = self.cfg.parentTitle;
  },
  methods: {
    onDialogBtnCancel: function() {
      this.dialogVisible = false;
      this.currentComponent = "";
    },
    onDialogBtnOk: function() {
      var self = this;

      if (!self.currentRoleId) {
        self.$message({
          message: "请先选择角色!",
          type: "warning"
        });
        return;
      }

      var selectedTableData = this.$refs.myApplyCompent.selectedTableData;
      if (selectedTableData.length == 0) {
        this.$message({
          message: "未选择员工",
          type: "warning"
        });
      } else {
        var userIds = [];
        for (var i = 0; i < selectedTableData.length; i++) {
          userIds.push(selectedTableData[i].id);
        }

        this.dialogVisible = false;
        this.currentComponent = "";
        var messsage = "添加";

        this.$confirm("确认要『" + messsage + "』吗, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            //do
            self.post({
              url: "/role/AddUser",
              data: {
                roleId: self.currentRoleId,
                userIs: userIds
              },
              success: function(r) {
                if (r.code == 200) {
                  self.$refs.user.fillData();
                }
              }
            });
          })
          .catch(e => {
            console.log(e);
          });
      }
    }
  }
};
</script>
<style>
.dataTables_filter {
  margin-bottom: -40px;
}
</style>
