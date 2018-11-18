<template>
    <div>
      <apply ref="apply" />
    </div>
</template>
<script>
import discardApplication from "./discardApplication.vue";
export default {
  mounted: function() {
    let self = this;
    this.setBreadcrumbTitle(this, "发起新流程", "固定资产报废流程");
    if(self.$refs.apply){
      self.$refs.apply.currentComponent = discardApplication;
    }
  },
  updated: function() {
    let self = this;
    self.$refs.apply.currentComponent = discardApplication;
  },
  destroyed: function() {
    this.setBreadcrumbTitle(this, "", "");
  },
  data() {
    let self = this;
    return {};
  }
};
</script>
<style>
</style>

