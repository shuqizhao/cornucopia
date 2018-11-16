<template>
 <mform ref="form" :cfg="cfg"></mform>
</template>
<script>
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
  },
  data() {
    var self = this;
    return {
      cfg: {
        title: "添加审批岗位",
        mode: "create",
        save: this.getGlobalData().ApiBaseUrl + "/approve/positionAdd",
        items: [
          {
            name: "id",
            title: "id",
            type: "hidden"
          },
          {
            name: "name",
            title: "岗位名",
            type: "text",
            width: "90%",
            isRequire: true
          },
          {
            name: "type",
            title: "岗位类型",
            width: "90%",
            isRequire: true,
            type: "select",
            data: [{ id: 1, value: "角色" }, { id: 2, value: "规则" }],
            onChange: function(s1) {
              self.$refs.form.detail.rule = "";
              if (s1 == 1) {
                self.$set(self.cfg.items, 3, {
                  name: "rule",
                  title: "角色",
                  width: "90%",
                  type: "select",
                  data: self.roles
                });
              } else {
                self.$set(self.cfg.items, 3, {
                  name: "rule",
                  title: "规则",
                  width: "90%",
                  type: "select",
                  data: self.pals
                });
              }
            }
          },
          {
            name: "rule",
            title: "岗位计算",
            isRequire: true,
            width: "90%",
            type: "select"
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
          data.id = 0;
          data.processNodeId =
            self.$parent.$parent.$parent.$parent.$parent.$refs.tree.value2;
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
        onLoaded: function() {
          self.get({
            url: "/approve/getAllRoles",
            success: function(response) {
              if ((response.code = 200)) {
                self.roles = response.data;
              }
            }
          });

          self.get({
            url: "/approve/getAllRules",
            success: function(response) {
              if ((response.code = 200)) {
                self.pals = response.data;
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