<template>
    <div>
        <mform ref="applyInfo" :cfg="cfg1"></mform>
        <component v-bind:is="discardComponent"></component>
    </div>
</template>
<script>
import lock from "./lock.vue";
import materiel from "./materiel.vue";
import bike from "./bike.vue";
export default {
  methods: {
    changeDetail: function(value) {
      var a = self.findRef("applyInfo").$parent;
      if (value == 0) {
        a.discardComponent = bike;
      } else if (value == 1) {
        a.discardComponent = materiel;
      } else {
        a.discardComponent = lock;
      }
    }
  },
  mounted: function() {
    self = this;
    self.get({
      url: "/data/get?parentCode=x",
      sccuess: function(response) {
        if (response.code == 200) {
          self.findCfgItem(self.cfg1, "discardType").data = response.data;
        }
      }
    });
  },
  data() {
    return {
      discardComponent: "",
      cfg1: {
        title: "申请信息",
        detailTitle: "申请信息",
        editTitle: "申请信息",
        mode: "create",
        hideFooter: "true",
        name: "applyInfo",
        items: [
          {
            name: "Id",
            type: "hidden"
          },
          {
            name: "discardType",
            title: "报废类别",
            type: "select",
            data: [
              { id: 0, value: "车辆" },
              { id: 1, value: "物料" },
              { id: 2, value: "锁" }
            ],
            width: "300px;",
            isRequire: true,
            onChange: function(value) {
              var a = self.findRef("applyInfo").$parent;
              a.changeDetail(value);
            }
          },
          {
            name: "area",
            title: "区域",
            type: "text",
            width: "300px;"
          },
          {
            name: "position",
            title: "位置",
            type: "text",
            width: "300px;",
            isRequire: true
          },
          {
            name: "remarks",
            title: "备注",
            type: "textarea",
            isRequire: true
          }
        ],
        afterEditRender: function() {
          //  $(self.$el).find("#Name").attr("disabled", true);
        },
        rules: {
          position: {
            required: true
          },
          discardType: {
            required: true
          },
          remarks: {
            required: true
          }
        },
        messages: {
          position: {
            required: "位置必须填写"
          },
          discardType: {
            required: "报废类别必须填写"
          },
          remarks: {
            required: "备注必须填写"
          }
        }
      }
    };
  }
};
</script>
