<template>
 <div>
     <mform ref="form" :cfg="cfg"></mform>
     <editList ref="editList" :cfg="cfg1"></editList>
     <buttonBar :cfg="cfg2"></buttonBar>
 </div>
</template>
<script>
export default {
  mounted: function() {
    var self = this;
    self.get({
      url: "/approve/getAllRoles",
      success: function(response) {
        if (response.code == 200) {
          self.roles = response.data;
          self.cfg.items[1].data = self.roles;
        }
      }
    });
  },
  data() {
    var self = this;
    return {
      cfg: {
        title: "编辑岗位子规则",
        mode: "edit",
        name: "rule",
        hideFooter: true,
        get: {
          url:  "/approve/getRule",
          params: {
            id: self.$parent.$parent.$parent.$parent.$parent.currentRuleId2
          }
        },
        items: [
          {
            name: "name",
            title: "规则名",
            type: "text"
          },
          {
            name: "jobId",
            title: "职位",
            type: "hidden",
            data: []
          },
          {
            name: "type",
            title: "类型",
            type: "select",
            data: [
              { id: 1, value: "角色" },
              { id: 2, value: "函数" },
              { id: 3, value: "指定人" },
              { id: 4, value: "跳过" }
            ],
            onChange: function(s1) {
              if (s1 == 1) {
                self.cfg.items[3].type = "select";
                self.cfg.items[3].data = self.roles;
                self.$set(self.$refs.form.detail, "user", "");
              } else if (s1 == 2) {
                self.cfg.items[3].url = "functionPopup";
                self.cfg.items[3].type = "popup";
                self.cfg.items[3].data = [];
                self.$refs.form.detail.user = "";
              } else if (s1 == 3) {
                self.cfg.items[3].type = "text";
                self.cfg.items[3].data = "";
                self.$refs.form.detail.user = "";
              } else if (s1 == 4) {
                self.cfg.items[3].type = "readonly";
                self.$refs.form.detail.user = "999999";
              }
            }
          },
          {
            name: "user",
            title: "人选",
            type: "select",
            data: []
          }
        ],
        rules: {
          name: {
            required: true
          }
        },
        messages: {
          name: {
            required: "规则名名必须填写"
          }
        },
        validate: function(data, saveData) {
          return true;
        },
        onLoaded: function(detail) {
          if (detail.type == 1) {
            self.get({
              url: "/approve/getAllRoles",
              success: function(response) {
                if (response.code == 200) {
                  self.roles = response.data;
                  self.cfg.items[3].type = "select";
                  self.cfg.items[3].data = self.roles;
                  self.$set(
                    self.$refs.form.detail,
                    "user",
                    parseInt(detail.user)
                  );
                }
              }
            });
          } else if (detail.type == 2) {
            self.cfg.items[3].url = "functionPopup";
            self.cfg.items[3].type = "popup";
          } else if (detail.type == 3) {
            self.cfg.items[3].type = "text";
          } else if (detail.type == 4) {
            self.cfg.items[3].type = "readonly";
          }
        }
      },
      cfg1: {
        title: "条件公式",
        mode: "edit",
        name: "ruleConditions",
        dialogWidth: "95%",
        get: {
          url:  "/approve/getRuleCondititons",
          params: {
            id: self.$parent.$parent.$parent.$parent.$parent.currentRuleId2
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
        save:  "/approve/ruleChildUpdate",
        getExtraData: function() {
          return {
            rule: {
              id: self.$parent.$parent.$parent.$parent.$parent.currentRuleId2,
              parentId:
                self.$parent.$parent.$parent.$parent.$parent.currentRuleId
            }
          };
        }
      },
      roles: []
    };
  }
};
</script>