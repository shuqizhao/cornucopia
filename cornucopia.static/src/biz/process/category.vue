<template>
  <list :cfg="cfg"></list>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        isShowSearchArea:"true",
        title: "流程分类",
        parentTitle: "系统管理",
        url:  "/processCategory/list",
        columns: [
          {
            title: "分类名",
            name: "name",
            // isSearch: true
          },
          {
            title: "是否启用",
            name: "isEnabled",
            isSearch: true,
            type: "combox",
            data: [
              {
                id: "2",
                value: "不限"
              },
              {
                id: "1",
                value: "是"
              },
              {
                id: "0",
                value: "否"
              }
            ]
          },
           {
            title: "图标",
            name: "icon"
          },
           {
            title: "排序",
            name: "orderNum"
          },
            {
            title: "创建时间",
            name: "createTime",
            // isSearch: true,
            type: "timer"
          },
           {
            title: "修改时间",
            name: "updateTime",
            // isSearch: true,
            type: "timer"
          }
        ],
        fnRowCallback: function(row, data) {
          if (data.isEnabled) {
            $("td:eq(3)", row).html('<i class="fa fa-fw fa-check-circle"></i>');
          } else {
            $("td:eq(3)", row).html('<i class="el-icon-close"></i>');
          }
        },
        idName: "id",
        functions: {
          more: [
            {
              text: "停用",
              url:  "/processCategory/disable",
              functionName:'processCategoryDisable'
            },
            {
              text: "启用",
              url:  "/processCategory/enable",
              functionName:'processCategoryEnable'
            },
            {
              text: "删除",
              url:  "/processCategory/delete",
              functionName:'processCategoryDelete'
            }
          ],
          common: [
            {
              text: "添加分类",
              url: "/processCategory/add",
              mode: "navigate",
              functionName:'processCategoryAdd'
            }
          ]
        },
        operations: [
          {
            text: "查看",
            url: "/processCategory/view",
            functionName:'processCategory'
          }
        ]
      }
    };
  }
};
</script>