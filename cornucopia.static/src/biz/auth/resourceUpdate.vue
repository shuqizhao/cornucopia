<template>
 <mform :cfg="cfg"></mform>
</template>
<script>
export default {
  data() {
    var self = this;
    var menuId = $('body').attr("menuId");
    return {
      cfg: {
        title: "查看资源",
        detailTitle: "查看资源",
        editTitle: "编辑资源",
        mode: "detailEdit",
        get: {
          url: "/resource/get",
          params: {
            id: menuId
          }
        },
        save: "/resource/update",
        items: [
         {
            name: "Id",
            type: "hidden"
          },
          {
            name: "Name",
            title: "资源名",
            type: "text",
            isRequire: true
          },
          {
            name: "ParentId",
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
            name: "Url",
            title: "前端路径",
            type: "text"
          },
          {
            name: "functionName",
            title: "功能名",
            type: "text"
          },
          {
            name: "Icon",
            title: "功能图标",
            type: "text"
          },
          {
            name: "api",
            title: "后台API",
            type: "text"
          },
          {
            name: "Type",
            title: "类型",
            type: "select",
            isRequire: true,
            data: [
              { id: 0, value: "普通页面" },
              { id: 1, value: "模态窗口" }
            ]
          }
        ],
        onLoaded: function(detail) {
          self.get({
            url: "/resource/all",
            success: function(response) {
              if (response.code == 200) {
                self.cfg.items[2].data = response.data;
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