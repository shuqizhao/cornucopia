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
        save:  "/processnode/add",
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
          data["processId"] = $("#id1").attr("processId");
          self.$parent.$parent.cfg.simpleUrl =
            "/processnode/alllist?processId=" +
            data["processId"];
          if (!data["processId"]) {
            self.$message({
              type: "warning",
              message: "请先选择一个流程!"
            });
            return false;
          }
          $.post({
            url: "/processnode/exists",
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