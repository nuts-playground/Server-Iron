<script setup lang="ts">
import Header from "@/components/Header.vue";
import Content from "@/components/Content.vue";
import HomeBanner from "@/components/HomeBanner.vue";
import Aside from "@/components/Aside.vue";


</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-container>
        <HomeBanner/>
      </el-container>

      <el-header>
        <Header/>
      </el-header>

      <el-container>
        <el-aside>
          <Aside/>
        </el-aside>

        <el-container>
          <el-main>

            <Content/>
          </el-main>
        </el-container>
      </el-container>

    </el-container>
  </div>


</template>

<style>
body {
  margin: 0;
  width: 100%;
  font-family: Pretendard, apple-system, BlinkMacSystemFont, system-ui, Roboto,
    'Helvetica Neue', 'Segoe UI', 'Apple SD Gothic Neo', 'Noto Sans KR', 'Malgun Gothic',
    sans-serif;


}
</style>
