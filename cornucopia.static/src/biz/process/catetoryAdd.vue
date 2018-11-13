<template>
 <mform :cfg="cfg"></mform>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "添加流程",
        mode: "create",
        save: this.getGlobalData().ApiBaseUrl + "/processCatetory/add",
        items: [
          {
            name: "name",
            title: "分类名",
            type: "text",
            isRequire:true,
          },
          {
            name: "icon",
            title: "图标",
            type: "text",
            isRequire:true,
          },
          {
            name: "orderNum",
            title: "排序",
            type: "text",
            isRequire:true,
          }
        ],
        rules: {
          name: {
            required: true
          },
          icon: {
            required: true
          },
          orderNum: {
            required: true,
            digits: true
          }
        },
        messages: {
          name: {
            required: "必须填写"
          },
          icon: {
            required: "必须填写"
          },
          orderNum: {
            required: "必须填写",
            digits: "只能输入整数"
          }
        },
        onSuccess: function(mode, response) {
          if (response.code == 200) {
            history.back();
          } else {
            $.fn.message({
              msg: "添加失败！"
            });
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