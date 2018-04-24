<template>
<div class="content-wrapper">
    <!-- Horizontal Form -->
    <div class="box box-info" >
        <div v-if="cfg.mode=='detailEdit'&&cfg.detailEditMode!='edit'" class="box-header with-border">
            <i class="el-icon-view" />
            <h3 class="box-title">{{cfg.detailTitle}}</h3>
            <small>{{cfg.desc}}</small>
        </div>
        <div v-else-if="cfg.mode=='detailEdit'&&cfg.detailEditMode=='edit'" class="box-header with-border">
            <i class="el-icon-edit" />
            <h3 class="box-title">{{cfg.editTitle}}</h3>
            <small>{{cfg.desc}}</small>
        </div>
        <div v-else class="box-header with-border">
            <i class="el-icon-circle-plus" />
            <h3 class="box-title">{{cfg.title}}</h3>
            <small>{{cfg.desc}}</small>
        </div>
        <!-- /.box-header -->
        <!-- form start -->
        <form class="form-inline form" onsubmit='return false;' role="form">
            <div class="box-body">
                <template v-for="item in cfg.items" >
                    <input v-if="item.type=='hidden'" :id="item.name" type="hidden" class="form-control" :value="detail[item.name]" :controltype='item.type' />
                    <br v-if="item.type=='textxml'" />
                    <br v-if="item.type=='textnginx'" />
                    <div v-if="item.type!='hidden'" class="form-group" style="margin-right:20px;width:100%">
                        <label style="width:10%;text-align: right" for="name">{{item.title}}：</label>
                        <div class="input-group" style="vertical-align: top;width:80%;display:inline-table;margin-bottom:30px;">
                            <div v-if="item.type=='baidutext'">
                                <div v-if="cfg.mode=='detailEdit'||cfg.mode=='create'" class="textarea">
                                    <hiden :id="item.name" :name="item.name" class="form-control" rows="5" :controltype='item.type'/>
                                    <div :id="item.name+1" class="baiduedit" type="text/plain" style="width:800px;height:500px;"></div> 
                                </div>
                            </div>
                            <template v-if="cfg.mode=='edit'||cfg.mode=='create'||(cfg.mode=='detailEdit'&&cfg.detailEditMode=='edit')">
                                <input v-if="item.type=='hidden'" :id="item.name" type="hidden" class="form-control" :value="detail[item.name]" :controltype='item.type' />
                                <input v-else-if="item.type=='text'" :id="item.name" :name="item.name" type="text" :placeholder="item.placeholder" class="input-xlarge form-control" :value="detail[item.name]" :controltype='item.type' />
                                <textarea v-else-if="item.type=='textarea'" :id="item.name" :name="item.name" style='width:270px;' class="form-control" rows="5" :controltype='item.type' :value="detail[item.name]"></textarea>
                                <iframe v-else-if="item.type=='textxml'" readonly='false' :id="item.name" :name="item.name"  scrolling="no" frameborder="0" class="form-control embed-responsive-item" :controltype='item.type' style="min-height:190px;" src="/src/ref/codemirror/codemirror.html"></iframe>
                                <iframe v-else-if="item.type=='textnginx'" readonly='false' :id="item.name" :name="item.name"  scrolling="no" frameborder="0" class="form-control embed-responsive-item" :controltype='item.type' style="min-height:190px;" src="/src/ref/codemirror/codemirrornginx.html"></iframe>
                                <input v-else-if="item.type=='pwd'" :id="item.name" :name="item.name" type="password" :placeholder="item.placeholder" class="input-xlarge form-control" :value="detail[item.name]" :controltype='item.type' />
                                <select v-else-if="item.type=='combox'" :id="item.name" style='width:284px;' class="input-xlarge form-control" :controltype='item.type'>
                                    <!-- <option v-for="option in item.data" v-if="option.id==detail[item.name]" selected="selected" :value="option.id">{{option.value}}</option>
                                    <option :value="option.id">{{option.value}}</option> -->
                                </select>
                                <input  v-else-if="item.type=='timer'" :id="item.name" :name="item.name" type="text" :placeholder="item.placeholder" class="form-control" :controltype='item.type' :value="detail[item.name]" />
                                <div v-else-if="item.type=='uploader'">
                                        <input :id="item.name" :name="item.name" type="text" :value="displayValue" class="form-control" :controltype='item.type' style="width: 0;height: 0;border: 0;background: transparent;" />
                                        <p :id="item.name+1" style="line-height:29px;display:inline;">
                                            <a target="_bank" :href='displayLink'>
                                                {{displayText}}
                                            </a>
                                        </p>
                                        &nbsp;&nbsp;&nbsp;
                                        <img :id="item.name+3" src="" style="display:none" />
                                        <div :id="item.name+2" style="width:400px;"></div>
                                </div>
                                <div v-else-if="item.type=='suggest'" class="input-group">
                                    <input :id="item.name" :name="item.name" type="text" style="width:230px;margin-right:0px;" class="form-control" :controltype='item.type' />
                                    <div class="input-group-btn" style="display:inline;margin-left:0px;">
                                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" style="display:inline;margin-left:0px;">
                                            <span class="caret"></span>
                                        </button>
                                        <ul :class="'dropdown-menu dropdown-menu-right dropdown-suggest-'+item.name" role="menu">
                                        </ul>
                                    </div>
                                </div>
                                <div v-else-if="item.type=='yesno'">
                                  <el-checkbox :id="item.name"  :name="item.name" :checked="detail[item.name]==1" class="form-control" :controltype='item.type' border size="medium"></el-checkbox>
                                </div>
                                <div v-else-if="item.type=='select2select'" v-bind="bindSelect2Select(item.name,item.url)">
                                    <div :id="item.name+'1'"></div>
                                    <input :id="item.name" type="hidden" :value="detail[item.name]" class="form-control" />
                                </div>
                                <div v-else-if="item.type=='tree'">
                                    <input :id="item.name" type="hidden" :value="detail[item.name]" class="form-control" />
                                    <div class="controls customTree">
                                        <table border="0">
                                            <tr>
                                                <td>ResourcesLang↓</td>
                                                <td>HaveResourcesLang↓</td>
                                            </tr>
                                            <tr>
                                                <td><ul :id="item.name+1" class="ztree"></ul></td>
                                                <td align="center"><ul :id="item.name+2" class="ztree" style="margin-right: 20px;"></ul></td>
                                            </tr>
                                        </table>
                                        <p class="help-block"></p>
                                    </div>
                                </div>
                            </template>
                            <template v-if="cfg.mode=='detail'||(cfg.mode=='detailEdit'&&cfg.detailEditMode!='edit')">
                                <input v-if="item.type=='hidden'" :id="item.name" type="hidden" class="form-control" :value="detail[item.name]" :controltype='item.type' />
                                <div v-if="item.type=='yesno'">
                                    <i v-if="detail[item.name]==1" class="fa fa-fw fa-check-circle" style="margin-top:8px;"></i>
                                    <i v-else class="el-icon-close" style="margin-top:8px;"></i>
                                </div>
                                <div v-else-if="item.type=='uploader'">
                                    <input :id="item.name" :name="item.name" type="text" :value="displayValue" class="form-control" :controltype='item.type' style="width: 0;height: 0;border: 0;background: transparent;" />
                                    <p id="<%=item.name+1%>" style="line-height:29px;display:inline;">
                                        <a target="_bank" :href='displayLink'>
                                            {{displayText}}
                                        </a>
                                    </p>
                                </div>
                                <iframe v-else-if="item.type=='textxml'" readonly='false' :id="item.name+'_readonly'" :name="item.name+'_readonly'" style='width:100%'  scrolling="no" frameborder="0" class="form-control" :controltype='item.type' src="/src/ref/codemirror/codemirror.html"></iframe>
                                <iframe v-else-if="item.type=='textnginx'" readonly='false' :id="item.name+'_readonly'" :name="item.name+'_readonly'" style='width:100%'  scrolling="no" frameborder="0" class="form-control" :controltype='item.type' src="/src/ref/codemirror/codemirrornginx.html"></iframe>
                                <ul v-else-if="item.type=='tree'" :id="item.name+1" class="ztree"></ul>
                                <!-- <div v-else-if="item.type=='select2select'" v-html="detail[item.name]" style="width:100%" /> -->
                                <input v-else-if="item.type!='hidden'" class="input-xlarge form-control" disabled='disabled' :value="detail[item.name]" style="width:100%" />
                            </template>
                        </div>
                        <!-- <span>
                                {{item.helpblock}}
                        </span> -->
                    </div> 
                </template> 
            </div>
            <!-- /.box-body -->
            <div class="box-footer navbar-fixed-bottom">
                <!-- Button -->
                <center>
                    <div v-if="cfg.mode=='edit'||cfg.mode=='create'">
                        <button @click="btnCommit($event)" class="btn btn-primary btn-commit">保存</button>
                        <button  @click="btnCancel" class="btn btn-info btn-cancel" data-dismiss="modal" aria-hidden="true">取消</button>
                        <div v-if="cfg.buttons">
                            <button v-for="item in cfg.buttons" :class="'btn '+item.type+' btn-buttons'" :name='item.name'>{{item.title}}</button>
                        </div>
                    </div>
                    <div v-if="cfg.mode=='detailEdit'">
                        <div v-if="cfg.mode=='detailEdit'&&cfg.detailEditMode!='edit'">
                            <button @click="btnEdit" class="btn btn-primary btn-edit">编辑</button>
                            <button @click="btnCancel" class="btn btn-info btn-cancel" data-dismiss="modal">返回</button>
                        </div>
                        <div v-if="cfg.mode=='detailEdit'&&cfg.detailEditMode=='edit'">
                            <button @click="btnDecommit($event)" class="btn btn-primary btn-decommit">保存</button>
                            <button @click="btnDecancel" class="btn btn-info btn-decancel" data-dismiss="modal">取消</button>
                        </div>
                    </div>
                    <div v-if="cfg.mode=='detail'">
                        <button class="btn btn-info btn-cancel" data-dismiss="modal">返回</button>
                    </div>
                </center>
            </div>
            <!-- /.box-footer -->
        </form>
    </div>
