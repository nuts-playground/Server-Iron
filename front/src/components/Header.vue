<script setup lang="ts">
import { ref } from 'vue'

const activeIndex = ref('1');

const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
};


</script>

<template>


  <el-menu :default-active="activeIndex" class="el-menu-demo header" mode="horizontal" @select="handleSelect">
    <el-menu-item index="0">
      <img style="width: 100px" src="#" alt="로고 넣을 곳"/>
    </el-menu-item>
    <el-menu-item index="1">Home</el-menu-item>
    <el-sub-menu index="2">
      <template #title>Post</template>
      <el-menu-item index="/read">Read</el-menu-item>
      <el-menu-item index="/write">Write</el-menu-item>
    </el-sub-menu>
    <el-menu-item index="3">Map</el-menu-item>
  </el-menu>

</template>
<style scoped>


.header {
  padding: 0;
  height: 60px;
}
</style>