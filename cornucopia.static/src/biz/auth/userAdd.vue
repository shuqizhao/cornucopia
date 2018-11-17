<template>
 <mform :cfg="cfg"></mform>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "添加用户",
        mode: "create",
        save:  "/user/add",
        items: [
          {
            name: "name",
            title: "用户名",
            type: "text",
            width:"300px;"
          },
           {
            name: "phone",
            title: "手机号",
            type: "text",
            width:"300px;"
          },
            {
            name: "email",
            title: "邮箱",
            type: "text",
            width:"300px;"
          },
          {
            name: "pwd",
            title: "我的密码",
            type: "pwd"
          },
          {
            name: "pwd1",
            title: "确认密码",
            type: "pwd"
          }
        ],
        rules: {
          name: {
            required: true
          },
          Pwd: {
            required: true
          },
          Pwd1: {
            required: true,
            equalTo: "#Pwd"
          }
        },
        messages: {
          name: {
            required: "用户名必须填写"
          },
          pwd: {
            required: "密码必须填写"
          },
          pwd1: {
            required: "确认密码必须填写",
            equalTo: "确认密码必须与密码相同"
          }
        },
        onSuccess: function(mode, response) {
          if (response.code == 200) {
            history.back();
          } else {
            $.fn.message({
              msg: "添加用户失败！"
            });
          }
        },
        validate: function(data,saveData) {
          self.post({
            url: "/user/exists",
            data: data,
            success: function(response) {
              if (response.code == 200 && response.data == 0) {
                  saveData(data);
              } else {
                self.$message({
                  type: "warning",
                  message: "用户名已经存在!"
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