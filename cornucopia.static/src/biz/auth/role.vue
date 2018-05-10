<template>
  <el-row>
    <el-col :span="12"><list :cfg="cfg"></list></el-col>
    <el-col :span="12"><tree :cfg="treeCfg"></tree></el-col>
  </el-row>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "角色管理列表",
        parentTitle: "权限管理",
        url: this.getGlobalData().ApiBaseUrl + "/role/list",
        "lengthMenu": [[-1], ["ALL"]],
        sDom:'<"dataTables_function"/>',
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
              url: "/auth/roleadd",
              mode: "navigate"
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
          alert(data.id)
        }
      },
      treeCfg: {
        title: "角色管理列表",
        parentTitle: "权限管理",
        url: this.getGlobalData().ApiBaseUrl + "/role/alllist",
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
              url: "/auth/roleadd",
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
    };
  }
};
</script>