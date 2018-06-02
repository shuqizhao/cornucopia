<template>
    <div  class="box" :style="this.cfg.boxStyle?this.cfg.boxStyle:''">
        <center>
        <el-button
        type="primary"
          @click="btnSave">保存</el-button>
        <el-button
          type="warning"
          @click="btnCancel">取消</el-button>
        </center>
    </div>
</template>
<script>
export default {
  props: ["cfg"],
  methods: {
    showDialog: function() {
      var className = this.$parent.$parent.$el.className;
      return className.indexOf("el-dialog__wrapper") != -1;
    },
    btnCancel: function() {
      var self = this;
      self.commiting = false;
      if (self.cfg.onCancel) {
        self.cfg.onCancel();
      } else if (self.showDialog()) {
        self.$parent.$parent.$parent.dialogVisible = false;
        self.$parent.$parent.$parent.currentComponent = "";
      } else {
        //$.fn.navigate();
        history.go(-1);
      }
    },
    btnSave: function() {
      var children = this.$parent.$children
      for(var i = 0;i < children.length;i++){
        if(children[i].validateFrom){
          children[i].validateFrom(function(data){
            
          });
        }
      }
    }
  },
  data() {
    return {
      message: "fds"
    };
  }
};
</script>
