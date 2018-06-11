<template>
 <mform :cfg="cfg"></mform>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "上传流程图",
        mode: "create",
        save: this.getGlobalData().ApiBaseUrl + "/process/upload",
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
            desc:"只能上传bpmn文件",
            accept:".bpmn",
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
        validate: function(data, saveData) {
          $.ajax({
            type: "POST",
            xhrFields: {
              withCredentials: true
            },
            url: self.getGlobalData().ApiBaseUrl + "/process/exists",
            data: data,
            success: function(response) {
              if (response.code == 200 && response.data == 0) {
                saveData(data);
              } else {
                self.$message({
                  type: "warning",
                  message: "流程已经存在!"
                });
              }
            }
          });
          return false;
        }
      }
    };
  }
};
</script>