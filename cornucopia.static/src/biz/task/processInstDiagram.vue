<template>
  <div>
    <ul class="timeline">
      <!-- timeline time label -->
      <li class="time-label">
        <span class="bg-red" style="width:100%;margin-left:5px;">流程图</span>
      </li>
      <!-- /.timeline-label -->
      <!-- timeline item -->
      <li v-for="(step,index) in parentSteps" :key="step.id">
        <!-- timeline icon -->
        <!-- <span class="pull-left badge bg-blue">{{index+1}}</span> -->
        <i class="fa fa-share bg-blue">{{index+1}}</i>
        <div class="timeline-item">
          <h3 class="timeline-header">
            <a>
              <b>{{step.name}}</b>
            </a>
          </h3>

          <div class="timeline-body" v-show="step.userName">
            <template v-for="childStep in getChilrenSetps(step.id)">
              <div
                :key="childStep.id"
                :class="childStep.isCurrent==1?'small-box bg-aqua':'small-box bg-aqua1'"
              >
                <el-popover
                  placement="left-start"
                  :title="childStep.userName"
                  trigger="hover"
                  :content="childStep.email+' '+childStep.personNumber"
                >
                  <el-button
                    icon="el-icon-arrow-left"
                    :type="childStep.isCurrent==1?'success':'info'"
                    style="width:100%"
                    slot="reference"
                    size="mini"
                  >{{childStep.userName}}</el-button>
                </el-popover>

                <a class="small-box-footer">{{childStep.createTime}}</a>
              </div>
            </template>
            <!-- small box -->
            <div :class="step.isCurrent==1?'small-box bg-aqua':'small-box bg-aqua1'">
              <el-popover
                placement="left-start"
                :title="step.userName"
                trigger="hover"
                :content="step.email+' '+step.personNumber"
              >
                <el-button
                  :type="isStepCurrent(step)?'success':'info'"
                  style="width:100%"
                  slot="reference"
                >{{step.userName}}</el-button>
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
    //   this.loadDiagram();
  },
  data() {
    return {
      steps: [],
      parentSteps: [],
      childrenSteps: [],
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
              for (var i = 0; i < r.data.length; i++) {
                if (r.data[i].parentId == 0) {
                  self.parentSteps.push(r.data[i]);
                } else {
                  self.childrenSteps.push(r.data[i]);
                }
              }
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
    },
    getChilrenSetps: function(id) {
      var childSteps = [];
      for (var i = 0; i < this.steps.length; i++) {
        if (this.steps[i].parentId == id) {
          childSteps.push(this.steps[i]);
        }
      }
      return childSteps;
    },
    isStepCurrent: function(step) {
      var children = this.getChilrenSetps(step.id);
      for (var i = 0; i < children.length; i++) {
        if (children[i].isCurrent == 1) {
          return false && step.isCurrent == 1;
        }
      }
      return true && step.isCurrent == 1;
    }
  }
};
</script>
<style>
.bg-aqua1 {
  background-color: rgb(194, 191, 191);
}
</style>
