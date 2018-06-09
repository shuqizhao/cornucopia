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
            width: "90%"
          },
          {
            name: "type",
            title: "岗位类型",
            width: "90%",
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
            width: "90%",
            type: "select"
          }
        ],
        rules: {
          name: {
            required: true
          }
        },
        messages: {
          name: {
            required: "岗位名必须填写"
          }
        },
        validate: function(data, saveData) {
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
          self.get(
            self.getGlobalData().ApiBaseUrl + "/approve/getAllRoles",
            "",
            function(response) {
              if ((response.code = 200)) {
                self.roles = response.data;
              }
            }
          );

          self.get(
            self.getGlobalData().ApiBaseUrl + "/role/alllist",
            "",
            function(response) {
              if ((response.code = 200)) {
                self.pals = response.data;
              }
            }
          );
        }
      },
      roles: [],
      pals: []
    };
  }
};
</script>