<template>
  <div>
    <mform v-show="isApprove" ref="approve" :cfg="cfgApprove"></mform>
    <mform ref="applicant" :cfg="cfgApplicant"></mform>
    <component ref="cellar" v-bind:is="cellar"></component>
    <mform ref="attachment" :cfg="cfgAttachment"></mform>
    <comment ref="comments" :cfg="cfgComment"></comment>
    <buttonBar v-show="!isApprove" ref="submit" :cfg="cfgSubmit"></buttonBar>
    <buttonBar v-show="isApprove" ref="agree" :cfg="cfgAgree"></buttonBar>
    <el-dialog append-to-body :visible.sync="dialogVisible" :width="'65%'" >
      <component  style="margin-top:-40px;margin-bottom:-40px;" v-bind:is="currentComponent"></component>
    </el-dialog>
  </div>
</template>
<script>
export default {
  methods: {
    getBizData: function(processId, dataId, processInstAuth) {
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
                  self.$refs.comments.setCurrentStep(
                    self.processInstAuth.currentStep
                  );
                }, 200);

                self.cfgComment.mode = "edit";

                if (self.processInstAuth.doaName == "Retry") {
                   self.findCfgBtn(self.cfgAgree, "重发起").hidden = false;
                  //  self.findCfgBtn(cfgAgree, "作废").hidden = false;
                }else if (self.processInstAuth.doaName == "PreSign") {
                  self.findCfgBtn(self.cfgAgree, "同意").hidden = false;
                  self.findCfgBtn(self.cfgAgree, "退回").hidden = false;
                }else if (self.processInstAuth.doaName == "AfterSign") {
                  self.findCfgBtn(self.cfgAgree, "同意").hidden = false;
                  self.findCfgBtn(self.cfgAgree, "退回").hidden = false;
                }else if (self.processInstAuth.doaName == "Transfer") {
                  self.findCfgBtn(self.cfgAgree, "同意").hidden = false;
                  self.findCfgBtn(self.cfgAgree, "退回").hidden = false;
                }else if (self.processInstAuth.doaName == "Modify") {
                  self.findCfgBtn(self.cfgAgree, "同意").hidden = false;
                  self.findCfgBtn(self.cfgAgree, "退回").hidden = false;
                } else {
                  self.findCfgBtn(self.cfgAgree, "同意").hidden = false;
                  self.findCfgBtn(self.cfgAgree, "退回").hidden = false;
                  if(self.processInstAuth.preSign==1){
                    self.findCfgBtn(self.cfgAgree, "前加签").hidden = false;
                  }
                  if(self.processInstAuth.afterSign==1){
                    self.findCfgBtn(self.cfgAgree, "后加签").hidden = false;
                  }
                  if(self.processInstAuth.transfer==1){
                    self.findCfgBtn(self.cfgAgree, "转办").hidden = false;
                  }
                  if(self.processInstAuth.modify==1){
                    self.findCfgBtn(self.cfgAgree, "申请人修订").hidden = false;
                  }
                }
              } else {
                // self.cfg1.mode = "detailEdit";
                self.cfgAttachment.mode = "detailEdit";
              }
            }
            self.getBizData(processId, dataId, self.processInstAuth);
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
      dialogVisible:false,
      currentComponent:'',
      processInstAuth: {},
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
            name: "退回",
            url: "/process/applyReturn",
            hidden: true,
            onSuccess: function() {
              self.$router.push({ path: "/mytask" });
            }
          },
          {
            name: "转办",
            type: "success",
            hidden: true,
            hideTipMsg:true,
            onClick:function(item){
              self.dialogVisible = true;
            },
            onSuccess: function() {
              self.$router.push({ path: "/mytask" });
            }
          },
          {
            name: "前加签",
            type: "success",
            url: "/process/applyReturn",
            hidden: true,
            onSuccess: function() {
              self.$router.push({ path: "/mytask" });
            }
          },
          {
            name: "后加签",
            type: "success",
            url: "/process/applyReturn",
            hidden: true,
            onSuccess: function() {
              self.$router.push({ path: "/mytask" });
            }
          },
          {
            name: "申请人修订",
            type: "success",
            url: "/process/applyReturn",
            hidden: true,
            onSuccess: function() {
              self.$router.push({ path: "/mytask" });
            }
          },
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
      }
    };
  }
};
</script>
<style>
</style>