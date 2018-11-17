<template>
 <div>
     <mform ref="from" :cfg="cfg"></mform>
     <editList ref="editList" :cfg="cfg1"></editList>
     <buttonBar :cfg="cfg2"></buttonBar>
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
        hideFooter: true,
        name: "function",
        items: [
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
        mode: "create",
        name: "functionParas",
        showCheckBox:true,
        items: [
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
              self.$refs.editList.insertNew({ type: 1 });
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
        mode: "create",
        save:  "/function/add"
      }
    };
  },
  methods: {}
};
</script>