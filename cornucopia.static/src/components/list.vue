<template>
<div>
  <div class="box" :style="this.cfg.boxStyle?this.cfg.boxStyle:''">
  
      <div v-if="this.cfg.title&&!this.showDialog()" class="box-header">
        <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item v-if="this.cfg.parentTitle"><i class="fa fa-dashboard"></i> 首页</el-breadcrumb-item>
        <el-breadcrumb-item v-if="this.cfg.parentTitle">{{this.cfg.parentTitle}}</el-breadcrumb-item>
        <el-breadcrumb-item>{{this.cfg.title}}</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>
          <!-- <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button> -->
        </div>
        <hr v-if="SearchItemsCount==0"/>
      </div>
      <div v-if="this.cfg.title&&this.showDialog()" class="box-header">
        <i class="el-icon-circle-plus" />
        <h3 class="box-title">{{cfg.title}}</h3>
        <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>
          <!-- <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button> -->
        </div>
        <hr v-if="SearchItemsCount==0"/>
      </div>

      <div v-show="SearchItemsCount!=0" class="box-header searchDataTableTop">
        <el-collapse v-model="activeName"  @change="onCollapseChange">
          <el-collapse-item name="true" >
            <template slot="title">
                <center> <el-button round><span id="listSearchAreaBtn">展开搜索区</span><i id="listSearchAreaI" class="el-icon-arrow-down"></i></el-button></center>
            </template>
            <div>
              <div class="col-md-10" style="border-right:1px dashed blue">
                  <form class="form-inline" role="form">
                      <div v-for="column in SearchItems" :key="column.name" class="form-group" style="margin-bottom:25px;margin-right:15px;display:inline-block;">
                          <label class="searchColumTitle" for="name">{{column.title}}：</label>
                          <div v-if="column.type=='combox'" class="input-group" >
                            <div class="input-group-addon">
                              <i class="fa fa-fw fa-list-alt"></i>
                            </div>
                              <input type="hidden" class="form-control" :id="column.name" :name="column.name"/>
                              <el-select v-model="value" placeholder="请选择" @change="onSelectChange(value,column.name)">
                              <el-option v-for="item in column.data" :key="item.id" :label="item.value" :value="item.id"> </el-option>
                            </el-select>
                          </div>
                          <div v-else-if="column.type=='timer'" class="input-group" v-bind="bindTimer(column.name)">
                            <div class="input-group-addon">
                              <i class="fa fa-fw fa-clock-o"></i>
                            </div>
                            <input type="text" class="form-control" :id="column.name" :name="column.name"/>
                          </div>
                          <div v-else-if="column.type=='suggest'" class="input-group">
                              <div class="input-group-addon">
                                <i class="fa fa-fw  fa-th-list"></i>
                              </div>
                              <input :id="column.name" :name="column.name" type="text" style="width:168px;margin-right:0px;" class="form-control" :controltype='column.type' />
                              <div class="input-group-btn" style="display:inline;margin-left:0px;">
                                  <button type="button" class="btn btn-default dropdown-toggle btn-suggest" data-toggle="dropdown">
                                      <span class="caret"></span>
                                  </button>
                                  <ul :class="'dropdown-menu dropdown-menu-right dropdown-suggest-'+column.name" role="menu">
                                  </ul>
                              </div>
                          </div>
                          <!-- <script v-if="column.type=='suggest'" type="text/javascript">
                              var data=[];
                              <% _.each(column.data, function(option,i) { %>
                                  data.push({id:"<%=option.id%>",word:"<%=option.word%>",description:"<%=option.description%>"});
                              <%})%>
                                  $("#<%=column.name%>").bsSuggest({
                                  indexId: 0, //data.value 的第几个数据，作为input输入框的内容
                                  indexKey: 1, //data.value 的第几个数据，作为input输入框的内容
                                  data: {
                                  'value':data
                              }
                              });
                          </script> -->
                          <div v-else class="input-group">
                            <div class="input-group-addon">
                              <i class="fa fa-fw fa-text-height"></i>
                            </div>
                            <input type="text" class="form-control" :id="column.name" autocomplete="off"/>
                          </div>
                      </div>
                  </form>
              </div>
              <div class="col-md-2">
                  <center>
                      <button class="btn btn-primary btn-large btn-searchDataTable btn-app btn-dropbox">
                        <i class="glyphicon glyphicon-search"></i>
                        搜索
                      </button>
                  </center>
              </div>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
      <!-- <hr v-if="SearchItemsCount!=0" /> -->
      <div class="box-body">
          <table :id="tableListId" style="width:100%;white-space: nowrap;" class="table table-bordered table-hover">
          </table>
      </div>
  </div>
  <el-dialog :visible.sync="dialogVisible" :width="this.cfg.dialogWidth?this.cfg.dialogWidth:'65%'" >
    <component :isDialog="true" style="margin-top:-40px;margin-bottom:-40px;" v-bind:is="currentComponent"></component>
  </el-dialog>
