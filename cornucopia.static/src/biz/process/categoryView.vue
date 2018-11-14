<template>
 <mform :cfg="cfg"></mform>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "查看流程分类",
        detailTitle: "查看流程分类",
        editTitle: "编辑流程分类",
        mode: "detailEdit",
        get: {
          url: this.getGlobalData().ApiBaseUrl + "/processCategory/get",
          params: {
            id: this.$route.query.id
          }
        },
        save: this.getGlobalData().ApiBaseUrl + "/processCategory/update",
        items: [
          {
            name: "id",
            type: "hidden"
          },
          {
            name: "name",
            title: "分类名",
            type: "text",
            isRequire:true,
          },
          {
            name: "icon",
            title: "图标",
            type: "text",
            isRequire:true,
          },
          {
            name: "orderNum",
            title: "排序",
            type: "text",
            isRequire:true,
          },
            {
            name: "isEnabled",
            title: "是否启用",
            type: "yesno"
          }
        ],
        rules: {
          name: {
            required: true
          },
          icon: {
            required: true
          },
          orderNum: {
            required: true,
            digits: true
          }
        },
        messages: {
          name: {
            required: "必须填写"
          },
          icon: {
            required: "必须填写"
          },
          orderNum: {
            required: "必须填写",
            digits: "只能输入整数"
          }
        },
        afterEditRender: function(mode,data) {

        }
      }
    };
  }
};
</script>