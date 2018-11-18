<template>
    <div>
        <mform ref="applyInfo" :cfg="cfg1"></mform>
    </div>
</template>
<script>
export default {
  data() {
    return {
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
      }
    };
  }
};
</script>
