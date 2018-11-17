<template>
 <div>
     <mform :cfg="cfg"></mform>
 </div>
</template>
<script>
export default {
  mounted: function() {
      this.currentRuleId = this.$parent.$parent.$parent.$parent.$parent.currentRuleId;
  },
  data() {
    var self = this;
    return {
      cfg: {
        title: "编辑岗位规则",
        mode: "edit",
        save:  "/approve/ruleUpdate",
        get: {
          url:  "/approve/getRule",
          params: {
            id: this.$parent.$parent.$parent.$parent.$parent.currentRuleId
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
            title: "规则名",
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
            required: "规则名必须填写"
          }
        },
        validate: function(data, saveData) {
          return true;
        }
      },
      currentRuleId:0
    };
  }
};
</script>