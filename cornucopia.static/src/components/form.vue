<template>
<div>
    <!-- Horizontal Form -->
    <div class="box box-info" :style="this.cfg.boxStyle?this.cfg.boxStyle:''">
        <div class="box-header with-border">
            <i v-if="cfg.mode=='detailEdit'&&cfg.detailEditMode!='edit'" class="el-icon-view" />
            <i v-else-if="cfg.mode=='detailEdit'&&cfg.detailEditMode=='edit'" class="el-icon-edit" />
            <i v-else-if="cfg.mode=='edit'" class="el-icon-edit" />
            <i v-else class="el-icon-circle-plus" />
            <h3 v-if="cfg.mode=='detailEdit'&&cfg.detailEditMode!='edit'" class="box-title">{{cfg.detailTitle}}</h3>
            <h3 v-else-if="cfg.mode=='detailEdit'&&cfg.detailEditMode=='edit'" class="box-title">{{cfg.editTitle}}</h3>
            <h3 v-else class="box-title">{{cfg.title}}</h3>
            <small>{{cfg.desc}}</small>
            <div class="box-tools pull-right">
              <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
              </button>
              <!-- <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button> -->
            </div>
        </div>
        <!-- /.box-header -->
        <!-- form start -->
        <form class="form-inline form" onsubmit='return false;' role="form" style="text-align:center">
            <div class="box-body">
                <template v-for="item in cfg.items" >
                    <input v-if="item.type=='hidden'" :id="item.name" type="hidden" :key="item.name" class="form-control" :value="detail[item.name]" :controltype='item.type' />
                    <br v-if="item.br==true" :key="item.name"/>
                    <br v-if="item.br==true" :key="item.name"/>
                    <table v-if="item.type!='hidden'" :key="item.name" class="form-group" :style="'margin-left:0px;margin-bottom:15px;width:'+item.width" align="center">
                      <tr>
                        <td v-if="!item.hideLabel" style="text-align:right;" :width="item.lableWidth?item.lableWidth:'100px'">{{item.title}}：<label v-if="item.isRequire" style="color:red;">*</label></td>
                        <td :width="item.width?item.width:'85%'">
                            <div v-if="item.type=='baidutext'">
                                <div v-if="cfg.mode=='detailEdit'||cfg.mode=='create'" class="textarea">
                                    <hiden :id="item.name" :name="item.name" class="form-control" rows="5" :controltype='item.type'/>
                                    <div :id="item.name+1" class="baiduedit" type="text/plain" style="width:800px;height:500px;"></div> 
                                </div>
                            </div>
                            <template v-if="cfg.mode=='edit'||cfg.mode=='create'||(cfg.mode=='detailEdit'&&cfg.detailEditMode=='edit')">
                                <input v-if="item.type=='hidden'" :id="item.name" type="hidden" class="form-control" :value="detail[item.name]" :controltype='item.type' />
                                <div v-else-if="item.type=='text'">
                                  <el-input  :name="item.name" style="width:100%;" :placeholder="item.placeholder" :controltype='item.type' v-model="detail[item.name]"></el-input>
                                  <input :id="item.name" type="hidden" class="form-control" :value="detail[item.name]" :controltype='item.type'/>
                                </div>
                                
                                <!-- <textarea v-else-if="item.type=='textarea'" :id="item.name" :name="item.name" style='width:100%' class="form-control" rows="5" :controltype='item.type' :value="detail[item.name]"></textarea> -->
                                <div v-else-if="item.type=='textarea'">
                                  <input :id="item.name" :name="item.name" style="height:0.5px;width:0px;padding:0px;margin:0px;" class="form-control" :controltype='item.type'/>
                                  <el-input
                                    type="textarea"
                                    :autosize="{ minRows: 2, maxRows: 100}"
                                    v-model="detail[item.name]">
                                    </el-input>
                                </div>
                                <iframe v-else-if="item.type=='textxml'" readonly='false' :id="item.name" :name="item.name"  scrolling="no" frameborder="0" class="form-control embed-responsive-item" :controltype='item.type' style="min-height:190px;" src="/src/ref/codemirror/codemirror.html"></iframe>
                                <iframe v-else-if="item.type=='textnginx'" readonly='false' :id="item.name" :name="item.name"  scrolling="no" frameborder="0" class="form-control embed-responsive-item" :controltype='item.type' style="min-height:190px;" src="/src/ref/codemirror/codemirrornginx.html"></iframe>
                                <input v-else-if="item.type=='pwd'" :id="item.name" :name="item.name" type="password" :placeholder="item.placeholder" class="input-xlarge form-control" style="width:100%;" :value="detail[item.name]" :controltype='item.type' />
                                <select v-else-if="item.type=='combox'" :id="item.name" style='width:100%' class="input-xlarge form-control" :controltype='item.type'>
                                    <option v-for="option in item.data" v-bind:key="option.id" :selected="option.id==detail[item.name]?'selected':''" :value="option">{{option.value}}</option>
                                    
                                </select>
                                <div v-else-if="item.type=='timer'">
                                   <input :id="item.name" :name="item.name" style="height:0.5px;width:0px;padding:0px;margin:0px;" class="form-control" :controltype='item.type'/>
                                    <el-date-picker
                                      v-model="detail[item.name]"
                                      style="width:100%"
                                      type="date">
                                    </el-date-picker>
                                </div>
                                <div v-else-if="item.type=='uploader'">
                                        <input :id="item.name" :name="item.name" style="height:0.5px;width:0px;padding:0px;margin:0px;" class="form-control" :controltype='item.type' :limit='item.limit' :mode='item.mode'  />
                                        <el-upload
                                          :action="item.url"
                                          :data="{id:item.name}"
                                          list-type="picture-card"
                                          :with-credentials="true"
                                          :limit="item.limit||1"
                                          :accept="item.accept||''"
                                          :file-list="detail[item.name]"
                                          :on-success="onFileUpload"
                                          :before-remove="onFileUploadBeforeDelete"
                                          :on-exceed="onLimited"
                                          :on-preview="handlePictureCardPreview"
                                          :on-remove="handleRemove">
                                          <i class="el-icon-plus"></i>
                                          <div slot="tip" class="el-upload__tip">{{item.desc}}</div>
                                        </el-upload>
                                        <el-dialog :visible.sync="dialogVisible">
                                          <img width="100%" :src="dialogImageUrl" alt="">
                                          <label>{{dialogImageName}}</label>
                                        </el-dialog>
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
                                <div  v-else-if="item.type=='transfer'">
                                    <input :id="item.name" type="hidden" :controltype='item.type'  class="form-control" />
                                    <el-transfer  filterable v-model="value1[item.name]" :data="bindTransfer(item.name,item.url,false)" :titles="['待选择', '已选择']" @change="bindTransferChangeEvent(item.name)" ></el-transfer>
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
                                <div v-else-if="item.type=='select'">
                                  <input :id="item.name" :name="item.name" style="height:0.5px;width:0px;padding:0px;margin:0px;" :value="detail[item.name]" class="form-control" :controltype='item.type'  />
                                    <el-select v-model="detail[item.name]" @change="onSelectChange(item)" filterable style="width:100%" placeholder="请选择">
                                      <el-option
                                        v-for="opItem in item.data"
                                        :key="opItem[item.displayId||'id']"
                                        :label="opItem[item.displayValue||'value']"
                                        :value="opItem[item.displayId||'id']">
                                        <span style="float: left">{{ opItem[item.displayValue||'value'] }}</span>
                                        <span style="float: right; color: #8492a6; font-size: 13px">{{ opItem[item.displayDesc||'desc'] }}</span>
                                      </el-option>
                                    </el-select>
                                </div>
                                <div v-else-if="item.type=='popup'">
                                  <input :id="item.name" :name="item.name" type="hidden" :value="detail[item.name]" class="form-control" :controltype='item.type'  />
                                  <el-input :name="item.name" v-model="detail[item.name]" disabled placeholder="" >
                                    <el-button slot="append" icon="el-icon-search" @click="onClick(item)"></el-button>
                                  </el-input>
                                </div>
                                <div v-else-if="item.type=='readonly'">
                                  <el-input  :name="item.name" disabled style="width:100%;" :placeholder="item.placeholder" :controltype='item.type' v-model="detail[item.name]"></el-input>
                                  <input :id="item.name" type="hidden" class="form-control" :value="detail[item.name]" :controltype='item.type'/>
                                </div>
                            </template>
                            <template v-if="cfg.mode=='detail'||(cfg.mode=='detailEdit'&&cfg.detailEditMode!='edit')">
                                <input v-if="item.type=='hidden'" :id="item.name" type="hidden" class="form-control" :value="detail[item.name]" :controltype='item.type' />
                                <div v-if="item.type=='yesno'">
                                    <i v-if="detail[item.name]==1" class="fa fa-fw fa-check-circle" style="margin-top:8px;"></i>
                                    <i v-else class="el-icon-close" style="margin-top:8px;"></i>
                                </div>
                                <div v-else-if="item.type=='uploader'">
                                        <input :id="item.name" :name="item.name" style="height:0.5px;width:0px;padding:0px;margin:0px;" class="form-control" :controltype='item.type' :limit='item.limit' :mode='item.mode'  />
                                        <el-upload
                                          :action="item.url"
                                          :data="{id:item.name}"
                                          list-type="picture-card"
                                          :with-credentials="true"
                                          :limit="item.limit||1"
                                          :accept="item.accept||''"
                                          :file-list="detail[item.name]"
                                          :on-success="onFileUpload"
                                          :before-remove="onFileUploadBeforeDelete"
                                          :on-exceed="onLimited"
                                          :on-preview="handlePictureCardPreview"
                                          :on-remove="handleRemove">
                                          <i class="el-icon-plus"></i>
                                          <div slot="tip" class="el-upload__tip">{{item.desc}}</div>
                                        </el-upload>
                                        <el-dialog :visible.sync="dialogVisible">
                                          <img width="100%" :src="dialogImageUrl" alt="">
                                          <label>{{dialogImageName}}</label>
                                        </el-dialog>
                                </div>
                                <iframe v-else-if="item.type=='textxml'" readonly='false' :id="item.name+'_readonly'" :name="item.name+'_readonly'" style='width:100%'  scrolling="no" frameborder="0" class="form-control" :controltype='item.type' src="/src/ref/codemirror/codemirror.html"></iframe>
                                <iframe v-else-if="item.type=='textnginx'" readonly='false' :id="item.name+'_readonly'" :name="item.name+'_readonly'" style='width:100%'  scrolling="no" frameborder="0" class="form-control" :controltype='item.type' src="/src/ref/codemirror/codemirrornginx.html"></iframe>
                                <ul v-else-if="item.type=='tree'" :id="item.name+1" class="ztree"></ul>
                                <!-- <div v-else-if="item.type=='select2select'" v-html="detail[item.name]" style="width:100%" /> -->
                                <el-transfer  v-else-if="item.type=='transfer'" filterable v-model="value1[item.name]" :data="bindTransfer(item.name,item.url,true)" :titles="['待选择', '已选择']"  ></el-transfer>
                                <input v-else-if="item.type=='combox'" class="input-xlarge form-control" disabled='disabled' :value="bindCombox(item.data,detail[item.name])" style="width:100%" />
                                <div v-else-if="item.type=='select'">
                                  <input :id="item.name" :name="item.name" type="hidden" :value="detail[item.name]" class="form-control" :controltype='item.type'  />
                                    <el-select v-model="detail[item.name]" :disabled="true" @change="onSelectChange(item)" filterable style="width:100%" placeholder="请选择">
                                      <el-option
                                        v-for="opItem in item.data"
                                        :key="opItem[item.displayId||'id']"
                                        :label="opItem[item.displayValue||'value']"
                                        :value="opItem[item.displayId||'id']">
                                        <span style="float: left">{{ opItem[item.displayValue||'value'] }}</span>
                                        <span style="float: right; color: #8492a6; font-size: 13px">{{ opItem[item.displayDesc||'desc'] }}</span>
                                      </el-option>
                                    </el-select>
                                </div>
                                <div v-else-if="item.type!='hidden'">
                                  <el-input  :name="item.name" style="width:100%;" :disabled="true" :placeholder="item.placeholder" :controltype='item.type' v-model="detail[item.name]"></el-input>
                                  <input :id="item.name" type="hidden" class="form-control" :value="detail[item.name]" :controltype='item.type'/>
                                </div>
                                <input v-else-if="item.type!='hidden'" :id="item.name" :name="item.name" class="input-xlarge form-control" disabled='disabled' :value="detail[item.name]" style="width:100%" :controltype='item.type' />
                            </template>
                        </td>
                        <!-- <span>
                                {{item.helpblock}}
                        </span> -->
                        </tr>
                    </table>
                </template>
            </div>
            <!-- /.box-body -->
            <div v-if="!cfg.hideFooter" class="box-footer">
                <!-- Button -->
                <center>
                    <div v-if="cfg.mode=='edit'||cfg.mode=='create'">
                        <button v-if="cfg.editFunctionName?this.showFunction(cfg.editFunctionName):true" @click="btnCommit($event)" class="btn btn-primary btn-commit">保存</button>
                        <button  @click="btnCancel" class="btn btn-info btn-cancel" data-dismiss="modal" aria-hidden="true">取消</button>
                        <div v-if="cfg.buttons">
                            <button v-for="item in cfg.buttons" :key="item.title" :class="'btn '+item.type+' btn-buttons'" :name='item.name'>{{item.title}}</button>
                        </div>
                    </div>
                    <div v-if="cfg.mode=='detailEdit'">
                        <div v-if="isShowDetail">
                            <button v-if="cfg.editFunctionName?this.showFunction(cfg.editFunctionName):true" @click="btnEdit" class="btn btn-primary btn-edit">编辑</button>
                            <button @click="btnCancel" class="btn btn-info btn-cancel" data-dismiss="modal">返回</button>
                        </div>
                        <div v-else>
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
  <el-dialog append-to-body :visible.sync="dialogVisible" :width="this.cfg.dialogWidth?this.cfg.dialogWidth:'65%'" >
    <component  style="margin-top:-40px;margin-bottom:-40px;" v-bind:is="currentComponent"></component>
  </el-dialog>
