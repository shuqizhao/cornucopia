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
            required: "岗位名必须填写"
          }
        },
        validate: function(data, saveData) {
          return true;
        }
      }
    };
  }
};
</script>