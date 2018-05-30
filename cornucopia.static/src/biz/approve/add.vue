<template>
 <div>
     <mform :cfg="cfg"></mform>
     <editList :cfg="cfg1"></editList>
     <buttonBar></buttonBar>
 </div>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "添加审批路径",
        mode: "create",
        save: this.getGlobalData().ApiBaseUrl + "/approve/add",
        items: [
          {
            name: "name",
            title: "路径名",
            type: "text"
          }
        ],
        rules: {
          name: {
            required: true
          }
        },
        messages: {
          name: {
            required: "审批路径名必须填写"
          }
        },
        validate: function(data, saveData) {
        //   $.ajax({
        //     type: "POST",
        //     xhrFields: {
        //       withCredentials: true
        //     },
        //     url: self.getGlobalData().ApiBaseUrl + "/approve/exists",
        //     data: data,
        //     success: function(response) {
        //       if (response.code == 200 && response.data == 0) {
        //         saveData(data);
        //       } else {
        //         self.$message({
        //           type: "warning",
        //           message: "角色已经存在!"
        //         });
        //       }
        //     }
        //   });
          return false;
        }
      },
        cfg1: {
        title: "条件公式",
        mode: "create",
        save: this.getGlobalData().ApiBaseUrl + "/approve/add",
        items: [
          {
            name: "name",
            title: "布尔运算符",
            type: "text"
          },
           {
            name: "name",
            title: "变量1",
            type: "text"
          },{
            name: "name",
            title: "变量1来源",
            type: "text"
          },{
            name: "name",
            title: "变量1数据类型",
            type: "text"
          },
           {
            name: "name",
            title: "比较运算符",
            type: "text"
          }, {
            name: "name",
            title: "变量2",
            type: "text"
          },{
            name: "name",
            title: "变量2来源",
            type: "text"
          },{
            name: "name",
            title: "变量2数据类型",
            type: "text"
          }
        ],
        rules: {
          name: {
            required: true
          }
        },
        messages: {
          name: {
            required: "审批路径名必须填写"
          }
        },
        validate: function(data, saveData) {
          return false;
        }
      }
    };
  }
};
</script>