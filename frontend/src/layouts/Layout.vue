<template>
  <!-- <n-layout has-sider class="flex flex-auto">
    <n-layout-sider
      bordered
      collapse-mode="width"
      :collapsed-width="64"
      :width="200"
      :collapsed="collapsed"
    >
      <Sider :collapsed="collapsed" :inverted="inverted" />
    </n-layout-sider>
    <n-layout class="w-full h-full">
      <n-layout-header class="overflow-x-auto overflow-y-hidden" absolute>
        <Header class="w-full h-64px" v-model:collapsed="collapsed" v-model:inverted="inverted" />
      </n-layout-header>
      <n-layout-content
        absolute
        class="w-full overflow-auto bg-[#f6f9f8] dark:bg-[#222222] transition duration-300 ease-in-out"
        style="height: calc(100vh - 60px)"
      >
        <Content />
      </n-layout-content>
    </n-layout>
  </n-layout> -->
</template>
<script setup lang="ts">
  import { ref } from 'vue';
  import { Header, Sider, Content } from './components';
  import { useMessage } from 'naive-ui';

  const inverted = ref(false);
  const collapsed = ref(false);
</script>
<style></style>
