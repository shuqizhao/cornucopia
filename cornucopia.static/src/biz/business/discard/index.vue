<template>
    <div>
      <apply ref="apply" @afterDataLoad='afterDataLoad' />
    </div>
</template>
<script>
import discardApplication from "./discardApplication.vue";
export default {
  mounted: function() {
    let self = this;
    this.setBreadcrumbTitle(this, "发起新流程", "固定资产报废流程");
    if (self.$refs.apply) {
      self.$refs.apply.cellar = discardApplication;
    }
  },
  updated: function() {
    let self = this;
    self.$refs.apply.cellar = discardApplication;
    // self.findRef("apply").cellar = discardApplication;
  },
  destroyed: function() {
    this.setBreadcrumbTitle(this, "", "");
  },
  data() {
    let self = this;
    return {};
  },
  methods: {
    afterDataLoad: function(data) {
      if(data.applyInfo[0].discardType == "0"){
        self.findRef('cellar').changeDetail(data.applyInfo[0].discardType)
      }
    }
  }
};
</script>
<style>
</style>

