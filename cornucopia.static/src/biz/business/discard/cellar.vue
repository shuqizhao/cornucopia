<template>
    <div>
        <mform ref="applyInfo" :cfg="cfgApplyInfo"></mform>
        <component ref="discard" v-bind:is="discardComponent"></component>
    </div>
</template>
<script>
import lock from "./lock.vue";
import materiel from "./materiel.vue";
import bike from "./bike.vue";
export default {
  methods: {
    changeDetail: function(value) {
      var applyInfo = self.findRef("applyInfo").$parent;
      if (value == 0) {
        applyInfo.discardComponent = bike;
      } else if (value == 1) {
        applyInfo.discardComponent = materiel;
      } else {
        applyInfo.discardComponent = lock;
      }
    },
    afterDataLoad: function(data,allData) {
      this.changeDetail(data.discardType);
      debugger;
       if (data.discardType == 0) {
        this.$refs.discard.dataTable = allData.DiscardBike
      } else if (data.discardType == 1) {
        this.$refs.discard.dataTable = allData.DiscardLock
      } else {
        this.$refs.discard.dataTable = allData.DiscardLock
      }
      
    }
  },
  mounted: function() {
    this.setBreadcrumbTitle(this, "发起新流程", "固定资产报废流程");
    self = this;
    self.get({
      url: "/data/get?parentCode=x",
      sccuess: function(response) {
        if (response.code == 200) {
          self.findCfgItem(self.cfgApplyInfo, "discardType").data = response.data;
        }
      }
    });
  },
  data() {
    return {
      discardComponent: "",
      cfgApplyInfo: {
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
