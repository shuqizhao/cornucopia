<template>
 <mform :cfg="cfg"></mform>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "添加资源",
        mode: "create",
        save: "/resource/add",
        items: [
          {
            name: "name",
            title: "资源名",
            type: "text",
            isRequire: true
          },
          {
            name: "parentId",
            title: "上级",
            type: "select",
            data: [],
            displayId: "Id",
            displayValue: "Name",
            displayDesc: "functionName",
            isRequire: true
          },
          {
            name: "routerName",
            title: "前端路由",
            type: "text"
          },
          {
            name: "url",
            title: "前端路径",
            type: "text"
          },
          {
            name: "functionName",
            title: "功能名",
            type: "text"
          },
          {
            name: "icon",
            title: "功能图标",
            type: "text"
          },
          {
            name: "api",
            title: "后台API",
            type: "text"
          },
          {
            name: "type",
            title: "类型",
            type: "select",
            isRequire: true,
            data: [
              { id: "0", value: "普通页面" },
              { id: "1", value: "模态窗口" }
            ]
          }
        ],
        onLoaded: function(detail) {
          self.get({
            url: "/resource/all?menuId=0",
            success: function(response) {
              if (response.code == 200) {
                self.cfg.items[1].data = response.data;
              }
            }
          });
        },
        rules: {
          name: {
            required: true
          },
          parentId: {
            required: true
          },
          type: {
            required: true
          }
        },
        messages: {
          name: {
            required: "资源名必须填写"
          },
          parentId: {
            required: "上级必须填写"
          },
          type: {
            required: "类型必须填写"
          }
        },
        validate: function(data, saveData) {
          return true;
        }
      }
    };
  }
};
</script>