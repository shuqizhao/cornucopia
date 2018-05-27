<template>
 <mform :cfg="cfg"></mform>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "添加流程节点",
        mode: "create",
        save: this.getGlobalData().ApiBaseUrl + "/processnode/add",
        items: [
          {
            name: "name",
            title: "节点名",
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
            required: "流程节点名必须填写"
          }
        },
        validate: function(data, saveData) {
          data["processId"] = $("#id1").attr('processId');
          if(!data["processId"]){
              self.$message({
                  type: "warning",
                  message: "请先选择一个流程!"
                });
            return false;
          }
          $.ajax({
            type: "POST",
            xhrFields: {
              withCredentials: true
            },
            url: self.getGlobalData().ApiBaseUrl + "/processnode/exists",
            data: data,
            success: function(response) {
              if (response.code == 200 && response.data == 0) {
                saveData(data);
              } else {
                self.$message({
                  type: "warning",
                  message: "流程节点已经存在!"
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