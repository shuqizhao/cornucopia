<template>
  <listV2 ref="list" :cfg="cfg">
    <template slot="header">
      <el-badge :value="totalCount"><b>{{cfg.title}}</b></el-badge>
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
  </listV2>
</template>
<script>
export default {
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
        url: "/process/catetoryGroup",
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
        url: "/process/launchedGroup?categoryId=" + categoryId,
        success: function(r) {
          if (r.code == 200) {
            self.processes = r.data;
            if (self.processes.length > 0) {
              self.currentProcessId = self.processes[0].id;
              self.$refs.list.fillData({
                processId: self.currentProcessId
              });
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
        autoLoad:false,
        title: "我发起的任务",
        url: "/process/launchedList",
        columns: [
          {
            title: "状态",
            name: "status",
            isSearch: true,
            type: "combox",
            data: [
              {
                id: 1,
                value: "全部"
              },
              {
                id: 2,
                value: "正常"
              },
              {
                id: 3,
                value: "催办"
              }
            ]
          },
          {
            title: "申请单号",
            name: "formCode",
            isSearch: true,
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
            type: "timer"
          },
          {
            title: "完成日期",
            name: "updateTime",
            isSearch: true,
            type: "timer"
          },
          {
            title: "召回状态",
            name: "zhaohuistatus",
            isSearch: true
          },
          {
            title: "流程状态",
            name: "processstatus",
            isSearch: true
          },
          {
            title: "当前审批信息",
            name: "createName",
            isSearch: true
          },
          {
            title: "流程ID",
            name: "Id",
            isSearch: true
          },
          {
            title: "流程图",
            name: "stepName"
          }
        ],
        idName: "id"
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

