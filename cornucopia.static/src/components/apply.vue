<template>
  <div>
    <mform v-show="isApprove" ref="approve" :cfg="cfgApprove"></mform>
    <mform ref="applicant" :cfg="cfgApplicant"></mform>
    <component ref="cellar" v-bind:is="cellar"></component>
    <mform ref="attachment" :cfg="cfgAttachment"></mform>
    <comment ref="comments" :cfg="cfgComment"></comment>
    <buttonBar v-show="!isApprove" ref="submit" :cfg="cfgSubmit"></buttonBar>
    <buttonBar v-show="isApprove" ref="agree" :cfg="cfgAgree"></buttonBar>
    <el-dialog
      ref="applyDialog"
      append-to-body
      :visible.sync="dialogVisible"
      :center="true"
      :width="'65%'"
      :title="applyDialogTitle"
    >
      <component
        ref="myApplyCompent"
        style="margin-top:-40px;margin-bottom:-40px;"
        v-bind:is="currentComponent"
      ></component>
      <span slot="footer" class="dialog-footer">
        <el-button @click="onDialogBtnCancel">取 消</el-button>
        <el-button type="primary" @click="onDialogBtnOk">确 定</el-button>
      </span>
    </el-dialog>
    <fab
      v-show="this.$route.query.showDiagram&&this.fabActions.length>0"
      :position="position"
      :toggle-when-away="true"
      :start-opened="false"
      :actions="fabActions"
      :bg-color="bgColor"
      main-tooltip="更多..."
      @preSign="onPreSign"
      @afterSign="onAfterSign"
      @transfer="onTransfer"
      @modify="onModify"
    ></fab>
  </div>
