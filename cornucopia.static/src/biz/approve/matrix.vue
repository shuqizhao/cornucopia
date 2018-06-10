<template>
  <editList ref="editList" :cfg="cfg"></editList>
</template>
<script>
export default {
  mounted: function() {
    var self = this;
    self.get(this.getGlobalData().ApiBaseUrl + "/process/alllist", "", function(
      response
    ) {
      if (response.code == 200) {
        self.$set(self.cfg.tools[0], "data", response.data);
      }
    });
  },
  data() {
    var self = this;
    return {
      cfg: {
        title: "审批矩阵",
        mode: "edit",
        dialogWidth: "95%",
        get: {
          url: this.getGlobalData().ApiBaseUrl + "/approve/getConditions",
          params: {
            id: 0
          }
        },
        items: [
          {
            name: "boolOperation",
            title: "审批岗位",
            type: "label"
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
        tools: [
          {
            name: "processId",
            title: "流程",
            type: "select",
            onChange: function(s1,toolData) {
              self.get(
                self.getGlobalData().ApiBaseUrl + "/processnode/alllist?processId="+s1,
                "",
                function(response) {
                  if (response.code == 200) {
                    self.$set(self.cfg.tools[1], "data", response.data);
                    toolData.processNodeId="";
                    toolData.approveId="";
                  }
                }
              );
            }
          },
          {
            name: "processNodeId",
            title: "节点",
            type: "select",
            onChange: function(s1,toolData) {
              self.get(
                self.getGlobalData().ApiBaseUrl + "/approve/alllist?processNodeId="+s1,
                "",
                function(response) {
                  if (response.code == 200) {
                    self.$set(self.cfg.tools[2], "data", response.data);
                    toolData.approveId="";
                  }
                }
              );
            }
          },
          {
            name: "approveId",
            title: "审批路线(一)",
            type: "select"
          }
        ]
      },
      id: 0
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
