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
        mode: "edit",
        // showCheckBox:true,
        get: {
          url: this.getGlobalData().ApiBaseUrl + "/approve/getConditions",
          params: {
            id: 0
          }
        },
        items: [
          {
            name: "name",
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
                  "/approve/positionlist?processNodeId=" +
                  s1,
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
              self.get(
                self.getGlobalData().ApiBaseUrl +
                  "/approve/allchildren?approveId=" +
                  s1,
                "",
                function(response) {
                  if (response.code == 200) {
                    var items = [
                      {
                        name: "name",
                        title: "审批岗位",
                        type: "label"
                      }
                    ];
                    for (var i = 0; i < response.data.length; i++) {
                      var item = response.data[i];
                      items.push({
                        name: item.name,
                        title: item.name,
                        type: "checkbox"
                      });
                    }
                    self.$set(self.cfg, "items", items);
                  }
                }
              );
            }
          }
        ]
      },
      cfg1: {
        save: this.getGlobalData().ApiBaseUrl + "/approve/matrixUpdate"
      }
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
