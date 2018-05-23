<template>
 <mform :cfg="cfg"></mform>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "修改密码",
        mode: "create",
        save: self.getGlobalData().ApiBaseUrl + "/auth/usermod",
        modal: "true",
        items: [
          {
            name: "Id",
            type: "hidden",
            
          },
          {
            name: "oldPwd",
            title: "原密码",
            type: "pwd",
            isRequire:true
          },
          {
            name: "pwd",
            title: "密码",
            type: "pwd",
            isRequire:true
          },
          {
            name: "pwd1",
            title: "确认密码",
            type: "pwd",
            isRequire:true
          }
        ],
        rules: {
          oldPwd: {
            required: true
          },
          pwd: {
            required: true
          },
          pwd1: {
            required: true,
            equalTo: "#pwd"
          }
        },
        messages: {
          oldPwd: {
            required: "原密码必须填写"
          },
          pwd: {
            required: "密码必须填写"
          },
          pwd1: {
            required: "确认密码必须填写",
            equalTo: "确认密码必须与密码相同"
          }
        },
        validate: function(data, saveData) {
          $.ajax({
            type: "POST",
            xhrFields: {
              withCredentials: true
            },
            url: self.getGlobalData().ApiBaseUrl + "/auth/checkpwd",
            data: data,
            success: function(response) {
              if (response.code == 200 && response.data == 1) {
                saveData(data);
              } else {
                self.$message({
                  type: "warning",
                  message: "原密码输入错误!"
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