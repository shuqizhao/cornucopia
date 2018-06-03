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
      var dataWillCommit = {};
      var children = this.$parent.$children;
      var willCommit = true;
      for (var i = 0; i < children.length; i++) {
        if (children[i].validateFrom) {
          var isPass = children[i].validateFrom(function(name, data) {
            dataWillCommit[name] = data;
          });
          willCommit = isPass && willCommit;
        }
      }
      if (!willCommit) {
        return;
      }
      this.postUrl(dataWillCommit);
    },
    postUrl: function(data,handler) {
      var self = this;
      $.ajax({
        type: "POST",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        xhrFields: {
          withCredentials: true
        },
        url: self.cfg.save,
        data: JSON.stringify(data),
        success: function(response) {
          if (response.code && response.code == "201") {
            window.open(response.data);
          } else if (response.code && response.code == "203") {
            alert("查看console.log");
            console.log(response.data);
          }
          if (response.code == "200") {
            self.$message({
              message: "成功!",
              type: "success"
            });
          } else if (response.message) {
            $(self.$el)
              .find(".btn-commit")
              .removeAttr("disabled");
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
                if (self.showDialog()) {
                  self.$parent.$parent.$parent.dialogVisible = false;
                  self.$parent.$parent.$parent.currentComponent = "";
                  self.$parent.$parent.$parent.reloadSimpleData();
                  return;
                } else {
                  history.go(-1);
                }
              }
            }
          } else {
            if (handler) {
              handler(response);
            } else {
              if (self.showDialog()) {
                self.$parent.$parent.$parent.dialogVisible = false;
                self.$parent.$parent.$parent.currentComponent = "";
                self.$parent.$parent.$parent.reloadSimpleData();
                return;
              } else {
                history.go(-1);
              }
            }
          }
        }
      });
    }
  },
  data() {
    return {
      message: "fds"
    };
  }
};
</script>
