<template>
  <n-space justify="space-between" align="center" class="flex border-b px-6 h-16">
    <div class="flex gap-5 text-xl">
      <router-link to="/" :class="[route.name == 'index' ? 'text-green-500' : '']"
        >首页</router-link
      >
      <router-link to="/shelf" :class="[route.name == 'shelf' ? 'text-green-500' : '']"
        >书架</router-link
      >
      <router-link to="/category" :class="[route.name == 'category' ? 'text-green-500' : '']"
        >分类</router-link
      >
    </div>
    <n-input></n-input>
  </n-space>
</template>
<script setup lang="ts">
  import { useRoute } from 'vue-router';

  const route = useRoute();
</script>
<style scoped>
  .menuActive {
  }
</style>
