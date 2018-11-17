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
        save:  "/processdiagram/upload",
        items: [
          {
            name: "name",
            title: "流程名",
            type: "text",
            isRequire: true,
            width: "90%"
          },
          {
            name: "fileId",
            title: "流程图",
            type: "uploader",
            desc: "只能上传bpmn文件",
            accept: ".bpmn",
            url:  "/upload",
            isRequire: true,
            width: "90%"
          },
          {
            name: "picFileId",
            title: "流程图片",
            type: "uploader",
            desc: "只能上传png文件",
            accept: ".png",
            url:  "/upload",
            isRequire: true,
            width: "90%"
          }
        ],
        rules: {
          name: {
            required: true
          },
          fileId: {
            required: true
          },
          picFileId: {
            required: true
          }
        },
        messages: {
          name: {
            required: "流程名必须填写"
          },
          fileId: {
            required: "流程图必须上传"
          },
          picFileId: {
            required: "流程图片必须上传"
          }
        },
        onSuccess: function() {
          self.get({
            url: "/processdiagram/getAll?processId=" + self.processId,
            success: function(response) {
              if (response.code == 200) {
                self.$parent.$parent.$parent.$parent.$parent.$refs.diagramList.loadSimpleData(
                  response.data
                );
                self.$parent.$parent.dialogVisible = false;
                self.$parent.$parent.currentComponent = "";
              }
            }
          });
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