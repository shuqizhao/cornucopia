<template>
  <list :cfg="cfg"></list>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "流程管理",
        parentTitle: "系统管理",
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
            title: "流程名",
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
              text: "添加流程",
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
      },
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
