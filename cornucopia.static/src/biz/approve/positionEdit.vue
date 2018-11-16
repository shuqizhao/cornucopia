<template>
 <mform ref="form" :cfg="cfg"></mform>
</template>
<script>
import Vue from "vue";
export default {
  mounted: function() {
    var self = this;
    if (!self.$parent.$parent.$parent.$parent.$parent.$refs.tree.value2) {
      this.$message({
        message: "请先选择流程节点!",
        type: "warning"
      });
      this.$parent.$parent.dialogVisible = false;
      this.$parent.$parent.currentComponent = "";
    }
    if (self.$parent.$parent.$parent.$parent.$parent.currentPositionId == 0) {
      this.$message({
        message: "请先选择审批岗位!",
        type: "warning"
      });
      this.$parent.$parent.dialogVisible = false;
      this.$parent.$parent.currentComponent = "";
    }
  },
  data() {
    var self = this;
    return {
      cfg: {
        title: "编辑审批岗位",
        mode: "edit",
        save: this.getGlobalData().ApiBaseUrl + "/approve/positionUpdate",
        get: {
          url: this.getGlobalData().ApiBaseUrl + "/approve/getPosition",
          params: {
            id: self.$parent.$parent.$parent.$parent.$parent.currentPositionId
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
            title: "岗位名",
            isRequire: true,
            type: "text",
            width: "90%"
          },
          {
            name: "type",
            title: "岗位类型",
            isRequire: true,
            width: "90%",
            type: "select",
            data: [{ id: 1, value: "角色" }, { id: 2, value: "规则" }],
            onChange: function(s1) {
              self.$refs.form.detail.rule = "";
              if (s1 == 1) {
                self.cfg.items[3].data = self.roles;
              } else {
                self.cfg.items[3].data = self.pals;
              }
            }
          },
          {
            name: "rule",
            title: "岗位计算",
            isRequire: true,
            width: "90%",
            type: "select",
            data: []
          },
          {
            name: "vitualTitle",
            title: "虚拟职位",
            isRequire: true,
            width: "90%",
            type: "text"
          },
          {
            name: "approveType",
            title: "审核类型",
            isRequire: true,
            width: "90%",
            type: "select",
            data: [{ id: 0, value: "竞签" }, { id: 1, value: "会签" }]
          }
        ],
        rules: {
          name: {
            required: true
          },
          type: {
            required: true
          },
          rule: {
            required: true
          },
          vitualTitle: {
            required: true
          },
          approveType: {
            required: true
          }
        },
        messages: {
          name: {
            required: "岗位名必须填写"
          },
          type: {
            required: "岗位类型必须填写"
          },
          rule: {
            required: "岗位规则必须填写"
          },
          vitualTitle: {
            required: "虚拟职位必须填写"
          },
          approveType: {
            required: "审核类型必须填写"
          }
        },
        validate: function(data, saveData) {
          return true;
        },
        onSuccess: function() {
          self.$parent.$parent.$parent.$parent.$parent.$refs.positionList.dialogVisible = false;
          self.$parent.$parent.$parent.$parent.$parent.$refs.positionList.currentComponent =
            "";
          self.$parent.$parent.$parent.$parent.$parent.$refs.positionList.reloadSimpleData(
            self.getGlobalData().ApiBaseUrl +
              "/approve/positionlist?processNodeId=" +
              (self.$parent.$parent.$parent.$parent.$parent.$refs.tree.value2 ||
                0)
          );
          return false;
        },
        onLoaded: function(detail) {
          var s1 = detail.type;
          var s2 = detail.rule;
          detail.rule = "";
          self.get({
            url: "/approve/getAllRoles",
            success: function(response) {
              if ((response.code = 200)) {
                self.roles = response.data;
                if (s1 == 1) {
                  self.cfg.items[3].data = self.roles;
                  detail.rule = s2;
                }
              }
            }
          });

          self.get({
            url: "/approve/getAllRules",
            success: function(response) {
              if ((response.code = 200)) {
                self.pals = response.data;
                if (s1 == 2) {
                  self.cfg.items[3].data = self.pals;
                  detail.rule = s2;
                }
              }
            }
          });
        }
      },
      roles: [],
      pals: []
    };
  }
};
</script>