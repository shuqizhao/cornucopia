<template>
 <mform :cfg="cfg"></mform>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "查看白名单",
        detailTitle: "查看白名单",
        editTitle: "编辑白名单",
        mode: "detailEdit",
        editFunctionName:'whitelistUpdate',
        get: {
          url:  "/whitelist/get",
          params: {
            id: this.$route.query.id
          }
        },
        save:  "/whitelist/update",
        items: [
          {
            name: "id",
            title: "id",
            type: "hidden"
          },
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
            type: "select",
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