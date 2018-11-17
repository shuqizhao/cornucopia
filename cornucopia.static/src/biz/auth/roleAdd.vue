<template>
 <mform :cfg="cfg"></mform>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "添加角色",
        mode: "create",
        save:  "/role/add",
        items: [
          {
            name: "name",
            title: "角色名",
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
            required: "角色名必须填写"
          }
        },
        validate: function(data, saveData) {
          self.post({
            url: "/role/exists",
            data: data,
            success: function(response) {
              if (response.code == 200 && response.data == 0) {
                saveData(data);
              } else {
                self.$message({
                  type: "warning",
                  message: "角色已经存在!"
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