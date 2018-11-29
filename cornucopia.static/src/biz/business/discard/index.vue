<template>
    <div>
      <apply ref="apply" />
    </div>
</template>
<script>
import cellar from "./cellar.vue";
export default {
  mounted: function() {
    let self = this;
    if (self.$refs.apply) {
      self.$refs.apply.cellar = cellar;
    }
  },
  updated: function() {
    let self = this;
    self.$refs.apply.cellar = cellar;
  },
  destroyed: function() {
    // this.setBreadcrumbTitle(this, "", "");
  },
  data() {
    let self = this;
    return {};
  },
  methods: {
  }
};
</script>
<style>
</style>

