<template>
 <mform :cfg="cfg"></mform>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "添加白名单",
        mode: "create",
        save: this.getGlobalData().ApiBaseUrl + "/role/add",
        items: [
          {
            name: "name",
            title: "名称",
            isRequire:true,
            type: "text"
          },
          {
            name: "url",
            title: "URL",
            isRequire:true,
            type: "text"
          },
          {
            name: "type",
            title: "类型",
            isRequire:true,
            type: "combox",
            data:[{
              id:1,
              value:'需要登录'
            },{
              id:0,
              value:'不需要登录'
            }]
          }
        ],
        rules: {
          name: {
            required: true
          }
        },
        messages: {
          name: {
            required: "名称必须填写"
          }
        },
        validate: function(data, saveData) {
          $.ajax({
            type: "POST",
            xhrFields: {
              withCredentials: true
            },
            url: self.getGlobalData().ApiBaseUrl + "/role/exists",
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