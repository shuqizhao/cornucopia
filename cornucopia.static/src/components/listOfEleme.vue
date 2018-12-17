<template>
  <div>
    <div class="box box-info" :style="this.cfg.boxStyle?this.cfg.boxStyle:''">
      <!-- <div v-if="this.cfg.title" class="box-header"> -->
      <div class="box-header" v-if="this.searchColumns.length!=0||this.cfg.title">
        <slot name="header">
          <el-breadcrumb v-if="this.cfg.title" separator-class="el-icon-arrow-right">
            <el-breadcrumb-item v-if="this.cfg.parentTitle">
              <i class="fa fa-dashboard"></i> 首页
            </el-breadcrumb-item>
            <el-breadcrumb-item v-if="this.cfg.parentTitle">{{this.cfg.parentTitle}}</el-breadcrumb-item>
            <el-breadcrumb-item>{{this.cfg.title}}</el-breadcrumb-item>
          </el-breadcrumb>
          <div v-if="this.cfg.title" class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" data-widget="collapse">
              <i class="fa fa-minus"></i>
            </button>
            <!-- <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button> -->
          </div>
        </slot>
        <hr v-if="this.cfg.title">
        <slot>
          <el-form
            :inline="true"
            :size="this.cfg.size||'mini'"
            :model="formInline"
            ref="myForm"
            class="demo-form-inline"
          >
            <template v-if="this.cfg.searchMode=='vertical'">
              <el-form-item>
                <el-select v-model="formInline['columnName']" @change="onVerticalSelectChange">
                  <el-option
                    v-for="column in this.searchColumns"
                    :key="column.name"
                    :label="column.title"
                    :value="column.name"
                  ></el-option>
                </el-select>
              </el-form-item>

              <el-form-item>
                <template v-for="column in this.searchColumns">
                  <el-select
                    v-if="column.type=='combox'"
                    v-show="formInline['columnName']==column.name"
                    :key="column.name"
                    v-model="formInline[column.name]"
                  >
                    <el-option
                      v-for="item in column.data"
                      :key="item.id"
                      :label="item.value"
                      :value="item.id"
                    ></el-option>
                  </el-select>
                  <el-date-picker
                    v-else-if="column.type=='timer'"
                    v-show="formInline['columnName']==column.name"
                    :key="column.name"
                    v-model="formInline[column.name]"
                    type="daterange"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                  ></el-date-picker>
                  <el-input
                    v-else
                    v-show="formInline['columnName']==column.name"
                    :key="column.name"
                    v-model="formInline[column.name]"
                    size="mini"
                    placeholder="查询条件"
                  ></el-input>
                </template>
              </el-form-item>
            </template>

            <template v-else v-for="column in this.searchColumns">
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
            <el-form-item v-if="this.searchColumns.length!=0">
              <el-button type="primary" @click="onSearch">查询</el-button>
              <el-button type="primary" @click="onReset">重置</el-button>
            </el-form-item>
            <el-form-item v-if="this.cfg.functions">
              <el-button-group>
                <template v-for="c in this.cfg.functions.common">
                  <el-button
                    v-if="showFunction(c.functionName)"
                    :key="c.text"
                    size="mini"
                    type="primary"
                    style="margin-right:10px;"
                    :icon="c.icon||getButtonIcon(c.functionName)"
                    :url="c.url"
                    :mode="c.mode"
                    @click="onButtonClick(c,$event)"
                  >{{c.text}}</el-button>
                </template>
              </el-button-group>
              <el-dropdown
                v-if="this.cfg.functions&&this.cfg.functions.more"
                @command="onButtonClick"
                split-button
                trigger="click"
                type="primary"
              >更多操作
                <el-dropdown-menu slot="dropdown">
                  <template v-for="m in this.cfg.functions.more">
                    <el-dropdown-item
                      v-if="showFunction(m.functionName)"
                      :key="m.text"
                      :icon="m.icon||getButtonIcon(m.functionName)"
                      :command="m"
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
        @selection-change="handleSelectionChange"
        :size="this.cfg.size||'mini'"
        :style="this.cfg.style||'width: 100%'"
        :height="this.cfg.height||'340'"
        :fixed="this.cfg.fixed||false"
        :border="this.cfg.border||true"
        :stripe="this.cfg.stripe||true"
        :fit="this.cfg.fit||true"
        ref="myListOfEleme"
      >
        <el-table-column
          v-if="this.cfg.functions|| this.cfg.showCheckBox"
          type="selection"
          width="55"
        ></el-table-column>
        <el-table-column v-if="this.cfg.showRadio" label="#" width="35">
          <template slot-scope="scope">
            <el-radio
              v-model="radio"
              :label="scope.row.id"
              @change.native="handleRadioSelectionChange(scope.row)"
            ></el-radio>
          </template>
        </el-table-column>
        <el-table-column v-if="cfg.operations" label="操作" :fixed="true">
          <template slot-scope="scope">
            <el-button
              v-for="o in cfg.operations"
              v-if="showFunction(o.functionName)"
              size="mini"
              :type="o.type||''"
              :key="o.text"
              @click="handleOperation(o,scope.$index, scope.row)"
            >{{o.text}}</el-button>
          </template>
        </el-table-column>
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
        <slot name="column"></slot>
      </el-table>
      <el-pagination
        size="mini"
        v-if="this.cfg.showPagination||true"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10,20,50,100, 200, 300, 400]"
        :page-size="currentSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalCount"
        :pager-count="this.cfg.pagerCount||5"
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
    for (var i = 0; i < this.cfg.columns.length; i++) {
      if (this.cfg.columns[i].isSearch) {
        this.searchColumns.push(this.cfg.columns[i]);
      }
    }
    var isAutoLoad = true;
    if (typeof this.cfg.autoLoad == "undefined") {
      isAutoLoad = true;
    } else {
      isAutoLoad = this.cfg.autoLoad;
    }
    if (isAutoLoad) {
      this.fillData();
    }
  },
  data() {
    var self = this;
    return {
      radio: "",
      searchColumns: [],
      formInline: {},
      dialogVisible: false,
      currentComponent: "",
      tableData: [],
      multipleSelection: [],
      currentPage: 1,
      currentSize: 10,
      totalCount: 0
    };
  },
  methods: {
    onReset(){
      this.formInline={};
      this.$refs.myForm.resetFields();
    },
    onSearch() {
      this.fillData();
    },
    handleSizeChange(val) {
      this.currentSize = val;
      this.fillData();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.fillData();
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      if (this.cfg.onRowSelected) {
        this.cfg.onRowSelected(val);
      }
    },
    handleRadioSelectionChange(val) {
      this.multipleSelection = [];
      this.multipleSelection.push(val);
      if (this.cfg.onRowSelected) {
        this.cfg.onRowSelected(this.multipleSelection);
      }
    },
    fillData(p) {
      var self = this;
      if (self.cfg.url) {
        self.openLoading();
        var listUrl = self.cfg.url;
        if (self.cfg.searchMode == "vertical") {
          self.formInline["columnValue"] =
            self.formInline[self.formInline["columnName"]];
        }
        self.post({
          url: listUrl,
          data: {
            iDisplayStart: (self.currentPage - 1) * self.currentSize,
            iDisplayLength: self.currentSize,
            sSearch: JSON.stringify(
              $.extend(
                self.formInline,
                p,
                self.cfg.beforeFillData(self.formInline)
              )
            )
          },
          success: function(response) {
            self.tableData = response.aaData;
            self.totalCount = response.iTotalDisplayRecords;
            self.closeLoading();
          }
        });
      }
    },
    onButtonClick(c, e) {
      var self = this;
      if (c.mode == "navigate") {
        self.$router.push({ path: c.url });
        return;
      } else if (c.mode == "modal") {
        self.currentComponent = c.url;
        self.dialogVisible = true;
        return;
      }
      var tipMsg = $(e.currentTarget || e.$el).text();
      var checks = self.multipleSelection;
      if (c.mode != "skipcheck" && checks.length == 0) {
        self.$message({
          message: "请选择一条记录",
          type: "warning"
        });
      } else {
        var newTipMsg = "是否继续" + tipMsg + "?";
        var tips = c.tips;
        if (!tips) {
          tips = newTipMsg;
        }
        self
          .$confirm(tips, "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "info"
          })
          .then(() => {
            var formData = [];
            for (var i = 0; i < checks.length; i++) {
              formData.push(checks[i][self.cfg.idName || "id"]);
            }
            // checks.each(function() {
            //   var idValue = $(this).val();
            //   debugger;
            //   if (idValue != "on") {
            //     formData.push($(this).val());
            //   }
            // });
            // if(self.currentId){
            //   formData.push(self.currentId)
            // }
            if (c.mode == "download") {
              if (c.limitSelected) {
                if (c.limitSelected < formData.length) {
                  $.fn.message({
                    msg: "只能下载" + c.limitSelected + "个对象！",
                    type: "warning"
                  });
                  return;
                }
              }
              var form = $("<form>");
              form.attr("style", "display:none");
              form.attr("target", "");
              form.attr("method", "post");
              form.attr("action", c.url);
              for (var i = 0; i < formData.length; i++) {
                var input1 = $("<input>");
                input1.attr("type", "hidden");
                input1.attr("name", "ids");
                input1.attr("value", formData[i]);
                $("body").append(form);
                form.append(input1);
              }
              form.submit();
            } else {
              debugger;

              self.post({
                url: c.url,
                traditional: true,
                // dataType:'json',
                data: { Ids: formData },
                success: function(response) {
                  if (c.onSuccess) {
                    c.onSuccess(response);
                  }
                  if (response.code == 200) {
                    // self.reloadSimpleData();
                    // self.dataTable.draw(false);
                    self.fillData();
                    self.$refs.myListOfEleme.doLayout();
                    self.$message({
                      message: "操作成功!",
                      type: "success"
                    });
                    // self.currentId = "";
                  } else {
                    self.$message({
                      message: response.message + "！",
                      type: "error"
                    });
                  }
                }
              });
            }
          })
          .catch(e => {
            // self.$message({
            //   message: e + "！",
            //   type: "error"
            // });
            console.log(e);
          });
      }
      console.log(e.currentTarget || e.$el);
    },
    handleOperation(op, index, row) {
      var idName = (op.idName ? op.idName : this.cfg.idName) || "id";
      this.$router.push({ path: op.url + "?" + idName + "=" + row[idName] });
    },
    toggleRowSelection(r) {
      this.$refs.myListOfEleme.toggleRowSelection(r);
    },
    onVerticalSelectChange(v) {
      this.formInline = {};
      this.formInline["columnName"] = v;
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
hr {
  margin: 10px;
}
</style>
