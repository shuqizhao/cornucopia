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
        title: "审批路径",
        mode: "create",
        save: this.getGlobalData().ApiBaseUrl + "/approve/add",
        hideFooter: true,
        items: [
          {
            name: "name",
            title: "路径名",
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
        title: "条件公式",
        mode: "create",
        save: this.getGlobalData().ApiBaseUrl + "/approve/add",
        items: [
          {
            name: "boolOperation",
            title: "布尔运算符",
            type: "combox",
            data: [
              {
                id: 1,
                name: "and"
              },
              {
                id: 2,
                name: "or"
              }
            ]
          },
          {
            name: "var1",
            title: "变量1",
            type: "text"
          },
          {
            name: "var1From",
            title: "变量1来源",
            type: "combox",
            data: [
              {
                id: 1,
                name: "文本"
              },
              {
                id: 2,
                name: "表单xpath"
              }
            ]
          },
          {
            name: "var1Type",
            title: "变量1类型",
            type: "combox",
            data: [
              {
                id: 1,
                name: "字符串"
              },
              {
                id: 2,
                name: "整数"
              },
              {
                id: 3,
                name: "浮点型"
              },
              {
                id: 4,
                name: "日期型"
              }
            ]
          },
          {
            name: "operation",
            title: "比较运算符",
            type: "combox",
            data: [
              {
                id: 1,
                name: "=="
              },
              {
                id: 2,
                name: ">"
              },
              {
                id: 3,
                name: ">="
              },
              {
                id: 4,
                name: "<"
              },
              {
                id: 5,
                name: "<="
              },
              {
                id: 6,
                name: "!="
              }
            ]
          },
          {
            name: "var2",
            title: "变量2",
            type: "text"
          },
          {
            name: "var2From",
            title: "变量2来源",
            type: "combox",
            data: [
              {
                id: 1,
                name: "文本"
              },
              {
                id: 2,
                name: "表单xpath"
              }
            ]
          },
          {
            name: "var2Type",
            title: "变量2类型",
            type: "combox",
            data: [
              {
                id: 1,
                name: "字符串"
              },
              {
                id: 2,
                name: "整数"
              },
              {
                id: 3,
                name: "浮点型"
              },
              {
                id: 4,
                name: "日期型"
              }
            ]
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
              self.$refs.editList.insertNew({});
            }
          },
          {
            text: "删除",
            type: "btn-success",
            icon: "el-icon-edit",
            onClick: function() {
              if (!self.$refs.tree1.value2) {
                self.$message({
                  message: "请先选择!",
                  type: "warning"
                });
                return;
              }
            }
          },
          {
            text: "上移",
            type: "btn-success",
            icon: "el-icon-edit",
            onClick: function() {
              if (!self.$refs.tree1.value2) {
                self.$message({
                  message: "请先选择!",
                  type: "warning"
                });
                return;
              }
            }
          },
          {
            text: "下移",
            type: "btn-success",
            icon: "el-icon-edit",
            onClick: function() {
              if (!self.$refs.tree1.value2) {
                self.$message({
                  message: "请先选择!",
                  type: "warning"
                });
                return;
              }
            }
          }
        ]
      }
    };
  }
};
</script>