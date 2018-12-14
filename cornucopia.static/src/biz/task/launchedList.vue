<template>
  <listV2 :cfg="cfg">
    <template slot="header">
      <b>{{cfg.title+'(16)'}}</b>
      <el-badge :value="12" class="item">
        <el-tag style="margin-left:10px;" plain size="mini">财务类(5)</el-tag>
      </el-badge>
      <el-badge :value="12" class="item">
        <el-tag style="margin-left:10px;" plain size="mini">人事类(11)</el-tag>
      </el-badge>
      <hr>
      <el-badge :value="12" class="item">
        <el-tag style="margin-left:10px;" plain size="mini">财务类</el-tag>
      </el-badge>
      <el-badge :value="12" class="item">
        <el-tag style="margin-left:10px;" plain size="mini">人事类</el-tag>
      </el-badge>
    </template>
    <!-- <template>
      <el-input
        style="width:35%"
        size="mini"
        placeholder="查询条件"
        v-model="inputSearch"
        class="input-with-select"
      >
        <el-select v-model="selectSearch" size="mini" slot="prepend" placeholder="查询列">
          <el-option label="申请单号" value="1"></el-option>
          <el-option label="状态" value="2"></el-option>
          <el-option label="发起日期" value="3"></el-option>
          <el-option label="完成日期" value="4"></el-option>
          <el-option label="流程状态" value="5"></el-option>
          <el-option label="召回状态" value="6"></el-option>
          <el-option label="当前审批信息" value="7"></el-option>
          <el-option label="流程ID" value="8"></el-option>
        </el-select>
        <el-button slot="append" size="mini" icon="el-icon-search"></el-button>
      </el-input>
    </template> -->
  </listV2>
</template>
<script>
export default {
  mounted: function() {
    this.setDocumentTitle(this, "流程管理系统", "100%");
    var self = this;
    self.get({
      url: "/process/alllist",
      success: function(r) {
        if (r.code == 200) {
          var data = [];
          for (var i = 0; i < r.data.length; i++) {
            data.push({ id: r.data[i].id, value: r.data[i].name });
          }
          self.cfg.columns[1].data = data;
        }
      }
    });
  },
  data() {
    var self = this;
    return {
      inputSearch: "",
      selectSearch: "vertical",
      processCategory: [],
      cfg: {
        searchMode:"vertical",
        title: "我发起的任务",
        url: "/process/launchedList",
        columns: [
          {
            title: "状态",
            name: "status",
            isSearch: true,
            type:"combox",
            data:[{
              id:1,
              value:"全部"
            },{
              id:2,
              value:"正常"
            },{
              id:3,
              value:"催办"
            }]
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
            type:"timer"
          },
           {
            title: "完成日期",
            name: "updateTime",
            isSearch: true,
             type:"timer"
          },
           {
            title: "召回状态",
            name: "zhaohuistatus",
            isSearch: true,
          },
          {
            title: "流程状态",
            name: "processstatus",
            isSearch: true,
          },
          {
            title: "当前审批信息",
            name: "createName",
            isSearch: true,
          },
          {
            title: "流程ID",
            name: "Id",
            isSearch: true,
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
.el-select .el-input {
  width: 130px;
}
.input-with-select .el-input-group__prepend {
  background-color: #fff;
}

</style>
