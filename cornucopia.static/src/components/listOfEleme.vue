<template>
  <div>
    <div class="box box-info" :style="this.cfg.boxStyle?this.cfg.boxStyle:''">
      <!-- <div v-if="this.cfg.title" class="box-header"> -->
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
         <hr/>
        <slot>
          <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item label="审批人">
              <el-input v-model="formInline.user" placeholder="审批人"></el-input>
            </el-form-item>
            <el-form-item label="活动区域">
              <el-select v-model="formInline.region" placeholder="活动区域">
                <el-option label="区域一" value="shanghai"></el-option>
                <el-option label="区域二" value="beijing"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">查询</el-button>
            </el-form-item>
          </el-form>
        </slot>
      </div>
      <!-- </div> -->
      <el-table
        :data="tableData"
        style="width: 100%"
        :height="this.cfg.height||'500'"
        :fixed="this.cfg.fixed||false"
        border
        stripe
      >
        <template v-for="column in this.cfg.columns">
          <el-table-column
            :key="column.name"
            :prop="column.name"
            :label="column.title"
            :width="column.width||''"
            :fixed="column.fixed||false"
          >
            <span
              slot-scope="scope"
              v-html="column.formatter?column.formatter(scope.row):scope.row[column.name]"
            ></span>
          </el-table-column>
        </template>
      </el-table>
      <el-pagination
        v-if="this.cfg.showPagination||true"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10,20,50,100, 200, 300, 400]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalCount"
      ></el-pagination>
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
    this.fillData();
  },
  data() {
    var self = this;
    return {
      formInline: {
        user: "",
        region: ""
      },
      dialogVisible: false,
      currentComponent: "",
      tableData: [],
      currentPage: 1,
      totalCount: 0
    };
  },
  methods: {
    onSubmit() {
      console.log("submit!");
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    fillData() {
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
            self.totalCount = response.iTotalDisplayRecords;
          }
        });
      }
    }
  }
};
</script>
<style>
</style>
