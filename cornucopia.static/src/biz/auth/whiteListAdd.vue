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
        save:  "/whitelist/add",
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
          },
          url: {
            required: true
          }
        },
        messages: {
          name: {
            required: "名称必须填写"
          },
          url: {
            required: "URL必须填写"
          }
        },
        onSuccess: function(mode, response) {
          if (response.code == 200) {
            history.back();
          } else {
            $.fn.message({
              msg: "添加白名单失败！"
            });
          }
        },
      }
    };
  }
};
</script>