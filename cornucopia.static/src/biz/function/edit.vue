<template>
 <div>
     <mform ref="from" :cfg="cfg"></mform>
     <editList ref="editList" :cfg="cfg1"></editList>
     <buttonBar :cfg="cfg2"></buttonBar>
 </div>
</template>
<script>
export default {
  mounted: function() {
    var functionId = this.$parent.$parent.$parent.id;
    if (functionId == 0) {
      this.$message({
        message: "请先选择函数!",
        type: "warning"
      });
      //   this.$destroy();
      this.$parent.$parent.dialogVisible = false;
      this.$parent.$parent.currentComponent = "";
    }
  },
  data() {
    var self = this;
    return {
      cfg: {
        title: "编辑函数",
        mode: "edit",
        hideFooter: true,
        get: {
          url: this.getGlobalData().ApiBaseUrl + "/function/get",
          params: {
            id: self.$parent.$parent.$parent.id
          }
        },
        name: "function",
        items: [
          {
            name: "id",
            title: "id",
            type: "hidden"
          },
          {
            name: "name",
            title: "函数名",
            type: "text"
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
          },
          desc: {
            required: true
          }
        },
        messages: {
          name: {
            required: "函数名必须填写"
          },
          desc: {
            required: "描述名必须填写"
          }
        }
      },
      cfg1: {
        title: "参数列表",
        mode: "edit",
        name: "functionParas",
        showCheckBox:true,
        get: {
          url: this.getGlobalData().ApiBaseUrl + "/function/getPara",
          params: {
            id: self.$parent.$parent.$parent.id
          }
        },
        items: [
          {
            name: "id",
            type: "hidden"
          },
          {
            name: "name",
            title: "参数名",
            type: "text"
          },
          {
            name: "type",
            title: "参数类型",
            type: "combox",
            data: [
              {
                id: 1,
                name: "文本"
              },
              {
                id: 2,
                name: "数字"
              },
              {
                id: 3,
                name: "日期"
              }
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
          },
          desc: {
            required: true
          }
        },
        messages: {
          name: {
            required: "参数必填"
          },
          desc: {
            required: "描述必填"
          }
        },
        validate: function(data, saveData) {
          return true;
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
      },
      cfg2: {
        title: "参数列表",
        mode: "edit",
        save: this.getGlobalData().ApiBaseUrl + "/function/update"
      }
    };
  },
  methods: {}
};
</script>