</div>
</template>
<script>
let Base64 = require("js-base64").Base64;
import "jquery-validation";
export default {
  props: ["cfg"],
  created: function() {
    let self = this;
    for (var i = 0; i < self.cfg.items.length; i++) {
      var item = self.cfg.items[i];
      // self.$set(self.detail, item.name, "");
      if (item.type == "uploader") {
        self.$set(self.detail, item.name, []);
        // self.$set(self.detail,item.name,[]);
      } else {
        self.$set(self.detail, item.name, "");
      }
    }
  },
  mounted: function() {
    let self = this;
    self.isMounted = true;

    if (this.cfg.mode != "create") {
      if (!self.cfg.get) {
        return;
      }
      self.openLoading();
      self.fillData(function() {
        if (self.cfg.onLoaded) {
          self.cfg.onLoaded(self.detail);
        }
      });
    } else {
      if (self.cfg.onLoaded) {
        self.cfg.onLoaded(self.detail);
      }
    }
    $(self.$el)
      .find('input[type="file"]')
      .hide();
  },
  beforeUpdate: function() {
    // $(".el-transfer__button").click(function() {
    //   setTimeout(function() {
    //     self.$forceUpdate();
    //   }, 100);
    // });
  },
  updated: function() {
    let self = this;
    if (self.cfg.afterEditRender) {
      self.cfg.afterEditRender(self.cfg.detailEditMode, self.detail);
    }
    this.iframeLoad();
    // alert("update01")
    if (
      this.cfg.mode == "detail" ||
      (this.cfg.mode == "detailEdit" && this.cfg.detailEditMode != "edit")
    ) {
      $(this.$el)
        .find(".el-upload--picture-card")
        .hide();
      $(this.$el)
        .find(".el-upload-list__item-delete")
        .hide();
    }
  },
  data() {
    return {
      detail: {},
      commiting: false,
      value1: {},
      data1: {},
      changing: {},
      isMounted: false,
      isShowDetail: true,
      dialogImageUrl: "",
      dialogImageName: "",
      dialogVisible: false,
      currentComponent: "",
      popUpValue: "",
      popUpItem: {}
    };
  },
  methods: {
    showDialog: function() {
      var className = this.$parent.$parent.$el.className;
      return className.indexOf("el-dialog__wrapper") != -1;
    },
    onSelectChange: function(item) {
      if (item.onChange) {
        item.onChange(this.detail[item.name]);
      }
      $("#" + item.name)
        .val(this.detail[item.name])
        .keyup();
    },
    onFileUploadBeforeDelete: function(file) {
      if (
        this.cfg.mode == "detail" ||
        (this.cfg.mode == "detailEdit" && this.cfg.detailEditMode != "edit")
      ) {
        return false;
      }
      var id = "";
      if (file.response) {
        id = file.response.message;
      } else {
        id = file.itemId;
      }
      $("#" + id)
        .parent()
        .find("li")
        .tooltip("hide");
    },
    handleRemove(file, fileList) {
      this.drawUploader(fileList);
      var id = "";
      if (file.response) {
        id = file.response.message;
      } else {
        id = file.itemId;
      }
      if (fileList.length == 0) {
        $("#" + id).val("");
      }
      this.detail[id] = fileList;
      $("#" + id).valid();
    },
    handlePictureCardPreview(file) {
      window.open(file.url);
      // this.dialogImageUrl = file.url;
      // this.dialogImageName = file.name;
      // this.dialogVisible = true;
    },
    onFileUpload: function(response, file, fileList) {
      var self = this;
      if (response.code == 200) {
        $("#" + response.message).val(response.data);
        if (self.commiting) {
          $("#" + response.message).valid();
        }
        self.detail[response.message] = fileList;
        self.drawUploader(fileList);
      }
    },
    drawUploader: function(fileList) {
      for (var i = 0; i < fileList.length; i++) {
        var item = fileList[i];
        var id = "";
        if (item.response) {
          id = item.response.message;
        } else {
          id = item.itemId;
        }
        $("#" + id)
          .parent()
          .find("li:eq(" + i + ")")
          .find("div")
          .remove();
        if (
          item.name.indexOf(".jpg") != -1 ||
          item.name.indexOf(".png") != -1
        ) {
          $("#" + id)
            .parent()
            .find("li:eq(" + i + ")")
            .append(
              '<div style="position: absolute; top: 120px; left: 0;z-">' +
                item.name +
                "</div>"
            );
        } else {
          $("#" + id)
            .parent()
            .find("li:eq(" + i + ")")
            .append(
              '<div><img width="100%" style="margin-top:-35px;" src="/src/assets/file.png"></img><div style="position: absolute; top: 120px; left: 0;z-">' +
                item.name +
                "</div></div>"
            );
        }

        $("#" + id)
          .parent()
          .find("li:eq(" + i + ")")
          .attr("data-toggle", "tooltip")
          .attr("data-placement", "top")
          .attr("data-original-title", item.name)
          .tooltip();
      }
      if (
        this.cfg.mode == "detail" ||
        (this.cfg.mode == "detailEdit" && this.cfg.detailEditMode != "edit")
      ) {
        $(this.$el)
          .find(".el-upload--picture-card")
          .hide();
        $(this.$el)
          .find(".el-upload-list__item-delete")
          .hide();
      }
    },
    onLimited: function(files, fileList) {
      self.$message({
        message: "只能上传" + fileList.length + "个文件!",
        type: "warning"
      });
    },
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
    btnEdit: function() {
      var self = this;
      this.cfg.detailEditMode = "edit";
      self.isShowDetail = false;
    },
    btnDecancel: function() {
      var self = this;
      this.cfg.detailEditMode = "detail";
      self.isShowDetail = true;
    },
    btnDecommit: function(event) {
      var target = event.currentTarget;
      let self = this;
      self.commiting = true;
      self.btnCommit(target, function() {
        self.cfg.detailEditMode = "detail";
        self.isShowDetail = true;
        self.commiting = false;
        self.fillData();
        // self.$forceUpdate();
      });
    },
    btnCommit: function(e, handler) {
      // var self = this;
      // if (!handler) {
      //   handler = function() {
      //     self.commiting = false;
      //   };
      // }
      // self.commiting = true;
      // var validateCfg = {
      //   ignore: ":hidden",//不验证的元素
      //   onfocusout: false,
      //   onclick: false,
      //   focusInvalid: false,
      //   onkeyup: false,
      //   onkeyup: function(element) {
      //     //console.log(element);
      //     $(element).valid();
      //   },
      //   errorPlacement: function(error, element) {
      //     if (element.attr("controltype") == "suggest") {
      //       element
      //         .next()
      //         .find("button")
      //         .attr("data-toggle", "tooltip");
      //       element
      //         .next()
      //         .find("button")
      //         .parent()
      //         .attr("data-placement", "right");
      //       element
      //         .next()
      //         .find("button")
      //         .parent()
      //         .attr("data-original-title", error.text());
      //       element
      //         .next()
      //         .find("button")
      //         .parent()
      //         .tooltip("show");

      //       // if (error.text()) {
      //       //   self.$message({
      //       //     type: "warning",
      //       //     message:
      //       //       element
      //       //         .parent()
      //       //         .parent()
      //       //         .parent()
      //       //         .find(".control-label")
      //       //         .text() +
      //       //       ":" +
      //       //       error.text()
      //       //   });
      //       // }
      //     } else if (element.attr("controltype") == "uploader") {
      //       element
      //         .parent()
      //         .find(".el-upload--picture-card")
      //         .attr("data-toggle", "tooltip")
      //         .attr("data-placement", "right")
      //         .attr("data-original-title", error.text())
      //         .tooltip("show");
      //     } else {
      //       element.attr("data-toggle", "tooltip");
      //       element.attr("data-placement", "top");
      //       element.attr("data-original-title", error.text());
      //       element.tooltip("show");
      //       element.addClass("has-error");
      //     }

      //     if (element.attr("controltype") != "suggest") {
      //       if (self.commiting && error.text()) {
      //         self.$notify({
      //           title: "验证未通过",
      //           message: error.text(),
      //           position: "bottom-right",
      //           type: "warning"
      //         });
      //       }
      //     }

      //     //error.appendTo(element.parent());
      //   },
      //   success: function(error, element) {
      //     if ($(element).attr("controltype") == "uploader") {
      //       $(element)
      //         .parent()
      //         .find(".el-upload--picture-card")
      //         .tooltip("destroy");
      //     }else{
      //       $(element).tooltip("destroy");
      //       $(element).removeClass("has-error");
      //     }
      //   },
      //   submitHandler: function(form) {
      //     if (!self.commiting) {
      //       return;
      //     }
      //     var data = self.getData();
      //     //console.log(data);
      //     var isOk = true;
      //     if (self.cfg.validate) {
      //       //自定义验证
      //       isOk = self.cfg.validate(data, self.saveData);
      //     }

      //     if (!isOk) {
      //       return;
      //     }
      //     if (self.cfg.beforeCommit) {
      //       self.cfg.beforeCommit(data);
      //     }
      //     $(self.$el)
      //       .find(".btn-commit")
      //       .attr("disabled", "disabled");
      //     self.saveData(data, handler);
      //   }
      // };
      // var lastCfg = $.extend(true, validateCfg, self.cfg);
      // $(self.$el)
      //   .find(".form")
      //   .validate(lastCfg);
      var self = this;
      self.validateFrom(false, function(name, data) {
        $(self.$el)
          .find(".btn-commit")
          .attr("disabled", "disabled");
        self.saveData(data, handler);
      });
    },
    validateFrom: function(isOut, onSuccess, onFail) {
      var self = this;
      self.commiting = true;
      var validateCfg = {
        ignore: ":hidden", //不验证的元素
        onfocusout: false,
        onclick: false,
        focusInvalid: false,
        onkeyup: false,
        onkeyup: function(element) {
          $(element).valid();
        },
        errorPlacement: function(error, element) {
          if (element.attr("controltype") == "uploader") {
            element
              .parent()
              .find(".el-upload--picture-card")
              .attr("data-toggle", "tooltip")
              .attr("data-placement", "right")
              .attr("data-original-title", error.text())
              .tooltip("show");
          } else if (element.attr("controltype") == "select") {
            element
              .parent()
              .find(".el-input__inner")
              .attr("data-toggle", "tooltip")
              .attr("data-placement", "top")
              .attr("data-original-title", error.text())
              .addClass("has-error")
              .tooltip("show");
          } else {
            element.attr("data-toggle", "tooltip");
            element.attr("data-placement", "top");
            element.attr("data-original-title", error.text());
            element.tooltip("show");
            element.addClass("has-error");
          }

          if (self.commiting && error.text()) {
            self.$notify({
              title: "验证未通过",
              message: error.text(),
              position: "bottom-right",
              type: "warning"
            });
          }
        },
        success: function(error, element) {
          if ($(element).attr("controltype") == "uploader") {
            $(element)
              .parent()
              .find(".el-upload--picture-card")
              .tooltip("destroy");
          } else if ($(element).attr("controltype") == "select") {
            $(element)
              .parent()
              .find(".el-input__inner")
              .removeClass("has-error")
              .tooltip("destroy");
          } else {
            $(element).tooltip("destroy");
            $(element).removeClass("has-error");
          }
        },
        submitHandler: function(form) {
          if (!self.commiting || isOut) {
            return;
          }
          var data = self.getData();

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
          // $(self.$el)
          //   .find(".btn-commit")
          //   .attr("disabled", "disabled");
          // self.saveData(data, handler);
          if (onSuccess) {
            onSuccess(self.cfg.name, data);
          }
        }
      };
      var lastCfg = $.extend(true, validateCfg, self.cfg);
      $(self.$el)
        .find(".form")
        .validate(lastCfg);
      if (
        isOut &&
        $(self.$el)
          .find(".form")
          .valid()
      ) {
        if (!self.commiting) {
          return;
        }
        var data = self.getData();

        var isOk = true;
        if (self.cfg.validate) {
          //自定义验证
          isOk = self.cfg.validate(data, self.saveData);
        }

        if (!isOk) {
          return false;
        }
        if (self.cfg.beforeCommit) {
          self.cfg.beforeCommit(data);
        }
        // $(self.$el)
        //   .find(".btn-commit")
        //   .attr("disabled", "disabled");
        // self.saveData(data, handler);
        if (onSuccess) {
          onSuccess(self.cfg.name, data);
        }
        return true;
      } else {
        return false;
      }
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
          var contentType = item.attr("controltype");
          if (this.type == "checkbox") {
            if (item.attr("checked")) {
              data[this.id] = 1;
            } else {
              data[this.id] = 0;
            }
          } else if (contentType == "suggest") {
            data[this.id] = item.attr("data-id");
          } else if (contentType == "yesno") {
            data[this.id] = item.hasClass("is-checked") ? 1 : 0;
          } else if (contentType == "addline") {
            var arraytemp = [];
            item.find("input").each(function() {
              var input = $(this);
              arraytemp.push(input.val());
            });
            data[this.id] = arraytemp;
          } else if (contentType == "textxml") {
            data[this.id] = item[0].contentWindow.getValue();
          } else if (contentType == "textnginx") {
            data[this.id] = item[0].contentWindow.getValue();
          } else if (contentType == "baidutext") {
            data[this.id] = UE.getEditor(this.id + "1")
              .getContent()
              .replace("<video", "<video autoplay");
          } else if (contentType == "transfer") {
            data[this.id] = self.value1[this.id];
            if (data[this.id].length == 0) {
              data[this.id].push(0);
            }
          } else if (
            contentType == "select" ||
            contentType == "text" ||
            contentType == "textarea" ||
            contentType == "combox" ||
            contentType == "hidden" ||
            contentType == "popup" ||
            contentType == "readonly" ||
            contentType == "timer"
          ) {
            if(self.detail[this.id]===0){
              data[this.id] = 0;
            }else{
              data[this.id] = self.detail[this.id] || "";
            }
          } else if (contentType == "uploader") {
            data[this.id] = [];
            for (var i = 0; i < self.detail[this.id].length; i++) {
              var item = self.detail[this.id][i];
              var varId = "";
              var varItemId = "";
              var varName = "";
              var varUrl = "";
              if (item.response) {
                varId = item.response.data;
                varItemId = item.response.message;
                varName = item.name;
                varUrl = "/download?id=" + item.response.data;
              } else {
                varId = item.id;
                varItemId = item.itemId;
                varName = item.name;
                varUrl = "/download?id=" + item.id;
              }
              data[this.id].push({
                id: varId,
                itemId: varItemId,
                name: varName,
                url: varUrl
                // response:item.response
              });
            }
          }
        });
      return data;
    },
    saveData: function(data, handler) {
      let self = this;
      var successLang = "成功!";
      if (data == null) {
        data = self.getData();
      }
      self.post({
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
            if (self.showDialog()) {
              self.$parent.$parent.$parent.dialogVisible = false;
              self.$parent.$parent.$parent.currentComponent = "";
              if (self.$parent.$parent.$parent.reloadSimpleData) {
                self.$parent.$parent.$parent.reloadSimpleData();
              }
              if (self.$parent.$parent.$parent.loadUrl) {
                self.$parent.$parent.$parent.loadUrl(1);
              }
              return;
            }
            if (handler) {
              handler(response);
            } else {
              history.go(-1);
            }
          }
        }
      });
    },
    fillData: function(onSuccess) {
      let self = this;
      self.get({
        url: self.cfg.get.url,
        data: self.cfg.get.params,
        success: function(result) {
          if (result.code == "200") {
            self.detail = result.data;
            if (onSuccess) {
              onSuccess();
            }
            self.closeLoading();
          }
        }
      });
    },
    bindSelect2Select: function(id, url) {
      var self = this;
      self.get({
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
    },
    bindTransfer: function(id, url, disabled) {
      var self = this;
      if (!self.isMounted) {
        return [];
      }
      if (self.changing[id]) {
        self.changing[id] = false;
        return self.data1[id];
      }

      self.data1[id] = [];
      self.value1[id] = [];
      self.get({
        async: false,
        success: function(data) {
          if (data.left) {
            for (var i in data.left) {
              self.data1[id].push({
                key: data.left[i].id,
                label: data.left[i].name,
                disabled: disabled
              });
            }
          }
          if (data.right) {
            for (var i = 0; i < data.right.length; i++) {
              self.data1[id].push({
                key: data.right[i].id,
                label: data.right[i].name,
                disabled: disabled
              });
              self.value1[id].push(data.right[i].id);
            }
          }
        }
      });
      return self.data1[id];
    },
    bindTransferChangeEvent: function(id) {
      this.changing[id] = true;
      this.$forceUpdate();
    },
    bindCombox: function(data, id) {
      if (data) {
        for (var i = 0; i < data.length; i++) {
          var item = data[i];
          if (item.id == id) {
            return item.value;
          }
        }
      } else {
        return id;
      }
    },
    onClick: function(item) {
      this.dialogVisible = true;
      this.currentComponent = item.url;
      this.popUpValue = this.detail[item.name];
      // this.popUpIndex = index;
      this.popUpItem = item;
    },
    getPopupValue: function() {
      return this.popUpValue;
    },
    setPopupValue: function(value) {
      this.detail[this.popUpItem.name] = value;
    }
  },
  watch: {
    detail: {
      handler: function(val, oldval) {
        var self = this;
        for (var i = 0; i < this.cfg.items.length; i++) {
          var item = this.cfg.items[i];
          if (item.type == "uploader") {
            for (var j in val) {
              if (j == item.name) {
                setTimeout(function() {
                  self.drawUploader(val[j]);
                }, 200);
              }
            }
          }
        }
      },
      deep: true
    }
  }
};
</script>
<style>
.has-error {
  border-color: #f56c6c;
}
.el-input__inner .has-error {
  border-color: #f56c6c;
}
.el-input.is-disabled .el-input__inner {
  color: brown;
}
</style>
