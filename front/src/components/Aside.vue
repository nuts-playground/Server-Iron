<script setup lang="ts">
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
</script>

<template>
  <el-row class="tac">
    <el-col :span="24">
      <el-menu
          default-active="2"
          class="el-menu-vertical"
          @open="handleOpen"
          @close="handleClose"
      >
        <el-sub-menu index="1">
          <template #title>
            <el-icon><location /></el-icon>
            <span>소개</span>
          </template>
          <el-menu-item index="1-1">과거</el-menu-item>
          <el-menu-item index="1-2">현재</el-menu-item>
          <el-menu-item index="1-3">미래</el-menu-item>
        </el-sub-menu>

        <el-menu-item index="2">
          <el-icon><icon-menu /></el-icon>
          <span>활동</span>
        </el-menu-item>

        <el-menu-item index="3" disabled>
          <el-icon><document /></el-icon>
          <span>비밀</span>
        </el-menu-item>

        <el-menu-item index="4">
          <el-icon><setting /></el-icon>
          <span>공부</span>
        </el-menu-item>
      </el-menu>
    </el-col>
  </el-row>
</template>

<style scoped>

</style>