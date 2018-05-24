<template>
 <el-row>
    <el-col :span="12"><list :cfg="cfg"></list></el-col>
    <el-col :span="12" id="resTree"><tree  ref="tree" :cfg="treeCfg"></tree></el-col>
    <!--el-col :span="6" id="resTree1"><tree  ref="tree1" :cfg="treeCfg"></tree></el-col-->
  </el-row>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        // isShowSearchArea:"true",
        // scrollX: true,
        // scrollCollapse: true,
        // fixedColumns: {
        //   leftColumns: 4
        // },
        title: "流程管理",
        parentTitle: "系统管理",
        url: this.getGlobalData().ApiBaseUrl + "/auth/whiteList",
        columns: [
          {
            title: "名称",
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
          
          //   {
          //   title: "创建时间",
          //   name: "createTime",
          //   isSearch: true,
          //   type: "timer"
          // },
          //  {
          //   title: "修改时间",
          //   name: "updateTime",
          //   isSearch: true,
          //   type: "timer"
          // }
        ],
        fnRowCallback: function(row, data) {
          if (data.isEnabled) {
            $("td:eq(3)", row).html('<center><i class="fa fa-fw fa-check-circle"></i></center>');
          } else {
            $("td:eq(3)", row).html('<center><i class="el-icon-close"></i></center>');
          }
        },
        idName: "id",
        functions: {
          more: [
            {
              text: "停用",
              url: this.getGlobalData().ApiBaseUrl + "/whitelist/disable",
              functionName:'whitelistDisable',
            },
            {
              text: "启用",
              url: this.getGlobalData().ApiBaseUrl + "/whitelist/enable",
              functionName:'whitelistEnable',
            }
          ],
          common: [
            {
              text: "添加流程",
              url: "/auth/whitelistAdd",
              functionName:'whitelistAdd',
              mode: "navigate"
            }
          ]
        },
        operations: [
          {
            text: "查看",
            url: "/auth/whitelistView",
            functionName:'whitelistView',
          }
        ]
      },
      treeCfg: {
        title: "节点管理",
        parentTitle: "权限管理",
        url: this.getGlobalData().ApiBaseUrl + "/auth/allResource",
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
      }
    };
  }
};
</script>