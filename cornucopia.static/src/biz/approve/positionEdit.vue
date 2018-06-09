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
            type: "text"
          },
          {
            name: "type",
            title: "岗位类型",
            type: "select",
            data: [{ id: 1, value: "角色" }, { id: 2, value: "规则" }],
            onChange:function(s1){
              if(s1 == 1){
                self.cfg.items[3].data=[];
              }else{
                self.cfg.items[3].data=[{ id: 1, value: "角色" }, { id: 2, value: "规则" }];
              }
            }
          },
          {
            name: "rule",
            title: "岗位计算",
            type: "select",
            data: [{ id: 1, value: "角色" }, { id: 2, value: "规则" }]
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