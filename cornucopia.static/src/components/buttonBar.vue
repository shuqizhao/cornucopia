<template>
  <div class="box" :style="this.cfg.boxStyle?this.cfg.boxStyle:''">
    <center>
      <el-button
        type="primary"
        v-show="!cfg.hideSave"
        @click="btnPost({name:cfg.saveButtonTitle||'保存',url:cfg.save})"
      >{{cfg.saveButtonTitle||'保存'}}</el-button>
      <span v-for="item in this.cfg.buttons" :key="item.name" style="margin-right:5px;">
        <el-button
          v-show="!(item.hidden||false)"
          :type="item.type||'primary'"
          @click="btnPost(item)"
        >{{item.name}}</el-button>
      </span>
      <el-button v-show="!cfg.hideCancel" type="warning" @click="btnCancel">取消</el-button>
    </center>
  </div>
</template>
<script>
// import {json2xml} from "../ref/json2xml"
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
    btnPost: function(item) {
      var self = this;
      if (item.onClick && item.hideTipMsg) {
        item.onClick(item);
      } else if (item.onClick) {
        self
          .$confirm("确定要" + item.name + "吗", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          })
          .then(() => {
            item.onClick(item);
          })
          .catch((e) => {
            console.log(e);
          });
      } else {
        self.btnSave(item);
      }
    },
    getLocalBizData(item){
      var self = this;
      self.currentAction = item.name;
      var dataWillCommit = {};
      var children = this.$parent.$children;
      var willCommit = true;
      for (var i = 0; i < children.length; i++) {
        if (children[i].validateFrom) {
          var isPass = children[i].validateFrom(
            true,
            function(name, data) {
              dataWillCommit[name] = data;
            },
            item,
            willCommit
          );
          willCommit = isPass && willCommit;
        }
        var childOfLevel02 = children[i].$children;
        for (var j = 0; j < childOfLevel02.length; j++) {
          if (childOfLevel02[j].validateFrom) {
            var isPass = childOfLevel02[j].validateFrom(
              true,
              function(name, data) {
                dataWillCommit[name] = data;
              },
              item,
              willCommit
            );
            willCommit = isPass && willCommit;
          }

          var childOfLevel03 = childOfLevel02[j].$children;
          for (var k = 0; k < childOfLevel03.length; k++) {
            if (childOfLevel03[k].validateFrom) {
              var isPass = childOfLevel03[k].validateFrom(
                true,
                function(name, data) {
                  dataWillCommit[name] = data;
                },
                item,
                willCommit
              );
              willCommit = isPass && willCommit;
            }
          }
        }
      }

      if (!willCommit) {
        return;
      }

      if (self.cfg.getExtraData) {
        dataWillCommit = $.extend(
          true,
          dataWillCommit,
          self.cfg.getExtraData()
        );
      }

      var isOk = true;
      if (self.cfg.validate) {
        //自定义验证
        isOk = self.cfg.validate(dataWillCommit);
      }

      if (!isOk) {
        return;
      }
      if (self.cfg.beforeCommit) {
        self.cfg.beforeCommit(dataWillCommit);
      }
      if (self.cfg.getExtraDataSkipValidate) {
        dataWillCommit = $.extend(
          true,
          dataWillCommit,
          self.cfg.getExtraDataSkipValidate()
        );
      }
      return dataWillCommit;
    },
    btnSave: function(item) {
      var self = this;
      // self.currentAction = item.name;
      var dataWillCommit = this.getLocalBizData(item);
      self
        .$confirm("确定要" + item.name + "吗", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
        .then(() => {
          self.postUrl(dataWillCommit, null, item);
        })
        .catch(err => {
          console.log(err);
        });
    },
    postUrl: function(data, handler, item) {
      var self = this;
      self.openLoading(self.$parent, null, "正在提交...", 15000);
      if (self.cfg.dataType == "xml" && item && item.dataType != "json") {
        data = { xmlStr: "", jsonStr: JSON.stringify(data) };
      }
      self.post({
        url: item ? item.url : self.cfg.save,
        data: data,
        dataType: "json",
        success: function(response) {
          self.closeLoading(self.$parent);
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

            if (item && item.onSuccess) {
              item.onSuccess(item);
              return;
            }
          } else if (response.code == "500" && response.message) {
            // $(self.$el)
            //   .find(".btn-commit")
            //   .removeAttr("disabled");
            self.$message({
              type: "warning",
              message: response.message
            });
            return;
          } else {
            return;
          }
          if (self.cfg.onSuccess) {
            if (self.cfg.onSuccess(self.cfg.mode, response)) {
              if (handler) {
                handler(response);
              } else {
                if (self.showDialog()) {
                  self.$parent.$parent.$parent.dialogVisible = false;
                  self.$parent.$parent.$parent.currentComponent = "";
                  if (self.$parent.$parent.$parent.reloadSimpleData) {
                    self.$parent.$parent.$parent.reloadSimpleData();
                  } else if (self.$parent.$parent.$parent.loadUrl) {
                    self.$parent.$parent.$parent.loadUrl(2);
                  }
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
                if (self.$parent.$parent.$parent.reloadSimpleData) {
                  self.$parent.$parent.$parent.reloadSimpleData();
                } else if (self.$parent.$parent.$parent.loadUrl) {
                  self.$parent.$parent.$parent.loadUrl(2);
                }
                return;
              } else {
                history.go(-1);
              }
            }
          }
        }
      });
    }
    // parse2xml: function(data) {
    //   var xmldata = "";
    //   for (var i in data) {
    //     if (!Array.isArray(data[i])) {
    //       xmldata += "<" + i + ">";
    //     }
    //     if (Array.isArray(data[i])) {
    //       for (var j = 0; j < data[i].length; j++) {
    //         xmldata += "<" + i + ">";
    //         xmldata += this.parse2xml(data[i][j]);
    //         xmldata += "</" + i + ">";
    //       }
    //     } else if (typeof data[i] == "object") {
    //       xmldata += this.parse2xml(data[i]);
    //     } else {
    //       xmldata += data[i];
    //     }
    //     if (!Array.isArray(data[i])) {
    //       xmldata += "</" + i + ">";
    //     }
    //   }
    //   return xmldata.replace(/undefined/g, '');
    // },
    // parse2json: function(xmlStr) {
    //   var root = document.createElement("XMLROOT");
    //   root.innerHTML = xmlStr;
    //   return this.parse(root);
    // },
    // parse: function(node) {
    //   var result = {};
    //   for (var i = 0; i < node.childNodes.length; ++i) {
    //     if (node.childNodes[i].nodeType == 1) {
    //       result[node.childNodes[i].nodeName.toLowerCase()] = this.parse(
    //         node.childNodes[i]
    //       );
    //     } else if (node.childNodes[i].nodeType == 3) {
    //       return node.childNodes[i].nodeValue;
    //     }
    //   }
    //   return result;
    // }
  },
  data() {
    return {
      currentAction: ""
    };
  }
};
</script>
