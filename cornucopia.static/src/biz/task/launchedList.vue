<template>
  <list :cfg="cfg"></list>
</template>
<script>
export default {
  mounted:function(){
    var self = this;
    self.get(self.getGlobalData().ApiBaseUrl + "/process/alllist",'',function(r){
      if(r.code==200){
        var data =[];
        for(var i=0;i<r.data.length;i++){
          data.push({id:r.data[i].id,value:r.data[i].name});
        }
        self.cfg.columns[1].data=data;
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
        url: this.getGlobalData().ApiBaseUrl + "/process/launchedList",
        columns: [
          {
            title: "单号",
            name: "formCode",
            isSearch: true
          },{
            title: "流程类型",
            name: "processName",
            isSearch: true,
            type:"combox",
            data:[]
          },
            {
            title: "发起时间",
            name: "createTime",
            isSearch: true,
            type: "timer"
          },
          {
            title: "最后审批人",
            name: "updateBy"
          },
           {
            title: "最后操作时间",
            name: "updateTime",
            isSearch: true,
            type: "timer"
          }
        ],
        fnRowCallback: function(row, data) {
          // if (data.isEnabled) {
          //   $("td:eq(3)", row).html('<i class="fa fa-fw fa-check-circle"></i>');
          // } else {
          //   $("td:eq(3)", row).html('<i class="el-icon-close"></i>');
          // }
        },
        idName: "id",
        // functions: {
        //   more: [
        //     {
        //       text: "停用",
        //       url: this.getGlobalData().ApiBaseUrl + "/user/disable",
        //       functionName:'userDisable'
        //     },
        //     {
        //       text: "启用",
        //       url: this.getGlobalData().ApiBaseUrl + "/user/enable",
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