</template>
<script>
import fab from "vue-fab";
import selectUser from "./selectUser.vue";
export default {
  components: {
    fab,
    selectUser
  },
  methods: {
    doAction: function(action, userId, processDataId, approvePositionId, data) {
      var self = this;
      self.openLoading(self, null, "正在提交...", 15000);
      self.post({
        url: "/process/doAction",
        data: {
          action: action,
          userId: userId,
          processDataId: processDataId,
          approvePositionId: approvePositionId,
          xmlStr: "",
          jsonStr: JSON.stringify(data)
        },
        success: function(response) {
          if (response.code == 200) {
            self.closeLoading(self);
            self.$message({
              type: "success",
              message: "成功!"
            });
            window.location.reload();
          }
        }
      });
    },
    onDialogBtnCancel: function() {
      this.dialogVisible = false;
      this.currentComponent = "";
    },
    onDialogBtnOk: function() {
      var self = this;
      var selectedTableData = this.$refs.myApplyCompent.selectedTableData;
      if (selectedTableData.length == 0) {
        this.$message({
          message: "未选择员工",
          type: "warning"
        });
      } else {
        this.dialogVisible = false;
        this.currentComponent = "";
        var messsage = "";
        if (this.currentAction == "preSign") {
          messsage = "前加签";
        } else if (this.currentAction == "afterSign") {
          messsage = "后加签";
        } else if (this.currentAction == "transfer") {
          messsage = "转办";
        } else if (this.currentAction == "modify") {
          messsage = "申请人修订";
        }
        this.$confirm("确认要『" + messsage + "』吗, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            $(this.$el)
              .find(".message")
              .val("");
            // self.$refs.agree.currentAction = messsage;
            this.doAction(
              this.currentAction,
              selectedTableData[0].id,
              this.$route.query.id,
              this.processInstAuth.approvePositionId,
              this.getLocalBizData(messsage)
            );
          })
          .catch(e => {
            console.log(e);
          });
      }
    },
    buildFabButtons: function(processInstAuth) {
      if (processInstAuth.preSign == 1) {
        this.fabActions.push({
          name: "preSign",
          tooltip: "前加签",
          icon: "undo"
        });
      }
      if (processInstAuth.afterSign == 1) {
        this.fabActions.push({
          name: "afterSign",
          tooltip: "后加签",
          icon: "redo"
        });
      }
      if (processInstAuth.transfer == 1) {
        this.fabActions.push({
          name: "transfer",
          tooltip: "转办",
          icon: "transfer_within_a_station"
        });
      }
      if (processInstAuth.modify == 1) {
        this.fabActions.push({
          name: "modify",
          tooltip: "申请人修订",
          icon: "rowing"
        });
      }
    },
    onPreSign() {
      this.dialogVisible = true;
      this.currentComponent = selectUser;
      this.currentAction = "preSign";
      this.applyDialogTitle = "选择员工进行『前加签』操作";
    },
    onAfterSign() {
      this.dialogVisible = true;
      this.currentComponent = selectUser;
      this.currentAction = "afterSign";
      this.applyDialogTitle = "选择员工进行『后加签』操作";
    },
    onModify() {
      this.$prompt("确认要『申请人修订』吗, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /.+/,
        inputErrorMessage: "请输入修订原因",
        inputPlaceholder: "修订原因",
        inputType: "textarea",
        type: "warning"
      })
        .then(({ value }) => {
          $(this.$el)
            .find(".message")
            .val(value);
          // self.$refs.agree.currentAction = "申请人修订";
          // this.$refs.comments.addMessage({
          //   msg: value + "<br/>" + this.$refs.comments.getNowFormatDate(),
          //   action: this.$refs.comments.currentStep + "-申请人修订",
          //   time: this.$refs.comments.getNowFormatDate(),
          //   name: this.$refs.comments.currentName
          // });
          this.doAction(
            "modify",
            0,
            this.$route.query.id,
            this.processInstAuth.approvePositionId,
            this.getLocalBizData("申请人修订")
          );
        })
        .catch(e => {
          console.log(e);
        });
    },
    onTransfer() {
      this.dialogVisible = true;
      this.currentComponent = selectUser;
      this.currentAction = "transfer";
      this.applyDialogTitle = "选择员工进行『转办』操作";
    },
    getRemoteBizData: function(processId, dataId, processInstAuth) {
      let self = this;
      self.openLoading();
      self.get({
        url: "/process/getBizData?processId=" + processId + "&id=" + dataId,
        success: function(response) {
          if (response.code == 200) {
            self.closeLoading();
            let dataJson = JSON.parse(response.data);
            // self.$emit("afterDataLoad", dataJson);
            Object.keys(dataJson).forEach(function(key) {
              if (key == "comments") {
                setTimeout(function() {
                  self.findRef("diagram").loadDiagram();
                  self.$refs.comments.setMessages(dataJson.comments);
                }, 200);
              } else {
                let obj = dataJson[key];
                Object.keys(obj).forEach(function(subKey) {
                  if (self.$refs[key] && self.$refs[key].detail) {
                    try {
                      self.$refs[key].detail[subKey] = obj[subKey];
                    } catch (err) {
                      console.log(err);
                    }
                  } else {
                    var other = self.findRef(key);
                    if (other && other.detail) {
                      try {
                        other.detail[subKey] = obj[subKey];
                      } catch (err) {
                        console.log(err);
                      }
                    }
                  }
                });
                // debugger;
                var theCompent = self.findRef("cellar");
                if (theCompent && theCompent.afterDataLoad) {
                  theCompent.afterDataLoad(key, obj, dataJson, processInstAuth);
                }
              }
            });
          }
        }
      });
    },
    getLocalBizData: function(name) {
      return this.$refs.agree.getLocalBizData({ name: name, url: "" });
    }
  },
  mounted: function() {
    let self = this;
    this.$nextTick(function() {
      var processId = self.$route.query.processId;
      var dataId = self.$route.query.id;
      if (dataId) {
        self.isApprove = true;
        self.get({
          url: "/process/getProcessInstAuth?processDataId=" + dataId,
          success: function(response) {
            if (response.code == 200) {
              if (response.data.length > 0) {
                self.processInstAuth = response.data[0];
                setTimeout(function() {
                  self.$refs.comments.isHideReply = false;
                  var currentStep = self.processInstAuth.currentStep;
                  if (self.processInstAuth.currentStep == "preSign") {
                    currentStep = self.processInstAuth.vitualTitle + "-前加签";
                  } else if (self.processInstAuth.currentStep == "afterSign") {
                    currentStep = self.processInstAuth.vitualTitle + "-后加签";
                  } else if (self.processInstAuth.currentStep == "transfer") {
                    currentStep = self.processInstAuth.vitualTitle + "-转办";
                  } else if (self.processInstAuth.currentStep == "modify") {
                    currentStep = self.processInstAuth.vitualTitle;
                  }
                  self.$refs.comments.setCurrentStep(currentStep);
                }, 200);

                self.cfgComment.mode = "edit";
                self.buildFabButtons(self.processInstAuth);
                if (self.processInstAuth.doaName == "Retry") {
                  self.findCfgBtn(self.cfgAgree, "重发起").hidden = false;
                  self.findCfgBtn(self.cfgAgree, "作废").hidden = false;
                } else if (self.processInstAuth.doaName == "PreSign") {
                  self.findCfgBtn(self.cfgAgree, "同意").hidden = false;
                  self.findCfgBtn(self.cfgAgree, "退回").hidden = false;
                } else if (self.processInstAuth.doaName == "AfterSign") {
                  self.findCfgBtn(self.cfgAgree, "同意").hidden = false;
                  self.findCfgBtn(self.cfgAgree, "退回").hidden = false;
                } else if (self.processInstAuth.doaName == "Transfer") {
                  self.findCfgBtn(self.cfgAgree, "同意").hidden = false;
                  self.findCfgBtn(self.cfgAgree, "退回").hidden = false;
                } else if (self.processInstAuth.doaName == "Modify") {
                  self.findCfgBtn(self.cfgAgree, "同意").hidden = false;
                  // self.findCfgBtn(self.cfgAgree, "退回").hidden = false;
                } else {
                  self.findCfgBtn(self.cfgAgree, "同意").hidden = false;
                  self.findCfgBtn(self.cfgAgree, "退回").hidden = false;
                }
              } else {
                // self.cfg1.mode = "detailEdit";
                self.cfgAttachment.mode = "detailEdit";
              }
            }
            self.getRemoteBizData(processId, dataId, self.processInstAuth);
          }
        });
      } else {
        self.isApprove = false;
        self.cfgComment.mode = "edit";
        setTimeout(function() {
          self.$refs.applicant.detail = self.getGlobalData().LoginUser;
        }, 200);
      }
    });
  },
  destroyed: function() {
    this.setBreadcrumbTitle(this, "", "");
  },
  data() {
    let self = this;
    return {
      isApprove: false,
      cellar: "",
      dialogVisible: false,
      currentComponent: "",
      processInstAuth: {},
      currentAction: "",
      cfgApprove: {
        title: "单据信息",
        detailTitle: "单据信息",
        editTitle: "单据信息",
        mode: "detailEdit",
        hideFooter: "true",
        name: "approve",
        items: [
          {
            name: "id",
            type: "hidden"
          },
          {
            name: "fromCode",
            title: "单号",
            type: "text",
            width: "300px;"
          },
          {
            name: "createTime",
            title: "创建时间",
            type: "text",
            width: "300px;"
          },
          {
            name: "processId",
            type: "hidden"
          },
          {
            name: "createBy",
            type: "hidden"
          }
          // {
          //   name: "updateTime",
          //   title: "上次时间",
          //   type: "text",
          //   width: "300px;"
          // }
        ]
      },
      cfgApplicant: {
        title: "申请人信息",
        detailTitle: "申请人信息",
        editTitle: "申请人信息",
        mode: "detailEdit",
        hideFooter: "true",
        name: "applicant",
        // get: {
        //   // url:  "/user/getApplicant",
        //   params: {
        //     id: this.$route.query.id
        //   }
        // },
        items: [
          {
            name: "id",
            type: "hidden"
          },
          {
            name: "departmentId",
            type: "hidden"
          },
          {
            name: "titleId",
            type: "hidden"
          },
          {
            name: "name",
            title: "姓名",
            type: "text",
            width: "300px;"
          },
          {
            name: "department",
            title: "部门",
            type: "text",
            width: "300px;"
          },
          {
            name: "title",
            title: "职位",
            type: "text",
            width: "300px;"
          },
          {
            name: "workNo",
            title: "工号",
            type: "text",
            width: "300px;"
          },
          {
            name: "workEmail",
            title: "邮箱",
            type: "text",
            width: "300px;"
          },
          {
            name: "phone",
            title: "手机号",
            type: "text",
            width: "300px;"
          }
        ],
        afterEditRender: function() {
          //  $(self.$el).find(".box-footer").hide();
        }
      },
      cfgAttachment: {
        title: "附件",
        detailTitle: "附件",
        editTitle: "附件",
        mode: "create",
        hideFooter: "true",
        desc: "任意文件，不能超过2M",
        name: "attachment",
        get: {
          url: "/role/get",
          params: {
            id: this.$route.query.id
          }
        },
        items: [
          {
            name: "Id",
            type: "hidden"
          },
          {
            name: "files",
            title: "文件集",
            type: "uploader",
            limit: 10,
            // mode:'xml',
            hideLabel: "true",
            url: "/upload"
          }
        ],
        afterEditRender: function() {}
      },
      cfgSubmit: {
        save: "/process/applySave",
        saveButtonTitle: "发起申请",
        dataType: "xml",
        getExtraData: function() {
          return {
            approve: {
              processId: self.$route.query.processId
            }
          };
        },
        getExtraDataSkipValidate: function() {
          return {
            comments: self.$refs.comments.getSendMsg("发起申请")
          };
        },
        onSuccess: function() {
          self.$router.push({ path: "/launchedtask" });
          return false;
        }
      },
      cfgAgree: {
        hideSave: true,
        hideCancel: true,
        dataType: "xml",
        getExtraData: function() {
          return {
            approve: {
              processId: self.$route.query.processId
            }
          };
        },
        getExtraDataSkipValidate: function() {
          return {
            comments: self.$refs.comments.getSendMsg(
              self.$refs.agree.currentAction
            )
          };
        },
        buttons: [
          {
            name: "同意",
            type: "success",
            url: "/process/applyAgree",
            onSuccess: function() {
              self.$router.push({ path: "/mytask" });
            },
            hidden: true
          },
          {
            name: "重发起",
            type: "success",
            url: "/process/applyRetry",
            onSuccess: function() {
              self.$router.push({ path: "/mytask" });
            },
            hidden: true
          },
          {
            name: "作废",
            type: "success",
            url: "/process/applyDicard",
            onSuccess: function() {
              self.$router.push({ path: "/mytask" });
            },
            hidden: true
          },
          {
            name: "退回",
            url: "/process/applyReturn",
            hidden: true,
            onSuccess: function() {
              self.$router.push({ path: "/mytask" });
            }
          },
          // {
          //   name: "转办",
          //   type: "success",
          //   hidden: true,
          //   hideTipMsg: true,
          //   onClick: function(item) {
          //     self.dialogVisible = true;
          //   },
          //   onSuccess: function() {
          //     self.$router.push({ path: "/mytask" });
          //   }
          // },
          // {
          //   name: "前加签",
          //   type: "success",
          //   url: "/process/applyReturn",
          //   hidden: true,
          //   onSuccess: function() {
          //     self.$router.push({ path: "/mytask" });
          //   }
          // },
          // {
          //   name: "后加签",
          //   type: "success",
          //   url: "/process/applyReturn",
          //   hidden: true,
          //   onSuccess: function() {
          //     self.$router.push({ path: "/mytask" });
          //   }
          // },
          // {
          //   name: "申请人修订",
          //   type: "success",
          //   url: "/process/applyReturn",
          //   hidden: true,
          //   onSuccess: function() {
          //     self.$router.push({ path: "/mytask" });
          //   }
          // },
          {
            name: "关闭",
            type: "warning",
            onClick: function() {
              window.close();
            }
          }
        ]
      },
      cfgComment: {
        title: "审批历史记录",
        name: "comments",
        mode: "detail"
      },
      bgColor: "#194D33",
      position: "bottom-right",
      fabActions: [],
      applyDialogTitle: "选择员工"
    };
  }
};
</script>
<style scoped>
@import "../ref/animate.min.css";
@import "../ref/icon.css";
</style>
<style>
.el-message-box__status {
  top: 17%;
}
</style>
