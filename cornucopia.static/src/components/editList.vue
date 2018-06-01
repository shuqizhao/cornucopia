<template>
<div>
  <div class="box box-info" :style="this.cfg.boxStyle?this.cfg.boxStyle:''">
  
      <div  class="box-header">
        <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item v-if="this.cfg.parentTitle"><i class="fa fa-dashboard"></i> 首页</el-breadcrumb-item>
        <el-breadcrumb-item v-if="this.cfg.parentTitle">{{this.cfg.parentTitle}}</el-breadcrumb-item>
        <el-breadcrumb-item> <i class="el-icon-circle-plus" />{{this.cfg.title}}</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>
          <!-- <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button> -->
        </div>
        <hr/>
      </div>
    <div>
        <button v-for="item in cfg.functions" :key="item.text" @click="item.onClick" :class="'btn '+item.type+' btn-buttons '+item.icon" style="margin-right:10px;">{{item.text}}</button>
    </div>
  <el-table
    :data="tableData"
    border
    :show-overflow-tooltip="true"
    @selection-change="handleSelectionChange"
    style="width: 100%">
    <el-table-column
      type="selection"
      width="55">
    </el-table-column>
    <el-table-column v-for="item in this.cfg.items" :key="item.name"
      :label="item.title"
     >
      <template slot-scope="scope">
        <el-input v-if="item.type=='text'" :value="tableData[scope.$index][item.name]" placeholder=""></el-input>
         <el-select v-else-if="item.type=='combox'" v-model="tableData[scope.$index][item.name]" placeholder="">
            <el-option
            v-for="opItem in item.data"
            :key="opItem.id"
            :label="opItem.name"
            :value="opItem.id">
            </el-option>
        </el-select>
      </template>
    </el-table-column>
  </el-table>
</div>
</div>
</template>

<script>
export default {
  props: ["cfg"],
  data() {
    var self = this;
    return {
      tableData: [],
      multipleSelection: []
    };
  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
    insertNew: function(data) {
      data.table_row_id = this.tableData.length;
      this.tableData.push(data);
    },
    deleteSelected: function() {
      // this.tableData.splice(index,1);
      for (var i = 0; i < this.multipleSelection.length; i++) {
        this.removeObjWithArr(this.tableData, this.multipleSelection[i]);
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    getIndexWithArr: function(_arr, _obj) {
      debugger;
      var len = _arr.length;
      for (var i = 0; i < len; i++) {
        if (_arr[i].table_row_id == _obj.table_row_id) {
          return parseInt(i);
        }
      }
      return -1;
    },
    removeObjWithArr: function(_arr, _obj) {
      var index = this.getIndexWithArr(_arr, _obj);
      _arr.splice(index-1, 1);
    }
  }
};
</script>