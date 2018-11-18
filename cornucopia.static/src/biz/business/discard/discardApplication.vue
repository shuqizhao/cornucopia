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
            name: "discardType",
            title: "报废类别",
            type: "select",
            data: [
              { id: 0, value: "车辆" },
              { id: 1, value: "物料" },
              { id: 2, value: "锁" }
            ],
            width: "300px;",
            isRequire: true
          },
          {
            name: "area",
            title: "区域",
            type: "text",
            width: "300px;"
          },
          {
            name: "position",
            title: "位置",
            type: "text",
            width: "300px;",
            isRequire: true
          },
          {
            name: "remarks",
            title: "备注",
            type: "textarea",
            isRequire: true
          }
        ],
        afterEditRender: function() {
          //  $(self.$el).find("#Name").attr("disabled", true);
        },
        rules: {
          position: {
            required: true
          },
          discardType: {
            required: true
          },remarks: {
            required: true
          }
        },
        messages: {
          position: {
            required: "位置必须填写"
          }
        }
      }
    };
  }
};
</script>
