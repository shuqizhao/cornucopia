<template>
  <div>
    <ul class="timeline">
      <!-- timeline time label -->
      <li class="time-label">
        <span class="bg-red" style="width:100%;margin-left:5px;">流程图</span>
      </li>
      <!-- /.timeline-label -->
      <!-- timeline item -->
      <li v-for="(step,indexParent) in parentSteps" :key="indexParent">
        <!-- timeline icon -->
        <!-- <span class="pull-left badge bg-blue">{{index+1}}</span> -->
        <i class="fa fa-share bg-blue">{{indexParent+1}}</i>
        <div class="timeline-item">
          <h3 class="timeline-header">
            <a>
              <b>{{formaterTitle(step)}}</b>
            </a>
          </h3>

          <div class="timeline-body" v-show="step.userName">
            <div
              v-for="childStep in getPreSignChilrenSteps(step.guid)"
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
                  :type="childStep.isCurrent==1?'success':'info'"
                  style="width:100%"
                  slot="reference"
                  size="mini"
                >{{childStep.userName+'-前加签'}}</el-button>
              </el-popover>

              <a class="small-box-footer">{{childStep.isCurrent==1?'处理中...':childStep.updateTime}}</a>
            </div>
            <template v-for="(childStep) in getTransferChilrenSteps(step.guid)">
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
                    icon="el-icon-refresh"
                    :type="childStep.isCurrent==1?'success':'info'"
                    style="width:100%"
                    slot="reference"
                    size="mini"
                  >{{step.userName+'->'+childStep.userName}}</el-button>
                </el-popover>

                <a class="small-box-footer">{{childStep.isCurrent==1?'处理中...':childStep.updateTime}}</a>
              </div>
            </template>
            <!-- small box -->
            <div
              v-if="getTransferChilrenSteps(step.guid).length==0"
              :class="step.isCurrent==1?'small-box bg-aqua':'small-box bg-aqua1'"
            >
              <el-popover
                placement="left-start"
                :title="step.userName"
                trigger="hover"
                :content="step.email+' '+step.personNumber"
              >
                <el-button
                  :type="step.isCurrent==1?'success':'info'"
                  style="width:100%"
                  slot="reference"
                  size="mini"
                >{{step.userName}}</el-button>
              </el-popover>

              <a class="small-box-footer">{{step.isCurrent==1?'处理中...':step.createTime}}</a>
            </div>
            <template v-for="(childStep) in getAfterSignChilrenSteps(step.guid)">
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
                    :type="childStep.isCurrent==1?'success':'info'"
                    style="width:100%"
                    slot="reference"
                    size="mini"
                  >{{childStep.userName+'-后加签'}}</el-button>
                </el-popover>

                <a class="small-box-footer">{{childStep.isCurrent==1?'处理中...':childStep.updateTime}}</a>
              </div>
            </template>
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
                if (r.data[i].parentGuid == "0") {
                  self.parentSteps.push(r.data[i]);
                } else {
                  self.childrenSteps.push(r.data[i]);
                }
              }
              self.parentSteps.push({
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
    getTransferChilrenSteps: function(id) {
      var childSteps = [];
      for (var i = 0; i < this.steps.length; i++) {
        if (
          this.steps[i].parentGuid == id &&
          this.steps[i].name == "transfer"
        ) {
          childSteps.push(this.steps[i]);
        }
      }
      return childSteps;
    },
    getPreSignChilrenSteps: function(id) {
      var childSteps = [];
      for (var i = 0; i < this.steps.length; i++) {
        if (this.steps[i].parentGuid == id && this.steps[i].name == "preSign") {
          childSteps.push(this.steps[i]);
        }
      }
      return childSteps;
    },
    getAfterSignChilrenSteps: function(id) {
      var childSteps = [];
      for (var i = 0; i < this.steps.length; i++) {
        if (
          this.steps[i].parentGuid == id &&
          this.steps[i].name == "afterSign"
        ) {
          childSteps.push(this.steps[i]);
        }
      }
      return childSteps;
    },
    formaterTitle: function(step) {
      if (step.name == "retry") {
        return "重发起";
      } else if (step.name == "modify") {
        return "申请人修订";
      }
      return step.name;
    }
    // ,
    // isStepCurrent: function(step) {
    //   var children = this.getPreSignChilrenSteps(step.id);
    //   for (var i = 0; i < children.length; i++) {
    //     if (children[i].isCurrent == 1) {
    //       return false && step.isCurrent == 1;
    //     }
    //   }
    //   return true && step.isCurrent == 1;
    // }
  }
};
</script>
<style>
.bg-aqua1 {
  background-color: rgb(194, 191, 191);
}
</style>
