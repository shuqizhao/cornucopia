<template>
    <el-steps direction="vertical" :space="80" :active="1" finish-status="success" style="margin-left: 70px;">
        <el-step v-for="step in steps" :title="step.name" :description="step.userName" :key="step.id"></el-step>
        <!-- <el-step title="结束"></el-step> -->
    </el-steps>
</template>
<script>
export default {
  mounted: function() {
    var self = this;
    var processDataId = self.$route.query.id;
    var showDiagram = self.$route.query.showDiagram;
    if (processDataId&&showDiagram) {
      self.get(
        self.getGlobalData().ApiBaseUrl + "/process/getProcessInstDiagram",
        { processDataId: processDataId },
        function(r) {
          if (r.code == 200) {
              self.steps = r.data;
              self.steps.push({
                  id:99999,
                  name:'结束'
              });
          }
        }
      );
    }
  },
  data() {
    return {
        steps:[]
    };
  }
};
</script>