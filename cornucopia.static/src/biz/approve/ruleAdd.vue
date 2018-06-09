<template>
 <div>
     <mform :cfg="cfg"></mform>
 </div>
</template>
<script>
export default {
  mounted:function(){
  },
  data() {
    var self = this;
    return {
      cfg: {
        title: "新增岗位规则",
        mode: "create",
        // name:'approve',
        // hideFooter: true,
        items: [
          {
            name: "name",
            title: "规则名",
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
            required: "规则名必须填写"
          }
        },
        validate: function(data, saveData) {
          return true;
        }
      }
    };
  }
};
</script>