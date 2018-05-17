<template>
<div class="box" :style="this.cfg.boxStyle?this.cfg.boxStyle:''">
<div class="box-header">
  <div v-if="this.cfg.title">
        <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item><i class="fa fa-dashboard"></i> 首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{this.cfg.parentTitle}}</el-breadcrumb-item>
        <el-breadcrumb-item>{{this.cfg.title}}</el-breadcrumb-item>
        </el-breadcrumb>
        <hr/>
  <div>
    <button v-for="item in cfg.functions" :key="item.text" @click="item.onClick" :class="'btn '+item.type+' btn-buttons '+item.icon" style="margin-right:10px;">{{item.text}}</button>
  </div>
  </div>

  <div class="box-tools pull-right">
    <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
    </button>
    <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
  </div>
</div>
  <div class="box-body">
    <el-input placeholder="输入关键字进行过滤" v-model="filterText"></el-input>

        <el-tree
        class="filter-tree"
         node-key="id"
        :data="data2"
        :props="defaultProps"
        check-strictly="true"
        indent="24"
        default-expand-all
        show-checkbox
        :filter-node-method="filterNode"
        ref="tree2">

          <span class="custom-tree-node" slot-scope="{ node, data }">
          
          <span><i :class="'fa '+data.icon"></i> {{ node.label }}</span>
      </span>

        </el-tree>
  </div>
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
    loadUrl: function(handler) {
      var self = this;
      self.openLoading();
      $.ajax({
        type: "GET",
        xhrFields: {
          withCredentials: true
        },
        url: self.cfg.url,
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
    setCheckedKeys:function(checkList){
      var self = this;
      self.$refs.tree2.setCheckedKeys(checkList);
    }
    ,
    getCheckedKeys:function(){
      var self = this;
      var checkedList = self.$refs.tree2.getCheckedKeys()
      if(checkedList.length==0){
        return [0];
      }
      return checkedList;
    },
    checkAll:function(){
      var self = this;
      self.$refs.tree2.setCheckedNodes(self.data2);
    },
    clearAll:function(){
      var self = this;
      self.$refs.tree2.setCheckedKeys([0]);
    }
  },

  data() {
    return {
      filterText: "",
      data2: [],
      defaultProps: {
        children: "children",
        label: "name"
      }
    };
  },
  mounted: function() {
    var self = this;
    this.cfg.functions = this.cfg.functions || []
    this.cfg.functions.push({
              text: "全选",
              type: "btn-success",
              icon:'el-icon-circle-check-outline',
              onClick:function(){
                self.checkAll();
              }
            });
    this.cfg.functions.push({
              text: "清空",
              type: "btn-success",
              icon:'el-icon-circle-close-outline',
              onClick:function(){
                self.clearAll();
              }
            });
    this.loadUrl();
  }
};
</script>
<style>
.el-tree {
  font-size: 24;
}
</style>
