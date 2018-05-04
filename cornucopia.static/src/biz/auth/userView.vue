<template>
 <mform :cfg="cfg"></mform>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "查看用户",
        detailTitle: "查看用户",
        editTitle: "编辑用户的角色",
        mode: "detailEdit",
        get: {
          url: this.getGlobalData().ApiBaseUrl + "/user/get",
          params: {
            id: this.$route.query.id
          }
        },
        save: this.getGlobalData().ApiBaseUrl + "/user/update",
        items: [
          {
            name: "Id",
            type: "hidden"
          },
          {
            name: "name",
            title: "用户名",
            type: "text"
          },
            {
            name: "isEnabled",
            title: "是否启用",
            type: "yesno",
          },
          {
            name: "roles",
            title: "角色",
            type: "select2select",
            width:"100%",
            url:
              this.getGlobalData().ApiBaseUrl +
              "/user/roles?id=" +
              this.$route.query.id
          },
        ],
        afterEditRender: function(mode,data) {
          $(self.$el).find("#name").attr("disabled", true);
          $(self.$el).find("#isEnabled").attr("disabled", true);
           $(self.$el).find("#isEnabled input").attr("disabled", true);
        }
      }
    };
  }
};
</script>