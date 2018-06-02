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
                <el-input v-if="item.type=='text'" :name="item.name" v-model="tableData[scope.$index][item.name]" placeholder="" ></el-input>
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
    </form>
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
            element.parent().parent().attr("data-toggle", "tooltip");
            element.parent().parent().attr("data-placement", "right");
            element.parent().parent().attr("data-original-title", error.text());
            element.parent().parent().tooltip("show");
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
          $(element).parent().parent().tooltip("destroy");
          $(element).removeClass("myerror");
        },
        submitHandler: function(form) {
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
            onSuccess(data);
          }
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
          onSuccess(data);
        }
      }
    },
    getData: function() {
      return [];
    }
  }
};
</script>
<style>
.myerror {
  border-color: #a94442;
}
</style>
