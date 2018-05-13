<template>
  <el-row>
    <el-col :span="12"><list :cfg="cfg"></list></el-col>
    <el-col :span="12" id="resTree"><tree :cfg="treeCfg"></tree></el-col>
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
        }
      },
      treeCfg: {
        title: "资源管理",
        parentTitle: "权限管理",
        url: this.getGlobalData().ApiBaseUrl + "/auth/allResource",
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
              url: "component-a",
              mode: "navigate"
            }
          ]
        },
        operations: [
          {
            text: "查看",
            url: "/auth/roleview"
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
