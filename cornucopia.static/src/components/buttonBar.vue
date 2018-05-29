<template>
    <div>
        <el-button
          size="mini"
          @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">删除</el-button>
    </div>
</template>
<script>
export default {
  data() {
    return {
      message: "fds"
    };
  }
};
</script>
