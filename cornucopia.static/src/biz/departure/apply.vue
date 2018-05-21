<template>
    <div>
      <mform :cfg="cfg"></mform>
      <mform :cfg="cfg1"></mform>
      <mform :cfg="cfg2"></mform>
    </div>
</template>
<script>
export default {
  mounted:function(){
    this.setBreadcrumbTitle(this,"发起新流程","离职申请");
  },
  destroyed:function(){
    this.setBreadcrumbTitle(this,'','');
  },
  data() {
    var self = this;
    return {
      cfg: {
        title: "申请人信息",
        detailTitle: "申请人信息",
        editTitle: "申请人信息",
        mode: "detailEdit",
        hideFooter:"true",
        get: {
          url: this.getGlobalData().ApiBaseUrl+"/user/getSelf",
          params: {
            id: this.$route.query.id
          }
        },
        // save: this.getGlobalData().ApiBaseUrl+"/role/update",
        items: [
          {
            name: "Id",
            type: "hidden"
          },
          {
            name: "name",
            title: "姓名",
            type: "text",
            width:"300px;"
          },
          {
            name: "department",
            title: "部门",
            type: "text",
            width:"300px;"
          },
          {
            name: "title",
            title: "职位",
            type: "text",
            width:"300px;"
          },
          {
            name: "workNo",
            title: "工号",
            type: "text",
            width:"300px;"
          },
          {
            name: "workEmail",
            title: "邮箱",
            type: "text",
            width:"300px;"
          }
          ,
          {
            name: "phone",
            title: "手机号",
            type: "text",
            width:"300px;"
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
        hideFooter:"true",
        get: {
          url: this.getGlobalData().ApiBaseUrl+"/role/get",
          params: {
            id: this.$route.query.id
          }
        },
        // save: this.getGlobalData().ApiBaseUrl+"/role/update",
        items: [
          {
            name: "Id",
            type: "hidden"
          },
          {
            name: "title",
            title: "职位",
            type: "text",
            width:"300px;"
          },
          {
            name: "department",
            title: "直接主管",
            type: "text",
            width:"300px;"
          },
          {
            name: "title",
            title: "入职日期",
            type: "text",
            width:"300px;"
          },
          {
            name: "workNo",
            title: "离职日期",
            type: "text",
            width:"300px;"
          },
          {
            name: "workEmail",
            title: "联系电话",
            type: "text",
            width:"300px;"
          }
          ,
          {
            name: "phone",
            title: "个人邮箱",
            type: "text",
            width:"300px;"
          },
          {
            name: "phone",
            title: "离职原因",
            type: "textarea"
          }
        ],
        afterEditRender: function() {
          //  $(self.$el).find("#Name").attr("disabled", true);
        }
      },
      cfg2: {
        title: "附件",
        detailTitle: "附件",
        editTitle: "附件",
        mode: "create",
        get: {
          url: this.getGlobalData().ApiBaseUrl+"/role/get",
          params: {
            id: this.$route.query.id
          }
        },
        // save: this.getGlobalData().ApiBaseUrl+"/role/update",
        items: [
          {
            name: "Id",
            type: "hidden"
          },
          {
            name: "name",
            title: "文件集",
            type: "uploader",
            hideLabel:"true",
            limit:10,
            url:this.getGlobalData().ApiBaseUrl+"/upload",
          }
        ],
        afterEditRender: function() {
          //  $(self.$el).find("#Name").attr("disabled", true);
        }
      },
    };
  }
};
</script>
<style>

</style>

