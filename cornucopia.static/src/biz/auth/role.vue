<template>
  <el-row>
    <el-col :span="8"><listV2 :cfg="cfgRole"></listV2></el-col>
    <el-col :span="8"><listV2 :cfg="cfgUser"></listV2></el-col>
    <el-col :span="8"><tree  ref="tree" :cfg="treeCfg"></tree></el-col>
  </el-row>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfgRole: {
        title: "角色管理",
        parentTitle: "权限管理",
        url:  "/role/list",
        showRadio:true,
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
            isSearch:true,
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
              url:  "/role/disable"
            },
            {
              text: "启用",
              url:  "/role/enable"
            },
            {
              text: "删除",
              url:  "/role/delete"
            }
          ]
        },
        onRowSelected: function(datas) {
          var data = datas[0];
          self.$refs.tree.cfg.title = data.name;
          self.openLoading(self.$refs.tree);
          self.get({
            url:
              "/auth/getCheckedList?roleId=" +
              data.id,
            success: function(response) {
              if (response.code == "200") {
                self.$refs.tree.setCheckedKeys(response.data);
                self.closeLoading(self.$refs.tree);
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
      }, 
      cfgUser: {
        title: "角色人员",
        parentTitle: "权限管理",
        url:  "/role/list",
        showRadio:true,
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
            isSearch:true,
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
              text: "添加人员",
              url: "roleAdd",
              mode: "modal"
            }
          ],
          more: [
            {
              text: "删除",
              url:  "/role/delete"
            }
          ]
        },
        onRowSelected: function(datas) {
          var data = datas[0];
          self.$refs.tree.cfg.title = data.name;
          self.openLoading(self.$refs.tree);
          self.get({
            url:
              "/auth/getCheckedList?roleId=" +
              data.id,
            success: function(response) {
              if (response.code == "200") {
                self.$refs.tree.setCheckedKeys(response.data);
                self.closeLoading(self.$refs.tree);
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
      },
      treeCfg: {
        title: "资源管理",
        parentTitle: "权限管理",
        url:  "/auth/allResource",
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
                    url:"/auth/saveCheckedList",
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
            icon:'el-icon-circle-plus-outline',
            onClick:function(){
              self.$refs.tree.dialogVisible = true;
              self.$refs.tree.currentComponent = "resourceAdd";
            }
          },
          {
            text: "修改",
            type: "btn-success",
            icon:'el-icon-edit-outline',
            onClick:function(){
              var checkedKeys = self.$refs.tree.getCheckedKeys();
              if(checkedKeys.length==0||(checkedKeys.length==1&&checkedKeys[0]==0)){
                self.$message({
                  type: "warning",
                  message: '请选择资源!'
                });
              }else if(checkedKeys.length>1){
                self.$message({
                  type: "warning",
                  message: '只能选择一个资源!'
                });
              }else{
                $("body").attr("menuId", checkedKeys[0]);
                self.$refs.tree.dialogVisible = true;
                self.$refs.tree.currentComponent = "resourceUpdate";
              }
            }
          },
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
    self.$parent.title = self.cfg.title;
    self.$parent.parentTitle = self.cfg.parentTitle;
  }
};
</script>
<style>
.dataTables_filter {
  margin-bottom: -40px;
}
</style>
