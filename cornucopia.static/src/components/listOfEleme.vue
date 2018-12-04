<template>
  <div style="margin-top:-10px;margin-bottom:-30px;">
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
        <hr>
        <slot>
          <el-form
            :inline="true"
            :size="this.cfg.size||'mini'"
            :model="formInline"
            class="demo-form-inline"
          >
            <template v-for="column in this.cfg.columns">
              <el-form-item
                v-if="column.isSearch&&column.type=='combox'"
                :key="column.name"
                :label="column.title+' :'"
              >
                <el-select v-model="formInline[column.name]">
                  <el-option
                    v-for="item in column.data"
                    :key="item.id"
                    :label="item.value"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item
                v-else-if="column.isSearch&&column.type=='timer'"
                :key="column.name"
                :label="column.title+' :'"
              >
                <el-date-picker
                style="width:220px;"
                  v-model="formInline[column.name]"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                ></el-date-picker>
              </el-form-item>
              <el-form-item
                v-else-if="column.isSearch"
                :key="column.name"
                :label="column.title+' :'"
              >
                <el-input v-model="formInline[column.name]"></el-input>
              </el-form-item>
            </template>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">查询</el-button>
              <el-button type="primary" @click="onSubmit" native-type="reset">重置</el-button>
            </el-form-item>
            <el-form-item v-if="this.cfg.functions">
              <el-button-group>
                <template v-for="c in this.cfg.functions.common">
                  <el-button
                    v-if="showFunction(c.functionName)"
                    :key="c.text"
                    size="mini"
                    type="primary"
                    :icon="c.icon"
                    @click="onButtonClick"
                  >{{c.text}}</el-button>
                </template>
              </el-button-group>
              <el-dropdown split-button type="primary">更多操作
                <el-dropdown-menu slot="dropdown">
                  <template v-for="m in this.cfg.functions.more">
                    <el-dropdown-item
                      v-if="showFunction(m.functionName)"
                      :key="m.text"
                      :command="m.text"
                      @click="onButtonClick"
                    >{{m.text}}</el-dropdown-item>
                  </template>
                </el-dropdown-menu>
              </el-dropdown>
            </el-form-item>
          </el-form>
        </slot>
      </div>
      <!-- </div> -->
      <el-table
        :data="tableData"
        :size="this.cfg.size||'mini'"
        style="width: 100%"
        :height="this.cfg.height||'340'"
        :fixed="this.cfg.fixed||false"
        border
        stripe
        :fit="this.cfg.fit||true"
      >
        <el-table-column type="selection" width="55"></el-table-column>
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
    },
    onButtonClick(e) {
      console.log(e);
    }
  }
};
</script>
<style>
label {
  display: inline-block;
  max-width: 100%;
  margin-bottom: 0px;
  font-weight: normal;
}
.el-date-editor .el-range-separator {
  width: 20px;
}
</style>
