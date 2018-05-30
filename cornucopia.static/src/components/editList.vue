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
        <el-button @click="setCurrent(tableData[1])">新增</el-button>
        <el-button @click="setCurrent()">编辑</el-button>
    </div>
  <el-table
    :data="tableData"
    style="width: 100%">
    <el-table-column v-for="item in this.cfg.items" :key="item.name"
      :label="item.title"
     >
      <template slot-scope="scope">
        <el-input v-if="item.type=='text'" value="d" placeholder="请输入内容"></el-input>
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
    return {
      tableData: [
        {
          date: "2016-05-02",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1518 弄"
        }
      ]
    };
  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    }
  }
};
</script>