</div>
</template>

<script>
import "datatables.net-bs/css/dataTables.bootstrap.css";
import "datatables.net/js/jquery.dataTables";
import "datatables.net-bs/js/dataTables.bootstrap";
import "datatables.net-fixedcolumns/js/dataTables.fixedColumns"
import "datatables.net-fixedcolumns-bs/css/fixedColumns.bootstrap.css"

import "bootstrap-daterangepicker/daterangepicker.css";
import "bootstrap-daterangepicker/daterangepicker";

import "admin-lte/plugins/iCheck/all.css";
import "admin-lte/plugins/iCheck/icheck";

export default {
  props: ["cfg"],
  mounted: function() {
    var self = this;
    var GodData = {};
    var searchColumns = [];
    var aoColumns = [];
    if (this.cfg.functions&&!self.cfg.hideCheckBox) {
      aoColumns.push({
        sTitle:
          '<center><input type="checkbox" class="searchDataTableCheckAll" /></center>',
        // mDataProp: [0],
        sName: "",
        bSearchable: false,
        bSortable: false,
        render: function(data, type, row, meta) {
          var str =
            '<center><input type="checkbox" class="searchDataTableCheckItem" value="' +
            row[self.cfg.idName] +
            '" /></center>';
          return str;
        } //自定义列的样式
      });
    }
    var OperationLang = "Operation";
    var SearchLang = "Search";
    var MoreLang = "更多操作";

    if (this.cfg.operations) {
      var ops = [];
      for (var i = 0; i < this.cfg.operations.length; i++) {
        var op = this.cfg.operations[i];
        var isUse = self.showFunction(op.functionName);
        if (!isUse) {
          continue;
        }
        ops.push(op);
      }
      this.cfg.operations = ops;
      aoColumns.push({
        sTitle: "操作",
        // mDataProp: [1],
        sName: "",
        bSearchable: false,
        bSortable: false,
        render: function(data, type, row, meta) {
          var str = "";
          for (var i = 0; i < self.cfg.operations.length; i++) {
            var op = self.cfg.operations[i];

            if (i != 0) {
              str += " | ";
            }
            var idName = op.idName ? op.idName : self.cfg.idName;
            var presuff = "";

            if (
              op.url.indexOf("frame?url=") >= 0 ||
              op.url.indexOf("address?url=") >= 0
            ) {
              presuff = "?";
            } else {
              presuff = op.url.indexOf("?") > 0 ? "&" : "?";
            }
            var paramUrl = presuff + "id=" + row[idName];
            if (self.cfg.urlParams) {
              for (var j = 0; j < self.cfg.urlParams.length; j++) {
                paramUrl +=
                  "&" +
                  self.cfg.urlParams[j] +
                  "=" +
                  row[self.cfg.urlParams[j]];
              }
            }
            if (!op.target) {
              op.target = "_self";
            }
            if (op.url == "#") {
              str +=
                '<a class="' +
                op.name +
                '" data="' +
                paramUrl +
                '">' +
                op.text +
                "</a>";
              continue;
            }
            if (op.url.indexOf("address?url=") >= 0) {
              str +=
                '<a href="/' +
                op.url.replace("address?url=", "") +
                paramUrl +
                '" target="' +
                op.target +
                '">' +
                op.text +
                "</a>";
            } else {
              str +=
                '<a href="#' +
                op.url +
                paramUrl +
                '" target="' +
                op.target +
                '">' +
                op.text +
                "</a>";
            }
          }
          return str;
        } //自定义列的样式
      });
    }
    for (var i = 0; i < this.cfg.columns.length; i++) {
      var column = this.cfg.columns[i];
      if (column.isSearch) {
        searchColumns.push(column);
      }
      if (!column.isHide) {
        aoColumns.push({
          sTitle: column.title,
          mDataProp: column.name,
          sName: column.name,
          sWidth: column.width,
          bSortable: column.sortable || false
        });
      }
    }

    var buttons = "";
    //功能按钮处理
    var functions = this.cfg.functions;
    if (functions) {
      if (functions && functions.common) {
        var ops = [];
        for (var i = 0; i < functions.common.length; i++) {
          var op = functions.common[i];
          var isUse = self.showFunction(op.functionName);
          if (!isUse) {
            continue;
          }
          ops.push(op);
        }
        functions.common = ops;
        for (var i = 0; i < functions.common.length; i++) {
          var button = functions.common[i];
          buttons +=
            "<button id='" +
            button.text +
            "' class='btn btn-success searchDataTableMoreOp "+self.getButtonIcon(button.functionName)+"' url='" +
            button.url +
            "' mode='" +
            button.mode +
            "' style='margin-left:10px;' limitSelected='" +
            button.limitSelected +
            "' tips='" +
            button.tips +
            "'>" +
            button.text +
            "</button>";
        }
      }
      if (functions.more) {
        var ops = [];
        for (var i = 0; i < functions.more.length; i++) {
          var op = functions.more[i];
          var isUse = self.showFunction(op.functionName);
          if (!isUse) {
            continue;
          }
          ops.push(op);
        }
        functions.more = ops;
        if (ops.length != 0) {
          buttons += '<div class="btn-group" style="margin-left:10px;" >';
          buttons +=
            '<button type="button" class="btn btn-success">' +
            MoreLang +
            "</button>";
          buttons +=
            '<button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown">';
          buttons += '<span class="caret"></span>';
          buttons += '<span class="sr-only"></span>';
          buttons += "</button>";
          buttons += '<ul class="dropdown-menu dropdown-ul-menu" role="menu">';
          if (functions && functions.more) {
            for (var i = 0; i < functions.more.length; i++) {
              var button = functions.more[i];
              buttons +=
                '<li><a class="searchDataTableMoreOp" id="' +
                button.text +
                '" url="' +
                button.url +
                '" mode="' +
                button.mode +
                '" limitSelected="' +
                button.limitSelected +
                '" tips="' +
                button.tips +
                '" >' +
                button.text +
                "</a></li>";
              buttons += '<li class="divider"></li>';
            }
          }
          buttons += "</ul>";
          buttons += "</div>";
        }
      }
    }

    var dataTableCfg = {
      sAjaxSource: this.cfg.url,
      aoColumns: aoColumns,
      aoColumnDefs: [
        {
          sDefaultContent: "",
          aTargets: ["_all"]
        }
      ],
      oLanguage: {
        sProcessing: "处理中...",
        sLengthMenu: "每页 _MENU_ 条记录",
        sZeroRecords: "没有匹配的记录",
        sInfo: "显示第 _START_ 至 _END_ 项记录，共 _TOTAL_ 项",
        sInfoEmpty: "显示第 0 至 0 项记录，共 0 项",
        sInfoFiltered: "(由 _MAX_ 项记录过滤)",
        sInfoPostFix: "",
        sSearch: "过滤:",
        sUrl: "",
        oPaginate: {
          sFirst: "首页",
          sPrevious: "上页",
          sNext: "下页",
          sLast: "末页"
        }
      },
      bRetrieve: true,
      bFilter: true,
      bPaginate: true,
      bServerSide: true,
      bSort: false,
      bLengthChange: true,
      sServerMethod: "POST",
      sDom: '<"H"<"dataTables_function"/>rp>t<"F"lip>',
      // "pagingType": "full_numbers",
      "lengthMenu": [[10, 25, 50, 100,300], [10, 25, 50, 100,300]]
    };
    self.lastCfg = $.extend(true, dataTableCfg, this.cfg);
    
    self.dataTable = $("#"+self.tableListId).DataTable(self.lastCfg);

    if(!self.lastCfg.bServerSide){
      self.reloadSimpleData();
    }

    self.dataTable.on("draw", function() {
      $(self.$el)
        .find(".dataTables_paginate")
        .css("margin-top", "-40px");
      $(".searchDataTableCheckItem").iCheck({
        checkboxClass: "icheckbox_flat-green",
        radioClass: "iradio_flat-green"
      });
    });

    $(self.$el)
      .find(".dataTables_function")
      .html(buttons);

    //全选
    $(".searchDataTableCheckAll").on("ifChecked", function(event) {
      $(".searchDataTableCheckItem").iCheck("check");
    });
    //反选
    $(".searchDataTableCheckAll").on("ifUnchecked", function(event) {
      $(".searchDataTableCheckItem").iCheck("uncheck");
    });

    $(".searchDataTableCheckAll").iCheck({
      checkboxClass: "icheckbox_flat-green",
      radioClass: "iradio_flat-green"
    });

    $(self.$el)
      .find(".searchDataTableMoreOp")
      .click(function() {
        var onSuccess = null;
        for (var i = 0; i < functions.common.length; i++) {
          var button = functions.common[i];
          if(button.text==this.id){
            onSuccess = button.onSuccess;
          }
        }
        self.searchDataTableMoreOp(this,onSuccess);
      });
    $(self.$el)
      .find(".btn-searchDataTable")
      .click(function() {
        self.doSearch(this);
      });

    this.SearchItemsCount = this.getSearchItemsCount();
    this.SearchItems = this.getSearchItems();

    $(self.$el).find('.el-collapse-item__arrow').hide();

    if(self.activeName=="true"){
      $('#listSearchAreaBtn').text('收起搜索区');
      $('#listSearchAreaBtn').next().addClass('el-icon-arrow-up');
    }

    $('#'+self.tableListId).on('click', 'tr', function () {
        var data = self.dataTable.row(this).data();
        if(!data)return;
        if(self.cfg.showSelectedRowColor){
           $('#'+self.tableListId+' tr').css('background-color', "");
           $(this).css('background-color', "#D6D5C3");
           self.currentId = data.id;
        }
        if(self.cfg.onClickRow){
          self.cfg.onClickRow(data,this);
        }
    } );
    $(self.dataTable.row(1)).css('background-color', "#D6D5C3");
  },
  updated: function() {
    var searchDataTableTop = $(this.$el).find(".searchDataTableTop");

    var span10Height = searchDataTableTop.find(".col-md-10").height();

    var searchButton = searchDataTableTop.find(".btn-searchDataTable");

    var searchButtonHeight = searchButton.outerHeight();
    searchButton.css(
      "margin-top",
      (span10Height - searchButtonHeight) / 2 + "px"
    );
    // alert('list')
    var self = this;
    self.$parent.$parent.title= self.cfg.title;
    self.$parent.$parent.parentTitle= self.cfg.parentTitle;
  },
  data() {
    return {
      lastCfg:{},
      currentComponent:'',
      dialogVisible: false,
      SearchItems: [],
      SearchItemsCount: 0,
      activeName: this.cfg.isShowSearchArea,
      value:"",
      tableListId : "tableList_"+this.newGuid()
    };
  },
  methods: {
    showDialog: function() {
      var parents = $(this.$el).parents("div");
      for(var i=0;i<parents.length;i++){
        var parent = parents[i];
        if(parents[i].className.indexOf("el-dialog__wrapper") != -1){
          return true;
        }
      }
      if(this.$parent.$parent.$el){
        var className = this.$parent.$parent.$el.className;
        return className.indexOf("el-dialog__wrapper") != -1;
      }else{
        return false;
      }
      
    },
    onSelectChange:function(val,id){
      $('#'+id).val(val);
    },
    onCollapseChange:function(val){
      var txt = $('#listSearchAreaBtn').text();
      $('#listSearchAreaBtn').next().removeClass();
      if(txt=='收起搜索区'){
         $('#listSearchAreaBtn').text('展开搜索区');
         $('#listSearchAreaBtn').next().addClass('el-icon-arrow-down');
      }else{
        $('#listSearchAreaBtn').text('收起搜索区');
        $('#listSearchAreaBtn').next().addClass('el-icon-arrow-up');
      }
    },
    getSearchItems: function() {
      var searchColumns = [];
      for (var i = 0; i < this.cfg.columns.length; i++) {
        var column = this.cfg.columns[i];
        if (column.isSearch) {
          searchColumns.push(column);
        }
      }
      return searchColumns;
    },
    getSearchItemsCount: function() {
      var searchColumns = this.getSearchItems();
      return searchColumns.length;
    },
    bindTimer: function(id) {
      $(function() {
        $("#" + id).daterangepicker(
          {
            autoApply: true,
            timePicker: false,
            showDropdowns: true,
            timePicker24Hour: true,
            linkedCalendars: false,
            autoUpdateInput: false,
            timePickerIncrement: 30,
            startOfWeek: "monday",
            locale: {
              format: "YYYY/MM/DD",
              separator: " ~ ",
              applyLabel: "应用",
              cancelLabel: "取消",
              resetLabel: "重置"
            }
          },
          function(start, end, label) {
            var beginTimeStore = start;
            var endTimeStore = end;
            if (!this.startDate) {
              this.element.val("");
            } else {
              this.element.val(
                this.startDate.format(this.locale.format) +
                  this.locale.separator +
                  this.endDate.format(this.locale.format)
              );
            }
          }
        );
      });
    },
    searchDataTableMoreOp: function(e,onSuccess) {
      var self = this;
      var mode = $(e).attr("mode");
      var url = $(e).attr("url");
      var limitSelected = $(e).attr("limitSelected");
      if (mode == "navigate") {
        this.$router.push({ path: url });
        return;
      } else if (mode == "modal") {
        self.currentComponent = url;
        self.dialogVisible = true;
        return;
      }
      var tipMsg = $(e).text();
      var checks = $(self.$el).find("input:checkbox:checked");
      if (mode != "skipcheck" && checks.length == 0&&!self.currentId) {
        self.$message({
          message: "请选择一条记录",
          type: "warning"
        });
      } else {
        var TipMsg = "是否继续?";
        var tips = $(e.target).attr("tips");
        if (!tips) {
          tips = TipMsg;
        }
        self
          .$confirm(tips, "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "info"
          })
          .then(() => {
            var formData = [];
            checks.each(function() {
              var idValue = $(this).val();
              if (idValue != "on") {
                formData.push($(this).val());
              }
            });
            if(self.currentId){
              formData.push(self.currentId)
            }
            if (mode == "download") {
              if (limitSelected) {
                if (limitSelected < data.length) {
                  $.fn.message({
                    msg: "只能下载" + limitSelected + "个对象！",
                    type: "warning"
                  });
                  return;
                }
              }
              var form = $("<form>");
              form.attr("style", "display:none");
              form.attr("target", "");
              form.attr("method", "post");
              form.attr("action", url);
              for (var i = 0; i < data.length; i++) {
                var input1 = $("<input>");
                input1.attr("type", "hidden");
                input1.attr("name", "ids");
                input1.attr("value", data[i]);
                $("body").append(form);
                form.append(input1);
              }
              form.submit();
            } else {
              $.ajax({
                url: url,
                // contentType: "application/json;charset=utf-8",
                // dataType: "json",
                traditional: true,
                //  crossDomain: false,
                xhrFields: {
                  withCredentials: true
                },
                type: "POST",
                data: { Ids: formData },
                success: function(response) {
                  if(onSuccess){
                      onSuccess(response)
                  }
                  if (response.code == 200) {
                    self.reloadSimpleData();
                    self.dataTable.draw(false);
                    self.$message({
                      message: "操作成功!",
                      type: "success"
                    });
                    self.currentId="";
                  } else {
                    self.$message({
                      message: response.message + "！",
                      type: "error"
                    });
                  }
                }
              });
            }
          })
          .catch(() => {
            // this.$message({
            //   type: "info",
            //   message: "已取消删除"
            // });
          });
      }
    },
    doSearch: function(e) {
      var self = this;
      var data = {};

      $(self.$el)
        .find(".searchDataTableTop")
        .find(".form-control")
        .each(function(index) {
          var item = $(this);
          if (item.attr("controltype") == "suggest") {
            data[this.id] = item.attr("data-id");
          } else {
            data[this.id] = item.val();
          }
        });
      self.dataTable.search(JSON.stringify(data)).draw();
    }
    ,
    getSimpleData: function(simpleUrl,onSuccess) {
      var self = this;
      if(!self.cfg.simpleUrl&&!simpleUrl){
        return;
      }
      self.openLoading();
      var data = [];
      $.ajax({
        type: "GET",
        // async:false,
        xhrFields: {
          withCredentials: true
        },
        url: simpleUrl||self.cfg.simpleUrl,
        success: function(response) {
          if (response.code == "200") {
            data = response.data;
            if(onSuccess){
              onSuccess(data);
            }
          } else if (response.message) {
            self.$message({
              type: "warning",
              message: response.message
            });
          }
          self.closeLoading();
        }
      });
      return data;
    },
    reloadSimpleData:function(simpleUrl){
      var self = this;
      if(!self.lastCfg.bServerSide){
        self.getSimpleData(simpleUrl,function(data){
          self.dataTable.clear().rows.add(data).draw()
        });
        
      }
    },
    loadSimpleData:function(data){
      var self = this;
      self.lastCfg.data = data;
      self.dataTable.clear().rows.add(data).draw()
    },
    clearData:function(){
      this.dataTable.clear().draw();
    }
  }
};
</script>

<style>
.dataTables_function {
  /* margin-bottom: -40px; */
  min-height: 30px;
}
.dataTables_length {
  display: inline-block;
}
.dataTables_info {
  margin-left: 40px;
  display: inline-block;
}
/* .dataTables_filter {
  visibility: hidden;
} */
.searchColumTitle {
  width: 100px;
}
</style>
