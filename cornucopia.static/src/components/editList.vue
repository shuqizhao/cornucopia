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
    <form class="form-inline form" onsubmit='return false;' role="form">
        <el-table
            :data="tableData"
            border
            @selection-change="handleSelectionChange"
            style="width: 100%">
            <el-table-column
            type="selection"
            width="55">
            </el-table-column>
            <el-table-column v-for="item in this.cfg.items" :key="item.name"
            :show-overflow-tooltip="true"
            :label="item.title"
            >
            <template slot-scope="scope">
                <el-input v-if="item.type=='text'" :name="item.name" v-model="tableData[scope.$index][item.name]" placeholder="" ></el-input>
                <el-select v-else-if="item.type=='combox'" :name="item.name" v-model="tableData[scope.$index][item.name]"
                 @change="item.onChange?item.onChange(scope.$index,item.name,tableData[scope.$index][item.name],tableData):''" placeholder="">
                    <el-option
                    v-for="opItem in item.data"
                    :key="opItem.id"
                    :label="opItem.name"
                    :value="opItem.id">
                    </el-option>
                </el-select>
                <div v-else-if="item.type=='popup'">
                  <el-input :name="item.name" v-model="tableData[scope.$index][item.name]" placeholder="" >
                    <el-button slot="append" icon="el-icon-search" @click="onClick(scope.$index,item)"></el-button>
                  </el-input>
                </div>
            </template>
            </el-table-column>
        </el-table>
    </form>
</div>
<el-dialog append-to-body :visible.sync="dialogVisible" :width="this.cfg.dialogWidth?this.cfg.dialogWidth:'65%'" >
    <component  style="margin-top:-40px;margin-bottom:-40px;" v-bind:is="currentComponent"></component>
  </el-dialog>
</div>
</template>

<script>
$(function() {
  if ($.validator) {
    $.validator.prototype.elements = function() {
      var validator = this,
        rulesCache = {};
      return $(this.currentForm)
        .find("input, select, textarea")
        .not(":submit, :reset, :image, [disabled]")
        .not(this.settings.ignore)
        .filter(function() {
          if (!this.name && validator.settings.debug && window.console) {
            console.error("%o has no name assigned", this);
          }
          if (!this.name || this.name == "") {
            return false;
          }
          rulesCache[this.name] = true;
          return true;
        });
    };
  }
});
export default {
  props: ["cfg"],
  mounted: function() {
    if (this.cfg.mode != "create") {
      this.openLoading();
      this.fillData();
    }
  },
  data() {
    var self = this;
    return {
      tableData: [],
      multipleSelection: [],
      currentComponent:'',
      dialogVisible: false,
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
      this.tableData.push(data);
    },
    deleteSelected: function() {
      for (var i = 0; i < this.multipleSelection.length; i++) {
        this.removeObjWithArr(this.tableData, this.multipleSelection[i]);
      }
    },
    upSelected: function() {
      for (var i = 0; i < this.multipleSelection.length; i++) {
        var index = this.getIndexWithArr(
          this.tableData,
          this.multipleSelection[i]
        );
        this.upRecord(this.tableData, index);
      }
    },
    downSelected: function() {
      for (var i = 0; i < this.multipleSelection.length; i++) {
        var index = this.getIndexWithArr(
          this.tableData,
          this.multipleSelection[i]
        );
        this.downRecord(this.tableData, index);
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    getIndexWithArr: function(_arr, _obj) {
      var len = _arr.length;
      for (var i = 0; i < len; i++) {
        if (_arr[i] == _obj) {
          return parseInt(i);
        }
      }
      return -1;
    },
    removeObjWithArr: function(_arr, _obj) {
      var index = this.getIndexWithArr(_arr, _obj);
      _arr.splice(index, 1);
    },
    swapItems: function(arr, index1, index2) {
      arr[index1] = arr.splice(index2, 1, arr[index1])[0];
      return arr;
    },
    // 上移
    upRecord: function(arr, $index) {
      if ($index == 0) {
        return;
      }
      this.swapItems(arr, $index, $index - 1);
    },
    // 下移
    downRecord: function(arr, $index) {
      if ($index == arr.length - 1) {
        return;
      }
      this.swapItems(arr, $index, $index + 1);
    },
    validateFrom: function(onSuccess, onFail) {
      var self = this;
      self.commiting = true;
      var validateCfg = {
        onfocusout: false,
        onclick: false,
        focusInvalid: false,
        onkeyup: false,
        onkeyup: function(element) {
          $(element).valid();
        },
        errorPlacement: function(error, element) {
          if (true) {
            element
              .parent()
              .parent()
              .parent()
              .attr("data-toggle", "tooltip");
            element
              .parent()
              .parent()
              .parent()
              .attr("data-placement", "right");
            element
              .parent()
              .parent()
              .parent()
              .attr("data-original-title", error.text());
            element
              .parent()
              .parent()
              .parent()
              .tooltip("show");
            element.addClass("myerror");
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
          }
        },
        success: function(error, element) {
          $(element)
            .parent()
            .parent()
            .parent()
            .tooltip("destroy");
          $(element).removeClass("myerror");
        }
      };
      var lastCfg = $.extend(true, validateCfg, self.cfg);
      $(self.$el)
        .find(".form")
        .validate(lastCfg);
      if (
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
          return isOk;
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
    getData: function() {
      return this.tableData;
    },
    fillData: function() {
      var self = this;
      $.ajax({
        type: "get",
        xhrFields: {
          withCredentials: true
        },
        url: self.cfg.get.url,
        data: self.cfg.get.params,
        // async: false,
        success: function(result) {
          if (result.code == "200") {
            if (result.data && result.data.length > 0) {
              for (var i = 0; i < result.data.length; i++) {
                self.insertNew(result.data[i]);
              }
            }
            self.closeLoading();
          }
        }
      });
    },
    onClick:function(index,item){
      this.dialogVisible = true;
      this.currentComponent = item.url;
    }
  }
};
</script>
<style>
.myerror {
  border-color: #a94442;
}
</style>
