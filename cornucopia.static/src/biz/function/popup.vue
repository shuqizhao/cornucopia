<template>
  <list :cfg="cfg"></list>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "选择函数",
        // parentTitle: "权限管理",
        simpleUrl: this.getGlobalData().ApiBaseUrl + "/function/alllist",
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
