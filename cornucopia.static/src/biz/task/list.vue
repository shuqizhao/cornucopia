<template>
  <listV2 ref="list" :cfg="cfg">
    <template slot="header">
      <el-badge :value="totalCount">
        <b>{{cfg.title}}</b>
      </el-badge>
      <el-badge
        v-for="(category) in processCategories"
        :key="category.id"
        :value="category.totalCount"
        class="item"
      >
        <el-tag
          :type="category.id==currentCategoryId?'danger':''"
          style="margin-left:25px;"
          plain
          size="mini"
        >
          <a @click="onCagegoryClick(category)">{{category.name}}</a>
        </el-tag>
      </el-badge>
      <hr v-if="processes.length>0">
      <el-badge
        v-for="(process) in processes"
        :key="process.id"
        :value="process.totalCount"
        class="item"
      >
        <el-tag
          :type="process.id==currentProcessId?'danger':''"
          style="margin-left:25px;"
          plain
          size="mini"
        >
          <a @click="onProcessClick(process)">{{process.name}}</a>
        </el-tag>
      </el-badge>
    </template>
    <template slot="column">
      <el-table-column label="流程图">
        <template slot-scope="scope">
          <el-popover placement="right" trigger="click">
            <diagram :processDataId="scope.row.id" style="height:550px;overflow:auto;"></diagram>
            <el-button icon="el-icon-search" slot="reference"></el-button>
          </el-popover>
        </template>
      </el-table-column>
    </template>
  </listV2>
</template>
<script>
import diagram from "../task/processInstDiagram.vue";

export default {
  components: {
    diagram
  },
  methods: {
    onCagegoryClick: function(category) {
      this.getProcessCategories(category.id);
    },
    onProcessClick: function(process) {
      this.currentProcessId = process.id;
      this.$refs.list.fillData({
        processId: process.id
      });
    },
    getProcessCategories: function(categoryId) {
      var self = this;
      self.processCategories = [];
      self.processes = [];
      self.post({
        url: "/process/myTaskCatetoryGroup",
        success: function(r) {
          if (r.code == 200) {
            self.totalCount = 0;
            self.processCategories = r.data;
            for (var i = 0; i < self.processCategories.length; i++) {
              self.totalCount += self.processCategories[i].totalCount;
            }
            if (self.processCategories.length > 0) {
              if (categoryId) {
                self.currentCategoryId = categoryId;
              } else {
                self.currentCategoryId = self.processCategories[0].id;
              }
              self.getProcesses(self.currentCategoryId);
            }
          }
        }
      });
    },
    getProcesses: function(categoryId) {
      self = this;
      self.post({
        url: "/process/taskGroup?categoryId=" + categoryId,
        success: function(r) {
          if (r.code == 200) {
            self.processes = r.data;
            if (self.processes.length > 0) {
              self.currentProcessId = self.processes[0].id;
              setTimeout(() => {
                self.$refs.list.fillData({
                  processId: self.currentProcessId
                });
              }, 100);
            }
          }
        }
      });
    }
  },
  mounted: function() {
    this.setDocumentTitle(this, "流程管理系统", "100%");
    this.getProcessCategories();
  },
  data() {
    var self = this;
    return {
      currentCategoryId: 0,
      currentProcessId: 0,
      totalCount: 0,
      processCategories: [],
      processes: [],
      cfg: {
        searchMode: "vertical",
        autoLoad: false,
        title: "我的任务",
        url: "/process/taskList",
        columns: [
          {
            title: "状态",
            name: "status",
            isSearch: true,
            type: "combox",
            data: [
              {
                id: 0,
                value: "全部"
              },
              {
                id: 1,
                value: "正常"
              },
              {
                id: 2,
                value: "催办"
              }
            ],
            formatter: function(data) {
              if (data.status == 2) {
                return '<center><div class="label label-warning">催办</div></center>';
              } else {
                return '<center><div class="label label-success">正常</div></center>';
              }
            }
          },
          {
            title: "申请单号",
            name: "formCode",
            isSearch: true,
            width: "150",
            formatter: function(data) {
              var f =
                '<a target="_blank" href="#/' +
                data.url +
                "?processId=" +
                data.processId +
                "&id=" +
                data.id +
                '&showDiagram=1">' +
                data.formCode +
                "</a>";
              return f;
            }
          },
          {
            title: "发起日期",
            name: "createTime",
            isSearch: true,
            type: "timer",
            width: "150"
          },
          {
            title: "完成日期",
            name: "updateTime",
            isSearch: true,
            type: "timer",
            width: "150",
            formatter: function(data) {
              if (data.processStatus != 1) {
                return data.updateTime;
              } else {
                return "";
              }
            }
          },
          {
            title: "召回状态",
            name: "callbackStatus",
            isSearch: true,
            type: "combox",
            data: [
              {
                id: 0,
                value: "全部"
              },
              {
                id: 1,
                value: "可召回"
              },
              {
                id: 2,
                value: "不可召回"
              }
            ],
            formatter: function(data) {
              if (data.callbackStatus == 2) {
                return '<center><div class="label label-default">不可召回</div></center>';
              } else {
                return '<center><div class="label label-success">可召回</div></center>';
              }
            }
          },
          {
            title: "流程状态",
            name: "processStatus",
            isSearch: true,
            type: "combox",
            data: [
              {
                id: 0,
                value: "全部"
              },
              {
                id: 1,
                value: "进行中"
              },
              {
                id: 2,
                value: "完成"
              },
              {
                id: 3,
                value: "终止"
              }
            ],
            formatter: function(data) {
              if (data.processStatus == 2) {
                return '<center><div class="label label-primary">完成</div></center>';
              } else if (data.processStatus == 1) {
                return '<center><div class="label label-success">进行中</div></center>';
              } else {
                return '<center><div class="label label-danger">终止</div></center>';
              }
            }
          },
          {
            title: "当前审批信息",
            name: "stepName",
            isSearch: true
          },
          {
            title: "流程ID",
            name: "id",
            isSearch: true
          }
        ],
        idName: "id",
        beforeFillData: function(formInline) {
          formInline["processId"] = self.currentProcessId;
          return formInline;
        }
      }
    };
  }
};
</script>
<style>
</style>

<style scoped>
.el-tag:hover {
  background-color: rgba(245, 108, 108, 0.1);
}
</style>

