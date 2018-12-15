<template>
  <listV2 :cfg="cfg">
    <template slot="header">
      <b>{{cfg.title+'('+totalCount+')'}}</b>
      <el-badge
        v-for="category in processCategories"
        :key="category.id"
        :value="category.totalCount"
        class="item"
      >
        <el-tag style="margin-left:25px;" plain size="mini">{{category.name}}</el-tag>
      </el-badge>
      <hr v-if="processes.length>0">
      <el-badge
        v-for="process in processes"
        :key="process.id"
        :value="process.totalCount"
        class="item"
      >
        <el-tag style="margin-left:25px;" plain size="mini">{{process.name}}</el-tag>
      </el-badge>
    </template>
  </listV2>
</template>
<script>
export default {
  methods: {
    getProcessCategories: function() {
      var self = this;
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
              self.getProcesses(self.processCategories[0].id);
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
      totalCount: 0,
      processCategories: [],
      processes: [],
      cfg: {
        searchMode: "vertical",
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
