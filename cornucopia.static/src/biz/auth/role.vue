<template>
  <el-row>
    <el-col :span="12"><list :cfg="cfg"></list></el-col>
    <el-col :span="12" id="resTree"><tree  ref="tree" :cfg="treeCfg"></tree></el-col>
  </el-row>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "角色管理",
        parentTitle: "权限管理",
        simpleUrl: this.getGlobalData().ApiBaseUrl + "/role/alllist",
        "lengthMenu": [[-1], ["ALL"]],
        sDom:'f<"dataTables_function"/>',
        bServerSide: false,
        hideCheckBox:true,
        columns: [
          {
            title: "id",
            name: "id",
            isHide: true
          },
          {
            title: "角色名",
            name: "name"
          },
          {
            title: "创建时间",
            name: "createTime"
          }
        ],
        idName: "id",
        functions: {
          common: [
            {
              text: "添加角色",
              url: "RoleAdd",
              mode: "modal"
            }
          ]
        },
        operations: [
          {
            text: "查看",
            url: "/auth/roleview"
          }
        ],
        onClickRow:function(data){
          self.openLoading($('#resTree')[0]);
           $.ajax({
              type: "GET",
              xhrFields: {
                withCredentials: true
              },
              url: self.getGlobalData().ApiBaseUrl + "/auth/getCheckedList?roleId="+data.id,
              success: function(response) {
                if (response.code == "200") {
                  self.$refs.tree.setCheckedKeys(response.data);
                  self.closeLoading();
                } else if (response.message) {
                  self.$message({
                    type: "warning",
                    message: response.message
                  });
                }
              }
            });
        }
      },
      treeCfg: {
        title: "资源管理",
        parentTitle: "权限管理",
        url: this.getGlobalData().ApiBaseUrl + "/auth/allResource",
        functions: [
            {
              text: "保存",
              type: "btn-info",
              onClick:function(){
              self.$confirm('确定要保存吗', "提示", {
                  confirmButtonText: "确定",
                  cancelButtonText: "取消",
                  type: "info"
                })
                .then(() => {
                  alert(1)
                });
              }
            }
          ]
      }
    }
  },
  updated:function(){
    var self = this;
    self.$parent.title= self.cfg.title;
    self.$parent.parentTitle= self.cfg.parentTitle;
  }
};
</script>
<style>
.dataTables_filter {
  margin-bottom: -40px;
}
</style>
