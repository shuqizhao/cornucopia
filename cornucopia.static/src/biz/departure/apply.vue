<template>
    <div>
      <mform :cfg="cfg"></mform>
      <mform :cfg="cfg1"></mform>
      <mform :cfg="cfg2"></mform>
      <buttonBar :cfg="cfg3"></buttonBar>
    </div>
</template>
<script>
export default {
  mounted: function() {
    this.setBreadcrumbTitle(this, "发起新流程", "离职申请");
  },
  destroyed: function() {
    this.setBreadcrumbTitle(this, "", "");
  },
  data() {
    var self = this;
    return {
      cfg: {
        title: "申请人信息",
        detailTitle: "申请人信息",
        editTitle: "申请人信息",
        mode: "detailEdit",
        hideFooter: "true",
        name: "applicant",
        get: {
          url: this.getGlobalData().ApiBaseUrl + "/user/getApplicant",
          params: {
            id: this.$route.query.id
          }
        },
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
        get: {
          url: this.getGlobalData().ApiBaseUrl + "/role/get",
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
          url: this.getGlobalData().ApiBaseUrl + "/role/get",
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
            hideLabel: "true",
            url: this.getGlobalData().ApiBaseUrl + "/upload"
          }
        ],
        afterEditRender: function() {}
      },
      cfg3: {
        save: this.getGlobalData().ApiBaseUrl + "/process/applySave",
        dataType: "xml",
        extraData: {
          approve: {
            processId: this.$route.query.processId
          }
        }
      }
    };
  }
};
</script>
<style>
</style>