</div>
</template>
<script>
let Base64 = require("js-base64").Base64;
import "jquery-validation";
export default {
  props: ["cfg"],
  mounted: function() {
    if (this.cfg.mode != "create") {
      this.fillData();
    }
    // this.iframeLoad();
  },
  updated: function() {
    self = this;
    if (self.cfg.afterEditRender) {
      self.cfg.afterEditRender(self.cfg.detailEditMode, self.detail);
    }
    this.iframeLoad();
  },
  data() {
    return {
      detail: {},
      commiting: false
    };
  },
  methods: {
    iframeLoad: function() {
      var iframes = document.getElementsByTagName("iframe");
      for (var i = 0; i < iframes.length; i++) {
        var iframe = iframes[i];
        var readonly = false;
        if (iframe.id.indexOf("_readonly") > -1) {
          readonly = true;
          iframe.onload = function(event) {
            var aiframe = event.target;
            var readonly = false;
            if (aiframe.id.indexOf("_readonly") > -1) {
              readonly = true;
            }
            aiframe.contentWindow.setValue(
              aiframe.id,
              Base64.decode(self.detail[aiframe.id.split("_")[0]]),
              readonly
            );
          };
        } else {
          iframe.contentWindow.setValue(
            iframe.id,
            Base64.decode(self.detail[iframe.id]),
            readonly
          );
        }
      }
    },
    btnCancel: function() {
      var self = this;
      if (self.cfg.onCancel) {
        self.cfg.onCancel();
      } else if (self.modal) {
        //$('.modal-backdrop').hide();
      } else {
        //$.fn.navigate();
        history.go(-1);
      }
    },
    btnEdit: function() {
      // debugger;
      this.cfg.detailEditMode = "edit";
      this.$forceUpdate();
    },
    btnDecancel: function() {
      this.cfg.detailEditMode = "detail";
      this.$forceUpdate();
    },
    btnDecommit: function(event) {
      var target = event.currentTarget;
      self = this;
      self.commiting = true;
      self.btnCommit(target, function() {
        self.cfg.detailEditMode = "detail";
        self.commiting = false;
        self.fillData();
        self.$forceUpdate();
      });
    },
    btnCommit: function(e, handler) {
      var self = this;
      if(!handler){
        handler = function(){
          self.commiting = false;
        }
      }
      self.commiting = true;
      var validateCfg = {
        onfocusout: false,
        onclick: false,
        focusInvalid: false,
        onkeyup: false,
        onkeyup: function(element) {
          //console.log(element);
          $(element).valid();
        },
        errorPlacement: function(error, element) {
          if (element.attr("controltype") == "suggest") {
            element
              .next()
              .find("button")
              .attr("data-toggle", "tooltip");
            element
              .next()
              .find("button")
              .parent()
              .attr("data-placement", "right");
            element
              .next()
              .find("button")
              .parent()
              .attr("data-original-title", error.text());
            element
              .next()
              .find("button")
              .parent()
              .tooltip("show");

            // if (error.text()) {
            //   self.$message({
            //     type: "warning",
            //     message:
            //       element
            //         .parent()
            //         .parent()
            //         .parent()
            //         .find(".control-label")
            //         .text() +
            //       ":" +
            //       error.text()
            //   });
            // }
          } else if (element.attr("controltype") == "upload") {
            element
              .parent()
              .find("object")
              .attr("data-toggle", "tooltip");
            element
              .parent()
              .find("object")
              .parent()
              .attr("data-placement", "right");
            element
              .parent()
              .find("object")
              .parent()
              .attr("data-original-title", error.text());
            element
              .parent()
              .find("object")
              .parent()
              .tooltip("show");
          } else if (element.attr("controltype") == "html5upload") {
            element
              .parent()
              .find(".pekeupload-btn-file")
              .attr("data-toggle", "tooltip");
            element
              .parent()
              .find(".pekeupload-btn-file")
              .attr("data-placement", "right");
            element
              .parent()
              .find(".pekeupload-btn-file")
              .attr("data-original-title", error.text());
            element
              .parent()
              .find(".pekeupload-btn-file")
              .tooltip("show");
          } else {
            element.attr("data-toggle", "tooltip");
            element.attr("data-placement", "top");
            element.attr("data-original-title", error.text());
            element.tooltip("show");
            element
              .parent()
              .parent()
              .addClass("has-error");
          }

          if (element.attr("controltype") != "suggest") {
            if (self.commiting && error.text()) {
              self.$notify({
                title: "验证未通过",
                message: error.text(),
                position: "bottom-right",
                type: "warning"
              });
            }
            
            // if (error.text()) {
            //   self.$message({
            //     type: "warning",
            //     message:
            //       element
            //         .parent()
            //         .parent()
            //         .find(".control-label")
            //         .text() +
            //       ":" +
            //       error.text()
            //   });
            // }
          }

          //error.appendTo(element.parent());
        },
        success: function(error, element) {
          //console.log(error);
          //console.log(element);
          $(element).tooltip("destroy");
          $(element)
            .parent()
            .parent()
            .removeClass("has-error");
        },
        submitHandler: function(form) {
          if (!self.commiting) {
            return;
          }
          var data = self.getData();
          //console.log(data);
          var isOk = true;
          if (self.cfg.validate) {
            //自定义验证
            isOk = self.cfg.validate(data, self.saveData);
          }

          if (!isOk) {
            return;
          }
          if (self.cfg.beforeCommit) {
            self.cfg.beforeCommit(data);
          }
          $(self.$el)
            .find(".btn-commit")
            .attr("disabled", "disabled");
          self.saveData(data, handler);
        }
      };
      var lastCfg = $.extend(true, validateCfg, self.cfg);
      $(self.$el)
        .find(".form")
        .validate(lastCfg);
    },
    btnButtons: function(e) {
      var name = e.target.name;
      var buttons = this.options.cfg.buttons;
      for (var i = 0; i < buttons.length; i++) {
        if (buttons[i].name == name) {
          buttons[i].handler();
        }
      }
    },
    getData: function() {
      var self = this;
      var data = {};
      $(self.$el)
        .find(".form")
        .find(".form-control")
        .each(function(index) {
          var item = $(this);
          if (this.type == "checkbox") {
            if (item.attr("checked")) {
              data[this.id] = 1;
            } else {
              data[this.id] = 0;
            }
          } else if (item.attr("controltype") == "suggest") {
            data[this.id] = item.attr("data-id");
          } else if (item.attr("controltype") == "yesno") {
            data[this.id] = item.hasClass("is-checked") ? 1 : 0;
          } else if (item.attr("controltype") == "addline") {
            var arraytemp = [];
            item.find("input").each(function() {
              var input = $(this);
              arraytemp.push(input.val());
            });
            data[this.id] = arraytemp;
          } else if (item.attr("controltype") == "textxml") {
            data[this.id] = item[0].contentWindow.getValue();
          } else if (item.attr("controltype") == "textnginx") {
            data[this.id] = item[0].contentWindow.getValue();
          } else if (item.attr("controltype") == "baidutext") {
            data[this.id] = UE.getEditor(this.id + "1")
              .getContent()
              .replace("<video", "<video autoplay");
          } else {
            data[this.id] = item.val();
          }
        });
      return data;
    },
    saveData: function(data, handler) {
      self = this;
      var successLang = "成功!";
      if (data == null) {
        data = self.getData();
      }
      $.ajax({
        type: "POST",
        // contentType: "application/json;charset=utf-8",
        // dataType: "json",
        xhrFields: {
          withCredentials: true
        },
        url: self.cfg.save,
        data: data,
        success: function(response) {
          if (response.code && response.code == "201") {
            window.open(response.data);
          } else if (response.code && response.code == "203") {
            alert("查看console.log");
            console.log(response.data);
          }
          if (response.code == "200") {
            self.$message({
              message: successLang,
              type: "success"
            });
            //如果是模态窗体,关闭模态
            $(".aModalForm").modal("hide");
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
                history.go(-1);
              }
            }
          } else {
            if (handler) {
              handler(response);
            } else {
              history.go(-1);
            }
          }
        }
      });
    },
    fillData: function() {
      self = this;
      $.ajax({
        type: "get",
        xhrFields: {
          withCredentials: true
        },
        url: self.cfg.get.url,
        data: self.cfg.get.params,
        async: false,
        success: function(result) {
          if (result.code == "200") {
            self.detail = result.data;
          }
        }
      });
    },
    bindSelect2Select: function(id, url) {
      var self = this;
      $.ajax({
        type: "GET",
        url: url,
        success: function(data) {
          $("#" + id + "1").selectToSelect({
            size: 10,
            opSelect: data.left,
            opReSelect: data.right,
            onChange: function(options) {
              var ids = $("#" + id + "1").selectToSelect("getSelectedIds");
              $("#" + id).val(ids);
            },
            selectNothing: function() {
              self.$message({
                message: "请选择一条记录",
                type: "warning"
              });
            }
          });
        }
      });
    }
  }
};
</script>