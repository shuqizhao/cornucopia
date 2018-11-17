<template>
 <mform :cfg="cfg"></mform>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "查看角色",
        detailTitle: "查看角色",
        editTitle: "编辑角色的资源",
        mode: "detailEdit",
        get: {
          url: "/role/get",
          params: {
            id: this.$route.query.id
          }
        },
        save: "/role/update",
        items: [
          {
            name: "Id",
            type: "hidden"
          },
          {
            name: "Name",
            title: "角色名",
            type: "text"
          },
          {
            name: "Menus",
            title: "资源",
            type: "select2select",
            url: "/role/resources?id=" + this.$route.query.id
          }
        ],
        afterEditRender: function() {
           $(self.$el).find("#Name").attr("disabled", true);
        }
      }
    };
  }
};
</script>