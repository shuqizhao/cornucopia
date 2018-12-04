<template>
 <listV2 :cfg="cfg"></listV2>
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
        url:  "/auth/whiteList",
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
            ],
            formatter: function(data) {
              if (data.isEnabled) {
                return '<center><i class="fa fa-fw fa-check-circle"></i></center>';
              } else {
                return '<center><i class="el-icon-close"></i></center>';
              }
            }
          },
          {
            title: "Url",
            name: "url"
          },
          {
            title: "类型",
            name: "type",
            formatter: function(data) {
              if (data.type==1) {
                return '<center><div class="label label-success">需要登陆</div></center>';
              } else {
                return '<center><div class="label label-warning">不需要登陆</div></center>';
              }
            }
          }
          ,
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
            $("td:eq(3)", row).html('<center><i class="fa fa-fw fa-check-circle"></i></center>');
          } else {
            $("td:eq(3)", row).html('<center><i class="el-icon-close"></i></center>');
          }

          if (data.type==1) {
            $("td:eq(5)", row).html('<center><div class="label label-success">需要登陆</div></center>');
          } else {
            $("td:eq(5)", row).html('<center><div class="label label-warning">不需要登陆</div></center>');
          }
        },
        idName: "id",
        functions: {
          more: [
            {
              text: "停用",
              url:  "/whitelist/disable",
              functionName:'whitelistDisable',
            },
            {
              text: "启用",
              url:  "/whitelist/enable",
              functionName:'whitelistEnable',
            }
          ],
          common: [
            {
              text: "添加白名单",
              url: "/auth/whitelistAdd",
              functionName:'whitelistAdd',
              mode: "navigate"
            }
          ]
        },
        operations: [
          {
            text: "查看",
            url: "/auth/whitelistView",
            functionName:'whitelistView',
          }
        ]
      }
    };
  }
};
</script>