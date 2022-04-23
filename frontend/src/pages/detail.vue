<template>
  <div class="m-4 border px-6 rounded-xl">
    <div class="text-xl font-semibold py-3 border-b mb-4"> 小说详情 </div>
    <div class="flex flex-col">
      <BookList />
      <div class="flex gap-3 mt-8 items-center">
        <n-button type="primary"> 开始阅读</n-button>
        <n-button type="warning">加入书架</n-button>
        <div class="flex flex-col border-l pl-4">
          <div>收藏人数 </div>
          <div>1200</div>
        </div>
      </div>
    </div>

    <div class="text-xl font-semibold py-3 border-b mb-4 mt-15"> 章节目录 </div>
    <n-grid x-gap="12" y-gap="24" :cols="3">
      <n-gi v-for="(item, index) of 99" :key="index">
        <router-link to="/read"> 第一章 门派弃徒 </router-link>
      </n-gi>
    </n-grid>
  </div>
</template>
<script setup lang="ts"></script>
<style scoped></style>
