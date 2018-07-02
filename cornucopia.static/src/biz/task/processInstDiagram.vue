<template>
    <el-steps direction="vertical" :space="80" :active="activeCount" finish-status="success" style="margin-left: 70px;">
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
              for(var i=0;i<r.data.length;i++){
                  if(r.data[i].isCurrent==1){
                      self.activeCount=i+1;
                  }
              }
          }
        }
      );
    }
  },
  data() {
    return {
        steps:[],
        activeCount:0
    };
  }
};
</script>