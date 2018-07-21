<template>
<div>
  <editList ref="editList" :cfg="cfg"></editList>
  <buttonBar :cfg="cfg1"></buttonBar>
</div>
</template>
<script>
export default {
  mounted: function() {
    var self = this;
    self.get(this.getGlobalData().ApiBaseUrl + "/process/alllist", "", function(
      response
    ) {
      if (response.code == 200) {
        self.$set(self.cfg.tools[0], "data", response.data);
      }
    });
  },
  data() {
    var self = this;
    return {
      cfg: {
        title: "审批矩阵",
        mode: "create",
        name: "matrix",
        items: [
          {
            name: "postionName",
            title: "审批岗位",
            type: "label"
          }
        ],
        validate: function(data, saveData) {
          return true;
        },
        tools: [
          {
            name: "processId",
            title: "流程",
            type: "select",
            onChange: function(s1, toolData) {
              self.currentProcessId = s1;
              // self.cfg1.extraData.process.processId = s1;
              self.currentProcessNodeId = 0;
              self.currentApproveId = 0;
              var items = [
                {
                  name: "postionName",
                  title: "审批岗位",
                  type: "label"
                }
              ];
              self.$set(self.cfg, "items", items);
              self.$set(self.$refs.editList, "tableData", []);

              self.get(
                self.getGlobalData().ApiBaseUrl +
                  "/processnode/alllist?processId=" +
                  s1,
                "",
                function(response) {
                  if (response.code == 200) {
                    self.$set(self.cfg.tools[1], "data", response.data);
                    toolData.processNodeId = "";
                    toolData.approveId = "";
                  }
                }
              );
            }
          },
          {
            name: "processNodeId",
            title: "节点",
            type: "select",
            onChange: function(s1, toolData) {
              self.currentProcessNodeId = s1;
              // self.cfg1.extraData.process.processNodeId = s1;
              self.currentApproveId = 0;
              var items = [
                {
                  name: "postionName",
                  title: "审批岗位",
                  type: "label"
                }
              ];
              self.$set(self.cfg, "items", items);
              self.$set(self.$refs.editList, "tableData", []);

              self.get(
                self.getGlobalData().ApiBaseUrl +
                  "/approve/alllist?processNodeId=" +
                  s1,
                "",
                function(response) {
                  if (response.code == 200) {
                    self.$set(self.cfg.tools[2], "data", response.data);
                    toolData.approveId = "";
                  }
                }
              );
              self.get(
                self.getGlobalData().ApiBaseUrl +
                  "/approve/matrix?processNodeId=" +
                  s1 +
                  "&conditionId=0",
                "",
                function(response) {
                  if (response.code == 200) {
                    self.$set(self.$refs.editList, "tableData", response.data);
                  }
                }
              );
            }
          },
          {
            name: "approveId",
            title: "审批路线(一)",
            type: "select",
            onChange: function(s1, toolData) {
              self.currentApproveId = s1;
              // self.cfg1.extraData.process.approveId = s1;
              var items = [
                {
                  name: "postionName",
                  title: "审批岗位",
                  type: "label"
                }
              ];
              self.$set(self.cfg, "items", items);
              self.$set(self.$refs.editList, "tableData", []);
              self.get(
                self.getGlobalData().ApiBaseUrl +
                  "/approve/allchildren?approveId=" +
                  s1,
                "",
                function(response) {
                  if (response.code == 200) {
                    var items = [
                      {
                        name: "postionName",
                        title: "审批岗位",
                        type: "label"
                      }
                    ];
                    for (var i = 0; i < response.data.length; i++) {
                      var item = response.data[i];
                      items.push({
                        name: "id_" + item.id,
                        title: item.name,
                        type: "checkbox"
                      });
                    }
                    self.$set(self.cfg, "items", items);
                  }
                }
              );
              self.get(
                self.getGlobalData().ApiBaseUrl +
                  "/approve/matrix?processNodeId=" +
                  self.currentProcessNodeId +
                  "&conditionId=" +
                  s1,
                "",
                function(response) {
                  if (response.code == 200) {
                    self.$set(self.$refs.editList, "tableData", response.data);
                  }
                }
              );
            }
          }
        ]
      },
      cfg1: {
        save: this.getGlobalData().ApiBaseUrl + "/approve/matrixUpdate",
         getExtraData: function() {
          return {
            process: {
              processNodeId: self.currentProcessNodeId,
              processId: self.currentProcessId,
              approveId: self.currentApproveId
            }
          }
        },
        validate: function() {
          if (self.currentApproveId == 0) {
            self.$message({
              type: "warning",
              message: "请先选择审批路线！"
            });
            return false;
          }
          return true;
        },
        onSuccess: function() {
          return false;
        }
      },
      currentProcessId: 0,
      currentProcessNodeId: 0,
      currentApproveId: 0
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
