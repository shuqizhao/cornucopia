<template>
  <listV2 ref="list" :cfg="cfg">
    <!-- <template slot="header">
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
    </template>-->
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
    getProcesses: function(categoryId) {
      self = this;
      self.get({
        url: "/process/alllist",
        success: function(r) {
          if (r.code == 200) {
            self.processes = r.data;
            for (var i = 0; i < self.processes.length; i++) {
              self.cfg.columns[0].data.push({
                id: self.processes[i].id,
                value: self.processes[i].name
              });
            }
          }
        }
      });
    }
  },
  mounted: function() {
    this.setDocumentTitle(this, "流程管理系统", "100%");
    this.getProcesses();
  },
  data() {
    var self = this;
    return {
      processes: [],
      cfg: {
        autoLoad: false,
        title: "流程监控",
        parentTitle: "系统管理",
        url: "/process/monitorList",
        columns: [
          {
            title: "流程名称",
            name: "processId",
            isSearch: true,
            type: "combox",
            data: [],
            formatter: function(data) {
              return data.processName;
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
            title: "申请人",
            name: "createBy",
            isSearch: true,
            type:"popup",
            formatter: function(data) {
              return data.createName;
            }
          },
          {
            title: "申请人账号",
            name: "loginName"
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
            // isSearch: true,
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
          //   {
          //     title: "召回状态",
          //     name: "callbackStatus",
          //     // isSearch: true,
          //     type: "combox",
          //     data: [
          //       {
          //         id: 0,
          //         value: "全部"
          //       },
          //       {
          //         id: 1,
          //         value: "可召回"
          //       },
          //       {
          //         id: 2,
          //         value: "不可召回"
          //       }
          //     ],
          //     formatter: function(data) {
          //       if (data.callbackStatus == 2) {
          //         return '<center><div class="label label-default">不可召回</div></center>';
          //       } else {
          //         return '<center><div class="label label-success">可召回</div></center>';
          //       }
          //     }
          //   },
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
          //   {
          //     title: "当前审批信息",
          //     name: "stepName",
          //     // isSearch: true
          //   },
          {
            title: "流程ID",
            name: "id"
            // isSearch: true
          }
        ],
        idName: "id",
        functions: {
          common: [
            {
              text: "重试",
              url: "/process/monitorRetry"
              //   mode: "navigate",
              //   functionName: "monitorRetry"
            },
            {
              text: "启动",
              url: "/auth/userAdd",
              mode: "navigate"
              //   functionName: "processStart"
            },
            {
              text: "删除",
              url: "/auth/userAdd",
              mode: "navigate"
              //   functionName: "processDelete"
            },
            {
              text: "跳转",
              url: "/auth/userAdd",
              mode: "navigate"
              //   functionName: "processJump"
            },
            {
              text: "修改",
              url: "/auth/userAdd",
              mode: "navigate"
              //   functionName: "processUpdate"
            },
            {
              text: "解析",
              url: "/auth/userAdd",
              mode: "navigate"
              //   functionName: "processParse"
            },
            {
              text: "模拟",
              url: "/auth/userAdd",
              mode: "navigate"
              //   functionName: "processDiagram"
            }
          ]
        }
      }
    };
  }
};
</script>
<style>
</style>
