<template>
<div class="box">
<div class="box-header">
<h3 class="box-title">Latest Orders</h3>

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
        :data="data2"
        :props="defaultProps"
        default-expand-all
        show-checkbox
        :filter-node-method="filterNode"
        ref="tree2">
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
      $.ajax({
        type: "GET",
        xhrFields: {
          withCredentials: true
        },
        url: self.cfg.url,
        success: function(response) {
          if (response.code == "200") {
            self.data2 = response.data;
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
    }
  },

  data() {
    return {
      filterText: "",
      data2: [],
      defaultProps: {
        children: "children",
        label: "Name"
      }
    };
  },
  mounted: function() {
    this.loadUrl();
  }
};
</script>
<style>
.el-tree {
  font-size: 24;
}
</style>
