<template>
 <div>
     <mform :cfg="cfg"></mform>
     <editList ref="editList" :cfg="cfg1"></editList>
     <buttonBar :cfg="cfg2"></buttonBar>
 </div>
</template>
<script>
export default {
  mounted: function() {},
  data() {
    var self = this;
    return {
      cfg: {
        title: "审批路线",
        mode: "edit",
        name: "approve",
        hideFooter: true,
        get: {
          url: this.getGlobalData().ApiBaseUrl + "/approve/get",
          params: {
            id:
              self.$parent.$parent.cfg.title == "审批路线(二)"
                ? self.$parent.$parent.$parent.$parent.$parent.$refs.tree1.getCurrentKey()
                : self.$parent.$parent.$parent.$parent.$parent.$refs.tree.getCurrentKey()
          }
        },
        items: [
          {
            name: "id",
            title: "id",
            type: "hidden"
          },
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
          return true;
        }
      },
      cfg1: {
        title: "条件公式",
        mode: "edit",
        name: "approveConditions",
        dialogWidth: "95%",
        showCheckBox: true,
        get: {
          url: this.getGlobalData().ApiBaseUrl + "/approve/getConditions",
          params: {
            id:
              self.$parent.$parent.cfg.title == "审批路线(二)"
                ? self.$parent.$parent.$parent.$parent.$parent.$refs.tree1.getCurrentKey()
                : self.$parent.$parent.$parent.$parent.$parent.$refs.tree.getCurrentKey()
          }
        },
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
            type: "text",
            url: "functionPopup"
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
              },
              {
                id: 3,
                name: "函数"
              }
            ],
            onChange: function(index, item, s1, tableCell, tableData, status) {
              if (!tableCell[index]) {
                tableCell[index] = {};
              }
              if (status != 1) {
                tableData[index]["var1"] = "";
              }

              if (s1 == 3) {
                tableCell[index]["var1"] = "popup";
                // this.parainstId = tableData[index][var2];
              } else {
                tableCell[index]["var1"] = "text";
              }
            }
          },
          {
            name: "varType",
            title: "变量类型",
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
            type: "text",
            url: "functionPopup"
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
              },
              {
                id: 3,
                name: "函数"
              }
            ],
            onChange: function(index, item, s1, tableCell, tableData, status) {
              if (!tableCell[index]) {
                tableCell[index] = {};
              }
              if (status != 1) {
                tableData[index]["var2"] = "";
              }

              if (s1 == 3) {
                tableCell[index]["var2"] = "popup";
                // this.parainstId = tableData[index][var2];
              } else {
                tableCell[index]["var2"] = "text";
              }
            }
          },
          {
            name: "level",
            title: "变量级别",
            type: "combox",
            data: [
              {
                id: 1,
                name: "一级"
              },
              {
                id: 2,
                name: "二级"
              }
            ]
          }
        ],
        rules: {
          var1: {
            required: true
          },
          var2: {
            required: true
          }
        },
        messages: {
          var1: {
            required: "变量1必须填写"
          },
          var2: {
            required: "变量2必须填写"
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
              self.$refs.editList.insertNew({
                boolOperation: 1,
                var1From: 2,
                varType: 1,
                var2From: 1,
                level: 1
              });
            }
          },
          {
            text: "删除",
            type: "btn-success",
            icon: "el-icon-delete",
            onClick: function() {
              self.$refs.editList.deleteSelected();
            }
          },
          {
            text: "上移",
            type: "btn-success",
            icon: "el-icon-arrow-up",
            onClick: function() {
              self.$refs.editList.upSelected();
            }
          },
          {
            text: "下移",
            type: "btn-success",
            icon: "el-icon-arrow-down",
            onClick: function() {
              self.$refs.editList.downSelected();
            }
          },
          {
            text: "预览",
            type: "btn-success",
            icon: "el-icon-view",
            onClick: function() {
              self.$refs.editList.showPopup("conditionPreview");
            }
          }
        ]
      },
      cfg2: {
        save: this.getGlobalData().ApiBaseUrl + "/approve/update",
        getExtraData: function() {
          return {
            approve: {
              processId: self.$parent.$parent.value1,
              processNodeId: self.$parent.$parent.value2
            }
          };
        }
      },
      id: 0
    };
  },
  methods: {}
};
</script>