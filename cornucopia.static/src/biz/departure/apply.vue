<template>
    <div>
      <mform v-show="isApprove" ref="approve" :cfg="cfg0"></mform>
      <mform ref="applicant" :cfg="cfg"></mform>
      <mform ref="applyInfo" :cfg="cfg1"></mform>
      <mform ref="attachment" :cfg="cfg2"></mform>
      <comment ref="comments" :cfg="cfgComment"></comment>
      <buttonBar v-show="!isApprove" ref="submit" :cfg="cfg3"></buttonBar>
      <buttonBar v-show="isApprove" ref="agree" :cfg="cfg4"></buttonBar>
    </div>
</template>
<script>
export default {
  mounted: function() {
    let self = this;
    this.setBreadcrumbTitle(this, "发起新流程", "离职申请");
    this.$nextTick(function() {
      var processId = self.$route.query.processId;
      var dataId = self.$route.query.id;
      if (dataId) {
        self.isApprove = true;
        self.openLoading();
        self.get({
          url: "/process/getBizData?processId=" + processId + "&id=" + dataId,
          success: function(response) {
            if (response.code == 200) {
              self.closeLoading();
              let dataJson = JSON.parse(response.data);
              Object.keys(dataJson).forEach(function(key) {
                if (key == "comments") {
                  setTimeout(function() {
                    self.$parent.$refs.diagram.loadDiagram();
                    self.$refs.comments.setMessages(dataJson.comments);
                  }, 200);
                } else {
                  let obj = dataJson[key][0];
                  Object.keys(obj).forEach(function(subKey) {
                    if (self.$refs[key] && self.$refs[key].detail) {
                      try {
                        self.$refs[key].detail[subKey] = obj[subKey];
                      } catch (err) {
                        console.log(err);
                      }
                    }
                  });
                }
              });
            }
          }
        });

        self.get({
          url: "/process/getProcessInstAuth?processDataId=" + dataId,
          success: function(response) {
            if (response.code == 200) {
              if (response.data.length > 0) {
                self.processInstAuth = response.data;
                for (var i = 0; i < self.processInstAuth.length; i++) {
                  let index = i;
                  setTimeout(function() {
                    self.$refs.comments.isHideReply = false;
                    self.$refs.comments.setCurrentStep(
                      self.processInstAuth[index].currentStep
                    );
                  }, 200);
                  break;
                }
                self.cfg4.buttons[0].hidden = false;
                self.cfg4.buttons[2].hidden = false;
                self.cfgComment.mode = "edit";
                for (var i = 0; i < response.data.length; i++) {
                  if (response.data[i].buttonType == "Retry") {
                    self.cfg4.buttons[1].hidden = false;
                    self.cfg4.buttons[0].hidden = true;
                    self.cfg4.buttons[2].hidden = true;
                  }
                }
              } else {
                self.cfg1.mode = "detailEdit";
                self.cfg2.mode = "detailEdit";
              }
            }
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
      processInstAuth: [],
      cfg0: {
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
      cfg: {
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
      cfg1: {
        title: "申请信息",
        detailTitle: "申请信息",
        editTitle: "申请信息",
        mode: "create",
        hideFooter: "true",
        name: "applyInfo",
        items: [
          {
            name: "Id",
            type: "hidden"
          },
          {
            name: "title",
            title: "职位",
            type: "text",
            width: "300px;",
            isRequire: true
          },
          {
            name: "managerId",
            title: "直接主管",
            type: "text",
            width: "300px;"
          },
          {
            name: "entryDate",
            title: "入职日期",
            type: "timer",
            width: "300px;",
            isRequire: true
          },
          {
            name: "leaveDate",
            title: "离职日期",
            type: "text",
            width: "300px;",
            isRequire: true
          },
          {
            name: "phone",
            title: "联系电话",
            type: "text",
            width: "300px;",
            isRequire: true
          },
          {
            name: "workEmail",
            title: "个人邮箱",
            type: "text",
            width: "300px;",
            isRequire: true
          },
          {
            name: "reason",
            title: "离职原因",
            type: "textarea",
            isRequire: true
          }
        ],
        afterEditRender: function() {
          //  $(self.$el).find("#Name").attr("disabled", true);
        },
        rules: {
          title: {
            required: true
          }
        },
        messages: {
          title: {
            required: "职位必须填写"
          }
        }
      },
      cfg2: {
        title: "附件",
        detailTitle: "附件",
        editTitle: "附件",
        mode: "create",
        hideFooter: "true",
        desc: "任意文件，不能超过2M",
        name: "attachment",
        get: {
          url:  "/role/get",
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
            url:  "/upload"
          }
        ],
        afterEditRender: function() {}
      },
      cfg3: {
        save:  "/process/applySave",
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
      cfg4: {
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
            url:  "/process/applyAgree",
            onSuccess: function() {
              self.$router.push({ path: "/mytask" });
            },
            hidden: true
          },
          {
            name: "重发起",
            type: "success",
            url:  "/process/applyRetry",
            onSuccess: function() {
              self.$router.push({ path: "/mytask" });
            },
            hidden: true
          },
          {
            name: "退回",
            url:  "/process/applyReturn",
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

