<template>
 <div>
     <mform :cfg="cfg"></mform>
     <editList></editList>
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
      }
    };
  }
};
</script>