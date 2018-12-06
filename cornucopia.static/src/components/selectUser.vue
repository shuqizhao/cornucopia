<template>
  <el-row>
    <el-col :span="5">
      <tree ref="tree" :cfg="cfg"></tree>
    </el-col>
    <el-col :span="13">
      <listV2 ref="user" :cfg="cfgUser"></listV2>
    </el-col>
    <el-col :span="6">
      <div class="box box-info" :style="this.cfg.boxStyle?this.cfg.boxStyle:''">
        <div class="box-header">已选择列表</div>
        <el-table :data="selectedTableData" style="width: 100%;" height="290px">
          <el-table-column label="姓名">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>姓名: {{ scope.row.name }}</p>
                <p>邮箱: {{ scope.row.email }}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium">{{ scope.row.name }}</el-tag>
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="danger"
                @click="selectedHandleDelete(scope.$index, scope.row)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-col>
  </el-row>
</template>
<script>
export default {
  mounted: function() {
    var self = this;
  },
  data() {
    var self = this;
    return {
      selectedTableData: [],
      cfg: {
        url: "/auth/allOrg",
        hideCheckBox: true,
        onNodeClick: function(data) {
          self.$refs.user.fillData({
            orgId: data.id
          });
        }
      },
      cfgUser: {
        autoLoad: false,
        showCheckBox: true,
        height: "270px",
        url: "/user/list",
        columns: [
          {
            title: "用户名",
            name: "name",
            isSearch: true
          },
          // {
          //   title: "工号",
          //   name: "personNumber"
          // },
          // {
          //   title: "职位",
          //   name: "jobId"
          // },
          {
            title: "工作邮箱",
            name: "email"
          },
          // {
          //   title: "所属上级",
          //   name: "managerId"
          // },
          {
            title: "所在部门",
            name: "orgId"
          }
        ],
        idName: "id",
        onRowSelected: function(data) {
          self.selectedTableData = data;
        }
      },
      id: 0,
      parainst: {}
    };
  },
  updated: function() {
    var self = this;
  },
  methods: {
    selectedHandleDelete: function(i, r) {
      var index = this.getIndexWithArr(this.selectedTableData, r);
      this.selectedTableData.splice(index, 1);
      this.$refs.user.toggleRowSelection(r);
    },
    getIndexWithArr: function(_arr, _obj) {
      var len = _arr.length;
      for (var i = 0; i < len; i++) {
        if (_arr[i] == _obj) {
          return parseInt(i);
        }
      }
      return -1;
    }
  }
};
</script>
<style>
.el-dialog--center .el-dialog__body {
  padding: 30px 20px;
  color: #606266;
  line-height: 24px;
  font-size: 14px;
}
</style>
