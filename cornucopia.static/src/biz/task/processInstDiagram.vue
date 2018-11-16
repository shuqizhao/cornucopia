<template>
<div>
    <ul class="timeline">

    <!-- timeline time label -->
    <li class="time-label">
        <span class="bg-red" style="width:100%;margin-left:5px;">
            流程图
        </span>
    </li>
    <!-- /.timeline-label -->

    <!-- timeline item -->
    <li v-for="(step,index) in steps" :key="step.id">
        <!-- timeline icon -->
        <!-- <span class="pull-left badge bg-blue">{{index+1}}</span> -->
        <i class="fa fa-share bg-blue">{{index+1}}</i>
        <div class="timeline-item" >
            <h3 class="timeline-header"><a><b>{{step.name}}</b></a></h3>
            
            <div class="timeline-body" v-show="step.userName">
                <!-- small box -->
                <div :class="step.isCurrent==1?'small-box bg-aqua':'small-box bg-aqua1'">
                    <el-popover
                        placement="top-start"
                        :title="step.userName"
                        trigger="hover"
                        :content="step.email+' '+step.personNumber">
                        <el-button :type="step.isCurrent==1?'success':'info'" style="width:100%" slot="reference">{{step.userName}}</el-button>
                        </el-popover>
                   
                    <a class="small-box-footer">{{step.createTime||(step.isCurrent==1?'处理中...':'')}}</a>
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
    //   this.loadDiagram();
  },
  data() {
    return {
      steps: [],
      activeCount: 0
    };
  },
  methods: {
    loadDiagram: function() {
      var self = this;
      var processDataId = self.$route.query.id;
      var showDiagram = self.$route.query.showDiagram;
      if (processDataId && showDiagram) {
        self.get({
          url: "/process/getProcessInstDiagram",
          data: { processDataId: processDataId },
          success: function(r) {
            if (r.code == 200) {
              self.steps = r.data;
              self.steps.push({
                id: 99999,
                name: "结束"
              });
              for (var i = 0; i < r.data.length; i++) {
                if (r.data[i].isCurrent == 1) {
                  self.activeCount = i + 1;
                }
              }
            }
          }
        });
      }
    }
  }
};
</script>
<style>
.bg-aqua1 {
  background-color: rgb(194, 191, 191);
}
</style>
