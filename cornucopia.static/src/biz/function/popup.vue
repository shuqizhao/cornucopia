<template>
  <el-row>
    <el-col :span="12"><list :cfg="cfg"></list></el-col>
    <el-col :span="12"><mform ref="para" :cfg="cfg1"></mform></el-col>
  </el-row>
</template>
<script>
export default {
  mounted: function() {
    var self = this;
    if (self.parainst.parainstId) {
      $.ajax({
        type: "GET",
        xhrFields: {
          withCredentials: true
        },
        url:
          self.getGlobalData().ApiBaseUrl +
          "/function/getParainst?id=" +
          self.parainst.parainstId,
        // data: data,
        success: function(response) {
          if (response.code == 200) {
            parainst = response.data;
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
        simpleUrl: this.getGlobalData().ApiBaseUrl + "/function/alllist",
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
          if (data.isEnabled) {
            $("td:eq(2)", row).html('<i class="fa fa-fw fa-check-circle"></i>');
          } else {
            $("td:eq(2)", row).html('<i class="el-icon-close"></i>');
          }
        },
        onClickRow: function(data, target) {
          self.id = data.id;

          $.ajax({
            type: "GET",
            xhrFields: {
              withCredentials: true
            },
            url:
              self.getGlobalData().ApiBaseUrl +
              "/function/getPara?id=" +
              data.id,
            // data: data,
            success: function(response) {
              if (response.code == 200) {
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
              }
            }
          });
        }
      },
      cfg1: {
        title: "参数",
        mode: "create",
        save: this.getGlobalData().ApiBaseUrl + "/function/addOrUpdateParainst",
        items: [
          // {
          //   name: "name",
          //   title: "角色名",
          //   type: "text"
          // }
        ],
        rules: {},
        messages: {},
        beforeCommit: function(data) {
          data.functionId = self.id;
          data.parainstJson = JSON.stringify(data);
          if (self.parainst.parainstId) {
            data.parainstId = self.parainst.parainstId;
          } else {
            data.parainstId = self.newGuid();
          }
        },
        validate: function(data, saveData) {
          if (self.id == 0) {
            self.$message({
              type: "warning",
              message: "请先选择一个函数!"
            });
            return false;
          }
          return true;
        }
      },
      id: 0,
      parainst: {}
    };
  },
  updated: function() {
    var self = this;
  }
};
</script>
<style>
.dataTables_filter {
  margin-bottom: -40px;
}
</style>
