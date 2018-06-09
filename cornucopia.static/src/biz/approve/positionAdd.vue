<template>
 <mform :cfg="cfg"></mform>
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
            name: "name",
            title: "岗位名",
            type: "text"
          },
          {
            name: "type",
            title: "岗位类型",
            type: "combox",
            data: [{ id: 1, value: "角色" }, { id: 2, value: "规则" }]
          },
          {
            name: "rule",
            title: "岗位计算",
            type: "select",
            data: [{ id: 1, value: "角色" }, { id: 2, value: "规则" }],
            url:this.getGlobalData().ApiBaseUrl + "/role/alllist"
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
              (self.$parent.$parent.$parent.$parent.$parent.$refs.tree.value2 || 0)
          );
          return false;
        }
      }
    };
  }
};
</script>