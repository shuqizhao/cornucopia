<template>
    <div>
        <editList ref="materiel" :cfg="cfg1"></editList>
    </div>
</template>
<script>
export default {
  mounted: function() {
      self = this;
  },
  updated:function(){
      self = this;
  },
  data() {
    return {
      cfg1: {
        title: "报废明细",
        mode: "create",
        name: "DiscardMateriel",
        showCheckBox: true,
        get: {
          url: "/function/getPara",
          params: {
            id: self.$parent.$parent.$parent.id
          }
        },
        items: [
          {
            name: "id",
            type: "hidden"
          },
          {
            name: "name",
            title: "参数名",
            type: "text"
          },
          {
            name: "type",
            title: "参数类型",
            type: "combox",
            data: [
              {
                id: 1,
                name: "文本"
              },
              {
                id: 2,
                name: "数字"
              },
              {
                id: 3,
                name: "日期"
              }
            ]
          },
          {
            name: "desc",
            title: "描述",
            type: "text"
          }
        ],
        rules: {
          name: {
            required: true
          },
          desc: {
            required: true
          }
        },
        messages: {
          name: {
            required: "参数必填"
          },
          desc: {
            required: "描述必填"
          }
        },
        validate: function(data, saveData) {
          return true;
        },
        functions: [
          {
            text: "新增",
            type: "btn-success",
            icon: "el-icon-circle-plus",
            onClick: function() {
              self.$refs.materiel.insertNew({});
            }
          },
          {
            text: "删除",
            type: "btn-success",
            icon: "el-icon-edit",
            onClick: function() {
              self.$refs.materiel.deleteSelected();
            }
          },
          {
            text: "导入",
            type: "btn-success",
            icon: "el-icon-edit",
            onClick: function() {
              self.$refs.materiel.upSelected();
            }
          },
          {
            text: "导出",
            type: "btn-success",
            icon: "el-icon-edit",
            onClick: function() {
              self.$refs.materiel.downSelected();
            }
          }
        ]
      }
    };
  }
};
</script>
