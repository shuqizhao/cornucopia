<template>
  <list :cfg="cfg"></list>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "函数管理",
        parentTitle: "权限管理",
        simpleUrl:  "/function/alllist?id=0",
        lengthMenu: [[-1], ["ALL"]],
        sDom: 'f<"dataTables_function"/>',
        bServerSide: false,
        hideCheckBox: true,
        showSelectedRowColor: true,
        columns: [
          {
            title: "id",
            name: "id",
            isHide: true
          },
          {
            title: "函数名",
            name: "name"
          },
          {
            title: "函数描述",
            name: "desc"
          },
          {
            title: "是否启用",
            name: "isEnabled"
          },
          {
            title: "创建时间",
            name: "createTime"
          },
          {
            title: "修改时间",
            name: "updateTime"
          }
        ],
        idName: "id",
        fnRowCallback: function(row, data) {
          if (data.isEnabled) {
            $("td:eq(2)", row).html('<i class="fa fa-fw fa-check-circle"></i>');
          } else {
            $("td:eq(2)", row).html('<i class="el-icon-close"></i>');
          }
        },
        functions: {
          common: [
            {
              text: "添加函数",
              url: "functionAdd",
              mode: "modal"
            },
            {
              text: "编辑函数",
              url: "functionEdit",
              mode: "modal"
            }
          ],
          more: [
            {
              text: "停用",
              url:  "/function/disable"
            },
            {
              text: "启用",
              url:  "/function/enable"
            },
            {
              text: "删除",
              url:  "/function/delete"
            }
          ]
        },
        onClickRow: function(data, target) {
          self.id = data.id;
        }
      },
      id:0
    };
  },
  updated: function() {
    var self = this;
  }
};
</script>
<style>
.dataTables_filter {
  margin-bottom: -40px;
}
</style>
