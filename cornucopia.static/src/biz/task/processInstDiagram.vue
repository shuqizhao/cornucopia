<template>

</template>
<script>
export default {
  mounted: function() {
    var self = this;
    var processDataId = self.$route.query.id;
    if (processDataId) {
      self.get(
        self.getGlobalData().ApiBaseUrl + "/process/getProcessInstDiagram",
        { processDataId: processDataId },
        function(r) {
          if (r.code == 200) {
          }
        }
      );
    }
  },
  data() {
    return {};
  }
};
</script>