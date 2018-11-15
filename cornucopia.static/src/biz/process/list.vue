<template>
  <el-row>
    <el-col :span="8"><list :cfg="cfg"></list></el-col>
    <el-col :span="4" id="id1"><list ref="nodeList" :cfg="cfg1"></list></el-col>
    <el-col :span="12" id="id2"><list ref="diagramList" :cfg="cfg2"></list></el-col>
  </el-row>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      processId: 0,
      cfg: {
        title: "流程管理",
        parentTitle: "系统管理",
        simpleUrl: this.getGlobalData().ApiBaseUrl + "/process/alllist",
        lengthMenu: [[-1], ["ALL"]],
        sDom: '<"dataTables_function"/>f',
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
            title: "排序",
            name: "orderNum"
          },
          {
            title: "分类",
            name: "categoryId"
          },
          {
            title: "流程名",
            name: "name"
          },
          {
            title: "前缀",
            name: "pre"
          },
          {
            title: "路由",
            name: "url"
          }
          // {
          //   title: "是否启用",
          //   name: "isEnabled"
          // },
          // {
          //   title: "创建时间",
          //   name: "createTime"
          // }
        ],
        idName: "id",
        fnRowCallback: function(row, data) {
          // if (data.isEnabled) {
          //   $("td:eq(1)", row).html('<i class="fa fa-fw fa-check-circle"></i>');
          // } else {
          //   $("td:eq(1)", row).html('<i class="el-icon-close"></i>');
          // }
        },
        functions: {
          common: [
            {
              text: "添加流程",
              url: "processAdd",
              mode: "modal",
              functionName: "processAdd"
            },{
              text: "修改流程",
              url: "processUpdate",
              mode: "modal",
              functionName: "processAdd"
            },
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
          self.processId = data.id;
          self.openLoading(self.$refs.nodeList, "nodeList");
          $.ajax({
            type: "GET",
            xhrFields: {
              withCredentials: true
            },
            url:
              self.getGlobalData().ApiBaseUrl +
              "/processnode/alllist?processId=" +
              data.id,
            success: function(response) {
              if (response.code == "200") {
                self.closeLoading(self.$refs.nodeList, "nodeList");
                // self.closeLoading(self,"id2");
                $("#id1").attr("processId", data.id);
                self.$refs.nodeList.loadSimpleData(response.data);
              } else if (response.message) {
                self.$message({
                  type: "warning",
                  message: response.message
                });
              }
            }
          });
          self.openLoading(self.$refs.diagramList);
          self.get(
            self.getGlobalData().ApiBaseUrl +
              "/processdiagram/getAll?processId=" +
              data.id,
            "",
            function(response) {
              if (response.code == 200) {
                self.$refs.diagramList.loadSimpleData(response.data);
                self.closeLoading(self.$refs.diagramList);
              }
            }
          );
        }
      },
      cfg1: {
        title: "流程节点管理",
        parentTitle: "系统管理",
        // simpleUrl: this.getGlobalData().ApiBaseUrl + "/processnode/alllist",
        lengthMenu: [[-1], ["ALL"]],
        sDom: '<"dataTables_function"/>',
        bServerSide: false,
        hideCheckBox: true,
        // showSelectedRowColor: true,
        columns: [
          {
            title: "id",
            name: "id",
            isHide: true
          },
          {
            title: "流程节点名",
            name: "name"
          }
          // {
          //   title: "是否启用",
          //   name: "isEnabled"
          // },
          // {
          //   title: "创建时间",
          //   name: "createTime"
          // }
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
              text: "添加流程节点",
              url: "processNodeAdd",
              mode: "modal",
              functionName: "processNodeAdd"
            }
          ]
          // more: [
          //   {
          //     text: "停用",
          //     url: this.getGlobalData().ApiBaseUrl + "/processnode/disable"
          //   },
          //   {
          //     text: "启用",
          //     url: this.getGlobalData().ApiBaseUrl + "/processnode/enable"
          //   },
          //   {
          //     text: "删除",
          //     url: this.getGlobalData().ApiBaseUrl + "/processnode/delete"
          //   }
          // ]
        }
      },
      cfg2: {
        title: "流程图管理",
        parentTitle: "系统管理",
        // simpleUrl: this.getGlobalData().ApiBaseUrl + "/processnode/alllist",
        lengthMenu: [[-1], ["ALL"]],
        sDom: '<"dataTables_function"/>',
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
            title: "流程图名",
            name: "name"
          },
          {
            title: "版本",
            name: "defId"
          },
          {
            title: "是否启用",
            name: "isEnabled"
          },
          {
            title: "上传时间",
            name: "createTime"
          }
        ],
        idName: "id",
        fnRowCallback: function(row, data) {
          if (data.isEnabled) {
            $("td:eq(2)", row).html('<i class="fa fa-fw fa-check-circle"></i>');
          } else {
            $("td:eq(2)", row).html('<i class="el-icon-close"></i>');
          }
        },
        functions: {
          common: [
            {
              text: "上传流程图",
              url: "uploadDiagram",
              mode: "modal",
              functionName: "uploadDiagram"
            },
            {
              text: "部署流程图",
              url: "deployDiagram",
              url: this.getGlobalData().ApiBaseUrl + "/processdiagram/deploy",
              functionName: "deployDiagram",
              onSuccess: function() {
                self.get(
                  self.getGlobalData().ApiBaseUrl +
                    "/processdiagram/getAll?processId=" +
                    self.processId,
                  "",
                  function(response) {
                    if (response.code == 200) {
                      self.$refs.diagramList.loadSimpleData(response.data);
                    }
                  }
                );
              }
            }
          ],
          more: [
            {
              text: "停用",
              url: this.getGlobalData().ApiBaseUrl + "/process/disable"
            },
            {
              text: "启用",
              url: this.getGlobalData().ApiBaseUrl + "/processnode/enable"
            },
            {
              text: "删除",
              url: this.getGlobalData().ApiBaseUrl + "/processnode/delete"
            }
          ]
        }
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
</style>
