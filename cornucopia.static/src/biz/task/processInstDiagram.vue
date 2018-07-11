<template>
<div>
    <!-- <el-steps direction="vertical" :space="80" :active="activeCount"  style="margin-left: 70px;">
        <el-step v-for="step in steps" :title="step.name" :description="step.userName" :key="step.id"></el-step>
        
    </el-steps> -->
    <ul class="timeline">

    <!-- timeline time label -->
    <li class="time-label">
        <span class="bg-red" style="width:100%;margin-left:5px;">
            流程图
        </span>
    </li>
    <!-- /.timeline-label -->

    <!-- timeline item -->
    <li v-for="step in steps" :key="step.id">
        <!-- timeline icon -->
        <i class="fa fa-arrow-down bg-blue"></i>
        <div class="timeline-item" >
            <!-- <span class="time"><i class="fa fa-clock-o"></i> 12:05</span> -->

            <h3 class="timeline-header"><a><b>{{step.name}}</b></a></h3>

            <div class="timeline-body" v-show="step.userName">
                <!-- small box -->
                <div class="small-box bg-aqua">
                    <el-popover
                        placement="top-start"
                        :title="step.userName"
                        trigger="hover"
                        :content="step.email">
                        <el-button :type="step.isCurrent==1?'success':'info'" style="width:100%" slot="reference">{{step.userName}}</el-button>
                        </el-popover>
                   
                    <a class="small-box-footer">{{step.createTime}}</a>
                </div>
            </div>
        </div>
    </li>
    <!-- END timeline item -->

</ul>
</div>
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