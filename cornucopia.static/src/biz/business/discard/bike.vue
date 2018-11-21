<template>
    <div>
        <editList ref="bike" :cfg="cfg1"></editList>
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
        name: "functionParas",
        showCheckBox: true,
        get: {
          url: "/function/getPara",
          params: {
            id: self.$parent.$parent.$parent.id
          }
        },
        items: [
          // {
          //   name: "id",
          //   type: "hidden"
          // },
          {
            name: "area",
            title: "区域",
            type: "text"
          },
          {
            name: "city",
            title: "城市",
            type: "text"
          },
          {
            name: "bikeType",
            title: "车型",
            type: "combox",
            data:[{id:'0',value:'Classic 3.0'}]
          },
          {
            name: "dealMoney",
            title: "预计处置金额",
            type: "money"
          },
          {
            name: "fixMoney",
            title: "预计维修成本",
            type: "money"
          },
          {
            name: "reason",
            title: "资产报废原因",
            type: "combox"
          },
          {
            name: "dealType",
            title: "处理方式",
            type: "combox"
          },
          {
            name: "commitDate",
            title: "报废提交时间",
            type: "date"
          },
          {
            name: "bikeId",
            title: "Bike Id",
            type: "text"
          },
          {
            name: "warehouse",
            title: "仓库",
            type: "combox"
          },
          {
            name: "fontWheel",
            title: "回收前轮",
            type: "number"
          },
          {
            name: "backWheel",
            title: "回收后轮",
            type: "number"
          },
          {
            name: "handlebar",
            title: "回收车把",
            type: "number"
          },
          // {
          //   name: "seatTube",
          //   title: "回收座管",
          //   type: "number"
          // },
          // {
          //   name: "standpipe",
          //   title: "回收立管",
          //   type: "number"
          // },
          // {
          //   name: "frontFork",
          //   title: "回收前叉",
          //   type: "number"
          // },
          // {
          //   name: "basketry",
          //   title: "回收篮筐",
          //   type: "number"
          // },
          // {
          //   name: "solarPanels",
          //   title: "回收太阳能板",
          //   type: "number"
          // },
          // {
          //   name: "lock",
          //   title: "回收锁",
          //   type: "number"
          // },
          // {
          //   name: "cushion",
          //   title: "回收坐垫",
          //   type: "number"
          // }
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
              self.$refs.bike.insertNew({});
            }
          },
          {
            text: "删除",
            type: "btn-success",
            icon: "el-icon-edit",
            onClick: function() {
              self.$refs.bike.deleteSelected();
            }
          },
          {
            text: "导入",
            type: "btn-success",
            icon: "el-icon-edit",
            onClick: function() {
              self.$refs.bike.upSelected();
            }
          },
          {
            text: "导出",
            type: "btn-success",
            icon: "el-icon-edit",
            onClick: function() {
              self.$refs.bike.downSelected();
            }
          }
        ]
      }
    };
  }
};
</script>
