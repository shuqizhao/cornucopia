<template>
 <mform :cfg="cfg"></mform>
</template>
<script>
export default {
  data() {
    var self = this;
    var processId = $("#id1").attr("processId");
    return {
      cfg: {
        title: "查看流程",
        detailTitle: "查看流程",
        editTitle: "编辑流程",
        mode: "detailEdit",
        get: {
          url: "/process/get",
          params: {
            id: processId
          }
        },
        save: "/process/update",
        items: [
          {
            name: "id",
            type: "hidden"
          },
          {
            name: "name",
            title: "流程名",
            type: "text",
            isRequire: true
          },
          {
            name: "url",
            title: "地址",
            type: "text",
            isRequire: true
          },
          {
            name: "icon",
            title: "图标",
            type: "text"
          },
          {
            name: "orderNum",
            title: "排序",
            type: "text",
            isRequire: true
          },
          {
            name: "pre",
            title: "前缀",
            type: "text",
            isRequire: true
          },
          {
            name: "categoryId",
            title: "所属分类",
            type: "select",
            data: [],
            isRequire: true
          }
        ],
        rules: {
          name: {
            required: true
          },
          url: {
            required: true
          },
          orderNum: {
            required: true,
            digits: true
          },
          pre: {
            required: true
          },
          categoryId: {
            required: true
          }
        },
        messages: {
          name: {
            required: "流程名必须填写"
          },
          url: {
            required: "地址必须填写"
          },
          orderNum: {
            required: "排序必须填写",
            digits: "只能输入整数"
          },
          pre: {
            required: "前缀必须填写"
          },
          categoryId: {
            required: "分类必须填写"
          }
        },
        validate: function(data, saveData) {
          self.post({
            url:"/process/exists",
            data:data,
            success:function(response){
              if (response.code == 200 && response.data == 0) {
                saveData(data);
              } else {
                self.$message({
                  type: "warning",
                  message: "流程或前缀已经存在!"
                });
              }
            }
          });
          return false;
        },
        onLoaded: function(detail) {
          self.get({
            url: "/process/category",
            success: function(response) {
              if ((response.code = 200)) {
                var tempArr =[];
                for(var i=0;i<response.data.length;i++){
                  tempArr.push({'id':response.data[i].id,'value':response.data[i].name});
                }
                self.cfg.items[6].data = tempArr;
              }
            }
          });
        }
      }
    };
  }
};
</script>