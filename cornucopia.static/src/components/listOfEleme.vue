<template>
  <div>
    <div class="box box-info" :style="this.cfg.boxStyle?this.cfg.boxStyle:''">
      <div v-if="this.cfg.title" class="box-header">
        <div v-if="this.cfg.title" class="box-header">
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item v-if="this.cfg.parentTitle">
              <i class="fa fa-dashboard"></i> 首页
            </el-breadcrumb-item>
            <el-breadcrumb-item v-if="this.cfg.parentTitle">{{this.cfg.parentTitle}}</el-breadcrumb-item>
            <el-breadcrumb-item>{{this.cfg.title}}</el-breadcrumb-item>
          </el-breadcrumb>
          <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse">
              <i class="fa fa-minus"></i>
            </button>
            <!-- <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button> -->
          </div>
          <hr v-if="tableData.length==0">
        </div>
      </div>
      <el-table
        :data="tableData"
        style="width: 100%"
        :height="this.cfg.height||'500'"
        :fixed="this.cfg.fixed||false"
        border
        stripe
      >
        <el-table-column
          v-for="column in this.cfg.columns"
          :key="column.name"
          :prop="column.name"
          :label="column.title"
          :width="column.width||''"
          :fixed="column.fixed||false"
        ></el-table-column>
      </el-table>
    </div>
    <el-dialog
      append-to-body
      :visible.sync="dialogVisible"
      :width="this.cfg.dialogWidth?this.cfg.dialogWidth:'65%'"
    >
      <component style="margin-top:-40px;margin-bottom:-40px;" v-bind:is="currentComponent"></component>
    </el-dialog>
  </div>
</template>

<script>
export default {
  props: ["cfg"],
  created: function() {
    self = this;
    if (self.cfg.tools) {
      for (var i = 0; i < self.cfg.tools.length; i++) {
        var item = self.cfg.tools[i];
        self.$set(self.toolData, item.name, "");
      }
    }
  },
  mounted: function() {
    var self = this;
    if (self.cfg.url) {
      var listUrl = self.cfg.url;
      self.post({
        url: listUrl,
        data: {
          iDisplayStart: 0,
          iDisplayLength: 10
        },
        success: function(response) {
          self.tableData = response.aaData;
        }
      });
    }
  },
  data() {
    var self = this;
    return {
      dialogVisible: false,
      currentComponent: "",
      tableData: []
    };
  },
  methods: {
    formatter(row, column) {
      return row.address;
    }
  }
};
</script>
<style>
</style>
