<template>
 <list :cfg="cfg"></list>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        // isShowSearchArea:"true",
        // scrollX: true,
        // scrollCollapse: true,
        // fixedColumns: {
        //   leftColumns: 4
        // },
        title: "白名单管理",
        parentTitle: "系统管理",
        url: this.getGlobalData().ApiBaseUrl + "/auth/whiteList",
        columns: [
          {
            title: "名称",
            name: "name",
            isSearch: true
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
            title: "Url",
            name: "url"
          },
            {
            title: "创建时间",
            name: "createTime",
            isSearch: true,
            type: "timer"
          },
           {
            title: "修改时间",
            name: "updateTime",
            isSearch: true,
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
              url: this.getGlobalData().ApiBaseUrl + "/user/disable"
            },
            {
              text: "启用",
              url: this.getGlobalData().ApiBaseUrl + "/user/enable"
            }
          ],
          common: [
            {
              text: "添加白名单",
              url: "roleAdd",
              mode: "modal"
            }
          ]
        },
        operations: [
          {
            text: "查看",
            url: "/auth/userview"
          }
        ]
      }
    };
  }
};
</script>