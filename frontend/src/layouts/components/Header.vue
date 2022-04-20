<template>
  <n-space justify="space-between" class="w-full h-full min-w-screen-sm flex">
    <n-space align="center" class="h-full">
      <div @click="$emit('update:collapsed', !collapsed)">
        <div class="p-18px">
          <i-ri-menu-unfold-fill v-if="collapsed" class="align-middle" />
          <i-ri-menu-fold-fill v-else class="align-middle" />
        </div>
      </div>
      <div class="p-18px">
        <i-mdi-refresh class="align-middle" />
      </div>
    </n-space>

    <n-space align="center">
      <div @click="inverted = !inverted" class="p-18px">
        <i-ion-sunny v-if="inverted" class="align-middle" />
        <i-bi-moon-fill v-else class="align-middle" />
      </div>
      <!-- <n-dropdown :options="options" @select="handleSelect" class="align-middle">
        <n-icon> <i-clarity-avatar-solid class="align-middle" /></n-icon>
        多凡
      </n-dropdown> -->
    </n-space>
  </n-space>
</template>
<script setup lang="ts">
  import { useRouter } from 'vue-router';
  defineProps({
    collapsed: Boolean,
    inverted: Boolean,
  });
</script>
<style></style>
