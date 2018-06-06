<template>
  <el-row>
    <el-col :span="12"><list :cfg="cfg"></list></el-col>
    <el-col :span="12"><mform :cfg="cfg1"></mform></el-col>
  </el-row>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "选择函数",
        // parentTitle: "权限管理",
        simpleUrl: this.getGlobalData().ApiBaseUrl + "/function/alllist",
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
            title: "函数名",
            name: "name"
          },
          {
            title: "函数描述",
            name: "desc"
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
        onClickRow: function(data, target) {
          self.id = data.id;
        }
      },
      cfg1: {
        title: "添加角色",
        mode: "create",
        save: this.getGlobalData().ApiBaseUrl + "/role/add",
        items: [
          {
            name: "name",
            title: "角色名",
            type: "text"
          }
        ],
        rules: {
          name: {
            required: true
          }
        },
        messages: {
          name: {
            required: "角色名必须填写"
          }
        },
        validate: function(data, saveData) {
          $.ajax({
            type: "POST",
            xhrFields: {
              withCredentials: true
            },
            url: self.getGlobalData().ApiBaseUrl + "/role/exists",
            data: data,
            success: function(response) {
              if (response.code == 200 && response.data == 0) {
                saveData(data);
              } else {
                self.$message({
                  type: "warning",
                  message: "角色已经存在!"
                });
              }
            }
          });
          return false;
        }
      },
      id:0
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
