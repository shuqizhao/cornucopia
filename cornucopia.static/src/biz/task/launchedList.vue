<template>
  <listV2 :cfg="cfg"></listV2>
  <!-- <list :cfg="cfg"></list> -->
</template>
<script>
export default {
  mounted: function() {
    this.setDocumentTitle(this, "流程管理系统", "100%");
    var self = this;
    self.get({
      url: "/process/alllist",
      success: function(r) {
        if (r.code == 200) {
          var data = [];
          for (var i = 0; i < r.data.length; i++) {
            data.push({ id: r.data[i].id, value: r.data[i].name });
          }
          self.cfg.columns[1].data = data;
        }
      }
    });
  },
  data() {
    var self = this;
    return {
      cfg: {
        // isShowSearchArea:"true",
        scrollCollapse: true,
        title: "我发起的任务",
        url: "/process/launchedList",
        columns: [
          {
            title: "单号",
            name: "formCode",
            fixed: true,
            isSearch: true
          },
          {
            title: "流程类型",
            name: "processName",
            isSearch: true,
            type: "combox",
            data: []
          },
          {
            title: "流程状态",
            name: "status"
          },
          // {
          //   title: "发起人",
          //   name: "createName"
          // },
          // {
          //   title: "发起时间",
          //   name: "createTime"
          // },
          {
            title: "最新审批步骤",
            name: "stepName"
          },
          {
            title: "最新审批人",
            name: "updateName"
          },
          {
            title: "最新操作时间",
            name: "updateTime",
            isSearch: true,
            type: "timer"
          }
        ],
        fnRowCallback: function(row, data) {
          $("td:eq(0)", row).html(
            '<a target="_blank" href="#/' +
              data.url +
              "?processId=" +
              data.processId +
              "&id=" +
              data.id +
              '&showDiagram=1">' +
              data.formCode +
              "</a>"
          );
        },
        idName: "id"
        // functions: {
        //   more: [
        //     {
        //       text: "停用",
        //       url:  "/user/disable",
        //       functionName:'userDisable'
        //     },
        //     {
        //       text: "启用",
        //       url:  "/user/enable",
        //       functionName:'userEnable'
        //     }
        //   ],
        // common: [
        //   {
        //     text: "添加用户",
        //     url: "/auth/userAdd",
        //     mode: "navigate",
        //     functionName:'userAdd'
        //   }
        // ]
        // },
        // operations: [
        //   {
        //     text: "查看",
        //     url: "/auth/userView"
        //   }
        // ]
      }
    };
  }
};
</script>