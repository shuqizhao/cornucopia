<template>
  <el-row>
    <el-col :span="12"><list ref="func" :cfg="cfg"></list></el-col>
    <el-col :span="12"><mform ref="para" :cfg="cfg1"></mform></el-col>
  </el-row>
</template>
<script>
export default {
  mounted: function() {
    var self = this;
    this.openLoading(self.$refs.func.$el,"func");
    var parainstId = self.$parent.$parent.getPopupValue();
    if (parainstId) {
      this.openLoading(self.$refs.para.$el,"para");
      $.ajax({
        type: "GET",
        xhrFields: {
          withCredentials: true
        },
        url:
          self.getGlobalData().ApiBaseUrl +
          "/function/getParainst?parainstId=" +
          parainstId,
        success: function(response) {
          if (response.code == 200) {
            self.parainst = response.data;
            self.getPara(self.parainst.functionId);
            self.$refs.func.reloadSimpleData(
              self.getGlobalData().ApiBaseUrl +
                "/function/alllist?id=" +
                self.parainst.functionId
            );
          }
        }
      });
    }
  },
  data() {
    var self = this;
    return {
      cfg: {
        title: "选择函数",
        // parentTitle: "权限管理",
        simpleUrl: this.getGlobalData().ApiBaseUrl + "/function/alllist?id=0",
        lengthMenu: [[-1], ["ALL"]],
        sDom: 'f<"dataTables_function"/>',
        bServerSide: false,
        hideCheckBox: true,
        showSelectedRowColor: true,
        columns: [
          {
            title: "id",
            name: "id",
            isHide: true
          },
          {
            title: "函数名",
            name: "name"
          },
          {
            title: "函数描述",
            name: "desc"
          }
        ],
        idName: "id",
        fnRowCallback: function(row, data) {
          if (data.id == self.parainst.functionId) {
            self.id = data.id;
            $(row).css("background-color", "#D6D5C3");
          }
          self.closeLoading("func");
        },
        onClickRow: function(data, target) {
          self.id = data.id;
          self.getPara(data.id);
        }
      },
      cfg1: {
        title: "参数",
        mode: "create",
        save: this.getGlobalData().ApiBaseUrl + "/function/addOrUpdateParainst",
        items: [],
        rules: {},
        messages: {},
        validate: function(data, saveData) {
          if (self.id == 0) {
            self.$message({
              type: "warning",
              message: "请先选择一个函数!"
            });
            return false;
          }
          data.functionId = self.id;
          data.parainstJson = JSON.stringify(data);
          if (self.parainst.parainstId && self.id == self.parainst.functionId) {
            data.parainstId = self.parainst.parainstId;
          } else {
            data.parainstId = self.newGuid();
          }
          saveData(data, function() {
            self.$parent.$parent.setPopupValue(data.parainstId);
            self.$parent.$parent.dialogVisible = false;
            self.$parent.$parent.currentComponent = "";
          });
          return false;
        },
        onCancel: function() {
          self.$parent.$parent.dialogVisible = false;
          self.$parent.$parent.currentComponent = "";
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
    getPara: function(id) {
      var self = this;
      $.ajax({
        type: "GET",
        xhrFields: {
          withCredentials: true
        },
        url: self.getGlobalData().ApiBaseUrl + "/function/getPara?id=" + id,
        success: function(response) {
          if (response.code == 200) {
            if (self.parainst.parainstJson) {
              self.$refs.para.detail = JSON.parse(self.parainst.parainstJson);
            }
            self.cfg1.items = [];
            for (var i = 0; i < response.data.length; i++) {
              var dataItem = response.data[i];
              self.cfg1.items.push({
                lableWidth: "150px",
                width: "80%",
                isRequire: true,
                name: dataItem.name,
                title: dataItem.desc,
                type: "text"
              });
              self.cfg1.rules[dataItem.name] = {
                required: true
              };
              self.cfg1.messages[dataItem.name] = {
                required: dataItem.desc + "必须填写"
              };
            }
            self.closeLoading("para");
          }
        }
      });
    }
  }
};
</script>
<style>
.dataTables_filter {
  margin-bottom: -40px;
}
</style>
