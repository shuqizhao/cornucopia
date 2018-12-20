<template>
  <listV2 :cfg="cfg"></listV2>
  <!-- <list :cfg="cfg"></list> -->
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        showRadio: true,
        showCheckBox: false,
        title: "用户管理",
        parentTitle: "系统管理",
        url: "/user/list",
        columns: [
          {
            title: "用户名",
            name: "name",
            isSearch: true
          },
          {
            title: "登陆名",
            name: "loginName"
          },
          {
            title: "是否启用",
            name: "isEnabled",
            // isSearch: true,
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
                return '<i class="fa fa-fw fa-check-circle"></i>';
              } else {
                return '<i class="el-icon-close"></i>';
              }
            }
          },
          {
            title: "工号",
            name: "personNumber"
            // isSearch: true,
            // fixed:true
          },
          {
            title: "职位",
            name: "jobName"
          },
          {
            title: "工作邮箱",
            name: "email"
          },
          {
            title: "手机号码",
            name: "phone"
          },
          {
            title: "所属上级",
            name: "managerName"
          },
          {
            title: "所在部门",
            name: "orgName"
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
          },
          {
            title: "最后登录时间",
            name: "lastLoginTime"
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
              url: "/user/disable",
              functionName: "userDisable"
            },
            {
              text: "启用",
              url: "/user/enable",
              functionName: "userEnable"
            }
          ],
          common: [
            {
              text: "添加用户",
              url: "/auth/userAdd",
              mode: "navigate",
              functionName: "userAdd"
            }
          ]
        },
        operations: [
          {
            text: "查看",
            url: "/auth/userView"
          }
        ]
      }
    };
  }
};
</script>