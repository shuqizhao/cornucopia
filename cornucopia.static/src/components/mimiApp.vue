<template>
<div>
    <loading v-if="isLoading" @hide="hideLoading"></loading>
    <loginOrHome v-else :collapse="this.collapse"></loginOrHome>
    </div>
</template>

<script>
export default {
  props: ["collapse"],
  data() {
    return {
      isLoading: true
    };
  },
  mounted:function(){
  },
  methods: {
    hideLoading() {
      this.isLoading = false;
    }
  }
};
</script>

<style>

</style>