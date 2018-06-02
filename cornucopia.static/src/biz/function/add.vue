<template>
 <div>
     <mform :cfg="cfg"></mform>
     <editList ref="editList" :cfg="cfg1"></editList>
     <buttonBar :cfg="cfg1"></buttonBar>
 </div>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "添加函数",
        mode: "create",
        save: this.getGlobalData().ApiBaseUrl + "/function/add",
        hideFooter: true,
        items: [
          {
            name: "name",
            title: "函数名",
            type: "text"
          },
          {
            name: "name",
            title: "描述",
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
            required: "审批路径名必须填写"
          }
        },
        validate: function(data, saveData) {
          //   $.ajax({
          //     type: "POST",
          //     xhrFields: {
          //       withCredentials: true
          //     },
          //     url: self.getGlobalData().ApiBaseUrl + "/approve/exists",
          //     data: data,
          //     success: function(response) {
          //       if (response.code == 200 && response.data == 0) {
          //         saveData(data);
          //       } else {
          //         self.$message({
          //           type: "warning",
          //           message: "角色已经存在!"
          //         });
          //       }
          //     }
          //   });
          return false;
        }
      },
      cfg1: {
        title: "参数列表",
        mode: "create",
        save: this.getGlobalData().ApiBaseUrl + "/approve/add",
        items: [
          {
            name: "var1",
            title: "参数名",
            type: "text"
          },
          {
            name: "var1From",
            title: "参数类型",
            type: "combox",
            data: [
              {
                id: 1,
                name: "文本"
              },
              {
                id: 2,
                name: "表单xpath"
              } ,{
                id: 3,
                name: "函数"
              },
            ]
          },
          {
            name: "desc",
            title: "描述",
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
            required: "审批路径名必须填写"
          }
        },
        validate: function(data, saveData) {
          return false;
        },
        functions: [
          {
            text: "新增",
            type: "btn-success",
            icon: "el-icon-circle-plus",
            onClick: function() {
              self.$refs.editList.insertNew({boolOperation:1,var1From:2,var1Type:1,var2From:1,var2Type:1});
            }
          },
          {
            text: "删除",
            type: "btn-success",
            icon: "el-icon-edit",
            onClick: function() {
             self.$refs.editList.deleteSelected();
            }
          },
          {
            text: "上移",
            type: "btn-success",
            icon: "el-icon-edit",
            onClick: function() {
              self.$refs.editList.upSelected();
            }
          },
          {
            text: "下移",
            type: "btn-success",
            icon: "el-icon-edit",
            onClick: function() {
              self.$refs.editList.downSelected();
            }
          }
        ]
      }
    };
  }
};
</script>