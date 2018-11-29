<template>
     <div>
         <div class="box box-default">
          <div v-for="category in this.categories" :key="category.id" class="box box-default">
            <div class="box-header with-border">
              <i :class="'fa '+category.icon"></i>

              <h3 class="box-title">{{category.name}}</h3>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <router-link v-for="process in category.proesses" :key="process.id" :to="(process.url+'?processId='+process.id)||''">
               <a class="btn btn-app">
               <i class="fa fa-plus-circle"></i> {{process.name}}
              </a>
              </router-link>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
     </div>
</template>
<script>
export default {
  mounted: function() {
    var self = this;
    self.setBreadcrumbTitle(this, "流程", "发起新流程");
    self.get({
      url: "/process/category",
      success: function(response) {
        if ((response.code = 200)) {
          self.categories = response.data;
          // self.$set(self, self.categories, response.data);
        }
      }
    });
  },
  destroyed: function() {
    this.setBreadcrumbTitle(this, "", "");
  },
  data() {
    var self = this;
    return {
      categories: []
    };
  }
};
</script>
<style>
</style>

