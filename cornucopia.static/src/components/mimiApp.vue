<template>
<div>
    <loading v-if="isLoading" @hide="hideLoading"></loading>
    <loginOrHome v-else></loginOrHome>
    </div>
</template>

<script>
export default {
  data() {
    return {
      isLoading: true
    };
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