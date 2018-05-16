<template>
 <tree  ref="tree" :cfg="cfg"></tree>
</template>
<script>
export default {
  data() {
    var self = this;
    return {
      cfg: {
        title: "组织机构",
        parentTitle: "权限管理",
        url: this.getGlobalData().ApiBaseUrl + "/auth/allOrg",
      }
    };
  }
};
</script>