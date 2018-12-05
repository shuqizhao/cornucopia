<template>
  <div class="box box-info" :style="this.cfg.boxStyle?this.cfg.boxStyle:''">
    <div class="box-header">
      <div v-if="this.cfg.title">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item v-if="this.cfg.parentTitle">
            <i class="fa fa-dashboard"></i> 首页
          </el-breadcrumb-item>
          <el-breadcrumb-item v-if="this.cfg.parentTitle">{{this.cfg.parentTitle}}</el-breadcrumb-item>
          <el-breadcrumb-item>{{this.cfg.title}}</el-breadcrumb-item>
        </el-breadcrumb>
        <hr>
        <div>
          <button
            v-for="item in cfg.functions"
            :key="item.text"
            @click="item.onClick"
            :class="'btn '+item.type+' btn-buttons '+item.icon"
            style="margin-right:10px;"
          >{{item.text}}</button>
        </div>
      </div>
      <el-row v-if="this.cfg.filterType=='combox'">
        <el-col :span="12">
          <el-select
            v-if="this.cfg.option1Url"
            v-model="value1"
            @change="option1Change"
            filterable
            placeholder="请选择"
          >
            <el-option v-for="item in options1" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-col>
        <el-col :span="12">
          <el-select
            v-if="this.cfg.option2Url"
            v-model="value2"
            @change="option2Change"
            filterable
            placeholder="请选择"
          >
            <el-option v-for="item in options2" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-col>
      </el-row>
      <el-input v-else placeholder="输入关键字进行过滤" v-model="filterText"></el-input>
      <div class="box-tools pull-right">
        <!-- <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
        </button>-->
        <!-- <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button> -->
      </div>
    </div>
    <div class="box-body">
      <el-tree
        class="filter-tree"
        node-key="id"
        :data="data2"
        :highlight-current="true"
        :check-on-click-node="true"
        :props="defaultProps"
        :check-strictly="true"
        :indent="30"
        :default-expand-all="false"
        :show-checkbox="!this.cfg.hideCheckBox"
        :filter-node-method="filterNode"
        :style="this.cfg.style||''"
        @node-click="onNodeClick"
        ref="tree2"
      >
        <span class="custom-tree-node" slot-scope="{ node, data }">
          <span>
            <i :class="'fa '+data.icon"></i>
            {{ node.label }}
          </span>
        </span>
      </el-tree>
    </div>

    <el-dialog :visible.sync="dialogVisible" :width="this.cfg.dialogWidth">
      <component
        :isDialog="true"
        style="margin-top:-40px;margin-bottom:-40px;"
        v-bind:is="currentComponent"
      ></component>
    </el-dialog>
  </div>
</template>
<script>
export default {
  props: ["cfg"],
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val);
    }
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    loadUrl: function(type, id, handler) {
      var self = this;
      if (type == 0 && (self.cfg.filterType == "combox" || !self.cfg.url)) {
        return;
      }
      var url = self.cfg.url;
      if (id || id == 0) {
        url += id;
        self.currentId = id;
      }
      if (type == 2) {
        url += self.currentId;
      }
      self.openLoading();
      self.get({
        url: url,
        success: function(response) {
          if (response.code == "200") {
            self.data2 = response.data;
            self.closeLoading();
          } else if (response.message) {
            self.$message({
              type: "warning",
              message: response.message
            });
          }
          if (self.cfg.onSuccess) {
            if (self.cfg.onSuccess(self.cfg.mode, response)) {
              if (handler) {
                handler(response);
              } else {
                // history.go(-1);
              }
            }
          } else {
            if (handler) {
              handler(response);
            } else {
              //   history.go(-1);
            }
          }
        }
      });
    },
    loadOption1Url: function(handler) {
      var self = this;
      if (!self.cfg.option1Url) {
        return;
      }
      // self.openLoading();
      self.get({
        url: self.cfg.option1Url,
        success: function(response) {
          if (response.code == "200") {
            self.options1 = response.data;
            // self.closeLoading();
          } else if (response.message) {
            self.$message({
              type: "warning",
              message: response.message
            });
          }
          if (self.cfg.onSuccess) {
            if (self.cfg.onSuccess(self.cfg.mode, response)) {
              if (handler) {
                handler(response);
              } else {
                // history.go(-1);
              }
            }
          } else {
            if (handler) {
              handler(response);
            } else {
              //   history.go(-1);
            }
          }
        }
      });
    },
    loadOption2Url: function(id) {
      var self = this;
      if (!self.cfg.option2Url) {
        return;
      }
      // self.openLoading();
      self.get({
        url: self.cfg.option2Url + id,
        success: function(response) {
          if (response.code == "200") {
            self.options2 = response.data;
            self.value2 = "";
            // self.closeLoading();
          } else if (response.message) {
            self.$message({
              type: "warning",
              message: response.message
            });
          }
        }
      });
    },
    option1Change: function(s1) {
      this.loadOption2Url(s1);

      if (this.cfg.option1Change) {
        this.cfg.option1Change(s1);
      }
    },
    option2Change: function(s1) {
      this.loadUrl(1, s1);

      if (this.cfg.option2Change) {
        this.cfg.option2Change(s1);
      }
    },
    setCheckedKeys: function(checkList) {
      var self = this;
      self.$refs.tree2.setCheckedKeys(checkList);
    },
    getCheckedKeys: function() {
      var self = this;
      var checkedList = self.$refs.tree2.getCheckedKeys();
      if (checkedList.length == 0) {
        return [0];
      }
      return checkedList;
    },
    getCurrentKey: function() {
      var self = this;
      var checkedList = self.$refs.tree2.getCurrentKey();
      return checkedList;
    },
    checkAll: function() {
      var self = this;
      self.$refs.tree2.setCheckedNodes(self.data2);
    },
    clearAll: function() {
      var self = this;
      self.$refs.tree2.setCheckedKeys([0]);
    },
    onNodeClick: function(data) {
      if (this.cfg.onNodeClick) {
        this.cfg.onNodeClick(data);
      }
    },
    clearOption1: function() {
      this.options1 = [];
    },
    clearOption2: function() {
      this.options2 = [];
    },
    clearData: function() {
      this.data2 = [];
    }
  },

  data() {
    return {
      filterText: "",
      data2: [],
      defaultProps: {
        children: "children",
        label: "name"
      },
      value1: "",
      value2: "",
      options1: [],
      options2: [],
      dialogVisible: false,
      currentComponent: "",
      currentId: 0
    };
  },
  mounted: function() {
    var self = this;
    this.cfg.functions = this.cfg.functions || [];
    if (!this.cfg.hideToolBar) {
      this.cfg.functions.push({
        text: "全选",
        type: "btn-success",
        icon: "el-icon-circle-check-outline",
        onClick: function() {
          self.checkAll();
        }
      });
      this.cfg.functions.push({
        text: "清空",
        type: "btn-success",
        icon: "el-icon-circle-close-outline",
        onClick: function() {
          self.clearAll();
        }
      });
    }

    this.loadUrl(0);
    this.loadOption1Url();
  }
};
</script>
<style>
</style>
