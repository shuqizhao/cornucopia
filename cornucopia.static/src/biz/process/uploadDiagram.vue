<template>
 <mform :cfg="cfg"></mform>
</template>
<script>
export default {
  mounted: function() {
    this.processId = this.$parent.$parent.$parent.$parent.$parent.processId;
    if (this.processId == 0) {
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
        title: "上传流程图",
        mode: "create",
        save: this.getGlobalData().ApiBaseUrl + "/processdiagram/upload",
        items: [
          {
            name: "name",
            title: "流程名",
            type: "text"
          },
          {
            name: "fileId",
            title: "流程图",
            type: "uploader",
            desc: "只能上传bpmn文件",
            accept: ".bpmn",
            url: this.getGlobalData().ApiBaseUrl + "/upload"
          }
        ],
        rules: {
          name: {
            required: true
          }
        },
        messages: {
          name: {
            required: "流程名必须填写"
          }
        },
        onSuccess: function() {
          self.get(
            self.getGlobalData().ApiBaseUrl +
              "/processdiagram/getAll?processId=" +
              self.processId,
            "",
            function(response) {
              if (response.code == 200) {
                self.$parent.$parent.$parent.$parent.$parent.$refs.diagramList.loadSimpleData(
                  response.data
                );
                self.$parent.$parent.dialogVisible = false;
                self.$parent.$parent.currentComponent = "";
              }
            }
          );
          return false;
        },
        validate: function(data, saveData) {
          data.processId = self.processId;
          return true;
        }
      },
      processId: 0
    };
  }
};
</script>